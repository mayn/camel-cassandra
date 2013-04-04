package org.apache.camel.component.cassandra.thrift;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CassandraThriftConfigurationTest {
    private CassandraThriftConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new CassandraThriftConfiguration();
    }

    @Test
    public void testDefaultHost() throws Exception {
        assertEquals("localhost", configuration.getHost());
    }

    @Test
    public void testDefaultPort() throws Exception {
        assertEquals(9160, configuration.getPort());
    }

    @Test
    public void testDefaultTimeout() throws Exception {
        assertEquals(3000, configuration.getTimeout());
    }

    @Test
    public void testDefaultConsistencyLevel() throws Exception {
        assertEquals(ConsistencyLevel.QUORUM, configuration.getConsistencyLevel());
    }
}
