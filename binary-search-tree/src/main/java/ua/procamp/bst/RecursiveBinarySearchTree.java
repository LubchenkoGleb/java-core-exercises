package ua.procamp.bst;

import java.util.Objects;
import java.util.function.Consumer;

public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;

    private int size;

    @Override
    public boolean insert(T element) {

        Node<T> newNode = new Node<>(element);

        if (root == null) {
            this.root = newNode;
        } else {
            boolean result = this.insertRecursively(element, this.root);
            if (!result) {
                return false;
            }
        }
        size++;

        return true;
    }

    private boolean insertRecursively(T element, Node<T> node) {
        if (Objects.isNull(node)) {
            return false;
        } else if (element.compareTo(node.value) > 0) {
            return insertInSubTree(element, node, false);
        } else if (element.compareTo(node.value) < 0) {
            return insertInSubTree(element, node, true);
        } else {
            return false;
        }
    }

    private boolean insertInSubTree(T element, Node<T> node, boolean left) {

        if (Objects.isNull(node.leftChild) && left) {
            node.leftChild = new Node<>(element);
            return true;
        } else if (Objects.isNull(node.rightChild) && !left) {
            node.rightChild = new Node<>(element);
            return true;
        } else {
            return insertRecursively(element, left ? node.leftChild : node.rightChild);
        }
    }

    @Override
    public boolean search(T element) {
        return this.searchRecursively(element, root);
    }

    private boolean searchRecursively(T element, Node<T> node) {
        if (Objects.isNull(node)) {
            return false;
        } else if (element.compareTo(node.value) > 0) {
            return searchRecursively(element, node.rightChild);
        } else if (element.compareTo(node.value) < 0) {
            return searchRecursively(element, node.leftChild);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return this.getHeightRecursively(root, 0);
    }

    private int getHeightRecursively(Node<T> node, int height) {
        if (Objects.isNull(node)) {
            return height;
        }
        return Math.max(
                Objects.nonNull(node.rightChild) ? getHeightRecursively(node.rightChild, height + 1) : height,
                Objects.nonNull(node.leftChild) ? getHeightRecursively(node.leftChild, height + 1) : height);
    }


    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        this.inOrderTraversalRecursively(root, consumer);
    }

    private void inOrderTraversalRecursively(Node<T> node, Consumer<T> consumer) {
        if (Objects.nonNull(node)) {
            inOrderTraversalRecursively(node.leftChild, consumer);
            consumer.accept(node.value);
            inOrderTraversalRecursively(node.rightChild, consumer);
        }
    }

    private static class Node<T extends Comparable<T>> {

        private T value;

        private Node<T> leftChild;

        private Node<T> rightChild;

        Node(T value) {
            this.value = value;
        }
    }

}
