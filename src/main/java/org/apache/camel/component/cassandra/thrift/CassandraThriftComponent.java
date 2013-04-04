package org.apache.camel.component.cassandra.thrift;


import java.net.URI;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Defines the <a href="http://wiki.apache.org/cassandra/API">Cassandra Thrift API Component</a>
 */
public class CassandraThriftComponent extends DefaultComponent {
    public CassandraThriftComponent() {
    }

    public CassandraThriftComponent(CamelContext context) {
        super(context);
    }

    @Override
    protected CassandraThriftEndpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        CassandraThriftConfiguration configuration = new CassandraThriftConfiguration();
        setProperties(configuration, parameters);

        URI myUri = new URI(remaining);

        int port = myUri.getPort();
        if(port != -1 ){
            configuration.setPort(port);
        }

        String host = myUri.getHost();
        if(host != null ){
            configuration.setHost(host);
        }

        CassandraThriftEndpoint endpoint = new CassandraThriftEndpoint(uri, this, configuration);
        return endpoint;
    }
}
