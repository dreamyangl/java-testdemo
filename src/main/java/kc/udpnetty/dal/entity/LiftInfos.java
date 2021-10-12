package kc.udpnetty.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * led呼叫盒基本信息(IP地址等,用作socket的连接)
 * </p>
 *
 * @author CaoGang
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LiftInfos对象", description="电梯基本信息(IP地址等,用作UDP发送数据)")
public class LiftInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "电梯号(规则待定)")
    private String liftNo;

    @ApiModelProperty(value = "电梯的ip地址")
    private String LiftIp;

    @ApiModelProperty(value = "电梯的端口")
    private Integer port;

    @ApiModelProperty(value = "0表示未启用，1表示启动")
    private Integer status;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updatedDate;

}
