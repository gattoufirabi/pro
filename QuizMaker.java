package quizmaker;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;

public class QuizMaker {

	private JFrame frame;
	private JButton btnNewButton;
	private JTextField textField_1;
	public static String currQuiz = null;
	private StringBuffer tableName = new StringBuffer();
	private Integer [] fontSizes = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,35,40,50};
	public static ArrayList<Quiz> quizList = new ArrayList();
	public static ArrayList<String> quizNames = new ArrayList();
	public static Question currQuestion;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JLabel lblCurrentScore;
	private int tried;
	private int score;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizMaker window = new QuizMaker();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	
	public void getQuizNames(){
		ResultSet rowQuery;
		int rows = -1;
		ResultSet quizNameQuery;
		
		try {
			String query = "SELECT Count(*) FROM QuizDB";
			PreparedStatement pst = connection.prepareStatement(query);
			rowQuery = pst.executeQuery();
			rows = rowQuery.getInt(1);
			
			pst.close();
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(null, e1); 
		}
		
		if (rows > 0) { //prevents from querying if there are no rows to process yet
			try {
				String query1 = "SELECT ALL QuizName FROM QuizDB";
				PreparedStatement pst = connection.prepareStatement(query1);
				quizNameQuery = pst.executeQuery();
				for (int i = 0; i <= rows; i++) {
					String gotString = quizNameQuery.getString(1);
					if (!quizNames.contains(gotString) &&
							gotString != null) {
						quizNames.add(gotString);
						Quiz temp = new Quiz(gotString); //create new Quiz object b/c name is stored in DB
						quizList.add(temp);
					}
					quizNameQuery.next();
				}
				pst.close();
			} catch (SQLException e1) {
				
				JOptionPane.showMessageDialog(null, e1); 
			}
		}
		
	}
	
	
	/**
	 * Create the application.
	 */
	public QuizMaker() {
		connection = SqliteConnection.dbConnector();
		getQuizNames();
		if (!quizList.isEmpty()) {
			for (Quiz q: quizList) {
				q.buildQuiz(q.getName());
			}
		}
		initialize();
	}
	
	public static Quiz getQuiz(String Name) {
		for (Quiz q: quizList) {
			if (q.getName().equals(Name)) {
				return q;
			}
		}
		return null;
	}
	
	private Object makeObj(final String item)  {
	     return new Object() { public String toString() { return item; } };
	   }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 51, 51));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.setBounds(0, 0, 915, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		comboBox.setBounds(199, 20, 178, 36);
		
		for (String s: quizNames) {
			comboBox.addItem(s);
		}
		frame.getContentPane().add(comboBox);
		
		JLabel lblCurrentQuiz = new JLabel("Current Quiz: ");
		lblCurrentQuiz.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		lblCurrentQuiz.setForeground(new Color(255, 255, 255));
		lblCurrentQuiz.setBounds(36, 63, 308, 14);
		frame.getContentPane().add(lblCurrentQuiz);
		
		
		JButton btnSetCurrentQuiz = new JButton("Set Current Quiz");
		btnSetCurrentQuiz.setIcon(new ImageIcon("C:\\Users\\blnob\\OneDrive\\Documents\\Activity Feed_32px.png"));
		btnSetCurrentQuiz.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnSetCurrentQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currQuiz = comboBox.getSelectedItem().toString();
				lblCurrentQuiz.setText("Current Quiz: " + currQuiz);
			}
		});
		btnSetCurrentQuiz.setBounds(26, 20, 163, 36);
		frame.getContentPane().add(btnSetCurrentQuiz);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		textField_1.setBounds(590, 20, 162, 36);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
	
		
		btnNewButton = new JButton("Create New Quiz");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\blnob\\OneDrive\\Documents\\New Document_32px.png"));
		btnNewButton.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// Update the GUI
					comboBox.addItem(makeObj(textField_1.getText()));
					Quiz newQuiz = new Quiz(textField_1.getText());
					quizList.add(newQuiz);
					textField_1.setText("");
				
			}
		});
		btnNewButton.setBounds(413, 20, 167, 36);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_2 = new JButton("Add Question");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\blnob\\OneDrive\\Documents\\Question Mark_32px.png"));
		btnNewButton_2.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currQuiz == null) {
					JOptionPane.showMessageDialog(null, "Please select a quiz.");
				} else {
					AddQuestion aq = new AddQuestion();
					aq.NewScreen();
				}
			}
		});
		btnNewButton_2.setBounds(188, 87, 162, 36);
		frame.getContentPane().add(btnNewButton_2);
		
		JTextArea txtA = new JTextArea("QuizMaker 1.0");
		txtA.setLineWrap(true);
		txtA.setWrapStyleWord(true);
		txtA.setFont(new Font("Leelawadee UI", Font.PLAIN, 30));
		txtA.setBackground(new Color(51, 204, 51));
		txtA.setBounds(164, 146, 543, 249);
		txtA.setEditable(false);
		frame.getContentPane().add(txtA);
		
		JComboBox comboBox_1 = new JComboBox(fontSizes);
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_1.setBounds(768, 215, 81, 36);
		frame.getContentPane().add(comboBox_1);
		
		JButton btnNewButton_4 = new JButton("Set Font Size");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_4.setIcon(new ImageIcon("C:\\Users\\blnob\\OneDrive\\Documents\\Lowercase_32px.png"));
		btnNewButton_4.setBounds(734, 163, 145, 41);
		frame.getContentPane().add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fSize = ((Integer) comboBox_1.getSelectedItem()).intValue();
				txtA.setFont(new Font("Leelawadee UI", Font.PLAIN, fSize));
			}
		});
		
		textField = new JTextField();
		textField.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		textField.setBounds(249, 407, 377, 36);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblCurrentScore = new JLabel("Current Score: 0/0");
		lblCurrentScore.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		lblCurrentScore.setForeground(new Color(255, 255, 255));
		lblCurrentScore.setBackground(new Color(102, 255, 102));
		lblCurrentScore.setBounds(29, 163, 117, 14);
		frame.getContentPane().add(lblCurrentScore);
		
		JButton btnNewButton_3 = new JButton("Submit Answer");
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\blnob\\OneDrive\\Documents\\Enter Key_32px.png"));
		btnNewButton_3.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnNewButton_3.setBackground(UIManager.getColor("Button.darkShadow"));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currQuiz == null) {
					JOptionPane.showMessageDialog(null, "Please select a quiz.");
				} else {
					//check to see if quiz started
					if (tried < getQuiz(currQuiz).length()) {
						//check that an answer has been given at all
						if (textField.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Please enter an answer.");
						} else {
							//check if correct answer
							if (textField.getText().toLowerCase().equals(currQuestion.getAnswer())){
								score++;
								tried++;
								lblCurrentScore.setText("Current Score: " + score + "/" + tried);
							} else {
								tried++;
								lblCurrentScore.setText("Current Score: " + score + "/" + tried);
							}
							//check if all questions have been asked
							if (tried == getQuiz(currQuiz).length()) {
								textField.setText(""); // clear answer box
								txtA.setText("End of Quiz");
								JOptionPane.showMessageDialog(null, "Final Score: " + score + "/" + tried);
								tried = 0; // reset tried for next quiz
							} else {
								textField.setText(""); // clear answer box
								ArrayList<Question> qs = getQuiz(currQuiz).getQuestions();
								Question newQ = qs.get(tried);
								txtA.setText(newQ.getQuestion());
								currQuestion = newQ;
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please start quiz.");
					}
				}
				
			}
		});
		btnNewButton_3.setBounds(349, 454, 178, 41);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_1 = new JButton("Start Quiz");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\blnob\\OneDrive\\Documents\\Circled Play_32px.png"));
		btnNewButton_1.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnNewButton_1.setBounds(26, 86, 135, 37);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currQuiz == null) {
					JOptionPane.showMessageDialog(null, "Please select a quiz.");
				} else {
					tried = 0;
					score = 0;
					lblCurrentScore.setText("Current Score: " + score + "/" + tried);
					ArrayList<Question> qs = getQuiz(currQuiz).getQuestions();
					Question q1 = qs.get(0);
					txtA.setText(q1.getQuestion());
					currQuestion = q1;
				}
			}
		});
		
	}
}
