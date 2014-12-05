package tsp;

import java.util.Map.Entry;
import java.util.TreeMap;



public class LinKernighanSteepestDescent extends LinKernighan{

	public LinKernighanSteepestDescent(int limit) {
		super(limit);
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
		visualizer.setPath(path);
		visualizer.sleep();
		this.nodes = nodes;
		this.path = path;
		short[] newPath = solve();
		return newPath;
	}
	
	short[] solve(){
		TreeMap<Double, NeighbourState> bestNeighbourStates = new TreeMap<Double, NeighbourState>();
		int limit = 150;
		while(true){
			bestNeighbourStates.clear();
			for(short t1Index = 0; t1Index < path.length; t1Index++){
				short t2Index = mod(t1Index + 1);
				short pathT2Index = path[t2Index];
				x1Dist = dist(pathT2Index, path[t1Index]);
				short t3StopBefore = mod(t1Index - 2);
				int i2 = 0;
				for(short t3Index = mod(t2Index + 2); t3Index != t3StopBefore && i2 < limit; t3Index = mod(t3Index + 1)){
					y1Dist = dist(pathT2Index,path[t3Index]);
					if(x1Dist < y1Dist){
						i2++;
						continue;
					}
					short t4Index = mod(t3Index - 1);
					x2Dist = dist(path[t4Index],path[t3Index]);
					float possibleY2Dist = dist(path[t4Index], path[t1Index]);
					if(x1Dist + x2Dist < y1Dist + possibleY2Dist){
						i2++;
						continue;
					}
					
					short pathT4Index = path[t4Index];
					short t5StopBefore = mod(t1Index - 1);
					int i3 = 0;
					for(short t5Index = mod(t3Index + 2); t5Index != t5StopBefore && i3 < limit; t5Index = mod(t5Index + 1)){ 
						y2Dist = dist(pathT4Index,path[t5Index]);
						short t6Index = mod(t5Index - 1);
						float x3Dist = dist(path[t6Index], path[t5Index]);
						float y3Dist = dist(path[t6Index], path[t1Index]);
						boolean foundGoodTour = x1Dist + x2Dist + x3Dist > y1Dist + y2Dist + y3Dist;
						if(foundGoodTour){
							bestNeighbourStates.put(new Double(x1Dist + x2Dist + x3Dist - y1Dist - y2Dist - y3Dist), new NeighbourState(t1Index, t3Index, t5Index));
						}
						i3++;
					}
					i2++;
				}
			}
			if(bestNeighbourStates.isEmpty()){ //no more improvements
				limit *= 2;
				if(limit > 1000){
					break;
				}
			}
			for(Entry<Double, NeighbourState> e : bestNeighbourStates.descendingMap().entrySet()){
				NeighbourState state = e.getValue();
				short t1Index = state.t1Index;
				short t2Index = mod(t1Index + 1);
				short t3Index = state.t3Index;
				short t4Index = mod(t3Index - 1);
				short t5Index = state.t5Index;
				short t6Index = mod(t5Index - 1);
				short[] newPath = improvePathWithSwap(nodes, path, t1Index, t2Index, t3Index, t4Index, t5Index, t6Index);
				path = newPath;
				break;
			}
		}
		
		return path;
	}
	
	private class NeighbourState{
		short t1Index;
		short t3Index;
		short t5Index;
		public NeighbourState(short t1Index, short t3Index, short t5Index) {
			this.t1Index = t1Index;
			this.t3Index = t3Index;
			this.t5Index = t5Index;
		}
	}

	public String toString(){
		return "Lin-Kernighan-SD(" + LIMIT + ")";
	}
}
