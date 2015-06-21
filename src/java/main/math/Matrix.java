package math;

import java.util.Arrays;

public class Matrix {

    private final int yl;
    private final int xl;
    private final int[] arr;

    public Matrix(int height, int width, int... arr) {
        if (height * width != arr.length) {
            throw new IllegalArgumentException("arr.length is inconsistent with dimensions");
        }

        this.yl = height;
        this.xl = width;
        this.arr = humanFriendlyArrayToComputerFriendly(height, width, arr);
    }

    private static int[] humanFriendlyArrayToComputerFriendly(int height, int width, int[] arr) {
        final int[] shuffled = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            shuffled[i%width + (height-1-i/width) * width] = arr[i];
        }

        return shuffled;
    }

    protected Matrix(Matrix m) {
        this.xl = m.xl;
        this.yl = m.yl;
        this.arr = m.arr;
    }

    public int get(int x, int y) {
        return arr[idx(x, y)];
    }

    public void set(int x, int y, int value) {
        arr[idx(x, y)] = value;
    }

    private int idx(int x, int y) {
        return x + xl * y;
    }

    public Matrix mul(Matrix right) {
        final Matrix left = this;

        if (left.xl != right.yl) {
            throw new IllegalArgumentException("incompatible matrices");
        }

        final int rows = left.yl;
        final int cols = right.xl;
        final int common = left.xl;
        final Matrix mul = new Matrix(rows, cols, new int[rows * cols]);

        for (int x = 0; x < cols; x++) {
            for (int y=0; y < rows; y++) {
                int sum = 0;
                for (int i=0; i < common; i++) {
                    sum += left.get(i, y) * right.get(x, yl-1-i);
                }
                mul.set(x, y, sum);
            }
        }

        return mul;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int y=yl-1; y >= 0; y--) {
            for (int x=0; x < xl; x++) {
                sb.append(String.format("%2d ", get(x, y)));
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix = (Matrix) o;

        return yl == matrix.yl && xl == matrix.xl && Arrays.equals(arr, matrix.arr);
    }

    @Override
    public int hashCode() {
        int result = yl;
        result = 31 * result + xl;
        result = 31 * result + Arrays.hashCode(arr);
        return result;
    }

    public int getWidth() {
        return xl;
    }


    public int getHeight() {
        return yl;
    }

}
