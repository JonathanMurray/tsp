package tsp;

import java.util.Random;
import java.util.TreeMap;



public class LinKernighanLimitedDescent extends LinKernighan{

	private final int NEIGHBOUR_BUFFER;
	private final float PROB_SKIP_NEIGHBOUR;
	
	private static final Random r = new Random();
	
	public LinKernighanLimitedDescent(int limit, int neighbourBuffer, float probSkipNeighbour) {
		super(limit);
		this.NEIGHBOUR_BUFFER = neighbourBuffer;
		this.PROB_SKIP_NEIGHBOUR = probSkipNeighbour;
	}


	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}
	
	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		short[] path = generateStartPath(nodes.length);
		return solveTSP(nodes, visualizer, path);
	}
	
	public short[] solveTSP(Node[] nodes, Visualizer visualizer, short[] path){
		distances = DistanceMapping.getDistances(nodes);
		this.nodes = nodes;
		this.path = path;
		short[] newPath = solve();
		return newPath;
	}
	
	private class NeighbourState{
		private short swapType;
		private short t1Index;
		private short t3Index;
		private short t5Index;
		public NeighbourState(short swapType, short t1Index, short t3Index){
			this.swapType = swapType;
			this.t1Index = t1Index;
			this.t3Index = t3Index;
		}
		
		public NeighbourState(short swapType, short t1Index, short t3Index, short t5Index){
			this.swapType = swapType;
			this.t1Index = t1Index;
			this.t3Index = t3Index;
			this.t5Index = t5Index;
		}
	}
	
	short[] solve(){
		
		TreeMap<Float, NeighbourState> bestNeighbours = new TreeMap<Float, NeighbourState>();
		
		for(short t1Index = 0; t1Index < path.length; t1Index++){ //Loop t1 to find x1
			short t2Index = mod(t1Index + 1);
			
			short pathT2Index = path[t2Index];
			x1Dist = dist(pathT2Index, path[t1Index]);
			short t3StopBefore = mod(t1Index - 2);
			
			bestNeighbours.clear();
			
			inT1:
			for(short t3Index = mod(t2Index + 2); t3Index != t3StopBefore; t3Index = mod(t3Index + 1)){ //Loop t3 to find y1
				y1Dist = dist(pathT2Index,path[t3Index]);
				if(x1Dist > y1Dist){ //Found good y1
					short t4Index = mod(t3Index - 1);
					x2Dist = dist(path[t4Index],path[t3Index]);
					float possibleY2Dist = dist(path[t4Index], path[t1Index]);
					if(x1Dist + x2Dist > (y1Dist + possibleY2Dist)){ //Perform a 2-swap 
						if(findFirstOccurence(t2Index, t4Index, path) == t2Index){
							bestNeighbours.put(x1Dist + x2Dist - (y1Dist + possibleY2Dist), new NeighbourState((short)1, t1Index, t3Index));
						}else{
							bestNeighbours.put(x1Dist + x2Dist - (y1Dist + possibleY2Dist), new NeighbourState((short)2, t1Index, t3Index));
						}
						if(bestNeighbours.size() == NEIGHBOUR_BUFFER){
							break inT1; //Found an improvement => go to outer loop
						}
					}else{ // No good 2-swap, go to next step
						short t5StopBefore = mod(t1Index - 1);
						short pathT4Index = path[t4Index];
						for(short t5Index = mod(t3Index + 2); t5Index != t5StopBefore; t5Index = mod(t5Index + 1)){ //Loop t5 to find y2
							y2Dist = dist(pathT4Index,path[t5Index]);
							if(x1Dist + x2Dist > y1Dist + y2Dist){ //Found good y2
								short t6Index = mod(t5Index - 1);
								float x3Dist = dist(path[t6Index], path[t5Index]);
								float y3Dist = dist(path[t6Index], path[t1Index]);
								if(x1Dist + x2Dist + x3Dist > y1Dist + y2Dist + y3Dist){ //Perform a 3-swap
									bestNeighbours.put(x1Dist + x2Dist + x3Dist - y1Dist + y2Dist + y3Dist, new NeighbourState((short)3, t1Index, t3Index, t5Index));
									if(bestNeighbours.size() == NEIGHBOUR_BUFFER){
										break inT1; //Found an improvement => go to outer loop
									}
								}
							}
						}
						//Found no good y2
					}
				}
			}
			//Found no y1 that worked out
			
			int i = 0;
			for(NeighbourState ns : bestNeighbours.descendingMap().values()){
				if(r.nextFloat() < PROB_SKIP_NEIGHBOUR){
					i ++;
					if(i < bestNeighbours.size()){ //Haven't skipped all yet
						continue;
					}
				}
				switch(ns.swapType){
				case 1:
					short nsT2Index = mod(ns.t1Index + 1);
					short nsT4Index = mod(ns.t3Index - 1);
					TwoOpt.swap(path, nsT2Index, nsT4Index);
					break;
				case 2:
					TwoOpt.swap(path, ns.t3Index, ns.t1Index);
					break;
				case 3:
					nsT2Index = mod(ns.t1Index + 1);
					nsT4Index = mod(ns.t3Index - 1);
					short nsT6Index = mod(ns.t5Index - 1);
					path = improvePathWithSwap(nodes, path, ns.t1Index, nsT2Index, ns.t3Index, nsT4Index, ns.t5Index, nsT6Index);
					break;
				}
				t1Index = -1; //Continue improving the path
				break;
			}
		}
		//Tried all t1 without finding a new improvement
		return path;
	}
	
	public String toString(){
		return "LK-LimDesc(" + LIMIT + ", " + NEIGHBOUR_BUFFER + ", " + PROB_SKIP_NEIGHBOUR + ")";
	}
}
