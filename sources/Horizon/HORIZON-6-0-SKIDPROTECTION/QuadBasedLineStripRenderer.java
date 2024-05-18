package HORIZON-6-0-SKIDPROTECTION;

public class QuadBasedLineStripRenderer implements LineStripRenderer
{
    private SGL Â;
    public static int HorizonCode_Horizon_È;
    private boolean Ý;
    private float Ø­áŒŠá;
    private float[] Âµá€;
    private float[] Ó;
    private int à;
    private int Ø;
    private DefaultLineStripRenderer áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private boolean ÂµÈ;
    
    static {
        QuadBasedLineStripRenderer.HorizonCode_Horizon_È = 10000;
    }
    
    public QuadBasedLineStripRenderer() {
        this.Â = Renderer.HorizonCode_Horizon_È();
        this.Ø­áŒŠá = 1.0f;
        this.áŒŠÆ = new DefaultLineStripRenderer();
        this.ÂµÈ = false;
        this.Âµá€ = new float[QuadBasedLineStripRenderer.HorizonCode_Horizon_È * 2];
        this.Ó = new float[QuadBasedLineStripRenderer.HorizonCode_Horizon_È * 4];
    }
    
    @Override
    public void Â(final boolean caps) {
        this.ÂµÈ = caps;
    }
    
