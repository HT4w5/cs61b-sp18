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
    public void testSetTile() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        /* Test basic tile setting with different tile types */
        // Test floor tile
        myWorld.setTile(0, 0, Tileset.FLOOR);
        // Test wall tile
        myWorld.setTile(5, 3, Tileset.WALL); 
        // Test decorative tile
        myWorld.setTile(9, 9, Tileset.FLOWER);
        world = myWorld.debugGetWorld();
        
        // Verify tiles were set correctly
        assertEquals("Floor tile not set correctly", Tileset.FLOOR, world[0][0]);
        assertEquals("Wall tile not set correctly", Tileset.WALL, world[5][3]);
        assertEquals("Flower tile not set correctly", Tileset.FLOWER, world[9][9]);

        /* Test edge cases */
        // Test minimum bounds
        myWorld.setTile(0, 0, Tileset.NOTHING);
        assertEquals("Minimum bounds tile not set", Tileset.NOTHING, world[0][0]);
        
        // Test maximum bounds
        myWorld.setTile(9, 9, Tileset.NOTHING);
        assertEquals("Maximum bounds tile not set", Tileset.NOTHING, world[9][9]);

        /* Test invalid coordinates */
        // Test negative coordinates (should silently fail)
        TETile[][] before = myWorld.debugGetWorld();
        myWorld.setTile(-1, -1, Tileset.FLOOR);
        world = myWorld.debugGetWorld();
        assertArrayEquals("Negative coordinates should not modify world", before, world);
        
        // Test coordinates beyond world size (should silently fail)
        myWorld.setTile(10, 10, Tileset.FLOOR);
        assertArrayEquals("Out of bounds coordinates should not modify world", before, world);

        // Test null tile (should throw exception)
        try {
            myWorld.setTile(5, 5, null);
            fail("Should throw exception for null tile");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testSetTileRow() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        /* Test full row */
        myWorld.setTileRow(0, 0, 9, Tileset.FLOOR);
        world = myWorld.debugGetWorld();
        for (int i = 0; i < 10; ++i) {
            assertEquals("Full row not set correctly at x=" + i, 
                        Tileset.FLOOR, world[i][0]);
        }

        /* Test partial row */
        myWorld.clear();
        myWorld.setTileRow(5, 5, 6, Tileset.WALL);
        world = myWorld.debugGetWorld();
        assertEquals("Row start not set correctly", Tileset.WALL, world[5][5]);
        assertEquals("Row middle not set correctly", Tileset.WALL, world[6][5]);
        assertEquals("Tile before start should not be set", Tileset.NOTHING, world[4][5]);
        assertEquals("Tile after end should not be set", Tileset.NOTHING, world[7][5]);

        /* Test edge cases */
        // Test single tile row
        myWorld.clear();
        myWorld.setTileRow(5, 5, 5, Tileset.FLOWER);
        assertEquals("Single tile row not set", Tileset.FLOWER, world[5][5]);

        // Test row at min y coordinate
        myWorld.setTileRow(0, 0, 5, Tileset.FLOOR);
        assertEquals("Min y coordinate row not set", Tileset.FLOOR, world[0][0]);

        // Test row at max y coordinate
        myWorld.setTileRow(0, 9, 5, Tileset.FLOOR);
        assertEquals("Max y coordinate row not set", Tileset.FLOOR, world[5][9]);

        /* Test invalid parameters */
        // Test backwards row (start > end)
        TETile[][] before = myWorld.debugGetWorld();
        myWorld.setTileRow(6, 5, 4, Tileset.FLOOR);
        assertArrayEquals("Backwards row should not modify world", before, world);

        // Test out of bounds row
        myWorld.setTileRow(-1, 5, 11, Tileset.FLOOR);
        assertArrayEquals("Out of bounds row should not modify world", before, world);
    }

    @Test
    public void testSetTileColl() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        // Test full column
        myWorld.setTileColl(0, 0, 9, Tileset.FLOOR);
        world = myWorld.debugGetWorld();
        for (int i = 0; i < 10; ++i) {
            assertEquals("Column not set correctly at y=" + i, Tileset.FLOOR, world[0][i]);
        }

        // Test partial column
        myWorld.clear();
        myWorld.setTileColl(5, 5, 6, Tileset.FLOOR);
        world = myWorld.debugGetWorld();
        assertEquals("Column start not set", Tileset.FLOOR, world[5][5]);
        assertEquals("Column end not set", Tileset.FLOOR, world[5][6]);
        assertEquals("Tile after end should not be set", Tileset.NOTHING, world[5][7]);
    }

    @Test
    public void testSetTileIf() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        // Setup initial state
        myWorld.setTile(5, 5, Tileset.FLOWER);
        
        // Test conditional set (should change)
        myWorld.setTileIf(5, 5, Tileset.FLOOR, Tileset.FLOWER);
        world = myWorld.debugGetWorld();
        assertEquals("Tile should be changed", Tileset.FLOOR, world[5][5]);

        // Test conditional set (should not change)
        myWorld.setTileIf(5, 5, Tileset.WALL, Tileset.FLOWER);
        assertEquals("Tile should not be changed", Tileset.FLOOR, world[5][5]);
    }

    @Test
    public void testSetTileRowIf() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        // Setup initial state
        myWorld.setTileRow(0, 5, 9, Tileset.FLOWER);
        
        // Test conditional row set
        myWorld.setTileRowIf(0, 5, 9, Tileset.FLOOR, Tileset.FLOWER);
        world = myWorld.debugGetWorld();
        for (int i = 0; i < 10; ++i) {
            assertEquals("Row not set correctly at x=" + i, Tileset.FLOOR, world[i][5]);
        }

        // Test with non-matching condition
        myWorld.setTileRowIf(0, 5, 9, Tileset.WALL, Tileset.FLOWER);
        for (int i = 0; i < 10; ++i) {
            assertEquals("Row should not be changed", Tileset.FLOOR, world[i][5]);
        }
    }

    @Test
    public void testSetTileCollIf() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        // Setup initial state
        myWorld.setTileColl(5, 0, 9, Tileset.FLOWER);
        
        // Test conditional column set
        myWorld.setTileCollIf(5, 0, 9, Tileset.FLOOR, Tileset.FLOWER);
        world = myWorld.debugGetWorld();
        for (int i = 0; i < 10; ++i) {
            assertEquals("Column not set correctly at y=" + i, Tileset.FLOOR, world[5][i]);
        }

        // Test with non-matching condition
        myWorld.setTileCollIf(5, 0, 9, Tileset.WALL, Tileset.FLOWER);
        for (int i = 0; i < 10; ++i) {
            assertEquals("Column should not be changed", Tileset.FLOOR, world[5][i]);
        }
    }

    @Test
    public void testFillTiles() {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);
        TETile[][] world;

        // Test basic fill
        myWorld.fillTiles(1, 1, 3, 3, Tileset.FLOOR);
        world = myWorld.debugGetWorld();

        for (int i = 1; i < 4; ++i) {
            for (int j = 1; j < 4; ++j) {
                assertEquals("Area not filled correctly", Tileset.FLOOR, world[i][j]);
            }
        }

        // Test edges not filled
        assertEquals("Edge should not be filled", Tileset.NOTHING, world[0][0]);
        assertEquals("Edge should not be filled", Tileset.NOTHING, world[4][4]);

        // Test bounds check
        boolean thrown = false;
        try {
            myWorld.fillTiles(-2, 4, 11, 3, Tileset.FLOOR);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse("Should handle out of bounds silently", thrown);
    }

    public static void main(String[] args) {
        System.out.println("Beginning world render test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);

        myWorld.fillTiles(1, 1, 12, 2, Tileset.FLOOR);

        myWorld.tick();
    }
}
