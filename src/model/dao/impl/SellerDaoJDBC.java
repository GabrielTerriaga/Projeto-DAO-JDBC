package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	//Injecao de dependencia
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}//
	
	@Override
	public void insert(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null; //leva ao banco a consulta sql ou sql
		ResultSet rs = null; //trazendo os dados do DB
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery(); //resultSet recebe a Query que foi enviado e executado no DB
			//testar se a consulta trouxe alguma coisa e transformar de table para obj na memoria, para ter referencia
			if (rs.next()) {
				//Department
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				//Seller
				Seller sel = new Seller();
				sel.setId(rs.getInt("Id"));
				sel.setName(rs.getString("Name"));
				sel.setEmail(rs.getString("Email"));
				sel.setBirthDate(rs.getDate("BirthDate"));
				sel.setBaseSalary(rs.getDouble("BaseSalary"));
				sel.setDepartment(dep);
				return sel;
			}
			System.out.println("ID invalido!");
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
