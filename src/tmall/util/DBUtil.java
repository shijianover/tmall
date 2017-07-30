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
 * @date 2017��7��4��
 * @time ����1:48:21
 *
 */
public class DBUtil {
	
	private static String driver;
	private static String dburl;
	private static String username;
	private static String pwd;
	
	static{
		//��ȡ�����ļ����������ļ��еĶ�Ӧֵ�����õ���̬�ĳ�Ա��������
		InputStream in=DBUtil.class.getClassLoader().getResourceAsStream("db-config.properties");
		//��InputStream�����а�װ��������װ��һ��Properties����(Properties��һ�������map����,�����Ԫ�ض����� key=value��ɵ�)
		Properties prop=new Properties();
		try {
			prop.load(in);
			driver=prop.getProperty("driverClass");//getProperty��String key�������еĲ���Ϊproperties�ļ��е�key
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
	public static void main(String[] args) {
		Connection c = DBUtil.getConnection();
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
		System.out.println(dburl);
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
	 * ���ܣ������б�ĸ��²�����insert ,update,delete)���г�ȡ�Ż�,�γ�һ��ͳһ��update����
	 * ��������˵����
	 * ��һ�� sql:��ʾҪ���и��µ�sql���
	 * �ڶ���params:��ʾsql��Ӧ�ʺ�λ�õ�ֵ������пɱ仯�����Ļ���
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
				//��θ��ʺ�λ�ø�ֵ�أ�
				for(int i=0;params!=null&&i<params.length;i++){
					//��Ҫ��������ƥ������⣨��ʵ�����е�boolean-��int,ʵ�����е�java.util.Date->java.sql.Date
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
