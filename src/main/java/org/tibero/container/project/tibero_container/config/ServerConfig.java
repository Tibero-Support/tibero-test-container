package org.tibero.container.project.tibero_container.config;

import java.util.TimeZone;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ServerConfig {

	private final DbProperties dbProperties;

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	@Bean(name = "datasource")
	public DataSource dataSourceProperties() {
		return DataSourceBuilder.create()
			.driverClassName(dbProperties.getDriverClassName())
			.url(dbProperties.getJdbcUrl())
			.username(dbProperties.getUsername())
			.password(dbProperties.getPassword())
			.build();
	}

	@Profile("!test")
	@Bean
	public FlywayMigrationStrategy cleanMigrateStrategy() {
		return flyway -> {
			flyway.repair();
			flyway.migrate();
		};
	}

	@Profile("test")
	@Bean
	public FlywayMigrationStrategy cleanMigrateStrategyForTest() {
		return flyway -> {
			flyway.clean();
			flyway.repair();
			flyway.migrate();
		};
	}

}
