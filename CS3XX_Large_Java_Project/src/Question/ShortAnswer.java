package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class ShortAnswer extends TextAnswer {

	/**
	 * Short Answer is a gradeable text question
	 */
	public ShortAnswer() {
		super();
		_gradeable = true;
	}

	/**
	 * Short answer requires both a question and a string of answers that must
	 * appear in the question. These are broken down upon grading to be indivual
	 * words
	 * 
	 * @param question
	 * @param answers
	 */
	public ShortAnswer(String question, ArrayList<String> answers) {
		super(question, answers, true);
	}

	/**
	 * Polymorphic make, guides user through making a Short Answer
	 * 
	 * @return Made Short Answer question
	 */
	public static ShortAnswer make() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please type your short answer question: ");
		String q = in.in();
		out.Out("Please type the words that must be included in the response (One word at a time): ");
		String answers = in.in();
		return new ShortAnswer(q, makeAnswer(answers));
	}

	@Override
	public ShortAnswer modify() {
		changeTitle(this);
		changeAnswer(this);
		return this;
	}
}
