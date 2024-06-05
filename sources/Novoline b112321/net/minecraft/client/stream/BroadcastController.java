package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import net.K_;
import net.aho;
import net.minecraft.client.stream.BroadcastController$2;
import net.minecraft.client.stream.BroadcastController$3;
import net.minecraft.client.stream.BroadcastController$BroadcastListener;
import net.minecraft.client.stream.BroadcastController$BroadcastState;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.client.stream.TwitchStream;
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
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.StreamInfoForSetting;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.VideoParams;

public class BroadcastController {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final int field_152865_a = 30;
   protected final int field_152866_b = 3;
   private static final ThreadSafeBoundList field_152862_C = new ThreadSafeBoundList(String.class, 50);
   private String field_152863_D = null;
   protected BroadcastController$BroadcastListener broadcastListener = null;
   protected String field_152868_d = "";
   protected String field_152869_e = "";
   protected String field_152870_f = "";
   protected boolean field_152871_g = true;
   protected Core field_152872_h = Core.getInstance();
   protected Stream field_152873_i;
   protected List field_152874_j = Lists.newArrayList();
   protected List field_152875_k = Lists.newArrayList();
   protected boolean field_152876_l = false;
   protected boolean field_152877_m = false;
   protected boolean field_152878_n = false;
   protected BroadcastController$BroadcastState broadcastState = BroadcastController$BroadcastState.Uninitialized;
   protected String field_152880_p = null;
   protected VideoParams videoParamaters = null;
   protected AudioParams audioParamaters = null;
   protected IngestList ingestList = new IngestList(new IngestServer[0]);
   protected IngestServer field_152884_t = null;
   protected AuthToken authenticationToken = new AuthToken();
   protected ChannelInfo channelInfo = new ChannelInfo();
   protected UserInfo userInfo = new UserInfo();
   protected StreamInfo streamInfo = new StreamInfo();
   protected ArchivingState field_152889_y = new ArchivingState();
   protected long field_152890_z = 0L;
   protected IngestServerTester field_152860_A = null;
   private ErrorCode errorCode;
   protected IStreamCallbacks field_177948_B = new aho(this);
   protected IStatCallbacks field_177949_C = new BroadcastController$2(this);

   public void func_152841_a(BroadcastController$BroadcastListener var1) {
      this.broadcastListener = var1;
   }

   public boolean func_152858_b() {
      return this.field_152876_l;
   }

   public void func_152842_a(String var1) {
      this.field_152868_d = var1;
   }

   public StreamInfo getStreamInfo() {
      return this.streamInfo;
   }

   public ChannelInfo getChannelInfo() {
      return this.channelInfo;
   }

   public boolean isBroadcasting() {
      return this.broadcastState == BroadcastController$BroadcastState.Broadcasting || this.broadcastState == BroadcastController$BroadcastState.Paused;
   }

   public boolean isReadyToBroadcast() {
      return this.broadcastState == BroadcastController$BroadcastState.ReadyToBroadcast;
   }

   public boolean isIngestTesting() {
      return this.broadcastState == BroadcastController$BroadcastState.IngestTesting;
   }

   public boolean isBroadcastPaused() {
      return this.broadcastState == BroadcastController$BroadcastState.Paused;
   }

   public boolean func_152849_q() {
      return this.field_152877_m;
   }

   public IngestServer func_152833_s() {
      return this.field_152884_t;
   }

   public void func_152824_a(IngestServer var1) {
      this.field_152884_t = var1;
   }

   public IngestList func_152855_t() {
      return this.ingestList;
   }

