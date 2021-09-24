package com.zhangtl.binarysearchtree;

import com.zhangtl.binarysearchtree.printer.BinaryTrees;
import org.junit.Test;

import java.util.List;

public class BinaryTest {
    @Test
    public void testBinarySearchTree(){
        Integer[] arr = new Integer[]{1,2,5,6,8,3,4};
        BinarySearchTreeSelf<Comparable> searchTree= new BinarySearchTreeSelf<>();
        for (Integer integer : arr) {
            searchTree.add(integer);
        }
        BinaryTrees.print(searchTree);
        List<Comparable> nodes = searchTree.preorderTransfer();
        System.out.println();
        System.out.println(nodes);
        System.out.println();
        searchTree.inOrderTransfer();
        System.out.println();
        System.out.println("=====");
        searchTree.levelOrderTransfer();
    }
    @Test
    public void testBinaryHeight(){
        Integer[] arr = new Integer[]{1,2,5,6,8,3,4,9};
        BinarySearchTreeSelf<Comparable> searchTree= new BinarySearchTreeSelf<>();
        for (Integer integer : arr) {
            searchTree.add(integer);
        }
        BinaryTrees.println(searchTree);
        System.out.println(searchTree.height());;
    }

    @Test
    public void testIsComplete(){
        Integer[] arr = new Integer[]{7,4,9,2,1,3};
        BinarySearchTreeSelf<Comparable> searchTree= new BinarySearchTreeSelf<>();
        for (Integer integer : arr) {
            searchTree.add(integer);
        }
        BinaryTrees.println(searchTree);
        System.out.println(searchTree.isComplete());;
    }
}
