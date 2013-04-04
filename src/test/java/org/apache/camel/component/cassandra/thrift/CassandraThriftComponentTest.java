package org.apache.camel.component.cassandra.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class CassandraThriftComponentTest {
    CassandraThriftComponent component;

    @Before
    public void setUp() throws Exception {
        component = new CassandraThriftComponent(new DefaultCamelContext());
    }

    @Test
    public void testCreateEndpoint() throws Exception {
        String uri = "cassandra://mylocalhost:9999";
        Map<String, Object> parameters = new HashMap<String, Object>();

        CassandraThriftEndpoint endpoint = component.createEndpoint(uri, uri, parameters);

        assertNotNull(endpoint);
        assertEquals("mylocalhost", endpoint.getConfiguration().getHost());
        assertEquals(9999, endpoint.getConfiguration().getPort());
    }
}
