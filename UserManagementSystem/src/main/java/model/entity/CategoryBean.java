package model.entity;

import java.io.Serializable;

public class CategoryBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int categoryId;
	private String categoryName;

	public CategoryBean() {
	}

	public CategoryBean(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}