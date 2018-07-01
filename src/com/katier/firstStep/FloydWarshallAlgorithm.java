package com.katier.firstStep;

public class FloydWarshallAlgorithm{
    public static class Result{
        final Graph.Cell[][] m;
        final boolean hasNext;

        public Result(Graph.Cell[][] m, boolean hasNext) {
            this.m = m;
            this.hasNext = hasNext;
        }
    }
    public static Result run(int[][] m, int steps){
        Graph.Cell[][] mat=new Graph.Cell[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                mat[i][j]=new Graph.Cell(m[i][j],i);
            }
        }
        return run(mat,steps);
    }

    private static Result run(Graph.Cell[][] m, int steps){
        main_loop: for(int i=0;i<m.length;i++){
            for(int j=0;j<m.length;j++){
                if(i==j)continue;
                if(m[j][i].weight==0)continue;
                for(int k=0;k<m.length;k++){
                    if(k==i)continue;
                    if(k==j)continue;
                    if(m[i][k].weight==0)continue;
                    steps--;
                    if(steps<0)break main_loop;
                    if(m[j][i].weight+m[i][k].weight<(m[j][k].weight==0?Integer.MAX_VALUE:m[j][k].weight)){
                        m[j][k]=new Graph.Cell(m[j][i].weight+m[i][k].weight,i);
                    }

                }
            }
        }
        return new Result(m,steps<0);
    }
}
