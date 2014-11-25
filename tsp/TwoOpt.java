package tsp;



public class TwoOpt implements TSPSolver{

	@Override
	public short[] solveTSP(Node[] nodes, Interval coordInterval) {
		//Call solveTSP with a mockup visualizer
		return solveTSP(nodes, coordInterval, Visualizer.getMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Interval coordInterval, Visualizer visualizer) {
		short[] path = new short[nodes.length];
		for(short i = 0; i < nodes.length; i++){
			path[i] = i;
		}
		visualizer.setPath(path);
		
		boolean didASwap = false;
//		System.out.println(Arrays.toString(path));
		while(true){
			loop:
			for(int i = 1; i < path.length; i++){
				for(int k = i+1; k < path.length; k++){
					visualizer.highlight(i, k);
					visualizer.repaint();
					didASwap = maybeSwap(nodes, path, i, k);
					visualizer.repaint();
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
	
	private boolean maybeSwap(Node[] nodes, short[] path, int i, int k){
		Node beforeSwap = nodes[path[i-1]];
		Node firstSwap = nodes[path[i]];
		Node lastSwap = nodes[path[k]];
		Node afterSwap = nodes[path[(k+1) % path.length]];
		double sqDistance = beforeSwap.sqDistance(firstSwap) + afterSwap.sqDistance(lastSwap);
		double newSqDistance = beforeSwap.sqDistance(lastSwap) + afterSwap.sqDistance(firstSwap);
		if(newSqDistance < sqDistance){
			reverseSubpath(path, i, k);
			return true;
		}
		return false;
	}
	
	private void reverseSubpath(short[] path, int start, int end){
		int length = end - start + 1;
		for(int i = 0; i < length/2; i++){
			swapTwoElements(path, start + i, end - i);
		}
	}
	
	private void swapTwoElements(short[] array, int index1, int index2){
		short tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;
	}
}
