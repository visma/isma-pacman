package org.isma.pacman.ai;

import org.isma.graph.Edge;
import org.isma.graph.Graph;
import org.isma.graph.Vertex;
import org.isma.pacman.tileset.PathTiledLayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {
    protected final PathTiledLayer pathTiledLayer;

    public GraphBuilder(PathTiledLayer pathTiledLayer) {
        this.pathTiledLayer = pathTiledLayer;
    }

    public Graph buildGraph() {
        Map<Point, Vertex> vertexes = buildVertexes();
        List<Edge> edges = buildEdges(vertexes);
        return new Graph(new ArrayList<Vertex>(vertexes.values()), edges);
    }

    private List<Edge> buildEdges(Map<Point, Vertex> vertexes) {
        List<Edge> edges = new ArrayList<Edge>();

        for (Point point : vertexes.keySet()) {
            Vertex source = vertexes.get(point);
            List<Point> adjacentPoints = getAdjacentPaths(point);
            for (Point adjacentPoint : adjacentPoints) {
                Vertex destination = vertexes.get(adjacentPoint);
                edges.add(new Edge(source, destination, getWeight(vertexes, source, destination)));
            }
        }
        return edges;
    }

    protected int getWeight(Map<Point, Vertex> vertexes, Vertex<Point> source, Vertex<Point> destination) {
        return 1;
    }

    private Map<Point, Vertex> buildVertexes() {
        Map<Point, Vertex> vertexes = new HashMap<Point, Vertex>();
        for (int i = 0; i < pathTiledLayer.getTiledMap().getWidth(); i++) {
            for (int j = 0; j < pathTiledLayer.getTiledMap().getHeight(); j++) {
                if (pathTiledLayer.isPath(i, j)) {
                    Point point = new Point(i, j);
                    vertexes.put(point, new Vertex<Point>(point));
                }
            }
        }
        return vertexes;
    }

    private List<Point> getAdjacentPaths(Point point) {
        int xLowerBound = 0;
        int xUpperBound = pathTiledLayer.getTiledMap().getWidth() - 1;

        int yLowerBound = 0;
        int yUpperBound = pathTiledLayer.getTiledMap().getHeight() - 1;

        List<Point> adjacents = new ArrayList<Point>();
        Point left, right, up, down;

        if (point.x > xLowerBound) {
            left = new Point(point.x - 1, point.y);
        } else if (point.x == xLowerBound) {
            left = new Point(xUpperBound, point.y);
        } else {
            throw new RuntimeException("?");
        }
        if (point.x < xUpperBound) {
            right = new Point(point.x + 1, point.y);
        } else if (point.x == xUpperBound) {
            right = new Point(xLowerBound, point.y);
        } else {
            throw new RuntimeException("?");
        }
        if (point.y > yLowerBound) {
            up = new Point(point.x, point.y - 1);
        } else if (point.x == xLowerBound) {
            up = new Point(point.x, yUpperBound);
        } else {
            throw new RuntimeException("?");
        }
        if (point.y < yUpperBound) {
            down = new Point(point.x, point.y + 1);
        } else if (point.y == yUpperBound) {
            down = new Point(point.x, yLowerBound);
        } else {
            throw new RuntimeException("?");
        }

        if (pathTiledLayer.isPath(left)) {
            adjacents.add(left);
        }
        if (pathTiledLayer.isPath(right)) {
            adjacents.add(right);
        }
        if (pathTiledLayer.isPath(down)) {
            adjacents.add(down);
        }
        if (pathTiledLayer.isPath(up)) {
            adjacents.add(up);
        }

        return adjacents;
    }


}
