package tsp;

import java.util.Arrays;



public class LinKernighan implements TSPSolver{
	
//	rand-1000nodes.txt:
//		----------------
//		Naive: [time: 20, length: 29622,00]
//		MST: [time: 344, length: 31580,00]
//		Did 7632 swaps!
//		2-Opt: [time: 66816, length: 24624,00]
//		Lin-Kernighan(200): [time: 6565, length: 33112,00]
//		Lin-Kernighan(300): [time: 8359, length: 25743,00]
//		Lin-Kernighan(400): [time: 8670, length: 26196,00]
//		Lin-Kernighan(500): [time: 8768, length: 25484,00]
//		Lin-Kernighan(600): [time: 11363, length: 25961,00]
//		Lin-Kernighan(700): [time: 13983, length: 26100,00]
	
	//with sorted distances:
//	3133 2-swaps
//	222 3-swaps
	
	private final int LIMIT;
//	private List<Dist>[] distanceMatrix;
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
	
//	private short get(short[] path, int index){
//		return path[index % path.length];
//	}
//	
//	private static boolean within(int x, int min, int max, int modulo){
//		int diff1 = (modulo + x - min) % modulo;
//		int diff2 = (modulo + max - min) % modulo;
//		return diff2 >= diff1;
//	}
	
	short[] setX1(){
		println("nodes: " + Arrays.toString(nodes));
//		System.out.println("start path: " + Arrays.toString(path));
//		System.out.println("length: " + Node.lengthOfPath(path, nodes));
		for(short t1Index = 0; t1Index < path.length; t1Index++){
//			Edge x1 = new Edge(path, t1Index, t1Index + 1);
//			println("x1: " + x1);
//			visualizer.highlightLoose(0, path[x1.fromIndex], path[x1.toIndex]);
//			visualizer.sleep();
			short[] newPath = setY1(t1Index, (short) ((t1Index + 1) % path.length));
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
//		for(int i = 0; i < 10000; i++){
//			visualizer.sleep();
//		}
		return path;
	}
	
	short[] setY1(short t1Index, short t2Index){
		int numTries = 0;
	
//		x1Dist = distances[path[t2Index]][path[t1Index]];
		short pathT2Index = path[t2Index];
		x1Dist = dist(pathT2Index, path[t1Index]);
		
		short stopBefore = (short) ((t1Index + path.length - 2)%path.length);
		for(short t3Index = (short) ((t2Index + 2)%path.length); t3Index != stopBefore && numTries < LIMIT; t3Index = (short) ((t3Index + 1)%path.length)){
			
//			println("y1: " + y1);
//			visualizer.highlightLoose(4, path[y1.fromIndex], path[y1.toIndex]);
//			visualizer.sleep();
//			double ySqDist = nodes[path[t2Index]].sqDistance(nodes[path[t3Index]]);
//			double xSqDist = nodes[path[t1Index]].sqDistance(nodes[path[t2Index]]);
//			boolean xIsBigger = xSqDist > ySqDist;
//			y1Dist = distances[path[t2Index]][path[t3Index]];
			y1Dist = dist(pathT2Index,path[t3Index]);
			boolean xIsBigger = x1Dist > y1Dist;
//			boolean xIsBigger = x1.sqDist(nodes) > y1.sqDist(nodes);
			if(xIsBigger){
//				Edge y1 = new Edge(path, t2Index, t3Index);
//				println("found smaller y1");
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
		short t4Index = (short) ((path.length + t3Index - 1) % path.length);
//			println("finding x2... t4Index == " + t4Index);
		
//			println("x2: " + x2);
//			visualizer.highlightLoose(1, path[x2.fromIndex], path[x2.toIndex]);
//			visualizer.sleep();
		
//			println("________________\n");
//			println("possible y2: " + possibleY2);
//			double x2Dist = nodes[path[t3Index]].distance(nodes[path[t4Index]]);
//			double y2Dist = nodes[path[t4Index]].distance(nodes[path[t1Index]]);
//			double x1Dist = nodes[path[t1Index]].distance(nodes[path[t2Index]]);
//			double y1Dist = nodes[path[t2Index]].distance(nodes[path[t3Index]]);
//			x2Dist = distances[path[t4Index]][path[t3Index]];
		x2Dist = dist(path[t4Index],path[t3Index]);
//			float possibleY2Dist = distances[path[t4Index]][path[t1Index]];
		float possibleY2Dist = dist(path[t4Index], path[t1Index]);
		boolean foundGoodTour = x1Dist + x2Dist > y1Dist + possibleY2Dist;
//			float x1Dist = distances[path[t1Index]][path[t2Index]];
//			float y1Dist = distances[path[t2Index]][path[t3Index]];
//			boolean foundGoodTour = x1Dist + x2Dist > y1Dist + y2Dist;
//			boolean foundGoodTour = x1.dist(nodes) + x2.dist(nodes) > y1.dist(nodes) + possibleY2.dist(nodes);
//			Edge x2 = new Edge(path, y1.toIndex, t4Index);
		if(foundGoodTour){
//				Edge possibleY2 = new Edge(path, t4Index, x1.fromIndex);
//				println("Found good tour");
//				println(x1.dist(nodes) + " + " + x2.dist(nodes) + " > " + y1.dist(nodes) + " + " + possibleY2.dist(nodes));
//				println((x1.dist(nodes) + x2.dist(nodes)) + " > " + (y1.dist(nodes) + possibleY2.dist(nodes)));
			if(findFirstOccurence(t2Index, t4Index /*x2.toIndex*/, path) == t2Index){
				TwoOpt.swap(path, t2Index, t4Index /*x2.toIndex*/);
//					println("A Swap between " + x1.toIndex + " and " + x2.toIndex);
			}else{
				TwoOpt.swap(path, t3Index /*x2.fromIndex*/, t1Index); //TODO
//					println("B Swap between " + x2.fromIndex + " and " + x1.fromIndex);
			}
			num2Swaps ++;
			return path; //Found a good 2-swap [SUCCESS]
		}else{
//				Edge x2 = new Edge(path, y1.toIndex, t4Index);
			short[] newPath = setY2(t1Index, t2Index, t3Index, t4Index);
//				visualizer.dehighlight(5);
//				visualizer.sleep();
			if(newPath != null){
//					visualizer.setPath(newPath);
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

		short stopBefore = (short) ((t1Index + path.length - 1)%path.length);
		short pathT4Index = path[t4Index];
		for(short t5Index = (short) ((t3Index + 2)%path.length); t5Index != stopBefore && numTries < LIMIT; t5Index = (short) ((t5Index + 1)%path.length)){
//			Edge y2 = new Edge(path, x2.toIndex, t5Index);
//			println("y2: " + y2);
//			visualizer.highlightLoose(5, path[y2.fromIndex], path[y2.toIndex]);
//			visualizer.sleep();
//			double y2SqDist = nodes[path[t4Index]].sqDistance(nodes[path[t5Index]]);
//			double x2SqDist = nodes[path[t3Index]].sqDistance(nodes[path[t4Index]]);
//			boolean xIsBigger = x2SqDist > y2SqDist;
//			y2Dist = distances[path[t4Index]][path[t5Index]];
			y2Dist = dist(pathT4Index,path[t5Index]);
			boolean xIsBigger = x1Dist + x2Dist > y1Dist + y2Dist;
//			boolean xIsBigger = x2.sqDist(nodes) > y2.sqDist(nodes);
			if(xIsBigger){
//				Edge y2 = new Edge(path, t4Index, t5Index);
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
		short t6Index = (short) ((path.length + t5Index - 1)%path.length);
//			Edge x3 = new Edge(path, y2.toIndex, t6Index);
//			Edge y3 = new Edge(path, t6Index, x1.fromIndex); //Close the tour
//			println("x3: " + x3);
//			println("y3: " + y3);
//			visualizer.highlightLoose(2, path[x3.fromIndex], path[x3.toIndex]);
//			visualizer.highlightLoose(6, path[y3.fromIndex], path[y3.toIndex]);
//			visualizer.sleep();
//			double totalXDist = nodes[path[t1Index]].distance(nodes[path[t2Index]]) +
//					nodes[path[t3Index]].distance(nodes[path[t4Index]]) +
//					nodes[path[t5Index]].distance(nodes[path[t6Index]]);
	
//			float totalXDist = distances[path[t1Index]][path[t2Index]] + 
//					distances[path[t3Index]][path[t4Index]] +
//					distances[path[t5Index]][path[t6Index]];
	
//			double totalYDist = nodes[path[t2Index]].distance(nodes[path[t3Index]]) +
//					nodes[path[t4Index]].distance(nodes[path[t5Index]]) +
//					nodes[path[t6Index]].distance(nodes[path[t1Index]]);
	
//			float totalYDist = distances[path[t2Index]][path[t3Index]] +
//					distances[path[t4Index]][path[t5Index]] +
//					distances[path[t6Index]][path[t1Index]];
			
//			double totalXDist = x1.dist(nodes) + x2.dist(nodes) + x3.dist(nodes);
//			double totalYDist = y1.dist(nodes) + y2.dist(nodes) + y3.dist(nodes);
//			boolean foundGoodTour = totalXDist > totalYDist;
		
//		float x3Dist = distances[path[t6Index]][path[t5Index]];
//		float y3Dist =  distances[path[t6Index]][path[t1Index]];
		
		float x3Dist = dist(path[t6Index], path[t5Index]);
		float y3Dist = dist(path[t6Index], path[t1Index]);
		
		boolean foundGoodTour = x1Dist + x2Dist + x3Dist > y1Dist + y2Dist + y3Dist;
		
		if(foundGoodTour){
//				println("Found good tour 2");
			short[] newPath = improvePathWithSwap(nodes, path, t1Index, t2Index, t3Index, t4Index, t5Index, t6Index);
//				visualizer.setPath(newPath);
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
		copySegment(path, newPath, t1Index, dstInd, 1, false); //copy first
		dstInd += 1;
		int reverseLen = (path.length + t5Index - t3Index) % path.length + 1;
		copySegment(path, newPath, t3Index, dstInd, reverseLen, true); //Copy reversed
		dstInd += reverseLen;
		int len = (path.length + t4Index - t2Index) % path.length + 1;
		copySegment(path, newPath, t2Index, dstInd, len, false); //Copy 2nd last
		dstInd += len;
		int lastLen = (path.length + t1Index - t5Index) % path.length + 1;
		copySegment(path, newPath, t5Index, dstInd, lastLen, false); //Copy last
		println("new path (after swap): " + Arrays.toString(newPath));
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
	void copySegment(short[] src, short[] dst, int srcStart, int dstStart, int len, boolean reverseTheSegment){
//		println("-----------");
//		println("src: " + Arrays.toString(src));
//		println("dst: " + Arrays.toString(dst));
//		println("srcstart: " + srcStart);
//		println("len: " + len);
//		println("copying " + len + " elements from src-" + srcStart + " to dst-" + dstStart);
		for(int offset = 0; offset < len; offset++){
			short val;
//			println("offset: " + offset);
			if(reverseTheSegment){
				val = src[(srcStart + len - 1 - offset + src.length) % src.length];
			}else{
				val = src[(srcStart + offset + src.length) % src.length];
			}
			dst[(dstStart + offset + dst.length) % dst.length] = val;
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
