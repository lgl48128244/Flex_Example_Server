package com.ctvit.flex.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ctvit.flex.model.Log;
import com.ctvit.flex.util.DataBaseUtil;

public class LogDAOTest {

	@Test
	public void testList() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Log> list = null;
		try {
			String sql = "select * from vdp_log_export";
			connection = DataBaseUtil.getConnection();
			System.out.println(connection);
			preparedStatement = DataBaseUtil.getPstmt(connection, sql);
			resultSet = preparedStatement.executeQuery();
			list = new ArrayList<Log>();
			while (resultSet.next()) {
				Log log = new Log();
				log.setPk(resultSet.getInt("pk"));
				log.setName(resultSet.getString("name"));
				log.setValue(resultSet.getString("value"));
				list.add(log);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			for (com.ctvit.flex.model.Log log : list) {
				System.out.println(log.getPk()+"\t"+log.getName()+"\t"+log.getValue());
			}
			DataBaseUtil.closeConnStmtRs(connection, preparedStatement, resultSet);
		}
	}

}
