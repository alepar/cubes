public class Side {

    private final int length;
    private final int[] arr;

    public Side(int... arr) {
        this.arr = arr;
        this.length = arr.length/4 + 1;

        if (arr.length != (length-1) * 4) {
            throw new IllegalArgumentException("invalid length");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int y=length-1; y>=0; y--) {
            for (int x=0; x<length; x++) {
                sb.append(get(x, y) == 1 ? '□' : ' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    private int idx(int x, int y) {
        if (y == 0) {
            return x;
        }

        if (y == length-1) {
            return 3*length-3 - x;
        }

        if (x == 0) {
            return 4*length-4 - y;
        }

        if (x == length-1) {
            return length-1 + y;
        }

        throw new AssertionError("never happens");
    }

    public int get(int x, int y) {
        if (x > 0 && y > 0 && x < length-1 && y < length-1) {
            return 1;
        }

        return arr[idx(x, y)];
    }

    public void set(int x, int y, int value) {
        if (x > 0 && y > 0 && x < length-1 && y < length-1) {
            throw new IllegalArgumentException("middle is always solid");
        }

        arr[idx(x, y)] = value;
    }

    public Side transform(Matrix transformation) {
        final Side transformed = new Side(new int[arr.length]);

        for (int i=0; i<arr.length; i++) {
            final int x, y;

            if (i<length) {
                y=0; x=i;
            } else if (i<2*length-1) {
                x=length-1; y=i-x;
            } else if (i<3*length-2) {
                y=length-1; x=3*length-3-i;
            } else {
                x=0; y=4*length-4-i;
            }

            final Matrix coord = new Matrix(3, 1,
                    x,
                    y,
                    1
            );

            final Matrix transformedCoord = transformation.mul(coord);

            final int tx = transformedCoord.get(0, 2);
            final int ty = transformedCoord.get(0, 1);
            transformed.set(tx, ty, get(x, y));
        }

        return transformed;
    }

}