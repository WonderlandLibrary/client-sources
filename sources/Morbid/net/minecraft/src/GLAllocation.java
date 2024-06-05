package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;
import java.nio.*;

public class GLAllocation
{
    private static final Map field_74531_a;
    private static final List field_74530_b;
    
    static {
        field_74531_a = new HashMap();
        field_74530_b = new ArrayList();
    }
    
    public static synchronized int generateDisplayLists(final int par0) {
        final int var1 = GL11.glGenLists(par0);
        GLAllocation.field_74531_a.put(var1, par0);
        return var1;
    }
    
    public static synchronized int generateTextureNames() {
        final int var0 = GL11.glGenTextures();
        GLAllocation.field_74530_b.add(var0);
        return var0;
    }
    
    public static synchronized void deleteDisplayLists(final int par0) {
        GL11.glDeleteLists(par0, GLAllocation.field_74531_a.remove(par0));
    }
    
    public static synchronized void func_98302_b() {
        for (int var0 = 0; var0 < GLAllocation.field_74530_b.size(); ++var0) {
            GL11.glDeleteTextures(GLAllocation.field_74530_b.get(var0));
        }
        GLAllocation.field_74530_b.clear();
    }
    
    public static synchronized void deleteTexturesAndDisplayLists() {
        for (final Map.Entry var2 : GLAllocation.field_74531_a.entrySet()) {
            GL11.glDeleteLists(var2.getKey(), var2.getValue());
        }
        GLAllocation.field_74531_a.clear();
        func_98302_b();
    }
    
    public static synchronized ByteBuffer createDirectByteBuffer(final int par0) {
        return ByteBuffer.allocateDirect(par0).order(ByteOrder.nativeOrder());
    }
    
    public static IntBuffer createDirectIntBuffer(final int par0) {
        return createDirectByteBuffer(par0 << 2).asIntBuffer();
    }
    
    public static FloatBuffer createDirectFloatBuffer(final int par0) {
        return createDirectByteBuffer(par0 << 2).asFloatBuffer();
    }
}
