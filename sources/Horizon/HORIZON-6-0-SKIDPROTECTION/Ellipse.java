package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class Ellipse extends Shape
{
    protected static final int Â = 50;
    private int HorizonCode_Horizon_È;
    private float Ý;
    private float Ø­áŒŠá;
    
    public Ellipse(final float centerPointX, final float centerPointY, final float radius1, final float radius2) {
        this(centerPointX, centerPointY, radius1, radius2, 50);
    }
    
    public Ellipse(final float centerPointX, final float centerPointY, final float radius1, final float radius2, final int segmentCount) {
        this.áˆºÑ¢Õ = centerPointX - radius1;
        this.ÂµÈ = centerPointY - radius2;
        this.Ý = radius1;
        this.Ø­áŒŠá = radius2;
        this.HorizonCode_Horizon_È = segmentCount;
        this.ÇŽÉ();
    }
    
    public void Â(final float radius1, final float radius2) {
        this.Â(radius1);
        this.Ý(radius2);
    }
    
    public float Ø() {
        return this.Ý;
    }
    
    public void Â(final float radius1) {
        if (radius1 != this.Ý) {
            this.Ý = radius1;
            this.µà = true;
        }
    }
    
    public float E_() {
        return this.Ø­áŒŠá;
    }
    
    public void Ý(final float radius2) {
        if (radius2 != this.Ø­áŒŠá) {
            this.Ø­áŒŠá = radius2;
            this.µà = true;
        }
    }
    
    @Override
    protected void Ø­áŒŠá() {
        final ArrayList tempPoints = new ArrayList();
        this.á = -1.4E-45f;
        this.ˆÏ­ = -1.4E-45f;
        this.£á = Float.MAX_VALUE;
        this.Å = Float.MAX_VALUE;
        final float start = 0.0f;
        final float end = 359.0f;
        final float cx = this.áˆºÑ¢Õ + this.Ý;
        final float cy = this.ÂµÈ + this.Ø­áŒŠá;
        final int step = 360 / this.HorizonCode_Horizon_È;
        for (float a = start; a <= end + step; a += step) {
            float ang = a;
            if (ang > end) {
                ang = end;
            }
            final float newX = (float)(cx + FastTrig.Â(Math.toRadians(ang)) * this.Ý);
            final float newY = (float)(cy + FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang)) * this.Ø­áŒŠá);
            if (newX > this.á) {
                this.á = newX;
            }
            if (newY > this.ˆÏ­) {
                this.ˆÏ­ = newY;
            }
            if (newX < this.£á) {
                this.£á = newX;
            }
            if (newY < this.Å) {
                this.Å = newY;
            }
            tempPoints.add(new Float(newX));
            tempPoints.add(new Float(newY));
        }
        this.Ø = new float[tempPoints.size()];
        for (int i = 0; i < this.Ø.length; ++i) {
            this.Ø[i] = tempPoints.get(i);
        }
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        this.ÇŽÉ();
        final Polygon resultPolygon = new Polygon();
        final float[] result = new float[this.Ø.length];
        transform.HorizonCode_Horizon_È(this.Ø, 0, result, 0, this.Ø.length / 2);
        resultPolygon.Ø = result;
        resultPolygon.ÇŽÉ();
        return resultPolygon;
    }
    
    @Override
    protected void Âµá€() {
        (this.áŒŠÆ = new float[2])[0] = this.áˆºÑ¢Õ + this.Ý;
        this.áŒŠÆ[1] = this.ÂµÈ + this.Ø­áŒŠá;
    }
    
    @Override
    protected void Ó() {
        this.£à = ((this.Ý > this.Ø­áŒŠá) ? this.Ý : this.Ø­áŒŠá);
    }
}
