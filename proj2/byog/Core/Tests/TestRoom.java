package byog.Core.Tests;

import org.junit.Test;

import byog.Core.World;
import byog.Core.Structures.*;
import byog.TileEngine.TERenderer;

public class TestRoom {
    @Test
    public void testRoomClip() {

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
