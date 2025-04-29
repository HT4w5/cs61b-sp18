package byog.Core.Structures;

import byog.Core.AbstractStructure;
import byog.Core.Structure;
import byog.Core.World;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway extends AbstractStructure {
    private int len;
    private boolean vertical;

    private TETile floor;
    private TETile wall;

    public Hallway(int xPos, int yPos, boolean vertical, int len, TETile floor, TETile wall) {
        super(xPos, yPos);
        this.vertical = vertical;
        this.len = len;
        this.floor = floor;
        this.wall = wall;
    }

    public void clip(World world) {
        if (vertical) {
            // Draw floor.
            world.setTileColl(xPos, yPos, yPos + len - 1, floor);
            // Draw wall.
            world.setTileCollIf(xPos - 1, yPos - 1, yPos + len, wall, Tileset.NOTHING);
            world.setTileCollIf(xPos + 1, yPos - 1, yPos + len, wall, Tileset.NOTHING);
            world.setTileIf(xPos, yPos - 1, wall, Tileset.NOTHING);
            world.setTileIf(xPos, yPos + len, wall, Tileset.NOTHING);

        } else {
            // Draw floor.
            world.setTileRow(xPos, yPos, xPos + len - 1, floor);
            // Draw wall.
            world.setTileRowIf(xPos - 1, yPos - 1, xPos + len, wall, Tileset.NOTHING);
            world.setTileRowIf(xPos - 1, yPos + 1, xPos + len, wall, Tileset.NOTHING);
            world.setTileIf(xPos - 1, yPos, wall, Tileset.NOTHING);
            world.setTileIf(xPos + len, yPos, wall, Tileset.NOTHING);
        }
    }

    public boolean overlapsWith(Structure other) {
        return false;
    }

    public int getLength() {
        return len;
    }

    public boolean isVertical() {
        return vertical;
    }

}
