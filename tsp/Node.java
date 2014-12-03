package tsp;

import java.util.HashSet;

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
	
	public static void assertValidPath(short[] path, Node[] nodes){
		if(nodes.length != path.length){
			throw new RuntimeException("Not even the same length");
		}
		HashSet<Short> seen = new HashSet<Short>();
		for(short node : path){
			if(seen.contains(node)){
				throw new RuntimeException(node + " occurs more than once in the path!");
			}
			seen.add(node);
		}
	}

	public static double lengthOfPath(short[] path, Node[] nodes) {
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
