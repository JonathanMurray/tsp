package tsp;

import java.util.Random;

public class LinKernighanSimAnneal extends LinKernighan{
	
	private Random random = new Random();
	
	private float temperature;
	private float tempMultiplier;
	private final float minTemp;
	
	private String str;
	
	public LinKernighanSimAnneal(int limit, float temperature, float tempMultiplier, float minTemp) {
		super(limit);
		this.temperature = temperature;
		this.tempMultiplier = tempMultiplier;
		this.minTemp = minTemp;
		str = "LK-SA(" + limit + ", " + temperature + ", " + tempMultiplier + ")";
	}
	
	
	private boolean simAnneal(double xDist, double yDist){
		if(xDist > yDist){
			return true;
		}
		double chance = Math.exp((xDist - yDist)/xDist/temperature);
		boolean ok = random.nextFloat() < chance; 
		return ok;
	}

	@Override
	public short[] solveTSP(Node[] nodes) {
		
		return solveTSP(nodes, new VisualizerMockup());
	}
	
	@Override
	public short[] solveTSP(Node[] nodes, Visualizer visualizer) {
		short[] path = generateStartPath(nodes.length);
		return solveTSP(nodes, visualizer, path);
	}
	
	public short[] solveTSP(Node[] nodes, Visualizer visualizer, short[] path){
		distances = DistanceMapping.getDistances(nodes);
		visualizer.setPath(path);
		visualizer.sleep();
		this.nodes = nodes;
		this.visualizer = visualizer;
		this.path = path;
		short[] newPath = findX1();
		return newPath;
	}
	
	short[] findX1(){
		while(temperature > minTemp){
			short t1Index = (short) random.nextInt(path.length);
			boolean equilibrium = false;
			int i = 0;
			double pastLength = 0;
			int numChanges = 0;
			while(!equilibrium && i < LIMIT){
//				visualizer.highlightLoose(0, path[x1.fromIndex], path[x1.toIndex]);
//				visualizer.sleep();
				short[] newPath = findY1(t1Index, mod(t1Index + 1));
//				visualizer.dehighlight(4);
				if(newPath != null){
					path = newPath; //Next step success, but we'll continue improving the path
					if(numChanges % 5 == 0){
						double length = Node.lengthOfPath(path, nodes);
						if(pastLength != 0 && length > pastLength * 0.95){
							equilibrium = true;
						}
						pastLength = length;
					}
					numChanges ++;
//					visualizer.setPath(path); //necessary I think, path is just a pointer.
//					visualizer.sleep();
					t1Index = (short) random.nextInt(path.length);
					i = -1;
				}
				t1Index = mod(t1Index + 1);
				i++;
			}
			if(numChanges < 4){
				break;
			}
			temperature *= tempMultiplier;
		}
		
		visualizer.dehighlight();
		return path;
	}
	
	
	short[] findY1(short t1Index, short t2Index){
		short pathT2Index = path[t2Index];
		x1Dist = dist(pathT2Index, path[t1Index]);
		
		short stopBefore = mod(t1Index - 2);
		short start = mod(t2Index + 2);
		int numLoops = Math.min(mod(stopBefore - start), LIMIT);
		short t3Index = mod(start + random.nextInt(numLoops));
		
		for(int i = 0; i < numLoops; i++){
//			visualizer.highlightLoose(4, path[y1.fromIndex], path[y1.toIndex]);
//			visualizer.sleep();
			y1Dist = dist(pathT2Index,path[t3Index]);
			if(simAnneal(x1Dist, y1Dist)){
				short[] newPath = findX2(t1Index, t2Index, t3Index); 
//				visualizer.dehighlight(1);
//				visualizer.sleep();
				if(newPath != null){
//					visualizer.setPath(newPath);
					return newPath; //Next step success
				}
			}
			t3Index = mod(t3Index + 1);
			if(t3Index == stopBefore){
				t3Index = start;
			}
		}
		return null; //This step failed
	}
	
	short[] findX2(short t1Index, short t2Index, short t3Index){
		short t4Index = mod(t3Index - 1);
//		visualizer.highlightLoose(1, path[x2.fromIndex], path[x2.toIndex]);
//		visualizer.sleep();
		x2Dist = dist(path[t4Index],path[t3Index]);
		float possibleY2Dist = dist(path[t4Index], path[t1Index]);
		if(simAnneal(x1Dist + x2Dist, y1Dist + possibleY2Dist)){
			if(findFirstOccurence(t2Index, t4Index, path) == t2Index){
				TwoOpt.swap(path, t2Index, t4Index);
			}else{
				TwoOpt.swap(path, t3Index, t1Index); //TODO
			}
			return path; //Found a good 2-swap [SUCCESS]
		}else{
			short[] newPath = findY2(t1Index, t2Index, t3Index, t4Index);
//			visualizer.dehighlight(5);
//			visualizer.sleep();
			if(newPath != null){
//				visualizer.setPath(newPath);
				return newPath; //Next step success
			}
		}
		return null; //This step failed
	}
	
	short[] findY2(short t1Index, short t2Index, short t3Index, short t4Index){
		short stopBefore = mod(t1Index - 1);
		short pathT4Index = path[t4Index];
		
		short start = mod(t3Index + 2);
		int numLoops = Math.min(mod(stopBefore - start), LIMIT);
		if(numLoops == 0){
			return null;
		}
		short t5Index = mod(start + random.nextInt(numLoops));
		
		for(int i = 0; i < numLoops; i++){
//			visualizer.highlightLoose(5, path[y2.fromIndex], path[y2.toIndex]);
//			visualizer.sleep();
			y2Dist = dist(pathT4Index,path[t5Index]);
			if(simAnneal(x1Dist + x2Dist, y1Dist + y2Dist)){
				short[] newPath = findX3Y3(t1Index, t2Index, t3Index, t4Index, t5Index);
//				visualizer.dehighlight(2);
//				visualizer.dehighlight(6);
//				visualizer.sleep();
				if(newPath != null){
//					visualizer.setPath(newPath);
					return newPath; //Next step success
				}
			}
			t5Index = mod(t5Index + 1);
			if(t5Index == stopBefore){
				t5Index = start;
			}
		}
		return null; //This step failed
	}
	
	short[] findX3Y3(short t1Index, short t2Index, short t3Index, short t4Index, short t5Index){
		short t6Index = mod(t5Index - 1);
//		visualizer.highlightLoose(2, path[x3.fromIndex], path[x3.toIndex]);
//		visualizer.highlightLoose(6, path[y3.fromIndex], path[y3.toIndex]);
//		visualizer.sleep();
		float x3Dist = dist(path[t6Index], path[t5Index]);
		float y3Dist = dist(path[t6Index], path[t1Index]);
		if(simAnneal(x1Dist + x2Dist + x3Dist, y1Dist + y2Dist + y3Dist)){
			short[] newPath = improvePathWithSwap(nodes, path, t1Index, t2Index, t3Index, t4Index, t5Index, t6Index);
//			visualizer.setPath(newPath);
			return newPath;
		}
		return null;
	}

	public String toString(){
		return str;
	}
}
