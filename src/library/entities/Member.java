package library.entities;

//import java.util.List;

import library.enums.EMemberState;

//TODO: finish implementing this class

public class Member extends Entity
{
	private String firstName;
	private String lastName;
	private float fineAmount;
	private String phoneNumber;
	private String emailAddress;
	private EMemberState state;
	private static int counter = 0;

	public Member (String firstName, String lastName, String phoneNumber, String emailAddress)
	{
		super(counter++);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public boolean hasFinesPayable()
	{
		if(fineAmount > 0)
		{
			return true;
		}
		return false;
	}

	public boolean hasReachedFineLimit()
	{
		//TODO: implement a max fine info somewhere
		return false;
	}

	public float getFineAmount()
	{
		return fineAmount;
	}

	//TODO: finish this after max fine amount info is added somewhere
	public boolean addFine(float fine)
	{
		float newAmount = fineAmount + fine;
		//check if exceeds fine limit and return false if it does, otherwise:
		fineAmount = newAmount;
		return true;
	}

	public boolean payFine(float payment)
	{
		if(payment <= fineAmount)
		{
			fineAmount -= payment;
			return true;
		}
		return false;
	}

	public EMemberState getState()
	{
		return state;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String  getEmailAddress()
	{
		return emailAddress;
	}
}
