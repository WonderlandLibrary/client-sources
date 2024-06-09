package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.Display;
import java.io.IOException;
import org.lwjgl.input.Cursor;
import org.lwjgl.Sys;
import java.util.Properties;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.Drawable;

public abstract class GameContainer implements GUIContext
{
    protected static SGL Ó;
    protected static Drawable à;
    protected long Ø;
    protected long áŒŠÆ;
    protected int áˆºÑ¢Õ;
    protected int ÂµÈ;
    protected boolean á;
    protected int ˆÏ­;
    protected int £á;
    protected Game Å;
    private Font HorizonCode_Horizon_È;
    private Graphics Â;
    protected Input £à;
    protected int µà;
    private boolean Ý;
    protected long ˆà;
    protected long ¥Æ;
    protected long Ø­à;
    protected Game µÕ;
    protected boolean Æ;
    protected boolean Šáƒ;
    protected boolean Ï­Ðƒà;
    protected boolean áŒŠà;
    protected boolean ŠÄ;
    protected int Ñ¢á;
    protected boolean ŒÏ;
    protected boolean Çªà¢;
    protected static boolean Ê;
    
    static {
        GameContainer.Ó = Renderer.HorizonCode_Horizon_È();
    }
    
    protected GameContainer(final Game game) {
        this.á = true;
        this.µà = -1;
        this.Ý = true;
        this.ˆà = 1L;
        this.Ø­à = 0L;
        this.Æ = true;
        this.Ï­Ðƒà = true;
        this.Å = game;
        this.Ø = this.áƒ();
        Çªà¢();
        Log.HorizonCode_Horizon_È();
    }
    
    public static void ¥Æ() {
        GameContainer.Ê = true;
    }
    
    public void HorizonCode_Horizon_È(final Font font) {
        if (font != null) {
            this.HorizonCode_Horizon_È = font;
        }
        else {
            Log.Â("Please provide a non null font");
        }
    }
    
    public void HorizonCode_Horizon_È(final int samples) {
        this.Ñ¢á = samples;
    }
    
    public boolean Ø­à() {
        return this.ŒÏ;
    }
    
    public int µÕ() {
        return this.Ñ¢á;
    }
    
    public void Ø­áŒŠá(final boolean forceExit) {
        this.Ï­Ðƒà = forceExit;
    }
    
    public void Âµá€(final boolean smoothDeltas) {
        this.ŠÄ = smoothDeltas;
    }
    
    public boolean Â() {
        return false;
    }
    
    public float Æ() {
        return this.µà() / this.£à();
    }
    
    public void HorizonCode_Horizon_È(final boolean fullscreen) throws SlickException {
    }
    
    public static void Šáƒ() throws SlickException {
        try {
            GameContainer.à = (Drawable)new Pbuffer(64, 64, new PixelFormat(8, 0, 0), (Drawable)null);
        }
        catch (LWJGLException e) {
            throw new SlickException("Unable to create the pbuffer used for shard context, buffers not supported", (Throwable)e);
        }
    }
    
    public static Drawable Ï­Ðƒà() {
        return GameContainer.à;
    }
    
    public void Ó(final boolean clear) {
        this.Æ = clear;
    }
    
    public void Ý() throws SlickException {
    }
    
    public void áŒŠà() {
        this.à(true);
    }
    
    public void ŠÄ() {
        this.à(false);
    }
    
    public boolean Ñ¢á() {
        return this.Šáƒ;
    }
    
    public void à(final boolean paused) {
        this.Šáƒ = paused;
    }
    
    public boolean ŒÏ() {
        return this.Çªà¢;
    }
    
    public void Ø(final boolean alwaysRender) {
        this.Çªà¢ = alwaysRender;
    }
    
    public static int Çªà¢() {
        try {
            final Properties props = new Properties();
            props.load(ResourceLoader.HorizonCode_Horizon_È("version"));
            final int build = Integer.parseInt(props.getProperty("build"));
            Log.Ý("Slick Build #" + build);
            return build;
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È("Unable to determine Slick build number");
            return -1;
        }
    }
    
