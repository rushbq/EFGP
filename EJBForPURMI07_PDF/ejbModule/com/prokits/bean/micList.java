package com.prokits.bean;

public class micList {
	/*
	 * 嘜頭資料宣告
	 */
	private String MarkName = ""; //自訂嘜頭名
	private String Pono = ""; //PO.NO
	private String MarkDesc1 = ""; //正嘜描述
	private String MarkDesc2 = ""; //側嘜描述
	private String MarkUrl1 = "";  //正嘜圖
	private String MarkUrl2 = "";  //側嘜圖
	private String CustID = "";
	
	
	public void setMarkName(String markName) {
		this.MarkName = markName;
	}
	public String getMarkName() {
		return MarkName;
	}
	
	public void setPono(String pono) {
		this.Pono = pono;
	}
	public String getPono() {
		return Pono;
	}
	
	public void setMarkDesc1(String markDesc1) {
		this.MarkDesc1 = markDesc1;
	}
	public String getMarkDesc1() {
		return MarkDesc1;
	}
	
	public void setMarkDesc2(String markDesc2) {
		this.MarkDesc2 = markDesc2;
	}
	public String getMarkDesc2() {
		return MarkDesc2;
	}
	
	public void setMarkUrl1(String markUrl1) {
		this.MarkUrl1 = markUrl1;
	}
	public String getMarkUrl1() {
		return MarkUrl1;
	}
	
	public void setMarkUrl2(String markUrl2) {
		this.MarkUrl2 = markUrl2;
	}
	public String getMarkUrl2() {
		return MarkUrl2;
	}
	
	public void setCustID(String custID) {
		this.CustID = custID;
	}
	public String getCustID() {
		return CustID;
	}
}
