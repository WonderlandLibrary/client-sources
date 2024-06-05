package ClassSub;

import java.util.*;
import org.lwjgl.opengl.*;

public class Class34
{
    private static HashMap graphics;
    private static boolean pbuffer;
    private static boolean pbufferRT;
    private static boolean fbo;
    private static boolean init;
    
    
    private static void init() throws Class341 {
        Class34.init = true;
        if (Class34.fbo) {
            Class34.fbo = GLContext.getCapabilities().GL_EXT_framebuffer_object;
        }
        Class34.pbuffer = ((Pbuffer.getCapabilities() & 0x1) != 0x0);
        Class34.pbufferRT = ((Pbuffer.getCapabilities() & 0x2) != 0x0);
        if (!Class34.fbo && !Class34.pbuffer && !Class34.pbufferRT) {
            throw new Class341("Your OpenGL card does not support offscreen buffers and hence can't handle the dynamic images required for this application.");
        }
        Class301.info("Offscreen Buffers FBO=" + Class34.fbo + " PBUFFER=" + Class34.pbuffer + " PBUFFERRT=" + Class34.pbufferRT);
    }
    
    public static void setUseFBO(final boolean fbo) {
        Class34.fbo = fbo;
    }
    
    public static boolean usingFBO() {
        return Class34.fbo;
    }
    
    public static boolean usingPBuffer() {
        return !Class34.fbo && Class34.pbuffer;
    }
    
    public static Class225 getGraphicsForImage(final Class220 class220) throws Class341 {
        Class225 graphics = Class34.graphics.get(class220.getTexture());
        if (graphics == null) {
            graphics = createGraphics(class220);
            Class34.graphics.put(class220.getTexture(), graphics);
        }
        return graphics;
    }
    
    public static void releaseGraphicsForImage(final Class220 class220) throws Class341 {
        final Class225 class221 = Class34.graphics.remove(class220.getTexture());
        if (class221 != null) {
            class221.destroy();
        }
    }
    
    private static Class225 createGraphics(final Class220 class220) throws Class341 {
        init();
        if (Class34.fbo) {
            try {
                return new Class295(class220);
            }
            catch (Exception ex) {
                Class34.fbo = false;
                Class301.warn("FBO failed in use, falling back to PBuffer");
            }
        }
        if (!Class34.pbuffer) {
            throw new Class341("Failed to create offscreen buffer even though the card reports it's possible");
        }
        if (Class34.pbufferRT) {
            return new Class268(class220);
        }
        return new Class317(class220);
    }
    
    static {
        Class34.graphics = new HashMap();
        Class34.pbuffer = true;
        Class34.pbufferRT = true;
        Class34.fbo = true;
        Class34.init = false;
    }
}
