import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.cli.*;

import bin.*;
public class main {
	public static int bins;  // Number of bins from CL Arg
	public static int datasize; // Number of numbers to use from CL Arg
	public static int numthreads; 
	
	public static void main(String[] args) {
		parse_args(args);
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<Bin> results = new ArrayList<Bin>(); // Results to get from threads
		ArrayList<Long> time_to_take_back = new ArrayList<Long>();
		for (int i=0;i<numthreads;i++)
		{
			results.add(new Bin(bins));
			time_to_take_back.add((long) 0);
		}
		int temps_len = 3;
		int chars_per_set = temps_len * bins;
		/* 
		 * Ok so this might seem like a strange way of deriving my stuff, but I have to worry about
		 * hitting the max int for java which is 2.2B and the biggest data set is 700M, since I have 
		 * 4 chars each value simply timesing 700M by 4 would int overflow
		 * 
		 * My goal skip through the file to where I need it.  
		 * I start with a datafile with n numbers in it
		 * I have decided that each temperature is 4 characters long
		 * So there would be n * 4 characters in the file
		 * This is because I can only skip m many characters at a time in reader.skip()
		 * Next, I want to break up all those characters up into some sort of fashion so that each thread does some of the work
		 * So (characters in the file) / (characters in a set) = sets in the file
		 * If I want to find out how much work each thread has to do then I divide by the number of threads
		 * So (sets in file) / numthreads = sets per thread
		 * Therefore, character offset per thread = (sets per thread) * (characters in a set)
		 * However, you may have noticed that the number of characters in a set was both multiplied and divided
		 * If we cancel those out we get:
		 * character offset per thread = ((numbers in file) * (characters per temperature)) / numthreads
		 * However this will exceed the java max int when numbers in file is 700M
		 * To mitigate this I simply divide by numthreads first then mult by the characters per temperature
		 * 
		 * Finally, this doesn't ensure that I always start at the beginning of a set of numbers because the offset per thread
		 * might not be divisible by the chars_per_set for various reasons.  If the bin size is always 7 for my 7X (7K,7M, etc) values
		 * then I can be ensured that offset is valid, but I can't so I won't.  If I run 700M on 3 threads, I lose because it won't
		 * divide evenly, so I make sure the scalar is divisible or add the difference of chars per set and temp to ensure it is
		 * functional.  
		 * 
		 *  At long last I need to dictate the datasize relative to the size of the file.  I need to stop parsing once I hit the end
		 *  Therefore I keep track of how many sets I am going to parse and once I get past the datasize, I stop
		 */
		long startTime = System.nanoTime();
		int offset_scalar = (datasize / numthreads) * temps_len;
		if (offset_scalar % chars_per_set != 0)
		{
			int temp = offset_scalar % chars_per_set;
			offset_scalar = offset_scalar + (chars_per_set - temp);
		}
		int sets_to_end_file = datasize/bins;
		int sets_per_offset = offset_scalar / chars_per_set;
		int offset = -1;
		for(int i=0;i<numthreads;i++)
		{
			if (i+1 == numthreads) // If this is the last iteration, how much do I need to wrap up?
			{
				sets_per_offset = sets_to_end_file;
			}
			Runnable rt = new Thread_work(i,offset_scalar,sets_per_offset,bins,results, time_to_take_back);
			threads.add(new Thread(rt));
			sets_to_end_file -= sets_per_offset;
		}
		
		for(int i=0;i<numthreads;i++)
		{
			threads.get(i).start();
		}
		for(int i=0;i<numthreads;i++)
		{
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Bin result = new Bin(bins);
		for(int i=0;i<numthreads;i++) // Add all the result together
		{
			for(int j=0;j<bins;j++)
			{
				result.inc_by_num(j,results.get(i).get_at_index(j));
			}
		}
		int total_sets = 0;
		for(int i=0;i<bins;i++) // Add all the results together to make sure that I only did the right number of sets
		{
			total_sets += result.get_at_index(i);
		}
		
		
		long endTime = System.nanoTime();
		long max = 0;
		for (int i=0;i<numthreads;i++) // Find max IO time to subtract it from program time... it's something, but nothing like total IO time across all threads
			// No good way to do this...
		{
			long temp = time_to_take_back.get(i);
			if (temp > max)
			{
				max = temp;
			}
		}
		//System.out.println();
		System.out.println("Took "+(endTime - startTime - max)/Math.pow(10,9) + " s");

	}
	public static void parse_args(String[] args) // Standard lib to parse command line args...
	{
		CommandLine commandLine = null;
		Options options = new Options();
		Option option_B = OptionBuilder.withArgName("opt3").hasArg().withDescription("The A option").create("NUMBINS");
        Option option_D = OptionBuilder.withArgName("opt1").hasArg().withDescription("The r option").create("DATASIZE");
        Option option_T = OptionBuilder.withArgName("opt2").hasArg().withDescription("The S option").create("NUMTHREADS");
        options.addOption(option_B);
        options.addOption(option_D);
        options.addOption(option_T);
        
        bins = -1;
        datasize = -1;
        numthreads = -1;
        
        CommandLineParser parser = new GnuParser();
		try {
			commandLine = parser.parse(options, args);
			if (commandLine.hasOption("NUMBINS"))
	        {
	            bins = Integer.parseInt(commandLine.getOptionValue("NUMBINS"));
	        }
			if (commandLine.hasOption("DATASIZE"))
			{
				datasize = Integer.parseInt(commandLine.getOptionValue("DATASIZE"));
			}
			if (commandLine.hasOption("NUMTHREADS"))
			{
				numthreads = Integer.parseInt(commandLine.getOptionValue("NUMTHREADS"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if ((bins == -1) || (datasize == -1) || (numthreads == -1))
		{
			System.out.print("Did not get all the args");
			System.exit(1);
		}
	}
}

