package Model.Shared;

import java.io.Serializable;
import java.util.HashSet;

/**
 * This class used for saving unsent messages from offline users.
 * @param <V> The generic type of the synchronizedHashSet.
 */
public class SynchronizedHashSet<V> implements Serializable
{
	private HashSet<V> values;

	/**
	 * Constructor.
	 */
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
