package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

// World class that represents terrain data.
public class World {

    /*
     * BEGIN FIELDS.
     */

    private final int WIDTH;
    private final int HEIGHT;
    private TETile[][] world;

    public TERenderer ter;

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
    public World(int w, int h, TERenderer ter) {
        WIDTH = w;
        HEIGHT = h;
        this.ter = ter;

        world = new TETile[WIDTH][HEIGHT];
        ter.initialize(w, h);

        // Initialize void world.
        clear();
    }

    /*
     * END CONSTRUCTORS.
     */

    /*
     * BEGIN WORLD MODIFICATION METHODS.
     */

    // Set single tile in world array. No bounds check.
    public void setTile(int xPos, int yPos, TETile t) {
        world[xPos][yPos] = t;
    }

    // Set horizontal line of tiles in world array. No bounds check.
    public void setTileLine(int xPos, int yPos, int len, TETile t) {
        for (int i = 0; i < len; ++i) {
            world[xPos + i][yPos] = t;
        }
    }

    // Set vertical collumn of tiles in world array. No bounds check.
    public void setTileColl(int xPos, int yPos, int len, TETile t) {
        for (int i = 0; i < len; ++i) {
            world[xPos][yPos + i] = t;
        }
    }

    // Fill rectangular area of tiles in world array. No bounds check.
    public void fillTiles(int xPos, int yPos, int xLen, int yLen, TETile t) {
        for (int i = 0; i < xLen; ++i) {
            for (int j = 0; j < yLen; ++j) {
                world[i][j] = t;
            }
        }
    }

    public void clear() {
        fillTiles(0, 0, WIDTH, HEIGHT, Tileset.NOTHING);
    }

    /*
     * END WORLD MODIFICATION METHODS.
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
