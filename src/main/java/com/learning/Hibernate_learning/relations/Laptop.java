package com.learning.Hibernate_learning.relations;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Laptop 
{
	@Id
	private int lId;
	private String name;
	private double price;

	public int getlId() 
	{
		return lId;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setlId(int lId) 
	{
		this.lId = lId;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}

	@Override
	public String toString() 
	{
		return "Laptop [lId=" + lId + ", name=" + name + ", price=" + price + "]";
	}
}
