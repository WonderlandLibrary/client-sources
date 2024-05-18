// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import net.minecraft.client.Minecraft;
import java.util.HashMap;

public final class Stencil
{
    public static final String test = "http://pastebin.com/UXrBQGS";
    public static final String test2 = "http://pastebin.com/UXrB8S";
    public static final String test3 = "http://pastebin.com/UXrB12QG8S";
    public static final String test4 = "http://pastebin.com/UXrQDQG8S";
    public static final String test5 = "http://pastebin.com/UXrBQG18S";
    public static final String test6 = "http://pastebin.com/UXrBQG8S";
    public static final String test7 = "http://pastebin.com/UXrBQG8S";
    public static final String test8 = "http://pastebin.com/UXrBQG8S";
    public static final String test9 = "http://pastebin.com/UXrBQG8S";
    public static final String test0 = "http://pastebin.com/UXrBQG8S";
    public static final String test10 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test11 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test12 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test15 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test16 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test17 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes18 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes19 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes20 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test122 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test21 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test251 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test54 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test125 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test55 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test654 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test542 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test5421 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String te12st = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesQ3Dt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesDSt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesQD3t = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes3QDt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesQDt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes35t = "http://pastebin.com/UXrBQGS";
    public static final String tes23t = "http://pastebin.com/UXrBQGS";
    public static final String tesGFD3t = "http://pastebin.com/UXrBQGS";
    public static final String tes31t = "http://pastebin.com/UXrBQGS";
    public static final String testS13 = "http://pastebin.com/UXrBQGS";
    public static final String test13S = "http://pastebin.com/UXrBQGS";
    public static final String tesT13t = "http://pastebin.com/UXrBQGS";
    public static final String test13ST = "http://pastebin.com/UXrBQGS";
    public static final String test1S3 = "http://pastebin.com/UXrBQGS";
    public static final String test13S3 = "http://pastebin.com/UXrBQGS";
    public static final String tes1Wt = "http://pastebin.com/UXrBQGS";
    public static final String tes13X31Xt = "http://pastebin.com/UXrBQGS";
    public static final String tes13XQt = "http://pastebin.com/UXrBQGS";
    public static final String tes13XDt = "http://pastebin.com/UXrBQGS";
    public static final String tes13t = "http://pastebin.com/UXrBQGS";
    public static final String te3st = "http://pastebin.com/UXrBQGS";
    public static final String te3s3t = "http://pastebin.com/UXrBQGS";
    public static final String tes3112t = "http://pastebin.com/UXrBQGS";
    public static final String test114 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String t1Gest14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String te12st14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String t1st14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesDQt14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teQFt14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teQFtQ14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tQFst14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes13t14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testTRE14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testgf14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teshgft14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testgfd14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14gf = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14ds = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14tgre = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14ed = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1qf4 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tqqsest = "http://pastebin.com/UXrBQGS";
    public static final String tegfdst = "http://pastebin.com/UXrBQGS";
    public static final String tesgqfdt = "http://pastebin.com/UXrBQGS";
    public static final String tesgfddt = "http://pastebin.com/UXrBQGS";
    public static final String tesqgfdt = "http://pastebin.com/UXrBQGS";
    public static final String tedst = "http://pastebin.com/UXrBQGS";
    public static final String tesgrfdt = "http://pastebin.com/UXrBQGS";
    public static final String tesgfdst = "http://pastebin.com/UXrBQGS";
    public static final String test32D = "http://pastebin.com/UXrBQGS";
    public static final String tes23REt = "http://pastebin.com/UXrBQGS";
    public static final String tes23Rt = "http://pastebin.com/UXrBQGS";
    public static final String tes2Xt = "http://pastebin.com/UXrBQGS";
    public static final String tes23Xt2 = "http://pastebin.com/UXrBQGS";
    public static final String test23X2 = "http://pastebin.com/UXrBQGS";
    public static final String tes23Xt = "http://pastebin.com/UXrBQGS";
    public static final String tes2XR2t = "http://pastebin.com/UXrBQGS";
    public static final String teHGFDst = "http://pastebin.com/UXrBQGS";
    public static final String teQFst = "http://pastebin.com/UXrBQGS";
    public static final String tGRTEest = "http://pastebin.com/UXrBQGS";
    public static final String tesRGEt = "http://pastebin.com/UXrBQGS";
    public static final String tesGSt = "http://pastebin.com/UXrBQGS";
    public static final String tes4GRZt = "http://pastebin.com/UXrBQGS";
    public static final String tRZEeqGFDQst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test13QDQD = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testQDQ13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesADt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testAFA13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAFst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAFEFst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testAAFA13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1AEX3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1A3A3XX3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testA3X1GF3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesA3Xt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tA3Xest13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesAX3t13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1A3X3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testA3X13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesAFt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1AF3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1AEFX3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAEXsEAt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAEXst1A3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testAEX13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesAEXt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1GA3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tAXEestA13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tAEXest13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tEAXest13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAEXst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teEAXst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teEAXAst13 = "055p://pas5ebin.com/raw/bG34aM68";
    private static final Stencil INSTANCE;
    private final HashMap<Integer, StencilFunc> stencilFuncs;
    private int layers;
    private boolean renderMask;
    
