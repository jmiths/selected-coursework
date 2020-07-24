package bin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bin {

	public Bin(int size)
	{
		bin_size = size;
		Integer[] data = new Integer[bin_size]; 
		Arrays.fill(data,new Integer(0));
		bin = Arrays.asList(data); // Makes a list of length bin_size of zeroes
	}
	
	public void inc_index(int index) // Quick way to add one to list
	{
		bin.set(index,bin.get(index)+1);
	}
	
	public void inc_by_num(int index, int num) // Quick way to add n to an index (used for summing at the end.)
	{
		bin.set(index,bin.get(index)+num);
	}
	
	public String toString()
	{
		String string_to_return = "";
		for (int element: bin)
		{
			string_to_return = string_to_return + element + " ";
		}
		return string_to_return;
	}
	
	public int get_at_index(int index)
	{
		return bin.get(index);
	}
	
	public int bin_size;
	public List<Integer> bin;

}
