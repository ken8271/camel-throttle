package cn.org.itpolaris.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.org.itpolaris.queue.circle.TimingCircleQueue;

public class QueueFactory {
	
	private static Map<String , Queue<?>> _queues = new ConcurrentHashMap<String , Queue<?>>();
	
	public static QueueFactory getInstance(){
		return new QueueFactory();
	}
	
	@SuppressWarnings("unchecked")
	public <T> Queue<T> createQueue(String id , int timing , int capacity){
		if(_queues.containsKey(id)){
			return (Queue<T>) _queues.get(id);
		} else {
			Queue<T> queue = new TimingCircleQueue<T>(timing , capacity);
			_queues.put(id, queue);
			return queue;
		}
	}
}
