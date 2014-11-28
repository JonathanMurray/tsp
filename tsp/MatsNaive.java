package tsp;
 
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MatsNaive {

	public MatsNaive() {		
		
		ArrayList<Double> points = Reader.getInput();		
		print(naive(points));
		
		
		
	}
	
	public void print(int[] list){
		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i]);
		}
	}
	
	public int[] naive(ArrayList<Double> points ){
		int[] tour = new int[points.size()];
		boolean used[] = new boolean[points.size()];
		used[0] = true;
		
		for (int i = 1; i < points.size(); i++) {
			int best = -1;
			for (int j = 0; j < points.size(); j++) {
				if(!used[j] && (best == -1 || dist(tour[i-1],j,points) < dist(tour[i-1],best,points)))
					best = j;
			}
			tour[i] = best;
			used[best] = true;
			
		}
		return tour;
	}

	/**
	 * Calculats the difference between provided index of points, and array
	 * @param i
	 * @param j
	 * @param points
	 * @return
	 */
	private int dist(int i, int j, ArrayList<Double> points) {		
		return dist(points.get(i), points.get(j));
	}
	
	/**
	 * Calculates the distance between two points.
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int dist(Double p1, Double p2){
		//Convert to points
		double x1 = p1.x;
		double y1 = p1.y;
		
		double x2 = p2.x;
		double y2 = p2.y;
		
		double a = x2 - x1;
		double b = y2 - y1;
		
		double c = Math.sqrt(a*a+b*b);
		
		return (int) Math.round(c);
		
	}

	public static void main(String[] args) {
		new MatsNaive();
	}
}