    @Override
    public Font Ê() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean ÇŽÉ() {
        return SoundStore.Å().áˆºÑ¢Õ();
    }
    
    public boolean ˆá() {
        return SoundStore.Å().à();
    }
    
    public void áŒŠÆ(final boolean on) {
        SoundStore.Å().Â(on);
    }
    
    public void áˆºÑ¢Õ(final boolean on) {
        SoundStore.Å().Ý(on);
    }
    
    public float ÇŽÕ() {
        return SoundStore.Å().áŒŠÆ();
    }
    
    public float É() {
        return SoundStore.Å().Ø();
    }
    
    public void HorizonCode_Horizon_È(final float volume) {
        SoundStore.Å().Ý(volume);
    }
    
    public void Â(final float volume) {
        SoundStore.Å().HorizonCode_Horizon_È(volume);
    }
    
    @Override
    public abstract int ÂµÈ();
    
    @Override
    public abstract int áˆºÑ¢Õ();
    
    @Override
    public int µà() {
        return this.ˆÏ­;
    }
    
    @Override
    public int £à() {
        return this.£á;
    }
    
    public abstract void Â(final String p0) throws SlickException;
    
    public abstract void HorizonCode_Horizon_È(final String[] p0) throws SlickException;
    
    @Override
    public long áƒ() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public void Â(final int milliseconds) {
        final long target = this.áƒ() + milliseconds;
        while (this.áƒ() < target) {
            try {
                Thread.sleep(1L);
            }
            catch (Exception ex) {}
        }
    }
    
    @Override
    public abstract void HorizonCode_Horizon_È(final String p0, final int p1, final int p2) throws SlickException;
    
    @Override
    public abstract void HorizonCode_Horizon_È(final ImageData p0, final int p1, final int p2) throws SlickException;
    
    public abstract void HorizonCode_Horizon_È(final Image p0, final int p1, final int p2) throws SlickException;
    
    @Override
    public abstract void HorizonCode_Horizon_È(final Cursor p0, final int p1, final int p2) throws SlickException;
    
    public void HorizonCode_Horizon_È(final String ref, final int x, final int y, final int width, final int height, final int[] cursorDelays) throws SlickException {
        try {
            final Cursor cursor = CursorLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(ref, x, y, width, height, cursorDelays);
            this.HorizonCode_Horizon_È(cursor, x, y);
        }
        catch (IOException e) {
            throw new SlickException("Failed to set mouse cursor", e);
        }
        catch (LWJGLException e2) {
            throw new SlickException("Failed to set mouse cursor", (Throwable)e2);
        }
    }
    
    @Override
    public abstract void ˆÏ­();
    
    @Override
    public Input á€() {
        return this.£à;
    }
    
    public int Õ() {
        return this.áˆºÑ¢Õ;
    }
    
    public abstract void Ý(final boolean p0);
    
    public abstract boolean Ø();
    
    protected int à¢() {
        final long time = this.áƒ();
        final int delta = (int)(time - this.Ø);
        this.Ø = time;
        return delta;
    }
    
    protected void C_() {
        if (this.áƒ() - this.áŒŠÆ > 1000L) {
            this.áŒŠÆ = this.áƒ();
            this.áˆºÑ¢Õ = this.ÂµÈ;
            this.ÂµÈ = 0;
        }
        ++this.ÂµÈ;
    }
    
    public void Ý(final int interval) {
        this.ˆà = interval;
    }
    
    public void Ø­áŒŠá(final int interval) {
        this.Ø­à = interval;
    }
    
