package tsp;


public class Node {
	private double x;
	private double y;
	
	public Node(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double x(){
		return x;
	}
	
	public double y(){
		return y;
	}
	
	public double sqDistance(Node other){
		return (x-other.x)*(x-other.x) + (y-other.y)*(y-other.y);
	}
	
	private double distance(Node other){
		return Math.sqrt(sqDistance(other));
	}
	
	public static double lengthOfPath(short[] path, Node[] nodes){
		double length = 0;
		Node prevNode = nodes[nodes.length - 1];
		for(short nodeIndex : path){
			Node node = nodes[nodeIndex];
			length += node.distance(prevNode);
			prevNode = node;
		}
		return length;
	}
	
	public String toString(){
		return "( " + doubleStr(x) + " ; " + doubleStr(y) + " )";
	}
	
	private String doubleStr(double x){
		String s = String.format("%.1f", x);
		if(s.charAt(s.length() - 1) == '0'){
			s = s.substring(0, s.length() - 2);
		}
		return s;
	}
	
	
}
