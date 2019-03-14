package com.liu.geohash.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二进制转化工具
 */
public class BinaryConversionUtil {

    public static void main(String[] args) {
        String result = geoHashEncode(39.928167, 116.389550, 20);
        System.out.println(result);
    }


    /**
     * 获取经纬度的geohash编码
     * @param latitude  维度
     * @param longtitude  经度
     * @param precision  精度
     * @return
     */
    public static String geoHashEncode(double latitude, double longtitude, int precision){

        String latitudeStr = convert2Binary(-90, 90, latitude, null, precision);
        String longtitudeStr = convert2Binary(-180, 180, longtitude, null, precision);

        char[] a = longtitudeStr.toCharArray();
        char[] b = latitudeStr.toCharArray();
        char[] r = new char[a.length+b.length];

        List<String> sArray = new ArrayList<String>();

        String c = "";
        for (int i=0; i<r.length; i++){
            if (i%2 == 0) {
                r[i] = a[i/2];
            }else {
                r[i] = b[(i-1)/2];
            }

            c += String.valueOf(r[i]);
            if((i+1)%5 == 0 || i == r.length-1){
                if (!"".equals(c)){
                    sArray.add(c);
                    c = "";
                }
            }
        }

        String geohashcode = sArray.stream().mapToInt(s -> Integer.valueOf(s, 2)).mapToObj(v -> base32(v)).collect(Collectors.joining());
        return geohashcode;
    }

    /**
     * 将经纬度转化成二进制字符串
     * @param min  最小值
     * @param max  最大值
     * @param v  要转化的经度或是维度
     * @param r  转化成的字符串
     * @param precision  精度
     * @return
     */
    public static String convert2Binary(double min, double max, double v, String r, int precision){
        double mid = (min+max)/2;
        if (r==null){
            r = "";
        }
        //是否在左边
        boolean left = between(min, mid, v);
        //是否在右边
        boolean right = between(mid, max, v);

        if (left && !right){
            r+=0;
            max = mid;
        }
        if (!left && right){
            r += 1;
            min = mid;
        }

        if (r.length()>= precision){
            return r;
        }else {
            return convert2Binary(min, max, v, r, precision);
        }
    }

    /**
     * 判断某一个值是否在该区间范围内
     * @param start
     * @param end
     * @param v
     * @return
     */
    public static boolean between(double start, double end, double v){
        return v>= start && v<=end;
    }

    /**
     * base32 编码表
     * @param v
     * @return
     */
    public static String base32(int v){
        switch (v){
            case 0:return "0";
            case 1:return "1";
            case 2:return "2";
            case 3:return "3";
            case 4:return "4";
            case 5:return "5";
            case 6:return "6";
            case 7:return "7";
            case 8:return "8";
            case 9:return "9";
            case 10:return "b";
            case 11:return "c";
            case 12:return "d";
            case 13:return "e";
            case 14:return "f";
            case 15:return "g";
            case 16:return "h";
            case 17:return "j";
            case 18:return "k";
            case 19:return "m";
            case 20:return "n";
            case 21:return "p";
            case 22:return "q";
            case 23:return "r";
            case 24:return "s";
            case 25:return "t";
            case 26:return "u";
            case 27:return "v";
            case 28:return "w";
            case 29:return "x";
            case 30:return "y";
            case 31:return "z";
            default:return "0";
        }
    }
}
