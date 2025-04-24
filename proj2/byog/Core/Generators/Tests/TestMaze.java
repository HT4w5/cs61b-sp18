package byog.Core.Generators.Tests;

import byog.Core.World;
import byog.Core.Generators.Maze;
import byog.Core.Structures.Room;
import byog.TileEngine.TERenderer;

public class TestMaze {
    public static void main(String[] args) {
        System.out.println("Beginning Maze generation test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(80, 45, ter);

        Maze mg = new Maze();
        mg.generate(myWorld);

        myWorld.clipStructures();

        myWorld.tick();
    }
}
