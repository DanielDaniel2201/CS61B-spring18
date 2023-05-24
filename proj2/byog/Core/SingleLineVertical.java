package byog.Core;

import byog.TileEngine.TETile;

public class SingleLineVertical {
    public static void DRAW(TETile[][] world, Position PosLeft, int length, TETile tile) {
        for (int i = PosLeft.y; i < length + PosLeft.y; i += 1) {
            world[PosLeft.x][i] = tile;
        }
    }
}
