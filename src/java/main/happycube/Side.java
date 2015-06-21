package happycube;

import space3d.Figure;
import space3d.Point;
import space3d.Transformation;

import java.util.HashSet;
import java.util.Set;

public class Side extends Figure {

    private final int id;

    public Side(int id, int... arr) {
        super(humanFriendlyArrayToPointSet(arr));
        this.id = id;
    }

    Side(int id, Figure figure) {
        super(figure);
        this.id = id;
    }

    @Override
    public Side transform(Transformation transformation) {
        return new Side(id, super.transform(transformation));
    }

    private static Set<Point> humanFriendlyArrayToPointSet(int[] arr) {
        final int d = calculateSize(arr.length);
        if (d*d != arr.length) {
            throw new IllegalArgumentException("not a square");
        }

        final Set<Point> points = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                final int x = i % d;
                final int y = d-1 - i/d;

                points.add(new Point(x, y, 0));
            }
        }

        return points;
    }

    private static int calculateSize(int length) {
        return (int) Math.round(Math.sqrt(length));
    }

    public int getId() {
        return id;
    }
}