package tsp;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {
	public static ArrayList<Point2D.Double> getInput() {

		ArrayList<Point2D.Double> points = null;
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			int N = Integer.parseInt(br.readLine());
			points = new ArrayList<Point2D.Double>(N);

			String line;

			for (int i = 0; i < N; i++) {
				line = br.readLine();
				String split[] = line.split(" ");
				Point2D.Double point = new Point2D.Double(
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
