package ClassSub;

import org.lwjgl.opengl.*;

public abstract class Class126
{
    private static Class282 lastUsed;
    private static boolean inSafe;
    
    
    public static void enterSafeBlock() {
        if (Class126.inSafe) {
            return;
        }
        Class197.get().flush();
        Class126.lastUsed = Class237.getLastBind();
        Class237.bindNone();
        GL11.glPushAttrib(1048575);
        GL11.glPushClientAttrib(-1);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5888);
        Class126.inSafe = true;
    }
    
    public static void leaveSafeBlock() {
        if (!Class126.inSafe) {
            return;
        }
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        if (Class126.lastUsed != null) {
            Class126.lastUsed.bind();
        }
        else {
            Class237.bindNone();
        }
        Class126.inSafe = false;
    }
    
    public final void call() throws Class341 {
        enterSafeBlock();
        this.performGLOperations();
        leaveSafeBlock();
    }
    
    protected abstract void performGLOperations() throws Class341;
    
    static {
        Class126.inSafe = false;
    }
}
