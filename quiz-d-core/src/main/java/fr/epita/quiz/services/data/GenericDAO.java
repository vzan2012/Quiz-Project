package fr.epita.quiz.services.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>GenericDAO</h1>
 * <p>
 * This DAO contains the create, delete, getType, search and update methods.
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 *
 * @param <T>
 */
public abstract class GenericDAO<T> {

	private static final Logger LOGGER = LogManager.getLogger(GenericDAO.class);

	@PersistenceContext
	protected EntityManager em;

	/**
	 * This method is used to update the entity.
	 * 
	 * @param instance
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T instance) {
		LOGGER.debug("Update entity");
		em.merge(instance);
	}

	/**
	 * This method is used to delete the entity.
	 * 
	 * @param instance
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T instance) {
		LOGGER.debug("Delete entity");
		em.remove(em.contains(instance) ? instance : em.merge(instance));

	}

	/**
	 * This method is used to create the entity.
	 * 
	 * @param instance
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(T instance) {
		LOGGER.debug("Create entity");
		em.persist(instance);

	}

	/**
	 * This method is used to search the entity.
	 * 
	 * @param criteriaInstance
	 * @return the search
	 */
	public abstract List<T> search(T criteriaInstance);

	/**
	 * This method is used to return the id.
	 * 
	 * @param id
	 * @return the id
	 */
	public T getById(Serializable id) {
		LOGGER.debug("Find entity by id");
		return em.find(getType(), id);
	}

	/**
	 * This method is used to return the class type.
	 * 
	 * @return the class type
	 */
	public abstract Class<T> getType();

}
