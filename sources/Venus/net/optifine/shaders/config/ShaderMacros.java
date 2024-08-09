/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.Util;
import net.optifine.Config;
import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderMacro;

public class ShaderMacros {
    private static String PREFIX_MACRO = "MC_";
    public static final String MC_VERSION = "MC_VERSION";
    public static final String MC_GL_VERSION = "MC_GL_VERSION";
    public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
    public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
    public static final String MC_OS_MAC = "MC_OS_MAC";
    public static final String MC_OS_LINUX = "MC_OS_LINUX";
    public static final String MC_OS_OTHER = "MC_OS_OTHER";
    public static final String MC_GL_VENDOR_AMD = "MC_GL_VENDOR_AMD";
    public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
    public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
    public static final String MC_GL_VENDOR_MESA = "MC_GL_VENDOR_MESA";
    public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
    public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
    public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
    public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
    public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
    public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
    public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
    public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
    public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
    public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
    public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
    public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
    public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
    public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
    public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
    public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
    public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
    public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
    public static final String MC_ANISOTROPIC_FILTERING = "MC_ANISOTROPIC_FILTERING";
    public static final String MC_TEXTURE_FORMAT_ = "MC_TEXTURE_FORMAT_";
    private static ShaderMacro[] extensionMacros;
    private static ShaderMacro[] constantMacros;

    public static String getOs() {
        Util.OS oS = Util.getOSType();
        switch (1.$SwitchMap$net$minecraft$util$Util$OS[oS.ordinal()]) {
            case 1: {
                return MC_OS_WINDOWS;
            }
            case 2: {
                return MC_OS_MAC;
            }
            case 3: {
                return MC_OS_LINUX;
            }
        }
        return MC_OS_OTHER;
    }

    public static String getVendor() {
        String string = Config.openGlVersion;
        if (string != null && string.contains("Mesa")) {
            return MC_GL_VENDOR_MESA;
        }
        String string2 = Config.openGlVendor;
        if (string2 == null) {
            return MC_GL_VENDOR_OTHER;
        }
        if ((string2 = string2.toLowerCase()).startsWith("amd")) {
            return MC_GL_VENDOR_AMD;
        }
        if (string2.startsWith("ati")) {
            return MC_GL_VENDOR_ATI;
        }
        if (string2.startsWith("intel")) {
            return MC_GL_VENDOR_INTEL;
        }
        if (string2.startsWith("nvidia")) {
            return MC_GL_VENDOR_NVIDIA;
        }
        return string2.startsWith("x.org") ? MC_GL_VENDOR_XORG : MC_GL_VENDOR_OTHER;
    }

    public static String getRenderer() {
        String string = Config.openGlRenderer;
        if (string == null) {
            return MC_GL_RENDERER_OTHER;
        }
        if ((string = string.toLowerCase()).startsWith("amd")) {
            return MC_GL_RENDERER_RADEON;
        }
        if (string.startsWith("ati")) {
            return MC_GL_RENDERER_RADEON;
        }
        if (string.startsWith("radeon")) {
            return MC_GL_RENDERER_RADEON;
        }
        if (string.startsWith("gallium")) {
            return MC_GL_RENDERER_GALLIUM;
        }
        if (string.startsWith("intel")) {
            return MC_GL_RENDERER_INTEL;
        }
        if (string.startsWith("geforce")) {
            return MC_GL_RENDERER_GEFORCE;
        }
        if (string.startsWith("nvidia")) {
            return MC_GL_RENDERER_GEFORCE;
        }
        if (string.startsWith("quadro")) {
            return MC_GL_RENDERER_QUADRO;
        }
        if (string.startsWith("nvs")) {
            return MC_GL_RENDERER_QUADRO;
        }
        return string.startsWith("mesa") ? MC_GL_RENDERER_MESA : MC_GL_RENDERER_OTHER;
    }

    public static String getPrefixMacro() {
        return PREFIX_MACRO;
    }

    public static ShaderMacro[] getExtensions() {
        if (extensionMacros == null) {
            String[] stringArray = Config.getOpenGlExtensions();
            ShaderMacro[] shaderMacroArray = new ShaderMacro[stringArray.length];
            for (int i = 0; i < stringArray.length; ++i) {
                shaderMacroArray[i] = new ShaderMacro(PREFIX_MACRO + stringArray[i], "");
            }
            extensionMacros = shaderMacroArray;
        }
        return extensionMacros;
    }

