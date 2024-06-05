package ClassSub;

import java.util.*;

public class Class278 implements Class92
{
    private static final float EPSILON = 1.0E-10f;
    private Class42 poly;
    private Class42 tris;
    private boolean tried;
    
    
    public Class278() {
        this.poly = new Class42();
        this.tris = new Class42();
    }
    
    @Override
    public void addPolyPoint(final float n, final float n2) {
        final Class338 class338 = new Class338(n, n2);
        if (!this.poly.contains(class338)) {
            this.poly.add(class338);
        }
    }
    
    public int getPolyPointCount() {
        return this.poly.size();
    }
    
    public float[] getPolyPoint(final int n) {
        return new float[] { Class338.access$000(this.poly.get(n)), Class338.access$100(this.poly.get(n)) };
    }
    
    @Override
    public boolean triangulate() {
        this.tried = true;
        return this.process(this.poly, this.tris);
    }
    
    @Override
    public int getTriangleCount() {
        if (!this.tried) {
            throw new RuntimeException("Call triangulate() before accessing triangles");
        }
        return this.tris.size() / 3;
    }
    
    @Override
    public float[] getTrianglePoint(final int n, final int n2) {
        if (!this.tried) {
            throw new RuntimeException("Call triangulate() before accessing triangles");
        }
        return this.tris.get(n * 3 + n2).toArray();
    }
    
    private float area(final Class42 class42) {
        final int size = class42.size();
        float n = 0.0f;
        int n2 = size - 1;
        for (int i = 0; i < size; n2 = i++) {
            final Class338 value = class42.get(n2);
            final Class338 value2 = class42.get(i);
            n += value.getX() * value2.getY() - value2.getX() * value.getY();
        }
        return n * 0.5f;
    }
    
    private boolean insideTriangle(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final float n9 = n5 - n3;
        final float n10 = n6 - n4;
        final float n11 = n - n5;
        final float n12 = n2 - n6;
        final float n13 = n3 - n;
        final float n14 = n4 - n2;
        final float n15 = n7 - n;
        final float n16 = n8 - n2;
        final float n17 = n7 - n3;
        final float n18 = n8 - n4;
        final float n19 = n7 - n5;
        final float n20 = n8 - n6;
        final float n21 = n9 * n18 - n10 * n17;
        final float n22 = n13 * n16 - n14 * n15;
        final float n23 = n11 * n20 - n12 * n19;
        return n21 >= 0.0f && n23 >= 0.0f && n22 >= 0.0f;
    }
    
    private boolean snip(final Class42 class42, final int n, final int n2, final int n3, final int n4, final int[] array) {
        final float x = class42.get(array[n]).getX();
        final float y = class42.get(array[n]).getY();
        final float x2 = class42.get(array[n2]).getX();
        final float y2 = class42.get(array[n2]).getY();
        final float x3 = class42.get(array[n3]).getX();
        final float y3 = class42.get(array[n3]).getY();
        if (1.0E-10f > (x2 - x) * (y3 - y) - (y2 - y) * (x3 - x)) {
            return false;
        }
        for (int i = 0; i < n4; ++i) {
            if (i != n && i != n2) {
                if (i != n3) {
                    if (this.insideTriangle(x, y, x2, y2, x3, y3, class42.get(array[i]).getX(), class42.get(array[i]).getY())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean process(final Class42 class42, final Class42 class43) {
        class43.clear();
        final int size = class42.size();
        if (size < 3) {
            return false;
        }
        final int[] array = new int[size];
        if (0.0f < this.area(class42)) {
            for (int i = 0; i < size; ++i) {
                array[i] = i;
            }
        }
        else {
            for (int j = 0; j < size; ++j) {
                array[j] = size - 1 - j;
            }
        }
        int k = size;
        int n = 2 * k;
        int n2 = 0;
        int n3 = k - 1;
        while (k > 2) {
            if (0 >= n--) {
                return false;
            }
            int n4 = n3;
            if (k <= n4) {
                n4 = 0;
            }
            n3 = n4 + 1;
            if (k <= n3) {
                n3 = 0;
            }
            int n5 = n3 + 1;
            if (k <= n5) {
                n5 = 0;
            }
            if (!this.snip(class42, n4, n3, n5, k, array)) {
                continue;
            }
            final int n6 = array[n4];
            final int n7 = array[n3];
            final int n8 = array[n5];
            class43.add(class42.get(n6));
            class43.add(class42.get(n7));
            class43.add(class42.get(n8));
            ++n2;
            int n9 = n3;
            for (int l = n3 + 1; l < k; ++l) {
                array[n9] = array[l];
                ++n9;
            }
            --k;
            n = 2 * k;
        }
        return true;
    }
    
    @Override
    public void startHole() {
    }
    
    private class Class42
    {
        private ArrayList points;
        final Class278 this$0;
        
        
        public Class42(final Class278 this$0) {
            this.this$0 = this$0;
            this.points = new ArrayList();
        }
        
        public boolean contains(final Class338 class338) {
            return this.points.contains(class338);
        }
        
        public void add(final Class338 class338) {
            this.points.add(class338);
        }
        
        public void remove(final Class338 class338) {
            this.points.remove(class338);
        }
        
        public int size() {
            return this.points.size();
        }
        
        public Class338 get(final int n) {
            return this.points.get(n);
        }
        
        public void clear() {
            this.points.clear();
        }
    }
    
    private class Class338
    {
        private float x;
        private float y;
        private float[] array;
        final Class278 this$0;
        
        
        public Class338(final Class278 this$0, final float x, final float y) {
            this.this$0 = this$0;
            this.x = x;
            this.y = y;
            this.array = new float[] { x, y };
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public float[] toArray() {
            return this.array;
        }
        
        @Override
        public int hashCode() {
            return (int)(this.x * this.y * 31.0f);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof Class338) {
                final Class338 class338 = (Class338)o;
                return class338.x == this.x && class338.y == this.y;
            }
            return false;
        }
        
        static float access$000(final Class338 class338) {
            return class338.x;
        }
        
        static float access$100(final Class338 class338) {
            return class338.y;
        }
    }
}
