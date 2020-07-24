package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public abstract class OneAnswer extends Question {
	// Constructor
	/**
	 * OneAnswer is an abstract class that is both gradeable in correct in any
	 * order due to there only being one answer
	 */
	public OneAnswer() {
		super();
		_gradeable = true;
		_correctOutOfOrder = true;
	}

	/**
	 * One answer takes super calls and passes on super constructors
	 * 
	 * @param question
	 * @param options
	 * @param answers
	 */
	public OneAnswer(String question, String[] options,
			ArrayList<String> answers) {
		super(question, options, answers, true, true);
	}

	/*
	 * protected static String firstAnswer(String answers) {
	 * 
	 * 
	 * first[0] = answers[0];
	 * 
	 * return first; }
	 */
	@Override
	public ArrayList<String> Exam() {
		Output out = new Output();
		Input in = new Input();
		out.Out(_question);
		for (int i = 0; i < _options.length; i++) {
			out.Out(i + ": " + _options[i]);
		}
		String answer = "";
		do {
			out.Out("Please enter one answer by number:");
			answer = in.in();
			answer = answer.trim();
			if (answer.contains(" "))
				out.Out("Please enter only one number");
		} while (answer.contains(" "));
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(answer);
		return temp;
	}
}
