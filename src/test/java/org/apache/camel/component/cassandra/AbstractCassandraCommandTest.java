package org.apache.camel.component.cassandra;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.util.IOHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doCallRealMethod;

public class AbstractCassandraCommandTest {

    private AbstractCassandraCommand command;
    private Exchange exchange;

    @Before
    public void setUp() throws Exception {
        command = Mockito.mock(AbstractCassandraCommand.class);
        command.exchange = new DefaultExchange(new DefaultCamelContext());
        exchange = command.exchange;
    }

    @Test
    public void testDetermineColumn() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN, "myColumn");
        doCallRealMethod().when(command).determineColumn();

        assertEquals("myColumn", new String(command.determineColumn().array(), IOHelper.getCharsetName(exchange)));

    }

    @Test
    public void testDetermineColumnFamily() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.COLUMN_FAMILY, "myColumnFamily");
        doCallRealMethod().when(command).determineColumnFamily();

        assertEquals("myColumnFamily", command.determineColumnFamily());
    }

    @Test
    public void testDetermineKey() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.KEY, "myKey");
        doCallRealMethod().when(command).determineKey();

        assertEquals("myKey", new String(command.determineKey().array(), IOHelper.getCharsetName(exchange)));
    }

    @Test
    public void testDetermineSuperColumn() throws Exception {
        exchange.getIn().setHeader(CassandraConstants.SUPER_COLUMN, "mySuperColumn");
        doCallRealMethod().when(command).determineSuperColumn();

        assertEquals("mySuperColumn", new String(command.determineSuperColumn().array(), IOHelper.getCharsetName(exchange)));
    }

    @Test
    public void testDoProcessResult() throws Exception {
        doCallRealMethod().when(command).doProcessResult(any());

        Object myResult = new Object();
        command.doProcessResult(myResult);

        assertFalse(exchange.hasOut());
        assertNull(exchange.getOut().getBody());
        assertNull(exchange.getOut().getHeader(CassandraConstants.RESULT));
        assertSame(myResult, exchange.getIn().getBody());
        assertSame(myResult, exchange.getIn().getHeader(CassandraConstants.RESULT));
    }

    @Test
    public void testDoProcessResultWithExchangeOut() throws Exception {
        doCallRealMethod().when(command).doProcessResult(any());

        Object myResult = new Object();
        exchange.setPattern(ExchangePattern.InOut);
        command.doProcessResult(myResult);

        assertTrue(exchange.hasOut());
        assertNull(exchange.getIn().getBody());
        assertNull(exchange.getIn().getHeader(CassandraConstants.RESULT));
        assertSame(myResult, exchange.getOut().getBody());
        assertSame(myResult, exchange.getOut().getHeader(CassandraConstants.RESULT));
    }
}
