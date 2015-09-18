package com.example.gamedemo.bean;

import java.io.Serializable;

import org.json.JSONObject;

public class Game implements Serializable {
	private String id;
	private String typeid;
	private String shorttitle;
	private String litpic;
	private String typename;
	private String release_date;
	private String release_company;
	private String websit;
	private String terrace;

	@Override
	public String toString() {
		return "Game [id=" + id + ", typeid=" + typeid + ", shorttitle=" + shorttitle + ", litpic=" + litpic
				+ ", typename=" + typename + ", release_date=" + release_date + ", release_company=" + release_company
				+ ", websit=" + websit + ", terrace=" + terrace + "]";
	}

	public void getJSONObject(JSONObject json) {
		this.id = json.optString("id");
		this.typeid = json.optString("typeid");
		this.shorttitle = json.optString("shorttitle");
		this.litpic = json.optString("litpic");
		this.typename = json.optString("typename");
		this.release_date = json.optString("release_date");
		this.release_company = json.optString("release_company");
		this.websit = json.optString("websit");
		this.terrace = json.optString("terrace");
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

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_dat) {
		this.release_date = release_date;
	}

	public String getRelease_company() {
		return release_company;
	}

	public void setRelease_company(String release_company) {
		this.release_company = release_company;
	}

	public String getWebsit() {
		return websit;
	}

	public void setWebsit(String websit) {
		this.websit = websit;
	}

	public String getTerrace() {
		return terrace;
	}

	public void setTerrace(String terrace) {
		this.terrace = terrace;
	}
}
