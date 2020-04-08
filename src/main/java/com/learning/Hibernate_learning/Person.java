package com.learning.Hibernate_learning;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name="person")
@Table(name="person")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Person 
{
	@Id
	private int id;
	@Column(name="person_name")
	private PersonName name;
	private int age;	
	@Transient
	private String country;
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}		
	
	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public int getAge() 
	{
		return age;
	}
	
	public void setAge(int age) 
	{
		this.age = age;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", country=" + country + "]";
	}	
}
