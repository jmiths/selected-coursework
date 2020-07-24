package Assessment;

import java.util.ArrayList;

import Interface.Input;
import Interface.Output;
import Question.Question;

/**
 * @author Jacob Smith
 * 
 */
@SuppressWarnings("serial")
public class Test extends Assessment {
	/**
	 * A test is a Graded Assessment
	 */
	public Test() {
		super();
		_graded = true;
		_AssessmentType = 'T';
	}

	/**
	 * A test is constructed with a String title and an ArrayList of questions
	 * 
	 * @param Title
	 * @param questions
	 */
	public Test(String Title, ArrayList<Question> questions)// ,
															// ArrayList<Users>
															// users, Users
															// instructor)
	{
		super(Title, true, 'T', questions);// ,users,instructor);
	}

	/**
	 * This makes a new test, asks the user for a title and calls a menu of
	 * questions
	 * 
	 * @return A made test
	 */
	public Test MakeNewTest() {
		Output out = new Output();
		out.Out("Please enter the title of the test: ");
		Input in = new Input();
		String title = in.in();
		ArrayList<Question> questions = Question.MakeQuestions();
		return new Test(title, questions);
	}

	@Override
	public Test load() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the file of which you wish to load: ");
		String file = in.in();
		Test test = this;
		try {
			test = (Test) in.loadIt(file);
		} catch (Exception e) {
			out.Out("Test not loaded, you told me to load a survey");
		}

		return test;
	}

	@Override
	public void save() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the file of which you wish to save the test to:");
		String file = in.in();
		out.saveIt(file, this);
	}
}
