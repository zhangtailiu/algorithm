package com.zhangtl.queue;

/*
* 循环双端队列
*  优化点
*   1.首先完全没必要使用last  head+size就是last
*   2. (head - 1 + elements.length) % elements.length 这个是生效的但是比较麻烦
*   实际上位置前移一位只有在head在0位置的时候是特殊的 其他位置就是可以直接减的 所以优化index方法就可以了
*
*
*  */
public class CircleDeQue<E> {
    private int size;
    private E[] elements;
    private int head;
    CircleDeQue(){
        elements = (E[]) new Object[10];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }
    /*
    * 从尾部添加
    * */
    public void addQueue(E element){
        if(size >= elements.length){
            explorsize();
        }
        elements[index(size)] = element;
        size++;
    }
    /*
     * 从头部添加
     * */
    public void addHead(E element){
        if(size >= elements.length){
            explorsize();
        }
        int index = index(-1);
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
    }
    /*
    * 从头部弹出
    * */
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
    /*
    * 从尾部弹出
    * */
    public E deQueueLast(){
        if(size == 0){
            throw new RuntimeException("索引越界");
        }
        if(size < elements.length>>2){
            deExplorsize();
        }
        int index = index(size - 1);
        E element = elements[index];
        elements[index] = null;
        size--;
        return  element;

    }




    /*缩容*/
    private void deExplorsize() {
        E[] temps = (E[]) new Object[elements.length >> 2];
        for (int i = 0; i <temps.length ; i++) {
            int index = index(i);
            temps[i] = elements[index];
        }
        elements = temps;
        head = 0;
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
        index += head;
        if(index<0){
            return index+elements.length;
        }
        return index % elements.length;
    }

}
