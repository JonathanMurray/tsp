package other;

import java.util.LinkedList;

import tsp.Dist;
import tsp.Node;
import tsp.TSPSolver;
import tsp.Visualizer;
import tsp.VisualizerMockup;

/**
 * Just as good as Naive, but slower........ 
 * 
 * @author matslexell
 *
 */
public class NearestNeighbour implements TSPSolver{

	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		
		
		LinkedList<Dist>[] matrix = Dist.getDistNearestMatrix(nodes);
		
		boolean[] beenThere = new boolean[nodes.length];
		short path[] = new short[nodes.length];
		//Start value
		short node = 0;
		
		short edges = 1;		
	
		path[edges] = node;
		
		while(edges < nodes.length){
			//Add the node, also an edge is added from that node
			
			//Now we have been to that node
			beenThere[node] = true;
			
			//The next node will be the closest, unvisited, node
			while(beenThere[matrix[node].peek().b])
				
				//So if you've already been there, poll it!
				matrix[node].poll();
			
			//Now we know that we have the nearest unvisited neighbour
			
			node = matrix[node].peek().b;
			path[edges] = node;
			edges++;
			
		}
		
		
		return path;
	}
	
	public String toString(){
		return "NearestNeighbour";
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer, short[] path) {
		throw new RuntimeException();
	}

}