    @Override
    public void Â() {
        if (this.Ø­áŒŠá == 1.0f) {
            this.áŒŠÆ.Â();
            return;
        }
        this.à = 0;
        this.Ø = 0;
        this.Â.Ó();
        final float[] col = this.Â.à();
        this.HorizonCode_Horizon_È(col[0], col[1], col[2], col[3]);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Ø­áŒŠá == 1.0f) {
            this.áŒŠÆ.HorizonCode_Horizon_È();
            return;
        }
        this.HorizonCode_Horizon_È(this.Âµá€, this.à);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        if (this.Ø­áŒŠá == 1.0f) {
            this.áŒŠÆ.HorizonCode_Horizon_È(x, y);
            return;
        }
        this.Âµá€[this.à * 2] = x;
        this.Âµá€[this.à * 2 + 1] = y;
        ++this.à;
        final int index = this.à - 1;
        this.HorizonCode_Horizon_È(this.Ó[index * 4], this.Ó[index * 4 + 1], this.Ó[index * 4 + 2], this.Ó[index * 4 + 3]);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float width) {
        this.Ø­áŒŠá = width;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean antialias) {
        this.áŒŠÆ.HorizonCode_Horizon_È(antialias);
        this.Ý = antialias;
    }
    
    public void HorizonCode_Horizon_È(final float[] points, final int count) {
        if (this.Ý) {
            this.Â.Âµá€(2881);
            this.HorizonCode_Horizon_È(points, count, this.Ø­áŒŠá + 1.0f);
        }
        this.Â.Ø­áŒŠá(2881);
        this.HorizonCode_Horizon_È(points, count, this.Ø­áŒŠá);
        if (this.Ý) {
            this.Â.Âµá€(2881);
        }
    }
    
    public void HorizonCode_Horizon_È(final float[] points, final int count, final float w) {
        final float width = w / 2.0f;
        float lastx1 = 0.0f;
        float lasty1 = 0.0f;
        float lastx2 = 0.0f;
        float lasty2 = 0.0f;
        this.Â.HorizonCode_Horizon_È(7);
        for (int i = 0; i < count + 1; ++i) {
            int current = i;
            int next = i + 1;
            int prev = i - 1;
            if (prev < 0) {
                prev += count;
            }
            if (next >= count) {
                next -= count;
            }
            if (current >= count) {
                current -= count;
            }
            final float x1 = points[current * 2];
            final float y1 = points[current * 2 + 1];
            final float x2 = points[next * 2];
            final float y2 = points[next * 2 + 1];
            float dx = x2 - x1;
            float dy = y2 - y1;
            if (dx != 0.0f || dy != 0.0f) {
                final float d2 = dx * dx + dy * dy;
                final float d3 = (float)Math.sqrt(d2);
                dx *= width;
                dy *= width;
                dx /= d3;
                final float tx;
                dy = (tx = dy / d3);
                final float ty = -dx;
                if (i != 0) {
                    this.HorizonCode_Horizon_È(prev);
                    this.Â.Ý(lastx1, lasty1, 0.0f);
                    this.Â.Ý(lastx2, lasty2, 0.0f);
                    this.HorizonCode_Horizon_È(current);
                    this.Â.Ý(x1 + tx, y1 + ty, 0.0f);
                    this.Â.Ý(x1 - tx, y1 - ty, 0.0f);
                }
                lastx1 = x2 - tx;
                lasty1 = y2 - ty;
                lastx2 = x2 + tx;
                lasty2 = y2 + ty;
                if (i < count - 1) {
                    this.HorizonCode_Horizon_È(current);
                    this.Â.Ý(x1 + tx, y1 + ty, 0.0f);
                    this.Â.Ý(x1 - tx, y1 - ty, 0.0f);
                    this.HorizonCode_Horizon_È(next);
                    this.Â.Ý(x2 - tx, y2 - ty, 0.0f);
                    this.Â.Ý(x2 + tx, y2 + ty, 0.0f);
                }
            }
        }
        this.Â.HorizonCode_Horizon_È();
        final float step = (width <= 12.5f) ? 5.0f : (180.0f / (float)Math.ceil(width / 2.5));
        if (this.ÂµÈ) {
            final float dx2 = points[2] - points[0];
            final float dy2 = points[3] - points[1];
            final float fang = (float)Math.toDegrees(Math.atan2(dy2, dx2)) + 90.0f;
            if (dx2 != 0.0f || dy2 != 0.0f) {
                this.Â.HorizonCode_Horizon_È(6);
                this.HorizonCode_Horizon_È(0);
                this.Â.Â(points[0], points[1]);
                for (int j = 0; j < 180.0f + step; j += (int)step) {
                    final float ang = (float)Math.toRadians(fang + j);
                    this.Â.Â(points[0] + (float)(Math.cos(ang) * width), points[1] + (float)(Math.sin(ang) * width));
                }
                this.Â.HorizonCode_Horizon_È();
            }
        }
        if (this.ÂµÈ) {
            final float dx2 = points[count * 2 - 2] - points[count * 2 - 4];
            final float dy2 = points[count * 2 - 1] - points[count * 2 - 3];
            final float fang = (float)Math.toDegrees(Math.atan2(dy2, dx2)) - 90.0f;
            if (dx2 != 0.0f || dy2 != 0.0f) {
                this.Â.HorizonCode_Horizon_È(6);
                this.HorizonCode_Horizon_È(count - 1);
                this.Â.Â(points[count * 2 - 2], points[count * 2 - 1]);
                for (int j = 0; j < 180.0f + step; j += (int)step) {
                    final float ang = (float)Math.toRadians(fang + j);
                    this.Â.Â(points[count * 2 - 2] + (float)(Math.cos(ang) * width), points[count * 2 - 1] + (float)(Math.sin(ang) * width));
                }
                this.Â.HorizonCode_Horizon_È();
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final int index) {
        if (index < this.Ø) {
            if (this.áˆºÑ¢Õ) {
                this.Â.Â(this.Ó[index * 4] * 0.5f, this.Ó[index * 4 + 1] * 0.5f, this.Ó[index * 4 + 2] * 0.5f, this.Ó[index * 4 + 3] * 0.5f);
            }
            else {
                this.Â.Â(this.Ó[index * 4], this.Ó[index * 4 + 1], this.Ó[index * 4 + 2], this.Ó[index * 4 + 3]);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float r, final float g, final float b, final float a) {
        if (this.Ø­áŒŠá == 1.0f) {
            this.áŒŠÆ.HorizonCode_Horizon_È(r, g, b, a);
            return;
        }
        this.Ó[this.à * 4] = r;
        this.Ó[this.à * 4 + 1] = g;
        this.Ó[this.à * 4 + 2] = b;
        this.Ó[this.à * 4 + 3] = a;
        ++this.Ø;
    }
    
    @Override
    public boolean Ý() {
        if (this.Ø­áŒŠá == 1.0f) {
            return this.áŒŠÆ.Ý();
        }
        return this.áŒŠÆ.Ý();
    }
}
