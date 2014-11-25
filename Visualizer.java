
public interface Visualizer {
	//Empty default methods to get a "mockup" for free
	default void repaint(){}
	default void sleep(){}
	default void highlight(int from, int to){}
	default void dehighlight(){}
	default void setPath(short[] path){}
}
