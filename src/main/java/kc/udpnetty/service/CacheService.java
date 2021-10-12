package kc.udpnetty.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import udpnetty.dal.entity.LiftInfos;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 呼叫盒缓存service
 * @Created CaoGang
 * @Date 2021/3/25 10:10
 * @Version 1.0
 */
@Service
@Slf4j
public class CacheService {

    public static Map<String, LiftInfos> liftInfosMap = new ConcurrentHashMap();

    /**
     * 将cache缓存与数据库保持同步更新
     */
    public void updateLiftInfosCache(List<LiftInfos> infos){
        if (CollectionUtils.isEmpty(infos)){
            return;
        }
        infos.forEach(t ->{
           // t.setSendNotColor(true);
            liftInfosMap.put(t.getLiftIp()+"_"+t.getPort(),t);
        });
        log.info("updateLiftInfosCache liftInfosMap[{}]", liftInfosMap);
    }

    /**
     * 获取value值
     */
    public LiftInfos getValue(String key){
        return liftInfosMap.get(key);
    }

    /**
     * 根据id获取value
     * @param id
     * @return
     */
    public LiftInfos getValueById(Long id){
        if (MapUtils.isEmpty(liftInfosMap)){
            return null;
        }
        LiftInfos infos = new LiftInfos();
        for (LiftInfos value : liftInfosMap.values()) {
            if (id.longValue() == value.getId().longValue()){
                infos = value;
            }
        }
        return infos;
    }
    /**
     * 添加key-value值,如果存在则覆盖
     * @param key
     * @param value
     */
    public boolean setValue(String key,LiftInfos value){
        if (value == null){
            return false;
        }
        if (Strings.isBlank(key) || null == value){
            key = value.getLiftIp()+"_"+value.getPort();
        }
        liftInfosMap.put(key,value);
        return true;
    }

    /**
     * 获取整个缓存map
     * @return
     */
    public  Map<String, LiftInfos> getCacheMap(){
        return liftInfosMap;
    }
}
