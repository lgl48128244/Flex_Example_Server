package com.ctvit.flex.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ctvit.flex.dao.UserDAO;
import com.ctvit.flex.model.User;

public class UserService {

	private UserDAO userDAO;

	/**
	 * 查询分页
	 * @param page
	 * @param rows
	 * @param userName
	 * @param realName
	 * @return
	 */

	//	public Map<String, Object> getSearchUserList(Integer page, Integer rows, String userName, String realName) {
	//		Map<String, Object> resultMap = new HashMap<String, Object>();
	//		Map<String, Object> params = new HashMap<String, Object>();
	//		List<User> userList = null;
	//		long count = 0;
	//		String hql = "";
	//		if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(realName)) {
	//			hql = "from com.ctvit.flex.model.User where uname like :uname";
	//			params.put("uname", "%" + userName + "%");
	//			userList = userDAO.find(hql, params, page, rows);
	//			count = userDAO.count(hql, params);
	//		} else if (StringUtils.isNotBlank(realName) && StringUtils.isBlank(userName)) {
	//			hql = "from User where nickname like :nickname";
	//			params.put("nickname", "%" + realName + "%");
	//			userList = userDAO.find(hql, params, page, rows);
	//			count = userDAO.count(hql, params);
	//		} else if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(realName)) {
	//			hql = "from User where uname like :uname or nickname like :nickname";
	//			params.put("uname", "%" + userName + "%");
	//			params.put("nickname", "%" + realName + "%");
	//			userList = userDAO.find(hql, params, page, rows);
	//			count = userDAO.count(hql, params);
	//		} else {
	//			hql = "from User";
	//			userList = userDAO.find(hql, page, rows);
	//			count = userDAO.count(hql);
	//		}
	//		resultMap.put("totalRows", count);
	//		resultMap.put("result", userList);
	//		return resultMap;
	//	}

	public Map<String, Object> getSearchUserList(Integer page, Integer rows, String userName, String realName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<User> userList = null;
		long count = 0;
		String hql = "";
		if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(realName)) {
			hql = "from User where uname like ?";
			Object[] params = { "%" + userName + "%" };
			userList = userDAO.find(hql, params, page, rows);
			count = userDAO.getTotal(hql, params);
		} else if (StringUtils.isNotBlank(realName) && StringUtils.isBlank(userName)) {
			hql = "from User where nickname like ?";
			Object[] params = { "%" + realName + "%" };
			userList = userDAO.find(hql, params, page, rows);
			count = userDAO.getTotal(hql, params);
		} else if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(realName)) {
			hql = "from User where uname like ? or nickname like ?";
			Object[] params = { "%" + userName + "%", "%" + realName + "%" };
			userList = userDAO.find(hql, params, page, rows);
			count = userDAO.getTotal(hql, params);
		} else {
			hql = "from User";
			Object[] params = {};
			userList = userDAO.findAll(page, rows);
			count = userDAO.getTotal(hql, params);
		}
		resultMap.put("totalRows", count);
		resultMap.put("result", userList);
		return resultMap;
	}

	/**
	 * 添加用户
	 */
	public int save(User user) {
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		int count = 0;
		if (user != null) {
			userDAO.save(user);
			count++;
		}
		return count;
	}

	public User findByID(int id) {
		return userDAO.findById(id);
	}

	public int update(User user) {
		int id = user.getId();
		User user2 = userDAO.findById(id);
		user.setCreateTime(user2.getCreateTime());
		user.setUpdateTime(new Date());
		int count = 0;
		if (user != null) {
			userDAO.merge(user);
			count++;
		}
		return count;
	}

	/**
	 * delete多删除
	 * @param user
	 */
	public int delMoreUser(List<Integer> ids) {
		List<User> list = new ArrayList<User>();
		for (Integer id : ids) {
			User user = userDAO.get(id);
			list.add(user);
		}
		userDAO.deleteAll(list);
		return ids.size();
	}

	/**
	 * delete单删除
	 * @param user
	 */
	public int delUser(Integer id) {
		User user = userDAO.get(id);
		int count = 0;
		if (user != null) {
			userDAO.delete(user);
			count++;
		}
		return count;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
