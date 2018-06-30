package com.katier.firstStep;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

import static com.katier.firstStep.Utils.deepCopy;

public class Graph implements Cloneable{
    private final int V;
    private final EdgeState[][] edges;
    private final VertexState[] vertexes;
    private final int[][] matrix;
    private @NotNull
    ArrayList< Consumer<Void>> changeListeners =new ArrayList<>();
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

    public void addChangeListeners(Consumer<Void> changeListeners){
        if (changeListeners != null) this.changeListeners.add(changeListeners);
    }

    public void setEdgeState(int from, int to, EdgeState state){
        edges[from][to]=state;
        for(Consumer<Void> cl: changeListeners) cl.accept(null);
    }

    public EdgeState getEdgeState(int from, int to){
        return edges[from][to];
    }

    public void setVertexState(int i, VertexState state){
        vertexes[i]=state;
        for(Consumer<Void> cl: changeListeners) cl.accept(null);
    }

    public VertexState getVertexState(int i){
        return vertexes[i];
    }
    public void setEggeWeight(int from, int to, int weight){
        matrix[from][to]=weight;
        for(Consumer<Void> cl: changeListeners) cl.accept(null);
    }

    public int getEdgeWeigth(int from, int to){
        return matrix[from][to];
    }
}
enum EdgeState{
    NORMAL,SELECTED,WIN,LOSE
}

enum VertexState{
    NORMAL,ACTIVE,PROCESSED,NEIGHBOR
}
