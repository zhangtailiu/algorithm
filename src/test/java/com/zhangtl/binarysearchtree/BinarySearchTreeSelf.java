package com.zhangtl.binarysearchtree;

import com.zhangtl.binarysearchtree.printer.BinaryTreeInfo;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/*
* 二叉搜索树
* */
public class BinarySearchTreeSelf<E> implements BinaryTreeInfo {
    private int size;
    /*
    * 最好把数据相关内容存放在 Node 对象中
    * 对于一个二叉搜索树只要保存根节点的数据即可
    * */
    private Node<E> root;
    /*可以通过传入比较器来实现覆盖默认的比较compare*/
    private Comparator<E> comparator;

    public BinarySearchTreeSelf() {
    }

    public BinarySearchTreeSelf(Comparator<E> comparator) {
        this.comparator = comparator;
    }




    /*
    * 优化写法（减少了重复判断）
    *
    * 这种写法的优化方案是 没有多重判断 左右子节点的判断分离开并且插入子节点到队列和判断是否是完全二叉树的逻辑分开
    * 右子节点为空的直接判断为之后都要是叶子结点 ， 如果左子节点为空右子节点不为空的情况直接返回false
    *
    * */
    public boolean isComplete(){
        if(root == null) return false;
        Deque<Node<E>> deque = new LinkedList<>();
        deque.offer(root);
        boolean isLeaf = false;
        while (!deque.isEmpty()){
            Node<E> eNode = deque.poll();
            if(eNode != null){
                if(isLeaf && !eNode.isLeaf()) return false;
                if(eNode.left != null){
                    deque.offer(eNode.left);
                }else if(eNode.right != null){
                    return false;
                }
                if(eNode.right != null){
                    deque.offer(eNode.right);
                }else {
                    isLeaf =true;
                }
            }
        }
        return true;
    }

   /*是否是完全二叉树
   * 这里的是否完全二叉树的判断是通过层序遍历实现的
   * 逻辑：因为完全二叉树是根据层序遍历的顺序查看是否和满二叉树一样所以在通过从左到右从上到下的节点遍历时
   * 如果左右都有子节点则继续遍历 如果左子节点为null 右子节点不为null 则不是
   * 其他的情况判断 当前层的后面的数据是否全是叶子节点 是则为完全二叉树否则不是
   * (注意 这里相当于截断了所有的 其他情况这种树的下面所有层的判断 所以会有判断失误)
   *
   * */
    public boolean isCompleteBank(){
        if(root == null)return false;
        Deque<Node<E>> deque = new LinkedList<>();
        deque.offer(root);
        boolean isLeaf = false;
        while (!deque.isEmpty()){
            Node<E> eNode = deque.poll();
            if(eNode != null){
                if(isLeaf && !eNode.isLeaf()){
                    return false;
                }
                if(eNode.hasTwoNode()){
                    deque.offer(eNode.left);
                    deque.offer(eNode.right);
                }else if(eNode.left == null && eNode.right != null){
                    return false;
                }else {
                    if(eNode.left != null){
                        deque.offer(eNode.left);
                    }
                    isLeaf = true;
                }
            }
        }
        return true;
    }

    /*
    * 树的高度  循环方式
    * 使用层序遍历进行高度计算
    * 1.根节点的节点数量为1
    * 2.当上层节点遍历完成之后 deque的size就是下层节点的数量 最后当节点数量为0时 上面一层就是树的高度
    *
    * */

    public int height(){
        return height(root);
    }

    private int height(Node<E> root) {
        if(root == null) return 0;
        int height = 0;
        Deque<Node<E>> deque = new LinkedList<>();
        deque.offer(root);
        int leverSize = 1;
        while (!deque.isEmpty()){
            Node<E> pop = deque.poll();
            if(pop != null){
                if(pop.left != null){
                    deque.offer(pop.left);
                }
                if(pop.right != null){
                    deque.offer(pop.right);
                }
                if(--leverSize<=0){
                    leverSize = deque.size();
                    height++;
                }
            }
        }
        return height;
    }

    /*
    * 树的高度  递归方式
    * */
    public int heightByCycle(){
       return heightByCycle(root);
    }
    /*
    * 节点的高度
    * 每个节点的高度 等于其左子节点和右子节点高度高的节点高度的值 + 1
    * 如果节点为null则高度为0
    * */
    private int heightByCycle(Node<E> root) {
        if(root == null) return 0;
        return 1+Math.max(heightByCycle(root.left),heightByCycle(root.right));
    }

    /*
    * 对于树的遍历
    * 前 中 后序遍历指的是根节点在访问时的顺序
    * 前序遍历先访问根节点  中序遍历 根节点在左右中间访问 后序遍历 根节点在左右访问后最后访问
    *
    * 层序遍历是根据树的一层一层从左到右进行访问
    * */

    /*层序遍历（队列实现方案）
    * */

