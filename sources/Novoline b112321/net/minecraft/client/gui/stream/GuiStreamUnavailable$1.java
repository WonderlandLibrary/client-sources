package net.minecraft.client.gui.stream;

import net.minecraft.client.gui.stream.GuiStreamUnavailable$Reason;
import net.minecraft.client.stream.IStream$AuthFailureReason;
import net.minecraft.util.Util$EnumOS;

// $FF: synthetic class
class GuiStreamUnavailable$1 {
   static final int[] $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason;
   static final int[] $SwitchMap$net$minecraft$util$Util$EnumOS;
   static final int[] $SwitchMap$net$minecraft$client$stream$IStream$AuthFailureReason = new int[IStream$AuthFailureReason.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$client$stream$IStream$AuthFailureReason[IStream$AuthFailureReason.INVALID_TOKEN.ordinal()] = 1;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IStream$AuthFailureReason[IStream$AuthFailureReason.ERROR.ordinal()] = 2;
      } catch (NoSuchFieldError var10) {
         ;
      }

      $SwitchMap$net$minecraft$util$Util$EnumOS = new int[Util$EnumOS.values().length];

      try {
         $SwitchMap$net$minecraft$util$Util$EnumOS[Util$EnumOS.WINDOWS.ordinal()] = 1;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$Util$EnumOS[Util$EnumOS.OSX.ordinal()] = 2;
      } catch (NoSuchFieldError var8) {
         ;
      }

      $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason = new int[GuiStreamUnavailable$Reason.values().length];

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.ACCOUNT_NOT_BOUND.ordinal()] = 1;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.FAILED_TWITCH_AUTH.ordinal()] = 2;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.ACCOUNT_NOT_MIGRATED.ordinal()] = 3;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.UNSUPPORTED_OS_MAC.ordinal()] = 4;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.UNKNOWN.ordinal()] = 5;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.LIBRARY_FAILURE.ordinal()] = 6;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[GuiStreamUnavailable$Reason.INITIALIZATION_FAILURE.ordinal()] = 7;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
