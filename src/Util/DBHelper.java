package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {//驱动 与数据库的链接
	private static final String driver= "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
	private static final String username="root";
	private static final String password="zxz123";
	private static Connection con= null;
	
	public DBHelper() {
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		if(con==null) {
			try {System.out.println("到 DBhelper??1");
				con=DriverManager.getConnection(url, username, password);
				System.out.println("到 DBHelper??2"+con);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
			return con;
	}
}
