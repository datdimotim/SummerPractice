package com.katier.firstStep;

import com.sun.istack.internal.NotNull;

import java.util.function.Consumer;

import static com.katier.firstStep.Utils.deepCopy;

public class Graph implements Cloneable{
    private final int V;
    private final EdgeState[][] edges;
    private final VertexState[] vertexes;
    private final int[][] matrix;
    private @NotNull Consumer<Void> changeListener=(v)->{};
    public Graph(int[][] mat) {
        edges=new EdgeState[mat.length][mat.length];
        for(int i=0;i<mat.length;i++)
            for(int j=0;j<mat.length;j++)
                edges[i][j]=EdgeState.NORMAL;
        vertexes=new VertexState[mat.length];
        for(int i=0;i<mat.length;i++)vertexes[i]=VertexState.NORMAL;
        matrix=deepCopy(mat);
        V=mat.length;
    }

    public int getV(){
        return V;
    }

    public void setChangeListener(Consumer<Void> changeListener){
        if(changeListener==null)this.changeListener=(v)->{};
        else this.changeListener=changeListener;
    }

    public void setEdgeState(int from, int to, EdgeState state){
        edges[from][to]=state;
        changeListener.accept(null);
    }

    public EdgeState getEdgeState(int from, int to){
        return edges[from][to];
    }

    public void setVertexState(int i, VertexState state){
        vertexes[i]=state;
        changeListener.accept(null);
    }

    public VertexState getVertexState(int i){
        return vertexes[i];
    }
    public void setEggeWeight(int from, int to, int weight){
        matrix[from][to]=weight;
        changeListener.accept(null);
    }

    public int getEdgeWeigth(int from, int to){
        return matrix[from][to];
    }
}
