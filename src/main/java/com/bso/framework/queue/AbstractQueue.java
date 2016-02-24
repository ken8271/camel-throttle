package cn.org.itpolaris.queue;

public abstract class AbstractQueue<E> implements Queue<E> {
	public static final int DEFAULT_CAPACITY = 1000;

	private int size;

	private int capacity;
	
	protected AbstractQueue(int capacity){
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void increaseSize(){
		this.size += 1;
	}
	
	public void decreaseSize(){
		this.size -= 1;
	}
}
