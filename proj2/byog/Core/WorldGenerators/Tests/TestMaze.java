package byog.Core.WorldGenerators.Tests;

import org.junit.Test;

import byog.Core.World;
import byog.Core.Structures.Room;
import byog.Core.WorldGenerators.Maze;
import byog.TileEngine.TERenderer;

public class TestMaze {
    public static void main(String[] args) {
        System.out.println("Beginning Maze generation test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(80, 45, ter);

        Maze mg = new Maze(myWorld);
        mg.generate();

        myWorld.tick();
    }

    @Test
    public void stressTest() {
        System.out.println("Beginning Maze generation test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(80, 45, ter);

        for (int i = 0; i < 100; ++i) {
            Maze mz = new Maze(myWorld);
            mz.generate();
            myWorld.clear();
        }
    }
}
