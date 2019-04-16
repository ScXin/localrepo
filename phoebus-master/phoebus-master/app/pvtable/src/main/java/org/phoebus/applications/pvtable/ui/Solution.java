package org.phoebus.applications.pvtable.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jdk.nashorn.api.tree.ThrowTree;
import org.epics.pvdata.factory.BaseUnion;
import org.epics.vtype.Array;
import org.phoebus.applications.pvtable.model.PVTableModel;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ScXin
 * @date 3/22/2019 10:19 AM
 */
//public class Solution {
//
//    public static void main(String[] args) {
//        int[] arr = {3, 4, 5, 6, 1, 2, 8, 9, 2};
//        quickSort(arr, 0, arr.length - 1);
//    }
//
//    public static void quickSort(int[] arr, int left, int right) {
//        if (left >= right) {
//            return;
//        }
//        int k = partition(arr, left, right);
//        quickSort(arr, left, k);
//        quickSort(arr, k + 1, right);
//    }
//
//    public static int partition(int[] arr, int left, int right) {
//        int temp = arr[left];
//        while (right > left) {
//            while (arr[right] > temp&&left<right) {
//                right--;
//            }
//            arr[left] = arr[right];
//            left++;
//            while (arr[left] < temp&&left<right) {
//                left++;
//            }
//            arr[right] = arr[left];
//            right--;
//        }
//        arr[left] = temp;
//        return left;
//    }
//
//}
//
//
//
//import java.util.List;
//
//import java.util.ArrayList;
//
//public class Solution {
//    private static ArrayList<ArrayList<Integer>> result = new ArrayList<>();
////    private  static ArrayList<Integer> list = new ArrayList<>();
//    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
//
//        if (sum == 1) {
//            return result;
//        }
//        for (int i = 1; i <= (sum / 2 )+ 1; i++) {
//            ArrayList<Integer>list=new ArrayList<>();
//            helper(sum,list, i);
//        }
//
//        return result;
//    }
//
//    public static void helper(int sum,ArrayList<Integer>list ,int i) {
//        if (sum == 0) {
//
//            result.add(list);
//
//            return;
//        }
//        if (sum < 0) {
//            return;
//        }
//        list.add(i);
//        helper(sum - i, list,i + 1);
//    }
//
//    public static void main(String[] args) {
//
//        List<ArrayList<Integer>> result = FindContinuousSequence(99);
//
//        for (int i = 0; i < result.size(); i++) {
//            List<Integer> res = result.get(i);
//            for (int j = 0; j < res.size(); j++) {
//                System.out.println(res.get(j));
//
//            }
//            System.out.println("------------------------------------------");
//        }
//    }
//}

//public class Solution {
//    public static int InversePairs(int [] array) {
//        int result=0;
//        for(int i=0;i<array.length-1;i++)
//        {
//            for(int j=i+1;j<array.length;j++)
//            {
//                if(array[i]>array[j])
//                {
//                    result++;
//                }
//            }
//        }
//        return result;
//    }
//
//    public static void main(String[] args) {
//
//        System.out.println(  InversePairs(new int[]{6,5,4,3,2,1}));
//    }
//}

//
//public class Solution{
//    public static boolean adjustIsTwoSqrt(int num)
//    {
//        int res=Integer.bitCount(num);
//        {
//            if(res==1)
//            {
//                return true;
//            }
//            else
//            {
//                return false;
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println(adjustIsTwoSqrt(512));
//    }
//}
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.DoubleUnaryOperator;

//public class Solution {
//
//     private static final String lock="lock";
//    public static void main(String[] args)  throws Exception{
//
//        Thread threadA = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                 synchronized (lock)
//                 {
//                     try{   System.out.println("wait...");
//                         lock.wait();
//
//                         System.out.println("A"+"执行了");
//
//                     }
//                     catch(Exception ex)
//                     {
//                         ex.printStackTrace();
//                     }
//                    finally {
//                         lock.notify();
//                     }
//
//                 }
//            }
//
//        });
//        Thread threadB = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (lock)
//                {
//                    System.out.println("B"+"执行了");
//                    lock.notify();
//                }
//            }
//        });
//        threadA.start();
//        threadB.start();
//        threadB.join();
//        System.out.println("main线程执行");
//    }
//
//

//}
//
///**
// * 快排
// */
//public class Solution {
//    public static void quickSort(int[] arr, int left, int right) {
//        if (left >= right || arr.length == 1) {
//            return;
//        }
//        int k = partition(arr, left, right);
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i]);
//        }
//        System.out.println("-------------------------------------");
//        quickSort(arr, left, k);
//        quickSort(arr, k + 1, right);
//    }
//
//    private static int partition(int[] arr, int left, int right) {
//        int temp = arr[left];
//        while (right > left) {
//            // 先判断基准数和后面的数依次比较
//            while (temp <= arr[right] && left < right) {
//                --right;
//            }
//            // 当基准数大于了 arr[right]，则填坑
//            if (left < right) {
//                arr[left] = arr[right];
//                ++left;
//            }
//            // 现在是 arr[right] 需要填坑了
//            while (temp >= arr[left] && left < right) {
//                ++left;
//            }
//            if (left < right) {
//                arr[right] = arr[left];
//                --right;
//            }
//        }
//        arr[left] = temp;
//        return left;
//    }
//
//    /***
//     * 选择排序
//     * @param arr
//     */
//    public static void selectSort(int[] arr) {
//        int len = arr.length;
//        if (len == 0) {
//            return;
//        }
//
//        for (int i = 0; i < len - 1; i++) {
//            int min = i;
//            for (int j = i + 1; j < len; j++) {
//                if (arr[j] < arr[min]) {
//                    min = j;
//                }
//            }
//            swap(arr, min, i);
//        }
//    }
//
//    public static void swap(int[] arr, int i, int j) {
//        int temp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = temp;
//    }
//
//    /**
//     * 冒泡排序
//     *
//     * @param arr
//     */
//    public static void bubbleSort(int[] arr) {
//        int len = arr.length;
//        if (len <= 1) {
//            return;
//        }
//        for (int i = 0; i < len; i++) {
//            for (int j = 0; j < len - i - 1; j++) {
//                if (arr[j] > arr[j + 1]) {
//                    swap(arr, j, j + 1);
//                }
//            }
//        }
//    }

/**
 * 插入排序
 *
 * @param arr //
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 * //
 * <p>
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 * <p>
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * <p>
 * Given the following 5x5 matrix:
 * <p>
 * Pacific ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * Atlantic
 * <p>
 * Return:
 * <p>
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 * <p>
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 * <p>
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * <p>
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * <p>
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * <p>
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * <p>
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * <p>
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * <p>
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * <p>
 * 说明：
 * <p>
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 * <p>
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 * 我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 * <p>
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 * <p>
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 * 因此无法执行有效的转换。
 * 示例 5:
 * <p>
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 * 因此返回 INT_MIN (−231) 。
 * <p>
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 * <p>
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 * <p>
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 * <p>
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 * <p>
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 * //
 */
//    public static void insertSort(int[] arr) {
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = i; j > 0; j--) {
//                if (arr[j] < arr[j - 1]) {
//                    swap(arr, j, j - 1);
//                }
//            }
//        }
//    }
//
//    /**
//     * 堆排序
//     *
//     * @param arr
//     */
//    public static void headSort(int[] arr) {
//        int len = arr.length;
//        if (len <= 1) {
//            return;
//        }
//        for (int i = len / 2 - 1; i >= 0; i--) {
//            AdjustHeap(arr, i, len);
//        }
//        for (int i = len - 1; i > 0; i--) {
//            swap(arr, 0, i);
//            AdjustHeap(arr, 0, i - 1);
//        }
//    }
//
//    public static void AdjustHeap(int[] arr, int s, int l) {
//        int temp = arr[s];
//
//        int i, j;
//        i = s;
//
//        for (j = 2 * i + 1; j < l; j = 2 * j + 1) {
//            if (j < l - 1 && arr[j] < arr[j + 1]) {
//                j++;
//            }
//            if (j < l && arr[j] < arr[i]) {
//                break;
//            }
//
//            swap(arr, i, j);
//            i = j;
//        }
//        arr[i] = temp;
//    }
//
//
//    public void headSort(int[] arr, int l) {
//
//        //build heap
//        for (int i = arr.length / 2 - 1; i >= 0; i--) {
//            //从第一个非叶子结点从下至上，从右至左调整结构
//            AdjustHeap(arr, i, arr.length);
//        }
//        for (int i = l; i > 0; i--) {
//            int temp = arr[l];
//            arr[l] = arr[0];
//            arr[0] = temp;
//            AdjustHeap(arr, 0, i - 1);
//        }
//    }
//
//    /**
//     * 希尔排序
//     *
//     * @param args
//     */
//
//    public static void shellSort(int[] arr) {
//        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
//            for (int i = 0; i < arr.length; i++) {
//                for (int j = i; j - gap >= 0; j -= gap) {
//                    if (arr[j] < arr[j - gap]) {
//                        swap(arr, j - gap, j);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 归并排序
//     *
//     * @param args
//     */
//
//    public void merge(int[] arr1, int[] arr2) {
//
//    }
//
//
//    /**
//     * 红黑树
//     * @param args
//     */
//
//
//    /**
//     * KMP算法
//     *
//     * @param args
//     */
//
////
////    public static int kmp(String s, String p) {
////
////        int[] next = new int[p.length()];
////        for(int k=0;k<p.length();k++)
////        {
////            next[k]=-1;
////        }
////        getNext(p, next);
////        int i = 0;
////        int j = 0;
////        while (i < s.length()&& j < p.length()) {
////
////            if (s.charAt(i) == p.charAt(j)) {
////                i++;
////                j++;
////            }
////            else
////            {
////                if(next[j]==-1)
////                {
////                    i++;
////                    j=0;
////                }
////                else
////                {
////                    j=next[j];
////                }
////            }
////            if(j==p.length())
////            {
////                return i-j;
////            }
////        }
////        return -1;
////    }
//    static int kmp(String s, String pattern) {
//        int i = 0;
//        int j = 0;
//        int slen = s.length();
//        int plen = pattern.length();
//
//        int[] next = new int[plen];
//
//        getNext(pattern, next);
//
//        while (i < slen && j < plen) {
//
//            if (s.charAt(i) == pattern.charAt(j)) {
//                i++;
//                j++;
//            } else {
//                if (next[j] == -1) {
//                    i++;
//                    j = 0;
//                } else {
//                    j = next[j];
//                }
//
//            }
//
//            if (j == plen) {
//                return i - j;
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * 这一步是最关键的，确定在出现不匹配字符时，模式字符串指针移动多少位！！！
//     *
//     * @param pattern
//     * @param next
//     *//
//    public static void getNext(String pattern, int[] next) {
//        int k = -1;
//        int j = 0;
//        next[0] = -1;
//        while (j < pattern.length() - 1) {
//            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
//                j++;
//                k++;
//            } else {
//                k = next[k];
//            }
//        }
//    }
//    static void getNext(String pattern, int next[]) {
//        int j = 0;
//        int k = -1;
//        int len = pattern.length();
//        next[0] = -1;
//
//        while (j < len - 1) {
//            if (k == -1 || pattern.charAt(k) == pattern.charAt(j)) {
//
//                j++;
//                k++;
//                next[j] = k;
//            } else {
//
//                // 比较到第K个字符，说明p[0——k-1]字符串和p[j-k——j-1]字符串相等，而next[k]表示
//                // p[0——k-1]的前缀和后缀的最长共有长度，所接下来可以直接比较p[next[k]]和p[j]
//                k = next[k];
//            }
//        }
//
//    }
//
//    public static int maxProduct(String[] words) {
//        int max = 0;
//        for (int i = 0; i < words.length - 1; i++) {
//            for (int j = i + 1; j < words.length; j++) {
//                if (isContain(words[i], words[j])) {
//                    int temp = words[i].length() * words[j].length();
//                    max = Math.max(max, temp);
//                }
//            }
//        }
//        return max;
//    }
//
//    public static boolean isContain(String s1, String s2) {
//        char[] str1 = s1.toCharArray();
//        char[] str2 = s2.toCharArray();
//        Arrays.sort(str1);
//        Arrays.sort(str2);
//        int i = 0;
//        int j = 0;
//        while (i < str1.length && j < str2.length) {
//            if (str1[i] == str2[j]) {
//                return false;
//            } else {
//                if (str1[i] < str2[j]) {
//                    i++;
//                } else {
//                    j++;
//                }
//            }
//        }
//        return true;
//    }
//
//
//    public static void main(String[] args) {
//        int[] arr = {2, 4, 3, 5, 6, 1, 7, 8, 12, 13, 1, 4, 1};
////        quickSort(arr,0,arr.length-1);
////        selectSort(arr);
////        bubbleSort(arr);
////        insertSort(arr);
////        headSort(arr);
////        shellSort(arr)
//        String[] array = {"abcw", "baz", "foo", "bar", "xtfn", "abcdef"};
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);
//        }
//        System.out.println(maxProduct(array));
////        System.out.println(kmp("abcdeabcdfabcdeg","ab"));
////
////        for (int i = 0; i < arr.length; i++) {
////            System.out.println(arr[i]);
////        }
//
//    }
//}
//
//
//        public int[] multiply(int[] A) {
//            int[] left=new int[A.length];
//            int[] right=new int[A.length];
//            for(int i=0;i<A.length;i++)
//            {
//                int k=i;
//                left[i]=1;
//                while(k>=0)
//                {
//                    left[i]*=A[k];
//                    k--;
//                }
//            }
//            for(int j=A.length-1;j>=0;j--)
//            {
//                int k=j;
//                right[j]=1;
//                while(k<A.length)
//                {
//                    right[j]*=A[k];
//                    k++;
//                }
//            }
//            int[] res=new int[A.length];
//            for(int k=0;k<A.length;k++)
//            {
//                if(k==0)
//                {
//                    res[k]=right[1];
//                }
//                if(k==(A.length-1))
//                {
//                    res[k]=left[k-1];
//                }
//                res[k]=left[k-1]*right[k+1];
//            }
//            return res;
//        }
//
//
//    }
//
////

