public class Planet {
    // Instance variables.
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    // Physics constants.
    public static final double GRVT = 6.67e-11;

    // Constructors.
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    // Methods.
    public double calcDistance(Planet p) {
        double dx = xxPos - p.xxPos;
        double dy = yyPos - p.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcDistanceSquared(Planet p) {
        double dx = xxPos - p.xxPos;
        double dy = yyPos - p.yyPos;
        return dx * dx + dy * dy;
    }

    public double calcForceExertedBy(Planet p) {
        return GRVT * mass * p.mass / calcDistanceSquared(p);
    }

    public double calcForceExertedByX(Planet p) {
        double dx = xxPos - p.xxPos;
        if (dx < 0) {
            dx = -dx;
        }
        double force = calcForceExertedBy(p);
        double dist = calcDistance(p);
        return force * dx / dist;
    }

    public double calcForceExertedByY(Planet p) {
        double dy = yyPos - p.yyPos;
        if (dy < 0) {
            dy = -dy;
        }
        double force = calcForceExertedBy(p);
        double dist = calcDistance(p);
        return force * dy / dist;
    }

    public double calcSignedForceExertedByX(Planet p) {
        double dx = xxPos - p.xxPos;
        double force = calcForceExertedBy(p);
        double dist = calcDistance(p);
        return -force * dx / dist;
    }

    public double calcSignedForceExertedByY(Planet p) {
        double dy = yyPos - p.yyPos;
        double force = calcForceExertedBy(p);
        double dist = calcDistance(p);
        return -force * dy / dist;
    }

    public double calcNetForceExertedByX(Planet[] ps) {
        double netXF = 0;
        for (Planet p : ps) {
            if (this.equals(p)) {
                continue;
            } else {
                netXF += calcSignedForceExertedByX(p);
            }
        }

        return netXF;
    }

    public double calcNetForceExertedByY(Planet[] ps) {
        double netYF = 0;
        for (Planet p : ps) {
            if (this.equals(p)) {
                continue;
            } else {
                netYF += calcSignedForceExertedByY(p);
            }
        }

        return netYF;
    }

    public void update(double t, double xF, double yF) {
        double xxAcc = xF / mass;
        double yyAcc = yF / mass;

        xxVel += t * xxAcc;
        yyVel += t * yyAcc;

        xxPos += xxVel * t;
        yyPos += yyVel * t;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}
