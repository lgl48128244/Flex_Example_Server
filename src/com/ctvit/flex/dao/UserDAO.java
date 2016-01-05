package com.ctvit.flex.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ctvit.flex.model.User;

/**
 	* A data access object (DAO) providing persistence and search support for User entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ctvit.flex.dao.User
  * @author MyEclipse Persistence Tools 
 */
public class UserDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

	//property constants

	protected void initDao() {
		//do nothing
	}

	/*********************************************start*************************************************/
	/**
	 * 
	 * @Title: delete
	 * @Description: getHibernateTemplate()自带方法
	 * @param persistentInstance
	 */
	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			this.getSessionFactory().getCurrentSession().flush();
			getHibernateTemplate().update(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void deleteAll(List<User> users) {
		try {
			getHibernateTemplate().deleteAll(users);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public User get(Integer ids) {
		try {
			return getHibernateTemplate().get(User.class, ids);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().load("com.ctvit.flex.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<User> findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from User as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<User> findAll(Integer page, Integer rows) {
		try {
			return getHibernateTemplate().findByExample(new User(), page, rows);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserDAO) ctx.getBean("UserDAO");
	}

	/*********************************************end*************************************************/

	/**************************************************方式一***************************************************/
	/**
	 * @Title: getTotal
	 * @Description:分页+模糊查询 推荐使用
	 * @param hql
	 * @param args
	 * @return
	 */
	public long getTotal(String hql, Object[] args) {
		String cHql = getCountHql(hql);
		Query cq = setParamterQuery(cHql, args);
		return (Long) cq.uniqueResult();
	}

	public List<User> find(String hql, Object[] args, Integer page, Integer rows) {
		Query q = setParamterQuery(hql, args);
		q.setFirstResult(page).setMaxResults(rows);
		return q.list();
	}

	private Query setParamterQuery(String hql, Object[] args) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				q.setParameter(i, args[i]);
			}
		}
		return q;
	}

	private String getCountHql(String hql) {
		String s = hql.substring(0, hql.indexOf("from"));
		if (s == null || s.trim().equals("")) {
			hql = "select count(*) " + hql;
		} else {
			hql = hql.replace(s, "select count(*) ");
		}
		hql = hql.replace("fetch", "");
		return hql;
	}

	/**************************************************end***************************************************/
	/**************************************************方式二***************************************************/
	/**
	 * 
	 * @Title: find
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param hql 不推荐使用
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<User> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<User> find(String hql, int page, int rows) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public Long count(String hql) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql);
		List<User> list = q.list();
		return (long) list.size();
	}

	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<User> list = q.list();
		return (long) list.size();
		//return (Long) q.uniqueResult();
	}

	/**************************************************end***************************************************/

	//	public List<User> getSearchList(Integer page, Integer rows, String userName, String realName) {
	//		// TODO Auto-generated method stub
	//		log.debug("分页查询结果");
	//		try {
	//			Map<String, Object> params = new HashMap<String, Object>();
	//			params.put("uname", "%" + userName + "%");
	//			params.put("nickname", "%" + realName + "%");
	//			List<User> list = getHibernateTemplate().findByExample("com.ctvit.flex.model.User", new User(), page, rows);
	//			for (User user : list) {
	//				System.out.println(user.getEmail());
	//			}
	//			//return getHibernateTemplate().findByExample(new User(), page, rows);
	//			return getHibernateTemplate().findByExample("com.ctvit.flex.model.User", new User(), page, rows);
	//		} catch (RuntimeException re) {
	//			log.error("查询失败", re);
	//			throw re;
	//		}
	//	}
}