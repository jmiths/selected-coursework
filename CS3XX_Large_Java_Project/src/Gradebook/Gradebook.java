package Gradebook;

import java.util.ArrayList;

import Assessment.Assessment;
import Interface.Input;
import Interface.Output;
import Question.Question;
import ResponseSheet.ResponseSheet;

/**
 * @author Jacob Smith
 * 
 */
public class Gradebook {
	protected Assessment assessment;
	// protected Users user;
	// protected boolean Aggr;
	protected double percentCorrect;
	protected int numberOfEssay;
	protected int numberOtherQ;

	/**
	 * Gradebook is a temporary object that takes care of all the grading for
	 * the tests, it is destroyed once completed
	 */
	public Gradebook() {

	}

	/**
	 * Gradebook is made with a key and a list of responses. These are used to
	 * make the remaining attributes.
	 * 
	 * @param key
	 * @param responses
	 */
	public Gradebook(Assessment key, ResponseSheet responses) {
		this.assessment = key;
		ArrayList<Question> questions = key.getQuestions();
		this.numberOfEssay = this.getNumEssay(questions);
		this.numberOtherQ = this.getNumOtherQ(questions);
		this.percentCorrect = (this.Compare2Correct(responses.getResponses(),
				questions) * 100);
	}

	/**
	 * Grade handles loading the ResponseSheet to grade and calling the
	 * constructor. This is the way the object is made. It also handles output
	 * of attributes.
	 * 
	 * @param key
	 */
	public static void Grade(Assessment key) {
		Output out = new Output();
		Input in = new Input();
		ResponseSheet sheet = new ResponseSheet();
		do {
			sheet = sheet.load();
			if (!key.title().equals(sheet.getAssessment().title()))
				out.Out("Assessments do not match, please tell me a different sheet to grade");
		} while (!key.title().equals(sheet.getAssessment().title()));

		Gradebook temp = new Gradebook(key, sheet);
		out.Out("You scored: " + temp.percentCorrect + "%");
		out.Out("There were " + temp.numberOtherQ + " gradeable questions");
		out.Out("\nEnter when done");
		in.in();
	}

	/**
	 * This handles the evaluation of the text Strings and checks if they are
	 * all right. It calls the correct methods to check against the key.
	 * 
	 * @param response
	 * @param question
	 * @return percent correct
	 */
	private double Compare2Correct(ArrayList<ArrayList<String>> response,
			ArrayList<Question> question) {
		double numcorrect = 0.0;
		for (int i = 0; i < response.size(); i++) {
			ArrayList<String> UserAnswer = response.get(i);
			ArrayList<String> KeyAnswer = question.get(i).getAnswers();
			if (question.get(i).getGradeable()) {
				if (question.get(i).getCorrectOutOfOrder()) {
					if (allAnswersContained(UserAnswer, KeyAnswer)) {
						numcorrect++;
					}
				} else {
					if (allAnswersExact(UserAnswer, KeyAnswer)) {
						numcorrect++;
					}
				}
			}

		}
		return numcorrect / this.numberOtherQ;
	}

	/**
	 * Find out if all the answers are contained for the key, calls within
	 * 
	 * @param responses
	 * @param key
	 * @return bool if correct
	 */
	private boolean allAnswersContained(ArrayList<String> responses,
			ArrayList<String> key) {
		for (String correctAnswer : key) {
			if (!within(correctAnswer, responses))
				return false;
		}
		return true;
	}

	/**
	 * Within checks indiv key parts for the responses as opposed to the whole
	 * key
	 * 
	 * @param key
	 * @param responses
	 * @return bool if contained
	 */
	private boolean within(String key, ArrayList<String> responses) {
		for (String response : responses) {
			if (response.equals(key))
				return true;
		}
		return false;
	}

	/**
	 * @param response
	 * @param key
	 * @return bool if answer is exactly the key
	 */
	private boolean allAnswersExact(ArrayList<String> response,
			ArrayList<String> key) {
		if (response.size() < key.size())
			return false;
		for (int i = 0; i < key.size(); i++) {
			if (!key.get(i).equals(response.get(i)))
				return false;
		}
		return true;
	}

	/**
	 * Finds number of essays in the assessment
	 * 
	 * @param questions
	 * @return number of essays
	 */
	private int getNumEssay(ArrayList<Question> questions) {
		int numEssay = 0;
		for (Question question : questions) {
			if (!question.getGradeable())
				numEssay++;
		}
		return numEssay;
	}

	/**
	 * Finds out how many non-essay questions there are.
	 * 
	 * @param questions
	 * @return number of non-essay questions
	 */
	private int getNumOtherQ(ArrayList<Question> questions) {
		return questions.size() - this.numberOfEssay;
	}

}
