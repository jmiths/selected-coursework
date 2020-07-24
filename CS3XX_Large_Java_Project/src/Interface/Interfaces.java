package Interface;

/**
 * @author Jacob Smith
 *
 */
/**
 * @author jws337
 * 
 */
public interface Interfaces {
	/**
	 * The print interface so that the driver can reasonably view the contents
	 * of objects that it operates on
	 */
	public void print();

	/**
	 * Provides a way for an object to save themselves. All objects will contain
	 * a call to output.saveIt() for their generic save
	 */
	public void save();

	/**
	 * Provides a way for an object to load themselves. All objects will contain
	 * a call to input.LoadIt() for their generic load
	 * 
	 * @return Object that has been loaded
	 */
	public Object load();

}
