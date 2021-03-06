/**
 *
 * Implementation of UnionFind also known as Disjoint Sets,
 * @author Andrew Choi
 * Date: 05/23/2019
 */

import java.util.ArrayList;
import java.util.Collections;

public class UnionFind {

    private ArrayList<Integer> vertices;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        vertices = new ArrayList<>(Collections.nCopies(n, -1));
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex > vertices.size()-1 || vertex < 0) {
            String exception = String.format("The input is not valid. Please choose from 0 to %s", vertices.size()-1);
            throw new IndexOutOfBoundsException(exception);
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        int index = v1;
        while (parent(index) >= 0) {
            index = parent(index);
        }
        return Math.abs(parent(index));
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return vertices.get(v1);
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // Validates inputs.
        validate(v1);
        validate(v2);
        if (!connected(v1, v2)) {
            int root1 = find(v1);
            int root2 = find(v2);
            int size1 = sizeOf(v1);
            int size2 = sizeOf(v2);
            if (size1 <= size2) {
                vertices.set(root1, root2);
                vertices.set(root2, -(size2+size1));
            } else {
                vertices.set(root2, root1);
                vertices.set(root1, -(size1+size2));
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        int index = vertex;
        int root = vertex;
        int counter = 0;
        if (parent(index) < 0) { return root; }
        while (parent(index) >= 0) {
            root = index = parent(index);
            counter++;
        }
        // Path-compression.
        index = vertex;
        for (int i = 0; i < counter; i++) {
            vertices.set(index, root);
            index = parent(index);
        }
        return root;

    }

}
