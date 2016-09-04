package library.entities;

import library.enums.EBookState;
import library.interfaces.entities.IEntity;


public class Book implements IEntity
{
	private EBookState state;
	private String author;
	private String title;
	private String callNumber;
	private int id;

	public void lose()
	{
		state = EBookState.LOST;
	}

	public void repair()
	{
		//if not damaged, can't repair it
		if(state == EBookState.DAMAGED)
		{
			state = EBookState.ACCEPTABLE;
		}
	}

	public void dispose()
	{
		//can't throw away an acceptable book
		if(state == EBookState.DAMAGED)
		{
			state = EBookState.DISPOSED;
		}
	}

	public EBookState getState()
	{
		return state;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getTitle()
	{
		return title;
	}

	public String getCallNumber()
	{
		return callNumber;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public void setId(int id)
	{
		this.id = id;
	}
}
