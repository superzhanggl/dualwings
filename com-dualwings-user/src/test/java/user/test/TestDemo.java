package user.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import user.ApplicationTest;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@EnableAutoConfiguration
public class TestDemo {
	
	@Test
	public void Test() {
		
	}
	
}
