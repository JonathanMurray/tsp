package other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import tsp.Dist;
import tsp.Node;
import tsp.Reader;
import tsp.TSPSolver;
import tsp.Visualizer;
import tsp.VisualizerMockup;

public class MST implements TSPSolver {


	
	public void test(){
		ArrayList<Node> points = Reader.getInput();
		Node nodes[] = new Node[points.size()];
		
		points.toArray(nodes);
		short[] path = solveTSP(nodes);
		print(path);
		
		double length = Node.lengthOfPath(path, nodes);
		System.out.println(length);
		
	}

	public static void main(String[] args) {
		new MST().test();
	}
	
	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	/**
	 * Solves
	 */
	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {

		// Create a list of distances between nodes
		ArrayList<Dist> dists = Dist.getDistList(nodes);

		// Sort the list, by increasing distances
		Collections.sort(dists);

		// Generate a minimum spanning tree, return a vetex
		Vertex mst = generateMst(dists, nodes.length);

		short[] nonLegitPath = generateNonLegitPath(mst, nodes.length);

		// print(nonLegitPath);

		short[] path = reducePath(nonLegitPath, nodes.length);

		// print(path);

		return path;
	}

	/**
	 * This takes a non-legit path and skips going to already visited
	 * vertices/nodes. This trip will only be equal to or shorter because of the
	 * triangular inequality.
	 * 
	 * @param nonLegitPath
	 * @param nodes
	 * @return
	 */
	private short[] reducePath(short[] nonLegitPath, int nodes) {
		short[] path = new short[nodes];
		boolean[] visited = new boolean[nodes];

		int ptr = 0;
		for (short vertex : nonLegitPath) {

			// If the vertex has already been visited, it is not included in the
			// path again
			if (visited[vertex])
				continue;

			path[ptr] = vertex;
			ptr++;
			visited[vertex] = true;

		}

		return path;
	}

	/**
	 * This constructs a non legit path from the mst, where each path is back
	 * and forth every vertex/node. This therefore is exactly 2*|mst|, which in
	 * turn is <= 2*OPT.
	 * 
	 * Why would we construct a non legit path?
	 * 
	 * Reason: It follows the algorithm! It's easy to implement with a mst and
	 * later reduce the path.
	 * 
	 * @param mst
	 * @param nodesSize
	 * @return
	 */
	private short[] generateNonLegitPath(Vertex mst, int nodesSize) {
		// boolean visited[] = new boolean[nodesSize];

		// The size of the route will be the number of edges in the MST
		// (vertices-1) times two (back and forth).
		short[] path = new short[(nodesSize - 1) * 2];

		mst.createPath(path);

		return path;

	}

	/**
	 * This takes a sorted distance list and returns an arraylist containing the
	 * minimum spanning tree
	 * 
	 * @param dists
	 * @return
	 */
	private Vertex generateMst(ArrayList<Dist> dists, int nodesSize) {

		Vertex neighbours[] = new Vertex[nodesSize];
		for (short i = 0; i < nodesSize; i++)
			neighbours[i] = new Vertex(i);

		int vertexCount = 0;
		for (Dist dist : dists) {
			if (vertexCount == nodesSize - 1)
				break;

			Vertex a = neighbours[dist.a];
			Vertex b = neighbours[dist.b];

			if (a.isAlreadyDistantNeighbourTo(b))
				continue;

			a.addNeighbour(b);
			vertexCount++;

		}

		// for (Vertex vertex : neighbours) {
		// System.out.println(vertex);
		// }

		return neighbours[dists.get(0).a];
	}

	private void print(short[] path) {
		String str = "Path: ";
		for (short s : path) {
			str += s + " ";
		}

		System.out.println(str);
	}

	private class Vertex {
		short id;
		LinkedList<Vertex> neighbours;

		public Vertex(short id) {
			this.id = id;
			neighbours = new LinkedList<Vertex>();
		}

		public boolean isAlreadyDistantNeighbourTo(Vertex vertex) {
			return isAlreadyDistantNeighbourTo(vertex, this);
		}

		private boolean isAlreadyDistantNeighbourTo(Vertex vertex,
				Vertex previous) {

			for (Vertex neighbour : neighbours) {
				if (neighbour.id == vertex.id)
					return true;

				// Don't check the source! Avoid endless loop
				if (neighbour.id == previous.id)
					continue;

				// Check if there will be a cycle back to the vertex
				if (neighbour.isAlreadyDistantNeighbourTo(vertex, this))
					return true;
			}
			return false;
		}

		public void createPath(short[] path) {
			createPath(path, 0, this);
		}

		private int createPath(short[] path, int ptr, Vertex prev) {

			for (Vertex neighbour : neighbours) {
				if (prev.id != neighbour.id) {
					path[ptr] = id;
					ptr++;
					ptr = neighbour.createPath(path, ptr, this);
				}
			}

			if (prev != this) {
				path[ptr] = id;
				ptr++;
			}
			return ptr;
		}

		public void addNeighbour(Vertex vertex) {
			neighbours.add(vertex);
			vertex.neighbours.add(this);
		}

		public String toString() {
			String str = id + ": ";
			for (Vertex neighbour : neighbours) {
				str += neighbour.id + ", ";
			}
			return str;
		}
	}

	
	@Override
	public String toString() {
		return "MST";
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer, short[] path) {
		throw new RuntimeException();
	}

}
