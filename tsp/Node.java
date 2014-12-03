package tsp;

public class Node {
	private double x;
	private double y;

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double sqDistance(Node other) {
		return (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y);
	}

	public double distance(Node other) {
		return Math.sqrt(sqDistance(other));
	}

	public static double lengthOfPath(short[] path, Node[] nodes) {

		/**
		 * The commented code produces the wrong result. Reference is that
		 * g1.txt according to kattis will produce:
		 * 
		 * path: 0 8 5 4 3 9 6 2 1 7
		 * 
		 * length: 323
		 * 
		 * THIS, however, produces: 283 (or 381, by connecting the last two
		 * nodes)
		 */
		// double length = 0;
		// Node prevNode = nodes[nodes.length - 1];
		// for(short nodeIndex : path){
		// Node node = nodes[nodeIndex];
		// length += node.distance(prevNode);
		// prevNode = node;
		// }
		// return length;

		/**
		 * This code produces the correct result for g1, and should be more
		 * trustworthy
		 */
		
		double length = 0;
		for (int i = 0; i < path.length - 1; i++) {
			//According to instructions on kattis each distance should be rounded
			length += Math.round(nodes[path[i]].distance(nodes[path[i+1]]));
		}
		
		Node first = nodes[path[0]];
		Node last = nodes[path[path.length-1]];
		
		length += Math.round(first.distance(last));
		return length;

	}

	public String toString() {
//		return doubleStr(x) + " " + doubleStr(y);
		return "( " + doubleStr(x) + " ; " + doubleStr(y) + " )";
	}

	private String doubleStr(double x) {
		String s = String.format("%.1f", x);
		if (s.charAt(s.length() - 1) == '0') {
			s = s.substring(0, s.length() - 2);
		}
		return s;
	}

}
