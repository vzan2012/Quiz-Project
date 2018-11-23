package fr.epita.quiz.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <h1>Question</h1>
 * <p>
 * This class is used to assign the Question ID, Question Label setter and
 * getter methods.
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 */

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String questionLabel;

	/**
	 * Default Constructor
	 */
	public Question() {

	}

	/**
	 * This method is used to return the Question ID
	 * 
	 * @return the Question ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method is used to set the Question ID
	 * 
	 * @param id
	 *            - Set the Question ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method is used to return the Question Label
	 * 
	 * @return the Question Label
	 */
	public String getQuestionLabel() {
		return questionLabel;
	}

	/**
	 * This method is set to the Question Label
	 * 
	 * @param questionLabel
	 */
	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

}
