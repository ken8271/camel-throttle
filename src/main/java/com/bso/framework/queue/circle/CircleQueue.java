package cn.org.itpolaris.queue.circle;

import cn.org.itpolaris.queue.AbstractQueue;

public class CircleQueue<E> extends AbstractQueue<E> {
	
	private int startPos;
	
	private int endPos;
	
	private E[] items;
	
	@SuppressWarnings("unchecked")
	public CircleQueue(int capacity){
		super(capacity);
		startPos = 0;
		endPos = -1;
		items = (E[]) new Object[capacity];
	}
	
	private boolean isLoop(int pos){
		if(pos + 1 >= getCapacity()) return true;
		else return false;
	}
	
	@Override
	public boolean offer(E e) {
		if(e == null) return false;
		
		if(!isFull()){
			if(isLoop(endPos)) {
				endPos = 0;
			}else {
				endPos += 1;
			}
			
			items[endPos] = e;
			
			increaseSize();		
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 * */
	@Override
	public E poll() {
		if(!isEmpty()){
			E e = items[startPos];
			items[startPos] = null;
			
			if(isLoop(startPos)) startPos = 0;
			else startPos += 1;
			
			decreaseSize();
			
			return e;
		} else {			
			return null;
		}
	}

	@Override
	public E peek() {
		if(!isEmpty())
			return items[startPos];
		else 
			return null;
	}

	@Override
	public boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	public boolean isFull() {
		return getSize() == getCapacity();
	}

}
