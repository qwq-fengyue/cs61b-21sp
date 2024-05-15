package deque;

import javax.print.attribute.standard.JobKOctets;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    private int getArrayIndex(int index) {
        return (index + items.length) % items.length;
    }

    private void resize(int length) {
        T[] newArray = (T []) new Object[length];
        int oldIndex = nextLast;
        for (int i = 0; i < size; i++) {
            newArray[i] = items[oldIndex];
            oldIndex = getArrayIndex(oldIndex + 1);
        }
        items = newArray;
        nextFirst = length - 1;
        nextLast = size;
    }

    private void checkExpand() {
        if (size == items.length) {
            resize(size * 2);
        }
    }

    private  void checkShrink() {
        if (size < items.length / 4 && items.length > 8) {
            resize(items.length / 2);
        }
    }
    public void addFirst(T item) {
        checkExpand();
        items[nextFirst] = item;
        nextFirst = getArrayIndex(nextFirst - 1);
        size++;
    }

    public void addLast(T item) {
        checkExpand();
        items[nextLast] = item;
        nextLast = getArrayIndex(nextLast + 1);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int index = getArrayIndex(nextFirst + 1);
        for (int i = 0; i < size - 1; i++) {
            System.out.print(items[index] + " ");
            index = getArrayIndex(index + 1);
        }
        System.out.println(items[index]);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        checkShrink();
        nextFirst = getArrayIndex(nextFirst + 1);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        checkShrink();
        nextLast = getArrayIndex(nextLast - 1);
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        return item;
    }

    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        return items[getArrayIndex(nextFirst + 1 + index)];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ArrayDeque)) {
            return false;
        }

        ArrayDeque<?> lld = (ArrayDeque<?>) o;
        if (this.size != lld.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (get(i) != lld.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 32; i++) {
            ad.addLast(i);
        }
        ad.printDeque();
    }
}
