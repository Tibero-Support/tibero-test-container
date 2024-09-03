package org.tibero.container.project.tibero_container.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "spring.db")
public class DbProperties {
	private String driverClassName;
	private String jdbcUrl;
	private String username;
	private String password;
}
