import org.junit.Test;

import java.util.TreeSet;

/**
 * TreeSet排序
 * 1:让存入的元素自定义比较规则。  需要元素实现Comparable接口，重写compareTo方法
 *
 * 2:给TreeSet指定排序规则。 那么此时可以让容器自身具备。需要定义一个类实现接口Comparator，重写compare方法，并将该接口的子类实例对象作为参数传递给TreeMap集合的构造方法。
 */
public class TestListAndSet {
    @Test
    public void test1() {
        TreeSet treeSet = new TreeSet();
        String s = "aaa";
    }

    @Test
    public void test2() {

    }
}
