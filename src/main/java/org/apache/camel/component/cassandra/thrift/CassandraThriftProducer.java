package org.apache.camel.component.cassandra.thrift;


import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.CassandraCommand;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.util.URISupport;

/**
 * A {@link org.apache.camel.Producer} which sends messages to Cassandra via the <a href="http://wiki.apache.org/cassandra/API">Thrift API</a>
 */
public class CassandraThriftProducer extends DefaultProducer {
    public CassandraThriftProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        CassandraCommand command;
        switch (determineOperation(exchange)) {
            case get:
                command = null;
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation");
        }

        command.execute();

    }

    private ThriftOperations determineOperation(Exchange exchange) {
        ThriftOperations operation = exchange.getIn().getHeader(CassandraConstants.OPERATION, ThriftOperations.class);
        if (operation == null) {
            operation = getEndpoint().getConfiguration().getOperation();
        }

        return operation;
    }

    @Override
    public CassandraThriftEndpoint getEndpoint() {
        return (CassandraThriftEndpoint) super.getEndpoint();
    }

    @Override
    public String toString() {
        return "CassandraThriftProducer[" + URISupport.sanitizeUri(getEndpoint().getEndpointUri()) + "]";
    }
}
