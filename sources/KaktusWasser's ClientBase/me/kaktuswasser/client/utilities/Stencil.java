// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import org.lwjgl.opengl.GL11;
import java.util.HashMap;

public final class Stencil
{
    private static final Stencil INSTANCE;
    private final HashMap<Integer, StencilFunc> stencilFuncs;
    private int layers;
    private boolean renderMask;
    
    static {
        INSTANCE = new Stencil();
    }
    
    private Stencil() {
        this.stencilFuncs = new HashMap<Integer, StencilFunc>();
        this.layers = 1;
    }
    
    public static Stencil getInstance() {
        return Stencil.INSTANCE;
    }
    
    public void setRenderMask(final boolean renderMask) {
        this.renderMask = renderMask;
    }
    
    public void startLayer() {
        if (this.layers == 1) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
        }
        GL11.glEnable(2960);
        ++this.layers;
        if (this.layers > this.getMaximumLayers()) {
            this.layers = 1;
        }
    }
    
    public void stopLayer() {
        if (this.layers == 1) {
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
        this.setStencilFunc(new StencilFunc(this.renderMask ? 519 : 512, this.layers, this.getMaximumLayers(), 7681, 7680, 7680));
    }
    
    public void setBuffer(final boolean set) {
        this.setStencilFunc(new StencilFunc(this.renderMask ? 519 : 512, set ? this.layers : (this.layers - 1), this.getMaximumLayers(), 7681, 7681, 7681));
    }
    
    public void cropOutside() {
        this.setStencilFunc(new StencilFunc(517, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
    }
    
    public void cropInside() {
        this.setStencilFunc(new StencilFunc(514, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
    }
    
    public void setStencilFunc(final StencilFunc stencilFunc) {
        GL11.glStencilFunc(stencilFunc.func_func, stencilFunc.func_ref, stencilFunc.func_mask);
        GL11.glStencilOp(stencilFunc.op_fail, stencilFunc.op_zfail, stencilFunc.op_zpass);
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
    
    public class StencilFunc
    {
        public final int func_func;
        public final int func_ref;
        public final int func_mask;
        public final int op_fail;
        public final int op_zfail;
        public final int op_zpass;
        
        public StencilFunc(final int func_func, final int func_ref, final int func_mask, final int op_fail, final int op_zfail, final int op_zpass) {
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
}
