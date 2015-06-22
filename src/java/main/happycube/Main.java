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
        final int iterations = 100;
        final long start = System.nanoTime();
        for (int i=0; i< iterations; i++) {
            final CubeAssembler assembler = new CubeAssembler(5, sides);
            cubes = assembler.assemble();
        }
        final long end = System.nanoTime();
        System.out.println();

        System.out.println(cubes.size() + " unique cubes, took " + (end-start)/1000000/ iterations /1000.0 + "s per iter\n\n");
        for (Cube cube : cubes) {
            System.out.println(cube);
            System.out.println("--------------\n");
        }
    }

}
