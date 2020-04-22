package cn.edu.sjucc.model;

import java.time.LocalDateTime;

	/**
	 * 
	 * @author lenovo
	 *
	 */
public class Comment {
	/**
	 * 	评论作者
	 */
	private String author;
	/**
	 * 	评论时间
	 */
	private LocalDateTime dt = LocalDateTime.now();
	/**
	 * 	评论点赞数量
	 */
	private int star = 0;
	/**
	 *	评论内容
	 */
	private String cotent;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDateTime getDt() {
		return dt;
	}
	public void setDt(LocalDateTime dt) {
		this.dt = dt;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getCotent() {
		return cotent;
	}
	public void setCotent(String cotent) {
		this.cotent = cotent;
	}
	@Override
	public String toString() {
		return "Comment [author=" + author + ", dt=" + dt + ", star=" + star + ", cotent=" + cotent + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		return true;
	}
}
