package com.zhangtl.binarysearchtree;

import com.zhangtl.binarysearchtree.printer.BinaryTreeInfo;

import java.util.*;

/*
* 二叉搜索树
* */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size;
    /*
    * 最好把数据相关内容存放在 Node 对象中
    * 对于一个二叉搜索树只要保存根节点的数据即可
    * */
    private Node<E> root;
    /*可以通过传入比较器来实现覆盖默认的比较compare*/
    private Comparator<E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /*
    * 中序遍历
    * */

    public void  inOrderTransfer(){
        inOrderTransfer(root);
    }
    public void  inOrderTransfer(Node<E> parent){
        if (parent == null) {
            return;
        }
        inOrderTransfer(parent.left);
        System.out.print(parent.data);
        inOrderTransfer(parent.right);

    }

    /*
    * 前置遍历
    * */
    public List<E> preorderTransfer(){
        List<E> list = new ArrayList<>();
        /*递归方式*/
//        preorderTransfer(root);
        /*非递归方式*/
        preorderTransfer(root);
        return list;
    }
    /*
    * 思路
    * 1.首先判断当前节点是否是null
    * 2.当前节点不空的情况 判断左节点是否为空
    *    - 1 不为空 直接打印
    *    - 2 空 判断右子节点是否为空
    *        -1 不为空 打印
    *        -2 右子节点为空回到上一层
    *
    *
    *
    * */


    public void  preorderTransferNodg(Node<E> parent){
        if(parent == null){
            return;
        }
        Set<E> set = new HashSet<>();
        /*上一个数据*/
        while (parent!=null){
            set.add(parent.data);
            System.out.println(parent.data);
            if(!set.contains(parent.data) && parent.left != null){
                parent = parent.left;
                continue;
            }else if(parent.right != null){
                parent = parent.right;
                continue;
            }else {
                parent = parent.parent;
            }
        }
    }

    /*
    * 前序遍历递归方式
    * 前序遍历的实质是 首先访问根节点 然后永远先访问左子树除非左子树是null才会访问右子树然后回到上一层再访问右子树
    * */
    private void preorderTransfer( Node<E> parent) {
        if (parent == null) {
            return;
        }
        System.out.println(parent.data);
        preorderTransfer(parent.left);
        preorderTransfer(parent.right);
    }


    /*添加方法*/
    public void add(E data) {
        /*数据检查*/
        checkElementNotNull(data);
        /*如果根节点为空的话*/
        if (root == null) {
            root = new Node<E>(data,null);
        }
        /*否则*/
        Node<E> parent = root;
        Node<E> temp = root;
        int compare = 0;
        /*
        * 一直循环比较直到比到叶子节点的 左或者右 肯定为 null 然后保存叶子节点的指针
        * */
        while (temp != null) {
            compare = compare(data, temp.data);
            parent = temp;
            if (compare > 0) {
                temp = temp.right;
            } else if (compare < 0) {
                temp = temp.left;
            } else {
                return;
            }
        }
        /*
        * 把叶子节点的地址 和是左节点还是又节点数据存入
        * */
        if (compare > 0) {
            parent.right = new Node<>(data,parent);
        } else {
            parent.left = new Node<>(data,parent);
        }


    }
    /*
    * 检验数据不能为null
    * */
    private void checkElementNotNull(E data) {
        if(data == null){
            throw new RuntimeException("element must not null");
        }
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node) node).data;
    }
}
