package org.apache.camel.component.cassandra;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doCallRealMethod;

public class AbstractCassandraCommandTest {

    private AbstractCassandraCommand command;
    private Exchange exchange;

    @Before
    public void setUp() throws Exception {
        exchange = new DefaultExchange(new DefaultCamelContext());
        command = Mockito.mock(AbstractCassandraCommand.class);
        command.exchange = exchange;
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
