import java.util.Arrays;



public class TwoOpt {
	
	public static void main(String[] args) {
		Node[] nodes = new Node[]{
			new Node(1,1),
			new Node(4,4),
			new Node(3,3),
			new Node(2,2),
			
			new Node(5,5)
		};
		short[] path = new short[]{0,1,2,3,4};
		System.out.println("path before swap: " + Arrays.toString(path));
		maybeSwap(nodes, path, (short)1, (short)3);
		System.out.println("path after swap: " + Arrays.toString(path));
	}
	
	public static boolean maybeSwap(Node[] nodes, short[] path, short i, short k){
		Node beforeSwap = nodes[path[i-1]];
		Node firstSwap = nodes[path[i]];
		Node lastSwap = nodes[path[k]];
		Node afterSwap = nodes[path[k+1]];
		double sqDistance = beforeSwap.sqDistance(firstSwap) + afterSwap.sqDistance(lastSwap);
		double newSqDistance = beforeSwap.sqDistance(lastSwap) + afterSwap.sqDistance(firstSwap);
		if(newSqDistance < sqDistance){
			reverseSubpath(path, i, k);
			return true;
		}
		return false;
	}
	
	private static void reverseSubpath(short[] path, int start, int end){
		int length = end - start + 1;
		for(int i = 0; i < length/2; i++){
			swapTwoElements(path, start + i, end - i);
		}
	}
	
	private static void swapTwoElements(short[] array, int index1, int index2){
		short tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;
	}
}
