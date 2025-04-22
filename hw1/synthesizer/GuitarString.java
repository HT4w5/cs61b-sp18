package synthesizer;

import java.util.HashSet;

//Make sure this class is public
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday.
     */
    private static final int SR = 44100; // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency. */
    public GuitarString(double frequency) {
        int cap = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(cap);

        // Fill buffer with zeros.
        for (int i = 0; i < cap; ++i) {
            buffer.enqueue(0d);
        }
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        HashSet<Double> noise = new HashSet<>(buffer.capacity());
        // Generate white noise.
        double ran;
        for (int i = 0; i < buffer.capacity(); ++i) {
            do {
                ran = Math.random() - 0.5;
            } while (noise.contains(ran));
            noise.add(ran);
            buffer.dequeue();
            buffer.enqueue(ran);
        }
    }

    /*
     * Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double front = buffer.dequeue().doubleValue();
        buffer.enqueue(DECAY * (front + buffer.peek().doubleValue()) * 0.5);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
