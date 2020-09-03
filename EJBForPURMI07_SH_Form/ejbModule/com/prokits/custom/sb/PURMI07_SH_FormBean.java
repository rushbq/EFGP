/**
 * 
 */
package com.prokits.custom.sb;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.Element;

import com.dsc.nana.domain.form.FormInstance;
import com.dsc.nana.services.exception.ServiceException;
import com.dsc.nana.util.Dom4jUtil;
import com.dsc.nana.util.logging.NaNaLog;
import com.dsc.nana.util.logging.NaNaLogFactory;


/**
 *
 * <!-- begin-user-doc -->
 * A generated session bean
 * <!-- end-user-doc -->
 * *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="PURMI07_SH_Form"	
 *           description="An EJB named PURMI07_SH_Form"
 *           display-name="PURMI07_SH_Form"
 *           jndi-name="PURMI07_SH_Form"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 * <!-- end-xdoclet-definition --> 
 * @generated
 */

public abstract class PURMI07_SH_FormBean implements javax.ejb.SessionBean {

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
			this.log = NaNaLogFactory.getLog(PURMI07_SH_FormBean.class);

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
	/**
	 * 
	 * <!-- begin-xdoclet-definition -->
	 * 
	 * @ejb.interface-method view-type="remote" <!-- end-xdoclet-definition -->
	 * @generated
	 * 
	 *            //TODO: Must provide implementation for bean method stub
	 */
	public FormInstance getFormData(FormInstance pForm) {
		// 取得表單欄位的值(使用fetchFieldValue)
		String mainID = pForm.fetchFieldValue("TC001"); // 單別
		String subID = pForm.fetchFieldValue("TC002"); // 單號
		String autoUpdate = pForm.fetchFieldValue("autoUpdate");  //判斷是否已自動更新(嘜頭資料)
		
		
		if(autoUpdate.equals("Y")){
			/* Flow:確認單據 to pdf產生 */
			System.out.println("--- [myBug]PURMI07(SH)-自訂嘜頭圖處理(1) Start ---");	
			System.out.println("PURID=" + mainID + subID);
			this.getLog().info("PURID=" + mainID + subID);

			
			/* [重置-自訂嘜頭圖暫存資料欄] */
			for(int row = 1;row <= 5; row++){				
				pForm.configureFieldValue("txt_MarkPic" + row + "01", "");	//正嘜圖
				pForm.configureFieldValue("txt_MarkPic" + row + "02", "");	//側嘜圖
				pForm.configureFieldValue("txt_MarkName" + row, "");
				pForm.configureFieldValue("txt_Pono" + row, "");  //客戶單號
				pForm.configureFieldValue("txt_MarkDesc" + row + "01", "");	//正嘜文字
				pForm.configureFieldValue("txt_MarkDesc" + row + "02", "");	//側嘜文字
				pForm.configureFieldValue("txt_CustID" + row, "");  //客戶代號
				pForm.configureFieldValue("hid_DtlData_" + row, "N"); //是否顯示資料
			}
			

			/*
			 * [單身]自訂嘜頭圖處理
			 * 取單身資料-> Group(getListByGroup) -> 回寫至單頭的txt_MarkPic101等欄位，至多5筆
			 * */
			List gridList = (List) pForm.getGridValueByID("gdBody1");
			Map myMap = getListByGroup(gridList);
			int limitRow = 5;
			int currRow = 1;
			
			//將資料Group
			Iterator iter = myMap.entrySet().iterator(); 
	        while (iter.hasNext()) { 
	        	//檔案限制
	        	if(currRow > limitRow){
	        		break;
	        	}
	        	
	        	//取MAP
	            Map.Entry entry = (Map.Entry) iter.next();
	            
	            //嘜頭名
	            String micName = entry.getKey().toString();
	            //嘜頭檔案名
	            String micValue = entry.getValue().toString();
	            //客戶單號
	            String myPono = "";
	            //正嘜文字
	            String myDesc1 = "";
	            //側嘜文字
	            String myDesc2 = "";
	            //客戶代號
	            String myCustID = "";
	            
	            
	            //比對List, 取得其他資料
	            for(int row = 0;row < gridList.size(); row++){
	            	Map gridItemMap = (Map) gridList.get(row);
	    			
	    			// 取得單身資料
	    			String tCustID = (String)gridItemMap.get("cm_CustID");
	    			String tMicID = (String)gridItemMap.get("cm_MicID");
	    			String tPono = (String)gridItemMap.get("c_Pono");
	    			String tDesc1 = (String)gridItemMap.get("cm_MicTxt1");
	    			String tDesc2 = (String)gridItemMap.get("cm_MicTxt2");
	    			
	    			//System.out.println("show-tCustID + tMicID:" + tCustID + tMicID);
	    			//System.out.println("show-micValue:" + micValue);
	    			
	    			if((tCustID + tMicID).equals(micValue)){
	    				myPono = tPono;
	    				myDesc1 = tDesc1;
	    				myDesc2 = tDesc2;
	    				myCustID = tCustID;
	    				//System.out.println("show:" + myDesc1);
	    				
	    				break;
	    			}
	            }
	            
	            //填入資料(最多5筆)
	            String pic1 = micValue + "1.jpg";
	            String pic2 = micValue + "2.jpg";
	            
	            pForm.configureFieldValue("txt_MarkPic" + currRow + "01", pic1);	//自訂正嘜圖(20180517改為帶預設嘜頭圖STDxxx.jpg,在pdf程式指定, 除了1525501/2002054001)
				pForm.configureFieldValue("txt_MarkPic" + currRow + "02", pic2);	//自訂側嘜圖(20180517改為帶預設嘜頭圖STDxxx.jpg,在pdf程式指定, 除了1525501/2002054001)
				pForm.configureFieldValue("txt_MarkName" + currRow, micName);
				pForm.configureFieldValue("txt_Pono" + currRow, myPono);  //客戶單號
				pForm.configureFieldValue("txt_MarkDesc" + currRow + "01", myDesc1);	//正嘜文字
				pForm.configureFieldValue("txt_MarkDesc" + currRow + "02", myDesc2);	//側嘜文字
				pForm.configureFieldValue("txt_CustID" + currRow, myCustID);  //客戶代號
				pForm.configureFieldValue("hid_DtlData_" + currRow, "Y"); //是否顯示資料

				//System.out.println("pic1" + currRow + ":" + pic1);
				//System.out.println("pic2" + currRow + ":" + pic2);
				//System.out.println("txt_Pono" + currRow + ":" + myPono);
				//System.out.println("myDesc1" + currRow + ":" + myDesc1);
				//System.out.println("myDesc2" + currRow + ":" + myDesc2);
			
	            currRow++;
	            
	        } 

			//調整maskFieldValue(Form資料設定完成後，請填入此段，表單才能回寫成功)
			pForm.setMaskFieldValues(pForm.getFieldValues());
			
			System.out.println("--- [myBug]PURMI07(SH)-自訂嘜頭圖處理 End(1) ---");
			
		}else{
			/* Flow:ERP to 第一關 */
			
			System.out.println("--- [myBug]PURMI07(SH)-自訂欄位資料取得 Start(2) ---");

			// 取得DB連結
			DataSource tPKDataSource = null;
			Connection tPKConnection = null;
			//Statement tPKStatement = null;
			ResultSet tPKResult = null;
			ResultSet tPKResultSet_Grid = null;
			PreparedStatement tPS = null;
			String dbName = "[SHPK2]";  //ERP資料庫名稱
			
			try {
				// 取得DB連線(查看nana_xxx.xml的<jndi-name>
				tPKDataSource = getJndiDataSource("NaNaPKSYS");
				tPKConnection = tPKDataSource.getConnection();
				//tPKStatement = tPKConnection.createStatement();
				
				// SQL - 單頭
				String sql = "";
				sql += "SELECT TOP 1 ";
				sql += " Sup.MA003 AS SupFullName, ISNULL(Sup.MA014, '') AS SupAddress, ISNULL(Sup.MA008, '') AS SupTel, ISNULL(Sup.MA010, '') AS SupFax";
				sql += " , ISNULL(Sup.MA011, '') AS SupEmail, ISNULL(Sup.MA005, '') AS Billnum, Base.TC012 AS LangType";
				sql += " , (CASE Base.TC018";
				sql += "	 WHEN '1' THEN '應稅內含'";
				sql += "	 WHEN '2' THEN '應稅外加'";
				sql += "	 WHEN '3' THEN '零稅率'";
				sql += "	 WHEN '4' THEN '免稅'";
				sql += "	 ELSE '不計稅' END";
				sql += "	) AS TaxType";
				sql += " , (CASE Base.TC018";
				sql += "	 WHEN '1' THEN '應稅內含'";
				sql += "	 WHEN '2' THEN '應稅外加'";
				sql += "	 WHEN '3' THEN 'Zero Tax'";
				sql += "	 WHEN '4' THEN '免稅'";
				sql += "	 ELSE '不計稅' END";
				sql += "	) AS TaxTypeEng";
				sql += " , ISNULL((SELECT TOP 1 MV047 FROM " + dbName + ".dbo.CMSMV WITH(NOLOCK) WHERE (MV001 = Base.TC011)), Base.TC011) AS EmpEngName";
				sql += " , ISNULL((SELECT TOP 1 MV020 FROM " + dbName + ".dbo.CMSMV WITH(NOLOCK) WHERE (MV001 = Base.TC011)), '') AS EmpMail";
				sql += " , [PKSYS].[dbo].[myfn_NumToCNum](Base.TC019 + Base.TC020) AS TotalinChinese";
				sql += " , UPPER([PKSYS].[dbo].[myfn_NumToENum](Base.TC019 + Base.TC020)) AS TotalinEnglish";
				sql += " , ISNULL((CASE WHEN OrderMain.TC054 = '' THEN EPSMD.MD004 ELSE OrderMain.TC054 END), '') AS MarkText1";
				sql += " , ISNULL((CASE WHEN OrderMain.TC055 = '' THEN EPSMD.MD005 ELSE OrderMain.TC055 END), '') AS MarkText2";
				// --有訂單時(參考單別TD013<>')=('客戶代號'+嘜頭代號)
				//sql += " , (CASE";
				//sql += "	  WHEN DT.TD013 <> '' THEN ISNULL(OrderMain.TC004 + OrderMain.TC034, '')";
				// --其他單別='Z999'+單別
				//sql += "	  ELSE 'Z999' + Base.TC001";
				//sql += "	 END) AS MarkPic"; // --'嘜頭圖示前置檔名+正(1)/側(2)+(於程式中加附檔名)=xxxx1.jpg'
				sql += ", ('Z999' + Base.TC001) AS MarkPic";

				sql += " FROM " + dbName + ".dbo.PURTC Base WITH(NOLOCK)";
				sql += "  INNER JOIN " + dbName + ".dbo.PURTD DT WITH(NOLOCK) ON Base.TC001 = DT.TD001 AND Base.TC002 = DT.TD002";
				sql += "  INNER JOIN " + dbName + ".dbo.PURMA Sup WITH(NOLOCK) ON Base.TC004 = Sup.MA001";
				sql += "  LEFT JOIN " + dbName + ".dbo.COPTD OrderItem WITH(NOLOCK) ON DT.TD013 = OrderItem.TD001 AND DT.TD021 = OrderItem.TD002 AND DT.TD023 = OrderItem.TD003";
				sql += "  LEFT JOIN " + dbName + ".dbo.COPTC OrderMain WITH(NOLOCK) ON OrderItem.TD001 = OrderMain.TC001 AND OrderItem.TD002 = OrderMain.TC002";
				sql += "  LEFT JOIN " + dbName + ".dbo.EPSMD WITH(NOLOCK) ON OrderMain.TC004 = EPSMD.MD001 AND OrderMain.TC034 = EPSMD.MD002";

				sql += " WHERE (Base.TC001 = ?) AND (Base.TC002 = ?)";

				// print sql
				//System.out.println("單頭SQL:" + sql);
				this.getLog().info("PURID=" + mainID + subID);

				// 取得DB欄位值
				String txtSupFullName = ""; // 廠商全名
				String txtSupAddress = ""; // 廠商地址
				String txtSupTel = ""; // 廠商電話
				String txtSupFax = ""; // 廠商傳真
				String txtBillnum = ""; // 發票統編
				String txtTaxType = ""; // 課稅別
				String txtTotalinChinese = ""; // 總計國字
				String txtMarkPic1 = ""; // 正嘜圖示
				String txtMarkPic2 = ""; // 側嘜圖示
				String txtMarkText1 = ""; // 正嘜
				String txtMarkText2 = ""; // 側嘜
				String txtEmail = ""; //廠商 email
				String txtLangType = "";
				String txtEmpEngName = ""; //採購人員英文
				String txtEmpMail = ""; //採購人員EMAIL
				

				// 執行SQL,取回結果
				//tPKResult = tPKStatement.executeQuery(sql);
				tPS = tPKConnection.prepareStatement(sql);
				tPS.setString(1, mainID);
				tPS.setString(2, subID);
				tPKResult = tPS.executeQuery();
				
				System.out.println("[myBug]開始執行單頭SQL:");
				
				
				while (tPKResult.next()) {
					txtSupFullName = tPKResult.getString("SupFullName"); // 廠商全名
					txtSupAddress = tPKResult.getString("SupAddress"); // 廠商地址
					txtSupTel = tPKResult.getString("SupTel"); // 廠商電話
					txtSupFax = tPKResult.getString("SupFax"); // 廠商傳真
					txtBillnum = tPKResult.getString("Billnum"); // 發票統編

					txtLangType = tPKResult.getString("LangType"); // 廠商全名
					
					if(txtLangType.equals("1")){
						txtTaxType = tPKResult.getString("TaxType"); // 課稅別
						txtTotalinChinese = tPKResult.getString("TotalinChinese"); // 總計國字
					}else{
						txtTaxType = tPKResult.getString("TaxTypeEng"); // 課稅別
						txtTotalinChinese = tPKResult.getString("TotalinEnglish"); // 總計國字
					}
					
					txtMarkPic1 = tPKResult.getString("MarkPic") + "1.jpg"; // 正嘜圖示(預設嘜頭)
					txtMarkPic2 = tPKResult.getString("MarkPic") + "2.jpg"; // 側嘜圖示(預設嘜頭)
					txtMarkText1 = tPKResult.getString("MarkText1"); // 正嘜(預設嘜頭)
					txtMarkText2 = tPKResult.getString("MarkText2"); // 側嘜(預設嘜頭)
					txtEmail = tPKResult.getString("SupEmail"); // Sup main Email(不使用)
					txtEmpEngName = tPKResult.getString("EmpEngName");  //採購人員英文名
					txtEmpMail = tPKResult.getString("EmpMail");  //採購人員EMAIL

				}

				System.out.println("[myBug]開始回寫表單自訂欄位-單頭Start");
				
				// 回寫表單自訂欄位-單頭(目標欄位名,回傳值)
				pForm.configureFieldValue("txt_SupFullName", txtSupFullName);
				pForm.configureFieldValue("txt_SupAddress", txtSupAddress);
				pForm.configureFieldValue("txt_SupTel", txtSupTel);
				pForm.configureFieldValue("txt_SupFax", txtSupFax);
				pForm.configureFieldValue("txt_Billnum", txtBillnum);
				pForm.configureFieldValue("txt_TaxType", txtTaxType);
				pForm.configureFieldValue("txt_TotalinChinese", txtTotalinChinese);
				pForm.configureFieldValue("txt_MarkPic1", txtMarkPic1);
				pForm.configureFieldValue("txt_MarkPic2", txtMarkPic2);
				pForm.configureFieldValue("txt_MarkText1", txtMarkText1);
				pForm.configureFieldValue("txt_MarkText2", txtMarkText2);
				pForm.configureFieldValue("txt_SupEmail", txtEmail);
				pForm.configureFieldValue("TC011En", txtEmpEngName);
				pForm.configureFieldValue("TC011EMail", txtEmpMail);
				pForm.configureFieldValue("autoUpdate", "Y");

				System.out.println("[myBug]開始回寫表單自訂欄位-單頭End");

				// [Log]
				this.getLog().info("執行單頭SQL: " + sql);
			
				
				/* tSQL - 單身, 單身資料處理 */
				// [Log]
				this.getLog().info("單身資料處理 Start");
				System.out.println("[myBug]單身資料處理 Start");
				
				// 取得Grid的{xxxID}欄位資料，在執行sql取得資訊後，帶回grid的{xxxName}內
				List tgrdLine1 = (List) pForm.getGridValueByID("gdBody1");
				String combineGridID = "";
				
				// 將Grid的序號TD003組合成字串
				for (int row = 0; row < tgrdLine1.size(); row++) {
					Map gridItemMap = (Map) tgrdLine1.get(row);

					//單身序號 
					String tGridID = (String)gridItemMap.get("TD003");
					combineGridID += "'" + tGridID + "',";
				}
				String FullGridID = combineGridID.substring(0, combineGridID.length()-1);
				System.out.println("FullGridID=" + FullGridID);
				
				
				// -- 執行單身SQL,取得資料並回寫到表單 Start --
				String gSql = "";

				//單身SQL
				gSql += "SELECT";
				//客戶品號
				gSql += " RTRIM(ISNULL(OrderItem.TD014, '')) AS c_CustModelNo";
				gSql += ",(";
				gSql += "	SELECT TOP 1 ISNULL(MB007, '')";
				gSql += "	FROM " + dbName + ".dbo.PURMB WITH(NOLOCK)";
				gSql += "	WHERE MB002 = Base.TC004 AND MB001 = DT.TD004";
				gSql += "	ORDER BY MB014 DESC";
				gSql += ") AS c_SupModelNo";
				gSql += ", CAST(Prod.MB201 AS INT) AS c_MB201, CAST(Prod.MB073 AS INT) AS c_MB073";
				gSql += ", CAST(Prod.MB074 AS FLOAT) AS c_MB074, CAST(Prod.MB075 AS FLOAT) AS c_MB075, CAST(Prod.MB071 AS FLOAT) AS c_MB071";
				
				//把換行字元(CHAR(10),CHAR(13))取代成空白
				//取代字元-單引號
				gSql += ", REPLACE(REPLACE(REPLACE(ISNULL(COPMG.MG200, ''),CHAR(13), ''), CHAR(10), ''), CHAR(39),'<#quot>') AS c_SpRemark";					
				
				//產品品名(分語系)
				//文字取代:換行字元/單引號/雙引號
				gSql += ", REPLACE(REPLACE(REPLACE(REPLACE(";
				gSql += " ISNULL((CASE WHEN Base.TC012 = '1' THEN myProd.Model_Name_zh_CN ELSE myProd.Model_Name_en_US END), RTRIM(Prod.MB002))";
				gSql += ",CHAR(13), ''), CHAR(10), ''), CHAR(39),'<#quot>'), CHAR(34),'<#quot2>') AS c_ModelName";
				
				//產品規格
				//取代字元-單引號
				gSql += ", REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(myProd.Model_Desc_zh_CN, ISNULL(RTRIM(Prod.MB003), '')), CHAR(39),'<#quot>'), CHAR(34),'<#quot2>'), CHAR(10), ''), CHAR(13), '') AS c_ModelSpec";
				
				//訂單-客戶代號TC004, 嘜頭代號TC034, 客戶單號TC012, 正嘜TC054(文字), 側嘜TC055(文字)
				gSql += ", RTRIM(OrderMain.TC004) AS cm_CustID";
				gSql += ", OrderMain.TC034 AS cm_MicID, '' AS cm_MicName";
				
				//訂單嘜頭文字
				//把換行字元(CHAR(10),CHAR(13))取代成 |#|, 輸出到PDF再取代成換行
				//取代字元=單引號
				//把空白字元取代成|_|,輸出到PDF再取代成空白(因寫入GRID時會自動去空白)
				gSql += ", REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(OrderMain.TC054, ''),CHAR(13), ''), CHAR(10), '|#|'), CHAR(39),'<#quot>'), ' ','|_|') AS cm_MicTxt1";
				gSql += ", REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(OrderMain.TC055, ''),CHAR(13), ''), CHAR(10), '|#|'), CHAR(39),'<#quot>'), ' ','|_|') AS cm_MicTxt2";
				
				//客戶單號
				gSql += ", OrderMain.TC012 AS c_Pono";
				gSql += " FROM " + dbName + ".dbo.PURTC Base WITH(NOLOCK)";
				gSql += "  INNER JOIN " + dbName + ".dbo.PURTD DT WITH(NOLOCK) ON Base.TC001 = DT.TD001 AND Base.TC002 = DT.TD002";
				gSql += "  INNER JOIN " + dbName + ".dbo.INVMB Prod WITH(NOLOCK) ON DT.TD004 = Prod.MB001";
				gSql += "  LEFT JOIN [ProductCenter].dbo.Prod_Item myProd WITH(NOLOCK) ON myProd.Model_No COLLATE Chinese_Taiwan_Stroke_BIN = Prod.MB001";
				gSql += "  LEFT JOIN " + dbName + ".dbo.COPTD OrderItem WITH(NOLOCK) ON DT.TD013 = OrderItem.TD001 AND DT.TD021 = OrderItem.TD002 AND DT.TD023 = OrderItem.TD003";
				gSql += "  LEFT JOIN " + dbName + ".dbo.COPTC OrderMain WITH(NOLOCK) ON OrderItem.TD001 = OrderMain.TC001 AND OrderItem.TD002 = OrderMain.TC002";
				gSql += "  LEFT JOIN " + dbName + ".dbo.EPSMD WITH(NOLOCK) ON OrderMain.TC004 = EPSMD.MD001 AND OrderMain.TC034 = EPSMD.MD002";
				gSql += "  LEFT JOIN " + dbName + ".dbo.COPMG WITH(NOLOCK) ON OrderMain.TC004 = COPMG.MG001 AND DT.TD004 = COPMG.MG002 AND OrderItem.TD014 = COPMG.MG003";
				gSql += " WHERE (Base.TC001 = ?) AND (Base.TC002 = ?) AND (DT.TD003 IN (" + FullGridID + "))";
				gSql += " ORDER BY Base.TC001, Base.TC002, DT.TD003";

				// print sql
				//System.out.println("[myBug]單身SQL:" + gSql);
				// [Log]
				this.getLog().info("執行單身SQL: " + gSql);

				// 執行SQL (IN條件無法直接用參數帶入)
				//tPKResultSet_Grid = tPKStatement.executeQuery(gSql);
				tPS = tPKConnection.prepareStatement(gSql);
				tPS.setString(1, mainID);
				tPS.setString(2, subID);
				tPKResultSet_Grid = tPS.executeQuery();
				
				// 宣告參數接收
				String c_CustModelNo = "";
				String c_SupModelNo = "";
				String c_SpRemark = "";
				String c_MB201 = "";
				String c_MB073 = "";
				String c_MB074 = "";
				String c_MB075 = "";
				String c_MB071 = "";
				String c_ModelName = "";
				String c_ModelSpec = "";
				String cm_CustID = "";
				String cm_MicID = "";
				String cm_MicName = "";
				String cm_MicTxt1 = "";
				String cm_MicTxt2 = "";
				String c_Pono = "";
				int row = 0;
				
				
				//Get XML element
				Document tXmlDocument = Dom4jUtil.buildXmlDoc(pForm.getFieldValues());
				Element tRootElement = tXmlDocument.getRootElement();
				Element tGridElement = tRootElement.element("gdBody1");
	

				// 取得ResultSet值, 將查詢值以string接收;
				// getString後接Table的欄位代號，或是select的順序，啟始值是1
				while (tPKResultSet_Grid.next()) { 
					// 取值 
					c_CustModelNo = tPKResultSet_Grid.getString("c_CustModelNo");
					c_SupModelNo = tPKResultSet_Grid.getString("c_SupModelNo");
					c_SpRemark = tPKResultSet_Grid.getString("c_SpRemark");
					c_MB201 = tPKResultSet_Grid.getString("c_MB201");
					c_MB073 = tPKResultSet_Grid.getString("c_MB073");
					c_MB074 = tPKResultSet_Grid.getString("c_MB074");
					c_MB075 = tPKResultSet_Grid.getString("c_MB075");
					c_MB071 = tPKResultSet_Grid.getString("c_MB071");					
					c_ModelName = tPKResultSet_Grid.getString("c_ModelName");
					c_ModelSpec = tPKResultSet_Grid.getString("c_ModelSpec");
					
					//嘜頭資料(COPTD帶出)
					cm_CustID = tPKResultSet_Grid.getString("cm_CustID");
					cm_MicID = tPKResultSet_Grid.getString("cm_MicID");
					cm_MicName = tPKResultSet_Grid.getString("cm_MicName");
					cm_MicTxt1 = tPKResultSet_Grid.getString("cm_MicTxt1");
					cm_MicTxt2 = tPKResultSet_Grid.getString("cm_MicTxt2");
					c_Pono = tPKResultSet_Grid.getString("c_Pono");
					

					System.out.println("開始重組grid- " + row);
					
					//2998改寫
					// 重組grid
					configureGridOneRowColumnValue_New(tGridElement, "c_SupModelNo", row, c_SupModelNo==null?"":c_SupModelNo);
					configureGridOneRowColumnValue_New(tGridElement, "c_SpRemark", row, c_SpRemark==null?"":c_SpRemark);
					configureGridOneRowColumnValue_New(tGridElement, "c_MB201", row, c_MB201);
					configureGridOneRowColumnValue_New(tGridElement, "c_MB073", row, c_MB073);
					configureGridOneRowColumnValue_New(tGridElement, "c_MB074", row, c_MB074);
					configureGridOneRowColumnValue_New(tGridElement, "c_MB075", row, c_MB075);
					configureGridOneRowColumnValue_New(tGridElement, "c_MB071", row, c_MB071);
					configureGridOneRowColumnValue_New(tGridElement, "c_ModelName", row, c_ModelName);
					configureGridOneRowColumnValue_New(tGridElement, "TD005", row, c_ModelName);
					configureGridOneRowColumnValue_New(tGridElement, "c_ModelSpec", row, c_ModelSpec==null?"":c_ModelSpec);
					//若有參考單據，則自動帶入嘜頭資料
					configureGridOneRowColumnValue_New(tGridElement, "cm_ModelNo", row, c_CustModelNo==null?"":c_CustModelNo); //客戶品號cm_ModelNo(嘜頭)
					configureGridOneRowColumnValue_New(tGridElement, "cm_CustID", row, cm_CustID==null?"":cm_CustID); //客戶代號cm_CustID (COPTC.TC004)
					configureGridOneRowColumnValue_New(tGridElement, "cm_MicID", row, cm_MicID==null?"":cm_MicID); //嘜頭代號cm_MicID (COPTC.TC034)
					configureGridOneRowColumnValue_New(tGridElement, "cm_MicName", row, cm_MicName==null?"":cm_MicName); //嘜頭圖示名稱cm_MicName
					configureGridOneRowColumnValue_New(tGridElement, "c_Pono", row, c_Pono==null?"":c_Pono); //客戶單號COPTC.TC012
					configureGridOneRowColumnValue_New(tGridElement, "cm_MicTxt1", row, cm_MicTxt1==null?"":cm_MicTxt1); //訂單正嘜文字
					configureGridOneRowColumnValue_New(tGridElement, "cm_MicTxt2", row, cm_MicTxt2==null?"":cm_MicTxt2); //訂單側嘜文字
			
					System.out.println("結束重組grid- " + row);
					
					row++;

				}
				
				//回寫grid
				pForm.setFieldValues(Dom4jUtil.toPrettyXml(tXmlDocument));
			
				// -- 執行單身SQL,取得資料並回寫到表單 End --
				
				// [Log]
				this.getLog().info("單身資料處理 End");
				System.out.println("[myBug]單身資料處理 End");
				
				//調整maskFieldValue(Form資料設定完成後，請填入此段，表單才能回寫成功)
				pForm.setMaskFieldValues(pForm.getFieldValues());
				
				System.out.println("--- [myBug]PURMI07(SH)-自訂欄位資料取得 End(2) ---");

			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("--- [myBug]PURMI07(SH)-自訂欄位資料取得 ERROR ---");
				this.getLog().info("--- PURMI07(SH)-自訂欄位資料取得 ERROR ---");
				throw new ServiceException(ex);

			} finally {
				// ** 重要 ** Release DB 連線
				closeResultSet(tPKResultSet_Grid);
				closeResultSet(tPKResult);
				//closeStmt(tPKStatement);
				closeConn(tPKConnection);
				closeStmt(tPS);
			}
			
		}


		return pForm;
	}

