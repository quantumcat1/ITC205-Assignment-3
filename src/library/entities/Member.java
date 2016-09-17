package library.entities;

import library.enums.EMemberState;

public class Member extends Entity
{
	public static final float MAX_FINE = 100;
	public static final int MAX_LOANS = 4;
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

	public String fullName()
	{
		return firstName + " " + lastName;
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
		if(fineAmount >= MAX_FINE)
		{
			return true;
		}
		return false;
	}

	public float getFineAmount()
	{
		return fineAmount;
	}

	public void setFineAmount(float fineAmount)
	{
		this.fineAmount = fineAmount;
	}

	public boolean addFine(float fine)
	{
		float newAmount = fineAmount + fine;
		if(newAmount > MAX_FINE || newAmount < 0) //including paying off fines (variable "fine" is negative)
		{
			return false;
		}
		fineAmount = newAmount;
		return true;
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
