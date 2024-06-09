package HORIZON-6-0-SKIDPROTECTION;

import java.io.Serializable;

public abstract class Shape implements Serializable
{
    public float[] Ø;
    public float[] áŒŠÆ;
    public float áˆºÑ¢Õ;
    public float ÂµÈ;
    public float á;
    public float ˆÏ­;
    public float £á;
    public float Å;
    public float £à;
    public boolean µà;
    public transient Triangulator ˆà;
    public boolean ¥Æ;
    
    public Shape() {
        this.µà = true;
    }
    
    public void Âµá€(final float x, final float y) {
        this.c_(x);
        this.d_(y);
    }
    
    public abstract Shape HorizonCode_Horizon_È(final Transform p0);
    
    protected abstract void Ø­áŒŠá();
    
    public float £á() {
        return this.áˆºÑ¢Õ;
    }
    
    public void c_(float x) {
        if (x != this.áˆºÑ¢Õ) {
            final float dx = x - this.áˆºÑ¢Õ;
            this.áˆºÑ¢Õ = x;
            if (this.Ø == null || this.áŒŠÆ == null) {
                this.ÇŽÉ();
            }
            for (int i = 0; i < this.Ø.length / 2; ++i) {
                final float[] ø = this.Ø;
                final int n = i * 2;
                ø[n] += dx;
            }
            final float[] áœšæ = this.áŒŠÆ;
            final int n2 = 0;
            áœšæ[n2] += dx;
            x += dx;
            this.á += dx;
            this.£á += dx;
            this.¥Æ = true;
        }
    }
    
    public void d_(float y) {
        if (y != this.ÂµÈ) {
            final float dy = y - this.ÂµÈ;
            this.ÂµÈ = y;
            if (this.Ø == null || this.áŒŠÆ == null) {
                this.ÇŽÉ();
            }
            for (int i = 0; i < this.Ø.length / 2; ++i) {
                final float[] ø = this.Ø;
                final int n = i * 2 + 1;
                ø[n] += dy;
            }
            final float[] áœšæ = this.áŒŠÆ;
            final int n2 = 1;
            áœšæ[n2] += dy;
            y += dy;
            this.ˆÏ­ += dy;
            this.Å += dy;
            this.¥Æ = true;
        }
    }
    
    public float Å() {
        return this.ÂµÈ;
    }
    
    public Vector2f Ø­à() {
        return new Vector2f(this.£á(), this.Å());
    }
    
    public void Ø­áŒŠá(final Vector2f loc) {
        this.c_(loc.HorizonCode_Horizon_È);
        this.d_(loc.Â);
    }
    
    public float HorizonCode_Horizon_È() {
        this.ÇŽÉ();
        return this.áŒŠÆ[0];
    }
    
    public void Ó(final float centerX) {
        if (this.Ø == null || this.áŒŠÆ == null) {
            this.ÇŽÉ();
        }
        final float xDiff = centerX - this.HorizonCode_Horizon_È();
        this.c_(this.áˆºÑ¢Õ + xDiff);
    }
    
    public float Â() {
        this.ÇŽÉ();
        return this.áŒŠÆ[1];
    }
    
    public void à(final float centerY) {
        if (this.Ø == null || this.áŒŠÆ == null) {
            this.ÇŽÉ();
        }
        final float yDiff = centerY - this.Â();
        this.d_(this.ÂµÈ + yDiff);
    }
    
    public float µÕ() {
        this.ÇŽÉ();
        return this.á;
    }
    
    public float Æ() {
        this.ÇŽÉ();
        return this.ˆÏ­;
    }
    
    public float Šáƒ() {
        this.ÇŽÉ();
        return this.£á;
    }
    
    public float Ï­Ðƒà() {
        this.ÇŽÉ();
        return this.Å;
    }
    
    public float áŒŠà() {
        this.ÇŽÉ();
        return this.£à;
    }
    
    public float[] Ý() {
        this.ÇŽÉ();
        return this.áŒŠÆ;
    }
    
    public float[] ŠÄ() {
        this.ÇŽÉ();
        return this.Ø;
    }
    
    public int Ñ¢á() {
        this.ÇŽÉ();
        return this.Ø.length / 2;
    }
    
    public float[] HorizonCode_Horizon_È(final int index) {
        this.ÇŽÉ();
        final float[] result = { this.Ø[index * 2], this.Ø[index * 2 + 1] };
        return result;
    }
    
    public float[] Â(final int index) {
        final float[] current = this.HorizonCode_Horizon_È(index);
        final float[] prev = this.HorizonCode_Horizon_È((index - 1 < 0) ? (this.Ñ¢á() - 1) : (index - 1));
        final float[] next = this.HorizonCode_Horizon_È((index + 1 >= this.Ñ¢á()) ? 0 : (index + 1));
        final float[] t1 = this.HorizonCode_Horizon_È(prev, current);
        final float[] t2 = this.HorizonCode_Horizon_È(current, next);
        if (index == 0 && !this.à()) {
            return t2;
        }
        if (index == this.Ñ¢á() - 1 && !this.à()) {
            return t1;
        }
        final float tx = (t1[0] + t2[0]) / 2.0f;
        final float ty = (t1[1] + t2[1]) / 2.0f;
        final float len = (float)Math.sqrt(tx * tx + ty * ty);
        return new float[] { tx / len, ty / len };
    }
    
