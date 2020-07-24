package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class Ranking extends OneColumnQuestion {
	/**
	 * Ranking is a one column question that has to be in order to be correct
	 */
	public Ranking() {
		super();
		_correctOutOfOrder = false;
	}

	/**
	 * Ranking can be made with a default question. Options and answers are
	 * still required.
	 * 
	 * @param Options
	 * @param Answers
	 */
	public Ranking(String[] Options, ArrayList<String> Answers) {
		super("Please rank these in order of magnitude", Options, Answers,
				false);
	}

	/**
	 * A ranking question will be a list of options with String question and
	 * answers
	 * 
	 * @param question
	 * @param Options
	 * @param Answers
	 */
	public Ranking(String question, String[] Options, ArrayList<String> Answers) {
		super(question, Options, Answers, false);
	}

	/**
	 * Polymorphic make for ranking, does not require a question. Walks user
	 * through creation
	 * 
	 * @return A made ranking question
	 */
	public static Ranking make() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please type your ranking question or nothing for the default: ");
		String q = in.in();
		out.Out("Please enter in your ranking options seperated by a tilda: '~': ");
		String[] options = in.Token(in.in()); // Options are delimited by ~
		displayOptions(options);
		ArrayList<String> answers = makeAnswer("");
		if (q.equals("")) // If no question, then use default question
		{
			return new Ranking(options, answers);
		} else {
			return new Ranking(q, options, answers);
		}
	}

	@Override
	public Ranking modify() {
		Output out = new Output();
		Input in = new Input();
		changeTitle(this);
		makeOptions(this.getOptions());
		displayOptions(this.getOptions());

		for (String answer : this.getAnswers()) {
			out.Out(answer);
		}
		out.Out("To keep your answer, please press enter: ");
		String test = in.in();
		if (test.equals(""))
			makeAnswer("");

		return this;
	}
	/*
	 * public static String makeAnswer(String op) { Output out = new Output();
	 * Input in = new Input(); if (! op.equals("") ) {
	 * out.Out("Your current answer: " + op);
	 * out.Out("To keep your answer, please press enter: "); String test =
	 * in.in(); if (test.equals("")) return op; }
	 * 
	 * out.Out(
	 * "Please enter the number order that is the correct answer separated by spaces: "
	 * ); String answers = in.in(); return answers; }
	 */
}
