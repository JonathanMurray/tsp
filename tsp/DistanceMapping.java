package tsp;

public class DistanceMapping {

	
	/**
	 * Creates a matrix where m[i,j] is the distance from nodes[i] to nodes[j]
	 * Use it like this: m[path[aIndex]][path[bIndex]]
	 * @param nodes
	 * @return
	 */
	public static float[][] getDistances(Node[] nodes){
		float[][] distances = new float[nodes.length][];
		for(int i = 0; i < nodes.length; i++){
			distances[i] = new float[nodes.length];
			for(int j = 0; j < nodes.length; j++){
				distances[i][j] = (float)nodes[i].distance(nodes[j]);
			}
		}
		return distances;
	}
}
