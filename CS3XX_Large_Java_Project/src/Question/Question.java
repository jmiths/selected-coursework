package Question;

import java.io.Serializable;
import java.util.ArrayList;

import Interface.Input;
import Interface.Interfaces;
import Interface.Output;

/**
 * @author Jacob Smith
 * 
 */
public abstract class Question implements Interfaces, Serializable {
	private static final long serialVersionUID = 3015750033215911482L;
	protected String _question;
	protected String[] _options;
	protected ArrayList<String> _Correct_Answers;
	protected boolean _gradeable;
	protected boolean _correctOutOfOrder;

	abstract public Question modify();

	abstract public ArrayList<String> Exam();

	// Constructor
	/**
	 * Question is the root level abstract class for the question package. The
	 * null constructor adds no additional parameters, but null constructing
	 * derived methods may result in some attributes due to the nature of the
	 * derivation
	 */
	public Question() {

	}

	/**
	 * A question is made of a question, options, answers. Whether or not it is
	 * gradeable and if it is correct out of order. It is made through by super
	 * calls from derived functions
	 * 
	 * @param question
	 * @param options
	 * @param answers
	 * @param gradeable
	 * @param Order
	 */
	public Question(String question, String[] options,
			ArrayList<String> answers, boolean gradeable, boolean Order) {
		_question = question;
		_options = options;
		_Correct_Answers = answers;
		_gradeable = gradeable;
		_correctOutOfOrder = Order;
	}

	// Mutators
	/**
	 * A question may be changed after the question has been made
	 * 
	 * @param question
	 */
	public void setQuestion(String question) {
		_question = question;
	}

	/**
	 * The options of a question may change should they be provided
	 * 
	 * @param options
	 */
	public void setOptions(String[] options) {
		_options = options;
	}

	/**
	 * The answer for a question may be changed after the creation
	 * 
	 * @param answer
	 */
	public void setAnswer(ArrayList<String> answer) {
		_Correct_Answers = answer;
	}

	// Inspectors
	/**
	 * A question may be retrieved and viewed alone
	 * 
	 * @return String
	 */
	public String getQuestion() {
		return _question;
	}

	/**
	 * The options may be viewed indivually
	 * 
	 * @return String[]
	 */
	public String[] getOptions() {
		return _options;
	}

	/**
	 * The answer can be view
	 * 
	 * @return String
	 */
	public ArrayList<String> getAnswers() {
		return _Correct_Answers;
	}

	/**
	 * A question can be get for gradeable
	 * 
	 * @return boolean
	 */
	public boolean getGradeable() {
		return _gradeable;
	}

	/**
	 * A question may be checked if it is correct out of order
	 * 
	 * @return boolean
	 */
	public boolean getCorrectOutOfOrder() {
		return _correctOutOfOrder;
	}

	@Override
	public Question load() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the file of which you wish to load: ");
		String file = in.in();
		Question question = (Question) in.loadIt(file);
		return question;
	}

	@Override
	public void save() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the file of which you wish to save the question to:");
		String file = in.in();
		out.saveIt(file, this);
	}

	@Override
	public void print() {
		Output out = new Output();
		out.Out("Question: " + _question);
		int count = 0;
		for (String option : _options) {
			out.Out(count + ".) " + option);
			count++;
		}
		out.Out("Answer(s): " + (_Correct_Answers));
		out.Out("Gradeable?: " + _gradeable);
		out.Out("Correct out of Order: " + _correctOutOfOrder);
	}

	/**
	 * This method is the second menu that creates the questions that are needed
	 * in Assessments. The user can continue to create questions until the
	 * Assessment is done at which point they are returned to finish making the
	 * assessment.
	 * 
	 * @return ArrayList<Question>
	 */
	public static ArrayList<Question> MakeQuestions() {
		Input in = new Input();
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<String> menuChoices = new ArrayList<String>();
		menuChoices.add("Add a New T/F Question");
		menuChoices.add("Add a New Multiple Choice Question");
		menuChoices.add("Add a New Short Answer Question");
		menuChoices.add("Add a New Essay Question");
		menuChoices.add("Add a New Ranking Question");
		menuChoices.add("Add a New Matching Question");
		menuChoices.add("Finish");
		int choice = -100; // Impossible Choice in case of failure
		do {
			choice = in.MenuSelect(menuChoices); // Menu presented
			switch (choice) // Questions are made and added to the list for the
							// assessment
			{
			case 0:
				questions.add(True_False.make());
				break;
			case 1:
				questions.add(MultiChoice.make());
				break;
			case 2:
				questions.add(ShortAnswer.make());
				break;
			case 3:
				questions.add(Essay.make());
				break;
			case 4:
				questions.add(Ranking.make());
				break;
			case 5:
				questions.add(Matching.make());
				break;
			}
		} while (choice < 6 && choice >= 0); // Continue while in range

		return questions;
	}

	public void changeTitle(Question question) {
		Output out = new Output();
		Input in = new Input();
		out.Out("Your Current Question: " + question._question);
		out.Out("To keep this question press enter, to change it: type a new question: ");
		String q = in.in();
		if (!(q.equals("")))
			this.setQuestion(q);
	}

	public void changeAnswer(Question question) {
		Output out = new Output();
		Input in = new Input();
		out.Out("Your Current Answers: " + question._Correct_Answers);
		out.Out("To keep these answers press enter, to change it: type a new set of answers: ");
		String a = in.in();
		if (!(a.equals("")))
			this.setAnswer(makeAnswer(a));

	}

	protected static ArrayList<String> makeAnswer(String a) {
		ArrayList<String> answers = new ArrayList<String>();
		Input in = new Input();
		Output out = new Output();
		out.Out("Please type 'Done' when you have finished with your answers");
		if (!(a.equals("")))
			answers.add(a);
		String temp = "";
		while (!temp.equals("Done")) {
			temp = in.in();
			if (!temp.equals("Done"))
				answers.add(temp);
		}
		return answers;

	}

	public static void displayOptions(String[] options) {
		Output out = new Output();
		int count = 0;
		for (String prin : options) // Options are displayed in order for the
									// user to answer their correct answer
									// correctly
		{
			out.Out(count + "). " + prin);
			count++;
		}
	}
}
