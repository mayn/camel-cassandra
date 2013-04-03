package org.apache.camel.component.cassandra;


public interface CassandraCommand {

    void execute() throws Exception;
}
