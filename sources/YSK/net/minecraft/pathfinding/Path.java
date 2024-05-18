package net.minecraft.pathfinding;

public class Path
{
    private int count;
    private static final String[] I;
    private PathPoint[] pathPoints;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("'\u0005S(#'\u0005 B", "hRscm");
    }
    
    static {
        I();
    }
    
    private void sortForward(int index) {
        final PathPoint pathPoint = this.pathPoints[index];
        final float distanceToTarget = pathPoint.distanceToTarget;
        while (true) {
            final int n = " ".length() + (index << " ".length());
            final int n2 = n + " ".length();
            if (n >= this.count) {
                "".length();
                if (4 == 1) {
                    throw null;
                }
                break;
            }
            else {
                final PathPoint pathPoint2 = this.pathPoints[n];
                final float distanceToTarget2 = pathPoint2.distanceToTarget;
                PathPoint pathPoint3;
                float distanceToTarget3;
                if (n2 >= this.count) {
                    pathPoint3 = null;
                    distanceToTarget3 = Float.POSITIVE_INFINITY;
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else {
                    pathPoint3 = this.pathPoints[n2];
                    distanceToTarget3 = pathPoint3.distanceToTarget;
                }
                if (distanceToTarget2 < distanceToTarget3) {
                    if (distanceToTarget2 >= distanceToTarget) {
                        "".length();
                        if (3 == 1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        this.pathPoints[index] = pathPoint2;
                        pathPoint2.index = index;
                        index = n;
                        "".length();
                        if (0 < 0) {
                            throw null;
                        }
                        continue;
                    }
                }
                else if (distanceToTarget3 >= distanceToTarget) {
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    this.pathPoints[index] = pathPoint3;
                    pathPoint3.index = index;
                    index = n2;
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        this.pathPoints[index] = pathPoint;
        pathPoint.index = index;
    }
    
    private void sortBack(int i) {
        final PathPoint pathPoint = this.pathPoints[i];
        final float distanceToTarget = pathPoint.distanceToTarget;
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i > 0) {
            final int n = i - " ".length() >> " ".length();
            final PathPoint pathPoint2 = this.pathPoints[n];
            if (distanceToTarget >= pathPoint2.distanceToTarget) {
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            else {
                this.pathPoints[i] = pathPoint2;
                pathPoint2.index = i;
                i = n;
            }
        }
        this.pathPoints[i] = pathPoint;
        pathPoint.index = i;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PathPoint dequeue() {
        final PathPoint pathPoint = this.pathPoints["".length()];
        final PathPoint[] pathPoints = this.pathPoints;
        final int length = "".length();
        final PathPoint[] pathPoints2 = this.pathPoints;
        final int count = this.count - " ".length();
        this.count = count;
        pathPoints[length] = pathPoints2[count];
        this.pathPoints[this.count] = null;
        if (this.count > 0) {
            this.sortForward("".length());
        }
        pathPoint.index = -" ".length();
        return pathPoint;
    }
    
    public void changeDistance(final PathPoint pathPoint, final float distanceToTarget) {
        final float distanceToTarget2 = pathPoint.distanceToTarget;
        pathPoint.distanceToTarget = distanceToTarget;
        if (distanceToTarget < distanceToTarget2) {
            this.sortBack(pathPoint.index);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            this.sortForward(pathPoint.index);
        }
    }
    
    public boolean isPathEmpty() {
        if (this.count == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Path() {
        this.pathPoints = new PathPoint[570 + 36 + 82 + 336];
    }
    
    public PathPoint addPoint(final PathPoint pathPoint) {
        if (pathPoint.index >= 0) {
            throw new IllegalStateException(Path.I["".length()]);
        }
        if (this.count == this.pathPoints.length) {
            final PathPoint[] pathPoints = new PathPoint[this.count << " ".length()];
            System.arraycopy(this.pathPoints, "".length(), pathPoints, "".length(), this.count);
            this.pathPoints = pathPoints;
        }
        this.pathPoints[this.count] = pathPoint;
        pathPoint.index = this.count;
        final int count = this.count;
        this.count = count + " ".length();
        this.sortBack(count);
        return pathPoint;
    }
    
    public void clearPath() {
        this.count = "".length();
    }
}
