package fr.epita.quiz.services.data;

import java.util.List;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.MCQChoice;

/**
 * <h1>MCQChoiceDAO</h1>
 * <p>
 * This class contains the search list of choices and the class type
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 *
 */

@Repository
public class MCQChoiceDAO extends GenericDAO<MCQChoice> {

	/**
	 * The logger is a log4j, which provides a reliable, fast and flexible logging
	 * framework.
	 */
	private static final Logger LOGGER = LogManager.getLogger(MCQChoiceDAO.class);

	/*
	 * This method is used to search the choices.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see fr.epita.quiz.services.data.GenericDAO#search(java.lang.Object)
	 */
	@Override
	public List<MCQChoice> search(MCQChoice mcqChoiceCriteria) {
		LOGGER.debug("Start searching MCQ choices for Question: ", mcqChoiceCriteria.getChoiceLabel());
		String hqlString = "from MCQChoice as m where m.question = :question";
		TypedQuery<MCQChoice> searchQuery = em.createQuery(hqlString, MCQChoice.class);
		searchQuery.setParameter("question", mcqChoiceCriteria.getQuestion());

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
	public Class<MCQChoice> getType() {
		return MCQChoice.class;
	}

}
