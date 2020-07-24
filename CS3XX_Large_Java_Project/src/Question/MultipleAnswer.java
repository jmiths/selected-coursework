package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public abstract class MultipleAnswer extends Question {
	/**
	 * MultiAnswer is an abstract class that can have multi answers as responses
	 * to its questions and it is gradeable
	 */
	public MultipleAnswer() {
		super();
		_gradeable = true;
	}

	/**
	 * This constuctor takes super calls and passes them off to super, allows
	 * question to be gradeable
	 * 
	 * @param question
	 * @param Options
	 * @param Answers
	 * @param Order
	 */
	public MultipleAnswer(String question, String[] Options,
			ArrayList<String> Answers, boolean Order) {
		super(question, Options, Answers, true, Order);
	}

	/**
	 * Take care of making the options for Multi Answer questions
	 * 
	 * @param op
	 * @return String array of options
	 */
	protected static String[] makeOptions(String[] op) {
		Output out = new Output();
		Input in = new Input();
		if (op != null) {
			out.Out("Your current options are: ");
			for (String option : op) {
				out.Out(option);
			}
			out.Out("To keep these options, type Yes. Else hit any key and enter");
			String test = in.in();
			if (test.equals("Yes"))
				return op;
		}
		out.Out("Please enter in your options seperated by a Tilda: '~': "); // Multi
																				// Choice
																				// has
																				// many
																				// choices,
																				// separate
																				// by
																				// ~
		String[] options = in.Token(in.in());
		displayOptions(options);
		return options;
	}
}
