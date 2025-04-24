package byog.Core.Generators;

import java.util.Random;
import java.util.Vector;

import byog.Core.RandomUtils;
import byog.Core.World;
import byog.Core.WorldGenerator;
import byog.Core.Structures.*;

public class Maze implements WorldGenerator {
    private final int ROOM_GEN_TRIALS;

    private final int MIN_WIDTH;
    private final int MAX_WIDTH;
    private final int MIN_HEIGHT;
    private final int MAX_HEIGHT;

    public Maze(int roomGenTrials, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        ROOM_GEN_TRIALS = roomGenTrials;
        MIN_WIDTH = minWidth;
        MAX_WIDTH = maxWidth;
        MIN_HEIGHT = minHeight;
        MAX_HEIGHT = maxHeight;
    }

    public Maze() {
        this(100, 8, 10, 8, 10);
    }

    public void generate(World world) {
        generate(world, world.SEED);
    }

    public void generate(World world, long seed) {
        // Generate rooms.
        Random random = new Random(seed);
        int xPos, yPos, width, height;
        Vector<Room> rooms = new Vector<>(40);
        for (int i = 0; i < ROOM_GEN_TRIALS; ++i) {
            // Generate room props from seed.
            xPos = RandomUtils.uniform(random, world.WIDTH - 1);
            yPos = RandomUtils.uniform(random, world.HEIGHT - 1);
            width = RandomUtils.uniform(random, MIN_WIDTH, MAX_WIDTH);
            height = RandomUtils.uniform(random, MIN_HEIGHT, MAX_HEIGHT);
            rooms.add(new Room(xPos, yPos, width, height));
        }
        // Discard overlapping.

        int origSize = rooms.size();
        for (int i = 0; i < origSize - 1; ++i) {
            for (int j = i + 1; j < origSize; ++j) {
                if (rooms.get(i).overlapsWith(rooms.get(j))) {
                    rooms.set(i, null);
                    break;
                }
            }
        }

        // Store structures.
        for (Room r : rooms) {
            if (r != null) {
                world.addStructure(r);
            }
        }
    }
}
