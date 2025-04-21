package byog.lab5;

import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

import javax.swing.text.Position;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WORLD_ORDER = 3;
    private static final int HEXAGON_ORDER = 3;

    public static void addHexagon(TETile[][] world, int xPos, int yPos, int len, TETile t) {
        // Draw left triangle.
        yPos = yPos + len - 1;
        int drawLen = 2;
        for (int i = 0; i < len - 1; ++i) {
            // Bounds check.
            if (xPos >= world.length) {
                break;
            } else {
                putTileCol(world, xPos, yPos, drawLen, t);
                ++xPos;
                --yPos;
                drawLen += 2;
            }
        }

        // Draw middle rectangle.
        for (int i = 0; i < len; ++i) {
            // Bounds check.
            if (xPos >= world.length) {
                break;
            } else {
                putTileCol(world, xPos, yPos, drawLen, t);
                ++xPos;
            }
        }

        // Draw right triangle.
        for (int i = 0; i < len - 1; ++i) {
            // Bounds check.
            if (xPos >= world.length) {
                break;
            } else {
                ++yPos;
                drawLen -= 2;
                putTileCol(world, xPos, yPos, drawLen, t);
                ++xPos;
            }
        }
    }

    private static void putTileCol(TETile[][] world, int xPos, int yPos, int len, TETile t) {
        TETile[] tgtCol = world[xPos];
        // Check end position.
        int yEnd = yPos + len - 1;
        if (yEnd >= tgtCol.length) {
            yEnd = tgtCol.length - 1;
        }
        for (; yPos <= yEnd; ++yPos) {
            tgtCol[yPos] = t;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();

        int order = 4;

        int w = 3 * order - 2;
        int h = 2 * order;

        ter.initialize(w, h);

        TETile[][] hexWorld = new TETile[w][h];

        populateWorld(hexWorld, Tileset.NOTHING);

        addHexagon(hexWorld, 0, 0, 5, Tileset.FLOWER);

        ter.renderFrame(hexWorld);
    }

    private static void populateWorld(TETile[][] world, TETile t) {
        for (int i = 0; i < world.length; ++i) {
            for (int j = 0; j < world[i].length; ++j) {
                world[i][j] = t;
            }
        }
    }
}