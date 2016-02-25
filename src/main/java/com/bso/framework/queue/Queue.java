package com.bso.framework.queue;

public interface Queue<E> {
	public boolean offer(E e);
	public E poll();
	public E peek();
	public boolean isEmpty();
	public boolean isFull();
}
