package library.entities;

import java.util.List;

import library.daos.LoanDAO;
import library.enums.EMemberState;

public class Member extends Entity
{
	public static final float MAX_FINE = 100;
	public static final int MAX_LOANS = 5;
	private String firstName;
	private String lastName;
	private float fineAmount;
	private String phoneNumber;
	private String emailAddress;
	private static int counter = 1;

	public Member (String firstName, String lastName, String phoneNumber, String emailAddress)
	{
		super(counter++);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public static void resetCounter()
	{
		counter = 1;
	}

	public static EMemberState checkRestricted(Member member)
	{
		//check whether too many fines (do this one first - quickest)
		if(member.getFineAmount() >= MAX_FINE)
		{
			return EMemberState.RESTRICTED_FINES;
		}
		/*check whether too many loans (not an else because we don't
		 * want to be getting a list if we don't have to)
		 */
		List<Loan> loanList = LoanDAO.getInstance().findLoansByBorrower(member);
		if(loanList.size() >= MAX_LOANS)
		{
			return EMemberState.RESTRICTED_LOANS;
		}
		return EMemberState.NOT_RESTRICTED;
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

	public String getEmailAddress()
	{
		return emailAddress;
	}
}
