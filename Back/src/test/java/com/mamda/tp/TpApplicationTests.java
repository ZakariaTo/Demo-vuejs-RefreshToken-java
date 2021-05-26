package com.mamda.tp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class TpApplicationTests {

	Mytest mytest = new Mytest();
	@Test
	void contextLoads() {
	}

	@Test
	public void sumTest(){
		System.out.println("My First automating test");
		assertEquals(7,mytest.sum(2,3));
	}
}
