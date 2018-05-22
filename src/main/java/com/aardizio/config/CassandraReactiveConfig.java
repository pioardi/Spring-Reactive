package com.aardizio.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

/**
 * Simple configuration for reactive Cassandra support.
 *
 * @author Alessandro Pio Ardizio
 */
@SpringBootApplication(exclude = CassandraDataAutoConfiguration.class)
@EnableReactiveCassandraRepositories
class CassandraReactiveConfiguration extends AbstractReactiveCassandraConfiguration {

	@Autowired
	private CassandraProperties cassandraProperties;

	@Override
	protected String getKeyspaceName() {
		return cassandraProperties.getKeyspaceName();
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.RECREATE;
	}

	/**
	 * Register query logger on cassandra cluster.
	 */
	@Bean
	public QueryLogger getQueryLogger(Cluster cluster) {
		QueryLogger queryLogger = QueryLogger.builder().withConstantThreshold(10) // max thresold for a normal query
				.build();
		cluster.register(queryLogger);
		return queryLogger;
	}
}