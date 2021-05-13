package Tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AVLTree<E> {
    Node<E> root;
    int size;
    int index;

    public AVLTree(){
        this.root = null;
        this.size = 0;
    }

    public boolean add(E e){
        Node<E> p = root;
        if (size == 0) {
            root = new Node<E>(e);
            size++;
            return true;
        } else {
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
        }

        while (p != null) {
            boolean isImbalanced = false;
            int heightDiff = 0;
            while (!isImbalanced && p != null) {
                heightDiff = heightDifference(p);
                if (heightDiff > 1 || heightDiff < -1) {
                    isImbalanced = true;
                    break;
                }
                if (p == root) {
                    break;
                }
                p = p.parent;
            }

            if (isImbalanced) {
                if (heightDiff > 1) {
                    int midNodH = heightDifference(p.left);
                    if (midNodH == 1) {
                        RRotation(p);
                    } else if (midNodH == -1) {
                        LRRotation(p);
                    }
                } else if (heightDiff < -1) {
                    int midNodH = heightDifference(p.right);
                    if (midNodH == 1) {
                        RLRotation(p);
                    } else if (midNodH == -1) {
                        LRotation(p);
                    }
                }
            }
            if (p != null) {
                p = p.parent;
            }
        }
        size++;
        return true;
    }

    private int heightDifference(Node<E> pointer) {
        int leftH = maxHeight(pointer.left);
        int rightH = maxHeight(pointer.right);
        return leftH - rightH;
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

    private void LRotation(Node<E> startNode) {
        if (startNode.parent != null) {
            if (startNode.parent.right == startNode) {
                startNode.parent.right = startNode.right;
            } else {
                startNode.parent.left = startNode.right;
            }
            startNode.right.parent = startNode.parent;
            startNode.parent = startNode.right;
            startNode.right = startNode.parent.left;
            if (startNode.right != null) {
                startNode.right.parent = startNode;
            }
            startNode.parent.left = startNode;
            startNode = startNode.parent;
        } else {
            startNode.right.parent = startNode.parent;
            startNode.parent = startNode.right;
            startNode.right = startNode.parent.left;
            if (startNode.right != null) {
                startNode.right.parent = startNode;
            }
            startNode.parent.left = startNode;
            startNode = startNode.parent;
            root = startNode;
        }
    }

    private void RRotation(Node<E> startNode) {
        if (startNode.parent != null) {
            if (startNode.parent.right == startNode) {
                startNode.parent.right = startNode.left;
            } else {
                startNode.parent.left = startNode.left;
            }
            startNode.left.parent = startNode.parent;
            startNode.parent = startNode.left;
            startNode.left = startNode.parent.right;
            if (startNode.left != null) {
                startNode.left.parent = startNode;
            }
            startNode.parent.right = startNode;
            startNode = startNode.parent;
        } else {
            startNode.left.parent = startNode.parent;
            startNode.parent = startNode.left;
            startNode.left = startNode.parent.right;
            if (startNode.left != null) {
                startNode.left.parent = startNode;
            }
            startNode.parent.right = startNode;
            startNode = startNode.parent;
            root = startNode;
        }
    }

    private void LRRotation(Node<E> startNode) {
        Node<E> tempL = startNode.left;
        startNode.left = tempL.right;
        startNode.left.parent = startNode;
        tempL.parent = startNode.left;
        tempL.right = startNode.left.left;
        if (tempL.right != null) {
            tempL.right.parent = tempL;
        }
        startNode.left.left = tempL;
        RRotation(startNode);
    }

    private void RLRotation(Node<E> startNode) {
        Node<E> tempR = startNode.right;
        startNode.right = tempR.left;
        startNode.right.parent = startNode;
        tempR.parent = startNode.right;
        tempR.left = startNode.right.right;
        if (tempR.left != null) {
            tempR.left.parent = tempR;
        }
        startNode.right.right = tempR;
        LRotation(startNode);
    }

    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        Node<E> p = root;
        Node<E> returned = remove(p, o);
        if (returned == null) {
            return false;
        } else {
            p = returned;
        }
        reBalance(p);
        size--;
        return true;
    }

    private Node<E> remove(Node<E> p, Object o) {
        while (true) {
            if (((Comparable) (p.value)).compareTo((Comparable) (E) o) == 0) {
                break;
            } else if (((Comparable) (p.value)).compareTo((Comparable) (E) o) > 0) {
                if (p.left == null) {
                    return null;
                } else {
                    p = p.left;
                }
            } else {
                if (p.right == null) {
                    return null;
                } else {
                    p = p.right;
                }
            }
        }

        if (p.right == null && p.left == null) {
            if (p.parent != null) {
                if (p.parent.right == p) {
                    p.parent.right = null;
                } else if (p.parent.left == p) {
                    p.parent.left = null;
                }
                p = p.parent;
            } else {
                root = null;
            }
        } else if (p.right == null) {
            p.value = p.left.value;
            p.left = null;
        } else if (p.left == null) {
            p.value = p.right.value;
            p.right = null;
        } else {
            Node<E> successor = inorderSuccessor(p);
            p.value = successor.value;
            remove(successor, successor.value);
        }
        return p;
    }

    private void reBalance(Node<E> p) {

        while (p != null) {
            boolean isImbalanced = false;
            int heightDiff = 0;
            while (!isImbalanced && p != null) {
                heightDiff = heightDifference(p);
                if (heightDiff > 1 || heightDiff < -1) {
                    isImbalanced = true;
                    break;
                }
                if (p == root) {
                    break;
                }
                p = p.parent;
            }

            if (isImbalanced) {
                if (heightDiff > 1) {
                    int midNodH = heightDifference(p.left);
                    if (midNodH == 1) {
                        RRotation(p);
                    } else if (midNodH == -1) {
                        LRRotation(p);
                    }
                } else if (heightDiff < -1) {
                    int midNodH = heightDifference(p.right);
                    if (midNodH == 1) {
                        RLRotation(p);
                    } else if (midNodH == -1) {
                        LRotation(p);
                    }
                }
            }
            if (p != null) {
                p = p.parent;
            }
        }
    }

    private Node<E> inorderSuccessor(Node<E> n) {

        Node<E> temp = n;
        if (temp.left != null) {
            temp = temp.left;
            while (temp.right != null) {
                temp = temp.right;
            }
            return temp;
        }

        else if (temp.right != null) {
            temp = temp.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }

        Node<E> p = temp.parent;
        while (p != null && temp == p.right) {
            temp = p;
            p = p.parent;
        }
        return p;

    }

    public boolean addAll(Collection<? extends E> c) {
        Iterator<? extends E> iter = c.iterator();
        while (iter.hasNext()) {
            add((E) iter.next());
        }
        return true;
    }

    public Iterator<E> iterator() {
        E[] iterArr = (E[]) new Comparable[size];
        index = 0;
        Node<E> pointer = root;
        if (size > 0) {
            inOrder(pointer, iterArr);
        }

        Iterator<E> iter = new Iterator<E>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                if (count < size) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public E next() {
                if (count < size) {
                    return iterArr[count++];
                } else {
                    throw new NoSuchElementException();
                }
            }

        };
        return iter;
    }

    private void inOrder(Node<E> p, E[] iterArr) {
        if (p.left != null) {
            inOrder(p.left, iterArr);
        }
        iterArr[index++] = p.value;
        if (p.right != null) {
            inOrder(p.right, iterArr);
        }
    }

    public int height() {
        return maxHeight(root);
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
