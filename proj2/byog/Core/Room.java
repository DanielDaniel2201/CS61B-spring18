package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Objects;
import java.util.Random;

import static byog.Core.Game.*;

public class Room {


    public static void DRAW(TETile[][] world, Position Pos, int width, int height) {
         Field.DRAW(world, Pos, width, height, outTile);
         int xNew = Pos.x + 1;
         int yNew = Pos.y + 1;
         Position PosNew = new Position(xNew, yNew);
         Field.DRAW(world, PosNew, width - 2, height - 2, inTile);
    }

    // returns a random number from a given range used for the number of rooms to be created
    private static int RandomRoomsNumber(int min, int max, Random RANDOM) {
        int returnValue = RANDOM.nextInt(100);
        while (returnValue <= min || returnValue >= max) {
            returnValue = RANDOM.nextInt(100);
        }
        return returnValue;
    }

    // makes sure that the rooms created do not overlap each other
    private static boolean Overlapping(int xPos, int yPos, int width, int length) {
        for (int i = xPos; i < xPos + width; i += 1) {
            for (int j = yPos; j < yPos + length; j += 1) {
                if (!Objects.equals(world[i][j].description(), "nothing")) {
                    return true;
                }
            }
        }
        return false;
    }

    //a method to fill the 2d world with random number of rooms of random width and length
    public static void DrawRandomRooms(TETile[][] WORLD) {
        int num = RandomRoomsNumber(15, 20, RANDOM);
        for (int i = 0; i < num; i += 1) {
            int x = RANDOM.nextInt(WIDTH - 4);
            int y = RANDOM.nextInt(HEIGHT - 4);
            int tmp1 = Math.min(15, WIDTH - x);
            int tmp2 = Math.min(15, HEIGHT - y);
            int w = RANDOM.nextInt(tmp1 - 4) + 4;
            int l = RANDOM.nextInt(tmp2 - 4) + 4;
            if (Overlapping(x, y, w, l)) {
                i -= 1;
                continue;
            }
            Room.DRAW(WORLD, new Position(x, y), w, l);
        }
    }
}
