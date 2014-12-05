package tsp;





public class TwoOpt implements TSPSolver{

	@Override
	public short[] solveTSP(final Node[] nodes) {
		//Call solveTSP with a mockup visualizer
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(final Node[] nodes, final Visualizer visualizer) {
		short[] path = new short[nodes.length];
		for(short i = 0; i < nodes.length; i++){
			path[i] = i;
		}
		visualizer.setPath(path);
		
		boolean didASwap = false;
		while(true){
			loop:
			for(int i = 1; i < path.length; i++){
				for(int k = i+1; k < path.length; k++){
					visualizer.highlight(0, i, k);
					didASwap = maybeSwap(nodes, path, i, k);
					if(didASwap){
						visualizer.sleep();
						break loop;
					}
				}
			}
			if(!didASwap){
				break;
			}
		}
		visualizer.dehighlight();
		return path;
	}
	
	public static boolean maybeSwap(final Node[] nodes, final short[] path, final int i, final int k){
		Node beforeSwap = nodes[path[i-1]];
		Node firstSwap = nodes[path[i]];
		Node lastSwap = nodes[path[k]];
		Node afterSwap = nodes[path[(k+1) % path.length]];
		double sqDistance = beforeSwap.distance(firstSwap) + afterSwap.distance(lastSwap);
		double newSqDistance = beforeSwap.distance(lastSwap) + afterSwap.distance(firstSwap);

		if(newSqDistance < sqDistance){
			swap(path, i, k);
			return true;
		}
		return false;
	}
	
	public static void swap(final short[] path, final int i, final int k){
		reverseSubpath(path, i, k);
	}
	
	private static void reverseSubpath(final short[] path, final int start, int end){
		end += path.length;
		int length = (end - start) % path.length + 1;
		for(int i = 0; i < length/2; i++){
			swapTwoElements(path, start + i, end - i);
		}
	}
	
	private static void swapTwoElements(final short[] array, final int index1, final int index2){
		short tmp = array[index1 % array.length];
		array[index1 % array.length] = array[index2 % array.length];
		array[index2 % array.length] = tmp;
	}
	
	@Override
	public String toString() {
		return "2-Opt";
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer, short[] path) {
		throw new RuntimeException();
	}
}
