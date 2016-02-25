package com.bso.framework.throttle;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bso.framework.queue.Queue;
import com.bso.framework.queue.QueueFactory;

/**
 * Represents a CircleQueue endpoint.
 */
public class CircleQueueEndpoint extends DefaultEndpoint {
	
	private static final Logger _logger = LoggerFactory.getLogger(CircleQueueEndpoint.class);
	
	private static final int DEFAULT_TIMING = 30;
	
	private static final int DEFAULT_CAPACITY = 100;
	
	private String id;
	
	private String timing;
	
	private String capacity;
	
	private Queue<Exchange> queue;

    public CircleQueueEndpoint(String uri, CircleQueueComponent component) {
        super(uri, component);
    }

    public void init() {
    	int t = timing != null && timing.length() != 0 ? Integer.valueOf(timing) : DEFAULT_TIMING;
    	int c = capacity != null && capacity.length() != 0 ? Integer.valueOf(capacity) : DEFAULT_CAPACITY;
    	this.queue = QueueFactory.getInstance().createQueue(id, t, c);
    	_logger.info("[init] - id [" + id + "] , timing [" + t + "] , capacity [" + c + "]");
	}

	public Producer createProducer() throws Exception {
        return new CircleQueueProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new CircleQueueConsumer(this, processor,true);
    }

    public boolean isSingleton() {
        return true;
    }

	public Queue<Exchange> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Exchange> queue) {
		this.queue = queue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
    
}
