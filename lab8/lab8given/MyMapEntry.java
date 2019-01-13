public class MyMapEntry<K, V> 
{
	public K key; 
	public V value; 

	public MyMapEntry(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	public K getKey()
	{
		return this.key;
	}
	public V getValue()
	{
		return this.value;
	}
	public void setValue(V newval)
	{
		this.value = newval;
	}
	public String toString()
	{
		return ("MapEntry(" + getKey() + ", " + getValue() + ")");
	}

} 