package com.zhangtl.linkedlist;

/*
* 双向链表
* */
public class DoubleLinkedList<E> {
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

    /*思考顺序 中文数字*/
    public void add(int index,E element){
        /*
          一
        * 1.首先思考中间节点，然后思考首位节点  先实现通用的方案，再考虑特殊方案
        * */
        /*新增检查小于0或者大于size就行*/
        checkrangeForAdd(index);
        /*
           四
        * 对于添加到末尾的情况
        * */
        if(index == size){
            /*五  先考虑通用情况即有前方数据*/
            /*
            * 1.需要创建新对象索引到之前的最后一个 和下一个为 null
            * 2.last指向新对象
            * 3.之前的last的next指向当前的对象
            * */
            Node<E> ordlast = last;
            last = new Node<>(element, ordlast, null);
            /* 六  对于ordlast是之前的last 在链表初始情况时可能为 null 所以 ordlast.next可能空指针*/
            if(ordlast == null){
                first = last;
            }else {
                ordlast.next = last;
            }

        }else {
            /*
         二
        中间节点需要修改的部分是
          首先找到之前index数据的对象 并把这个对象往后挪一位
        * 1.对于插入中间的元素 需要索引到上一个和下一个
        * 2.对于上一个需要修改下一个的地址到新添加元素
        * 3.对于下一个需要修改上一个的地址到新添加元素
        * */
            /*1*/
            Node<E> next = node(index);
            Node<E> pre = next.pre;
            Node<E> node = new Node<>(element, pre, next);
            /*2*/
        /*
            三
            因为对于中间位置的数据来说 next 不可能为 null 所以next.pre 不会空指针
         * 但是对于next.pre在add到0的位置的时候可能为null 所以需要对pre判断空指针
         * */
            if(pre == null){
                first = node;
            }else {
                pre.next = node;
            }
            next.pre = node;

        }

        /*七  新加一条数据大小加1*/
        size ++;

    }
    /*
    * 自己首先写的版本
    * */
    public E removeBank(int index){
        checkrange(index);
        /*
        * 一 删除先考虑中间情况最后考虑两边
        * */
        /*
         二
        * 1.找到应删除节点
        * 2.使上个节点的next指向删除节点的next
        * 3.使下个节点的prev指向删除节点的prev
        * */
        Node<E> node = node(index);
        /*
         三
         * 考虑左右边界情况(包含2个数据以上)
         * 1.左边界 所删除的上个节点 node.pre 为null 所以node.pre.next空指针
         *    1-1 首先删除节点的下一个节点的pre应该指向null
         *    1-2 first 应该指向删除节点的下一个节点
         * 2.右边界 所删除的节点的下一个节点是null 所以node.next.pre空指针
         *    2-1 首先删除节点的上一个节点的next应该指向null
         *    2-2 last 应该指向删除节点的上一个节点
         * 考虑即是左有是右边界情况(仅1个数据)
         * 3.即删除最后一条数据设置头尾皆为 null
         *
         * */
        Node<E> pre = node.pre;
        Node<E> next = node.next;
        /*中间情况*/
        if(pre != null && next != null){
            pre.next =node.next;
            next.pre = node.pre;
        /*左边界*/
        }else if(pre == null && next != null){
            next.pre = null;
            first = next;
        /*右边界*/
        }else if(pre != null && next == null){
            pre.next = null;
            last = pre;
        /*同时在左边界和友边界 即 pre == null && next == null 即只有一条数据的情况*/
        }else {
            first =null;
            last = null;
        }
        size--;
        return node.elemnt;
    }
    /*
    * 对于上面的这个版本的不足
    * 1.对于已存在的数据的并没有很好的复用 比如 node.next和next的实质是一个东西
    * 2.首先考虑的情况正确，但是其实没有在进行进一步的思考
    * 应该进行对比 包含2个数据以上 和 一个数据 对于左边界 和 友边界 的逻辑有没有差别
    * 实际上在左边界的删除只需要处理 pre 和 next 即可对于本身的node 无需处理
    * 那么在pre的处理逻辑上 2个以上数据和 1个数据处理逻辑没有区别   next同理
    *
    * */
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
