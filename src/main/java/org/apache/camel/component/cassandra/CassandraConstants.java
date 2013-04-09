package org.apache.camel.component.cassandra;


/**
 * Constants used in Camel Cassandra Module
 */
public class CassandraConstants {
    public static final String CAMEL_CASSANDRA_PREFIX = "CamelCassandra";
    public static final String COLUMN = CAMEL_CASSANDRA_PREFIX + "Column";
    public static final String COLUMN_FAMILY = CAMEL_CASSANDRA_PREFIX + "ColumnFamily";
    public static final String CONSISTENCY_LEVEL = CAMEL_CASSANDRA_PREFIX + "ConsistencyLevel";
    public static final String KEY = CAMEL_CASSANDRA_PREFIX + "Key";
    public static final String KEYSPACE = CAMEL_CASSANDRA_PREFIX + "Keyspace";
    public static final String OPERATION = CAMEL_CASSANDRA_PREFIX + "Operation";
    public static final String RESULT = CAMEL_CASSANDRA_PREFIX + "Result";
    public static final String SUPER_COLUMN = CAMEL_CASSANDRA_PREFIX + "SuperColumn";
    public static final String TIMESTAMP = CAMEL_CASSANDRA_PREFIX + "Timestamp";
    public static final String TTL = CAMEL_CASSANDRA_PREFIX + "Ttl";

    private CassandraConstants() {
    }
}
