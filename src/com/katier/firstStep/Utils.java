package com.katier.firstStep;


public class Utils{
    public static int[][] deepCopy(int[][] m){
        int[][] r=new int[m.length][];
        for(int i=0;i<m.length;i++)r[i]=m[i].clone();
        return r;
    }
}