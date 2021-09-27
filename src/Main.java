import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Main test = new Main();
        int[] nums = {4, 2};
        test.printArray(nums);
        System.out.println(test.largestRectangleArea(nums));
    }

    /*
     * 有效的括号（easy）
     * 检查括号顺序（栈）
     * */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.empty()) {
                    return false;
                }
                char Left = stack.pop();
                switch (c) {
                    case ']':
                        if (Left != '[') {
                            return false;
                        }
                        break;
                    case '}':
                        if (Left != '{') {
                            return false;
                        }
                        break;
                    case ')':
                        if (Left != '(') {
                            return false;
                        }
                        break;
                }
            }
        }
        return stack.empty();
    }

    /*
     * 寻找柱状图中最大矩形(Hard)
     * 在某个区间存在最小的Height使得区间面积最大，公式为（l-r+1）* H
     * 寻找左右边界
     * */
    //递归方法：穷举法
    public int largestRectangleAreaByRecursion(int[] heights) {
        int maxSize = 0;       //最大面积
        int length = heights.length;    //目前矩形长，初始值为数组长度
        int right = length - 1, left = 0;     //左右指针
        Main main = new Main();
        maxSize = main.largestRectanglePart(heights, left, right);
        return maxSize;
    }
    public int largestRectanglePart(int[] heights, int left, int right){
        System.out.println("new " + left + " " + right);
        if(left == right){
            System.out.println("E" + left);
            return heights[left];
        }
        int length = right - left + 1, minHeight = heights[left], minIndex = left;
        for(int i = left; i < right + 1; i++){                 //找到最小H
            if(heights[i] < minHeight){
                minHeight = heights[i];
                minIndex = i;
            }
        }
        int size = length * minHeight;
        System.out.println(minIndex);
        int leftSize = 0, rightSize = 0;
        if(minIndex > left){
            leftSize = largestRectanglePart(heights, left, minIndex - 1);
        }
        if(right > minIndex){
            rightSize = largestRectanglePart(heights, minIndex + 1, right);
        }
        int maxLR = Math.max(leftSize,rightSize);
        return Math.max(maxLR,size);
    }
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int size = 0, len = heights.length;
        int[] leftSide = new int[len];
        int[] rightSide = new int[len];
        Arrays.fill(rightSide,len);
        for (int i = 0; i < len; i++) {
            int number = heights[i];
            while ((!stack.isEmpty()) && heights[stack.peek()] >= number) {
                rightSide[stack.peek()] = i;
                stack.pop();
            }
            leftSide[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        for (int i = 0; i < len; ++i) {
            size = Math.max(size, (rightSide[i] - leftSide[i] - 1) * heights[i]);
        }
        return size;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public int getLength(ListNode head) {
            int length = 0;
            while (head.next != null) {
                length++;
            }
            return length;
        }

        public void printFromHead(ListNode head) {
            ListNode pointHead = head;
            while (head.next != null) {
                System.out.println(pointHead.val);
                pointHead = pointHead.next;
            }
        }
    }

    /*
     * 合并链表（easy）
     * */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode newHead;
        ListNode head1 = l1, head2 = l2;
        if (l1.val > l2.val) {
            newHead = l2;
            head2 = head2.next;
        } else {
            newHead = l1;
            head1 = head1.next;
        }
        ListNode tail = newHead;
        while (head1 != null && head2 != null) {
            if (head1.val >= head2.val) {
                tail.next = head2;
                head2 = head2.next;
            } else {
                tail.next = head1;
                head1 = head1.next;
            }
            tail = tail.next;
        }
        if (head1 == null && head2 != null) {
            tail.next = head2;
        } else if (head2 == null && head1 != null) {
            tail.next = head1;
        }
        return newHead;
    }

    /*
     * 反转链表（easy）
     * 可以用迭代/递归两种方法
     * */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        // 递归
        // ListNode node = reverseList(head.next);
        // if(node == null){
        //     //return new head;
        //     return head;
        // }
        // else{
        //     ListNode tail = node;
        //     while(tail.next != null){
        //         tail = tail.next;
        //     }
        //     tail.next = head;
        //     head.next = null;
        //     return node;
        // }
        //迭代
        ListNode node, prev = head.next;
        head.next = null;
        while (prev != null) {
            node = prev.next;
            prev.next = head;
            head = prev;
            prev = node;
        }
        return head;
    }

    /*
     * 相交链表（easy）
     * 需要考虑最后一个点相交和第一个点相交
     * */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode head1 = headA, head2 = headB;
        int length1 = 0, length2 = 0;
        while (head1 != null) {
            length1++;
            head1 = head1.next;
        }
        while (head2 != null) {
            length2++;
            head2 = head2.next;
        }
        ListNode node1 = headA, node2 = headB;
        if (length1 > length2) {
            for (int i = 0; i < length1 - length2; i++) {
                node1 = node1.next;
            }
        } else {
            for (int i = 0; i < length2 - length1; i++) {
                node2 = node2.next;
            }
        }
        while (node1 != null && node2 != null) {
            if (node1.val == node2.val) {
                node1.val++;
                if (node2.val == node1.val) {
                    node1.val--;
                    return node1;
                }
                node1.val--;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        return null;
    }

    /*链表排序（mid）
     *时间复杂度O(n * log(n))，空间O(1)，自底向上合并（二分，先分成最小的size，然后合并，size <<= 1），合并用merge方法
     * */
    @NotNull
    public ListNode sortList(ListNode head) {
        if (head == null) {
            return head;
        }
        int length = 1;
        ListNode node = head;
        while (node.next != null) {
            node = node.next;
            length++;
        }
        ListNode newHead = new ListNode(0, head);
        for (int subLength = 1; subLength < length; subLength <<= 1) {
            ListNode prev = newHead, curr = newHead.next;
            while (curr != null) {
                ListNode head1 = curr;
                for (int i = 1; i < subLength && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode head2 = curr.next;
                curr.next = null;
                curr = head2;
                for (int i = 1; i < subLength && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode next = null;
                if (curr != null) {
                    next = curr.next;
                    curr.next = null;
                }
                prev.next = merge(head1, head2);
                while (prev.next != null) {
                    prev = prev.next;
                }
                curr = next;
            }
        }
        return newHead.next;
    }

    //合并链表，比较后移
    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }


    //删除倒数N个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pointHead = new ListNode(0, head);
        ListNode fast = pointHead;
        ListNode slow = pointHead;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return pointHead.next;
    }

    //移动0
    public void moveZeroes(int[] nums) {
        int left = 0, right = 0;
        while (right < nums.length) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
    }

    /*
     * 空间O(1)的方法为：反转链表后半部分，然后依次比较；缺点：并发情况下，链表结构会被锁定。
     * */
    //回文链表判断
    public boolean isPalindrome(ListNode head) {
        ListNode pointHead = head;
        int length = pointHead.getLength(head);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < length / 2; i++) {
            stack.push(pointHead.val);
        }
        if (length % 2 != 0) {
            pointHead = pointHead.next;
        }
        while (pointHead.next != null) {
            int cmp = stack.pop();
            if (cmp != pointHead.val) {
                return false;
            }
            pointHead = pointHead.next;
        }
        return true;
    }

    //循环链表判断
    /*
     * 快慢指针
     * 快指针走了慢指针的2倍，head-循环点距离x，循环点-相遇点距离y，相遇点-循环点距离z
     * 相遇时慢指针走了x+y，快指针走了x+（n+1）y+nz => x = （n - 1）b + nc = (n-1)(b+c) +c
     * 注意快指针垮两步时的空值问题
     * */
    public ListNode detectCycle(ListNode head) {
        ListNode pointHead1 = head, pointHead2 = head;
        if (head == null || head.next == null) return null;
        while (pointHead2 != null) {
            pointHead1 = pointHead1.next;
            if (pointHead2.next != null) {
                pointHead2 = pointHead2.next.next;
            } else {
                return null;
            }
            if (pointHead1 == pointHead2) {
                ListNode ntr = head;
                while (ntr != pointHead2) {
                    pointHead2 = pointHead2.next;
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
        int right = n - 1, left = 0;
        for (int i = 0; i < right; i++) {
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[left];
                nums[left] = temp;
                left++;
            }
            while (i <= right && nums[i] == 2) {
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
        if (n < 3) return lists;
        for (int i = 0; i < n - 2; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1;
            int right = n - 1;
            int result;
            while (left < right) {
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    left++;
                    continue;
                }
                if (right < n - 1 && nums[right] == nums[right + 1]) {
                    right--;
                    continue;
                }
                result = nums[i] + nums[left] + nums[right];
                if (result == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    lists.add(list);
                    left++;
                    right--;
                } else if (result < 0) {
                    left++;
                } else {
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
        while (k >= 0) {
            //判断单个数组为空
            System.out.println("k=" + k);
            if (point1 >= m) {
                //在数组2找到第k小的数
                System.out.println("1out");
                return (m + n) % 2 == 0 ? (double) (nums2[k + point2] + nums2[k + point2 - 1]) / 2 : nums2[k + point2 - 1];
            } else if (point2 >= n) {
                //在数组1找到第k小的数
                System.out.println("2out");
                return (m + n) % 2 == 0 ? (double) (nums1[k + point1 - 1] + nums1[k + point1]) / 2 : nums1[k + point1 - 1];
            }

            if (k == 1) {
                if ((m + n) % 2 == 0) {
                    System.out.println("3out");
                    if (point1 < m - 1) {
                        if (nums1[point1 + 1] < nums2[point2]) {
                            return (double) (nums1[point1] + nums1[point1 + 1]) / 2;
                        }
                    }
                    if (point2 < n - 1) {
                        if (nums2[point2 + 1] < nums1[point1]) {
                            return (double) (nums2[point2] + nums2[point2 + 1]) / 2;
                        }
                    }
                    return (double) (nums1[point1] + nums2[point2]) / 2;
                } else {
                    System.out.println("4out");
                    return Math.min(nums1[point1], nums2[point2]);
                }
            }

            int cmp1 = nums1[m - 1], cmp2 = nums2[n - 1];
            if (m - point1 > k / 2 - 1) {
                cmp1 = nums1[point1 + k / 2 - 1];
            }
            if (n - point2 > k / 2 - 1) {
                cmp2 = nums2[point2 + k / 2 - 1];
            }
            //去除不可能的数字
            if (cmp1 <= cmp2) {
                if (m - point1 < k / 2) {
                    int tmp = m - point1;
                    point1 += k / 2;
                    k -= tmp;
                } else {
                    point1 += k / 2;
                    k -= k / 2;
                }
            } else {
                if (n < point2 + k / 2) {
                    int tmp = n - point2;
                    point2 += k / 2;
                    k -= tmp;
                } else {
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
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{0, 0};
    }

    public void printArray(int[] nums) {
        for (int i : nums) {
            System.out.print(i + " ");
        }
        System.out.print("\n");
    }
}
