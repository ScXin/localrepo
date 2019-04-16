import javax.xml.stream.events.Characters;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.rmi.server.ExportException;
import java.util.*;

public class Main {

/**  this is a solution of calculator implement
 public static int calculate(String s) {

 char[] str=s.toCharArray();
 String re="";
 for(int m=0;m<str.length;m++)
 {
 if(str[m]==' ')
 {
 continue;
 }
 else
 {
 re+=str[m];

 }
 }
 Stack<Integer> number = new Stack<>();
 Stack<Character> operator = new Stack<>();
 int k = 0;
 int length = re.length();
 while (k < length) {
 if (Character.isDigit(re.charAt(k))) {
 int po = k;
 while (po < length && Character.isDigit(re.charAt(po))) {
 po++;
 }
 Integer.parseInt(re.substring(k, po));
 number.push(Integer.parseInt(re.substring(k, po)));
 k = po - 1;

 } else {

 if (re.charAt(k) == '-') {
 operator.push('-');
 }
 if (re.charAt(k) == '+') {
 operator.push('+');
 }
 if (re.charAt(k) == '*') {
 int po = k + 1;

 while (po < length && Character.isDigit(re.charAt(po))) {
 po++;
 }
 int multiNum = Integer.parseInt(re.substring(k + 1, po));
 int value = multiNum * number.pop();
 number.push(value);
 k = po - 1;
 }
 if (re.charAt(k) == '/') {
 int po = k + 1;

 while (po < length && Character.isDigit(re.charAt(po))) {
 po++;
 }
 int divideNum = Integer.parseInt(re.substring(k + 1, po));
 int value = number.pop() / divideNum;
 number.push(value);
 k = po - 1;
 }
 }

 }
 while (!operator.isEmpty()) {

 Character oper = operator.pop();

 if (oper == '+') {
 if(!operator.isEmpty()&&operator.peek()=='-')
 {
 int first=number.pop();
 int second=number.pop();
 int res =  second-first;
 number.push(res);
 operator.pop();
 operator.push('+');
 }
 else
 {
 int res = number.pop() + number.pop();
 number.push(res);
 }

 }
 if (oper == '-') {
 if(!operator.isEmpty()&&operator.peek()=='-')
 {
 int first=number.pop();
 int second=number.pop();
 int res =  second+first;
 number.push(res);
 }
 else {
 int second = number.pop();
 int first = number.pop();
 int res = first - second;
 number.push(res);
 }
 }
 }
 return number.pop();
 }
 */
/**
 // this is solution
 public  static  int[] maxSlidingWindow(int[] nums, int k) {
 ArrayList<Integer> res = new ArrayList<>();
 int[] result = new int[nums.length - k + 1];
 for (int i = 0; i < nums.length-k+1; i++) {
 int m = i;
 int max= 0;
 while (m - i < k) {
 max=Math.max(max,nums[m]);
 m++;
 }
 res.add(max);
 }
 for (int j = 0; j < res.size(); j++) {
 result[j] = (Integer) res.get(j);
 System.out.println(result[j]);
 }
 return result;

 }

 public static void main(String[] args) {
 int[] arr={1,2,3,1,2,,9,0,1,-11};
 maxSlidingWindow(arr,0);
 }
 */


/**
 public void solve(char[][] board) {
 int rows=board.length;
 int cols=board[0].length;
 if(rows==0||cols==0||rows==1||cols==1)
 {
 return;
 }
 boolean[][] flag=new boolean[rows][cols];

 for(int i=0;i<rows;i++)
 for(int j=0;j<cols;j++)
 {
 flag[i][j]=false;
 }
 for(int i=1;i<rows-1;i++)
 for(int j=1;j<cols-1;j++)
 {
 if(board[i][j]=='O'&&adjust(board,i,j,rows,cols,flag))
 {
 board[i][j]='X';
 }
 }
 }
 public boolean adjust(char[][]board,int row,int col,int rows,int cols,boolean[][]flag)
 {
 if(row==0)
 {
 if(board[row][col]=='X')
 return true;
 else
 return false;
 }
 if(col==0)
 {
 if(board[row][col]=='X')
 return true;
 else
 return false;
 }
 if(row==rows)
 {
 if(board[row][col]=='X')
 return true;
 else
 return false;
 }
 if(col==cols)
 {
 if(board[row][col]=='X')
 return true;
 else
 return false;
 }
 if(board[row][col]=='X')
 {
 return true;
 }
 if(flag[row][col])
 {
 return true;
 }
 if(board[row][col]=='O')
 {
 boolean temp=adjust(board,row+1,col,rows,cols,flag)&&adjust(board,row-1,col,rows,cols,flag)&&adjust(board,row,col-1,rows,cols,flag)&&adjust(board,row,col+1,rows,cols,flag);
 if(temp)
 {
 flag[row][col]=true;
 }
 return temp;
 }
 return true;
 }
 }
 */


//     public class TreeNode {
//         int val;
//         TreeNode left;
//       TreeNode right;
//        TreeNode(int x) { val = x; }
//
//
//    class Solution {
//        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//            Set<Integer> set1=new HashSet<>();
//            Set<Integer> set2=new HashSet<>();
//            set1.add(root.val);
//            if(root.left!=null) {
//                dfs(root.left, set1);
//            }
//            if(root.right!=null) {
//                dfs(root.right, set1);
//            }
//            int r=p.val;
//            int s=q.val;
//            if(set1.contains(r)&&set2.contains(s))
//            {
//                return root;
//            }
//            if()
//            }
//        }
//
//        }
//        public void dfs(TreeNode node,Set<Integer> sets)
//        {
//            if(node==null) {
//                return;
//            }
//            sets.add(node.val);
//            dfs(node.left,sets);
//            dfs(node.right,sets);
////        }
//        public static  void parseSnapshotName(String SnapshotName)
//    {
//        String temp=SnapshotName;
//        char arr[]=temp.toCharArray();
//        for(int i=-0;i<arr.length;i++)
//        {
//            if(arr[i]==':')
//            {
//                arr[i]='.';
//            }
//        }
//        SnapshotName=new String(arr);
//
//    }
//public static void main(String[] args) {
//
//
//    String snapshotspath="G:\\snapshots";
//    File file=new File(snapshotspath,"10.23.28.sav");
//    if(!file.exists())
//    {
//        System.out.println(file.getPath());
//        try {
//            file.createNewFile();
//        }catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }
//    String str="12:12:13:12";
//    System.out.println("before=="+str);
//   parseSnapshotName(str);
//    System.out.println("after=="+str) ;
//
//
//
//}
//public static int[] findOrder(int numCourses, int[][] prerequisites) {
//
//    Set<Integer>  res=new HashSet<>();
//    Queue<Integer> queue=new LinkedList<>();
//    int [] result=new int[numCourses];
//    int[] arr=new int[numCourses];
//
//    for(int i=0;i<prerequisites.length;i++)
//    {
//        arr[prerequisites[i][0]]++;
//    }
//
//    for(int j=0;j<numCourses;j++)
//    {
//        if(arr[j]==0)
//        {
//            queue.offer(j);
//        }
//    }
//    while(!queue.isEmpty())
//    {
//
//        int startCourse=((LinkedList<Integer>) queue).getFirst();
//        res.add(startCourse);
//        System.out.println(startCourse);
//        for(int i=0;i<prerequisites.length;i++)
//        {
//            if(prerequisites[i][1]==startCourse)
//            {
//                queue.offer(prerequisites[i][0]);
//            }
//        }
//    }
//
//
//
//      res.stream().forEach(p-> System.out.println(p));
//    return res.stream().mapToInt(p->p.intValue()).toArray();

//}


//    public static  int findCircleNum(int[][] M) {
//        int row=M.length;
//        int col=M[0].length;
//        int sum=0;
//        for(int i=1;i<row;i++)
//        {
//            if(M[i][i-1]==0)
//            {
//                sum+=1;
//            }
//        }
//        return sum+1;
//    }
//public static int[] findOrder(int numCourses, int[][] prerequisites) {
//    Queue<Integer> res=new LinkedList<>();
//
//    Set<Integer> result=new HashSet<>();
//    int[] indegree=new int[numCourses];
//    int length=prerequisites.length;
//    for(int i=0;i<length;i++)
//    {
//        indegree[prerequisites[i][0]]++;
//    }
//    for(int j=0;j<indegree.length;j++)
//    {
//        if(indegree[j]==0)
//        {
//            res.offer(j);
//        }
//    }
//    while(!res.isEmpty())
//    {
//        int startCourse=res.poll();
//        result.add(startCourse);
//        for(int i=0;i<prerequisites.length;i++)
//        {
//            if(startCourse==prerequisites[i][1])
//            {
//                indegree[prerequisites[i][0]]--;
//                if(indegree[prerequisites[i][0]]==0)
//                {
//                    res.offer(prerequisites[i][0]);
//                }
//            }
//        }
//    }
//    for(int j=0;j<indegree.length;j++)
//    {
//        if(indegree[j]!=0)
//        {
//            return new int[0];
//        }
//    }
//    return res.stream().mapToInt(p->p.intValue()).toArray();
//}
//
//
//    public static void main(String[] args) {
////        int[][] arr = {{1, 0, 0, 1}, {0, 1, 1, 0}, {0, 1, 1, 1}, {1, 0, 1, 1}};
////        System.out.println(findCircleNum(arr));
//          int [][]arr=new int[1][2];
//          arr[0][0]=0;
//          arr[0][1]=1;
//          try{        Thread.currentThread().sleep(6000);
//          }
//          catch(Exception ex)
//          {
//              ex.printStackTrace();
//          }
//
//          int numCourses=2;
//          int[] result= findOrder(numCourses,arr);
//          Arrays.stream(result).forEach(e-> System.out.println(e));
//
//}

