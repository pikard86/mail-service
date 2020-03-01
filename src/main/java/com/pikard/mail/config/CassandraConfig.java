package com.pikard.mail.config;


import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port:#{null}}")
    private Integer port;
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.data.cassandra.username:#{null}}")
    private String userName;
    @Value("${spring.data.cassandra.password:#{null}}")
    private String password;

    @Value("${spring.data.cassandra.cluster-name:#{null}}")
    private String clusterName;


    @Value("${spring.data.cassandra.schema-action:none}")
    private SchemaAction schemaAction;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port != null ? port : super.getPort();
    }

    @Override
    public SchemaAction getSchemaAction() {
        return schemaAction;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.pikard.mail.model"};
    }

    @Override
    public CassandraClusterFactoryBean cluster() {
        PlainTextAuthProvider authProvider = new PlainTextAuthProvider(userName, password);
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setJmxReportingEnabled(false);
        cluster.setContactPoints(getContactPoints());
        cluster.setPort(getPort());
        cluster.setAuthProvider(authProvider);
        cluster.setClusterName(clusterName);
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        cluster.setReconnectionPolicy(new ConstantReconnectionPolicy(1000));

        return cluster;
    }


    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true);

        return List.of(specification);
    }


    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return List.of(DropKeyspaceSpecification.dropKeyspace(keyspace));
    }


}