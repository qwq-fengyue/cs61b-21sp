package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private static final int DEFAULT_INITIAL_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final int initialSize;
    private final double LoadFactor;
    private int size;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = 0;
        this.initialSize = initialSize;
        LoadFactor = maxLoad;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] buckets = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            buckets[i] = createBucket();
        }
        return buckets;
    }

    @Override
    public void clear() {
        buckets = createTable(initialSize);
        size = 0;
    }

    private int getIndex(K key) {
        return getIndex(key, buckets.length);
    }

    private int getIndex(K key, int bucketsNum) {
        return Math.floorMod(key.hashCode(), bucketsNum);
    }

    private Node getNode(K key) {
        int index = getIndex(key);
        return getNode(key, index);
    }

    private Node getNode(K key, int index) {
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;

    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public int size() {
        return size;
    }

    private boolean isOverLoad() {
        return (double) size / buckets.length >= LoadFactor;
    }

    private void resize(int bucketsNum) {
        Collection<Node>[] newBuckets = createTable(bucketsNum);
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                newBuckets[getIndex(node.key, bucketsNum)].add(node);
            }
        }
        buckets = newBuckets;
    }

    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        Node node = getNode(key, index);

        if (node != null) {
            node.value = value;
            return;
        }

        node = createNode(key, value);
        buckets[index].add(node);
        size++;

        if (isOverLoad()) {
            resize(buckets.length * 2);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> hashset = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                hashset.add(node.key);
            }
        }
        return hashset;
    }

    @Override
    public V remove(K key) {
        return remove(key, null);
    }

    @Override
    public V remove(K key, V value) {
        int index = getIndex(key);
        Node node = getNode(key, index);

        if (node == null) {
            return null;
        }

        if (value != null && value != node.value) {
            return null;
        }

        V result = node.value;
        buckets[index].remove(node);
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
