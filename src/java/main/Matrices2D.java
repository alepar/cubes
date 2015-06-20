public class Matrices2D {

    private Matrices2D() {
    }

    public static Matrix rotation(double rads) {
        return new Matrix(3, 3,
                round(Math.cos(rads)), round(-Math.sin(rads)),  0,
                round(Math.sin(rads)), round(Math.cos(rads)),   0,
                           0,                      0,           1
        );
    }

    public static Matrix translation(int x, int y) {
        return new Matrix(3, 3,
                1,  0,  x,
                0,  1,  y,
                0,  0,  1
        );
    }

    public static Matrix mirror() {
        return new Matrix(3, 3,
                0,  1,  0,
                1,  0,  0,
                0,  0,  1
        );
    }

    private static int round(double d) {
        return (int)Math.round(d);
    }

    public static Matrix[] orientations(int sideLength) {
        final Matrix[] transformations = new Matrix[8];

        for (int i=0; i<4; i++)  {
            final int sx = ((i/2 + i%2) % 2) * (sideLength-1);
            final int sy = i/2 * (sideLength-1);
            transformations[i] = Matrices2D.translation(sx, sy).mul(Matrices2D.rotation(Math.PI / 2 * i));
        }

        final Matrix mirror = Matrices2D.mirror();
        for (int i=4; i<8; i++) {
            transformations[i] = transformations[i-4].mul(mirror);
        }

        return transformations;
    }
}