//
//    public class Solution {
//
//       class TreeNode {
//           int val = 0;
//           TreeNode left = null;
//           TreeNode right = null;
//
//           public TreeNode(int val) {
//               this.val = val;
//
//           }
//       }
//        private static  ArrayList<ArrayList<Integer>> res=new ArrayList<>();
//        public ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
//            Stack<TreeNode> nodeStack=new Stack<>();
//            if(pRoot==null)
//            {
//                return res;
//            }
//            nodeStack.push(pRoot);
//
//            while(nodeStack!=null)
//            {
//  ArrayList<Integer> temp=new ArrayList<>();
//                int size=nodeStack.size();
//                while(size-- >0){
//                    TreeNode node=nodeStack.pop();
//                    temp.add(node.val);
//                    if(node.right!=null){
//                        nodeStack.push(node.right);
//                    }
//                    if(node.left!=null)
//                    {
//                        nodeStack.push(node.left);
//                    }
//                }
//                res.add(temp);
//
//            }
//            return res;
//        }
//
//
//            ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
//                ArrayList<ArrayList<Integer>>res=new ArrayList<>();
//                Queue<TreeNode> queue=new PriorityQueue<>();
//                ArrayList<Integer> temp=new ArrayList<>();
//                queue.add(pRoot);
//
//                while(!queue.isEmpty())
//                {
//                    int size=queue.size();
//                    while(size-- >0) {
//                        TreeNode node = queue.poll();
//                        temp.add(node.val);
//                        if(node.left!=null)
//                        {
//                            queue.add(node.left);
//                        }
//                        if(node.right!=null)
//                        {
//                            queue.add(node.right);
//                        }
//                    }
//                    res.add(temp);
//                }
//               return res;
//
//            }
//
//        }
//
//    }


//       }
//        static  ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
//            ArrayList<ArrayList<Integer>>res=new ArrayList<>();
//            Queue<TreeNode> queue=new LinkedList<>();
//
//            queue.add(pRoot);
//
//            while(!queue.isEmpty())
//            {
//                int size=queue.size();
//                while(size-- >0) {
//                    ArrayList<Integer> temp=new ArrayList<>();
//                    TreeNode node = queue.poll();
//                    temp.add(node.val);
//                    if(node.left!=null)
//                    {
//                        queue.add(node.left);
//                    }
//                    if(node.right!=null)
//                    {
//                        queue.add(node.right);
//                    }
//                    System.out.println(node.val);
//                }
//
//                res.add(temp);
//
//            }
//            return res;
//
//        }

//
//        public class Solution {
//        class TreeNode {
//            int val = 0;
//            TreeNode left = null;
//            TreeNode right = null;
//
//            public TreeNode(int val) {
//                this.val = val;
//
//            }
//
//
//            public static ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
//                ArrayList<ArrayList<Integer>> res = new ArrayList<>();
//
//                Stack<TreeNode> nodeStack = new Stack<>();
//                if (pRoot == null) {
//                    return res;
//                }
//                nodeStack.push(pRoot);
//
//                while (nodeStack != null) {
//                    ArrayList<Integer> temp = new ArrayList<>();
//                    int size = nodeStack.size();
//                    while (size-- > 0) {
//                        TreeNode node = nodeStack.pop();
//                        temp.add(node.val);
//                        if (node.right != null) {
//                            nodeStack.push(node.right);
//                        }
//                        if (node.left != null) {
//                            nodeStack.push(node.left);
//                        }
//                    }
//                    res.add(temp);
//
//                }
//                return res;
//            }
//
//            public static void main(String[] args) {
//                TreeNode head = new TreeNode(5);
//                head.left = new TreeNode(4);
//                head.right = new TreeNode(6);
//                head.left.left = new TreeNode(3);
//                head.right.right = new TreeNode(7);
//                ArrayList<ArrayList<Integer>> res = new ArrayList<>();
//                res = Print(head);
//                for (int i = 0; i < res.size(); i++) {
//                    ArrayList<Integer> temp = new ArrayList<>();
//                    for (int j = 0; i < temp.size(); j++) {
//                        System.out.print(temp.get(i));
//                    }
//                    System.out.println("---------------------");
//                }
//            }
//        }
//    }

//  public class Solution
//    {
//        static  class TreeNode {
//            int val = 0;
//            TreeNode left = null;
//            TreeNode right = null;
//
//            public TreeNode(int val) {
//                this.val = val;
//
//            }
//        }
//        private static ArrayList<ArrayList<Integer>> res = new ArrayList<>();
//        public static ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
//
//
//                Stack<TreeNode> nodeStack = new Stack<>();
//                if (pRoot == null) {
//                    return res;
//                }
//                nodeStack.push(pRoot);
//
//                while (!nodeStack.isEmpty() ) {
//                    ArrayList<Integer> temp = new ArrayList<>();
//                    Stack<TreeNode>stack=new Stack<>();
//                    int size = nodeStack.size();
//                    while (size-- > 0) {
//                        TreeNode node = nodeStack.pop();
//                        temp.add(node.val);
//                        System.out.println(node.val);
//                        if (node.right != null) {
//                            stack.push(node.right);
//                        }
//                        if (node.left != null) {
//                            stack.push(node.left);
//                        }
//                    }
//                    System.out.println("temp[0]=="+temp.get(0));
//                     res.add(temp);
//                     nodeStack=stack;
//                }
//                return res;
//            }
//
//        public static void main(String[] args) {
//                TreeNode head = new TreeNode(5);
//                head.left = new TreeNode(4);
//                head.right = new TreeNode(6);
//                head.left.left = new TreeNode(3);
//                head.right.right = new TreeNode(7);
//                ArrayList<ArrayList<Integer>> res = new ArrayList<>();
//                res = Print(head);
//                for (int i = 0; i < res.size(); i++) {
//                    ArrayList<Integer> temp = res.get(i);
//                    for (int j = 0; j < temp.size(); j++) {
//                        System.out.print(temp.get(j));
//                    }
//                    System.out.println("---------------------");
//
//                }
//            }
//        }
//import java.util.Arrays;
//public class Solution
//{
//    public static int solved(int n,int[] array)
//    {
//        Arrays.sort(array);
//        if(n==1)
//        {
//            return array[0];
//        }
//        int res=0;
//        int temp=0;
//
//        while(n>1)
//        {
//            temp=array[n-1]-array[n-2];
//            res+=temp;
//            temp=0;
//            n-=2;
//        }
//        if(n==1)
//        {
//            res+=array[0];
//        }
//        return res;
//    }
//
//    public static void main(String[] args) {
//        int[] array={2,7,4};
//        int n=3;
//        System.out.println(solved(n,array));
//    }
//}

//


//

//
//public class Solution
//    {
//        public static void solved(String str)
//        {
//            char [] c=str.toCharArray();
//            int k=0;
//            for(int i=0;i<c.length;i++)
//            {
//                if(Character.isLowerCase(c[i]))
//                {
//                    int j=i;
//                    char temp=c[i];
//                    while(j>k)
//                    {
//                        c[j]=c[j-1];
//                        j--;
//                    }
//                    c[k]=temp;
//                    k++;
//                }
//            }
//            for(int i=0;i<c.length;i++)
//            {
//                System.out.println(c[i]);
//            }
//
//
//        }
//
//        public static void main(String[] args) {
//            String str="AkleBiCeilD";
//          solved(str);
//        }
//
//
//    }


//
// class TreeNode {
//     int val;
//      TreeNode left;
//     TreeNode right;
//      TreeNode(int x) { val = x; }
//  }
//
//class BSTIterator {
//     Queue<Integer> queue=new LinkedList<>();
//    public BSTIterator(TreeNode root) {
//        dfs(root);
//    }
//    public void dfs(TreeNode root)
//    {
//        if(root==null){return;}
//        dfs(root.left);
//        queue.add(root.val);
//        dfs(root.right);
//    }
//
//    /** @return the next smallest number */
//    public int next() {
//        return  queue.poll();
//    }
//
//    /** @return whether we have a next smallest number */
//    public boolean hasNext() {
//         if(queue.size()>0)
//         {
//             return true;
//         }
//         return false;
//    }
//
//
//}
//public class Solution
//{
//    public static void main(String[] args) {
//
//        TreeNode root=new TreeNode(5);
//        root.left=new TreeNode(4);
//        root.right=new TreeNode(6);
//        root.left.left=new TreeNode(3);
//        root.right.right=new TreeNode(7);
//
//
//        BSTIterator bstIterator=new BSTIterator(root);
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.next());
//        System.out.println(bstIterator.next());
//
//        System.out.println(bstIterator.hasNext());
//    }
//
//}
//
//
//public class Solution
//    {
//        public static boolean judgeSquareSum(int c) {
//            Double hi= Math.ceil(Math.sqrt(c));
//            int right=hi.intValue();
//            int left=0;
//            while(left<right)
//            {
//                if(left*left+right*right==c)
//                {
//                    System.out.println("left==="+left+",right=="+right);
//                    return true;
//                }
//                else if(left*left+right*right<c)
//                {
//                    left++;
//                }
//                else
//                {
//                    right--;
//                }
//            }
//            return false;
//        }
//    }


