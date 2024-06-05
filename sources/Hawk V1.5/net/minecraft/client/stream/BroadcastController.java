package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ThreadSafeBoundList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.MessageLevel;
import tv.twitch.StandardCoreAPI;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.AudioDeviceType;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.DesktopStreamAPI;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.StatType;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.StreamInfoForSetting;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.VideoParams;

public class BroadcastController {
   private static final Logger logger = LogManager.getLogger();
   protected String field_152869_e = "";
   private ErrorCode field_152864_E;
   protected Core field_152872_h = null;
   protected final int field_152866_b = 3;
   protected VideoParams field_152881_q;
   protected StreamInfo field_152888_x;
   protected IngestServerTester field_152860_A;
   protected IStreamCallbacks field_177948_B;
   protected AuthToken field_152885_u;
   protected boolean field_152878_n = false;
   protected UserInfo field_152887_w;
   protected Stream field_152873_i = null;
   protected BroadcastController.BroadcastState broadcastState;
   protected BroadcastController.BroadcastListener field_152867_c = null;
   protected boolean field_152876_l = false;
   protected ChannelInfo channelInfo;
   private static final ThreadSafeBoundList field_152862_C = new ThreadSafeBoundList(String.class, 50);
   protected final int field_152865_a = 30;
   protected String field_152868_d = "";
   protected List field_152874_j = Lists.newArrayList();
   protected IStatCallbacks field_177949_C;
   protected IngestServer field_152884_t;
   private String field_152863_D = null;
   protected ArchivingState field_152889_y;
   protected boolean field_152877_m = false;
   protected String field_152870_f = "";
   protected AudioParams field_152882_r;
   private static final String __OBFID = "CL_00001822";
   protected IngestList field_152883_s;
   protected String field_152880_p;
   protected List field_152875_k = Lists.newArrayList();
   protected long field_152890_z;
   protected boolean field_152871_g = true;

   protected PixelFormat func_152826_z() {
      return PixelFormat.TTV_PF_RGBA;
   }

   public ErrorCode func_152852_P() {
      return this.field_152864_E;
   }

   protected void func_152835_I() {
      long var1 = System.nanoTime();
      long var3 = (var1 - this.field_152890_z) / 1000000000L;
      if (var3 >= 30L) {
         this.field_152890_z = var1;
         ErrorCode var5 = this.field_152873_i.getStreamInfo(this.field_152885_u, this.field_152880_p);
         if (ErrorCode.failed(var5)) {
            String var6 = ErrorCode.getString(var5);
            this.func_152820_d(String.format("Error in TTV_GetStreamInfo: %s", var6));
         }
      }

   }

   public IngestList func_152855_t() {
      return this.field_152883_s;
   }

   public boolean func_152817_A() {
      if (this.field_152876_l) {
         return false;
      } else {
         this.field_152873_i.setStreamCallbacks(this.field_177948_B);
         ErrorCode var1 = this.field_152872_h.initialize(this.field_152868_d, System.getProperty("java.library.path"));
         if (!this.func_152853_a(var1)) {
            this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
            this.field_152864_E = var1;
            return false;
         } else {
            var1 = this.field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
            if (!this.func_152853_a(var1)) {
               this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
               this.field_152872_h.shutdown();
               this.field_152864_E = var1;
               return false;
            } else if (ErrorCode.succeeded(var1)) {
               this.field_152876_l = true;
               this.func_152827_a(BroadcastController.BroadcastState.Initialized);
               return true;
            } else {
               this.field_152864_E = var1;
               this.field_152872_h.shutdown();
               return false;
            }
         }
      }
   }

   public boolean isIngestTesting() {
      return this.broadcastState == BroadcastController.BroadcastState.IngestTesting;
   }

   public boolean func_152858_b() {
      return this.field_152876_l;
   }

   public boolean func_152819_E() {
      if (!this.isBroadcasting()) {
         return false;
      } else {
         ErrorCode var1 = this.field_152873_i.stop(true);
         if (ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152820_d(String.format("Error while stopping the broadcast: %s", var2));
            return false;
         } else {
            this.func_152827_a(BroadcastController.BroadcastState.Stopping);
            return ErrorCode.succeeded(var1);
         }
      }
   }

   public boolean func_152849_q() {
      return this.field_152877_m;
   }

