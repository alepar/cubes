package happycube;

import space3d.Point;

public class SidePrinter {

    private final char[][] arr;
    private final int size;

    public SidePrinter(int sideSize, int rows, int cols) {
        this.size = sideSize;
        this.arr = createAndFill2DArrayWithSpaces(sideSize * rows, sideSize * cols);
    }

    private static char[][] createAndFill2DArrayWithSpaces(int rows, int cols) {
        final char[][] arr = new char[rows][];
        for (int i=0; i< rows; i++) {
            arr[i] = new char[cols];
            for (int j=0; j<arr[i].length; j++) {
                arr[i][j] = ' ';
            }
        }
        return arr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        int y=0;
        for (char[] row : arr) {
            int x=0;
            for (char c : row) {
                sb.append(c).append(' ');
                if(++x % size == 0) {
                    sb.append("  ");
                }
            }
            sb.append('\n');
            if (++y % size == 0) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public void print(Side side, int row, int col) {
        final int startRow = row*size;
        final int startCol = col*size;

        for (int y=size-1; y>=0; y--) {
            for (int x=0; x<size; x++) {
                char c;
                if (x > 0 && x < size-1 && y > 0 && y < size-1) {
                    c = '□';
                } else {
                    c = side.contains(new Point(x, y, 0)) ? '□' : '.';
                }
                arr[startRow+size-1-y][startCol+x] = c;
            }
        }

        arr[startRow+size/2][startCol+size/2] = (char)(side.getId() + '0');
    }
}
