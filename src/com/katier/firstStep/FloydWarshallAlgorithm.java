package com.katier.firstStep;

public class FloydWarshallAlgorithm{
    public static class Result{
        public final Graph.Cell[][] m;
        public final boolean hasNext;
        public final Graph.VertexState[] vs;
        public final Graph.EdgeState[][] es;

        public Result(Graph.Cell[][] m, boolean hasNext, Graph.VertexState[] vs, Graph.EdgeState[][] es) {
            this.m = m;
            this.hasNext = hasNext;
            this.vs = vs;
            this.es = es;
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
        Graph.VertexState[] vs=new Graph.VertexState[m.length];
        Graph.EdgeState[][] es=new Graph.EdgeState[m.length][m.length];
        for(int i=0;i<vs.length;i++)vs[i]= Graph.VertexState.NORMAL;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j <m.length; j++)
                es[i][j]= Graph.EdgeState.NORMAL;

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

                    if(steps==0){
                        vs[i]= Graph.VertexState.ACTIVE;
                        es[j][i]= Graph.EdgeState.SELECTED;
                        es[i][k]= Graph.EdgeState.SELECTED;
                    }
                    if(m[j][i].weight+m[i][k].weight<(m[j][k].weight==0?Integer.MAX_VALUE:m[j][k].weight)){
                        m[j][k]=new Graph.Cell(m[j][i].weight+m[i][k].weight,i);
                        if(steps==0)es[j][k]= Graph.EdgeState.LOSE;
                    }
                    else if(steps==0)es[j][k]= Graph.EdgeState.WIN;

                }
            }
        }
        return new Result(m,steps<0, vs, es);
    }
}
