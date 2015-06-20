
public class Main {



    public static void main(String[] args) {

        final Side side = new Side(
                1, 1, 1, 1, 1,    1, 0, 1, 1,   0, 1, 0, 1,   0, 0, 0
        );

        System.out.println("original:\n");
        System.out.println(side);
        System.out.println("all orientations:\n");

        final Matrix[] orientations = Matrices2D.orientations(5);
        for (Matrix orientation : orientations) {
            System.out.println(side.transform(orientation));
        }
    }

}
