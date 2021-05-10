package List;

import java.util.Random;

public class SkipList<E> {
    private final double p = 0.5;
    private final int MAX_LEVEL = 30;
    private Node<E> head;
    private int size;
    private int height;
    private Random r;

    public SkipList(){
        head = new Node<E>(null);
        head.next = null;
        head.down = null;
        head.distance = 1;
        size = 0;
        height = 1;
    }

    public int size(){
        return size;
    }

    public boolean add(E e){
        add(size, e);
        return true;
    }

    public E get(int index){

    }

    public void add(int index, E element){
        int lvl = randomLevel();
        if(lvl > MAX_LEVEL) {
            lvl = MAX_LEVEL;
        }
        if(lvl > height){

        }
    }

    private int randomLevel(){
        int lvl = 1;
        while(r.nextDouble() < p && lvl < MAX_LEVEL)
            lvl++;

        return lvl;
    }

    public E remove(int index){
    }

    public String toString(){
        String result = "";
        Node<E> pointer = head;
        for(int i = 0; i < size; i++) {
            if(i + 1 == size) {
                result += pointer.next.data;
            }
            else {
                result += pointer.next.data + ", ";
                pointer = pointer.next;
            }
        }
        return "[" + result + "]";
    }

    private static class Node<E>{
        E data;
        Node<E> next;
        Node<E> down;
        int distance;

        Node(E data){
            this.data = data;
        }
    }
}
