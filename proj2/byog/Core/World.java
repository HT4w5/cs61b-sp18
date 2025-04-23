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

    /*
     * BEGIN FIELDS.
     */

    private final int WIDTH;
    private final int HEIGHT;
    private final long SEED;
    private TETile[][] world;

    private Vector<Structure> structures;

    private TERenderer ter;

    /*
     * END FIELDS.
     */

    /*
     * BEGIN CONSTRUCTORS.
     */

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

    /*
     * END CONSTRUCTORS.
     */

    /*
     * BEGIN WORLD ARRAY MODIFIERS.
     */

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

    // Fill rectangular area of tiles in world array. No bounds check.
    public void fillTiles(int xPos, int yPos, int xLen, int yLen, TETile t) {
        for (int i = 0; i < xLen; ++i) {
            for (int j = 0; j < yLen; ++j) {
                world[xPos + i][yPos + j] = t;
            }
        }
    }

    public void clear() {
        fillTiles(0, 0, WIDTH, HEIGHT, Tileset.NOTHING);
    }

    /*
     * END WORLD ARRAY MODIFIERS.
     */

    /*
     * BEGIN STRUCT QUEUE METHODS.
     */

    // Add structure.
    public void addStructure(Structure struct) {
        structures.add(struct);
    }

    // Clear structures.
    public void clearStructures() {
        structures.clear();
    }

    public void clipStructures() {
        clear();
        for (Structure s : structures) {
            s.clip(this);
        }
    }

    /*
     * END STRUCT QUEUE METHODS.
     */

    /*
     * BEGIN WORLD GENERATION METHODS.
     */

    // Generate random structure vector with Rooms from seed.
    public void genRoomsWorld() {
        Random random = new Random(SEED);
        // Generate Rooms.
        int numRooms = RandomUtils.uniform(random, 16, 32);

        int xPos, yPos, w, h;
        for (int i = 0; i < numRooms; ++i) {
            w = RandomUtils.uniform(random, 5, 10);
            h = RandomUtils.uniform(random, 5, 10);
            xPos = RandomUtils.uniform(random, WIDTH - w + 1);
            yPos = RandomUtils.uniform(random, HEIGHT - h + 1);
            structures.add(new Room(xPos, yPos, w, h));
        }

        // Generate hallways.
        int x, y, len;
        double factor;
        Room r;
        for (int i = 0; i < numRooms; ++i) {
            r = (Room) structures.get(i);
            // Vertical.
            factor = RandomUtils.uniform(random);
            if (factor > 0.5) {
                x = RandomUtils.uniform(random, r.getXPos(), Math.min(r.getXPos() + r.getWidth() - 1, WIDTH - 3));
                y = RandomUtils.uniform(random, 0, Math.min(r.getYPos() + r.getHeight() - 1, HEIGHT - 3));
                len = RandomUtils.uniform(random, 3, Math.max(HEIGHT - y - 1, 4));
                structures.add(new Hallway(x, y, len, true));
            }
            // Horizontal.
            factor = RandomUtils.uniform(random);
            factor = RandomUtils.uniform(random);
            if (factor > 0.5) {
                y = RandomUtils.uniform(random, r.getYPos(), Math.min(r.getYPos() + r.getHeight() - 1, HEIGHT - 3));
                x = RandomUtils.uniform(random, 0, Math.min(r.getXPos() + r.getWidth() - 1, WIDTH - 3));
                len = RandomUtils.uniform(random, 3, Math.max(WIDTH - x - 1, 4));
                structures.add(new Hallway(x, y, len, false));
            }
        }
    }
    /*
     * END WORLD GENERATION METHODS.
     */

    /*
     * BEGIN WORLD UPDATE METHODS.
     */

    // Render one frame from world array.
    public void tick() {
        ter.renderFrame(world);
    }
    /*
     * END WORLD UPDATE METHODS.
     */

    /*
     * BEGIN DEBUG METHODS.
     */

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

    /*
     * END DEBUG METHODS.
     */

}
