package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import java.nio.*;
import java.util.*;
import optfine.*;

public class WorldVertexBufferUploader
{
    private static final String[] I;
    private static final String __OBFID;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\b\u001c=[e{`P^c|", "KPbkU");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void func_181679_a(final WorldRenderer worldRenderer) {
        if (worldRenderer.getVertexCount() > 0) {
            final VertexFormat vertexFormat = worldRenderer.getVertexFormat();
            final int nextOffset = vertexFormat.getNextOffset();
            final ByteBuffer byteBuffer = worldRenderer.getByteBuffer();
            final List<VertexFormatElement> elements = vertexFormat.getElements();
            final boolean exists = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
            final boolean exists2 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < elements.size()) {
                final VertexFormatElement vertexFormatElement = elements.get(i);
                final VertexFormatElement.EnumUsage usage = vertexFormatElement.getUsage();
                if (exists) {
                    final VertexFormatElement.EnumUsage enumUsage = usage;
                    final ReflectorMethod forgeVertexFormatElementEnumUseage_preDraw = Reflector.ForgeVertexFormatElementEnumUseage_preDraw;
                    final Object[] array = new Object["   ".length()];
                    array["".length()] = vertexFormatElement;
                    array[" ".length()] = nextOffset;
                    array["  ".length()] = byteBuffer;
                    Reflector.callVoid(enumUsage, forgeVertexFormatElementEnumUseage_preDraw, array);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    final int glConstant = vertexFormatElement.getType().getGlConstant();
                    final int index = vertexFormatElement.getIndex();
                    byteBuffer.position(vertexFormat.func_181720_d(i));
                    switch (WorldVertexBufferUploader$1.field_178958_a[usage.ordinal()]) {
                        case 1: {
                            GL11.glVertexPointer(vertexFormatElement.getElementCount(), glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(24789 + 19333 - 41234 + 29996);
                            "".length();
                            if (1 >= 2) {
                                throw null;
                            }
                            break;
                        }
                        case 2: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index);
                            GL11.glTexCoordPointer(vertexFormatElement.getElementCount(), glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(14805 + 23098 - 25462 + 20447);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            "".length();
                            if (1 >= 2) {
                                throw null;
                            }
                            break;
                        }
                        case 3: {
                            GL11.glColorPointer(vertexFormatElement.getElementCount(), glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(2417 + 16716 + 5496 + 8257);
                            "".length();
                            if (4 <= -1) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            GL11.glNormalPointer(glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(3266 + 3582 - 6361 + 32398);
                            break;
                        }
                    }
                }
                ++i;
            }
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.drawMultiTexture();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                GL11.glDrawArrays(worldRenderer.getDrawMode(), "".length(), worldRenderer.getVertexCount());
            }
            int j = "".length();
            final int size = elements.size();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (j < size) {
                final VertexFormatElement vertexFormatElement2 = elements.get(j);
                final VertexFormatElement.EnumUsage usage2 = vertexFormatElement2.getUsage();
                if (exists2) {
                    final VertexFormatElement.EnumUsage enumUsage2 = usage2;
                    final ReflectorMethod forgeVertexFormatElementEnumUseage_postDraw = Reflector.ForgeVertexFormatElementEnumUseage_postDraw;
                    final Object[] array2 = new Object["   ".length()];
                    array2["".length()] = vertexFormatElement2;
                    array2[" ".length()] = nextOffset;
                    array2["  ".length()] = byteBuffer;
                    Reflector.callVoid(enumUsage2, forgeVertexFormatElementEnumUseage_postDraw, array2);
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                else {
                    final int index2 = vertexFormatElement2.getIndex();
                    switch (WorldVertexBufferUploader$1.field_178958_a[usage2.ordinal()]) {
                        case 1: {
                            GL11.glDisableClientState(19753 + 15065 - 27501 + 25567);
                            "".length();
                            if (1 >= 2) {
                                throw null;
                            }
                            break;
                        }
                        case 2: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index2);
                            GL11.glDisableClientState(18735 + 12344 - 27705 + 29514);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            break;
                        }
                        case 3: {
                            GL11.glDisableClientState(1788 + 869 + 19072 + 11157);
                            GlStateManager.resetColor();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            GL11.glDisableClientState(10760 + 24303 - 22990 + 20812);
                            break;
                        }
                    }
                }
                ++j;
            }
        }
        worldRenderer.reset();
    }
    
    static {
        I();
        __OBFID = WorldVertexBufferUploader.I["".length()];
    }
    
    static final class WorldVertexBufferUploader$1
    {
        static final int[] field_178958_a;
        private static final String[] I;
        private static final String __OBFID;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("7\u0004\u000bu\\DxfpZB", "tHTEl");
        }
        
        static {
            I();
            __OBFID = WorldVertexBufferUploader$1.I["".length()];
            field_178958_a = new int[VertexFormatElement.EnumUsage.values().length];
            try {
                WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.POSITION.ordinal()] = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.UV.ordinal()] = "  ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.COLOR.ordinal()] = "   ".length();
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.NORMAL.ordinal()] = (0xB6 ^ 0xB2);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
