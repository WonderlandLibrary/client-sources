package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class Polygon extends Shape
{
    private boolean HorizonCode_Horizon_È;
    private boolean Â;
    
    public Polygon(final float[] points) {
        this.HorizonCode_Horizon_È = false;
        this.Â = true;
        final int length = points.length;
        this.Ø = new float[length];
        this.á = -1.4E-45f;
        this.ˆÏ­ = -1.4E-45f;
        this.£á = Float.MAX_VALUE;
        this.Å = Float.MAX_VALUE;
        this.áˆºÑ¢Õ = Float.MAX_VALUE;
        this.ÂµÈ = Float.MAX_VALUE;
        for (int i = 0; i < length; ++i) {
            this.Ø[i] = points[i];
            if (i % 2 == 0) {
                if (points[i] > this.á) {
                    this.á = points[i];
                }
                if (points[i] < this.£á) {
                    this.£á = points[i];
                }
                if (points[i] < this.áˆºÑ¢Õ) {
                    this.áˆºÑ¢Õ = points[i];
                }
            }
            else {
                if (points[i] > this.ˆÏ­) {
                    this.ˆÏ­ = points[i];
                }
                if (points[i] < this.Å) {
                    this.Å = points[i];
                }
                if (points[i] < this.ÂµÈ) {
                    this.ÂµÈ = points[i];
                }
            }
        }
        this.Âµá€();
        this.Ó();
        this.µà = true;
    }
    
    public Polygon() {
        this.HorizonCode_Horizon_È = false;
        this.Â = true;
        this.Ø = new float[0];
        this.á = -1.4E-45f;
        this.ˆÏ­ = -1.4E-45f;
        this.£á = Float.MAX_VALUE;
        this.Å = Float.MAX_VALUE;
    }
    
    public void HorizonCode_Horizon_È(final boolean allowDups) {
        this.HorizonCode_Horizon_È = allowDups;
    }
    
    public void Â(final float x, final float y) {
        if (this.Ø(x, y) && !this.HorizonCode_Horizon_È) {
            return;
        }
        final ArrayList tempPoints = new ArrayList();
        for (int i = 0; i < this.Ø.length; ++i) {
            tempPoints.add(new Float(this.Ø[i]));
        }
        tempPoints.add(new Float(x));
        tempPoints.add(new Float(y));
        final int length = tempPoints.size();
        this.Ø = new float[length];
        for (int j = 0; j < length; ++j) {
            this.Ø[j] = tempPoints.get(j);
        }
        if (x > this.á) {
            this.á = x;
        }
        if (y > this.ˆÏ­) {
            this.ˆÏ­ = y;
        }
        if (x < this.£á) {
            this.£á = x;
        }
        if (y < this.Å) {
            this.Å = y;
        }
        this.Âµá€();
        this.Ó();
        this.µà = true;
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        this.ÇŽÉ();
        final Polygon resultPolygon = new Polygon();
        final float[] result = new float[this.Ø.length];
        transform.HorizonCode_Horizon_È(this.Ø, 0, result, 0, this.Ø.length / 2);
        resultPolygon.Ø = result;
        resultPolygon.Âµá€();
        resultPolygon.Â = this.Â;
        return resultPolygon;
    }
    
    @Override
    public void c_(final float x) {
        super.c_(x);
        this.µà = false;
    }
    
    @Override
    public void d_(final float y) {
        super.d_(y);
        this.µà = false;
    }
    
    @Override
    protected void Ø­áŒŠá() {
    }
    
    @Override
    public boolean à() {
        return this.Â;
    }
    
    public void Â(final boolean closed) {
        this.Â = closed;
    }
    
    public Polygon Ø() {
        final float[] copyPoints = new float[this.Ø.length];
        System.arraycopy(this.Ø, 0, copyPoints, 0, copyPoints.length);
        return new Polygon(copyPoints);
    }
}
