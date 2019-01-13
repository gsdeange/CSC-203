import java.util.List;
import java.util.ArrayList;

// You may not use any library classes other than List and ArrayList,
// above, to implement your map.  If the string "java." or "javax."
// occurs in this file after this, your submission will be rejected.


// DO NOT MODIFY ANYTHING IN THIS FILE UNTIL THIS LINE!
// Fill in you class after this.  Your submission will be 
// rejected by checkgit and by the autograder
// if you modify anything before this comment block.

/**
 * This class implements a map from any key type K to any value
 * type V.  The K type must have a valid equals() and hashCode() 
 * implementations.  MyMap<K, V> supports a public constructor that
 * takes a single int argument, giving the number of buckets for the
 * internal hashtable.
 * <p>
 * MyMap<K, V> supports a get() operation that takes
 * a key value, and delivers null if the key is not found, or a value
 * of type V (or a descendant of V) if the key is found.  The return type
 * of get() is V.
 * <p>
 * MyMap<K, V> further supports a put() operation that takes a key and a value,
 * in that order.  The value is associated with that key value, replacing any
 * other value that might have been stored at that key.
 * <p>
 * MyMap<K, V> also supports a method called getEntries() that takes no
 * arguments, and returns a List<MyMapEntry<K, V>> containing all of the
 * entries currently in the map.  MyMapEntry<K, V> has public final fields 
 * called "key" and "value".  
 * <p>
 * Finally, MyMap<K, V> supports a debugging method called getBuckets().
 * It delivers a List<List<MyMapEntry<K, V>>>, with one entry for each 
 * bucket in the internal hash table.  Because this is just a debugging
 * method, it's OK to return internal data structures; MyMap<K, V> needen't
 * make a defensive copy.  (A defensive copy is when you make a copy of
 * a data structure and return the copy, so a caller can't modify your
 * internal data structures).
 * <p>
 * All of the above methods are public.
 */

public class MyMap<K, V>
{
	private int size;

	//our map arraylist
	private List<List<MyMapEntry<K, V>>> map;


	public MyMap(int buckets)
	{
		//constructor
		size = buckets;
		map = new ArrayList<List<MyMapEntry<K, V>>>(size); // initialize our arraylist to bucket size

		for(int i = 0; i<size; i++)
		{
			List<MyMapEntry<K, V>> newlist = new ArrayList<>();
			map.add(newlist);
		}

	}
	private int getSize()
	{
		return this.size;
	}

	private int getSpot(K key)
	{
		int kval = key.hashCode()%size;
		if(kval >= 0)
		{
			return kval;
		}
		else
		{
			return kval + size;
		}
	}

	public V get(K key)
	{
		// returns V if found 
		int kval = getSpot(key);
		for(MyMapEntry<K,V> entry : getEntries())
		{
			if(entry.getKey().equals(key))
			{
				return entry.getValue();
			}
		}
		return null;
		//System.out.println("Object: "+ map.get(kval).get(0).getValue());
		/*if(!map.get(kval).isEmpty())
		{
			return map.get(kval).get(0).getValue();
		}
		else
		{
			return null;
		}*/
		
	}

	private MyMapEntry<K, V> getEntry(K key)
	{
		boolean found = false;
		int kval = getSpot(key);
		for(MyMapEntry<K, V> entry : map.get(kval))
		{
			if(entry.getKey().equals(key))
			{
				return entry;
			}
		}
		return null;
	}

	public void put(K key, V value)
	{
		int kval = getSpot(key);

		//List<MyMapEntry<K, V>> entrylist = new ArrayList<>();

		MyMapEntry<K, V> newentry = new MyMapEntry<>(key, value);

		if(get(key) == null)
		{
			map.get(kval).add(newentry);
		}
		else
		{
			getEntry(key).setValue(value);
		}
	}

	public List<MyMapEntry<K, V>> getEntries()
	{
		// return a list of all entries
		List<MyMapEntry<K, V>> entrylist = new ArrayList<>();
		// code here
		for(List<MyMapEntry<K, V>> entrytype : map)
		{
			for(MyMapEntry<K, V> entry : entrytype)
			{
				entrylist.add(entry);
			}
		}
		return entrylist;
	}

	public List<List<MyMapEntry<K, V>>> getBuckets()
	{
		// returns a list of the buckets aka the map which is List<List<MyMapEntry>>>
		return map;

	}



}


