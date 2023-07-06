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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //首先从头节点开始对比
        //可以认为有3个链表 一个是最终的链表 一个是list1 一个是list2
        //每次从两个链表中取出一个小的节点暂存 然后链表向后迭代一次
        //再把这个节点加到最终的节点上
        ListNode result = new ListNode();
        ListNode current = result;
        while (list1 != null && list2 != null){
            if(list1.val < list2.val){
                current.next = list1;
                list1 = list1.next;
            }else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        //在判断另外一个链表是否后直接把非空节点赋值到当前节点后面
        if(list2 == null) {
            current.next = list1;
        }
        if(list1==null ) {
            current.next = list2;
        }

        return result.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
