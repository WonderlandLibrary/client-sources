package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.chat.ChatUserInfo;

public interface IStream {
   boolean func_152913_F();

   void func_176026_a(Metadata var1, long var2, long var4);

   boolean func_152927_B();

   ChatUserInfo func_152926_a(String var1);

   void func_152914_u();

   int func_152920_A();

   IngestServerTester func_152932_y();

   boolean func_152928_D();

   boolean isPaused();

   void func_152915_s();

   void func_152933_r();

   boolean func_152924_m();

   void func_152909_x();

   IngestServer[] func_152925_v();

   void func_152922_k();

   boolean func_152936_l();

   boolean func_152934_n();

   void func_152931_p();

   boolean func_152929_G();

   void func_152930_t();

   void func_152911_a(Metadata var1, long var2);

   void func_152910_a(boolean var1);

   boolean func_152908_z();

   void func_152916_q();

   void func_152935_j();

   ErrorCode func_152912_E();

   IStream.AuthFailureReason func_152918_H();

   void func_152917_b(String var1);

   void shutdownStream();

   String func_152921_C();

   public static enum AuthFailureReason {
      private static final String __OBFID = "CL_00001813";
      private static final IStream.AuthFailureReason[] ENUM$VALUES = new IStream.AuthFailureReason[]{ERROR, INVALID_TOKEN};
      ERROR("ERROR", 0),
      INVALID_TOKEN("INVALID_TOKEN", 1);

      private static final IStream.AuthFailureReason[] $VALUES = new IStream.AuthFailureReason[]{ERROR, INVALID_TOKEN};

      private AuthFailureReason(String var3, int var4) {
      }
   }
}
