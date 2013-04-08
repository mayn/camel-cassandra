package org.apache.camel.component.cassandra.thrift;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThriftOperationsTest {

    @Test
    public void supportedOperationCount() {
        assertEquals(4, ThriftOperations.values().length);
    }


    @Test
    public void testValueOf() {
        assertEquals(ThriftOperations.get, ThriftOperations.valueOf("get"));
        assertEquals(ThriftOperations.insert, ThriftOperations.valueOf("insert"));
        assertEquals(ThriftOperations.remove, ThriftOperations.valueOf("remove"));
        assertEquals(ThriftOperations.truncate, ThriftOperations.valueOf("truncate"));
    }
}
