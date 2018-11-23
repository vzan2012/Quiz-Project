package fr.epita.quiz.services.web.api.transport;

import java.util.List;

/**
 * <h1>QuestionMessage</h1>
 * <p>
 * This class contains the methods to set and retrive the id, questions and the
 * MCQ choices
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 *
 */
public class QuestionMessage {

	private Long id;

	private String questionLabel;

	private List<MCQChoiceMessage> mcqChoices;

	/**
	 * This method is used to return the ID.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method is set the ID.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method is used to return the Question.
	 * 
	 * @return
	 */
	public String getQuestionLabel() {
		return questionLabel;
	}

	/**
	 * This method is used to set the Question.
	 * 
	 * @param questionLabel
	 */
	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	/**
	 * This method is used to return the mcqChoices.
	 * 
	 * @return mcqChoices
	 */
	public List<MCQChoiceMessage> getMcqChoices() {
		return mcqChoices;
	}

	/**
	 * This method is used to set the MCQChoices.
	 * 
	 * @param mcqChoices
	 */
	public void setMcqChoices(List<MCQChoiceMessage> mcqChoices) {
		this.mcqChoices = mcqChoices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QuestionMessage [id=" + id + ", questionLabel=" + questionLabel + ", mcqChoices=" + mcqChoices + "]";
	}
}
