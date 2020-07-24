package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

@SuppressWarnings("serial")
public abstract class TwoColumnQuestion extends MultipleAnswer {
	/**
	 * A two column question is differiated from one column question. It only
	 * passes along a super call
	 */
	public TwoColumnQuestion() {
		super();
	}

	/**
	 * Two column questions are gradeable due to being part of a Multiple Answer
	 * question and have a question, options, answers and may or may not be
	 * correct in any order
	 * 
	 * @param question
	 * @param Options
	 * @param Answers
	 * @param Order
	 */
	public TwoColumnQuestion(String question, String[] Options,
			ArrayList<String> Answers, boolean Order) {
		super(question, Options, Answers, Order);
	}

	@Override
	public ArrayList<String> Exam() {
		Output out = new Output();
		Input in = new Input();
		out.Out(_question);
		// for (int i=0; i<_options.length;i=i+2)
		// {
		// out.Out(i + ": " +_options[i] + "/t/t" + i + " " + _options[i+1]);
		// }
		displayOptions(_options);
		if (this.getCorrectOutOfOrder())
			out.Out("This question is correct in any order");
		else
			out.Out("This question is not correct in any order");
		out.Out("Please enter your response in the form # #<enter>. 'Done' ends entry.");
		ArrayList<String> temp = new ArrayList<String>();
		String answer = "";
		do {
			do {
				// Pattern pattern =
				// Pattern.compile("([0-9]*?~){1,}([0-9]*){1}");
				answer = in.in();
				answer = answer.trim();
				if (answer.matches("([0-9]*?) ([0-9]*)"))
					temp.add(answer);
				else if (answer.equals("Done"))
					break;
				else
					out.Out("Please use the form # #");
			} while (!answer.matches("([0-9]*?) ([0-9]*)"));
		} while (!answer.equals("Done"));
		return temp;
	}
}
