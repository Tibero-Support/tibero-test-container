package org.tibero.container.project.tibero_container.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class TestConfig {

	@Bean
	public DataSource dataSource(@Value("${spring.db.driver-class-name}") String driverClassName,
		@Value("${spring.db.jdbc-url}") String url,
		@Value("${spring.db.username}") String username,
		@Value("${spring.db.password}") String password) {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load Tibero JDBC driver", e);
		}
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
}

