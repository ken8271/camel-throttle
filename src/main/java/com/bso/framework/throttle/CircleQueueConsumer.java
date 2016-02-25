package com.bso.framework.throttle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bso.framework.queue.Queue;

/**
 * The CircleQueue consumer.
 */
public class CircleQueueConsumer extends DefaultConsumer {
	
	private static final Logger _logger = LoggerFactory.getLogger(CircleQueueConsumer.class);
	
    private Queue<Exchange> queue;
    
    private ScheduledExecutorService executor;
    
    private Processor processor;
    
    public CircleQueueConsumer(CircleQueueEndpoint endpoint, Processor processor, boolean multi) {
        super(endpoint, processor);
        this.processor = processor;
        this.queue = endpoint.getQueue();
        
        if(multi)
        	this.executor = Executors.newScheduledThreadPool(2);
        else 
        	this.executor = Executors.newScheduledThreadPool(1);
    }

	@Override
	protected void doStart() throws Exception {
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				doReceive();
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		executor.shutdown();
	}

	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		executor.shutdown();
	}
    
    private void  doReceive(){
    	Exchange xchange = null;
    	try {
    		xchange = this.queue.poll();
    		if(xchange != null)
    			this.processor.process(xchange);
    	} catch (Exception e) {
    		_logger.error("[receive] - exception catched , error message : [" + e.getMessage() + "]" , e);
    	}
    }
}
