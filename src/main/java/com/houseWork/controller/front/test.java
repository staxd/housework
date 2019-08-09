package com.houseWork.controller.front;

public class test {
//    public static class QuickSort {
//        private static int count;
//        /**
//         * 测试
//         * @param args
//         */
//        public static void main(String[] args) {
//            int[] num = {3,45,78,64,52,11,64,55,99,11,18};
//            System.out.println(arrayToString(num,"未排序"));
//            QuickSort(num,0,num.length-1);
//            System.out.println(arrayToString(num,"排序"));
//            System.out.println("数组个数："+num.length);
//            System.out.println("循环次数："+count);
//
//        }
//        /**
//         * 快速排序
//         * @param num	排序的数组
//         * @param left	数组的前针
//         * @param right 数组后针
//         */
//        private static void QuickSort(int[] num, int left, int right) {
//            //如果left等于right，即数组只有一个元素，直接返回
//            if(left>=right) {
//                return;
//            }
//            //设置最左边的元素为基准值
//            int key=num[left];
//            //数组中比key小的放在左边，比key大的放在右边，key值下标为i
//            int i=left;
//            int j=right;
//            while(i<j){
//                //j向左移，直到遇到比key小的值
//                while(num[j]>=key && i<j){
//                    j--;
//                }
//                //i向右移，直到遇到比key大的值
//                while(num[i]<=key && i<j){
//                    i++;
//                }
//                //i和j指向的元素交换
//                if(i<j){
//                    int temp=num[i];
//                    num[i]=num[j];
//                    num[j]=temp;
//                }
//            }
//            num[left]=num[i];
//            num[i]=key;
//            count++;
//            QuickSort(num,left,i-1);
//            QuickSort(num,i+1,right);
//        }
//        /**
//         * 将一个int类型数组转化为字符串
//         * @param arr
//         * @param flag
//         * @return
//         */
//        private static String arrayToString(int[] arr,String flag) {
//            String str = "数组为("+flag+")：";
//            for(int a : arr) {
//                str += a + "\t";
//            }
//            return str;
//        }
//
//    }

    public static class BubbleSort {
        public static void main(String[] args) {
            int[] arr = {16, 4, 2, 5, 4, 63, 66, 32};
            sort(arr);
            for (int i : arr) {
                System.out.print(i + " ");
            }
        }

        public static void sort(int[] arr) {
            int n = arr.length;
            //对n个元素排序，只需要执行n-1趟
            for (int i = 0; i < n - 1; i++) {
                //每趟排序都会确定此趟中最高的，上一趟确定的最高的无须参与此趟排序
                for (int j = 1; j < n - i; j++) {
                    if (arr[j - 1] > arr[j]) {
                        swap(arr, j - 1, j);
                    }
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }



//public static class SleepSort {
//
//    public static void main(String[] args) {
//
//        int[] arr = {1,4,7,3,8,9,2,6,5,56};
//        //创建指定长度的线程数组
//        SortThread[] sortThreads = new SortThread[arr.length];
//        //指定每个线程数组的值
//        for (int i = 0; i < sortThreads.length; i++) {
//            sortThreads[i] = new SortThread(arr[i]);
//        }
//        //开启每个线程
//        for (int i = 0; i < sortThreads.length; i++) {
//            sortThreads[i].start();
//        }
//    }
//}
//    static class SortThread extends Thread {
//        int s = 0;
//
//        public SortThread(int s) {
//            this.s = s;
//        }
//
//        public void run() {
//            try {
//                sleep(s * 1000 + 10);  //睡眠指定的时间
//            } catch (InterruptedException e) {
//
//                e.printStackTrace();
//            }
//            //输出该数
//            System.out.println(s);
//        }
//    }

}
