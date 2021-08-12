package com.zhangtl.binarysearchtree;

import com.zhangtl.binarysearchtree.printer.BinaryTrees;
import org.junit.Test;

import java.util.List;

public class BinaryTest {
    @Test
    public void testBinarySearchTree(){
        Integer[] arr = new Integer[]{1,2,5,6,8,3,4};
        BinarySearchTree<Comparable> searchTree= new BinarySearchTree<>();
        for (Integer integer : arr) {
            searchTree.add(integer);
        }
        BinaryTrees.print(searchTree);
        List<Comparable> nodes = searchTree.preorderTransfer();
        System.out.println();
        System.out.println(nodes);
        System.out.println();
        searchTree.inOrderTransfer();
    }
}
