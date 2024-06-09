package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class MorphShape extends Shape
{
    private ArrayList HorizonCode_Horizon_È;
    private float Â;
    private Shape Ý;
    private Shape Ø­áŒŠá;
    
    public MorphShape(final Shape base) {
        (this.HorizonCode_Horizon_È = new ArrayList()).add(base);
        final float[] copy = base.Ø;
        this.Ø = new float[copy.length];
        this.Ý = base;
        this.Ø­áŒŠá = base;
    }
    
    public void Â(final Shape shape) {
        if (shape.Ø.length != this.Ø.length) {
            throw new RuntimeException("Attempt to morph between two shapes with different vertex counts");
        }
        final Shape prev = this.HorizonCode_Horizon_È.get(this.HorizonCode_Horizon_È.size() - 1);
        if (this.HorizonCode_Horizon_È(prev, shape)) {
            this.HorizonCode_Horizon_È.add(prev);
        }
        else {
            this.HorizonCode_Horizon_È.add(shape);
        }
        if (this.HorizonCode_Horizon_È.size() == 2) {
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È.get(1);
        }
    }
    
    private boolean HorizonCode_Horizon_È(final Shape a, final Shape b) {
        a.ÇŽÉ();
        b.ÇŽÉ();
        for (int i = 0; i < a.Ø.length; ++i) {
            if (a.Ø[i] != b.Ø[i]) {
                return false;
            }
        }
        return true;
    }
    
    public void HorizonCode_Horizon_È(final float time) {
        int p = (int)time;
        int n = p + 1;
        final float offset = time - p;
        p = this.Ý(p);
        n = this.Ý(n);
        this.HorizonCode_Horizon_È(p, n, offset);
    }
    
    public void Â(final float delta) {
        this.Â += delta;
        if (this.Â < 0.0f) {
            int index = this.HorizonCode_Horizon_È.indexOf(this.Ý);
            if (index < 0) {
                index = this.HorizonCode_Horizon_È.size() - 1;
            }
            final int nframe = this.Ý(index + 1);
            this.HorizonCode_Horizon_È(index, nframe, this.Â);
            ++this.Â;
        }
        else if (this.Â > 1.0f) {
            int index = this.HorizonCode_Horizon_È.indexOf(this.Ø­áŒŠá);
            if (index < 1) {
                index = 0;
            }
            final int nframe = this.Ý(index + 1);
            this.HorizonCode_Horizon_È(index, nframe, this.Â);
            --this.Â;
        }
        else {
            this.µà = true;
        }
    }
    
    public void Ý(final Shape current) {
        this.Ý = current;
        this.Ø­áŒŠá = this.HorizonCode_Horizon_È.get(0);
        this.Â = 0.0f;
    }
    
    private int Ý(int n) {
        while (n >= this.HorizonCode_Horizon_È.size()) {
            n -= this.HorizonCode_Horizon_È.size();
        }
        while (n < 0) {
            n += this.HorizonCode_Horizon_È.size();
        }
        return n;
    }
    
    private void HorizonCode_Horizon_È(final int a, final int b, final float offset) {
        this.Ý = this.HorizonCode_Horizon_È.get(a);
        this.Ø­áŒŠá = this.HorizonCode_Horizon_È.get(b);
        this.Â = offset;
        this.µà = true;
    }
    
    @Override
    protected void Ø­áŒŠá() {
        if (this.Ý == this.Ø­áŒŠá) {
            System.arraycopy(this.Ý.Ø, 0, this.Ø, 0, this.Ø.length);
            return;
        }
        final float[] apoints = this.Ý.Ø;
        final float[] bpoints = this.Ø­áŒŠá.Ø;
        for (int i = 0; i < this.Ø.length; ++i) {
            this.Ø[i] = apoints[i] * (1.0f - this.Â);
            final float[] ø = this.Ø;
            final int n = i;
            ø[n] += bpoints[i] * this.Â;
        }
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        this.Ø­áŒŠá();
        final Polygon poly = new Polygon(this.Ø);
        return poly;
    }
}
