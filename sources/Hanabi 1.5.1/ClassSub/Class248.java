package ClassSub;

public class Class248 implements Class92
{
    static final float EPSILON = 1.0E-6f;
    private float[] pointsX;
    private float[] pointsY;
    private int numPoints;
    private Class172[] edges;
    private int[] V;
    private int numEdges;
    private Class114[] triangles;
    private int numTriangles;
    private float offset;
    
    
    public Class248() {
        this.offset = 1.0E-6f;
        this.pointsX = new float[100];
        this.pointsY = new float[100];
        this.numPoints = 0;
        this.edges = new Class172[100];
        this.numEdges = 0;
        this.triangles = new Class114[100];
        this.numTriangles = 0;
    }
    
    public void clear() {
        this.numPoints = 0;
        this.numEdges = 0;
        this.numTriangles = 0;
    }
    
    private int findEdge(final int n, final int n2) {
        int n3;
        int n4;
        if (n < n2) {
            n3 = n;
            n4 = n2;
        }
        else {
            n3 = n2;
            n4 = n;
        }
        for (int i = 0; i < this.numEdges; ++i) {
            if (this.edges[i].v0 == n3 && this.edges[i].v1 == n4) {
                return i;
            }
        }
        return -1;
    }
    
    private void addEdge(final int n, final int n2, final int n3) {
        final int edge = this.findEdge(n, n2);
        int t0;
        int t2;
        Class172 class173;
        if (edge < 0) {
            if (this.numEdges == this.edges.length) {
                final Class172[] edges = new Class172[this.edges.length * 2];
                System.arraycopy(this.edges, 0, edges, 0, this.numEdges);
                this.edges = edges;
            }
            t0 = -1;
            t2 = -1;
            final int n4 = this.numEdges++;
            final Class172[] edges2 = this.edges;
            final int n5 = n4;
            final Class172 class172 = new Class172();
            edges2[n5] = class172;
            class173 = class172;
        }
        else {
            class173 = this.edges[edge];
            t0 = class173.t0;
            t2 = class173.t1;
        }
        int v0;
        int v2;
        if (n < n2) {
            v0 = n;
            v2 = n2;
            t0 = n3;
        }
        else {
            v0 = n2;
            v2 = n;
            t2 = n3;
        }
        class173.v0 = v0;
        class173.v1 = v2;
        class173.t0 = t0;
        class173.t1 = t2;
        class173.suspect = true;
    }
    
    private void deleteEdge(final int n, final int n2) throws Class32 {
        final int edge;
        if (0 > (edge = this.findEdge(n, n2))) {
            throw new Class32("Attempt to delete unknown edge");
        }
        final Class172[] edges = this.edges;
        final int n3 = edge;
        final Class172[] edges2 = this.edges;
        final int numEdges = this.numEdges - 1;
        this.numEdges = numEdges;
        edges[n3] = edges2[numEdges];
    }
    
    void markSuspect(final int n, final int n2, final boolean suspect) throws Class32 {
        final int edge;
        if (0 > (edge = this.findEdge(n, n2))) {
            throw new Class32("Attempt to mark unknown edge");
        }
        this.edges[edge].suspect = suspect;
    }
    
    private Class172 chooseSuspect() {
        for (int i = 0; i < this.numEdges; ++i) {
            final Class172 class172 = this.edges[i];
            if (class172.suspect) {
                class172.suspect = false;
                if (class172.t0 >= 0 && class172.t1 >= 0) {
                    return class172;
                }
            }
        }
        return null;
    }
    
    private static float rho(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = n5 - n3;
        final float n8 = n6 - n4;
        final float n9 = n - n5;
        final float n10 = n2 - n6;
        float n11 = n7 * n10 - n8 * n9;
        if (n11 > 0.0f) {
            if (n11 < 1.0E-6f) {
                n11 = 1.0E-6f;
            }
            final float n12 = n7 * n7;
            final float n13 = n8 * n8;
            final float n14 = n9 * n9;
            final float n15 = n10 * n10;
            final float n16 = n3 - n;
            final float n17 = n4 - n2;
            return (n12 + n13) * (n14 + n15) * (n16 * n16 + n17 * n17) / (n11 * n11);
        }
        return -1.0f;
    }
    
