import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TestSort {
    int[] a = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 1};

    @Before
    public void before() {
        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    @After
    public void after() {
        System.out.println("排序之后：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    //直接插入排序
    //每步将一个待排序的记录，按其顺序码大小插入到前面已经排序的字序列的合适位置，直到全部插入排序完为止。
    @Test
    public void directInsertionSort() {
        // 直接插入排序
        for (int i = 1; i < a.length; i++) {
            // 待插入元素
            int temp = a[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                // 将大于temp的往后移动一位
                if (a[j] > temp) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = temp;
        }
        System.out.println();

    }

    //二分插入排序
    //二分法插入排序的思想和直接插入一样，只是找合适的插入位置的方式不同，这里是按二分法找到合适的位置，可以减少比较的次数。
    @Test
    public void erFenSort() {
        for (int i = 0; i < a.length; i++) {
            int temp = a[i];
            int left = 0;
            int right = i - 1;
            int mid = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                if (temp < a[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            for (int j = i - 1; j >= left; j--) {
                a[j + 1] = a[j];
            }
            if (left != i) {
                a[left] = temp;
            }
        }
        System.out.println();
    }

    //希尔排序
    //先取一个小于n的整数d1作为第一个增量，把文件的全部记录分成d1个组。所有距离为d1的倍数的记录放在同一个组中。先在各组内进行直接插入排序；然后，取第二个增量d2
    @Test
    public void xiErPaixu() {
        {
            // 希尔排序
            int d = a.length;
            while (true) {
                d = d / 2;
                for (int x = 0; x < d; x++) {
                    for (int i = x + d; i < a.length; i = i + d) {
                        int temp = a[i];
                        int j;
                        for (j = i - d; j >= 0 && a[j] > temp; j = j - d) {
                            a[j + d] = a[j];
                        }
                        a[j + d] = temp;
                    }
                }
                if (d == 1) {
                    break;
                }
            }
            System.out.println();
        }
    }

    //堆排序
    //初始时把要排序的数的序列看作是一棵顺序存储的二叉树，调整它们的存储序，使之成为一个堆，
    // 这时堆的根节点的数最大。然后将根节点与堆的最后一个节点交换。然后对前面(n-1)个数重新调整使之成为堆。
    // 依此类推，直到只有两个节点的堆，并对它们作交换，最后得到有n个节点的有序序列。从算法描述来看
    // ，堆排序需要两个过程，一是建立堆，二是堆顶与堆的最后一个元素交换位置。所以堆排序有两个函数组成。
    // 一是建堆的渗透函数，二是反复调用渗透函数实现排序的函数
    @Test
    public void duiSort() {
    }

    //選擇排序
    //每一趟从待排序的记录中选出最小的元素，顺序放在已排好序的序列最后，直到全部记录排序完毕
    @Test
    public void selectSort() {
        //选择排序
        for (int i = 0; i < a.length - 1; i++) {// 做第i趟排序
            int k = i;
            for (int j = k + 1; j < a.length; j++) {// 选最小的记录
                if (a[j] < a[k]) {
                    k = j; //记下目前找到的最小值所在的位置
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if (i != k) {  //交换a[i]和a[k]
                int temp = a[i];
                a[i] = a[k];
                a[k] = temp;
            }
        }
        System.out.println();
    }

    @Test
    public void testUtilSort() {
        System.out.println();
        Arrays.sort(a);
    }

    @Test
    public void testWeiCalc() {
        int a = 111;
        int b = 222;
        int c = a|b;
        System.out.println();
        System.out.println(c);
    }
}
