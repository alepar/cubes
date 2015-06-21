package space3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Figure {

    private final Set<Point> points;

    public Figure(Set<Point> points) {
        this.points = points;
    }

    public Figure(Figure figure) {
        this.points = figure.points;
    }

    public Figure transform(Transformation transformation) {
        final Set<Point> transformed = new HashSet<>();

        for (Point point : points) {
            transformed.add(new Point(transformation.mul(point)));
        }

        return new Figure(transformed);
    }

    public boolean contains(Point point) {
        return points.contains(point);
    }

    @Override
    public String toString() {
        int maxX = 0, maxY = 0, maxZ = 0;
        for (Point point : points) {
                if (point.getX() > maxX) {
                    maxX = point.getX();
                }
                if (point.getY() > maxY) {
                    maxY = point.getY();
                }
                if (point.getZ() > maxZ) {
                    maxZ = point.getZ();
                }
        }

        final StringBuilder sb = new StringBuilder();
        for (int y = maxY; y>=0; y--) {
            for (int z = 0; z<=maxZ; z++) {
                for (int x = 0; x<=maxX; x++) {
                    sb.append(contains(new Point(x, y, z)) ? '□' : '.').append(' ');
                }
                sb.append("  |  ");
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Figure figure = (Figure) o;

        return points.equals(figure.points);

    }

    @Override
    public int hashCode() {
        final List<Point> orderedPoints = new ArrayList<>(points);
        Collections.sort(orderedPoints, PointsComparator.instance);
        return orderedPoints.hashCode();
    }

    public Set<Point> getPoints() {
        return points;
    }

    private static class PointsComparator implements Comparator<Point> {
        private static PointsComparator instance = new PointsComparator();

        @Override
        public int compare(Point left, Point right) {
            for (int i=0; i<4; i++) {
                final int cmp = Integer.compare(left.get(0, i), right.get(0, i));
                if (cmp != 0) {
                    return cmp;
                }
            }

            return 0;
        }
    }
}
