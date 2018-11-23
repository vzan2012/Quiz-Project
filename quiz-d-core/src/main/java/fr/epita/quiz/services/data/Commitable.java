package fr.epita.quiz.services.data;

/**
 * <h1>Commitable</h1>
 * <p>
 * This interface has three methods getInterface(), isCommitable() and commit().
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 * 
 * @param <T>
 */
public interface Commitable<T> {

	/**
	 * This method is used to return the instance
	 * 
	 * @return the instance
	 */
	public T getInstance();

	/**
	 * This boolean method is used to check the status
	 * 
	 * @return the boolean
	 */
	public boolean isCommitable();

	/**
	 * This method is used to commit
	 * 
	 */
	public void commit();

}
