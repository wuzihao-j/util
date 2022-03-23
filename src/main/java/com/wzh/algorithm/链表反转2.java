package com.wzh.algorithm;

/**
 * @author wuzihao3
 * @date 2022/3/23
 */
public class 链表反转2 {

    public static void main(String[] args) {
        ListNode e = new ListNode(5, null);
        ListNode d = new ListNode(4, e);
        ListNode c = new ListNode(3, d);
        ListNode b = new ListNode(2, c);
        ListNode a = new ListNode(1, b);

        ListNode listNode = reverseByRange(a, 2, 5);
        while (listNode != null) {
            System.out.print(listNode.value + " ");
            listNode = listNode.next;
        }
    }


    public static ListNode reverseByRange(ListNode head, int begin, int end) {
        //先找到要反转的范围
        ListNode reverseHead = head;
        //第一段的尾部
        ListNode firstNotReverseEnd = reverseHead;

        for (int i = 1; i < begin; i++) {
            firstNotReverseEnd = reverseHead;
            reverseHead = reverseHead.next;
        }

        //反转开始的头部
        ListNode reverseBegin = reverseHead;
        //对范围内的链表进行反转
        /**
         * current
         * next
         * nextNext
         */
        ListNode current = reverseHead;
        ListNode next = reverseHead.next;
        ListNode nextNext = next.next;
        for (int i = begin; i < end; i++) {
            //反转指针
            next.next = current;
            if (nextNext == null) {
                break;
            }
            //往后移动
            current = next;
            next = nextNext;
            nextNext = nextNext.next;

        }
        //重新连接链表
        firstNotReverseEnd.next = current;
        reverseBegin.next = next;
        if (begin == 1) {
            reverseHead.next = null;
            return next;
        }
        return head;
    }

    public static class ListNode {

        private Integer value;

        private ListNode next;

        public ListNode(Integer value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }
}
