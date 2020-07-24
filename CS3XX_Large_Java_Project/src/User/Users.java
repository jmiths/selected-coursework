package User;

import java.util.ArrayList;

import Interface.Output;

public class Users // implements Interfaces
{
	private int _id;
	private String _FirstName;
	private String _LastName;

	// private transient Assessment[] ActiveAssessments;
	// private transient Assessment[] CompletedAssessments;

	private boolean _Admin;

	// private Assessment[] EssaysToComment; // TO Do: Assessment must have
	// owner

	public Users(int id, String FirstName, String LastName, boolean Admin) {
		_id = id;
		_FirstName = FirstName;
		_LastName = LastName;
		_Admin = Admin;
	}

	// Mutators

	public void MakeAdmin() {
		_Admin = true;
	}

	// Inspectors
	public int getId() {
		return _id;
	}

	public String getName() {
		return _FirstName + " " + _LastName;
	}

	public boolean isAdmin() {
		return _Admin;
	}

	public <T> void save(ArrayList<T> t) {
		String filename = "/tmp/Users.ser";
		Output out = new Output();
		boolean success = out.saveIt(filename, t);
		if (!success) {
			out.Out(filename + " was not able to be saved.");
		}
	}

	/*
	 * public <T> ArrayList<T> load() { String filename = "/tmp/Users.ser";
	 * Output out = new Output(); Input in = new Input(); ArrayList<T>
	 * success;// = new ArrayList<T>(); success = in.loadIt(filename); if
	 * (success.isEmpty()) { out.Out(filename + " was not able to be loaded.");
	 * }
	 * 
	 * return success; }
	 */
	public void print() {
		Output out = new Output();
		out.Out("Userid: " + _id);
		out.Out("First Name: " + _FirstName);
		out.Out("Last Name: " + _LastName);
		out.Out("Is this User an Admin: " + _Admin);
	}

	public void Exam() {
		Output out = new Output();
		out.Out("Good luck on your exam" + this.getName());

	}
}
