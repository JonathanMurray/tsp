package tsp;
import java.awt.Dimension;
import java.util.Random;

import static tsp.VisualizerImpl.TSPInput;
import static tsp.VisualizerImpl.VisualizationParams;

public class Main {
	
	private static final short NUM_NODES = 550;
	private static final int MIN_COORD = 0;
	private static final int MAX_COORD = 1000;
	private static final Dimension WINDOW_SIZE = new Dimension(600,600);
	
	public static void main(String[] args) {
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
		new TwoOpt().solveTSP(nodes, coordInterval, visualizer);
		System.out.println("done");
	}
	
	
	
	
}
