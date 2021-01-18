package com.zhangtl.queue;

import java.util.Arrays;

/*
    优化点
    1.对于 所有的  (head + i) % elements.length 来说实质上都是对数据当前索引对于实际索引的映射所以直接抽出公共方法


* */
public class CircleQueue<E> {
    private int size;
    private E[] elements;
    private int head;
    CircleQueue(){
        elements = (E[]) new Object[10];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void addQueue(E element){
        if(size >= elements.length){
            explorsize();
        }
        elements[index(size)] = element;
        size++;
    }
    /*扩容*/
    private void explorsize() {
        E[] temps = (E[]) new Object[size << 1];
        for (int i = 0; i < elements.length ; i++) {
            int index =index(i);
            temps[i] = elements[index];
        }
        elements = temps;
        head = 0;
    }

    public E deQueue(){
        if(size == 0){
            throw new RuntimeException("索引越界");
        }
        if(size < elements.length>>2){
            deExplorsize();
        }
        E element = elements[head];
        elements[head] = null;
        head = index(1);
        size--;
        return element;
    }
    /*缩容*/
    private void deExplorsize() {
        E[] temps = (E[]) new Object[elements.length >> 2];
        for (int i = 0; i <temps.length ; i++) {
            int index = index(i);
            temps[i] = elements[index];
        }
        elements = temps;
    }
    public E front(){
        if(size == 0){
            throw new RuntimeException("索引越界");
        }
        return elements[head];
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("head:").append( head).append(",size:").append(size).append(",");
        sb.append("[");
        for (int i = 0; i < elements.length ; i++) {
            if(i != 0){
                sb.append(",");
            }
            sb.append(elements[i] == null?"null":elements[i]);
        }
        sb.append("]");

        return sb.toString();
    }
    public int index(int index){
        return (head+ index) % elements.length;
    }
}
