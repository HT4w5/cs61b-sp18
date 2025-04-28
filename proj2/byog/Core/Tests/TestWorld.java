package byog.Core.Tests;

import org.junit.Test;

import byog.Core.World;
import byog.Core.Structures.Room;
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

        myWorld.clear();

        // setTileRow.
        myWorld.setTileRow(0, 0, 9, Tileset.FLOOR);
        myWorld.setTileRow(5, 5, 6, Tileset.FLOOR);
        world = myWorld.debugGetWorld();
        for (int i = 0; i < 10; ++i) {
            assertEquals(Tileset.FLOOR, world[i][0]);
        }
        assertEquals(Tileset.FLOOR, world[5][5]);
        assertEquals(Tileset.FLOOR, world[6][5]);

        myWorld.clear();

        // setTileColl.
        myWorld.setTileColl(0, 0, 9, Tileset.FLOOR);
        myWorld.setTileColl(5, 5, 6, Tileset.FLOOR);
        world = myWorld.debugGetWorld();
        for (int i = 0; i < 10; ++i) {
            assertEquals(Tileset.FLOOR, world[0][i]);
        }
        assertEquals(Tileset.FLOOR, world[5][5]);
        assertEquals(Tileset.FLOOR, world[5][6]);

        myWorld.clear();

        // setTileCollIf.
        myWorld.setTileRow(0, 4, 9, Tileset.FLOWER);
        myWorld.setTileRow(0, 6, 9, Tileset.FLOWER);

        myWorld.setTileCollIf(0, 0, 9, Tileset.FLOOR, Tileset.NOTHING);
        myWorld.setTileCollIf(5, 0, 9, Tileset.FLOOR, Tileset.FLOWER);

        world = myWorld.debugGetWorld();
        assertEquals(Tileset.FLOWER, world[0][4]);
        assertEquals(Tileset.FLOWER, world[0][6]);

        assertEquals(Tileset.FLOOR, world[5][4]);
        assertEquals(Tileset.FLOOR, world[5][4]);

        myWorld.clear();

        // setTileRowIf.
        myWorld.setTileColl(4, 0, 9, Tileset.FLOWER);
        myWorld.setTileColl(6, 0, 9, Tileset.FLOWER);

        myWorld.setTileRowIf(0, 0, 9, Tileset.FLOOR, Tileset.NOTHING);
        myWorld.setTileRowIf(0, 5, 9, Tileset.FLOOR, Tileset.FLOWER);

        world = myWorld.debugGetWorld();
        assertEquals(Tileset.FLOWER, world[4][0]);
        assertEquals(Tileset.FLOWER, world[6][0]);

        assertEquals(Tileset.FLOOR, world[4][5]);
        assertEquals(Tileset.FLOOR, world[4][5]);

        myWorld.clear();

        // fillTiles.
        myWorld.fillTiles(1, 1, 3, 3, Tileset.FLOOR);

        world = myWorld.debugGetWorld();

        for (int i = 1; i < 4; ++i) {
            for (int j = 1; j < 4; ++j) {
                assertEquals(Tileset.FLOOR, world[i][j]);
            }
        }

        // Test bounds check.
        myWorld.clear();

        boolean thrown = false;

        try {
            myWorld.fillTiles(4, 4, 5, 3, Tileset.FLOOR);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);

        thrown = false;
        try {
            myWorld.setTileRow(4, 4, 1, Tileset.FLOOR);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);

        thrown = false;
        try {
            myWorld.setTileColl(4, 4, 1, Tileset.FLOOR);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);

        thrown = false;
        try {
            myWorld.clear();
            myWorld.setTileRowIf(4, 4, 1, Tileset.FLOOR, Tileset.NOTHING);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);

        thrown = false;
        try {
            myWorld.clear();
            myWorld.setTileCollIf(4, 4, 1, Tileset.FLOOR, Tileset.NOTHING);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);
    }

    public static void main(String[] args) {
        System.out.println("Beginning world render test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(50, 50, ter);

        myWorld.clipStructures();

        myWorld.tick();
    }
}
