package org.apache.camel.component.cassandra.thrift.command;


import java.nio.ByteBuffer;

import org.apache.camel.Exchange;
import org.apache.camel.NoTypeConversionAvailableException;
import org.apache.camel.component.cassandra.AbstractCassandraCommand;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.apache.camel.component.cassandra.thrift.CassandraThriftConfiguration;
import org.apache.camel.util.ExchangeHelper;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.TException;

public abstract class AbstractThriftCommand extends AbstractCassandraCommand {

    protected CassandraThriftConfiguration configuration;
    protected Cassandra.Client cassandraClient;

    public AbstractThriftCommand(Cassandra.Client cassandraClient, CassandraThriftConfiguration configuration, Exchange exchange) {
        super(exchange);
        this.configuration = configuration;
        this.cassandraClient = cassandraClient;
    }

    protected String determineKeyspace() {
        String keyspace = exchange.getIn().getHeader(CassandraConstants.KEYSPACE, String.class);

        return keyspace != null ? keyspace : configuration.getKeyspace();
    }

    protected ByteBuffer determineColumn() throws NoTypeConversionAvailableException {
        String column = exchange.getIn().getHeader(CassandraConstants.COLUMN, String.class);

        //calling ByteBuffer.wrap was necessary as they weren't being processed correctly by thrift. not sure why
        return column == null ? null : ByteBuffer.wrap(ExchangeHelper.convertToMandatoryType(exchange, byte[].class, column));
    }

    protected String determineColumnFamily() {
        return exchange.getIn().getHeader(CassandraConstants.COLUMN_FAMILY, String.class);
    }

    protected ByteBuffer determineKey() {
        return ByteBuffer.wrap(exchange.getIn().getHeader(CassandraConstants.KEY, byte[].class));
    }

    protected ByteBuffer determineSuperColumn() throws NoTypeConversionAvailableException {
        String superColumn = exchange.getIn().getHeader(CassandraConstants.SUPER_COLUMN, String.class);

        //calling ByteBuffer.wrap was necessary as they weren't being processed correctly by thrift. not sure why
        return superColumn == null ? null : ByteBuffer.wrap(ExchangeHelper.convertToMandatoryType(exchange, byte[].class, superColumn));
    }

    /**
     * Creates a new {@link org.apache.cassandra.thrift.ColumnParent} from values gathered from current {@link Exchange}
     *
     * @return <tt>ColumnParent</tt>
     */
    protected ColumnParent determineColumnParent() throws NoTypeConversionAvailableException {
        ColumnParent columnParent = new ColumnParent(determineColumnFamily());
        columnParent.setSuper_column(determineSuperColumn());

        return columnParent;
    }

    /**
     * Creates a new {@link org.apache.cassandra.thrift.ColumnPath} populated from values gathered from current {@link Exchange}
     *
     * @return <tt>ColumnPath</tt>
     */
    protected ColumnPath determineColumnPath() throws NoTypeConversionAvailableException {
        ColumnPath columnPath = new ColumnPath(determineColumnFamily());
        columnPath.setColumn(determineColumn());
        columnPath.setSuper_column(determineSuperColumn());

        return columnPath;
    }

    protected ConsistencyLevel determineConsistencyLevel() {
        ConsistencyLevel consistencyLevel = exchange.getIn().getHeader(CassandraConstants.CONSISTENCY_LEVEL, ConsistencyLevel.class);

        return consistencyLevel != null ? consistencyLevel : configuration.getConsistencyLevel();
    }

    /**
     * sets keyspace on client if keyspace is found
     *
     * @throws InvalidRequestException
     * @throws TException
     */
    protected void doSetKeyspaceOnClient() throws InvalidRequestException, TException {
        String keyspace = determineKeyspace();
        if (keyspace != null) {
            cassandraClient.set_keyspace(keyspace);
        }
    }
}
