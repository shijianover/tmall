/**
 * 
 */
package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.dao.CategoryDao;
import tmall.util.ImageUtil;
import tmall.util.Page;

/**
 * @author Timesupsj
 *下午9:56:10
 */
public class CategoryServlet extends BaseBackServlet {
	public String add(HttpServletRequest request,HttpServletResponse response,Page page){
		Map<String,String> params = new HashMap();
		InputStream is = super.parseUpload(request, params);
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		new CategoryDao().add(c);
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		try{
			if(null!=is && 0!=is.available()){
				try(FileOutputStream fos = new FileOutputStream(file)){
					byte b[] = new byte[1024*1024];
					int len = 0;
					while(-1!=(len = is.read(b))){
						fos.write(b, 0, len);
					}
					fos.flush();
					//将不同格式的图片文件全部转换成jpg格式的文件
					BufferedImage img = ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return "@admin_category_list";
		
	}




	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		categoryDao.delete(id);
		return "@admin_category_list";
	}

	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#edit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		//首先将所要修改的商品种类信息查询上来
		Category c  = categoryDao.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";
		
	}

	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#update(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap();
		InputStream is = super.parseUpload(request, params);
		int id = Integer.parseInt(params.get("id"));
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		c.setId(id);
		//将更新信息写入数据库
		categoryDao.update(c);
		File imgFolder = new File(request.getServletContext().getRealPath("img/category"));
		String imgFolderPath = request.getServletContext().getRealPath("img/category");
		
		System.out.println("这边获得到的图片路径:"+imgFolderPath);
		File file = new File(imgFolder,c.getId()+".jpg");
		try {
			if(null!=is && 0!=is.available()){
				try(FileOutputStream fos = new FileOutputStream(file)){
					byte buffer[] = new byte[1024*1024];
					int len = -1;
					while((len = is.read(buffer))>0){
						fos.write(buffer, 0, len);
					}
					fos.flush();
				}catch(Exception e){
					e.printStackTrace();
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin_category_list";
		
		
	}

	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#list(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		List<Category>cs = categoryDao.list(page.getStart(),page.getCount());
		int total = categoryDao.getTotal();
		page.setTotal(total);
		request.setAttribute("thecs",cs);
		//将查询到的集合写入request对象中去
		request.setAttribute("page", page);
		
		return "admin/listCategory.jsp";
		
	}
}
