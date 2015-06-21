package space3d;

import math.Matrix;

public class Point extends Matrix {

    public Point(int x, int y, int z) {
        super(4, 1,
                x,
                y,
                z,
                1
        );
    }

    public Point(Matrix matrix) {
        super(assertMatrixIsPoint(matrix));
    }

    public int getX() {
        return get(0, 3);
    }

    public int getY() {
        return get(0, 2);
    }

    public int getZ() {
        return get(0, 1);
    }

    private static Matrix assertMatrixIsPoint(Matrix matrix) {
        if (matrix.getWidth() != 1 || matrix.getHeight() != 4) {
            throw new IllegalArgumentException("not a point");
        }

        if (matrix.get(0, 0) != 1) {
            throw new IllegalArgumentException("there should not be a need for normalization just yet");
        }

        return matrix;
    }
}
