package HORIZON-6-0-SKIDPROTECTION;

public class NeatTriangulator implements Triangulator
{
    static final float HorizonCode_Horizon_È = 1.0E-6f;
    private float[] Â;
    private float[] Ý;
    private int Ø­áŒŠá;
    private HorizonCode_Horizon_È[] Âµá€;
    private int[] Ó;
    private int à;
    private Ý[] Ø;
    private int áŒŠÆ;
    private float áˆºÑ¢Õ;
    
    public NeatTriangulator() {
        this.áˆºÑ¢Õ = 1.0E-6f;
        this.Â = new float[100];
        this.Ý = new float[100];
        this.Ø­áŒŠá = 0;
        this.Âµá€ = new HorizonCode_Horizon_È[100];
        this.à = 0;
        this.Ø = new Ý[100];
        this.áŒŠÆ = 0;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ø­áŒŠá = 0;
        this.à = 0;
        this.áŒŠÆ = 0;
    }
    
    private int Â(final int i, final int j) {
        int k;
        int l;
        if (i < j) {
            k = i;
            l = j;
        }
        else {
            k = j;
            l = i;
        }
        for (int i2 = 0; i2 < this.à; ++i2) {
            if (this.Âµá€[i2].HorizonCode_Horizon_È == k && this.Âµá€[i2].Â == l) {
                return i2;
            }
        }
        return -1;
    }
    
    private void HorizonCode_Horizon_È(final int i, final int j, final int k) {
        int l1 = this.Â(i, j);
        int j2;
        int k2;
        HorizonCode_Horizon_È edge;
        if (l1 < 0) {
            if (this.à == this.Âµá€.length) {
                final HorizonCode_Horizon_È[] aedge = new HorizonCode_Horizon_È[this.Âµá€.length * 2];
                System.arraycopy(this.Âµá€, 0, aedge, 0, this.à);
                this.Âµá€ = aedge;
            }
            j2 = -1;
            k2 = -1;
            l1 = this.à++;
            final HorizonCode_Horizon_È[] âµá€ = this.Âµá€;
            final int n = l1;
            final HorizonCode_Horizon_È horizonCode_Horizon_È = new HorizonCode_Horizon_È();
            âµá€[n] = horizonCode_Horizon_È;
            edge = horizonCode_Horizon_È;
        }
        else {
            edge = this.Âµá€[l1];
            j2 = edge.Ý;
            k2 = edge.Ø­áŒŠá;
        }
        int m;
        int i2;
        if (i < j) {
            m = i;
            i2 = j;
            j2 = k;
        }
        else {
            m = j;
            i2 = i;
            k2 = k;
        }
        edge.HorizonCode_Horizon_È = m;
        edge.Â = i2;
        edge.Ý = j2;
        edge.Ø­áŒŠá = k2;
        edge.Âµá€ = true;
    }
    
    private void Ý(final int i, final int j) throws Â {
        final int k;
        if ((k = this.Â(i, j)) < 0) {
            throw new Â("Attempt to delete unknown edge");
        }
        final HorizonCode_Horizon_È[] âµá€ = this.Âµá€;
        final int n = k;
        final HorizonCode_Horizon_È[] âµá€2 = this.Âµá€;
        final int à = this.à - 1;
        this.à = à;
        âµá€[n] = âµá€2[à];
    }
    
    void HorizonCode_Horizon_È(final int i, final int j, final boolean flag) throws Â {
        final int k;
        if ((k = this.Â(i, j)) < 0) {
            throw new Â("Attempt to mark unknown edge");
        }
        this.Âµá€[k].Âµá€ = flag;
    }
    
    private HorizonCode_Horizon_È Ó() {
        for (int i = 0; i < this.à; ++i) {
            final HorizonCode_Horizon_È edge = this.Âµá€[i];
            if (edge.Âµá€) {
                edge.Âµá€ = false;
                if (edge.Ý >= 0 && edge.Ø­áŒŠá >= 0) {
                    return edge;
                }
            }
        }
        return null;
    }
    
    private static float HorizonCode_Horizon_È(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float f6 = f4 - f2;
        final float f7 = f5 - f3;
        final float f8 = f - f4;
        final float f9 = f1 - f5;
        float f10 = f6 * f9 - f7 * f8;
        if (f10 > 0.0f) {
            if (f10 < 1.0E-6f) {
                f10 = 1.0E-6f;
            }
            final float f11 = f6 * f6;
            final float f12 = f7 * f7;
            final float f13 = f8 * f8;
            final float f14 = f9 * f9;
            final float f15 = f2 - f;
            final float f16 = f3 - f1;
            final float f17 = f15 * f15;
            final float f18 = f16 * f16;
            return (f11 + f12) * (f13 + f14) * (f17 + f18) / (f10 * f10);
        }
        return -1.0f;
    }
    
