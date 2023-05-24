package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Objects;
import java.util.Random;

import static byog.Core.Game.*;

public class HallwayHorizontal {

    private static int RandomHallwaysNumber(int min, int max) {
        int returnValue = RANDOM.nextInt(100);
        while (returnValue <= min || returnValue >= max) {
            returnValue = RANDOM.nextInt(100);
        }
        return returnValue;
    }

    public static void DrawRandomHallways(TETile[][] world) {
        int num = RandomHallwaysNumber(8, 15);
        for (int i = 0; i < num; i += 1) {
            DrawRandomHallway(world);
        }
    }

    public static void DrawRandomHallway(TETile[][] world) {
        Position sp1 = Sp1Pos();
        Position sp2 = Sp2Pos(sp1);
        int l = sp2.x - sp1.x + 1;
        DrawSpecHallway(world, sp1, l);
    }

    //given the left middle position and the length, add a hallway to the rendered world
    public static void DrawSpecHallway(TETile[][] world, Position LeftMidPos, int length) {
        SingleLine.DRAW(world, new Position(LeftMidPos.x, LeftMidPos.y - 1), length, outTile);
        SingleLine.DRAW(world, new Position(LeftMidPos.x, LeftMidPos.y), length, inTile);
        SingleLine.DRAW(world, new Position(LeftMidPos.x, LeftMidPos.y + 1), length, outTile);
    }

    //returns a position of special point 1 on an appropriate place of a room edge and on its way there will be a valid special point 2
    private static Position Sp1Pos() {
        int x = RANDOM.nextInt(WIDTH - 2);
        int y = RANDOM.nextInt(1, HEIGHT - 2);
        return Purify1(x, y);
    }

    private static Position Sp2Pos(Position sp1) {
        for (int i = sp1.x; i < WIDTH; i += 1) {
            if (isSp2Pos(i, sp1.y)) {
                return new Position(i, sp1.y);
            }
        }
        return null;
    }

    //given a random coordinate x y of the special point 1 and checks whether on its way there will be a valid special point 2
    private static boolean existsSp2Pos(int x, int y) {
        for (int i = x + 1; i < WIDTH; i += 1) {
           if (isSp2Pos(i, y)) {
               return true;
           }
        }
        return false;
    }

    //rearranges the random coordinates of special point 1 so that its location is appropriate and on its way there will be a valid special point 2
    //returns the position of rearranged coordinates of special point 1
    private static Position Purify1(int xInput, int yInput) {
        boolean var1 = Objects.equals(world[xInput][yInput].description(), Game.outTile.description());
        boolean var2 = Objects.equals(world[xInput][yInput - 1].description(), Game.outTile.description());
        boolean var3 = Objects.equals(world[xInput][yInput + 1].description(), Game.outTile.description());
        boolean var4 = Objects.equals(world[xInput + 1][yInput].description(), Tileset.NOTHING.description());
        boolean var5 = existsSp2Pos(xInput, yInput);
        if (var1 && var2 && var3 && var4 && var5) {
            return new Position(xInput, yInput);
        } else {
            return Purify1(RANDOM.nextInt(WIDTH - 2), RANDOM.nextInt(1, HEIGHT - 2));
        }
    }

    //given a pair of coordinates and checks whether this point is a special point 2
    private static boolean isSp2Pos(int xInput, int yInput) {
        boolean var1 = Objects.equals(world[xInput][yInput].description(), Game.outTile.description());
        boolean var2 = Objects.equals(world[xInput][yInput - 1].description(), Game.outTile.description());
        boolean var3 = Objects.equals(world[xInput][yInput + 1].description(), Game.outTile.description());
        boolean var4 = Objects.equals(world[xInput - 1][yInput].description(), Tileset.NOTHING.description());
            return var1 && var2 && var3 && var4;
    }
}
