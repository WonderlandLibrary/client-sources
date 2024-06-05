package me.satisfactory.base.utils;

import org.lwjgl.opengl.*;

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
