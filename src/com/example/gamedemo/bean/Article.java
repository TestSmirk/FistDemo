package com.example.gamedemo.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

	private String id;
	private String typeid;
	private String typeid2;
	private String title;
	private String shorttitle;
	private String litpic;
	private String pubdate;
	private String arcurl;

	// 封装对应的json解析
	public void getJSONObject(JSONObject json) {
		try {
			this.id = json.getString("id");
			this.typeid = json.getString("typeid");
			this.typeid2 = json.getString("typeid2");
			this.title = json.getString("title");
			this.shorttitle = json.getString("shorttitle");
			this.litpic = json.getString("litpic");
			this.pubdate = json.getString("pubdate");
			this.arcurl = json.getString("arcurl");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", typeid=" + typeid + ", typeid2=" + typeid2 + ", title=" + title
				+ ", shorttitle=" + shorttitle + ", litpic=" + litpic + ", pubdate=" + pubdate + ", arcurl=" + arcurl
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypeid2() {
		return typeid2;
	}

	public void setTypeid2(String typeid2) {
		this.typeid2 = typeid2;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShorttitle() {
		return shorttitle;
	}

	public void setShorttitle(String shorttitle) {
		this.shorttitle = shorttitle;
	}

	public String getLitpic() {
		return litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getArcurl() {
		return arcurl;
	}

	public void setArcurl(String arcurl) {
		this.arcurl = arcurl;
	}

	public Article() {
		// TODO Auto-generated constructor stub
	}

}
