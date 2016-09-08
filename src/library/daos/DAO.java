package library.daos;

import java.util.List;
import java.util.Map;

import library.interfaces.entities.IEntity;


public class DAO <X extends IEntity>
{
	private Map<Integer, X> database;

	public boolean add(X x)
	{
		X testX = database.get(x.getId());
		if(testX == null)
		{
			database.put(x.getId(), x);
			return true;
		}
		return false;
	}

	public X getById(int id)
	{
		return database.get(id);
	}

	public List<X> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
