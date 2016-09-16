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

	@Override
	public String toString()
	{
		Calendar cal = Calendar.getInstance(); //defaults to now
		Date now = cal.getTime();
		return "Loan ID: " + String.valueOf(id) + "\nAuthor: " + book.getAuthor() + "\nTitle: " + book.getTitle() + "\nBorrower: " + member.fullName() + "\nBorrowed: " + borrowDate + "\nDue Date: " + dueDate(now);
	}

	public boolean checkOverDue(Date currentDate)
	{
		Date dueDate = dueDate(currentDate);
		if(currentDate.after(dueDate))
		{
			return true;
		}
		return false;
	}

	public Date dueDate(Date currentDate)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(borrowDate);
		c.add(Calendar.DATE, LOAN_PERIOD);
		return c.getTime();
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
