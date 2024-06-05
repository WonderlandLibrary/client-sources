package club.mega.util;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class Stencil {
    public class StencilFunc {

        public final int func_func;
        public final int func_ref;
        public final int func_mask;
        public final int op_fail;
        public final int op_zfail;
        public final int op_zpass;

        public StencilFunc(int func_func, int func_ref, int func_mask, int op_fail, int op_zfail, int op_zpass) {
            this.func_func = func_func;
            this.func_ref = func_ref;
            this.func_mask = func_mask;
            this.op_fail = op_fail;
            this.op_zfail = op_zfail;
            this.op_zpass = op_zpass;
        }

        public void use() {
            GL11.glStencilFunc(this.func_func, this.func_ref, this.func_mask);
            GL11.glStencilOp(this.op_fail, this.op_zfail, this.op_zpass);
        }
    }

    private static final Stencil INSTANCE = new Stencil();

    private final HashMap<Integer, StencilFunc> stencilFuncs = new HashMap<>();

    private int layers = 1;

    private boolean renderMask;

    public static Stencil getInstance() {
        MCStencil.checkSetupFBO();
        return INSTANCE;
    }

    public void startLayer() {
        if (this.layers == 1) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
        }
        GL11.glEnable(2960);
        this.layers++;
        if (this.layers > getMaximumLayers())
            this.layers = 1;
    }

    public void stopLayer() {
        if (this.layers == 1)
            return;
        this.layers--;
        if (this.layers == 1) {
            GL11.glDisable(2960);
        } else {
            StencilFunc lastStencilFunc = this.stencilFuncs.remove(Integer.valueOf(this.layers));
            if (lastStencilFunc != null)
                lastStencilFunc.use();
        }
    }

    public void clear() {
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        this.stencilFuncs.clear();
        this.layers = 1;
    }

    public void setBuffer() {
        setStencilFunc(new StencilFunc(!this.renderMask ? 512 : 519, this.layers,
                getMaximumLayers(), 7681, 7680, 7680));
    }

    public void setBuffer(boolean set) {
        setStencilFunc(new StencilFunc(!this.renderMask ? 512 : 519, set ? this.layers : (this.layers - 1),

                getMaximumLayers(), 7681, 7681, 7681));
    }

    public void cropInside() {
        setStencilFunc(new StencilFunc(514, this.layers, getMaximumLayers(), 7680, 7680, 7680));
    }

    public void setStencilFunc(StencilFunc stencilFunc) {
        GL11.glStencilFunc(stencilFunc.func_func, stencilFunc.func_ref, stencilFunc.func_mask);
        GL11.glStencilOp(stencilFunc.op_fail, stencilFunc.op_zfail, stencilFunc.op_zpass);
        this.stencilFuncs.put(Integer.valueOf(this.layers), stencilFunc);
    }

    public int getLayer() {
        return this.layers;
    }

    public int getStencilBufferSize() {
        return GL11.glGetInteger(3415);
    }

    public int getMaximumLayers() {
        return (int)(Math.pow(2.0D, getStencilBufferSize()) - 1.0D);
    }

    public void createRect(double x, double y, double x2, double y2) {
        GL11.glBegin(7);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
    }

    public static class MCStencil {
        public static void checkSetupFBO() {
            Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
            if (fbo != null &&
                    fbo.depthBuffer > -1) {
                setupFBO(fbo);
                fbo.depthBuffer = -1;
            }
        }

        public static void setupFBO(Framebuffer fbo) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
            int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
            EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
            EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041,
                    (Minecraft.getMinecraft()).displayWidth,
                    (Minecraft.getMinecraft()).displayHeight);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
        }
    }
}
