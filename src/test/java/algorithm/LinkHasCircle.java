package algorithm;

import java.util.HashSet;
import java.util.Set;

public class LinkHasCircle {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            //如果重复出现说明有环
            if (set.contains(head))
                return true;
            //否则就把当前节点加入到集合中
            set.add(head);
            head = head.next;
        }
        return false;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
