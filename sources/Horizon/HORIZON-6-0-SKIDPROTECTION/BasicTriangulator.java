package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class BasicTriangulator implements Triangulator
{
    private static final float HorizonCode_Horizon_È = 1.0E-10f;
    private Â Â;
    private Â Ý;
    private boolean Ø­áŒŠá;
    
    public BasicTriangulator() {
        this.Â = new Â();
        this.Ý = new Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        final HorizonCode_Horizon_È p = new HorizonCode_Horizon_È(x, y);
        if (!this.Â.HorizonCode_Horizon_È(p)) {
            this.Â.Â(p);
        }
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Â.HorizonCode_Horizon_È();
    }
    
    public float[] HorizonCode_Horizon_È(final int index) {
        return new float[] { this.Â.HorizonCode_Horizon_È(index).Â, this.Â.HorizonCode_Horizon_È(index).Ý };
    }
    
    @Override
    public boolean Â() {
        this.Ø­áŒŠá = true;
        final boolean worked = this.HorizonCode_Horizon_È(this.Â, this.Ý);
        return worked;
    }
    
    @Override
    public int Ý() {
        if (!this.Ø­áŒŠá) {
            throw new RuntimeException("Call triangulate() before accessing triangles");
        }
        return this.Ý.HorizonCode_Horizon_È() / 3;
    }
    
    @Override
    public float[] HorizonCode_Horizon_È(final int tri, final int i) {
        if (!this.Ø­áŒŠá) {
            throw new RuntimeException("Call triangulate() before accessing triangles");
        }
        return this.Ý.HorizonCode_Horizon_È(tri * 3 + i).Ý();
    }
    
    private float HorizonCode_Horizon_È(final Â contour) {
        final int n = contour.HorizonCode_Horizon_È();
        float A = 0.0f;
        int p = n - 1;
        for (int q = 0; q < n; p = q++) {
            final HorizonCode_Horizon_È contourP = contour.HorizonCode_Horizon_È(p);
            final HorizonCode_Horizon_È contourQ = contour.HorizonCode_Horizon_È(q);
            A += contourP.HorizonCode_Horizon_È() * contourQ.Â() - contourQ.HorizonCode_Horizon_È() * contourP.Â();
        }
        return A * 0.5f;
    }
    
    private boolean HorizonCode_Horizon_È(final float Ax, final float Ay, final float Bx, final float By, final float Cx, final float Cy, final float Px, final float Py) {
        final float ax = Cx - Bx;
        final float ay = Cy - By;
        final float bx = Ax - Cx;
        final float by = Ay - Cy;
        final float cx = Bx - Ax;
        final float cy = By - Ay;
        final float apx = Px - Ax;
        final float apy = Py - Ay;
        final float bpx = Px - Bx;
        final float bpy = Py - By;
        final float cpx = Px - Cx;
        final float cpy = Py - Cy;
        final float aCROSSbp = ax * bpy - ay * bpx;
        final float cCROSSap = cx * apy - cy * apx;
        final float bCROSScp = bx * cpy - by * cpx;
        return aCROSSbp >= 0.0f && bCROSScp >= 0.0f && cCROSSap >= 0.0f;
    }
    
    private boolean HorizonCode_Horizon_È(final Â contour, final int u, final int v, final int w, final int n, final int[] V) {
        final float Ax = contour.HorizonCode_Horizon_È(V[u]).HorizonCode_Horizon_È();
        final float Ay = contour.HorizonCode_Horizon_È(V[u]).Â();
        final float Bx = contour.HorizonCode_Horizon_È(V[v]).HorizonCode_Horizon_È();
        final float By = contour.HorizonCode_Horizon_È(V[v]).Â();
        final float Cx = contour.HorizonCode_Horizon_È(V[w]).HorizonCode_Horizon_È();
        final float Cy = contour.HorizonCode_Horizon_È(V[w]).Â();
        if (1.0E-10f > (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)) {
            return false;
        }
        for (int p = 0; p < n; ++p) {
            if (p != u && p != v) {
                if (p != w) {
                    final float Px = contour.HorizonCode_Horizon_È(V[p]).HorizonCode_Horizon_È();
                    final float Py = contour.HorizonCode_Horizon_È(V[p]).Â();
                    if (this.HorizonCode_Horizon_È(Ax, Ay, Bx, By, Cx, Cy, Px, Py)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean HorizonCode_Horizon_È(final Â contour, final Â result) {
        result.Â();
        final int n = contour.HorizonCode_Horizon_È();
        if (n < 3) {
            return false;
        }
        final int[] V = new int[n];
        if (0.0f < this.HorizonCode_Horizon_È(contour)) {
            for (int v = 0; v < n; ++v) {
                V[v] = v;
            }
        }
        else {
            for (int v = 0; v < n; ++v) {
                V[v] = n - 1 - v;
            }
        }
        int nv = n;
        int count = 2 * nv;
        int m = 0;
        int v2 = nv - 1;
        while (nv > 2) {
            if (count-- <= 0) {
                return false;
            }
            int u = v2;
            if (nv <= u) {
                u = 0;
            }
            v2 = u + 1;
            if (nv <= v2) {
                v2 = 0;
            }
            int w = v2 + 1;
            if (nv <= w) {
                w = 0;
            }
            if (!this.HorizonCode_Horizon_È(contour, u, v2, w, nv, V)) {
                continue;
            }
            final int a = V[u];
            final int b = V[v2];
            final int c = V[w];
            result.Â(contour.HorizonCode_Horizon_È(a));
            result.Â(contour.HorizonCode_Horizon_È(b));
            result.Â(contour.HorizonCode_Horizon_È(c));
            ++m;
            int s = v2;
            for (int t = v2 + 1; t < nv; ++t) {
                V[s] = V[t];
                ++s;
            }
            --nv;
            count = 2 * nv;
        }
        return true;
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    private class HorizonCode_Horizon_È
    {
        private float Â;
        private float Ý;
        private float[] Ø­áŒŠá;
        
        public HorizonCode_Horizon_È(final float x, final float y) {
            this.Â = x;
            this.Ý = y;
            this.Ø­áŒŠá = new float[] { x, y };
        }
        
        public float HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public float Â() {
            return this.Ý;
        }
        
        public float[] Ý() {
            return this.Ø­áŒŠá;
        }
        
        @Override
        public int hashCode() {
            return (int)(this.Â * this.Ý * 31.0f);
        }
        
        @Override
        public boolean equals(final Object other) {
            if (other instanceof HorizonCode_Horizon_È) {
                final HorizonCode_Horizon_È p = (HorizonCode_Horizon_È)other;
                return p.Â == this.Â && p.Ý == this.Ý;
            }
            return false;
        }
    }
    
    private class Â
    {
        private ArrayList Â;
        
        public Â() {
            this.Â = new ArrayList();
        }
        
        public boolean HorizonCode_Horizon_È(final HorizonCode_Horizon_È p) {
            return this.Â.contains(p);
        }
        
        public void Â(final HorizonCode_Horizon_È point) {
            this.Â.add(point);
        }
        
        public void Ý(final HorizonCode_Horizon_È point) {
            this.Â.remove(point);
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Â.size();
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final int i) {
            return this.Â.get(i);
        }
        
        public void Â() {
            this.Â.clear();
        }
    }
}
