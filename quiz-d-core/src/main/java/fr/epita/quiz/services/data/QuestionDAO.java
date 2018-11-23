package fr.epita.quiz.services.data;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Question;

/**
 * <h1>QuestionDAO</h1>
 * <p>
 * This DAO extends the GenericDAO, contains the search and searchAll methods
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 * 
 */
@Repository
public class QuestionDAO extends GenericDAO<Question> {

	/**
	 * The logger is a log4j, which provides a reliable, fast and flexible logging
	 * framework.
	 */
	private static final Logger LOGGER = LogManager.getLogger(QuestionDAO.class);

	/*
	 * This method is used to search the questions. (non-Javadoc)
	 * 
	 * @see fr.epita.quiz.services.data.GenericDAO#search(java.lang.Object)
	 */
	@Override
	public List<Question> search(Question questionCriteria) {
		LOGGER.debug("Start searching for Question :" + questionCriteria.getQuestionLabel());

		String hqlString = "from Question as q where q.questionLabel like :qlabel";
		TypedQuery<Question> searchQuery = em.createQuery(hqlString, Question.class);
		if (questionCriteria.getQuestionLabel() == null || questionCriteria.getQuestionLabel().equals("")) {
			searchQuery.setParameter("qlabel", null);
		} else {
			searchQuery.setParameter("qlabel", "%" + questionCriteria.getQuestionLabel() + "%");
		}

		return searchQuery.getResultList();
	}

	/**
	 * This method is used to search all the questions
	 * 
	 * @return the Result List of Questions
	 */
	public List<Question> searchAll() {
		LOGGER.debug("Start searching for all the questions");
		String hqlString = "from Question";
		TypedQuery<Question> searchQuery = em.createQuery(hqlString, Question.class);

		return searchQuery.getResultList();
	}

	/*
	 * This method is used to return the class type.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see fr.epita.quiz.services.data.GenericDAO#getType()
	 */
	@Override
	public Class<Question> getType() {
		return Question.class;
	}

}
