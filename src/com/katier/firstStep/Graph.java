package com.katier.firstStep;

import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Graph{
    private final int V;
    private final int[][] weights;

    private EdgeState[][] edges;
    private VertexState[] vertexes;
    private Cell[][] m;
    private @NotNull ArrayList< Consumer<Void>> changeListeners =new ArrayList<>();
    public Graph(int v) {
        V=v;
        edges=new EdgeState[V][V];
        for(int i=0;i<V;i++)
            for(int j=0;j<V;j++)
                edges[i][j]=EdgeState.NORMAL;
        vertexes=new VertexState[V];
        for(int i=0;i<V;i++)vertexes[i]=VertexState.NORMAL;
        weights=new int[V][V];
        m=FloydWarshallAlgorithm.run(weights,0).m;
    }

    public int getV(){
        return V;
    }

    public void addChangeListeners(Consumer<Void> changeListeners){
        if (changeListeners != null) this.changeListeners.add(changeListeners);
    }

    public Cell getMatrixCell(int i,int j){
        return m[i][j];
    }

    public EdgeState getEdgeState(int from, int to){
        return edges[from][to];
    }

    public VertexState getVertexState(int i){
        return vertexes[i];
    }

    public boolean setStepOfAlgorithm(int step){
        FloydWarshallAlgorithm.Result r=FloydWarshallAlgorithm.run(weights,step);
        m=r.m;
        edges=r.es;
        vertexes=r.vs;
        notifyListeners();
        return r.hasNext;
    }

    public void setEdgeWeight(int from, int to, int weight){
        weights[from][to]=weight;
        m=FloydWarshallAlgorithm.run(weights,0).m;
        notifyListeners();
    }

    private void notifyListeners(){
        for(Consumer<Void> cl: changeListeners) cl.accept(null);
    }

    public int getEdgeWeight(int from, int to){
        return weights[from][to];
    }

    public enum EdgeState{
        NORMAL {
            @Override
            public int getColor() {
                return Color.BLACK.getRGB();
            }
        },SELECTED {
            @Override
            public int getColor() {
                return Color.RED.getRGB();
            }
        },WIN {
            @Override
            public int getColor() {
                return Color.GREEN.getRGB();
            }
        },LOSE {
            @Override
            public int getColor() {
                return Color.GRAY.getRGB();
            }
        };
        public abstract int getColor();
    }

    public enum VertexState{
        NORMAL {
            @Override
            public int getColor() {
                return Color.BLACK.getRGB();
            }
        },ACTIVE {
            @Override
            public int getColor() {
                return Color.RED.getRGB();
            }
        },PROCESSED {
            @Override
            public int getColor() {
                return Color.YELLOW.getRGB();
            }
        },NEIGHBOR {
            @Override
            public int getColor() {
                return Color.BLUE.getRGB();
            }
        };
        public abstract int getColor();
    }
    public static class Cell{
        public final int weight;
        public final int prev;

        public Cell(int weight, int prev) {
            this.weight = weight;
            this.prev = prev;
        }
    }
}

