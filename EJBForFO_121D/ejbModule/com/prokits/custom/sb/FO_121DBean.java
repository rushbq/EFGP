/**
 * 
 */
package com.prokits.custom.sb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;


import com.dsc.nana.domain.form.AttachmentElementInstance;
import com.dsc.nana.domain.form.FormInstance;
import com.dsc.nana.services.doc_manager.DocManager;
import com.dsc.nana.services.doc_manager.DocManagerFactory;
import com.dsc.nana.services.exception.NotFoundException;
import com.dsc.nana.services.exception.ServiceException;
import com.dsc.nana.util.logging.NaNaLog;
import com.dsc.nana.util.logging.NaNaLogFactory;
import com.prokits.bean.FTPUploader;

/**
 * 
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> * <!--
 * begin-xdoclet-definition -->
 * 
 * @ejb.bean name="FO_121D" description="An EJB named FO_121D"
 *           display-name="FO_121D" jndi-name="FO_121D" type="Stateless"
 *           transaction-type="Container"
 * 
 *           <!-- end-xdoclet-definition -->
 * @generated
 */

public abstract class FO_121DBean implements javax.ejb.SessionBean {

	/* 寫log的工具 */
	private NaNaLog log;

	/*
	 * 屬性log的getter
	 */
	private NaNaLog getLog() {
		if (this.log == null) {
			this.log = NaNaLogFactory.getLog(FO_121DBean.class);

		}

		return this.log;
	}

