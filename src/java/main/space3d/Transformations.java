package space3d;

public class Transformations {

    private Transformations() {
    }

    // https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations

    public static Transformation rotationZ(double rads) {
        return new Transformation(
                round(Math.cos(rads)), round(-Math.sin(rads)),  0,  0,
                round(Math.sin(rads)), round(Math.cos(rads)),   0,  0,
                           0,                      0,           1,  0,
                           0,                      0,           0,  1
        );
    }

    public static Transformation rotationX(double rads) {
        return new Transformation(
                1,                      0,           0,                  0,
                0,       round(Math.cos(rads)), round(-Math.sin(rads)),  0,
                0,       round(Math.sin(rads)), round(Math.cos(rads)),   0,
                0,                      0,           0,                  1
        );
    }

    public static Transformation rotationY(double rads) {
        return new Transformation(
                round(Math.cos(rads)),  0, round(Math.sin(rads)),   0,
                           0,           1,           0,             0,
                round(-Math.sin(rads)), 0, round(Math.cos(rads)),   0,
                           0,           0,           0,             1
        );
    }



    // https://en.wikipedia.org/wiki/Translation_%28geometry%29#Matrix_representation

    public static Transformation translation(int x, int y, int z) {
        return new Transformation(
                1,  0,  0,  x,
                0,  1,  0,  y,
                0,  0,  1,  z,
                0,  0,  0,  1
        );
    }

    public static Transformation mirrorZ() {
        return new Transformation(
                0,  1,  0,  0,
                1,  0,  0,  0,
                0,  0,  1,  0,
                0,  0,  0,  1
        );
    }

    public static Transformation one() {
        return new Transformation(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    public static Transformation[] orientationsZ(int sideLength) {
        final Transformation[] transformations = new Transformation[8];

        for (int i=0; i<4; i++)  {
            final int sx = ((i/2 + i%2) % 2) * (sideLength-1);
            final int sy = i/2 * (sideLength-1);
            transformations[i] = Transformations.translation(sx, sy, 0).mul(Transformations.rotationZ(Math.PI / 2 * i));
        }

        final Transformation mirror = Transformations.mirrorZ();
        for (int i=4; i<8; i++) {
            transformations[i] = transformations[i-4].mul(mirror);
        }

        return transformations;
    }

    private static int round(double d) {
        return (int)Math.round(d);
    }
}
