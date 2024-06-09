package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.GLContext;
import java.util.HashMap;

public class GraphicsFactory
{
    private static HashMap HorizonCode_Horizon_È;
    private static boolean Â;
    private static boolean Ý;
    private static boolean Ø­áŒŠá;
    private static boolean Âµá€;
    
    static {
        GraphicsFactory.HorizonCode_Horizon_È = new HashMap();
        GraphicsFactory.Â = true;
        GraphicsFactory.Ý = true;
        GraphicsFactory.Ø­áŒŠá = true;
        GraphicsFactory.Âµá€ = false;
    }
    
    private static void Ý() throws SlickException {
        GraphicsFactory.Âµá€ = true;
        if (GraphicsFactory.Ø­áŒŠá) {
            GraphicsFactory.Ø­áŒŠá = GLContext.getCapabilities().GL_EXT_framebuffer_object;
        }
        GraphicsFactory.Â = ((Pbuffer.getCapabilities() & 0x1) != 0x0);
        GraphicsFactory.Ý = ((Pbuffer.getCapabilities() & 0x2) != 0x0);
        if (!GraphicsFactory.Ø­áŒŠá && !GraphicsFactory.Â && !GraphicsFactory.Ý) {
            throw new SlickException("Your OpenGL card does not support offscreen buffers and hence can't handle the dynamic images required for this application.");
        }
        Log.Ý("Offscreen Buffers FBO=" + GraphicsFactory.Ø­áŒŠá + " PBUFFER=" + GraphicsFactory.Â + " PBUFFERRT=" + GraphicsFactory.Ý);
    }
    
    public static void HorizonCode_Horizon_È(final boolean useFBO) {
        GraphicsFactory.Ø­áŒŠá = useFBO;
    }
    
    public static boolean HorizonCode_Horizon_È() {
        return GraphicsFactory.Ø­áŒŠá;
    }
    
    public static boolean Â() {
        return !GraphicsFactory.Ø­áŒŠá && GraphicsFactory.Â;
    }
    
    public static Graphics HorizonCode_Horizon_È(final Image image) throws SlickException {
        Graphics g = GraphicsFactory.HorizonCode_Horizon_È.get(image.áŒŠÆ());
        if (g == null) {
            g = Ý(image);
            GraphicsFactory.HorizonCode_Horizon_È.put(image.áŒŠÆ(), g);
        }
        return g;
    }
    
    public static void Â(final Image image) throws SlickException {
        final Graphics g = GraphicsFactory.HorizonCode_Horizon_È.remove(image.áŒŠÆ());
        if (g != null) {
            g.Ø­à();
        }
    }
    
    private static Graphics Ý(final Image image) throws SlickException {
        Ý();
        if (GraphicsFactory.Ø­áŒŠá) {
            try {
                return new FBOGraphics(image);
            }
            catch (Exception e) {
                GraphicsFactory.Ø­áŒŠá = false;
                Log.Â("FBO failed in use, falling back to PBuffer");
            }
        }
        if (!GraphicsFactory.Â) {
            throw new SlickException("Failed to create offscreen buffer even though the card reports it's possible");
        }
        if (GraphicsFactory.Ý) {
            return new PBufferGraphics(image);
        }
        return new PBufferUniqueGraphics(image);
    }
}
