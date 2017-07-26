/**
 * 
 */
package tmall.bean;

import java.util.Date;

/**
 * @author TimesupSJ
 * @date 2017年7月12日
 * @time 下午1:20:43
 *
 */
public class Review {
	private String content;
	private Date createDate;
	private User user;
	private Product product;
	private int id;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
