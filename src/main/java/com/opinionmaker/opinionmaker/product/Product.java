package com.opinionmaker.opinionmaker.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Product {

	@Id
	private String id;
	private String name;
	
	private List<String> files;
	
	public Product() {
		this.files = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
}
