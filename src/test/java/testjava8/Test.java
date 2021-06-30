package testjava8;

import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.ir.ReturnNode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    @org.junit.Test
    public void test() {
        List<Integer> list = Stream.of(1,2,3).collect(Collectors.toList());
        list.forEach(System.out::println);
    }

    @org.junit.Test
    public void test1() {
        testMethod(a-> System.out.println(3));
    }

    public void testMethod(TestInterface testInterface){
        testInterface.execute(1);
    }
}
