package Tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTree<E> {
    Node<E> root;
    int size;
    int index;

    public BSTree(){
        this.root = null;
        this.size = 0;
    }

    public boolean add(E e){
        if (size == 0) {
            root = new Node<E>(e);
            size++;
            return true;
        } else {
            Node<E> p = root;
            while (true) {
                if (((Comparable) p.value).compareTo((Comparable) e) == 0) {
                    return false;
                } else if (((Comparable) p.value).compareTo((Comparable) e) > 0) {
                    if (p.left == null) {
                        p.left = new Node(e);
                        p.left.parent = p;
                        break;
                    } else {
                        p = p.left;
                    }
                } else {
                    if (p.right == null) {
                        p.right = new Node(e);
                        p.right.parent = p;
                        break;
                    } else {
                        p = p.right;
                    }
                }
            }
            size++;
            return true;
        }
    }

    public boolean addAll(Collection<? extends E> c){
        Iterator<? extends E> iter = c.iterator();
        while (iter.hasNext()) {
            add((E) iter.next());
        }
        return true;
    }

    public boolean remove(Object o){
        if (remove(root, o)) {
            size--;
            return true;
        } else {
            return false;
        }
    }

    private boolean remove(Node<E> start, Object o){
        Node<E> p = start;

        while (true) {
            if (((Comparable) (p.value)).compareTo((Comparable) (E) o) == 0) break;
            else if (((Comparable) (p.value)).compareTo((Comparable) (E) o) > 0) {
                if (p.left == null) return false;
                else p = p.left;
            } else {
                if (p.right == null) return false;
                else p = p.right;
            }
        } // Find element o. If it doesn't exist, return false.

        if (p.parent == null) {
            if (p.left == null && p.right == null) root = null;
            else if (p.left == null) {
                root = root.right;
                root.parent = null;
            } else if (p.right == null) {
                root = root.left;
                root.parent = null;
            } else {
                E min = getMin(p.right);
                remove(p.right, min);
                p.value = min;
            }
        } // If the element found is root.

        else {
            if (p.left == null && p.right == null) {
                if (p.parent.left == p) {
                    p.parent.left = null;
                } else if (p.parent.right == p) {
                    p.parent.right = null;
                }
                p = null;
            } else if (p.left == null) {
                if (p.parent.right == p) {
                    p.parent.right = p.right;
                    p.right.parent = p.parent;
                    p = null;
                } else if (p.parent.left == p) {
                    p.parent.left = p.right;
                    p.right.parent = p.parent;
                    p = null;
                }
            } else if (p.right == null) {
                if (p.parent.right == p) {
                    p.parent.right = p.left;
                    p.left.parent = p.parent;
                    p = null;
                } else if (p.parent.left == p) {
                    p.parent.left = p.left;
                    p.left.parent = p.parent;
                    p = null;
                }
            } else {
                E min = getMin(p.right);
                remove(p.right, min);
                p.value = min;
            }
        }
        return true;
    }

    private E getMin(Node<E> pointer){
        if (pointer.left != null) {
            return getMin(pointer.left);
        }
        return pointer.value;
    }

    public boolean contains(Object o){
        Node<E> p = root;
        while (true) {
            if (p == null) {
                return false;
            } else {
                while (true) {
                    if (((Comparable) (p.value)).compareTo((Comparable) (E) o) == 0) {
                        return true;
                    } else if (((Comparable) (p.value)).compareTo((Comparable) (E) o) > 0) {
                        if (p.left == null) {
                            return false;
                        } else {
                            p = p.left;
                        }
                    } else {
                        if (p.right == null) {
                            return false;
                        } else {
                            p = p.right;
                        }
                    }
                }
            }
        }
    }

    private void inOrder(Node<E> pointer, E[] iterArr){
        if (pointer.left != null) {
            inOrder(pointer.left, iterArr);
        }
        iterArr[index++] = pointer.value;
        if (pointer.right != null) {
            inOrder(pointer.right, iterArr);
        }
    }

    public Iterator<E> iterator() {
        E[] iterArr = (E[]) new Comparable[size];
        index = 0;
        Node<E> p = root;
        if (size > 0) {
            inOrder(p, iterArr);
        }

        Iterator<E> iter = new Iterator<E>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                if (count < size) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public E next() {
                // TODO Auto-generated method stub
                if (count < size) {
                    return iterArr[count++];
                } else {
                    throw new NoSuchElementException();
                }
            }

        };
        return iter;
    }

    public int height(){
        return maxHeight(root);
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
