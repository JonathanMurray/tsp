package tsp;

public class LinKernighanWithNaive implements TSPSolver{

	private final int limit;
	
	public LinKernighanWithNaive(int limit){
		this.limit = limit;
	}
	
	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		short[] path = new Naive().solveTSP(nodes);
		return new LinKernighan(limit).solveTSP(nodes, visualizer, path);
	}
	
	public String toString(){
		return "Lin-Kernighan(" + limit + ") with Naive start";
	}

}
