package com.zhangtl.algorithmpdf.one;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/*
* 基本小算法
* */
public class BaseAlgori {


    /*
    * 双栈算术表达式求值算法
    * */
    public int doubleStackAlgori(String str){
        int  result  =  0;
        Character left  =  '(';
        Character right  =  ')';
        Set<Character> cals = new HashSet<>();
        cals.add('+');
        cals.add('-');
        cals.add('*');
        cals.add('/');
        Stack<Character> number = new Stack<>();
        Stack<Character> calcu = new Stack<>();
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (left.equals(aChar)){
                continue;
            }else if(cals.contains(aChar)){
                calcu.push(aChar);
            }else if(right.equals(aChar)){
//                if()
            }else {
                number.push(aChar);
            }
        }

        return 1;
    }

}
