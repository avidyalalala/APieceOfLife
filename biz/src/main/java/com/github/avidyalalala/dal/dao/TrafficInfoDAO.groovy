package com.github.avidyalalala.dal.dao

import com.github.avidyalalala.pojo.TrafficInfo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectKey

/**
 * User: junyi.glj
 * Date: 14-7-21
 * Time: 下午2:57
 */
public interface TrafficInfoDAO {

    @Insert('''
        INSERT IGNORE INTO
            traffic_info(imsi,
                    iccid,
                    network_id,
                    start_time,
                    bill_time,
                    app_name,
                    traffic_data,
                    total_traffic_data,
                    uuid,
                    imei,
                    kp,
                    gmt_create,
                    gmt_modified
                    )
        VALUES (#{imsi},
                #{iccid},
                #{networkId},
                #{startTime},
                #{billTime},
                #{appName},
                #{trafficData},
                #{totalTrafficData},
                #{uuid},
                #{imei},
                #{kp},
                now(),
                now())
    ''')
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS ID", resultType = long.class, keyProperty = "id", before = false)
    public int insert(TrafficInfo trafficInfo);
    // on DUPLICATE KEY UPDATE gmt_modified=now()

    @Select('''
        SELECT id, imsi, iccid, network_id,
        start_time, bill_time,app_name,
        traffic_data,total_traffic_data,
        gmt_create,gmt_modified
        FROM traffic_info
        WHERE gmt_create >=#{stime}AND gmt_create<#{etime} limit #{offset}, #{count}
    ''')
    public List<TrafficInfo> findRecordsByCreateTime( @Param("stime")Date stime, @Param("etime")Date etime, @Param("offset")int offset, @Param("count")int count);

}