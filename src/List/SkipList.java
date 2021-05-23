package List;

import java.util.Random;

public class SkipList<E> {
    private int size;
    private int height;
    private Random r = new Random();
    private final double p = 0.5;
    private final int MAX_LEVEL = 40;
    private Node<E> head;

    public SkipList(){
        head = new Node<E>(null);
        size = 0;
        height = 1;
    }

    public int size(){
        return size;
    }

    public boolean add(E e){
        int lvl = randomLevel();

        if(lvl > MAX_LEVEL) {
            lvl = MAX_LEVEL;
        }

        int lastIndex;
        if(lvl > height) {
            lastIndex = lvl - 1;
        }
        else {
            lastIndex = height -1;
        }

        Node<E> newOne = new Node(e);
        Node<E> pointer = head;

        if(size == 0) {
            while(lastIndex >= 0) {
                pointer.next[lastIndex] = newOne;
                lastIndex--;
            }
            size++;
            return true;
        }

        int i = size;
        while(lastIndex >= 0) {
            if(lastIndex >= height) {
                pointer.width[lastIndex] = size + 1;
                pointer.next[lastIndex] = newOne;
                lastIndex--;
            }
            else {
                while(i >= pointer.width[lastIndex]) {
                    i = i - pointer.width[lastIndex];
                    pointer = pointer.next[lastIndex];
                }
                pointer.next[lastIndex] = newOne;
                lastIndex--;
            }
        }

        if(lvl > height) {
            height = lvl;
        }
        size++;
        return true;
    }

    public void clear(){
        size = 0;
        height = 1;
        head = new Node<E>(null);
    }

    public E get(int index){
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        Node<E> pointer = head;
        int i = index + 1;
        for(int lastIndex = height - 1; lastIndex >= 0; lastIndex--) {
            while(i >= pointer.width[lastIndex]) {
                i = i - pointer.width[lastIndex];
                pointer = pointer.next[lastIndex];
            }
        }
        return pointer.element;
    }

    public void add(int index, E element){
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        int lvl = randomLevel();

        if(lvl > MAX_LEVEL) {
            lvl = MAX_LEVEL;
        }

        Node<E> pointer = head;
        Node<E> newOne = new Node(element);

        if(lvl > height) {

            int lastIndex = lvl - 1;

            while(lastIndex > height - 1) {
                newOne.width[lastIndex] = size - index + 1;
                head.next[lastIndex] = newOne;
                head.width[lastIndex] = index + 1;
                lastIndex--;
            }
        }


        int i = index;
        for(int lastIndex = height - 1; lastIndex >= 0; lastIndex--) {
            while(i >= pointer.width[lastIndex]) {
                i = i - pointer.width[lastIndex];
                pointer = pointer.next[lastIndex];
            }

            int frontwidth = i + 1;
            int backwidth = pointer.width[lastIndex] - i;
            newOne.next[lastIndex] = pointer.next[lastIndex];
            pointer.next[lastIndex] = newOne;
            newOne.width[lastIndex] = backwidth;
            pointer.width[lastIndex] = frontwidth;

        }
        size++;
    }

    private int randomLevel(){
        int lvl = 1;
        while(r.nextDouble() < p && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }

    public E remove(int index){
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        if(size == 1 && index == 0) {
            E result = head.next[0].element;
            clear();
            return result;
        }

        Node<E> pointer = head;
        E result = null;

        int i = index + 1;

        for(int lastIndex = height - 1; lastIndex >= 0; lastIndex--) {
            while(i > pointer.width[lastIndex]) {
                i = i - pointer.width[lastIndex];
                pointer = pointer.next[lastIndex];
            }
            if(i - pointer.width[lastIndex] == 0) {
                int newWidth = pointer.width[lastIndex] + pointer.next[lastIndex].width[lastIndex] - 1;
                pointer.next[lastIndex] = pointer.next[lastIndex].next[lastIndex];
                pointer.width[lastIndex] = newWidth;
            }
            else {
                pointer.width[lastIndex]--;
            }
        }
        int temp = height;
        for(int j = height - 1; j >= 0; j--) {
            if(head.next[j] == null) {
                head.width[j] = 1;
                temp--;
            }
            else {
                break;
            }
        }
        this.height = temp;
        size--;

        return result;
    }

    public String toString(){
        String result = "";
        Node<E> pointer = head;
        for(int i = 0; i < size; i++) {
            if(i + 1 == size) {
                result += pointer.next[0].element;
            }
            else {
                result += pointer.next[0].element + ", ";
                pointer = pointer.next[0];
            }
        }
        return "[" + result + "]";
    }

    private class Node<E>{
        int[] width = new int[MAX_LEVEL];
        Node<E>[] next = new Node[MAX_LEVEL];
        E element;

        Node(E element) {
            this.element = element;
            for(int i = 0; i < width.length; i++) {
                width[i] = 1;
            }
            for(int i = 0; i < width.length; i++) {
                next[i] = null;
            }
        }
    }
}
