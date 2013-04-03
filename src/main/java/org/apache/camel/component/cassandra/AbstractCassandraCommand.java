package org.apache.camel.component.cassandra;

import java.nio.ByteBuffer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.NoTypeConversionAvailableException;
import org.apache.camel.util.ExchangeHelper;

public abstract class AbstractCassandraCommand implements CassandraCommand {

    protected Exchange exchange;

    public AbstractCassandraCommand(Exchange exchange) {
        this.exchange = exchange;
    }

    protected ByteBuffer determineColumn() throws NoTypeConversionAvailableException {
        String column = exchange.getIn().getHeader(CassandraConstants.COLUMN, String.class);

        return column != null ? ExchangeHelper.convertToMandatoryType(exchange, ByteBuffer.class, column) : null;
    }

    protected String determineColumnFamily() {
        return exchange.getIn().getHeader(CassandraConstants.COLUMN_FAMILY, String.class);
    }

    protected ByteBuffer determineKey() {
        return exchange.getIn().getHeader(CassandraConstants.KEY, ByteBuffer.class);
    }

    protected ByteBuffer determineSuperColumn() throws NoTypeConversionAvailableException {
        String superColumn = exchange.getIn().getHeader(CassandraConstants.SUPER_COLUMN, String.class);

        return superColumn != null ? ExchangeHelper.convertToMandatoryType(exchange, ByteBuffer.class, superColumn) : null;
    }


    protected void doProcessResult(Object result) {
        Message answer = exchange.getIn();
        if (ExchangeHelper.isOutCapable(exchange)) {
            answer = exchange.getOut();
            // preserve headers
            answer.getHeaders().putAll(exchange.getIn().getHeaders());
        }
        // set the result as body
        answer.setBody(result);

        answer.setHeader(CassandraConstants.RESULT, result);
    }
}
