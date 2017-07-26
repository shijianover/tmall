/**
 * 
 */
package tmall.bean;

/**
 * @author TimesupSJ
 * @date 2017年7月12日
 * @time 下午1:09:42
 *
 */
public class ProductImage {
	private String type;
	private Product product;
	private int id;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
