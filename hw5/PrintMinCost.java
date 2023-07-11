import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class PrintMinCost {
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("%d-by-%d image\n", picture.width(), picture.height());

        SeamCarver sc = new SeamCarver(picture);
        sc.findVerticalSeam();

        StdOut.printf("Printing minimum costs calculated for each pixel.\n");

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.0f ", sc.allMinCostAt[row][col]);
            StdOut.println();
        }
    }
}
