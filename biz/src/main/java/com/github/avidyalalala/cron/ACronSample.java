package com.github.avidyalalala.cron;

import com.github.avidyalalala.config.AutoConfig;
import com.github.avidyalalala.dal.dao.TrafficInfoDAO;
import com.github.avidyalalala.pojo.TrafficInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: lina.hou
 * Date: 14-7-21
 * Time: 下午8:00
 */
@Component
public class ACronSample {
    private static final Logger log = LoggerFactory.getLogger("taskLogger");

    private static int count = 200;

    public SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
    public ACronSample(){}

    //@Scheduled(cron = "0 */15 * * * ?")
    @Scheduled(cron = "0 7 7 */1 * ?")
    public void createFileEveryMorning() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        log.info("********** this cronHostName : {}",hostName);
        if(hostName=="hehe"){
            selectAndWriteToFile(new Date());
        }

    }

    /**
     * 按照参数传入的时间从数据库里查出数据，写入文件
     * 文件名里含有 开始时间
     * @param ctimeStart
     * @return
     */
    public File selectAndWriteToFile(Date ctimeStart){
        int offset = 0;
        List<TrafficInfo> tiList;

        String fileName = "/home/admin/pieceOfLife" + sdf.format(ctimeStart) + ".txt";
        File file = new File(fileName);

        log.info("created the file which named {}",fileName);

        do{
            tiList = new ArrayList<TrafficInfo>();
            offset+=count;
            try {
                FileUtils.writeLines(file, tiList, true);
                log.info("append "+tiList.size()+" records into file successfully,  the current offset is:"+offset);
            } catch (IOException e) {
                log.info("Writing records into "+fileName+" failed:{}", e.getMessage());
            }
        } while(count==tiList.size());

        return file;
    }


}
