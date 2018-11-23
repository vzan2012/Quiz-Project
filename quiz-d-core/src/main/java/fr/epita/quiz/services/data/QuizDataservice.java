package fr.epita.quiz.services.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;

/**
 * 
 * <h1>QuizDataservice</h1>
 * <p>
 * This class contains the methods for creating, finding, updating the questions
 * and answers
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 *
 */
@Repository
public class QuizDataservice {

	private static final Logger LOGGER = LogManager.getLogger(QuizDataservice.class);

	@Inject
	QuestionDAO questionDAO;

	@Inject
	MCQChoiceDAO mcqDAO;

	/**
	 * This method is used to create the questions with choices
	 * 
	 * @param question
	 * @param mcqChoiceList
	 */
	public void createQuestionWithChoices(Question question, List<MCQChoice> mcqChoiceList) {
		LOGGER.info("Creating the MCQs");
		questionDAO.create(question);
		for (MCQChoice choice : mcqChoiceList) {
			choice.setQuestion(question);
			mcqDAO.create(choice);
		}
	}

	/**
	 * This method is used to return the list of choices for the given question
	 * 
	 * @param question
	 * @return questionsAndChoices
	 */
	public Map<Question, List<MCQChoice>> findQuestionsAndChoices(Question question) {
		LOGGER.info("Find the Question and Choices for a given question");
		Map<Question, List<MCQChoice>> questionsAndChoices = new LinkedHashMap<Question, List<MCQChoice>>();

		List<Question> questionsList = questionDAO.search(question);

		for (Question currentQuestion : questionsList) {
			MCQChoice mcqChoiceCriteria = new MCQChoice();
			mcqChoiceCriteria.setQuestion(currentQuestion);
			List<MCQChoice> mcqChoiceList = mcqDAO.search(mcqChoiceCriteria);
			questionsAndChoices.put(currentQuestion, mcqChoiceList);
		}

		return questionsAndChoices;

	}

	/**
	 * @return questionsAndChoices
	 */
	public Map<Question, List<MCQChoice>> findAllQuestionsWithMCQChoices() {
		LOGGER.info("Find all questions with choices");
		Map<Question, List<MCQChoice>> questionsAndChoices = new LinkedHashMap<Question, List<MCQChoice>>();

		List<Question> questionsList = questionDAO.searchAll();

		for (Question currentQuestion : questionsList) {
			MCQChoice mcqChoiceCriteria = new MCQChoice();
			mcqChoiceCriteria.setQuestion(currentQuestion);
			List<MCQChoice> mcqChoiceList = mcqDAO.search(mcqChoiceCriteria);
			questionsAndChoices.put(currentQuestion, mcqChoiceList);
		}

		return questionsAndChoices;
	}

	/**
	 * This method is used to get Question by providing Id of the question.
	 * 
	 * @param id
	 * @return the question
	 */
	public Question getQuestionById(Long id) {
		Question question = questionDAO.getById(id);
		return question;
	}

	/**
	 * This method is used to update the Question and the choices.
	 * 
	 * @param question
	 * @param mcqChoiceList
	 */
	public void updateQuestionAndChoices(Question question, List<MCQChoice> mcqChoiceList) {
		LOGGER.debug("Update question and choices");
		questionDAO.update(question);
		for (MCQChoice currentChoice : mcqChoiceList) {
			if (currentChoice.getChoiceLabel() != null && currentChoice.getValid() != null) {
				mcqDAO.update(currentChoice);
			}
		}
	}

	/**
	 * This method is used to delete a MCQ along with the choices.
	 * 
	 * @param question
	 * @return boolean status, true for the deletion of question and choices and
	 *         false for the failure of deletion question and choices
	 */
	public Boolean deleteQuestionWithChoices(Question question) {
		LOGGER.debug("Delete question with choices");
		MCQChoice choiceCriteria = new MCQChoice();
		choiceCriteria.setQuestion(question);
		List<MCQChoice> mcqChoiceList = mcqDAO.search(choiceCriteria);

		if (question != null && question.getId() != null) {
			// Delete mcq choices one by one
			for (MCQChoice currentChoice : mcqChoiceList) {
				LOGGER.debug("Deleting MCQ choice: " + currentChoice.getId());
				mcqDAO.delete(currentChoice);
			}
			questionDAO.delete(question);
			return true;
		}
		return false;
	}
}
