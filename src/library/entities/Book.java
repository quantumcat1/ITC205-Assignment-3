package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

//just a little change to make sure I can push to the repository properly
//extra comment to change it further

public class Book implements IBook
{
	public void borrow(ILoan loan)
	{

	}

	public ILoan getLoan()
	{
		return null;
	}

	public void returnBook(boolean damaged)
	{

	}

	public void lose()
	{

	}

	public void repair()
	{

	}

	public void dispose()
	{

	}

	public EBookState getState()
	{
		return null;
	}

	public String getAuthor()
	{
		return null;
	}

	public String getTitle()
	{
		return null;
	}

	public String getCallNumber()
	{
		return null;
	}

	public int getId()
	{
		return 1;
	}
}
