package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class Matching extends TwoColumnQuestion {

	/**
	 * Matching is a muli answer question that is correct out of order
	 */
	public Matching() {
		super();
		_correctOutOfOrder = true;
	}

	/**
	 * Matching without a question will default to a generic "Match" question
	 * 
	 * @param Options
	 * @param Answers
	 */
	public Matching(String[] Options, ArrayList<String> Answers) {
		super("Match items in Column 1 with Column 2 \n Column 1 \t\t Column2",
				Options, Answers, true);
	}

	/**
	 * Matching takes a question, the options, and the answers
	 * 
	 * @param question
	 * @param Options
	 * @param Answers
	 */
	public Matching(String question, String[] Options, ArrayList<String> Answers) {
		super(question, Options, Answers, true);
	}

	/**
	 * This is the polymorphic make call that prompts the user to create a
	 * matching question
	 * 
	 * @return A made matching question
	 */
	public static Matching make() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please type your matching question or nothing for the default: ");
		String q = in.in();
		out.Out("Please use the form column1~column2~column1");
		String[] a = null;
		String[] op = makeOptions(a);
		op = toColumns(op);
		displayOptions(op);
		out.Out("Please enter two numbers that match the correct answer (# #<enter>...): ");
		String answers = in.in();
		if (q.equals("")) // Did the user want the default question or not
		{
			return new Matching(op, makeAnswer(answers));
		} else {
			return new Matching(q, op, makeAnswer(answers));
		}
	}

	@Override
	public Matching modify() {
		Output out = new Output();
		changeTitle(this);
		out.Out("Please use the form column1~column2~column1");
		makeOptions(this.getOptions());
		this.setOptions(toColumns(this.getOptions()));
		out.Out("Please use the form ## ## ##..., answer in column one; answer in column 2");
		changeAnswer(this);
		return this;
	}

	private static String[] toColumns(String[] opt) {
		ArrayList<String> optionsMade = new ArrayList<String>();
		int count = 0;
		String temp = "";
		for (String option : opt) // Concats every other option together to make
									// two columns
		{
			if (count % 2 == 0) // Will cut off odd numbered options, will
								// always have two full columns
				temp = option;
			else
				optionsMade.add(temp + "\t\t" + option);
			count++;
		}
		String[] op = optionsMade.toArray(new String[optionsMade.size()]); // Makes
																			// ArrayList
																			// back
																			// into
																			// String[]
		return op;
	}

}