   public VideoParams func_152834_a(int var1, int var2, float var3, float var4) {
      int[] var5 = this.field_152873_i.getMaxResolution(var1, var2, var3, var4);
      VideoParams var6 = new VideoParams();
      var6.maxKbps = var1;
      var6.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
      var6.pixelFormat = this.func_152826_z();
      var6.targetFps = var2;
      var6.outputWidth = var5[0];
      var6.outputHeight = var5[1];
      var6.disableAdaptiveBitrate = false;
      var6.verticalFlip = false;
      return var6;
   }

   protected boolean func_152823_L() {
      for(int var1 = 0; var1 < 3; ++var1) {
         FrameBuffer var2 = this.field_152873_i.allocateFrameBuffer(this.field_152881_q.outputWidth * this.field_152881_q.outputHeight * 4);
         if (!var2.getIsValid()) {
            this.func_152820_d(String.format("Error while allocating frame buffer"));
            return false;
         }

         this.field_152874_j.add(var2);
         this.field_152875_k.add(var2);
      }

      return true;
   }

   public IngestServerTester func_152838_J() {
      if (this.func_152857_n() && this.field_152883_s != null) {
         if (this.isIngestTesting()) {
            return null;
         } else {
            this.field_152860_A = new IngestServerTester(this.field_152873_i, this.field_152883_s);
            this.field_152860_A.func_176004_j();
            this.func_152827_a(BroadcastController.BroadcastState.IngestTesting);
            return this.field_152860_A;
         }
      } else {
         return null;
      }
   }

   protected boolean func_152853_a(ErrorCode var1) {
      if (ErrorCode.failed(var1)) {
         this.func_152820_d(ErrorCode.getString(var1));
         return false;
      } else {
         return true;
      }
   }

   public StreamInfo func_152816_j() {
      return this.field_152888_x;
   }

   public boolean func_152845_C() {
      if (this.isIngestTesting()) {
         return false;
      } else {
         if (this.isBroadcasting()) {
            this.field_152873_i.stop(false);
         }

         this.field_152880_p = "";
         this.field_152885_u = new AuthToken();
         if (!this.field_152877_m) {
            return false;
         } else {
            this.field_152877_m = false;
            if (!this.field_152878_n) {
               try {
                  if (this.field_152867_c != null) {
                     this.field_152867_c.func_152895_a();
                  }
               } catch (Exception var2) {
                  this.func_152820_d(var2.toString());
               }
            }

            this.func_152827_a(BroadcastController.BroadcastState.Initialized);
            return true;
         }
      }
   }

   public boolean func_152847_F() {
      if (!this.isBroadcasting()) {
         return false;
      } else {
         ErrorCode var1 = this.field_152873_i.pauseVideo();
         if (ErrorCode.failed(var1)) {
            this.func_152819_E();
            String var2 = ErrorCode.getString(var1);
            this.func_152820_d(String.format("Error pausing stream: %s\n", var2));
         } else {
            this.func_152827_a(BroadcastController.BroadcastState.Paused);
         }

         return ErrorCode.succeeded(var1);
      }
   }

   public IngestServer func_152833_s() {
      return this.field_152884_t;
   }

   public long func_152844_x() {
      return this.field_152873_i.getStreamTime();
   }

   public ChannelInfo func_152843_l() {
      return this.channelInfo;
   }

   public IngestServerTester isReady() {
      return this.field_152860_A;
   }

   protected boolean func_152848_y() {
      return true;
   }

