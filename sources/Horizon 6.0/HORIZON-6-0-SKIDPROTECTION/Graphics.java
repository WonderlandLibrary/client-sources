package HORIZON-6-0-SKIDPROTECTION;

import java.nio.FloatBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.lwjgl.BufferUtils;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

public class Graphics
{
    protected static SGL HorizonCode_Horizon_È;
    private static LineStripRenderer á;
    public static int Â;
    public static int Ý;
    public static int Ø­áŒŠá;
    public static int Âµá€;
    public static int Ó;
    public static int à;
    private static final int ˆÏ­ = 50;
    protected static Graphics Ø;
    protected static Font áŒŠÆ;
    private float £á;
    private float Å;
    private Font £à;
    private Color µà;
    protected int áˆºÑ¢Õ;
    protected int ÂµÈ;
    private boolean ˆà;
    private Rectangle ¥Æ;
    private DoubleBuffer Ø­à;
    private ByteBuffer µÕ;
    private boolean Æ;
    private Rectangle Šáƒ;
    private int Ï­Ðƒà;
    private float áŒŠà;
    private ArrayList ŠÄ;
    private int Ñ¢á;
    
    static {
        Graphics.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
        Graphics.á = Renderer.Â();
        Graphics.Â = 1;
        Graphics.Ý = 2;
        Graphics.Ø­áŒŠá = 3;
        Graphics.Âµá€ = 4;
        Graphics.Ó = 5;
        Graphics.à = 6;
        Graphics.Ø = null;
    }
    
    public static void HorizonCode_Horizon_È(final Graphics current) {
        if (Graphics.Ø != current) {
            if (Graphics.Ø != null) {
                Graphics.Ø.Ø­áŒŠá();
            }
            (Graphics.Ø = current).Â();
        }
    }
    
    public Graphics() {
        this.£á = 1.0f;
        this.Å = 1.0f;
        this.µà = Color.Ý;
        this.Ø­à = BufferUtils.createDoubleBuffer(4);
        this.µÕ = BufferUtils.createByteBuffer(4);
        this.Ï­Ðƒà = Graphics.Â;
        this.áŒŠà = 1.0f;
        this.ŠÄ = new ArrayList();
    }
    
