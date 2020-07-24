package ResponseSheet;

import java.io.Serializable;
import java.util.ArrayList;

import Assessment.Assessment;
import Interface.Input;
import Interface.Interfaces;
import Interface.Output;

public class ResponseSheet implements Interfaces, Serializable {
	private static final long serialVersionUID = 8364637913003958749L;
	private Assessment assessment;
	// private Users user;
	private ArrayList<ArrayList<String>> responses;

	public ResponseSheet() {
	}

	/**
	 * ResponseSheet is made of the assessment and an ArrayList of answers for
	 * each question
	 * 
	 * @param assess
	 * @param rep
	 */
	public ResponseSheet(Assessment assess, ArrayList<ArrayList<String>> rep) {
		assessment = assess;
		responses = rep;
	}

	/**
	 * @return Assessment that this was taken for
	 */
	public Assessment getAssessment() {
		return assessment;
	}

	/*
	 * public Users getUser() { return user; }
	 */
	/**
	 * @return Responses
	 */
	public ArrayList<ArrayList<String>> getResponses() {
		return responses;
	}

	/*
	 * public String makeResponse(String[] answer) { String temp = ""; for
	 * (String response : answer) { temp += response + " "; } if (temp.length()
	 * > 0) temp = temp.substring(0,temp.length()-1); return temp; }
	 */
	/*
	 * public <T> void save(ArrayList<T> t) { String filename =
	 * "/tmp/Responses.ser"; Output out = new Output(); boolean success =
	 * out.saveIt(filename, t); if (!success) { out.Out(filename +
	 * " was not able to be saved."); } }
	 */
	/*
	 * public <T> ArrayList<T> load() { String filename = "/tmp/Responses.ser";
	 * Output out = new Output(); Input in = new Input(); ArrayList<T>
	 * success;// = new ArrayList<T>(); success = in.loadIt(filename); if
	 * (success.isEmpty()) { out.Out(filename + " was not able to be loaded.");
	 * }
	 * 
	 * return success; }
	 */
	@Override
	public void print() {
		Output out = new Output();
		// out.Out("Respondent: " + user.getName());
		out.Out("Assessment: " + assessment.title());
		for (int i = 0; i < responses.size(); i++) {
			out.Out("Question" + i + ": " + responses.get(i));
		}
	}

	@Override
	public void save() {
		Input in = new Input();
		Output out = new Output();
		String file = assessment.title().split(" ", 2)[0];
		out.Out("Who took this Assessment?");
		String name = in.in();
		file += name;
		out.Out("Responses will be saved to: " + file);
		out.saveIt(file, this);
	}

	@Override
	public ResponseSheet load() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the response sheet of which you wish to load: ");
		String file = in.in();
		ResponseSheet sheet = this;
		try {
			sheet = (ResponseSheet) in.loadIt(file);
		} catch (Exception e) {
			out.Out("Response Sheet not loaded, you told me to load a something else.");
		}
		return sheet;
	}

	/**
	 * Load with the file already known
	 * 
	 * @param open
	 * @return Loaded sheet
	 */
	public static ResponseSheet load(String open) {
		Output out = new Output();
		Input in = new Input();
		ResponseSheet sheet = null;
		try {
			sheet = (ResponseSheet) in.loadIt(open);
		} catch (Exception e) {
			out.Out("Response Sheet not loaded, you told me to load a something else.");
		}
		return sheet;
	}

}
