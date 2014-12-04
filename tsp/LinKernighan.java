package tsp;

import java.util.Arrays;



public class LinKernighan implements TSPSolver{

	private final int LIMIT;
	private float[][] distances;
	private int num2Swaps = 0;
	private int num3Swaps = 0;
	
	private float x1Dist;
	private float y1Dist;
	private float x2Dist;
	private float y2Dist;
	
	private short[] path;
	private Node[] nodes;
	private Visualizer visualizer;
	
	public LinKernighan(int limit) {
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
		this.visualizer = visualizer;
		this.path = path;
		short[] newPath = setX1();
//		System.out.println(num2Swaps + " 2-swaps");
//		System.out.println(num3Swaps + " 3-swaps");
		return newPath;
	}
	
	//(mod path.length)
	private short mod(int i){
		return (short) ((i + path.length) % path.length);
	}
	
	short[] setX1(){
		println("nodes: " + Arrays.toString(nodes));
//		System.out.println("start path: " + Arrays.toString(path));
//		System.out.println("length: " + Node.lengthOfPath(path, nodes));
		for(short t1Index = 0; t1Index < path.length; t1Index++){
//			visualizer.highlightLoose(0, path[x1.fromIndex], path[x1.toIndex]);
//			visualizer.sleep();
			short[] newPath = setY1(t1Index, mod(t1Index + 1));
//			visualizer.dehighlight(4);
			if(newPath != null){
//				println("\nFound new path: " + Arrays.toString(newPath) + "\n");
				path = newPath; //Next step success, but we'll continue improving the path
//				visualizer.setPath(path); //necessary I think, path is just a pointer.
//				visualizer.sleep();
				t1Index = -1; //so loop is restarted with t1 = 0
			}
		}
		println("final path: " + Arrays.toString(path));
		visualizer.dehighlight();
		return path;
	}
	
	short[] setY1(short t1Index, short t2Index){
		int numTries = 0;
		short pathT2Index = path[t2Index];
		x1Dist = dist(pathT2Index, path[t1Index]);
		
		short stopBefore = mod(t1Index - 2);
		for(short t3Index = mod(t2Index + 2); t3Index != stopBefore && numTries < LIMIT; t3Index = mod(t3Index + 1)){
//			visualizer.highlightLoose(4, path[y1.fromIndex], path[y1.toIndex]);
//			visualizer.sleep();
			y1Dist = dist(pathT2Index,path[t3Index]);
			boolean xIsBigger = x1Dist > y1Dist;
			if(xIsBigger){
				short[] newPath = setX2(t1Index, t2Index, t3Index); 
//				visualizer.dehighlight(1);
//				visualizer.sleep();
				if(newPath != null){
//					visualizer.setPath(newPath);
					return newPath; //Next step success
				}
			}
			numTries ++;
		}
		return null; //This step failed
	}
	
	short[] setX2(short t1Index, short t2Index, short t3Index){
		short t4Index = mod(t3Index - 1);
//		visualizer.highlightLoose(1, path[x2.fromIndex], path[x2.toIndex]);
//		visualizer.sleep();
		x2Dist = dist(path[t4Index],path[t3Index]);
		float possibleY2Dist = dist(path[t4Index], path[t1Index]);
		boolean foundGoodTour = x1Dist + x2Dist > y1Dist + possibleY2Dist;
		if(foundGoodTour){
			if(findFirstOccurence(t2Index, t4Index, path) == t2Index){
				TwoOpt.swap(path, t2Index, t4Index);
			}else{
				TwoOpt.swap(path, t3Index, t1Index); //TODO
			}
			num2Swaps ++;
			return path; //Found a good 2-swap [SUCCESS]
		}else{
			short[] newPath = setY2(t1Index, t2Index, t3Index, t4Index);
//			visualizer.dehighlight(5);
//			visualizer.sleep();
			if(newPath != null){
//				visualizer.setPath(newPath);
				return newPath; //Next step success
			}
		}
		return null; //This step failed
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
	
	short[] setY2(short t1Index, short t2Index, short t3Index, short t4Index){
		int numTries = 0;

		short stopBefore = mod(t1Index - 1);
		short pathT4Index = path[t4Index];
		for(short t5Index = mod(t3Index + 2); t5Index != stopBefore && numTries < LIMIT; t5Index = mod(t5Index + 1)){
//			visualizer.highlightLoose(5, path[y2.fromIndex], path[y2.toIndex]);
//			visualizer.sleep();
			y2Dist = dist(pathT4Index,path[t5Index]);
			boolean xIsBigger = x1Dist + x2Dist > y1Dist + y2Dist;
			if(xIsBigger){
				short[] newPath = setX3Y3(t1Index, t2Index, t3Index, t4Index, t5Index);
//				visualizer.dehighlight(2);
//				visualizer.dehighlight(6);
//				visualizer.sleep();
				if(newPath != null){
//					visualizer.setPath(newPath);
					return newPath; //Next step success
				}
			}
			numTries ++;
		}
		return null; //This step failed
	}
	
	short[] setX3Y3(short t1Index, short t2Index, short t3Index, short t4Index, short t5Index){
		short t6Index = mod(t5Index - 1);
//		visualizer.highlightLoose(2, path[x3.fromIndex], path[x3.toIndex]);
//		visualizer.highlightLoose(6, path[y3.fromIndex], path[y3.toIndex]);
//		visualizer.sleep();
		float x3Dist = dist(path[t6Index], path[t5Index]);
		float y3Dist = dist(path[t6Index], path[t1Index]);
		boolean foundGoodTour = x1Dist + x2Dist + x3Dist > y1Dist + y2Dist + y3Dist;
		if(foundGoodTour){
			short[] newPath = improvePathWithSwap(nodes, path, t1Index, t2Index, t3Index, t4Index, t5Index, t6Index);
//			visualizer.setPath(newPath);
			num3Swaps ++;
			return newPath;
		}
		return null;
	}
	
	short[] improvePathWithSwap(Node[] nodes, short[] path, short t1Index, short t2Index, short t3Index, short t4Index, short t5Index, short t6Index){
//		println("improving with swap:");
//		println("x1: " + x1);
//		println("y1: " + y1);
//		println("x2: " + x2);
//		println("y2: " + y2);
//		println("x3: " + x3);
//		println("y3: " + y3);
//		println("path: " + Arrays.toString(path));
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
//		println("-----------");
//		println("src: " + Arrays.toString(src));
//		println("dst: " + Arrays.toString(dst));
//		println("srcstart: " + srcStart);
//		println("len: " + len);
//		println("copying " + len + " elements from src-" + srcStart + " to dst-" + dstStart);
		
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
//		println("dst: " + Arrays.toString(dst));
	}

	short[] generateStartPath(int numNodes){
		short[] path = new short[numNodes];
		for(short i = 0; i < numNodes; i++){
			path[i] = i;
		}
		return path;
	}
	
	public String toString(){
		return "Lin-Kernighan(" + LIMIT + ")";
	}
	
	private void println(Object str){
//		System.out.println(str);
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
