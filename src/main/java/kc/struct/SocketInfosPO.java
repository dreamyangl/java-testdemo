package kc.struct;


import lombok.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
@Builder
@ToString
public class SocketInfosPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ip;

    private Integer port;

    private Integer ipI;

    public static void main(String[] args) {
        SocketInfosPO socketInfosPO1 = SocketInfosPO.builder()
                .ip("5")
                .port(8080)
                .build();

        SocketInfosPO socketInfosPO2 = SocketInfosPO.builder()
                .ip("2")
                .port(8080)
                .build();

        SocketInfosPO socketInfosPO3 = SocketInfosPO.builder()
                .ip("3")
                .port(8080)
                .build();
        List<SocketInfosPO> list = Stream.of(socketInfosPO1,socketInfosPO2,socketInfosPO3).collect(Collectors.toList());
        list.stream().filter(x->!"-".equals(x.getIp())).peek(x-> x.setIpI(Integer.parseInt(x.getIp()))).sorted(Comparator.comparing(SocketInfosPO::getIpI)).forEach(System.out::println);
    }

}
