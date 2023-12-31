package leetcode.editor.cn;
//leetcode submit region begin(Prohibit modification and deletion)

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> key = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index= key.get(target-nums[i]);
            if(index != null){
                return new int[]{index,i};
            }else {
                key.put(nums[i],i);
            }
        }
        return null;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