	//取得單身自訂嘜頭資料(將資料群組化)
	private Map getListByGroup(List list) {
        Map result = new HashMap();
        
        for (int row = 0; row < list.size(); row++) {
			Map gridItemMap = (Map) list.get(row);
			
			// 取得原資料的單身資料
			String tCustID = (String)gridItemMap.get("cm_CustID");
			String tMicID = (String)gridItemMap.get("cm_MicID");
			String tMicName = (String)gridItemMap.get("cm_MicName");
						
        	if(tCustID != ""){
        		String setFileName = tCustID + tMicID;
        		result.put(tMicName.equals("")?setFileName:tMicName, setFileName);
        	}
        }
        
        return result;
    }

	
	// 取得DataSource
	private DataSource getJndiDataSource(String pDataSource) throws Exception {
		DataSource tDs = null;
		Context tCtx = null;

		try {
			tCtx = new InitialContext();
			tDs = (DataSource) tCtx.lookup("java:" + pDataSource);

		} finally {
			if (tCtx != null) {
				try {
					tCtx.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				tCtx = null;
			}

		}

		if (tDs == null) {
			throw new Exception("Can not get DataSrouce from jndi name "
					+ pDataSource);

		}

		return tDs;
	}

	//[重組Grid]
	private void configureGridOneRowColumnValue_New(Element pGridElement, String pColID, int pIndex, String pValue)
			throws Exception {

		Element tRecordNode = null;
		Element tCridColumn = null;

		if (pGridElement != null) {
			Element tRecordsNode = pGridElement.element("records");// see if this element is grid
			if (tRecordsNode != null) {
				List tRecordList = tRecordsNode.elements();
				for (int i = 0; i < tRecordList.size(); i++) { // for every 'record'(row)
					
					if (i == pIndex) {
						tRecordNode = (Element) tRecordList.get(i);
						tCridColumn = getGridColByID(tRecordNode, pColID);
						tCridColumn.setText(pValue);
						break;// 找到指定行後就終止
					}
				}
			}
		}

	}	
	

	//[重組Grid]-搜尋Grid欄位
	private Element getGridColByID(Element pRecordNode, String pColId) {
		List tItemNodes = pRecordNode.elements();
		for (int j = 0; j < tItemNodes.size(); j++) {// every column > 'item'
			Element tItemNode = (Element) tItemNodes.get(j);
			String tColName = tItemNode.attributeValue("id");
			if (tColName.equals(pColId)) {
				return tItemNode;
			}
		}
		return null;
	}

	private void closeConn(Connection pConn) {
		if (pConn != null) {
			try {
				pConn.close();
			} catch (SQLException e) {
				if (this.getLog().isErrorEnabled()) {
					this.getLog().error(
							"(" + this.hashCode()
									+ ")Close closeConn Fail!!ErrMsg:"
									+ e.getMessage());
				}
			}
		}
	}

	private void closePreparedStmt(PreparedStatement pPreparedStmt) {
		if (pPreparedStmt != null) {
			try {
				pPreparedStmt.close();
			} catch (SQLException e) {
				if (this.getLog().isErrorEnabled()) {
					this.getLog().error(
							"(" + this.hashCode()
									+ ")Close PreparedStatement Fail!!ErrMsg:"
									+ e.getMessage());
				}
			}
		}
	}

	private void closeStmt(Statement pStmt) {
		if (pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				if (this.getLog().isErrorEnabled()) {
					this.getLog().error(
							"(" + this.hashCode()
									+ ")Close PreparedStatement Fail!!ErrMsg:"
									+ e.getMessage());
				}
			}
		}
	}

	private void closeResultSet(ResultSet pRs) {
		if (pRs != null) {
			try {
				pRs.close();
			} catch (SQLException e) {
				if (this.getLog().isErrorEnabled()) {
					this.getLog().error(
							"(" + this.hashCode()
									+ ")Close ResultSet Fail!!ErrMsg:"
									+ e.getMessage());
				}
			}
		}
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
	public PURMI07_SH_FormBean() {
		// TODO Auto-generated constructor stub
	}
}
