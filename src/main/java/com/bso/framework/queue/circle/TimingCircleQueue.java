package com.bso.framework.queue.circle;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.bso.framework.queue.AbstractQueue;

public class TimingCircleQueue<E> extends AbstractQueue<E> {
	
	private Lock lock;
	
	private long timing;
	
	private CircleQueue<Date> timeQueue;
	
	private CircleQueue<E> itemQueue;
	
	public TimingCircleQueue(int timing , int capacity){
		super(capacity);
		this.timing = timing;
		this.lock = new ReentrantLock();
		this.timeQueue = new CircleQueue<Date>(capacity);
		this.itemQueue = new CircleQueue<E>(capacity);
	}

	@Override
	public boolean offer(E e) {
		lock.lock();
		
		try{
			Date now = new Date();
			if(timeQueue.isFull()){
				if(now.getTime() - timeQueue.peek().getTime() > timing*1000) {
					timeQueue.poll();
					timeQueue.offer(now);
					itemQueue.poll();
					itemQueue.offer(e);
					return true;
				}else {
					return false;
				}
			}else {
				return timeQueue.offer(now) && itemQueue.offer(e);
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E poll() {
		lock.lock();
		try {
			return itemQueue.poll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E peek() {
		lock.lock();
		try {
			return itemQueue.peek();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		lock.lock();
		try {
			return itemQueue.isEmpty();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isFull() {
		lock.lock();
		try {
			return itemQueue.isFull();
		} finally {
			lock.unlock();
		}
	}

}
