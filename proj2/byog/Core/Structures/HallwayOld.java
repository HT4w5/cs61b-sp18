package byog.Core.Structures;

import byog.Core.AbstractStructure;
import byog.Core.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.Structure;

public class HallwayOld extends AbstractStructure {
    private int len;
    private boolean vertical;

    private TETile floor;
    private TETile wall;

    // Full constructor.
    public HallwayOld(int xPos, int yPos, int len, boolean vertical, TETile floor, TETile wall) {
        super(xPos, yPos);

        this.len = len;
        this.vertical = vertical;

        this.floor = floor;
        this.wall = wall;
    }

    // Constructor w/o Tile spec.
    public HallwayOld(int xPos, int yPos, int len, boolean vertical) {
        this(xPos, yPos, len, vertical, Tileset.FLOOR, Tileset.WALL);
    }

    @Override
    public void clip(World world) {
        int width, height;
        if (vertical) {
            width = 3;
            height = len;
        } else {
            height = 3;
            width = len;
        }
        // Fill floor area, overwriting previous tiles.
        world.fillTiles(xPos + 1, yPos + 1, width - 2, height - 2, floor);
        // Fill walls only if previously empty.
        world.setTileRowIf(xPos, yPos, width, wall, Tileset.NOTHING);
        world.setTileRowIf(xPos, yPos + height - 1, width, wall, Tileset.NOTHING);
        world.setTileCollIf(xPos, yPos, height, wall, Tileset.NOTHING);
        world.setTileCollIf(xPos + width - 1, yPos, height, wall, Tileset.NOTHING);
    }

    public int getLen() {
        return len;
    }

    public boolean isVertical() {
        return vertical;
    }

    public TETile getFloor() {
        return floor;
    }

    public TETile getWall() {
        return wall;
    }

    public boolean overlapsWith(Structure other) {
        return false;
    }
}
