package library.entities;

import java.util.Calendar;
import java.util.Date;

import library.entities.Book;
import library.entities.Member;


public class Loan extends Entity
{
	public static final int LOAN_PERIOD = 14;
	private Member member;
	private Book book;
	private int id;
	private Date borrowDate;
	private static int counter = 0;

	public Loan(Member member, Book book, Date borrowDate)
	{
		super(counter++);
		this.member = member;
		this.book = book;
		this.borrowDate = borrowDate;
	}

	public boolean checkOverDue(Date currentDate)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(borrowDate);
		c.add(Calendar.DATE, LOAN_PERIOD);
		Date dueDate = c.getTime();
		if(currentDate.after(dueDate))
		{
			return true;
		}
		return false;
	}

	public Member getMember()
	{
		return member;
	}

	public Book getBook()
	{
		return book;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