    public void levelOrderTransfer(){
        Deque<Node<E>> deque = new LinkedList<>();
        deque.offer(root);
        while (!deque.isEmpty()){
            Node<E> pop = deque.poll();
            if(pop != null){
                System.out.println(pop.data);
                if(pop.left != null){
                    deque.offer(pop.left);
                }
                if(pop.right != null){
                    deque.offer(pop.right);
                }
            }
        }
    }
    /*
    * 层序遍历双list实现方式(自己实现方案)
    * */
    public void levelOrderTransfer2List(){
        List<Node<E>> parentList = new ArrayList<>();
        List<Node<E>> nowList = new ArrayList<>();
        parentList.add(root);
        while (!parentList.isEmpty()){
            for (Node<E> eNode : parentList) {
                System.out.println(eNode.data);
                if(eNode.left != null){
                    nowList.add(eNode.left);
                }
                if(eNode.right != null){
                    nowList.add(eNode.right);
                }
            }
            parentList = nowList;
            nowList = new ArrayList<>();
        }
    }

    /*后序遍历
    * 先访问左子树
    * 然后访问右子树
    * 最后访问根节点
    * */
    public void  postOrderTransfer(){
        postOrderTransfer(root);
    }

    private void postOrderTransfer(Node<E> root) {
        if(root == null)return;
        postOrderTransfer(root.left);
        postOrderTransfer(root.right);
        System.out.println(root.data);
    }



    /*
    * 中序遍历(对于二叉搜索树 是升序或者降序的)
    * 首先访问左子树(左子树也从左子树开始访问而不是根节点)
    * 然后访问根节点(左子树的数据全部访问完)
    * 最后访问右子树(根节点访问完后访问右子数)
    * */

    public void  inOrderTransfer(){
        inOrderTransfer(root);
    }
    public void  inOrderTransfer(Node<E> parent){
        if (parent == null) {
            return;
        }
        inOrderTransfer(parent.left);
        System.out.print(parent.data);
        inOrderTransfer(parent.right);

    }

    /*
    * 前置遍历
    * 首先访问根节点 然后访问左子树(访问左子树时依然先访问左子树的根节点)
    * 然后访问右子树(只有在根节点和左子树节点数据完全访问完才会访问)
    * */
    public List<E> preorderTransfer(){
        List<E> list = new ArrayList<>();
        /*递归方式*/
//        preorderTransfer(root);
        /*非递归方式*/
        preorderTransfer(root);
        return list;
    }
    /*
    * 思路
    * 1.首先判断当前节点是否是null
    * 2.当前节点不空的情况 判断左节点是否为空
    *    - 1 不为空 直接打印
    *    - 2 空 判断右子节点是否为空
    *        -1 不为空 打印
    *        -2 右子节点为空回到上一层
    *
    *
    *
    * */


    public void  preorderTransferNodg(Node<E> parent){
        if(parent == null){
            return;
        }
        Set<E> set = new HashSet<>();
        /*上一个数据*/
        while (parent!=null){
            set.add(parent.data);
            System.out.println(parent.data);
            if(!set.contains(parent.data) && parent.left != null){
                parent = parent.left;
                continue;
            }else if(parent.right != null){
                parent = parent.right;
                continue;
            }else {
                parent = parent.parent;
            }
        }
    }

    /*
    * 前序遍历递归方式
    * 前序遍历的实质是 首先访问根节点
    * 然后永远先访问左子树除非左子树是null才会访问右子树然后回到上一层再访问右子树
    * */
    private void preorderTransfer( Node<E> parent) {
        if (parent == null) {
            return;
        }
        System.out.println(parent.data);
        preorderTransfer(parent.left);
        preorderTransfer(parent.right);
    }


    /*添加方法
    * 思考步骤
    * 1.作为二叉树每一个节点的信息都相同所以简化为一个内部类 Node 包含 left right parent 3个属性
    * 2.对于root为空的情况添加第一个节点
    * 3.循环从根节点向下判断，如果传入数据比当前节点大则拿右边进行比较否则使用左边进行比较 一直比较到
    * 数据为null，这个时候这个数据应当存放在这个位置，然后设置他的父节点为上一次比较的节点数据
    *
    *
    * */
    public void add(E data) {
        /*数据检查*/
        checkElementNotNull(data);
        /*如果根节点为空的话 为第一个节点*/
        if (root == null) {
            root = new Node<E>(data,null);
            size++;
            return;
        }
        /*否则*/
        Node<E> parent = root;//父节点数据记录
        Node<E> temp = root; //比较节点数据
        int compare = 0;
        /*
        * 一直循环比较直到比到叶子节点的 左或者右 肯定为 null 然后保存叶子节点的指针
        * */
        while (temp != null) {
            /*比较数据*/
            compare = compare(data, temp.data);
            /*将比较前的数据赋值给父节点*/
            parent = temp;
            /*如果比较左边大于右边  即传入的值大于节点值 使用右节点进行比较*/
            if (compare > 0) {
                temp = temp.right;
                /*如果比较左边小于右边  即传入的值小于节点值 使用左节点进行比较*/
            } else if (compare < 0) {
                temp = temp.left;
            /*相等直接返回*/
            } else {
                temp.data = data;
                return;
            }
        }
        /*
        * 把叶子节点的地址 和是左节点还是又节点数据存入
        * */
        Node<E> eNode = new Node<>(data, parent);
        if (compare > 0) {
            parent.right = eNode;
        } else {
            parent.left = eNode;
        }
        size++;
    }
    /*
    * 检验数据不能为null
    * */
    private void checkElementNotNull(E data) {
        if(data == null){
            throw new RuntimeException("element must not null");
        }
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable) e1).compareTo(e2);
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node) node).data;
    }
}
