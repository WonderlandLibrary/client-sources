package ClassSub;

import java.util.*;
import java.lang.reflect.*;
import java.io.*;

public class Class192 implements Class92
{
    private static final double EPSILON = 1.0E-5;
    protected Class177 contour;
    protected Class177 holes;
    private Class177 nextFreePointBag;
    private Class277 nextFreePoint;
    private List triangles;
    
    
    public Class192() {
        this.triangles = new ArrayList();
        this.contour = this.getPointBag();
    }
    
    @Override
    public void addPolyPoint(final float n, final float n2) {
        this.addPoint(new Class224(n, n2));
    }
    
    public void reset() {
        while (this.holes != null) {
            this.holes = this.freePointBag(this.holes);
        }
        this.contour.clear();
        this.holes = null;
    }
    
    @Override
    public void startHole() {
        final Class177 pointBag = this.getPointBag();
        pointBag.next = this.holes;
        this.holes = pointBag;
    }
    
    private void addPoint(final Class224 class224) {
        if (this.holes == null) {
            this.contour.add(this.getPoint(class224));
        }
        else {
            this.holes.add(this.getPoint(class224));
        }
    }
    
    private Class224[] triangulate(Class224[] array) {
        this.contour.computeAngles();
        for (Class177 class177 = this.holes; class177 != null; class177 = class177.next) {
            class177.computeAngles();
        }
        while (this.holes != null) {
            Class277 next = this.holes.first;
        Label_0273:
            do {
                if (next.angle <= 0.0) {
                    Class277 prev = this.contour.first;
                    do {
                        if (next.isInfront(prev) && prev.isInfront(next) && !this.contour.doesIntersectSegment(next.pt, prev.pt)) {
                            Class177 class178 = this.holes;
                            while (!class178.doesIntersectSegment(next.pt, prev.pt)) {
                                if ((class178 = class178.next) == null) {
                                    final Class277 point = this.getPoint(prev.pt);
                                    prev.insertAfter(point);
                                    final Class277 point2 = this.getPoint(next.pt);
                                    next.insertBefore(point2);
                                    prev.next = next;
                                    next.prev = prev;
                                    point2.next = point;
                                    point.prev = point2;
                                    prev.computeAngle();
                                    next.computeAngle();
                                    point.computeAngle();
                                    point2.computeAngle();
                                    this.holes.first = null;
                                    break Label_0273;
                                }
                            }
                        }
                    } while ((prev = prev.next) != this.contour.first);
                }
            } while ((next = next.next) != this.holes.first);
            this.holes = this.freePointBag(this.holes);
        }
        final int n = (this.contour.countPoints() - 2) * 3 + 1;
        if (array.length < n) {
            array = (Class224[])Array.newInstance(array.getClass().getComponentType(), n);
        }
        int n2 = 0;
        while (true) {
            Class277 class179 = this.contour.first;
            if (class179 == null) {
                break;
            }
            if (class179.next == class179.prev) {
                break;
            }
            do {
                if (class179.angle > 0.0) {
                    final Class277 prev2 = class179.prev;
                    final Class277 next2 = class179.next;
                    if ((next2.next == prev2 || (prev2.isInfront(next2) && next2.isInfront(prev2))) && !this.contour.doesIntersectSegment(prev2.pt, next2.pt)) {
                        array[n2++] = class179.pt;
                        array[n2++] = next2.pt;
                        array[n2++] = prev2.pt;
                        break;
                    }
                    continue;
                }
            } while ((class179 = class179.next) != this.contour.first);
            final Class277 prev3 = class179.prev;
            final Class277 next3 = class179.next;
            this.contour.first = prev3;
            class179.unlink();
            this.freePoint(class179);
            next3.computeAngle();
            prev3.computeAngle();
        }
        array[n2] = null;
        this.contour.clear();
        return array;
    }
    
    private Class177 getPointBag() {
        final Class177 nextFreePointBag = this.nextFreePointBag;
        if (nextFreePointBag != null) {
            this.nextFreePointBag = nextFreePointBag.next;
            nextFreePointBag.next = null;
            return nextFreePointBag;
        }
        return new Class177();
    }
    
    private Class177 freePointBag(final Class177 nextFreePointBag) {
        final Class177 next = nextFreePointBag.next;
        nextFreePointBag.clear();
        nextFreePointBag.next = this.nextFreePointBag;
        this.nextFreePointBag = nextFreePointBag;
        return next;
    }
    
    private Class277 getPoint(final Class224 pt) {
        final Class277 nextFreePoint = this.nextFreePoint;
        if (nextFreePoint != null) {
            this.nextFreePoint = nextFreePoint.next;
            nextFreePoint.next = null;
            nextFreePoint.prev = null;
            nextFreePoint.pt = pt;
            return nextFreePoint;
        }
        return new Class277(pt);
    }
    
    private void freePoint(final Class277 nextFreePoint) {
        nextFreePoint.next = this.nextFreePoint;
        this.nextFreePoint = nextFreePoint;
    }
    
    private void freePoints(final Class277 nextFreePoint) {
        nextFreePoint.prev.next = this.nextFreePoint;
        nextFreePoint.prev = null;
        this.nextFreePoint = nextFreePoint;
    }
    
    @Override
    public boolean triangulate() {
        final Class224[] array = this.triangulate(new Class224[0]);
        for (int n = 0; n < array.length && array[n] != null; ++n) {
            this.triangles.add(array[n]);
        }
        return true;
    }
    
    @Override
    public int getTriangleCount() {
        return this.triangles.size() / 3;
    }
    
