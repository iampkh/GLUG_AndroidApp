package com.pkh.db;

public class Feed {
	String title=null,link=null,description=null,id=null;

	public Feed() {
		// TODO Auto-generated constructor stub
	}
	 public Feed(String id, String title, String link,String description){
	        this.id = id;
	        this.title=title;
	        this.description=description;
	        this.link=link;
	    }
	 
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
