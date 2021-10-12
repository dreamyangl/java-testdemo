package kc.udpnetty.netty;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import udpnetty.dal.entity.LiftInfos;
import udpnetty.dal.service.ILiftInfosService;
import kc.udpnetty.service.CacheService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description netty启动类,项目启动时自动启动
 * @Created CaoGang
 */
@Service
@Slf4j
public class NettyStartClient {
    @Resource
    private ILiftInfosService liftInfosService;
    @Resource
    private CacheService cacheService;

    @PostConstruct
    public void startNettyClient(){
        List<LiftInfos> liftInfos = liftInfosService.queryList(1);
        if (CollectionUtils.isEmpty(liftInfos)){
            log.error("没有找到可用的电梯配置信息");
            return;
        }
        // 将电梯的基本信息保存到本地
        cacheService.updateLiftInfosCache(liftInfos);
        for (LiftInfos liftInfo : liftInfos) {
            start(liftInfo);
        }
    }

    /**
     * 构建netty实例,每个电梯一个通讯
     * @param liftInfo
     */
    public void start(LiftInfos liftInfo){
        UdpManager manager = new UdpManager();
        UdpConfig config = new UdpConfig.Builder()
                .setResendEnable(true)
                .setKeepSendLimit(3)
                .setKeepSendEnable(true)
                .setKeepSendInterval(200L)
                .build();
        manager.init(config);
        manager.startClient();
        ConnectCache.connectMap.put(liftInfo.getLiftIp(),manager);
    }
}
