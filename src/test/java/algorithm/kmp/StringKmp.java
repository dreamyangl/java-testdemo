package algorithm.kmp;

/**
 * @Description 研究kmp算法
 * @author daixiaoyong
 * @date 2019年3月6日 下午5:10:30
 * 原理链接：http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html
 */
public class StringKmp {

    /**
     * 用于计算匹配的位置（从头到尾）
     *
     * @param str
     * @param sub
     * @return
     */
    public static int kmp(String str, String sub) {
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
            throw new IllegalArgumentException("str或者sub不能为空");
        }

        int j = 0;
        int[] n = next(sub);
        for (int i = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != sub.charAt(j)) {
                j = n[j - 1];
            }

            if (str.charAt(i) == sub.charAt(j)) {
                j++;
            }

            if (sub.length() == j) {
                int index = i - j + 1;
                return index;
            }
        }

        return -1;
    }

    /**
     * 用于生成部分匹配表
     *
     * @param sub
     * @return
     */
    private static int[] next(String sub) {
        int[] n = new int[sub.length()];
        int x = 0;
        for (int i = 1; i < sub.length(); i++) {
            while (x > 0 && sub.charAt(x) != sub.charAt(x)) {
                x = n[x - 1];
            }

            if (sub.charAt(i) == sub.charAt(x)) {
                x++;
            }
            System.out.println(x);
            n[i] = x;
        }
        return n;
    }

    public static void main(String[] args) {

        String str = "BBCABCDABABCDABCDABDE";
        String sub = "ABCDAB";

        int index = StringKmp.kmp(str, sub);
        System.out.println("index-->" + index);
    }
}