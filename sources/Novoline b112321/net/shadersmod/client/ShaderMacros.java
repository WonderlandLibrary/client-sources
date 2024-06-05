package net.shadersmod.client;

import net.aNh;
import net.minecraft.util.Util$EnumOS;
import net.optifine.Config;
import net.shadersmod.client.ShaderMacros$1;
import net.shadersmod.client.ShaderOption;

public class ShaderMacros {
   private static String PREFIX_MACRO = "MC_";
   public static final String u = "MC_VERSION";
   public static final String s = "MC_GL_VERSION";
   public static final String h = "MC_GLSL_VERSION";
   public static final String f = "MC_OS_WINDOWS";
   public static final String b = "MC_OS_MAC";
   public static final String n = "MC_OS_LINUX";
   public static final String e = "MC_OS_OTHER";
   public static final String t = "MC_GL_VENDOR_ATI";
   public static final String i = "MC_GL_VENDOR_INTEL";
   public static final String q = "MC_GL_VENDOR_NVIDIA";
   public static final String a = "MC_GL_VENDOR_XORG";
   public static final String g = "MC_GL_VENDOR_OTHER";
   public static final String c = "MC_GL_RENDERER_RADEON";
   public static final String p = "MC_GL_RENDERER_GEFORCE";
   public static final String o = "MC_GL_RENDERER_QUADRO";
   public static final String r = "MC_GL_RENDERER_INTEL";
   public static final String k = "MC_GL_RENDERER_GALLIUM";
   public static final String l = "MC_GL_RENDERER_MESA";
   public static final String d = "MC_GL_RENDERER_OTHER";
   private static String[] extensionMacros;

   public static String getOs() {
      Util$EnumOS var0 = aNh.a();
      switch(ShaderMacros$1.$SwitchMap$net$minecraft$util$Util$EnumOS[var0.ordinal()]) {
      case 1:
         return "MC_OS_WINDOWS";
      case 2:
         return "MC_OS_MAC";
      case 3:
         return "MC_OS_LINUX";
      default:
         return "MC_OS_OTHER";
      }
   }

   public static String getVendor() {
      ShaderOption.p();
      String var1 = Config.openGlVendor;
      if(var1 == null) {
         return "MC_GL_VENDOR_OTHER";
      } else {
         var1 = var1.toLowerCase();
         return var1.startsWith("ati")?"MC_GL_VENDOR_ATI":(var1.startsWith("intel")?"MC_GL_VENDOR_INTEL":(var1.startsWith("nvidia")?"MC_GL_VENDOR_NVIDIA":(var1.startsWith("x.org")?"MC_GL_VENDOR_XORG":"MC_GL_VENDOR_OTHER")));
      }
   }

   public static String getRenderer() {
      ShaderOption.p();
      String var1 = Config.openGlRenderer;
      if(var1 == null) {
         return "MC_GL_RENDERER_OTHER";
      } else {
         var1 = var1.toLowerCase();
         return var1.startsWith("amd")?"MC_GL_RENDERER_RADEON":(var1.startsWith("ati")?"MC_GL_RENDERER_RADEON":(var1.startsWith("radeon")?"MC_GL_RENDERER_RADEON":(var1.startsWith("gallium")?"MC_GL_RENDERER_GALLIUM":(var1.startsWith("intel")?"MC_GL_RENDERER_INTEL":(var1.startsWith("geforce")?"MC_GL_RENDERER_GEFORCE":(var1.startsWith("nvidia")?"MC_GL_RENDERER_GEFORCE":(var1.startsWith("quadro")?"MC_GL_RENDERER_QUADRO":(var1.startsWith("nvs")?"MC_GL_RENDERER_QUADRO":(var1.startsWith("mesa")?"MC_GL_RENDERER_MESA":"MC_GL_RENDERER_OTHER")))))))));
      }
   }

   public static String getPrefixMacro() {
      return PREFIX_MACRO;
   }

   public static String[] getExtensions() {
      String[] var0 = ShaderOption.p();
      if(extensionMacros == null) {
         String[] var1 = Config.getOpenGlExtensions();
         String[] var2 = new String[var1.length];
         int var3 = 0;
         if(var3 < var1.length) {
            var2[var3] = PREFIX_MACRO + var1[var3];
            ++var3;
         }

         extensionMacros = var2;
      }

      return extensionMacros;
   }
}
