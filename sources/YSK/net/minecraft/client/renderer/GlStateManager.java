package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import java.nio.*;

public class GlStateManager
{
    private static Color colorState;
    private static DepthState depthState;
    private static ColorMask colorMaskState;
    private static BooleanState rescaleNormalState;
    private static final String[] I;
    private static TextureState[] textureState;
    private static ClearState clearState;
    private static ColorMaterialState colorMaterialState;
    private static final String __OBFID;
    public static boolean clearEnabled;
    private static AlphaState alphaState;
    private static BooleanState normalizeState;
    private static ColorLogicState colorLogicState;
    private static FogState fogState;
    private static BooleanState[] lightState;
    private static StencilState stencilState;
    private static TexGenState texGenState;
    private static BlendState blendState;
    private static BooleanState lightingState;
    private static int activeTextureUnit;
    private static PolygonOffsetState polygonOffsetState;
    private static int activeShadeModel;
    private static CullState cullState;
    
    public static void clearColor(final float red, final float green, final float blue, final float alpha) {
        if (red != GlStateManager.clearState.field_179203_b.red || green != GlStateManager.clearState.field_179203_b.green || blue != GlStateManager.clearState.field_179203_b.blue || alpha != GlStateManager.clearState.field_179203_b.alpha) {
            GL11.glClearColor(GlStateManager.clearState.field_179203_b.red = red, GlStateManager.clearState.field_179203_b.green = green, GlStateManager.clearState.field_179203_b.blue = blue, GlStateManager.clearState.field_179203_b.alpha = alpha);
        }
    }
    
    public static void disableAlpha() {
        GlStateManager.alphaState.field_179208_a.setDisabled();
    }
    
    public static void blendFunc(final int srcFactor, final int dstFactor) {
        if (srcFactor != GlStateManager.blendState.srcFactor || dstFactor != GlStateManager.blendState.dstFactor) {
            GL11.glBlendFunc(GlStateManager.blendState.srcFactor = srcFactor, GlStateManager.blendState.dstFactor = dstFactor);
        }
    }
    
    public static void shadeModel(final int activeShadeModel) {
        if (activeShadeModel != GlStateManager.activeShadeModel) {
            GL11.glShadeModel(GlStateManager.activeShadeModel = activeShadeModel);
        }
    }
    
