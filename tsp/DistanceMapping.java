package tsp;

public class DistanceMapping {

	public static float[][] getDistances(Node[] nodes){
//		System.out.println("dist");
		float[][] distances = new float[nodes.length][];
		for(int i = 0; i < nodes.length; i++){
			distances[i] = new float[nodes.length];
			for(int j = 0; j < nodes.length; j++){
				distances[i][j] = (float)nodes[i].sqDistance(nodes[j]);
			}
		}
//		System.out.println("done");
		return distances;
	}
}
