package byog.Core.Structures.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import byog.Core.RandomUtils;
import byog.Core.World;
import byog.Core.Structures.*;
import byog.TileEngine.TERenderer;

public class TestRoom {
    @Test
    public void testRoomClip() {
    }

    @Test
    public void testRoomClipTruncation() {
        System.out.println("Beginning world render test.\n");
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);

        Random random = new Random(System.currentTimeMillis());
        int numRooms = RandomUtils.uniform(random, 5, 10);

        int xPos, yPos, w, h;
        for (int i = 0; i < numRooms; ++i) {
            w = RandomUtils.uniform(random, 5, 10);
            h = RandomUtils.uniform(random, 5, 10);
            xPos = RandomUtils.uniform(random, myWorld.WIDTH);
            yPos = RandomUtils.uniform(random, myWorld.HEIGHT);
            myWorld.addStructure(new Room(xPos, yPos, w, h));
        }

        myWorld.clipStructures();
    }

    @Test
    public void testRoomOverlapsWith() {
        Room r1 = new Room(0, 0, 10, 10);
        Room r2;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                r2 = new Room(i, j, 1, 1);
                assertTrue(r1.overlapsWith(r2));
                assertTrue(r2.overlapsWith(r1));
            }
        }

        // Edge cases.
        r1 = new Room(3, 3, 3, 3);

        r2 = new Room(0, 0, 3, 6);

        assertFalse(r1.overlapsWith(r2));
        assertFalse(r2.overlapsWith(r1));

        r2 = new Room(0, 0, 6, 3);

        assertFalse(r1.overlapsWith(r2));
        assertFalse(r2.overlapsWith(r1));

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        World myWorld = new World(10, 10, ter);

        Room r1 = new Room(1, 1, 4, 4);
        Room r2 = new Room(2, 2, 4, 4);
        r1.clip(myWorld);
        r2.clip(myWorld);

        myWorld.tick();
    }
}
