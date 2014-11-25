import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GraphVisualizer extends JFrame{
	
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	
	public static void main(String[] args) {
		Random r = new Random();
		short numNodes = 40;
		Interval coordInterval = new Interval(0, 1000);
		Node[] nodes = new Node[numNodes];
		short[] path = new short[numNodes];
		for(short i = 0; i < numNodes; i++){
			int x = r.nextInt(coordInterval.max() + 1);
			int y = r.nextInt(coordInterval.max() + 1);
			nodes[i] = new Node(x, y); 
			path[i] = i;
		}
		Dimension dimension = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		new GraphVisualizer(dimension, coordInterval, nodes, path);
		sleep(1000);
	}
	
	private static void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Node[] nodes;
	private short[] path;
	private Interval coordInterval;
	private final int invisBorderWidth = 40;
	
	public GraphVisualizer(Dimension dimension, Interval coordInterval, Node[] nodes, short[] path){
		super("Visualizer");
		this.nodes = nodes;
		this.path = path;
		this.coordInterval = coordInterval;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);
        setVisible(true);
        setLocationRelativeTo(null); //makes it centered on screen
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintNodes(g);
		paintPath(g);
	}
	
	private void paintNodes(Graphics g){
		for(Node node : nodes){
			int x = coordToScreen(node.x());
			int y = coordToScreen(node.y());
			g.fillRect(x-2, y-2, 4, 4);
		}
	}
	
	private void paintPath(Graphics g){
		int fromIndex = path[0];
		int toIndex;
		for(int i = 1; i < path.length; i++){
			toIndex = path[i];
			paintEdge(g, nodes[fromIndex], nodes[toIndex]);
			fromIndex = path[i];
		}
		paintEdge(g, nodes[nodes.length-1], nodes[0]);
	}
	
	private void paintEdge(Graphics g, Node from, Node to){
		int fromX = coordToScreen(from.x());
		int fromY = coordToScreen(from.y());
		int toX = coordToScreen(to.x());
		int toY = coordToScreen(to.y());
		g.drawLine(fromX, fromY, toX, toY);
	}
	
	private int coordToScreen(int coordinate){
		return (int)(invisBorderWidth + (coordinate - coordInterval.min())/(float)coordInterval.length() * (Math.min(getWidth(), getHeight()) - 2*invisBorderWidth));
	}

}
