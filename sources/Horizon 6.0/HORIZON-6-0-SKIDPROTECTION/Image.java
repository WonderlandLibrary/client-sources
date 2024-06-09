package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.io.IOException;

public class Image implements Renderable
{
    public static final int Â = 0;
    public static final int Ý = 1;
    public static final int Ø­áŒŠá = 2;
    public static final int Âµá€ = 3;
    protected static SGL Ó;
    protected static Image à;
    public static final int Ø = 1;
    public static final int áŒŠÆ = 2;
    protected Texture áˆºÑ¢Õ;
    protected int ÂµÈ;
    protected int á;
    protected float ˆÏ­;
    protected float £á;
    protected float Å;
    protected float £à;
    protected float µà;
    protected float ˆà;
    protected String ¥Æ;
    protected boolean Ø­à;
    protected byte[] µÕ;
    protected boolean Æ;
    protected float Šáƒ;
    protected float Ï­Ðƒà;
    protected String áŒŠà;
    protected Color[] ŠÄ;
    private int HorizonCode_Horizon_È;
    private boolean Ñ¢á;
    private Color ŒÏ;
    
    static {
        Image.Ó = Renderer.HorizonCode_Horizon_È();
    }
    
    protected Image(final Image other) {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
        this.ÂµÈ = other.ŒÏ();
        this.á = other.Çªà¢();
        this.áˆºÑ¢Õ = other.áˆºÑ¢Õ;
        this.ˆÏ­ = other.ˆÏ­;
        this.£á = other.£á;
        this.¥Æ = other.¥Æ;
        this.Å = other.Å;
        this.£à = other.£à;
        this.Šáƒ = this.ÂµÈ / 2;
        this.Ï­Ðƒà = this.á / 2;
        this.Ø­à = true;
    }
    
    protected Image() {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
    }
    
    public Image(final Texture texture) {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
        this.áˆºÑ¢Õ = texture;
        this.¥Æ = texture.toString();
        this.µà();
    }
    
    public Image(final String ref) throws SlickException {
        this(ref, false);
    }
    
    public Image(final String ref, final Color trans) throws SlickException {
        this(ref, false, 1, trans);
    }
    
    public Image(final String ref, final boolean flipped) throws SlickException {
        this(ref, flipped, 1);
    }
    
    public Image(final String ref, final boolean flipped, final int filter) throws SlickException {
        this(ref, flipped, filter, null);
    }
    
    public Image(final String ref, final boolean flipped, final int f, final Color transparent) throws SlickException {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
        this.HorizonCode_Horizon_È = ((f == 1) ? 9729 : 9728);
        this.ŒÏ = transparent;
        this.Ñ¢á = flipped;
        try {
            this.¥Æ = ref;
            int[] trans = null;
            if (transparent != null) {
                trans = new int[] { (int)(transparent.£à * 255.0f), (int)(transparent.µà * 255.0f), (int)(transparent.ˆà * 255.0f) };
            }
            this.áˆºÑ¢Õ = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(ref, flipped, this.HorizonCode_Horizon_È, trans);
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load image from: " + ref, e);
        }
    }
    
    public void HorizonCode_Horizon_È(final int f) {
        this.HorizonCode_Horizon_È = ((f == 1) ? 9729 : 9728);
        this.áˆºÑ¢Õ.Ý();
        Image.Ó.Â(3553, 10241, this.HorizonCode_Horizon_È);
        Image.Ó.Â(3553, 10240, this.HorizonCode_Horizon_È);
    }
    
    public Image(final int width, final int height) throws SlickException {
        this(width, height, 2);
    }
    
    public Image(final int width, final int height, final int f) throws SlickException {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
        this.¥Æ = super.toString();
        this.HorizonCode_Horizon_È = ((f == 1) ? 9729 : 9728);
        try {
            this.áˆºÑ¢Õ = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(width, height, this.HorizonCode_Horizon_È);
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to create empty image " + width + "x" + height);
        }
        this.¥Æ();
    }
    
    public Image(final InputStream in, final String ref, final boolean flipped) throws SlickException {
        this(in, ref, flipped, 1);
    }
    
