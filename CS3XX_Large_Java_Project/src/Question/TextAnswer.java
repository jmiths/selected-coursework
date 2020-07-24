package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public abstract class TextAnswer extends Question {
	/**
	 * A text answer can be correct in any order. There are no options to choose
	 * from in a text response
	 */
	public TextAnswer() {
		super();
		_options = new String[0];
		_correctOutOfOrder = true;
	}

	/**
	 * Text answers require a question, a String that should be contained and
	 * they could be gradeable. It is up to the derived classes to decide.
	 * 
	 * @param question
	 * @param answers
	 * @param gradeable
	 */
	public TextAnswer(String question, ArrayList<String> answers,
			boolean gradeable) {
		super(question, new String[0], answers, gradeable, true);
	}

	@Override
	public ArrayList<String> Exam() {
		Output out = new Output();
		Input in = new Input();
		out.Out(this.getQuestion());
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(in.in());
		return temp;
	}
}
