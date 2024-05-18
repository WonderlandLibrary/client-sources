package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.RenderTexture;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.Pbuffer;

public class PBufferUniqueGraphics extends Graphics
{
    private Pbuffer á;
    private Image ˆÏ­;
    
    public PBufferUniqueGraphics(final Image image) throws SlickException {
        super(image.áŒŠÆ().áˆºÑ¢Õ(), image.áŒŠÆ().à());
        this.ˆÏ­ = image;
        Log.Ø­áŒŠá("Creating pbuffer(unique) " + image.ŒÏ() + "x" + image.Çªà¢());
        if ((Pbuffer.getCapabilities() & 0x1) == 0x0) {
            throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
        }
        this.Šáƒ();
    }
    
    private void Šáƒ() throws SlickException {
        try {
            final Texture tex = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ˆÏ­.ŒÏ(), this.ˆÏ­.Çªà¢(), this.ˆÏ­.Å());
            (this.á = new Pbuffer(this.áˆºÑ¢Õ, this.ÂµÈ, new PixelFormat(8, 0, 0), (RenderTexture)null, (Drawable)null)).makeCurrent();
            this.µÕ();
            this.ˆÏ­.HorizonCode_Horizon_È(0.0f, 0.0f);
            GL11.glBindTexture(3553, tex.Ø());
            GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, tex.áˆºÑ¢Õ(), tex.à(), 0);
            this.ˆÏ­.HorizonCode_Horizon_È(tex);
            Display.makeCurrent();
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È(e);
            throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
        }
    }
    
    @Override
    protected void Ø­áŒŠá() {
        GL11.glBindTexture(3553, this.ˆÏ­.áŒŠÆ().Ø());
        GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, this.ˆÏ­.áŒŠÆ().áˆºÑ¢Õ(), this.ˆÏ­.áŒŠÆ().à(), 0);
        try {
            Display.makeCurrent();
        }
        catch (LWJGLException e) {
            Log.HorizonCode_Horizon_È((Throwable)e);
        }
        SlickCallable.Â();
    }
    
    @Override
    protected void Â() {
        SlickCallable.HorizonCode_Horizon_È();
        try {
            if (this.á.isBufferLost()) {
                this.á.destroy();
                this.Šáƒ();
            }
            this.á.makeCurrent();
        }
        catch (Exception e) {
            Log.HorizonCode_Horizon_È("Failed to recreate the PBuffer");
            Log.HorizonCode_Horizon_È(e);
            throw new RuntimeException(e);
        }
        TextureImpl.µà();
        this.µÕ();
    }
    
    protected void µÕ() {
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glViewport(0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.Æ();
    }
    
    protected void Æ() {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.áˆºÑ¢Õ, 0.0, (double)this.ÂµÈ, 1.0, -1.0);
        GL11.glMatrixMode(5888);
    }
    
    @Override
    public void Ø­à() {
        super.Ø­à();
        this.á.destroy();
    }
    
    @Override
    public void Ý() {
        super.Ý();
        this.ˆÏ­.ÇŽÉ();
    }
}