    public Graphics(final int width, final int height) {
        this.£á = 1.0f;
        this.Å = 1.0f;
        this.µà = Color.Ý;
        this.Ø­à = BufferUtils.createDoubleBuffer(4);
        this.µÕ = BufferUtils.createByteBuffer(4);
        this.Ï­Ðƒà = Graphics.Â;
        this.áŒŠà = 1.0f;
        this.ŠÄ = new ArrayList();
        if (Graphics.áŒŠÆ == null) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                @Override
                public Object run() {
                    try {
                        Graphics.áŒŠÆ = new AngelCodeFont("org/newdawn/slick/data/defaultfont.fnt", "org/newdawn/slick/data/defaultfont.png");
                    }
                    catch (SlickException e) {
                        Log.HorizonCode_Horizon_È(e);
                    }
                    return null;
                }
            });
        }
        this.£à = Graphics.áŒŠÆ;
        this.áˆºÑ¢Õ = width;
        this.ÂµÈ = height;
    }
    
    void HorizonCode_Horizon_È(final int width, final int height) {
        this.áˆºÑ¢Õ = width;
        this.ÂµÈ = height;
    }
    
    public void HorizonCode_Horizon_È(final int mode) {
        this.µÕ();
        this.Ï­Ðƒà = mode;
        if (this.Ï­Ðƒà == Graphics.Â) {
            Graphics.HorizonCode_Horizon_È.Âµá€(3042);
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, true, true, true);
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(770, 771);
        }
        if (this.Ï­Ðƒà == Graphics.Ý) {
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(3042);
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(false, false, false, true);
        }
        if (this.Ï­Ðƒà == Graphics.Ø­áŒŠá) {
            Graphics.HorizonCode_Horizon_È.Âµá€(3042);
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, true, true, false);
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(772, 773);
        }
        if (this.Ï­Ðƒà == Graphics.Âµá€) {
            Graphics.HorizonCode_Horizon_È.Âµá€(3042);
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, true, true, true);
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(769, 768);
        }
        if (this.Ï­Ðƒà == Graphics.Ó) {
            Graphics.HorizonCode_Horizon_È.Âµá€(3042);
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, true, true, true);
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(1, 1);
        }
        if (this.Ï­Ðƒà == Graphics.à) {
            Graphics.HorizonCode_Horizon_È.Âµá€(3042);
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, true, true, true);
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(1, 769);
        }
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È() {
        this.ˆà();
        Graphics.HorizonCode_Horizon_È.Ý();
        final int originalMode = this.Ï­Ðƒà;
        this.HorizonCode_Horizon_È(Graphics.Ý);
        this.Â(new Color(0, 0, 0, 0));
        this.Ø­áŒŠá(0.0f, 0.0f, this.áˆºÑ¢Õ, this.ÂµÈ);
        this.Â(this.µà);
        this.HorizonCode_Horizon_È(originalMode);
        this.¥Æ();
    }
    
    private void µÕ() {
        HorizonCode_Horizon_È(this);
    }
    
    private void Æ() {
    }
    
    protected void Â() {
    }
    
    public void Ý() {
        if (Graphics.Ø == this) {
            Graphics.Ø.Ø­áŒŠá();
            Graphics.Ø = null;
        }
    }
    
    protected void Ø­áŒŠá() {
    }
    
    public Font Âµá€() {
        return this.£à;
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(color.£à, color.µà, color.ˆà, color.¥Æ);
        this.Æ();
    }
    
    public Color Ó() {
        this.µÕ();
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3106, buffer);
        this.Æ();
        return new Color(buffer);
    }
    
    public void à() {
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.Ý(16384);
        this.Æ();
    }
    
    public void Ø() {
        this.£á = 1.0f;
        this.Å = 1.0f;
        if (this.ˆà) {
            this.µÕ();
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá();
            this.ˆà = false;
            this.Æ();
        }
    }
    
    private void Šáƒ() {
        if (!this.ˆà) {
            this.µÕ();
            Graphics.HorizonCode_Horizon_È.Âµá€();
            this.ˆà = true;
            this.Æ();
        }
    }
    
    public void HorizonCode_Horizon_È(final float sx, final float sy) {
        this.£á *= sx;
        this.Å *= sy;
        this.Šáƒ();
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(sx, sy, 1.0f);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final float rx, final float ry, final float ang) {
        this.Šáƒ();
        this.µÕ();
        this.Â(rx, ry);
        Graphics.HorizonCode_Horizon_È.Ý(ang, 0.0f, 0.0f, 1.0f);
        this.Â(-rx, -ry);
        this.Æ();
    }
    
    public void Â(final float x, final float y) {
        this.Šáƒ();
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.Â(x, y, 0.0f);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Font font) {
        this.£à = font;
    }
    
    public void áŒŠÆ() {
        this.£à = Graphics.áŒŠÆ;
    }
    
    public void Â(final Color color) {
        if (color == null) {
            return;
        }
        this.µà = new Color(color);
        this.µÕ();
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public Color áˆºÑ¢Õ() {
        return new Color(this.µà);
    }
    
    public void HorizonCode_Horizon_È(float x1, float y1, float x2, float y2) {
        float lineWidth = this.áŒŠà - 1.0f;
        if (Graphics.á.Ý()) {
            if (x1 == x2) {
                if (y1 > y2) {
                    final float temp = y2;
                    y2 = y1;
                    y1 = temp;
                }
                final float step = 1.0f / this.Å;
                lineWidth /= this.Å;
                this.Ø­áŒŠá(x1 - lineWidth / 2.0f, y1 - lineWidth / 2.0f, lineWidth + step, y2 - y1 + lineWidth + step);
                return;
            }
            if (y1 == y2) {
                if (x1 > x2) {
                    final float temp = x2;
                    x2 = x1;
                    x1 = temp;
                }
                final float step = 1.0f / this.£á;
                lineWidth /= this.£á;
                this.Ø­áŒŠá(x1 - lineWidth / 2.0f, y1 - lineWidth / 2.0f, x2 - x1 + lineWidth + step, lineWidth + step);
                return;
            }
        }
        this.µÕ();
        this.µà.HorizonCode_Horizon_È();
        TextureImpl.£à();
        Graphics.á.Â();
        Graphics.á.HorizonCode_Horizon_È(x1, y1);
        Graphics.á.HorizonCode_Horizon_È(x2, y2);
        Graphics.á.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final ShapeFill fill) {
        this.µÕ();
        TextureImpl.£à();
        ShapeRenderer.HorizonCode_Horizon_È(shape, fill);
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void Â(final Shape shape, final ShapeFill fill) {
        this.µÕ();
        TextureImpl.£à();
        ShapeRenderer.Â(shape, fill);
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Shape shape) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        ShapeRenderer.HorizonCode_Horizon_È(shape);
        this.Æ();
    }
    
    public void Â(final Shape shape) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        ShapeRenderer.Ý(shape);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final Image image) {
        this.HorizonCode_Horizon_È(shape, image, 0.01f, 0.01f, false);
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final Image image, final ShapeFill fill) {
        this.HorizonCode_Horizon_È(shape, image, 0.01f, 0.01f, fill);
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final Image image, final boolean fit) {
        if (fit) {
            this.HorizonCode_Horizon_È(shape, image, 1.0f, 1.0f, true);
        }
        else {
            this.HorizonCode_Horizon_È(shape, image, 0.01f, 0.01f, false);
        }
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final Image image, final float scaleX, final float scaleY) {
        this.HorizonCode_Horizon_È(shape, image, scaleX, scaleY, false);
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final Image image, final float scaleX, final float scaleY, final boolean fit) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        if (fit) {
            ShapeRenderer.Â(shape, image, scaleX, scaleY);
        }
        else {
            ShapeRenderer.HorizonCode_Horizon_È(shape, image, scaleX, scaleY);
        }
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Shape shape, final Image image, final float scaleX, final float scaleY, final ShapeFill fill) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        ShapeRenderer.HorizonCode_Horizon_È(shape, image, scaleX, scaleY, fill);
        this.Æ();
    }
    
    public void Â(final float x1, final float y1, final float width, final float height) {
        final float lineWidth = this.Å();
        this.HorizonCode_Horizon_È(x1, y1, x1 + width, y1);
        this.HorizonCode_Horizon_È(x1 + width, y1, x1 + width, y1 + height);
        this.HorizonCode_Horizon_È(x1 + width, y1 + height, x1, y1 + height);
        this.HorizonCode_Horizon_È(x1, y1 + height, x1, y1);
    }
    
    public void ÂµÈ() {
        this.¥Æ = null;
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(3089);
        this.Æ();
    }
    
    public void Ý(final float x, final float y, final float width, final float height) {
        this.µÕ();
        this.Šáƒ = new Rectangle(x, y, width, height);
        Graphics.HorizonCode_Horizon_È.Âµá€(12288);
        this.Ø­à.put(1.0).put(0.0).put(0.0).put(-x).flip();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(12288, this.Ø­à);
        Graphics.HorizonCode_Horizon_È.Âµá€(12289);
        this.Ø­à.put(-1.0).put(0.0).put(0.0).put(x + width).flip();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(12289, this.Ø­à);
        Graphics.HorizonCode_Horizon_È.Âµá€(12290);
        this.Ø­à.put(0.0).put(1.0).put(0.0).put(-y).flip();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(12290, this.Ø­à);
        Graphics.HorizonCode_Horizon_È.Âµá€(12291);
        this.Ø­à.put(0.0).put(-1.0).put(0.0).put(y + height).flip();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(12291, this.Ø­à);
        this.Æ();
    }
    
    public void á() {
        this.µÕ();
        this.Šáƒ = null;
        Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(12288);
        Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(12289);
        Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(12290);
        Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(12291);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Rectangle clip) {
        if (clip == null) {
            this.á();
        }
        else {
            this.Ý(clip.£á(), clip.Å(), clip.F_(), clip.G_());
        }
    }
    
    public Rectangle ˆÏ­() {
        return this.Šáƒ;
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int width, final int height) {
        this.µÕ();
        if (this.¥Æ == null) {
            Graphics.HorizonCode_Horizon_È.Âµá€(3089);
            this.¥Æ = new Rectangle(x, y, width, height);
        }
        else {
            this.¥Æ.HorizonCode_Horizon_È(x, y, width, height);
        }
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, this.ÂµÈ - y - height, width, height);
        this.Æ();
    }
    
    public void Â(final Rectangle rect) {
        if (rect == null) {
            this.ÂµÈ();
            return;
        }
        this.HorizonCode_Horizon_È((int)rect.£á(), (int)rect.Å(), (int)rect.F_(), (int)rect.G_());
    }
    
    public Rectangle £á() {
        return this.¥Æ;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height, final Image pattern, final float offX, final float offY) {
        final int cols = (int)Math.ceil(width / pattern.ŒÏ()) + 2;
        final int rows = (int)Math.ceil(height / pattern.Çªà¢()) + 2;
        final Rectangle preClip = this.ˆÏ­();
        this.Ý(x, y, width, height);
        this.µÕ();
        for (int c = 0; c < cols; ++c) {
            for (int r = 0; r < rows; ++r) {
                pattern.HorizonCode_Horizon_È(c * pattern.ŒÏ() + x - offX, r * pattern.Çªà¢() + y - offY);
            }
        }
        this.Æ();
        this.HorizonCode_Horizon_È(preClip);
    }
    
    public void Ø­áŒŠá(final float x1, final float y1, final float width, final float height) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(7);
        Graphics.HorizonCode_Horizon_È.Â(x1, y1);
        Graphics.HorizonCode_Horizon_È.Â(x1 + width, y1);
        Graphics.HorizonCode_Horizon_È.Â(x1 + width, y1 + height);
        Graphics.HorizonCode_Horizon_È.Â(x1, y1 + height);
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void Âµá€(final float x1, final float y1, final float width, final float height) {
        this.HorizonCode_Horizon_È(x1, y1, width, height, 50);
    }
    
    public void HorizonCode_Horizon_È(final float x1, final float y1, final float width, final float height, final int segments) {
        this.HorizonCode_Horizon_È(x1, y1, width, height, segments, 0.0f, 360.0f);
    }
    
    public void HorizonCode_Horizon_È(final float x1, final float y1, final float width, final float height, final float start, final float end) {
        this.HorizonCode_Horizon_È(x1, y1, width, height, 50, start, end);
    }
    
    public void HorizonCode_Horizon_È(final float x1, final float y1, final float width, final float height, final int segments, final float start, float end) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        while (end < start) {
            end += 360.0f;
        }
        final float cx = x1 + width / 2.0f;
        final float cy = y1 + height / 2.0f;
        Graphics.á.Â();
        for (int step = 360 / segments, a = (int)start; a < (int)(end + step); a += step) {
            float ang = a;
            if (ang > end) {
                ang = end;
            }
            final float x2 = (float)(cx + FastTrig.Â(Math.toRadians(ang)) * width / 2.0);
            final float y2 = (float)(cy + FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang)) * height / 2.0);
            Graphics.á.HorizonCode_Horizon_È(x2, y2);
        }
        Graphics.á.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void Ó(final float x1, final float y1, final float width, final float height) {
        this.Â(x1, y1, width, height, 50);
    }
    
    public void Â(final float x1, final float y1, final float width, final float height, final int segments) {
        this.Â(x1, y1, width, height, segments, 0.0f, 360.0f);
    }
    
    public void Â(final float x1, final float y1, final float width, final float height, final float start, final float end) {
        this.Â(x1, y1, width, height, 50, start, end);
    }
    
    public void Â(final float x1, final float y1, final float width, final float height, final int segments, final float start, float end) {
        this.µÕ();
        TextureImpl.£à();
        this.µà.HorizonCode_Horizon_È();
        while (end < start) {
            end += 360.0f;
        }
        final float cx = x1 + width / 2.0f;
        final float cy = y1 + height / 2.0f;
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(6);
        final int step = 360 / segments;
        Graphics.HorizonCode_Horizon_È.Â(cx, cy);
        for (int a = (int)start; a < (int)(end + step); a += step) {
            float ang = a;
            if (ang > end) {
                ang = end;
            }
            final float x2 = (float)(cx + FastTrig.Â(Math.toRadians(ang)) * width / 2.0);
            final float y2 = (float)(cy + FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang)) * height / 2.0);
            Graphics.HorizonCode_Horizon_È.Â(x2, y2);
        }
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        if (this.Æ) {
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(6);
            Graphics.HorizonCode_Horizon_È.Â(cx, cy);
            if (end != 360.0f) {
                end -= 10.0f;
            }
            for (int a = (int)start; a < (int)(end + step); a += step) {
                float ang = a;
                if (ang > end) {
                    ang = end;
                }
                final float x2 = (float)(cx + FastTrig.Â(Math.toRadians(ang + 10.0f)) * width / 2.0);
                final float y2 = (float)(cy + FastTrig.HorizonCode_Horizon_È(Math.toRadians(ang + 10.0f)) * height / 2.0);
                Graphics.HorizonCode_Horizon_È.Â(x2, y2);
            }
            Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        }
        this.Æ();
    }
    
    public void Ý(final float x, final float y, final float width, final float height, final int cornerRadius) {
        this.HorizonCode_Horizon_È(x, y, width, height, cornerRadius, 50);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height, int cornerRadius, final int segs) {
        if (cornerRadius < 0) {
            throw new IllegalArgumentException("corner radius must be > 0");
        }
        if (cornerRadius == 0) {
            this.Â(x, y, width, height);
            return;
        }
        final int mr = (int)Math.min(width, height) / 2;
        if (cornerRadius > mr) {
            cornerRadius = mr;
        }
        this.HorizonCode_Horizon_È(x + cornerRadius, y, x + width - cornerRadius, y);
        this.HorizonCode_Horizon_È(x, y + cornerRadius, x, y + height - cornerRadius);
        this.HorizonCode_Horizon_È(x + width, y + cornerRadius, x + width, y + height - cornerRadius);
        this.HorizonCode_Horizon_È(x + cornerRadius, y + height, x + width - cornerRadius, y + height);
        final float d = cornerRadius * 2;
        this.HorizonCode_Horizon_È(x + width - d, y + height - d, d, d, segs, 0.0f, 90.0f);
        this.HorizonCode_Horizon_È(x, y + height - d, d, d, segs, 90.0f, 180.0f);
        this.HorizonCode_Horizon_È(x + width - d, y, d, d, segs, 270.0f, 360.0f);
        this.HorizonCode_Horizon_È(x, y, d, d, segs, 180.0f, 270.0f);
    }
    
    public void Ø­áŒŠá(final float x, final float y, final float width, final float height, final int cornerRadius) {
        this.Â(x, y, width, height, cornerRadius, 50);
    }
    
    public void Â(final float x, final float y, final float width, final float height, int cornerRadius, final int segs) {
        if (cornerRadius < 0) {
            throw new IllegalArgumentException("corner radius must be > 0");
        }
        if (cornerRadius == 0) {
            this.Ø­áŒŠá(x, y, width, height);
            return;
        }
        final int mr = (int)Math.min(width, height) / 2;
        if (cornerRadius > mr) {
            cornerRadius = mr;
        }
        final float d = cornerRadius * 2;
        this.Ø­áŒŠá(x + cornerRadius, y, width - d, cornerRadius);
        this.Ø­áŒŠá(x, y + cornerRadius, cornerRadius, height - d);
        this.Ø­áŒŠá(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - d);
        this.Ø­áŒŠá(x + cornerRadius, y + height - cornerRadius, width - d, cornerRadius);
        this.Ø­áŒŠá(x + cornerRadius, y + cornerRadius, width - d, height - d);
        this.Â(x + width - d, y + height - d, d, d, segs, 0.0f, 90.0f);
        this.Â(x, y + height - d, d, d, segs, 90.0f, 180.0f);
        this.Â(x + width - d, y, d, d, segs, 270.0f, 360.0f);
        this.Â(x, y, d, d, segs, 180.0f, 270.0f);
    }
    
    public void HorizonCode_Horizon_È(final float width) {
        this.µÕ();
        this.áŒŠà = width;
        Graphics.á.HorizonCode_Horizon_È(width);
        Graphics.HorizonCode_Horizon_È.Â(width);
        this.Æ();
    }
    
    public float Å() {
        return this.áŒŠà;
    }
    
    public void £à() {
        this.µÕ();
        Renderer.Â().HorizonCode_Horizon_È(1.0f);
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1.0f);
        Graphics.HorizonCode_Horizon_È.Â(1.0f);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final boolean anti) {
        this.µÕ();
        this.Æ = anti;
        Graphics.á.HorizonCode_Horizon_È(anti);
        if (anti) {
            Graphics.HorizonCode_Horizon_È.Âµá€(2881);
        }
        else {
            Graphics.HorizonCode_Horizon_È.Ø­áŒŠá(2881);
        }
        this.Æ();
    }
    
    public boolean µà() {
        return this.Æ;
    }
    
    public void HorizonCode_Horizon_È(final String str, final float x, final float y) {
        this.µÕ();
        this.£à.HorizonCode_Horizon_È(x, y, str, this.µà);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Image image, final float x, final float y, final Color col) {
        this.µÕ();
        image.HorizonCode_Horizon_È(x, y, col);
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Animation anim, final float x, final float y) {
        this.HorizonCode_Horizon_È(anim, x, y, Color.Ý);
    }
    
    public void HorizonCode_Horizon_È(final Animation anim, final float x, final float y, final Color col) {
        this.µÕ();
        anim.HorizonCode_Horizon_È(x, y, col);
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Image image, final float x, final float y) {
        this.HorizonCode_Horizon_È(image, x, y, Color.Ý);
    }
    
    public void HorizonCode_Horizon_È(final Image image, final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        this.µÕ();
        image.HorizonCode_Horizon_È(x, y, x2, y2, srcx, srcy, srcx2, srcy2);
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Image image, final float x, final float y, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        this.HorizonCode_Horizon_È(image, x, y, x + image.ŒÏ(), y + image.Çªà¢(), srcx, srcy, srcx2, srcy2);
    }
    
    public void HorizonCode_Horizon_È(final Image target, final int x, final int y) {
        final int format = target.áŒŠÆ().£á() ? 6408 : 6407;
        target.Â();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3553, 0, format, x, this.ÂµÈ - (y + target.Çªà¢()), target.áŒŠÆ().áˆºÑ¢Õ(), target.áŒŠÆ().à(), 0);
        target.à();
    }
    
    private int HorizonCode_Horizon_È(final byte b) {
        if (b < 0) {
            return 256 + b;
        }
        return b;
    }
    
    public Color Â(final int x, final int y) {
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, this.ÂµÈ - y, 1, 1, 6408, 5121, this.µÕ);
        this.Æ();
        return new Color(this.HorizonCode_Horizon_È(this.µÕ.get(0)), this.HorizonCode_Horizon_È(this.µÕ.get(1)), this.HorizonCode_Horizon_È(this.µÕ.get(2)), this.HorizonCode_Horizon_È(this.µÕ.get(3)));
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int width, final int height, final ByteBuffer target) {
        if (target.capacity() < width * height * 4) {
            throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
        }
        this.µÕ();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(x, this.ÂµÈ - y - height, width, height, 6408, 5121, target);
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Image image, final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2, final Color col) {
        this.µÕ();
        image.HorizonCode_Horizon_È(x, y, x2, y2, srcx, srcy, srcx2, srcy2, col);
        this.µà.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final Image image, final float x, final float y, final float srcx, final float srcy, final float srcx2, final float srcy2, final Color col) {
        this.HorizonCode_Horizon_È(image, x, y, x + image.ŒÏ(), y + image.Çªà¢(), srcx, srcy, srcx2, srcy2, col);
    }
    
    public void HorizonCode_Horizon_È(final float x1, final float y1, final float red1, final float green1, final float blue1, final float alpha1, final float x2, final float y2, final float red2, final float green2, final float blue2, final float alpha2) {
        this.µÕ();
        TextureImpl.£à();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1);
        Graphics.HorizonCode_Horizon_È.Â(red1, green1, blue1, alpha1);
        Graphics.HorizonCode_Horizon_È.Â(x1, y1);
        Graphics.HorizonCode_Horizon_È.Â(red2, green2, blue2, alpha2);
        Graphics.HorizonCode_Horizon_È.Â(x2, y2);
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void HorizonCode_Horizon_È(final float x1, final float y1, final Color Color1, final float x2, final float y2, final Color Color2) {
        this.µÕ();
        TextureImpl.£à();
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1);
        Color1.HorizonCode_Horizon_È();
        Graphics.HorizonCode_Horizon_È.Â(x1, y1);
        Color2.HorizonCode_Horizon_È();
        Graphics.HorizonCode_Horizon_È.Â(x2, y2);
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Æ();
    }
    
    public void ˆà() {
        this.µÕ();
        FloatBuffer buffer;
        if (this.Ñ¢á >= this.ŠÄ.size()) {
            buffer = BufferUtils.createFloatBuffer(18);
            this.ŠÄ.add(buffer);
        }
        else {
            buffer = this.ŠÄ.get(this.Ñ¢á);
        }
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(2982, buffer);
        buffer.put(16, this.£á);
        buffer.put(17, this.Å);
        ++this.Ñ¢á;
        this.Æ();
    }
    
    public void ¥Æ() {
        if (this.Ñ¢á == 0) {
            throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
        }
        this.µÕ();
        --this.Ñ¢á;
        final FloatBuffer oldBuffer = this.ŠÄ.get(this.Ñ¢á);
        Graphics.HorizonCode_Horizon_È.HorizonCode_Horizon_È(oldBuffer);
        this.£á = oldBuffer.get(16);
        this.Å = oldBuffer.get(17);
        this.Æ();
    }
    
    public void Ø­à() {
    }
}
