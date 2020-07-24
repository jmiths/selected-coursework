import java.io.*;
import java.util.ArrayList;

import bin.Bin;


public class Thread_work implements Runnable {
	private int num_thread; // What thread am I
	private int offset_scalar; // How chars are in each thread set
	private int sets_to_read; // How many sets do I actually read
	private int bins; // How many bins do I have
	private volatile Bin bin; // Result storage
	private ArrayList<Bin> results; // Global results
	private ArrayList<Long> time_to_take_back; // IO time
	public Thread_work(int num_thread,int offset_scalar, int sets_to_read, int bins, ArrayList<Bin> results, ArrayList<Long> time_to_take_back) {
		this.num_thread = num_thread;
		this.offset_scalar = offset_scalar;
		this.sets_to_read = sets_to_read;
		this.bins = bins;
		this.bin = new Bin(bins);
		this.results = results;
		this.time_to_take_back = time_to_take_back;
	}
	public void run() {
		try {
			long tempTime = 0; // How long has IO taken?
			long thread_start = System.nanoTime(); // How long does this thread take
			Reader reader = new InputStreamReader(new FileInputStream("blah"), "US-ASCII"); // Symlink to a data file
			for(int i = 0; i < num_thread; i++) // Skip number of chars * Thread NUM
			{
				reader.skip(offset_scalar);
			}
			char[] chars = new char[3*bins]; // Read three chars (didn't feel like passing in len of a val) * bin len
			int charsRead = -1;
			while ((sets_to_read > 0) && ((charsRead = read(reader, chars)) != -1))  { // Read until done or done with sets
				long startTime = System.nanoTime(); // Io tracker
				String output = new String(chars); // String comes in as char array
			    String[] output2 = output.split(" ");
			    long endTime = System.nanoTime();
			    int pos = 0;
			    String max = output2[0];
			    for(int i=1;i<bins;i++)
			    {
			    	if (max.compareTo(output2[i]) < 0) // Find biggest
			    	{
			    		pos = i;
			    		max = output2[i];
			    	}
			    }
			    bin.inc_index(pos); // Increment bin at pos that was biggest
			    sets_to_read--;
			    
				tempTime = tempTime + (endTime - startTime); // Io time
				
			}
			long thread_stop = System.nanoTime();  // Thread time calculations for IO
			long thread_time = thread_stop - thread_start;
			long thread_without_process = thread_time - tempTime;
			time_to_take_back.set(num_thread, thread_without_process);
			results.set(num_thread,bin);
			//System.out.println(bin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static int read(Reader reader, char[] chars) throws IOException {
	    return reader.read(chars);
	}
}
