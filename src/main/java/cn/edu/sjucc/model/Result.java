package cn.edu.sjucc.model;

public class Result {
	public static final int OK = 1;
	public static final int ERROR = -1;
	
	private int status;
	private String message;
	private User date;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getDate() {
		return date;
	}
	public void setDate(User date) {
		this.date = date;
	}
	
}
