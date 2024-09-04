 package org.tibero.container.project.tibero_container.config;

 import static org.junit.jupiter.api.Assertions.*;

 import org.junit.jupiter.api.DisplayName;
 import org.junit.jupiter.api.Test;
 import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
 import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 import org.springframework.test.context.ActiveProfiles;

 import jakarta.persistence.EntityManager;
 import jakarta.persistence.PersistenceContext;

 @DataJpaTest
 @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 @ActiveProfiles("test")
 class TiberoDatabaseSimpleConnectionTest extends TestContainerBaseTests {


 	@PersistenceContext
 	private EntityManager entityManager;

 	@Test
 	@DisplayName("현재 테스트 클래스 두개 이상 실행시 docker compose의 script를 재실행하면서 tibero가 정상적으로 연결되지않아 테스트가 실패하는 문제가 있음")
 	void testSimpleDualQuery() {
 		// DUAL 테이블에서 1을 선택하는 간단한 쿼리
 		Object result = entityManager.createNativeQuery("SELECT 1 FROM DUAL").getSingleResult();

 		assertEquals(1, ((Number) result).intValue(), "Simple query should return 1");
 	}
 }



