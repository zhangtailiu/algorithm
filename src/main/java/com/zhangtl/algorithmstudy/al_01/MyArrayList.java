package com.zhangtl.algorithmstudy.al_01;

public interface MyArrayList<E> {
    //数组大小
    int size();
    //是否为空
    boolean isEmpty();
    //添加元素
    void add(E element);
    //在制定位置添加元素
    void add(int index,E element);
    //删除元素
    E remove(int index);
    //设置元素
    E set(int index,E element);
    //获取元素
    E get(int index);
    //元素所在位置
    int indexOf(E element);
    //清除元素
    void clear();
}
