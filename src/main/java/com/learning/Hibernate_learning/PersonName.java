package com.learning.Hibernate_learning;

import javax.persistence.Embeddable;

@Embeddable
public class PersonName 
{
	private String firstName;
	private String middleNmae;
	private String lastName;
	
	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getMiddleNmae() 
	{
		return middleNmae;
	}
	
	public void setMiddleNmae(String middleNmae) 
	{
		this.middleNmae = middleNmae;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "PersonName [firstName=" + firstName + ", middleNmae=" + middleNmae + ", lastName=" + lastName + "]";
	}
}
