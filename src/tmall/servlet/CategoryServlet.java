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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#edit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#update(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tmall.servlet.BaseBackServlet#list(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, tmall.util.Page)
	 */
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
}
