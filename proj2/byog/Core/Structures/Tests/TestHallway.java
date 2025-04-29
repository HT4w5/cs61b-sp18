package byog.Core.Structures.Tests;

import byog.Core.World;
import byog.Core.Structures.Hallway;
import byog.Core.Structures.Room;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;

public class TestHallway {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);

        Hallway h1 = new Hallway(0, 3, false, 9, Tileset.FLOOR, Tileset.WALL);
        Hallway h2 = new Hallway(4, 2, true, 7, Tileset.FLOOR, Tileset.WALL);
        h1.clip(myWorld);
        h2.clip(myWorld);

        myWorld.tick();
    }
}
