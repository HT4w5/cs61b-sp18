public class NBody {
    public static double readRadius(String filePath) {
        In in = new In(filePath);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filePath) {
        In in = new In(filePath);
        int numps = in.readInt();
        Planet[] ps = new Planet[numps];
        in.readDouble();
        for (int i = 0; i < numps; ++i) {
            ps[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readString());
        }

        return ps;
    }

    public static void main(String[] args) {
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] ps = readPlanets(filename);

        // Initialize universe.
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();

        // Start simulation.
        double time = 0;
        double[] xForces = new double[ps.length];
        double[] yForces = new double[ps.length];

        while (time < T) {
            // Calculate forces.
            for (int i = 0; i < ps.length; ++i) {
                xForces[i] = ps[i].calcNetForceExertedByX(ps);
                yForces[i] = ps[i].calcNetForceExertedByY(ps);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (int i = 0; i < ps.length; ++i) {
                ps[i].update(dt, xForces[i], yForces[i]);
                ps[i].draw();
            }
            //StdDraw.show();
            //StdDraw.pause(10);

            time += dt;
        }

        StdOut.printf("%d\n", ps.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                    ps[i].yyVel, ps[i].mass, ps[i].imgFileName);
        }
    }
}
