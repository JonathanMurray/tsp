package tsp;

public interface TSPSolver {
	/**
	 * Try to find the shortest path for given nodes. 
	 * The coordinates of all nodes lie within given interval.
	 * @param nodes The nodes (graph).
	 * @return A path (a list of node-indices)
	 */
	short[] solveTSP(Node[] nodes);
	
	/**
	 * Try to find the shortest path for given nodes. 
	 * The coordinates of all nodes lie within given interval.
	 * Also visualize the algorithm while running. Call sleep and repaint
	 * when needed.
	 * @param nodes The nodes (graph).
	 * @param visualizer A graphical program that visualizes the algorithm.
	 * @return A path (a list of node-indices)
	 */
	short[] solveTSP(Node[] nodes, Visualizer visualizer);
}
