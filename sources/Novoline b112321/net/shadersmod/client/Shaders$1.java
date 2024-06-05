package net.shadersmod.client;

import net.minecraft.util.EnumWorldBlockLayer;
import net.shadersmod.client.EnumShaderOption;

// $FF: synthetic class
class Shaders$1 {
   static final int[] $SwitchMap$net$shadersmod$client$EnumShaderOption;
   static final int[] $SwitchMap$net$minecraft$util$EnumWorldBlockLayer = new int[EnumWorldBlockLayer.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$util$EnumWorldBlockLayer[EnumWorldBlockLayer.SOLID.ordinal()] = 1;
      } catch (NoSuchFieldError var22) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumWorldBlockLayer[EnumWorldBlockLayer.CUTOUT.ordinal()] = 2;
      } catch (NoSuchFieldError var21) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumWorldBlockLayer[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = 3;
      } catch (NoSuchFieldError var20) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$EnumWorldBlockLayer[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = 4;
      } catch (NoSuchFieldError var19) {
         ;
      }

      $SwitchMap$net$shadersmod$client$EnumShaderOption = new int[EnumShaderOption.values().length];

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.ANTIALIASING.ordinal()] = 1;
      } catch (NoSuchFieldError var18) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.NORMAL_MAP.ordinal()] = 2;
      } catch (NoSuchFieldError var17) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.SPECULAR_MAP.ordinal()] = 3;
      } catch (NoSuchFieldError var16) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.RENDER_RES_MUL.ordinal()] = 4;
      } catch (NoSuchFieldError var15) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_RES_MUL.ordinal()] = 5;
      } catch (NoSuchFieldError var14) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.HAND_DEPTH_MUL.ordinal()] = 6;
      } catch (NoSuchFieldError var13) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.CLOUD_SHADOW.ordinal()] = 7;
      } catch (NoSuchFieldError var12) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.OLD_HAND_LIGHT.ordinal()] = 8;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.OLD_LIGHTING.ordinal()] = 9;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADER_PACK.ordinal()] = 10;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TWEAK_BLOCK_DAMAGE.ordinal()] = 11;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.SHADOW_CLIP_FRUSTRUM.ordinal()] = 12;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_B.ordinal()] = 13;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_N.ordinal()] = 14;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MIN_FIL_S.ordinal()] = 15;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_B.ordinal()] = 16;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_N.ordinal()] = 17;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$shadersmod$client$EnumShaderOption[EnumShaderOption.TEX_MAG_FIL_S.ordinal()] = 18;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
