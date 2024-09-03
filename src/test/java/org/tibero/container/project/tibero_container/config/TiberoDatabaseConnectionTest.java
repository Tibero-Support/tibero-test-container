package org.tibero.container.project.tibero_container.config;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TiberoDatabaseConnectionTest extends TestContainerBaseTests {


	@PersistenceContext
	private EntityManager entityManager;

	@Test
	void testSimpleDualQuery() {
		// DUAL 테이블에서 1을 선택하는 간단한 쿼리
		Object result = entityManager.createNativeQuery("SELECT 1 FROM DUAL").getSingleResult();

		assertEquals(1, ((Number) result).intValue(), "Simple query should return 1");
	}
}
