package aeist.mobile.persistence;

import java.io.Serializable;

/**
 * @since version 1.0
 * 
 * 
 * @author joaovasques
 *
 */
public class Event implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String description;
	private String facebook_link;
	private String tag;
	private byte[] image;
	private int timestamp;
	
	public Event(String name, String description, String facebook_link, byte[] image)
	{
		this.name = name;
		this.description = description;
		this.facebook_link = facebook_link;
		this.image = image;
		this.timestamp = 1;
	}
	
	public Event() {
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getFacebookLink() {
		return facebook_link;
	}
	
	public void setFacebookLink(String link) {
		this.facebook_link = link;
	}

	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void incrementTimestamp() {
		this.timestamp+=1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
