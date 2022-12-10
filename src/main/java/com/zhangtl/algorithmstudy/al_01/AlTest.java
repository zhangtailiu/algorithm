package com.zhangtl.algorithmstudy.al_01;

import org.junit.Test;

public class AlTest {

    @Test
    public void testMyArrayList(){
        MyArrayList<Integer> list = new MyArrayListImpl<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(0);
        list.remove(2);
        list.remove(0);
        list.remove(0);
        System.out.printf("list "+ list.size()+": isEmpty "+list.isEmpty());
    }


}
