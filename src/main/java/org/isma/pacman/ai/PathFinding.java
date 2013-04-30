package org.isma.pacman.ai;

import org.isma.graph.Vertex;
import org.isma.slick2d.Direction;
import org.isma.slick2d.PositionHelper;
import org.isma.slick2d.ShapeUtils;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathFinding {

    public static Direction nextDirection(LinkedList<Vertex> path, Rectangle bounds) {
        Vertex source = path.get(0);
        Vertex nextDestination = path.get(1);

        return getDirection(source, nextDestination, bounds);
    }

    private static Direction getDirection(Vertex<Point> vertex, Vertex<Point> closestVertex, Rectangle bounds) {
        Rectangle rect1 = ShapeUtils.buildRectangle(vertex.getId());
        Rectangle rect2 = ShapeUtils.buildRectangle(closestVertex.getId());
        List<Direction> orientations = PositionHelper.getOrientation(rect1, rect2);
        if (orientations.size() != 1){
            throw new RuntimeException("?");
        }
        if (hasChangeBound(vertex, closestVertex, bounds)){
            return PositionHelper.getReverseDirection(orientations.get(0));
        }
        return orientations.get(0);
    }

    private static boolean hasChangeBound(Vertex<Point> vertex, Vertex<Point> nextVertex, Rectangle bounds) {
        Point pointA = vertex.getId();
        Point pointB = nextVertex.getId();
        if (pointA.x == bounds.getMinX() && pointB.x == bounds.getMaxX()){
            return true;
        }
        if (pointB.x == bounds.getMinX() && pointA.x == bounds.getMaxX()){
            return true;
        }
        if (pointA.y == bounds.getMinY() && pointB.y == bounds.getMaxY()){
            return true;
        }
        if (pointB.y == bounds.getMinY() && pointA.y == bounds.getMaxY()){
            return true;
        }
        return false;
    }
}
