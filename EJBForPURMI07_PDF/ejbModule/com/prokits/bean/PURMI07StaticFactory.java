package com.prokits.bean;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * LOCAL 測試用資料 */
public class PURMI07StaticFactory {
	public static List<PURMI07> createBeanCollection() {
		List<PURMI07> tFormList = new ArrayList<PURMI07>(1);
		
		PURMI07 tForm = new PURMI07();
		
		/* 單頭資料 */
		tForm.setPoType("F"); //採購類型
		tForm.setTC004("102001"); // 供應廠商代號
		tForm.setTC004C("亨龍"); // 供應廠商名稱
		tForm.setTC001("3301"); // 採購單別
		tForm.setTC002("A170705001"); // 採購單號
		tForm.setTC052("路人甲"); // 聯絡人
		tForm.setTC021("新北市新店區之新店大碧潭999巷99號9樓之一"); // 送貨地址1
		tForm.setTC022("新北市新店區之新店小碧潭888巷88號8樓之二"); // 送貨地址2
		tForm.setTC027("234"); // 付款條件ID
		tForm.setTC027C("120 出貨後30天匯款(T/T)"); // 付款條件Name
		tForm.setTC005("NTD"); // 交易幣別
		tForm.setTC007("無"); // 價格條件
		tForm.setTC006("1"); // 匯率
		tForm.setTC024("2017/07/26"); // 單據日期
		tForm.setTC011("10341"); // 採購人員ID
		tForm.setTC011C("高的貴"); // 採購人員Name
		tForm.setTC011En("Sunny Kao");// 採購人員EngName
		tForm.setTC017("空運"); // 運輸方式
		tForm.setTC012("1"); // 列印語系(1:中文/2:英文)

		tForm.setC_SupFullName("江蘇金鹿集團進出口公司  A01-0210(JIANGSU JINLU GROUP IMP.&EXP.CO.,LTD)"); // 廠商全名
		tForm.setC_SupAddress("亨龍的地址亨龍的地址亨龍的地址亨龍的地址"); // 廠商地址
		tForm.setC_SupTel("(02) 2399-9999"); // 廠商電話
		tForm.setC_SupFax("(02) 2399-9998"); // 廠商傳真
		tForm.setC_Billnum("987654321"); // 發票統編
		tForm.setC_Creater("高得貴"); // 製單
		tForm.setC_CreaterID("10255"); // 製單人工號(用來抓簽名檔)
		tForm.setC_LastSignDate("2018/1/15"); // 最後簽核日
		tForm.setC_ShowMic("Y"); //是否要顯示預設嘜頭		
		tForm.setC_SignUrl("http://ref.prokits.com.tw/ERP_Files/Signature/10081.jpg");
		tForm.setC_SchValue("選項一/選項二/"); //學生期選項
		tForm.setC_Cno("PK1234"); //箱號

		
		// [column footer]-此段改為table方式 start
		/*
		tForm.setTC009("我是備註我是備註我是備註我是備註我是備註我是"); // 備註		
		tForm.setTC023(numFmt.format(Double.parseDouble("800"))); // 數量合計(設置千分位)
		tForm.setTC019(numFmt.format(Double.parseDouble("15600"))); // 金額合計(設置千分位)
		tForm.setTC020(numFmt.format(Double.parseDouble("780"))); // 稅額(設置千分位)
		tForm.setTC019C1(numFmt.format(Double.parseDouble("16380"))); // 總計(設置千分位)
		tForm.setC_TaxType("應稅外加"); // 課稅別(TC018C)
		tForm.setC_TotalinChinese("壹萬陸仟參佰捌拾"); // 總計(TC019C1)
		*/
		//---- end

		// [footer]-預設嘜頭
		tForm.setC_MarkPic1("檔名1.jpg"); //正嘜圖示
		tForm.setC_MarkPic2("檔名2.jpg"); //側嘜圖示
		tForm.setC_MarkUrl1("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/prokit(II)/EPS/Z99933071.jpg"); //正嘜圖示
		tForm.setC_MarkUrl2("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/prokit(II)/EPS/Z99933042.jpg"); //側嘜圖示

		/* 自訂嘜頭 (20180517改為帶預設圖, 除了1525501/2002054001) */
		/*
		tForm.setC_MarkUrl101("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/ERP_GPTEST/EPS/SUNGLOWEC31.jpg"); //自訂正嘜圖示1
		tForm.setC_MarkUrl102("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard/1525501-102.jpg"); //自訂側嘜圖示1
		tForm.setC_MarkUrl201("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard/STD101.jpg"); //自訂正嘜圖示2
		tForm.setC_MarkUrl202("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard/STD102.jpg"); //自訂側嘜圖示2
		tForm.setC_MarkUrl301(null); //自訂正嘜圖示3
		tForm.setC_MarkUrl302(null); //自訂側嘜圖示3
		tForm.setC_MarkUrl401(null); //自訂正嘜圖示4
		tForm.setC_MarkUrl402(null); //自訂側嘜圖示4
		tForm.setC_MarkUrl501(null); //自訂正嘜圖示5
		tForm.setC_MarkUrl502(null); //自訂側嘜圖示5
		
		tForm.setC_MarkName1("Mic001"); //自訂嘜頭名1
		tForm.setC_MarkName2("Mic002"); //自訂嘜頭名2
		tForm.setC_MarkName3(""); //自訂嘜頭名3
		tForm.setC_MarkName4(""); //自訂嘜頭名4
		tForm.setC_MarkName5(""); //自訂嘜頭名5
		tForm.setC_Pono1("Test001");
		tForm.setC_Pono2("Test002");
		tForm.setC_Pono3("");
		tForm.setC_Pono4("");
		tForm.setC_Pono5("");
		tForm.setC_ShowDtl1("Y");
		tForm.setC_ShowDtl2("Y");
		tForm.setC_ShowDtl3("N");
		tForm.setC_ShowDtl4("N");
		tForm.setC_ShowDtl5("N");
		tForm.setC_CustID1("1180401");
		tForm.setC_CustID2("102001");
		tForm.setC_CustID3("");
		tForm.setC_CustID4("");
		tForm.setC_CustID5("");
		
		tForm.setC_MarkDesc101("|_||_||_||_||_||_||_||_|／＼|_||#||_||_||_||_||_||_|／|_||_||_||_|＼|_||#||_||_||_||_|／|_||_||_||_||_||_||_||_|＼|#||_||_|／|_||_||_||_|GM|_||_||_||_||_||_|＼|#|／|_||_||_||_||_||_||_||_||_||_||_||_||_||_||_||_|＼|#|¯¯¯¯¯¯¯¯¯¯|_||#|PRAHA|#|C/NO.|#|MADE|_|IN|_|TAIWAN");
		tForm.setC_MarkDesc102("PO NO.:031717T\nCTN NO.:\nITEM NO. 依客戶品號\nQ'TY : PCS\nN.W. : KGS\nG.W. : KGS\nMEAS'T: CUFT");
		tForm.setC_MarkDesc201(null);
		tForm.setC_MarkDesc202("PO NO.:031717T\nCTN NO.:\nITEM NO. 依客戶品號\nQ'TY : PCS\nN.W. : KGS\nG.W. : KGS\nMEAS'T: CUFT");
		tForm.setC_MarkDesc301(null);
		tForm.setC_MarkDesc302(null);
		tForm.setC_MarkDesc401(null);
		tForm.setC_MarkDesc402(null);
		tForm.setC_MarkDesc501(null);
		tForm.setC_MarkDesc502(null);
		*/
		
		/* 嘜頭List */
		List<micList> grid_mic = new ArrayList<micList>(1);

		micList tMic = new micList();
		tMic.setCustID("1180401");
		tMic.setMarkName("Mic001");
		tMic.setPono("Test001");
		tMic.setMarkDesc1("|_||_||_||_||_||_||_||_|／＼|_||#||_||_||_||_||_||_|／|_||_||_||_|＼|_||#||_||_||_||_|／|_||_||_||_||_||_||_||_|＼|#||_||_|／|_||_||_||_|GM|_||_||_||_||_||_|＼|#|／|_||_||_||_||_||_||_||_||_||_||_||_||_||_||_||_|＼|#|¯¯¯¯¯¯¯¯¯¯|_||#|PRAHA|#|C/NO.|#|MADE|_|IN|_|TAIWAN");
		tMic.setMarkDesc2("PO NO.:031717T\nCTN NO.:\nITEM NO. 依客戶品號\nQ'TY : PCS\nN.W. : KGS\nG.W. : KGS\nMEAS'T: CUFT");
		tMic.setMarkUrl1("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/ERP_GPTEST/EPS/SUNGLOWEC31.jpg");
		tMic.setMarkUrl2("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard/1525501-102.jpg");		
		grid_mic.add(tMic);
		
		tMic = new micList();
		tMic.setCustID("102001");
		tMic.setMarkName("Mic002");
		tMic.setPono("Test002");
		tMic.setMarkDesc1(null);
		tMic.setMarkDesc2("PO NO.:8888T\nCTN NO.:\nITEM NO. 依客戶品號\nQ'TY : PCS\nN.W. : KGS\nG.W. : KGS\nMEAS'T: CUFT");
		tMic.setMarkUrl1("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard/STD101.jpg");
		tMic.setMarkUrl2("http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard/STD102.jpg");
		grid_mic.add(tMic);

		
		/* 單身資料 */
		List<gdBody1Bean> grid1 = new ArrayList<gdBody1Bean>(1);
		
		/*
		for(int row=0;row<3;row++){
			gdBody1Bean tGrid = new gdBody1Bean();
			
			tGrid.setC_CustModelNo("1PK-036S036Cust"); // 客戶品號
			tGrid.setC_SupModelNo("MT-8100"); // 廠商品號
			tGrid.setC_SpRemark("1.內盒條碼: 00082911000024外箱條碼: 000829130000962.2013/7/23 已完成更換新款包材 & 本體logo.3.客貼需要內盒貼+外箱貼."); // 產品特別注意事項
			tGrid.setC_MB201("100"); // 包裝資料-內盒
			tGrid.setC_MB073("1200"); // 包裝資料-外箱
			tGrid.setC_MB074("12"); // 包裝資料-淨重
			tGrid.setC_MB075("13.5"); // 包裝資料-毛重
			tGrid.setC_MB071("2.1"); // 包裝資料-材積
			tGrid.setCm_MicName("嘜頭" + row+1);
			tGrid.setCm_ModelNo("1PK-036S-12345");
			tGrid.setC_ModelName("Insulated Tweezers (120mm)  (Please use some antirust oil)");
			tGrid.setC_ModelSpec("120mm,無磁性");
			tGrid.setC_Pono("C12345678"); //客戶單號

			tGrid.setTD003("0" + (row+1)); // 序號
			tGrid.setTD004("1PK-3003FD17-MA" + row+1); // 產品品號
			tGrid.setTD005("3 3/4自動數位錶,附頻率.電 容."); // 產品品名
			tGrid.setTD203(row + "_**請特別注意：鐵桿不可歪斜** **外箱請不要有PK LOGO** 1)本體客戶STEREN logo 印刷 2)6支裝一內透明塑膠盒 3)塑膠盒外加彩盒包裝，彩盒由貴司印製， (PK提供檔案) 4)每內盒(24)/外箱(96)請貼由寶工提供之條碼，外箱條碼請貼在正嘜的右上角"); // 備註
			tGrid.setTD008("200"); // 採購數量
			tGrid.setTD009("PCE"); // 單位
			tGrid.setTD012("2017/08/14"); // 預交日
			tGrid.setTD014("我是單身備註備註備註.."); // 採購單身備註
			tGrid.setTD010("100"); // 單價
			tGrid.setTD011("20000"); // 金額
			tGrid.setTD013("2212"); // 參考單別
			tGrid.setTD021("1707050"); // 參考單號
			tGrid.setTD023("00" + row+1); // 參考序號
			tGrid.setTD204("2");// 起始箱號
			tGrid.setTD205("7");// 截止箱號
			tGrid.setTD206("A");// 字軌
			tGrid.setLang("1");
			
			grid1.add(tGrid);
		}
		*/
		
		//-- test
		gdBody1Bean tGrid = new gdBody1Bean();
		
		tGrid.setC_SupModelNo("MT-8100"); // 廠商品號
		tGrid.setC_SpRemark(""); // 產品特別注意事項
		tGrid.setC_MB201("100"); // 包裝資料-內盒
		tGrid.setC_MB073("1200"); // 包裝資料-外箱
		tGrid.setC_MB074("12"); // 包裝資料-淨重
		tGrid.setC_MB075("13.5"); // 包裝資料-毛重
		tGrid.setC_MB071("2.1"); // 包裝資料-材積
		tGrid.setCm_MicName("嘜頭");
		tGrid.setCm_ModelNo("1PK-036S-12345");
		tGrid.setC_ModelName("12水管鉗PK卡包裝");
		tGrid.setC_ModelSpec("綠色麻面沾塑手把");
		tGrid.setC_Pono("C12345678"); //客戶單號

		tGrid.setTD003("01"); // 序號
		tGrid.setTD004("1PK-3003FD17-MA"); // 產品品號
		tGrid.setTD005("3 3/4自動數位錶,附頻率.電 容."); // 產品品名
		tGrid.setTD203(""); // 備註
		tGrid.setTD008("200"); // 採購數量
		tGrid.setTD009("PCE"); // 單位
		tGrid.setTD012("2017/08/14"); // 預交日
		tGrid.setTD014("."); // 採購單身備註
		tGrid.setTD010("100"); // 單價
		tGrid.setTD011("20000"); // 金額
		tGrid.setTD013("2212"); // 參考單別
		tGrid.setTD021("1707050"); // 參考單號
		tGrid.setTD023("00"); // 參考序號
		tGrid.setTD204("2");// 起始箱號
		tGrid.setTD205("7");// 截止箱號
		tGrid.setTD206("A");// 字軌
		tGrid.setLang("1");
		
		grid1.add(tGrid);
		
		
        tGrid = new gdBody1Bean();
		
		tGrid.setC_SupModelNo("MT-8100"); // 廠商品號
		tGrid.setC_SpRemark(""); // 產品特別注意事項
		tGrid.setC_MB201("100"); // 包裝資料-內盒
		tGrid.setC_MB073("1200"); // 包裝資料-外箱
		tGrid.setC_MB074("12"); // 包裝資料-淨重
		tGrid.setC_MB075("13.5"); // 包裝資料-毛重
		tGrid.setC_MB071("2.1"); // 包裝資料-材積
		tGrid.setCm_MicName("嘜頭");
		tGrid.setCm_ModelNo("1PK-036S-12345");
		tGrid.setC_ModelName("直流電源供應器(0~30V/5A) 單電源");
		tGrid.setC_ModelSpec("");
		tGrid.setC_Pono("C12345678"); //客戶單號

		tGrid.setTD003("02"); // 序號
		tGrid.setTD004("1PK-3003FD17-MA"); // 產品品號
		tGrid.setTD005("3 3/4自動數位錶,附頻率.電 容."); // 產品品名
		tGrid.setTD203("1.輸入:AC220~240V 50/60Hz,輸出:單電源 DC0~30V 0~5A,上殼具 有提把,用歐規圓形插頭. 符VDE三芯電源線,產品 需符合CE安規和RoHS, 面板PK型號+Logo. 2.彩 盒與中英文說明書設計稿 由寶工提供, 貴廠印刷."); // 備註
		tGrid.setTD008("200"); // 採購數量
		tGrid.setTD009("PCE"); // 單位
		tGrid.setTD012("2017/08/14"); // 預交日
		tGrid.setTD014("."); // 採購單身備註
		tGrid.setTD010("100"); // 單價
		tGrid.setTD011("20000"); // 金額
		tGrid.setTD013("2212"); // 參考單別
		tGrid.setTD021("1707050"); // 參考單號
		tGrid.setTD023("00"); // 參考序號
		tGrid.setTD204("2");// 起始箱號
		tGrid.setTD205("7");// 截止箱號
		tGrid.setTD206("A");// 字軌
		tGrid.setLang("1");
		
		grid1.add(tGrid);
		
		/*
	    tGrid = new gdBody1Bean();
		
		tGrid.setC_SupModelNo("MT-8100"); // 廠商品號
		tGrid.setC_SpRemark(""); // 產品特別注意事項
		tGrid.setC_MB201("100"); // 包裝資料-內盒
		tGrid.setC_MB073("1200"); // 包裝資料-外箱
		tGrid.setC_MB074("12"); // 包裝資料-淨重
		tGrid.setC_MB075("13.5"); // 包裝資料-毛重
		tGrid.setC_MB071("2.1"); // 包裝資料-材積
		tGrid.setCm_MicName("嘜頭");
		tGrid.setCm_ModelNo("1PK-036S-12345");
		tGrid.setC_ModelName("電子零件盒-PP材質 5PCS/SET");
		tGrid.setC_ModelSpec("180x125x80mm,PK彩標收 縮");
		tGrid.setC_Pono("C12345678"); //客戶單號

		tGrid.setTD003("02"); // 序號
		tGrid.setTD004("1PK-3003FD17-MA"); // 產品品號
		tGrid.setTD005("3 3/4自動數位錶,附頻率.電 容."); // 產品品名
		tGrid.setTD203("高耐衝擊PP材質 5個盒 子+16條插柱+4個頂蓋 熱縮膜+彩貼包裝(彩標 由寶工提供)"); // 備註
		tGrid.setTD008("200"); // 採購數量
		tGrid.setTD009("PCE"); // 單位
		tGrid.setTD012("2017/08/14"); // 預交日
		tGrid.setTD014("."); // 採購單身備註
		tGrid.setTD010("100"); // 單價
		tGrid.setTD011("20000"); // 金額
		tGrid.setTD013("2212"); // 參考單別
		tGrid.setTD021("1707050"); // 參考單號
		tGrid.setTD023("00"); // 參考序號
		tGrid.setTD204("2");// 起始箱號
		tGrid.setTD205("7");// 截止箱號
		tGrid.setTD206("A");// 字軌
		tGrid.setLang("1");
		
		grid1.add(tGrid);*/
		//-- test end
		

		/* 單身表尾 */
		//-- 宣告數字格式化 --
		NumberFormat numFmt = NumberFormat.getNumberInstance();  
		List<PURMI07> gridFooter = new ArrayList<PURMI07>(1);
		PURMI07 tForm_Footer = new PURMI07();
		tForm_Footer.setTC009("我是備註我是備註我是備註我是備註我是備註我是"); // 備註
		
		tForm_Footer.setTC023(numFmt.format(Double.parseDouble("800"))); // 數量合計(設置千分位)
		tForm_Footer.setTC019(numFmt.format(Double.parseDouble("15600"))); // 金額合計(設置千分位)
		tForm_Footer.setTC020(numFmt.format(Double.parseDouble("780"))); // 稅額(設置千分位)
		tForm_Footer.setTC019C1(numFmt.format(Double.parseDouble("16380"))); // 總計(設置千分位)
		tForm_Footer.setC_TaxType("應稅外加"); // 課稅別(TC018C)
		tForm_Footer.setC_TotalinChinese("壹萬陸仟參佰捌拾"); // 總計(TC019C1)
		gridFooter.add(tForm_Footer);
		
		//將單身資料塞入
		tForm.setDetails(grid1);
		tForm.setDetails_footer(gridFooter);
		tForm.setDetails_micList(grid_mic);
		
		
		//將所有資料塞入List
		tFormList.add(tForm);

		return tFormList;
	}

}
