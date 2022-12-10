package com.zhangtl.algorithmstudy.al_01;

/**
 * 可变数组构建
 * -- 原因：数组创建之后长度固定而实际应用中需要很多不定长度的数组
 * -- 核心点：
 * 1。数据存储使用数组
 * 2。数据的索引检查(即操作和位置相关的元素时不能超过数据存储的数组的[0,size))
 * 3。数据的扩容缩容(防止无用的空间浪费)
 * -- 补充细节
 * 1。数组初始化时
 *
 * @param <E>
 */
public class MyArrayListImpl<E> implements MyArrayList<E> {
    private int size;
    private E[] data;

    MyArrayListImpl() {
        this.size = 0;
        this.data = (E[]) new Object[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    /**
     * 1.首先检查插入的索引是否在数据范围内[0,size]这里包括size位是因为add边界的情况 不在[-max,0)||(size,max]则直接报错
     * 2。扩容 在新增之前扩容而不是新增后扩容(是否有区别？)
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        indexCheck(index);
        expandCheck(size + 1);
        /**
         * 这里分 2种情况
         * size < length; 直接往后移动一位没关系
         * size = length; 这里因为上一步已经扩容 所以也不会越界
         */
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        size++;
    }

    /**
     * 删除时要检查在索引[0,size)内 不在[-max,0) || [size,max] 则报错
     * 注意删除之后判断缩容(ArrayList未进行缩容)
     *
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        indexCheckWithSzie(index);
        E temp = data[index];
        /**
         * 这里分 2种情况
         * index < size-1; 从index+1 到 size-1的索引都往前移动一位 然后size-1设置为null
         * index = szie-1; size-1直接设置为null
         */
        for (int i = index; i < size; i++) {
            data[index] = data[index + 1];
        }
        data[size - 1] = null;
        size--;
        return temp;
    }


    /**
     * 修改时要检查在索引[0,size)内 不在[-max,0) || [size,max] 则报错
     *
     * @param index
     * @return
     */
    @Override
    public E set(int index, E element) {
        indexCheck(index);
        E temp = data[index];
        data[index] = element;
        return temp;
    }

    /**
     * 查询时要检查在索引[0,size)内 不在[-max,0) || [size,max] 则报错
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        indexCheck(index);
        return data[index];
    }

    /**
     * @param element
     * @return
     */
    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i] == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        size = 0;
    }

    //检查是否要缩容
    private void reduceCheck(int curSize) {
        if (curSize < data.length / 4) {
            E[] temp = (E[]) new Object[data.length / 2];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
    }

    //检查是否要扩容
    private void expandCheck(int curSize) {
        if (data.length < curSize) {
            E[] temp = (E[]) new Object[size * 2 + size];
            for (int i = 0; i < data.length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
    }

    private void indexCheckWithSzie(int index) {
        if (index < 0 && index >= size) {
            throw new RuntimeException("index out of range");
        }
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size) {
            throw new RuntimeException("index:" + index + " out of range");
        }
    }
}
