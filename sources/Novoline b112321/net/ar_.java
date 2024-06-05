package net;

import net.minecraft.client.stream.BroadcastController;
import net.minecraft.client.stream.BroadcastController$BroadcastListener;
import net.minecraft.client.stream.IngestServerTester;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.VideoParams;

public class ar_ {
   public static boolean a(BroadcastController var0, String var1, AuthToken var2) {
      return var0.func_152818_a(var1, var2);
   }

   public static boolean m(BroadcastController var0) {
      return var0.func_152817_A();
   }

   public static void a(BroadcastController var0, BroadcastController$BroadcastListener var1) {
      var0.func_152841_a(var1);
   }

   public static void a(BroadcastController var0, String var1) {
      var0.func_152842_a(var1);
   }

   public static void n(BroadcastController var0) {
      var0.statCallback();
   }

   public static boolean h(BroadcastController var0) {
      return var0.func_152849_q();
   }

   public static void i(BroadcastController var0) {
      var0.func_152821_H();
   }

   public static ChannelInfo k(BroadcastController var0) {
      return var0.getChannelInfo();
   }

   public static boolean d(BroadcastController var0) {
      return var0.isBroadcastPaused();
   }

   public static FrameBuffer l(BroadcastController var0) {
      return var0.func_152822_N();
   }

   public static void b(BroadcastController var0, FrameBuffer var1) {
      var0.captureFramebuffer(var1);
   }

   public static ErrorCode a(BroadcastController var0, FrameBuffer var1) {
      return var0.submitStreamFrame(var1);
   }

   public static long t(BroadcastController var0) {
      return var0.func_152844_x();
   }

   public static boolean a(BroadcastController var0, String var1, long var2, String var4, String var5) {
      return var0.func_152840_a(var1, var2, var4, var5);
   }

   public static long b(BroadcastController var0, String var1, long var2, String var4, String var5) {
      return var0.func_177946_b(var1, var2, var4, var5);
   }

   public static boolean a(BroadcastController var0, String var1, long var2, long var4, String var6, String var7) {
      return var0.func_177947_a(var1, var2, var4, var6, var7);
   }

   public static boolean j(BroadcastController var0) {
      return var0.requestCommercial();
   }

   public static boolean b(BroadcastController var0) {
      return var0.func_152847_F();
   }

   public static boolean q(BroadcastController var0) {
      return var0.func_152854_G();
   }

   public static void a(BroadcastController var0, float var1) {
      var0.setPlaybackDeviceVolume(var1);
   }

   public static void b(BroadcastController var0, float var1) {
      var0.setRecordingDeviceVolume(var1);
   }

   public static VideoParams a(BroadcastController var0, int var1, int var2, float var3, float var4) {
      return var0.func_152834_a(var1, var2, var3, var4);
   }

   public static void a(BroadcastController var0, IngestServer var1) {
      var0.func_152824_a(var1);
   }

   public static boolean a(BroadcastController var0, VideoParams var1) {
      return var0.func_152836_a(var1);
   }

   public static IngestServer g(BroadcastController var0) {
      return var0.func_152833_s();
   }

   public static boolean a(BroadcastController var0, String var1, String var2, String var3) {
      return var0.func_152828_a(var1, var2, var3);
   }

   public static boolean c(BroadcastController var0) {
      return var0.stopBroadcasting();
   }

   public static IngestList f(BroadcastController var0) {
      return var0.func_152855_t();
   }

   public static IngestServerTester a(BroadcastController var0) {
      return var0.func_152838_J();
   }

   public static IngestServerTester e(BroadcastController var0) {
      return var0.isReady();
   }

   public static boolean o(BroadcastController var0) {
      return var0.isIngestTesting();
   }

   public static StreamInfo s(BroadcastController var0) {
      return var0.getStreamInfo();
   }

   public static boolean p(BroadcastController var0) {
      return var0.func_152858_b();
   }

   public static ErrorCode r(BroadcastController var0) {
      return var0.getErrorCode();
   }
}
