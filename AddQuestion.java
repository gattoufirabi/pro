package quizmaker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class AddQuestion {

	private JFrame frame;
	private JTextField textField;
	public String textInput = new String();

	/**
	 * Launch the application.
	 */
	public void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddQuestion window = new AddQuestion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddQuestion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 51, 51));
		frame.setBounds(100, 100, 503, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(51, 204, 51));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(89, 220, 312, 50);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(51, 204, 51));
		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setBounds(89, 33, 312, 151);
		frame.getContentPane().add(textArea_1);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(textArea_1.getText().equals(""))){
					Question nq = new Question(textArea_1.getText(),textArea.getText().toLowerCase());
					QuizMaker.getQuiz(QuizMaker.currQuiz).addQuestion(nq);
					frame.dispose();
				}
			}
		});
		btnSubmit.setBounds(143, 286, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblQuestion = new JLabel("Question:");
		lblQuestion.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		lblQuestion.setForeground(Color.WHITE);
		lblQuestion.setBounds(89, 8, 78, 14);
		frame.getContentPane().add(lblQuestion);
		
		JLabel lblAnswer = new JLabel("Answer:");
		lblAnswer.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		lblAnswer.setForeground(Color.WHITE);
		lblAnswer.setBounds(89, 195, 78, 14);
		frame.getContentPane().add(lblAnswer);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		btnCancel.setBounds(257, 286, 89, 23);
		frame.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}
}
