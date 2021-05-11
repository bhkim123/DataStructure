package Tree;

import java.util.Collection;
import java.util.Iterator;

public class BSTree<E> {
    Node<E> root;
    int size;
    int index;

    public BSTree(){
        this.root = null;
        this.size = 0;
    }

    public boolean add(E e){

    }

    public boolean addAll(Collection<? extends E> c){

    }

    public boolean remove(Object o){

    }

    private boolean remove(Node<E> start, Object o){

    }

    private E getMin(Node<E> pointer){

    }

    public boolean contains(Object o){

    }

    private void inOrder(Node<E> pointer, E[] iterArr){

    }

    public Iterator<E> iterator() {

    }

    public int height(){

    }

    private int maxHeight(Node<E> root) {
        if (root == null) {
            return -1;
        } else {
            int leftMax = maxHeight(root.left);
            int rightMax = maxHeight(root.right);

            if (leftMax > rightMax) {
                return leftMax + 1;
            } else {
                return rightMax + 1;
            }
        }
    }

    public int size() {
        return size;
    }

    public static class Node<E>{
        E value;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        Node(E value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
}
