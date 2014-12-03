package tsp;

import java.util.HashSet;

public class LinKernighan implements TSPSolver{
	
	private int t1;
	private Node[] nodes;
	private short[] path;
	final int LIMIT = 5;
	private Edge x1;
	private Edge y1;
	private Edge x2;
	private Edge y2;

	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		path = generateStartPath(nodes.length);
		this.nodes = nodes;
		
		
		int lastTriedT1 = -1;
		HashSet<Edge> triedY1 = new HashSet<Edge>();
		HashSet<Edge> triedX1 = new HashSet<Edge>();
		
		while(true){
			if(lastTriedT1 == nodes.length -1){
				break; //Tried all t1
			}
			t1 = lastTriedT1 + 1;
			lastTriedT1 = t1;
			choseT1();
		}

		return null; //TODO
	}
	
	private void choseT1(){
		for(int t2 : new int[]{(t1-1)%nodes.length, (t1+1)%nodes.length}){
			Edge x1 = new Edge(path[t1], path[t2]);
			choseX1();
		}
	}
		
	private void choseX1(){
//		double x1SqDist = nodes[t1].sqDistance(nodes[path[t2]]);
//		boolean positiveGain = false;
//		for(int t3Index = t2Index-LIMIT; t3Index < t2Index + LIMIT; t3Index ++){
//			if(Math.abs(t3Index - t2Index) <= 1){
//				continue;
//			}
//			int t3 = path[t3Index];
//			double y1SqDist = nodes[t2].sqDistance(nodes[t3]);
//			positiveGain = x1SqDist > y1SqDist;
//			if(positiveGain){
//				y1 = new Edge(t2, t3);
//				break;
//			}
//		}
//		if(positiveGain){
//			i ++;
//		}else{
//			break t2; //try new t1 if possible	
//		}
	}
	
	private short[] generateStartPath(int numNodes){
		short[] path = new short[numNodes];
		for(short i = 0; i < numNodes; i++){
			path[i] = i;
		}
		return path;
	}
	
	public String toString(){
		return "Lin-Kernighan";
	}
	
	private class Edge{
		int fromIndex;
		int toIndex;
		Edge(int fromIndex, int toIndex){
			this.fromIndex = fromIndex;
			this.toIndex = toIndex;
		}
		
		double sqDist(Node[] nodes){
			return nodes[fromIndex].sqDistance(nodes[toIndex]);
		}
	}

}
