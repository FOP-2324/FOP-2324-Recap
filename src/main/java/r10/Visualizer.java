package r10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.util.List;
import java.util.*;
import java.util.function.Function;

/**
 * Visualizes the internal structure of a SkipList
 * Only use {@link #step(SkipList)} and {@link #stepWithHighlight(SkipList, ListItem)}
 * @param <T> The value-type of the SkipList
 */
public class Visualizer<T> {

    private static final boolean DARK_MODE = true;

    /**
     * Shows the current status of the given SkipList and waits until users presses ENTER or SPACE
     *
     * @param list The List
     */
    public static void step(SkipList<?> list) {
        stepWithHighlight(list, null);
    }

    /**
     * Shows the current status of the given SkipList while highlighting the given node and waits until users presses ENTER or SPACE
     *
     * @param list      The List
     * @param highlight The node to highlight
     */
    public static void stepWithHighlight(SkipList<?> list, ListItem<? extends ExpressNode<?>> highlight) {
        if(closedOnce) {
            return;
        }
        if(currentInstance == null || currentInstance.skipList != list) {
            createNewInstance(list);
        }

        currentInstance.internalStepWithHighlight(highlight);
    }

    private void internalStepWithHighlight(ListItem<? extends ExpressNode<?>> highlight) {
        if(!frame.isVisible()) {
            frame.setVisible(true);
        }

        final ListItem<? extends ExpressNode<?>> usedHighlight;

        if(highlight != null && collectAllNodes().stream().map(s -> s.item).noneMatch(i -> i.node == highlight)) {
            System.out.println("WARNING! Node that should be highlighted is not yet added to the SkipList and thus not visible!");
            usedHighlight = null;
        }
        else {
            usedHighlight = highlight;
        }

        // Only wait on any changes
        final FontRenderContext ctx = new FontRenderContext(new AffineTransform(),true,true);
        final List<RenderElement> elements = createElements(ctx);

        if(usedHighlight != highlightedItem || !Objects.equals(lastElements, elements)) {
            lastElements = elements;
            highlightedItem = usedHighlight;
            painter.repaint();

            try {
                if(!killed) {
                    waitThread = Thread.currentThread();
                    Thread.sleep(LONG_WAIT_MILLIS);
                }
            }
            catch(InterruptedException ignored) {
                // Ignore exception. This is supposed to happen if users presses ENTER or SPACE
            } finally {
                waitThread = null;
            }
        }
    }

