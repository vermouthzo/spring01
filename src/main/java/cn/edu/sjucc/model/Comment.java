package cn.edu.sjucc.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private int star = 0;
	/**
	 *	评论内容
	 */
	private String content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Comment [author=" + author + ", dt=" + dt + ", star=" + star + ", content=" + content + "]";
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
