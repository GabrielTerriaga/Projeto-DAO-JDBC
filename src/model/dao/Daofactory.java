package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	//Classe para instanciar as interfaces DAO
	//Essa classe vai instanciar por meio do metodo do tipo da interface retornando a classe que contem os metodos,
	//assim o programa nao conhece a implementacao somente a interface
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
