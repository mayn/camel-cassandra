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
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GetCommandTest {
    private Cassandra.Client client;
    private CassandraThriftConfiguration configuration;
    private Exchange exchange;
    private GetCommand command;

    @Before
    public void setUp() throws Exception {
        client = mock(Cassandra.Client.class);
        configuration = new CassandraThriftConfiguration();
        exchange = new DefaultExchange(new DefaultCamelContext());

        command = new GetCommand(client, configuration, exchange);
    }


    @Test
    public void testExecute() throws Exception {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(CassandraConstants.KEYSPACE, "myKeyspace");
        headers.put(CassandraConstants.KEY, "myKey");
        headers.put(CassandraConstants.COLUMN_FAMILY, "myColumnFamily");
        headers.put(CassandraConstants.COLUMN, "myColumn");
        exchange.getIn().setHeaders(headers);

        command.execute();

        verify(client).set_keyspace((String) headers.get(CassandraConstants.KEYSPACE));
        verify(client).get(any(ByteBuffer.class), any(ColumnPath.class), any(ConsistencyLevel.class));
    }


}
