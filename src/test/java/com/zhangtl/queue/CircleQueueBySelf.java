package com.zhangtl.queue;

import java.util.Arrays;

/*
* 循环队列 使用数组形式实现的队列 自己写的版本 参照 CircleQueue 实现版本
*
* 重大错误 ( head + i) % elements.length
*  对于不超过最大长度的等于自身，超过最大长度的取数据取模最大长度
*  head + i - elements.length
*         if(index < 0 ){
                index = index * -1;
            }
*自己原先的想法是这种 这种方案的想法是不对的对于在最大长度之内的根本不适用
* */
public class CircleQueueBySelf<E> {
    private int size;
    private E[] elements;
    private int head;
    CircleQueueBySelf(){
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
        int index = (head + size) % elements.length;
        elements[index] = element;
        size++;
    }
    /*扩容*/
    private void explorsize() {
        E[] temps = (E[]) new Object[size << 1];
        for (int i = 0; i < elements.length ; i++) {
            int index =( head + i) % elements.length;
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
        int newHead = ( head + 1) % elements.length;
        if(newHead < 0){
            newHead = newHead * -1;
        }
        head = newHead;
        size--;
        return element;
    }
    /*缩容*/
    private void deExplorsize() {
        E[] temps = (E[]) new Object[size >> 2];
        for (int i = 0; i <temps.length ; i++) {
            int index = (head + i) % elements.length;
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
}
