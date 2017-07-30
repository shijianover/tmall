/**
 * 
 */
package tmall.servlet;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tmall.dao.CategoryDao;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;
import tmall.util.Page;

/**
 * @author Timesupsj
 *下午9:00:47
 */
public abstract class BaseBackServlet extends HttpServlet {
	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) ;
	public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;
	
	
	protected CategoryDao categoryDao = new CategoryDao();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO productImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();
	public void service(HttpServletRequest request,HttpServletResponse response){
		try{
			int start = 0;
			int count = 5;
			try{
				start = Integer.parseInt(request.getParameter("page.start"));
			}catch(Exception e){
				
			}
			try{
				count = Integer.parseInt(request.getParameter("page.count"));
			}catch(Exception e){
				
			}
			Page page = new Page(start,count);
			//这边借助反射调用相应的方法
			String method = (String)request.getAttribute("method");
			System.out.println("得到的method方法是："+method);
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class,Page.class);
			String redirect = m.invoke(this,request,response,page).toString();
			if(redirect.startsWith("@")){
				response.sendRedirect(redirect.substring(1));
			}else if(redirect.startsWith("%")){
				response.getWriter().print(redirect.substring(1));
			}else{
				request.getRequestDispatcher(redirect).forward(request,response);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	public InputStream parseUpload(HttpServletRequest request,Map<String,String> params){
		InputStream is = null;
		try {
			DiskFileItemFactory factory  = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			factory.setSizeThreshold(1024*10240);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while(iter.hasNext()){
				FileItem item = (FileItem)iter.next();
				if(!item.isFormField()){
					is = item.getInputStream();
				}else{
					String paramName = item.getFieldName();
					String paramValue = item.getString();
					paramValue = new String(paramValue.getBytes("iso-8859-1"),"UTF-8");
					params.put(paramName, paramValue);
					
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return is;
		
	}

}
