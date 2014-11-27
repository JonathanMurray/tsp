package tsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {
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
				String split[] = line.split(" ");
				Node point = new Node(
						Double.parseDouble(split[0]),
						Double.parseDouble(split[1]));
				points.add(point);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return points;
	}

}