    private static final double MAX_ABSOLUTE_SCALE = 3;
    private static final String FRAME_TITLE = "SkipList Visualizer";
    private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, 25);
    private static final double EXTRA_PADDING = 10;
    private static final double DOT_SIZE = 10;
    private static final double BORDER_THICKNESS = 2;
    private static final double BEZIER_POINT_DISTANCE = 20;
    private static final double BEZIER_LOOP_DISTANCE_FACTOR = 0.1;
    private static final double BEZIER_MIN_LOOP_DISTANCE = 4;
    private static final double VERTICAL_SPACING = 2;
    private static final double HORIZONTAL_SPACING = 1.5;
    private static final double SCALE_FACTOR = 0.9;
    private static final double ARROW_LINE_THICKNESS = 7;
    private static final double ARROW_HEAD_LENGTH = 20;
    private static final double ARROW_HEAD_THICKNESS = 12;
    private static final int LONG_WAIT_MILLIS = 100_000_000;
    private static final Color FOREGROUND_HOVERING_COLOR = Color.BLUE;
    private static final Color FOREGROUND_COLOR = DARK_MODE? Color.WHITE : Color.BLACK;
    private static final Color BACKGROUND_COLOR = DARK_MODE ? Color.BLACK : Color.WHITE;
    private static final Color NODE_BACKGROUND_SELECTED_COLOR = DARK_MODE? new Color(19, 39, 92) : new Color(148, 176, 255);
    private static final Color NODE_BACKGROUND_COLOR = BACKGROUND_COLOR;
    private static volatile boolean closedOnce = false;
    private static Visualizer<?> currentInstance = null;
    private final SkipList<T> skipList;
    private final Object hoverMonitor = new Object();
    private final List<Map.Entry<Rectangle2D.Double, Item<T>>> hoverNodeList = new ArrayList<>();
    private Painter painter;
    private JFrame frame;
    private List<RenderElement> lastElements = null;
    private volatile boolean killed = false;
    private volatile Thread waitThread = null;
    private ListItem<? extends ExpressNode<?>> highlightedItem = null;
    private Item<T> currentHoverItem = null;

    public Visualizer(Visualizer<?> reuse, SkipList<T> list) {
        skipList = list;
        createFrame(reuse, reuse == null? null : reuse.frame);
    }

    private void createFrame(Visualizer<?> oldVisualizer, JFrame frameToReuse) {
        if(frameToReuse != null) {
            frame = frameToReuse;
            painter = new Painter();
            SwingUtilities.invokeLater(() -> {
                frame.remove(oldVisualizer.painter);
                frame.add(painter);
                frame.revalidate();
                painter.setup();
                painter.repaint();
            });
        }
        else {
            frame = new JFrame(FRAME_TITLE);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize((int) Math.round(screenSize.getWidth() * 0.3), (int) Math.round(screenSize.getWidth() * 0.3));
            frame.setLocationRelativeTo(null);

            frame.add(painter = new Painter());
            painter.setup();
        }

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closedOnce = true;
                delete();
            }
        });
    }

    private void delete() {
        killed = true;

        Thread waitThread = this.waitThread;
        if(waitThread != null) {
            waitThread.interrupt();
        }
    }

    private static <T> void createNewInstance(SkipList<T> list) {
        if(currentInstance != null) {
            currentInstance.delete();
        }
        currentInstance = new Visualizer<T>(currentInstance, list);
    }

    private List<RenderElement> createElements(FontRenderContext fontRenderContext) {
        final List<Node<T>> allNodes = collectAllNodes();
        final int maxLength = allNodes.stream().map(node -> String.valueOf(node.item.node.key.value)).mapToInt(String::length).max().orElse(0);
        final Rectangle2D maxCharBounds = FONT.getMaxCharBounds(fontRenderContext);

        final double textHeight = Math.max(maxCharBounds.getHeight(), DOT_SIZE) + EXTRA_PADDING * 2;
        final double textWidth = maxCharBounds.getWidth() * maxLength + EXTRA_PADDING * 2;
        final double connectorWidth = textHeight;

        final double nodeWidth = textWidth + connectorWidth;
        final double nodeHeight = textHeight;

        final List<RenderElement> elements = new ArrayList<>();
        final List<Node<T>> mainNodes = allNodes.stream().filter(n -> n.isMain).toList();

        for(Node<T> allNode : allNodes) {
            if(allNode.isMain) {
                elements.add(new NodeRenderElement(allNode.item, allNode.item.node == highlightedItem, allNode.x * nodeWidth * HORIZONTAL_SPACING, allNode.y * nodeHeight * VERTICAL_SPACING, textWidth, textHeight, connectorWidth));
            }
        }

        // Iteratively find positions of non-main nodes
        findPositions(allNodes, mainNodes);

        for(Node<T> allNode : allNodes) {
            if(!allNode.isMain) {
                final double x;
                final double y;

                if(allNode.verifiedX && allNode.verifiedY) {
                    // Verified position
                    x = allNode.x;
                    y = allNode.y;
                }
                else {
                    // Guessed and interpolated position
                    x = allNode.isOffX? allNode.offX : allNode.x;
                    y = allNode.isOffY? allNode.offY : allNode.y;
                }

                elements.add(new NodeRenderElement(allNode.item, allNode.item.node == highlightedItem, x * nodeWidth * HORIZONTAL_SPACING, y * nodeHeight * VERTICAL_SPACING, textWidth, textHeight, connectorWidth));
            }
        }

        final List<NodeRenderElement> nodeRenderElements = elements.stream().filter(e -> e instanceof Visualizer.NodeRenderElement).map(e -> (NodeRenderElement) e).toList();


        // Create pointers
        for(NodeRenderElement a : nodeRenderElements) {
            for(NodeRenderElement b : nodeRenderElements) {
                if(Objects.equals(a.item.next(),b.item)) {
                    elements.add(createPointer(a, b, PointerDirection.RIGHT, PointerDirection.LEFT));
                }
                if(Objects.equals(a.item.prev(),b.item)) {
                    elements.add(createPointer(a, b, PointerDirection.LEFT, PointerDirection.RIGHT));
                }
                if(Objects.equals(a.item.up(),b.item)) {
                    elements.add(createPointer(a, b, PointerDirection.UP, PointerDirection.DOWN));
                }
                if(Objects.equals(a.item.down(),b.item)) {
                    elements.add(createPointer(a, b, PointerDirection.DOWN, PointerDirection.UP));
                }
            }
        }

        return elements;
    }

    private void draw(Graphics2D g, int width, int height) {
        // Rendering Hints
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g.setFont(FONT);

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, width, height);


        final List<RenderElement> elements = createElements(g.getFontRenderContext());

        // Calculate global bounding box

        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        for(RenderElement element : elements) {
            final Rectangle2D.Double bb = element.getBoundingBox();
            minX = Math.min(minX, bb.getMinX());
            minY = Math.min(minY, bb.getMinY());

            maxX = Math.max(maxX, bb.getMaxX());
            maxY = Math.max(maxY, bb.getMaxY());
        }
        if(elements.isEmpty()) {
            minX = minY = 0;
            maxX = maxY = 1;
        }


        final double areaWidth = maxX - minX;
        final double areaHeight = maxY - minY;
        final double scale = Math.min(Math.min(width / areaWidth, height / areaHeight) * SCALE_FACTOR, MAX_ABSOLUTE_SCALE);
        final double padX = (width - (scale * areaWidth)) / 2d;
        final double padY = (height - (scale * areaHeight)) / 2d;

        g.translate(padX, padY);
        g.scale(scale, scale);
        g.translate(-minX, -minY);

        // Render all elements

        synchronized(hoverMonitor) {
            hoverNodeList.clear();

            // First pass: non-hover
            for(RenderElement element : elements) {
                element.draw(g, false);
            }
            // Second pass: hover only
            for(RenderElement element : elements) {
                element.draw(g, true);
            }
        }

    }

    private RenderElement createPointer(NodeRenderElement a, NodeRenderElement b, PointerDirection aDir, PointerDirection bDir) {
        return new PointerRenderElement(a, b, a.getPointerPosition(aDir, true), b.getPointerPosition(bDir, false), aDir.getOutVector(), bDir.getOutVector());
    }

    private void findPositions(List<Node<T>> allNodes, List<Node<T>> mainNodes) {
        final Map<Item<T>, Node<T>> nodeMap = new HashMap<>();

        for(Node<T> mainNode : mainNodes) {
            nodeMap.put(mainNode.item, mainNode);
        }

        final LinkedList<Node<T>> toDo = new LinkedList<>(allNodes.stream().filter(n -> !n.isMain).toList());

        LocationPriority[] priorityOrder = new LocationPriority[]{LocationPriority.DOUBLE_VERIFIED, LocationPriority.ONE_SIDED_VERIFIED, LocationPriority.DOUBLE_CONNECTION, LocationPriority.WEAK_CONNECTION};

        final boolean[] doHorizontalOrder = new boolean[]{false, true};

        for(boolean doHorizontal : doHorizontalOrder) {

            for(LocationPriority allowedPriority : priorityOrder) {

                while(true) {
                    boolean doneOne = false;

                    final Iterator<Node<T>> iterator = toDo.iterator();
                    while(iterator.hasNext()) {
                        final Node<T> node = iterator.next();
                        final Item<T> next = node.item.next();
                        final Item<T> down = node.item.down();
                        final Item<T> up = node.item.up();
                        final Item<T> prev = node.item.prev();

                        final List<PossibleLocation> possibleLocations = new ArrayList<>(List.of(PossibleLocation.get(node.item, nodeMap.get(down), 0, -1, item -> item.up()), PossibleLocation.get(node.item, nodeMap.get(up), 0, 1, item -> item.down()), doHorizontal? PossibleLocation.get(node.item, nodeMap.get(prev), 1, 0, item -> item.next()) : PossibleLocation.NONE, doHorizontal? PossibleLocation.get(node.item, nodeMap.get(next), -1, 0, item -> item.prev()) : PossibleLocation.NONE));

                        final List<PossibleLocation> xPos = possibleLocations.stream().filter(l -> l != PossibleLocation.NONE).filter(l -> l.xPriority == allowedPriority).toList();
                        final List<PossibleLocation> yPos = possibleLocations.stream().filter(l -> l != PossibleLocation.NONE).filter(l -> l.yPriority == allowedPriority).toList();

                        if(!node.locatedX && !xPos.isEmpty()) {
                            doneOne = true;
                            node.locatedX = true;
                            if(xPos.size() != 1) {
                                node.isOffX = true;
                                node.offX = xPos.stream().mapToDouble(l -> l.x).average().orElseThrow();
                                node.x = (int) Math.round(node.offX);
                            }
                            else {
                                node.x = xPos.get(0).x;
                            }
                        }

                        if(!node.locatedY && !yPos.isEmpty()) {
                            doneOne = true;

                            node.locatedY = true;
                            if(yPos.size() != 1) {
                                node.isOffY = true;
                                node.offY = yPos.stream().mapToDouble(l -> l.y).average().orElseThrow();
                                node.y = (int) Math.round(node.offY);
                            }
                            else {
                                node.y = yPos.get(0).y;
                            }
                        }

                        if(node.locatedX && node.locatedY) {
                            if(allowedPriority == LocationPriority.DOUBLE_VERIFIED) {
                                //Verified node!
                                node.verifiedX = node.verifiedY = true;
                            }
                            // Finished
                            iterator.remove();
                        }
                        if(node.locatedX || node.locatedY) {
                            // Help to locate others
                            nodeMap.put(node.item, node);
                        }
                    }

                    if(!doneOne) {
                        break;
                    }
                }

            }

        }

        // Assert that every node was located
        // Should always be the case
        for(Node<T> allNode : allNodes) {
            final boolean locatedFully = allNode.locatedX && allNode.locatedY;
            if(!locatedFully) {
                throw new IllegalStateException("Critical failure! Not all nodes could be located fully. :(");
            }
        }
    }

    private List<Node<T>> collectAllNodes() {
        final List<Node<T>> nodes = new ArrayList<>();

        if(skipList.head == null) {
            return nodes;
        }

        final Node<T> headNode = new Node<T>(toItem(skipList.head), 0, 0, true);
        collectAll(headNode, nodes, true);
        return nodes;
    }

    private void collectAll(Node<T> pointer, List<Node<T>> nodes, boolean isMain) {
        if(!nodes.contains(pointer)) {
            nodes.add(pointer);

            collectIfPossible(pointer, pointer.item.down(), nodes, 0, 1, isMain);
            collectIfPossible(pointer, pointer.item.next(), nodes, 1, 0, isMain && (pointer.x != 0 || pointer.item.down() == null));

            collectIfPossible(pointer, pointer.item.up(), nodes, 0, -1, false);
            collectIfPossible(pointer, pointer.item.prev(), nodes, -1, 0, false);
        }
    }

    private void collectIfPossible(Node<T> pointer, Item<T> a, List<Node<T>> nodes, int deltaX, int deltaY, boolean isMain) {
        if(a != null) {
            final Node<T> next = new Node<>(a, pointer.x + deltaX, pointer.y + deltaY, isMain);
            collectAll(next, nodes, isMain);
        }
    }

    private enum LocationPriority {
        DOUBLE_VERIFIED, ONE_SIDED_VERIFIED, WEAK_CONNECTION, DOUBLE_CONNECTION, NO_LOCATION
    }

    public enum PointerDirection {
        LEFT(new Point2D.Double(-1, 0)), RIGHT(new Point2D.Double(1, 0)), UP(new Point2D.Double(0, -1)), DOWN(new Point2D.Double(0, 1));

        private final Point2D.Double outVector;

        PointerDirection(Point2D.Double outVector) {

            this.outVector = outVector;
        }

        public Point2D.Double getOutVector() {
            return outVector;
        }
    }

    private static class Node<T> {

        private final Item<T> item;
        private final boolean isMain;
        private int x;
        private int y;
        private boolean locatedX = false;
        private boolean locatedY = false;
        private boolean verifiedX = false;
        private boolean verifiedY = false;

        private double offX, offY;
        private boolean isOffX, isOffY;

        public Node(Item<T> item, int x, int y, boolean isMain) {
            this.item = item;
            this.x = x;
            this.y = y;
            this.isMain = isMain;
            if(isMain) {
                locatedX = locatedY = verifiedX = verifiedY = true;
            }
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }
            if(o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return Objects.equals(item, node.item);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(item);
        }
    }

    public abstract static class RenderElement {

        public abstract Rectangle2D.Double getBoundingBox();

        public abstract void draw(Graphics2D g, boolean hoverOnly);
    }

    private record PossibleLocation(int x, int y, LocationPriority xPriority, LocationPriority yPriority) {

        public static final PossibleLocation NONE = new PossibleLocation(0, 0, null, null);

        public static <T> PossibleLocation get(Item<T> myItem, Node<T> other, int deltaX, int deltaY, Function<Item<T>, Item<T>> otherDirectionMapper) {
            if(other == null) {
                return NONE;
            }
            if(!other.locatedX && !other.locatedY) {
                // No location!
                return NONE;
            }

            final boolean isOtherDirection = otherDirectionMapper.apply(other.item) == myItem;

            return new PossibleLocation(other.x + deltaX, other.y + deltaY, other.locatedX? (other.verifiedX? (isOtherDirection? LocationPriority.DOUBLE_VERIFIED : LocationPriority.ONE_SIDED_VERIFIED) : (isOtherDirection? LocationPriority.DOUBLE_CONNECTION : LocationPriority.WEAK_CONNECTION)) : LocationPriority.NO_LOCATION,

                                        other.locatedY? (other.verifiedY? (isOtherDirection? LocationPriority.DOUBLE_VERIFIED : LocationPriority.ONE_SIDED_VERIFIED) : (isOtherDirection? LocationPriority.DOUBLE_CONNECTION : LocationPriority.WEAK_CONNECTION)) : LocationPriority.NO_LOCATION);
        }
    }

    private class NodeRenderElement extends RenderElement {

        private final Item<T> item;
        private final boolean selected;
        private final double x;
        private final double y;
        private final double textWidth;
        private final double textHeight;
        private final double connectorWidth;

        public NodeRenderElement(Item<T> item, boolean selected, double x, double y, double textWidth, double textHeight, double connectorWidth) {
            this.item = item;
            this.selected = selected;
            this.x = x;
            this.y = y;
            this.textWidth = textWidth;
            this.textHeight = textHeight;
            this.connectorWidth = connectorWidth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeRenderElement that = (NodeRenderElement) o;
            return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Objects.equals(item, that.item);
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, x, y);
        }

        public Rectangle2D.Double getTextBoundingBox() {
            final Rectangle2D.Double bb = getBoundingBox();
            return new Rectangle2D.Double(bb.getX(), bb.getY(), textWidth, textHeight);
        }

        @Override
        public void draw(Graphics2D g, boolean hoverOnly) {
            if(Objects.equals(currentHoverItem,item) != hoverOnly) {
                return;
            }
            final Color prevColor = g.getColor();
            final Stroke prevStroke = g.getStroke();

            final Rectangle2D.Double bb = getBoundingBox();
            final Rectangle2D.Double textBb = getTextBoundingBox();

            g.setColor(selected? NODE_BACKGROUND_SELECTED_COLOR : NODE_BACKGROUND_COLOR);
            g.fill(bb);

            g.setStroke(new BasicStroke(((float) BORDER_THICKNESS)));
            g.setColor(Objects.equals(currentHoverItem,item)? FOREGROUND_HOVERING_COLOR : FOREGROUND_COLOR);
            g.draw(bb);

            g.draw(textBb);
            final Point2D.Double dotPosition = getDotPosition();
            g.fill(new Ellipse2D.Double(dotPosition.x - DOT_SIZE / 2d, dotPosition.y - DOT_SIZE / 2d, DOT_SIZE, DOT_SIZE));


            g.setColor(FOREGROUND_COLOR);


            final Point2D min = g.getTransform().transform(new Point2D.Double(bb.getMinX(), bb.getMinY()), null);
            final Point2D max = g.getTransform().transform(new Point2D.Double(bb.getMaxX(), bb.getMaxY()), null);
            hoverNodeList.add(new AbstractMap.SimpleImmutableEntry<>(new Rectangle2D.Double(min.getX(), min.getY(), max.getX() - min.getX(), max.getY() - min.getY()), item));


            final String stringRepresentation = String.valueOf(item.value());
            final Rectangle2D stringBounds = g.getFont().getStringBounds(stringRepresentation, g.getFontRenderContext());
            g.translate(-stringBounds.getX(), -stringBounds.getY());
            g.translate(textBb.getCenterX() - stringBounds.getWidth() / 2.0, textBb.getCenterY() - stringBounds.getHeight() / 2.0);
            g.drawString(stringRepresentation, 0, 0);
            g.translate(-textBb.getCenterX() + stringBounds.getWidth() / 2.0, -textBb.getCenterY() + stringBounds.getHeight() / 2.0);
            g.translate(stringBounds.getX(), stringBounds.getY());


            g.setStroke(prevStroke);
            g.setColor(prevColor);
        }

        public Point2D.Double getPointerPosition(PointerDirection dir, boolean out) {
            final Rectangle2D.Double bb = getBoundingBox();
            return switch(dir) {
                case LEFT -> new Point2D.Double(bb.getMinX(), bb.getCenterY() + (out? 1 : -1) * bb.getHeight() / 4d);
                case RIGHT -> new Point2D.Double(bb.getMaxX(), bb.getCenterY() + (out? -1 : 1) * bb.getHeight() / 4d);
                case UP -> new Point2D.Double(getDotPosition().x + (out? 1 : -1) * connectorWidth / 4d, bb.getMinY());
                case DOWN -> new Point2D.Double(getDotPosition().x + (out? -1 : 1) * connectorWidth / 4d, bb.getMaxY());
            };
        }

        @Override
        public Rectangle2D.Double getBoundingBox() {
            final double width = getWidth();
            final double height = getHeight();
            return new Rectangle2D.Double(x - width / 2d, y - height / 2d, width, height);
        }

        public Point2D.Double getDotPosition() {
            final Rectangle2D.Double bb = getBoundingBox();
            return new Point2D.Double(bb.getX() + textWidth + (connectorWidth / 2.0), bb.getCenterY());
        }

        public double getWidth() {
            return textWidth + connectorWidth;
        }

        public double getHeight() {
            return textHeight;
        }
    }

    private class Painter extends JComponent {

        public Painter() {

        }

        public void setup() {
            setFocusable(true);
            requestFocusInWindow();
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {

                    synchronized(hoverMonitor) {
                        Item<T> prevHover = Visualizer.this.currentHoverItem;
                        Visualizer.this.currentHoverItem = null;
                        for(Map.Entry<Rectangle2D.Double, Item<T>> entry : hoverNodeList) {
                            if(entry.getKey().contains(e.getPoint())) {
                                Visualizer.this.currentHoverItem = entry.getValue();
                            }
                        }

                        if(currentHoverItem != prevHover) {
                            repaint();
                        }
                    }
                }
            });
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                        Thread t = Visualizer.this.waitThread;
                        if(t != null) {
                            t.interrupt();
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g2) {
            super.paintComponent(g2);
            Graphics2D g = (Graphics2D) g2;

            draw(g, getWidth(), getHeight());
        }
    }

    private class PointerRenderElement extends RenderElement {

        private final NodeRenderElement aNode;
        private final NodeRenderElement bNode;
        private final Path2D.Double pointerShape;
        private final Rectangle2D.Double bounds;
        private final Path2D.Double arrowHeadShape;

        public PointerRenderElement(NodeRenderElement aNode, NodeRenderElement bNode, Point2D.Double a, Point2D.Double b, Point2D.Double aVec, Point2D.Double bVec) {
            super();
            this.aNode = aNode;
            this.bNode = bNode;

            final double distanceFactor;
            final Point2D.Double bezierAVec;
            final Point2D.Double bezierBVec;

            if(aVec.x == -bVec.x && aVec.y == -bVec.y) {
                Point2D.Double moveDir = new Point2D.Double(b.x - a.x, b.y- a.y);
                final double dotProduct = moveDir.x * aVec.x + moveDir.y * aVec.y;
                if(dotProduct < 0) {
                    // Would otherwise be a straight line!
                    bezierAVec = new Point2D.Double(aVec.x + aVec.y, aVec.y - aVec.x);
                    bezierBVec = new Point2D.Double(-bezierAVec.x, bezierAVec.y);
                    distanceFactor = Math.max((BEZIER_LOOP_DISTANCE_FACTOR * moveDir.distance(0,0)) / BEZIER_POINT_DISTANCE, BEZIER_MIN_LOOP_DISTANCE);
                }
                else {
                    bezierAVec = aVec;
                    bezierBVec = bVec;
                    distanceFactor = 1;
                }

            }
            else {
                bezierAVec = aVec;
                bezierBVec = bVec;
                distanceFactor = 1;
            }

            final Point2D.Double aBezier = new Point2D.Double(a.x + BEZIER_POINT_DISTANCE * bezierAVec.x * distanceFactor, a.y + BEZIER_POINT_DISTANCE * bezierAVec.y * distanceFactor);
            final Point2D.Double bBezier = new Point2D.Double(b.x + BEZIER_POINT_DISTANCE * bezierBVec.x * distanceFactor, b.y + BEZIER_POINT_DISTANCE * bezierBVec.y * distanceFactor);

            pointerShape = new Path2D.Double();
            pointerShape.moveTo(a.x + aVec.x*ARROW_LINE_THICKNESS/2, a.y + aVec.y*ARROW_LINE_THICKNESS/2);
            pointerShape.curveTo(aBezier.x, aBezier.y, bBezier.x, bBezier.y,
                                 b.x + bVec.x*(ARROW_HEAD_LENGTH), b.y + bVec.y*(ARROW_HEAD_LENGTH));

            double vLength = bVec.distance(0, 0);
            Point2D.Double varB = new Point2D.Double(-bVec.y / vLength, bVec.x / vLength);
            Point2D.Double normB = new Point2D.Double(bVec.x / vLength, bVec.y / vLength);

            final double headLength = ARROW_HEAD_LENGTH;
            final double headThickness = ARROW_HEAD_THICKNESS;


            arrowHeadShape = new Path2D.Double();
            arrowHeadShape.moveTo(b.x, b.y);
            arrowHeadShape.lineTo(b.x + headLength * normB.x + varB.x * headThickness, b.y + headLength * normB.y + varB.y * headThickness);
            arrowHeadShape.lineTo(b.x + headLength * normB.x - varB.x * headThickness, b.y + headLength * normB.y - varB.y * headThickness);
            arrowHeadShape.closePath();

            final Rectangle2D pointerBounds = pointerShape.getBounds2D();
            bounds = new Rectangle2D.Double(pointerBounds.getX(), pointerBounds.getY(), pointerBounds.getWidth(), pointerBounds.getHeight());
        }

        @Override
        public Rectangle2D.Double getBoundingBox() {
            return bounds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointerRenderElement that = (PointerRenderElement) o;
            return Objects.equals(aNode, that.aNode) && Objects.equals(bNode, that.bNode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(aNode, bNode);
        }

        @Override
        public void draw(Graphics2D g, boolean hoverOnly) {
            boolean hovering = aNode.item.equals(currentHoverItem);
            if(hovering != hoverOnly) {
                return;
            }
            final Color prevColor = g.getColor();
            final Stroke prevStroke = g.getStroke();

            g.setStroke(new BasicStroke((float) ARROW_LINE_THICKNESS));

            g.setColor(hovering? FOREGROUND_HOVERING_COLOR : FOREGROUND_COLOR);
            g.draw(pointerShape);

            g.fill(arrowHeadShape);

            g.setStroke(prevStroke);
            g.setColor(prevColor);
        }
    }

    private static class Item<T> {
        private final ListItem<ExpressNode<T>> node;

        private Item<T> next() {
            return toItem(node.next);
        }
        private Item<T> prev() {
            return toItem(node.key.prev);
        }
        private Item<T> up() {
            return toItem(node.key.up);
        }
        private Item<T> down() {
            return toItem(node.key.down);
        }

        public Item(ListItem<ExpressNode<T>> node) {
            this.node = node;
            if(node.key == null) {
                throw new RuntimeException("ListItem.key was null! This should never be the case!");
            }
        }

        public boolean internalEquals(Item<T> other) {
            return node == other.node;
        }

        public T value() {
            return node.key.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item<?> item = (Item<?>) o;
            return internalEquals((Item<T>) item);
        }

        private static <T> int myHashCode(Item<T> head, List<Item<T>> used) {
            return valueHash(head);
        }

        private static <T> int valueHash(Item<T> head) {
            return head == null? 0 : Objects.hashCode(head.value());
        }

        @Override
        public int hashCode() {
            return myHashCode(this, new ArrayList<>());
        }
    }

    private static <T> Item<T> toItem(ListItem<ExpressNode<T>> node) {
        if(node == null) {
            return null;
        }
        return new Item<>(node);
    }
}
