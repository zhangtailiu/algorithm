package com.zhangtl.binarysearchtree;

public class Node<E> {
    public Node<E> parent;
    public Node<E> left;
    public Node<E> right;
    public E data;

    public  Node(E data,Node<E> parent) {
         this(data,parent,null,null);
    }

    public Node(E data ,Node<E>  parent, Node<E>  left, Node<E> right) {
        this.left = null;
        this.right = null;
        this.data = data;
        this.parent = parent;
    }
    public boolean hasTwoNode(){
        return left != null && right != null;
    }
    public boolean isLeaf(){
        return left == null && right == null;
    }
}
