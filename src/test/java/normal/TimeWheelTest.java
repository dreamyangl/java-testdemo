package normal;

import io.netty.util.HashedWheelTimer;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeWheelTest {
    private static volatile boolean state;
    private static HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(3, TimeUnit.SECONDS, 15);

    public static void main(String[] args) throws InterruptedException {
        List<String> list = mockQuery();
        if (list.contains("error")){
            state = false;
            //模拟报警5秒钟
            System.out.println(111);
            hashedWheelTimer.newTimeout(timeout -> {
                if (state){
                    return;
                }
                System.out.println(222);
            },5,TimeUnit.SECONDS);
        }
    }

    public static List<String> mockQuery(){
        return Stream.of("normal","error","error","error").collect(Collectors.toList());
    }



}
