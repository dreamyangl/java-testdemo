package kc.testClasses;

import org.junit.Test;

import java.util.Optional;

public class TestOptional {
    @Test
    public void test() {
        Optional<String> optionalS = Optional.of("111");
        System.out.println(optionalS.map(x->x+"222").get());
    }
}
