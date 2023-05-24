package byog.Core;

import byog.TileEngine.TETile;

public class SingleLine {
    public static void DRAW(TETile[][] world, Position PosLeft, int length, TETile tile) {
        for (int i = PosLeft.x; i < length + PosLeft.x; i += 1) {
            world[i][PosLeft.y] = tile;
        }
    }
}
