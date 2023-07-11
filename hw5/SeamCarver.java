import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    Picture picture;
    Picture originalPicture;
    double[][] allMinCostAt;
    int[][] edgeTo;
    public SeamCarver(Picture picture) {
        originalPicture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture().width();
    }

    // height of current picture
    public int height() {
        return picture().height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        coordinatesValidity(x, y);

        return xEnergyHelper(x, y) + yEnergyHelper(x, y);
    }



    // sequence of indices for horizontal seam
    public int[] findVerticalSeam() {
        picture = originalPicture;
        allMinCostAt = new double[picture.height()][picture.width()];
        edgeTo = new int[height()][width()];
        return findVerticalSeamHelper(picture());
    }

    private int[] findVerticalSeamHelper(Picture picture) {
        int[] verticalSeam = new int[picture.height()];
        setAllMinCostAt();
        int lastIndex = getSmallestIndex(allMinCostAt[picture.height() - 1]);
        verticalSeam[picture.height() - 1] = lastIndex;

        int xCoordinate = lastIndex;
        for (int h = picture.height() - 1; h > 0; h -= 1) {
            int oneD = edgeTo[h][xCoordinate];
            xCoordinate = oneDToXCoordinate(oneD);
            verticalSeam[h - 1] = xCoordinate;
        }
        return verticalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findHorizontalSeam() {
        Picture tiltedPicture = tiltPicture(picture);
        picture = tiltedPicture;
        allMinCostAt = new double[height()][width()];
        edgeTo = new int[height()][width()];
        int[] seam = findVerticalSeamHelper(tiltedPicture);
        for (int i = 0; i < seam.length; i += 1) {
            seam[i] = width() - 1- seam[i];
        }
        findVerticalSeam();
        return seam;
    }

    private Picture tiltPicture(Picture originalPic) {
        Picture pic = new Picture(originalPic.height(), originalPic.width());
        for (int row = 0; row  < originalPic.height(); row += 1) {
            for (int col = 0; col < originalPic.width(); col += 1) {
                Color color = originalPic.get(col, row);
                pic.set(originalPic.height() - 1 - row, col, color) ;
            }
        }
        return pic;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        seamValidity(seam);

        SeamRemover.removeHorizontalSeam(picture(), seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        seamValidity(seam);

        SeamRemover.removeVerticalSeam(picture(), seam);
    }

    private double xEnergyHelper(int x, int y) {
        coordinatesValidity(x, y);

        int leftX, rightX, leftY, rightY;
        leftY = y;
        rightY = y;
        if (x == 0) {
            leftX = width() - 1;
        } else {
            leftX = x - 1;
        }
        if (x == width() - 1) {
            rightX = 0;
        } else {
            rightX = x + 1;
        }

        Color left = picture().get(leftX, leftY);
        Color right = picture().get(rightX, rightY);
        List<Integer> leftRGB = colorRGB(left);
        List<Integer> rightRGB = colorRGB(right);

        double deltaR = leftRGB.get(0) - rightRGB.get(0);
        double deltaG = leftRGB.get(1) - rightRGB.get(1);
        double deltaB = leftRGB.get(2) - rightRGB.get(2);

        return deltaR * deltaR + deltaG * deltaG + deltaB * deltaB;
    }

    private double yEnergyHelper(int x, int y) {
        coordinatesValidity(x, y);

        int upX, downX, upY, downY;
        upX = x;
        downX = x;
        if (y == 0) {
            upY = height() - 1;
        } else {
            upY = y - 1;
        }
        if (y == height() - 1) {
            downY = 0;
        } else {
            downY = y + 1;
        }

        Color up = picture().get(upX, upY);
        Color down = picture().get(downX, downY);
        List<Integer> upRBG = colorRGB(up);
        List<Integer> downRBG = colorRGB(down);

        double deltaR = upRBG.get(0) - downRBG.get(0);
        double deltaG = upRBG.get(1) - downRBG.get(1);
        double deltaB = upRBG.get(2) - downRBG.get(2);

        return deltaR * deltaR + deltaG * deltaG + deltaB * deltaB;
    }

    private List<Integer> colorRGB(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        List<Integer> rgb = new ArrayList<>();
        rgb.add(red);
        rgb.add(green);
        rgb.add(blue);
        return rgb;
    }

    private int getSmallestIndex(double[] arr) {
        int index = 0;
        double smallest = arr[index];

        for (int i = 1; i < arr.length; i += 1) {
            if (arr[i] < smallest) {
                index = i;
                smallest = arr[i];
            }
        }
        return index;
    }

    private void setAllMinCostAt() {
        for (int j = 0; j < height(); j += 1) {
            for (int i = 0; i < width(); i += 1) {
                if (j == 0) {
                    allMinCostAt[j][i] = energy(i, j);
                } else if (j == height()) {
                    break;
                } else {
                    double minCost = minOfAbove(i, j);
                    allMinCostAt[j][i] = energy(i, j) + minCost;
                }
            }
        }
    }

    private double minOfAbove(int i, int j) {
        coordinatesValidity(i, j);

        if (i == 0) {
            edgeTo[j][i] = -1;
            return minOfLeft2Above(i, j);
        } else if (i == width() - 1) {
            return minOfRight2Above(i, j);
        } else {
            return minOf3Above(i, j);
        }
    }

    private int twoDToOneD(int i, int j) {
        coordinatesValidity(i, j);

        return i + j * width();
    }

    private int oneDToXCoordinate(int i) {
        return i % width();
    }

    private double minOfLeft2Above(int i, int j) {
        coordinatesValidity(i, j);

        double cost1 = allMinCostAt[j - 1][i];
        double cost2 = allMinCostAt[j - 1][i + 1];
        if (cost1 <= cost2) {
            edgeTo[j][i] = twoDToOneD(i, j - 1);
        } else {
            edgeTo[j][i] = twoDToOneD(i + 1, j - 1);
        }
        return Math.min(cost1, cost2);
    }

    private double minOfRight2Above(int i, int j) {
        coordinatesValidity(i, j);

        double cost1 = allMinCostAt[j - 1][i];
        double cost2 = allMinCostAt[j - 1][i - 1];
        if (cost1 <= cost2) {
            edgeTo[j][i] = twoDToOneD(i, j - 1);
        } else {
            edgeTo[j][i] = twoDToOneD(i - 1, j - 1);
        }
        return Math.min(cost1, cost2);
    }

    private double minOf3Above(int i, int j) {
        coordinatesValidity(i, j);

        double cost1 = allMinCostAt[j - 1][i];
        double cost2 = allMinCostAt[j - 1][i - 1];
        double cost3 = allMinCostAt[j - 1][i + 1];
        double tmp = Math.min(cost1, cost2);
        double smallest = Math.min(tmp, cost3);
        if (smallest == cost1) {
            edgeTo[j][i] = twoDToOneD(i, j - 1);
        } else if(smallest == cost2) {
            edgeTo[j][i] = twoDToOneD(i - 1, j - 1);
        } else {
            edgeTo[j][i] = twoDToOneD(i + 1, j - 1);
        }
        return Math.min(tmp, cost3);
    }

    private void coordinatesValidity(int i, int j) {
        if (i < 0 || i > width() - 1 || j < 0 || j > height()) {
            throw new IndexOutOfBoundsException("i or j is out of the picture bounds");
        }
    }

    private void seamValidity(int[] seam) {
        for (int i = 0; i < seam.length - 1; i += 1) {
            int diff = seam[i] - seam[i + 1];
            if (diff < -1 || diff > 1) {
                throw new IllegalArgumentException("illegal seam!");
            }
        }
    }
}
