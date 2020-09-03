package com.prokits.bean;

import java.util.List;

public class PURMI07 {
	/*
	 * 單頭資料宣告
	 */
	// [header]
	private String poType = ""; // 採購類型
	private String TC004 = ""; // 供應廠商代號
	private String TC004C = ""; // 供應廠商名稱
	private String TC001 = ""; // 採購單別
	private String TC002 = ""; // 採購單號
	private String TC052 = ""; // 聯絡人
	private String TC021 = ""; // 送貨地址1
	private String TC022 = ""; // 送貨地址2
	private String TC027 = ""; // 付款條件ID
	private String TC027C = ""; // 付款條件Name
	private String TC048C = ""; // 交易條件
	private String TC005 = "";	//交易幣別
	private String TC007 = ""; // 價格條件
	private String TC006 = ""; // 匯率
	private String TC024 = ""; // 單據日期
	private String TC011 = ""; // 採購人員ID
	private String TC011C = ""; // 採購人員Name
	private String TC011En = ""; // 採購人員英文名
	private String TC017 = ""; // 運輸方式
	private String TC012 = ""; // 列印語系(1:中文/2:英文)

	private String c_SupFullName = ""; // 廠商全名
	private String c_SupAddress = ""; // 廠商地址
	private String c_SupTel = ""; // 廠商電話
	private String c_SupFax = ""; // 廠商傳真
	private String c_Billnum = ""; // 發票統編
	private String c_Creater = ""; // 製單Name
	private String c_CreaterID = ""; // 製單ID
	private String c_LastSignDate = ""; //最後簽核日
	private String c_ShowMic = ""; //是否要顯示預設嘜頭
	private String c_SignUrl = ""; //簽名檔URL
	private String c_SchValue = ""; //學生期選項
	private String c_Cno = ""; //箱號
	

	// [column footer]
	private String TC009 = ""; // 備註
	private String TC023 = ""; // 數量合計
	private String TC019 = ""; // 金額合計
	private String TC020 = ""; // 稅額
	private String TC019C1 = ""; // 總計
	private String c_TaxType = ""; // 課稅別(TC018C)

	// [footer]
	private String c_TotalinChinese = ""; // 總計國字(TC019C1)
	private String c_MarkPic1 = ""; //標準正嘜圖示(單頭)
	private String c_MarkPic2 = ""; //標準側嘜圖示(單頭
	private String c_MarkUrl1 = ""; //標準正嘜圖url-1(單頭)
	private String c_MarkUrl2 = ""; //標準側嘜圖url-2(單頭)

	//(20180517改為帶預設圖, 除了1525501/2002054001) --- Start
	private String c_MarkUrl101 = ""; //自訂正嘜圖url-1
	private String c_MarkUrl102 = ""; //自訂側嘜圖url-1
	private String c_MarkUrl201 = ""; //自訂正嘜圖url-2
	private String c_MarkUrl202 = ""; //自訂側嘜圖url-2
	private String c_MarkUrl301 = ""; //自訂正嘜圖url-3
	private String c_MarkUrl302 = ""; //自訂側嘜圖url-3
	private String c_MarkUrl401 = ""; //自訂正嘜圖url-4
	private String c_MarkUrl402 = ""; //自訂側嘜圖url-4
	private String c_MarkUrl501 = ""; //自訂正嘜圖url-5
	private String c_MarkUrl502 = ""; //自訂側嘜圖url-5
	//(20180517改為帶預設圖) --- End
	
