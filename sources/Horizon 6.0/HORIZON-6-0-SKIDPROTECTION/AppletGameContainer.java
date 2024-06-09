package HORIZON-6-0-SKIDPROTECTION;

import java.awt.GridLayout;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.awt.Label;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.Panel;
import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.PixelFormat;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import org.lwjgl.opengl.Display;
import java.awt.Component;
import java.awt.Canvas;
import java.applet.Applet;

public class AppletGameContainer extends Applet
{
    protected Ý HorizonCode_Horizon_È;
    protected Â Â;
    protected Canvas Ý;
    protected Thread Ø­áŒŠá;
    protected boolean Âµá€;
    
    public AppletGameContainer() {
        this.Âµá€ = true;
    }
    
    @Override
    public void destroy() {
        if (this.Ý != null) {
            this.remove(this.Ý);
        }
        super.destroy();
        Log.Ý("Clear up");
    }
    
    private void Ý() {
        this.Â.Âµá€();
        try {
            this.Ø­áŒŠá.join();
        }
        catch (InterruptedException e) {
            Log.HorizonCode_Horizon_È(e);
        }
    }
    
    @Override
    public void start() {
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Ø­áŒŠá != null) {
            return;
        }
        (this.Ø­áŒŠá = new Thread() {
            @Override
            public void run() {
                try {
                    AppletGameContainer.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    if (Display.isCreated()) {
                        Display.destroy();
                    }
                    AppletGameContainer.this.Ý.setVisible(false);
                    AppletGameContainer.this.add(new HorizonCode_Horizon_È(e));
                    AppletGameContainer.this.validate();
                }
            }
        }).start();
    }
    
    @Override
    public void stop() {
    }
    
    @Override
    public void init() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setIgnoreRepaint(true);
        try {
            final Game game = (Game)Class.forName(this.getParameter("game")).newInstance();
            this.Â = new Â(game);
            this.HorizonCode_Horizon_È = new Ý(this.Â);
            (this.Ý = new Canvas() {
                @Override
                public final void addNotify() {
                    super.addNotify();
                    AppletGameContainer.this.HorizonCode_Horizon_È();
                }
                
                @Override
                public final void removeNotify() {
                    AppletGameContainer.this.Ý();
                    super.removeNotify();
                }
            }).setSize(this.getWidth(), this.getHeight());
            this.add(this.Ý);
            this.Ý.setFocusable(true);
            this.Ý.requestFocus();
            this.Ý.setIgnoreRepaint(true);
            this.setVisible(true);
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new RuntimeException("Unable to create game container");
        }
    }
    
    public GameContainer Â() {
        return this.Â;
    }
    
    public class Ý
    {
        private Â Â;
        
        public Ý(final Â container) {
            this.Â = container;
        }
        
        private void Ý() throws Exception {
            try {
                Display.create(new PixelFormat(8, 8, GameContainer.Ê ? 8 : 0));
                AppletGameContainer.this.Âµá€ = true;
            }
            catch (Exception e) {
                AppletGameContainer.this.Âµá€ = false;
                Display.destroy();
                Display.create();
            }
        }
        
        public void HorizonCode_Horizon_È() throws Exception {
            Display.setParent(AppletGameContainer.this.Ý);
            Display.setVSyncEnabled(true);
            try {
                this.Ý();
            }
            catch (LWJGLException e) {
                e.printStackTrace();
                Thread.sleep(1000L);
                this.Ý();
            }
            this.Â();
            AppletGameContainer.this.Ý.requestFocus();
            this.Â.£á();
        }
        
        protected void Â() {
            try {
                InternalTextureLoader.HorizonCode_Horizon_È().Ý();
                SoundStore.Å().HorizonCode_Horizon_È();
                this.Â.HorizonCode_Horizon_È();
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
                this.Â.Âµá€();
            }
        }
    }
    
    public class Â extends GameContainer
    {
        public Â(final Game game) {
            super(game);
            this.ˆÏ­ = AppletGameContainer.this.getWidth();
            this.£á = AppletGameContainer.this.getHeight();
        }
        
        public void HorizonCode_Horizon_È() throws SlickException {
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
            this.Å.HorizonCode_Horizon_È(this);
            this.à¢();
        }
        
        public boolean Ø­áŒŠá() {
            return this.á;
        }
        
        public void Âµá€() {
            this.á = false;
        }
        
        @Override
        public int áˆºÑ¢Õ() {
            return 0;
        }
        
        @Override
        public int ÂµÈ() {
            return 0;
        }
        
        public boolean Ó() {
            return AppletGameContainer.this.Âµá€;
        }
        
        @Override
        public boolean áŒŠÆ() {
            return true;
        }
        
        public Applet á() {
            return AppletGameContainer.this;
        }
        
        @Override
        public void Â(final String ref) throws SlickException {
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
                final Cursor cursor = CursorLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(buffer, hotSpotX, hotSpotY, temp.ŒÏ(), temp.Çªà¢());
                Mouse.setNativeCursor(cursor);
            }
            catch (Throwable e) {
                Log.HorizonCode_Horizon_È("Failed to load and apply cursor.", e);
                throw new SlickException("Failed to set mouse cursor", e);
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final String[] refs) throws SlickException {
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
        
        @Override
        public void ˆÏ­() {
        }
        
        @Override
        public boolean Â() {
            return Display.isFullscreen();
        }
        
        @Override
        public void HorizonCode_Horizon_È(final boolean fullscreen) throws SlickException {
            if (fullscreen == this.Â()) {
                return;
            }
            try {
                if (fullscreen) {
                    final int screenWidth = Display.getDisplayMode().getWidth();
                    final int screenHeight = Display.getDisplayMode().getHeight();
                    final float gameAspectRatio = this.ˆÏ­ / this.£á;
                    final float screenAspectRatio = screenWidth / screenHeight;
                    int newWidth;
                    int newHeight;
                    if (gameAspectRatio >= screenAspectRatio) {
                        newWidth = screenWidth;
                        newHeight = this.£á / (this.ˆÏ­ / screenWidth);
                    }
                    else {
                        newWidth = this.ˆÏ­ / (this.£á / screenHeight);
                        newHeight = screenHeight;
                    }
                    final int xoffset = (screenWidth - newWidth) / 2;
                    final int yoffset = (screenHeight - newHeight) / 2;
                    GL11.glViewport(xoffset, yoffset, newWidth, newHeight);
                    this.Âµà();
                    this.á€().Â(-xoffset * this.ˆÏ­ / newWidth, -yoffset * this.£á / newHeight);
                    this.á€().HorizonCode_Horizon_È(this.ˆÏ­ / newWidth, this.£á / newHeight);
                    this.ˆÏ­ = screenWidth;
                    this.£á = screenHeight;
                    Display.setFullscreen(true);
                }
                else {
                    this.á€().Â(0.0f, 0.0f);
                    this.á€().HorizonCode_Horizon_È(1.0f, 1.0f);
                    this.ˆÏ­ = AppletGameContainer.this.getWidth();
                    this.£á = AppletGameContainer.this.getHeight();
                    GL11.glViewport(0, 0, this.ˆÏ­, this.£á);
                    this.Âµà();
                    Display.setFullscreen(false);
                }
            }
            catch (LWJGLException e) {
                Log.HorizonCode_Horizon_È((Throwable)e);
            }
        }
        
        public void £á() throws Exception {
            while (this.á) {
                final int delta = this.à¢();
                this.Âµá€(delta);
                this.C_();
                Display.update();
            }
            Display.destroy();
        }
    }
    
    public class HorizonCode_Horizon_È extends Panel
    {
        TextArea HorizonCode_Horizon_È;
        
        public HorizonCode_Horizon_È(final Exception e) {
            this.HorizonCode_Horizon_È = new TextArea();
            this.setLayout(new BorderLayout());
            this.setBackground(Color.black);
            this.setForeground(Color.white);
            final Font consoleFont = new Font("Arial", 1, 14);
            final Label slickLabel = new Label("SLICK CONSOLE", 1);
            slickLabel.setFont(consoleFont);
            this.add(slickLabel, "First");
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            this.HorizonCode_Horizon_È.setText(sw.toString());
            this.HorizonCode_Horizon_È.setEditable(false);
            this.add(this.HorizonCode_Horizon_È, "Center");
            this.add(new Panel(), "Before");
            this.add(new Panel(), "After");
            final Panel bottomPanel = new Panel();
            bottomPanel.setLayout(new GridLayout(0, 1));
            final Label infoLabel1 = new Label("An error occured while running the applet.", 1);
            final Label infoLabel2 = new Label("Plese contact support to resolve this issue.", 1);
            infoLabel1.setFont(consoleFont);
            infoLabel2.setFont(consoleFont);
            bottomPanel.add(infoLabel1);
            bottomPanel.add(infoLabel2);
            this.add(bottomPanel, "Last");
        }
    }
}
