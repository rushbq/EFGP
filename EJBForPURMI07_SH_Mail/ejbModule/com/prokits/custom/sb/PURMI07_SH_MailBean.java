/**
 * 
 */
package com.prokits.custom.sb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import com.dsc.nana.domain.form.FormInstance;
import com.dsc.nana.services.exception.ServiceException;
import com.dsc.nana.util.logging.NaNaLog;
import com.dsc.nana.util.logging.NaNaLogFactory;


/**
 *
 * <!-- begin-user-doc -->
 * A generated session bean
 * <!-- end-user-doc -->
 * *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="PURMI07_SH_Mail"	
 *           description="An EJB named PURMI07_SH_Mail"
 *           display-name="PURMI07_SH_Mail"
 *           jndi-name="PURMI07_SH_Mail"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 * <!-- end-xdoclet-definition --> 
 * @generated
 */

public abstract class PURMI07_SH_MailBean implements javax.ejb.SessionBean {

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
			this.log = NaNaLogFactory.getLog(PURMI07_SH_MailBean.class);

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
	 * //TODO: Must provide implementation for bean method stub
	 */
	public void sendPDF(FormInstance tFormInstance) {
		//~Response
		System.out.println("--- 開始發送郵件,PDF Mail (SH-PURMI07)---");

		//取得表單欄位的值(使用fetchFieldValue)
		String mainID = tFormInstance.fetchFieldValue("TC001"); // 單別
		String subID = tFormInstance.fetchFieldValue("TC002"); // 單號
		String poType = tFormInstance.fetchFieldValue("drp_POtype"); // 採購類型
		String supID = tFormInstance.fetchFieldValue("TC004"); // 供應商代號
		String supName = tFormInstance.fetchFieldValue("TC004C"); // 供應商簡稱
		String purNameID = tFormInstance.fetchFieldValue("TC011"); // 採購人員ID
		String purName = tFormInstance.fetchFieldValue("TC011C"); // 採購人員Name
		String purNameEn = tFormInstance.fetchFieldValue("TC011En"); // 採購人員EnName
		String purEmail = tFormInstance.fetchFieldValue("TC011EMail"); // 採購EMAIL
		String createrMail = tFormInstance.fetchFieldValue("txt_CreaterMail");	//填單人EMAIL
		String lang = tFormInstance.fetchFieldValue("TC012"); // 中文:1 / 英文:2
		String supMail = ""; // 廠商Mail(至PKSYS取得完整mail)
		//String signerMail = tFormInstance.fetchFieldValue("txt_LastSignEmail"); // 最後簽核者Mail (不寄給簽核者)
		String fullMailTo = "";// 完整收件人清單
		Boolean sendToSup = true; //是否發信給廠商
		
		
		/* ------ 資料處理 S ------ */
		
		//[廠商Mail]
		//判斷:若採購類型符合條件，不發送給廠商(設變/新品)
		if (poType.equals("E") || poType.equals("F") || poType.equals("G") || poType.equals("H")) {
			sendToSup = false;
		}

		//取得廠商Mail(From PKSYS)
		if (sendToSup) {
			Map myMap = GetSupMail(supID);
			
			//Group by 
			Iterator iter = myMap.entrySet().iterator();
			while (iter.hasNext()) {
				//取MAP
				Map.Entry entry = (Map.Entry) iter.next();

				//key為email address
				supMail += entry.getKey() + ";";
				// System.out.println("---Show Result---" + entry.getKey() + "__" + entry.getValue());
			}
		}
		
		//取得採購人員資料(From PKSYS)
		//Tel, TelExt
		String tel = "";
		String telExt = "";
		String email = "";
		List purInfo = GetPurManInfo(purNameID);
		
		//~Response
		//System.out.println("GetPurManInfo count: " + purInfo.size());
		//foreach
		if(purInfo.size() > 0){
			for (int row = 0; row < purInfo.size(); row++) {
				Map item = (Map)purInfo.get(row);				
				
				tel = (String)item.get("Tel");
				telExt = (String)item.get("Ext");
				email = (String)item.get("Email");
							
	        	//System.out.println("PurInfo:" + email + "_" + tel + "_" + telExt);
	        }
		}
		
		//[Mail Html]
		//Html路徑(HTML檔案必須轉成 檔首無BOM)
		String htmlName = lang.equals("1") ? "Mail_CN.html" : "Mail_EN.html";
		String url = "http://cdn.prokits.com.tw/BPM/PURMI07/" + htmlName;

		//~Response
		System.out.println("Get Mail Html:" + url);
		
		//設定郵件內容Html(Html內容只允許body)
		String mailBody = GetHtml(url);
		if(mailBody == ""){
			//~Response
			System.out.println("無法取得Mail Html : " + url);			
			return;
		}
				
		
		//通用title
		String poTitle =  mainID + "-" + subID + "(" + supName + ")";
		//set year
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		//取代Html會變動的內容
		mailBody = mailBody.replace("#subject#", poTitle);
		mailBody = mailBody.replace("#今年#", year);
		mailBody = mailBody.replace("#supName#", supName);
		mailBody = mailBody.replace("#Who#", lang.equals("1") ? purName : purNameEn);
		mailBody = mailBody.replace("#Mail#", email);
		mailBody = mailBody.replace("#Tel#", tel);
		mailBody = mailBody.replace("#Ext#", telExt);

		//~Response
		//System.out.println("mailBody:" + mailBody);
		
		//[收件人名單]
		//建立收件人清單(上海採購群組/供應商/採購/填單人)
		//採購Mail空白 & 採購不等於填單 -> 帶採購Mail
		fullMailTo = "sh_purchase@mail.prokits.com.tw;" + supMail + ((!purEmail.equals("") && !purEmail.equals(createrMail))? purEmail + ";" : "") + createrMail;
		//fullMailTo = "purchase@mail.prokits.com.tw;sh_purchase@mail.prokits.com.tw";  //for test
		
		/* ------ 資料處理 E ------ */
		
		/* ------ Mail處理 S ------ */
		//宣告SMTP參數
		String host = "smtp.prokits.com.tw";
		String port = "25";
		String mailFrom = "pkmailman";
		String password = "PK!@#mail";
		
		//設定:收件人
		String mailTo = fullMailTo;
		
		//設定:主旨/內文
		String subject = "";
		String bodyMessage = mailBody;
		if(lang.equals("1")){
			//---中文(CN)---
			subject = "上海宝工采购单 - " + poTitle;
			
		}else{
			//---英文---
			subject = "From Prokits New P/O - " + poTitle;			
		}
		
		//System.out.println("bodyMessage:" + bodyMessage);
		//System.out.println("subject:" + subject);
		//System.out.println("mailTo:" + mailTo);
	
		//[附件處理]
		String[] attachFiles = new String[1];
		//取得PDF檔案名稱
		String pdfName = mainID + subID + ".pdf";
		
		//設定:PDF完整路徑
		attachFiles[0] = System.getProperty("jboss.home.dir")
				+ "\\server\\default\\deploy\\NaNaWeb.war\\report\\iPURMI07_SH\\"
				+ pdfName;

		try {
			if (mailTo == "") {
				System.out.println("--- 收件人清單空白無法發送 ---," + mainID + subID);
			} else {
				//採購人Mail空白,使用填單人Mail
				if(purEmail.equals("")){
					purEmail = createrMail;
				}
				
				//send email
				sendEmailWithAttachments(host, port
						, mailFrom, password
						, purEmail, mailTo
						, subject
						, bodyMessage
						, attachFiles);
				System.out.println("--- 郵件已發送 ---," + mainID + subID);
			}
		} catch (Exception ex) {
			System.out.println("--- 無法寄送郵件 ---");
			ex.printStackTrace();
			throw new ServiceException(ex);
		}
		
		/* ------ Mail處理 E ------ */
	}


