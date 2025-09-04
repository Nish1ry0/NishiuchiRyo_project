package model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int productId;
	private String productName;
	private BigDecimal price;
	private int stock;
	private CategoryBean category; 

	public ProductBean() {
	}

	public ProductBean(int productId, String productName, BigDecimal price, int stock, CategoryBean category) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}

	// GetterとSetter
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public CategoryBean getCategory() {
		return category;
	}

	public void setCategory(CategoryBean category) {
		this.category = category;
	}
}