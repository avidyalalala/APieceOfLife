package com.github.avidyalalala.ftp;

//import com.github.avidyalalala.ftp.FtpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lina.hou
 * Date: 14-7-22
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
@Component("ftpWrapper")
public class FtpWrapper {

    private static final Logger log = LoggerFactory.getLogger("taskLogger");

    public void upLoadToFtp(File file){

        String fileName=file.getName();

        String server="";
        String userName="";
        String password="";
        String directory="";

        log.info("start to upload File "+fileName+" to FTP "+ server);

   //     boolean re = FtpUtils.uploadFile(file, server, userName, password, directory);
      //  log.info("end of uploading File "+ fileName+" , the result is "+ re);
    }
}