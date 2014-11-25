import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class VisualizerImpl extends JFrame implements Visualizer{

	private Node[] nodes;
	private short[] path;
	private Interval coordInterval;
	private final int invisBorderWidth = 40;
	private Interval highlighted;
	
	public VisualizerImpl(Dimension dimension, Interval coordInterval, Node[] nodes){
		super("Visualizer");
		this.nodes = nodes;
		this.path = new short[]{};
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
	
	public void sleep(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void highlight(int firstNode, int lastNode){
		highlighted = new Interval(firstNode, lastNode);
	}
	
	public void dehighlight(){
		highlighted = null;
	}
	
	public void setPath(short[] path){
		this.path = path;
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
