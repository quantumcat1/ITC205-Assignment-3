package library.interfaces.daos;

import java.util.Date;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;

public interface ILoanHelper
{
	public Loan makeLoan(Book book, Member borrower, Date borrowDate, Date dueDate);
}
