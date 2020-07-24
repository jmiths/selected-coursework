package Question;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class Essay extends TextAnswer {
	/**
	 * Essay by default cannot be graded. If null constructed it will not have a
	 * question
	 */
	public Essay() {
		super();
		_gradeable = false;
	}

	/**
	 * The only difference between each essay question is the question itself.
	 * 
	 * @param question
	 */
	public Essay(String question) {
		super(question, null, false);
	}

	/**
	 * A standard way to make questions, only requires the user input a String
	 * 
	 * @return Essay object
	 */
	public static Essay make() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please type your essay question: ");
		String q = in.in();
		return new Essay(q);
	}

	@Override
	public Essay modify() {
		changeTitle(this);
		return this;
	}
}
