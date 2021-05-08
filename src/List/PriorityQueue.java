package List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueue<E>{
    private E[] PQ;
    private int size;
    private int indexForIt;
    private final int INITIAL_SIZE = 30;

    public PriorityQueue(){
        this.PQ = (E[]) new Comparable[INITIAL_SIZE];
        this.PQ[0] = null;
        // Make index 0 element null for convenient index calculation.
        // Then, in heap tree, index / 2 = parent index.
        this.size = 0;
        this.indexForIt = 0; // for iterator
    }

    public Iterator<E> iterator() {
        indexForIt = 1;
        Iterator<E> it = new Iterator<E>() {
            @Override
            public boolean hasNext() {
                if(indexForIt <= size){
                    return true;
                }
                else{
                    return false;
                }
            }
            @Override
            public E next() {
                if(indexForIt <= size){
                    return PQ[indexForIt++];
                }
                else{
                    throw new NoSuchElementException();
                }
            }
        };
        return it;
    }

    public int size() {
        return size;
    }

    // Inserts the specified element into this PQ.
    public boolean offer(E e) {

        if(e == null) {
            throw new NullPointerException("It's null");
        } // check null.

        try {
            ((Comparable)e).compareTo(e);
        }
        catch(Exception ex){
            throw new ClassCastException("This object is not comparble");
        } // check if element for PQ is comparable.

        if(PQ.length == size + 1) {
            E[] newPQ = (E[]) new Comparable[PQ.length * 2];
            newPQ[0] = null;
            for(int i = 1; i <= size; i++) {
                newPQ[i] = PQ[i];
            }
            PQ = newPQ;
        } // If the array is full, make a new array with larger capacity.

        int lastIndex = ++size;
        // Thd last index and size update.

        PQ[lastIndex] = e;
        while(lastIndex / 2 > 0) {
            if(((Comparable) PQ[lastIndex]).compareTo(PQ[lastIndex / 2]) < 0) {
                E temp = PQ[lastIndex];
                PQ[lastIndex] = PQ[lastIndex/ 2];
                PQ[lastIndex / 2] = temp;
            } // Change heap element until parent's element is not larger than element e.

            else {
                break;
            }
            lastIndex /= 2;
        }

        return true;
    }

    public E remove(){
        if(size == 0) {
            throw new NoSuchElementException("It's empty");
        } // If PQ is empty, throw exception.

        E result = PQ[1];
        PQ[1] = PQ[size];
        size--;
        // change top element to the last element and update size. (size = last index)

        int parent = 1;
        int left = parent * 2;
        int right = parent * 2 + 1;
        // index that starts from the top.

        while(parent <= size) {
            if(right <= size) {
                if(((Comparable)PQ[right]).compareTo(PQ[left]) >= 0) {
                    if(((Comparable) PQ[parent]).compareTo(PQ[left]) > 0) {
                        E temp = PQ[parent];
                        PQ[parent] = PQ[left];
                        PQ[left] = temp;
                        parent = left;
                        left = parent * 2;
                        right = parent * 2 + 1;
                    }
                    else {
                        break;
                    }
                }

                else {
                    if (((Comparable) PQ[parent]).compareTo(PQ[right]) > 0) {
                        E temp = PQ[parent];
                        PQ[parent] = PQ[right];
                        PQ[right] = temp;
                        parent = right;
                        left = parent * 2;
                        right = parent * 2 + 1;
                    } else {
                        break;
                    }
                }

            } // right node exists

            else if(left <= size) {
                if(((Comparable) PQ[parent]).compareTo(PQ[left]) > 0) {
                    E temp = PQ[parent];
                    PQ[parent] = PQ[left];
                    PQ[left] = temp;
                    break;
                }
                else {
                    break;
                }
            } // right node does not exist and the left node exists.

            // Compare to smaller element among left and right elements
            // and switch elements if the parent element is larger.

            else{
                break;
            } // right node and left node does not exist.
        }

        return result;
    }

    public E poll() {
        if(size == 0) {
            return null;
        }
        else {
            return remove();
        }
    }

    public E peek() {
        if(size == 0) {
            return null;
        }
        else {
            return PQ[1];
        }
    }

    public String toString(){
        if(size == 0) {
            return "[null]";
        }
        else {
            StringBuilder result = new StringBuilder("[null, ");
            for(int i = 1; i <= size; i++) {
                if(i == size) {
                    result.append(PQ[i].toString() + "]");
                }
                else {
                    result.append(PQ[i].toString() +", ");
                }
            }
            return result.toString();
        }
    } // format: ex) [null, 1, 3, 5, 6, 10]

    public String toTreeShape() {
        if(size == 0) {
            return "";
        }
        else {

            int height = (int) (Math.log10(size) / Math.log10(2)) + 1;

            if(height == 1) {
                String result = String.format("%4s", PQ[1]);
                return result;
            }

            else {

                int midSpace = 2;
                for(int i = height; i > 0; i--) {
                    midSpace *= 2;
                }

                int frontSpace = 4;
                int add = 2;
                for(int i = height - 1; i > 0; i--) {
                    frontSpace += add;
                    add *= 2;
                }

                StringBuilder result = new StringBuilder("");


                int i = 1;
                int tempHeight = height;

                while(i <= size) {
                    int start = i;
                    int last = start * 2 - 1;
                    if(last > size) {
                        last = size;
                    }
                    int numOfelement = last - start + 1;

                    if(numOfelement == 1) {
                        String first = String.format("%" + frontSpace + "s", PQ[i]);
                        result.append(first + "\n");
                        numOfelement--;
                        i++;
                        midSpace /= 2;
                        add /= 2;
                        frontSpace -= add;
                        tempHeight--;
                    }
                    else {

                        if(tempHeight == 1) {
                            frontSpace = 4;
                        }

                        String first = String.format("%" + frontSpace + "s", PQ[i]);
                        result.append(first);
                        i++;
                        numOfelement--;

                        while(numOfelement > 0) {
                            String mid = String.format("%" + midSpace + "s", PQ[i]);
                            result.append(mid);
                            i++;
                            numOfelement--;
                        }
                        result.append("\n");
                        midSpace /= 2;
                        add /= 2;
                        frontSpace -= add;
                        tempHeight--;
                    }
                }
                return result.toString();
            }
        }
    }
}
