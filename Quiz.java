package quizmaker;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Quiz {
	
	private String Name;
	private int Number;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private Connection connection = SqliteConnection.dbConnector();
	
	public Quiz(String nameIn) {
		Name = nameIn;
	}
	public String getName() {
		return Name;
	}
	
	public int getNum() {
		return Number;
	}
	
	public int length() {
		return questions.size();
	}
	
	public void addQuestion(Question quest) {
		questions.add(quest);
		try {
			String query = "INSERT INTO QuizDB (Question,Answer,QuizName) VALUES (?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, quest.getQuestion());
			pst.setString(2, quest.getAnswer());
			pst.setString(3, QuizMaker.currQuiz);
			pst.execute();
			pst.close();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1); 
		}
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public String toString() {
		return this.getName();
	}
	
	public Quiz buildQuiz(String quiz) {
		ResultSet qaTable;
		
		/*First query the DB for the questions and answers for the quiz to build*/
		try {
			String query = "SELECT ALL Question,Answer FROM QuizDB WHERE QuizName = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, quiz);
			qaTable = pst.executeQuery();
			while (qaTable.next()) {
				Question newQ = new Question(qaTable.getString(1), qaTable.getString(2));
				QuizMaker.getQuiz(quiz).questions.add(newQ);
			}
			
			pst.close();
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1); 
		}
		/*Then, create all of the question objects for the quiz*/
		
		return null;
	}
}
