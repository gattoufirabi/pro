package quizmaker;

public class Question {
	private String question;
	private String answer;

	public Question(String qInput, String aInput) {
		if ((qInput != null && !qInput.equals("")) && (aInput != null && !aInput.equals(""))) {
			question = qInput;
			answer = aInput.toLowerCase();
		}
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String toString() {
		return this.getQuestion() + " " + this.getAnswer();
	}
}
