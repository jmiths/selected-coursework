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
public class Survey extends Assessment {
	/**
	 * Surveys are the Ungraded Assessments
	 */
	public Survey() {
		super();
		_graded = false;
		_AssessmentType = 'S';
	}

	/**
	 * A survey is made simply by giving it a title and a list of questions
	 * 
	 * @param Title
	 * @param questions
	 */
	public Survey(String Title, ArrayList<Question> questions)// ,
																// ArrayList<Users>
																// users, Users
																// instructor)
	{
		super(Title, false, 'S', questions);// ,users,instructor);
	}

	/**
	 * Asks user to name the survey and calls a menu to make all of the
	 * questions
	 * 
	 * @return The created survey
	 */
	public Survey MakeNewSurvey() {

		Output out = new Output();
		out.Out("Please enter the title of the survey: ");
		Input in = new Input();
		String title = in.in();
		ArrayList<Question> questions = Question.MakeQuestions();
		return new Survey(title, questions);
	}

	@Override
	public Survey load() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the file of which you wish to load: ");
		String file = in.in();
		Survey survey = this;
		try {
			survey = (Survey) in.loadIt(file);
		} catch (Exception e) {
			out.Out("Survey not loaded, you told me to load a test");
		}
		return survey;
	}

	@Override
	public void save() {
		Output out = new Output();
		Input in = new Input();
		out.Out("Please enter the name of the file of which you wish to save the survey to:");
		String file = in.in();
		out.saveIt(file, this);
	}
}
