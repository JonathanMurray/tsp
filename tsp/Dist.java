package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;



// Object for handling distances between nodes a to b
public class Dist implements Comparable<Dist> {
	public short a;
	public short b;
	double sqDistance;

	public Dist(short a, short b, double sqDistance) {
		this.a = a;
		this.b = b;
		this.sqDistance = sqDistance;
	}

	@Override
	public int compareTo(Dist o) {
		return Double.compare(sqDistance, o.sqDistance);
	}

	public String toString() {
		return "(" + a + "," + b + ") " + sqDistance;
	}
	
	
	public static LinkedList<Dist>[] getDistNearestMatrix(Node[] nodes){
		LinkedList<Dist>[] mtrx = new LinkedList[nodes.length];
		
		for (int i = 0; i < nodes.length; i++) {
			mtrx[i] = new LinkedList<Dist>();
		}
		
		for (short i = 0; i < nodes.length; i++) {
			for (short j = 0; j < nodes.length; j++) {
				if(i==j )
					continue;
				
				double distance = nodes[i].sqDistance(nodes[j]);
				
				mtrx[i].add(new Dist(i, j, distance));
			}
			Collections.sort(mtrx[i]);
		}
		return mtrx;
		
	}
	
	/**
	 * This will generate a list of all the distances between the nodes.
	 * 
	 * Size: It will be of size (n^2 - n) / 2; reason: 1. All distances from a
	 * -> b implies the same from b -> a, this manages to cut listsize in half;
	 * 2. all points from a -> a (from to same node) are trivial and is not
	 * included, removes n.
	 * 
	 * @param nodes
	 * @return
	 */
	public static ArrayList<Dist> getDistList(Node[] nodes) {
		
		//We know the exact size to be this 
		int size = (nodes.length * nodes.length - nodes.length) /2 ;
		ArrayList<Dist> dists = new ArrayList<Dist>(size);
		
		for (short i = 0; i < nodes.length - 1; i++) {
			for (short j = (short) (1 + i); j < nodes.length; j++) {
				double distance = nodes[i].sqDistance(nodes[j]);
				dists.add(new Dist(i, j, distance));
			}
		}
//		System.out.println(dists.size() + " " + size);

		return dists;

	}

}
