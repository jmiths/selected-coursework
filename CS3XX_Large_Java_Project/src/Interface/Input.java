package Interface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * @author Jacob Smith
 * 
 */
public class Input {
	/**
	 * Input exists only to provide helper functions that involve input
	 */
	public Input() {
	}

	/**
	 * This is the method by which all inputs in the program are done
	 * 
	 * @return String that the user input
	 */
	public String in() {
		String s = "";
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			s = bufferRead.readLine();
		} catch (IOException e) {
			return "%454!#^76"; // Unlikely to be input. Saves program from
								// crashing.
		}
		return s;
	}

	/**
	 * Helper function to split on space, sparsely used, if at all anymore,
	 * unlikely it does what it was intended to do
	 * 
	 * @param temp
	 * @return A string array from a string split on space
	 */
	public String[] Tokenize(String temp) {
		return temp.split(" ");
	}

	/**
	 * I decided to Tokenize my input by prompting the user to enter Tilda
	 * whenever they needed to separate inputs. Its a cheap way of doing it.
	 * 
	 * @param temp
	 * @return String array from string split on Tilda
	 */
	public String[] Token(String temp) {
		return temp.split("~");
	}

	/**
	 * Generic Object Loader, All load methods come here. Takes in the filename
	 * to load from Will still return on failure, but object will return null
	 * object
	 * 
	 * @param filename
	 * @return The object from the file it was given
	 */
	public Object loadIt(String filename) {
		Output out = new Output();
		Object loaded = null;
		try {
			FileInputStream streamIn = new FileInputStream(filename);
			ObjectInputStream objectIn = new ObjectInputStream(streamIn);
			loaded = objectIn.readObject();
		} catch (Exception e) {
			out.Out("LOAD FAILED!!!!!!!");
		}
		return loaded;
	}

	/**
	 * Parses int, not directly used, but through other functions
	 * 
	 * @param s
	 * @return String -> int
	 */
	public int toInt(String s) {
		return Integer.parseInt(s);
	}

	/**
	 * Sees if String is an int without parsing
	 * 
	 * @param s
	 * @return boolean to show whether or not it is an int
	 */
	public boolean ValidateInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Takes a String s and a String[] proper and checks through proper to
	 * insure that s is contained. Will get more use when user input for
	 * Assessments is accepted
	 * 
	 * @param s
	 * @param proper
	 * @return Whether or not the string is allowable
	 */
	public boolean ValidateString(String s, String[] proper) {
		boolean found = false;
		for (String good : proper) {
			if (good.equals(s))
				found = true;
		}
		return found;
	}

	/**
	 * Validates whether or not i is between min and max
	 * 
	 * @param i
	 * @param min
	 * @param max
	 * @return boolean showing whether not or not it is in range
	 */
	public boolean ValidateRange(int i, int min, int max) {
		if (min <= i && i <= max)
			return true;
		else
			return false;
	}

	/**
	 * Takes a single argument -> the length of something such as a menu, calls
	 * successive function to fill out the request
	 * 
	 * @param length
	 * @return The choice the user made for the range
	 */
	public int getChoice(int length) {
		return getChoice(length, length - 1);
	}

	/**
	 * Lets the driver truncate the range as to lessen allowed menu options with
	 * the same menu. Passes to next function for input
	 * 
	 * @param length
	 * @param max
	 * @return User Choice
	 */
	public int getChoice(int length, int max) {
		return getChoice(length, max, 0);
	}

	/**
	 * Function that allows both a min and max for the range to be input, runs
	 * the menu selections and other number input
	 * 
	 * @param length
	 * @param max
	 * @param min
	 * @return User Choice in range
	 */
	public int getChoice(int length, int max, int min) {
		int choice = -100;
		Output out = new Output();
		do {
			out.Out("Please enter your choice " + Integer.toString(min) + " - "
					+ Integer.toString(max) + ": ");
			String temp = in();
			if (ValidateInt(temp)) // If user did not enter an Int, then make
									// them
				choice = toInt(temp);
			else
				out.Out("Please enter an Integer.");
			if (!ValidateRange(choice, min, max)) // If failed, tell user why
				out.Out("Please enter an Integer in Range.");
		} while (!ValidateRange(choice, min, max));

		return choice;
	}

	/**
	 * This function takes a list of menu options and display the menu
	 * 
	 * @param options
	 * @return User Choice in the Menu
	 */
	public int MenuSelect(ArrayList<String> options) {
		int count = 0;
		Output out = new Output();
		for (String option : options) {
			out.Out(count + ".) " + option);
			count++;
		}
		return getChoice(options.size());
	}
}