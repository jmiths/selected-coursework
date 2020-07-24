package Question;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class True_False extends OneAnswer {
	/**
	 * A True/False question will always have true and false as its sole
	 * options. It only takes one answer.
	 */
	public True_False() {
		super();
		_options = PrepOptions();
	}

	/**
	 * True/False requires a question and an answer that is either true or false
	 * 
	 * @param question
	 * @param answers
	 */
	public True_False(String question, ArrayList<String> answers) {
		super(question, PrepOptions(), answers);
	}

	/**
	 * Private method to create a string array containing only True/False
	 * 
	 * @return String[]
	 */
	private static String[] PrepOptions() {
		String[] Options = new String[2];
		Options[0] = "True";
		Options[1] = "False";
		return Options;
	}

	/**
	 * Polymorphic make to create a T/F question
	 * 
	 * @return A made T/F question
	 */
	public static True_False make() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please type your T/F question: ");
		String q = in.in();
		ArrayList<String> answers = makeAns("");
		return new True_False(q, answers);
	}

	private static ArrayList<String> makeAns(String old) {
		Output out = new Output();
		Input in = new Input();
		String answers = "";
		ArrayList<String> answer = new ArrayList<String>();
		if (!(old.equals(""))) {
			out.Out("Your current answers is: " + old
					+ " . To leave it unchanged type Yes, else type anything.");
			answers = in.in();
			if (answers.equals("Yes")) {
				answer.add(old);
				return answer;
			}
		}
		do // Answer must be either True or False
		{
			out.Out("Please enter the correct answer either (False = 0; True = 1)");
			answers = in.in();
			out.Out(answers);
		} while (!(answers.equals("0") || answers.equals("1")));
		answer.add(answers);
		return answer;
	}

	@Override
	public Question modify() {
		changeTitle(this);
		makeAns(this.getAnswers().get(0));
		return this;
	}

}