    private static boolean insideTriangle(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
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
        return n21 >= 0.0 && n23 >= 0.0 && n22 >= 0.0;
    }
    
    private boolean snip(final int n, final int n2, final int n3, final int n4) {
        final float n5 = this.pointsX[this.V[n]];
        final float n6 = this.pointsY[this.V[n]];
        final float n7 = this.pointsX[this.V[n2]];
        final float n8 = this.pointsY[this.V[n2]];
        final float n9 = this.pointsX[this.V[n3]];
        final float n10 = this.pointsY[this.V[n3]];
        if (1.0E-6f > (n7 - n5) * (n10 - n6) - (n8 - n6) * (n9 - n5)) {
            return false;
        }
        for (int i = 0; i < n4; ++i) {
            if (i != n && i != n2 && i != n3 && insideTriangle(n5, n6, n7, n8, n9, n10, this.pointsX[this.V[i]], this.pointsY[this.V[i]])) {
                return false;
            }
        }
        return true;
    }
    
    private float area() {
        float n = 0.0f;
        int n2 = this.numPoints - 1;
        for (int i = 0; i < this.numPoints; n2 = i++) {
            n += this.pointsX[n2] * this.pointsY[i] - this.pointsY[n2] * this.pointsX[i];
        }
        return n * 0.5f;
    }
    
    public void basicTriangulation() throws Class32 {
        int i = this.numPoints;
        if (i < 3) {
            return;
        }
        this.numEdges = 0;
        this.numTriangles = 0;
        this.V = new int[i];
        if (0.0 < this.area()) {
            for (int j = 0; j < i; ++j) {
                this.V[j] = j;
            }
        }
        else {
            for (int k = 0; k < i; ++k) {
                this.V[k] = this.numPoints - 1 - k;
            }
        }
        int n = 2 * i;
        int n2 = i - 1;
        while (i > 2) {
            if (0 >= n--) {
                throw new Class32("Bad polygon");
            }
            int n3 = n2;
            if (i <= n3) {
                n3 = 0;
            }
            n2 = n3 + 1;
            if (i <= n2) {
                n2 = 0;
            }
            int n4 = n2 + 1;
            if (i <= n4) {
                n4 = 0;
            }
            if (!this.snip(n3, n2, n4, i)) {
                continue;
            }
            final int n5 = this.V[n3];
            final int n6 = this.V[n2];
            final int n7 = this.V[n4];
            if (this.numTriangles == this.triangles.length) {
                final Class114[] triangles = new Class114[this.triangles.length * 2];
                System.arraycopy(this.triangles, 0, triangles, 0, this.numTriangles);
                this.triangles = triangles;
            }
            this.triangles[this.numTriangles] = new Class114(n5, n6, n7);
            this.addEdge(n5, n6, this.numTriangles);
            this.addEdge(n6, n7, this.numTriangles);
            this.addEdge(n7, n5, this.numTriangles);
            ++this.numTriangles;
            int n8 = n2;
            for (int l = n2 + 1; l < i; ++l) {
                this.V[n8] = this.V[l];
                ++n8;
            }
            --i;
            n = 2 * i;
        }
        this.V = null;
    }
    
