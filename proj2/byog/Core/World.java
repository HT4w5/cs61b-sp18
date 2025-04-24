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
    //  BEGIN FIELDS  //
    ////////////////////

    public final int WIDTH;
    public final int HEIGHT;
    public final long SEED;
    private TETile[][] world;

    private Vector<Structure> structures;

    private TERenderer ter;

    //////////////////
    //  END FIELDS  //
    //////////////////

    //////////////////////////
    //  BEGIN CONSTRUCTORS  //
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
    //  END CONSTRUCTORS  //
    ////////////////////////

    ///////////////////////////////////
    //  BEGIN WORLD ARRAY MODIFIERS  //
    ///////////////////////////////////

    // Set single tile in world array. No bounds check.
    public void setTile(int xPos, int yPos, TETile t) {
        world[xPos][yPos] = t;
    }

    // Set horizontal line of tiles in world array. No bounds check.
    public void setTileRow(int xPos, int yPos, int len, TETile t) {
        for (int i = 0; i < len; ++i) {
            world[xPos + i][yPos] = t;
        }
    }

    // Set horizontal line of tiles in world array if previous tile is prev. No
    // bounds check.
    public void setTileRowIf(int xPos, int yPos, int len, TETile t, TETile prev) {
        for (int i = 0; i < len; ++i) {
            if (world[xPos + i][yPos] == prev) {
                world[xPos + i][yPos] = t;
            }
        }
    }

    // Set vertical collumn of tiles in world array. No bounds check.
    public void setTileColl(int xPos, int yPos, int len, TETile t) {
        for (int i = 0; i < len; ++i) {
            world[xPos][yPos + i] = t;
        }
    }

    // Set vertical collumn of tiles in world array if previous tile is prev. No
    // bounds check.
    public void setTileCollIf(int xPos, int yPos, int len, TETile t, TETile prev) {
        for (int i = 0; i < len; ++i) {
            if (world[xPos][yPos + i] == prev) {
                world[xPos][yPos + i] = t;
            }
        }
    }

    /**
     * Fill rectangular area of tiles in world array. No bounds check.
     * 
     * @param xPos
     * @param yPos
     * @param xLen
     * @param yLen
     * @param t
     */
    public void fillTiles(int xPos, int yPos, int xLen, int yLen, TETile t) {
        for (int i = 0; i < xLen; ++i) {
            for (int j = 0; j < yLen; ++j) {
                world[xPos + i][yPos + j] = t;
            }
        }
    }

    /**
     * Set all tiles to Tileset.NOTHING.
     */
    public void clear() {
        fillTiles(0, 0, WIDTH, HEIGHT, Tileset.NOTHING);
    }

    /////////////////////////////////
    //  END WORLD ARRAY MODIFIERS  //
    /////////////////////////////////

    //////////////////////////////////
    //  BEGIN STRUCT QUEUE METHODS. //
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
    //  END STRUCT QUEUE METHODS. //
    ////////////////////////////////


    //////////////////////////////////
    //  BEGIN WORLD UPDATE METHODS  //
    //////////////////////////////////

    // Render one frame from world array.
    public void tick() {
        ter.renderFrame(world);
    }
    ////////////////////////////////
    //  END WORLD UPDATE METHODS  //
    ////////////////////////////////

    ///////////////////////////
    //  BEGIN DEBUG METHODS  //
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
    //  END DEBUG METHODS  //
    /////////////////////////

}
