/**
 * 
 */
package com.prokits.custom.sb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
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
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> * <!--
 * begin-xdoclet-definition -->
 * 
 * @ejb.bean name="PURMI07_Mail" description="An EJB named PURMI07_Mail"
 *           display-name="PURMI07_Mail" jndi-name="PURMI07_Mail"
 *           type="Stateless" transaction-type="Container" <!--
 *           end-xdoclet-definition -->
 * @generated
 */

public abstract class PURMI07_MailBean implements javax.ejb.SessionBean {

	/**
	 * <!-- begin-xdoclet-definition --> <!-- end-xdoclet-definition -->
	 * 
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
			this.log = NaNaLogFactory.getLog(PURMI07_MailBean.class);

		}

		return this.log;
	}

	/**
	 * 
	 * <!-- begin-xdoclet-definition -->
	 * 
	 * @ejb.create-method view-type="remote" <!-- end-xdoclet-definition -->
	 * @generated
	 * 
	 *            //TODO: Must provide implementation for bean create stub
	 */
	public void ejbCreate() {
	}

	/**
	 * 
	 * <!-- begin-xdoclet-definition -->
	 * 
	 * @ejb.interface-method view-type="remote" <!-- end-xdoclet-definition -->
	 * @generated
	 * 
	 *            //TODO: Must provide implementation for bean method stub
	 */
	public String foo(String param) {
		return null;
	}


