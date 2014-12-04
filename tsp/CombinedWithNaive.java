package tsp;


public class CombinedWithNaive implements TSPSolver{

	private final TSPSolver improver;
	
	public CombinedWithNaive(TSPSolver improver){
		this.improver = improver;
	}
	
	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		short[] path = new Naive().solveTSP(nodes);
		return improver.solveTSP(nodes, visualizer, path);
	}
	
	public String toString(){
		return improver + "+Naive";
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer, short[] path) {
		throw new RuntimeException();
	}

}
