package r10;

import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Objects;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Represents a skip list. A skip list is a randomized data structure that allows fast access to elements in a sorted
 * list.
 *
 * <p>Its a two-dimensional linked list where the lowest level contains all elements and each higher level contains a
 * subset of the elements of the lower level.
 *
 * <p>Example:
 * <pre>{@code
 *  head ------------ 47
 *    |                          |
 *    |----- 12 ------- 47
 *    |         |                |
 *    |----- 12 ------- 47 -- 72
 *    |         |                |        |
 *    | 5 -- 12 -- 17 -- 47 -- 72 -- 98
 * }</pre>
 *
 * @param <T> the type of the elements in this list
 *
 * @author Nhan Huynh
 * @see <a href="https://en.wikipedia.org/wiki/Skip_list">Skip list</a>
 */
public class SkipList<T> {

    /**
     * The default probability to not add elements on higher levels.
     */
    private static final Probability DEFAULT_PROBABILITY = new Probability() {
        @Override
        public boolean nextBoolean() {
            return false;
        }

        @Override
        public String toString() {
            return "0%";
        }
    };

    /**
     * The comparator used to maintain order in this list.
     */
    protected final Comparator<? super T> cmp;

    /**
     * The maximum height of the skip list.
     */
    final int maxHeight;

    /**
     * The probability function used to determine if a node should be added to another level.
     */
    private Probability probability;

    /**
     * The head of the skip list.
     */
    @Nullable ListItem<ExpressNode<T>> head;

    /**
     * The current height of the skip list.
     */
    int height = 0;

    /**
     * The number of items in the skip list.
     */
    int size = 0;

    /**
     * Constructs and initializes an empty skip list without the probability to add elements on higher levels.
     *
     * @param cmp            the comparator used to maintain order in this list
     * @param maxHeight the maximum height of the skip list
     */
    public SkipList(Comparator<? super T> cmp, int maxHeight) {
        this(cmp, maxHeight, DEFAULT_PROBABILITY);
    }

    /**
     * Constructs and initializes an empty skip list.
     *
     * @param cmp           the comparator used to maintain order in this list
     * @param maxHeight the maximum height of the skip list
     * @param probability the probability function used to determine if a node should be added on another level
     */
    public SkipList(Comparator<? super T> cmp, int maxHeight, Probability probability) {
        this.cmp = cmp;
        this.maxHeight = maxHeight;
        this.probability = probability;
    }

    /**
     * Returns the current height of this skip list.
     *
     * @return the current height of this skip list
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the probability function used to determine if a node should be added on another level.
     *
     * @return the probability function used to determine if a node should be added on another level
     */
    public Probability getProbability() {
        return probability;
    }

    /**
     * Sets te probability function used to determine if a node should be added on another level.
     *
     * @param probability the probability function
     */
    public void setProbability(Probability probability) {
        this.probability = probability;
    }

    /**
     * Returns the number of items in this skip list.
     *
     * @return the number of items in this skip list
     */
    public int size() {
        return size;
    }

