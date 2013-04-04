package org.apache.camel.component.cassandra.thrift;

import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class CassandraThriftEndpointTest {
    CassandraThriftEndpoint endpoint;
    CassandraThriftConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new CassandraThriftConfiguration();
        final String endpointUri = "cassandra://" + configuration.getHost() + ":" + configuration.getPort();
        endpoint = new CassandraThriftEndpoint(endpointUri, new CassandraThriftComponent(), configuration);
    }

    @Test
    public void testCreateProducer() throws Exception {
        Producer result = endpoint.createProducer();

        assertNotNull(result);
        assertTrue(result instanceof CassandraThriftProducer);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateConsumer() throws Exception {
        endpoint.createConsumer(Mockito.mock(Processor.class));
    }

    @Test
    public void testIsSingleton() throws Exception {
        assertTrue(endpoint.isSingleton());
    }

    @Test
    public void testGetConfiguration() throws Exception {
        assertSame(configuration, endpoint.getConfiguration());
    }
}
