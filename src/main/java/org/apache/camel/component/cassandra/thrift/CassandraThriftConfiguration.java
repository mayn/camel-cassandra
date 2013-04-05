package org.apache.camel.component.cassandra.thrift;


import org.apache.cassandra.thrift.ConsistencyLevel;

/**
 * cassandra component configuration properties
 */
public class CassandraThriftConfiguration implements Cloneable {

    private String host = "localhost";
    private int port = 9160;
    private int timeout = 3000;
    private ConsistencyLevel consistencyLevel = ConsistencyLevel.QUORUM;
    private ThriftOperations operation;
    private String keyspace;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public ConsistencyLevel getConsistencyLevel() {
        return consistencyLevel;
    }

    public void setConsistencyLevel(ConsistencyLevel consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
    }

    public ThriftOperations getOperation() {
        return operation;
    }

    public void setOperation(ThriftOperations operation) {
        this.operation = operation;
    }

    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }
}