//class Solution {
//    public static String reverseVowels(String s) {
//        int left = 0;
//        int right = s.length()-1;
//        char[] c = s.toCharArray();
//        while (left < right) {
//            if (isVowel(c[left]) && isVowel(c[right])) {
//                 swap(c,left,right);
//                 left++;
//                 right--;
//                 continue;
//            }
//            else if(isVowel(c[left]))
//            {
//                right--;
//            }
//            else
//            {
//                left++;
//            }
//        }
//        for(int i=0;i<c.length;i++)
//        {
//            System.out.println(c[i]);
//        }
//        return new String(c);
//    }
//
//    public static void swap(char[] c, int left, int right) {
//        char temp = c[left];
//        c[left] = c[right];
//        c[right] = temp;
//    }
//
//    public static boolean isVowel(char ch) {
//        if (ch == 'e' || ch == 'o' || ch == 'a' || ch == 'i' || ch == 'u') {
//            return true;
//        }
//        return false;
//    }
//
//
//    public static void main(String[] args) {
//        String str="leetcode";
//        System.out.println(reverseVowels(str));
//
//    }
//}
//
//class Solution {
//    public  static boolean validPalindrome(String s) {
//        int len=s.length();
//
//        for(int i=0;i<len/2;i++)
//        {
//            int j=len-i-1;
//            System.out.println("i==="+s.charAt(i)+"j===="+s.charAt(j));
//            if(s.charAt(i)!=s.charAt(j))
//            {
//                return isPalindrome(s,i+1,j)||isPalindrome(s,i,j-1);
//            }
//        }
//        return true;
//
//    }
//    public static boolean  isPalindrome(String s,int i,int j)
//    {
//        while(i<j)
//                {
//            System.out.println("Palindrome----"+"i==="+s.charAt(i)+"j====="+s.charAt(j));
//            if(s.charAt(i)!=s.charAt(j))
//            {
//                return false;
//            }
//            i++;
//            j--;
//        }
//        return true;
//    }
//
//    public static void main(String[] args) {
//        String str="atbbga";
//        System.out.println(validPalindrome(str));
//
//    }
//}
//class Solution {
//    public String findLongestWord(String s, List<String> d) {
//        String res="";
//        for(int i=0;i<d.size();i++)
//        {
//            if(isChildString(s,d.get(i)))
//            {
//                if(res.length()==d.get(i).length())
//                {
//                    if(res.compareTo((String)d.get(i))==1)
//                    {
//                        res=(String)d.get(i);
//                    }
//                }
//                else
//                {
//                    res=res.length()>d.get(i).length()?res:(String)d.get(i);
//                }
//            }
//        }
//        return res;
//
//    }
//    public boolean isChildString(String s,String child)
//    {
//        int i=0;
//        int j=0;
//        while(i<s.length()&&j<child.length())
//        {
//            if(s.charAt(i)==child.charAt(j))
//            {
//                i++;j++;
//            }
//            else
//            {
//                i++;
//            }
//        }
//        if(j<child.length())
//        {
//            return false;
//        }
//        return true;
//
//
//    }
//}
//
//
//class Solution{
//    public static int findKthLargest(int[] nums, int k)
//    {
//
//        k=nums.length-k;
//        int lo=0;
//        int hi=nums.length-1;
//
//        int res=-1;
//        System.out.println("nums.length=="+nums.length+"k==="+k);
//        if(nums.length==1)
//        {
//            if(k==1)
//            {
//                return nums[0];
//            }
//            else
//
//            {
//                return res;
//            }
//        }
//        while(lo<hi) {
//            int m = partition(nums, lo, hi);
//            for (int i = 0; i < nums.length; i++)
//            {
//                System.out.print(nums[i]+",");
//            }
//            System.out.println('\n');
//            System.out.println("m=="+m+"k==="+k);
//            if(k==m)
//            {
//                res= nums[k];
//                break;
//            }
//            else if(m>k)
//            {
//                hi=m-1;
//            }
//            else if(m<k)
//            {
//                lo=m+1;
//            }
//        }
//        return res;
//    }
//    public static int partition(int[] nums,int lo,int hi)
//    {
//        int temp=nums[lo];
//        System.out.println("lo=="+lo);
//        while(lo<hi)
//        {
//            while(nums[hi]>temp&&lo<hi)
//            {
//                hi--;
//            }
//            if(lo>=hi){
//                break;
//            }
//            nums[lo]=nums[hi];
//            lo++;
//            while(lo<hi&&nums[lo]<temp)
//            {
//                lo++;
//            }
//            if(lo>=hi)
//            {
//                break;
//            }
//            nums[hi]=nums[lo];
//            hi--;
//        }
//        nums[lo]=temp;
//        System.out.println("after===="+lo);
//        return lo;
//
//    }
//    public void swap(int[] nums,int i,int j)
//    {
//        int temp=nums[i];
//        nums[i]=nums[j];
//        nums[j]=temp;
//    }
//
//    public static void main(String[] args) {
//        int[] nums={1};
//        System.out.println(findKthLargest(nums,1));
//    }
//}


//class Solution {
//    public List<Integer> topKFrequent(int[] nums, int k) {
//        List<Integer> res=new ArrayList<>();
//        Map<Integer,Integer> frequentMap=new HashMap<>();
//        for(int i=0;i<nums.length;i++)
//        {
//            frequentMap.put(nums[i],frequentMap.getOrDefault(nums[i],1)+1);
//        }
//        List<Integer>[] bucket=new List[nums.length];
//
//        for(Integer key:frequentMap.keySet())
//        {
//            if(bucket[frequentMap.get(key)]==null)
//            {
//                List<Integer> temp=new ArrayList<>();
//                temp.add(key);
//                bucket[frequentMap.get(key)]=temp;
//            }
//            else
//            {
//                bucket[frequentMap.get(key)].add(key);
//            }
//        }
//        for(int j=bucket.length-1;j>0;j--)
//        {
//            if(k-bucket[j].size()>0)
//            {
//                k=-bucket[j].size();
//            }
//            else
//            {
//                res.addAll(bucket[j]);
//            }
//        }
//            return res;
//    }
//}
//
//class Solution {
//    public static List<Integer> topKFrequent(int[] nums, int k) {
//        List<Integer> res = new ArrayList<>();
//        Map<Integer, Integer> frequentMap = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
//            frequentMap.put(nums[i], frequentMap.getOrDefault(nums[i], 0) + 1);
//        }
//        List<Integer>[] bucket = new List[nums.length];
//
//        for (Integer key : frequentMap.keySet()) {
//            if (bucket[frequentMap.get(key)] == null) {
//                List<Integer> temp = new ArrayList<>();
//                temp.add(key);
//                bucket[frequentMap.get(key)] = temp;
//            } else {
//                bucket[frequentMap.get(key)].add(key);
//            }
//        }
//
//        for (int j = bucket.length - 1; j > 0; j--) {
//
//            if (bucket[j] == null) {
//                continue;
//            } else {
//                if (k > 0) {
//                    res.addAll(bucket[j]);
//                    k -= bucket[j].size();
//                    continue;
//                } else {
//                    break;
//                }
//            }
//        }
//        return res;
//    }
//
//}


//class Solution {
//    public static String frequencySort(String s) {
//        Map<Character, Integer> frequencyMap = new HashMap<>();
//        String res = "";
//        for (int i = 0; i < s.length(); i++) {
//            frequencyMap.put(s.charAt(i), frequencyMap.getOrDefault(s.charAt(i), 0) + 1);
//        }
//        List<Character>[] bucket = new List[s.length() + 1];
//        for (Character key : frequencyMap.keySet()) {
//            if (bucket[frequencyMap.get(key)] == null) {
//                List<Character> temp = new ArrayList<>();
//                temp.add(key);
//                bucket[frequencyMap.get(key)] = temp;
//            } else {
//                bucket[frequencyMap.get(key)].add(key);
//            }
//        }
//
//        for (int j = bucket.length - 1; j > 0; j--) {
//            if (bucket[j] != null) {
//
//                List<Character> temp = bucket[j];
//                for (int i = 0; i < temp.size(); i++) {
//                    int k = j;
//                    while (k-- > 0) {
//                        res += temp.get(i);
//                    }
//                }
//            }
//
//        }
//        return res;
//    }
//

//}

//
//public class Solution {
//    public static String frequencySort(String s) {
//        String res="";
//        List<String> list=new ArrayList<>();
//        char[] c = s.toCharArray();
//        Arrays.sort(c);
//        int i = 0;
//        int j = 0;
//        int k = 0;
//        while (j < c.length && i < c.length) {
//            String temp = "";
//            while (j < c.length && c[j] == c[i]) {
//                temp += c[i];
//                j++;
//            }
//            list.add(temp);
//            System.out.println("temp=="+temp);
//            i = j;
//            k++;
//        }
//        String [] strArr=list.toArray(new String[list.size()]);
//        Arrays.sort(strArr, new Comparator<String>() {
//                    @Override
//            public int compare(String o1, String o2) {
//                        return o2.length()-o1.length();
//            }
//        });
//        for(int m=0;m<strArr.length;m++)
//        {
//            res+=strArr[m];
//        }
//        return res;
//    }
//        public static void main(String[] args) {
//        String str="aabc";
//            System.out.println(frequencySort(str));
//    }
//
//}

//
//class Solution {
//    public static void sortColors(int[] nums) {
//        int[] arr=new int[3];
//
//        for(int i=0;i<nums.length;i++)
//        {
//            arr[nums[i]]++;
//        }
//        int m=0;
//        for(int j=0;j<arr.length;j++)
//        {
//            int k=arr[j];
//            while(k-- > 0)
//            {
//                nums[m]=j;
//                m++;
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        int[] nums={0,1,2,1,2,0};
//
//        sortColors(nums);
//        for(int i=0;i<nums.length;i++)
//        {
//            System.out.print(nums[i]);
//        }
//    }
//}
//
//
//
//class Solution {
//    public static int findMinArrowShots(int[][] points) {
//        int len = points.length;
//        int dup = 0;
//        if (len == 0) {
//            return 0;
//        }
//        Arrays.sort(points, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[1] - o2[1];
//            }
//        });
//        int end = points[0][1];
//        for (int i = 1; i < points.length; i++) {
//            if (points[i][0] <= end) {
//                dup++;
//                System.out.println("points["+i+"]"+"="+points[i][0]+"end=="+end);
//            } else {
//                end = points[i][1];
//            }
//        }
//        return points.length - dup;
//    }
//
//    public static void main(String[] args) {
//        int[][] arr = {{1,2}, {2, 3}, {3, 4}, {4, 5}};
//        System.out.println(findMinArrowShots(arr));
//    }
//}
//

