package org.isma.pacman.tileset;

import org.isma.graph.Vertex;
import org.isma.slick2d.PositionHelper;
import org.isma.slick2d.tileset.TiledLayer;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO faire du visitor pour tous les parcours x,y de tileMap
public class PathTiledLayer extends TiledLayer<PathTiledLayerProperties> {
    //TODO un peu bourrin cette Map de points...
    private final Map<Point, Integer> pathPoints = new HashMap<Point, Integer>();
    private final Rectangle bounds;


    public PathTiledLayer(TiledMap tiledMap) {
        super(2, tiledMap);

        bounds = new Rectangle(0, 0,
                tiledMap.getWidth() * tiledMap.getTileWidth(),
                tiledMap.getHeight() * tiledMap.getTileHeight());

        initPathPoints();
    }

    private List<Point> getAdjacentPaths(Point point) {
        int xLowerBound = 0;
        int xUpperBound = getTiledMap().getWidth() - 1;

        int yLowerBound = 0;
        int yUpperBound = getTiledMap().getHeight() - 1;

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

        if (properties().isPath(left)) {
            adjacents.add(left);
        }
        if (properties().isPath(right)) {
            adjacents.add(right);
        }
        if (properties().isPath(down)) {
            adjacents.add(down);
        }
        if (properties().isPath(up)) {
            adjacents.add(up);
        }

        return adjacents;
    }

    private Map<Point, Vertex> buildVertexes() {
        Map<Point, Vertex> vertexes = new HashMap<Point, Vertex>();
        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (properties().isPath(i, j)) {
                    Point point = new Point(i, j);
                    vertexes.put(point, new Vertex<Point>(point));
                }
            }
        }
        return vertexes;
    }

    @Override
    protected PathTiledLayerProperties buildProperties() {
        return new PathTiledLayerProperties(this);
    }

    public boolean existPath(Line line, int moveLevel) {
        float[] point1 = line.getPoint(0);
        float[] point2 = line.getPoint(1);
        Point pointA = new Point((int) point1[0], (int) point1[1]);
        Point pointB = new Point((int) point2[0], (int) point2[1]);

        PositionHelper.modifyPositionIfOutOfBounds(bounds, pointA, pointB);

        boolean containsPoint1 = pathPoints.containsKey(pointA) && moveLevel >= pathPoints.get(pointA);
        boolean containsPoint2 = pathPoints.containsKey(pointB) && moveLevel >= pathPoints.get(pointB);
        return containsPoint1 && containsPoint2;
    }


    private void initPathPoints() {
        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (properties().isPath(i, j)) {
                    putPathPoints(i, j, properties().getPath(i, j));
                }
            }
        }
    }

    private void putPathPoints(int x, int y, int pathValue) {
        int tileWidth = getTiledMap().getTileWidth();
        for (int i = 0; i < tileWidth; i++) {
            for (int j = 0; j < tileWidth; j++) {
                Point point = new Point(x * tileWidth + i, y * tileWidth + j);
                pathPoints.put(point, pathValue);
            }
        }
    }

    public Rectangle getGhostHouse() {
        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (properties().isGhostHouse(i, j)) {
                    return getRectangle(i, j);
                }
            }
        }
        throw new RuntimeException("no ghost house defined");
    }

    public Rectangle getGhostCorner(String ghostName, int cornerIndex) {
        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (properties().isGhostCorner(i, j, ghostName, cornerIndex)) {
                    return getRectangle(i, j);
                }
            }
        }
        throw new RuntimeException("no ghost corner defined for " + ghostName + " at " + cornerIndex);
    }

}
