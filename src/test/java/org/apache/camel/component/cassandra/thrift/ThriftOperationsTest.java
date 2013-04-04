package org.apache.camel.component.cassandra.thrift;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ThriftOperationsTest {

    @Test
    public void supportedOperationCount() {
        assertEquals(1, ThriftOperations.values().length);
    }


    @Test
    public void testValueOf() {
        assertEquals(ThriftOperations.get, ThriftOperations.valueOf("get"));
    }
}
