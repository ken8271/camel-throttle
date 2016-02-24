package cn.org.itpolaris.throttle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.org.itpolaris.queue.Queue;

/**
 * The CircleQueue consumer.
 */
public class CircleQueueConsumer extends DefaultConsumer {
	
	private static final Logger _logger = LoggerFactory.getLogger(CircleQueueConsumer.class);
	
    private Queue<Exchange> queue;
    
    private ExecutorService executor;
    
    private Processor processor;
    
    private boolean runnable = true;

    public CircleQueueConsumer(CircleQueueEndpoint endpoint, Processor processor, boolean multi) {
        super(endpoint, processor);
        this.processor = processor;
        this.queue = endpoint.getQueue();
        
        if(multi)
        	this.executor = Executors.newFixedThreadPool(4);
        else 
        	this.executor = Executors.newFixedThreadPool(1);
        
        this.runnable = true;
    }

	@Override
	protected void doStart() throws Exception {
		executor.execute(new Runnable(){

			@Override
			public void run() {
				while(runnable){
					receive();
					try {
						Thread.sleep(1000);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
			
		});
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		this.runnable = false;
	}

	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		this.runnable = false;
	}
    
    private void  receive(){
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
