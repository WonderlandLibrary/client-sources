package ClassSub;

public class Class169 implements Class127
{
    private Class311 GL;
    public static int MAX_POINTS;
    private boolean antialias;
    private float width;
    private float[] points;
    private float[] colours;
    private int pts;
    private int cpt;
    private Class16 def;
    private boolean renderHalf;
    private boolean lineCaps;
    
    
    public Class169() {
        this.GL = Class197.get();
        this.width = 1.0f;
        this.def = new Class16();
        this.lineCaps = false;
        this.points = new float[Class169.MAX_POINTS * 2];
        this.colours = new float[Class169.MAX_POINTS * 4];
    }
    
    @Override
    public void setLineCaps(final boolean lineCaps) {
        this.lineCaps = lineCaps;
    }
    
    @Override
    public void start() {
        if (this.width == 1.0f) {
            this.def.start();
            return;
        }
        this.pts = 0;
        this.cpt = 0;
        this.GL.flush();
        final float[] array = this.GL.getCurrentColor();
        this.color(array[0], array[1], array[2], array[3]);
    }
    
    @Override
    public void end() {
        if (this.width == 1.0f) {
            this.def.end();
            return;
        }
        this.renderLines(this.points, this.pts);
    }
    
    @Override
    public void vertex(final float n, final float n2) {
        if (this.width == 1.0f) {
            this.def.vertex(n, n2);
            return;
        }
        this.points[this.pts * 2] = n;
        this.points[this.pts * 2 + 1] = n2;
        ++this.pts;
        final int n3 = this.pts - 1;
        this.color(this.colours[n3 * 4], this.colours[n3 * 4 + 1], this.colours[n3 * 4 + 2], this.colours[n3 * 4 + 3]);
    }
    
    @Override
    public void setWidth(final float width) {
        this.width = width;
    }
    
    @Override
    public void setAntiAlias(final boolean b) {
        this.def.setAntiAlias(b);
        this.antialias = b;
    }
    
    public void renderLines(final float[] array, final int n) {
        if (this.antialias) {
            this.GL.glEnable(2881);
            this.renderLinesImpl(array, n, this.width + 1.0f);
        }
        this.GL.glDisable(2881);
        this.renderLinesImpl(array, n, this.width);
        if (this.antialias) {
            this.GL.glEnable(2881);
        }
    }
    
    public void renderLinesImpl(final float[] array, final int n, final float n2) {
        final float n3 = n2 / 2.0f;
        float n4 = 0.0f;
        float n5 = 0.0f;
        float n6 = 0.0f;
        float n7 = 0.0f;
        this.GL.glBegin(7);
        for (int i = 0; i < n + 1; ++i) {
            int n8 = i;
            int n9 = i + 1;
            int n10 = i - 1;
            if (n10 < 0) {
                n10 += n;
            }
            if (n9 >= n) {
                n9 -= n;
            }
            if (n8 >= n) {
                n8 -= n;
            }
            final float n11 = array[n8 * 2];
            final float n12 = array[n8 * 2 + 1];
            final float n13 = array[n9 * 2];
            final float n14 = array[n9 * 2 + 1];
            final float n15 = n13 - n11;
            final float n16 = n14 - n12;
            if (n15 != 0.0f || n16 != 0.0f) {
                final float n17 = (float)Math.sqrt(n15 * n15 + n16 * n16);
                final float n18 = n15 * n3;
                final float n19 = n16 * n3;
                final float n20 = n18 / n17;
                final float n21 = n19 / n17;
                final float n22 = -n20;
                if (i != 0) {
                    this.bindColor(n10);
                    this.GL.glVertex3f(n4, n5, 0.0f);
                    this.GL.glVertex3f(n6, n7, 0.0f);
                    this.bindColor(n8);
                    this.GL.glVertex3f(n11 + n21, n12 + n22, 0.0f);
                    this.GL.glVertex3f(n11 - n21, n12 - n22, 0.0f);
                }
                n4 = n13 - n21;
                n5 = n14 - n22;
                n6 = n13 + n21;
                n7 = n14 + n22;
                if (i < n - 1) {
                    this.bindColor(n8);
                    this.GL.glVertex3f(n11 + n21, n12 + n22, 0.0f);
                    this.GL.glVertex3f(n11 - n21, n12 - n22, 0.0f);
                    this.bindColor(n9);
                    this.GL.glVertex3f(n13 - n21, n14 - n22, 0.0f);
                    this.GL.glVertex3f(n13 + n21, n14 + n22, 0.0f);
                }
            }
        }
        this.GL.glEnd();
        final float n23 = (n3 <= 12.5f) ? 5.0f : (180.0f / (float)Math.ceil(n3 / 2.5));
        if (this.lineCaps) {
            final float n24 = array[2] - array[0];
            final float n25 = array[3] - array[1];
            final float n26 = (float)Math.toDegrees(Math.atan2(n25, n24)) + 90.0f;
            if (n24 != 0.0f || n25 != 0.0f) {
                this.GL.glBegin(6);
                this.bindColor(0);
                this.GL.glVertex2f(array[0], array[1]);
                for (int n27 = 0; n27 < 180.0f + n23; n27 += (int)n23) {
                    final float n28 = (float)Math.toRadians(n26 + n27);
                    this.GL.glVertex2f(array[0] + (float)(Math.cos(n28) * n3), array[1] + (float)(Math.sin(n28) * n3));
                }
                this.GL.glEnd();
            }
        }
        if (this.lineCaps) {
            final float n29 = array[n * 2 - 2] - array[n * 2 - 4];
            final float n30 = array[n * 2 - 1] - array[n * 2 - 3];
            final float n31 = (float)Math.toDegrees(Math.atan2(n30, n29)) - 90.0f;
            if (n29 != 0.0f || n30 != 0.0f) {
                this.GL.glBegin(6);
                this.bindColor(n - 1);
                this.GL.glVertex2f(array[n * 2 - 2], array[n * 2 - 1]);
                for (int n32 = 0; n32 < 180.0f + n23; n32 += (int)n23) {
                    final float n33 = (float)Math.toRadians(n31 + n32);
                    this.GL.glVertex2f(array[n * 2 - 2] + (float)(Math.cos(n33) * n3), array[n * 2 - 1] + (float)(Math.sin(n33) * n3));
                }
                this.GL.glEnd();
            }
        }
    }
    
    private void bindColor(final int n) {
        if (n < this.cpt) {
            if (this.renderHalf) {
                this.GL.glColor4f(this.colours[n * 4] * 0.5f, this.colours[n * 4 + 1] * 0.5f, this.colours[n * 4 + 2] * 0.5f, this.colours[n * 4 + 3] * 0.5f);
            }
            else {
                this.GL.glColor4f(this.colours[n * 4], this.colours[n * 4 + 1], this.colours[n * 4 + 2], this.colours[n * 4 + 3]);
            }
        }
    }
    
    @Override
    public void color(final float n, final float n2, final float n3, final float n4) {
        if (this.width == 1.0f) {
            this.def.color(n, n2, n3, n4);
            return;
        }
        this.colours[this.pts * 4] = n;
        this.colours[this.pts * 4 + 1] = n2;
        this.colours[this.pts * 4 + 2] = n3;
        this.colours[this.pts * 4 + 3] = n4;
        ++this.cpt;
    }
    
    @Override
    public boolean applyGLLineFixes() {
        if (this.width == 1.0f) {
            return this.def.applyGLLineFixes();
        }
        return this.def.applyGLLineFixes();
    }
    
    static {
        Class169.MAX_POINTS = 10000;
    }
}