	private String c_MarkName1 = ""; //自訂嘜頭名1
	private String c_MarkName2 = ""; //自訂嘜頭名2
	private String c_MarkName3 = ""; //自訂嘜頭名3
	private String c_MarkName4 = ""; //自訂嘜頭名4
	private String c_MarkName5 = ""; //自訂嘜頭名5
	private String c_Pono1 = ""; //客戶單號1
	private String c_Pono2 = ""; //客戶單號2
	private String c_Pono3 = ""; //客戶單號3
	private String c_Pono4 = ""; //客戶單號4
	private String c_Pono5 = ""; //客戶單號5
	private String c_MarkDesc101 = ""; //自訂正嘜文字Desc-1
	private String c_MarkDesc102 = ""; //自訂側嘜文字Desc-1
	private String c_MarkDesc201 = ""; //自訂正嘜文字Desc-2
	private String c_MarkDesc202 = ""; //自訂側嘜文字Desc-2
	private String c_MarkDesc301 = ""; //自訂正嘜文字Desc-3
	private String c_MarkDesc302 = ""; //自訂側嘜文字Desc-3
	private String c_MarkDesc401 = ""; //自訂正嘜文字Desc-4
	private String c_MarkDesc402 = ""; //自訂側嘜文字Desc-4
	private String c_MarkDesc501 = ""; //自訂正嘜文字Desc-5
	private String c_MarkDesc502 = ""; //自訂側嘜文字Desc-5
	private String c_ShowDtl1 = ""; //是否顯示嘜頭區塊1
	private String c_ShowDtl2 = ""; //是否顯示嘜頭區塊2
	private String c_ShowDtl3 = ""; //是否顯示嘜頭區塊3
	private String c_ShowDtl4 = ""; //是否顯示嘜頭區塊4
	private String c_ShowDtl5 = ""; //是否顯示嘜頭區塊5
	private String c_CustID1 = ""; //客戶代號1
	private String c_CustID2 = ""; //客戶代號2
	private String c_CustID3 = ""; //客戶代號3
	private String c_CustID4 = ""; //客戶代號4
	private String c_CustID5 = ""; //客戶代號5

	/*
	 * 單身Object宣告 Start
	 */
	List details = null; // 用於Grid物件

	public List getDetails() {
		return details;
	}

	public void setDetails(List details) {
		this.details = details;
	}

	List details_footer = null; // 用於Grid物件
	public List getDetails_footer() {
		return details_footer;
	}

	public void setDetails_footer(List details) {
		this.details_footer = details;
	}
	
	List details_micList = null; // 用於Grid物件
	public List getDetails_micList() {
		return details_micList;
	}

	public void setDetails_micList(List details) {
		this.details_micList = details;
	}
	
	/*
	 * 單身Object宣告 End
	 */

	/* getter and setter */
	public String getPoType() {
		return poType;
	}

	public void setPoType(String poType) {
		this.poType = poType;
	}
	
	public String getTC004() {
		return TC004;
	}

	public void setTC004(String tC004) {
		TC004 = tC004;
	}

	public String getTC004C() {
		return TC004C;
	}

	public void setTC004C(String tC004C) {
		TC004C = tC004C;
	}

	public String getTC001() {
		return TC001;
	}

	public void setTC001(String tC001) {
		TC001 = tC001;
	}

	public String getTC002() {
		return TC002;
	}

	public void setTC002(String tC002) {
		TC002 = tC002;
	}

	public String getTC052() {
		return TC052;
	}

	public void setTC052(String tC052) {
		TC052 = tC052;
	}

	public String getTC021() {
		return TC021;
	}

	public void setTC021(String tC021) {
		TC021 = tC021;
	}

	public String getTC022() {
		return TC022;
	}

	public void setTC022(String tC022) {
		TC022 = tC022;
	}

	public String getTC027() {
		return TC027;
	}

	public void setTC027(String tC027) {
		TC027 = tC027;
	}

	public String getTC027C() {
		return TC027C;
	}

	public void setTC027C(String tC027C) {
		TC027C = tC027C;
	}

	public String getTC048C() {
		return TC048C;
	}

	public void setTC048C(String tC048C) {
		TC048C = tC048C;
	}

	public String getTC007() {
		return TC007;
	}

	public void setTC007(String tC007) {
		TC007 = tC007;
	}

	public String getTC006() {
		return TC006;
	}

	public void setTC006(String tC006) {
		TC006 = tC006;
	}

	public String getTC024() {
		return TC024;
	}

	public void setTC024(String tC024) {
		TC024 = tC024;
	}

	public String getTC011() {
		return TC011;
	}

	public void setTC011(String tC011) {
		TC011 = tC011;
	}

	public String getTC011C() {
		return TC011C;
	}

	public void setTC011C(String tC011C) {
		TC011C = tC011C;
	}

	public String getTC017() {
		return TC017;
	}

	public void setTC017(String tC017) {
		TC017 = tC017;
	}

