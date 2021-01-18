package com.zhangtl.queue;

public class CircleQueueTest {

    public static void  test(){
        CircleQueueBySelf<Integer> bySelf = new CircleQueueBySelf<>();
        for (int i = 0; i <10 ; i++) {
            bySelf.addQueue(i);
        }

        for (int i = 0; i <5 ; i++) {
            bySelf.deQueue();
        }

        System.out.println(bySelf);
//        for (int i = 15; i < 20 ; i++) {
//            bySelf.addQueue(i);
//        }

        for (int i = 15; i <25 ; i++) {
            bySelf.addQueue(i);
        }

        if(!bySelf.isEmpty()){
            System.out.println(bySelf);
        }

    }
    /*双端数组*/
    public static void  test2(){
        CircleDeQue<Integer> circleQueue = new CircleDeQue<>();
        for (int i = 0; i < 10 ; i++) {
            circleQueue.addHead(i+1);
            circleQueue.addQueue(100+i);
        }
        /*
        * 扩容第一次扩容前
        * 1,100,101,102,103,104,5,4,3,2
        * 第二次扩容前
        * 5,4,3,2,1,100,101,102,103,104
        * 第二次扩容
        * 5,4,3,2,1,100,101,102,103,104,105,106,107,108,109,10,9,8,7,6
        *
        * */
        for (int i = 0; i < 10 ; i++) {
            circleQueue.deQueue();
            circleQueue.deQueueLast();
        }

        System.out.println(circleQueue);
    }

    public static void main(String[] args) {
//        test();
        test2();
    }
}
