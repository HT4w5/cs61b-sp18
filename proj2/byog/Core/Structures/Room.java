package byog.Core.Structures;

import byog.Core.AbstractStructure;
import byog.Core.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room extends AbstractStructure {
    private int width;
    private int height;

    private TETile floor;
    private TETile wall;

    // Full constructor.
    public Room(int xPos, int yPos, int w, int h, TETile floor, TETile wall) {
        super(xPos, yPos);

        width = w;
        height = h;

        this.floor = floor;
        this.wall = wall;
    }

    // Constructor w/o Tile spec.
    public Room(int xPos, int yPos, int w, int h) {
        this(xPos, yPos, w, h, Tileset.FLOOR, Tileset.WALL);
    }

    @Override
    public void clip(World world) {
        // Fill floor area, overwriting previous tiles.
        world.fillTiles(xPos + 1, yPos + 1, width - 2, height - 2, floor);
        // Fill walls only if previously empty.
        world.setTileRowIf(xPos, yPos, width, wall, Tileset.NOTHING);
        world.setTileRowIf(xPos, yPos + height - 1, width, wall, Tileset.NOTHING);
        world.setTileCollIf(xPos, yPos, height, wall, Tileset.NOTHING);
        world.setTileCollIf(xPos + width - 1, yPos, height, wall, Tileset.NOTHING);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TETile getFloor() {
        return floor;
    }

    public TETile getWall() {
        return wall;
    }

}
