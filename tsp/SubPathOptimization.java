package tsp;

/**
 * Point with this is to quickly create a (not so great) solution, and then
 * optimizing it's sub-components... or sub paths
 * 
 * @author matslexell
 *
 */
public class SubPathOptimization implements TSPSolver {

	final int C = 4;

	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {

		short path[] = new Naive().solveTSP(nodes);
		optimizeSubPath(0, path.length - 1, path, nodes);

		return path;
	}

	private void optimizeSubPath(int from, int to, short[] path, Node[] nodes) {
		int size = 1 + to - from;

		// Divide the list recursively until it's of a specific size. //TODO how
		// do we decide C?
		if (size > C) {
			int middle = (to + from) / 2;
			optimizeSubPath(from, middle, path, nodes);
			optimizeSubPath(middle + 1, to, path, nodes);
			return;
		}
		// What to do here?

//		System.out.println("C: " + C + ", [" + from + ", " + to + "]");
	}

	public String toString() {
		return "Naive+AntColony";
	}

}
