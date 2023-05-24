//package byog.Core;
//
//import byog.TileEngine.TERenderer;
//import byog.TileEngine.TETile;
//import byog.TileEngine.Tileset;
//
//import java.util.Random;
//
//import static byog.Core.HallwayHorizontal.DrawRandomHallways;
//import static byog.Core.HallwayVertical.DrawRandomHallwaysVertical;
//import static byog.Core.Room.DrawRandomRooms;
//
//public class WorldGenerator {
//    //the width of the entire world
//    public static final int WIDTH = 70;
//    //the height of the entire world
//    public static final int HEIGHT = 35;
//    private static final long SEED = 1236437659;
//    public static final Random RANDOM = new Random(SEED);
//    //the 2d world
//    public static TETile[][] world;
//    public static TETile outTile = Tileset.WALL;
//    public static TETile inTile = Tileset.FLOOR;
//
//    static TERenderer ter = new TERenderer();
//
//    //fill the empty world with all NOTHING tiles, some of which are to be overwritten
//    public static void Initialize() {
//        ter.initialize(WIDTH, HEIGHT);
//
//        world = new TETile[WIDTH][HEIGHT];
//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                world[x][y] = Tileset.NOTHING;
//            }
//        }
//    }
//
//    //make everything visible!
//    public static void main(String[] args) {
//        Initialize();
//        DrawRandomRooms(world, new Room(outTile, inTile));
//        DrawRandomHallways(world);
//        DrawRandomHallwaysVertical(world);
//        ter.renderFrame(world);
//        System.out.println(TETile.toString(world));
//    }
//}
