package ez.h.utils.shaders;

import org.lwjgl.opengl.*;

public class StencilUtil
{
    static bib mc;
    
    public static void readStencilBuffer(final int n) {
        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(449 + 416 - 651 + 300, n, 1);
        GL11.glStencilOp(6516 + 2264 - 8767 + 7667, 1688 + 3225 + 516 + 2251, 278 + 2224 + 4659 + 519);
    }
    
    public static void checkSetupFBO(final bvd bvd) {
        if (bvd != null && bvd.h > -1) {
            setupFBO(bvd);
            bvd.h = -1;
        }
    }
    
    static {
        StencilUtil.mc = bib.z();
    }
    
    public static void setupFBO(final bvd bvd) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(bvd.h);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(25328 + 2811 - 1714 + 9736, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(21003 + 8104 + 4496 + 2558, 33082 + 31533 - 49308 + 18734, StencilUtil.mc.d, StencilUtil.mc.e);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(26788 + 7060 - 30605 + 32917, 36066 + 15222 - 20078 + 4918, 12128 + 25901 - 15346 + 13478, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(1265 + 28643 - 24545 + 30797, 16370 + 12354 - 465 + 7837, 8276 + 33804 - 17552 + 11633, glGenRenderbuffersEXT);
    }
    
    public static void initStencilToWrite() {
        StencilUtil.mc.b().a(false);
        checkSetupFBO(StencilUtil.mc.b());
        GL11.glClear(97 + 406 + 106 + 415);
        GL11.glEnable(2209 + 1040 - 2326 + 2037);
        GL11.glStencilFunc(408 + 295 - 414 + 230, 1, 1);
        GL11.glStencilOp(7479 + 531 - 1564 + 1235, 3395 + 1268 - 4057 + 7075, 5717 + 3110 - 8561 + 7415);
        GL11.glColorMask(false, false, false, false);
    }
    
    public static void uninitStencilBuffer() {
        GL11.glDisable(2726 + 2123 - 4124 + 2235);
    }
}