    public static ShaderMacro[] getConstantMacros() {
        if (constantMacros == null) {
            ArrayList<ShaderMacro> arrayList = new ArrayList<ShaderMacro>();
            arrayList.addAll(Arrays.asList(ShaderMacros.getRenderStages()));
            constantMacros = arrayList.toArray(new ShaderMacro[arrayList.size()]);
        }
        return constantMacros;
    }

    private static ShaderMacro[] getRenderStages() {
        RenderStage[] renderStageArray = RenderStage.values();
        ShaderMacro[] shaderMacroArray = new ShaderMacro[renderStageArray.length];
        for (int i = 0; i < renderStageArray.length; ++i) {
            RenderStage renderStage = renderStageArray[i];
            shaderMacroArray[i] = new ShaderMacro(PREFIX_MACRO + "RENDER_STAGE_" + renderStage.name(), "" + renderStage.ordinal());
        }
        return shaderMacroArray;
    }

    public static String getFixedMacroLines() {
        StringBuilder stringBuilder = new StringBuilder();
        ShaderMacros.addMacroLine(stringBuilder, MC_VERSION, Config.getMinecraftVersionInt());
        ShaderMacros.addMacroLine(stringBuilder, "MC_GL_VERSION " + Config.getGlVersion().toInt());
        ShaderMacros.addMacroLine(stringBuilder, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
        ShaderMacros.addMacroLine(stringBuilder, ShaderMacros.getOs());
        ShaderMacros.addMacroLine(stringBuilder, ShaderMacros.getVendor());
        ShaderMacros.addMacroLine(stringBuilder, ShaderMacros.getRenderer());
        return stringBuilder.toString();
    }

    public static String getOptionMacroLines() {
        StringBuilder stringBuilder = new StringBuilder();
        if (Shaders.configAntialiasingLevel > 0) {
            ShaderMacros.addMacroLine(stringBuilder, MC_FXAA_LEVEL, Shaders.configAntialiasingLevel);
        }
        if (Shaders.configNormalMap) {
            ShaderMacros.addMacroLine(stringBuilder, MC_NORMAL_MAP);
        }
        if (Shaders.configSpecularMap) {
            ShaderMacros.addMacroLine(stringBuilder, MC_SPECULAR_MAP);
        }
        ShaderMacros.addMacroLine(stringBuilder, MC_RENDER_QUALITY, Shaders.configRenderResMul);
        ShaderMacros.addMacroLine(stringBuilder, MC_SHADOW_QUALITY, Shaders.configShadowResMul);
        ShaderMacros.addMacroLine(stringBuilder, MC_HAND_DEPTH, Shaders.configHandDepthMul);
        if (Shaders.isOldHandLight()) {
            ShaderMacros.addMacroLine(stringBuilder, MC_OLD_HAND_LIGHT);
        }
        if (Shaders.isOldLighting()) {
            ShaderMacros.addMacroLine(stringBuilder, MC_OLD_LIGHTING);
        }
        if (Config.isAnisotropicFiltering()) {
            ShaderMacros.addMacroLine(stringBuilder, MC_ANISOTROPIC_FILTERING, Config.getAnisotropicFilterLevel());
        }
        return stringBuilder.toString();
    }

    public static String getTextureMacroLines() {
        AtlasTexture atlasTexture = Config.getTextureMap();
        if (atlasTexture == null) {
            return "";
        }
        ITextureFormat iTextureFormat = atlasTexture.getTextureFormat();
        if (iTextureFormat == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = iTextureFormat.getMacroName();
        if (string != null) {
            ShaderMacros.addMacroLine(stringBuilder, MC_TEXTURE_FORMAT_ + string);
            String string2 = iTextureFormat.getMacroVersion();
            if (string2 != null) {
                ShaderMacros.addMacroLine(stringBuilder, MC_TEXTURE_FORMAT_ + string + "_" + string2);
            }
        }
        return stringBuilder.toString();
    }

    public static String[] getHeaderMacroLines() {
        String string = ShaderMacros.getFixedMacroLines() + ShaderMacros.getOptionMacroLines() + ShaderMacros.getTextureMacroLines();
        return Config.tokenize(string, "\n\r");
    }

    private static void addMacroLine(StringBuilder stringBuilder, String string, int n) {
        stringBuilder.append("#define ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append(n);
        stringBuilder.append("\n");
    }

    private static void addMacroLine(StringBuilder stringBuilder, String string, float f) {
        stringBuilder.append("#define ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append(f);
        stringBuilder.append("\n");
    }

    private static void addMacroLine(StringBuilder stringBuilder, String string) {
        stringBuilder.append("#define ");
        stringBuilder.append(string);
        stringBuilder.append("\n");
    }
}

