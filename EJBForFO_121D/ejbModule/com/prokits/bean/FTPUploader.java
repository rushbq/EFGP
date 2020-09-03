package com.prokits.bean;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUploader {
	/**
     * @param port 窗口（可默認）
     * @param url FTP hostname
     * @param username FTP登入帳號
     * @param password FTP登入密碼
     * PKRC9不支援中文檔名
     */

    int port = 21;

    //只能填IP
    String url = "192.168.1.214";

    String username = "PKFTP";

    String password = "1qaz@WSX";

    FTPClient ftp = new FTPClient();

    public FTPUploader(){
        try{

            ftp.connect(url, port);//連接FTP服務器

            //如果採用默認端口，可以使用ftp.connect(url)的方式直接連接FTP服務器

        }catch (Exception e){

            System.out.println(e);

        }
    }

    /**
     * @param path FTP上的保存目錄
     * @param filename 文件名稱
     * @param input 輸入流
     */
    public boolean uploadFile(String path, String filename, InputStream input) {
        boolean success = false;

        try {
        	ftp.setControlEncoding("UTF-8");  
        	
            int reply;

            ftp.login(username, password);//登錄

            reply = ftp.getReplyCode();

            ftp.makeDirectory(path);

            if (!FTPReply.isPositiveCompletion(reply)) {

                ftp.disconnect();

                return success;

            }

            //設置文件類型
            ftp.setFileType(ftp.BINARY_FILE_TYPE, ftp.BINARY_FILE_TYPE);
            ftp.setFileTransferMode(ftp.BINARY_FILE_TYPE);
            
            ftp.changeWorkingDirectory(path);

            ftp.storeFile(filename, input);        

             
            input.close();

            ftp.logout();

            success = true;

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (ftp.isConnected()) {

                try {

                    ftp.disconnect();

                } catch (IOException ioe) {

                }

            }

        }

        return success;//成功返回true

    }
}
