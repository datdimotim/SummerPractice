package com.katier.firstStep;

import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Graph{
    private final int V;
    public  int[][] weights;

    private EdgeState[][] edges;
    private VertexState[] vertexes;
    public Cell[][] m;
    private @NotNull ArrayList< Consumer<Void>> changeListeners =new ArrayList<>();
    public Graph(int v) {
        V=v;
        weights=new int[V][V];
        FloydWarshallAlgorithm.Result result=FloydWarshallAlgorithm.run(weights,0);
        m=result.m;
        edges=result.es;
        vertexes=result.vs;
    }

    public Graph getLarge(){
        Graph g=new Graph(V+1);
        for (int i = 0; i < V; i++) {
            System.arraycopy(weights[i], 0, g.weights[i], 0, V);
        }
        FloydWarshallAlgorithm.Result res=FloydWarshallAlgorithm.run(g.weights,0);
        g.m=res.m;
        g.vertexes=res.vs;
        g.edges=res.es;
        return g;
    }

    public void  setMatr(int matr [][]){
         weights = Utils.deepCopy(matr);
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
                return Color.PINK.getRGB();
            }
        },WIN {
            @Override
            public int getColor() {
                return Color.GREEN.getRGB();
            }
        },LOSE {
            @Override
            public int getColor() {
                return Color.RED.getRGB();
            }
        }
        ,TMP {
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
                return Color.PINK.getRGB();
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

