/**
 *
 * @author yong0312
 */
package adt;

import java.io.Serializable;
import java.util.Iterator;

public class SortedArrayList<T extends Comparable<T>> implements SortedListInterface<T>, Serializable {

    private T[] array;
    private int length;
    private static final int DEFAULT_CAPACITY = 25;

    public SortedArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public SortedArrayList(int initialCapacity) {
        length = 0;
        array = (T[]) new Comparable[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {
        int i = 0;
        while (i < length && newEntry.compareTo(array[i]) > 0) {
            i++;
        }
        makeRoom(i + 1);
        array[i] = newEntry;
        length++;
        return true;
    }

    @Override
    public boolean remove(T anEntry) {
        int i = 0;
        if (!isEmpty() && contains(anEntry)) {

            //remove reverse order sorted list
            while (i < length && anEntry.compareTo(array[i]) > 0) {
                i++;
            }
            removeGap(i);
            length--;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        length = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < length); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            result = array[givenPosition - 1];
        }
        return result;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < length; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    private boolean isArrayFull() {
        return length == array.length;
    }

    private void doubleArray() {
        T[] oldList = array;
        int oldSize = oldList.length;

        array = (T[]) new Object[2 * oldSize];

        for (int index = 0; index < oldSize; index++) {
            array[index] = oldList[index];
        }
    }

    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = length - 1;

        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    private void removeGap(int givenPosition) {
        int removedIndex = givenPosition - 1;
        int lastIndex = length - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    @Override
    public Iterator<T> getIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class SortedArrayIterator implements Iterator<T> {

        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < length;
        }

        @Override
        public T next() {
            T currentElement = null;
            if (hasNext()) {
                currentElement = array[currentIndex];
                currentIndex++;
            }
            return currentElement;
        }

    }
}
