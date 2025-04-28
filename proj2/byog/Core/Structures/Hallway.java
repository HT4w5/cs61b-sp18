package byog.Core.Structures;

import byog.Core.AbstractStructure;
import byog.Core.Structure;
import byog.Core.World;
import byog.TileEngine.TETile;

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