//
//class Solution {
//    public int maxSubArray(int[] nums) {
//        int max = Integer.MIN_VALUE;
//        int subArrayVal = max;
//        for (int i = 0; i < nums.length; i++) {
//            if (max < 0) {
//                max = nums[i];
//            } else {
//                max += nums[i];
//            }
//            subArrayVal = Math.max(subArrayVal, max);
//        }
//        return subArrayVal;
//    }
//}
//class Solution {
//    public static int[] searchRange(int[] nums, int target) {
//
//        int first = binarySearch(nums, target);
//        int last = binarySearch(nums, target + 1) - 1;
//        if (first == nums.length || nums[first] != target) {
//
//            return new int[]{-1, -1};
//        }
//        System.out.println("first==" + first + "last===" + last);
//        return new int[]{first, last};
//
//    }
//
//    public static int binarySearch(int[] nums, int target) {
//        int lo = 0;
//        int hi = nums.length;
//
//        while (lo < hi) {
//            int mid = lo + (hi - lo) / 2;
//            if (nums[mid] >= target) {
//                hi = mid;
//            } else {
//                lo = mid + 1;
//            }
//        }
//        return lo;
//    }
//
//    public static void main(String[] args) {
//        int[] nums = {8, 8, 8, 8, 8, 8};
//        searchRange(nums, 0);
//
//    }
//}
//
//class Solution {
//    public List<Integer> diffWaysToCompute(String input) {
//        calculated(input);
//
//    }
//
//    public int calculated(String input)
//    {
//        Stack<Character> stack=new Stack<>();
//
//        for(int i=0;i<input.length();i++)
//        {
//
//        }
//
//
//    }
//
//
//class Solution {
//    public static int numSquares(int n) {
//
//        Queue<Integer> queue = new LinkedList<>();
//        List<Integer> squares = squareNumberList(n);
//        boolean[] marked = new boolean[n];
//        queue.add(n);
//        int count = 0;
//        while (!queue.isEmpty()) {
//            count++;
//            int size = queue.size();
//            while (size-- > 0) {
//                int cur = queue.poll();
//                for (int i = 0; i < squares.size(); i++) {
//                    int res = cur - (Integer) squares.get(i);
//                    if (res == 0) {
//                        return count;
//                    }
//                    if (res < 0) {
//                        break;
//                    }
//                    if (marked[res]) {
//                        continue;
//                    }
//                    marked[res] = true;
//                    ((LinkedList<Integer>) queue).add(res);
//                }
//            }
//        }
//        return count;
//    }
//
//    public static List<Integer> squareNumberList(int n) {
//        List<Integer> squareNumbers = new ArrayList<>();
//        int i = 4;
//        int distance = 3;
//        squareNumbers.add(1);
//        while (i <= n) {
//            squareNumbers.add(i);
//            distance += 2;
//            i += distance;
//        }
//        return squareNumbers;
//    }
//
//}
//
//class Solution {
//    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
//        Queue<String> queue = new LinkedList<>();
//        if (!wordList.contains(endWord)) {
//            return 0;
//        }
//        ((LinkedList<String>) queue).add(beginWord);
//        int transformTime = 0;
//        while (!queue.isEmpty()) {
//            transformTime++;
//            int size = queue.size();
//            while (size-- > 0) {
//                beginWord = queue.poll();
//                System.out.println("---------");
//                System.out.println(beginWord);
//                System.out.println("---------");
//                for (String word : wordList) {
//                    if (isAdapt(beginWord, word) && endWord.equals(word)) {
//                        return transformTime;
//                    }
//                    if (isAdapt(beginWord, word)) {
//                        System.out.println("queue.add===" + word);
//                        ((LinkedList<String>) queue).add(word);
//                    }
//                }
//                if (wordList.contains(beginWord)) {
//                    wordList.remove(beginWord);
//                }
//            }
//        }
//        return 0;
//    }
//
//    public static boolean isAdapt(String s1, String s2) {
//        if (s1.length() != s2.length()) {
//            return false;
//        }
//        int difCount = 0;
//        for (int i = 0; i < s1.length(); i++) {
//            if (s1.charAt(i) != s2.charAt(i)) {
//                difCount++;
//            }
//        }
//
//        return difCount == 1;
//    }
//
//    public static void main(String[] args) {
//        String[] str = {"tours", "awake", "goats", "crape", "boron", "payee", "waken", "cares", "times", "piled", "maces", "cuter", "spied", "spare", "mouse", "minty", "theed", "sprat", "veins", "brian", "crown", "years", "drone", "froth", "foggy", "laura", "sears", "shunt", "gaunt", "hovel", "staff", "child", "arson", "haber", "knows", "rubes", "czars", "pawed", "whine", "treed", "bauer", "jodie", "timed", "flits", "robby", "gooks", "yawls", "purse", "veeps", "tints", "taped", "raced", "shaft", "modes", "dykes", "slims", "parts", "emile", "frail", "salem", "jives", "heave", "bayer", "leech", "clipt", "yanks", "wilds", "hikes", "cilia", "spiel", "mulls", "fetal", "homed", "drown", "suite", "defer", "oaken", "flail", "zippy", "burke", "slued", "mowed", "manes", "verse", "serra", "bruno", "spoke", "mikes", "hafts", "breed", "sully", "croce", "boers", "chair", "thong", "pulse", "pasta", "perot", "fices", "shies", "nadir", "every", "diets", "roads", "cones", "tuned", "globs", "graft", "stall", "royal", "fixes", "north", "pikes", "slack", "vests", "quart", "crawl", "tangs", "calks", "mayor", "filmy", "barns", "block", "hoods", "storm", "cedes", "emote", "tacks", "skirt", "horsy", "mawed", "moray", "wring", "munch", "hewed", "hooke", "batch", "drawl", "berth", "sport", "welch", "jeans", "river", "tabby", "amens", "stump", "cause", "maced", "hiker", "spays", "dusty", "trail", "acorn", "zooms", "puked", "clown", "sands", "kelli", "stein", "rawer", "water", "dolts", "momma", "fluky", "scots", "pupil", "halls", "toady", "pored", "latch", "shags", "union", "tamps", "stead", "ryder", "knoll", "cacao", "damns", "charm", "frank", "draws", "gowns", "risen", "saxes", "lucks", "avert", "yolks", "clime", "wedge", "ruses", "famed", "sabik", "gravy", "anion", "veils", "pyres", "raspy", "lofts", "tress", "showy", "percy", "rices", "taker", "roger", "yeats", "baked", "ayers", "fazes", "curly", "shawn", "clare", "paine", "ranks", "hocks", "berta", "plays", "parks", "tacos", "onion", "skeet", "acton", "lamer", "teals", "reset", "steal", "maven", "sored", "fecal", "harsh", "totem", "swoop", "rough", "jokes", "mires", "weird", "quits", "damps", "touts", "fling", "sarah", "peeps", "waxen", "traps", "mange", "swell", "swoon", "catch", "mower", "bonny", "finds", "yards", "pleas", "filed", "smelt", "drams", "vivid", "smirk", "whigs", "loafs", "opens", "meter", "hakes", "berms", "whack", "donny", "faint", "peace", "libby", "yates", "purer", "wants", "brace", "razed", "emend", "bards", "karyn", "japed", "fated", "missy", "punks", "humps", "steak", "depth", "brunt", "hauls", "craws", "blast", "broom", "tones", "ousts", "wires", "peeks", "ruffs", "crack", "monte", "worth", "spans", "tonic", "runny", "erick", "singe", "maine", "casts", "jello", "realm", "haste", "utter", "bleat", "kasey", "palms", "solos", "hoagy", "sweep", "loner", "naves", "rhine", "acmes", "cadet", "dices", "saris", "mauro", "fifty", "prows", "karat", "dowel", "frays", "shorn", "sails", "ticks", "train", "stars", "stork", "halts", "basal", "glops", "beset", "rifer", "layla", "lathe", "daffy", "jinns", "snide", "groin", "kelly", "zincs", "fryer", "quilt", "drama", "shook", "swami", "hulls", "swazi", "danes", "axons", "those", "lorry", "plath", "prime", "faces", "crock", "shake", "borer", "droop", "derek", "shirk", "styed", "frown", "jells", "slows", "lifts", "jeers", "helms", "turds", "dross", "tired", "rimes", "beats", "dingo", "crews", "bides", "loins", "furry", "shana", "wises", "logos", "aural", "light", "pings", "belch", "campy", "swish", "sangs", "nerds", "boggy", "skies", "weals", "snags", "joyed", "mamet", "miser", "leaks", "ramos", "tract", "rends", "marks", "taunt", "sissy", "lipid", "beach", "coves", "fates", "grate", "gloss", "heros", "sniff", "verve", "tells", "bulge", "grids", "skein", "clout", "leaps", "males", "surfs", "slips", "grave", "boats", "tamed", "muled", "meier", "lower", "leafy", "stool", "reich", "rider", "iring", "ginny", "flaks", "chirp", "tonga", "chest", "ollie", "foxes", "links", "alton", "darth", "drier", "sated", "rails", "gyros", "green", "jenna", "cures", "veals", "sense", "sworn", "roses", "aides", "loses", "rival", "david", "worms", "stand", "track", "dales", "noyes", "fraud", "shock", "sward", "pluto", "biked", "roans", "whiny", "halve", "bunts", "spilt", "gamey", "deeds", "oozed", "ruder", "drano", "sages", "fewer", "maize", "aimed", "bails", "poole", "hunts", "shari", "champ", "shuns", "jonah", "faced", "spook", "harry", "lagos", "peale", "nacho", "saint", "power", "chaff", "shard", "cocky", "irate", "tummy", "withe", "forks", "bates", "stuns", "turfs", "coped", "coups", "vince", "helps", "facet", "fezes", "outer", "cheek", "tried", "sumps", "fakes", "fonds", "yearn", "brays", "flute", "fetid", "beyer", "mamma", "topic", "bouts", "trend", "gorey", "hills", "swaps", "sexes", "cindy", "ruler", "kited", "gaits", "shank", "cloys", "stuck", "purus", "musks", "gouge", "brake", "biker", "layer", "lilly", "bills", "seven", "flyer", "phase", "wowed", "beaus", "cokes", "chimp", "spats", "mooch", "dried", "hulks", "shift", "galen", "wiped", "clops", "decal", "nopes", "huffs", "lades", "sunny", "foyer", "gusty", "wormy", "chips", "focus", "pails", "solid", "ariel", "gamed", "diver", "vying", "sacks", "spout", "sides", "agave", "bandy", "scant", "coils", "marci", "marne", "swank", "basil", "shine", "nines", "clues", "fuzes", "jacks", "robin", "pyxes", "later", "silas", "napes", "homes", "baled", "dames", "abuse", "piker", "coots", "tiles", "bents", "pearl", "booty", "hells", "dusky", "glare", "scale", "pales", "leary", "scull", "bimbo", "mossy", "apron", "manet", "opted", "kusch", "shiny", "argos", "hoped", "towns", "bilbo", "slums", "skull", "shale", "mandy", "scows", "speed", "eager", "lards", "crows", "merry", "anted", "faxed", "leave", "fargo", "creek", "comas", "golda", "baize", "easts", "plied", "rared", "ashed", "doted", "bunin", "bonds", "yarns", "latin", "right", "worst", "sixes", "gabby", "begun", "upend", "giant", "tykes", "creak", "manor", "bosom", "riced", "dimly", "holes", "stunt", "parsi", "peers", "snell", "mates", "jules", "rusty", "myles", "yules", "sades", "hobbs", "booth", "clean", "liven", "gamer", "howdy", "stray", "riser", "wisps", "lubes", "tubes", "rodeo", "bigot", "tromp", "pimps", "reeve", "pumps", "dined", "still", "terms", "hines", "purrs", "roast", "dooms", "lints", "sells", "swims", "happy", "spank", "inset", "meany", "bobby", "works", "place", "brook", "haded", "chide", "slime", "clair", "zeros", "britt", "screw", "ducal", "wroth", "edger", "basie", "benin", "unset", "shade", "doers", "plank", "betsy", "bryce", "cross", "roped", "weans", "bliss", "moist", "corps", "clara", "notch", "sheep", "weepy", "bract", "diced", "carla", "locks", "sawed", "covey", "jocks", "large", "pasts", "bumps", "stile", "stole", "slung", "mooed", "souls", "dupes", "fairs", "lined", "tunis", "spelt", "joked", "wacky", "moira", "strut", "soled", "pints", "axing", "drank", "weary", "coifs", "wills", "gibes", "ceded", "gerry", "tires", "crazy", "tying", "sites", "trust", "dover", "bolds", "tools", "latex", "capet", "lanky", "grins", "brood", "hitch", "perts", "dozes", "keels", "vault", "laius", "chung", "deres", "glove", "corms", "wafer", "coons", "ponce", "tumid", "spinx", "verge", "soggy", "fleas", "middy", "saiph", "payer", "nukes", "click", "limps", "oared", "white", "chart", "nasty", "perth", "paddy", "elisa", "owing", "gifts", "manna", "ofter", "paley", "fores", "sough", "wanda", "doggy", "antic", "ester", "swath", "spoon", "lamas", "meuse", "hotel", "weedy", "quads", "paled", "blond", "flume", "pried", "rates", "petal", "rover", "marsh", "grief", "downy", "pools", "buffs", "dunne", "cruel", "finny", "cosby", "patch", "polly", "jerks", "linen", "cider", "visas", "beard", "mewed", "spill", "trots", "tares", "pured", "prior", "build", "throe", "wends", "baned", "mario", "misty", "golds", "lacey", "slags", "jived", "finis", "inner", "money", "skews", "sunks", "fined", "bauds", "lapel", "class", "berne", "rabin", "roils", "hyped", "styes", "evans", "towed", "hawed", "allow", "modal", "ports", "erich", "rills", "humid", "hooks", "sedge", "shirt", "nippy", "fundy", "runes", "smile", "dolly", "tisha", "byers", "goths", "sousa", "mimed", "welts", "hoots", "shown", "winds", "drays", "slams", "susan", "frogs", "peach", "goody", "boned", "chewy", "eliza", "peary", "pyxed", "tiled", "homer", "tokes", "verdi", "mabel", "rolls", "laden", "loxed", "phony", "woods", "brine", "rooks", "moods", "hired", "sises", "close", "slops", "tined", "creel", "hindu", "gongs", "wanes", "drips", "belly", "leger", "demon", "sills", "chevy", "brads", "drawn", "donna", "glean", "dying", "sassy", "gives", "hazes", "cores", "kayla", "hurst", "wheat", "wiled", "vibes", "kerry", "spiny", "wears", "rants", "sizer", "asses", "duked", "spews", "aired", "merak", "lousy", "spurt", "reeds", "dared", "paged", "prong", "deere", "clogs", "brier", "becks", "taken", "boxes", "wanna", "corny", "races", "spuds", "jowls", "mucks", "milch", "weest", "slick", "nouns", "alley", "bight", "paper", "lamps", "trace", "types", "sloop", "devon", "pedal", "glint", "gawky", "eaves", "herbs", "felts", "fills", "naval", "icing", "eking", "lauds", "stats", "kills", "vends", "capes", "chary", "belle", "moats", "fonts", "teems", "wards", "bated", "fleet", "renal", "sleds", "gases", "loony", "paced", "holst", "seeds", "curie", "joist", "swill", "seats", "lynda", "tasks", "colts", "shops", "toted", "nuder", "sachs", "warts", "pupal", "scalp", "heirs", "wilma", "pansy", "berra", "keeps", "menus", "grams", "loots", "heels", "caste", "hypes", "start", "snout", "nixes", "nests", "grand", "tines", "vista", "copes", "ellis", "narks", "feint", "lajos", "brady", "barry", "trips", "forth", "sales", "bests", "hears", "twain", "plaid", "hated", "kraft", "fared", "cubit", "jayne", "heats", "chums", "pangs", "glows", "lopez", "vesta", "garbo", "ethel", "blood", "roams", "mealy", "clunk", "rowed", "hacks", "davit", "plane", "fuses", "clung", "fitch", "serer", "wives", "lully", "clans", "kinks", "spots", "nooks", "plate", "knits", "greet", "loads", "manic", "scone", "darin", "pills", "earth", "gored", "socks", "fauna", "ditch", "wakes", "savvy", "quiet", "nulls", "sizes", "diana", "mayan", "velds", "dines", "punch", "bales", "sykes", "spiky", "hover", "teats", "lusts", "ricky", "think", "culls", "bribe", "pairs", "month", "cored", "packs", "lobes", "older", "hefts", "faxes", "cased", "swain", "bawdy", "troop", "woven", "stomp", "swags", "beads", "check", "shill", "broad", "souse", "pouch", "lived", "iambs", "teaks", "clams", "outed", "maxed", "plain", "sappy", "cabal", "penal", "shame", "budge", "offed", "kooks", "gybed", "basin", "thoth", "arced", "hypos", "flows", "fetch", "needs", "davis", "jared", "bongo", "added", "sames", "randy", "tunes", "jamar", "smash", "blows", "grows", "palmy", "miler", "chins", "viola", "tower", "cream", "molls", "cello", "sucks", "fears", "stone", "leans", "zions", "nutty", "tasha", "ratty", "tenet", "raven", "coast", "roods", "mixes", "kmart", "looms", "scram", "chapt", "lites", "trent", "baron", "rasps", "ringo", "fazed", "thank", "masts", "trawl", "softy", "toils", "romes", "norma", "teens", "blank", "chili", "anise", "truss", "cheat", "tithe", "lawns", "reese", "slash", "prate", "comet", "runts", "shall", "hosed", "harpy", "dikes", "knock", "strip", "boded", "tough", "spend", "coats", "husky", "tyree", "menes", "liver", "coins", "axles", "macho", "jawed", "weeps", "goods", "pryor", "carts", "dumps", "posts", "donor", "daunt", "limbo", "books", "bowls", "welds", "leper", "benny", "couch", "spell", "burst", "elvin", "limbs", "regal", "loyal", "gaily", "blade", "wheal", "zests", "seine", "hubby", "sheen", "tapes", "slugs", "bench", "lungs", "pipes", "bride", "selma", "berry", "burns", "skins", "bowen", "gills", "conan", "yucky", "gauls", "voled", "crust", "jerky", "moans", "plump", "sided", "disks", "gleam", "larry", "billy", "aloud", "match", "udder", "rises", "wryer", "deter", "cling", "brisk", "lever", "chaps", "tansy", "gland", "rocky", "lists", "joins", "tubed", "react", "farsi", "dopes", "chats", "olsen", "stern", "gully", "youth", "wiles", "slink", "cooke", "arise", "bores", "maims", "danny", "rives", "rusts", "plots", "loxes", "troys", "cleat", "waxes", "booze", "haven", "dilly", "shaun", "gasps", "rains", "panda", "quips", "kings", "frets", "backs", "arabs", "rhino", "beets", "fiber", "duffy", "parry", "sever", "hunks", "cheap", "beeps", "fifes", "deers", "purls", "hello", "wolfs", "stays", "lands", "hawks", "feels", "swiss", "tyros", "nerve", "stirs", "mixed", "tombs", "saves", "cater", "studs", "dorky", "cinch", "spice", "shady", "elder", "plato", "hairs", "newts", "slump", "boots", "lives", "walls", "spunk", "bucks", "mined", "parch", "disco", "newel", "doris", "glues", "brawn", "abner", "piked", "laxes", "bulky", "moran", "cozen", "tinge", "dowry", "snare", "sagan", "harms", "burch", "plows", "sunni", "fades", "coach", "girls", "typed", "slush", "saver", "bulls", "grass", "holed", "coven", "dukes", "ocher", "texan", "cakes", "gilts", "jenny", "salon", "divas", "maris", "costs", "sulla", "lends", "gushy", "pears", "teddy", "huffy", "sited", "rhone", "euler", "solve", "grace", "snarl", "taste", "sally", "allay", "suers", "bogey", "pooch", "songs", "cameo", "molts", "snipe", "cargo", "forge", "reins", "hoses", "crams", "fines", "tings", "wings", "spoor", "twice", "waxed", "mixer", "bongs", "stung", "gages", "yelps", "croci", "corks", "bolls", "madge", "honer", "riled", "camus", "trick", "bowed", "overt", "steed", "ripes", "stave", "crick", "great", "scott", "scald", "point", "finch", "bulks", "chant", "kiddo", "cover", "drunk", "sered", "dicky", "wider", "saith", "mutts", "blind", "lyres", "sized", "darby", "rebel", "zones", "title", "yawns", "laths", "sting", "taine", "paris", "route", "livia", "roots", "belay", "daubs", "spoof", "camel", "colds", "foist", "saned", "doles", "slays", "woody", "leads", "stout", "caper", "erika", "lance", "earns", "vines", "mercy", "antis", "terri", "messy", "lords", "shims", "serfs", "jinni", "caged", "threw", "rainy", "bumpy", "arias", "wails", "romeo", "gorge", "dolls", "risks", "skyed", "fumes", "payed", "mites", "choir", "piles", "scene", "flake", "solon", "brahe", "bikes", "dawes", "goofs", "payne", "cried", "slavs", "hives", "snack", "cribs", "aways", "fired", "swarm", "pumas", "paved", "smith", "gooey", "liefs", "safer", "banes", "slake", "doled", "dummy", "gazed", "heaps", "loped", "scoff", "crash", "balmy", "hexed", "lunch", "guide", "loges", "alien", "rated", "stabs", "whets", "blest", "poops", "cowls", "canes", "story", "cunts", "tusks", "pinto", "scats", "flier", "chose", "brute", "laked", "swabs", "preps", "loose", "merle", "farms", "gapes", "lindy", "share", "floes", "scary", "bungs", "smart", "craps", "curbs", "vices", "tally", "beret", "lenny", "waked", "brats", "carpi", "night", "junes", "signs", "karla", "dowdy", "devil", "toned", "araby", "trait", "puffy", "dimer", "honor", "moose", "synch", "murks", "doric", "muted", "quite", "sedan", "snort", "rumps", "teary", "heard", "slice", "alter", "barer", "whole", "steep", "catty", "bidet", "bayes", "suits", "dunes", "jades", "colin", "ferry", "blown", "bryon", "sways", "bayed", "fairy", "bevel", "pined", "stoop", "smear", "mitty", "sanes", "riggs", "order", "palsy", "reels", "talon", "cools", "retch", "olive", "dotty", "nanny", "surat", "gross", "rafts", "broth", "mewls", "craze", "nerdy", "barfs", "johns", "brims", "surer", "carve", "beers", "baker", "deena", "shows", "fumed", "horde", "kicky", "wrapt", "waits", "shane", "buffy", "lurks", "treat", "savor", "wiper", "bided", "funny", "dairy", "wispy", "flees", "midge", "hooch", "sired", "brett", "putty", "caked", "witch", "rearm", "stubs", "putts", "chase", "jesus", "posed", "dates", "dosed", "yawed", "wombs", "idles", "hmong", "sofas", "capek", "goner", "musts", "tangy", "cheer", "sinks", "fatal", "rubin", "wrest", "crank", "bared", "zilch", "bunny", "islet", "spies", "spent", "filth", "docks", "notes", "gripe", "flair", "quire", "snuck", "foray", "cooks", "godly", "dorms", "silos", "camps", "mumps", "spins", "cites", "sulky", "stink", "strap", "fists", "tends", "adobe", "vivas", "sulks", "hasps", "poser", "bethe", "sudan", "faust", "bused", "plume", "yoked", "silly", "wades", "relay", "brent", "cower", "sasha", "staci", "haves", "dumbs", "based", "loser", "genes", "grape", "lilia", "acted", "steel", "award", "mares", "crabs", "rocks", "lines", "margo", "blahs", "honda", "rides", "spine", "taxed", "salty", "eater", "bland", "sweat", "sores", "ovens", "stash", "token", "drink", "swans", "heine", "gents", "reads", "piers", "yowls", "risky", "tided", "blips", "myths", "cline", "tiers", "racer", "limed", "poled", "sluts", "chump", "greek", "wines", "mangy", "fools", "bands", "smock", "prowl", "china", "prove", "oases", "gilda", "brews", "sandy", "leers", "watch", "tango", "keven", "banns", "wefts", "crass", "cloud", "hunch", "cluck", "reams", "comic", "spool", "becky", "grown", "spike", "lingo", "tease", "fixed", "linda", "bleep", "funky", "fanny", "curve", "josie", "minds", "musty", "toxin", "drags", "coors", "dears", "beams", "wooer", "dells", "brave", "drake", "merge", "hippo", "lodge", "taper", "roles", "plums", "dandy", "harps", "lutes", "fails", "navel", "lyons", "magic", "walks", "sonic", "voles", "raped", "stamp", "minus", "hazel", "clods", "tiffs", "hayed", "rajah", "pared", "hates", "makes", "hinds", "splay", "flags", "tempe", "waifs", "roved", "dills", "jonas", "avers", "balds", "balks", "perms", "dully", "lithe", "aisha", "witty", "ellie", "dived", "range", "lefty", "wined", "booby", "decor", "jaded", "knobs", "roded", "moots", "whens", "valet", "talks", "blare", "heeds", "cuing", "needy", "knees", "broke", "bored", "henna", "rages", "vises", "perch", "laded", "emily", "spark", "tracy", "tevet", "faith", "sweet", "grays", "teams", "adder", "miffs", "tubae", "marin", "folds", "basis", "drugs", "prick", "tucks", "fifth", "treks", "taney", "romps", "jerry", "bulgy", "anton", "codes", "bones", "quota", "turns", "melts", "croat", "woken", "wried", "leash", "spacy", "bless", "lager", "rakes", "pukes", "cushy", "silks", "auden", "dotes", "hinge", "noisy", "coked", "hiked", "garth", "natty", "novel", "peeve", "macaw", "sloth", "warns", "soles", "lobed", "aimee", "toads", "plugs", "chasm", "pries", "douse", "ruled", "venus", "robes", "aglow", "waves", "swore", "strum", "stael", "seeps", "snots", "freed", "truck", "hilly", "fixer", "rarer", "rhyme", "smugs", "demos", "ships", "piped", "jumpy", "grant", "dirty", "climb", "quell", "pulps", "puers", "comte", "kirks", "waver", "fever", "swear", "straw", "serum", "cowed", "blent", "yuppy", "ropes", "conks", "boozy", "feeds", "japes", "auger", "noons", "wench", "tasty", "honed", "balms", "trams", "pasha", "mummy", "tides", "shove", "shyer", "trope", "clash", "promo", "harem", "never", "humus", "burks", "plans", "tempi", "crude", "vocal", "lames", "guppy", "crime", "cough", "rural", "break", "codex", "baggy", "camry", "muses", "exile", "harte", "evens", "uriel", "bombs", "wrens", "goren", "clark", "groom", "tinny", "alias", "irwin", "ruddy", "twins", "rears", "ogden", "joker", "shaky", "sodas", "larch", "lelia", "longs", "leeds", "store", "scars", "plush", "speck", "lamar", "baser", "geeky", "wilda", "sonny", "gummy", "porch", "grain", "testy", "wreck", "spurs", "belie", "ached", "vapid", "chaos", "brice", "finks", "lamed", "prize", "tsars", "drubs", "direr", "shelf", "ceres", "swops", "weirs", "vader", "benet", "gurus", "boors", "mucky", "gilds", "pride", "angus", "hutch", "vance", "candy", "pesky", "favor", "glenn", "denim", "mines", "frump", "surge", "burro", "gated", "badge", "snore", "fires", "omens", "sicks", "built", "baits", "crate", "nifty", "laser", "fords", "kneel", "louse", "earls", "greed", "miked", "tunic", "takes", "align", "robed", "acres", "least", "sleek", "motes", "hales", "idled", "faked", "bunks", "biped", "sowed", "lucky", "grunt", "clear", "flops", "grill", "pinch", "bodes", "delta", "lopes", "booms", "lifer", "stunk", "avery", "wight", "flaps", "yokel", "burgs", "racks", "claus", "haled", "nears", "finns", "chore", "stove", "dunce", "boles", "askew", "timid", "panic", "words", "soupy", "perks", "bilge", "elias", "crush", "pagan", "silts", "clive", "shuck", "fulls", "boner", "claws", "panza", "blurb", "soaks", "skips", "shape", "yells", "raved", "poppy", "lease", "trued", "minks", "estes", "aisle", "penes", "kathy", "combo", "viper", "chops", "blend", "jolly", "gimpy", "burma", "cohan", "gazer", "drums", "gnaws", "clone", "drain", "morns", "wages", "moths", "slues", "slobs", "warps", "brand", "popes", "triad", "ounce", "stilt", "shins", "greer", "hodge", "minos", "tweed", "sexed", "alger", "floss", "timer", "steve", "birch", "known", "aryan", "hedge", "fully", "jumps", "bites", "shots", "curer", "board", "lenin", "corns", "dough", "named", "kinda", "truce", "games", "lanes", "suave", "leann", "pesos", "masks", "ghats", "stows", "mules", "hexes", "chuck", "alden", "aping", "dives", "thurs", "nancy", "kicks", "gibed", "burly", "sager", "filly", "onset", "anons", "yokes", "tryst", "rangy", "pours", "rotes", "hided", "touch", "shads", "tonya", "finer", "moors", "texas", "shoot", "tears", "elope", "tills"};
//        String beginWord = "catch";
//        String endWord = "choir";
//        List<String> wordList = new ArrayList<String>(Arrays.asList(str));
//
//        System.out.println(ladderLength(beginWord, endWord, wordList));
//    }
//}
//
//
//class Solution {
//   private static boolean[][] marked ;
//    public static int maxAreaOfIsland(int[][] grid) {
//        int rows = grid.length;
//        int cols = grid[0].length;
//        int max = 0;
//        marked=new boolean[rows][cols];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//
//                if (grid[i][j] == 1) {
//                    max = Math.max(max, helper(grid, i, j));
//                }
//            }
//        }
//        return max;
//    }
//
//    public static int helper(int[][] grid, int i, int j) {
//        if (i < 0 || j < 0 || i > grid.length - 1 || j > grid[0].length - 1 || marked[i][j]) {
//            return 0;
//        }
//        if (grid[i][j] == 0) {
//            return 0;
//        }
//        marked[i][j] = true;
//        return grid[i][j] + helper(grid,i + 1, j) + helper(grid,i - 1, j) + helper(grid, i, j + 1) + helper(grid,i, j - 1);
//    }
//
//    public static void main(String[] args) {
//        int[][] grid = {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
//                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
//        };
//        System.out.println(maxAreaOfIsland(grid));
//    }
//}
//

