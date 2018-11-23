package fr.epita.quiz.services.web.api.transport;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;

/**
 * <h1>MCQChoiceMessage</h1>
 * <p>
 * This class is used to set the ID, status, choice labels of the MCQs
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 *
 */

public class MCQChoiceMessage {

	private Long id;
	private Boolean valid;
	private String label;

	/**
	 * This method is used to return the id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method is used to set the id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method is used to return the status of the choice
	 * 
	 * @return valid
	 */
	public Boolean getValid() {
		return valid;
	}

	/**
	 * This method is used to set the status of the choice
	 * 
	 * @param valid
	 */
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * This method is used to retrieve the label of the choice
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * This method is used to set the label of the choice
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * This method is used to assign the Question to the corresponding choices.
	 * 
	 * @param question
	 * @return choice
	 */
	public MCQChoice toMCQChoice(Question question) {
		MCQChoice choice = new MCQChoice();
		choice.setChoiceLabel(this.label);
		choice.setValid(valid);
		choice.setId(id);
		choice.setQuestion(question);
		return choice;
	}

}
