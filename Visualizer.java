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
	default void repaint(){}
	default void sleep(){}
	default void highlight(int from, int to){}
	default void dehighlight(){}
	default void setPath(short[] path){}
}
