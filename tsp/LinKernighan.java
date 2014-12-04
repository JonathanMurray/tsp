package tsp;

import java.util.Arrays;
import java.util.List;



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
	private int num2Swaps = 0;
	private int num3Swaps = 0;
	
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
//		distanceMatrix = Dist.getDistNearestMatrix(nodes);
		visualizer.setPath(path);
		visualizer.sleep();
		short[] newPath = setX1(visualizer, nodes, path);
//		System.out.println(num2Swaps + " 2-swaps");
//		System.out.println(num3Swaps + " 3-swaps");
		return newPath;
	}
	
	private short get(short[] path, int index){
		return path[index % path.length];
	}
	
	private static boolean within(int x, int min, int max, int modulo){
		int diff1 = (modulo + x - min) % modulo;
		int diff2 = (modulo + max - min) % modulo;
		return diff2 >= diff1;
	}
	
	short[] setX1(Visualizer visualizer, Node[] nodes, short[] path){
		println("nodes: " + Arrays.toString(nodes));
//		System.out.println("start path: " + Arrays.toString(path));
//		System.out.println("length: " + Node.lengthOfPath(path, nodes));
		for(int t1Index = 0; t1Index < path.length; t1Index++){
			Edge x1 = new Edge(path, t1Index, t1Index + 1);
//			println("x1: " + x1);
//			visualizer.highlightLoose(0, path[x1.fromIndex], path[x1.toIndex]);
//			visualizer.sleep();
			short[] newPath = setY1(visualizer, nodes, path, x1);
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
	
	short[] setY1(Visualizer visualizer, Node[] nodes, short[] path, Edge x1){
		int numTries = 0;
		
		
//		List<Dist> nearestNodes = distanceMatrix[x1.toIndex];
//		for(Dist link : nearestNodes){
//			if(link.a != x1.toIndex){
//				throw new RuntimeException(link.a + " != " + x1.toIndex);
//			}
//			int t3Index = link.b;
//			if(!within(t3Index, x1.toIndex + 2, x1.fromIndex - 3, path.length)){
//				continue;
//			}
//			Edge y1 = new Edge(path, x1.toIndex, t3Index);
//			
//			boolean xIsBigger = x1.sqDist(nodes) > y1.sqDist(nodes);
//			if(!xIsBigger){
//				break; //They are sorted in an ascending order!
//			}
//			visualizer.highlightLoose(4, path[y1.fromIndex], path[y1.toIndex]);
//			visualizer.sleep();
//			short[] newPath = setX2(visualizer, nodes, path, x1, y1); 
//			visualizer.dehighlight(1);
//			visualizer.sleep();
//			if(newPath != null){
//				visualizer.setPath(newPath);
//				return newPath; //Next step success
//			}
//		}
//		return null;
		
		
		for(int t3Index = (x1.toIndex + 2)%path.length; t3Index != (x1.fromIndex + path.length - 2)%path.length  && numTries < LIMIT; t3Index = (t3Index + 1)%path.length){
			
//			println("y1: " + y1);
//			visualizer.highlightLoose(4, path[y1.fromIndex], path[y1.toIndex]);
//			visualizer.sleep();
			double ySqDist = nodes[path[x1.toIndex]].sqDistance(nodes[path[t3Index]]);
			boolean xIsBigger = x1.sqDist(nodes) > ySqDist;
//			boolean xIsBigger = x1.sqDist(nodes) > y1.sqDist(nodes);
			if(xIsBigger){
				Edge y1 = new Edge(path, x1.toIndex, t3Index);
//				println("found smaller y1");
				short[] newPath = setX2(visualizer, nodes, path, x1, y1); 
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
	
	short[] setX2(Visualizer visualizer, Node[] nodes, short[] path, Edge x1, Edge y1){
//		for(int t4Index = y1.toIndex - 1; t4Index > y1.fromIndex; t4Index --){
			int t4Index = (path.length + y1.toIndex - 1) % path.length;
//			println("finding x2... t4Index == " + t4Index);
			
//			println("x2: " + x2);
//			visualizer.highlightLoose(1, path[x2.fromIndex], path[x2.toIndex]);
//			visualizer.sleep();
			
//			println("________________\n");
//			println("possible y2: " + possibleY2);
			double x2Dist = nodes[path[y1.toIndex]].distance(nodes[path[t4Index]]);
			double y2Dist = nodes[path[t4Index]].distance(nodes[path[x1.fromIndex]]);
			boolean foundGoodTour = x1.dist(nodes) + x2Dist > y1.dist(nodes) + y2Dist;
//			boolean foundGoodTour = x1.dist(nodes) + x2.dist(nodes) > y1.dist(nodes) + possibleY2.dist(nodes);
//			Edge x2 = new Edge(path, y1.toIndex, t4Index);
			if(foundGoodTour){
//				Edge possibleY2 = new Edge(path, t4Index, x1.fromIndex);
//				println("Found good tour");
//				println(x1.dist(nodes) + " + " + x2.dist(nodes) + " > " + y1.dist(nodes) + " + " + possibleY2.dist(nodes));
//				println((x1.dist(nodes) + x2.dist(nodes)) + " > " + (y1.dist(nodes) + possibleY2.dist(nodes)));
				if(findFirstOccurence(x1.toIndex, t4Index /*x2.toIndex*/, path) == x1.toIndex){
					TwoOpt.swap(path, x1.toIndex, t4Index /*x2.toIndex*/);
//					println("A Swap between " + x1.toIndex + " and " + x2.toIndex);
				}else{
					TwoOpt.swap(path, y1.toIndex /*x2.fromIndex*/, x1.fromIndex); //TODO
//					println("B Swap between " + x2.fromIndex + " and " + x1.fromIndex);
				}
				num2Swaps ++;
				return path; //Found a good 2-swap [SUCCESS]
			}else{
				Edge x2 = new Edge(path, y1.toIndex, t4Index);
				short[] newPath = setY2(visualizer, nodes, path, x1, y1, x2);
//				visualizer.dehighlight(5);
//				visualizer.sleep();
				if(newPath != null){
//					visualizer.setPath(newPath);
					return newPath; //Next step success
				}
			}
//		}
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
	
	short[] setY2(Visualizer visualizer, Node[] nodes, short[] path, Edge x1, Edge y1, Edge x2){
		int numTries = 0;
		
//		for(Dist link : distanceMatrix[x2.toIndex]){
//			if(link.a != x2.toIndex){
//				throw new RuntimeException(link.a + " != " + x2.toIndex);
//			}
//			int t5Index = link.b;
//			if(!within(t5Index, x2.fromIndex + 2, x1.fromIndex - 2, path.length)){
//				continue;
//			}
//			Edge y2 = new Edge(path, x2.toIndex, t5Index);
//			println("y2: " + y2);
//			boolean xIsBigger = x2.sqDist(nodes) > y2.sqDist(nodes);
//			if(!xIsBigger){
//				break;
//			}
//			visualizer.highlightLoose(5, path[y2.fromIndex], path[y2.toIndex]);
//			visualizer.sleep();
//			short[] newPath = setX3Y3(visualizer, nodes, path, x1, y1, x2, y2);
//			visualizer.dehighlight(2);
//			visualizer.dehighlight(6);
//			visualizer.sleep();
//			if(newPath != null){
//				visualizer.setPath(newPath);
//				return newPath; //Next step success
//			}
//			
//		}
//		return null;
		
		
		
		
		
		
		
		for(int t5Index = (x2.fromIndex + 2)%path.length; t5Index != (x1.fromIndex + path.length - 1)%path.length && numTries < LIMIT; t5Index = (t5Index + 1)%path.length){
//			Edge y2 = new Edge(path, x2.toIndex, t5Index);
//			println("y2: " + y2);
//			visualizer.highlightLoose(5, path[y2.fromIndex], path[y2.toIndex]);
//			visualizer.sleep();
			double y2SqDist = nodes[path[x2.toIndex]].sqDistance(nodes[path[t5Index]]);
			boolean xIsBigger = x2.sqDist(nodes) > y2SqDist;
//			boolean xIsBigger = x2.sqDist(nodes) > y2.sqDist(nodes);
			if(xIsBigger){
				Edge y2 = new Edge(path, x2.toIndex, t5Index);
				short[] newPath = setX3Y3(visualizer, nodes, path, x1, y1, x2, y2);
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
	
	short[] setX3Y3(Visualizer visualizer, Node[] nodes, short[] path, Edge x1, Edge y1, Edge x2, Edge y2){
//		for(int t6Index = y2.toIndex - 1; t6Index > x2.fromIndex; t6Index --){
			int t6Index = y2.toIndex - 1;
			Edge x3 = new Edge(path, y2.toIndex, t6Index);
			Edge y3 = new Edge(path, t6Index, x1.fromIndex); //Close the tour
//			println("x3: " + x3);
//			println("y3: " + y3);
			visualizer.highlightLoose(2, path[x3.fromIndex], path[x3.toIndex]);
			visualizer.highlightLoose(6, path[y3.fromIndex], path[y3.toIndex]);
			visualizer.sleep();
			double totalXDist = x1.dist(nodes) + x2.dist(nodes) + x3.dist(nodes);
			double totalYDist = y1.dist(nodes) + y2.dist(nodes) + y3.dist(nodes);
			boolean foundGoodTour = totalXDist > totalYDist;
			if(foundGoodTour){
				println("Found good tour 2");
				short[] newPath = improvePathWithSwap(nodes, path, x1, y1, x2, y2, x3, y3);
				visualizer.setPath(newPath);
				num3Swaps ++;
				return newPath;
			}
//		}
		return null;
	}
	
	short[] improvePathWithSwap(Node[] nodes, short[] path, Edge x1, Edge y1, Edge x2, Edge y2, Edge x3, Edge y3){
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
		copySegment(path, newPath, x1.fromIndex, dstInd, 1, false); //copy first
		dstInd += 1;
		int reverseLen = (path.length + y3.fromIndex - y1.toIndex) % path.length + 1;
		copySegment(path, newPath, y1.toIndex, dstInd, reverseLen, true); //Copy reversed
		dstInd += reverseLen;
		int len = (path.length + y2.fromIndex - y1.fromIndex) % path.length + 1;
		copySegment(path, newPath, y1.fromIndex, dstInd, len, false); //Copy 2nd last
		dstInd += len;
		int lastLen = (path.length + x1.fromIndex - y2.toIndex) % path.length + 1;
		copySegment(path, newPath, y2.toIndex, dstInd, lastLen, false); //Copy last
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
	
	class Edge{
		//NOTE:
		//These indices denote the edge's position IN THE PATH, not in the nodes-array
		int fromIndex;
		int toIndex;
		String str;
		double sqDist;
		double dist;
		
		//This object is not valid anymore if path is changed, since it's part of this specific path.
		short[] path;
		
		Edge(short[] path, int fromIndex, int toIndex){
			this.path = path;
			this.fromIndex = (fromIndex + path.length)% path.length;
			this.toIndex = (toIndex + path.length)% path.length;
			
		}
		
		private void setString(){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(path[this.fromIndex]);
			sb.append(",");
			sb.append(path[this.toIndex]);
			sb.append(" (in path: ");
			sb.append(this.fromIndex);
			sb.append(",");
			sb.append(this.toIndex);
			str = sb.toString();
		}
		
		double sqDist(Node[] nodes){
			if(sqDist == 0){
				sqDist = nodes[path[fromIndex]].sqDistance(nodes[path[toIndex]]);
			}
			return sqDist;
		}
		
		double dist(Node[] nodes){
			if(dist == 0){
				double sqDist = sqDist(nodes);
				dist = Math.sqrt(sqDist);
			}
			return dist;
		}
		public String toString(){
//			System.err.println("[" + fromIndex + "," + toIndex + "]"); //TODO
			if(str == null){
				setString();
			}
			return str;
		}
	}
	
	private void println(Object str){
//		System.out.println(str);
	}

}