    /**
     * using the jconsole tool to monitor the JVM Memory
     */
    /*
    static class OOMObject {
        public byte[] placeholder=new byte[64*1024];
      }


      public static void fillHeap(int num) throws InterruptedException
      {
          List<OOMObject> list=new ArrayList<>();
          for(int i=0;i<num;i++)
          {
              Thread.sleep(500);
              list.add(new OOMObject());
              System.gc();
          }

      }

    public static void main(String[] args) throws  Exception{
        fillHeap(100);
    }*/


    /**
     * create a test instance of using jconcole tool to monitor thread
     */

    /*
    public static void createBusyThread()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {

                }
            }
        },"testBusyThread");
        thread.start();
    }
    public static void createLockThread(final Object lock)
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try{
                        lock.wait();
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        },"testLockThread");
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object object=new Object();
        createLockThread(object);

    }*/

    /**
     *
     */
    /*static class SynAddRunable implements Runnable {
        int a, b;

        public SynAddRunable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a))
            {
                synchronized (Integer.valueOf(b))
                {
                    System.out.println(a+b);
                }
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++)
        {
            new Thread(new SynAddRunable(1,2)).start();
            new Thread(new SynAddRunable(2,1)).start();

        }
    }*/

    /**
     * Longest Increasing Path in a Matrix,time Limit
     * @param matrix
     * @return
     */
/*
    public static int longestIncreasingPath(int[][] matrix) {
            int row=matrix.length;
            int col=matrix[0].length;
            int[][]flag=new int[row][col];
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    flag[i][j]=0;
                }
            }
            int maxLen=0;
            for(int i=0;i<row;i++)
            {
                for(int j=0;j<col;j++)
                {
                    if(flag[i][j]!=0)
                    {
                        maxLen=Math.max(maxLen,flag[i][j]);
                    }
                    else {
                       int value=solved(matrix, i, j, flag);
                        maxLen=Math.max(maxLen,value);
                    }

                }
            }
            return maxLen;
        }

    public static int solved(int[][] matrix,int i,int j,int[][] flag) {
        if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length) {
            return 0;
        }
        int up = 1;
        int down = 1;
        int left = 1;
        int right = 1;
        if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
            up = 1 + solved(matrix, i - 1, j, flag);
        }
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            down = 1 + solved(matrix, i + 1, j, flag);
        }
        if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
            left = 1 + solved(matrix, i, j - 1, flag);
        }
        if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
            right = 1 + solved(matrix, i, j + 1, flag);
        }
        int max1 = Math.max(up, down);
        int max2 = Math.max(left, right);
        int max = Math.max(max1, max2);
        flag[i][j] = max;
        System.out.println("flag" + "[" + i + "]" + "[" + j + "]" + "===" + flag[i][j]);
        return max;
    }

    public static void main(String[] args) {
    }
    */
//    private static ArrayList<String>currList;
//    private static List<List<String>>res=new ArrayList<>();
//    public static List<List<String>> partition(String s) {
//         currList=new ArrayList<>();
//        solved(s,0);
//        return res;
//    }
//    public static void solved(String s,int l)
//    {
//        if(currList.size()>0&&l>=s.length()){
//            List<String> r=(ArrayList<String>)currList.clone();
//            res.add(r);
//        }
//        for(int i=1;i<s.length();i++)
//        {
//            if(isPalindrome(s,l,i))
//            {
//                if(l==i)
//                    currList.add(s.substring(l,i+1));
//                else
//                    currList.add(s.substring(l,i+1));
//                solved(s,i+1);
//                currList.remove(currList.size()-1);
//            }
//        }
//    }
//    public static boolean isPalindrome(String s,int l,int r)
//    {
//        if(l==r){
//            return true;
//        }
//        while(l<r)
//        {
//            if(s.charAt(l)!=s.charAt(r))
//            {
//                return false;
//            }
//            l++;
//
//            r--;
//        }
//        return true;
//    }
//
//    public static void main(String[] args) {
//            String s="aab";
//             partition(s);
//    }
//}
            /*
   static List<List<String>> resultLst;
    static  ArrayList<String> currLst;
    public static List<List<String>> partition(String s) {
        resultLst = new ArrayList<List<String>>();
        currLst = new ArrayList<String>();
        backTrack(s,0);
        return resultLst;
    }
    public static void backTrack(String s, int l){
        if(currLst.size()>0 //the initial str could be palindrome
                && l>=s.length()){
            List<String> r = (ArrayList<String>) currLst.clone();
            resultLst.add(r);
        }
        for(int i=l;i<s.length();i++){
            if(isPalindrome(s,l,i)){
                if(l==i)
                    currLst.add(Character.toString(s.charAt(i)));
                else
                    currLst.add(s.substring(l,i+1));
                System.out.println("l=="+l+"i=="+i+"s.substring(l,i)"+s.substring(l,i+1));
                backTrack(s,i+1);
                currLst.remove(currLst.size()-1);
            }
        }
    }
    public static boolean isPalindrome(String str, int l, int r){
        if(l==r) return true;
        while(l<r){
            if(str.charAt(l)!=str.charAt(r)) return false;
            l++;r--;
        }
        return true;
    }

    public static void main(String[] args) {
        String s="aab";
        partition(s);
    }
}
*/
     static class SharedVariableThread extends Thread {
        private int  count = 5;

        @Override
        public synchronized void run() {
            super.run();
            count--;
            System.out.println("由 " + SharedVariableThread.currentThread().getName() + " 计算，count=" + count);
        }
    }
        public static void main(String[] args) {
            SharedVariableThread mythread = new SharedVariableThread();
            // 下列线程都是通过mythread对象创建的
            Thread a = new Thread(mythread, "A");
            Thread b = new Thread(mythread, "B");
            Thread c = new Thread(mythread, "C");
            Thread d = new Thread(mythread, "D");
            Thread e = new Thread(mythread, "E");
            a.start();
            b.start();
            c.start();
            d.start();
            e.start();
        }

    }





