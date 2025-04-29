package byog.Core.Structures;

import byog.Core.AbstractStructure;
import byog.Core.Structure;
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
        // Fill floor area, which is one pixel thinner than the room on four sides.
        world.fillTiles(xPos + 1, yPos + 1, xPos + width - 2, yPos + height - 2, floor);
        // Fill walls.
        world.setTileRowIf(xPos, yPos, xPos + width - 1, wall, Tileset.NOTHING);
        world.setTileRowIf(xPos, yPos + height - 1, xPos + width - 1, wall, Tileset.NOTHING);
        world.setTileCollIf(xPos, yPos, yPos + height - 1, wall, Tileset.NOTHING);
        world.setTileCollIf(xPos + width - 1, yPos, yPos + height - 1, wall, Tileset.NOTHING);

        /*
         * Old implemention.
         * // Fill floor area, overwriting previous tiles.
         * world.fillTiles(xPos + 1, yPos + 1, width - 2 - (Math.max(xTrunc, 1) - 1),
         * height - 2 - (Math.max(yTrunc, 1) - 1), floor);
         * world.fillTiles(xTruncHigh, yTruncHigh, xTruncLow, yTruncLow, floor);
         * // Fill walls only if previously empty.
         * world.setTileRowIf(xPos, yPos, width - xTrunc, wall, Tileset.NOTHING);
         * if (yTrunc < 1) {
         * world.setTileRowIf(xPos, yPos + height - 1, width - xTrunc, wall,
         * Tileset.NOTHING);
         * }
         * world.setTileCollIf(xPos, yPos, height - yTrunc, wall, Tileset.NOTHING);
         * if (xTrunc < 1) {
         * world.setTileCollIf(xPos + width - 1, yPos, height - yTrunc, wall,
         * Tileset.NOTHING);
         * }
         */
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

    /**
     * Check whether this overlaps with other.
     * 
     * @param other Other Room.
     * @return Whether this overlaps with other.
     * @throws IllegalArgumentException if
     *                                  {@code other.getClass() != this.getClass()}
     */
    public boolean overlapsWith(Structure other) {
        // Enforce other's type to Room.
        if (other.getClass() != this.getClass()) {
            throw new IllegalArgumentException("Can't check overlapping between "
                    + other.getClass()
                    + " and "
                    + this.getClass());
        }

        Room otherRoom = (Room) other;

        // Calculate boundaries
        int thisX1 = getXPos();
        int thisY1 = getYPos();
        int thisX2 = thisX1 + getWidth() - 1;
        int thisY2 = thisY1 + getHeight() - 1;

        int otherX1 = otherRoom.getXPos();
        int otherY1 = otherRoom.getYPos();
        int otherX2 = otherX1 + otherRoom.getWidth() - 1;
        int otherY2 = otherY1 + otherRoom.getHeight() - 1;

        return !(thisX2 < otherX1 ||
                otherX2 < thisX1 ||
                thisY2 < otherY1 ||
                otherY2 < thisY1);
    }

}