    protected void Âµá€(int delta) throws SlickException {
        if (this.ŠÄ && this.Õ() != 0) {
            delta = 1000 / this.Õ();
        }
        this.£à.Âµá€(this.ˆÏ­, this.£á);
        Music.HorizonCode_Horizon_È(delta);
        Label_0235: {
            if (!this.Šáƒ) {
                this.¥Æ += delta;
                if (this.¥Æ < this.ˆà) {
                    break Label_0235;
                }
                try {
                    if (this.Ø­à == 0L) {
                        this.Å.HorizonCode_Horizon_È(this, (int)this.¥Æ);
                        this.¥Æ = 0L;
                        break Label_0235;
                    }
                    final long cycles = this.¥Æ / this.Ø­à;
                    for (int i = 0; i < cycles; ++i) {
                        this.Å.HorizonCode_Horizon_È(this, (int)this.Ø­à);
                    }
                    final int remainder = (int)(this.¥Æ % this.Ø­à);
                    if (remainder > this.ˆà) {
                        this.Å.HorizonCode_Horizon_È(this, (int)(remainder % this.Ø­à));
                        this.¥Æ = 0L;
                        break Label_0235;
                    }
                    this.¥Æ = remainder;
                    break Label_0235;
                }
                catch (Throwable e) {
                    Log.HorizonCode_Horizon_È(e);
                    throw new SlickException("Game.update() failure - check the game code.");
                }
            }
            this.Å.HorizonCode_Horizon_È(this, 0);
        }
        if (this.áŒŠÆ() || this.ŒÏ()) {
            if (this.Æ) {
                GameContainer.Ó.Ý(16640);
            }
            GameContainer.Ó.Ý();
            this.Â.Ø();
            this.Â.áŒŠÆ();
            this.Â.£à();
            this.Â.HorizonCode_Horizon_È(false);
            try {
                this.Å.HorizonCode_Horizon_È(this, this.Â);
            }
            catch (Throwable e) {
                Log.HorizonCode_Horizon_È(e);
                throw new SlickException("Game.render() failure - check the game code.");
            }
            this.Â.Ø();
            if (this.Ý) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(10.0f, 10.0f, "FPS: " + this.áˆºÑ¢Õ);
            }
            GameContainer.Ó.Ó();
        }
        if (this.µà != -1) {
            Display.sync(this.µà);
        }
    }
    
    public void Â(final boolean updateOnlyWhenVisible) {
    }
    
    public boolean à() {
        return true;
    }
    
    protected void ŠÂµà() {
        Log.Ý("Starting display " + this.ˆÏ­ + "x" + this.£á);
        GameContainer.Ó.HorizonCode_Horizon_È(this.ˆÏ­, this.£á);
        if (this.£à == null) {
            this.£à = new Input(this.£á);
        }
        this.£à.Ý(this.£á);
        if (this.Å instanceof InputListener) {
            this.£à.Ý((InputListener)this.Å);
            this.£à.HorizonCode_Horizon_È((InputListener)this.Å);
        }
        if (this.Â != null) {
            this.Â.HorizonCode_Horizon_È(this.µà(), this.£à());
        }
        this.µÕ = this.Å;
    }
    
    protected void ¥à() throws SlickException {
        this.ŠÂµà();
        this.Â(1.0f);
        this.HorizonCode_Horizon_È(1.0f);
        this.Â = new Graphics(this.ˆÏ­, this.£á);
        this.HorizonCode_Horizon_È = this.Â.Âµá€();
    }
    
    protected void Âµà() {
        this.HorizonCode_Horizon_È(this.ˆÏ­, this.£á);
    }
    
    public void ÂµÈ(final boolean show) {
        this.Ý = show;
    }
    
    public boolean Ç() {
        return this.Ý;
    }
    
    public void Ó(final int fps) {
        this.µà = fps;
    }
    
    public void á(final boolean vsync) {
        Display.setVSyncEnabled(this.áŒŠà = vsync);
    }
    
    public boolean È() {
        return this.áŒŠà;
    }
    
    protected boolean Å() {
        return this.á;
    }
    
    public void ˆÏ­(final boolean verbose) {
        Log.HorizonCode_Horizon_È(verbose);
    }
    
    public void áŠ() {
        this.á = false;
    }
    
    public abstract boolean áŒŠÆ();
    
    public Graphics ˆáŠ() {
        return this.Â;
    }
    
    protected void HorizonCode_Horizon_È(final int xsize, final int ysize) {
        GameContainer.Ó.Â(xsize, ysize);
    }
}
