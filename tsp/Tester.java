package tsp;

import java.awt.Dimension;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import tsp.VisualizerImpl.TSPInput;
import tsp.VisualizerImpl.VisualizationParams;
 
public class Tester {
	
	public static void compareSolvers(List<TSPSolver> solvers, List<String> testFiles, int numRuns) throws NumberFormatException, IOException{
		HashMap<String, Node[]> graphs = new HashMap<String, Node[]>();
		for(String testFile : testFiles){
			Node[] graph = Reader.readGraphFile(testFile);
			graphs.put(testFile, graph);
		}
		for(String testFile : testFiles){
			Node[] graph = graphs.get(testFile);
			System.out.println();
			System.out.println(testFile + ":");
			System.out.println("----------------");
			for(TSPSolver solver : solvers){
				Result result = Tester.test(solver, graph, numRuns);
				System.out.println(solver + ": " + result);
			}
		}
	}
	
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
		Visualizer visualizer = new VisualizerMockup();
		if(solver.toString().equals("Lin-Kernighan")){
			visualizer = new VisualizerImpl("LK" , new Dimension(500,500), new TSPInput(new Interval(0, 1000), nodes), new VisualizationParams(0, 0));
		} //TODO
		long time = System.currentTimeMillis();
		short[] path = solver.solveTSP(nodes, visualizer);
		time = System.currentTimeMillis() - time;
		visualizer.close();
		Node.assertValidPath(path, nodes);
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
