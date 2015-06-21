package space3d;

import math.Matrix;

public class Transformation extends Matrix {

    public Transformation(int... arr) {
        super(4, 4, arr);
    }

    public Transformation(Matrix matrix) {
        super(assertMatrixIsTransformation(matrix));
    }

    private static Matrix assertMatrixIsTransformation(Matrix matrix) {
        if (matrix.getWidth() != 4 || matrix.getHeight() != 4) {
            throw new IllegalArgumentException("not a 3d transformation");
        }

        return matrix;
    }

    public Transformation mul(Transformation right) {
        return new Transformation(super.mul(right));
    }
}
