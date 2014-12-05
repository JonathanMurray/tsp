package tsp;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;



public class LinKernighanSteepestDescent implements TSPSolver{

	private final int LIMIT;
	private float[][] distances;
	
	private float x1Dist;
	private float y1Dist;
	private float x2Dist;
	private float y2Dist;
	
	private short[] path;
	private Node[] nodes;
	
	public LinKernighanSteepestDescent(int limit) {
		this.LIMIT = limit;
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
		short[] newPath = setX1();
		return newPath;
	}
	
	//(mod path.length)
	private short mod(int i){
		return (short) ((i + path.length) % path.length);
	}
	
	short[] setX1(){
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
	
	short[] improvePathWithSwap(Node[] nodes, short[] path, short t1Index, short t2Index, short t3Index, short t4Index, short t5Index, short t6Index){
		short[] newPath = new short[path.length];
		int dstInd = 0;
		copySegment(path, newPath, t1Index, dstInd, 1); //copy first
		dstInd += 1;
		int reverseLen = mod(t5Index - t3Index) + 1;
		copySegmentReverse(path, newPath, t3Index, dstInd, reverseLen); //Copy reversed
		dstInd += reverseLen;
		int len = mod(t4Index - t2Index) + 1;
		copySegment(path, newPath, t2Index, dstInd, len); //Copy 2nd last
		dstInd += len;
		int lastLen = mod(t1Index - t5Index) + 1;
		copySegment(path, newPath, t5Index, dstInd, lastLen); //Copy last
		return newPath;
	}

	/**
	 * Copy from src to dst. 
	 * Copied: Elements srcStart, srcStart + 1, ..., srcStart + len - 1.
	 * To: dstStart, dstStart + 1, ..., dstStart + len - 1.
	 * If reverse, they will be put in reversed order, but all indices are the same.
	 * 
	 * Example:
	 *  short[] src = new short[]{0,1,2,3,4,5,6,7,8,9};
	 *	short[] dst = new short[10];
	 *	copySegment(src, dst, 2, 6, 3, true);
	 *  // dst == [0, 0, 0, 0, 0, 0, 4, 3, 2, 0]
	 */
	void copySegmentReverse(short[] src, short[] dst, int srcStart, int dstStart, int len){
		int srcInd = mod(srcStart + len - 1);
		int dstInd = dstStart;
		for(int offset = 0; offset < len; offset++){
			dst[dstInd] = src[srcInd];
			srcInd --;
			if(srcInd < 0){
				srcInd  = src.length - 1;
			}
			dstInd ++;
			if(dstInd >= src.length){
				dstInd = 0;
			}
		}
	}
	
	void copySegment(short[] src, short[] dst, int srcStart, int dstStart, int len){
		int arrayLen = src.length;
		if(srcStart > dstStart){
			if(srcStart + len <= arrayLen){
				System.arraycopy(src, srcStart, dst, dstStart, len); //Normal copy
			}else{
				int lenUntilEnd = arrayLen - srcStart;
				System.arraycopy(src, srcStart, dst, dstStart, lenUntilEnd); //First copy until end
				int remaining = len - lenUntilEnd;
				copySegment(src, dst, 0, dstStart + lenUntilEnd, remaining); //Then copy from beginning
			}
		}else{
			if(dstStart + len <= arrayLen){
				System.arraycopy(src, srcStart, dst, dstStart, len); //Normal copy
			}else{
				int lenUntilEnd = arrayLen - dstStart;
				System.arraycopy(src, srcStart, dst, dstStart, lenUntilEnd); //First copy until end
				int remaining = len - lenUntilEnd;
				copySegment(src, dst, mod(srcStart + lenUntilEnd), 0, remaining); //Then copy from beginning
			}
		}
	}

	short[] generateStartPath(int numNodes){
		short[] path = new short[numNodes];
		for(short i = 0; i < numNodes; i++){
			path[i] = i;
		}
		return path;
	}
	
	public String toString(){
		return "Lin-Kernighan-SD(" + LIMIT + ")";
	}

	/**
	 * NOTE: You have to call like dist(path[i], path[j]), NOT dist(i, j).
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	private float dist(int fromIndex, int toIndex){
		return distances[fromIndex][toIndex];
	}
}
