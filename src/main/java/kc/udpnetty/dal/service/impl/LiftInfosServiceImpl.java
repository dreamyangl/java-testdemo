package kc.udpnetty.dal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import udpnetty.dal.entity.LiftInfos;
import udpnetty.dal.mapper.LiftInfosMapper;
import kc.udpnetty.dal.service.ILiftInfosService;

import java.util.List;

/**
 * <p>
 * 电梯基本信息(IP地址等) 服务实现类
 * </p>
 */
@Service
public class LiftInfosServiceImpl extends ServiceImpl<LiftInfosMapper, LiftInfos> implements ILiftInfosService {
    @Override
    public List<LiftInfos> queryList(int status) {
        QueryWrapper<LiftInfos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",status);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public LiftInfos getLiftInfoByLineNo(String liftNo) {
        QueryWrapper<LiftInfos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lift_no",liftNo);
        return baseMapper.selectOne(queryWrapper);
    }
}
