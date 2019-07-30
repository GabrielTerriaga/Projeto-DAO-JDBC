package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	//INJECAO DE DEPENDENCIA
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}//
	
	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					DB.closeResultSet(rs);
				}
			}
			else {
				throw new DbException("unexpected error! no rows affected!");
			}
		}catch (SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}	
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null; // leva ao banco a consulta sql ou sql
		ResultSet rs = null; // trazendo os dados do DB
		try {
			st = conn.prepareStatement(
					"SELECT department.* " 
					+ "FROM department " 
					+ "WHERE department.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery(); // resultSet recebe a Query que foi enviado e executado no DB
			// testar se a consulta trouxe alguma coisa e transformar de table para obj na
			// memoria, para ter referencia
			if (rs.next()) {
				// Department
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				return dep;
			}
			System.out.println("ID invalido!");
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null; // leva ao banco a consulta sql ou sql
		ResultSet rs = null; // trazendo os dados do DB
		try {
			st = conn.prepareStatement(
					"SELECT * " 
					+ "FROM department " 
					+ "ORDER BY Name");

			rs = st.executeQuery(); // resultSet recebe a Query que foi enviado e executado no DB
			// testar se a consulta trouxe alguma coisa e transformar de table para obj na
			// memoria, para ter referencia

			List<Department> list = new ArrayList<>();
			
			while (rs.next()) {
				Department dep = new Department();	 
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));

				list.add(dep);
				}
			
			return list;
			
			} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