    public Image(final InputStream in, final String ref, final boolean flipped, final int filter) throws SlickException {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
        this.HorizonCode_Horizon_È(in, ref, flipped, filter, null);
    }
    
    Image(final ImageBuffer buffer) {
        this(buffer, 1);
        TextureImpl.£à();
    }
    
    Image(final ImageBuffer buffer, final int filter) {
        this((ImageData)buffer, filter);
        TextureImpl.£à();
    }
    
    public Image(final ImageData data) {
        this(data, 1);
    }
    
    public Image(final ImageData data, final int f) {
        this.ˆà = 1.0f;
        this.Ø­à = false;
        this.HorizonCode_Horizon_È = 9729;
        try {
            this.HorizonCode_Horizon_È = ((f == 1) ? 9729 : 9728);
            this.áˆºÑ¢Õ = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(data, this.HorizonCode_Horizon_È);
            this.¥Æ = this.áˆºÑ¢Õ.toString();
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
        }
    }
    
    public int Å() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String £à() {
        return this.¥Æ;
    }
    
    public void Âµá€(final float r, final float g, final float b, final float a) {
        this.HorizonCode_Horizon_È(0, r, g, b, a);
        this.HorizonCode_Horizon_È(1, r, g, b, a);
        this.HorizonCode_Horizon_È(3, r, g, b, a);
        this.HorizonCode_Horizon_È(2, r, g, b, a);
    }
    
    public void Â(final float r, final float g, final float b) {
        this.HorizonCode_Horizon_È(0, r, g, b);
        this.HorizonCode_Horizon_È(1, r, g, b);
        this.HorizonCode_Horizon_È(3, r, g, b);
        this.HorizonCode_Horizon_È(2, r, g, b);
    }
    
