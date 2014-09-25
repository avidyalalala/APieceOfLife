package com.github.avidyalalala.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-7-22
 * Time: 下午7:53
 * To change this template use File | Settings | File Templates.
 */
public class FtpUtils {
        private static final Logger log_ftp = LoggerFactory.getLogger("taskLogger");

        public static final  FTPClient ftpClient= new FTPClient();

        static{
            ftpClient.setDataTimeout(30000);       //设置传输超时时间为30秒
            ftpClient.setConnectTimeout(30000);       //连接超时为30秒
        }

        private FtpUtils(){}

        /**
         * 上传文件
         * @return boolean
         */
        public static boolean uploadFile(File localFile, String serverName, String userName, String password, final String descDirectory) {

            boolean flag = false;
            InputStream input = null;
            try {
                boolean re = connectServer(serverName, userName, password);
                if(!re){
                    log_ftp.info("cannot connect to {} ", serverName);
                    return false;
                }
                log_ftp.info("connect to {} succesfully", serverName);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                ftpClient.changeWorkingDirectory(descDirectory);

                input = new FileInputStream(localFile);

                log_ftp.info("input size is {} bytes", input.available());

                flag = ftpClient.storeFile(localFile.getName(), input);
                if (flag) {
                    log_ftp.info("upload " + localFile + " success:)");
                } else {
                    log_ftp.info("upload " + localFile + " failed:(");
                }
                ftpClient.logout();
            } catch (IOException e) {
                log_ftp.info("upload {} failed:(,{}", localFile, e);
            } catch (Exception e) {
                log_ftp.info("upload {} failed:(,{}", localFile, e);
            } finally {
                IOUtils.closeQuietly(input);
                closeFtpServer();
            }
            return flag;
        }

        public static boolean deleteTile(String remoteFileName, String serverName, String userName, String password, final String descDirectory){
            boolean flag = true;
            boolean re = connectServer(serverName, userName, password);
            if(!re){
                log_ftp.info("cannot connect to {} ", serverName);
                return false;
            }
            log_ftp.info("connect to {} succesfully", serverName);
            try {
                ftpClient.deleteFile(descDirectory+"/"+remoteFileName);

            } catch (Exception e) {
                log_ftp.info("删除FTP文件下载失败！", e);
            }finally {
                closeFtpServer();
            }
            return flag;
        }

        /**
         * 下载文件
         */
        public static boolean downloadFile(String remoteFileName, String serverName, String userName, String password, final String descDirectory) {
            boolean flag = true;
            boolean re = connectServer(serverName, userName, password);
            if(!re){
                log_ftp.info("cannot connect to {} ", serverName);
                return false;
            }
            log_ftp.info("connect to {} succesfully", serverName);
            // 下载文件
            BufferedOutputStream buffOut = null;
            try {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                buffOut = new BufferedOutputStream(new FileOutputStream(remoteFileName));
                flag = ftpClient.retrieveFile(remoteFileName, buffOut);
            } catch (Exception e) {
                log_ftp.info("本地文件下载失败！", e);
            } finally {
                try {
                    if(buffOut!=null){buffOut.close();}
                    closeFtpServer();
                } catch (Exception e) {
                    log_ftp.info(e.getMessage());
                }
            }
            return flag;
        }

        private static void closeFtpServer(){
            if(ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch(IOException ioe) {
                    log_ftp.info("close ftp failed,{}", ioe);
                }
            }
        }

        /**
         * 连接到服务器
         *
         * @return true 连接服务器成功，false 连接服务器失败
         */
        private static boolean connectServer(String serverName, String userName, String password) {
            boolean flag = true;
            int reply;
            try {
                log_ftp.info("trying to connect ftp");
                ftpClient.connect(serverName);
                log_ftp.info("after connect, try to log_ftp in");
                ftpClient.login(userName, password);
                log_ftp.info(ftpClient.getReplyString());

                reply = ftpClient.getReplyCode();
                //miliSeconds ,max 为6mins
                ftpClient.setDataTimeout(360000);
                ftpClient.setAutodetectUTF8(true);
                ftpClient.setBufferSize(1024 * 1024);

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    log_ftp.info("FTP " + serverName + " refuse to connect.");
                    flag = false;
                }

            } catch (SocketException e) {
                flag = false;
                log_ftp.info("login FTP {} failed! the reason is {}", serverName, e);

            } catch (IOException e) {
                flag = false;
                log_ftp.info("login FTP {} failed! the reason is {}", serverName, e);
            }
            return flag;
        }

}
