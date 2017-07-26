/**
 * 
 */
package tmall.bean;

/**
 * @author TimesupSJ
 * @date 2017年7月12日
 * @time 上午10:34:33
 * 功能：商品属性的实体类
 *
 */
public class Property {
	private String name;
	private Category category;
	private int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