	///寄送郵件
	///(host, port, loginname, loginpwd, 採購人員MAIL, 收件人清單, 主旨, 內文, 附件)
	private void sendEmailWithAttachments(String host, String port,
			final String userName, final String password
			, String purEmail, String toAddress
			, String subject, String bodyMessage, String[] attachFiles)
			throws AddressException, MessagingException {

		// sets SMTP server properties
		Properties properties = new Properties();

		try {
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", "true");
			// properties.put("mail.smtp.starttls.enable", "true"); //非SSL要mark此段
			properties.put("mail.user", userName);
			properties.put("mail.password", password);

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			};
			Session session = Session.getInstance(properties, auth);

			auth = null;

			//建立Email
			MimeMessage msg = new MimeMessage(session);
			
			//[設定]寄件人
			msg.setFrom(new InternetAddress("pkmailman@mail.prokits.com.tw", "ProsKit Mail System")); 
			
			//[設定]收件人
			//Array解析收件人(;)
			String[] toAddrAry = toAddress.split(";");
			//宣告暫存List
			List listAddr = new ArrayList();
			
			//過濾不正確的Email, 將正確的塞入List
			for (int i = 0; i < toAddrAry.length; i++) {
				if(isEmail(toAddrAry[i])){
					listAddr.add(toAddrAry[i]);	
					
					System.out.println("mailList("+ i +")==" + toAddrAry[i]);
				}
			}
			//加入收件清單
			InternetAddress[] toAddresses = new InternetAddress[listAddr.size()];
			for (int row = 0; row < listAddr.size(); row++) {
				//System.out.println(listAddr.get(row));
				toAddresses[row] = new InternetAddress(listAddr.get(row).toString());
			}
			
			//指定收件人
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			// msg.setRecipients(Message.RecipientType.CC, "");
			
			//[設定]Reply To(採購人員)
			msg.setReplyTo(new InternetAddress[]{new InternetAddress(purEmail)});
		

			//[設定]主旨
			msg.setSubject(subject, "utf-8");
			msg.setSentDate(new Date());

			//[設定]內文
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(bodyMessage, "text/html;charset=utf-8"); //body

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			//[設定]附件
			if (attachFiles != null && attachFiles.length > 0) {
				MimeBodyPart attachPart = new MimeBodyPart();

				for (int i = 0; i < attachFiles.length; i++) {
					try {
						attachPart.attachFile(attachFiles[i]);
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					multipart.addBodyPart(attachPart);
				}
			}

			//加入附件 sets the multi-part as e-mail's content
			msg.setContent(multipart); // 附件

			//發送郵件 sends the e-mail
			Transport.send(msg);
			
			//System.out.println("--Transport.send--");

			msg = null;

		} catch (Exception ex) {
			System.out.println("--- sendEmailWithAttachments 有問題 ---");
			ex.printStackTrace();
			throw new ServiceException(ex);
		} finally {
			properties = null;

		}
	}

