package tsp;

import java.text.DecimalFormat;
 
public class Tester {
	
	/**
	 * Run same test multiple times,  return mean result
	 * @param solver
	 * @param nodes
	 * @param numRuns
	 * @return
	 */
	public static Result test(TSPSolver solver, Node[] nodes, int numRuns){
		Result result = new Result(0,0);
		for (int i = 0; i < numRuns; i++) {
			result.add(test(solver,nodes));
		}
		result.divide(numRuns);
		return result;
	}
	
	/**
	 * Run a single test with a given alg, return Result
	 * @param solver
	 * @param nodes
	 * @return
	 */
	public static Result test(TSPSolver solver, Node[] nodes){
		long time = System.currentTimeMillis();
		short[] path = solver.solveTSP(nodes);
		time = System.currentTimeMillis() - time;
		double length = Node.lengthOfPath(path, nodes);
		return new Result(time,length);
	}
	
	public static class Result{
		long computationTime;
		double totalPathLength;
		
		public Result(long time, double length){
			this.computationTime = time;
			this.totalPathLength = length;
		}
		
		public void add(Result result){
			computationTime += result.computationTime;
			totalPathLength += result.totalPathLength;
		}
		
		public void divide(int val){
			computationTime /= val;
			totalPathLength /= val;
		}
		
		public String toString(){
			String length = new DecimalFormat("#0.00").format(totalPathLength);
			return "[time: " + computationTime + ", length: " + length + "]";
		}
	}
}
