package net.minecraft.client.settings;

import net.minecraft.client.settings.GameSettings$Options;

final class GameSettings$GameSettings$2 {
   static final int[] field_151477_a = new int[GameSettings$Options.values().length];
   private static final String b = "CL_00000652";

   static {
      try {
         field_151477_a[GameSettings$Options.INVERT_MOUSE.ordinal()] = 1;
      } catch (NoSuchFieldError var18) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.VIEW_BOBBING.ordinal()] = 2;
      } catch (NoSuchFieldError var17) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.ANAGLYPH.ordinal()] = 3;
      } catch (NoSuchFieldError var16) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.FBO_ENABLE.ordinal()] = 4;
      } catch (NoSuchFieldError var15) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.CHAT_COLOR.ordinal()] = 5;
      } catch (NoSuchFieldError var14) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.CHAT_LINKS.ordinal()] = 6;
      } catch (NoSuchFieldError var13) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.CHAT_LINKS_PROMPT.ordinal()] = 7;
      } catch (NoSuchFieldError var12) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.SNOOPER_ENABLED.ordinal()] = 8;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.USE_FULLSCREEN.ordinal()] = 9;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.ENABLE_VSYNC.ordinal()] = 10;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.USE_VBO.ordinal()] = 11;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.TOUCHSCREEN.ordinal()] = 12;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.STREAM_SEND_METADATA.ordinal()] = 13;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.FORCE_UNICODE_FONT.ordinal()] = 14;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.BLOCK_ALTERNATIVES.ordinal()] = 15;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.REDUCED_DEBUG_INFO.ordinal()] = 16;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_151477_a[GameSettings$Options.ENTITY_SHADOWS.ordinal()] = 17;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
