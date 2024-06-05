package net;

import net.minecraft.client.stream.ChatController;
import net.minecraft.client.stream.ChatController$ChatState;
import tv.twitch.ErrorCode;
import tv.twitch.chat.IChatAPIListener;

class agv implements IChatAPIListener {
   final ChatController a;

   agv(ChatController var1) {
      this.a = var1;
   }

   public void chatInitializationCallback(ErrorCode var1) {
      if(ErrorCode.succeeded(var1)) {
         this.a.field_153008_f.setMessageFlushInterval(this.a.field_175993_n);
         this.a.field_153008_f.setUserChangeEventInterval(this.a.field_175994_o);
         this.a.func_153001_r();
         this.a.func_175985_a(ChatController$ChatState.Initialized);
      } else {
         this.a.func_175985_a(ChatController$ChatState.Uninitialized);
      }

      try {
         if(this.a.field_153003_a != null) {
            this.a.field_153003_a.func_176023_d(var1);
         }
      } catch (Exception var3) {
         this.a.func_152995_h(var3.toString());
      }

   }

   public void chatShutdownCallback(ErrorCode var1) {
      if(ErrorCode.succeeded(var1)) {
         ErrorCode var2 = this.a.field_175992_e.shutdown();
         if(ErrorCode.failed(var2)) {
            String var3 = ErrorCode.getString(var2);
            this.a.func_152995_h(String.format("Error shutting down the Twitch sdk: %s", new Object[]{var3}));
         }

         this.a.func_175985_a(ChatController$ChatState.Uninitialized);
      } else {
         this.a.func_175985_a(ChatController$ChatState.Initialized);
         this.a.func_152995_h(String.format("Error shutting down Twith chat: %s", new Object[]{var1}));
      }

      try {
         if(this.a.field_153003_a != null) {
            this.a.field_153003_a.func_176022_e(var1);
         }
      } catch (Exception var4) {
         this.a.func_152995_h(var4.toString());
      }

   }

   public void chatEmoticonDataDownloadCallback(ErrorCode var1) {
      if(ErrorCode.succeeded(var1)) {
         this.a.func_152988_s();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
