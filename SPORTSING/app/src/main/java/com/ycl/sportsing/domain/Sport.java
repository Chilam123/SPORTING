package com.ycl.sportsing.domain;

public class Sport {
      private  int sport_id;
	  private String publisher_id;//发布者id
     private String sport_city;
	  private String sport_xuechang;
     private String sport_title;
	private String sport_date;
	private int time_to_now;
	private String sport_picture;
	private String sport_join_list;//参加这活动的人列表



	private int is_join;//是否有人参加

	public int getTime_to_now() {
		return time_to_now;
	}

	public void setTime_to_now(int time_to_now) {
		this.time_to_now = time_to_now;
	}

	public int getSport_id() {
		return sport_id;
	}

	public void setSport_id(int sport_id) {
		this.sport_id = sport_id;
	}

	public String getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getSport_city() {
		return sport_city;
	}

	public void setSport_city(String sport_city) {
		this.sport_city = sport_city;
	}

	public String getSport_xuechang() {
		return sport_xuechang;
	}

	public void setSport_xuechang(String sport_xuechang) {
		this.sport_xuechang = sport_xuechang;
	}

	public String getSport_title() {
		return sport_title;
	}

	public void setSport_title(String sport_title) {
		this.sport_title = sport_title;
	}

	public String getSport_picture() {
		return sport_picture;
	}

	public void setSport_picture(String sport_picture) {
		this.sport_picture = sport_picture;
	}

	public String getSport_join_list() {
		return sport_join_list;
	}

	public void setSport_join_list(String sport_join_list) {
		this.sport_join_list = sport_join_list;
	}

	public String getSport_date() {
		return sport_date;
	}

	public void setSport_date(String sport_date) {
		this.sport_date = sport_date;
	}

	public int getIs_join() {
		return is_join;
	}

	public void setIs_join(int is_join) {
		this.is_join = is_join;
	}
}
