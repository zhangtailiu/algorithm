package com.zhangtl.linkedlist;

/*
* 双向链表循环列表
*
* 对于双向循环列表来说只有新增和删除有改变
* */
public class CircleDoubleLinkedList<E> {
    /*头结点*/
    public Node<E> first;
    /*尾结点*/
    public Node<E> last;
    /*大小*/
    public int size;
    public E get(int index){
        return node(index).elemnt;
    }
    public Node<E> node(int index){
        /*检查数据越界情况*/
        checkrange(index);
        /*小于长度一半的从开头找*/
        if(index < (size >> 1)){
            Node<E> node = first;
            for (int i = 0; i < index ; i++) {
                node = node.next;
            }
            return node;
            /*大于长度一半的从末尾找*/
        }else {
            Node<E> node = last;
            for (int i = size -1; i > index ; i--) {
                node = node.pre;
            }
            return node;
        }
    }

    public Node<E> set(int index , Node<E> element){
        Node<E> src = node(index);
        src.pre.next = element;
        src.next.pre = element;
        return src;
    }

    public void clear(){
        size = 0;
        first = null;
        last = null;
    }

    public int indexOf(E element){
        int result = -1;
        Node<E> node = first;
        for (int i = 0; i < size ; i++) {
            /*判断两者是否相等
            * 1. 判断首先进来的是否是 null 是null 则用 ==
            * 2. 否则判断equals是否相等
            * */
            if(element == null){
                if(node.elemnt == null){
                    return i;
                }
            }else {
                if(element.equals(node.elemnt)){
                    return i;
                }
            }
            node = node.next;
        }
        return result;
    }

    public boolean contains(E element){
        int i = indexOf(element);
        return i>=0;
    }


    public int size(){
        return size;
    }

    public void add(E element){
        add(size,element);
    }

    /*
    * 对于链表的增加实质上是两种介入数据的方式
    * 1.数据加入在尾部
    * 2.数据加入在中间和头部
    * */

    public void add(int index,E element){
        checkrangeForAdd(index);
        /*
        * 如果相较于加入到尾部的情况
        * 对于数据来说实际的结果是
      first 1  =  2  =  3  last
        *   \\   =   //
        * 如果有数据的情况(超过2个数据)
        * 1.保存之前的last
        * 2.创建新节点 pre=之前的last next=first
        * 3.修改之前节点的next=新创建的节点
        * 4.修改first.pre=新创建的节点
        * 对于0数据的情况
        * 不同之处在于 之前节点的last == null first==null
        * 所以需要将last和first都设置为新创建的节点 并且新创建的节点的pre和last都是自己
        * 对于1数据的情况和多数据情况一致
        * 其实逻辑有区别，在创建新节点时 创建的节点的 pre 和 next 都是原先的 1 但是就是最后正常的结果 所以正常
        *
        * */
        if(index == size){
            Node<E> ordlast = last;
            last = new Node<>(element, ordlast, first);
            if(ordlast == null){
                first = last;
                first.next = last;
            }else {
                ordlast.next = last;
            }
            first.pre = last;
        /*
           对于插入到头部和中央的结果
        * 对于数据来说实际的结果是
      first 1  =  2  =  3  last
        *   \\   =   //
        * 对于这个整体循环来说 在任何地方插入数据都是一样的逻辑
        * 1.首先获取到插入的位置
        * 2.已原先的数据的 prev 和 他自己 构建中间节点
        * 3.上一个的next=新创建节点  下一个的pre=新创建节点
        *
        * 只有在插入到first时需要对当前插入的数据作为新的first
        * 对于1数据来说 效果和多数据一致
        其实逻辑有区别，在创建新节点时 创建的节点的 pre 和 next 都是原先的 1 但是就是最后正常的结果 所以正常
        * */
        }else {
            Node<E> next = node(index);
            Node<E> pre = next.pre;
            Node<E> node = new Node<>(element, pre, next);
            if(next.equals(first)) { //index == 0
                first = node;
            }
            pre.next = node;
            next.pre = node;
        }
        size ++;

    }
    public E remove(int index){
        checkrange(index);
        Node<E> node = node(index);
        Node<E> pre = node.pre;
        Node<E> next = node.next;
        if(pre == null){
            first = next;
        }else {
            pre.next = next;
        }
        if(next == null){
            last = pre;
        }else {
            next.pre = pre;
        }
        size--;
        return node.elemnt;
    }


    private void checkrangeForAdd(int index) {
        if(index < 0 || index > size){
            throw new RuntimeException("索引越界");
        }
    }

    private void checkrange(int index) {
        if(index < 0 || index >= size){
            throw new RuntimeException("索引越界");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(size > 0){
            Node<E> node = first;
            for (int i = 0; i < size ; i++) {
                if(i != 0){
                    sb.append(",");
                }
                sb.append(node);
                node = node.next;
            }
        }
        sb.append("]");
        return sb.toString();

    }

    public class Node<E> {
        public   E  elemnt;
        public Node<E> pre;
        public Node<E> next;

        public  Node(E elemnt, Node<E> pre, Node<E> next) {
            this.elemnt= elemnt;
            this.pre = pre;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(pre != null){
                sb.append(pre.elemnt);
            }else {
                sb.append("null");
            }
            sb.append("_").append(elemnt).append("_");
            if(next != null){
                sb.append(next.elemnt);
            }else {
                sb.append("null");
            }
            return sb.toString();
        }
    }
}
