/**
 * 
 */
package com.prokits.custom.sb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.dsc.nana.domain.form.FormInstance;
import com.dsc.nana.services.exception.ServiceException;
import com.dsc.nana.util.logging.NaNaLog;
import com.dsc.nana.util.logging.NaNaLogFactory;
import com.prokits.bean.PURMI07;
import com.prokits.bean.gdBody1Bean;
import com.prokits.bean.micList;


/**
 *
 * <!-- begin-user-doc -->
 * A generated session bean
 * <!-- end-user-doc -->
 * *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="PURMI07_SH_PDF"	
 *           description="An EJB named PURMI07_SH_PDF"
 *           display-name="PURMI07_SH_PDF"
 *           jndi-name="PURMI07_SH_PDF"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 * <!-- end-xdoclet-definition --> 
 * @generated
 */

public abstract class PURMI07_SH_PDFBean implements javax.ejb.SessionBean {

	/** 
	 * <!-- begin-xdoclet-definition --> 
	 * <!-- end-xdoclet-definition --> 
	 * @generated
	 */
	private static final long serialVersionUID = 1L;

	/* 寫log的工具 */
	private NaNaLog log;

	/*
	 * 屬性log的getter
	 */
	private NaNaLog getLog() {
		if (this.log == null) {
			this.log = NaNaLogFactory.getLog(PURMI07_SH_PDFBean.class);

		}

		return this.log;
	}
	
	/** 
	 *
	 * <!-- begin-xdoclet-definition --> 
	 * @ejb.create-method view-type="remote"
	 * <!-- end-xdoclet-definition --> 
	 * @generated
	 *
	 * //TODO: Must provide implementation for bean create stub
	 */
	public void ejbCreate() {
	}

	/** 
	 *
	 * <!-- begin-xdoclet-definition --> 
	 * @ejb.interface-method view-type="remote"
	 * <!-- end-xdoclet-definition --> 
	 * @generated
	 *
	 * //TODO: Must provide implementation for bean method stub
	 */
	public String foo(String param) {
		return null;
	}
	
