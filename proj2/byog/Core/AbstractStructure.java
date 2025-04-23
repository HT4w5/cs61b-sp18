package byog.Core;

public abstract class AbstractStructure implements Structure {
    protected int xPos;
    protected int yPos;

    public AbstractStructure(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public int getXPos() {
        return xPos;
    }

    @Override
    public int getYPos() {
        return yPos;
    }

}