	//取得廠商通訊錄(PKSYS)
	private Map GetSupMail(String supID) {
		System.out.println("--- 取得廠商通訊錄 Start ---");

		// 取得DB連結
		DataSource tPKDataSource = null;
		Connection tPKConnection = null;
		Statement tPKStatement = null;
		ResultSet tPKResult = null;
		// 宣告MAP暫存
		Map result = new HashMap();

		try {
			// 取得DB連線(查看nana_xxx.xml的<jndi-name>
			tPKDataSource = getJndiDataSource("NaNaPKSYS");
			tPKConnection = tPKDataSource.getConnection();
			tPKStatement = tPKConnection.createStatement();

			// SQL
			String sql = "";
			sql += " SELECT Base.Email, Base.FullName, Base.Gender";
			sql += " FROM [PKSYS].dbo.Supplier_Members Base";
			sql += " WHERE (Base.ERP_ID = '" + supID + "') AND (Base.IsSendOrder = 'Y')";

			// print sql
			// System.out.println("Mail SQL:" + sql);

			// 取得DB欄位值
			String fullName = ""; // 全名
			String email = ""; // Email
			//String gender = ""; // 性別

			// 執行SQL,取回結果
			tPKResult = tPKStatement.executeQuery(sql);
			while (tPKResult.next()) {
				fullName = tPKResult.getString("FullName");
				email = tPKResult.getString("Email");
				//gender = tPKResult.getString("Gender").equals("M") ? " 先生": " 小姐";

				// Set Map
				result.put(email, fullName);
			}

			System.out.println("--- 取得廠商通訊錄 End, ok ---");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("--- 取得廠商通訊錄 Error ---");
			throw new ServiceException(ex);

		} finally {
			// ** 重要 ** Release DB 連線
			closeResultSet(tPKResult);
			closeStmt(tPKStatement);
			closeConn(tPKConnection);
		}

		// return map
		return result;
	}

