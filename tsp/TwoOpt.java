package tsp;




public class TwoOpt implements TSPSolver{

	@Override
	public short[] solveTSP(final Node[] nodes) {
		//Call solveTSP with a mockup visualizer
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(final Node[] nodes, final Visualizer visualizer) {
		//distances doesn't seem to help //TODO
		float[][] distances = DistanceMapping.getDistances(nodes);
		short[] path = new short[nodes.length];
		for(short i = 0; i < nodes.length; i++){
			path[i] = i;
		}
		visualizer.setPath(path);
		
		int numSwaps = 0;
		boolean didASwap = false;
//		System.out.println(Arrays.toString(path));
		while(true){
			loop:
			for(int i = 1; i < path.length; i++){
				for(int k = i+1; k < path.length; k++){
					visualizer.highlight(i, k);
					visualizer.repaint();
					didASwap = maybeSwap(distances, nodes, path, i, k);
					visualizer.repaint();
					if(didASwap){
						visualizer.sleep();
						break loop;
					}
				}
			}
			if(didASwap){
				numSwaps ++;
			}else{
				break;
			}
		}
		System.out.println("Did " + numSwaps + " swaps!");
		visualizer.dehighlight();
		return path;
	}
	
	private boolean maybeSwap(float[][] distances, final Node[] nodes, final short[] path, final int i, final int k){
		int before = path[i-1];
		int first = path[i];
		int last = path[k];
		int after = path[(k+1)%path.length];
		Node beforeSwap = nodes[path[i-1]];
		Node firstSwap = nodes[path[i]];
		Node lastSwap = nodes[path[k]];
		Node afterSwap = nodes[path[(k+1) % path.length]];
		double sqDistance = beforeSwap.sqDistance(firstSwap) + afterSwap.sqDistance(lastSwap);
		double newSqDistance = beforeSwap.sqDistance(lastSwap) + afterSwap.sqDistance(firstSwap);

//		double sqDistance = distances[before][first] + distances[after][last];
//		double newSqDistance = distances[before][last] + distances[after][first];

		if(newSqDistance < sqDistance){
			reverseSubpath(path, i, k);
			return true;
		}
		return false;
	}
	
	private void reverseSubpath(final short[] path, final int start, final int end){
		int length = end - start + 1;
		for(int i = 0; i < length/2; i++){
			swapTwoElements(path, start + i, end - i);
		}
	}
	
	private void swapTwoElements(final short[] array, final int index1, final int index2){
		short tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;
	}
	
	@Override
	public String toString() {
		return "2-Opt";
	}
}
