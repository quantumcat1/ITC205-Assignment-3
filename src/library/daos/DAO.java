package library.daos;

import java.util.HashMap;
import java.util.List;

import library.entities.Entity;


public class DAO <X extends Entity>
{
	private HashMap<Integer, X> database = new HashMap<Integer, X>();

	public X add(X x)
	{
		X testX = database.get(x.getId());
		if(testX == null)
		{
			database.put(x.getId(), x);
		}
		return x;
	}

	public void add(List<X> list)
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

}