    private static boolean HorizonCode_Horizon_È(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final float f6, final float f7) {
        final float f8 = f4 - f2;
        final float f9 = f5 - f3;
        final float f10 = f - f4;
        final float f11 = f1 - f5;
        final float f12 = f2 - f;
        final float f13 = f3 - f1;
        final float f14 = f6 - f;
        final float f15 = f7 - f1;
        final float f16 = f6 - f2;
        final float f17 = f7 - f3;
        final float f18 = f6 - f4;
        final float f19 = f7 - f5;
        final float f20 = f8 * f17 - f9 * f16;
        final float f21 = f12 * f15 - f13 * f14;
        final float f22 = f10 * f19 - f11 * f18;
        return f20 >= 0.0 && f22 >= 0.0 && f21 >= 0.0;
    }
    
    private boolean HorizonCode_Horizon_È(final int i, final int j, final int k, final int l) {
        final float f = this.Â[this.Ó[i]];
        final float f2 = this.Ý[this.Ó[i]];
        final float f3 = this.Â[this.Ó[j]];
        final float f4 = this.Ý[this.Ó[j]];
        final float f5 = this.Â[this.Ó[k]];
        final float f6 = this.Ý[this.Ó[k]];
        if (1.0E-6f > (f3 - f) * (f6 - f2) - (f4 - f2) * (f5 - f)) {
            return false;
        }
        for (int i2 = 0; i2 < l; ++i2) {
            if (i2 != i && i2 != j && i2 != k) {
                final float f7 = this.Â[this.Ó[i2]];
                final float f8 = this.Ý[this.Ó[i2]];
                if (HorizonCode_Horizon_È(f, f2, f3, f4, f5, f6, f7, f8)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private float à() {
        float f = 0.0f;
        int i = this.Ø­áŒŠá - 1;
        for (int j = 0; j < this.Ø­áŒŠá; i = j++) {
            f += this.Â[i] * this.Ý[j] - this.Ý[i] * this.Â[j];
        }
        return f * 0.5f;
    }
    
    public void Âµá€() throws Â {
        int i = this.Ø­áŒŠá;
        if (i < 3) {
            return;
        }
        this.à = 0;
        this.áŒŠÆ = 0;
        this.Ó = new int[i];
        if (0.0 < this.à()) {
            for (int k = 0; k < i; ++k) {
                this.Ó[k] = k;
            }
        }
        else {
            for (int l = 0; l < i; ++l) {
                this.Ó[l] = this.Ø­áŒŠá - 1 - l;
            }
        }
        int k2 = 2 * i;
        int i2 = i - 1;
        while (i > 2) {
            if (k2-- <= 0) {
                throw new Â("Bad polygon");
            }
            int j = i2;
            if (i <= j) {
                j = 0;
            }
            i2 = j + 1;
            if (i <= i2) {
                i2 = 0;
            }
            int j2 = i2 + 1;
            if (i <= j2) {
                j2 = 0;
            }
            if (!this.HorizonCode_Horizon_È(j, i2, j2, i)) {
                continue;
            }
            final int l2 = this.Ó[j];
            final int i3 = this.Ó[i2];
            final int j3 = this.Ó[j2];
            if (this.áŒŠÆ == this.Ø.length) {
                final Ý[] atriangle = new Ý[this.Ø.length * 2];
                System.arraycopy(this.Ø, 0, atriangle, 0, this.áŒŠÆ);
                this.Ø = atriangle;
            }
            this.Ø[this.áŒŠÆ] = new Ý(l2, i3, j3);
            this.HorizonCode_Horizon_È(l2, i3, this.áŒŠÆ);
            this.HorizonCode_Horizon_È(i3, j3, this.áŒŠÆ);
            this.HorizonCode_Horizon_È(j3, l2, this.áŒŠÆ);
            ++this.áŒŠÆ;
            int k3 = i2;
            for (int l3 = i2 + 1; l3 < i; ++l3) {
                this.Ó[k3] = this.Ó[l3];
                ++k3;
            }
            --i;
            k2 = 2 * i;
        }
        this.Ó = null;
    }
    
    private void Ø() throws Â {
        HorizonCode_Horizon_È edge;
        while ((edge = this.Ó()) != null) {
            final int i1 = edge.HorizonCode_Horizon_È;
            final int k1 = edge.Â;
            final int j = edge.Ý;
            final int l = edge.Ø­áŒŠá;
            int j2 = -1;
            int l2 = -1;
            for (int m = 0; m < 3; ++m) {
                final int i2 = this.Ø[j].HorizonCode_Horizon_È[m];
                if (i1 != i2 && k1 != i2) {
                    l2 = i2;
                    break;
                }
            }
            for (int l3 = 0; l3 < 3; ++l3) {
                final int j3 = this.Ø[l].HorizonCode_Horizon_È[l3];
                if (i1 != j3 && k1 != j3) {
                    j2 = j3;
                    break;
                }
            }
            if (-1 == j2 || -1 == l2) {
                throw new Â("can't find quad");
            }
            final float f = this.Â[i1];
            final float f2 = this.Ý[i1];
            final float f3 = this.Â[j2];
            final float f4 = this.Ý[j2];
            final float f5 = this.Â[k1];
            final float f6 = this.Ý[k1];
            final float f7 = this.Â[l2];
            final float f8 = this.Ý[l2];
            float f9 = HorizonCode_Horizon_È(f, f2, f3, f4, f5, f6);
            final float f10 = HorizonCode_Horizon_È(f, f2, f5, f6, f7, f8);
            float f11 = HorizonCode_Horizon_È(f3, f4, f5, f6, f7, f8);
            final float f12 = HorizonCode_Horizon_È(f3, f4, f7, f8, f, f2);
            if (0.0f > f9 || 0.0f > f10) {
                throw new Â("original triangles backwards");
            }
            if (0.0f > f11 || 0.0f > f12) {
                continue;
            }
            if (f9 > f10) {
                f9 = f10;
            }
            if (f11 > f12) {
                f11 = f12;
            }
            if (f9 <= f11) {
                continue;
            }
            this.Ý(i1, k1);
            this.Ø[j].HorizonCode_Horizon_È[0] = j2;
            this.Ø[j].HorizonCode_Horizon_È[1] = k1;
            this.Ø[j].HorizonCode_Horizon_È[2] = l2;
            this.Ø[l].HorizonCode_Horizon_È[0] = j2;
            this.Ø[l].HorizonCode_Horizon_È[1] = l2;
            this.Ø[l].HorizonCode_Horizon_È[2] = i1;
            this.HorizonCode_Horizon_È(j2, k1, j);
            this.HorizonCode_Horizon_È(k1, l2, j);
            this.HorizonCode_Horizon_È(l2, j2, j);
            this.HorizonCode_Horizon_È(l2, i1, l);
            this.HorizonCode_Horizon_È(i1, j2, l);
            this.HorizonCode_Horizon_È(j2, l2, l);
            this.HorizonCode_Horizon_È(j2, l2, false);
        }
    }
    
    @Override
    public boolean Â() {
        try {
            this.Âµá€();
            return true;
        }
        catch (Â e) {
            this.à = 0;
            return false;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, float y) {
        for (int i = 0; i < this.Ø­áŒŠá; ++i) {
            if (this.Â[i] == x && this.Ý[i] == y) {
                y += this.áˆºÑ¢Õ;
                this.áˆºÑ¢Õ += 1.0E-6f;
            }
        }
        if (this.Ø­áŒŠá == this.Â.length) {
            float[] af = new float[this.Ø­áŒŠá * 2];
            System.arraycopy(this.Â, 0, af, 0, this.Ø­áŒŠá);
            this.Â = af;
            af = new float[this.Ø­áŒŠá * 2];
            System.arraycopy(this.Ý, 0, af, 0, this.Ø­áŒŠá);
            this.Ý = af;
        }
        this.Â[this.Ø­áŒŠá] = x;
        this.Ý[this.Ø­áŒŠá] = y;
        ++this.Ø­áŒŠá;
    }
    
    @Override
    public int Ý() {
        return this.áŒŠÆ;
    }
    
    @Override
    public float[] HorizonCode_Horizon_È(final int tri, final int i) {
        final float xp = this.Â[this.Ø[tri].HorizonCode_Horizon_È[i]];
        final float yp = this.Ý[this.Ø[tri].HorizonCode_Horizon_È[i]];
        return new float[] { xp, yp };
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    class Ý
    {
        int[] HorizonCode_Horizon_È;
        
        Ý(final int i, final int j, final int k) {
            (this.HorizonCode_Horizon_È = new int[3])[0] = i;
            this.HorizonCode_Horizon_È[1] = j;
            this.HorizonCode_Horizon_È[2] = k;
        }
    }
    
    class HorizonCode_Horizon_È
    {
        int HorizonCode_Horizon_È;
        int Â;
        int Ý;
        int Ø­áŒŠá;
        boolean Âµá€;
        
        HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = -1;
            this.Â = -1;
            this.Ý = -1;
            this.Ø­áŒŠá = -1;
        }
    }
    
    class Â extends Exception
    {
        public Â(final String msg) {
            super(msg);
        }
    }
}
