package com.katier.firstStep;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.util.function.Consumer;

import static com.katier.firstStep.Utils.*;

public class Utils{
    public static int[][] deepCopy(int[][] m){
        int[][] r=new int[m.length][];
        for(int i=0;i<m.length;i++)r[i]=m[i].clone();
        return r;
    }
}