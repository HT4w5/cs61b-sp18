package byog.Core.WorldGenerators.Tests;

import org.junit.Test;

import byog.Core.World;
import byog.Core.WorldGenerators.Dungeon;
import byog.TileEngine.TERenderer;

public class TestDungeon {
    public static void main(String[] args) {
        System.out.println("Beginning Maze generation test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(191, 91, ter, 114514);

        Dungeon mg = new Dungeon(myWorld, 200, 3, 10, 3, 10);
        mg.generate();

        myWorld.tick();
    }

    @Test
    public void stressTest() {
        System.out.println("Beginning Maze generation test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(81, 45, ter);

        for (int i = 0; i < 100; ++i) {
            Dungeon mz = new Dungeon(myWorld);
            mz.generate();
            myWorld.clear();
        }
    }
}
