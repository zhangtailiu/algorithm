package com.zhangtl.algorithmpdf.onehundred.service;

import com.zhangtl.algorithmpdf.onehundred.entity.ListNode;

import java.util.HashMap;
import java.util.Map;

public class Test01 {

    public ListNode toNumberAdd(ListNode left, ListNode rigth){
        ListNode resultNode = null;
        ListNode next = new ListNode();
        ListNode last =  new ListNode();
        while (left != null || rigth !=null){
            last.next = next;
            int local = next.val + (left == null ? 0 : left.val) + (rigth == null ? 0 : rigth.val);
            next.val = local%10;
            last = next;
            if(resultNode == null){
                resultNode = last;
            }
            next = new ListNode(local/10);
            left = left == null? null:left.next;
            rigth = rigth == null?null:rigth.next;
        }
        if(next.val > 0){
            last.next = next;
        }
        return resultNode;
    }
    /*
     * 代码问题 1.这里由于想使用 next value 来存储进位值所以导致last和next的混乱,其实可以用变量来存
     *         2.这里可以用预先指针来屏蔽掉每次的  resultNode == null 判断
     * 优化点 : 去除了next, 进位判断使用> 不用 /
     *
     * */
    public ListNode toNumberAddEdit(ListNode left, ListNode rigth){
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int jin = 0;
        while (left != null || rigth !=null){
            int local = jin + (left == null ? 0 : left.val) + (rigth == null ? 0 : rigth.val);
            if(local > 9){
                local = local%10;
                jin = 1;
            }else {
                jin = 0;
            }
            cur.next = new ListNode(local);
            cur = cur.next;
            left = left == null? null:left.next;
            rigth = rigth == null?null:rigth.next;
        }
        if(jin > 0){
            cur.next = new ListNode(1);
        }
        return pre.next;
    }
/*==============================*/

    /*
    无重复最长子串
* 思路：通过map key-字符 value-索引 来记录不重复字符
* 当出现重复字符时计算差值 并更新字符索引
* 此方法完全错误
* 这里计算的其实是两个重复字符间的最大距离  但是不能找到最大子串
  会包含以下问题(无重复字段情形，重复字段间长度小于无重复字段长度)
* */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            Integer last = map.put(chars[i], i);
            if(last != null){
                int minums =  i - last ;
                max = minums >max?minums:max;
            }
        }
        return max;
    }
   /*滑动窗口方法
   * 这里的滑动窗口可以看作将不重复的字符串的start和end索引看成一个窗口区间随着这个窗口区间的往后移动
   * 记录每次重复时的间距并最后比较出最大值
   * 这里的关键点在出现重复字符时的处理
   *
   * */
   public int lengthOfLongestSubstringhuadong(String s) {
       Map<Character, Integer> map = new HashMap<>();
       int length = s.length();
       int max = 0;
       for (int end = 0,start = 0; end < length ; end++) {
           Integer last = map.put(s.charAt(end), end+1);
           if(last != null){
               start = Math.max(start,last);
           }
           max = Math.max(max,end-start+1);
       }
       if(max == 0){
           max = length;
       }
       return max;
   }

   /*========================*/
    /*
    * 获取两个数组的中位数
    * 想法：这里首先的想法是 使用两个指标从两个数组的0开始逐渐向后移动 一直移动到中位数 或者中位数的左右 最后求出结果 复杂度On
    * 这里是判断小的数移动指针
    *
    * */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int aLength = nums1.length;
        int bLength = nums2.length;
        int half = (aLength + bLength) / 2;
        boolean oushu = (aLength + bLength) % 2 == 0;
        int aStart = 0;
        int bStart = 0;
        int last = 0;
        int now = 0;
        while (aStart+bStart<=half){
            last =now;
            if( aStart>=aLength){
                now =nums2[bStart];
                bStart++;
            }else if(bStart >=bLength){
                now =nums1[aStart];
                aStart++;
            }else {
                if(nums1[aStart] >nums2[bStart]){
                    now = nums2[bStart];
                    bStart++;
                }else {
                    now = nums1[aStart];
                    aStart++;
                }
            }
        }
        return oushu?(last+now)/2.0:now;
    }

    /*
    * 经提示看到了二分法
    * 这里的代码有些复杂这里主要对比上方进行了如下的优化
    * 1.中位数判断 使用 left 和right 的方式  如果m+n为奇数那么 +1和+2  /2 的值都是一样的  如果为偶数 那么left就是中位数的左边rigth就是中位数的右边
    * 这种就屏蔽了奇偶数
    * 2。这里因为是两个数组都拍了序，所以这里使用的是二分法查找，这里的二分法的逻辑可以看作如下
    * 如果要找到两个数组中的排序第k的数据那么就要排除掉前k-1个数据那么剩下的那个小的就是第k个数据
    * 这里是通过先排除 k/2 个数据
    * 这里的排除逻辑是
    *  1 。。。。。。a。。。。。。。。
    *  2 。。。b。。。。
    *  假设 a<b 那么可以断定第k个数据肯定不在 a的左边，因为 a的左边+b的左边的数量为 k-2 或者k-1 那么即使b的左边都比a小第k位数也应该是 a这个位置，其他情况应该在a的右边或者2这个数组里所以可以排除
    * k/2个数据 同时移动1的start的位置到a的后面一个
    * a>b就反过来
    * a=b 相当于上面任意一个情况
    * 然后再找 第 k-k/2的数据 这里重复上面的流程
    * 这里递归调用的k会不断减小直到k
    * 这里的边界情况有
    * 1.两个数组的起点不断向后推进有可能超过数组长度，此时返回的应该是另外一个数组再移动此时k的数量 -1是因为数组的值从0开始
    * 2。k的值不断减少最后会等于1因为 如果k是奇数那么-k/2一直会是奇数然后到321偶数会一直是偶数到21
    *
    * */

    public double findMedianSortedArraysEdit(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = (m + n + 1) / 2;
        int right = (m + n + 2) / 2;
        return (findKth(nums1, 0, nums2, 0, left) + findKth(nums1, 0, nums2, 0, right)) / 2.0;
    }
    //i: nums1的起始位置 j: nums2的起始位置
    public int findKth(int[] nums1, int i, int[] nums2, int j, int k){
        if( i >= nums1.length) return nums2[j + k - 1];//nums1为空数组
        if( j >= nums2.length) return nums1[i + k - 1];//nums2为空数组
        if(k == 1){
            return Math.min(nums1[i], nums2[j]);
        }
        int midVal1 = (i + k / 2 - 1 < nums1.length) ? nums1[i + k / 2 - 1] : Integer.MAX_VALUE;
        int midVal2 = (j + k / 2 - 1 < nums2.length) ? nums2[j + k / 2 - 1] : Integer.MAX_VALUE;
        if(midVal1 < midVal2){
            return findKth(nums1, i + k / 2, nums2, j , k - k / 2);
        }else{
            return findKth(nums1, i, nums2, j + k / 2 , k - k / 2);
        }
    }

}
