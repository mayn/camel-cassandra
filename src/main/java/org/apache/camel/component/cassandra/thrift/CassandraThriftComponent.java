package org.apache.camel.component.cassandra.thrift;


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
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        CassandraThriftConfiguration configuration = new CassandraThriftConfiguration();
        setProperties(configuration, parameters);

        CassandraThriftEndpoint endpoint = new CassandraThriftEndpoint(uri, this, configuration);
        return endpoint;
    }
}
