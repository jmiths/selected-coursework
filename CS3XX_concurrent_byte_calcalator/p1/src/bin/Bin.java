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
		bin = Arrays.asList(data); // Make a list of bin_size length filled with zeroes
	}
	
	public void inc_index(int index) // Quick way to add the largest at a position
	{
		bin.set(index,bin.get(index)+1);
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
	
	public int bin_size;
	public List<Integer> bin;

}
