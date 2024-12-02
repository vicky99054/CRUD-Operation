package com.product.table;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProductDto {
	
	@NotEmpty(message = "The name is requred")
	private String name;
    
	@NotEmpty(message = "The Brand is requred")
	private String brand;

	@NotEmpty(message = "The Categroy is requred")
	private String catagroy;
	
	@Min(0)
	private double price;
	
	@Size(min = 10 , message = "The description should be at least 10 characters")
	@Size(max  = 1000 , message = "The description should be at least 1000 characters")
    private String description;
	
	private MultipartFile imageFile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCatagroy() {
		return catagroy;
	}

	public void setCatagroy(String catagroy) {
		this.catagroy = catagroy;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	
	
}
