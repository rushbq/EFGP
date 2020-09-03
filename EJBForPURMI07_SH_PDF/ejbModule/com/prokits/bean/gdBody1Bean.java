package com.prokits.bean;

public class gdBody1Bean {
	/*
	 * 單身資料宣告
	 */
	private String c_SupModelNo = ""; // 廠商品號
	private String c_SpRemark = ""; // 產品特別注意事項
	private String c_MB201 = ""; // 包裝資料-內盒
	private String c_MB073 = ""; // 包裝資料-外箱
	private String c_MB074 = ""; // 包裝資料-淨重
	private String c_MB075 = ""; // 包裝資料-毛重
	private String c_MB071 = ""; // 包裝資料-材積

	private String TD003 = ""; // 序號
	private String TD004 = ""; // 產品品號
	private String TD005 = ""; // 產品品名
	private String TD008 = ""; // 採購數量
	private String TD009 = ""; // 單位
	private String TD010 = ""; // 單價
	private String TD011 = ""; // 金額
	private String TD012 = ""; // 預交日
	private String TD014 = ""; // 採購單身備註
	private String TD013 = ""; // 參考單別
	private String TD021 = ""; // 參考單號
	private String TD023 = ""; // 參考序號
	private String TD203 = ""; // 備註
	private String TD204 = ""; // 起始箱號
	private String TD205 = ""; // 截止箱號
	private String TD206 = ""; // 字軌
	
	private String cm_ModelNo = ""; // 對應客戶品號
	private String cm_CustID = ""; // 對應客戶代號
	private String cm_MicID = ""; // 對應嘜頭代號
	private String cm_MicName = ""; // 定義嘜頭圖示名稱
	private String c_ModelName = ""; // 品名(產品中心)
	private String c_ModelSpec = ""; // 品規(產品中心)
	private String c_Pono = ""; // 客戶單號
	private String lang = "";


	/* getter and setter */
	/*
	public String getC_CustModelNo() {
		return c_CustModelNo;
	}

	public void setC_CustModelNo(String c_CustModelNo) {
		this.c_CustModelNo = c_CustModelNo;
	}
	*/

	public String getC_SupModelNo() {
		return c_SupModelNo;
	}

	public void setC_SupModelNo(String c_SupModelNo) {
		this.c_SupModelNo = c_SupModelNo;
	}

	public String getC_SpRemark() {
		return c_SpRemark;
	}

	public void setC_SpRemark(String c_SpRemark) {
		this.c_SpRemark = c_SpRemark;
	}

	public String getCm_ModelNo() {
		return cm_ModelNo;
	}

	public void setCm_ModelNo(String cm_ModelNo) {
		this.cm_ModelNo = cm_ModelNo;
	}

	public String getCm_CustID() {
		return cm_CustID;
	}

	public void setCm_CustID(String cm_CustID) {
		this.cm_CustID = cm_CustID;
	}

	public String getCm_MicID() {
		return cm_MicID;
	}

	public void setCm_MicID(String cm_MicID) {
		this.cm_MicID = cm_MicID;
	}

	public String getCm_MicName() {
		return cm_MicName;
	}

	public void setCm_MicName(String cm_MicName) {
		this.cm_MicName = cm_MicName;
	}

	public String getC_MB201() {
		return c_MB201;
	}

	public void setC_MB201(String c_MB201) {
		this.c_MB201 = c_MB201;
	}

	public String getC_MB073() {
		return c_MB073;
	}

	public void setC_MB073(String c_MB073) {
		this.c_MB073 = c_MB073;
	}

	public String getC_MB074() {
		return c_MB074;
	}

	public void setC_MB074(String c_MB074) {
		this.c_MB074 = c_MB074;
	}

	public String getC_MB075() {
		return c_MB075;
	}

	public void setC_MB075(String c_MB075) {
		this.c_MB075 = c_MB075;
	}

	public String getC_MB071() {
		return c_MB071;
	}

	public void setC_MB071(String c_MB071) {
		this.c_MB071 = c_MB071;
	}

	public String getTD003() {
		return TD003;
	}

	public void setTD003(String tD003) {
		TD003 = tD003;
	}

	public String getTD004() {
		return TD004;
	}

	public void setTD004(String tD004) {
		TD004 = tD004;
	}

	public String getTD005() {
		return TD005;
	}

	public void setTD005(String tD005) {
		TD005 = tD005;
	}

	public String getTD203() {
		return TD203;
	}

	public void setTD203(String tD203) {
		TD203 = tD203;
	}

	public String getTD008() {
		return TD008;
	}

	public void setTD008(String tD008) {
		TD008 = tD008;
	}

	public String getTD009() {
		return TD009;
	}

	public void setTD009(String tD009) {
		TD009 = tD009;
	}

	public String getTD012() {
		return TD012;
	}

	public void setTD012(String tD012) {
		TD012 = tD012;
	}

	public String getTD014() {
		return TD014;
	}

	public void setTD014(String tD014) {
		TD014 = tD014;
	}

	public String getTD010() {
		return TD010;
	}

	public void setTD010(String tD010) {
		TD010 = tD010;
	}

	public String getTD011() {
		return TD011;
	}

	public void setTD011(String tD011) {
		TD011 = tD011;
	}

	public String getTD013() {
		return TD013;
	}

	public void setTD013(String tD013) {
		TD013 = tD013;
	}

	public String getTD021() {
		return TD021;
	}

	public void setTD021(String tD021) {
		TD021 = tD021;
	}

	public String getTD023() {
		return TD023;
	}

	public void setTD023(String tD023) {
		TD023 = tD023;
	}

	public String getTD204() {
		return TD204;
	}

	public void setTD204(String tD204) {
		TD204 = tD204;
	}

	public String getTD205() {
		return TD205;
	}

	public void setTD205(String tD205) {
		TD205 = tD205;
	}

	public String getTD206() {
		return TD206;
	}

	public void setTD206(String tD206) {
		TD206 = tD206;
	}

	public String getC_ModelName() {
		return c_ModelName;
	}

	public void setC_ModelName(String c_ModelName) {
		this.c_ModelName = c_ModelName;
	}

	public String getC_ModelSpec() {
		return c_ModelSpec;
	}

	public void setC_ModelSpec(String c_ModelSpec) {
		this.c_ModelSpec = c_ModelSpec;
	}

	public String getC_Pono() {
		return c_Pono;
	}

	public void setC_Pono(String c_Pono) {
		this.c_Pono = c_Pono;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
