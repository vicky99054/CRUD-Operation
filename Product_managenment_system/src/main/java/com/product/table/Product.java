package com.product.table;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int id;
	private String name;
	private String brand;
	private String catagroy;
	private double price ;
	
	@Column(columnDefinition = "Text")
	private String descripation;
	private Date creattedt;
	private String imageFileName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getDescripation() {
		return descripation;
	}
	public void setDescripation(String descripation) {
		this.descripation = descripation;
	}
	public Date getCreattedt() {
		return creattedt;
	}
//	public void setCreattedt(Date creattedt) {
//		this.creattedt = creattedt;
//	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public void setCreattedt(Date date) {
		this.creattedt=date;
		// TODO Auto-generated method stub
		
	}
	

	// getter--satters


	

}
