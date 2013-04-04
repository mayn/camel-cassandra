package org.apache.camel.component.cassandra;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.util.ExchangeHelper;

public abstract class AbstractCassandraCommand implements CassandraCommand {

    protected Exchange exchange;

    public AbstractCassandraCommand(Exchange exchange) {
        this.exchange = exchange;
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
