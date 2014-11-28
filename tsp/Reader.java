package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Reader {

	public Reader() throws NumberFormatException, IOException {

		// File file = new File(getGraph("g1.txt"));
		// System.out.println(file.getAbsolutePath());
		Node[] nodes = readFile(getGraphPath("g1.txt"));

		for (Node node : nodes) {
			System.out.println(node);
		}
	}

	private String getGraphPath(String filename) {
		// TODO, run from makefile or java, this is NOT the same, that's why
		// this is needed. Hardcoded...
		String s = System.getProperty("file.separator");
		String makefilePath = "graphs" + s + filename;
		String eclipsePath = "tsp" + s + makefilePath;

		// Path when compiling from the makefile
		File file = new File(makefilePath);
		if (file.exists())
			return file.getAbsolutePath();

		// Path compiling from eclipse
		return new File(eclipsePath).getAbsolutePath();
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		new Reader();
	}

	public static Node[] readFile(String path) throws NumberFormatException,
			IOException {
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

	private static Node[] read(BufferedReader br) throws NumberFormatException,
			IOException {

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