	public String getTC012() {
		return TC012;
	}

	public void setTC012(String tC012) {
		TC012 = tC012;
	}

	public String getC_SupFullName() {
		return c_SupFullName;
	}

	public void setC_SupFullName(String c_SupFullName) {
		this.c_SupFullName = c_SupFullName;
	}

	public String getC_SupAddress() {
		return c_SupAddress;
	}

	public void setC_SupAddress(String c_SupAddress) {
		this.c_SupAddress = c_SupAddress;
	}

	public String getC_SupTel() {
		return c_SupTel;
	}

	public void setC_SupTel(String c_SupTel) {
		this.c_SupTel = c_SupTel;
	}

	public String getC_SupFax() {
		return c_SupFax;
	}

	public void setC_SupFax(String c_SupFax) {
		this.c_SupFax = c_SupFax;
	}

	public String getC_Billnum() {
		return c_Billnum;
	}

	public void setC_Billnum(String c_Billnum) {
		this.c_Billnum = c_Billnum;
	}

	public String getC_Creater() {
		return c_Creater;
	}

	public void setC_Creater(String c_Creater) {
		this.c_Creater = c_Creater;
	}

	public String getTC009() {
		return TC009;
	}

	public void setTC009(String tC009) {
		TC009 = tC009;
	}

	public String getTC023() {
		return TC023;
	}

	public void setTC023(String tC023) {
		TC023 = tC023;
	}

	public String getTC019() {
		return TC019;
	}

	public void setTC019(String tC019) {
		TC019 = tC019;
	}

	public String getTC020() {
		return TC020;
	}

	public void setTC020(String tC020) {
		TC020 = tC020;
	}

	public String getTC019C1() {
		return TC019C1;
	}

	public void setTC019C1(String tC019C1) {
		TC019C1 = tC019C1;
	}

	public String getC_TaxType() {
		return c_TaxType;
	}

	public void setC_TaxType(String c_TaxType) {
		this.c_TaxType = c_TaxType;
	}

	public String getC_TotalinChinese() {
		return c_TotalinChinese;
	}

	public void setC_TotalinChinese(String c_TotalinChinese) {
		this.c_TotalinChinese = c_TotalinChinese;
	}

	public String getC_MarkPic1() {
		return c_MarkPic1;
	}

	public void setC_MarkPic1(String c_MarkPic1) {
		this.c_MarkPic1 = c_MarkPic1;
	}

	public String getC_MarkPic2() {
		return c_MarkPic2;
	}

	public void setC_MarkPic2(String c_MarkPic2) {
		this.c_MarkPic2 = c_MarkPic2;
	}

	public String getTC005() {
		return TC005;
	}

	public void setTC005(String tC005) {
		TC005 = tC005;
	}
	
	public String getC_MarkUrl1() {
		return c_MarkUrl1;
	}

	public void setC_MarkUrl1(String c_MarkUrl1) {
		this.c_MarkUrl1 = c_MarkUrl1;
	}

	public String getC_MarkUrl2() {
		return c_MarkUrl2;
	}

	public void setC_MarkUrl2(String c_MarkUrl2) {
		this.c_MarkUrl2 = c_MarkUrl2;
	}
	
	public String getC_MarkUrl101() {
		return c_MarkUrl101;
	}

	public void setC_MarkUrl101(String c_MarkUrl101) {
		this.c_MarkUrl101 = c_MarkUrl101;
	}

	public String getC_MarkUrl102() {
		return c_MarkUrl102;
	}

	public void setC_MarkUrl102(String c_MarkUrl102) {
		this.c_MarkUrl102 = c_MarkUrl102;
	}

	public String getC_MarkUrl201() {
		return c_MarkUrl201;
	}

	public void setC_MarkUrl201(String c_MarkUrl201) {
		this.c_MarkUrl201 = c_MarkUrl201;
	}

	public String getC_MarkUrl202() {
		return c_MarkUrl202;
	}

	public void setC_MarkUrl202(String c_MarkUrl202) {
		this.c_MarkUrl202 = c_MarkUrl202;
	}

	public String getC_MarkUrl301() {
		return c_MarkUrl301;
	}

