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
	//LK-1000 gave 28.07 in kattis (4/12 17:07)
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		kattis(new LinKernighanSimAnneal(500, 5f, 0.98f));
//		testVisualization();
//		compareSolvers();
		
//		compareOnRandomNodes();
	}
	
	private static void compareOnRandomNodes(){
		List<TSPSolver> solvers = new ArrayList<TSPSolver>();
		solvers.add(new Naive());
		solvers.add(new LinKernighan(1000));
		solvers.add(new CombinedWithNaive(new LinKernighan(1000)));
		solvers.add(new LinKernighanSimAnneal(500, 5f, 0.98f));
//		solvers.add(new CombinedWithNaive(new LinKernighanSimAnneal(50, 3f, 0.99f)));
//		for(float temp : new float[]{1f, 2f, 3f}){
//			for(float mult : new float[]{ 0.99f, 0.992f, 0.994f, 0.996f, 0.998f}){
//				for(int limit : new int[]{1000}){
//					solvers.add(new CombinedWithNaive(new LinKernighanSimAnneal(limit, temp, mult)));
//				}
//				
//			}
//		}
		for(int i = 0; i < 1; i++){
			testRandom(solvers, 1000);	
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
		Node[] nodes = generateRandomNodes(numNodes);
		for(TSPSolver solver : solvers){
			Result result = Tester.test(solver, nodes);
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
