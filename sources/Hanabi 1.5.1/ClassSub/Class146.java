package ClassSub;

import java.util.*;

public class Class146
{
    public float EPSILON;
    public float EDGE_SCALE;
    public int MAX_POINTS;
    public Class314 listener;
    
    
    public Class146() {
        this.EPSILON = 1.0E-4f;
        this.EDGE_SCALE = 1.0f;
        this.MAX_POINTS = 10000;
    }
    
    public Class186[] subtract(Class186 transform, Class186 transform2) {
        transform = transform.transform(new Class141());
        transform2 = transform2.transform(new Class141());
        int n = 0;
        for (int i = 0; i < transform.getPointCount(); ++i) {
            if (transform2.contains(transform.getPoint(i)[0], transform.getPoint(i)[1])) {
                ++n;
            }
        }
        if (n == transform.getPointCount()) {
            return new Class186[0];
        }
        if (!transform.intersects(transform2)) {
            return new Class186[] { transform };
        }
        int n2 = 0;
        for (int j = 0; j < transform2.getPointCount(); ++j) {
            if (transform.contains(transform2.getPoint(j)[0], transform2.getPoint(j)[1]) && !this.onPath(transform, transform2.getPoint(j)[0], transform2.getPoint(j)[1])) {
                ++n2;
            }
        }
        for (int k = 0; k < transform.getPointCount(); ++k) {
            if (transform2.contains(transform.getPoint(k)[0], transform.getPoint(k)[1]) && !this.onPath(transform2, transform.getPoint(k)[0], transform.getPoint(k)[1])) {
                ++n2;
            }
        }
        if (n2 < 1) {
            return new Class186[] { transform };
        }
        return this.combine(transform, transform2, true);
    }
    
    private boolean onPath(final Class186 class186, final float n, final float n2) {
        for (int i = 0; i < class186.getPointCount() + 1; ++i) {
            if (this.getLine(class186, rationalPoint(class186, i), rationalPoint(class186, i + 1)).distance(new Class224(n, n2)) < this.EPSILON * 100.0f) {
                return true;
            }
        }
        return false;
    }
    
    public void setListener(final Class314 listener) {
        this.listener = listener;
    }
    
    public Class186[] union(Class186 transform, Class186 transform2) {
        transform = transform.transform(new Class141());
        transform2 = transform2.transform(new Class141());
        if (!transform.intersects(transform2)) {
            return new Class186[] { transform, transform2 };
        }
        boolean b = false;
        int n = 0;
        for (int i = 0; i < transform.getPointCount(); ++i) {
            if (transform2.contains(transform.getPoint(i)[0], transform.getPoint(i)[1]) && !transform2.hasVertex(transform.getPoint(i)[0], transform.getPoint(i)[1])) {
                b = true;
                break;
            }
            if (transform2.hasVertex(transform.getPoint(i)[0], transform.getPoint(i)[1])) {
                ++n;
            }
        }
        for (int j = 0; j < transform2.getPointCount(); ++j) {
            if (transform.contains(transform2.getPoint(j)[0], transform2.getPoint(j)[1]) && !transform.hasVertex(transform2.getPoint(j)[0], transform2.getPoint(j)[1])) {
                b = true;
                break;
            }
        }
        if (!b && n < 2) {
            return new Class186[] { transform, transform2 };
        }
        return this.combine(transform, transform2, false);
    }
    
    private Class186[] combine(final Class186 class186, final Class186 class187, final boolean b) {
        if (b) {
            final ArrayList<Class186> list = new ArrayList<Class186>();
            final ArrayList<Class224> list2 = new ArrayList<Class224>();
            for (int i = 0; i < class186.getPointCount(); ++i) {
                final float[] array = class186.getPoint(i);
                if (class187.contains(array[0], array[1])) {
                    list2.add(new Class224(array[0], array[1]));
                    if (this.listener != null) {
                        this.listener.pointExcluded(array[0], array[1]);
                    }
                }
            }
            for (int j = 0; j < class186.getPointCount(); ++j) {
                final float[] array2 = class186.getPoint(j);
                if (!list2.contains(new Class224(array2[0], array2[1]))) {
                    final Class186 combineSingle = this.combineSingle(class186, class187, true, j);
                    list.add(combineSingle);
                    for (int k = 0; k < combineSingle.getPointCount(); ++k) {
                        final float[] array3 = combineSingle.getPoint(k);
                        list2.add(new Class224(array3[0], array3[1]));
                    }
                }
            }
            return list.toArray(new Class186[0]);
        }
        for (int l = 0; l < class186.getPointCount(); ++l) {
            if (!class187.contains(class186.getPoint(l)[0], class186.getPoint(l)[1]) && !class187.hasVertex(class186.getPoint(l)[0], class186.getPoint(l)[1])) {
                return new Class186[] { this.combineSingle(class186, class187, false, l) };
            }
        }
        return new Class186[] { class187 };
    }
    
