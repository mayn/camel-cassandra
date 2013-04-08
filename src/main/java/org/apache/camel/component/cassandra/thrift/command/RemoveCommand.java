package org.apache.camel.component.cassandra.thrift.command;

import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;

/**
 * Camel Cassandra Thrift API wrapper for
 * {@link org.apache.cassandra.thrift.Cassandra.Client#remove(java.nio.ByteBuffer, org.apache.cassandra.thrift.ColumnPath, long, org.apache.cassandra.thrift.ConsistencyLevel)}
 */
public class RemoveCommand extends AbstractThriftCommand {

    public RemoveCommand(Cassandra.Client cassandraClient, CassandraThriftConfiguration configuration, Exchange exchange) {
        super(cassandraClient, configuration, exchange);
    }

    @Override
    public void execute() throws Exception {
        doSetKeyspaceOnClient();

        cassandraClient.remove(determineKey(),determineColumnPath(),determineTimestamp(),determineConsistencyLevel());
    }


}