    static {
        INSTANCE = new Stencil();
    }
    
    public Stencil() {
        this.stencilFuncs = new HashMap<Integer, StencilFunc>();
        this.layers = 1;
    }
    
    public static Stencil getInstance() {
        return Stencil.INSTANCE;
    }
    
    public void setRenderMask(final boolean renderMask) {
        this.renderMask = renderMask;
    }
    
    public static void checkSetupFBO() {
        final Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
            final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
            EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
            final int n = 36161;
            final int n2 = 34041;
            Minecraft.getMinecraft();
            final int displayWidth = Minecraft.displayWidth;
            Minecraft.getMinecraft();
            EXTFramebufferObject.glRenderbufferStorageEXT(n, n2, displayWidth, Minecraft.displayHeight);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
            fbo.depthBuffer = -1;
        }
    }
    
    public static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        final int n = 36161;
        final int n2 = 34041;
        Minecraft.getMinecraft();
        final int displayWidth = Minecraft.displayWidth;
        Minecraft.getMinecraft();
        EXTFramebufferObject.glRenderbufferStorageEXT(n, n2, displayWidth, Minecraft.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
    
    public void startLayer() {
        if (this.layers == 1) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
        }
        GL11.glEnable(2960);
        ++this.layers;
        if (this.layers > this.getMaximumLayers()) {
            System.out.println("StencilUtil: Reached maximum amount of layers!");
            this.layers = 1;
        }
    }
    
    public void stopLayer() {
        if (this.layers == 1) {
            System.out.println("StencilUtil: No layers found!");
            return;
        }
        --this.layers;
        if (this.layers == 1) {
            GL11.glDisable(2960);
        }
        else {
            final StencilFunc lastStencilFunc = this.stencilFuncs.remove(this.layers);
            if (lastStencilFunc != null) {
                lastStencilFunc.use();
            }
        }
    }
    
    public void clear() {
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        this.stencilFuncs.clear();
        this.layers = 1;
    }
    
    public void setBuffer() {
        this.setStencilFunc(new StencilFunc(this, this.renderMask ? 519 : 512, this.layers, this.getMaximumLayers(), 7681, 7680, 7680));
    }
    
    public void setBuffer(final boolean set) {
        this.setStencilFunc(new StencilFunc(this, this.renderMask ? 519 : 512, set ? this.layers : (this.layers - 1), this.getMaximumLayers(), 7681, 7681, 7681));
    }
    
    public void cropOutside() {
        this.setStencilFunc(new StencilFunc(this, 517, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
    }
    
    public void cropInside() {
        this.setStencilFunc(new StencilFunc(this, 514, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
    }
    
    public void setStencilFunc(final StencilFunc stencilFunc) {
        GL11.glStencilFunc(StencilFunc.func_func, StencilFunc.func_ref, StencilFunc.func_mask);
        GL11.glStencilOp(StencilFunc.op_fail, StencilFunc.op_zfail, StencilFunc.op_zpass);
        this.stencilFuncs.put(this.layers, stencilFunc);
    }
    
    public StencilFunc getStencilFunc() {
        return this.stencilFuncs.get(this.layers);
    }
    
    public int getLayer() {
        return this.layers;
    }
    
    public int getStencilBufferSize() {
        return GL11.glGetInteger(3415);
    }
    
    public int getMaximumLayers() {
        return (int)(Math.pow(2.0, this.getStencilBufferSize()) - 1.0);
    }
    
    public void createCirlce(final double x, final double y, final double radius) {
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double sin = Math.sin(i * 3.141592653589793 / 180.0) * radius;
            final double cos = Math.cos(i * 3.141592653589793 / 180.0) * radius;
            GL11.glVertex2d(x + sin, y + cos);
        }
        GL11.glEnd();
    }
    
    public void createRect(final double x, final double y, final double x2, final double y2) {
        GL11.glBegin(7);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
    }
    
    public static class StencilFunc
    {
        public static int func_func;
        public static int func_ref;
        public static int func_mask;
        public static int op_fail;
        public static int op_zfail;
        public static int op_zpass;
        
        public StencilFunc(final Stencil paramStencil, final int func_func, final int func_ref, final int func_mask, final int op_fail, final int op_zfail, final int op_zpass) {
            StencilFunc.func_func = func_func;
            StencilFunc.func_ref = func_ref;
            StencilFunc.func_mask = func_mask;
            StencilFunc.op_fail = op_fail;
            StencilFunc.op_zfail = op_zfail;
            StencilFunc.op_zpass = op_zpass;
        }
        
        public void use() {
            GL11.glStencilFunc(StencilFunc.func_func, StencilFunc.func_ref, StencilFunc.func_mask);
            GL11.glStencilOp(StencilFunc.op_fail, StencilFunc.op_zfail, StencilFunc.op_zpass);
        }
    }
}
