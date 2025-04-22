package byog.Core;

import org.junit.Test;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import static org.junit.Assert.*;

public class TestWorld {
    @Test
    public void testWorldConstructor() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(32, 16, ter);

        int worldW = myWorld.debugGetWorldWidth();
        int worldH = myWorld.debugGetWorldHeight();

        assertEquals("World width mismatch", 32, worldW);
        assertEquals("World height mismatch", 16, worldH);

        TETile[][] world = myWorld.debugGetWorld();
        assertNotNull("Null world array", world);

        for (int i = 0; i < worldW; ++i) {
            for (int j = 0; j < worldH; ++j) {
                assertEquals("World not nothing-ed on init", Tileset.NOTHING, world[i][j]);
            }
        }
    }

    @Test
    public void testWorldModifiers() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        // setTile.
        myWorld.setTile(0, 0, Tileset.FLOOR);
        myWorld.setTile(5, 3, Tileset.FLOOR);
        myWorld.setTile(9, 9, Tileset.FLOWER);
        world = myWorld.debugGetWorld();
        String msg = "setTile mismatch";
        assertEquals(msg, world[0][0], Tileset.FLOOR);
        assertEquals(msg, world[5][3], Tileset.FLOOR);
        assertEquals(msg, world[9][9], Tileset.FLOWER);

        // setTileLine.

    }

    public static void main(String[] args) {
        System.out.println("Beginning world render test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(64, 36, ter);

        myWorld.tick();
    }
}