	public void setC_MarkUrl301(String c_MarkUrl301) {
		this.c_MarkUrl301 = c_MarkUrl301;
	}

	public String getC_MarkUrl302() {
		return c_MarkUrl302;
	}

	public void setC_MarkUrl302(String c_MarkUrl302) {
		this.c_MarkUrl302 = c_MarkUrl302;
	}

	public String getC_MarkUrl401() {
		return c_MarkUrl401;
	}

	public void setC_MarkUrl401(String c_MarkUrl401) {
		this.c_MarkUrl401 = c_MarkUrl401;
	}

	public String getC_MarkUrl402() {
		return c_MarkUrl402;
	}

	public void setC_MarkUrl402(String c_MarkUrl402) {
		this.c_MarkUrl402 = c_MarkUrl402;
	}

	public String getC_MarkUrl501() {
		return c_MarkUrl501;
	}

	public void setC_MarkUrl501(String c_MarkUrl501) {
		this.c_MarkUrl501 = c_MarkUrl501;
	}

	public String getC_MarkUrl502() {
		return c_MarkUrl502;
	}

	public void setC_MarkUrl502(String c_MarkUrl502) {
		this.c_MarkUrl502 = c_MarkUrl502;
	}
   	
	public String getC_MarkName1() {
		return c_MarkName1;
	}

	public void setC_MarkName1(String c_MarkName1) {
		this.c_MarkName1 = c_MarkName1;
	}

	public String getC_MarkName2() {
		return c_MarkName2;
	}

	public void setC_MarkName2(String c_MarkName2) {
		this.c_MarkName2 = c_MarkName2;
	}

	public String getC_MarkName3() {
		return c_MarkName3;
	}

	public void setC_MarkName3(String c_MarkName3) {
		this.c_MarkName3 = c_MarkName3;
	}

	public String getC_MarkName4() {
		return c_MarkName4;
	}

	public void setC_MarkName4(String c_MarkName4) {
		this.c_MarkName4 = c_MarkName4;
	}

	public String getC_MarkName5() {
		return c_MarkName5;
	}

	public void setC_MarkName5(String c_MarkName5) {
		this.c_MarkName5 = c_MarkName5;
	}

	public String getC_CreaterID() {
		return c_CreaterID;
	}

	public void setC_CreaterID(String c_CreaterID) {
		this.c_CreaterID = c_CreaterID;
	}

	public String getC_LastSignDate() {
		return c_LastSignDate;
	}

	public void setC_LastSignDate(String c_LastSignDate) {
		this.c_LastSignDate = c_LastSignDate;
	}

	public String getC_ShowMic() {
		return c_ShowMic;
	}

	public void setC_ShowMic(String c_ShowMic) {
		this.c_ShowMic = c_ShowMic;
	}

	public String getC_SignUrl() {
		return c_SignUrl;
	}

	public void setC_SignUrl(String c_SignUrl) {
		this.c_SignUrl = c_SignUrl;
	}

	public String getC_SchValue() {
		return c_SchValue;
	}

	public void setC_SchValue(String c_SchValue) {
		this.c_SchValue = c_SchValue;
	}

	public String getTC011En() {
		return TC011En;
	}

	public void setTC011En(String tC011En) {
		TC011En = tC011En;
	}

	public String getC_Cno() {
		return c_Cno;
	}

	public void setC_Cno(String c_Cno) {
		this.c_Cno = c_Cno;
	}
	
	public String getC_Pono1() {
		return c_Pono1;
	}

	public void setC_Pono1(String c_Pono1) {
		this.c_Pono1 = c_Pono1;
	}

	public String getC_Pono2() {
		return c_Pono2;
	}

	public void setC_Pono2(String c_Pono2) {
		this.c_Pono2 = c_Pono2;
	}

	public String getC_Pono3() {
		return c_Pono3;
	}

	public void setC_Pono3(String c_Pono3) {
		this.c_Pono3 = c_Pono3;
	}

	public String getC_Pono4() {
		return c_Pono4;
	}

	public void setC_Pono4(String c_Pono4) {
		this.c_Pono4 = c_Pono4;
	}

	public String getC_Pono5() {
		return c_Pono5;
	}

	public void setC_Pono5(String c_Pono5) {
		this.c_Pono5 = c_Pono5;
	}

