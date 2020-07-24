package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class MultiChoice extends OneColumnQuestion {

	/**
	 * Multiple Choice can be correct in any order, null constructor makes
	 * without question, options, answers
	 */
	public MultiChoice() {
		super();
		_correctOutOfOrder = true;
	}

	/**
	 * Multiple Choice is made from a question, options, and a String of
	 * answer(s)
	 * 
	 * @param question
	 * @param Options
	 * @param Answers
	 */
	public MultiChoice(String question, String[] Options,
			ArrayList<String> Answers) {
		super(question, Options, Answers, true);
	}

	/**
	 * Polymorphic way to make Multiple Choice question. Guides user through
	 * object creation
	 * 
	 * @return A made multiple choice object
	 */
	public static MultiChoice make() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please type your Multiple Choice question: ");
		String q = in.in();
		String[] a = null;
		String[] options = makeOptions(a);
		String answers = "";
		out.Out("Please enter the correct answers in the form #<enter>... (Start from 0) "); // Prompts
																								// user
																								// to
																								// enter
																								// answer
																								// to
																								// question
		answers = in.in();
		return new MultiChoice(q, options, makeAnswer(answers));
	}

	@Override
	public MultiChoice modify() {
		Output out = new Output();
		changeTitle(this);
		makeOptions(this.getOptions());
		out.Out("Answers are in the form #<enter>....");
		changeAnswer(this);
		return this;
	}
}
