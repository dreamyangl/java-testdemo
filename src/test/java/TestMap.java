import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestMap {
    @Test
    public void test1() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        //1,2 增强for循环遍历
        //使用keySet()遍历
        for (String key : map.keySet()) {
            System.out.println(key + " ：" + map.get(key));
        }
        //使用entrySet()遍历
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " ：" + entry.getValue());
        }
        //3,4 迭代器遍历
        //使用keySet()遍历
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + "　：" + map.get(key));
        }
        //使用entrySet()遍历
        Iterator<Map.Entry<String, String>> iterator1 = map.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, String> entry = iterator1.next();
            System.out.println(entry.getKey() + "　：" + entry.getValue());
        }

        //测试性能  10w数据

        //结果
        /**
         * 增强for循环，keySet迭代 -> 37 ms
         * 增强for循环，entrySet迭代 -> 19 ms
         * 迭代器，keySet迭代 -> 14 ms
         * 迭代器，entrySet迭代 -> 9 ms
         * */
        //结论
        /**
         * 增强for循环使用方便，但性能较差，不适合处理超大量级的数据。
         *
         * 迭代器的遍历速度要比增强for循环快很多，是增强for循环的2倍左右。
         *
         * 使用entrySet遍历的速度要比keySet快很多，是keySet的1.5倍左右
         */

    }
}
