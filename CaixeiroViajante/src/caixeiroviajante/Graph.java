package caixeiroviajante;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Cindel
 */
public interface Graph extends Serializable {

    public void setEdge(int ori, int target, double weight);

    public ArrayList<Integer> getAdjVertex(int node);

    public int getVertexSize();

    public void printGraph();

    public double getPeso(int ori, int target);
}