    /**
     * Returns the first occurrence of the specified element in this list, or {@code null} if this list does not
     * contain the element. The first element is on the lowest level.
     *
     * @param key the element to search for
     *
     * @return the first occurrence of the specified element in this list, or {@code null} if this list does not
     * contain the element
     */
    private ListItem<ExpressNode<T>> get(T key) {
        if (isEmpty()) {
            return null;
        }
        ListItem<ExpressNode<T>> current = head;
        while(current != null) {
            if(current.next == null) {
                current = current.key.down;
                continue;
            }
            ListItem<ExpressNode<T>> successor = current.next;
            int value = cmp.compare(successor.key.value, key);
            if(value == 0) {
                return successor;
            } else if(value < 0) {
                current = current.next;
            } else {
                current = current.key.down;
            }
        }
        return null;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param key the element whose presence in this list is to be tested
     *
     * @return {@code true} if this list contains the specified element
     */
    public boolean contains(T key) {
        return get(key) != null;
    }

    /**
     * Returns all possible insertion points for the specified element in this list. The order is from lowest level
     * to the highest level.
     *
     * @param key the element to insert
     *
     * @return all possible insertion points for the specified element in this list
     */
    private ListItem<ListItem<ExpressNode<T>>> getInsertionPositions(T key) {
        ListItem<ExpressNode<T>> current = head;
        // Store the potential previous node for each level where an insertion
        ListItem<ListItem<ExpressNode<T>>> positions = null;
        // Find insertion position on all levels
        while (current != null) {
            // If we are on the last node of the current level, go down
            if (current.next == null) {
                ListItem<ListItem<ExpressNode<T>>> node = new ListItem<>();
                node.key = current;
                // The lowest level will be the first element in the list
                // Insertion at the beginning of the list
                node.next = positions;
                positions = node;
                current = current.key.down;
                continue;
            }
            ListItem<ExpressNode<T>> successor = current.next;
            int value = cmp.compare(successor.key.value, key);
            if (value <= 0) {
                // Key can be on the same level
                current = current.next;
            } else {
                // God down, remember potential insertion position
                ListItem<ListItem<ExpressNode<T>>> node = new ListItem<>();
                node.key = current;
                // The lowest level will be the first element in the list
                // Insertion at the beginning of the list
                node.next = positions;
                positions = node;
                current = current.key.down;
            }
        }
        return positions;
    }

    /**
     * Adds the specified element to this list. The element will be added on the highest floor of the skip list and on
     * the next levels if the probability function returns {@code true}.
     *
     * @param key the element to be added
     */
    public void add(T key) {
        ListItem<ListItem<ExpressNode<T>>> positions = getInsertionPositions(key);
        // Potential insertions on each level
        int currentHeight = 1;
        ListItem<ExpressNode<T>> lowerLevelNode = null;
        do {
            if (head == null) {
                // Empty list, create the first level and sentinel node
                head = new ListItem<>();
                head.key = new ExpressNode<>();

                // Insertion position on the first level
                ListItem<ListItem<ExpressNode<T>>> node = new ListItem<>();
                node.key = head;
                positions = node;
                height++;
            } else if (currentHeight > height) {
                // Create new level if it does not exist (new upper level)
                ListItem<ExpressNode<T>> newHead = new ListItem<>();
                newHead.key = new ExpressNode<>();
                newHead.key.down = head;
                head.key.up = newHead;
                head = newHead;

                ListItem<ListItem<ExpressNode<T>>> node = new ListItem<>();
                node.key = head;
                positions = node;
                height++;
            }
            ListItem<ExpressNode<T>> node = new ListItem<>();
            node.key = new ExpressNode<>();
            node.key.value = key;

            // Connect lower and upper levels
            if (lowerLevelNode != null) {
                node.key.down = lowerLevelNode;
                lowerLevelNode.key.up = node;
            }

            assert positions != null;
            ListItem<ExpressNode<T>> current = positions.key;
            if (current.next != null) {
                // Last node does not have a next node, so we do not need to adjust the references
                current.next.key.prev = node;
                node.next = current.next;
            }

            node.key.prev = current;
            current.next = node;

            positions = positions.next;
            currentHeight++;
            lowerLevelNode = node;
        } while ((positions != null || currentHeight <= maxHeight) && probability.nextBoolean());
        size++;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present. The element will be
     * removed from all levels.
     *
     * @param key the element to be removed from this list, if present
     */
    public void remove(T key) {
        // Get the first occurrence of the element
        ListItem<ExpressNode<T>> current = get(key);

        // If the element is not null, that means it is present in the list
        if (current != null) {
            size--;
        }

        // Removal of element on all levels
        while (current != null) {
            ListItem<ExpressNode<T>> lowerLevel = current.key.down;
            // Cannot be null since get returns non-null values if the element is found
            // We checked that walker != null
            assert current.key.prev != null;
            if (current.key.prev.key.value == null && current.next == null) {
                // Single element list
                if (current.key.up == null) {
                    // Head should be deleted
                    // Since walker is non-null, the list is not empty
                    assert head != null;
                    head = head.key.down;
                    if (lowerLevel != null) {
                        lowerLevel.key.up = null;
                    }
                } else {
                    // Adjust reference from up and down levels
                    assert current.key.prev.key.up != null;
                    current.key.prev.key.up.key.down = current.key.prev.key.down;
                    // Since walker is non-null, the list is not empty
                    assert current.key.prev.key.down != null;
                    current.key.prev.key.down.key.up = current.key.prev.key.up;
                }
                height--;
            } else {
                // Adjust reference from prev and next nodes
                current.key.prev.next = current.next;
                if (current.next != null) {
                    current.next.key.prev = current.key.prev;
                }
            }
            current = lowerLevel;
        }
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        // Check instance of to avoid ClassCastException
        if (!(o instanceof SkipList<?> other)) {
            return false;
        }
        return height == other.height
                && size == other.size
                && Objects.equals(head, other.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxHeight, head, height, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (ListItem<ExpressNode<T>> current = head; current != null; current = current.key.down) {
            sb.append("[");
            for (ListItem<ExpressNode<T>> element = current.next; element != null; element = element.next) {
                sb.append(element.key.value);
                if (element.next != null) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (current.key.down != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