    public void HorizonCode_Horizon_È(final int corner, final float r, final float g, final float b, final float a) {
        if (this.ŠÄ == null) {
            this.ŠÄ = new Color[] { new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f) };
        }
        this.ŠÄ[corner].£à = r;
        this.ŠÄ[corner].µà = g;
        this.ŠÄ[corner].ˆà = b;
        this.ŠÄ[corner].¥Æ = a;
    }
    
    public void HorizonCode_Horizon_È(final int corner, final float r, final float g, final float b) {
        if (this.ŠÄ == null) {
            this.ŠÄ = new Color[] { new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f) };
        }
        this.ŠÄ[corner].£à = r;
        this.ŠÄ[corner].µà = g;
        this.ŠÄ[corner].ˆà = b;
    }
    
    public void µà() {
        if (Image.Ó.áŒŠÆ()) {
            Image.Ó.Â(3553, 10242, 34627);
            Image.Ó.Â(3553, 10243, 34627);
        }
        else {
            Image.Ó.Â(3553, 10242, 10496);
            Image.Ó.Â(3553, 10243, 10496);
        }
    }
    
    public void HorizonCode_Horizon_È(final String name) {
        this.áŒŠà = name;
    }
    
    public String ˆà() {
        return this.áŒŠà;
    }
    
    public Graphics Ø() throws SlickException {
        return GraphicsFactory.HorizonCode_Horizon_È(this);
    }
    
    private void HorizonCode_Horizon_È(final InputStream in, final String ref, final boolean flipped, final int f, final Color transparent) throws SlickException {
        this.HorizonCode_Horizon_È = ((f == 1) ? 9729 : 9728);
        try {
            this.¥Æ = ref;
            int[] trans = null;
            if (transparent != null) {
                trans = new int[] { (int)(transparent.£à * 255.0f), (int)(transparent.µà * 255.0f), (int)(transparent.ˆà * 255.0f) };
            }
            this.áˆºÑ¢Õ = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(in, ref, flipped, this.HorizonCode_Horizon_È, trans);
        }
        catch (IOException e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to load image from: " + ref, e);
        }
    }
    
    public void Â() {
        this.áˆºÑ¢Õ.Ý();
    }
    
    protected void ÂµÈ() {
        this.Ø­à = false;
        this.¥Æ();
    }
    
    protected final void ¥Æ() {
        if (this.Ø­à) {
            return;
        }
        this.Ø­à = true;
        if (this.áˆºÑ¢Õ != null) {
            this.ÂµÈ = this.áˆºÑ¢Õ.Ó();
            this.á = this.áˆºÑ¢Õ.Âµá€();
            this.Å = 0.0f;
            this.£à = 0.0f;
            this.ˆÏ­ = this.áˆºÑ¢Õ.ÂµÈ();
            this.£á = this.áˆºÑ¢Õ.Ø­áŒŠá();
        }
        this.áˆºÑ¢Õ();
        this.Šáƒ = this.ÂµÈ / 2;
        this.Ï­Ðƒà = this.á / 2;
    }
    
    protected void áˆºÑ¢Õ() {
    }
    
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f);
    }
    
    public void Ý(final float x, final float y) {
        this.HorizonCode_Horizon_È(x - this.ŒÏ() / 2, y - this.Çªà¢() / 2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.¥Æ();
        this.HorizonCode_Horizon_È(x, y, this.ÂµÈ, this.á);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final Color filter) {
        this.¥Æ();
        this.HorizonCode_Horizon_È(x, y, this.ÂµÈ, this.á, filter);
    }
    
    public void Â(final float x, final float y, final float width, final float height) {
        this.¥Æ();
        if (this.ŠÄ == null) {
            Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à);
            Image.Ó.Ý(x, y, 0.0f);
            Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à + this.£á);
            Image.Ó.Ý(x, y + height, 0.0f);
            Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à + this.£á);
            Image.Ó.Ý(x + width, y + height, 0.0f);
            Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à);
            Image.Ó.Ý(x + width, y, 0.0f);
        }
        else {
            this.ŠÄ[0].HorizonCode_Horizon_È();
            Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à);
            Image.Ó.Ý(x, y, 0.0f);
            this.ŠÄ[3].HorizonCode_Horizon_È();
            Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à + this.£á);
            Image.Ó.Ý(x, y + height, 0.0f);
            this.ŠÄ[2].HorizonCode_Horizon_È();
            Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à + this.£á);
            Image.Ó.Ý(x + width, y + height, 0.0f);
            this.ŠÄ[1].HorizonCode_Horizon_È();
            Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à);
            Image.Ó.Ý(x + width, y, 0.0f);
        }
    }
    
    public float Ø­à() {
        this.¥Æ();
        return this.Å;
    }
    
    public float µÕ() {
        this.¥Æ();
        return this.£à;
    }
    
    public float Æ() {
        this.¥Æ();
        return this.ˆÏ­;
    }
    
    public float Šáƒ() {
        this.¥Æ();
        return this.£á;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float scale) {
        this.¥Æ();
        this.HorizonCode_Horizon_È(x, y, this.ÂµÈ * scale, this.á * scale, Color.Ý);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float scale, final Color filter) {
        this.¥Æ();
        this.HorizonCode_Horizon_È(x, y, this.ÂµÈ * scale, this.á * scale, filter);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height) {
        this.¥Æ();
        this.HorizonCode_Horizon_È(x, y, width, height, Color.Ý);
    }
    
    public void Ø­áŒŠá(final float x, final float y, final float hshear, final float vshear) {
        this.Ý(x, y, hshear, vshear, Color.Ý);
    }
    
    public void Ý(final float x, final float y, final float hshear, final float vshear, Color filter) {
        if (this.ˆà != 1.0f) {
            if (filter == null) {
                filter = Color.Ý;
            }
            final Color color;
            filter = (color = new Color(filter));
            color.¥Æ *= this.ˆà;
        }
        if (filter != null) {
            filter.HorizonCode_Horizon_È();
        }
        this.áˆºÑ¢Õ.Ý();
        Image.Ó.Â(x, y, 0.0f);
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.HorizonCode_Horizon_È(7);
        this.¥Æ();
        Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à);
        Image.Ó.Ý(0.0f, 0.0f, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à + this.£á);
        Image.Ó.Ý(hshear, this.á, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à + this.£á);
        Image.Ó.Ý(this.ÂµÈ + hshear, this.á + vshear, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à);
        Image.Ó.Ý(this.ÂµÈ, vshear, 0.0f);
        Image.Ó.HorizonCode_Horizon_È();
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(-this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.Â(-x, -y, 0.0f);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height, Color filter) {
        if (this.ˆà != 1.0f) {
            if (filter == null) {
                filter = Color.Ý;
            }
            final Color color;
            filter = (color = new Color(filter));
            color.¥Æ *= this.ˆà;
        }
        if (filter != null) {
            filter.HorizonCode_Horizon_È();
        }
        this.áˆºÑ¢Õ.Ý();
        Image.Ó.Â(x, y, 0.0f);
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.HorizonCode_Horizon_È(7);
        this.Â(0.0f, 0.0f, width, height);
        Image.Ó.HorizonCode_Horizon_È();
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(-this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.Â(-x, -y, 0.0f);
    }
    
    public void Ý(final float x, final float y, final float width, final float height) {
        this.Â(x, y, width, height, Color.Ý);
    }
    
    public void Ø­áŒŠá(final float x, final float y) {
        this.Šáƒ = x;
        this.Ï­Ðƒà = y;
    }
    
    public float Ï­Ðƒà() {
        this.¥Æ();
        return this.Šáƒ;
    }
    
    public float áŒŠà() {
        this.¥Æ();
        return this.Ï­Ðƒà;
    }
    
    public void Â(final float x, final float y, final float width, final float height, final Color col) {
        this.¥Æ();
        col.HorizonCode_Horizon_È();
        this.áˆºÑ¢Õ.Ý();
        if (Image.Ó.áˆºÑ¢Õ()) {
            Image.Ó.Âµá€(33880);
            Image.Ó.HorizonCode_Horizon_È((byte)(col.£à * 255.0f), (byte)(col.µà * 255.0f), (byte)(col.ˆà * 255.0f));
        }
        Image.Ó.HorizonCode_Horizon_È(8960, 8704, 8448);
        Image.Ó.Â(x, y, 0.0f);
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.HorizonCode_Horizon_È(7);
        this.Â(0.0f, 0.0f, width, height);
        Image.Ó.HorizonCode_Horizon_È();
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(-this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.Â(-x, -y, 0.0f);
        if (Image.Ó.áˆºÑ¢Õ()) {
            Image.Ó.Ø­áŒŠá(33880);
        }
    }
    
    public void Â(final float x, final float y) {
        this.Ý(x, y, this.ŒÏ(), this.Çªà¢());
    }
    
    public void Â(final float angle) {
        this.µà = angle % 360.0f;
    }
    
    public float ŠÄ() {
        return this.µà;
    }
    
    public float Ñ¢á() {
        return this.ˆà;
    }
    
    public void Ý(final float alpha) {
        this.ˆà = alpha;
    }
    
    public void Ø­áŒŠá(final float angle) {
        this.µà += angle;
        this.µà %= 360.0f;
    }
    
    public Image HorizonCode_Horizon_È(final int x, final int y, final int width, final int height) {
        this.¥Æ();
        final float newTextureOffsetX = x / this.ÂµÈ * this.ˆÏ­ + this.Å;
        final float newTextureOffsetY = y / this.á * this.£á + this.£à;
        final float newTextureWidth = width / this.ÂµÈ * this.ˆÏ­;
        final float newTextureHeight = height / this.á * this.£á;
        final Image sub = new Image();
        sub.Ø­à = true;
        sub.áˆºÑ¢Õ = this.áˆºÑ¢Õ;
        sub.Å = newTextureOffsetX;
        sub.£à = newTextureOffsetY;
        sub.ˆÏ­ = newTextureWidth;
        sub.£á = newTextureHeight;
        sub.ÂµÈ = width;
        sub.á = height;
        sub.¥Æ = this.¥Æ;
        sub.Šáƒ = width / 2;
        sub.Ï­Ðƒà = height / 2;
        return sub;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        this.HorizonCode_Horizon_È(x, y, x + this.ÂµÈ, y + this.á, srcx, srcy, srcx2, srcy2);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        this.HorizonCode_Horizon_È(x, y, x2, y2, srcx, srcy, srcx2, srcy2, Color.Ý);
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2, Color filter) {
        this.¥Æ();
        if (this.ˆà != 1.0f) {
            if (filter == null) {
                filter = Color.Ý;
            }
            final Color color;
            filter = (color = new Color(filter));
            color.¥Æ *= this.ˆà;
        }
        filter.HorizonCode_Horizon_È();
        this.áˆºÑ¢Õ.Ý();
        Image.Ó.Â(x, y, 0.0f);
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.HorizonCode_Horizon_È(7);
        this.Â(0.0f, 0.0f, x2 - x, y2 - y, srcx, srcy, srcx2, srcy2);
        Image.Ó.HorizonCode_Horizon_È();
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(-this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.Â(-x, -y, 0.0f);
    }
    
    public void Â(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2) {
        this.Â(x, y, x2, y2, srcx, srcy, srcx2, srcy2, null);
    }
    
    public void Â(final float x, final float y, final float x2, final float y2, final float srcx, final float srcy, final float srcx2, final float srcy2, final Color filter) {
        if (filter != null) {
            filter.HorizonCode_Horizon_È();
        }
        final float mywidth = x2 - x;
        final float myheight = y2 - y;
        final float texwidth = srcx2 - srcx;
        final float texheight = srcy2 - srcy;
        final float newTextureOffsetX = srcx / this.ÂµÈ * this.ˆÏ­ + this.Å;
        final float newTextureOffsetY = srcy / this.á * this.£á + this.£à;
        final float newTextureWidth = texwidth / this.ÂµÈ * this.ˆÏ­;
        final float newTextureHeight = texheight / this.á * this.£á;
        Image.Ó.HorizonCode_Horizon_È(newTextureOffsetX, newTextureOffsetY);
        Image.Ó.Ý(x, y, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(newTextureOffsetX, newTextureOffsetY + newTextureHeight);
        Image.Ó.Ý(x, y + myheight, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(newTextureOffsetX + newTextureWidth, newTextureOffsetY + newTextureHeight);
        Image.Ó.Ý(x + mywidth, y + myheight, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(newTextureOffsetX + newTextureWidth, newTextureOffsetY);
        Image.Ó.Ý(x + mywidth, y, 0.0f);
    }
    
    public void Ý(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4) {
        Color.Ý.HorizonCode_Horizon_È();
        this.áˆºÑ¢Õ.Ý();
        Image.Ó.Â(x1, y1, 0.0f);
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.HorizonCode_Horizon_È(7);
        this.¥Æ();
        Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à);
        Image.Ó.Ý(0.0f, 0.0f, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(this.Å, this.£à + this.£á);
        Image.Ó.Ý(x2 - x1, y2 - y1, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à + this.£á);
        Image.Ó.Ý(x3 - x1, y3 - y1, 0.0f);
        Image.Ó.HorizonCode_Horizon_È(this.Å + this.ˆÏ­, this.£à);
        Image.Ó.Ý(x4 - x1, y4 - y1, 0.0f);
        Image.Ó.HorizonCode_Horizon_È();
        if (this.µà != 0.0f) {
            Image.Ó.Â(this.Šáƒ, this.Ï­Ðƒà, 0.0f);
            Image.Ó.Ý(-this.µà, 0.0f, 0.0f, 1.0f);
            Image.Ó.Â(-this.Šáƒ, -this.Ï­Ðƒà, 0.0f);
        }
        Image.Ó.Â(-x1, -y1, 0.0f);
    }
    
    public int ŒÏ() {
        this.¥Æ();
        return this.ÂµÈ;
    }
    
    public int Çªà¢() {
        this.¥Æ();
        return this.á;
    }
    
    public Image Ý() {
        this.¥Æ();
        return this.HorizonCode_Horizon_È(0, 0, this.ÂµÈ, this.á);
    }
    
    public Image HorizonCode_Horizon_È(final float scale) {
        this.¥Æ();
        return this.Ý((int)(this.ÂµÈ * scale), (int)(this.á * scale));
    }
    
    public Image Ý(final int width, final int height) {
        this.¥Æ();
        final Image image = this.Ý();
        image.ÂµÈ = width;
        image.á = height;
        image.Šáƒ = width / 2;
        image.Ï­Ðƒà = height / 2;
        return image;
    }
    
    public void à() {
        if (this.£á > 0.0f) {
            this.£à += this.£á;
            this.£á = -this.£á;
        }
    }
    
    public Image HorizonCode_Horizon_È(final boolean flipHorizontal, final boolean flipVertical) {
        this.¥Æ();
        final Image image = this.Ý();
        if (flipHorizontal) {
            image.Å = this.Å + this.ˆÏ­;
            image.ˆÏ­ = -this.ˆÏ­;
        }
        if (flipVertical) {
            image.£à = this.£à + this.£á;
            image.£á = -this.£á;
        }
        return image;
    }
    
    public void Âµá€() {
        if (Image.à != this) {
            throw new RuntimeException("The sprite sheet is not currently in use");
        }
        Image.à = null;
        Image.Ó.HorizonCode_Horizon_È();
    }
    
    public void Ó() {
        if (Image.à != null) {
            throw new RuntimeException("Attempt to start use of a sprite sheet before ending use with another - see endUse()");
        }
        (Image.à = this).¥Æ();
        Color.Ý.HorizonCode_Horizon_È();
        this.áˆºÑ¢Õ.Ý();
        Image.Ó.HorizonCode_Horizon_È(7);
    }
    
    @Override
    public String toString() {
        this.¥Æ();
        return "[Image " + this.¥Æ + " " + this.ÂµÈ + "x" + this.á + "  " + this.Å + "," + this.£à + "," + this.ˆÏ­ + "," + this.£á + "]";
    }
    
    public Texture áŒŠÆ() {
        return this.áˆºÑ¢Õ;
    }
    
    public void HorizonCode_Horizon_È(final Texture texture) {
        this.áˆºÑ¢Õ = texture;
        this.ÂµÈ();
    }
    
    private int HorizonCode_Horizon_È(final byte b) {
        if (b < 0) {
            return 256 + b;
        }
        return b;
    }
    
    public Color Â(int x, int y) {
        if (this.µÕ == null) {
            this.µÕ = this.áˆºÑ¢Õ.ˆÏ­();
        }
        final int xo = (int)(this.Å * this.áˆºÑ¢Õ.áˆºÑ¢Õ());
        final int yo = (int)(this.£à * this.áˆºÑ¢Õ.à());
        if (this.ˆÏ­ < 0.0f) {
            x = xo - x;
        }
        else {
            x += xo;
        }
        if (this.£á < 0.0f) {
            y = yo - y;
        }
        else {
            y += yo;
        }
        int offset = x + y * this.áˆºÑ¢Õ.áˆºÑ¢Õ();
        offset *= (this.áˆºÑ¢Õ.£á() ? 4 : 3);
        if (this.áˆºÑ¢Õ.£á()) {
            return new Color(this.HorizonCode_Horizon_È(this.µÕ[offset]), this.HorizonCode_Horizon_È(this.µÕ[offset + 1]), this.HorizonCode_Horizon_È(this.µÕ[offset + 2]), this.HorizonCode_Horizon_È(this.µÕ[offset + 3]));
        }
        return new Color(this.HorizonCode_Horizon_È(this.µÕ[offset]), this.HorizonCode_Horizon_È(this.µÕ[offset + 1]), this.HorizonCode_Horizon_È(this.µÕ[offset + 2]));
    }
    
    public boolean Ê() {
        return this.Æ;
    }
    
    public void £á() throws SlickException {
        if (this.Ê()) {
            return;
        }
        this.Æ = true;
        this.áˆºÑ¢Õ.á();
        GraphicsFactory.Â(this);
    }
    
    public void ÇŽÉ() {
        this.µÕ = null;
    }
}
