package com.ctvit.flex.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctvit.flex.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserDAOTest {
	@Autowired
	UserDAO userDAO;

	@Test
	public void test() {
		User user = new User();
		user.setId(3052);
		userDAO.delete(user);
	}

	@Test
	public void test1() {
		List<User> list = userDAO.findAll(1, 7);
		for (User user : list) {
			System.out.println(user.getNickname());
		}
	}

	@Test
	public void test3() {
		List<User> list = userDAO.findByProperty("nickname", "张三丰");
		System.out.println(list.size());

	}

	@Test
	public void test5() {
		User user = userDAO.get(3309);
		int count = 0;
		if (user != null) {
			userDAO.delete(user);
			count++;
		}
		System.out.println(count);
	}
}
