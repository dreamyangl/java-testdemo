package kc.udpnetty.dal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import udpnetty.dal.entity.LiftInfos;

import java.util.List;

/**
 * <p>
 * led呼叫盒基本信息(IP地址等,用作socket的连接) 服务类
 * </p>
 *
 * @author CaoGang
 * @since 2021-07-20
 */
public interface ILiftInfosService extends IService<LiftInfos> {
    /**
     * 根据启用状态(0-未启用,1-启用),得到所有socket(呼叫盒ip,port)的配置信息
     * @param status
     * @return
     */
    List<LiftInfos> queryList(int status);

    /**
     * 根据呼叫盒编号获取呼叫盒的信息
     * @param ledNo
     * @return
     */
    LiftInfos getLiftInfoByLineNo(String ledNo);
}
