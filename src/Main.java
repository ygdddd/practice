import java.util.*;
public class Main {
    public static void main(String[] args){
        Main test = new Main();
        int[] nums = {2,0,2,1,1,0};
        int target = 6;
        int[] nums1 = new int[]{1}, nums2 = new int[]{};//1,3,4,5,6,7,9,10

//        int[] result = test.twoSum(nums,target);
//        System.out.println("res " + test.sortColors(nums));
        test.printArray(test.sortColors(nums));
    }


    //循环链表判断
    class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
    }

    /*
    * 快慢指针
    * 快指针走了慢指针的2倍，head-循环点距离x，循环点-相遇点距离y，相遇点-循环点距离z，相遇时慢指针走了x+y，快指针走了x+（n+1）y+nz => x = （n - 1）b + nc = (n-1)(b+c) +c
    * 注意快指针垮两步时的空值问题
    * */
    public ListNode detectCycle(ListNode head) {
        ListNode phead1 = head, phead2 = head;
        if(head == null || head.next == null) return null;
        while(phead2 != null){
            phead1 = phead1.next;
            if(phead2.next!=null){
                phead2 = phead2.next.next;
            }
            else{
                return null;
            }
            if(phead1 == phead2){
                ListNode ntr = head;
                while(ntr != phead2){
                    phead2 = phead2.next;
                    ntr = ntr.next;
                }
                return ntr;
            }
        }
        return null;
    }

    /*
    * 滑动窗口法
    * */
    public String minWindow(String s, String t) {
        //转换为int数组
        char[] chars = s.toCharArray(), chart = t.toCharArray();
        int n = chars.length, m = chart.length;
        //创建hash数组作为记录
        int[] hash = new int[128];
        for (char ch : chart) hash[ch]--;
        //结果
        String res = "";
        //j = left，i = right，cnt为有效子串长度
        for (int i = 0, j = 0, cnt = 0; i < n; i++) {
            hash[chars[i]]++;
            //如果是有效字符（在t中且非冗余），cnt++
            if (hash[chars[i]] <= 0) cnt++;
            //如果子串长度等于t，且left所在字符数量超出t中数量（冗余字符），则left所在元素--
            while (cnt == m && hash[chars[j]] > 0) hash[chars[j++]]--;
            //结果串为空或者长度大于当前子串事更新
            if (cnt == m)
                if (res.equals("") || res.length() > i - j + 1)
                    res = s.substring(j, i + 1);
        }
        return res;
    }

    /*
    * 由于for循环从左向右遍历，所以遇到0只需要判断一次，而遇到2需要避免交换之后的还是2
    * 若使用while（nums[i] == 0），由于遇到 left==i 时，i不变而left++，然后下一次i++，left和i又重合，会导致一直重合而溢出
    * */
    public int[] sortColors(int[] nums) {
        int n = nums.length;
        int right = n - 1,left = 0;
        for(int i = 0; i < right; i++){
            if(nums[i] == 0){
                int temp = nums[i];
                nums[i] = nums[left];
                nums[left] = temp;
                left++;
            }
            while(i <= right && nums[i] == 2){
                int temp = nums[i];
                nums[i] = nums[right];
                nums[right] = temp;
                right--;
            }
        }
        return nums;
    }

    //三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> lists = new ArrayList<>();
        int n = nums.length;
        if(n < 3) return lists;
        for(int i = 0; i < n - 2; i++){
            if(nums[i] > 0) break;
            if(i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1;
            int right = n - 1;
            int result;
            while(left < right){
                if(left > i + 1 && left < right && nums[left] == nums[left - 1]){
                    left++;
                    continue;
                }
                if(right < n - 1 && right > left && nums[right] == nums[right + 1]){
                    right--;
                    continue;
                }
                result = nums[i] + nums[left] + nums[right];
                if(result == 0){
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    lists.add(list);
                    left++;
                    right--;
                }
                else if(result < 0){
                    left++;
                }
                else{
                    right--;
                }
            }

        }
        return lists;
    }

    //两个数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int k = (m + n) % 2 == 0 ? (m + n) / 2 : (m + n) / 2 + 1;//偶数选择一半，奇数选择一半+1
        int point1 = 0, point2 = 0;
        while(k >= 0){
            //判断单个数组为空
            System.out.println("k=" + k);
            if(point1 >= m){
                //在数组2找到第k小的数
                System.out.println("1out");
                return (m + n) % 2 == 0 ? (double)(nums2[k + point2] + nums2[k + point2 - 1]) / 2 : nums2[k + point2 - 1];
            }
            else if(point2 >= n){
                //在数组1找到第k小的数
                System.out.println("2out");
                return (m + n) % 2 == 0 ? (double)(nums1[k + point1 - 1] + nums1[k + point1]) / 2 : nums1[k + point1 - 1];
            }

            if(k == 1){
                if((m + n) % 2 == 0){
                    System.out.println("3out");
                    if(point1 < m - 1){
                        if(nums1[point1 + 1] < nums2[point2]){
                            return (double)(nums1[point1] + nums1[point1 + 1]) / 2;
                        }
                    }
                    if(point2 < n - 1){
                        if(nums2[point2 + 1] < nums1[point1]){
                            return (double)(nums2[point2] + nums2[point2 + 1]) / 2;
                        }
                    }
                    return (double)(nums1[point1] + nums2[point2]) / 2;
                }
                else{
                    System.out.println("4out");
                    return nums1[point1]<nums2[point2]?nums1[point1]:nums2[point2];
                }
            }

            int cmp1 = nums1[m - 1], cmp2 = nums2[n - 1];
            if(m - point1 > k / 2 - 1){
                cmp1 = nums1[point1 + k / 2 - 1];
            }
            if(n - point2 > k / 2 - 1) {
                cmp2 = nums2[point2 + k / 2 - 1];
            }
            //去除不可能的数字
            if(cmp1 <= cmp2){
                if(m - point1 < k / 2){
                    int tmp = m - point1;
                    point1 += k / 2;
                    k -= tmp;
                }
                else{
                    point1 += k / 2;
                    k -= k / 2;
                }
            }
            else{
                if(n < point2 + k / 2){
                    int tmp = n - point2;
                    point2 += k / 2;
                    k -= tmp;
                }
                else{
                    point2 += k / 2;
                    k -= k / 2;
                }
            }
            System.out.println(point1 + " point " + point2);
        }
        return 0;
    }

    //两数之和
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap();
        int n = nums.length;
        for(int i = 0; i < n; i++){
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
        map.put(nums[i],i);
        }
        return new int[]{0,0};
    }

    public void printArray(int[] nums){
        for(int i = 0; i<nums.length;i++){
            System.out.print(nums[i] + " ");
        }
    }
}
