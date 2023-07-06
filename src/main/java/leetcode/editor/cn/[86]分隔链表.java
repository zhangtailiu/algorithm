import com.zhangtl.algorithmpdf.onehundred.entity.ListNode;

//leetcode submit region begin(Prohibit modification and deletion)


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode partition(ListNode head, int x) {
        // 1. 定义两个链表，一个链表存储小于x的节点，一个链表存储大于等于x的节点
        // 2. 遍历原链表，将小于x的节点放入小链表，大于等于x的节点放入大链表
        ListNode lt = new ListNode(-1);
        ListNode gt = new ListNode(-1);
        ListNode lcur = lt;
        ListNode gcur = gt;
        while (head != null){
            if(head.val < x){
                lcur.next = head;
                lcur = lcur.next;
            }else {
                gcur.next = head;
                gcur = gcur.next;
            }
            head = head.next;
        }
        //将大链表的最后一个节点的next置为null因为如果最后一个节点是小于x
        //的情况下，会出现循环链表
        gcur.next = null;
        //合并2个链表
        lcur.next = gt.next;
        return lt.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
