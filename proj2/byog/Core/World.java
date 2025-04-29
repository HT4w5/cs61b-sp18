package byog.Core;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import byog.Core.Structures.*;

// World class that represents terrain data.
public class World {

    ////////////////////
    // BEGIN FIELDS //
    ////////////////////

    public final int WIDTH;
    public final int HEIGHT;
    public final long SEED;
    private TETile[][] world;

    private Vector<Structure> structures;

    private TERenderer ter;

    //////////////////
    // END FIELDS //
    //////////////////

    //////////////////////////
    // BEGIN CONSTRUCTORS //
    //////////////////////////

    /**
     * Counstructor specifying width and height of world.
     * 
     * @param w
     * @param h
     */
    public World(int w, int h, TERenderer ter, long seed) {
        WIDTH = w;
        HEIGHT = h;
        this.ter = ter;
        SEED = seed;

        world = new TETile[WIDTH][HEIGHT];
        structures = new Vector<>(32);
        ter.initialize(w, h);

        // Initialize void world.
        clear();
    }

    public World(int w, int h, TERenderer ter) {
        this(w, h, ter, System.currentTimeMillis());
    }

    ////////////////////////
    // END CONSTRUCTORS //
    ////////////////////////

    ///////////////////////////////////
    // BEGIN WORLD ARRAY MODIFIERS //
    ///////////////////////////////////

    // Set single tile in world array.
    public void setTile(int xPos, int yPos, TETile t) {
        // Check bounds.
        if (xPos < 0 || yPos < 0 || xPos >= WIDTH || yPos >= HEIGHT) {
            return;
        }
        world[xPos][yPos] = t;
    }

    // Set single tile in world array if previous tile is prev.
    public void setTileIf(int xPos, int yPos, TETile t, TETile prev) {
        // Check bounds.
        if (xPos < 0 || yPos < 0 || xPos >= WIDTH || yPos >= HEIGHT) {
            return;
        }
        if (world[xPos][yPos] == prev) {
            world[xPos][yPos] = t;
        }
    }

    // Set horizontal line of tiles in world array. Truncates out of bounds parts.
    public void setTileRow(int xPos, int yPos, int xEnd, TETile t) {
        // Basic bounds check.
        // Check yPos.
        if (yPos < 0 || yPos >= HEIGHT) {
            return; // Not in bounds. Do nothing.
        }
        // Limit xPos and xEnd to bounds first.
        // Then check whether combination is valid.
        if (xPos < 0) {
            xPos = 0;
        }
        if (xEnd >= WIDTH) {
            xEnd = WIDTH - 1;
        }
        if (xEnd < xPos) {
            return; // Empty row. Do nothing.
        }

        for (; xPos <= xEnd; ++xPos) {
            world[xPos][yPos] = t;
        }
    }

    // Set horizontal line of tiles in world array if previous tile is prev.
    // Truncates out of bounds parts.
    public void setTileRowIf(int xPos, int yPos, int xEnd, TETile t, TETile prev) {
        // Basic bounds check.
        // Check yPos.
        if (yPos < 0 || yPos >= HEIGHT) {
            return; // Not in bounds. Do nothing.
        }
        // Limit xPos and xEnd to bounds first.
        // Then check whether combination is valid.
        if (xPos < 0) {
            xPos = 0;
        }
        if (xEnd >= WIDTH) {
            xEnd = WIDTH - 1;
        }
        if (xEnd < xPos) {
            return; // Empty row. Do nothing.
        }

        for (; xPos <= xEnd; ++xPos) {
            if (world[xPos][yPos] == prev) {
                world[xPos][yPos] = t;
            }
        }
    }

