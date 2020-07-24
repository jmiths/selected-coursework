package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public abstract class OneColumnQuestion extends MultipleAnswer {
	/**
	 * One column question is differiated from Two Column Question in the
	 * hierarchy
	 */
	public OneColumnQuestion() {
		super();
	}

	/**
	 * This function does not apply additional parameters, but instead passes
	 * super calls up the hierarchy
	 * 
	 * @param question
	 * @param Options
	 * @param Answers
	 * @param Order
	 */
	public OneColumnQuestion(String question, String[] Options,
			ArrayList<String> Answers, boolean Order) {
		super(question, Options, Answers, Order);
	}

	@Override
	public ArrayList<String> Exam() {
		Input in = new Input();
		Output out = new Output();
		out.Out(_question);
		for (int i = 0; i < _options.length; i++) {
			out.Out(i + ": " + _options[i]);
		}
		if (this.getCorrectOutOfOrder())
			out.Out("This question is correct in any order");
		else
			out.Out("This question is not correct in any order");
		out.Out("Please enter your response in the form #<enter>...");
		String answer = "";
		ArrayList<String> temp = new ArrayList<String>();
		do {
			do {
				answer = in.in();
				answer = answer.trim();
				if (in.ValidateInt(answer))
					temp.add(answer);
				else if (answer.equals("Done"))
					break;
				else
					out.Out("Please use the form '#<enter>'. 'Done' will finish entry");
			} while (!in.ValidateInt(answer));
		} while (!answer.equals("Done"));
		return temp;
	}
}
