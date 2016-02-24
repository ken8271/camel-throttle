package cn.org.itpolaris.throttle;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.org.itpolaris.queue.Queue;

/**
 * The CircleQueue producer.
 */
public class CircleQueueProducer extends DefaultProducer {
	
    private static final Logger _logger = LoggerFactory.getLogger(CircleQueueProducer.class);
    
    private Queue<Exchange> queue;

    public CircleQueueProducer(CircleQueueEndpoint endpoint) {
        super(endpoint);
        this.queue = endpoint.getQueue();
    }

    public void process(Exchange exchange) throws Exception {
    	if(!queue.offer(exchange)) {
    		_logger.info("[process] - fail to add a exchange to exchange queue");
    	}
    }

}
