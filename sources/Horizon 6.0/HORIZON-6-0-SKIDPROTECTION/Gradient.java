package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class Gradient
{
    private String HorizonCode_Horizon_È;
    private ArrayList Â;
    private float Ý;
    private float Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private float à;
    private Image Ø;
    private boolean áŒŠÆ;
    private Transform áˆºÑ¢Õ;
    private String ÂµÈ;
    
    public Gradient(final String name, final boolean radial) {
        this.Â = new ArrayList();
        this.HorizonCode_Horizon_È = name;
        this.áŒŠÆ = radial;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.áŒŠÆ;
    }
    
    public void HorizonCode_Horizon_È(final Transform trans) {
        this.áˆºÑ¢Õ = trans;
    }
    
    public Transform Â() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final String ref) {
        this.ÂµÈ = ref;
    }
    
    public void HorizonCode_Horizon_È(final Diagram diagram) {
        if (this.ÂµÈ == null) {
            return;
        }
        final Gradient other = diagram.Â(this.ÂµÈ);
        for (int i = 0; i < other.Â.size(); ++i) {
            this.Â.add(other.Â.get(i));
        }
    }
    
    public void Ý() {
        if (this.Ø == null) {
            final ImageBuffer buffer = new ImageBuffer(128, 16);
            for (int i = 0; i < 128; ++i) {
                final Color col = this.Ó(i / 128.0f);
                for (int j = 0; j < 16; ++j) {
                    buffer.HorizonCode_Horizon_È(i, j, col.Ø(), col.áŒŠÆ(), col.áˆºÑ¢Õ(), col.ÂµÈ());
                }
            }
            this.Ø = buffer.Ø();
        }
    }
    
    public Image Ø­áŒŠá() {
        this.Ý();
        return this.Ø;
    }
    
    public void HorizonCode_Horizon_È(final float r) {
        this.à = r;
    }
    
    public void Â(final float x1) {
        this.Ý = x1;
    }
    
    public void Ý(final float x2) {
        this.Ø­áŒŠá = x2;
    }
    
    public void Ø­áŒŠá(final float y1) {
        this.Âµá€ = y1;
    }
    
    public void Âµá€(final float y2) {
        this.Ó = y2;
    }
    
    public float Âµá€() {
        return this.à;
    }
    
    public float Ó() {
        return this.Ý;
    }
    
    public float à() {
        return this.Ø­áŒŠá;
    }
    
    public float Ø() {
        return this.Âµá€;
    }
    
    public float áŒŠÆ() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final float location, final Color c) {
        this.Â.add(new HorizonCode_Horizon_È(location, c));
    }
    
    public Color Ó(float p) {
        if (p <= 0.0f) {
            return this.Â.get(0).Â;
        }
        if (p > 1.0f) {
            return this.Â.get(this.Â.size() - 1).Â;
        }
        for (int i = 1; i < this.Â.size(); ++i) {
            final HorizonCode_Horizon_È prev = this.Â.get(i - 1);
            final HorizonCode_Horizon_È current = this.Â.get(i);
            if (p <= current.HorizonCode_Horizon_È) {
                final float dis = current.HorizonCode_Horizon_È - prev.HorizonCode_Horizon_È;
                p -= prev.HorizonCode_Horizon_È;
                final float v = p / dis;
                final Color c = new Color(1, 1, 1, 1);
                c.¥Æ = prev.Â.¥Æ * (1.0f - v) + current.Â.¥Æ * v;
                c.£à = prev.Â.£à * (1.0f - v) + current.Â.£à * v;
                c.µà = prev.Â.µà * (1.0f - v) + current.Â.µà * v;
                c.ˆà = prev.Â.ˆà * (1.0f - v) + current.Â.ˆà * v;
                return c;
            }
        }
        return Color.Ø;
    }
    
    private class HorizonCode_Horizon_È
    {
        float HorizonCode_Horizon_È;
        Color Â;
        
        public HorizonCode_Horizon_È(final float location, final Color c) {
            this.HorizonCode_Horizon_È = location;
            this.Â = c;
        }
    }
}
