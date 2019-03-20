package ua.procamp.bst;

import java.util.Objects;
import java.util.function.Consumer;

public class RecursiveBinarySearchTree<T extends Comparable> implements BinarySearchTree<T> {
    private Node<T> root;
    private int size = 0;

    @Override
    public boolean insert(T element) {
        Objects.requireNonNull(element);
        boolean isInserted = insertElement(element);
        if (isInserted) {
            size++;
        }
        return isInserted;
    }

    boolean insertElement(T element) {
        if (root == null) {
            root = new Node<>(element);
            return true;
        } else {
            return insertIntoSubTree(root, element);
        }
    }

    private boolean insertIntoSubTree(Node<T> subTreeRoot, T element) {
        if (subTreeRoot.element.compareTo(element) > 0) {
            return insertIntoLeftSubtree(subTreeRoot, element);
        } else if (subTreeRoot.element.compareTo(element) < 0) {
            return insertIntoRightSubtree(subTreeRoot, element);
        } else {
            return false;
        }
    }

    private boolean insertIntoLeftSubtree(Node<T> node, T element) {
        if (node.left != null) {
            return insertIntoSubTree(node.left, element);
        } else {
            node.left = new Node<>(element);
            return true;
        }
    }

    private boolean insertIntoRightSubtree(Node<T> node, T element) {
        if (node.right != null) {
            return insertIntoSubTree(node.right, element);
        } else {
            node.right = new Node<>(element);
            return true;
        }
    }

    @Override
    public boolean search(T element) {
        Objects.requireNonNull(element);
        return findChildNodeByElement(root, element) != null;
    }

    private Node<T> findChildNodeByElement(Node<T> node, T element) {
        if (node == null) {
            return null;
        } else if (node.element.compareTo(element) > 0) {
            return findChildNodeByElement(node.left, element);
        } else if (node.element.compareTo(element) < 0) {
            return findChildNodeByElement(node.right, element);
        } else {
            return node;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return root != null ? height(root) - 1 : 0;
    }

    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + Math.max(height(node.left), height(node.right));
        }
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    private void inOrderTraversal(Node<T> node, Consumer<T> consumer) {
        if (node != null) {
            inOrderTraversal(node.left, consumer);
            consumer.accept(node.element);
            inOrderTraversal(node.right, consumer);
        }
    }

    private static class Node<T extends Comparable> {
        T element;
        Node<T> left;
        Node<T> right;

        Node(T element) {
            this.element = element;
        }
    }
}
