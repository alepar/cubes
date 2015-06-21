package happycube;

import space3d.Point;
import space3d.Transformation;
import space3d.Transformations;

import java.util.*;

import static happycube.CubeTopology.Face.*;
import static space3d.Transformations.*;

public class CubeTopology {

    public enum Face {
        Front, Left, Right, Top, Bottom, Back
    }

    private final int size;
    private final Map<Face, Transformation> moveTransformations;
    private final Transformation[] orientations;
    private final List<List<Point>> edges;
    private final Set<Point> allEdgePoints;


    public CubeTopology(int size) {
        this.size = size;
        this.moveTransformations = calculateMoveTransformations(size);
        this.orientations = Transformations.orientationsZ(size);
        this.edges = calculateEdges(size);
        this.allEdgePoints = union(edges);
    }

    private static Set<Point> union(List<List<Point>> listOfLists) {
        final Set<Point> result = new HashSet<>();

        for (List<Point> list : listOfLists) {
            for (Point point : list) {
                result.add(point);
            }
        }

        return result;
    }

    private static List<List<Point>> calculateEdges(int size) {
        final List<List<Point>> edges = new ArrayList<>();

        for (int a=0; a<4; a++) {
            final List<Point> edgeX = new ArrayList<>();
            final List<Point> edgeY = new ArrayList<>();
            final List<Point> edgeZ = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                final int j = a / 2 * (size-1);
                final int k = (a % 2) * (size-1);
                edgeX.add(new Point(i, j, k));
                edgeY.add(new Point(j, i, k));
                edgeZ.add(new Point(j, k, i));
            }

            edges.add(edgeX);
            edges.add(edgeY);
            edges.add(edgeZ);
        }

        return edges;
    }

    private static Map<Face, Transformation> calculateMoveTransformations(int size) {
        final Map<Face, Transformation> result = new EnumMap<>(Face.class);
        final int m = size-1;

        result.put(Front, Transformations.one());
        result.put(Back, translation(m, 0, m).mul(rotationY(Math.PI)));

        result.put(Left, translation(0, 0, m).mul(rotationY(Math.PI / 2)));
        result.put(Right, translation(m, 0, 0).mul(rotationY(-Math.PI / 2)));

        result.put(Top, translation(0, m, 0).mul(rotationX(Math.PI / 2)));
        result.put(Bottom, translation(0, 0, m).mul(rotationX(-Math.PI / 2)));

        return result;
    }

    public List<List<Point>> getEdges() {
        return edges;
    }

    public Transformation getMoveTransformation(Face face) {
        return moveTransformations.get(face);
    }

    public Transformation[] getSideOrientations() {
        return orientations;
    }

    public int getSize() {
        return size;
    }

    public Set<Point> getAllEdgePoints() {
        return allEdgePoints;
    }

}
