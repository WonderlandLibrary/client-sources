package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class Path extends Shape
{
    private ArrayList HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private boolean Ø­áŒŠá;
    private ArrayList Âµá€;
    private ArrayList Ó;
    
    public Path(final float sx, final float sy) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Âµá€ = new ArrayList();
        this.HorizonCode_Horizon_È.add(new float[] { sx, sy });
        this.Â = sx;
        this.Ý = sy;
        this.µà = true;
    }
    
    public void Â(final float sx, final float sy) {
        this.Ó = new ArrayList();
        this.Âµá€.add(this.Ó);
    }
    
    public void Ý(final float x, final float y) {
        if (this.Ó != null) {
            this.Ó.add(new float[] { x, y });
        }
        else {
            this.HorizonCode_Horizon_È.add(new float[] { x, y });
        }
        this.Â = x;
        this.Ý = y;
        this.µà = true;
    }
    
    public void Ø() {
        this.Ø­áŒŠá = true;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float cx1, final float cy1, final float cx2, final float cy2) {
        this.HorizonCode_Horizon_È(x, y, cx1, cy1, cx2, cy2, 10);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float cx1, final float cy1, final float cx2, final float cy2, final int segments) {
        if (this.Â == x && this.Ý == y) {
            return;
        }
        final Curve curve = new Curve(new Vector2f(this.Â, this.Ý), new Vector2f(cx1, cy1), new Vector2f(cx2, cy2), new Vector2f(x, y));
        final float step = 1.0f / segments;
        for (int i = 1; i < segments + 1; ++i) {
            final float t = i * step;
            final Vector2f p = curve.HorizonCode_Horizon_È(t);
            if (this.Ó != null) {
                this.Ó.add(new float[] { p.HorizonCode_Horizon_È, p.Â });
            }
            else {
                this.HorizonCode_Horizon_È.add(new float[] { p.HorizonCode_Horizon_È, p.Â });
            }
            this.Â = p.HorizonCode_Horizon_È;
            this.Ý = p.Â;
        }
        this.µà = true;
    }
    
    @Override
    protected void Ø­áŒŠá() {
        this.Ø = new float[this.HorizonCode_Horizon_È.size() * 2];
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            final float[] p = this.HorizonCode_Horizon_È.get(i);
            this.Ø[i * 2] = p[0];
            this.Ø[i * 2 + 1] = p[1];
        }
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        final Path p = new Path(this.Â, this.Ý);
        p.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, transform);
        for (int i = 0; i < this.Âµá€.size(); ++i) {
            p.Âµá€.add(this.HorizonCode_Horizon_È(this.Âµá€.get(i), transform));
        }
        p.Ø­áŒŠá = this.Ø­áŒŠá;
        return p;
    }
    
    private ArrayList HorizonCode_Horizon_È(final ArrayList pts, final Transform t) {
        final float[] in = new float[pts.size() * 2];
        final float[] out = new float[pts.size() * 2];
        for (int i = 0; i < pts.size(); ++i) {
            in[i * 2] = ((float[])pts.get(i))[0];
            in[i * 2 + 1] = ((float[])pts.get(i))[1];
        }
        t.HorizonCode_Horizon_È(in, 0, out, 0, pts.size());
        final ArrayList outList = new ArrayList();
        for (int j = 0; j < pts.size(); ++j) {
            outList.add(new float[] { out[j * 2], out[j * 2 + 1] });
        }
        return outList;
    }
    
    @Override
    public boolean à() {
        return this.Ø­áŒŠá;
    }
}
