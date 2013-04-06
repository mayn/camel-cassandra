package org.apache.camel.component.cassandra.thrift.command;

import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.cassandra.thrift.Cassandra;

/**
 * Camel Cassandra Thrift API wrapper for
 * {@link org.apache.cassandra.thrift.Cassandra.Client#truncate(String)}
 */
public class TruncateCommand extends AbstractThriftCommand {

    public TruncateCommand(Cassandra.Client cassandraClient, CassandraThriftConfiguration configuration, Exchange exchange) {
        super(cassandraClient, configuration, exchange);
    }

    @Override
    public void execute() throws Exception {
        doSetKeyspaceOnClient();

        cassandraClient.truncate(determineColumnFamily());
    }
}
