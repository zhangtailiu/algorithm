package com.zhangtl.algorithmpdf.onehundred.entity;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    public static ListNode byArray(int[] arr){
        ListNode listNode = new ListNode();
        ListNode head = listNode;
        for (int i = arr.length-1; i >= 0; i--) {
            listNode.next = new ListNode(arr[i]);
            listNode = listNode.next;
        }
        return head.next;
    }
}
