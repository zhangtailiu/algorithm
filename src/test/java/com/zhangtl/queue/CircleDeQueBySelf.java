package com.zhangtl.queue;

/*
* 循环双端队列 自己的版本 （不可用版本）
*
*  */
public class CircleDeQueBySelf<E> {
    private int size;
    private E[] elements;
    private int head;
    private int last;
    CircleDeQueBySelf(){
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
        int index = index(size);
        elements[index] = element;
        last = index;
        size++;
    }

    public void addHead(E element){
        if(size >= elements.length){
            explorsize();
        }
        int index = (head - 1 + elements.length) % elements.length;
        elements[index] = element;
        head = index;
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
        last = size -1;
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
        int newHead = index(1);
        if(newHead < 0){
            newHead = newHead * -1;
        }
        head = newHead;
        size--;
        return element;
    }

    public E deQueueLast(){
        if(size == 0){
            throw new RuntimeException("索引越界");
        }
        if(size < elements.length>>2){
            deExplorsize();
        }
        E element = elements[last];
        elements[last] = null;
        int newLast = (last -1 +elements.length) % elements.length;
        last = newLast;
        size--;
        return element;
    }




    /*缩容*/
    private void deExplorsize() {
        E[] temps = (E[]) new Object[elements.length >> 2];
        for (int i = 0; i <elements.length ; i++) {
            int index = index(i);
            temps[i] = elements[index];
        }
        elements = temps;
        head = 0;
        last = size -1;
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
