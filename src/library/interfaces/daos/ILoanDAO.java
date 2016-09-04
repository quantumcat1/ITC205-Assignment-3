package library.interfaces.daos;

import java.util.Date;
import java.util.List;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;

public interface ILoanDAO
{
	public Loan createLoan(Member borrower, Book book);

	public void commitLoan(Loan loan);

	public Loan getLoanById(int id);

	public Loan getLoanByBook(Book book);

	public List<Loan> listLoans();

	public List<Loan> findLoansByBorrower(Member borrower);

	public List<Loan> findLoansByBookTitle(String title);

	public void updateOverDueStatus(Date currentDate);

	public List<Loan> findOverDueLoans();
}

