package com.sudagoarth.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "spring.config.import=optional:configserver:http://localhost:8888"
})
@ActiveProfiles("default")
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