	//取得採購人員通訊錄(PKSYS)
	private List GetPurManInfo(String erpID) {
		System.out.println("--- 取得採購人員Info Start ---");

		// 取得DB連結
		DataSource tPKDataSource = null;
		Connection tPKConnection = null;
		Statement tPKStatement = null;
		ResultSet tPKResult = null;
		
		// 將MAP 寫入LIST
		List myList = new ArrayList();
		// 宣告MAP暫存
		Map result = new HashMap();

		try {
			// 取得DB連線(查看nana_xxx.xml的<jndi-name>
			tPKDataSource = getJndiDataSource("NaNaPKSYS");
			tPKConnection = tPKDataSource.getConnection();
			tPKStatement = tPKConnection.createStatement();

			/*
			 * 取得PKSYS人員資料
			 * (ERP_UserID=PKEF.ERP員工代號)
			 * */
			String sql = "";
			sql += " SELECT TOP 1 Display_Name, Email, NickName";
			sql += " , ISNULL(Tel, '+886 2 2218-3233') Tel, ISNULL(Tel_Ext, '123') Tel_Ext";
			sql += " FROM [PKSYS].dbo.User_Profile";
			sql += " WHERE (ERP_UserID = '" + erpID + "')";
			
			//~Response
			//System.out.println("採購人員Info SQL:" + sql);

			// 取得DB欄位值
			String Tel = "";
			String Tel_Ext = "";
			String email = "";

			// 執行SQL,取回結果
			tPKResult = tPKStatement.executeQuery(sql);
			while (tPKResult.next()) {
				Tel = tPKResult.getString("Tel");
				Tel_Ext = tPKResult.getString("Tel_Ext");
				email = tPKResult.getString("Email");

				// Set Map
				result.put("Tel", Tel);
				result.put("Ext", Tel_Ext);
				result.put("Email", email);
				
				//System.out.println("Map:" + Tel + "_" + Tel_Ext + "_" + email);
				myList.add(result);
			}

			System.out.println("--- 取得採購人員Info End, ok ---");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("--- 取得採購人員Info Error ---");
			throw new ServiceException(ex);

		} finally {
			// ** 重要 ** Release DB 連線
			closeResultSet(tPKResult);
			closeStmt(tPKStatement);
			closeConn(tPKConnection);
		}

		// return map
		return myList;
	}

	
	//取得Mail Html
	private String GetHtml(String url){
		try{
			//宣告url
			URL obj = new URL(url);
			//Url連線
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			//~Response
			//System.out.println("URL : " + url);

			//讀取串流
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
			//設定接收字串
			StringBuffer response = new StringBuffer();
			String inputLine;

			//字串組合
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			//關閉串流
			in.close();
			
			return response.toString().replaceAll("\\s{2,}"," ");
			
		} catch (Exception ex) {
			System.out.println("--- 無法取得Email Html Body ---");
			ex.printStackTrace();
			//throw new ServiceException(ex);
			return "";
			
		} finally {
			
	
		}
		
	}
	

	//檢查Email格式
	private boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
				+ "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
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
	public PURMI07_SH_MailBean() {
		// TODO Auto-generated constructor stub
	}
}
