package tsp;
import java.awt.Dimension;
import java.util.Random;

import tsp.VisualizerImpl.TSPInput;
import tsp.VisualizerImpl.VisualizationParams;
 
@SuppressWarnings("unused")
public class Main {
	 
	private static final short NUM_NODES = 20;
	private static final int MIN_COORD = 0;
	private static final int MAX_COORD = 1000;
	private static final Dimension WINDOW_SIZE = new Dimension(600,600);
	
	public static void main(String[] args) {
//		kattis();
		testVisualization();
	}
	
	private static void kattis(){
		Node[] nodes = Reader.getInput().toArray(new Node[]{});
		short[] path = new TwoOpt().solveTSP(nodes);
		for(short nodeIndex : path){
			System.out.println(nodeIndex);
		}
	}
	
	private static void test(){
		
	}
	
	private static void testVisualization(){
		Random r = new Random();
		Interval coordInterval = new Interval(MIN_COORD, MAX_COORD);
		Node[] nodes = new Node[NUM_NODES];
		for(short i = 0; i < NUM_NODES; i++){
			int x = coordInterval.min() + r.nextInt(coordInterval.length());
			int y = coordInterval.min() + r.nextInt(coordInterval.length());
			nodes[i] = new Node(x, y); 
		}
		TSPInput tspInput = new TSPInput(coordInterval, nodes);
		VisualizationParams params = new VisualizationParams(1000, 0);
		VisualizerImpl visualizer = new VisualizerImpl(WINDOW_SIZE, tspInput, params);
		new TwoOpt().solveTSP(nodes, visualizer);
		System.out.println("done");
	}
	
	
}
