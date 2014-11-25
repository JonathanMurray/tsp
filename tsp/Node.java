package tsp;

public class Node {
	private int x;
	private int y;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int x(){
		return x;
	}
	
	public int y(){
		return y;
	}
	
	public double sqDistance(Node other){
		return (x-other.x)*(x-other.x) + (y-other.y)*(y-other.y);
	}
	
	public String toString(){
		return "(" + x + "," + y + ")";
	}
}
