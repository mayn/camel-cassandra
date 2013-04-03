package org.apache.camel.component.cassandra.thrift;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultPollingEndpoint;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Defines the <a href="http://wiki.apache.org/cassandra/API">Cassandra Thrift API Endpoint</a>
 */
public class CassandraThriftEndpoint extends DefaultPollingEndpoint  {

    protected CassandraThriftConfiguration configuration;


    public CassandraThriftEndpoint(String endpointUri, CassandraThriftComponent component, CassandraThriftConfiguration configuration) {
        super(endpointUri, component);
        this.configuration = configuration;
    }
    @Override
    public Producer createProducer() throws Exception {
        return new CassandraThriftProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("You cannot receive messages from this endpoint");
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public CassandraThriftConfiguration getConfiguration() {
        return configuration;
    }


    public Cassandra.Client getThriftClient(){
        //TODO correct this. tmp,this is to get up and running only
        TTransport transport = new TFramedTransport(new TSocket(configuration.getHost(), configuration.getPort()));
        TProtocol protocol = new TBinaryProtocol(transport);
        Cassandra.Client client = new Cassandra.Client(protocol);
        return client;
    }
}
