package com.machao.accountbox.db.service.model;

public class User {
	private String username;
	private String password;
	private String password2;
	private int questionIndex;
	private String answer;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getQuestionIndex() {
		return questionIndex;
	}
	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", password2=" + password2 + ", questionIndex="
				+ questionIndex + ", answer=" + answer + "]";
	}
	
	
}
