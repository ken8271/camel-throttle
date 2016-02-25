package com.bso.framework.throttle;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link CircleQueueEndpoint}.
 */
public class CircleQueueComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
    	CircleQueueEndpoint endpoint = new CircleQueueEndpoint(uri, this);
        setProperties(endpoint, parameters);
        endpoint.init();
        return endpoint;
    }
}
