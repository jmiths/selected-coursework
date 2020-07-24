package Interface;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Output {
	/**
	 * Output provides an empty object so that the methods contained within may
	 * be accesed
	 */
	public Output() {
	}

	/**
	 * Abstraction of output, says everything that used to be printed.
	 * 
	 * @param out
	 */
	public void Out(String out) {
		String voiceName = "kevin16";
        
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice(voiceName);

        voice.allocate();
        voice.speak(out);
        voice.deallocate();
		//System.out.println(out);
	}

	/**
	 * Redirects to regular out
	 * 
	 * @param out
	 */
	public void OutSameLine(String out) {
		//System.out.print(out);
		Out(out);
	}

	/**
	 * Takes in a filename and an object t and saves it to the specified place.
	 * 
	 * @param filename
	 * @param t
	 * @return Boolean whether or not it was able to accomplish
	 */
	public boolean saveIt(String filename, Object t) {
		File savelocation = new File(filename);
		if (!savelocation.exists()) // Make file if it doesn't exist
		{
			try {
				savelocation.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}

		FileOutputStream fileout;

		try { // Open file to write to
			fileout = new FileOutputStream(filename);
		} catch (FileNotFoundException e1) {
			return false;
		}

		ObjectOutputStream objectOut;

		try { // Writes object to the file
			objectOut = new ObjectOutputStream(fileout);
			objectOut.writeObject(t);
			objectOut.close();
			fileout.close();
		} catch (IOException e) {
			return false;
		}

		return true; // Only returns true if everything was able to be done
	}

	/**
	 * Takes in a String[] array and converts it to a String
	 * 
	 * @param array
	 * @return String
	 */
	public String Array2String(String[] array) {
		String temp = "";
		for (String string : array) {
			temp += string + " ";
		}
		if (temp.length() > 0)
			temp = temp.substring(0, temp.length() - 1);
		return temp;
	}

}
