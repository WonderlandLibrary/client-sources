package ClassSub;

import org.lwjgl.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class Class295 extends Class225
{
    private Class220 image;
    private int FBO;
    private boolean valid;
    
    
    public Class295(final Class220 image) throws Class341 {
        super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
        this.valid = true;
        this.image = image;
        Class301.debug("Creating FBO " + image.getWidth() + "x" + image.getHeight());
        if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) {
            throw new Class341("Your OpenGL card does not support FBO and hence can't handle the dynamic images required for this application.");
        }
        this.init();
    }
    
    private void completeCheck() throws Class341 {
        final int glCheckFramebufferStatusEXT = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        switch (glCheckFramebufferStatusEXT) {
            case 36053: {}
            case 36054: {
                throw new Class341("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
            }
            case 36055: {
                throw new Class341("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
            }
            case 36057: {
                throw new Class341("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
            }
            case 36059: {
                throw new Class341("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
            }
            case 36058: {
                throw new Class341("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
            }
            case 36060: {
                throw new Class341("FrameBuffer: " + this.FBO + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
            }
            default: {
                throw new Class341("Unexpected reply from glCheckFramebufferStatusEXT: " + glCheckFramebufferStatusEXT);
            }
        }
    }
    
    private void init() throws Class341 {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        EXTFramebufferObject.glGenFramebuffersEXT(intBuffer);
        this.FBO = intBuffer.get();
        try {
            final Class282 texture = Class144.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
            EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, texture.getTextureID(), 0);
            this.completeCheck();
            this.unbind();
            this.clear();
            this.flush();
            this.drawImage(this.image, 0.0f, 0.0f);
            this.image.setTexture(texture);
        }
        catch (Exception ex) {
            throw new Class341("Failed to create new texture for FBO");
        }
    }
    
    private void bind() {
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.FBO);
        GL11.glReadBuffer(36064);
    }
    
    private void unbind() {
        EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
        GL11.glReadBuffer(1029);
    }
    
    @Override
    protected void disable() {
        Class295.GL.flush();
        this.unbind();
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        Class126.leaveSafeBlock();
    }
    
    @Override
    protected void enable() {
        if (!this.valid) {
            throw new RuntimeException("Attempt to use a destroy()ed offscreen graphics context.");
        }
        Class126.enterSafeBlock();
        GL11.glPushAttrib(1048575);
        GL11.glPushClientAttrib(-1);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        this.bind();
        this.initGL();
    }
    
    protected void initGL() {
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.enterOrtho();
    }
    
    protected void enterOrtho() {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.screenWidth, 0.0, (double)this.screenHeight, 1.0, -1.0);
        GL11.glMatrixMode(5888);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        intBuffer.put(this.FBO);
        intBuffer.flip();
        EXTFramebufferObject.glDeleteFramebuffersEXT(intBuffer);
        this.valid = false;
    }
    
    @Override
    public void flush() {
        super.flush();
        this.image.flushPixelData();
    }
}
