package org.example;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Теория<br/>
 * <a href="http://e-maxx.ru/algo/treap">http://e-maxx.ru/algo/treap</a><br/>
 * <a href="https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/">https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/</a><br/>
 * <a href="https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/">https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/</a><br/>
 * <a href="http://faculty.washington.edu/aragon/pubs/rst89.pdf">http://faculty.washington.edu/aragon/pubs/rst89.pdf</a><br/>
 * <a href="https://habr.com/ru/articles/101818/">https://habr.com/ru/articles/101818/</a><br/>
 * Примеение в linux kernel<br/>
 * <a href="https://www.kernel.org/doc/mirror/ols2005v2.pdf">https://www.kernel.org/doc/mirror/ols2005v2.pdf</a>
 */
public class Treap<K extends Comparable> {

    Node<K> root;


    public void add(K key) {
        root = insert(root, key);
    }

    public void remove(K key) {
        root = deleteNode(root, key);
    }

    public boolean search(K key) {
        return searchNode(root, key) != null;
    }

    public List<K> inorder() {
        List<K> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    public Node<K>[] split(K key) {
        return root.split(key);
    }

    public void addByMerge(K key) {
        Node<K> tmp = new Node<>(key);

        if (root == null) {
            root = tmp;
            return;
        }

        addByMerge(root, tmp);

    }

    private void addByMerge(Node<K> cur, Node<K> tmp) {
        if (cur.priority < tmp.priority) {
            if (cur.key.compareTo(tmp.key) > 0) {
                if (cur.left != null) {
                    addByMerge(cur.left, tmp);
                } else {
                    cur.left = tmp;

                }
            } else {
                if (cur.right != null) {
                    addByMerge(cur.right, tmp);
                } else {
                    cur.right = tmp;
                }

            }
            return;
        }

        Node<K>[] split = cur.split(tmp.key);
        cur.key = tmp.key;
        cur.priority = tmp.priority;
        cur.left = split[0];
        cur.right = split[1];

    }

    public Node<K> merge(Node<K> leftTree, Node<K> rightTree) {
        if (leftTree == null) {
            return rightTree;
        }
        if (rightTree == null) {
            return leftTree;
        }
        if (leftTree.priority > rightTree.priority) {
            Node<K> newRight = merge(leftTree.right, rightTree);
            return new Node<>(leftTree.key, leftTree.priority, leftTree.left, newRight);
        } else {
            Node<K> newLeft = merge(leftTree, rightTree.left);
            return new Node<>(rightTree.key, rightTree.priority, newLeft, rightTree.right);
        }
    }

    private void inorder(Node<K> cur, List<K> res) {
        if (cur == null) {
            return;
        }
        inorder(cur.left, res);
        res.add(cur.key);
        inorder(cur.right, res);
    }

    private Node<K> searchNode(Node<K> cur, K key) {
        if (cur == null || key.compareTo(cur.key) == 0) {
            return cur;
        }
        if (key.compareTo(cur.key) > 0) {
            return searchNode(cur.right, key);
        }
        return searchNode(cur.left, key);
    }

    private Node<K> deleteNode(Node<K> cur, K key) {
        if (cur == null)
            return cur;

        if (key.compareTo(cur.key) < 0)
            cur.left = deleteNode(cur.left, key);
        else if (key.compareTo(cur.key) > 0)
            cur.right = deleteNode(cur.right, key);

            // IF KEY IS AT ROOT

            // If left is NULL
        else if (cur.left == null) {
            Node<K> temp = cur.right;
            cur = temp;  // Make right child as root
        }
        // If Right is NULL
        else if (cur.right == null) {
            Node<K> temp = cur.left;
            cur = temp;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (cur.left.priority < cur.right.priority) {
            cur = leftRotation(cur);
            cur.left = deleteNode(cur.left, key);
        } else {
            cur = rightRotation(cur);
            cur.right = deleteNode(cur.right, key);
        }

        return cur;
    }

    private Node<K> insert(Node<K> cur, K key) {
        if (cur == null) {
            return new Node<>(key);
        }
        if (key.compareTo(cur.key) > 0) {
            cur.right = insert(cur.right, key);
            if (cur.right.priority < cur.priority) {
                cur = leftRotation(cur);
            }

        } else {
            cur.left = insert(cur.left, key);
            if (cur.left.priority < cur.priority) {
                cur = rightRotation(cur);
            }

        }
        return cur;
    }

    /* T1, T2 and T3 are subtrees of the tree rooted with y
  (on left side) or x (on right side)
                y                               x
               / \     Right Rotation          /  \
              x   T3   – – – – – – – >        T1   y
             / \       < - - - - - - -            / \
            T1  T2     Left Rotation            T2  T3 */

    private Node<K> leftRotation(Node<K> x) {
        Node<K> y = x.right;
        Node<K> t2 = y.left;

        y.left = x;
        x.right = t2;

        return y;
    }

    private Node<K> rightRotation(Node<K> y) {
        Node<K> x = y.left;
        Node<K> t2 = x.right;

        x.right = y;
        y.left = t2;

        return x;
    }

    public List<Node<K>> getSubSet(K max) {
        var result = new ArrayList<Node<K>>();
        getSubSet(max, result, root);
        return result;
    }

    private void getSubSet(K max, List<Node<K>> result, Node<K> current) {
        if (null == current) {
            return;
        }
        getSubSet(max, result, current.left);
        if (max.compareTo(current.key) >= 0) {
            result.add(current);
        }
        getSubSet(max, result, current.right);
    }

    @Getter
    public static class Node<K extends Comparable> {
        static Random rnd = new Random();
        K key;
        int priority;
        Node<K> left;
        Node<K> right;

        public Node(K key) {
            this(key, rnd.nextInt());
        }

        public Node(K key, int priority) {
            this(key, priority, null, null);
        }

        public Node(K key, int priority, Node<K> left, Node<K> right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        public Node<K>[] split(K key) {
            Node<K> tmp = null;

            Node<K>[] res = (Node<K>[]) Array.newInstance(this.getClass(), 2);

            if (this.key.compareTo(key) <= 0) {
                if (this.right == null) {
                    res[1] = null;
                } else {
                    Node<K>[] rightSplit = this.right.split(key);
                    res[1] = rightSplit[1];
                    tmp = rightSplit[0];
                }
                res[0] = new Node<>(this.key, priority, this.left, tmp);
            } else {
                if (left == null) {
                    res[0] = null;
                } else {
                    Node<K>[] leftSplit = this.left.split(key);
                    res[0] = leftSplit[0];
                    tmp = leftSplit[1];
                }
                res[1] = new Node<>(this.key, priority, tmp, this.right);
            }
            return res;
        }

        @Override
        public String toString() {
            return String.format("(%s,%d)", key.toString(), priority);
        }
    }
}
