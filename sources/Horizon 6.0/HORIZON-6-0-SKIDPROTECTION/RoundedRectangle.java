package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

public class RoundedRectangle extends Rectangle
{
    public static final int Ý = 1;
    public static final int Ø­áŒŠá = 2;
    public static final int Âµá€ = 4;
    public static final int Ó = 8;
    public static final int à = 15;
    private static final int Ø­à = 25;
    private float µÕ;
    private int Æ;
    private int Šáƒ;
    
    public RoundedRectangle(final float x, final float y, final float width, final float height, final float cornerRadius) {
        this(x, y, width, height, cornerRadius, 25);
    }
    
    public RoundedRectangle(final float x, final float y, final float width, final float height, final float cornerRadius, final int segmentCount) {
        this(x, y, width, height, cornerRadius, segmentCount, 15);
    }
    
    public RoundedRectangle(final float x, final float y, final float width, final float height, final float cornerRadius, final int segmentCount, final int cornerFlags) {
        super(x, y, width, height);
        if (cornerRadius < 0.0f) {
            throw new IllegalArgumentException("corner radius must be >= 0");
        }
        this.áˆºÑ¢Õ = x;
        this.ÂµÈ = y;
        this.HorizonCode_Horizon_È = width;
        this.Â = height;
        this.µÕ = cornerRadius;
        this.Æ = segmentCount;
        this.µà = true;
        this.Šáƒ = cornerFlags;
    }
    
    public float áˆºÑ¢Õ() {
        return this.µÕ;
    }
    
    public void Âµá€(final float cornerRadius) {
        if (cornerRadius >= 0.0f && cornerRadius != this.µÕ) {
            this.µÕ = cornerRadius;
            this.µà = true;
        }
    }
    
    @Override
    public float G_() {
        return this.Â;
    }
    
    @Override
    public void Ø­áŒŠá(final float height) {
        if (this.Â != height) {
            this.Â = height;
            this.µà = true;
        }
    }
    
    @Override
    public float F_() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void Ý(final float width) {
        if (width != this.HorizonCode_Horizon_È) {
            this.HorizonCode_Horizon_È = width;
            this.µà = true;
        }
    }
    
    @Override
    protected void Ø­áŒŠá() {
        this.á = this.áˆºÑ¢Õ + this.HorizonCode_Horizon_È;
        this.ˆÏ­ = this.ÂµÈ + this.Â;
        this.£á = this.áˆºÑ¢Õ;
        this.Å = this.ÂµÈ;
        final float useWidth = this.HorizonCode_Horizon_È - 1.0f;
        final float useHeight = this.Â - 1.0f;
        if (this.µÕ == 0.0f) {
            (this.Ø = new float[8])[0] = this.áˆºÑ¢Õ;
            this.Ø[1] = this.ÂµÈ;
            this.Ø[2] = this.áˆºÑ¢Õ + useWidth;
            this.Ø[3] = this.ÂµÈ;
            this.Ø[4] = this.áˆºÑ¢Õ + useWidth;
            this.Ø[5] = this.ÂµÈ + useHeight;
            this.Ø[6] = this.áˆºÑ¢Õ;
            this.Ø[7] = this.ÂµÈ + useHeight;
        }
        else {
            float doubleRadius = this.µÕ * 2.0f;
            if (doubleRadius > useWidth) {
                doubleRadius = useWidth;
                this.µÕ = doubleRadius / 2.0f;
            }
            if (doubleRadius > useHeight) {
                doubleRadius = useHeight;
                this.µÕ = doubleRadius / 2.0f;
            }
            final ArrayList tempPoints = new ArrayList();
            if ((this.Šáƒ & 0x1) != 0x0) {
                tempPoints.addAll(this.HorizonCode_Horizon_È(this.Æ, this.µÕ, this.áˆºÑ¢Õ + this.µÕ, this.ÂµÈ + this.µÕ, 180.0f, 270.0f));
            }
            else {
                tempPoints.add(new Float(this.áˆºÑ¢Õ));
                tempPoints.add(new Float(this.ÂµÈ));
            }
            if ((this.Šáƒ & 0x2) != 0x0) {
                tempPoints.addAll(this.HorizonCode_Horizon_È(this.Æ, this.µÕ, this.áˆºÑ¢Õ + useWidth - this.µÕ, this.ÂµÈ + this.µÕ, 270.0f, 360.0f));
            }
            else {
                tempPoints.add(new Float(this.áˆºÑ¢Õ + useWidth));
                tempPoints.add(new Float(this.ÂµÈ));
            }
            if ((this.Šáƒ & 0x4) != 0x0) {
                tempPoints.addAll(this.HorizonCode_Horizon_È(this.Æ, this.µÕ, this.áˆºÑ¢Õ + useWidth - this.µÕ, this.ÂµÈ + useHeight - this.µÕ, 0.0f, 90.0f));
            }
            else {
                tempPoints.add(new Float(this.áˆºÑ¢Õ + useWidth));
                tempPoints.add(new Float(this.ÂµÈ + useHeight));
            }
            if ((this.Šáƒ & 0x8) != 0x0) {
                tempPoints.addAll(this.HorizonCode_Horizon_È(this.Æ, this.µÕ, this.áˆºÑ¢Õ + this.µÕ, this.ÂµÈ + useHeight - this.µÕ, 90.0f, 180.0f));
            }
            else {
                tempPoints.add(new Float(this.áˆºÑ¢Õ));
                tempPoints.add(new Float(this.ÂµÈ + useHeight));
            }
            this.Ø = new float[tempPoints.size()];
            for (int i = 0; i < tempPoints.size(); ++i) {
                this.Ø[i] = tempPoints.get(i);
            }
        }
        this.Âµá€();
        this.Ó();
    }
    
    private List HorizonCode_Horizon_È(final int numberOfSegments, final float radius, final float cx, final float cy, final float start, final float end) {
        final ArrayList tempPoints = new ArrayList();
        final int step = 360 / numberOfSegments;
        for (float a = start; a <= end + step; a += step) {
            float ang = a;
            if (ang > end) {
                ang = end;
            }
            final float x = (float)(cx + FastTrig.Â(Math.toRadians(ang)) * radius);
            final float y = (float)(cy + FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang)) * radius);
            tempPoints.add(new Float(x));
            tempPoints.add(new Float(y));
        }
        return tempPoints;
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        this.ÇŽÉ();
        final Polygon resultPolygon = new Polygon();
        final float[] result = new float[this.Ø.length];
        transform.HorizonCode_Horizon_È(this.Ø, 0, result, 0, this.Ø.length / 2);
        resultPolygon.Ø = result;
        resultPolygon.Âµá€();
        return resultPolygon;
    }
}
