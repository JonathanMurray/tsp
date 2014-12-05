package tsp;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import tsp.Tester.Result;
import tsp.VisualizerImpl.TSPInput;
import tsp.VisualizerImpl.VisualizationParams;
 
@SuppressWarnings("unused")
public class Main {

	//LK-150 gave 15.05 in kattis  (4/12 15:53)
	//LK-175 gave 20.68 in kattis (4/12 17:02)
	//LK-250 gave 23.44 in kattis (4/12 17:02)
	//LK-400 gave 26.59 in kattis (4/12 17:04)
	//LK-600 gave 26.75 in kattis (4/12 17:05)
	//LK-1000 gave 28.07 in kattis (4/12 17:07) (all the above with naive I think)
	//LK-1000 steepest descent with naive gave only 8.39 (4/12 22:13)
	//LK-1000 limited descent(35) gave 28.92 (5/12 13:09)
	//LK-1000 limited descent(70) gave 29.29 (5/12 13:12)
	//LK-1000 limited descent(120) gave 28.67 (5/12 13:15)
	//LK-1000 limited descent(120) p(0.1) gave 29.83 (5/12 13.27)
	//LK-1000 limited descent(70) p(0.1) gave 29.92 (5/12 13.29)
	
	public static void main(String[] args) throws NumberFormatException, IOException {
//		kattis(new CombinedWithNaive(new LinKernighanLimitedDescent(1000, 70, 0.05f)));
//		testVisualization();
//		compareSolvers();
		compareOnRandomNodes();
	}
	
	private static void compareOnRandomNodes(){
		List<TSPSolver> solvers = new ArrayList<TSPSolver>();
		solvers.add(new Naive());
		solvers.add(new CombinedWithNaive(new LinKernighan(1000)));
		solvers.add(new CombinedWithNaive(new LinKernighanLimitedDescent(1000, 50, 0.02f)));
		solvers.add(new CombinedWithNaive(new LinKernighanSimAnneal(100, 2f, 0.95f, 0.0001f)));
		solvers.add(new CombinedWithNaive(new LinKernighanSteepestDescent(500)));
		testRandom(solvers, 1000);	
	}
	
	private static void kattis(TSPSolver solver){
		Node[] nodes = Reader.getInput().toArray(new Node[]{});
		short[] path = solver.solveTSP(nodes);
		for(short nodeIndex : path){
			System.out.println(nodeIndex);
		}
	}
	
	private static void compareSolvers() throws NumberFormatException, IOException{
		List<String> testFiles = new ArrayList<String>();
		testFiles.add("rand-1000nodes.txt");
		
		List<TSPSolver> solvers = new ArrayList<TSPSolver>();
		solvers.add(new Naive());
		solvers.add(new LinKernighan(85));
		solvers.add(new LinKernighan(300));
		solvers.add(new LinKernighan(500));
		
		
		for(int i = 0; i < 500; i++){
			Tester.compareSolvers(solvers, testFiles, 1);	
		}
	}
	
	private static void testRandom(Collection<TSPSolver> solvers, int numNodes){
		Node[] nodes = generateRandomNodes(numNodes);
		for(TSPSolver solver : solvers){
			Result result = Tester.test(solver, nodes, 10);
			System.out.println(solver + ": " + result);
		}
		
	}
	
	private static Node[] generateRandomNodes(int numNodes){
		Random r = new Random();
		Node[] nodes = new Node[numNodes];
		for(int i = 0; i <  numNodes; i++){
			double x =  r.nextDouble() * 2 * 1000000 - 1000000;
			double y =  r.nextDouble() * 2 * 1000000 - 1000000;
			nodes[i] = new Node(x, y);
		}
		return nodes;
	}
	
	private static void testVisualization(){
		final short NUM_NODES = 1000;
	    final int MIN_COORD = 0;
		final int MAX_COORD = 1000;
		final Dimension WINDOW_SIZE = new Dimension(600,600);
		Random r = new Random();
		Interval coordInterval = new Interval(MIN_COORD, MAX_COORD);
		Node[] nodes = new Node[NUM_NODES];
		for(short i = 0; i < NUM_NODES; i++){
			int x = coordInterval.min() + r.nextInt(coordInterval.length());
			int y = coordInterval.min() + r.nextInt(coordInterval.length());
			nodes[i] = new Node(x, y); 
			System.out.println(nodes[i]);
		}
		TSPInput tspInput = new TSPInput(coordInterval, nodes);
		VisualizationParams params = new VisualizationParams(1000, 0);
		VisualizerImpl visualizer = new VisualizerImpl("Visualization", WINDOW_SIZE, tspInput, params);
		new TwoOpt().solveTSP(nodes, visualizer);
		System.out.println("done");
	}
	
	
}
