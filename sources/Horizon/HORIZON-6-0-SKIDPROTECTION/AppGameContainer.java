package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.OutputStream;
import org.lwjgl.openal.AL;
import org.lwjgl.Sys;
import org.lwjgl.opengl.PixelFormat;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import java.security.AccessController;
import org.lwjgl.opengl.Display;
import java.security.PrivilegedAction;
import org.lwjgl.opengl.DisplayMode;

public class AppGameContainer extends GameContainer
{
    protected DisplayMode HorizonCode_Horizon_È;
    protected DisplayMode Â;
    protected boolean Ý;
    protected boolean Ø­áŒŠá;
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    Display.getDisplayMode();
                }
                catch (Exception e) {
                    Log.HorizonCode_Horizon_È(e);
                }
                return null;
            }
        });
    }
    
    public AppGameContainer(final Game game) throws SlickException {
        this(game, 640, 480, false);
    }
    
    public AppGameContainer(final Game game, final int width, final int height, final boolean fullscreen) throws SlickException {
        super(game);
        this.Ý = true;
        this.Ø­áŒŠá = false;
        this.HorizonCode_Horizon_È = Display.getDisplayMode();
        this.HorizonCode_Horizon_È(width, height, fullscreen);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final String title) {
        Display.setTitle(title);
    }
    
    public void HorizonCode_Horizon_È(final int width, final int height, final boolean fullscreen) throws SlickException {
        if (this.ˆÏ­ == width && this.£á == height && this.Â() == fullscreen) {
            return;
        }
        try {
            this.Â = null;
            if (fullscreen) {
                final DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;
                for (int i = 0; i < modes.length; ++i) {
                    final DisplayMode current = modes[i];
                    if (current.getWidth() == width && current.getHeight() == height) {
                        if ((this.Â == null || current.getFrequency() >= freq) && (this.Â == null || current.getBitsPerPixel() > this.Â.getBitsPerPixel())) {
                            this.Â = current;
                            freq = this.Â.getFrequency();
                        }
                        if (current.getBitsPerPixel() == this.HorizonCode_Horizon_È.getBitsPerPixel() && current.getFrequency() == this.HorizonCode_Horizon_È.getFrequency()) {
                            this.Â = current;
                            break;
                        }
                    }
                }
            }
            else {
                this.Â = new DisplayMode(width, height);
            }
            if (this.Â == null) {
                throw new SlickException("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
            }
            this.ˆÏ­ = width;
            this.£á = height;
            Display.setDisplayMode(this.Â);
            Display.setFullscreen(fullscreen);
            if (Display.isCreated()) {
                this.ŠÂµà();
                this.Âµà();
            }
            if (this.Â.getBitsPerPixel() == 16) {
                InternalTextureLoader.HorizonCode_Horizon_È().Ø­áŒŠá();
            }
        }
        catch (LWJGLException e) {
            throw new SlickException("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen, (Throwable)e);
        }
        this.à¢();
    }
    
    @Override
    public boolean Â() {
        return Display.isFullscreen();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean fullscreen) throws SlickException {
        if (this.Â() == fullscreen) {
            return;
        }
        Label_0059: {
            if (!fullscreen) {
                try {
                    Display.setFullscreen(fullscreen);
                    break Label_0059;
                }
                catch (LWJGLException e) {
                    throw new SlickException("Unable to set fullscreen=" + fullscreen, (Throwable)e);
                }
            }
            this.HorizonCode_Horizon_È(this.ˆÏ­, this.£á, fullscreen);
        }
        this.à¢();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String ref, final int hotSpotX, final int hotSpotY) throws SlickException {
        try {
            final Cursor cursor = CursorLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(ref, hotSpotX, hotSpotY);
            Mouse.setNativeCursor(cursor);
        }
        catch (Throwable e) {
            Log.HorizonCode_Horizon_È("Failed to load and apply cursor.", e);
            throw new SlickException("Failed to set mouse cursor", e);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ImageData data, final int hotSpotX, final int hotSpotY) throws SlickException {
        try {
            final Cursor cursor = CursorLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(data, hotSpotX, hotSpotY);
            Mouse.setNativeCursor(cursor);
        }
        catch (Throwable e) {
            Log.HorizonCode_Horizon_È("Failed to load and apply cursor.", e);
            throw new SlickException("Failed to set mouse cursor", e);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Cursor cursor, final int hotSpotX, final int hotSpotY) throws SlickException {
        try {
            Mouse.setNativeCursor(cursor);
        }
        catch (Throwable e) {
            Log.HorizonCode_Horizon_È("Failed to load and apply cursor.", e);
            throw new SlickException("Failed to set mouse cursor", e);
        }
    }
    
    private int à(final int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {}
        return ret;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Image image, final int hotSpotX, final int hotSpotY) throws SlickException {
        try {
            final Image temp = new Image(this.à(image.ŒÏ()), this.à(image.Çªà¢()));
            final Graphics g = temp.Ø();
            final ByteBuffer buffer = BufferUtils.createByteBuffer(temp.ŒÏ() * temp.Çªà¢() * 4);
            g.HorizonCode_Horizon_È(image.HorizonCode_Horizon_È(false, true), 0.0f, 0.0f);
            g.Ý();
            g.HorizonCode_Horizon_È(0, 0, temp.ŒÏ(), temp.Çªà¢(), buffer);
            final Cursor cursor = CursorLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(buffer, hotSpotX, hotSpotY, temp.ŒÏ(), image.Çªà¢());
            Mouse.setNativeCursor(cursor);
        }
        catch (Throwable e) {
            Log.HorizonCode_Horizon_È("Failed to load and apply cursor.", e);
            throw new SlickException("Failed to set mouse cursor", e);
        }
    }
    
    @Override
    public void Ý() throws SlickException {
        InternalTextureLoader.HorizonCode_Horizon_È().Ý();
        SoundStore.Å().HorizonCode_Horizon_È();
        this.¥à();
        this.Âµà();
        try {
            this.Å.HorizonCode_Horizon_È(this);
        }
        catch (SlickException e) {
            Log.HorizonCode_Horizon_È(e);
            this.á = false;
        }
    }
    
    private void HorizonCode_Horizon_È(final PixelFormat format) throws LWJGLException {
        if (AppGameContainer.à == null) {
            Display.create(format);
        }
        else {
            Display.create(format, AppGameContainer.à);
        }
    }
    
    public void Ø­áŒŠá() throws SlickException {
        try {
            this.Âµá€();
            this.à¢();
            while (this.Å()) {
                this.Ó();
            }
        }
        finally {
            this.á();
        }
        this.á();
        if (this.Ï­Ðƒà) {
            System.exit(0);
        }
    }
    
    protected void Âµá€() throws SlickException {
        if (this.Â == null) {
            this.HorizonCode_Horizon_È(640, 480, false);
        }
        Display.setTitle(this.Å.Â());
        Log.Ý("LWJGL Version: " + Sys.getVersion());
        Log.Ý("OriginalDisplayMode: " + this.HorizonCode_Horizon_È);
        Log.Ý("TargetDisplayMode: " + this.Â);
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    final PixelFormat format = new PixelFormat(8, 8, AppGameContainer.Ê ? 8 : 0, AppGameContainer.this.Ñ¢á);
                    AppGameContainer.this.HorizonCode_Horizon_È(format);
                    AppGameContainer.this.ŒÏ = true;
                }
                catch (Exception e4) {
                    Display.destroy();
                    try {
                        final PixelFormat format2 = new PixelFormat(8, 8, AppGameContainer.Ê ? 8 : 0);
                        AppGameContainer.this.HorizonCode_Horizon_È(format2);
                        AppGameContainer.this.Ø­áŒŠá = false;
                    }
                    catch (Exception e5) {
                        Display.destroy();
                        try {
                            AppGameContainer.this.HorizonCode_Horizon_È(new PixelFormat());
                        }
                        catch (Exception e3) {
                            Log.HorizonCode_Horizon_È(e3);
                        }
                    }
                }
                return null;
            }
        });
        if (!Display.isCreated()) {
            throw new SlickException("Failed to initialise the LWJGL display");
        }
        this.¥à();
        this.Âµà();
        try {
            this.á€().Å();
        }
        catch (SlickException e) {
            Log.Ý("Controllers not available");
        }
        catch (Throwable e2) {
            Log.Ý("Controllers not available");
        }
        try {
            this.Å.HorizonCode_Horizon_È(this);
        }
        catch (SlickException e) {
            Log.HorizonCode_Horizon_È(e);
            this.á = false;
        }
    }
    
    protected void Ó() throws SlickException {
        final int delta = this.à¢();
        if (!Display.isVisible() && this.Ý) {
            try {
                Thread.sleep(100L);
            }
            catch (Exception ex) {}
        }
        else {
            try {
                this.Âµá€(delta);
            }
            catch (SlickException e) {
                Log.HorizonCode_Horizon_È(e);
                this.á = false;
                return;
            }
        }
        this.C_();
        Display.update();
        if (Display.isCloseRequested() && this.Å.HorizonCode_Horizon_È()) {
            this.á = false;
        }
    }
    
    @Override
    public void Â(final boolean updateOnlyWhenVisible) {
        this.Ý = updateOnlyWhenVisible;
    }
    
    @Override
    public boolean à() {
        return this.Ý;
    }
    
    @Override
    public void Â(final String ref) throws SlickException {
        this.HorizonCode_Horizon_È(new String[] { ref });
    }
    
    @Override
    public void Ý(final boolean grabbed) {
        Mouse.setGrabbed(grabbed);
    }
    
    @Override
    public boolean Ø() {
        return Mouse.isGrabbed();
    }
    
    @Override
    public boolean áŒŠÆ() {
        return Display.isActive();
    }
    
    @Override
    public int áˆºÑ¢Õ() {
        return this.HorizonCode_Horizon_È.getHeight();
    }
    
    @Override
    public int ÂµÈ() {
        return this.HorizonCode_Horizon_È.getWidth();
    }
    
    public void á() {
        Display.destroy();
        AL.destroy();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String[] refs) throws SlickException {
        final ByteBuffer[] bufs = new ByteBuffer[refs.length];
        for (int i = 0; i < refs.length; ++i) {
            boolean flip = true;
            LoadableImageData data;
            if (refs[i].endsWith(".tga")) {
                data = new TGAImageData();
            }
            else {
                flip = false;
                data = new ImageIOImageData();
            }
            try {
                bufs[i] = data.HorizonCode_Horizon_È(ResourceLoader.HorizonCode_Horizon_È(refs[i]), flip, false, null);
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
                throw new SlickException("Failed to set the icon");
            }
        }
        Display.setIcon(bufs);
    }
    
    @Override
    public void ˆÏ­() {
        try {
            Mouse.setNativeCursor((Cursor)null);
        }
        catch (LWJGLException e) {
            Log.HorizonCode_Horizon_È("Failed to reset mouse cursor", (Throwable)e);
        }
    }
    
    private class HorizonCode_Horizon_È extends OutputStream
    {
        @Override
        public void write(final int b) throws IOException {
        }
    }
}
