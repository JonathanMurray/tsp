import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GraphVisualizer extends JFrame{
	
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	
	public static void main(String[] args) {
		Random r = new Random();
		short numNodes = 8;
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
		GraphVisualizer visualization = new GraphVisualizer(dimension, coordInterval, nodes, path);

		boolean didASwap = false;
		int sleepTime = 200;
		System.out.println(Arrays.toString(path));
		while(true){
			loop:
			for(int i = 1; i < path.length; i++){
				for(int k = i+1; k < path.length; k++){
					visualization.highlight(i, k);
					visualization.repaint();
					sleep(sleepTime);
					visualization.unhighlight();
					didASwap = TwoOpt.maybeSwap(nodes, path, i, k);
					visualization.repaint();
					sleep(sleepTime);
					if(didASwap){
						System.out.println(Arrays.toString(path));
						break loop;
					}
				}
			}
			if(!didASwap){
				break;
			}
		}
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
	private Interval highlighted;
	
	public GraphVisualizer(Dimension dimension, Interval coordInterval, Node[] nodes, short[] path){
		super("Visualizer");
		this.nodes = nodes;
		this.path = path;
		this.coordInterval = coordInterval;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);
        setVisible(true);
        setLocationRelativeTo(null); //makes it centered on screen
        add(new JComponent() {
        	protected void paintComponent(Graphics g) {
        		paintEverything(g);
        	}
		});
	}

	public void highlight(int firstNode, int lastNode){
		highlighted = new Interval(firstNode, lastNode);
	}
	
	public void unhighlight(){
		highlighted = null;
	}

	private void paintEverything(Graphics g){
		paintNodes(g);
		paintPath(g);
		if(highlighted != null){
			Color c = g.getColor();
			g.setColor(Color.GREEN);
			paintSubPath(g, highlighted.min(), highlighted.max(), false);
			g.setColor(c);
		}
	}
	
	private void paintNodes(Graphics g){
		
		for(int i = 0; i < nodes.length; i++){
			Node node = nodes[i];
			int x = coordToScreen(node.x());
			int y = coordToScreen(node.y());
			paintNode(g, x, y, i);
		}
	}
	
	private void paintNode(Graphics g, int screenX, int screenY, int nodeIndex){
		g.fillRect(screenX-2, screenY-2, 4, 4);
		g.drawString("" + nodeIndex, screenX-3, screenY-5);
	}
	
	private void paintPath(Graphics g){
		paintSubPath(g, 0, path.length - 1, true);
	}
	
	private void paintSubPath(Graphics g, int firstNode, int lastNode, boolean loop){
		int fromIndex = path[firstNode];
		int toIndex;
		for(int i = firstNode + 1; i <= lastNode; i++){
			toIndex = path[i];
			paintEdge(g, nodes[fromIndex], nodes[toIndex]);
			fromIndex = path[i];
		}
		if(loop){
			paintEdge(g, nodes[path[lastNode]], nodes[path[firstNode]]);
		}
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