    private Class186 combineSingle(final Class186 class186, final Class186 class187, final boolean b, final int n) {
        Class186 class188 = class186;
        Class186 class189 = class187;
        int n2 = n;
        int n3 = 1;
        final Class269 class190 = new Class269();
        int n4 = 1;
        int n5 = 0;
        float n6 = class188.getPoint(n2)[0];
        float n7 = class188.getPoint(n2)[1];
        while (!class190.hasVertex(n6, n7) || n4 != 0 || class188 != class186) {
            n4 = 0;
            if (++n5 > this.MAX_POINTS) {
                break;
            }
            class190.addPoint(n6, n7);
            if (this.listener != null) {
                this.listener.pointUsed(n6, n7);
            }
            final Class51 intersect = this.intersect(class189, this.getLine(class188, n6, n7, rationalPoint(class188, n2 + n3)));
            if (intersect != null) {
                final Class303 line = intersect.line;
                final Class224 pt = intersect.pt;
                n6 = pt.x;
                n7 = pt.y;
                if (this.listener != null) {
                    this.listener.pointIntersected(n6, n7);
                }
                if (class189.hasVertex(n6, n7)) {
                    n2 = class189.indexOf(pt.x, pt.y);
                    n3 = 1;
                    n6 = pt.x;
                    n7 = pt.y;
                    final Class186 class191 = class188;
                    class188 = class189;
                    class189 = class191;
                }
                else {
                    final float n8 = line.getDX() / line.length();
                    final float n9 = line.getDY() / line.length();
                    final float n10 = n8 * this.EDGE_SCALE;
                    final float n11 = n9 * this.EDGE_SCALE;
                    if (class188.contains(pt.x + n10, pt.y + n11)) {
                        if (b) {
                            if (class188 == class187) {
                                n2 = intersect.p2;
                                n3 = -1;
                            }
                            else {
                                n2 = intersect.p1;
                                n3 = 1;
                            }
                        }
                        else if (class188 == class186) {
                            n2 = intersect.p2;
                            n3 = -1;
                        }
                        else {
                            n2 = intersect.p2;
                            n3 = -1;
                        }
                        final Class186 class192 = class188;
                        class188 = class189;
                        class189 = class192;
                    }
                    else if (class188.contains(pt.x - n10, pt.y - n11)) {
                        if (b) {
                            if (class188 == class186) {
                                n2 = intersect.p2;
                                n3 = -1;
                            }
                            else {
                                n2 = intersect.p1;
                                n3 = 1;
                            }
                        }
                        else if (class188 == class187) {
                            n2 = intersect.p1;
                            n3 = 1;
                        }
                        else {
                            n2 = intersect.p1;
                            n3 = 1;
                        }
                        final Class186 class193 = class188;
                        class188 = class189;
                        class189 = class193;
                    }
                    else {
                        if (b) {
                            break;
                        }
                        final int p4 = intersect.p1;
                        n3 = 1;
                        final Class186 class194 = class188;
                        class188 = class189;
                        class189 = class194;
                        n2 = rationalPoint(class188, p4 + n3);
                        n6 = class188.getPoint(n2)[0];
                        n7 = class188.getPoint(n2)[1];
                    }
                }
            }
            else {
                n2 = rationalPoint(class188, n2 + n3);
                n6 = class188.getPoint(n2)[0];
                n7 = class188.getPoint(n2)[1];
            }
        }
        class190.addPoint(n6, n7);
        if (this.listener != null) {
            this.listener.pointUsed(n6, n7);
        }
        return class190;
    }
    
    public Class51 intersect(final Class186 class186, final Class303 class187) {
        float n = Float.MAX_VALUE;
        Class51 class188 = null;
        for (int i = 0; i < class186.getPointCount(); ++i) {
            final int rationalPoint = rationalPoint(class186, i + 1);
            final Class303 line = this.getLine(class186, i, rationalPoint);
            final Class224 intersect = class187.intersect(line, true);
            if (intersect != null) {
                final float distance = intersect.distance(class187.getStart());
                if (distance < n && distance > this.EPSILON) {
                    class188 = new Class51();
                    class188.pt = intersect;
                    class188.line = line;
                    class188.p1 = i;
                    class188.p2 = rationalPoint;
                    n = distance;
                }
            }
        }
        return class188;
    }
    
    public static int rationalPoint(final Class186 class186, int i) {
        while (i < 0) {
            i += class186.getPointCount();
        }
        while (i >= class186.getPointCount()) {
            i -= class186.getPointCount();
        }
        return i;
    }
    
    public Class303 getLine(final Class186 class186, final int n, final int n2) {
        final float[] array = class186.getPoint(n);
        final float[] array2 = class186.getPoint(n2);
        return new Class303(array[0], array[1], array2[0], array2[1]);
    }
    
    public Class303 getLine(final Class186 class186, final float n, final float n2, final int n3) {
        final float[] array = class186.getPoint(n3);
        return new Class303(n, n2, array[0], array[1]);
    }
    
    public class Class51
    {
        public Class303 line;
        public int p1;
        public int p2;
        public Class224 pt;
        final Class146 this$0;
        
        
        public Class51(final Class146 this$0) {
            this.this$0 = this$0;
        }
    }
}
