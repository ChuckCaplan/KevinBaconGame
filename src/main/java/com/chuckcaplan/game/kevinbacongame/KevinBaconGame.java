package com.chuckcaplan.game.kevinbacongame;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class KevinBaconGame {

	// create the graph to store the relationships between movies and actors
	private Graph<String, DefaultEdge> g = GraphTypeBuilder.<String, DefaultEdge>undirected()
			.allowingMultipleEdges(false).allowingSelfLoops(true).edgeClass(DefaultEdge.class).weighted(false)
			.buildGraph();

	// list of actors - will iterate through while trying to find longest path
	private List<String> actors = new ArrayList<String>();

	public static void main(String[] args) {
		KevinBaconGame app = new KevinBaconGame();
		app.run();
	}

	public void run() {

		System.out.println("Kevin Bacon Game implementation in Java");
		System.out.println("Note - This can take several minutes to run.");
		System.out.println();

		File file = new File("data.txt");
		List<String> data = null;

		// read the file
		try {
			data = FileUtils.readLines(file, Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// populate the graph
		for (String str : data) {
			JSONObject jsonObj = new JSONObject(str);
			String title = jsonObj.getString("title");
			g.addVertex(title);
			JSONArray a = jsonObj.getJSONArray("cast");
			for (Object actorObj : a) {
				String actor = actorObj.toString();
				if (!g.vertexSet().contains(actor)) {
					g.addVertex(actor);
					actors.add(actor);
				}
				g.addEdge(actor, title);
			}
		}

		// print out actor farthest from Kevin Bacon
		printLongestPathStats("[[Kevin Bacon]]");

		System.out.println();

		// print shortest path between two actors
		String actor1 = "[[Kevin Bacon]]";
		String actor2 = "[[Jim Gaffigan]]";
		printPathStats(actor1, actor2);
	}

	public void printStat(int length, String path, String actor) {
		System.out.println("Length: " + (length / 2) + " - Actor: " + actor + " - Path: " + path);
	}

	/**
	 * Print the shortest path between 2 actors
	 * 
	 * @param actor1
	 * @param actor2
	 */
	public void printPathStats(String actor1, String actor2) {
		DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(g);
		SingleSourcePaths<String, DefaultEdge> cPaths = dijkstraAlg.getPaths(actor1);
		GraphPath<String, DefaultEdge> graphPath = cPaths.getPath(actor2);
		printStat(graphPath.getLength(), graphPath.toString(), actor2);
	}

	/**
	 * Find the actor the farthest distance away in the graph from the passed in
	 * actor
	 * 
	 * @param from
	 */
	public void printLongestPathStats(String from) {
		DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(g);
		SingleSourcePaths<String, DefaultEdge> cPaths = dijkstraAlg.getPaths(from);
		String longestActor = "";
		int longestLength = 0;
		String longestPath = "";
		for (String actor : actors) {
			GraphPath<String, DefaultEdge> graphPath = cPaths.getPath(actor);
			if (graphPath != null) {
				int length = graphPath.getLength();
				if (length > longestLength) {
					longestLength = length;
					longestPath = graphPath.toString();
					longestActor = actor;
				}
			}
		}
		// print longest
		printStat(longestLength, longestPath, longestActor);
	}
}