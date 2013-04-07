package org.apache.camel.component.cassandra.thrift.command;

import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.cassandra.thrift.Cassandra;

/**
 * Camel Cassandra Thrift API wrapper for
 * {@link org.apache.cassandra.thrift.Cassandra.Client#insert(java.nio.ByteBuffer, org.apache.cassandra.thrift.ColumnParent, org.apache.cassandra.thrift.Column, org.apache.cassandra.thrift.ConsistencyLevel)}
 */
public class InsertCommand extends AbstractThriftCommand {

    public InsertCommand(Cassandra.Client cassandraClient, CassandraThriftConfiguration configuration, Exchange exchange) {
        super(cassandraClient, configuration, exchange);
    }

    @Override
    public void execute() throws Exception {
        doSetKeyspaceOnClient();

        cassandraClient.insert(determineKey(), determineColumnParent(), determineThriftColumn(), determineConsistencyLevel());
    }


}
