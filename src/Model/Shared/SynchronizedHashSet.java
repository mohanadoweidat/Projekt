package Model.Shared;

import java.io.Serializable;
import java.util.HashSet;

public class SynchronizedHashSet<V> implements Serializable
{
	private HashSet<V> values;

	public SynchronizedHashSet()
	{
		this.values = new HashSet<>();
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

	public HashSet<V> getValues()
	{
		return values;
	}
}