   public void func_152821_H() {
      if (this.field_152873_i != null && this.field_152876_l) {
         ErrorCode var1 = this.field_152873_i.pollTasks();
         this.func_152853_a(var1);
         if (this.isIngestTesting()) {
            this.field_152860_A.func_153041_j();
            if (this.field_152860_A.func_153032_e()) {
               this.field_152860_A = null;
               this.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
            }
         }

         String var2;
         switch(this.broadcastState) {
         case Authenticated:
            this.func_152827_a(BroadcastController.BroadcastState.LoggingIn);
            var1 = this.field_152873_i.login(this.field_152885_u);
            if (ErrorCode.failed(var1)) {
               var2 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_Login: %s\n", var2));
            }
            break;
         case LoggedIn:
            this.func_152827_a(BroadcastController.BroadcastState.FindingIngestServer);
            var1 = this.field_152873_i.getIngestServers(this.field_152885_u);
            if (ErrorCode.failed(var1)) {
               this.func_152827_a(BroadcastController.BroadcastState.LoggedIn);
               var2 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_GetIngestServers: %s\n", var2));
            }
            break;
         case ReceivedIngestServers:
            this.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
            var1 = this.field_152873_i.getUserInfo(this.field_152885_u);
            if (ErrorCode.failed(var1)) {
               var2 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_GetUserInfo: %s\n", var2));
            }

            this.func_152835_I();
            var1 = this.field_152873_i.getArchivingState(this.field_152885_u);
            if (ErrorCode.failed(var1)) {
               var2 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_GetArchivingState: %s\n", var2));
            }
         case Starting:
         case Stopping:
         case FindingIngestServer:
         case Authenticating:
         case Initialized:
         case Uninitialized:
         case IngestTesting:
         default:
            break;
         case Paused:
         case Broadcasting:
            this.func_152835_I();
         }
      }

   }

   protected void func_152827_a(BroadcastController.BroadcastState var1) {
      if (var1 != this.broadcastState) {
         this.broadcastState = var1;

         try {
            if (this.field_152867_c != null) {
               this.field_152867_c.func_152891_a(var1);
            }
         } catch (Exception var3) {
            this.func_152820_d(var3.toString());
         }
      }

   }

   public boolean isBroadcastPaused() {
      return this.broadcastState == BroadcastController.BroadcastState.Paused;
   }

   public long func_177946_b(String var1, long var2, String var4, String var5) {
      long var6 = this.field_152873_i.sendStartSpanMetaData(this.field_152885_u, var1, var2, var4, var5);
      if (var6 == -1L) {
         this.func_152820_d(String.format("Error in SendStartSpanMetaData\n"));
      }

      return var6;
   }

   public boolean func_152857_n() {
      return this.broadcastState == BroadcastController.BroadcastState.ReadyToBroadcast;
   }

   public BroadcastController() {
      this.broadcastState = BroadcastController.BroadcastState.Uninitialized;
      this.field_152880_p = null;
      this.field_152881_q = null;
      this.field_152882_r = null;
      this.field_152883_s = new IngestList(new IngestServer[0]);
      this.field_152884_t = null;
      this.field_152885_u = new AuthToken();
      this.channelInfo = new ChannelInfo();
      this.field_152887_w = new UserInfo();
      this.field_152888_x = new StreamInfo();
      this.field_152889_y = new ArchivingState();
      this.field_152890_z = 0L;
      this.field_152860_A = null;
      this.field_177948_B = new IStreamCallbacks(this) {
         private static final String __OBFID = "CL_00002375";
         final BroadcastController this$0;

         public void sendEndSpanMetaDataCallback(ErrorCode var1) {
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("sendEndSpanMetaDataCallback got failure: %s", var2));
            }

         }

