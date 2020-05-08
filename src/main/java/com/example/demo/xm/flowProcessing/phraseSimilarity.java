package com.example.demo.xm.flowProcessing;/**
 * @ClassName phraseSimilarity
 * @Description TODO
 * @Author ZH
 * @Date 2020-04-06 15:29
 * @Version 1.0
 **/

/**
 * 词组相似度
 * @author
 * @create 2020-04-06 15:29
 * @version 1.0
 **/
public class phraseSimilarity {
    public static void main(String[] args) {
        String[] tag1 = { "信息系统", "作业系统"};
        String[] tag2 = {"信息系统", "物流作业系统"};
        double similar = levenshtein(tag1, tag2);
        System.out.println(similar);

    }

    public static float levenshtein(String[] str1, String[] str2) {
        // 计算两个字符串的长度。
        int len1 = str1.length;
        int len2 = str2.length;
        // 建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        // 赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        // 计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1, dif[i - 1][j] + 1);

            }
        }
        // 取数组右下角的值，同样不同位置代表不同字符串的比较
        // System.out.println("差异步骤：" + dif[len1][len2]);
        // 计算相似度
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length, str2.length);

        return similarity;
    }

    private static int min(int a, int b, int c) {
        int min = a < b ? a : b;
        return min < c ? min : c;
    }
}
