package com.zhangtl.binarysearchtree.leetcode;

class Solution {
    public TreeNode invertTree(TreeNode root) {
        pre(root);
        return root;
    }
    public void pre(TreeNode root){
        if(root == null) return;
        TreeNode temp = null;
        temp = root.left;
        root.left = root.right;
        root.right = temp;
        pre(root.left);
        pre(root.right);
    }
}
