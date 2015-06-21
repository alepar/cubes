package happycube;

import happycube.CubeTopology.Face;
import space3d.Point;
import space3d.Transformation;

import java.util.*;

public class Cube {

    private final CubeTopology topology;

    private final Map<Face, Side> faces;
    private final Map<Face, Side> transformedFaces;

    private final Map<Point, Integer> counts;

    public Cube(int size) {
        this.topology = new CubeTopology(size);
        this.faces = new EnumMap<>(Face.class);
        this.transformedFaces = new EnumMap<>(Face.class);
        this.counts = new HashMap<>();
    }

    public Cube(Cube src) {
        this.topology = src.topology;
        this.faces = new EnumMap<>(src.faces);
        this.transformedFaces = new EnumMap<>(src.transformedFaces);
        this.counts = new HashMap<>(src.counts);
    }

    public void setFace(Face face, Side side) {
        if (side == null) {
            final Side transformedSide = transformedFaces.get(face);
            if (transformedSide != null) {
                decrementCounts(transformedSide);
            }

            faces.remove(face);
            transformedFaces.remove(face);
        } else {
            faces.put(face, side);
            final Side transformedSide = side.transform(topology.getMoveTransformation(face));
            transformedFaces.put(face, transformedSide);
            incrementCounts(transformedSide);
        }
    }

    private void incrementCounts(Side side) {
        for (Point point : side.getPoints()) {
            Integer count = counts.get(point);
            if (count == null) {
                count = 0;
            }
            counts.put(point, count+1);
        }
    }

    private void decrementCounts(Side side) {
        for (Point point : side.getPoints()) {
            counts.put(point, counts.get(point) -1);
        }
    }

    public boolean isPotentiallyWellformed() {
        final Collection<Point> points = topology.getAllEdgePoints();
        for (Point point : points) {
            final Integer count = counts.get(point);
            if (count != null && count > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isWellformed() {
        final Collection<Point> points = topology.getAllEdgePoints();
        for (Point point : points) {
            if (counts.get(point) != 1) {
                return false;
            }
        }
        return true;
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
