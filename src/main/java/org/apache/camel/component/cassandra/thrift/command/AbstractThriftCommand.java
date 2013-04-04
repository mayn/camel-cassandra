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

public abstract class AbstractThriftCommand extends AbstractCassandraCommand {

    protected CassandraThriftConfiguration configuration;
    protected Cassandra.Client cassandraClient;

    public AbstractThriftCommand(Cassandra.Client cassandraClient, CassandraThriftConfiguration configuration, Exchange exchange) {
        super(exchange);
        this.configuration = configuration;
        this.cassandraClient = cassandraClient;
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

        return superColumn == null ? null : ExchangeHelper.convertToMandatoryType(exchange, ByteBuffer.class, superColumn);
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

}
