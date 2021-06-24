package normal;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
    @Test
    public void test() {
        Map<String,String> map = new HashMap<>();
        map.put("test1","test1");
        map.put("test2","test2");
        map.put("test3","test3");
        System.out.println(map.size());
    }
}