    public boolean Ø­áŒŠá(final Shape other) {
        if (other.HorizonCode_Horizon_È(this)) {
            return false;
        }
        for (int i = 0; i < other.Ñ¢á(); ++i) {
            final float[] pt = other.HorizonCode_Horizon_È(i);
            if (!this.HorizonCode_Horizon_È(pt[0], pt[1])) {
                return false;
            }
        }
        return true;
    }
    
    private float[] HorizonCode_Horizon_È(final float[] start, final float[] end) {
        float dx = start[0] - end[0];
        float dy = start[1] - end[1];
        final float len = (float)Math.sqrt(dx * dx + dy * dy);
        dx /= len;
        dy /= len;
        return new float[] { -dy, dx };
    }
    
    public boolean Ó(final float x, final float y) {
        if (this.Ø.length == 0) {
            return false;
        }
        this.ÇŽÉ();
        final Line testLine = new Line(0.0f, 0.0f, 0.0f, 0.0f);
        final Vector2f pt = new Vector2f(x, y);
        for (int i = 0; i < this.Ø.length; i += 2) {
            int n = i + 2;
            if (n >= this.Ø.length) {
                n = 0;
            }
            testLine.HorizonCode_Horizon_È(this.Ø[i], this.Ø[i + 1], this.Ø[n], this.Ø[n + 1]);
            if (testLine.Â(pt)) {
                return true;
            }
        }
        return false;
    }
    
    public int à(final float x, final float y) {
        for (int i = 0; i < this.Ø.length; i += 2) {
            if (this.Ø[i] == x && this.Ø[i + 1] == y) {
                return i / 2;
            }
        }
        return -1;
    }
    
    public boolean HorizonCode_Horizon_È(final float x, final float y) {
        this.ÇŽÉ();
        if (this.Ø.length == 0) {
            return false;
        }
        boolean result = false;
        final int npoints = this.Ø.length;
        float xold = this.Ø[npoints - 2];
        float yold = this.Ø[npoints - 1];
        for (int i = 0; i < npoints; i += 2) {
            final float xnew = this.Ø[i];
            final float ynew = this.Ø[i + 1];
            float x2;
            float x3;
            float y2;
            float y3;
            if (xnew > xold) {
                x2 = xold;
                x3 = xnew;
                y2 = yold;
                y3 = ynew;
            }
            else {
                x2 = xnew;
                x3 = xold;
                y2 = ynew;
                y3 = yold;
            }
            if (xnew < x == x <= xold && (y - y2) * (x3 - x2) < (y3 - y2) * (x - x2)) {
                result = !result;
            }
            xold = xnew;
            yold = ynew;
        }
        return result;
    }
    
