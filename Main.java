import java.awt.Dimension;
import java.util.Random;


public class Main {
	
	private static final short NUM_NODES = 150;
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
		VisualizerImpl visualizer = new VisualizerImpl(WINDOW_SIZE, coordInterval, nodes);
		new TwoOpt().solveTSP(nodes, coordInterval, visualizer);
	}
	
	
	
	
}
