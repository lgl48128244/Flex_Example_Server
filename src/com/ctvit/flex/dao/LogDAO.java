package com.ctvit.flex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ctvit.flex.model.Log;
import com.ctvit.flex.util.DataBaseUtil;

public class LogDAO {
	private static Logger logger = Logger.getLogger(LogDAO.class);

	public List<Log> list() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Log> list = null;
		try {
			String sql = "select * from vdp_log_export";
			connection = DataBaseUtil.getConnection();
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
			logger.info("查询操作：" + list.size() + "\t");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(connection, preparedStatement, resultSet);
		}
		return list;
	}

	public void addLog(Log log) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into vdp_log_export (name,value) values (?,?)";
		try {
			pstmt = DataBaseUtil.getPstmt(conn, sql);
			pstmt.setString(1, log.getName());
			pstmt.setString(2, log.getValue());
			int i = pstmt.executeUpdate();
			logger.info(i > 0 ? "successfully--添加操作" : "unsuccessfully--添加操作");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(conn, pstmt, null);
		}
	}

	public static Log findByID(int pk) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Log log = null;
		String sql = "select * from vdp_log_export where pk=?";
		try {
			pstmt = DataBaseUtil.getPstmt(conn, sql);
			pstmt.setInt(1, pk);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				log = new Log();
				log.setPk(resultSet.getInt("pk"));
				log.setName(resultSet.getString("name"));
				log.setValue(resultSet.getString("value"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(conn, pstmt, null);
		}
		return log;
	}

	public void updateLog(Log log) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update vdp_log_export set name=?,value=? where pk=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, log.getName());
			pstmt.setString(2, log.getValue());
			pstmt.setInt(3, log.getPk());
			int i = pstmt.executeUpdate();
			logger.info(i > 0 ? "successfully--修改操作" : "unsuccessfully--修改操作");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(conn, pstmt, null);
		}
	}

	/**
	 * 批量删除
	 * @param catNo
	 */
	public static void deleteMore(Log[] catNo) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from vdp_log_export where pk =?";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for (Log in : catNo) {
				pstmt.setObject(1, in.getPk());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(conn, pstmt, null);
		}
	}

	public static void main(String[] args) {
		Log log = new Log();
		Log log1 = findByID(15);
		System.out.println(log1.getName()+log1.getValue());
		log.setName("你好Flex");
		Log[] arr = { log };
		deleteMore(arr);
		System.out.println(arr.toString());
	}

	/**
	 * 删除单条记录
	 */
	public void deleteByPK(Integer pk) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from vdp_log_export where pk =?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pk);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(conn, pstmt, null);
		}
	}
	public void deleteByLog(Log log) {
		Connection conn = DataBaseUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "delete from vdp_log_export where pk =?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, log.getPk());
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeConnStmtRs(conn, pstmt, null);
		}
	}
}
