package tsp;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import tsp.VisualizerImpl.TSPInput;
import tsp.VisualizerImpl.VisualizationParams;

public class LinKernighanTest {

	
//	@Test
//	public void testCopySegment(){
//		short[] src =  new short[]{1,1,2,3,4,5,5,5,6,7,8};
//		short[] dst =  new short[]{0,0,0,0,0,0,0,0,0,0,0};
//		short[] dst2 = new short[]{0,0,0,0,0,0,0,0,0,0,0};
//		LinKernighan.copySegment(src, dst, 3, 6, 3, false);
//		LinKernighan.copySegment(src, dst2, 0, 8, 3, true);
//		assert(Arrays.equals(dst, new short[]{0, 0, 0, 0, 0, 0, 3, 4, 5, 0, 0}));
//		assert(Arrays.equals(dst2,  new short[]{0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1}));
//	}
//	
//	@Test
//	public void testImprovePathWithSwap(){
//		Node[] nodes = new Node[]{
//				new Node(0, 0),
//				new Node(3,3),
//				new Node(5,0),
//				new Node(0,5),
//				new Node(7,0),
//				new Node(15,15),
//				new Node(2,7),
//				new Node(5, 6),
//				new Node(0,1),
//				new Node(10,2),
//				new Node(11, 11),
//				new Node(4, 12)
//		};
//		
//		short[] path = new short[]{0,1,2,3,4,5,6,7,8,9,10,11};
//		Edge x1 = new Edge(0, 1);
//		Edge y1 = new Edge(1, 4);
//		Edge x2 = new Edge(4, 3);
//		Edge y2 = new Edge(3, 10);
//		Edge x3 = new Edge(10, 9);
//		Edge y3 = new Edge(9, 0);
//		
//		short[] newPath = LinKernighan.improvePathWithSwap(nodes, path, x1, y1, x2, y2, x3, y3);
//		assert(Arrays.equals(newPath, new short[]{0, 9, 8, 7, 6, 5, 4, 1, 2, 0, 10, 11}));
//	}
	
//	@Test
//	public void testImprovePathWithSwap(){
//		Node[] nodes = new Node[]{
//				new Node(0,0),
//				new Node(1,1),
//				new Node(2,2),
//				new Node(3,3),
//				new Node(4,4),
//				new Node(5,5),
//				new Node(6,6),
//		};
//		
//		short[] path = new short[]{0,2,3,1,4,5,6};
//		Edge x1 = new Edge(path, 1,2);
//		Edge y1 = new Edge(path, 2,4);
//		Edge x2 = new Edge(path, 4,3);
//		Edge y2 = new Edge(path, 3,6);
//		Edge x3 = new Edge(path, 6,5);
//		Edge y3 = new Edge(path, 5,1);
//		short[] newPath = LinKernighan.improvePathWithSwap(nodes, path, x1, y1, x2, y2, x3, y3);
//		System.out.println(Arrays.toString(newPath));
//		Assert.assertArrayEquals(new short[]{2, 5,4,3,1,6,0}, newPath);
//	}
	
//	@Test
//	public void testLinKernighan(){
//		Node[] nodes = new Node[]{
//				new Node(-2, -1),
//				new Node(-2, 1),
//				new Node(0, 2),
//				new Node(0.5, 0.5),
//				new Node(2, 1),
//				new Node(0, -2), 
//				new Node(2, -1)
//		};
//		
//		Visualizer visualizer = new VisualizerImpl(
//				new Dimension(500,500), 
//				new TSPInput(new Interval(-2, 2), nodes),
//				new VisualizationParams(0, 200)
//		);
//		
//		short[] path = new LinKernighan().solveTSP(nodes, visualizer);
//		Assert.assertArrayEquals(path, new short[]{0, 1, 2, 3, 4, 6, 5});
//	}
//	
//	@Test
//	public void testLinKernighan2(){
//		Node[] nodes = new Node[]{
//				new Node(2,1),
//				new Node(0,-2),
//				new Node(-2,1),
//				new Node(0,2),
//				new Node(2,-1),
//				new Node(0.5,0.5), 
//				new Node(-2,-1)
//		};
//		
//		Visualizer visualizer = new VisualizerImpl(
//				new Dimension(500,500), 
//				new TSPInput(new Interval(-2, 2), nodes),
//				new VisualizationParams(0, 200)
//		);
//		
//		short[] path = new LinKernighan().solveTSP(nodes, visualizer);
//		Assert.assertArrayEquals(path, new short[]{1, 6, 2, 3, 5, 0, 4});
//	}
//	
//	@Test
//	public void testLinKernighan3(){
//		//Same nodes but specified order
//		Node[] nodes = new Node[]{
//				new Node(0,-2),
//				new Node(0.5,0.5),
//				new Node(2,1),
//				new Node(-2,1),
//				new Node(2,-1),
//				new Node(-2,-1), 
//				new Node(0,2)
//		};
//		
//		Visualizer visualizer = new VisualizerImpl(
//				new Dimension(500,500), 
//				new TSPInput(new Interval(-2, 2), nodes),
//				new VisualizationParams(0, 50)
//		);
//		
//		short[] path = new LinKernighan().solveTSP(nodes, visualizer);
//		Assert.assertArrayEquals(path, new short[]{3, 5, 0, 4, 2, 1, 6});
//	}
	
	@Test
	public void testLinKernighan4(){
		
		//nodes: [( -2 ; 1 ), ( 2 ; 1 ), ( -2 ; -1 ), ( 2 ; -1 ), ( 0 ; 2 ), ( 0 ; -2 ), ( 0,5 ; 0,5 )]

		Node[] nodes = new Node[]{
			new Node(-2, 1),
			new Node(0, 2),
			new Node(2, 1),
			new Node(0.5, 0.5),
			new Node(2, -1),
			new Node(0, -2),
			new Node(-2, -1)
		};
		List<Node> nodeList = Arrays.asList(nodes);
		Collections.shuffle(nodeList);
		nodes = nodeList.toArray(new Node[]{});
		
		Visualizer visualizer = new VisualizerImpl(
				new Dimension(500,500), 
				new TSPInput(new Interval(-2, 2), nodes),
				new VisualizationParams(0, 20)
		);
		
		short[] path = new LinKernighan().solveTSP(nodes, visualizer);
		System.out.println("pathlen: " + Node.lengthOfPath(path, nodes));
		Assert.assertTrue(Node.lengthOfPath(path, nodes) <= 16.1);
	}

}
