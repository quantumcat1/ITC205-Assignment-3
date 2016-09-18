package library.daos;

import java.util.HashMap;
import java.util.List;

import library.entities.Entity;


public class DAO <X extends Entity>
{
	protected HashMap<Integer, X> database = new HashMap<Integer, X>();


	public boolean add(X x)
	{
		X testX = database.get(x.getId());
		if(testX == null) //don't add more than one of the same thing
		{
			database.put(x.getId(), x);
			return true;
		}
		return false;
	}

	public void add(List<X> list)
	{
		for(X x: list)
		{
			add(x);
		}
	}

	public void add(X[] list)
	{
		for(X x: list)
		{
			add(x);
		}
	}

	public X getById(int id)
	{
		return database.get(id);
	}

	public List<X> list()
	{
		return null;
	}

	public void clear()
	{
		database.clear();
	}
}