    public boolean HorizonCode_Horizon_È(final Shape shape) {
        this.ÇŽÉ();
        boolean result = false;
        final float[] points = this.ŠÄ();
        final float[] thatPoints = shape.ŠÄ();
        int length = points.length;
        int thatLength = thatPoints.length;
        if (!this.à()) {
            length -= 2;
        }
        if (!shape.à()) {
            thatLength -= 2;
        }
        for (int i = 0; i < length; i += 2) {
            int iNext = i + 2;
            if (iNext >= points.length) {
                iNext = 0;
            }
            for (int j = 0; j < thatLength; j += 2) {
                int jNext = j + 2;
                if (jNext >= thatPoints.length) {
                    jNext = 0;
                }
                final double unknownA = ((points[iNext] - points[i]) * (thatPoints[j + 1] - points[i + 1]) - (points[iNext + 1] - points[i + 1]) * (thatPoints[j] - points[i])) / ((points[iNext + 1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j]) - (points[iNext] - points[i]) * (thatPoints[jNext + 1] - thatPoints[j + 1]));
                final double unknownB = ((thatPoints[jNext] - thatPoints[j]) * (thatPoints[j + 1] - points[i + 1]) - (thatPoints[jNext + 1] - thatPoints[j + 1]) * (thatPoints[j] - points[i])) / ((points[iNext + 1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j]) - (points[iNext] - points[i]) * (thatPoints[jNext + 1] - thatPoints[j + 1]));
                if (unknownA >= 0.0 && unknownA <= 1.0 && unknownB >= 0.0 && unknownB <= 1.0) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
    
    public boolean Ø(final float x, final float y) {
        if (this.Ø.length == 0) {
            return false;
        }
        this.ÇŽÉ();
        for (int i = 0; i < this.Ø.length; i += 2) {
            if (this.Ø[i] == x && this.Ø[i + 1] == y) {
                return true;
            }
        }
        return false;
    }
    
    protected void Âµá€() {
        this.áŒŠÆ = new float[] { 0.0f, 0.0f };
        final int length = this.Ø.length;
        for (int i = 0; i < length; i += 2) {
            final float[] áœšæ = this.áŒŠÆ;
            final int n = 0;
            áœšæ[n] += this.Ø[i];
            final float[] áœšæ2 = this.áŒŠÆ;
            final int n2 = 1;
            áœšæ2[n2] += this.Ø[i + 1];
        }
        final float[] áœšæ3 = this.áŒŠÆ;
        final int n3 = 0;
        áœšæ3[n3] /= length / 2;
        final float[] áœšæ4 = this.áŒŠÆ;
        final int n4 = 1;
        áœšæ4[n4] /= length / 2;
    }
    
    protected void Ó() {
        this.£à = 0.0f;
        for (int i = 0; i < this.Ø.length; i += 2) {
            final float temp = (this.Ø[i] - this.áŒŠÆ[0]) * (this.Ø[i] - this.áŒŠÆ[0]) + (this.Ø[i + 1] - this.áŒŠÆ[1]) * (this.Ø[i + 1] - this.áŒŠÆ[1]);
            this.£à = ((this.£à > temp) ? this.£à : temp);
        }
        this.£à = (float)Math.sqrt(this.£à);
    }
    
    protected void ŒÏ() {
        if (!this.¥Æ && this.ˆà != null) {
            return;
        }
        if (this.Ø.length >= 6) {
            boolean clockwise = true;
            float area = 0.0f;
            for (int i = 0; i < this.Ø.length / 2 - 1; ++i) {
                final float x1 = this.Ø[i * 2];
                final float y1 = this.Ø[i * 2 + 1];
                final float x2 = this.Ø[i * 2 + 2];
                final float y2 = this.Ø[i * 2 + 3];
                area += x1 * y2 - y1 * x2;
            }
            area /= 2.0f;
            clockwise = (area > 0.0f);
            this.ˆà = new NeatTriangulator();
            for (int i = 0; i < this.Ø.length; i += 2) {
                this.ˆà.HorizonCode_Horizon_È(this.Ø[i], this.Ø[i + 1]);
            }
            this.ˆà.Â();
        }
        this.¥Æ = false;
    }
    
    public void Çªà¢() {
        this.ÇŽÉ();
        this.ŒÏ();
        this.ˆà = new OverTriangulator(this.ˆà);
    }
    
    public Triangulator Ê() {
        this.ÇŽÉ();
        this.ŒÏ();
        return this.ˆà;
    }
    
    protected final void ÇŽÉ() {
        if (this.µà) {
            this.Ø­áŒŠá();
            this.Âµá€();
            this.Ó();
            if (this.Ø.length > 0) {
                this.á = this.Ø[0];
                this.ˆÏ­ = this.Ø[1];
                this.£á = this.Ø[0];
                this.Å = this.Ø[1];
                for (int i = 0; i < this.Ø.length / 2; ++i) {
                    this.á = Math.max(this.Ø[i * 2], this.á);
                    this.ˆÏ­ = Math.max(this.Ø[i * 2 + 1], this.ˆÏ­);
                    this.£á = Math.min(this.Ø[i * 2], this.£á);
                    this.Å = Math.min(this.Ø[i * 2 + 1], this.Å);
                }
            }
            this.µà = false;
            this.¥Æ = true;
        }
    }
    
    public void ˆá() {
        this.ÇŽÉ();
        this.Ê();
    }
    
    public boolean à() {
        return true;
    }
    
    public Shape ÇŽÕ() {
        final Polygon result = new Polygon();
        for (int i = 0; i < this.Ñ¢á(); ++i) {
            final int next = (i + 1 >= this.Ñ¢á()) ? 0 : (i + 1);
            final int prev = (i - 1 < 0) ? (this.Ñ¢á() - 1) : (i - 1);
            float dx1 = this.HorizonCode_Horizon_È(i)[0] - this.HorizonCode_Horizon_È(prev)[0];
            float dy1 = this.HorizonCode_Horizon_È(i)[1] - this.HorizonCode_Horizon_È(prev)[1];
            float dx2 = this.HorizonCode_Horizon_È(next)[0] - this.HorizonCode_Horizon_È(i)[0];
            float dy2 = this.HorizonCode_Horizon_È(next)[1] - this.HorizonCode_Horizon_È(i)[1];
            final float len1 = (float)Math.sqrt(dx1 * dx1 + dy1 * dy1);
            final float len2 = (float)Math.sqrt(dx2 * dx2 + dy2 * dy2);
            dx1 /= len1;
            dy1 /= len1;
            dx2 /= len2;
            dy2 /= len2;
            if (dx1 != dx2 || dy1 != dy2) {
                result.Â(this.HorizonCode_Horizon_È(i)[0], this.HorizonCode_Horizon_È(i)[1]);
            }
        }
        return result;
    }
    
    public Shape[] Âµá€(final Shape other) {
        return new GeomUtil().HorizonCode_Horizon_È(this, other);
    }
    
    public Shape[] Ó(final Shape other) {
        return new GeomUtil().Â(this, other);
    }
    
    public float F_() {
        return this.á - this.£á;
    }
    
    public float G_() {
        return this.ˆÏ­ - this.Å;
    }
}
