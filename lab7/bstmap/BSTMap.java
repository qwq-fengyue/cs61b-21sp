package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    public BSTMap() {
        root = null;
        size = 0;
    }

    private BSTNode root;
    private int size;

    private class BSTNode<K, V> {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return getBSTNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        BSTNode<K, V> node = getBSTNode(root, key);
        return node == null ? null : node.value;
    }

    private BSTNode<K, V> getBSTNode(BSTNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getBSTNode(node.left, key);
        } else if (cmp > 0) {
            return getBSTNode(node.right, key);
        } else {
            return node;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = insertBSTNode(root, key, value);
    }

    private BSTNode insertBSTNode(BSTNode<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertBSTNode(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insertBSTNode(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }

//    @Override
//    public Set<K> keySet() {
//        HashSet<K> set = new HashSet<>();
//        addKeyToSet(root, set);
//        return set;
//    }

//    private void addKeyToSet(BSTNode<K, V> node, Set<K> set) {
//        if (node == null) {
//            return;
//        }
//
//        set.add(node.key);
//        addKeyToSet(node.left, set);
//        addKeyToSet(node.right, set);
//    }

    @Override
    public V remove(K key) {
        return remove(key, null);
    }

    @Override
    public V remove(K key, V value) {
        BSTNode<K, V> node = getBSTNode(root, key);

        if (node == null || (value != null && !node.value.equals(value))) {
            return null;
        }

        V result = node.value;
        root = deleteBSTNode(root, key);
        return result;
    }

    private BSTNode deleteBSTNode(BSTNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = deleteBSTNode(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteBSTNode(node.right, key);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            } else {
                BSTNode<K, V> minNode = findMin(node.right);
                node.key = minNode.key;
                node.value = minNode.value;
                deleteBSTNode(node.right, node.key);
            }
        }

        return node;
    }

    private BSTNode findMin(BSTNode node) {
        if (node == null) {
            return null;
        }

        BSTNode cur = node;
        while (cur.left != null) {
            cur = cur.left;
        }

        return cur;
    }

    private void inOrderTraversal(BSTNode<K, V> node, BiConsumer<K, V> f) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.left, f);
        f.accept(node.key, node.value);
        inOrderTraversal(node.right, f);
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<>();
        inOrderTraversal(root, (key, value) -> set.add(key));
        return set;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public void printInOrder() {
        inOrderTraversal(root, (key, value) -> System.out.println(key + ": " + value));
    }

}
