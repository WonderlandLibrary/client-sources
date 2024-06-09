package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet extends Image
{
    private int HorizonCode_Horizon_È;
    private int Ñ¢á;
    private int ŒÏ;
    private Image[][] Çªà¢;
    private int Ê;
    private Image ÇŽÉ;
    
    public SpriteSheet(final URL ref, final int tw, final int th) throws SlickException, IOException {
        this(new Image(ref.openStream(), ref.toString(), false), tw, th);
    }
    
    public SpriteSheet(final Image image, final int tw, final int th) {
        super(image);
        this.ŒÏ = 0;
        this.ÇŽÉ = image;
        this.HorizonCode_Horizon_È = tw;
        this.Ñ¢á = th;
        this.áˆºÑ¢Õ();
    }
    
    public SpriteSheet(final Image image, final int tw, final int th, final int spacing, final int margin) {
        super(image);
        this.ŒÏ = 0;
        this.ÇŽÉ = image;
        this.HorizonCode_Horizon_È = tw;
        this.Ñ¢á = th;
        this.Ê = spacing;
        this.ŒÏ = margin;
        this.áˆºÑ¢Õ();
    }
    
    public SpriteSheet(final Image image, final int tw, final int th, final int spacing) {
        this(image, tw, th, spacing, 0);
    }
    
    public SpriteSheet(final String ref, final int tw, final int th, final int spacing) throws SlickException {
        this(ref, tw, th, null, spacing);
    }
    
    public SpriteSheet(final String ref, final int tw, final int th) throws SlickException {
        this(ref, tw, th, null);
    }
    
    public SpriteSheet(final String ref, final int tw, final int th, final Color col) throws SlickException {
        this(ref, tw, th, col, 0);
    }
    
    public SpriteSheet(final String ref, final int tw, final int th, final Color col, final int spacing) throws SlickException {
        super(ref, false, 2, col);
        this.ŒÏ = 0;
        this.ÇŽÉ = this;
        this.HorizonCode_Horizon_È = tw;
        this.Ñ¢á = th;
        this.Ê = spacing;
    }
    
    public SpriteSheet(final String name, final InputStream ref, final int tw, final int th) throws SlickException {
        super(ref, name, false);
        this.ŒÏ = 0;
        this.ÇŽÉ = this;
        this.HorizonCode_Horizon_È = tw;
        this.Ñ¢á = th;
    }
    
    @Override
    protected void áˆºÑ¢Õ() {
        if (this.Çªà¢ != null) {
            return;
        }
        final int tilesAcross = (this.ŒÏ() - this.ŒÏ * 2 - this.HorizonCode_Horizon_È) / (this.HorizonCode_Horizon_È + this.Ê) + 1;
        int tilesDown = (this.Çªà¢() - this.ŒÏ * 2 - this.Ñ¢á) / (this.Ñ¢á + this.Ê) + 1;
        if ((this.Çªà¢() - this.Ñ¢á) % (this.Ñ¢á + this.Ê) != 0) {
            ++tilesDown;
        }
        this.Çªà¢ = new Image[tilesAcross][tilesDown];
        for (int x = 0; x < tilesAcross; ++x) {
            for (int y = 0; y < tilesDown; ++y) {
                this.Çªà¢[x][y] = this.Ø­áŒŠá(x, y);
            }
        }
    }
    
    public Image HorizonCode_Horizon_È(final int x, final int y) {
        this.¥Æ();
        if (x < 0 || x >= this.Çªà¢.length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
        }
        if (y < 0 || y >= this.Çªà¢[0].length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
        }
        return this.Çªà¢[x][y];
    }
    
    public Image Ø­áŒŠá(final int x, final int y) {
        this.ÇŽÉ.¥Æ();
        this.áˆºÑ¢Õ();
        if (x < 0 || x >= this.Çªà¢.length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
        }
        if (y < 0 || y >= this.Çªà¢[0].length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
        }
        return this.ÇŽÉ.HorizonCode_Horizon_È(x * (this.HorizonCode_Horizon_È + this.Ê) + this.ŒÏ, y * (this.Ñ¢á + this.Ê) + this.ŒÏ, this.HorizonCode_Horizon_È, this.Ñ¢á);
    }
    
    public int HorizonCode_Horizon_È() {
        this.ÇŽÉ.¥Æ();
        this.áˆºÑ¢Õ();
        return this.Çªà¢.length;
    }
    
    public int á() {
        this.ÇŽÉ.¥Æ();
        this.áˆºÑ¢Õ();
        return this.Çªà¢[0].length;
    }
    
    public void Â(final int x, final int y, final int sx, final int sy) {
        this.Çªà¢[sx][sy].Â(x, y, this.HorizonCode_Horizon_È, this.Ñ¢á);
    }
    
    @Override
    public void Âµá€() {
        if (this.ÇŽÉ == this) {
            super.Âµá€();
            return;
        }
        this.ÇŽÉ.Âµá€();
    }
    
    @Override
    public void Ó() {
        if (this.ÇŽÉ == this) {
            super.Ó();
            return;
        }
        this.ÇŽÉ.Ó();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Texture texture) {
        if (this.ÇŽÉ == this) {
            super.HorizonCode_Horizon_È(texture);
            return;
        }
        this.ÇŽÉ.HorizonCode_Horizon_È(texture);
    }
}
