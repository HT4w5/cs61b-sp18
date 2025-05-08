package byog.Core.WorldGenerators;

import java.util.Random;
import java.util.Vector;

import byog.Core.AbstractWorldGenerator;
import byog.Core.RandomUtils;
import byog.Core.World;
import byog.Core.WorldGenerator;
import byog.Core.Structures.*;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Maze extends AbstractWorldGenerator {
    private final int ROOM_GEN_TRIALS;

    private final int ROOM_MIN_WIDTH;
    private final int ROOM_MAX_WIDTH;
    private final int ROOM_MIN_HEIGHT;
    private final int ROOM_MAX_HEIGHT;
    private final TETile FLOOR;
    private final TETile WALL;

    private Vector<Rect> rooms;

    public Maze(World world, int roomGenTrials, int minWidth, int maxWidth, int minHeight,
            int maxHeight, TETile floor, TETile wall) {
        super(world);
        ROOM_GEN_TRIALS = roomGenTrials;
        ROOM_MIN_WIDTH = minWidth;
        ROOM_MAX_WIDTH = maxWidth;
        ROOM_MIN_HEIGHT = minHeight;
        ROOM_MAX_HEIGHT = maxHeight;
        FLOOR = floor;
        WALL = wall;

        rooms = new Vector<>(ROOM_GEN_TRIALS / 3); // TODO: Optimize pre-allocation.
    }

    public Maze(World world, int roomGenTrials, int minWidth, int maxWidth, int minHeight,
            int maxHeight) {
        this(world, roomGenTrials, minWidth, maxWidth, minHeight, maxHeight, Tileset.FLOOR,
                Tileset.WALL);
    }

    public Maze(World world) {
        this(world, 1000, 3, 8, 3, 8);
    }

    @Override
    public void generate() {
        generateRooms();

        for (Rect r : rooms) {
            r.clip(world);
        }
    }

    private void generateRooms() {
        for (int i = 0; i < ROOM_GEN_TRIALS; ++i) {
            Rect room = new Rect(RandomUtils.uniform(random, world.WIDTH - 1),
                    RandomUtils.uniform(random, world.HEIGHT - 1),
                    RandomUtils.uniform(random, ROOM_MIN_WIDTH, ROOM_MAX_WIDTH + 1),
                    RandomUtils.uniform(random, ROOM_MIN_HEIGHT, ROOM_MAX_HEIGHT + 1), FLOOR);

            boolean overlaps = false;
            for (Rect r : rooms) {
                if (r.overlapsWith(room)) {
                    overlaps = true;
                    break;
                }
            }

            if (overlaps) {
                continue;
            }

            rooms.add(room);
        }
    }

    /*
     * Old implemention.
     * public void generate(World world, long seed) {
     * // Generate rooms.
     * Random random = new Random(seed);
     * int xPos, yPos, width, height;
     * Vector<Room> rooms = new Vector<>(40);
     * for (int i = 0; i < ROOM_GEN_TRIALS; ++i) {
     * // Generate room props from seed.
     * xPos = RandomUtils.uniform(random, world.WIDTH - 1);
     * yPos = RandomUtils.uniform(random, world.HEIGHT - 1);
     * width = RandomUtils.uniform(random, MIN_WIDTH, MAX_WIDTH);
     * height = RandomUtils.uniform(random, MIN_HEIGHT, MAX_HEIGHT);
     * rooms.add(new Room(xPos, yPos, width, height));
     * }
     * // Discard 50% overlapping.
     * 
     * double factor;
     * int origSize = rooms.size();
     * for (int i = 0; i < origSize - 1; ++i) {
     * for (int j = i + 1; j < origSize; ++j) {
     * if (rooms.get(i).overlapsWith(rooms.get(j))) {
     * factor = RandomUtils.uniform(random);
     * if (true) {
     * rooms.set(i, null);
     * break;
     * }
     * }
     * }
     * }
     * 
     * // Draw hallways.
     * Room tmp;
     * Vector<Hallway> hallways = new Vector<>(rooms.size() / 2);
     * int maxLen = Math.max(world.WIDTH, world.HEIGHT);
     * for (int i = 0; i < rooms.size(); ++i) {
     * tmp = rooms.get(i);
     * if (tmp == null) {
     * continue;
     * }
     * 
     * // Generate random starting points and lengths.
     * factor = RandomUtils.uniform(random);
     * boolean vert = false;
     * if (factor > 0.5) {
     * vert = true;
     * }
     * 
     * int xStart = tmp.getXPos() + RandomUtils.uniform(random, tmp.getWidth());
     * int yStart = tmp.getYPos() + RandomUtils.uniform(random, tmp.getHeight());
     * 
     * factor = RandomUtils.uniform(random);
     * 
     * if (factor > 0.3) {
     * hallways.add(new Hallway(xStart, yStart, vert,
     * RandomUtils.uniform(random, maxLen), Tileset.FLOOR, Tileset.WALL));
     * }
     * }
     * 
     * // Store structures.
     * for (Room r : rooms) {
     * if (r != null) {
     * world.addStructure(r);
     * }
     * }
     * // for (Hallway h : hallways) {
     * // world.addStructure(h);
     * // }
     * 
     * world.clipStructures();
     * }
     */
}
