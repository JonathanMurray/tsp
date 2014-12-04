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
	
//	Linkernighan(75) and LinKernighan(85) gave 10 points in kattis
	
	
	
	
	//After a few runs:    (this is with the new tIndex-opt)
//	rand-1000nodes.txt:
//	----------------
//	Naive: [time: 8, length: 29622.0]
//	Lin-Kernighan(25) with Naive start: [time: 20, length: 27410.0]
//	Lin-Kernighan(30): [time: 130, length: 80837.0]
//	Lin-Kernighan(150): [time: 315, length: 37018.0]
//	Lin-Kernighan(300): [time: 409, length: 27041.0]
//	Lin-Kernighan(500): [time: 701, length: 24699.0]
	
	
	
	//Running LK(350) on random1000 (w/o precomputed distances):
//	Lin-Kernighan(350) with Naive start: [time: 211, length: 4.9408547E7]
//	Lin-Kernighan(350) with Naive start: [time: 265, length: 4.9683391E7]
//	Lin-Kernighan(350) with Naive start: [time: 306, length: 4.8690678E7]
//	Lin-Kernighan(350) with Naive start: [time: 267, length: 5.0163892E7]
//	Lin-Kernighan(350) with Naive start: [time: 227, length: 4.8341687E7]
//	Lin-Kernighan(350) with Naive start: [time: 229, length: 4.8621453E7]
//	Lin-Kernighan(350) with Naive start: [time: 193, length: 4.9008609E7]
//	Lin-Kernighan(350) with Naive start: [time: 225, length: 4.8923659E7]
//	Lin-Kernighan(350) with Naive start: [time: 205, length: 5.1121268E7]
//	Lin-Kernighan(350) with Naive start: [time: 203, length: 5.144457E7]
//	Lin-Kernighan(350) with Naive start: [time: 200, length: 4.9991479E7]
//	Lin-Kernighan(350) with Naive start: [time: 277, length: 4.9601303E7]
//	Lin-Kernighan(350) with Naive start: [time: 226, length: 4.9564248E7]
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
//		kattis(new LinKernighan(150));
//		testVisualization();
		compareSolvers();
		
//		for(int i = 0; i < 1000; i++){
//			testRandom(Arrays.asList(new TSPSolver[]{
//				new LinKernighan(350)
//			}), 1000);	
//		}
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
		solvers.add(new LinKernighan(30));
		solvers.add(new LinKernighan(150));
		solvers.add(new LinKernighan(300));
		solvers.add(new LinKernighan(500));
		
		
		for(int i = 0; i < 50; i++){
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
