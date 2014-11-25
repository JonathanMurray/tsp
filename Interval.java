
public class Interval {
	private int min;
	private int max;
	private int length;
	public Interval(int min, int max){
		this.min = min;
		this.max = max;
		this.length = max - min;
	}
	
	public int min(){
		return min;
	}
	
	public int max(){
		return max;
	}
	
	public int length(){
		return length;
	}
}
