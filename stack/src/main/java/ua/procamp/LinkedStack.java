package ua.procamp;


import ua.procamp.exception.EmptyStackException;

import java.util.Objects;

public class LinkedStack<T> implements Stack<T> {

	private Node<T> head;

	private int size;

	@Override
	public void push(T element) {
		Objects.requireNonNull(element);
		Node<T> node = new Node<>(element);
		if (head == null) {
			head = node;
		} else {
			node.previous = head;
			head = node;
		}
		size++;
	}

	@Override
	public T pop() {
		if (size == 0) {
			throw new EmptyStackException();
		} else {
			T returnValue = head.value;
			head = head.previous;
			size--;
			return returnValue;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	private class Node<T> {

		private T value;

		private Node<T> previous;

		public Node(T value) {
			this.value = value;
		}
	}
}
