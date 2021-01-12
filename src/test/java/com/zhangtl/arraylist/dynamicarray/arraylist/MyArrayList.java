package com.zhangtl.arraylist.dynamicarray.arraylist;

public class MyArrayList<E> {

    /**
     * 元素的数量
     */
    private int size;

    private E[] arryList;

//    private E[] EMPTY_LIST = (E[]) new Object[0];

    public MyArrayList() {
        this(0);
    }

    public MyArrayList(int size) {
        if (size < 0) {
            throw new RuntimeException("不能为0");
        }
        this.size = size;
        this.arryList = (E[]) new Object[size];
    }

    /**
     * 清除所有元素
     */
    /*这个地方不能这么写 因为EMPTY_LIST 是固定的地址，每次clear都会重新指向这个地址并不能
    * 清除所有数据*/
//    public void clear() {
//        this.arryList = EMPTY_LIST;
//    }
    /*所以要么全部数据置空，要么重新创建数组
    * 大多数情况不需要清空
    * */
    public void clear() {
        for (int i = 0; i < size; i++) {
            arryList[i] = null;
        }
        size = 0;
    }
    /**/
    /**
     * 元素的数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某个元素
     *
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    /**
     * 添加元素到尾部
     *
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /*判断数据是否超长进行扩容*/
    private void dilatationArray() {
        if (size >= arryList.length) {
            E[] temp = (E[]) new Object[arryList.length * 2 + 1];
            for (int i = 0; i < arryList.length; i++) {
                temp[i] = arryList[i];
            }
            arryList = temp;
        }
    }

    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);
        return arryList[index];
    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E temp = arryList[index];
        arryList[index] = element;
        return temp;
    }

    /**
     * 在index位置插入一个元素
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        /*缺少索引检查，假如size只有10，而index有100则会越界
         * 即使arrayList.length有1000但是这样数据是不连续的后续的普通
         * 的add到时候会覆盖这个插入的数据*/
        /*这里的检查应该放弃index=size的情况因为0-size-1肯定都满了
        * 所以允许进行插入，相当于add(element)*/
        rangeCheckForAdd(index);
        /*数据扩容这种写法也不正确，因为新增一条数据会使长度加1应该
         * 判断长度加1后是否需要扩容*/
        //dilatationArray();
        ensureCapacity(size + 1);
        /*替换数据*/
        /*这个地方的写法有错误*/
        /*首先不需要判断当前位置是否等于数组长度，因为如果数据不够就会进行扩容
         * 或者不会进入for循环条件*/
        /*第二个for循环不能从数组最大值开始，因为其中没有赋值，从arryList.length到size中间
         * 的数据操作都是浪费的操作*/
//        if(index == arryList.length){
//            arryList[index] = element;
//        }else {
//            for (int i = arryList.length-1; i >= index; i--) {
//                arryList[i] = arryList[i-1];
//            }
//        }
//        set(index,element);

        /*正确写法*/
        for (int i = size; i > index; i--) {
            arryList[i] = arryList[i - 1];
        }
        arryList[index] = element;
        size++;
    }

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        /*这里也要加索引检查，删除之后的数据无意义*/
        rangeCheck(index);
        E temp = arryList[index];
        /*同样这里也要用size，因为之后的数据操作也没有意义*/
//        for (int i = index; i < arryList.length-1; i++) {
//            arryList[i] = arryList[i+1];
//        }
        for (int i = index; i < size; i++) {
            arryList[i - 1] = arryList[i];
        }
        /*这里忘记了size-1和最后两位有重复数据应该删除最后一位*/
        /*这里应该是为什么ArrayList必须使用包装类型而不能使用基本类型
         * 应该是为了防止置空和比较的时候的麻烦*/
        arryList[--size] = null;
        return temp;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    public int indexOf(E element) {
        /*这种写法首先增加了空间复杂度，每次需要赋值一个栈对象*/
        /*同时所有的为空的进行了多的判断*/
//        for (int i = 0; i < arryList.length; i++) {
//            E e = arryList[i];
//            if(e == null){
//                if(element == null){
//                    return  i;
//                }
//            }else {
//                if(e == element || e.equals(element)){
//                    return  i;
//                }
//            }
//        }
        /*这个写法更好*/
        if (element == null) {  // 1
            for (int i = 0; i < size; i++) {
                if (arryList[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(arryList[i])) return i; // n
            }
        }
        return -1;
    }


    /**
     * 保证要有capacity的容量
     *
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = arryList.length;
        if (oldCapacity >= capacity) return;

        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = arryList[i];
        }
        arryList = newElements;

        System.out.println(oldCapacity + "扩容为" + newCapacity);
    }

    private void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

}
