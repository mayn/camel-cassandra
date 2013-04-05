package org.apache.camel.component.cassandra.thrift.command;

import org.apache.camel.Exchange;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.util.ExchangeHelper;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


public class AbstractThriftCommandTest {
    private AbstractThriftCommand command;
    private Exchange exchange;

    @Before
    public void setUp() throws Exception {
        exchange = new DefaultExchange(new DefaultCamelContext());
        command = new MyAbstractThriftCommand(null, null, exchange);
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
        exchange.getIn().setHeader(CassandraConstants.CONSISTENCY_LEVEL, ConsistencyLevel.ALL);

        ConsistencyLevel result = command.determineConsistencyLevel();

        assertNotNull(result);
        assertEquals(ConsistencyLevel.ALL, result);
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
