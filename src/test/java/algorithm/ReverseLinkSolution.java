package algorithm;

public class ReverseLinkSolution {
    public ListNode ReverseList(ListNode head) {
        //1->2->3->4
        //4->3->2->1
        //初始化pre指针，用于记录当前结点的前一个结点地址
        ListNode pre = null;
        //初始化p指针，用于记录当前结点的下一个结点地址
        ListNode p = null;
        //head指向null时，循环终止。
        while(head != null){
            //先用p指针记录当前结点的下一个结点地址。
            p = head.next;      //p=2
            //让被当前结点与链表断开并指向前一个结点pre。
            head.next = pre;    //head.next = null
            //pre指针指向当前结点
            pre = head;     //pre = 1
            //head指向p(保存着原链表中head的下一个结点地址)
            head = p;      //head = 2
        }
        return pre;//当循环结束时,pre所指的就是反转链表的头结点
    }

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        ReverseLinkSolution reverseLinkSolution = new ReverseLinkSolution();
        ListNode result = reverseLinkSolution.ReverseList(listNode1);
        System.out.println(result.val);
    }
}
