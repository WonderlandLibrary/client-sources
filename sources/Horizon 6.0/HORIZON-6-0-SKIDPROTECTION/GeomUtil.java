package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class GeomUtil
{
    public float HorizonCode_Horizon_È;
    public float Â;
    public int Ý;
    public GeomUtilListener Ø­áŒŠá;
    
    public GeomUtil() {
        this.HorizonCode_Horizon_È = 1.0E-4f;
        this.Â = 1.0f;
        this.Ý = 10000;
    }
    
    public Shape[] HorizonCode_Horizon_È(Shape target, Shape missing) {
        target = target.HorizonCode_Horizon_È(new Transform());
        missing = missing.HorizonCode_Horizon_È(new Transform());
        int count = 0;
        for (int i = 0; i < target.Ñ¢á(); ++i) {
            if (missing.HorizonCode_Horizon_È(target.HorizonCode_Horizon_È(i)[0], target.HorizonCode_Horizon_È(i)[1])) {
                ++count;
            }
        }
        if (count == target.Ñ¢á()) {
            return new Shape[0];
        }
        if (!target.HorizonCode_Horizon_È(missing)) {
            return new Shape[] { target };
        }
        int found = 0;
        for (int j = 0; j < missing.Ñ¢á(); ++j) {
            if (target.HorizonCode_Horizon_È(missing.HorizonCode_Horizon_È(j)[0], missing.HorizonCode_Horizon_È(j)[1]) && !this.HorizonCode_Horizon_È(target, missing.HorizonCode_Horizon_È(j)[0], missing.HorizonCode_Horizon_È(j)[1])) {
                ++found;
            }
        }
        for (int j = 0; j < target.Ñ¢á(); ++j) {
            if (missing.HorizonCode_Horizon_È(target.HorizonCode_Horizon_È(j)[0], target.HorizonCode_Horizon_È(j)[1]) && !this.HorizonCode_Horizon_È(missing, target.HorizonCode_Horizon_È(j)[0], target.HorizonCode_Horizon_È(j)[1])) {
                ++found;
            }
        }
        if (found < 1) {
            return new Shape[] { target };
        }
        return this.HorizonCode_Horizon_È(target, missing, true);
    }
    
    private boolean HorizonCode_Horizon_È(final Shape path, final float x, final float y) {
        for (int i = 0; i < path.Ñ¢á() + 1; ++i) {
            final int n = HorizonCode_Horizon_È(path, i + 1);
            final Line line = this.HorizonCode_Horizon_È(path, HorizonCode_Horizon_È(path, i), n);
            if (line.HorizonCode_Horizon_È(new Vector2f(x, y)) < this.HorizonCode_Horizon_È * 100.0f) {
                return true;
            }
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final GeomUtilListener listener) {
        this.Ø­áŒŠá = listener;
    }
    
    public Shape[] Â(Shape target, Shape other) {
        target = target.HorizonCode_Horizon_È(new Transform());
        other = other.HorizonCode_Horizon_È(new Transform());
        if (!target.HorizonCode_Horizon_È(other)) {
            return new Shape[] { target, other };
        }
        boolean touches = false;
        int buttCount = 0;
        for (int i = 0; i < target.Ñ¢á(); ++i) {
            if (other.HorizonCode_Horizon_È(target.HorizonCode_Horizon_È(i)[0], target.HorizonCode_Horizon_È(i)[1]) && !other.Ø(target.HorizonCode_Horizon_È(i)[0], target.HorizonCode_Horizon_È(i)[1])) {
                touches = true;
                break;
            }
            if (other.Ø(target.HorizonCode_Horizon_È(i)[0], target.HorizonCode_Horizon_È(i)[1])) {
                ++buttCount;
            }
        }
        for (int i = 0; i < other.Ñ¢á(); ++i) {
            if (target.HorizonCode_Horizon_È(other.HorizonCode_Horizon_È(i)[0], other.HorizonCode_Horizon_È(i)[1]) && !target.Ø(other.HorizonCode_Horizon_È(i)[0], other.HorizonCode_Horizon_È(i)[1])) {
                touches = true;
                break;
            }
        }
        if (!touches && buttCount < 2) {
            return new Shape[] { target, other };
        }
        return this.HorizonCode_Horizon_È(target, other, false);
    }
    
    private Shape[] HorizonCode_Horizon_È(final Shape target, final Shape other, final boolean subtract) {
        if (subtract) {
            final ArrayList shapes = new ArrayList();
            final ArrayList used = new ArrayList();
            for (int i = 0; i < target.Ñ¢á(); ++i) {
                final float[] point = target.HorizonCode_Horizon_È(i);
                if (other.HorizonCode_Horizon_È(point[0], point[1])) {
                    used.add(new Vector2f(point[0], point[1]));
                    if (this.Ø­áŒŠá != null) {
                        this.Ø­áŒŠá.HorizonCode_Horizon_È(point[0], point[1]);
                    }
                }
            }
            for (int i = 0; i < target.Ñ¢á(); ++i) {
                final float[] point = target.HorizonCode_Horizon_È(i);
                final Vector2f pt = new Vector2f(point[0], point[1]);
                if (!used.contains(pt)) {
                    final Shape result = this.HorizonCode_Horizon_È(target, other, true, i);
                    shapes.add(result);
                    for (int j = 0; j < result.Ñ¢á(); ++j) {
                        final float[] kpoint = result.HorizonCode_Horizon_È(j);
                        final Vector2f kpt = new Vector2f(kpoint[0], kpoint[1]);
                        used.add(kpt);
                    }
                }
            }
            return shapes.toArray(new Shape[0]);
        }
        for (int k = 0; k < target.Ñ¢á(); ++k) {
            if (!other.HorizonCode_Horizon_È(target.HorizonCode_Horizon_È(k)[0], target.HorizonCode_Horizon_È(k)[1]) && !other.Ø(target.HorizonCode_Horizon_È(k)[0], target.HorizonCode_Horizon_È(k)[1])) {
                final Shape shape = this.HorizonCode_Horizon_È(target, other, false, k);
                return new Shape[] { shape };
            }
        }
        return new Shape[] { other };
    }
    
    private Shape HorizonCode_Horizon_È(final Shape target, final Shape missing, final boolean subtract, final int start) {
        Shape current = target;
        Shape other = missing;
        int point = start;
        int dir = 1;
        final Polygon poly = new Polygon();
        boolean first = true;
        int loop = 0;
        float px = current.HorizonCode_Horizon_È(point)[0];
        float py = current.HorizonCode_Horizon_È(point)[1];
        while (!poly.Ø(px, py) || first || current != target) {
            first = false;
            if (++loop > this.Ý) {
                break;
            }
            poly.Â(px, py);
            if (this.Ø­áŒŠá != null) {
                this.Ø­áŒŠá.Ý(px, py);
            }
            final Line line = this.HorizonCode_Horizon_È(current, px, py, HorizonCode_Horizon_È(current, point + dir));
            final HorizonCode_Horizon_È hit = this.HorizonCode_Horizon_È(other, line);
            if (hit != null) {
                final Line hitLine = hit.HorizonCode_Horizon_È;
                final Vector2f pt = hit.Ø­áŒŠá;
                px = pt.HorizonCode_Horizon_È;
                py = pt.Â;
                if (this.Ø­áŒŠá != null) {
                    this.Ø­áŒŠá.Â(px, py);
                }
                if (other.Ø(px, py)) {
                    point = other.à(pt.HorizonCode_Horizon_È, pt.Â);
                    dir = 1;
                    px = pt.HorizonCode_Horizon_È;
                    py = pt.Â;
                    final Shape temp = current;
                    current = other;
                    other = temp;
                }
                else {
                    float dx = hitLine.á() / hitLine.áˆºÑ¢Õ();
                    float dy = hitLine.ˆÏ­() / hitLine.áˆºÑ¢Õ();
                    dx *= this.Â;
                    dy *= this.Â;
                    if (current.HorizonCode_Horizon_È(pt.HorizonCode_Horizon_È + dx, pt.Â + dy)) {
                        if (subtract) {
                            if (current == missing) {
                                point = hit.Ý;
                                dir = -1;
                            }
                            else {
                                point = hit.Â;
                                dir = 1;
                            }
                        }
                        else if (current == target) {
                            point = hit.Ý;
                            dir = -1;
                        }
                        else {
                            point = hit.Ý;
                            dir = -1;
                        }
                        final Shape temp2 = current;
                        current = other;
                        other = temp2;
                    }
                    else if (current.HorizonCode_Horizon_È(pt.HorizonCode_Horizon_È - dx, pt.Â - dy)) {
                        if (subtract) {
                            if (current == target) {
                                point = hit.Ý;
                                dir = -1;
                            }
                            else {
                                point = hit.Â;
                                dir = 1;
                            }
                        }
                        else if (current == missing) {
                            point = hit.Â;
                            dir = 1;
                        }
                        else {
                            point = hit.Â;
                            dir = 1;
                        }
                        final Shape temp2 = current;
                        current = other;
                        other = temp2;
                    }
                    else {
                        if (subtract) {
                            break;
                        }
                        point = hit.Â;
                        dir = 1;
                        final Shape temp2 = current;
                        current = other;
                        other = temp2;
                        point = HorizonCode_Horizon_È(current, point + dir);
                        px = current.HorizonCode_Horizon_È(point)[0];
                        py = current.HorizonCode_Horizon_È(point)[1];
                    }
                }
            }
            else {
                point = HorizonCode_Horizon_È(current, point + dir);
                px = current.HorizonCode_Horizon_È(point)[0];
                py = current.HorizonCode_Horizon_È(point)[1];
            }
        }
        poly.Â(px, py);
        if (this.Ø­áŒŠá != null) {
            this.Ø­áŒŠá.Ý(px, py);
        }
        return poly;
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È(final Shape shape, final Line line) {
        float distance = Float.MAX_VALUE;
        HorizonCode_Horizon_È hit = null;
        for (int i = 0; i < shape.Ñ¢á(); ++i) {
            final int next = HorizonCode_Horizon_È(shape, i + 1);
            final Line local = this.HorizonCode_Horizon_È(shape, i, next);
            final Vector2f pt = line.HorizonCode_Horizon_È(local, true);
            if (pt != null) {
                final float newDis = pt.Âµá€(line.Ø());
                if (newDis < distance && newDis > this.HorizonCode_Horizon_È) {
                    hit = new HorizonCode_Horizon_È();
                    hit.Ø­áŒŠá = pt;
                    hit.HorizonCode_Horizon_È = local;
                    hit.Â = i;
                    hit.Ý = next;
                    distance = newDis;
                }
            }
        }
        return hit;
    }
    
    public static int HorizonCode_Horizon_È(final Shape shape, int p) {
        while (p < 0) {
            p += shape.Ñ¢á();
        }
        while (p >= shape.Ñ¢á()) {
            p -= shape.Ñ¢á();
        }
        return p;
    }
    
    public Line HorizonCode_Horizon_È(final Shape shape, final int s, final int e) {
        final float[] start = shape.HorizonCode_Horizon_È(s);
        final float[] end = shape.HorizonCode_Horizon_È(e);
        final Line line = new Line(start[0], start[1], end[0], end[1]);
        return line;
    }
    
    public Line HorizonCode_Horizon_È(final Shape shape, final float sx, final float sy, final int e) {
        final float[] end = shape.HorizonCode_Horizon_È(e);
        final Line line = new Line(sx, sy, end[0], end[1]);
        return line;
    }
    
    public class HorizonCode_Horizon_È
    {
        public Line HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public Vector2f Ø­áŒŠá;
    }
}
