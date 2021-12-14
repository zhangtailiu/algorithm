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

}