    public static void enableTexture2D() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].texture2DState.setEnabled();
    }
    
    public static void multMatrix(final FloatBuffer floatBuffer) {
        GL11.glMultMatrix(floatBuffer);
    }
    
    public static void enableBlend() {
        GlStateManager.blendState.field_179213_a.setEnabled();
    }
    
    public static void translate(final double n, final double n2, final double n3) {
        GL11.glTranslated(n, n2, n3);
    }
    
    public static void setFogDensity(final float field_179048_c) {
        if (field_179048_c != GlStateManager.fogState.field_179048_c) {
            GL11.glFogf(931 + 230 - 425 + 2178, GlStateManager.fogState.field_179048_c = field_179048_c);
        }
    }
    
    public static void scale(final double n, final double n2, final double n3) {
        GL11.glScaled(n, n2, n3);
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void doPolygonOffset(final float field_179043_c, final float field_179041_d) {
        if (field_179043_c != GlStateManager.polygonOffsetState.field_179043_c || field_179041_d != GlStateManager.polygonOffsetState.field_179041_d) {
            GL11.glPolygonOffset(GlStateManager.polygonOffsetState.field_179043_c = field_179043_c, GlStateManager.polygonOffsetState.field_179041_d = field_179041_d);
        }
    }
    
    private static TexGenCoord texGenCoord(final TexGen texGen) {
        switch (GlStateManager$1.field_179175_a[texGen.ordinal()]) {
            case 1: {
                return GlStateManager.texGenState.field_179064_a;
            }
            case 2: {
                return GlStateManager.texGenState.field_179062_b;
            }
            case 3: {
                return GlStateManager.texGenState.field_179063_c;
            }
            case 4: {
                return GlStateManager.texGenState.field_179061_d;
            }
            default: {
                return GlStateManager.texGenState.field_179064_a;
            }
        }
    }
    
    public static void scale(final float n, final float n2, final float n3) {
        GL11.glScalef(n, n2, n3);
    }
    
    public static void depthFunc(final int depthFunc) {
        if (depthFunc != GlStateManager.depthState.depthFunc) {
            GL11.glDepthFunc(GlStateManager.depthState.depthFunc = depthFunc);
        }
    }
    
    public static void colorLogicOp(final int field_179196_b) {
        if (field_179196_b != GlStateManager.colorLogicState.field_179196_b) {
            GL11.glLogicOp(GlStateManager.colorLogicState.field_179196_b = field_179196_b);
        }
    }
    
    public static void disableLighting() {
        GlStateManager.lightingState.setDisabled();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(" \u001d*ZcSaG_f[", "cQujS");
    }
    
    public static void enableLight(final int n) {
        GlStateManager.lightState[n].setEnabled();
    }
    
    public static void setFog(final int field_179047_b) {
        if (field_179047_b != GlStateManager.fogState.field_179047_b) {
            GL11.glFogi(1910 + 127 - 603 + 1483, GlStateManager.fogState.field_179047_b = field_179047_b);
        }
    }
    
    public static void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        if (red != GlStateManager.colorMaskState.red || green != GlStateManager.colorMaskState.green || blue != GlStateManager.colorMaskState.blue || alpha != GlStateManager.colorMaskState.alpha) {
            GL11.glColorMask(GlStateManager.colorMaskState.red = red, GlStateManager.colorMaskState.green = green, GlStateManager.colorMaskState.blue = blue, GlStateManager.colorMaskState.alpha = alpha);
        }
    }
    
    public static void disableCull() {
        GlStateManager.cullState.field_179054_a.setDisabled();
    }
    
    public static void rotate(final float n, final float n2, final float n3, final float n4) {
        GL11.glRotatef(n, n2, n3, n4);
    }
    
    public static void matrixMode(final int n) {
        GL11.glMatrixMode(n);
    }
    
    public static void clear(final int n) {
        if (GlStateManager.clearEnabled) {
            GL11.glClear(n);
        }
    }
    
    public static void popAttrib() {
        GL11.glPopAttrib();
    }
    
    public static void disableNormalize() {
        GlStateManager.normalizeState.setDisabled();
    }
    
    public static void viewport(final int n, final int n2, final int n3, final int n4) {
        GL11.glViewport(n, n2, n3, n4);
    }
    
    public static void disableLight(final int n) {
        GlStateManager.lightState[n].setDisabled();
    }
    
    public static void disablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setDisabled();
    }
    
    public static void enableDepth() {
        GlStateManager.depthState.depthTest.setEnabled();
    }
    
    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }
    
    public static void disableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setDisabled();
    }
    
    public static void color(final float n, final float n2, final float n3) {
        color(n, n2, n3, 1.0f);
    }
    
    public static void enableRescaleNormal() {
        GlStateManager.rescaleNormalState.setEnabled();
    }
    
    public static void deleteTexture(final int n) {
        GL11.glDeleteTextures(n);
        final TextureState[] textureState;
        final int length = (textureState = GlStateManager.textureState).length;
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < length) {
            final TextureState textureState2 = textureState[i];
            if (textureState2.textureName == n) {
                textureState2.textureName = -" ".length();
            }
            ++i;
        }
    }
    
    public static void enablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setEnabled();
    }
    
    public static void disableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setDisabled();
    }
    
    public static void func_179105_a(final TexGen texGen, final int n, final FloatBuffer floatBuffer) {
        GL11.glTexGen(texGenCoord(texGen).field_179065_b, n, floatBuffer);
    }
    
    public static void alphaFunc(final int func, final float ref) {
        if (func != GlStateManager.alphaState.func || ref != GlStateManager.alphaState.ref) {
            GL11.glAlphaFunc(GlStateManager.alphaState.func = func, GlStateManager.alphaState.ref = ref);
        }
    }
    
    static {
        I();
        __OBFID = GlStateManager.I["".length()];
        GlStateManager.alphaState = new AlphaState(null);
        GlStateManager.lightingState = new BooleanState(2117 + 320 - 876 + 1335);
        GlStateManager.lightState = new BooleanState[0x8B ^ 0x83];
        GlStateManager.colorMaterialState = new ColorMaterialState(null);
        GlStateManager.blendState = new BlendState(null);
        GlStateManager.depthState = new DepthState(null);
        GlStateManager.fogState = new FogState(null);
        GlStateManager.cullState = new CullState(null);
        GlStateManager.polygonOffsetState = new PolygonOffsetState(null);
        GlStateManager.colorLogicState = new ColorLogicState(null);
        GlStateManager.texGenState = new TexGenState(null);
        GlStateManager.clearState = new ClearState(null);
        GlStateManager.stencilState = new StencilState(null);
        GlStateManager.normalizeState = new BooleanState(2116 + 1649 - 1356 + 568);
        GlStateManager.activeTextureUnit = "".length();
        GlStateManager.textureState = new TextureState[0x8A ^ 0x82];
        GlStateManager.activeShadeModel = 2033 + 3710 + 11 + 1671;
        GlStateManager.rescaleNormalState = new BooleanState(4977 + 1438 + 18007 + 8404);
        GlStateManager.colorMaskState = new ColorMask(null);
        GlStateManager.colorState = new Color();
        GlStateManager.clearEnabled = (" ".length() != 0);
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < (0x9B ^ 0x93)) {
            GlStateManager.lightState[i] = new BooleanState(8478 + 16362 - 14110 + 5654 + i);
            ++i;
        }
        int j = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (j < (0x35 ^ 0x3D)) {
            GlStateManager.textureState[j] = new TextureState(null);
            ++j;
        }
    }
    
    public static void ortho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glOrtho(n, n2, n3, n4, n5, n6);
    }
    
    public static void setFogEnd(final float field_179046_e) {
        if (field_179046_e != GlStateManager.fogState.field_179046_e) {
            GL11.glFogf(690 + 2685 - 1577 + 1118, GlStateManager.fogState.field_179046_e = field_179046_e);
        }
    }
    
    public static void enableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setEnabled();
    }
    
    public static void enableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setEnabled();
    }
    
    public static void colorMaterial(final int field_179189_b, final int field_179190_c) {
        if (field_179189_b != GlStateManager.colorMaterialState.field_179189_b || field_179190_c != GlStateManager.colorMaterialState.field_179190_c) {
            GL11.glColorMaterial(GlStateManager.colorMaterialState.field_179189_b = field_179189_b, GlStateManager.colorMaterialState.field_179190_c = field_179190_c);
        }
    }
    
    public static void resetColor() {
        final Color colorState = GlStateManager.colorState;
        final Color colorState2 = GlStateManager.colorState;
        final Color colorState3 = GlStateManager.colorState;
        final Color colorState4 = GlStateManager.colorState;
        final float n = -1.0f;
        colorState4.alpha = n;
        colorState3.blue = n;
        colorState2.green = n;
        colorState.red = n;
    }
    
    public static void translate(final float n, final float n2, final float n3) {
        GL11.glTranslatef(n, n2, n3);
    }
    
    public static void setFogStart(final float field_179045_d) {
        if (field_179045_d != GlStateManager.fogState.field_179045_d) {
            GL11.glFogf(2511 + 2309 - 3490 + 1585, GlStateManager.fogState.field_179045_d = field_179045_d);
        }
    }
    
    public static void enableAlpha() {
        GlStateManager.alphaState.field_179208_a.setEnabled();
    }
    
    public static void enableLighting() {
        GlStateManager.lightingState.setEnabled();
    }
    
    public static void clearDepth(final double field_179205_a) {
        if (field_179205_a != GlStateManager.clearState.field_179205_a) {
            GL11.glClearDepth(GlStateManager.clearState.field_179205_a = field_179205_a);
        }
    }
    
    public static int generateTexture() {
        return GL11.glGenTextures();
    }
    
    public static void bindTexture(final int textureName) {
        if (textureName != GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName) {
            GL11.glBindTexture(1239 + 1673 + 618 + 23, GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = textureName);
        }
    }
    
    public static void disableBlend() {
        GlStateManager.blendState.field_179213_a.setDisabled();
    }
    
    public static void enableNormalize() {
        GlStateManager.normalizeState.setEnabled();
    }
    
    public static void pushAttrib() {
        GL11.glPushAttrib(4426 + 8081 - 5067 + 816);
    }
    
    public static void disableTexture2D() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].texture2DState.setDisabled();
    }
    
    public static void disableFog() {
        GlStateManager.fogState.field_179049_a.setDisabled();
    }
    
    public static void callList(final int n) {
        GL11.glCallList(n);
    }
    
    public static void tryBlendFuncSeparate(final int srcFactor, final int dstFactor, final int srcFactorAlpha, final int dstFactorAlpha) {
        if (srcFactor != GlStateManager.blendState.srcFactor || dstFactor != GlStateManager.blendState.dstFactor || srcFactorAlpha != GlStateManager.blendState.srcFactorAlpha || dstFactorAlpha != GlStateManager.blendState.dstFactorAlpha) {
            OpenGlHelper.glBlendFunc(GlStateManager.blendState.srcFactor = srcFactor, GlStateManager.blendState.dstFactor = dstFactor, GlStateManager.blendState.srcFactorAlpha = srcFactorAlpha, GlStateManager.blendState.dstFactorAlpha = dstFactorAlpha);
        }
    }
    
    public static void popMatrix() {
        GL11.glPopMatrix();
    }
    
    public static void disableRescaleNormal() {
        GlStateManager.rescaleNormalState.setDisabled();
    }
    
    public static void disableTexGenCoord(final TexGen texGen) {
        texGenCoord(texGen).field_179067_a.setDisabled();
    }
    
    public static void color(final float red, final float green, final float blue, final float alpha) {
        if (red != GlStateManager.colorState.red || green != GlStateManager.colorState.green || blue != GlStateManager.colorState.blue || alpha != GlStateManager.colorState.alpha) {
            GL11.glColor4f(GlStateManager.colorState.red = red, GlStateManager.colorState.green = green, GlStateManager.colorState.blue = blue, GlStateManager.colorState.alpha = alpha);
        }
    }
    
    public static void getFloat(final int n, final FloatBuffer floatBuffer) {
        GL11.glGetFloat(n, floatBuffer);
    }
    
    public static void cullFace(final int field_179053_b) {
        if (field_179053_b != GlStateManager.cullState.field_179053_b) {
            GL11.glCullFace(GlStateManager.cullState.field_179053_b = field_179053_b);
        }
    }
    
    public static void setActiveTexture(final int activeTexture) {
        if (GlStateManager.activeTextureUnit != activeTexture - OpenGlHelper.defaultTexUnit) {
            GlStateManager.activeTextureUnit = activeTexture - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(activeTexture);
        }
    }
    
    public static void enableTexGenCoord(final TexGen texGen) {
        texGenCoord(texGen).field_179067_a.setEnabled();
    }
    
    public static void enableFog() {
        GlStateManager.fogState.field_179049_a.setEnabled();
    }
    
    public static void texGen(final TexGen texGen, final int field_179066_c) {
        final TexGenCoord texGenCoord = texGenCoord(texGen);
        if (field_179066_c != texGenCoord.field_179066_c) {
            texGenCoord.field_179066_c = field_179066_c;
            GL11.glTexGeni(texGenCoord.field_179065_b, 3972 + 3061 - 6564 + 9003, field_179066_c);
        }
    }
    
    public static void depthMask(final boolean maskEnabled) {
        if (maskEnabled != GlStateManager.depthState.maskEnabled) {
            GL11.glDepthMask(GlStateManager.depthState.maskEnabled = maskEnabled);
        }
    }
    
    public static void bindCurrentTexture() {
        GL11.glBindTexture(2283 + 3327 - 3351 + 1294, GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName);
    }
    
    public static void enableCull() {
        GlStateManager.cullState.field_179054_a.setEnabled();
    }
    
    public static void disableDepth() {
        GlStateManager.depthState.depthTest.setDisabled();
    }
    
    public static void pushMatrix() {
        GL11.glPushMatrix();
    }
    
    public enum TexGen
    {
        private static final TexGen[] ENUM$VALUES;
        
        T(TexGen.I["   ".length()], " ".length(), TexGen.I[0x5A ^ 0x5E], " ".length()), 
        R(TexGen.I[0x99 ^ 0x9C], "  ".length(), TexGen.I[0x9F ^ 0x99], "  ".length()), 
        Q(TexGen.I[0x79 ^ 0x7E], "   ".length(), TexGen.I[0x45 ^ 0x4D], "   ".length());
        
        private static final String __OBFID;
        private static final TexGen[] $VALUES;
        private static final String[] I;
        
        S(TexGen.I[" ".length()], "".length(), TexGen.I["  ".length()], "".length());
        
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
                if (1 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x7E ^ 0x77])["".length()] = I("\u0015\u0000\bJff|eObd", "VLWzV");
            TexGen.I[" ".length()] = I("%", "vFvUB");
            TexGen.I["  ".length()] = I(";", "hKuof");
            TexGen.I["   ".length()] = I("\u0013", "GElrA");
            TexGen.I[0xC7 ^ 0xC3] = I("7", "cSiiO");
            TexGen.I[0x9B ^ 0x9E] = I("5", "gHqeV");
            TexGen.I[0x4 ^ 0x2] = I(";", "iEzLV");
            TexGen.I[0x63 ^ 0x64] = I("\t", "XoRxE");
            TexGen.I[0x23 ^ 0x2B] = I("$", "uTNXP");
        }
        
        static {
            I();
            __OBFID = TexGen.I["".length()];
            final TexGen[] enum$VALUES = new TexGen[0x6E ^ 0x6A];
            enum$VALUES["".length()] = TexGen.S;
            enum$VALUES[" ".length()] = TexGen.T;
            enum$VALUES["  ".length()] = TexGen.R;
            enum$VALUES["   ".length()] = TexGen.Q;
            ENUM$VALUES = enum$VALUES;
            final TexGen[] $values = new TexGen[0x80 ^ 0x84];
            $values["".length()] = TexGen.S;
            $values[" ".length()] = TexGen.T;
            $values["  ".length()] = TexGen.R;
            $values["   ".length()] = TexGen.Q;
            $VALUES = $values;
        }
        
        private TexGen(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    static class BlendState
    {
        private static final String[] I;
        public int dstFactor;
        public int srcFactorAlpha;
        private static final String __OBFID;
        public int dstFactorAlpha;
        public int srcFactor;
        public BooleanState field_179213_a;
        
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
                if (2 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private BlendState() {
            this.field_179213_a = new BooleanState(2118 + 94 - 1662 + 2492);
            this.srcFactor = " ".length();
            this.dstFactor = "".length();
            this.srcFactorAlpha = " ".length();
            this.dstFactorAlpha = "".length();
        }
        
        BlendState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\"\u0003\u001bT}Q\u007fvQxT", "aODdM");
        }
        
        static {
            I();
            __OBFID = BlendState.I["".length()];
        }
    }
    
    static final class GlStateManager$1
    {
        private static final String[] I;
        static final int[] field_179175_a;
        private static final String __OBFID;
        
        static {
            I();
            __OBFID = GlStateManager$1.I["".length()];
            field_179175_a = new int[TexGen.values().length];
            try {
                GlStateManager$1.field_179175_a[TexGen.S.ordinal()] = " ".length();
                "".length();
                if (4 == 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GlStateManager$1.field_179175_a[TexGen.T.ordinal()] = "  ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                GlStateManager$1.field_179175_a[TexGen.R.ordinal()] = "   ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                GlStateManager$1.field_179175_a[TexGen.Q.ordinal()] = (0x89 ^ 0x8D);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0010 1jzc\\\\o\u007fd", "SlnZJ");
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
                if (1 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class BooleanState
    {
        private static final String __OBFID;
        private boolean currentState;
        private static final String[] I;
        private final int capability;
        
        static {
            I();
            __OBFID = BooleanState.I["".length()];
        }
        
        public BooleanState(final int capability) {
            this.currentState = ("".length() != 0);
            this.capability = capability;
        }
        
        public void setEnabled() {
            this.setState(" ".length() != 0);
        }
        
        public void setDisabled() {
            this.setState("".length() != 0);
        }
        
        public void setState(final boolean currentState) {
            if (currentState != this.currentState) {
                this.currentState = currentState;
                if (currentState) {
                    GL11.glEnable(this.capability);
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                else {
                    GL11.glDisable(this.capability);
                }
            }
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
                if (0 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0002%\ns[qYgv^u", "AiUCk");
        }
    }
    
    static class TextureState
    {
        private static final String[] I;
        private static final String __OBFID;
        public BooleanState texture2DState;
        public int textureName;
        
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
                if (false) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        TextureState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0011%\tB[bYdGXk", "RiVrk");
        }
        
        private TextureState() {
            this.texture2DState = new BooleanState(2208 + 1900 - 792 + 237);
            this.textureName = "".length();
        }
        
        static {
            I();
            __OBFID = TextureState.I["".length()];
        }
    }
    
    static class TexGenCoord
    {
        private static final String[] I;
        private static final String __OBFID;
        public BooleanState field_179067_a;
        public int field_179065_b;
        public int field_179066_c;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0014\u000f5c|gsXfxf", "WCjSL");
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
                if (2 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public TexGenCoord(final int field_179065_b, final int n) {
            this.field_179066_c = -" ".length();
            this.field_179065_b = field_179065_b;
            this.field_179067_a = new BooleanState(n);
        }
        
        static {
            I();
            __OBFID = TexGenCoord.I["".length()];
        }
    }
    
    static class ColorMask
    {
        public boolean alpha;
        private static final String __OBFID;
        public boolean blue;
        public boolean red;
        private static final String[] I;
        public boolean green;
        
        private ColorMask() {
            this.red = (" ".length() != 0);
            this.green = (" ".length() != 0);
            this.blue = (" ".length() != 0);
            this.alpha = (" ".length() != 0);
        }
        
        ColorMask(final GlStateManager$1 glStateManager$1) {
            this();
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
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("3?(ZS@CE_V@", "pswjc");
        }
        
        static {
            I();
            __OBFID = ColorMask.I["".length()];
        }
    }
    
    static class DepthState
    {
        private static final String[] I;
        public int depthFunc;
        public boolean maskEnabled;
        private static final String __OBFID;
        public BooleanState depthTest;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0004\u000b\rw[ww`r_p", "GGRGk");
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
                if (3 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            __OBFID = DepthState.I["".length()];
        }
        
        private DepthState() {
            this.depthTest = new BooleanState(887 + 2724 - 2498 + 1816);
            this.maskEnabled = (" ".length() != 0);
            this.depthFunc = 200 + 487 - 428 + 254;
        }
        
        DepthState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class Color
    {
        public float alpha;
        private static final String[] I;
        public float red;
        public float blue;
        public float green;
        private static final String __OBFID;
        
        public Color(final float red, final float green, final float blue, final float alpha) {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(")\u001f8C_ZcUFZX", "jSgso");
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Color() {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
        }
        
        static {
            I();
            __OBFID = Color.I["".length()];
        }
    }
    
    static class StencilFunc
    {
        private static final String[] I;
        public int field_179079_b;
        private static final String __OBFID;
        public int field_179081_a;
        public int field_179080_c;
        
        StencilFunc(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        static {
            I();
            __OBFID = StencilFunc.I["".length()];
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
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0016\u001a*RzefGW~a", "UVubJ");
        }
        
        private StencilFunc() {
            this.field_179081_a = 10 + 381 - 54 + 182;
            this.field_179079_b = "".length();
            this.field_179080_c = -" ".length();
        }
    }
    
    static class ClearState
    {
        private static final String __OBFID;
        private static final String[] I;
        public Color field_179203_b;
        public int field_179204_c;
        public double field_179205_a;
        
        ClearState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private ClearState() {
            this.field_179205_a = 1.0;
            this.field_179203_b = new Color(0.0f, 0.0f, 0.0f, 0.0f);
            this.field_179204_c = "".length();
        }
        
        static {
            I();
            __OBFID = ClearState.I["".length()];
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\t#;iHz_VlMy", "JodYx");
        }
    }
    
    static class FogState
    {
        public BooleanState field_179049_a;
        public int field_179047_b;
        private static final String __OBFID;
        public float field_179045_d;
        private static final String[] I;
        public float field_179046_e;
        public float field_179048_c;
        
        private FogState() {
            this.field_179049_a = new BooleanState(2472 + 1041 - 1372 + 771);
            this.field_179047_b = 618 + 996 - 1462 + 1896;
            this.field_179048_c = 1.0f;
            this.field_179045_d = 0.0f;
            this.field_179046_e = 1.0f;
        }
        
        FogState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("$(\rjTWT`oPQ", "gdRZd");
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
                if (3 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            __OBFID = FogState.I["".length()];
        }
    }
    
    static class ColorMaterialState
    {
        public int field_179190_c;
        public BooleanState field_179191_a;
        private static final String[] I;
        public int field_179189_b;
        private static final String __OBFID;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("15\u0014FjBIyCnK", "ryKvZ");
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        ColorMaterialState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        static {
            I();
            __OBFID = ColorMaterialState.I["".length()];
        }
        
        private ColorMaterialState() {
            this.field_179191_a = new BooleanState(766 + 1640 - 2125 + 2622);
            this.field_179189_b = 519 + 2 + 263 + 248;
            this.field_179190_c = 961 + 2857 - 2906 + 4722;
        }
    }
    
    static class StencilState
    {
        private static final String[] I;
        public int field_179075_e;
        public int field_179077_c;
        public int field_179076_b;
        public int field_179074_d;
        private static final String __OBFID;
        public StencilFunc field_179078_a;
        
        static {
            I();
            __OBFID = StencilState.I["".length()];
        }
        
        StencilState(final GlStateManager$1 glStateManager$1) {
            this();
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
                if (2 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(":-\u0007VhIQjSlJ", "yaXfX");
        }
        
        private StencilState() {
            this.field_179078_a = new StencilFunc(null);
            this.field_179076_b = -" ".length();
            this.field_179077_c = 3267 + 5163 - 1980 + 1230;
            this.field_179074_d = 7351 + 2827 - 8301 + 5803;
            this.field_179075_e = 4555 + 722 - 1931 + 4334;
        }
    }
    
    static class CullState
    {
        private static final String[] I;
        public BooleanState field_179054_a;
        public int field_179053_b;
        private static final String __OBFID;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0001\n+DjrvFAnz", "BFttZ");
        }
        
        static {
            I();
            __OBFID = CullState.I["".length()];
        }
        
        private CullState() {
            this.field_179054_a = new BooleanState(1064 + 2307 - 2767 + 2280);
            this.field_179053_b = 771 + 949 - 1247 + 556;
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        CullState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class ColorLogicState
    {
        public BooleanState field_179197_a;
        private static final String[] I;
        public int field_179196_b;
        private static final String __OBFID;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("6\u001c:RRE`WWWD", "uPebb");
        }
        
        static {
            I();
            __OBFID = ColorLogicState.I["".length()];
        }
        
        private ColorLogicState() {
            this.field_179197_a = new BooleanState(2795 + 483 - 295 + 75);
            this.field_179196_b = 1122 + 3282 - 2048 + 3023;
        }
        
        ColorLogicState(final GlStateManager$1 glStateManager$1) {
            this();
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class AlphaState
    {
        private static final String[] I;
        public BooleanState field_179208_a;
        private static final String __OBFID;
        public float ref;
        public int func;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u00018\nCcrDgFft", "BtUsS");
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private AlphaState() {
            this.field_179208_a = new BooleanState(697 + 1982 - 106 + 435);
            this.func = 182 + 424 - 596 + 509;
            this.ref = -1.0f;
        }
        
        static {
            I();
            __OBFID = AlphaState.I["".length()];
        }
        
        AlphaState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class PolygonOffsetState
    {
        public BooleanState field_179042_b;
        private static final String[] I;
        public BooleanState field_179044_a;
        public float field_179043_c;
        public float field_179041_d;
        private static final String __OBFID;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0000+\u001e|dsWsy`v", "CgALT");
        }
        
        static {
            I();
            __OBFID = PolygonOffsetState.I["".length()];
        }
        
        PolygonOffsetState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private PolygonOffsetState() {
            this.field_179044_a = new BooleanState(11994 + 15401 - 24412 + 29840);
            this.field_179042_b = new BooleanState(2154 + 1956 + 4396 + 2248);
            this.field_179043_c = 0.0f;
            this.field_179041_d = 0.0f;
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
                if (2 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class TexGenState
    {
        public TexGenCoord field_179063_c;
        public TexGenCoord field_179062_b;
        private static final String[] I;
        public TexGenCoord field_179061_d;
        private static final String __OBFID;
        public TexGenCoord field_179064_a;
        
        static {
            I();
            __OBFID = TexGenState.I["".length()];
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(" \u0019=aiSePdmS", "cUbQY");
        }
        
        TexGenState(final GlStateManager$1 glStateManager$1) {
            this();
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
                if (4 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private TexGenState() {
            this.field_179064_a = new TexGenCoord(3025 + 2399 - 4101 + 6869, 1710 + 1983 - 3640 + 3115);
            this.field_179062_b = new TexGenCoord(2190 + 7022 - 3225 + 2206, 2714 + 80 - 154 + 529);
            this.field_179063_c = new TexGenCoord(7404 + 2170 - 3498 + 2118, 99 + 2211 - 620 + 1480);
            this.field_179061_d = new TexGenCoord(7551 + 4242 - 7488 + 3890, 1651 + 2041 - 924 + 403);
        }
    }
}
