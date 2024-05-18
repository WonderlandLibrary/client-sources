package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public abstract class SlickCallable
{
    private static Texture HorizonCode_Horizon_È;
    private static boolean Â;
    
    static {
        SlickCallable.Â = false;
    }
    
    public static void HorizonCode_Horizon_È() {
        if (SlickCallable.Â) {
            return;
        }
        Renderer.HorizonCode_Horizon_È().Ó();
        SlickCallable.HorizonCode_Horizon_È = TextureImpl.Å();
        TextureImpl.£à();
        GL11.glPushAttrib(1048575);
        GL11.glPushClientAttrib(-1);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        SlickCallable.Â = true;
    }
    
    public static void Â() {
        if (!SlickCallable.Â) {
            return;
        }
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        if (SlickCallable.HorizonCode_Horizon_È != null) {
            SlickCallable.HorizonCode_Horizon_È.Ý();
        }
        else {
            TextureImpl.£à();
        }
        SlickCallable.Â = false;
    }
    
    public final void Ý() throws SlickException {
        HorizonCode_Horizon_È();
        this.Ø­áŒŠá();
        Â();
    }
    
    protected abstract void Ø­áŒŠá() throws SlickException;
}