	/**
	 * 
	 * <!-- begin-xdoclet-definition -->
	 * 
	 * @ejb.interface-method view-type="remote" <!-- end-xdoclet-definition -->
	 * @generated
	 * 
	 *            //TODO: Must provide implementation for bean method stub
	 */
	public void sendPDF(FormInstance tFormInstance) {
		System.out.println("--- 開始發送郵件,PDF Mail ---");

		//宣告SMTP參數
		String host = "smtp.prokits.com.tw";
		String port = "25";
		String mailFrom = "pkmailman";
		String password = "PK!@#mail";

		//取得表單欄位的值(使用fetchFieldValue)
		String mainID = tFormInstance.fetchFieldValue("TC001"); // 單別
		String subID = tFormInstance.fetchFieldValue("TC002"); // 單號
		String poType = tFormInstance.fetchFieldValue("drp_POtype"); // 採購類型
		String supID = tFormInstance.fetchFieldValue("TC004"); // 供應商代號
		String supName = tFormInstance.fetchFieldValue("TC004C"); // 供應商簡稱
		String purName = tFormInstance.fetchFieldValue("TC011C"); // 採購人員Name
		String purNameEn = tFormInstance.fetchFieldValue("TC011En"); // 採購人員EnName
		String purEmail = tFormInstance.fetchFieldValue("TC011EMail"); // 採購EMAIL
		String createrMail = tFormInstance.fetchFieldValue("txt_CreaterMail");	//填單人EMAIL
		String lang = tFormInstance.fetchFieldValue("TC012"); // 中文:1 / 英文:2
		String supMail = ""; // 廠商Mail
		//String signerMail = tFormInstance.fetchFieldValue("txt_LastSignEmail"); // 最後簽核者Mail (不寄給簽核者)
		String fullMailTo = "";// 完整收件人清單
		Boolean sendToSup = true;

		//取得PDF檔案名稱
		String pdfName = mainID + subID + ".pdf";

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

				supMail += entry.getKey() + ";";
				// System.out.println("---Show Result---" + entry.getKey() + "__" + entry.getValue());
			}
		}
		
	
		//建立收件人清單(供應商/採購/填單人)
		//採購Mail空白 & 採購<>填單 -> 帶採購Mail
		fullMailTo = supMail + ((!purEmail.equals("") && !purEmail.equals(createrMail))?purEmail + ";" : "") + createrMail;
		//fullMailTo = "clyde@mail.prokits.com.tw";  //20190513 for test

		//設定:收件人
		String mailTo = fullMailTo;
		
		//設定:主旨/內文
		String subject = "";
		String message = "";
		String footerImg = "";
		
		//判斷語系
		if(lang.equals("1")){
			//---中文---
			subject = "寶工採購單 - " + mainID + "-" + subID + "(" + supName + ")";
			message = "Dear " + supName + " 您好<br/><br/>"
				+ "附件為我司新增的採購單，請確認交期後，<br/><span style=\"color:red\">用mail全部回覆(勿單獨回覆系統,以免無人處理)</span><br/>有任何問題請與 採購部-" + purName + " (" + purEmail + ")" + " 聯繫,謝謝<br/><br/>"
				+ "寶工採購部";
			
			footerImg = "<br/><br/><br/>**本電子郵件及附件所載資訊均為保密資訊，受合約保護或依法不得洩漏。切勿轉寄、散佈、複製或公開其內容。<br/>臺端如非本電子郵件之收件者，請於誤收此文件後，立即與本公司聯絡，並請刪除此郵件與附件。謝謝您的合作！**";
			
		}else{
			//---英文---
			subject = "From Prokits New P/O - " + mainID + "-" + subID + "(" + supName + ")";
			message = "Dear " + supName + "<br/><br/>"
				+ "Good day!<br/>Please find attached New Order, and please confirm and reply the delivery date.<br/>If there is any question, please contact with us. Thanks.<br/><br/>"
				+ "Thanks & Best wishes,<br/>"
				+ purNameEn + "<br/>" + purEmail;
			
			footerImg = "<br/><br/><br/>**本电子邮件及附件所载信息均为保密信息，受合同保护或依法不得泄漏。切勿转寄、散布、复制或公开其内容。<br/>因误传而收到本邮件的收件人，请于误收此文件后，立即与本公司联络，并请删除邮件与此电子档，谢谢您的合作！**";			
		}
		
		message += footerImg;

		System.out.println("subject:" + subject);
		System.out.println("mailTo:" + mailTo);
	

		//宣告附件
		String[] attachFiles = new String[1];
		//設定:PDF完整路徑
		attachFiles[0] = System.getProperty("jboss.home.dir")
				+ "\\server\\default\\deploy\\NaNaWeb.war\\report\\iPURMI07\\"
				+ pdfName;

		try {
			if (fullMailTo == "") {
				System.out.println("--- 收件人清單空白無法發送 ---," + mainID + subID);
			} else {
				//採購人Mail空白,使用填單人Mail
				if(purEmail.equals("")){
					purEmail = createrMail;
				}
				//for test 20190513
				//purEmail = "clyde@mail.prokits.com.tw";
				
				//send email
				sendEmailWithAttachments(host, port, mailFrom, password
						,purEmail, mailTo, subject, message, attachFiles);
				System.out.println("--- 郵件已發送 ---");
			}
		} catch (Exception ex) {
			System.out.println("--- 無法寄送郵件 ---");
			ex.printStackTrace();
			throw new ServiceException(ex);
		}
	}


	///寄送郵件
	///(host, port, loginname, loginpwd, 採購人員MAIL, 收件人清單, 主旨, 內文, 附件)
	private void sendEmailWithAttachments(String host, String port,
			final String userName, final String password
			, String purEmail, String toAddress
			, String subject, String message, String[] attachFiles)
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

			// creates a new e-mail message
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress("pkmailman@mail.prokits.com.tw", "ProsKit Mail System")); // 寄件人

			
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
			
			//設定收件人
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			// msg.setRecipients(Message.RecipientType.CC, "");
			
			//Reply To(採購人員)
			msg.setReplyTo(new InternetAddress[]{new InternetAddress(purEmail)});
		

			//設定主旨
			msg.setSubject(subject, "utf-8");
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html;charset=utf-8"); // 內文

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments
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

			// sets the multi-part as e-mail's content
			msg.setContent(multipart); // 附件

			// sends the e-mail
			Transport.send(msg);
			
			System.out.println("--Transport.send--");

			msg = null;

		} catch (Exception ex) {
			System.out.println("--- sendEmailWithAttachments 有問題 ---");
			ex.printStackTrace();
			throw new ServiceException(ex);
		} finally {
			properties = null;

		}
	}

	// 取得廠商通訊錄Mail(PKSYS)
	private Map GetSupMail(String supID) {
		System.out.println("--- 取得廠商通訊錄Mail Start ---");

		// 取得DB連結
		DataSource tPKDataSource = null;
		Connection tPKConnection = null;
		Statement tPKStatement = null;
		ResultSet tPKResult = null;
		// 宣告MAP暫存
		Map result = new HashMap();

		try {
			// 取得DB連線(查看nana_xxx.xml的<jndi-name>
			tPKDataSource = getJndiDataSource("NaNaERPTW");
			tPKConnection = tPKDataSource.getConnection();
			tPKStatement = tPKConnection.createStatement();

			// SQL
			String sql = "";
			sql += " SELECT Base.Email, Base.FullName, Base.Gender";
			sql += " FROM PKSYS.dbo.Supplier_Members Base";
			sql += " WHERE (Base.ERP_ID = '" + supID + "') AND (Base.IsSendOrder = 'Y')";

			// print sql
			// System.out.println("Mail SQL:" + sql);

			// 取得DB欄位值
			String fullName = ""; // 全名
			String email = ""; // Email
			String gender = ""; // 性別

			// 執行SQL,取回結果
			tPKResult = tPKStatement.executeQuery(sql);
			while (tPKResult.next()) {
				fullName = tPKResult.getString("FullName");
				email = tPKResult.getString("Email");
				gender = tPKResult.getString("Gender").equals("M") ? " 先生"
						: " 小姐";

				// Set Map
				result.put(email, fullName + gender);
			}

			System.out.println("--- 取得廠商通訊錄Mail End, ok ---");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("--- 取得廠商通訊錄Mail Error ---");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */

	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public PURMI07_MailBean() {
		// TODO Auto-generated constructor stub
	}

}
