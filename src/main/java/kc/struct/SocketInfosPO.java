package kc.struct;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * led呼叫盒基本信息(IP地址等,用作socket的连接)
 * </p>
 *
 * @author CaoGang
 * @since 2021-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketInfosPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ip;

    private Integer port;

}
