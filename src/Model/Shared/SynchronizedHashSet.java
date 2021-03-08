package Model.Shared;

import java.util.HashSet;

public class SynchronizedHashSet<V>
{
	private HashSet<V> values;

	public SynchronizedHashSet(HashSet<V> values)
	{
		this.values = values;
	}

	public synchronized void add(V val)
	{
		values.add(val);
	}

	public synchronized V get(V val)
	{
		for (V v : values)
		{
			if (v == val)
			{
				return v;
			}
		}
		return null;
	}

	public synchronized void remove(V val)
	{
		values.remove(val);
	}


}
