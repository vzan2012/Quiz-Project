package fr.epita.quiz.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.data.QuizDataservice;
import fr.epita.quiz.services.web.api.transport.MCQChoiceMessage;
import fr.epita.quiz.services.web.api.transport.QuestionMessage;

/**
 * <h1>Question Resource</h1>
 * <p>
 * This class is contains the methods and path for the REST services.
 * </p>
 * 
 * @author Deepak Guptha
 * @version 1.0
 *
 */
@Path("questions")
public class QuestionResource {

	private static final Logger LOGGER = LogManager.getLogger(QuestionResource.class);

	static final String PATH = "questions";

	@Inject
	QuizDataservice ds;

	/**
	 * This method is used to create a question for the REST Service API.
	 * 
	 * @param message
	 * @return response
	 * @throws URISyntaxException
	 */
	@POST
	@Path("/")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response createQuestion(QuestionMessage message) throws URISyntaxException {
		LOGGER.info("Create a question with choices");
		Question question = toQuestion(message);
		List<MCQChoice> mcqChoiceList = toMCQChoiceList(question, message.getMcqChoices());
		ds.createQuestionWithChoices(question, mcqChoiceList);
		return Response.created(new URI(PATH + "/" + String.valueOf(question.getId()))).build();
	}

	/**
	 * This method is used to find the Questions and Answers for a REST Service API.
	 * 
	 * @param inputString
	 * @return response
	 */
	@GET
	@Path("/")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response findQuestionsAndChoices(@QueryParam("s") String inputString) {

		List<QuestionMessage> questionMessages = new ArrayList<QuestionMessage>();
		Question question = new Question();
		question.setQuestionLabel(inputString);
		Map<Question, List<MCQChoice>> map = ds.findQuestionsAndChoices(question);
		for (Entry<Question, List<MCQChoice>> entry : map.entrySet()) {
			QuestionMessage qm = new QuestionMessage();
			qm.setQuestionLabel(entry.getKey().getQuestionLabel());
			qm.setId(entry.getKey().getId());
			addMCQChoiceListToQuestionMessage(entry.getValue(), qm);
			questionMessages.add(qm);
		}

		return Response.ok(questionMessages).build();
	}

	/**
	 * This method is used to find or view all the Questions and Answers - REST
	 * Service API
	 * 
	 * @return response
	 */
	@GET
	@Path("/findAll")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response findAllMCQs() {
		LOGGER.info("Find all multiple choice questions");
		List<QuestionMessage> questionMessages = new ArrayList<QuestionMessage>();
		Map<Question, List<MCQChoice>> map = ds.findAllQuestionsWithMCQChoices();
		for (Entry<Question, List<MCQChoice>> entry : map.entrySet()) {
			QuestionMessage qm = new QuestionMessage();
			qm.setQuestionLabel(entry.getKey().getQuestionLabel());
			qm.setId(entry.getKey().getId());
			addMCQChoiceListToQuestionMessage(entry.getValue(), qm);
			questionMessages.add(qm);
		}

		return Response.ok(questionMessages).build();
	}

	/**
	 * 
	 * This method is used to retrieve a single question - REST Service API
	 * 
	 * @param id
	 * @return response
	 */
	@GET
	@Path("/{id}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getOneQuestion(@PathParam("id") String id) {

		Question question = ds.getQuestionById(Long.valueOf(id));
		if (question == null) {
			return Response.status(Status.NOT_FOUND).entity("{\"message\" : 'Not found'}").build();
		}
		QuestionMessage message = new QuestionMessage();
		message.setId(question.getId());
		message.setQuestionLabel(question.getQuestionLabel());

		return Response.ok(message).build();
	}

	/**
	 * This method is used to a REST API service for updating the Questions and
	 * MCQChoices.
	 * 
	 * @param message
	 * @return response
	 */
	@PUT
	@Path("/updateMcq")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response updateQuestionAndChoices(QuestionMessage message) {
		LOGGER.info("Update a question with its choices");
		Question question = ds.getQuestionById(Long.valueOf(message.getId()));
		if (question == null) {
			return Response.status(Status.NOT_FOUND).entity("{\"message\" : 'Question not found in database'}").build();
		}
		List<MCQChoice> mcqChoiceList = toMCQChoiceList(question, message.getMcqChoices());
		question.setQuestionLabel(message.getQuestionLabel());
		ds.updateQuestionAndChoices(question, mcqChoiceList);

		return Response.ok(message).build();
	}

	/**
	 * This method is used to a REST API service for finding the Questions and
	 * Answers.
	 * 
	 * @param message
	 * @return response
	 */
	@DELETE
	@Path("/deleteMcq")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response deleteQuestionAndChoices(QuestionMessage message) {
		LOGGER.info("Delete a question with choices");
		Question question = ds.getQuestionById(Long.valueOf(message.getId()));
		if (question == null) {
			return Response.status(Status.NOT_FOUND).entity("{\"message\" : 'Question not found in database'}").build();
		}
		Boolean result = ds.deleteQuestionWithChoices(question);
		if (result == Boolean.FALSE)
			return Response.status(Status.BAD_REQUEST).build();

		return Response.ok(message).build();
	}

	/**
	 * This method is used to set the Question Label
	 * 
	 * @param qm
	 * @return question
	 */
	private static Question toQuestion(QuestionMessage qm) {
		Question question = new Question();
		question.setQuestionLabel(qm.getQuestionLabel());
		return question;
	}

	/**
	 * This method is used to set the ID, Question Label.
	 * 
	 * @param question
	 * @return questionMessage
	 */
	private static QuestionMessage fromQuestion(Question question) {
		QuestionMessage questionMessage = new QuestionMessage();
		questionMessage.setId(question.getId());
		questionMessage.setQuestionLabel(question.getQuestionLabel());
		return questionMessage;
	}

	/**
	 * This method to return the choice list.
	 * 
	 * @param question
	 * @param mcqChoices
	 * @return mcqChoiceList
	 */
	private static List<MCQChoice> toMCQChoiceList(Question question, List<MCQChoiceMessage> mcqChoices) {
		List<MCQChoice> mcqChoiceList = new ArrayList<>();
		for (MCQChoiceMessage choiceMessage : mcqChoices) {
			MCQChoice mcqChoice = choiceMessage.toMCQChoice(question);
			mcqChoiceList.add(mcqChoice);
		}

		return mcqChoiceList;
	}

	/**
	 * This method is used to convert MCQChoice object to MCQChoiceMessage.
	 * 
	 * @param mcqChoice
	 * @return choiceMessage
	 */

	private static MCQChoiceMessage fromMCQChoice(MCQChoice mcqChoice) {
		MCQChoiceMessage choiceMessage = new MCQChoiceMessage();
		choiceMessage.setId(mcqChoice.getId());
		choiceMessage.setLabel(mcqChoice.getChoiceLabel());
		choiceMessage.setValid(mcqChoice.getValid());

		return choiceMessage;
	}

	/**
	 * This method is used to add mcq choices in the list.
	 * 
	 * @param list
	 * @param qm
	 */
	private void addMCQChoiceListToQuestionMessage(List<MCQChoice> list, QuestionMessage qm) {
		List<MCQChoiceMessage> resultList = new ArrayList<>();
		for (MCQChoice choice : list) {
			resultList.add(fromMCQChoice(choice));
		}
		qm.setMcqChoices(resultList);
	}

}
