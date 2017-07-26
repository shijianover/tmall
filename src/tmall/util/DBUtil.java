/**
 * 
 */
package tmall.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



/**
 * @author TimesupSJ
 * @date 2017年7月4日
 * @time 下午1:48:21
 *
 */
public class DBUtil {
	
	private static String driver;
	private static String dburl;
	private static String username;
	private static String pwd;
	
	static{
		//读取配置文件，把配置文件中的对应值，设置到静态的成员变量中来
		InputStream in=DBUtil.class.getClassLoader().getResourceAsStream("db-config.properties");
		//对InputStream流进行包装，把他包装成一个Properties对象(Properties是一个特殊的map集合,里面的元素都是有 key=value组成的)
		Properties prop=new Properties();
		try {
			prop.load(in);
			driver=prop.getProperty("driverClass");//getProperty（String key）方法中的参数为properties文件中的key
			dburl=prop.getProperty("dburl");
			username=prop.getProperty("user");
			pwd=prop.getProperty("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	
	
	public static void close(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//PreparedStatement
	public static void close(Connection con,Statement st){
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close(con);
		}
	}
	
	public static void close(Connection con,Statement st,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		close(con,st);
	}
	
	
	public static Connection getConnection(){
		Connection con=null;
		   try {
			Class.forName(driver);
			con=DriverManager.getConnection(dburl, username, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * 功能：对所有表的更新操作（insert ,update,delete)进行抽取优化,形成一个统一的update方法
	 * 方法参数说明：
	 * 第一个 sql:表示要进行更新的sql语句
	 * 第二个params:表示sql对应问号位置的值（如果有可变化参数的话）
	 * @return
	 */
	public static  boolean update(String sql,Object[] params){
		boolean result=false;
		Connection con=null;
		PreparedStatement ps=null;
		con=getConnection();
		if(con!=null){
			try {
				ps=con.prepareStatement(sql);
				//如何给问号位置赋值呢？
				for(int i=0;params!=null&&i<params.length;i++){
					//还要考虑类型匹配的问题（如实体类中的boolean-》int,实体类中的java.util.Date->java.sql.Date
					if(params[i] instanceof Boolean || params[i].getClass() ==boolean.class){
						boolean b=(boolean) params[i];
						ps.setInt(i+1, b?1:0);
					}else if(params[i] instanceof java.util.Date){
						java.util.Date date=(java.util.Date)params[i];
						ps.setDate(i+1, new java.sql.Date(date.getTime()));
					}else{
						ps.setObject(i+1, params[i]);	
					}
					
				}
				int n=ps.executeUpdate();
				if(n>0){
					result=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				close(con,ps);
			}
		}
		return result;
	}
}
