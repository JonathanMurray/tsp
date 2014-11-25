package tsp;

/**
 * This class is used from within algorithms, to visualize the running of the algorithm.
 * setPath must be called as soon as the first has been generated. Then the reference is kept by
 * the visualizer. repaint() must be called everytime the path changes if changes should be drawn.
 * highlight() can be used to further improve the visualization, and sleep can be used to control
 * running speed.
 * @author Jonathan
 *
 */
public interface Visualizer {
	//Empty default methods to get a "mockup" for free
	void repaint();
	void sleep();
	void highlight(int from, int to);
	void dehighlight();
	void setPath(short[] path);
	
	public static Visualizer getMockup(){
		return new Visualizer(){
			public void repaint() {}
			public void sleep() {	}
			public void highlight(int from, int to) {}
			public void dehighlight() {	}
			public void setPath(short[] path) {}
		};
	}
}