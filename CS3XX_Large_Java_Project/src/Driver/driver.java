package Driver;

import java.util.ArrayList;

import Assessment.Survey;
import Assessment.Test;
import Gradebook.Gradebook;
import Interface.Input;
import Interface.Output;

public class driver {

	/**
	 * Main method of program, it calls the first menu and the program finishes
	 * when it is done
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		menu1();
	}

	/**
	 * The menu makes a empty test and survey to guard against viewing an empty
	 * assessment and then allows the user to choose from a menu
	 */
	public static void menu1() {
		Output out = new Output();
		Input in = new Input();
		int choice = 0;
		Test test = new Test(); // Empty Test
		Test noneTest = test; // Reserve to check against as test is passed
								// around
		Survey survey = new Survey(); // Empty Survey
		Survey noneSurvey = survey; // Reserve to check against as survey is
									// passed around
		ArrayList<String> menuChoices = seedMenu1(); // Menu options

		do {
			choice = in.MenuSelect(menuChoices); // Helper to actually do
													// selection
			switch (choice) {
			case 0:
				survey = survey.MakeNewSurvey();
				break;
			case 1:
				test = test.MakeNewTest();
				break;
			case 2:
				if (noneSurvey == survey) // If survey is null
					out.Out("No Survey Loaded");
				else
					survey.print();
				break;
			case 3:
				if (noneTest == test) // If test is null
					out.Out("No Test Loaded");
				else
					test.print();
				break;
			case 4:
				survey = survey.load();
				break;
			case 5:
				test = test.load();
				break;
			case 6:
				survey.save();
				break;
			case 7:
				test.save();
				break;
			case 8:
				if (noneTest == test) // If test is null
					out.Out("No Test Loaded");
				else
					test.modify();
				break;
			case 9:
				if (noneSurvey == survey) // If survey is null
					out.Out("No Survey Loaded");
				else
					survey.modify();
				break;
			case 10:
				if (noneTest == test) // If test is null
					out.Out("No Test Loaded");
				else
					test.take();
				break;
			case 11:
				if (noneSurvey == survey) // If survey is null
					out.Out("No Survey Loaded");
				else
					survey.take();
				break;
			case 12:
				if (noneTest == test) // If test is null
					out.Out("No Test Loaded");
				else
					Gradebook.Grade(test);
				break;
			case 13:
				if (noneSurvey == survey) // If survey is null
					out.Out("No Survey Loaded");
				else
					survey.tabulate();
				break;
			case 14:
				if (noneTest == test) // If test is null
					out.Out("No Test Loaded");
				else
					test.tabulate();
				break;
			}
		} while (menuChoices.size() - 1 != choice); // Only exit with valid
													// exit, else ask for more
													// input
	}

	public static ArrayList<String> seedMenu1() {
		ArrayList<String> menuChoices = new ArrayList<String>();
		menuChoices.add("Create a New Survey");
		menuChoices.add("Create New Test");
		menuChoices.add("Display a Survey");
		menuChoices.add("Display a Test");
		menuChoices.add("Load a Survey");
		menuChoices.add("Load a Test");
		menuChoices.add("Save a Survey");
		menuChoices.add("Save a Test");
		menuChoices.add("Modify an Existing Test");
		menuChoices.add("Modify an Existing Survey");
		menuChoices.add("Take a Test");
		menuChoices.add("Take a Survey");
		menuChoices.add("Grade a Test");
		menuChoices.add("Tabulate a Survey");
		menuChoices.add("Tabulate a Test");
		menuChoices.add("Quit");
		return menuChoices;
	}

	/*
	 * private static Users login(Users CurrentUser) { Output out = new
	 * Output(); ArrayList<Users> listOfUsers = Users.load();
	 * 
	 * out.Out("You are logged into the system as:");
	 * out.Out(CurrentUser.print());
	 * 
	 * return CurrentUser; }
	 */

}
