package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class FurnitureCategory implements Serializable {

	private String name;
	private String description;
	private FurnitureCategory subCategory;
	
	public FurnitureCategory() {
		// TODO Auto-generated constructor stub
	}

	public FurnitureCategory(String name, String description,
			FurnitureCategory subCategory) {
		super();
		this.name = name;
		this.description = description;
		this.subCategory = subCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FurnitureCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(FurnitureCategory subCategory) {
		this.subCategory = subCategory;
	}

	
}
