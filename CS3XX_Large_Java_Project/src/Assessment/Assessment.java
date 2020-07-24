package Assessment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

import Interface.Input;
import Interface.Interfaces;
import Interface.Output;
import Question.*;
import ResponseSheet.ResponseSheet;

/**
 * @author Jacob Smith
 * 
 * 
 */

public abstract class Assessment implements Interfaces, Serializable {

	private static final long serialVersionUID = -3781638319797696061L;
	protected String _title;
	protected boolean _graded;
	protected char _AssessmentType;
	protected ArrayList<Question> _questions;

	// protected ArrayList<Users> _users;
	// protected Users _Instructor;

	public Assessment() {
	}

	/**
	 * Abstract class and is thus never constructed, derived classes use it
	 * 
	 * @param title
	 * @param graded
	 * @param Type
	 * @param questions
	 */
	public Assessment(String title, boolean graded, char Type,
			ArrayList<Question> questions)// , ArrayList<Users> users, Users
											// instructor)
	{

		_title = title;
		_graded = graded;
		_AssessmentType = Type;
		_questions = questions;

	}

	// _users = users;
	// _Instructor = instructor;

	// Inspectors
	/**
	 * @return Title of Assessment
	 */
	public String title() {
		return _title;
	}

	/**
	 * @return Whether the Assessment is graded or not
	 */
	public boolean isGraded() {
		return _graded;
	}

	/**
	 * @return Type of the Assessment
	 */
	public char getType() {
		return _AssessmentType;
	}

	/*
	 * public ArrayList<Users> getRespondants() { return _users; }
	 */
	/**
	 * @return List of questions that are in this assessment
	 */
	public ArrayList<Question> getQuestions() {
		return _questions;
	}

	/*
	 * public Users getInstructor() { return _Instructor; }
	 */
	// Mutators

	/**
	 * Allows for the Assessment to be renamed, currently not accessible with
	 * driver
	 * 
	 * @param title
	 */
	public void rename(String title) {

		_title = title;
	}

	/*
	 * public boolean changeInstructor(Users user) { if (user.isAdmin()) {
	 * _Instructor = user; return true; } return false;
	 * 
	 * }
	 */
	/**
	 * Adds a question to the Assessment, will work with driver at a future date
	 * 
	 * @param question
	 */
	public void AddQuestion(Question question) {
		_questions.add(question);
	}

	/**
	 * Removes question from the Assessment, driver will access in future
	 * 
	 * @param question
	 */
	public void RemoveQuestion(Question question) {
		_questions.remove(question);
	}

	/*
	 * public void AddUser(Users user) { _users.add(user); }
	 * 
	 * public void RemoveUser(Users user) { _users.remove(user); }
	 */
	/*
	 * public <T> void save(ArrayList<T> t) { String filename =
	 * "/tmp/Assessment.ser"; Output out = new Output(); boolean success =
	 * out.saveIt(filename, t); if (!success) { out.Out(filename +
	 * " was not able to be saved."); } }
	 */
	/*
	 * public <T> ArrayList<T> load() { String filename = "/tmp/Assessment.ser";
	 * Output out = new Output(); Input in = new Input(); ArrayList<T>
	 * success;// = new ArrayList<T>(); success = in.loadIt(filename); if
	 * (success.isEmpty()) { out.Out(filename + " was not able to be loaded.");
	 * }
	 * 
	 * return success; }
	 */
	@Override
	public void print() {
		Output out = new Output();
		out.Out("Title: " + _title);
		out.Out("Graded? : " + _graded);
		out.Out("Assessment Type: " + _AssessmentType);
		// out.Out("Instructor: " + _Instructor.getName());
		for (int i = 0; i < _questions.size(); i++) {
			out.OutSameLine("Question" + i + ": ");
			(_questions.get(i)).print();
			out.Out("");
		}
	}

	/**
	 * Take allows the assessment regardless of its type to be taken with a
	 * standard method
	 * 
	 * 
	 */
	public void take() {
		Output out = new Output();
		ArrayList<ArrayList<String>> Responses = new ArrayList<ArrayList<String>>();
		out.Out("Title: " + _title);
		if (_graded)
			out.Out("This Assessment is graded.");
		else
			out.Out("This Assessment is not graded.");
		for (int i = 0; i < _questions.size(); i++) {
			Question current = _questions.get(i);
			ArrayList<String> temp = new ArrayList<String>();
			out.OutSameLine("Question" + i + ": ");
			temp = current.Exam();
			Responses.add(temp);
		}
		ResponseSheet sheet = new ResponseSheet(this, Responses);
		sheet.save();
	}

	/**
	 * Allows a survey or test to be completely modified
	 * 
	 * @return modified assessment
	 */
	public Assessment modify() {
		Input in = new Input();
		Output out = new Output();
		ArrayList<String> questions = this.listquestions();
		int choice = 0;
		do {
			out.Out("Which Question would you like to modify?");
			choice = in.MenuSelect(questions);
			Question chosen = this._questions.get(choice);
			chosen = chosen.modify();
			this._questions.set(choice, chosen);
		} while (choice != questions.size() - 1);

		return this;
	}

	/**
	 * Generates a list of questions so that they can be read without the
	 * options
	 * 
	 * @return list of questions
	 */
	public ArrayList<String> listquestions() {
		ArrayList<String> questions = new ArrayList<String>();
		for (Question question : this._questions) {
			questions.add(question.getQuestion());
		}
		questions.add("Done");
		return questions;
	}

	/**
	 * Tabulates the results and prints them out.
	 */
	public void tabulate() {
		Output out = new Output();
		Input in = new Input();

		ArrayList<ResponseSheet> tabs = new ArrayList<ResponseSheet>();

		out.Out("Please type the file names for the response sheets you would like to tabulate");
		out.Out("Type 'Done' when finished");
		String file = "";
		do // Loads all the ResponseSheets, bounces if it does not have the same
			// assessment
		{
			file = in.in();
			if (file.equals("Done"))
				break;
			ResponseSheet temp = ResponseSheet.load(file);
			if (temp.getAssessment().title().equals(this.title()))
				tabs.add(temp);
			else
				out.Out("You tried to load a response sheet for different assessment");
		} while (!file.equals("Done"));

		int count = 0;
		for (Question question : this.getQuestions()) // For every question
		{
			out.Out(question.getQuestion());
			HashMap<String, Integer> responses = new HashMap<String, Integer>();
			for (ResponseSheet sheet : tabs) // For every response
			{
				ArrayList<String> current = sheet.getResponses().get(count);
				for (String answer : current) // For every answer
				{
					if (question instanceof ShortAnswer) // Short answer must be
															// tokenized
					{
						String[] ans = in.Tokenize(answer);
						for (String word : ans) {
							if (responses.containsKey(word))
								responses.put(word, responses.get(word) + 1);
							else
								responses.put(word, 1);
						}
					} // Hash them and increment a counter if the entry already
						// exists
					else {
						if (responses.containsKey(answer))
							responses.put(answer, responses.get(answer) + 1);
						else
							responses.put(answer, 1);
					}
				}
			}
			Question.displayOptions(question.getOptions()); // What were the
															// original options?
			for (String key : responses.keySet()) // Print out all the different
													// responses
			{
				out.Out(key + " - " + responses.get(key));
			}
			out.Out("");
			count++;
		}
		out.Out("Enter when ready");
		in.in();

	}

}