	public String getC_MarkDesc101() {
		return c_MarkDesc101;
	}

	public void setC_MarkDesc101(String c_MarkDesc101) {
		this.c_MarkDesc101 = c_MarkDesc101;
	}

	public String getC_MarkDesc102() {
		return c_MarkDesc102;
	}

	public void setC_MarkDesc102(String c_MarkDesc102) {
		this.c_MarkDesc102 = c_MarkDesc102;
	}

	public String getC_MarkDesc201() {
		return c_MarkDesc201;
	}

	public void setC_MarkDesc201(String c_MarkDesc201) {
		this.c_MarkDesc201 = c_MarkDesc201;
	}

	public String getC_MarkDesc202() {
		return c_MarkDesc202;
	}

	public void setC_MarkDesc202(String c_MarkDesc202) {
		this.c_MarkDesc202 = c_MarkDesc202;
	}

	public String getC_MarkDesc301() {
		return c_MarkDesc301;
	}

	public void setC_MarkDesc301(String c_MarkDesc301) {
		this.c_MarkDesc301 = c_MarkDesc301;
	}

	public String getC_MarkDesc302() {
		return c_MarkDesc302;
	}

	public void setC_MarkDesc302(String c_MarkDesc302) {
		this.c_MarkDesc302 = c_MarkDesc302;
	}

	public String getC_MarkDesc401() {
		return c_MarkDesc401;
	}

	public void setC_MarkDesc401(String c_MarkDesc401) {
		this.c_MarkDesc401 = c_MarkDesc401;
	}

	public String getC_MarkDesc402() {
		return c_MarkDesc402;
	}

	public void setC_MarkDesc402(String c_MarkDesc402) {
		this.c_MarkDesc402 = c_MarkDesc402;
	}

	public String getC_MarkDesc501() {
		return c_MarkDesc501;
	}

	public void setC_MarkDesc501(String c_MarkDesc501) {
		this.c_MarkDesc501 = c_MarkDesc501;
	}

	public String getC_MarkDesc502() {
		return c_MarkDesc502;
	}

	public void setC_MarkDesc502(String c_MarkDesc502) {
		this.c_MarkDesc502 = c_MarkDesc502;
	}

	public String getC_ShowDtl1() {
		return c_ShowDtl1;
	}

	public void setC_ShowDtl1(String c_ShowDtl1) {
		this.c_ShowDtl1 = c_ShowDtl1;
	}

	public String getC_ShowDtl2() {
		return c_ShowDtl2;
	}

	public void setC_ShowDtl2(String c_ShowDtl2) {
		this.c_ShowDtl2 = c_ShowDtl2;
	}

	public String getC_ShowDtl3() {
		return c_ShowDtl3;
	}

	public void setC_ShowDtl3(String c_ShowDtl3) {
		this.c_ShowDtl3 = c_ShowDtl3;
	}

	public String getC_ShowDtl4() {
		return c_ShowDtl4;
	}

	public void setC_ShowDtl4(String c_ShowDtl4) {
		this.c_ShowDtl4 = c_ShowDtl4;
	}

	public String getC_ShowDtl5() {
		return c_ShowDtl5;
	}

	public void setC_ShowDtl5(String c_ShowDtl5) {
		this.c_ShowDtl5 = c_ShowDtl5;
	}

	public String getC_CustID1() {
		return c_CustID1;
	}

	public void setC_CustID1(String c_CustID1) {
		this.c_CustID1 = c_CustID1;
	}

	public String getC_CustID2() {
		return c_CustID2;
	}

	public void setC_CustID2(String c_CustID2) {
		this.c_CustID2 = c_CustID2;
	}

	public String getC_CustID3() {
		return c_CustID3;
	}

	public void setC_CustID3(String c_CustID3) {
		this.c_CustID3 = c_CustID3;
	}

	public String getC_CustID4() {
		return c_CustID4;
	}

	public void setC_CustID4(String c_CustID4) {
		this.c_CustID4 = c_CustID4;
	}

	public String getC_CustID5() {
		return c_CustID5;
	}

	public void setC_CustID5(String c_CustID5) {
		this.c_CustID5 = c_CustID5;
	}
}
