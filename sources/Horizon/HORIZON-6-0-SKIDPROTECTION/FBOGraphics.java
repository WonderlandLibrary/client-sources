package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GLContext;

public class FBOGraphics extends Graphics
{
    private Image á;
    private int ˆÏ­;
    private boolean £á;
    
    public FBOGraphics(final Image image) throws SlickException {
        super(image.áŒŠÆ().áˆºÑ¢Õ(), image.áŒŠÆ().à());
        this.£á = true;
        this.á = image;
        Log.Ø­áŒŠá("Creating FBO " + image.ŒÏ() + "x" + image.Çªà¢());
        final boolean FBOEnabled = GLContext.getCapabilities().GL_EXT_framebuffer_object;
        if (!FBOEnabled) {
            throw new SlickException("Your OpenGL card does not support FBO and hence can't handle the dynamic images required for this application.");
        }
        this.Ï­Ðƒà();
    }
    
    private void Šáƒ() throws SlickException {
        final int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        switch (framebuffer) {
            case 36053: {}
            case 36054: {
                throw new SlickException("FrameBuffer: " + this.ˆÏ­ + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
            }
            case 36055: {
                throw new SlickException("FrameBuffer: " + this.ˆÏ­ + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
            }
            case 36057: {
                throw new SlickException("FrameBuffer: " + this.ˆÏ­ + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
            }
            case 36059: {
                throw new SlickException("FrameBuffer: " + this.ˆÏ­ + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
            }
            case 36058: {
                throw new SlickException("FrameBuffer: " + this.ˆÏ­ + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
            }
            case 36060: {
                throw new SlickException("FrameBuffer: " + this.ˆÏ­ + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
            }
            default: {
                throw new SlickException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
            }
        }
    }
    
    private void Ï­Ðƒà() throws SlickException {
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        EXTFramebufferObject.glGenFramebuffersEXT(buffer);
        this.ˆÏ­ = buffer.get();
        try {
            final Texture tex = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.á.ŒÏ(), this.á.Çªà¢(), this.á.Å());
            EXTFramebufferObject.glBindFramebufferEXT(36160, this.ˆÏ­);
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, tex.Ø(), 0);
            this.Šáƒ();
            this.ŠÄ();
            this.à();
            this.Ý();
            this.HorizonCode_Horizon_È(this.á, 0.0f, 0.0f);
            this.á.HorizonCode_Horizon_È(tex);
        }
        catch (Exception e) {
            throw new SlickException("Failed to create new texture for FBO");
        }
    }
    
    private void áŒŠà() {
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.ˆÏ­);
        GL11.glReadBuffer(36064);
    }
    
    private void ŠÄ() {
        EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
        GL11.glReadBuffer(1029);
    }
    
    @Override
    protected void Ø­áŒŠá() {
        FBOGraphics.HorizonCode_Horizon_È.Ó();
        this.ŠÄ();
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        SlickCallable.Â();
    }
    
    @Override
    protected void Â() {
        if (!this.£á) {
            throw new RuntimeException("Attempt to use a destroy()ed offscreen graphics context.");
        }
        SlickCallable.HorizonCode_Horizon_È();
        GL11.glPushAttrib(1048575);
        GL11.glPushClientAttrib(-1);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        this.áŒŠà();
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
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        buffer.put(this.ˆÏ­);
        buffer.flip();
        EXTFramebufferObject.glDeleteFramebuffersEXT(buffer);
        this.£á = false;
    }
    
    @Override
    public void Ý() {
        super.Ý();
        this.á.ÇŽÉ();
    }
}