         public void sendActionMetaDataCallback(ErrorCode var1) {
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("sendActionMetaDataCallback got failure: %s", var2));
            }

         }

         public void bufferUnlockCallback(long var1) {
            FrameBuffer var3 = FrameBuffer.lookupBuffer(var1);
            this.this$0.field_152875_k.add(var3);
         }

         public void startCallback(ErrorCode var1) {
            if (ErrorCode.succeeded(var1)) {
               try {
                  if (this.this$0.field_152867_c != null) {
                     this.this$0.field_152867_c.func_152899_b();
                  }
               } catch (Exception var4) {
                  this.this$0.func_152820_d(var4.toString());
               }

               this.this$0.func_152827_a(BroadcastController.BroadcastState.Broadcasting);
            } else {
               this.this$0.field_152881_q = null;
               this.this$0.field_152882_r = null;
               this.this$0.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);

               try {
                  if (this.this$0.field_152867_c != null) {
                     this.this$0.field_152867_c.func_152892_c(var1);
                  }
               } catch (Exception var3) {
                  this.this$0.func_152820_d(var3.toString());
               }

               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("startCallback got failure: %s", var2));
            }

         }

         public void setStreamInfoCallback(ErrorCode var1) {
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152832_e(String.format("SetStreamInfoCallback got failure: %s", var2));
            }

         }

         public void sendStartSpanMetaDataCallback(ErrorCode var1) {
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("sendStartSpanMetaDataCallback got failure: %s", var2));
            }

         }

         public void loginCallback(ErrorCode var1, ChannelInfo var2) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.channelInfo = var2;
               this.this$0.func_152827_a(BroadcastController.BroadcastState.LoggedIn);
               this.this$0.field_152877_m = true;
            } else {
               this.this$0.func_152827_a(BroadcastController.BroadcastState.Initialized);
               this.this$0.field_152877_m = false;
               String var3 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("LoginCallback got failure: %s", var3));
            }

            try {
               if (this.this$0.field_152867_c != null) {
                  this.this$0.field_152867_c.func_152897_a(var1);
               }
            } catch (Exception var4) {
               this.this$0.func_152820_d(var4.toString());
            }

         }

         public void requestAuthTokenCallback(ErrorCode var1, AuthToken var2) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.field_152885_u = var2;
               this.this$0.func_152827_a(BroadcastController.BroadcastState.Authenticated);
            } else {
               this.this$0.field_152885_u.data = "";
               this.this$0.func_152827_a(BroadcastController.BroadcastState.Initialized);
               String var3 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("RequestAuthTokenDoneCallback got failure: %s", var3));
            }

            try {
               if (this.this$0.field_152867_c != null) {
                  this.this$0.field_152867_c.func_152900_a(var1, var2);
               }
            } catch (Exception var4) {
               this.this$0.func_152820_d(var4.toString());
            }

         }

         public void getGameNameListCallback(ErrorCode var1, GameInfoList var2) {
            if (ErrorCode.failed(var1)) {
               String var3 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("GameNameListCallback got failure: %s", var3));
            }

            try {
               if (this.this$0.field_152867_c != null) {
                  this.this$0.field_152867_c.func_152898_a(var1, var2 == null ? new GameInfo[0] : var2.list);
               }
            } catch (Exception var4) {
               this.this$0.func_152820_d(var4.toString());
            }

         }

         public void getArchivingStateCallback(ErrorCode var1, ArchivingState var2) {
            this.this$0.field_152889_y = var2;
            if (ErrorCode.failed(var1)) {
            }

         }

         public void getIngestServersCallback(ErrorCode var1, IngestList var2) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.field_152883_s = var2;
               this.this$0.field_152884_t = this.this$0.field_152883_s.getDefaultServer();
               this.this$0.func_152827_a(BroadcastController.BroadcastState.ReceivedIngestServers);

               try {
                  if (this.this$0.field_152867_c != null) {
                     this.this$0.field_152867_c.func_152896_a(var2);
                  }
               } catch (Exception var4) {
                  this.this$0.func_152820_d(var4.toString());
               }
            } else {
               String var3 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("IngestListCallback got failure: %s", var3));
               this.this$0.func_152827_a(BroadcastController.BroadcastState.LoggingIn);
            }

         }

         {
            this.this$0 = var1;
         }

         public void stopCallback(ErrorCode var1) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.field_152881_q = null;
               this.this$0.field_152882_r = null;
               this.this$0.func_152831_M();

               try {
                  if (this.this$0.field_152867_c != null) {
                     this.this$0.field_152867_c.func_152901_c();
                  }
               } catch (Exception var3) {
                  this.this$0.func_152820_d(var3.toString());
               }

               if (this.this$0.field_152877_m) {
                  this.this$0.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
               } else {
                  this.this$0.func_152827_a(BroadcastController.BroadcastState.Initialized);
               }
            } else {
               this.this$0.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("stopCallback got failure: %s", var2));
            }

         }

         public void runCommercialCallback(ErrorCode var1) {
            if (ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.this$0.func_152832_e(String.format("RunCommercialCallback got failure: %s", var2));
            }

         }

         public void getStreamInfoCallback(ErrorCode var1, StreamInfo var2) {
            if (ErrorCode.succeeded(var1)) {
               this.this$0.field_152888_x = var2;

               try {
                  if (this.this$0.field_152867_c != null) {
                     this.this$0.field_152867_c.func_152894_a(var2);
                  }
               } catch (Exception var4) {
                  this.this$0.func_152820_d(var4.toString());
               }
            } else {
               String var3 = ErrorCode.getString(var1);
               this.this$0.func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", var3));
            }

         }

         public void getUserInfoCallback(ErrorCode var1, UserInfo var2) {
            this.this$0.field_152887_w = var2;
            if (ErrorCode.failed(var1)) {
               String var3 = ErrorCode.getString(var1);
               this.this$0.func_152820_d(String.format("UserInfoDoneCallback got failure: %s", var3));
            }

         }
      };
      this.field_177949_C = new IStatCallbacks(this) {
         final BroadcastController this$0;
         private static final String __OBFID = "CL_00002374";

         public void statCallback(StatType var1, long var2) {
         }

         {
            this.this$0 = var1;
         }
      };
      this.field_152872_h = Core.getInstance();
      if (Core.getInstance() == null) {
         this.field_152872_h = new Core(new StandardCoreAPI());
      }

      this.field_152873_i = new Stream(new DesktopStreamAPI());
   }

   public void func_152837_b(float var1) {
      this.field_152873_i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, var1);
   }

   public void func_152842_a(String var1) {
      this.field_152868_d = var1;
   }

   public boolean func_152828_a(String var1, String var2, String var3) {
      if (!this.field_152877_m) {
         return false;
      } else {
         if (var1 == null || var1.equals("")) {
            var1 = this.field_152880_p;
         }

         if (var2 == null) {
            var2 = "";
         }

         if (var3 == null) {
            var3 = "";
         }

         StreamInfoForSetting var4 = new StreamInfoForSetting();
         var4.streamTitle = var3;
         var4.gameName = var2;
         ErrorCode var5 = this.field_152873_i.setStreamInfo(this.field_152885_u, var1, var4);
         this.func_152853_a(var5);
         return ErrorCode.succeeded(var5);
      }
   }

   protected void func_152832_e(String var1) {
      field_152862_C.func_152757_a(String.valueOf((new StringBuilder("<Warning> ")).append(var1)));
      logger.warn(TwitchStream.field_152949_a, "[Broadcast controller] {}", new Object[]{var1});
   }

   public boolean isBroadcasting() {
      return this.broadcastState == BroadcastController.BroadcastState.Broadcasting || this.broadcastState == BroadcastController.BroadcastState.Paused;
   }

   public void func_152841_a(BroadcastController.BroadcastListener var1) {
      this.field_152867_c = var1;
   }

   protected void func_152820_d(String var1) {
      this.field_152863_D = var1;
      field_152862_C.func_152757_a(String.valueOf((new StringBuilder("<Error> ")).append(var1)));
      logger.error(TwitchStream.field_152949_a, "[Broadcast controller] {}", new Object[]{var1});
   }

   public boolean func_152854_G() {
      if (!this.isBroadcastPaused()) {
         return false;
      } else {
         this.func_152827_a(BroadcastController.BroadcastState.Broadcasting);
         return true;
      }
   }

   public void func_152846_a(FrameBuffer var1) {
      try {
         this.field_152873_i.captureFrameBuffer_ReadPixels(var1);
      } catch (Throwable var5) {
         CrashReport var3 = CrashReport.makeCrashReport(var5, "Trying to submit a frame to Twitch");
         CrashReportCategory var4 = var3.makeCategory("Broadcast State");
         var4.addCrashSection("Last reported errors", Arrays.toString(field_152862_C.func_152756_c()));
         var4.addCrashSection("Buffer", var1);
         var4.addCrashSection("Free buffer count", this.field_152875_k.size());
         var4.addCrashSection("Capture buffer count", this.field_152874_j.size());
         throw new ReportedException(var3);
      }
   }

   public boolean func_152840_a(String var1, long var2, String var4, String var5) {
      ErrorCode var6 = this.field_152873_i.sendActionMetaData(this.field_152885_u, var1, var2, var4, var5);
      if (ErrorCode.failed(var6)) {
         String var7 = ErrorCode.getString(var6);
         this.func_152820_d(String.format("Error while sending meta data: %s\n", var7));
         return false;
      } else {
         return true;
      }
   }

   public void func_152824_a(IngestServer var1) {
      this.field_152884_t = var1;
   }

   public void func_152829_a(float var1) {
      this.field_152873_i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, var1);
   }

   public boolean func_152851_B() {
      if (!this.field_152876_l) {
         return true;
      } else if (this.isIngestTesting()) {
         return false;
      } else {
         this.field_152878_n = true;
         this.func_152845_C();
         this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
         this.field_152873_i.setStatCallbacks((IStatCallbacks)null);
         ErrorCode var1 = this.field_152872_h.shutdown();
         this.func_152853_a(var1);
         this.field_152876_l = false;
         this.field_152878_n = false;
         this.func_152827_a(BroadcastController.BroadcastState.Uninitialized);
         return true;
      }
   }

   public ErrorCode func_152859_b(FrameBuffer var1) {
      if (this.isBroadcastPaused()) {
         this.func_152854_G();
      } else if (!this.isBroadcasting()) {
         return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
      }

      ErrorCode var2 = this.field_152873_i.submitVideoFrame(var1);
      if (var2 != ErrorCode.TTV_EC_SUCCESS) {
         String var3 = ErrorCode.getString(var2);
         if (ErrorCode.succeeded(var2)) {
            this.func_152832_e(String.format("Warning in SubmitTexturePointer: %s\n", var3));
         } else {
            this.func_152820_d(String.format("Error in SubmitTexturePointer: %s\n", var3));
            this.func_152819_E();
         }

         if (this.field_152867_c != null) {
            this.field_152867_c.func_152893_b(var2);
         }
      }

      return var2;
   }

   public boolean func_177947_a(String var1, long var2, long var4, String var6, String var7) {
      if (var4 == -1L) {
         this.func_152820_d(String.format("Invalid sequence id: %d\n", var4));
         return false;
      } else {
         ErrorCode var8 = this.field_152873_i.sendEndSpanMetaData(this.field_152885_u, var1, var2, var4, var6, var7);
         if (ErrorCode.failed(var8)) {
            String var9 = ErrorCode.getString(var8);
            this.func_152820_d(String.format("Error in SendStopSpanMetaData: %s\n", var9));
            return false;
         } else {
            return true;
         }
      }
   }

   public FrameBuffer func_152822_N() {
      if (this.field_152875_k.size() == 0) {
         this.func_152820_d(String.format("Out of free buffers, this should never happen"));
         return null;
      } else {
         FrameBuffer var1 = (FrameBuffer)this.field_152875_k.get(this.field_152875_k.size() - 1);
         this.field_152875_k.remove(this.field_152875_k.size() - 1);
         return var1;
      }
   }

   public void statCallback() {
      if (this.broadcastState != BroadcastController.BroadcastState.Uninitialized) {
         if (this.field_152860_A != null) {
            this.field_152860_A.func_153039_l();
         }

         for(; this.field_152860_A != null; this.func_152821_H()) {
            try {
               Thread.sleep(200L);
            } catch (Exception var2) {
               this.func_152820_d(var2.toString());
            }
         }

         this.func_152851_B();
      }

   }

   public boolean func_152830_D() {
      if (!this.isBroadcasting()) {
         return false;
      } else {
         ErrorCode var1 = this.field_152873_i.runCommercial(this.field_152885_u);
         this.func_152853_a(var1);
         return ErrorCode.succeeded(var1);
      }
   }

   public boolean func_152818_a(String var1, AuthToken var2) {
      if (this.isIngestTesting()) {
         return false;
      } else {
         this.func_152845_C();
         if (var1 != null && !var1.isEmpty()) {
            if (var2 != null && var2.data != null && !var2.data.isEmpty()) {
               this.field_152880_p = var1;
               this.field_152885_u = var2;
               if (this.func_152858_b()) {
                  this.func_152827_a(BroadcastController.BroadcastState.Authenticated);
               }

               return true;
            } else {
               this.func_152820_d("Auth token must be valid");
               return false;
            }
         } else {
            this.func_152820_d("Username must be valid");
            return false;
         }
      }
   }

   protected void func_152831_M() {
      for(int var1 = 0; var1 < this.field_152874_j.size(); ++var1) {
         FrameBuffer var2 = (FrameBuffer)this.field_152874_j.get(var1);
         var2.free();
      }

      this.field_152875_k.clear();
      this.field_152874_j.clear();
   }

   public boolean func_152836_a(VideoParams var1) {
      if (var1 != null && this.func_152857_n()) {
         this.field_152881_q = var1.clone();
         this.field_152882_r = new AudioParams();
         this.field_152882_r.audioEnabled = this.field_152871_g && this.func_152848_y();
         this.field_152882_r.enableMicCapture = this.field_152882_r.audioEnabled;
         this.field_152882_r.enablePlaybackCapture = this.field_152882_r.audioEnabled;
         this.field_152882_r.enablePassthroughAudio = false;
         if (!this.func_152823_L()) {
            this.field_152881_q = null;
            this.field_152882_r = null;
            return false;
         } else {
            ErrorCode var2 = this.field_152873_i.start(var1, this.field_152882_r, this.field_152884_t, StartFlags.None, true);
            if (ErrorCode.failed(var2)) {
               this.func_152831_M();
               String var3 = ErrorCode.getString(var2);
               this.func_152820_d(String.format("Error while starting to broadcast: %s", var3));
               this.field_152881_q = null;
               this.field_152882_r = null;
               return false;
            } else {
               this.func_152827_a(BroadcastController.BroadcastState.Starting);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   static final class SwitchBroadcastState {
      static final int[] field_177773_a = new int[BroadcastController.BroadcastState.values().length];
      private static final String __OBFID = "CL_00001821";

      static {
         try {
            field_177773_a[BroadcastController.BroadcastState.Authenticated.ordinal()] = 1;
         } catch (NoSuchFieldError var12) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.LoggedIn.ordinal()] = 2;
         } catch (NoSuchFieldError var11) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.ReceivedIngestServers.ordinal()] = 3;
         } catch (NoSuchFieldError var10) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Starting.ordinal()] = 4;
         } catch (NoSuchFieldError var9) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Stopping.ordinal()] = 5;
         } catch (NoSuchFieldError var8) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.FindingIngestServer.ordinal()] = 6;
         } catch (NoSuchFieldError var7) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Authenticating.ordinal()] = 7;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Initialized.ordinal()] = 8;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Uninitialized.ordinal()] = 9;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.IngestTesting.ordinal()] = 10;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Paused.ordinal()] = 11;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177773_a[BroadcastController.BroadcastState.Broadcasting.ordinal()] = 12;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static enum BroadcastState {
      Broadcasting("Broadcasting", 10),
      LoggingIn("LoggingIn", 4);

      private static final BroadcastController.BroadcastState[] $VALUES = new BroadcastController.BroadcastState[]{Uninitialized, Initialized, Authenticating, Authenticated, LoggingIn, LoggedIn, FindingIngestServer, ReceivedIngestServers, ReadyToBroadcast, Starting, Broadcasting, Stopping, Paused, IngestTesting};
      ReceivedIngestServers("ReceivedIngestServers", 7),
      Initialized("Initialized", 1),
      Paused("Paused", 12);

      private static final String __OBFID = "CL_00001820";
      Authenticating("Authenticating", 2),
      IngestTesting("IngestTesting", 13),
      Uninitialized("Uninitialized", 0),
      Authenticated("Authenticated", 3),
      Starting("Starting", 9),
      Stopping("Stopping", 11),
      ReadyToBroadcast("ReadyToBroadcast", 8),
      LoggedIn("LoggedIn", 5);

      private static final BroadcastController.BroadcastState[] ENUM$VALUES = new BroadcastController.BroadcastState[]{Uninitialized, Initialized, Authenticating, Authenticated, LoggingIn, LoggedIn, FindingIngestServer, ReceivedIngestServers, ReadyToBroadcast, Starting, Broadcasting, Stopping, Paused, IngestTesting};
      FindingIngestServer("FindingIngestServer", 6);

      private BroadcastState(String var3, int var4) {
      }
   }

   public interface BroadcastListener {
      void func_152899_b();

      void func_152898_a(ErrorCode var1, GameInfo[] var2);

      void func_152900_a(ErrorCode var1, AuthToken var2);

      void func_152891_a(BroadcastController.BroadcastState var1);

      void func_152893_b(ErrorCode var1);

      void func_152896_a(IngestList var1);

      void func_152895_a();

      void func_152894_a(StreamInfo var1);

      void func_152901_c();

      void func_152892_c(ErrorCode var1);

      void func_152897_a(ErrorCode var1);
   }
}
