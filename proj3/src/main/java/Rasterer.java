import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    /** Each tile is 256x256 pixels. */
    public static final int TILE_SIZE = 256;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();

        //make the numbers in params more accessible
        double[] arrayOfNums = convertToArray(params);
        System.out.println(Arrays.toString(arrayOfNums));
        double lrlon = arrayOfNums[0];
        double ullon = arrayOfNums[1];
        double w = arrayOfNums[2];
        double h = arrayOfNums[3];
        double ullat = arrayOfNums[4];
        double lrlat = arrayOfNums[5];

        //calculate the appropriate depth
        double usrLonDPP = lonDPPCalculator(lrlon, ullon, w);
        int depth = depthCalculation(usrLonDPP);
        System.out.println(depth);
        String[][] twoDStringArray = StringArray(depth, lrlon, ullon, ullat, lrlat);
        double[] fourCoors = fourCoors(depth, lrlon, ullon, ullat, lrlat);

        results.put("render_grid", twoDStringArray);
        results.put("raster_ul_lon", fourCoors[0]);
        results.put("raster_ul_lat", fourCoors[1]);
        results.put("raster_lr_lon", fourCoors[2]);
        results.put("raster_lr_lat", fourCoors[3]);
        results.put("depth", depth);
        results.put("query_success", true);

        System.out.println(Arrays.deepToString(twoDStringArray));
        System.out.println(results);
        return results;
    }

    private double lonDPPCalculator(double longitude1, double longitude2, double width) {
        double diff = Math.abs(longitude1 - longitude2);
        return diff / width;
    }

    private double[] convertToArray(Map<String, Double> params) {
        int i = 0;
        double[] arrayOfNumbers = new double[6];
        for (double num: params.values()) {
            arrayOfNumbers[i] = num;
            i += 1;
        }
        return arrayOfNumbers;
    }

    private int depthCalculation(double usrLonDPP) {
        int depth = 0;
        double lonDPP;
        do {
            double factor = Math.pow(2, depth);
            double lon1 = ROOT_LRLON / factor;
            double lon2 = ROOT_ULLON / factor;
            lonDPP = lonDPPCalculator(lon1, lon2, TILE_SIZE);
            depth += 1;
        } while (lonDPP > usrLonDPP);

        if (depth > 8) {
            return 7;
        } else {
            return depth - 1;
        }
    }

    private String[][] StringArray(int depth, double lrlon, double ullon, double ullat, double lrlat) {

        double factor = Math.pow(2, depth);
        double singleTileSideX = Math.abs(ROOT_LRLON - ROOT_ULLON) / factor;
        double singleTileSideY = Math.abs(ROOT_LRLAT - ROOT_ULLAT) / factor;

        if (lrlon > ROOT_LRLON) {
            lrlon = ROOT_LRLON - singleTileSideX;
        }
        if (ullon < ROOT_ULLON) {
            ullon = ROOT_ULLON;
        }
        if (lrlat < ROOT_LRLAT) {
            lrlat = ROOT_LRLAT - singleTileSideY;
        }
        if (ullat > ROOT_ULLAT) {
            ullat = ROOT_ULLAT;
        }

        double mapLeftToBoxLeft = Math.abs(ullon - ROOT_ULLON);
        double mapUpToBoxUp = Math.abs(ullat - ROOT_ULLAT);
        double mapLeftToBoxRight = Math.abs(lrlon - ROOT_ULLON);
        double mapUpToBoxDown = Math.abs(lrlat - ROOT_ULLAT);

        int ulTileX = (int)Math.floor(mapLeftToBoxLeft / singleTileSideX);
        int ulTileY = (int)Math.floor(mapUpToBoxUp / singleTileSideY);
        int lrTileX = (int)Math.floor(mapLeftToBoxRight / singleTileSideX);
        int lrTileY = (int)Math.floor(mapUpToBoxDown / singleTileSideY);

        System.out.println(ulTileX);
        System.out.println(ulTileY);
        System.out.println(lrTileX);
        System.out.println(lrTileY);

        int stringWidth = Math.abs(lrTileX - ulTileX) + 1;
        int stringHeight = Math.abs(ulTileY - lrTileY) + 1;
        String[][] StringArray = new String[stringHeight][stringWidth];

        for (int i = 0; i <= lrTileY - ulTileY; i += 1) {
            for (int j = 0; j <= lrTileX - ulTileX; j += 1) {
                int x = ulTileX + j;
                int y = ulTileY + i;
                StringArray[i][j] = "d" + depth + "_x" + x + "_y" + y + ".png";
            }
        }

        return StringArray;
    }

    private double[] fourCoors(int depth, double lrlon, double ullon, double ullat, double lrlat) {

        double factor = Math.pow(2, depth);
        double singleTileSideX = Math.abs(ROOT_LRLON - ROOT_ULLON) / factor;
        double singleTileSideY = Math.abs(ROOT_LRLAT - ROOT_ULLAT) / factor;

        if (lrlon > ROOT_LRLON) {
            lrlon = ROOT_LRLON - singleTileSideX;
        }
        if (ullon < ROOT_ULLON) {
            ullon = ROOT_ULLON - singleTileSideX;
        }
        if (lrlat < ROOT_LRLAT) {
            lrlat = ROOT_LRLAT - singleTileSideY;
        }
        if (ullat > ROOT_ULLAT) {
            ullat = ROOT_ULLAT - singleTileSideY;
        }

        double mapLeftToBoxLeft = Math.abs(ullon - ROOT_ULLON);
        double mapUpToBoxUp = Math.abs(ullat - ROOT_ULLAT);
        double mapLeftToBoxRight = Math.abs(lrlon - ROOT_ULLON);
        double mapUpToBoxDown = Math.abs(lrlat - ROOT_ULLAT);

        int ulTileX = (int)Math.floor(mapLeftToBoxLeft / singleTileSideX);
        int ulTileY = (int)Math.floor(mapUpToBoxUp / singleTileSideY);
        int lrTileX = (int)Math.floor(mapLeftToBoxRight / singleTileSideX);
        int lrTileY = (int)Math.floor(mapUpToBoxDown / singleTileSideY);

        double[] coorsArray = new double[4];
        coorsArray[0] = ulTileX * singleTileSideX + ROOT_ULLON;
        coorsArray[1] = ROOT_ULLAT - ulTileY * singleTileSideY;
        coorsArray[2] = (lrTileX  + 1) * singleTileSideX + ROOT_ULLON;
        coorsArray[3] = ROOT_ULLAT - (lrTileY + 1) * singleTileSideY;

        return coorsArray;
    }

}
