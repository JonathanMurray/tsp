package tsp;

public class VisualizerMockup implements Visualizer{
	public void repaint() {}
	public void sleep() {	}
	public void highlight(int colorIndex, int from, int to) {}
	public void highlightLoose(int colorIndex, int from, int to){}
	public void dehighlight(int colorIndex){}
	public void dehighlight() {	}
	public void setPath(short[] path) {}
}
