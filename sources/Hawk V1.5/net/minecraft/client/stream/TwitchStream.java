package net.minecraft.client.stream;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.lwjgl.opengl.GL11;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.VideoParams;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

public class TwitchStream implements BroadcastController.BroadcastListener, IngestServerTester.IngestTestListener, ChatController.ChatListener, IStream {
   public static final Marker field_152949_a = MarkerManager.getMarker("STREAM");
   private IStream.AuthFailureReason field_152964_p;
   private final ChatController field_152952_d;
   private boolean field_152957_i;
   private String field_176029_e;
   private boolean field_152960_l = false;
   private final IChatComponent field_152954_f = new ChatComponentText("Twitch");
   private int field_152958_j = 30;
   private boolean field_152962_n;
   private boolean field_152961_m;
   private static final Logger field_152950_b = LogManager.getLogger();
   private boolean field_152963_o;
   private long field_152959_k = 0L;
   private final Map field_152955_g = Maps.newHashMap();
   private static boolean field_152965_q;
   private static final String __OBFID = "CL_00001812";
   private final Minecraft field_152953_e;
   private final BroadcastController broadcastController;
   private Framebuffer field_152956_h;

   public boolean func_152908_z() {
      return this.broadcastController.isIngestTesting();
   }

   public boolean func_152927_B() {
      return this.field_176029_e != null && this.field_176029_e.equals(this.broadcastController.func_152843_l().name);
   }

   public void func_176021_d() {
   }

   public TwitchStream(Minecraft var1, Property var2) {
      this.field_152964_p = IStream.AuthFailureReason.ERROR;
      this.field_152953_e = var1;
      this.broadcastController = new BroadcastController();
      this.field_152952_d = new ChatController();
      this.broadcastController.func_152841_a(this);
      this.field_152952_d.func_152990_a(this);
      this.broadcastController.func_152842_a("nmt37qblda36pvonovdkbopzfzw3wlq");
      this.field_152952_d.func_152984_a("nmt37qblda36pvonovdkbopzfzw3wlq");
      this.field_152954_f.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
      if (var2 != null && !Strings.isNullOrEmpty(var2.getValue()) && OpenGlHelper.framebufferSupported) {
         Thread var3 = new Thread(this, "Twitch authenticator", var2) {
            final TwitchStream this$0;
            private static final String __OBFID = "CL_00001811";
            private final Property val$p_i46057_2_;

            public void run() {
               try {
                  URL var1 = new URL(String.valueOf((new StringBuilder("https://api.twitch.tv/kraken?oauth_token=")).append(URLEncoder.encode(this.val$p_i46057_2_.getValue(), "UTF-8"))));
                  String var2 = HttpUtil.get(var1);
                  JsonObject var3 = JsonUtils.getElementAsJsonObject((new JsonParser()).parse(var2), "Response");
                  JsonObject var4 = JsonUtils.getJsonObject(var3, "token");
                  if (JsonUtils.getJsonObjectBooleanFieldValue(var4, "valid")) {
                     String var5 = JsonUtils.getJsonObjectStringFieldValue(var4, "user_name");
                     TwitchStream.access$0().debug(TwitchStream.field_152949_a, "Authenticated with twitch; username is {}", new Object[]{var5});
                     AuthToken var6 = new AuthToken();
                     var6.data = this.val$p_i46057_2_.getValue();
                     TwitchStream.access$1(this.this$0).func_152818_a(var5, var6);
                     TwitchStream.access$2(this.this$0).func_152998_c(var5);
                     TwitchStream.access$2(this.this$0).func_152994_a(var6);
                     Runtime.getRuntime().addShutdownHook(new Thread(this, "Twitch shutdown hook") {
                        final <undefinedtype> this$1;
                        private static final String __OBFID = "CL_00001810";

                        public void run() {
                           null.access$0(this.this$1).shutdownStream();
                        }

                        {
                           this.this$1 = var1;
                        }
                     });
                     TwitchStream.access$1(this.this$0).func_152817_A();
                     TwitchStream.access$2(this.this$0).func_175984_n();
                  } else {
                     TwitchStream.access$3(this.this$0, IStream.AuthFailureReason.INVALID_TOKEN);
                     TwitchStream.access$0().error(TwitchStream.field_152949_a, "Given twitch access token is invalid");
                  }
               } catch (IOException var7) {
                  TwitchStream.access$3(this.this$0, IStream.AuthFailureReason.ERROR);
                  TwitchStream.access$0().error(TwitchStream.field_152949_a, "Could not authenticate with twitch", var7);
               }

            }

            static TwitchStream access$0(Object var0) {
               return var0.this$0;
            }

            {
               this.this$0 = var1;
               this.val$p_i46057_2_ = var3;
            }
         };
         var3.setDaemon(true);
         var3.start();
      }

   }

