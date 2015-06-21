package happycube;

import space3d.Transformation;

import java.util.HashSet;
import java.util.Set;

import static happycube.CubeTopology.Face.Front;

public class CubeAssembler {

    private final Side[] sides;
    private final int size;

    public CubeAssembler(int size, Side[] sides) {
        this.size = size;
        this.sides = sides;
    }

    public Set<Cube> assemble() {
        final Cube prototype = new Cube(size);
        final Set<Cube> cubes = new HashSet<>();

        prototype.setFace(Front, sides[0]);
        brute(cubes, prototype, 1, 0b0111110);


        // looks like this does not add any unique assemblies, interesting
//        prototype.setFace(Front, sides[0].transform(Transformations.mirrorZ()));
//        brute(cubes, prototype, 1, 0b0111110);

        return cubes;
    }

    private void brute(Set<Cube> cubes, Cube prototype, int faceIndex, int availableSides) {
        if (faceIndex == CubeTopology.Face.values().length) {
            if(prototype.isWellformedCube()) {
                cubes.add(new Cube(prototype));
            }
        }

        for (int i=0; i<6; i++) {
            if ((availableSides & (1<<i)) > 0) {
                for (Transformation orientation : prototype.getSideOrientations()) {
                    prototype.setFace(
                            CubeTopology.Face.values()[faceIndex],
                            sides[i].transform(orientation)
                    );

                    if (prototype.isPotentiallyWellformed()) {
                        brute(cubes, prototype, faceIndex+1, availableSides ^ (1<<i));
                    }

                    prototype.setFace(
                            CubeTopology.Face.values()[faceIndex],
                            null
                    );
                }
            }
        }
    }

}
