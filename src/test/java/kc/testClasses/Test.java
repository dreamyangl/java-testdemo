package kc.testClasses;

import kc.model.door.Close;
import kc.model.door.Context;
import kc.model.door.Open;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import kc.TestStarter;

import java.util.ArrayList;
import java.util.List;

public class Test extends TestStarter {
    @Autowired
    private Open open;
    @Autowired
    private Close close;
    private List<String> list = new ArrayList<>();
    @Before
    public void before() {
        list.add("open");
        list.forEach(System.out::println);
    }

    @After
    public void after() {
        list.forEach(System.out::println);
    }

    @org.junit.Test
    public void testOpen() {
        new Context(open).operation("test", "OPEN",list);
    }

    @org.junit.Test
    public void testClose() {
        new Context(close).operation("test", "close",list);
    }
}