   public void setRecordingDeviceVolume(float var1) {
      this.field_152873_i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, var1);
   }

   public void setPlaybackDeviceVolume(float var1) {
      this.field_152873_i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, var1);
   }

   public IngestServerTester isReady() {
      return this.field_152860_A;
   }

   public long func_152844_x() {
      return this.field_152873_i.getStreamTime();
   }

   protected boolean func_152848_y() {
      return true;
   }

   public ErrorCode getErrorCode() {
      return this.errorCode;
   }

   public BroadcastController() {
      if(Core.getInstance() == null) {
         this.field_152872_h = new Core(new StandardCoreAPI());
      }

      this.field_152873_i = new Stream(new DesktopStreamAPI());
   }

   protected PixelFormat func_152826_z() {
      return PixelFormat.TTV_PF_RGBA;
   }

   public boolean func_152817_A() {
      if(this.field_152876_l) {
         return false;
      } else {
         this.field_152873_i.setStreamCallbacks(this.field_177948_B);
         ErrorCode var1 = this.field_152872_h.initialize(this.field_152868_d, System.getProperty("java.library.path"));
         if(!this.func_152853_a(var1)) {
            this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
            this.errorCode = var1;
            return false;
         } else {
            var1 = this.field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
            if(!this.func_152853_a(var1)) {
               this.field_152873_i.setStreamCallbacks((IStreamCallbacks)null);
               this.field_152872_h.shutdown();
               this.errorCode = var1;
               return false;
            } else if(ErrorCode.succeeded(var1)) {
               this.field_152876_l = true;
               this.func_152827_a(BroadcastController$BroadcastState.Initialized);
               return true;
            } else {
               this.errorCode = var1;
               this.field_152872_h.shutdown();
               return false;
            }
         }
      }
   }

   public boolean func_152851_B() {
      if(!this.field_152876_l) {
         return true;
      } else if(this.isIngestTesting()) {
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
         this.func_152827_a(BroadcastController$BroadcastState.Uninitialized);
         return true;
      }
   }

   public void statCallback() {
      if(this.broadcastState != BroadcastController$BroadcastState.Uninitialized) {
         if(this.field_152860_A != null) {
            this.field_152860_A.func_153039_l();
         }

         for(; this.field_152860_A != null; this.func_152821_H()) {
            long var10000 = 200L;

            try {
               Thread.sleep(var10000);
            } catch (Exception var2) {
               this.func_152820_d(var2.toString());
            }
         }

         this.func_152851_B();
      }

   }

   public boolean func_152818_a(String var1, AuthToken var2) {
      if(this.isIngestTesting()) {
         return false;
      } else {
         this.func_152845_C();
         if(!var1.isEmpty()) {
            if(var2.data != null && !var2.data.isEmpty()) {
               this.field_152880_p = var1;
               this.authenticationToken = var2;
               if(this.func_152858_b()) {
                  this.func_152827_a(BroadcastController$BroadcastState.Authenticated);
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

   public boolean func_152845_C() {
      if(this.isIngestTesting()) {
         return false;
      } else {
         if(this.isBroadcasting()) {
            this.field_152873_i.stop(false);
         }

         this.field_152880_p = "";
         this.authenticationToken = new AuthToken();
         if(!this.field_152877_m) {
            return false;
         } else {
            this.field_152877_m = false;
            if(!this.field_152878_n) {
               try {
                  if(this.broadcastListener != null) {
                     this.broadcastListener.func_152895_a();
                  }
               } catch (Exception var2) {
                  this.func_152820_d(var2.toString());
               }
            }

            this.func_152827_a(BroadcastController$BroadcastState.Initialized);
            return true;
         }
      }
   }

   public boolean func_152828_a(String var1, String var2, String var3) {
      if(!this.field_152877_m) {
         return false;
      } else {
         if(var1.equals("")) {
            var1 = this.field_152880_p;
         }

         var2 = "";
         var3 = "";
         StreamInfoForSetting var4 = new StreamInfoForSetting();
         var4.streamTitle = var3;
         var4.gameName = var2;
         ErrorCode var5 = this.field_152873_i.setStreamInfo(this.authenticationToken, var1, var4);
         this.func_152853_a(var5);
         return ErrorCode.succeeded(var5);
      }
   }

   public boolean requestCommercial() {
      if(!this.isBroadcasting()) {
         return false;
      } else {
         ErrorCode var1 = this.field_152873_i.runCommercial(this.authenticationToken);
         this.func_152853_a(var1);
         return ErrorCode.succeeded(var1);
      }
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

   public boolean func_152836_a(VideoParams var1) {
      if(!this.isReadyToBroadcast()) {
         return false;
      } else {
         this.videoParamaters = var1.clone();
         this.audioParamaters = new AudioParams();
         this.audioParamaters.audioEnabled = this.field_152871_g && this.func_152848_y();
         this.audioParamaters.enableMicCapture = this.audioParamaters.audioEnabled;
         this.audioParamaters.enablePlaybackCapture = this.audioParamaters.audioEnabled;
         this.audioParamaters.enablePassthroughAudio = false;
         if(!this.func_152823_L()) {
            this.videoParamaters = null;
            this.audioParamaters = null;
            return false;
         } else {
            ErrorCode var2 = this.field_152873_i.start(var1, this.audioParamaters, this.field_152884_t, StartFlags.None, true);
            if(ErrorCode.failed(var2)) {
               this.func_152831_M();
               String var3 = ErrorCode.getString(var2);
               this.func_152820_d(String.format("Error while starting to broadcast: %s", new Object[]{var3}));
               this.videoParamaters = null;
               this.audioParamaters = null;
               return false;
            } else {
               this.func_152827_a(BroadcastController$BroadcastState.Starting);
               return true;
            }
         }
      }
   }

   public boolean stopBroadcasting() {
      if(!this.isBroadcasting()) {
         return false;
      } else {
         ErrorCode var1 = this.field_152873_i.stop(true);
         if(ErrorCode.failed(var1)) {
            String var2 = ErrorCode.getString(var1);
            this.func_152820_d(String.format("Error while stopping the broadcast: %s", new Object[]{var2}));
            return false;
         } else {
            this.func_152827_a(BroadcastController$BroadcastState.Stopping);
            return ErrorCode.succeeded(var1);
         }
      }
   }

   public boolean func_152847_F() {
      if(!this.isBroadcasting()) {
         return false;
      } else {
         ErrorCode var1 = this.field_152873_i.pauseVideo();
         if(ErrorCode.failed(var1)) {
            this.stopBroadcasting();
            String var2 = ErrorCode.getString(var1);
            this.func_152820_d(String.format("Error pausing stream: %s\n", new Object[]{var2}));
         } else {
            this.func_152827_a(BroadcastController$BroadcastState.Paused);
         }

         return ErrorCode.succeeded(var1);
      }
   }

   public boolean func_152854_G() {
      if(!this.isBroadcastPaused()) {
         return false;
      } else {
         this.func_152827_a(BroadcastController$BroadcastState.Broadcasting);
         return true;
      }
   }

   public boolean func_152840_a(String var1, long var2, String var4, String var5) {
      ErrorCode var6 = this.field_152873_i.sendActionMetaData(this.authenticationToken, var1, var2, var4, var5);
      if(ErrorCode.failed(var6)) {
         String var7 = ErrorCode.getString(var6);
         this.func_152820_d(String.format("Error while sending meta data: %s\n", new Object[]{var7}));
         return false;
      } else {
         return true;
      }
   }

   public long func_177946_b(String var1, long var2, String var4, String var5) {
      long var6 = this.field_152873_i.sendStartSpanMetaData(this.authenticationToken, var1, var2, var4, var5);
      if(var6 == -1L) {
         this.func_152820_d(String.format("Error in SendStartSpanMetaData\n", new Object[0]));
      }

      return var6;
   }

   public boolean func_177947_a(String var1, long var2, long var4, String var6, String var7) {
      if(var4 == -1L) {
         this.func_152820_d(String.format("Invalid sequence id: %d\n", new Object[]{Long.valueOf(var4)}));
         return false;
      } else {
         ErrorCode var8 = K_.a(this.field_152873_i, this.authenticationToken, var1, var2, var4, var6, var7);
         if(ErrorCode.failed(var8)) {
            String var9 = ErrorCode.getString(var8);
            this.func_152820_d(String.format("Error in SendStopSpanMetaData: %s\n", new Object[]{var9}));
            return false;
         } else {
            return true;
         }
      }
   }

   protected void func_152827_a(BroadcastController$BroadcastState var1) {
      if(var1 != this.broadcastState) {
         this.broadcastState = var1;

         try {
            if(this.broadcastListener != null) {
               this.broadcastListener.func_152891_a(var1);
            }
         } catch (Exception var3) {
            this.func_152820_d(var3.toString());
         }
      }

   }

   public void func_152821_H() {
      if(this.field_152873_i != null && this.field_152876_l) {
         ErrorCode var1 = this.field_152873_i.pollTasks();
         this.func_152853_a(var1);
         if(this.isIngestTesting()) {
            this.field_152860_A.func_153041_j();
            if(this.field_152860_A.func_153032_e()) {
               this.field_152860_A = null;
               this.func_152827_a(BroadcastController$BroadcastState.ReadyToBroadcast);
            }
         }

         switch(BroadcastController$3.$SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[this.broadcastState.ordinal()]) {
         case 1:
            this.func_152827_a(BroadcastController$BroadcastState.LoggingIn);
            var1 = this.field_152873_i.login(this.authenticationToken);
            if(ErrorCode.failed(var1)) {
               String var9 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_Login: %s\n", new Object[]{var9}));
            }
            break;
         case 2:
            this.func_152827_a(BroadcastController$BroadcastState.FindingIngestServer);
            var1 = this.field_152873_i.getIngestServers(this.authenticationToken);
            if(ErrorCode.failed(var1)) {
               this.func_152827_a(BroadcastController$BroadcastState.LoggedIn);
               String var8 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_GetIngestServers: %s\n", new Object[]{var8}));
            }
            break;
         case 3:
            this.func_152827_a(BroadcastController$BroadcastState.ReadyToBroadcast);
            var1 = this.field_152873_i.getUserInfo(this.authenticationToken);
            if(ErrorCode.failed(var1)) {
               String var2 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_GetUserInfo: %s\n", new Object[]{var2}));
            }

            this.func_152835_I();
            var1 = this.field_152873_i.getArchivingState(this.authenticationToken);
            if(ErrorCode.failed(var1)) {
               String var7 = ErrorCode.getString(var1);
               this.func_152820_d(String.format("Error in TTV_GetArchivingState: %s\n", new Object[]{var7}));
            }
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         default:
            break;
         case 11:
         case 12:
            this.func_152835_I();
         }
      }

   }

   protected void func_152835_I() {
      long var1 = System.nanoTime();
      long var3 = (var1 - this.field_152890_z) / 1000000000L;
      if(var3 >= 30L) {
         this.field_152890_z = var1;
         ErrorCode var5 = this.field_152873_i.getStreamInfo(this.authenticationToken, this.field_152880_p);
         if(ErrorCode.failed(var5)) {
            String var6 = ErrorCode.getString(var5);
            this.func_152820_d(String.format("Error in TTV_GetStreamInfo: %s", new Object[]{var6}));
         }
      }

   }

   public IngestServerTester func_152838_J() {
      if(this.isReadyToBroadcast() && this.ingestList != null) {
         if(this.isIngestTesting()) {
            return null;
         } else {
            this.field_152860_A = new IngestServerTester(this.field_152873_i, this.ingestList);
            this.field_152860_A.func_176004_j();
            this.func_152827_a(BroadcastController$BroadcastState.IngestTesting);
            return this.field_152860_A;
         }
      } else {
         return null;
      }
   }

   protected boolean func_152823_L() {
      for(int var1 = 0; var1 < 3; ++var1) {
         FrameBuffer var2 = this.field_152873_i.allocateFrameBuffer(this.videoParamaters.outputWidth * this.videoParamaters.outputHeight * 4);
         if(!var2.getIsValid()) {
            this.func_152820_d(String.format("Error while allocating frame buffer", new Object[0]));
            return false;
         }

         this.field_152874_j.add(var2);
         this.field_152875_k.add(var2);
      }

      return true;
   }

   protected void func_152831_M() {
      for(FrameBuffer var2 : this.field_152874_j) {
         var2.free();
      }

      this.field_152875_k.clear();
      this.field_152874_j.clear();
   }

   public FrameBuffer func_152822_N() {
      if(this.field_152875_k.isEmpty()) {
         this.func_152820_d(String.format("Out of free buffers, this should never happen", new Object[0]));
         return null;
      } else {
         FrameBuffer var1 = (FrameBuffer)this.field_152875_k.get(this.field_152875_k.size() - 1);
         this.field_152875_k.remove(this.field_152875_k.size() - 1);
         return var1;
      }
   }

   public void captureFramebuffer(FrameBuffer var1) {
      BroadcastController var10000 = this;

      try {
         var10000.field_152873_i.captureFrameBuffer_ReadPixels(var1);
      } catch (Throwable var5) {
         CrashReport var3 = CrashReport.makeCrashReport(var5, "Trying to submit a frame to Twitch");
         CrashReportCategory var4 = var3.makeCategory("Broadcast State");
         var4.addCrashSection("Last reported errors", Arrays.toString(field_152862_C.func_152756_c()));
         var4.addCrashSection("Buffer", var1);
         var4.addCrashSection("Free buffer count", Integer.valueOf(this.field_152875_k.size()));
         var4.addCrashSection("Capture buffer count", Integer.valueOf(this.field_152874_j.size()));
         throw new ReportedException(var3);
      }
   }

   public ErrorCode submitStreamFrame(FrameBuffer var1) {
      if(this.isBroadcastPaused()) {
         this.func_152854_G();
      } else if(!this.isBroadcasting()) {
         return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
      }

      ErrorCode var2 = this.field_152873_i.submitVideoFrame(var1);
      if(var2 != ErrorCode.TTV_EC_SUCCESS) {
         String var3 = ErrorCode.getString(var2);
         if(ErrorCode.succeeded(var2)) {
            this.func_152832_e(String.format("Warning in SubmitTexturePointer: %s\n", new Object[]{var3}));
         } else {
            this.func_152820_d(String.format("Error in SubmitTexturePointer: %s\n", new Object[]{var3}));
            this.stopBroadcasting();
         }

         if(this.broadcastListener != null) {
            this.broadcastListener.func_152893_b(var2);
         }
      }

      return var2;
   }

   protected boolean func_152853_a(ErrorCode var1) {
      if(ErrorCode.failed(var1)) {
         this.func_152820_d(ErrorCode.getString(var1));
         return false;
      } else {
         return true;
      }
   }

   protected void func_152820_d(String var1) {
      this.field_152863_D = var1;
      field_152862_C.func_152757_a("<Error> " + var1);
      LOGGER.error(TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[]{var1});
   }

   protected void func_152832_e(String var1) {
      field_152862_C.func_152757_a("<Warning> " + var1);
      LOGGER.warn(TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[]{var1});
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
