package kc.struct;


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
public class SocketInfosPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String ip;

    private Integer port;

    private Integer status;

    private String remark;

    private Date createdDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    private Date updatedDate;

}