/**
 * Input:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * Output: 2
 * Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
 * The 2nd student himself is in a friend circle. So return 2.
 * [[1,0,0,1],
 * [0,1,1,0],
 * [0,1,1,1],
 * [1,0,1,1]]
 */

//class Solution {
//    public static int findCircleNum(int[][] M) {
//        List<Integer>[] friends=new ArrayList[M.length];
//
//
//        for(int i=0;i<M.length;i++)
//        {
//            for(int j=0;j<M[0].length;j++)
//            {
//                if(M[i][j]==1)
//                {
//                   if(friends[i]==null){
//                    friends[i]=new ArrayList<>();
//                    friends[i].add(j);
//                }
//                   else
//                   {
//                       if(!friends[i].contains(j))
//                       {
//                           friends[i].add(j);
//                       }
//                   }
//                }
//            }
//        }
//        boolean[] hasConnected=new boolean[M.length];
//        int count=0;
//        for(int i=0;i<friends.length;i++)
//        {
//            List<Integer>friend=friends[i];
//            if(!hasConnected[i]) {
//                for (int j = 0; j < friend.size(); j++) {
//                    hasConnected[friend.get(j)] = true;
//                }
//                count++;
//            }
//        }
//           return count;
//       }
//
//       public static  void main(String[] args)
//       {
//           int[][] friends={
//                   {1,1,0},
//           {1,1,0},
//           {0,0,1,}};
//           System.out.println(findCircleNum(friends));
//       }
//}
//

