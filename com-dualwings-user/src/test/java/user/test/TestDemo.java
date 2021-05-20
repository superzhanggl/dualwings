package user.test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dualwings.user.dao.UserMapper;
import com.dualwings.user.po.User;

import user.ApplicationTest;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@EnableAutoConfiguration

public class TestDemo {
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void Test() {
		User user = new User();
		user.setPassword("1111");
		user.setUserId("1212121212");
		user.setUserName("2222");
		userMapper.insert(user);
	}
	
}