    // Set vertical collumn of tiles in world array. Truncates out of bounds parts.
    public void setTileColl(int xPos, int yPos, int yEnd, TETile t) {
        // Basic bounds check.
        // Check xPos.
        if (xPos < 0 || xPos >= WIDTH) {
            return; // Not in bounds. Do nothing.
        }
        // Limit xPos and xEnd to bounds first.
        // Then check whether combination is valid.
        if (xPos < 0) {
            xPos = 0;
        }
        if (yEnd >= HEIGHT) {
            yEnd = HEIGHT - 1;
        }
        if (yEnd < yPos) {
            return; // Empty row. Do nothing.
        }

        for (; yPos <= yEnd; ++yPos) {
            world[xPos][yPos] = t;
        }
    }

    // Set vertical collumn of tiles in world array if previous tile is prev.
    // Truncates out of bounds parts.
    public void setTileCollIf(int xPos, int yPos, int yEnd, TETile t, TETile prev) {
        // Basic bounds check.
        // Check xPos.
        if (xPos < 0 || xPos >= WIDTH) {
            return; // Not in bounds. Do nothing.
        }
        // Limit xPos and xEnd to bounds first.
        // Then check whether combination is valid.
        if (xPos < 0) {
            xPos = 0;
        }
        if (yEnd >= HEIGHT) {
            yEnd = HEIGHT - 1;
        }
        if (yEnd < yPos) {
            return; // Empty row. Do nothing.
        }

        for (; yPos <= yEnd; ++yPos) {
            if (world[xPos][yPos] == prev) {
                world[xPos][yPos] = t;
            }
        }
    }

    /**
     * Fill rectangular area of tiles in world array. Truncates out of bounds parts.
     * 
     * @param xPos
     * @param yPos
     * @param xLen
     * @param yLen
     * @param t
     */
    public void fillTiles(int xPos, int yPos, int xEnd, int yEnd, TETile t) {
        // Basic bounds check.
        if (xPos < 0) {
            xPos = 0;
        }
        if (yPos < 0) {
            yPos = 0;
        }
        if (xEnd >= WIDTH) {
            xEnd = WIDTH - 1;
        }
        if (yEnd >= HEIGHT) {
            yEnd = HEIGHT - 1;
        }
        if (yEnd < yPos || xEnd < xPos) {
            return;
        }
        for (; xPos <= xEnd; ++xPos) {
            for (int yTmp = yPos; yTmp <= yEnd; ++yTmp) {
                world[xPos][yTmp] = t;
            }
        }
    }

    /**
     * Set all tiles to Tileset.NOTHING.
     */
    public void clear() {
        fillTiles(0, 0, WIDTH - 1, HEIGHT - 1, Tileset.NOTHING);
    }

    /////////////////////////////////
    // END WORLD ARRAY MODIFIERS //
    /////////////////////////////////

    //////////////////////////////////
    // BEGIN STRUCT QUEUE METHODS. //
    //////////////////////////////////

    /**
     * Add structure to world structure vector.
     * 
     * @param struct
     */
    public void addStructure(Structure struct) {
        structures.add(struct);
    }

    /**
     * Remove all structures from world structure vector.
     */
    public void clearStructures() {
        structures.clear();
    }

    public void clipStructures() {
        clear();
        for (Structure s : structures) {
            s.clip(this);
        }
    }

    ////////////////////////////////
    // END STRUCT QUEUE METHODS. //
    ////////////////////////////////

    //////////////////////////////////
    // BEGIN WORLD UPDATE METHODS //
    //////////////////////////////////

    // Render one frame from world array.
    public void tick() {
        ter.renderFrame(world);
    }
    ////////////////////////////////
    // END WORLD UPDATE METHODS //
    ////////////////////////////////

    ///////////////////////////
    // BEGIN DEBUG METHODS //
    ///////////////////////////

    // Get world array.
    public TETile[][] debugGetWorld() {
        return world;
    }

    // Get world width.
    public int debugGetWorldWidth() {
        return WIDTH;
    }

    // Get world height.
    public int debugGetWorldHeight() {
        return HEIGHT;
    }

    /////////////////////////
    // END DEBUG METHODS //
    /////////////////////////

}
