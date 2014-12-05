package tsp;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;



public class LinKernighanLimitedDescent implements TSPSolver{

	private final int LIMIT;
	private float[][] distances;
	
	private float x1Dist;
	private float y1Dist;
	private float x2Dist;
	private float y2Dist;
	
	private short[] path;
	private Node[] nodes;
	
	private final int NEIGHBOUR_BUFFER;
	private final float PROB_SKIP_NEIGHBOUR;
	
	private static final Random r = new Random();
	
	public LinKernighanLimitedDescent(int limit, int neighbourBuffer, float probSkipNeighbour) {
		this.LIMIT = limit;
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
	
	//(mod path.length)
	private short mod(int i){
		return (short) ((i + path.length) % path.length);
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
	

	
	private int findFirstOccurence(int a, int b, short[] array){
		for(short x : array){
			if(x == a){
				return a;
			}
			if(x == b){
				return b;
			}
		}
		throw new RuntimeException("Neither " + a + " or " + b + " was found in " + Arrays.toString(array));
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
//		println("new path (after swap): " + Arrays.toString(newPath));
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
		return "LK-LimDesc(" + LIMIT + ", " + NEIGHBOUR_BUFFER + ", " + PROB_SKIP_NEIGHBOUR + ")";
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
