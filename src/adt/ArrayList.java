
package adt;

import java.io.Serializable;

/**
 *
 * @author KXian
 */
public class ArrayList<T> implements ListInterface<T>, Serializable {
    
    private T[] array;
    private int length;
    private static final int DEFAULT_CAPACITY = 5;
    
    // Default Constructor
    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }
    
    // Constructor with intial capacity params
    public ArrayList(int initialCapacity){
        length = 0;
        array = (T[]) new Object[initialCapacity];
    }
    
    // Add new entry
    @Override
    public boolean add(T newEntry) {
        if(isArrayFull()){
            doubleArraySize();
        }
        array[length] = newEntry;
        length ++;
        return true;
    }

    // Add new entry into specific position
    @Override
    public boolean add(int newPosition, T newEntry) {
        if ((newPosition >= 1) && (newPosition <= length + 1)) {
            if (isArrayFull()) {
              doubleArraySize();
            }
            makeRoom(newPosition);
            array[newPosition - 1] = newEntry;
            length++;
            return true;
        }
        return false;
    }

    // Remove entry with given position
    @Override
    public T remove(int givenPosition) {
        T result = null;
        if((givenPosition >= 1) && (givenPosition <= length)){
            result = array[givenPosition - 1];
            
            if(givenPosition < length){
                removeGap(givenPosition);
            }
            
            length--;
        }
        return result;
    }

    // Clear the array
    @Override
    public void clear() {
        length = 0;
    }

    // Replace an entry with a position and new entry
    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if((givenPosition >= 1) && (givenPosition <= length)){
            array[givenPosition - 1] = newEntry;
            return true;
        }
        return false;
    }

    // Retrieve the entry with a given position
    @Override
    public T getEntry(int givenPosition) {
        T result = null;
        
        if((givenPosition >= 1) && (givenPosition <= length)){
            result = array[givenPosition - 1];
        }
        return result;
    }

    // Checks if the arraylist contains an entry
    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int i = 0; !found && (i < length); i++){
            if(anEntry.equals(array[i])){
                found = true;
            }
        }
        return found;
    }

    // Get Length 
    @Override
    public int getLength() {
        return length;
    }

    // Check if the arraylist is empty
    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    // Check if the arraylist is full
    @Override
    public boolean isArrayFull() {
        return length == array.length;
    }
    
    // Find the first position of an entry - returns 0 if not found 
    @Override
    public int locate(T anEntry){
        boolean found = false;
        int result = 0;
        for(int i = 0; !found && (i < length); i++){
            if(anEntry.equals(array[i])){
                found = true;
                result = i;
            }
        }
        return result;
    }
    
    public void makeRoom(int newPosition){
        int newIndex = newPosition + 1;
        int lastIndex = length - 1;
        
        for (int i = lastIndex; i >= newIndex; i--){
            array[i + 1] = array[i];
        }
    }
    
    public void removeGap(int givenPosition){
        int removedIndex = givenPosition - 1;
        int lastIndex = length - 1;
        
        for(int i = removedIndex; i < lastIndex; i++){
            array[i] = array[i + 1];
        }
    }
    
    public void doubleArraySize(){
        T[] oldArray = array;
        int oldSize = length;
        
        array = (T[]) new Object[oldSize * 2];
        
        for(int i = 0; i < oldSize; i ++){
            array[i] = oldArray[i];
        }
    }
}
