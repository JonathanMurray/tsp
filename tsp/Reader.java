package tsp;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
	
	public Reader() throws NumberFormatException, IOException {
		Node[] nodes = readFile("graphs/g1");
		
		for (Node node : nodes) {
			System.out.println(node);
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		new Reader();
	}
	public static Node[] readFile(String path) throws NumberFormatException, IOException {
			File file = new File(path);

			BufferedReader br = new BufferedReader(new FileReader(file));
			
			return read(br);

	}

	private static Node[] readNodesFromString(String string) {
		String lines[] = string.split("\n");
		int n = Integer.parseInt(lines[0]);

		Node[] nodes = new Node[n];

		for (int i = 0; i < n; i++)
			nodes[i] = getNodeFromLine(lines[i + 1]);

		return nodes;

	}

	private static Node getNodeFromLine(String line) {
		String xy[] = line.split(" ");
		return new Node(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
	}
	
	private static Node[] read(BufferedReader br) throws NumberFormatException, IOException{
		
		int N = Integer.parseInt(br.readLine());
		Node nodes[] = new Node[N];

		String line;

		for (int i = 0; i < N; i++) {
			line = br.readLine();
			Node point = getNodeFromLine(line);
			nodes[i] = point;
		}
		
		return nodes;
	}

	public static ArrayList<Node> getInput() {

		ArrayList<Node> points = null;
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			int N = Integer.parseInt(br.readLine());
			points = new ArrayList<Node>(N);

			String line;

			for (int i = 0; i < N; i++) {
				line = br.readLine();
				Node point = getNodeFromLine(line);
				points.add(point);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return points;
	}

}
