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
	
	public static void main(String[] args) throws NumberFormatException, IOException {
//		kattis(new LinKernighan(200));
//		testVisualization();
//		compareSolvers();
		
		for(int i = 0; i < 1000; i++){
			testRandom(Arrays.asList(new TSPSolver[]{
//					new Naive(),
//					new LinKernighan(85),
					new LinKernighan(150),
//					new LinKernighan(500)
			}), 1000);	
		}
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
//		testFiles.add("g1.txt");
//		testFiles.add("rand-50nodes.txt");
//		testFiles.add("rand-150nodes.txt");
//		testFiles.add("rand-500nodes.txt");
		testFiles.add("rand-1000nodes.txt");
		
		List<TSPSolver> solvers = new ArrayList<TSPSolver>();
		solvers.add(new Naive());
//		solvers.add(new MST());
//		solvers.add(new TwoOpt());
//		solvers.add(new LinKernighanWithNaive(25));
//		solvers.add(new LinKernighan(30));
		solvers.add(new LinKernighan(85));
		solvers.add(new LinKernighan(300));
		solvers.add(new LinKernighan(500));
		
		
		for(int i = 0; i < 500; i++){
			Tester.compareSolvers(solvers, testFiles, 1);	
		}
	}
	
	private static void testRandom(Collection<TSPSolver> solvers, int numNodes){
		for(TSPSolver solver : solvers){
			Result result = Tester.test(solver, generateRandomNodes(numNodes));
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
