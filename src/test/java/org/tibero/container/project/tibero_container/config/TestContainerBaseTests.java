package org.tibero.container.project.tibero_container.config;

import java.io.File;
import java.time.Duration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

@Import({TestConfig.class})
@EnableConfigurationProperties
public abstract class TestContainerBaseTests {

	@Container
	public static DockerComposeContainer<?> environment =
		new DockerComposeContainer<>(new File("src/test/resources/docker/docker-compose.yml"))
			.withExposedService("tibero-test", 8629,
				Wait.forLogMessage(".*For details.*", 1)
					.withStartupTimeout(Duration.ofMinutes(5)));

	static {
		environment.start();
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.db.driver-class-name", () -> "com.tmax.tibero.jdbc.TbDriver");
		registry.add("spring.db.jdbc-url", () ->
			String.format("jdbc:tibero:thin:@%s:%d:tibero",
				environment.getServiceHost("tibero-test", 8629),
				environment.getServicePort("tibero-test", 8629))
		);
		registry.add("spring.db.username", () -> "tibero");
		registry.add("spring.db.password", () -> "tibero");
	}
}
