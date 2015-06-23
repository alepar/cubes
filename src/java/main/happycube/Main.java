package happycube;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int count = 0;
        final Side[] sides = new Side[]{
            new Side(count++,
                    0, 0, 1, 0, 0,
                    0, 0, 0, 0, 0,
                    1, 0, 0, 0, 1,
                    0, 0, 0, 0, 0,
                    0, 0, 1, 0, 0
            ),
            new Side(count++,
                    1, 0, 1, 0, 1,
                    1, 0, 0, 0, 1,
                    0, 0, 0, 0, 0,
                    1, 0, 0, 0, 1,
                    1, 0, 1, 0, 1
            ),
            new Side(count++,
                    0, 0, 1, 0, 0,
                    0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 1,
                    0, 0, 1, 0, 0
            ),
            new Side(count++,
                    0, 1, 0, 1, 0,
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0,
                    1, 1, 0, 1, 0
            ),
            new Side(count++,
                    0, 1, 0, 1, 0,
                    1, 0, 0, 0, 1,
                    0, 0, 0, 0, 0,
                    1, 0, 0, 0, 1,
                    1, 0, 1, 0, 0
            ),
            new Side(count++,
                    0, 1, 0, 1, 0,
                    0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 1,
                    1, 1, 0, 1, 1
            )
        };

        // starting from a different side speeds bruteforce up ~5x
        final Side tmp = sides[0];
        sides[0] = sides[5];
        sides[5] = tmp;

        final SidePrinter printer = new SidePrinter(5, 2, 3);
        for (int i = 0; i < sides.length; i++) {
            printer.print(sides[i], i/3, i%3);
        }
        System.out.println("input\n\n" + printer + "--------------\n");

        Set<Cube> cubes = null;
        int iterations = 0;
        long warmupEndNanos = 0;
        long curNanos;
        final long startNanos = System.nanoTime();
        while ((curNanos = System.nanoTime()) < startNanos + 2_000_000_000L) {
            if(warmupEndNanos == 0 && curNanos > startNanos + 1_000_000_000L) {
                warmupEndNanos = curNanos;
            }

            final CubeAssembler assembler = new CubeAssembler(5, sides);
            cubes = assembler.assemble();

            if (warmupEndNanos > 0) {
                iterations++;
            }
        }
        final long endNanos = System.nanoTime();

        System.out.format("%d unique cubes, %.1fms to solve (%d iters in %.1fs)\n\n", cubes.size(), (endNanos - warmupEndNanos) / 100_000 / iterations / 10.0, iterations, (endNanos-warmupEndNanos)/100_000_000L/10.0);
        for (Cube cube : cubes) {
            System.out.println(cube);
            System.out.println("--------------\n");
        }
    }

}