/**
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 *
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 */
//class Solution {
//    public static void solve(char[][] board) {
//        for (int i = 0; i < board.length; i++) {
//            if (board[i][0] == 'O' || board[i][board[0].length - 1] == 'O') {
//
//                addColor(board, i, 0);
//                addColor(board, i, board[0].length - 1);
//
//            }
//        }
//        for (int j = 0; j < board[0].length; j++) {
//            if (board[0][j] == 'O' || board[board.length - 1][j] == 'O') {
////                System.out.println(board[board.length-1][0]);
//                addColor(board, 0, j);
//                addColor(board, board.length - 1, j);
//            }
//        }
//
//
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++) {
//                if (board[i][j] == 'O') {
//                    board[i][j] = 'X';
//                } else if (board[i][j] == 'M') {
//                    board[i][j] = 'O';
//                }
//                System.out.println("i==" + i + "j==" + j + "===" + board[i][j]);
//            }
//        }
//
//    }
//
//    public static void addColor(char[][] board, int i, int j) {
//        if (i < 0 || j < 0 || i > board.length - 1 || j > board[0].length - 1 || board[i][j] == 'X' || board[i][j] == 'M') {
//            return;
//        }
//        board[i][j] = 'M';
////        System.out.println("i=="+i+"j=="+j+board[i][j]);
//        addColor(board, i + 1, j);
//        addColor(board, i - 1, j);
//        addColor(board, i, j - 1);
//        addColor(board, i, j + 1);
//    }
//
//    public static void main(String[] args) {
//        char[][] board = {{'X', 'X', 'X', 'X'},
//                {'X', 'O', 'O', 'X'},
//                {'X', 'X', 'O', 'X'},
//                {'X', 'O', 'X', 'X'}
//        };
//        solve(board);
//    }
//}

/**
 * Given the following 5x5 matrix:
 *
 *   Pacific ~   ~   ~   ~   ~
 *        ~  1   2   2   3  (5) *
 *        ~  3   2   3  (4) (4) *
 *        ~  2   4  (5)  3   1  *
 *        ~ (6) (7)  1   4   5  *
 *        ~ (5)  1   1   2   4  *
 *           *   *   *   *   * Atlantic
 *
 * Return:
 *
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 */

//
//class Solution {
//    private static List<int[]> res;
//
//    public static List<int[]> pacificAtlantic(int[][] matrix) {
//        res = new ArrayList<>();
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                if (fluidToAtlantic(matrix, i, j) && fluidTopacific(matrix, i, j)) {
//                    int[] coordinate = new int[2];
//                    coordinate[0] = i;
//                    coordinate[1] = j;
//                    res.add(coordinate);
//                }
//            }
//        }
//        return res;
//    }
//
//    public static boolean fluidTopacific(int[][] matrix, int i, int j) {
//
//        if (i <= 0 || j <= 0) {
//            return true;
//        }
//        if (i > matrix.length - 1 || j > matrix[0].length - 1) {
//            return false;
//        }
//        boolean fluidToDown = false;
//        if (i < matrix.length - 1) {
//            if (matrix[i][j] >= matrix[i + 1][j]) {
//                fluidToDown = fluidTopacific(matrix, i + 1, j);
//            }
//        }
//        boolean fluidToRight = false;
//        if (j < matrix[0].length - 1) {
//            if (matrix[i][j] >= matrix[i][j + 1]) {
//                fluidToRight = fluidTopacific(matrix, i, j + 1);
//            }
//        }
//        if (matrix[i][j] >= matrix[i - 1][j] || matrix[i][j] >= matrix[i][j - 1]) {
//            return fluidTopacific(matrix, i - 1, j) || fluidTopacific(matrix, i, j - 1);
//        }
//        return false||fluidToDown||fluidToRight;
//    }
//
//    public static boolean fluidToAtlantic(int[][] matrix, int i, int j) {
//        if (i >= matrix.length - 1 || j >= matrix[0].length - 1) {
//            return true;
//        }
//        if (i < 0 || j < 0) {
//            return false;
//        }
//        boolean fluidToUp = false;
//        if (i > 0) {
//            if (matrix[i][j] >= matrix[i - 1][j]) {
//                fluidToUp = fluidTopacific(matrix, i - 1, j);
//            }
//        }
//        boolean fluidToLeft = false;
//        if (j > 0) {
//            if (matrix[i][j] >= matrix[i][j - 1]) {
//                fluidToLeft = fluidTopacific(matrix, i, j - 1);
//            }
//        }
//        if (matrix[i][j] >= matrix[i][j + 1] || matrix[i][j] >= matrix[i + 1][j]) {
//            return fluidToAtlantic(matrix, i, j + 1) || fluidToAtlantic(matrix, i + 1, j);
//
//        }
//        return false||fluidToLeft||fluidToUp;
//    }
//
//    public static void main(String[] args) {
//        int[][] matrix = {
//                {1, 2, 3},
//                {8, 9, 4},
//                {7, 6, 5}};
//        List<int[]> res = new ArrayList<>();
//        res = pacificAtlantic(matrix);
//        for (int i = 0; i < res.size(); i++) {
//            int[] temp = res.get(i);
//            System.out.println("[" + temp[0] + "," + temp[1] + "]");
//        }
//    }
//}

//
//

/**
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 */
//class Solution {
//
//    private static List<String> res = new ArrayList<>();
//
//    public static List<String> restoreIpAddresses(String s) {
//
//        if (s.length() <= 0) {
//            return res;
//        }
//        int k=0;
//        int num=0;
//        while(k<s.length())
//        {
//            if(s.charAt(k)=='0')
//            {
//                k++;
//            }
//            else
//            {
//                break;
//            }
//        }
//
//            splitStringIntoIp(s, 0, "", 0);
//
//        return res;
//    }
//
//    public static void splitStringIntoIp(String s, int po, String ip, int num) {
//        if (po > s.length()) {
//            return;
//        }
//        if (num > 4) {
//            return;
//        }
//        if (po == s.length() && num == 4) {
//            res.add(ip);
//            return;
//        }
//
//        if (po + 3 <= s.length() && Integer.parseInt(s.substring(po, po + 3)) < 256&&!s.substring(po,po+1).equals("0")) {
//            if (num == 3) {
//                splitStringIntoIp(s, po + 3, ip + s.substring(po, po + 3), num + 1);
//            } else {
//                splitStringIntoIp(s, po + 3, ip + s.substring(po, po + 3) + '.', num + 1);
//            }
//
//        }
//        if (num == 3) {
//            if (po + 2 <= s.length()&&!s.substring(po,po+1).equals("0")) {
//                splitStringIntoIp(s, po + 2, ip + s.substring(po, po + 2), num + 1);
//            }
//            if (po + 1 <= s.length()) {
//                splitStringIntoIp(s, po + 1, ip + s.substring(po, po + 1), num + 1);
//            }
//
//        } else {
//            if (po + 2 <= s.length()&&!s.substring(po,po+1).equals("0")) {
//                splitStringIntoIp(s, po + 2, ip + s.substring(po, po + 2) + ".", num + 1);
//            }
//            if (po + 1 <= s.length() ) {
//                splitStringIntoIp(s, po + 1, ip + s.substring(po, po + 1) + ".", num + 1);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        String ip = "010010";
//        List<String> res = new ArrayList<>();
//        res = restoreIpAddresses(ip);
//        for (int i = 0; i < res.size(); i++) {
//            System.out.println(res.get(i));
//        }
//    }
//}

//
//class TreeNode
//{
//    int val;
//    TreeNode left;
//
//    TreeNode right;
//
//    public TreeNode(int val)
//    {
//        this.val=val;
//    }
//}

//
//class Solution
//{
//    private static int distance=0;
//    public static int findDistance(TreeNode root,TreeNode node1,TreeNode node2)
//    {
//        helper(root,node1,node2);
//    }
//    public static  void helper(TreeNode root,TreeNode node1,TreeNode node2)
//    {
//           if(root==null)
//           {
//               return ;
//           }
//           helper(root.left,node1,node2);
//           helper(root.right,node1,node2);
//
//           if(root==node1||node2==root) {
//               distance++;
//           }
//    }
//
//
//
//}

/**
 * 找出数组中重复的数字，其中数组中的数字的范围是[0,n-1]
 * 时间复杂度O(N),空间复杂度O(0)
 */
//
//class Solution
//{
//    public static int findDuplicate(int[] arr)
//    {
//        int k=0;
//        while(k<arr.length)
//        {
//            int m=arr[k];
//            if(k==m)
//            {
//                k++;
//            }
//            else
//            {
//                if(arr[k]==arr[arr[k]])
//                {
//                    return arr[k];
//                }
//                else
//                {
//                    swap(arr,k,m);
//                }
//            }
//        }
//        return 0;
//    }
//    public static void swap(int[] arr,int i,int j)
//    {
//        int temp=arr[i];
//        arr[i]=arr[j];
//        arr[j]=temp;
//    }
//    public static void main(String[] args) {
//        int[] arr = {7, 4, 5, 6, 1, 2, 3, 0};
//        System.out.println(findDuplicate(arr));
//    }
//}
//
//class Solution
//{
//    public static void print1ToMaxOfNDigits(int n) {
//        if (n <= 0)
//            return;
//        char[] number = new char[n];
//        print1ToMaxOfNDigits(number, 0);
//    }
//
//    private static void print1ToMaxOfNDigits(char[] number, int digit) {
//        if (digit == number.length) {
//            printNumber(number);
//            return;
//        }
//        for (int i = 0; i < 10; i++) {
//            number[digit] = (char) (i + '0');
//            System.out.println("number"+"["+digit+"]"+number[digit]);
//            print1ToMaxOfNDigits(number, digit + 1);
//        }
//    }
//
//    private static void printNumber(char[] number) {
//        int index = 0;
//        while (index < number.length && number[index] == '0')
//            index++;
//        while (index < number.length)
//            System.out.print(number[index++]);
//        System.out.println();
//    }
//
//    public static void main(String[] args) {
//        print1ToMaxOfNDigits(2);
//    }
//}

/**
 * 题目描述
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 */

//class Solution
//{
//        public  static boolean isNumeric(char[] str) {
//            if(str.length==0)
//            {
//                return false;
//            }
//            return new String(str).matches("[+-]?\\d*(.\\d+)?([Ee]?[+-]?\\d*)");
//        }
//
//
//    public static void main(String[] args) {
//        char[] str={'-','1','E','-','1','6'};
//        System.out.println(isNumeric(str));
//    }
////}
//
//public class Solution {
////    public static int NumberOf1Between1AndN_Solution(int n) {
////        int cnt = 0;
////        for (int m = 1; m <= n; m *= 10) {
////            int a = n / m, b = n % m;
////            cnt += (a + 8) / 10 * m + (a % 10 == 1 ? b + 1 : 0);
////        }
////        return cnt;
////    }
//public class Solution {
//    public static int NumberOf1Between1AndN_Solution(int n) {
//
//        int count=0;
//        while(n>=1)
//        {
//            String nStr=String.valueOf(n);
//            char[] nArray=nStr.toCharArray();
//            for(int i=0;i<nArray.length;i++)
//            {
//                if(nArray[i]=='1')
//                {
//                    count++;
//                }
//            }
//            n--;
//        }
//        return count;
//    }
//}
//    public static void main(String[] args) {
//        System.out.println(NumberOf1Between1AndN(111));
//    }
//}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
//


//
//class Solution {
//    public static int lengthOfLongestSubstring(String s) {
//         if(s.charAt(0)==' ')
//         {
//             return 0;
//         }
//        int len=s.length();
//        int[] monitor=new int[256] ;
//        Arrays.fill(monitor,-1);
//        int i=0;
//        int max=0;
//        int j=0;
//        while(j<len)
//        {
//            int po=s.charAt(j);
//            System.out.println("po=="+s.charAt(j)+"---"+po);
//            if(monitor[po]!=-1)
//            {
//                max=Math.max(max,j-i);
//                i=Math.max(i,monitor[po]+1);
//                monitor[po]=j;
//                j++;
//                System.out.println("i==="+i+"j==="+j);
//                System.out.println("max=="+max);
//            }
//            else
//            {
//                monitor[po]=j;
//                j++;
//                max=Math.max(j-i,max);
//            }
//        }
//        System.out.println("i=="+i+"j=="+j);
//        return Math.max(j-i,max);
//
//    }
//
//    public static void main(String[] args) {
//        String str="abc*&**abs";
//        System.out.println(lengthOfLongestSubstring(str));
//        /**
//         * java.lang.ArrayIndexOutOfBoundsException: -65
//         *   at line 17, Solution.lengthOfLongestSubstring
//         *   at line 54, __DriverSolution__.__helper__
//         *   at line 79, __Driver__.main
//         */
//    }
//}

/**
 * 输入一个字符串包含括号字母和数字，数字表示括里面的字符重复了多少次，括号可能包含小括号，中括号，小括号，并且所以的括号的形式均为正确的。
 * 倒着打印出字符串；例子如下：
 * 输入：ABC3(AB)
 * 结果：ABABABCBA
 * 输入：ABC3[A2(B)]
 * 结果：ABBABBABBCBA
 */
/*
class Solution {
    public static String rebuildString(String s) {
        String res = "";
        Stack<String> stack = new Stack<>();
        Stack<Character> par = new Stack<>();
        int i = s.length()-1;
        while (i >= 0) {
            if (s.charAt(i) == '}' || s.charAt(i) == ']' || s.charAt(i) == ')') {
                par.push(s.charAt(i));
                i--;
                continue;
            }

            if (s.charAt(i) == '{' || s.charAt(i) == '[' || s.charAt(i) == '(') {
                par.pop();
                i--;
                continue;
            }
            if (Character.isLetter(s.charAt(i))) {
                if (par.isEmpty()) {
                    res = res + s.charAt(i);
                    i--;
                    continue;
                } else {
                    String temp = "";
                    int k = i;
                    while (Character.isLetter(s.charAt(k))) {
                        temp = s.charAt(k) + temp;
                        k--;
                    }
                    if (!stack.isEmpty()) {
                        temp += stack.pop();
                    }
                    stack.push(temp);
                    i = k;
                    continue;
                }
            }

            if (Character.isDigit(s.charAt(i))) {
                int num = s.charAt(i) - '1';
                String tempStr = stack.pop();
                String temp = tempStr;
                while (num-- > 0) {
                    temp += tempStr;
                }
                if (par.isEmpty()) {
                    res += temp;
                } else {
                    stack.push(temp);
                }
                i--;
                continue;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNext()) {
            String s = in.nextLine();
            System.out.println(rebuildString(s));
        }
    }
}
*/

/**
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 */
/*
class Solution {
    public String convert(String s, int numRows) {
        HashMap<Integer, List<Character>> hashmap = new HashMap<>();
        int i = 0;
        String res = "";
        while (i++ < s.length()) {
            int j = 0;
            while (j++ < numRows) {
                if (hashmap.containsKey(j)) {
                    hashmap.get(j).add(s.charAt(i + j));
                } else {
                    List<Character> temp = new ArrayList<>();
                    temp.add(s.charAt(i + j));
                    hashmap.put(j, temp);
                }
            }

        }

        for (int k = 0; k < hashmap.size(); k++) {
            List<Character> list = hashmap.get(k);
            for (int m = 0; m < list.size(); m++) {
                res += list.get(m);
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}
*/


/**
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 *
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 *
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 *
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 *
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 *
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 *
 * 说明：
 *
 * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
 *
 * 示例 1:
 *
 * 输入: "42"
 * 输出: 42
 * 示例 2:
 *
 * 输入: "   -42"
 * 输出: -42
 * 解释: 第一个非空白字符为 '-', 它是一个负号。
 *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
 * 示例 3:
 *
 * 输入: "4193 with words"
 * 输出: 4193
 * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
 * 示例 4:
 *
 * 输入: "words and 987"
 * 输出: 0
 * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
 *      因此无法执行有效的转换。
 * 示例 5:
 *
 * 输入: "-91283472332"
 * 输出: -2147483648
 * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
 *      因此返回 INT_MIN (−231) 。
 */
/*
class Solution {
    public static int myAtoi(String str) {

        String preStr = str.trim();

        if (preStr.length() == 0 || preStr == "") {
            return 0;
        }
        if (!Character.isDigit(preStr.charAt(0)) && preStr.charAt(0) != '-') {
            return 0;
        }
        if (Character.isDigit(preStr.charAt(0))) {
            int i = 0;
            String temp = "";
            while (i < preStr.length() && Character.isDigit(preStr.charAt(i))) {
                temp += preStr.charAt(i);
                i++;
            }
            String max = Integer.MAX_VALUE  + "";

            if (compareString(temp,max)) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.parseInt(temp);
            }
        }
        if (preStr.charAt(0) == '-') {
            int i = 1;
            String temp = "";
            while (i < preStr.length() && Character.isDigit(preStr.charAt(i))) {
                temp += preStr.charAt(i);
                i++;
            }
            if (temp.equals("")) {
                return 0;
            }

//            Double res=Double.parseDouble(temp);
//            System.out.println("res=="+res);
//            long res=Integer.parseInt(temp);
            String max = Math.abs(Integer.MAX_VALUE)+"";

//            System.out.println(res>Integer.MAX_VALUE)sout

            if (compareString(temp,max)) {
                return Integer.MIN_VALUE;
            } else {
                return 0 - Integer.parseInt(temp);
            }
        }
        return 0;

    }

    public static void main(String[] args) {


        System.out.println(myAtoi(
                "-2147483647"));

    }

    public static boolean compareString(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return true;
        } else if (s2.length() > s1.length()) {
            return false;
        } else {
            int i = 0;
            while (i < s1.length()) {
                if (s1.charAt(i) < s2.charAt(i)) {
                    return false;
                }
                else if (s1.charAt(i) > s2.charAt(i)) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }
}
*/

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 示例 1:
 *
 * 输入: 121
 * 输出: true
 * 示例 2:
 *
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 *
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 */
//

/**
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 *
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 *
 * 示例 1:
 *
 * 输入: 3
 * 输出: "III"
 * 示例 2:
 *
 * 输入: 4
 * 输出: "IV"
 * 示例 3:
 *
 * 输入: 9
 * 输出: "IX"
 * 示例 4:
 *
 * 输入: 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * 示例 5:
 *
 * 输入: 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 */
//
//


/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 // */

//


//
//class Solution {
//
//    public static ListNode mergeKLists(ListNode[] lists) {
//        if (lists.length == 2) {
//            ListNode head = mergeTwoList(lists[0], lists[1]);
//            return head;
//        }
//        if (lists.length == 1) {
//            return  lists[0];
//        }
//        int len = lists.length - 1;
//        int mid = len >> 1;
//        ListNode[] newLists1 = new ListNode[mid + 1];
//        for (int i = 0; i < mid + 1; i++) {
//            newLists1[i] = lists[i];
//        }
//        ListNode[] newLists2 = new ListNode[lists.length-newLists1.length];
//        System.out.println("mid==="+mid);
//        for (int i = mid + 1; i < lists.length; i++) {
//            newLists2[i - (mid + 1)] = lists[i];
//            System.out.println("list["+i+"]=="+lists[i].val);
//        }
//        System.out.println("newLists1.length   "+newLists1.length);
//        System.out.println("newLists2.length   "+newLists2.length);
//        ListNode l1=mergeKLists(newLists1);
//
//
//
//        ListNode l2=mergeKLists(newLists2);
//
//        ListNode temp1=l1;
//        while(temp1!=null)
//        {
//            System.out.print(temp1.val);
//            temp1=temp1.next;
//        }
//        System.out.println("------------------------------");
//        ListNode temp2=l2;
//        while(temp2!=null)
//        {
//            System.out.print(temp2.val);
//            temp2=temp2.next;
//        }
//        System.out.println("------------------------------");
//        return mergeTwoList(l1,l2);
//    }
//    static class ListNode {
//        int val;
//        ListNode next;
//        ListNode(int x) { val = x; }
//    }
//
//    public static ListNode mergeTwoList(ListNode l1,ListNode l2)
//    {
//        ListNode head=new ListNode(-1);
//       ListNode pre=head;
//
//        while(l1!=null&&l2!=null)
//        {
//            if(l1.val<=l2.val)
//            {
//                ListNode next=l1.next;
//
//                l1.next=null;
//                pre.next=l1;
//
//                pre=pre.next;
//                l1=next;
//            }
//            else
//            {
//                ListNode next=l2.next;
//                l2.next=null;
//                pre.next=l2;
//
//                pre=pre.next;
//                l2=next;
//            }
//        }
//
//        if(l1!=null)
//        {
//            pre.next=l1;
//        }
//        if(l2!=null)
//        {
//            pre.next=l2;
//        }
//        return head.next;
//    }
//
//    public static void main(String[] args) {
//        ListNode list1=new ListNode(1);
//        list1.next=new ListNode(2);
//        list1.next.next=new ListNode(5);
//        ListNode list2=new ListNode(4);
//        list2.next=new ListNode(7);
//        list2.next.next=new ListNode(8);
//        ListNode list3=new ListNode(0);
//        list3.next=new ListNode(6);
//        list3.next.next=new ListNode(9);
//
//        ListNode list4=new ListNode(-1);
//        list4.next=new ListNode(3);
//        list4.next.next=new ListNode(10);
//        ListNode[] lists=new ListNode[4];
//        lists[0]=list1;
//        lists[1]=list2;
//        lists[2]=list3;
//        lists[3]=list4;
//        ListNode head=mergeKLists(lists);
//        while(head!=null)
//        {    b
//            System.out.println(head.val);
//            head=head.next;
//        }
//    }
//}

//class Solution {
//
//    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
//
//        ListNode head = new ListNode(-1);
//        ListNode pre = head;
//        while (l1 != null && l2 != null) {
//            if (l1.val <= l2.val) {
//                ListNode next = l1.next;
//                l1.next = null;
//                pre.next = l1;
//                pre = pre.next;
//                l1 = next;
//            } else {
//                ListNode next = l2.next;
//                l2.next = null;
//                pre.next = l2;
//                pre = pre.next;
//                l2 = next;
//            }
//        }
//        if (l1 == null) {
//            pre.next = l2;
//        }
//        if (l2 == null) {
//            pre.next = l1;
//        }
//        return head.next;
//    }
//
//    public static void main(String[] args) {
//       ListNode l1=new ListNode(1);
//       l1.next=new ListNode(2);
//    }
//}

//
//class Solution {
//    public static int coinChange(int[] coins, int amount) {
//        Queue<Integer> queue = new LinkedList<>();
//
//        boolean[] marked = new boolean[amount];
//        ((LinkedList<Integer>) queue).add(amount);
//        Arrays.sort(coins);
//        int count = 0;
//        while (!queue.isEmpty()) {
//            count++;
//            int size = queue.size();
//            while (size-- > 0) {
//                int number = queue.poll();
//                for (Integer coin : coins) {
//                    if (number - coin == 0) {
//                        return count;
//                    }
//                    if(number-coin<0)
//                    {
//                        break;
//                    }
//                    if(marked[number-coin])
//                    {
//                        continue;
//                    }
//                    if(number-coin>0)
//                    {
//                        ((LinkedList<Integer>) queue).add(number-coin);
//                        marked[number-coin]=true;
//                    }
//                }
//            }
//        }
//        return -1;
//    }
//
//    public static void main(String[] args) {
//        int[] coins={1,2,5};
//        int amount=21;
//        System.out.println(coinChange(coins,amount));
//
//    }
//}
//class Solution {
//    public static int[] countBits(int num) {
//
//        if (num == 0) {
//            int[] res = new int[1];
//            res[0] = 0;
//            return res;
//        }
//        int[] ret = new int[num + 1];
//        for (int i = 0; i <= num; i++) {
//            ret[i] = zeroCount(i);
//
//        }
//        return ret;
//    }
//
//    public static int zeroCount(int n) {
//        int k = 0;
//        while (n > 0) {
//            n &= (n - 1);
//            k++;
//        }
//        return k;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(countBits(5));
//
//    }
//}


//class Solution {
//    public int countNumbersWithUniqueDigits(int n) {
//
//    }
//}


/**
 * 求数组中逆序对的数目
 */

public class Solution {
    public static int reversePairsCount(int[] arr) {

        if (arr.length <= 1) {
            return 0;
        }
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return reverseNumber(arr, copy, 0, arr.length - 1);
    }

    public static int reverseNumber(int[] arr, int[] copy, int start, int end) {
        if (start >= end) {
            copy[start] = arr[start];
            return 0;
        }
        int length = (end + start) >> 2;
        int left = reverseNumber(arr, copy, start, start + length);
        int right = reverseNumber(arr, copy, start + length + 1, end);

        int i = start + length;

        int j = end;
        int indexCopt = end;
        int count = 0;
        while (i >= start && j >= start + length + 1) {
            if (arr[i] > arr[j]) {
                copy[indexCopt--] = arr[i--];
                count += j - start - length;
            } else {
                copy[indexCopt--] = arr[j--];
            }
        }

        for (; i >= start; i--) {
            copy[indexCopt] = arr[i];
        }
        for (; j > start + end + 1; j--) {
            copy[indexCopt] = arr[j];
        }
        return left+right+count;

    }



    public static void main(String[] args) {
       int[] arr={7,5,4,6
       };
        System.out.println(reversePairsCount(arr));
    }


}