   public void func_152930_t() {
      GameSettings var1 = this.field_152953_e.gameSettings;
      VideoParams var2 = this.broadcastController.func_152834_a(func_152946_b(var1.streamKbps), func_152948_a(var1.streamFps), func_152947_c(var1.streamBytesPerPixel), (float)this.field_152953_e.displayWidth / (float)this.field_152953_e.displayHeight);
      switch(var1.streamCompression) {
      case 0:
         var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
         break;
      case 1:
         var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
         break;
      case 2:
         var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
      }

      if (this.field_152956_h == null) {
         this.field_152956_h = new Framebuffer(var2.outputWidth, var2.outputHeight, false);
      } else {
         this.field_152956_h.createBindFramebuffer(var2.outputWidth, var2.outputHeight);
      }

      if (var1.streamPreferredServer != null && var1.streamPreferredServer.length() > 0) {
         IngestServer[] var3 = this.func_152925_v();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            IngestServer var6 = var3[var5];
            if (var6.serverUrl.equals(var1.streamPreferredServer)) {
               this.broadcastController.func_152824_a(var6);
               break;
            }
         }
      }

      this.field_152958_j = var2.targetFps;
      this.field_152957_i = var1.streamSendMetadata;
      this.broadcastController.func_152836_a(var2);
      field_152950_b.info(field_152949_a, "Streaming at {}/{} at {} kbps to {}", new Object[]{var2.outputWidth, var2.outputHeight, var2.maxKbps, this.broadcastController.func_152833_s().serverUrl});
      this.broadcastController.func_152828_a((String)null, "Minecraft", (String)null);
   }

   protected void func_152942_I() {
      ChatController.ChatState var1 = this.field_152952_d.func_153000_j();
      String var2 = this.broadcastController.func_152843_l().name;
      this.field_176029_e = var2;
      if (var1 != ChatController.ChatState.Initialized) {
         field_152950_b.warn("Invalid twitch chat state {}", new Object[]{var1});
      } else if (this.field_152952_d.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected) {
         this.field_152952_d.func_152986_d(var2);
      } else {
         field_152950_b.warn("Invalid twitch chat state {}", new Object[]{var1});
      }

   }

   public void func_152899_b() {
      this.func_152915_s();
      field_152950_b.info(field_152949_a, "Broadcast to Twitch has started");
   }

   static BroadcastController access$1(TwitchStream var0) {
      return var0.broadcastController;
   }

   public void func_152897_a(ErrorCode var1) {
      if (ErrorCode.succeeded(var1)) {
         field_152950_b.debug(field_152949_a, "Login attempt successful");
         this.field_152961_m = true;
      } else {
         field_152950_b.warn(field_152949_a, "Login attempt unsuccessful: {} (error code {})", new Object[]{ErrorCode.getString(var1), var1.getValue()});
         this.field_152961_m = false;
      }

   }

   public void func_152915_s() {
      if (this.func_152934_n()) {
         float var1 = this.field_152953_e.gameSettings.streamGameVolume;
         boolean var2 = this.field_152962_n || var1 <= 0.0F;
         this.broadcastController.func_152837_b(var2 ? 0.0F : var1);
         this.broadcastController.func_152829_a(this.func_152929_G() ? 0.0F : this.field_152953_e.gameSettings.streamMicVolume);
      }

   }

   public void func_152892_c(ErrorCode var1) {
      ChatComponentTranslation var2;
      if (var1 == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
         var2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
         var2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
         var2.getChatStyle().setUnderlined(true);
         ChatComponentTranslation var3 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[]{var2});
         var3.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
         this.field_152953_e.ingameGUI.getChatGUI().printChatMessage(var3);
      } else {
         var2 = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[]{ErrorCode.getString(var1)});
         var2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
         this.field_152953_e.ingameGUI.getChatGUI().printChatMessage(var2);
      }

   }

   public static float func_152947_c(float var0) {
      return 0.1F + var0 * 0.1F;
   }

   public void func_152909_x() {
      IngestServerTester var1 = this.broadcastController.func_152838_J();
      if (var1 != null) {
         var1.func_153042_a(this);
      }

   }

   public void func_152895_a() {
      field_152950_b.info(field_152949_a, "Logged out of twitch");
   }

   public void func_176016_c(String var1) {
   }

   public void func_176018_a(String var1, ChatUserInfo[] var2, ChatUserInfo[] var3, ChatUserInfo[] var4) {
      ChatUserInfo[] var5 = var3;
      int var6 = var3.length;

      int var7;
      ChatUserInfo var8;
      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var5[var7];
         this.field_152955_g.remove(var8.displayName);
      }

      var5 = var4;
      var6 = var4.length;

      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var5[var7];
         this.field_152955_g.put(var8.displayName, var8);
      }

      var5 = var2;
      var6 = var2.length;

      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var5[var7];
         this.field_152955_g.put(var8.displayName, var8);
      }

   }

   public void func_152914_u() {
      if (this.broadcastController.func_152819_E()) {
         field_152950_b.info(field_152949_a, "Stopped streaming to Twitch");
      } else {
         field_152950_b.warn(field_152949_a, "Could not stop streaming to Twitch");
      }

   }

   static void access$3(TwitchStream var0, IStream.AuthFailureReason var1) {
      var0.field_152964_p = var1;
   }

   public void func_152898_a(ErrorCode var1, GameInfo[] var2) {
   }

   public void func_152910_a(boolean var1) {
      this.field_152963_o = var1;
      this.func_152915_s();
   }

   public void func_176019_a(String var1, String var2) {
   }

   public void func_152891_a(BroadcastController.BroadcastState var1) {
      field_152950_b.debug(field_152949_a, "Broadcast state changed to {}", new Object[]{var1});
      if (var1 == BroadcastController.BroadcastState.Initialized) {
         this.broadcastController.func_152827_a(BroadcastController.BroadcastState.Authenticated);
      }

   }

   public boolean isPaused() {
      return this.broadcastController.isBroadcastPaused();
   }

   static Logger access$0() {
      return field_152950_b;
   }

   public ChatUserInfo func_152926_a(String var1) {
      return (ChatUserInfo)this.field_152955_g.get(var1);
   }

   public void func_152901_c() {
      field_152950_b.info(field_152949_a, "Broadcast to Twitch has stopped");
   }

   public void func_152900_a(ErrorCode var1, AuthToken var2) {
   }

   public void func_152933_r() {
      this.broadcastController.func_152854_G();
      this.field_152962_n = false;
      this.func_152915_s();
   }

   static ChatController access$2(TwitchStream var0) {
      return var0.field_152952_d;
   }

   public void func_152896_a(IngestList var1) {
   }

   public static int func_152948_a(float var0) {
      return MathHelper.floor_float(10.0F + var0 * 50.0F);
   }

   public void func_152916_q() {
      this.broadcastController.func_152847_F();
      this.field_152962_n = true;
      this.func_152915_s();
   }

   public void func_176026_a(Metadata var1, long var2, long var4) {
      if (this.func_152934_n() && this.field_152957_i) {
         long var6 = this.broadcastController.func_152844_x();
         String var8 = var1.func_152809_a();
         String var9 = var1.func_152806_b();
         long var10 = this.broadcastController.func_177946_b(var1.func_152810_c(), var6 + var2, var8, var9);
         if (var10 < 0L) {
            field_152950_b.warn(field_152949_a, "Could not send stream metadata sequence from {} to {}: {}", new Object[]{var6 + var2, var6 + var4, var1});
         } else if (this.broadcastController.func_177947_a(var1.func_152810_c(), var6 + var4, var10, var8, var9)) {
            field_152950_b.debug(field_152949_a, "Sent stream metadata sequence from {} to {}: {}", new Object[]{var6 + var2, var6 + var4, var1});
         } else {
            field_152950_b.warn(field_152949_a, "Half-sent stream metadata sequence from {} to {}: {}", new Object[]{var6 + var2, var6 + var4, var1});
         }
      }

   }

   public void func_176023_d(ErrorCode var1) {
      if (ErrorCode.failed(var1)) {
         field_152950_b.error(field_152949_a, "Chat failed to initialize");
      }

   }

   public void func_152907_a(IngestServerTester var1, IngestServerTester.IngestTestState var2) {
      field_152950_b.debug(field_152949_a, "Ingest test state changed to {}", new Object[]{var2});
      if (var2 == IngestServerTester.IngestTestState.Finished) {
         this.field_152960_l = true;
      }

   }

   public void func_152893_b(ErrorCode var1) {
      field_152950_b.warn(field_152949_a, "Issue submitting frame: {} (Error code {})", new Object[]{ErrorCode.getString(var1), var1.getValue()});
      this.field_152953_e.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(String.valueOf((new StringBuilder("Issue streaming frame: ")).append(var1).append(" (").append(ErrorCode.getString(var1)).append(")"))), 2);
   }

   public static int func_152946_b(float var0) {
      return MathHelper.floor_float(230.0F + var0 * 3270.0F);
   }

   public ErrorCode func_152912_E() {
      return !field_152965_q ? ErrorCode.TTV_EC_OS_TOO_OLD : this.broadcastController.func_152852_P();
   }

   private void func_176027_a(String var1, ChatRawMessage var2) {
      ChatUserInfo var3 = (ChatUserInfo)this.field_152955_g.get(var1);
      if (var3 == null) {
         var3 = new ChatUserInfo();
         var3.displayName = var1;
         this.field_152955_g.put(var1, var3);
      }

      var3.subscriptions = var2.subscriptions;
      var3.modes = var2.modes;
      var3.nameColorARGB = var2.nameColorARGB;
   }

   public void func_152931_p() {
      if (this.broadcastController.func_152830_D()) {
         field_152950_b.debug(field_152949_a, "Requested commercial from Twitch");
      } else {
         field_152950_b.warn(field_152949_a, "Could not request commercial from Twitch");
      }

   }

   public boolean func_152929_G() {
      boolean var1 = this.field_152953_e.gameSettings.streamMicToggleBehavior == 1;
      return this.field_152962_n || this.field_152953_e.gameSettings.streamMicVolume <= 0.0F || var1 != this.field_152963_o;
   }

   public int func_152920_A() {
      return this.func_152934_n() ? this.broadcastController.func_152816_j().viewers : 0;
   }

   public void func_176024_e() {
   }

   public String func_152921_C() {
      return this.field_176029_e;
   }

   public IngestServer[] func_152925_v() {
      return this.broadcastController.func_152855_t().getServers();
   }

   public void func_152917_b(String var1) {
      this.field_152952_d.func_175986_a(this.field_176029_e, var1);
   }

   public void func_180606_a(String var1) {
      field_152950_b.debug(field_152949_a, "Chat connected");
   }

   public boolean func_152924_m() {
      return this.broadcastController.func_152857_n();
   }

   public boolean func_152928_D() {
      return field_152965_q && this.broadcastController.func_152858_b();
   }

   public boolean func_152934_n() {
      return this.broadcastController.isBroadcasting();
   }

   public void func_152922_k() {
      if (this.broadcastController.isBroadcasting() && !this.broadcastController.isBroadcastPaused()) {
         long var1 = System.nanoTime();
         long var3 = (long)(1000000000 / this.field_152958_j);
         long var5 = var1 - this.field_152959_k;
         boolean var7 = var5 >= var3;
         if (var7) {
            FrameBuffer var8 = this.broadcastController.func_152822_N();
            Framebuffer var9 = this.field_152953_e.getFramebuffer();
            this.field_152956_h.bindFramebuffer(true);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0D, (double)this.field_152956_h.framebufferWidth, (double)this.field_152956_h.framebufferHeight, 0.0D, 1000.0D, 3000.0D);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.viewport(0, 0, this.field_152956_h.framebufferWidth, this.field_152956_h.framebufferHeight);
            GlStateManager.func_179098_w();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            float var10 = (float)this.field_152956_h.framebufferWidth;
            float var11 = (float)this.field_152956_h.framebufferHeight;
            float var12 = (float)var9.framebufferWidth / (float)var9.framebufferTextureWidth;
            float var13 = (float)var9.framebufferHeight / (float)var9.framebufferTextureHeight;
            var9.bindFramebufferTexture();
            GL11.glTexParameterf(3553, 10241, 9729.0F);
            GL11.glTexParameterf(3553, 10240, 9729.0F);
            Tessellator var14 = Tessellator.getInstance();
            WorldRenderer var15 = var14.getWorldRenderer();
            var15.startDrawingQuads();
            var15.addVertexWithUV(0.0D, (double)var11, 0.0D, 0.0D, (double)var13);
            var15.addVertexWithUV((double)var10, (double)var11, 0.0D, (double)var12, (double)var13);
            var15.addVertexWithUV((double)var10, 0.0D, 0.0D, (double)var12, 0.0D);
            var15.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
            var14.draw();
            var9.unbindFramebufferTexture();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            this.broadcastController.func_152846_a(var8);
            this.field_152956_h.unbindFramebuffer();
            this.broadcastController.func_152859_b(var8);
            this.field_152959_k = var1;
         }
      }

   }

   public boolean func_152936_l() {
      return this.broadcastController.func_152849_q();
   }

   public void func_176022_e(ErrorCode var1) {
      if (ErrorCode.failed(var1)) {
         field_152950_b.error(field_152949_a, "Chat failed to shutdown");
      }

   }

   public void func_152894_a(StreamInfo var1) {
      field_152950_b.debug(field_152949_a, "Stream info updated; {} viewers on stream ID {}", new Object[]{var1.viewers, var1.streamId});
   }

   public void func_152911_a(Metadata var1, long var2) {
      if (this.func_152934_n() && this.field_152957_i) {
         long var4 = this.broadcastController.func_152844_x();
         if (!this.broadcastController.func_152840_a(var1.func_152810_c(), var4 + var2, var1.func_152809_a(), var1.func_152806_b())) {
            field_152950_b.warn(field_152949_a, "Couldn't send stream metadata action at {}: {}", new Object[]{var4 + var2, var1});
         } else {
            field_152950_b.debug(field_152949_a, "Sent stream metadata action at {}: {}", new Object[]{var4 + var2, var1});
         }
      }

   }

   public IngestServerTester func_152932_y() {
      return this.broadcastController.isReady();
   }

   public void func_176017_a(ChatController.ChatState var1) {
   }

   public void func_180607_b(String var1) {
      field_152950_b.debug(field_152949_a, "Chat disconnected");
      this.field_152955_g.clear();
   }

   public void func_176020_d(String var1) {
   }

   public boolean func_152913_F() {
      return this.field_152961_m;
   }

   public void func_180605_a(String var1, ChatRawMessage[] var2) {
      ChatRawMessage[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ChatRawMessage var6 = var3[var5];
         this.func_176027_a(var6.userName, var6);
         if (this.func_176028_a(var6.modes, var6.subscriptions, this.field_152953_e.gameSettings.streamChatUserFilter)) {
            ChatComponentText var7 = new ChatComponentText(var6.userName);
            ChatComponentTranslation var8 = new ChatComponentTranslation(String.valueOf((new StringBuilder("chat.stream.")).append(var6.action ? "emote" : "text")), new Object[]{this.field_152954_f, var7, EnumChatFormatting.getTextWithoutFormattingCodes(var6.message)});
            if (var6.action) {
               var8.getChatStyle().setItalic(true);
            }

            ChatComponentText var9 = new ChatComponentText("");
            var9.appendSibling(new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));
            Iterator var10 = GuiTwitchUserMode.func_152328_a(var6.modes, var6.subscriptions, (IStream)null).iterator();

            while(var10.hasNext()) {
               IChatComponent var11 = (IChatComponent)var10.next();
               var9.appendText("\n");
               var9.appendSibling(var11);
            }

            var7.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, var9));
            var7.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, var6.userName));
            this.field_152953_e.ingameGUI.getChatGUI().printChatMessage(var8);
         }
      }

   }

   public void shutdownStream() {
      field_152950_b.debug(field_152949_a, "Shutdown streaming");
      this.broadcastController.statCallback();
      this.field_152952_d.func_175988_p();
   }

   public void func_152935_j() {
      int var1 = this.field_152953_e.gameSettings.streamChatEnabled;
      boolean var2 = this.field_176029_e != null && this.field_152952_d.func_175990_d(this.field_176029_e);
      boolean var3 = this.field_152952_d.func_153000_j() == ChatController.ChatState.Initialized && (this.field_176029_e == null || this.field_152952_d.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected);
      if (var1 == 2) {
         if (var2) {
            field_152950_b.debug(field_152949_a, "Disconnecting from twitch chat per user options");
            this.field_152952_d.func_175991_l(this.field_176029_e);
         }
      } else if (var1 == 1) {
         if (var3 && this.broadcastController.func_152849_q()) {
            field_152950_b.debug(field_152949_a, "Connecting to twitch chat per user options");
            this.func_152942_I();
         }
      } else if (var1 == 0) {
         if (var2 && !this.func_152934_n()) {
            field_152950_b.debug(field_152949_a, "Disconnecting from twitch chat as user is no longer streaming");
            this.field_152952_d.func_175991_l(this.field_176029_e);
         } else if (var3 && this.func_152934_n()) {
            field_152950_b.debug(field_152949_a, "Connecting to twitch chat as user is streaming");
            this.func_152942_I();
         }
      }

      this.broadcastController.func_152821_H();
      this.field_152952_d.func_152997_n();
   }

   static {
      try {
         if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            System.loadLibrary("avutil-ttv-51");
            System.loadLibrary("swresample-ttv-0");
            System.loadLibrary("libmp3lame-ttv");
            if (System.getProperty("os.arch").contains("64")) {
               System.loadLibrary("libmfxsw64");
            } else {
               System.loadLibrary("libmfxsw32");
            }
         }

         field_152965_q = true;
      } catch (Throwable var1) {
         field_152965_q = false;
      }

   }

   private boolean func_176028_a(Set var1, Set var2, int var3) {
      return var1.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED) ? false : (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) ? true : (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) ? true : (var1.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF) ? true : (var3 == 0 ? true : (var3 == 1 ? var2.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) : false)))));
   }

   public void func_176025_a(String var1, ChatTokenizedMessage[] var2) {
   }

   public IStream.AuthFailureReason func_152918_H() {
      return this.field_152964_p;
   }
}