    private void optimize() throws Class32 {
        Class172 chooseSuspect;
        while ((chooseSuspect = this.chooseSuspect()) != null) {
            final int v0 = chooseSuspect.v0;
            final int v2 = chooseSuspect.v1;
            final int t0 = chooseSuspect.t0;
            final int t2 = chooseSuspect.t1;
            int n = -1;
            int n2 = -1;
            for (int i = 0; i < 3; ++i) {
                final int n3 = this.triangles[t0].v[i];
                if (v0 != n3 && v2 != n3) {
                    n2 = n3;
                    break;
                }
            }
            for (int j = 0; j < 3; ++j) {
                final int n4 = this.triangles[t2].v[j];
                if (v0 != n4 && v2 != n4) {
                    n = n4;
                    break;
                }
            }
            if (-1 == n || -1 == n2) {
                throw new Class32("can't find quad");
            }
            final float n5 = this.pointsX[v0];
            final float n6 = this.pointsY[v0];
            final float n7 = this.pointsX[n];
            final float n8 = this.pointsY[n];
            final float n9 = this.pointsX[v2];
            final float n10 = this.pointsY[v2];
            final float n11 = this.pointsX[n2];
            final float n12 = this.pointsY[n2];
            float rho = rho(n5, n6, n7, n8, n9, n10);
            final float rho2 = rho(n5, n6, n9, n10, n11, n12);
            float rho3 = rho(n7, n8, n9, n10, n11, n12);
            final float rho4 = rho(n7, n8, n11, n12, n5, n6);
            if (0.0f > rho || 0.0f > rho2) {
                throw new Class32("original triangles backwards");
            }
            if (0.0f > rho3 || 0.0f > rho4) {
                continue;
            }
            if (rho > rho2) {
                rho = rho2;
            }
            if (rho3 > rho4) {
                rho3 = rho4;
            }
            if (rho <= rho3) {
                continue;
            }
            this.deleteEdge(v0, v2);
            this.triangles[t0].v[0] = n;
            this.triangles[t0].v[1] = v2;
            this.triangles[t0].v[2] = n2;
            this.triangles[t2].v[0] = n;
            this.triangles[t2].v[1] = n2;
            this.triangles[t2].v[2] = v0;
            this.addEdge(n, v2, t0);
            this.addEdge(v2, n2, t0);
            this.addEdge(n2, n, t0);
            this.addEdge(n2, v0, t2);
            this.addEdge(v0, n, t2);
            this.addEdge(n, n2, t2);
            this.markSuspect(n, n2, false);
        }
    }
    
    @Override
    public boolean triangulate() {
        try {
            this.basicTriangulation();
            return true;
        }
        catch (Class32 class32) {
            this.numEdges = 0;
            return false;
        }
    }
    
    @Override
    public void addPolyPoint(final float n, float n2) {
        for (int i = 0; i < this.numPoints; ++i) {
            if (this.pointsX[i] == n && this.pointsY[i] == n2) {
                n2 += this.offset;
                this.offset += 1.0E-6f;
            }
        }
        if (this.numPoints == this.pointsX.length) {
            final float[] pointsX = new float[this.numPoints * 2];
            System.arraycopy(this.pointsX, 0, pointsX, 0, this.numPoints);
            this.pointsX = pointsX;
            final float[] pointsY = new float[this.numPoints * 2];
            System.arraycopy(this.pointsY, 0, pointsY, 0, this.numPoints);
            this.pointsY = pointsY;
        }
        this.pointsX[this.numPoints] = n;
        this.pointsY[this.numPoints] = n2;
        ++this.numPoints;
    }
    
    @Override
    public int getTriangleCount() {
        return this.numTriangles;
    }
    
    @Override
    public float[] getTrianglePoint(final int n, final int n2) {
        return new float[] { this.pointsX[this.triangles[n].v[n2]], this.pointsY[this.triangles[n].v[n2]] };
    }
    
    @Override
    public void startHole() {
    }
    
    class Class172
    {
        int v0;
        int v1;
        int t0;
        int t1;
        boolean suspect;
        final Class248 this$0;
        
        
        Class172(final Class248 this$0) {
            this.this$0 = this$0;
            this.v0 = -1;
            this.v1 = -1;
            this.t0 = -1;
            this.t1 = -1;
        }
    }
    
    class Class114
    {
        int[] v;
        final Class248 this$0;
        
        
        Class114(final Class248 this$0, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            (this.v = new int[3])[0] = n;
            this.v[1] = n2;
            this.v[2] = n3;
        }
    }
    
    class Class32 extends Exception
    {
        final Class248 this$0;
        
        
        public Class32(final Class248 this$0, final String s) {
            this.this$0 = this$0;
            super(s);
        }
    }
}
