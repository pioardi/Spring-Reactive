package com.aardizio.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class JournalMongo {

	private String title;
	private Date created;
	@Id
	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	private String summary;

	public JournalMongo(String title, String summary, String date) {
		this.title = title;
		this.summary = summary;
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			this.created = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	JournalMongo() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String toString() {
		StringBuilder value = new StringBuilder("JournalEntry(");
		value.append(",Title: ");
		value.append(title);
		value.append(",Summary: ");
		value.append(summary);
		value.append(",Created: ");
		value.append(created);
		value.append(")");
		return value.toString();
	}
}
