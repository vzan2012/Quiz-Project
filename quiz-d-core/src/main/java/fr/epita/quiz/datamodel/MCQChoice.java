package fr.epita.quiz.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * <h1>MCQChoice</h1>
 * <p>
 * This class is used to assign the MCQChoice ID, MCQChoice Label setter and
 * getter methods.
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 */

@Entity
public class MCQChoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Boolean valid;

	private String choiceLabel;

	@ManyToOne
	private Question question;

	/**
	 * 
	 * MCQChoice - Default Constructor
	 * 
	 */
	public MCQChoice() {
		// Default constructor
	}

	/**
	 * This method is used to return the MCQChoice ID
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method is used to set the MCQChoice ID
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method is used to return the MCQChoice Label
	 * 
	 * @return the choiceLabel
	 */
	public String getChoiceLabel() {
		return choiceLabel;
	}

	/**
	 * This method is used to set the MCQChoice Label
	 * 
	 * @param choiceLabel
	 *            the choiceLabel to set
	 */
	public void setChoiceLabel(String choiceLabel) {
		this.choiceLabel = choiceLabel;
	}

	/**
	 * This method is used to return the Question
	 * 
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * This method is used to set the Question
	 * 
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * This method is used to return the boolean valid of the choice
	 * 
	 * @return the valid
	 */
	public Boolean getValid() {
		return valid;
	}

	/**
	 * This method is used to set the boolean valid of the choice
	 * 
	 * @param valid
	 *            the valid to set
	 */
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}