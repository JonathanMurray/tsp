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
	
	public String toString(){
		return "( " + doubleStr(x) + " ; " + doubleStr(y) + " )";
//		return "(" + x + "," + y + ")";
	}
	
	private String doubleStr(double x){
		String s = String.format("%.1f", x);
		if(s.charAt(s.length() - 1) == '0'){
			s = s.substring(0, s.length() - 2);
		}
		return s;
	}
}
