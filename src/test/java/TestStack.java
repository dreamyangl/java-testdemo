import org.junit.Test;

import java.util.Stack;

/**
 * 栈分为顺序栈和链式栈
 * Stack 是线程安全的。
 * 内部使用数组保存数据，不够时翻倍。
 */
public class TestStack {
    @Test
    public void test1() {
        Stack stack = new Stack();
        stack.push(1);
    }
}
