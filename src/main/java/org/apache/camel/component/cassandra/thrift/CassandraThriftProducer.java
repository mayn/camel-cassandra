package org.apache.camel.component.cassandra.thrift;


import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.CassandraCommand;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.apache.camel.component.cassandra.thrift.command.GetCommand;
import org.apache.camel.component.cassandra.thrift.command.InsertCommand;
import org.apache.camel.component.cassandra.thrift.command.TruncateCommand;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.util.URISupport;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.transport.TTransportException;

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
        Cassandra.Client client = getEndpoint().getThriftClient();
        switch (determineOperation(exchange)) {
            case get:
                command = new GetCommand(client, getEndpoint().getConfiguration(), exchange);
                break;
            case insert:
                command = new InsertCommand(client, getEndpoint().getConfiguration(), exchange);
                break;
            case truncate:
                command = new TruncateCommand(client, getEndpoint().getConfiguration(), exchange);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation");
        }

        try {
            openClientTransport(client);
            command.execute();
        } finally {
            closeClientTransport(client);
        }
    }

    private void openClientTransport(Cassandra.Client client) throws TTransportException {
        if (client != null && client.getInputProtocol() != null && client.getInputProtocol().getTransport() != null && !client.getInputProtocol().getTransport().isOpen()) {
            client.getInputProtocol().getTransport().open();
        }
    }

    private void closeClientTransport(Cassandra.Client client) {
        if (client != null && client.getInputProtocol() != null && client.getInputProtocol().getTransport() != null) {
            client.getInputProtocol().getTransport().close();
        }
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
