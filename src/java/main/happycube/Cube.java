package happycube;

import happycube.CubeTopology.Face;
import space3d.Point;
import space3d.Transformation;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cube {

    private final CubeTopology topology;

    private final Map<Face, Side> faces;
    private final Map<Face, Side> transformedFaces;

    public Cube(int size) {
        this.topology = new CubeTopology(size);
        this.faces = new EnumMap<>(Face.class);
        this.transformedFaces = new EnumMap<>(Face.class);
    }

    public Cube(Cube proto) {
        this.topology = proto.topology;
        this.faces = new EnumMap<>(proto.faces);
        this.transformedFaces = new EnumMap<>(proto.transformedFaces);
    }

    public void setFace(Face face, Side side) {
        if (side == null) {
            faces.remove(face);
            transformedFaces.remove(face);
        } else {
            faces.put(face, side);
            transformedFaces.put(face, side.transform(topology.getMoveTransformation(face)));
        }
    }

    public boolean isPotentiallyWellformed() {
        final List<List<Point>> edges = topology.getEdges();
        for (List<Point> edge : edges) {
            if (!isPotentiallyWellformed(edge)) {
                return false;
            }
        }
        return true;
    }

    public boolean isWellformedCube() {
        final List<List<Point>> edges = topology.getEdges();
        for (List<Point> edge : edges) {
            if (!isWellformed(edge)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPotentiallyWellformed(List<Point> edge) {
        final int[] counts = countPoints(edge);

        for (int count : counts) {
            if (count > 1) {
                return false;
            }
        }

        return true;
    }

    private boolean isWellformed(List<Point> edge) {
        final int[] counts = countPoints(edge);

        for (int count : counts) {
            if (count != 1) {
                return false;
            }
        }

        return true;
    }

    private int[] countPoints(List<Point> edge) {
        final int[] counts = new int[edge.size()];
        for (int i = 0; i < edge.size(); i++) {
            final Point edgePoint = edge.get(i);
            for (Side side : transformedFaces.values()) {
                if (side.contains(edgePoint)) {
                    counts[i]++;
                }
            }
        }
        return counts;
    }

    public Transformation[] getSideOrientations() {
        return topology.getSideOrientations();
    }

    @Override
    public String toString() {
        final int size = topology.getSize();

        final SidePrinter printer = new SidePrinter(size, 3, 4);
        printer.print(faces.get(Face.Top), 0, 1);

        printer.print(faces.get(Face.Left), 1, 0);
        printer.print(faces.get(Face.Front), 1, 1);
        printer.print(faces.get(Face.Right), 1, 2);
        printer.print(faces.get(Face.Back), 1, 3);

        printer.print(faces.get(Face.Bottom), 2, 1);

        return printer.toString();
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for (int i=0; i<8; i++) {
            final Transformation orientation = topology.getSideOrientations()[i];
            for (Face face : Face.values()) {
                final Transformation rotation = topology.getMoveTransformation(face);
                for (Side side : transformedFaces.values()) {
                    sum += side.transform(orientation).transform(rotation).hashCode();
                }
            }
        }
        return sum;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cube)) {
            return false;
        }
        final Cube that = (Cube) obj;

        for (int i=0; i<8; i++) {
            final Transformation orientation = topology.getSideOrientations()[i];
            for (Face face : Face.values()) {
                final Transformation rotation = topology.getMoveTransformation(face);
                final Set<Side> rotatedSides = new HashSet<>();
                for (Side side : transformedFaces.values()) {
                    rotatedSides.add(side.transform(orientation).transform(rotation));
                }

                final Collection<Side> thatSides = that.transformedFaces.values();
                if (rotatedSides.size() == thatSides.size() && rotatedSides.containsAll(thatSides)) {
                    return true;
                }
            }
        }

        return false;
    }
}
