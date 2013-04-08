package org.apache.camel.component.cassandra.thrift.command;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class InsertCommandTest {
    private Cassandra.Client client;
    private CassandraThriftConfiguration configuration;
    private Exchange exchange;
    private InsertCommand command;

    @Before
    public void setUp() throws Exception {
        client = mock(Cassandra.Client.class);
        configuration = new CassandraThriftConfiguration();
        exchange = new DefaultExchange(new DefaultCamelContext());

        command = new InsertCommand(client, configuration, exchange);
    }

    @Test
    public void testExecute() throws Exception {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(CassandraConstants.KEYSPACE, "myKeyspace");
        headers.put(CassandraConstants.KEY, "myKey");
        headers.put(CassandraConstants.COLUMN_FAMILY, "myColumnFamily");
        headers.put(CassandraConstants.COLUMN, "myColumn");
        headers.put(CassandraConstants.TTL, 60);
        headers.put(CassandraConstants.TIMESTAMP, System.nanoTime());
        exchange.getIn().setHeaders(headers);

        command.execute();

        verify(client).set_keyspace("myKeyspace");
        verify(client).insert(any(ByteBuffer.class), any(ColumnParent.class), any(Column.class), any(ConsistencyLevel.class));
    }
}