    @Override
    public float[] getTrianglePoint(final int n, final int n2) {
        final Class224 class224 = this.triangles.get(n * 3 + n2);
        return new float[] { class224.x, class224.y };
    }
    
    static void access$000(final Class192 class192, final Class277 class193) {
        class192.freePoints(class193);
    }
    
    private static class Class277 implements Serializable
    {
        protected Class224 pt;
        protected Class277 prev;
        protected Class277 next;
        protected double nx;
        protected double ny;
        protected double angle;
        protected double dist;
        
        
        public Class277(final Class224 pt) {
            this.pt = pt;
        }
        
        public void unlink() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
            this.next = null;
            this.prev = null;
        }
        
        public void insertBefore(final Class277 class277) {
            this.prev.next = class277;
            class277.prev = this.prev;
            class277.next = this;
            this.prev = class277;
        }
        
        public void insertAfter(final Class277 class277) {
            this.next.prev = class277;
            class277.prev = this;
            class277.next = this.next;
            this.next = class277;
        }
        
        private double hypot(final double n, final double n2) {
            return Math.sqrt(n * n + n2 * n2);
        }
        
        public void computeAngle() {
            if (this.prev.pt.equals(this.pt)) {
                final Class224 pt = this.pt;
                pt.x += 0.01f;
            }
            final double n = this.pt.x - this.prev.pt.x;
            final double n2 = this.pt.y - this.prev.pt.y;
            final double hypot = this.hypot(n, n2);
            final double nx = n / hypot;
            final double n3 = n2 / hypot;
            if (this.next.pt.equals(this.pt)) {
                final Class224 pt2 = this.pt;
                pt2.y += 0.01f;
            }
            final double n4 = this.next.pt.x - this.pt.x;
            final double n5 = this.next.pt.y - this.pt.y;
            final double hypot2 = this.hypot(n4, n5);
            final double n6 = n4 / hypot2;
            final double ny = n5 / hypot2;
            final double n7 = -n3;
            final double n8 = nx;
            this.nx = (n7 - ny) * 0.5;
            this.ny = (n8 + n6) * 0.5;
            if (this.nx * this.nx + this.ny * this.ny < 1.0E-5) {
                this.nx = nx;
                this.ny = ny;
                this.angle = 1.0;
                if (nx * n6 + n3 * ny > 0.0) {
                    this.nx = -nx;
                    this.ny = -n3;
                }
            }
            else {
                this.angle = this.nx * n6 + this.ny * ny;
            }
        }
        
        public double getAngle(final Class277 class277) {
            final double n = class277.pt.x - this.pt.x;
            final double n2 = class277.pt.y - this.pt.y;
            return (this.nx * n + this.ny * n2) / this.hypot(n, n2);
        }
        
        public boolean isConcave() {
            return this.angle < 0.0;
        }
        
        public boolean isInfront(final double n, final double n2) {
            final boolean b = (this.prev.pt.y - this.pt.y) * n + (this.pt.x - this.prev.pt.x) * n2 >= 0.0;
            final boolean b2 = (this.pt.y - this.next.pt.y) * n + (this.next.pt.x - this.pt.x) * n2 >= 0.0;
            return (this.angle < 0.0) ? (b | b2) : (b & b2);
        }
        
        public boolean isInfront(final Class277 class277) {
            return this.isInfront(class277.pt.x - this.pt.x, class277.pt.y - this.pt.y);
        }
    }
    
    protected class Class177 implements Serializable
    {
        protected Class277 first;
        protected Class177 next;
        final Class192 this$0;
        
        
        protected Class177(final Class192 this$0) {
            this.this$0 = this$0;
        }
        
        public void clear() {
            if (this.first != null) {
                Class192.access$000(this.this$0, this.first);
                this.first = null;
            }
        }
        
        public void add(final Class277 prev) {
            if (this.first != null) {
                this.first.insertBefore(prev);
            }
            else {
                this.first = prev;
                prev.next = prev;
                prev.prev = prev;
            }
        }
        
        public void computeAngles() {
            if (this.first == null) {
                return;
            }
            Class277 class277 = this.first;
            do {
                class277.computeAngle();
            } while ((class277 = class277.next) != this.first);
        }
        
        public boolean doesIntersectSegment(final Class224 class224, final Class224 class225) {
            final double n = class225.x - class224.x;
            final double n2 = class225.y - class224.y;
            Class277 first = this.first;
            while (true) {
                final Class277 next = first.next;
                if (first.pt != class224 && next.pt != class224 && first.pt != class225 && next.pt != class225) {
                    final double n3 = next.pt.x - first.pt.x;
                    final double n4 = next.pt.y - first.pt.y;
                    final double n5 = n * n4 - n2 * n3;
                    if (Math.abs(n5) > 1.0E-5) {
                        final double n6 = first.pt.x - class224.x;
                        final double n7 = first.pt.y - class224.y;
                        final double n8 = (n4 * n6 - n3 * n7) / n5;
                        final double n9 = (n2 * n6 - n * n7) / n5;
                        if (n8 >= 0.0 && n8 <= 1.0 && n9 >= 0.0 && n9 <= 1.0) {
                            return true;
                        }
                    }
                }
                if (next == this.first) {
                    return false;
                }
                first = next;
            }
        }
        
        public int countPoints() {
            if (this.first == null) {
                return 0;
            }
            int n = 0;
            Class277 class277 = this.first;
            do {
                ++n;
            } while ((class277 = class277.next) != this.first);
            return n;
        }
        
        public boolean contains(final Class224 class224) {
            return this.first != null && (this.first.prev.pt.equals(class224) || this.first.pt.equals(class224));
        }
    }
}
