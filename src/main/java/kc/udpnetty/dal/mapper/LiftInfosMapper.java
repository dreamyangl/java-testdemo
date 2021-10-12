package kc.udpnetty.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import udpnetty.dal.entity.LiftInfos;

/**
 * <p>
 * led呼叫盒基本信息(IP地址等,用作socket的连接) Mapper 接口
 * </p>
 *
 * @author CaoGang
 * @since 2021-07-20
 */
@Mapper
public interface LiftInfosMapper extends BaseMapper<LiftInfos> {

}