	/**
	 * <!-- begin-xdoclet-definition --> <!-- end-xdoclet-definition -->
	 * 
	 * @generated
	 */
	private static final long serialVersionUID = 1L;

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
	public String foo1(String param) {
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
	public void apiUpdateFiles(FormInstance pForm) {
		try {
						
			/*
			 * FTP呼叫 FTPUploader
			 * 連線資訊已寫死在類別裡
			 * */
			
			// 宣告
			AttachmentElementInstance tAttachmentElementInstance = null;
			String serialNo = pForm.fetchFieldValue("SerialNumber_auto"); //表單序號(傳送參數)
			String procOID = pForm.fetchFieldValue("hid_processInstOID"); //processInstOID(傳送參數)
			String folderName = "EFGP/" + serialNo + "/"; //目標資料夾 ftpRoot/EFGP/表單序號/檔名
			List orgFileNameGrp = new ArrayList(); //原始檔名List
			List newFileNameGrp = new ArrayList(); //新檔名List
			String fullOrgFileName = "";	//原始檔名組合字串
			String fullNewFileName = "";	//新檔名組合字串			
		
			// 判斷有無附件
			if (pForm.getAttachments().size() > 0) {

				System.out.println("--- 傳送附件至FTP 開始(注意主機的firewall要關閉) ---");
				
				//--- [Loop] 檔案迴圈  Start ---
				for (int i = 0; i < pForm.getAttachments().size(); i++) {
					//取得檔案OID
					String tOID = ((AttachmentElementInstance) pForm
							.getAttachments().get(i)).getOID();
					System.out.println("檔案OID:" + tOID);

					//取得原始檔案名稱
					String orgFileName = ((AttachmentElementInstance) pForm
							.getAttachments().get(i)).getOriginalFileName();
					System.out.println("原檔名:" + orgFileName);
					
					//將原檔名加入List
					orgFileNameGrp.add(orgFileName);					
					
					//命名新檔名
					String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					String newFileName = ts + i + getFileExt(orgFileName);
					//將新檔名加入List
					newFileNameGrp.add(newFileName);
					
					System.out.println("新檔名:" + newFileName);
					

					//將檔案OID(實體檔案)，轉成byte
					byte[] tFileBytes = getBytesFromFile(tOID); // 輸入附件OID
																// 到DocServer
																// 將附件取回					
					System.out.println("取得附件 getBytesFromFile(tOID),轉成byte.");

					//將byte轉成InputStream
					InputStream myStream = new ByteArrayInputStream(tFileBytes);					
					System.out.println("將byte轉成InputStream.");

					// 上傳檔案至FTP
					FTPUploader uploader = new FTPUploader();
					System.out.println("開始上傳檔案至FTP");

					//呼叫 uploadFile,開始上傳(資料夾名, 新檔案名稱, 檔案stream)
					boolean flag = uploader.uploadFile(folderName,
							newFileName, myStream);
					if (flag == false) {
						System.out.println("--- 檔案上傳失敗 ---");

						throw new Exception("FTP Upload Failed...." + orgFileName);
					}

					myStream.close();
					
				}
				//--- [Loop] 檔案迴圈  End ---

				System.out.println("--- 傳送附件至FTP 結束 ---");
			}
			

			//----- 呼叫 PK API -----
			System.out.println("--- 呼叫PK API 開始 ---");
			
			//原始檔名,組成字串(使用 || 分隔)
			fullOrgFileName = combineString(orgFileNameGrp,"||");
			//新檔名,組成字串(使用 || 分隔)
			fullNewFileName = combineString(newFileNameGrp,"||");

			System.out.println("processInstOID(psOid):" + procOID);
			System.out.println("表單序號sn:" + serialNo);
			System.out.println("原檔名組合fo:" + fullOrgFileName);
			System.out.println("新檔名組合fn:" + fullNewFileName);
			
			//呼叫PK API
			//表單序號, 原檔名組合, 新檔名組合, processInstOID
			sendPost(serialNo, fullOrgFileName, fullNewFileName, procOID);
			
			System.out.println("--- 呼叫PK API 結束 ---");

		} catch (Exception e) {
			System.out.println("--- 無法上傳檔案或API呼叫失敗 ---");
			// TODO Auto-generated catch block
			if (this.getLog().isErrorEnabled()) {
				this.getLog().error(
						"(" + this.hashCode() + ") Fail!!ErrMsg:"
								+ e.getMessage());
			}
			throw new ServiceException(e.getMessage());

		} finally {

		}
	}
	
	//字串組合(自訂分隔符號)
	private String combineString(List s, String delimiter) {
	    if (s == null || s.isEmpty()) return "";
	    Iterator iter = s.iterator();
	    StringBuilder builder = new StringBuilder(iter.next().toString());
	    while( iter.hasNext() )
	    {
	        builder.append(delimiter).append(iter.next());
	    }
	    return builder.toString();
	}
	
	
	//取得副檔名(.xxx)
	  private String getFileExt(String fileName){
	    	String extension = "";

	    	int i = fileName.lastIndexOf('.');
	    	int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

	    	if (i > p) {
	    		//.txt
	    	    extension = fileName.substring(i);
	    	}
	    	
	    	return extension;
	    }
	    

	// HTTP POST request(PK APi接收)
	//表單序號, 原檔名組合(fo), 新檔名組合(fn), processInstOID
	private void sendPost(String sn, String fo, String fn, String psOid) throws Exception {		
		try {
			//Api路徑
			String url = "http://api.prokits.com.tw/api/EFGP_FO121D";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("charset", "utf-8");

			//傳送參數 (表單序號, 檔名組合, processInstOID)
			String urlParameters = "psOid=" + psOid + "&sn=" + sn + "&fo=" + URLEncoder.encode(fo, "UTF-8") + "&fn=" + URLEncoder.encode(fn, "UTF-8");

			//Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			
			wr.write(urlParameters.getBytes("utf-8"));
			wr.flush();
			wr.close();

			System.out.println("--- API傳送資訊 ---");
			System.out.println("URL : " + url);
			System.out.println("Post 參數 : " + urlParameters);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//Api Response Code
			System.out.println("API Response : " + response.toString());
			if(!response.toString().equals("200")){
				throw new Exception("API處理失敗..." + sn);
			}
		

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			if (this.getLog().isErrorEnabled()) {
				this.getLog().error(
						"(" + this.hashCode() + ") Fail!!ErrMsg:"
								+ e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (this.getLog().isErrorEnabled()) {
				this.getLog().error(
						"(" + this.hashCode() + ") Fail!!ErrMsg:"
								+ e.getMessage());
			}
		}
	}

	/**
	 * 
	 * *************************************************************************
	 * **********************
	 * 
	 * @ejb.interface-method view-type="local"
	 * @ejb.transaction type="RequiresNew"
	 *                  **************************************
	 *                  *******************
	 *                  **************************************
	 *                  取到formIntance對應實體附件檔案
	 * @param pOID
	 *            String
	 * @throws Exception
	 */
	public byte[] getBytesFromFile(String pOID) throws IOException {
		ServiceLocator tServiceLocator = ServiceLocator.getInstance();
		DocManagerFactory tDocManagerFactory = null;
		tDocManagerFactory = tServiceLocator.getDocManagerFactory();

		DocManager tDocManager = null;
		tDocManager = tServiceLocator.getDocManager();

		byte[] tFileBytes = null;

		try {
			tFileBytes = tDocManager.getDocument(pOID);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			if (this.getLog().isErrorEnabled()) {
				this.getLog().error(
						"(" + this.hashCode() + ") Fail!!ErrMsg:"
								+ e.getMessage());
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			if (this.getLog().isErrorEnabled()) {
				this.getLog().error(
						"(" + this.hashCode() + ") Fail!!ErrMsg:"
								+ e.getMessage());
			}
		} // 輸入附件OID 到DocServer 將附件取回

		return tFileBytes;
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
	public FO_121DBean() {
		// TODO Auto-generated constructor stub
	}
}
