package tsp;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import tsp.Tester.Result;
import tsp.VisualizerImpl.TSPInput;
import tsp.VisualizerImpl.VisualizationParams;
 
@SuppressWarnings("unused")
public class Main {
	 
	private static final short NUM_NODES = 1000;
	private static final int MIN_COORD = 0;
	private static final int MAX_COORD = 1000;
	private static final Dimension WINDOW_SIZE = new Dimension(600,600);
	
	public static void main(String[] args) throws NumberFormatException, IOException {
//		kattis(new TwoOpt());
//		testVisualization();
		compareSolvers();
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
		solvers.add(new MST());
		solvers.add(new TwoOpt());
		solvers.add(new LinKernighan(200));
		solvers.add(new LinKernighan(300));
		solvers.add(new LinKernighan(400));
		solvers.add(new LinKernighan(500));
		solvers.add(new LinKernighan(600));
		solvers.add(new LinKernighan(700));

		Tester.compareSolvers(solvers, testFiles, 1);
	}
	
	private static void testVisualization(){
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
