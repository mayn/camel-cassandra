package org.apache.camel.component.cassandra.thrift.command;

import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.util.ExchangeHelper;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class AbstractThriftCommandTest {
    private AbstractThriftCommand command;
    private Exchange exchange;
    private CassandraThriftConfiguration configuration;
    private Cassandra.Client client;

    @Before
    public void setUp() throws Exception {
        configuration = new CassandraThriftConfiguration();
        configuration.setKeyspace("aKeyspace");

        client = mock(Cassandra.Client.class);
        exchange = new DefaultExchange(new DefaultCamelContext());
        command = new MyAbstractThriftCommand(client, configuration, exchange);
    }

    @Test
    public void testDetermineKeyspace() throws Exception {
        assertEquals(configuration.getKeyspace(), ExchangeHelper.convertToMandatoryType(exchange, String.class, command.determineKeyspace()));
    }

    @Test
    public void testDetermineKeyspaceFromHeaderValue() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.KEYSPACE, "myKeyspace");

        assertEquals("myKeyspace", ExchangeHelper.convertToMandatoryType(exchange, String.class, command.determineKeyspace()));
    }

    @Test
    public void testDetermineColumn() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN, "myColumn");

        assertEquals("myColumn", ExchangeHelper.convertToMandatoryType(exchange, String.class, command.determineColumn()));
    }

    @Test
    public void testDetermineColumnFamily() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN_FAMILY, "myColumnFamily");

        assertEquals("myColumnFamily", command.determineColumnFamily());
    }

    @Test
    public void testDetermineKey() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.KEY, "myKey");

        assertEquals("myKey", ExchangeHelper.convertToMandatoryType(exchange, String.class, command.determineKey()));
    }

    @Test
    public void testDetermineSuperColumn() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.SUPER_COLUMN, "mySuperColumn");

        assertEquals("mySuperColumn", ExchangeHelper.convertToMandatoryType(exchange, String.class, command.determineSuperColumn()));
    }

    @Test
    public void testDetermineColumnParent() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN_FAMILY, "myColumnFamily");
        exchange.getIn().setHeader(CassandraConstants.SUPER_COLUMN, "mySuperColumn");

        ColumnParent result = command.determineColumnParent();

        assertNotNull(result);
        assertEquals("myColumnFamily", result.getColumn_family());
        assertEquals("mySuperColumn", ExchangeHelper.convertToMandatoryType(exchange, String.class, result.getSuper_column()));
    }

    @Test
    public void testDetermineColumnPath() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN_FAMILY, "myColumnFamily");
        exchange.getIn().setHeader(CassandraConstants.SUPER_COLUMN, "mySuperColumn");
        exchange.getIn().setHeader(CassandraConstants.COLUMN, "myColumn");

        ColumnPath result = command.determineColumnPath();

        assertNotNull(result);
        assertEquals("myColumnFamily", result.getColumn_family());
        assertEquals("myColumn", ExchangeHelper.convertToMandatoryType(exchange, String.class, result.getColumn()));
        assertEquals("mySuperColumn", ExchangeHelper.convertToMandatoryType(exchange, String.class, result.getSuper_column()));
    }

    @Test
    public void testDetermineConsistencyLevel() throws Exception {
        ConsistencyLevel result = command.determineConsistencyLevel();

        assertNotNull(result);
        assertEquals(configuration.getConsistencyLevel(), result);
    }

    @Test
    public void testDetermineConsistencyLevelFromHeaderValue() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.CONSISTENCY_LEVEL, ConsistencyLevel.ALL);

        ConsistencyLevel result = command.determineConsistencyLevel();

        assertNotNull(result);
        assertEquals(ConsistencyLevel.ALL, result);
    }

    @Test
    public void testdoSetKeyspaceOnClient() throws Exception {
        command.doSetKeyspaceOnClient();

        verify(client).set_keyspace(anyString());
    }

    @Test
    public void testdoSetKeyspaceOnClientWithoutKeyspace() throws Exception {
        configuration.setKeyspace(null);
        command.doSetKeyspaceOnClient();

        verify(client, never()).set_keyspace(anyString());
    }

    @Test
    public void testDetermineTtl() throws Exception {
        Integer ttl = 60;
        exchange.getIn().setHeader(CassandraConstants.TTL, ttl);

        assertEquals(ttl, command.determineTtl());
    }

    @Test
    public void testDetermineTtlNotSet() throws Exception {
        assertNull(command.determineTtl());
    }

    @Test
    public void testDetermineTimestamp() throws Exception {
        long timestamp = System.currentTimeMillis();
        exchange.getIn().setHeader(CassandraConstants.TIMESTAMP, timestamp);

        assertEquals(timestamp, command.determineTimestamp());
    }

    @Test
    public void testDetermineTimestampNotSet() throws Exception {
        assertEquals(0L, command.determineTimestamp());
    }

    @Test
    public void testDetermineThriftColumn() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN, "myColumn");
        exchange.getIn().setHeader(CassandraConstants.TIMESTAMP, 1234L);
        exchange.getIn().setHeader(CassandraConstants.TTL, 120);
        exchange.getIn().setBody("myValue");

        Column result = command.determineThriftColumn();

        assertNotNull(result);
        assertEquals(120, result.getTtl());
        assertEquals(1234L, result.getTimestamp());
        assertEquals("myColumn", ExchangeHelper.convertToMandatoryType(exchange, String.class, result.getName()));
        assertEquals("myValue", ExchangeHelper.convertToMandatoryType(exchange, String.class, result.getValue()));
    }

    /**
     * util class used to populate AbstractThriftCommand
     */
    class MyAbstractThriftCommand extends AbstractThriftCommand {
        protected MyAbstractThriftCommand(Cassandra.Client cassandraClient, CassandraThriftConfiguration configuration, Exchange exchange) {
            super(cassandraClient, configuration, exchange);
        }

        @Override
        public void execute() throws Exception {
            //noop
        }
    }
}
