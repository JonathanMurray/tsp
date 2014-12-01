package tsp;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MatsNaive implements TSPSolver {

	/**
	 * Used only for quick testing 
	 */
	
	public void test(){
		ArrayList<Node> points = Reader.getInput();
		Node nodes[] = new Node[points.size()];
		
		points.toArray(nodes);
		
		print(naive(nodes));
		
	}

	@Override
	public short[] solveTSP(Node[] nodes) {
		return solveTSP(nodes, new VisualizerMockup());
	}

	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		return naive(nodes);
	}

	public void print(short[] list) {
		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i]);
		}
	}

	public short[] naive(Node[] nodes) {
		short[] tour = new short[nodes.length];
		boolean used[] = new boolean[nodes.length];
		used[0] = true;

		for (short i = 1; i < nodes.length; i++) {
			short best = -1;
			for (short j = 0; j < nodes.length; j++) {
				if (!used[j]
						&& (best == -1 || dist(tour[i - 1], j, nodes) < dist(
								tour[i - 1], best, nodes)))
					best = j;
			}
			tour[i] = best;
			used[best] = true;

		}
		return tour;
	}

	/**
	 * Calculats the difference between provided index of points, and array
	 * 
	 * @param i
	 * @param j
	 * @param points
	 * @return
	 */
	private double dist(int i, int j, Node[] nodes) {
		return nodes[i].sqDistance(nodes[j]);
	}

	/**
	 * Calculates the distance between two points.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int dist(Double p1, Double p2) {
		// Convert to points
		double x1 = p1.x;
		double y1 = p1.y;

		double x2 = p2.x;
		double y2 = p2.y;

		double a = x2 - x1;
		double b = y2 - y1;

		double c = Math.sqrt(a * a + b * b);

		return (int) Math.round(c);

	}

	public static void main(String[] args) {
		new MatsNaive().test();;
	}

}