	/* ---------- 自訂程式 Start ---------- */
	public static JasperDesign jasperDesign;
	public static JasperPrint jasperPrint;
	public static JasperReport jasperReport;
	// public static String reportTemplateUrl =
	// "C:\\EFGP5631\\jboss-4.2.3.GA\\server\\default\\deploy\\zReportUtil.war\\report\\iReportForm\\TE007.jrxml";
	// iReport XML檔位置
	public static String reportTemplateUrl = System
			.getProperty("jboss.home.dir")
			+ "\\server\\default\\deploy\\zReportUtil.war\\report\\iPURMI07_SH\\iPURMI07_SH.jrxml";
	
	
	/**
	 * 
	 * <!-- begin-xdoclet-definition -->
	 * 
	 * @ejb.interface-method view-type="remote" <!-- end-xdoclet-definition -->
	 * @generated
	 * 
	 *            //TODO: Must provide implementation for bean method stub
	 */
	public void createPDF(FormInstance tFormInstance) {
		try {
			// 取得表單欄位的值(使用fetchFieldValue)
			String mainID = tFormInstance.fetchFieldValue("TC001"); // 單別
			String subID = tFormInstance.fetchFieldValue("TC002"); // 單號
			String lang = tFormInstance.fetchFieldValue("TC012"); // 列印語系(1:中文/2:英文)
			 
			System.out.println("--- createPDF Start ---");			
			
			//載入語系定義檔
			String langName = "zh_CN";
			
			//判斷語系參數(1:中文, 2:英文)
			/*
			if(lang.equals("2")){
				langName = "en_US";
			}
			*/
			
			//語系路徑
			String bundlePath = System.getProperty("jboss.home.dir")+ "\\server\\default\\deploy\\zReportUtil.war\\report\\iPURMI07_SH\\ResBundle\\myRes_"
			 + langName + ".properties";

			System.out.println("bundlePath=" + bundlePath);
			
			FileInputStream fis = new FileInputStream(bundlePath);
			ResourceBundle resourceBundle = new PropertyResourceBundle(fis);
			
			//iReport			
			jasperDesign = JRXmlLoader.load(getReportFile());
			jasperReport = JasperCompileManager.compileReport(jasperDesign);			

			//取得欄位資料
			List tList = findReportData(tFormInstance);
			//System.out.println("tList.size === " + tList.size());
			   
			//iReport執行檔路徑
			File jasperFile = new File(
					System.getProperty("jboss.home.dir")
							+ "\\server\\default\\deploy\\zReportUtil.war\\report\\iPURMI07_SH\\iPURMI07_SH.jasper");
									
			System.out.println("取得iReport執行檔,Get File:"+jasperFile.isFile());
			
			byte[] bytes = new byte[0];

			System.out.println("--開始執行PDF轉檔--");
			//System.out.println("jasperFile.getPath()="+jasperFile.getPath());
			//System.out.println("getReportParameter()="+getReportParameter());
			bytes = JasperRunManager
					.runReportToPdf(jasperFile.getPath(), getReportParameter(lang, resourceBundle),
							new JRBeanCollectionDataSource(tList));
			
			System.out.println("runReportToPDF:" + bytes.length);
			
			// PDF匯出路徑(檔名為單別+單號)
			String pdfName = mainID + subID + ".pdf";
			FileOutputStream fos = new FileOutputStream(
					System.getProperty("jboss.home.dir")
							+ "\\server\\default\\deploy\\NaNaWeb.war\\report\\iPURMI07_SH\\"
							+ pdfName);
			fos.write(bytes);
			fos.flush();
			fos.close();
			System.out.println("PDF已輸出:" + pdfName);
			System.out.println("--- createPDF End ---");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	// 取得表單資料, 讓PDF取用
	public static List findReportData(FormInstance tFormInstance) {

		List tList = new ArrayList(1);

		PURMI07 tForm = new PURMI07();
		
		String lang = tFormInstance.fetchFieldValue("TC012");// 列印語系(1:中文/2:英文)

		/* 單頭資料 */
		tForm.setPoType(tFormInstance.fetchFieldValue("drp_POtype")); // 採購類型
		tForm.setTC004(tFormInstance.fetchFieldValue("TC004")); // 供應廠商代號
		tForm.setTC004C(tFormInstance.fetchFieldValue("TC004C")); // 供應廠商名稱
		tForm.setTC001(tFormInstance.fetchFieldValue("TC001")); // 採購單別
		tForm.setTC002(tFormInstance.fetchFieldValue("TC002")); // 採購單號
		tForm.setTC052(tFormInstance.fetchFieldValue("TC052")); // 聯絡人
		tForm.setTC021(tFormInstance.fetchFieldValue("TC021")); // 送貨地址1
		tForm.setTC022(tFormInstance.fetchFieldValue("TC022")); // 送貨地址2
		tForm.setTC027(tFormInstance.fetchFieldValue("TC027")); // 付款條件ID
		tForm.setTC027C(tFormInstance.fetchFieldValue("TC027C")); // 付款條件Name
		tForm.setTC005(tFormInstance.fetchFieldValue("TC005")); // 交易幣別
		tForm.setTC007(tFormInstance.fetchFieldValue("TC007")); // 價格條件
		tForm.setTC006(tFormInstance.fetchFieldValue("TC006")); // 匯率
		tForm.setTC024(tFormInstance.fetchFieldValue("TC024")); // 單據日期
		tForm.setTC011(tFormInstance.fetchFieldValue("TC011")); // 採購人員ID
		tForm.setTC011C(tFormInstance.fetchFieldValue("TC011C")); // 採購人員Name
		tForm.setTC011En(tFormInstance.fetchFieldValue("TC011En")); // 採購人員EngName
		tForm.setTC017(tFormInstance.fetchFieldValue("TC017")); // 運輸方式
		tForm.setTC012(lang); // 列印語系(1:中文/2:英文)
		
		tForm.setC_SupFullName(tFormInstance.fetchFieldValue("txt_SupFullName")); // 廠商全名
		tForm.setC_SupAddress(tFormInstance.fetchFieldValue("txt_SupAddress")); // 廠商地址
		tForm.setC_SupTel(tFormInstance.fetchFieldValue("txt_SupTel")); // 廠商電話
		tForm.setC_SupFax(tFormInstance.fetchFieldValue("txt_SupFax")); // 廠商傳真
		tForm.setC_Billnum(tFormInstance.fetchFieldValue("txt_Billnum")); // 發票統編
		tForm.setC_Creater(tFormInstance.fetchFieldValue("txt_CreaterName")); // 製單人NAME
		tForm.setC_CreaterID(tFormInstance.fetchFieldValue("txt_CreaterID")); // 製單人工號		
		tForm.setC_ShowMic(tFormInstance.fetchFieldValue("rdo_ShowMic")); //是否要顯示預設嘜頭
		tForm.setC_SchValue(tFormInstance.fetchFieldValue("txt_SchValue")); //設變選項
		tForm.setC_Cno(tFormInstance.fetchFieldValue("txt_c_Cno")); //箱號
		
		//--- 最後簽核日 Check Start ---
		String txt_LastSignDate = tFormInstance.fetchFieldValue("txt_LastSignDate");
		//目前時間
		Date date = new Date();
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//進行轉換
		String dateString = sdf.format(date);
		//若為空白則填目前日期
		tForm.setC_LastSignDate(txt_LastSignDate.equals("")? dateString : txt_LastSignDate);
		//--- 最後簽核日 Check End ---
		
		//--- 簽名檔Url Check Start ---
		//取最後簽核者工號(若空值則抓採購人員ID(ERP CMSMV))
		String signID = tFormInstance.fetchFieldValue("txt_LastSigner").equals("") ? tFormInstance.fetchFieldValue("TC011") : tFormInstance.fetchFieldValue("txt_LastSigner");
		String signUrl = ImageExists("http://ref.prokits.com.tw/ERP_Files/Signature/", signID + ".jpg");
		tForm.setC_SignUrl(signUrl.equals("") ? null : signUrl);
		//--- 簽名檔Url Check End ---
		

		//-- 宣告數字格式化 --
		NumberFormat numFmt = NumberFormat.getNumberInstance();  
		

		// [footer]-預設嘜頭
		String getCompanyID = tFormInstance.fetchFieldValue("CompanyId");
		String getPic1 = tFormInstance.fetchFieldValue("txt_MarkPic1");
		String getPic2 = tFormInstance.fetchFieldValue("txt_MarkPic2");
		//圖檔路徑ref
		String getPicUrl = "http://ref.prokits.com.tw/ERP_Files/Cust_Mark/" + getCompanyID + "/EPS/";

		// http://ref.prokits.com.tw/ERP_Files/Cust_Mark/prokit(II)/EPS/Z99933011.jpg
		String myMarkUrl = "";
		
		tForm.setC_MarkPic1(getPic1); // 正嘜圖示(若圖片未顯示，請檢查所屬公司別的資料夾是否有檔案)
		tForm.setC_MarkPic2(getPic2); // 側嘜圖示
		
		myMarkUrl = ImageExists(getPicUrl, getPic1);
		tForm.setC_MarkUrl1(myMarkUrl.equals("") ? null : myMarkUrl); // 正嘜圖示
		
		myMarkUrl = ImageExists(getPicUrl, getPic2);
		tForm.setC_MarkUrl2(myMarkUrl.equals("") ? null : myMarkUrl); // 側嘜圖示

		/* ----- 自訂嘜頭 Start ----- */
		String getPic101 = tFormInstance.fetchFieldValue("txt_MarkPic101");
		String getPic102 = tFormInstance.fetchFieldValue("txt_MarkPic102");
		String getPic201 = tFormInstance.fetchFieldValue("txt_MarkPic201");
		String getPic202 = tFormInstance.fetchFieldValue("txt_MarkPic202");
		String getPic301 = tFormInstance.fetchFieldValue("txt_MarkPic301");
		String getPic302 = tFormInstance.fetchFieldValue("txt_MarkPic302");
		String getPic401 = tFormInstance.fetchFieldValue("txt_MarkPic401");
		String getPic402 = tFormInstance.fetchFieldValue("txt_MarkPic402");
		String getPic501 = tFormInstance.fetchFieldValue("txt_MarkPic501");
		String getPic502 = tFormInstance.fetchFieldValue("txt_MarkPic502");
		
		String getMarkName1 = tFormInstance.fetchFieldValue("txt_MarkName1");
		String getMarkName2 = tFormInstance.fetchFieldValue("txt_MarkName2");
		String getMarkName3 = tFormInstance.fetchFieldValue("txt_MarkName3");
		String getMarkName4 = tFormInstance.fetchFieldValue("txt_MarkName4");
		String getMarkName5 = tFormInstance.fetchFieldValue("txt_MarkName5");
		String getPono1 = tFormInstance.fetchFieldValue("txt_Pono1");
		String getPono2 = tFormInstance.fetchFieldValue("txt_Pono2");
		String getPono3 = tFormInstance.fetchFieldValue("txt_Pono3");
		String getPono4 = tFormInstance.fetchFieldValue("txt_Pono4");
		String getPono5 = tFormInstance.fetchFieldValue("txt_Pono5");
		String getDesc101 = tFormInstance.fetchFieldValue("txt_MarkDesc101");
		String getDesc102 = tFormInstance.fetchFieldValue("txt_MarkDesc102");
		String getDesc201 = tFormInstance.fetchFieldValue("txt_MarkDesc201");
		String getDesc202 = tFormInstance.fetchFieldValue("txt_MarkDesc202");
		String getDesc301 = tFormInstance.fetchFieldValue("txt_MarkDesc301");
		String getDesc302 = tFormInstance.fetchFieldValue("txt_MarkDesc302");
		String getDesc401 = tFormInstance.fetchFieldValue("txt_MarkDesc401");
		String getDesc402 = tFormInstance.fetchFieldValue("txt_MarkDesc402");
		String getDesc501 = tFormInstance.fetchFieldValue("txt_MarkDesc501");
		String getDesc502 = tFormInstance.fetchFieldValue("txt_MarkDesc502");
		String getShowDtl1 = tFormInstance.fetchFieldValue("hid_DtlData_1");
		String getShowDtl2 = tFormInstance.fetchFieldValue("hid_DtlData_2");
		String getShowDtl3 = tFormInstance.fetchFieldValue("hid_DtlData_3");
		String getShowDtl4 = tFormInstance.fetchFieldValue("hid_DtlData_4");
		String getShowDtl5 = tFormInstance.fetchFieldValue("hid_DtlData_5");
		String getCustID1 = tFormInstance.fetchFieldValue("txt_CustID1");
		String getCustID2 = tFormInstance.fetchFieldValue("txt_CustID2");
		String getCustID3 = tFormInstance.fetchFieldValue("txt_CustID3");
		String getCustID4 = tFormInstance.fetchFieldValue("txt_CustID4");
		String getCustID5 = tFormInstance.fetchFieldValue("txt_CustID5");
		

		/* 嘜頭List */
		List grid_mic = new ArrayList(1);
		
		micList tMic = new micList();
		
		if((getShowDtl1.equals("") ? "N" : getShowDtl1).equals("Y")){
			tMic.setCustID(getCustID1.equals("") ? null : getCustID1);
			tMic.setMarkName(getMarkName1.equals("") ? null : getMarkName1);
			tMic.setPono(getPono1.equals("") ? null : getPono1);
			tMic.setMarkDesc1(getDesc101.equals("") ? null : getDesc101);
			tMic.setMarkDesc2(getDesc102.equals("") ? null : getDesc102);
			tMic.setMarkUrl1(checkImage(ImageExists(getPicUrl, getPic101), getCustID1, "101", getDesc101));
			tMic.setMarkUrl2(checkImage(ImageExists(getPicUrl, getPic102), getCustID1, "102", getDesc102));		
			grid_mic.add(tMic);
		}
		
		if((getShowDtl2.equals("") ? "N" : getShowDtl2).equals("Y")){
			tMic = new micList();
			tMic.setCustID(getCustID2.equals("") ? null : getCustID2);
			tMic.setMarkName(getMarkName2.equals("") ? null : getMarkName2);
			tMic.setPono(getPono2.equals("") ? null : getPono2);
			tMic.setMarkDesc1(getDesc201.equals("") ? null : getDesc201);
			tMic.setMarkDesc2(getDesc202.equals("") ? null : getDesc202);
			tMic.setMarkUrl1(checkImage(ImageExists(getPicUrl, getPic201), getCustID2, "201", getDesc201));
			tMic.setMarkUrl2(checkImage(ImageExists(getPicUrl, getPic202), getCustID2, "202", getDesc202));		
			grid_mic.add(tMic);
		}
		
		
		if((getShowDtl3.equals("") ? "N" : getShowDtl3).equals("Y")){
			tMic = new micList();
			tMic.setCustID(getCustID3.equals("") ? null : getCustID3);
			tMic.setMarkName(getMarkName3.equals("") ? null : getMarkName3);
			tMic.setPono(getPono3.equals("") ? null : getPono3);
			tMic.setMarkDesc1(getDesc301.equals("") ? null : getDesc301);
			tMic.setMarkDesc2(getDesc302.equals("") ? null : getDesc302);
			tMic.setMarkUrl1(checkImage(ImageExists(getPicUrl, getPic301), getCustID3, "301", getDesc301));
			tMic.setMarkUrl2(checkImage(ImageExists(getPicUrl, getPic302), getCustID3, "302", getDesc302));		
			grid_mic.add(tMic);
		}
		
		if((getShowDtl4.equals("") ? "N" : getShowDtl4).equals("Y")){
			tMic = new micList();
			tMic.setCustID(getCustID4.equals("") ? null : getCustID4);
			tMic.setMarkName(getMarkName4.equals("") ? null : getMarkName4);
			tMic.setPono(getPono4.equals("") ? null : getPono4);
			tMic.setMarkDesc1(getDesc401.equals("") ? null : getDesc401);
			tMic.setMarkDesc2(getDesc402.equals("") ? null : getDesc402);
			tMic.setMarkUrl1(checkImage(ImageExists(getPicUrl, getPic401), getCustID4, "401", getDesc401));
			tMic.setMarkUrl2(checkImage(ImageExists(getPicUrl, getPic402), getCustID4, "402", getDesc402));		
			grid_mic.add(tMic);
		}
		
		if((getShowDtl5.equals("") ? "N" : getShowDtl5).equals("Y")){
			tMic = new micList();
			tMic.setCustID(getCustID5.equals("") ? null : getCustID5);
			tMic.setMarkName(getMarkName5.equals("") ? null : getMarkName5);
			tMic.setPono(getPono5.equals("") ? null : getPono5);
			tMic.setMarkDesc1(getDesc501.equals("") ? null : getDesc501);
			tMic.setMarkDesc2(getDesc502.equals("") ? null : getDesc502);
			tMic.setMarkUrl1(checkImage(ImageExists(getPicUrl, getPic501), getCustID5, "501", getDesc501));
			tMic.setMarkUrl2(checkImage(ImageExists(getPicUrl, getPic502), getCustID5, "502", getDesc502));		
			grid_mic.add(tMic);
		}
		/* ----- 自訂嘜頭 End ----- */
				
		
		/* 單身表尾 - 金額部份 (使用Table格式才不會亂跑) */
		List gridFooter = new ArrayList(1);
		PURMI07 tForm_Footer = new PURMI07();
		
		tForm_Footer.setTC009(tFormInstance.fetchFieldValue("TC009")); // 備註
		tForm_Footer.setTC023(numFmt.format(Double.parseDouble(tFormInstance.fetchFieldValue("TC023")))); // 數量合計(設置千分位)
		tForm_Footer.setTC019(numFmt.format(Double.parseDouble(tFormInstance.fetchFieldValue("TC019")))); // 金額合計(設置千分位)
		tForm_Footer.setTC020(numFmt.format(Double.parseDouble(tFormInstance.fetchFieldValue("TC020")))); // 稅額(設置千分位)
		tForm_Footer.setTC019C1(numFmt.format(Double.parseDouble(tFormInstance.fetchFieldValue("TC019C1")))); // 總計(設置千分位)
		tForm_Footer.setC_TaxType(tFormInstance.fetchFieldValue("txt_TaxType")); // 課稅別(TC018C)
		tForm_Footer.setC_TotalinChinese(tFormInstance
				.fetchFieldValue("txt_TotalinChinese")); // 總計(TC019C1)(中文大寫)		
		gridFooter.add(tForm_Footer);		
	
				
		/* 單身資料 */
		List tFormGrid = tFormInstance.getGridValueByID("gdBody1");
		if (tFormGrid.size() > 0) {
			List grid0 = new ArrayList(1);
			//System.out.println("FormGrid.size = " + tFormGrid.size());
			for (int t = 0; t < tFormGrid.size(); t++) {
				Map tRow = (Map) tFormGrid.get(t);
				gdBody1Bean tGrid = new gdBody1Bean();

				//tGrid.setC_CustModelNo((String) tRow.get("c_CustModelNo")); // 客戶品號
				tGrid.setC_SupModelNo((String) tRow.get("c_SupModelNo")); // 廠商品號
				tGrid.setC_SpRemark((String) tRow.get("c_SpRemark")); // 產品特別注意事項
				tGrid.setC_MB201((String) tRow.get("c_MB201")); // 包裝資料-內盒
				tGrid.setC_MB073((String) tRow.get("c_MB073")); // 包裝資料-外箱
				tGrid.setC_MB074((String) tRow.get("c_MB074")); // 包裝資料-淨重
				tGrid.setC_MB075((String) tRow.get("c_MB075")); // 包裝資料-毛重
				tGrid.setC_MB071((String) tRow.get("c_MB071")); // 包裝資料-材積

				tGrid.setTD003((String) tRow.get("TD003")); // 序號
				tGrid.setTD004((String) tRow.get("TD004")); // 產品品號
				tGrid.setTD005((String) tRow.get("TD005")); // 產品品名
				tGrid.setTD203((String) tRow.get("TD203")); // 備註
				tGrid.setTD008((String) tRow.get("TD008")); // 採購數量
				tGrid.setTD009((String) tRow.get("TD009")); // 單位
				tGrid.setTD012((String) tRow.get("TD012")); // 預交日
				tGrid.setTD014((String) tRow.get("TD014")); // 採購單身備註
				
				//轉成金額格式
				tGrid.setTD010(numFmt.format(Double.parseDouble((String) tRow.get("TD010")))); // 單價				
				tGrid.setTD011(numFmt.format(Double.parseDouble((String) tRow.get("TD011")))); // 金額
			
				tGrid.setTD013((String) tRow.get("TD013")); // 參考單別
				tGrid.setTD021((String) tRow.get("TD021")); // 參考單號
				tGrid.setTD023((String) tRow.get("TD023")); // 參考序號
				tGrid.setTD204((String) tRow.get("TD204"));// 起始箱號
				tGrid.setTD205((String) tRow.get("TD205"));// 截止箱號
				tGrid.setTD206((String) tRow.get("TD206"));// 字軌
				
				tGrid.setCm_ModelNo((String) tRow.get("cm_ModelNo")); // 對應客戶品號
				tGrid.setCm_MicName((String) tRow.get("cm_MicName")); // 定義嘜頭圖示名稱
				tGrid.setC_ModelName((String) tRow.get("c_ModelName")); // 品名
				tGrid.setC_ModelSpec((String) tRow.get("c_ModelSpec")); // 規格
				tGrid.setC_Pono((String) tRow.get("c_Pono")); //客戶單號
				
				tGrid.setLang(lang); //單頭的語系(給TABLE判斷用)
				

				grid0.add(tGrid);
			}			

			//將單身資料塞入
			tForm.setDetails(grid0);
			tForm.setDetails_footer(gridFooter);
			tForm.setDetails_micList(grid_mic);
		}

		//將所有資料塞入List
		tList.add(tForm);
		
		return tList;
	}

	// get a parameter to pass into jrxml
	// 自訂報表參數(1:中文/2:英文)
	public static Map getReportParameter(String myLang, ResourceBundle resourceBundle) {
		Map parameters = new HashMap();
		
		Locale locale;
		
		//判斷語系參數
		if(myLang.equals("2")){
			locale = new Locale("en", "US");
					
		}else{
			locale = new Locale("zh", "TW");
		}
		parameters.put(JRParameter.REPORT_LOCALE, locale);
		parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
		
		// parameters.put("參數標籤", "參數內容");
		return parameters;
	}

	// return the report filename
	public static String getReportFile() {
		return reportTemplateUrl;
	}

	// 判斷圖片是否存在
	public static String ImageExists(String URLName, String FileName) {
		try {
			if (FileName == "") {
				return "";
			}

			String url = URLName + FileName;
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(url)
					.openConnection();
			con.setRequestMethod("HEAD");
			if ((con.getResponseCode() == HttpURLConnection.HTTP_OK)) {
				return url;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	//自訂嘜頭圖:當指定客戶圖片檔不存在時,取得預設圖片,其他客戶直接使用預設圖
	private static String checkImage(String imgUrl, String custID, String lastFileName, String markDesc){
		String frontFileName = "http://ref.prokits.com.tw/ERP_Files/Cust_Mark/Standard-SH/";
		String fullFileName = "";
		
		if(custID.equals("1525501") || custID.equals("2002054001")){
			if(imgUrl.equals("")){
				fullFileName = frontFileName + custID + "-" + lastFileName + ".jpg";
							
			}else{
				fullFileName = imgUrl;
				
			}			
			
		}else{			
			if(imgUrl.equals("") && markDesc.equals("")){
				//無圖無字:帶預設圖(圖片有文字)
				fullFileName = frontFileName + "STD" + lastFileName + "_txt.jpg";
			}else if(imgUrl.equals("")){
				//無圖有字:帶預設圖(圖片無文字)
				fullFileName = frontFileName + "STD" + lastFileName + ".jpg";
				
			}else{
				fullFileName = imgUrl;
				
			}
		}
		
		return fullFileName;
	}
	
	/* ---------- 自訂程式 End ---------- */
	

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	
	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public PURMI07_SH_PDFBean() {
		// TODO Auto-generated constructor stub
	}
}
