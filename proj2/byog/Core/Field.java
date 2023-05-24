package byog.Core;

import byog.TileEngine.TETile;

public class Field {
    public static void DRAW(TETile[][]world, Position Pos, int Width, int Height, TETile tile) {
        int x = Pos.x;
        int y = Pos.y;
        for (int i = y; i < y + Height; i += 1) {
            SingleLine.DRAW(world, new Position(x, i), Width, tile);
        }
    }
}
