package byog.Core.Structures;

import byog.Core.AbstractStructure;
import byog.Core.Structure;
import byog.Core.World;

public class Hallway extends AbstractStructure {
    public Hallway(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void clip(World world) {

    }

    public boolean overlapsWith(Structure other) {
        return false;
    }

}
