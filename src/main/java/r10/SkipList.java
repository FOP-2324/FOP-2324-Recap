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
        return crash();
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param key the element whose presence in this list is to be tested
     *
     * @return {@code true} if this list contains the specified element
     */
    public boolean contains(T key) {
        return crash(); // TODO: H1 - remove if implemented
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
        crash(); // TODO: H2 - remove if implemented
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present. The element will be
     * removed from all levels.
     *
     * @param key the element to be removed from this list, if present
     */
    public void remove(T key) {
        crash(); // TODO: H3 - remove if implemented
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
