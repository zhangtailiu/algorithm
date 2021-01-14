package com.zhangtl.stack;

import java.util.Stack;

public class StackTest {

   public static boolean isValidate(String arr){
       Stack<Character> stack = new Stack<>();
        if(arr == null){
            return false;
        }
       char[] chars = arr.toCharArray();
       for (int i = 0; i <chars.length ; i++) {
           char aChar = chars[i];
           if('{'==(aChar)||'['==aChar||'('==aChar){
               stack.push(aChar);
           }else {
               if(stack.empty()){
                   return false;
               }
               Character peek = stack.pop();
               if(!(('{'== peek&& aChar == '}')
                       || ('('==peek && aChar == ')')
                       || ('['==peek && aChar == ']'))){
                   return false;
               }
           }
       }
        return stack.empty();
   }

    public static void main(String[] args) {
        System.out.println(isValidate("{[()]}"));;
        System.out.println(isValidate("{()]}"));
        System.out.println(isValidate("{[[]()]}"));
        System.out.println(isValidate("{()[][()]}"));

    }

}
