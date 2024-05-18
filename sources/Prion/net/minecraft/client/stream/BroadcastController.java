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

public class BroadcastController
{
  private static final Logger logger = ;
  protected final int field_152865_a = 30;
  protected final int field_152866_b = 3;
  private static final ThreadSafeBoundList field_152862_C = new ThreadSafeBoundList(String.class, 50);
  private String field_152863_D = null;
  protected BroadcastListener field_152867_c = null;
  protected String field_152868_d = "";
  protected String field_152869_e = "";
  protected String field_152870_f = "";
  protected boolean field_152871_g = true;
  protected Core field_152872_h = null;
  protected Stream field_152873_i = null;
  protected List field_152874_j = Lists.newArrayList();
  protected List field_152875_k = Lists.newArrayList();
  protected boolean field_152876_l = false;
  protected boolean field_152877_m = false;
  protected boolean field_152878_n = false;
  protected BroadcastState broadcastState;
  protected String field_152880_p;
  protected VideoParams field_152881_q;
  protected AudioParams field_152882_r;
  protected IngestList field_152883_s;
  protected IngestServer field_152884_t;
  protected AuthToken field_152885_u;
  protected ChannelInfo channelInfo;
  protected UserInfo field_152887_w;
  protected StreamInfo field_152888_x;
  protected ArchivingState field_152889_y;
  protected long field_152890_z;
  protected IngestServerTester field_152860_A;
  private ErrorCode field_152864_E;
  protected IStreamCallbacks field_177948_B;
  protected IStatCallbacks field_177949_C;
  private static final String __OBFID = "CL_00001822";
  
  public void func_152841_a(BroadcastListener p_152841_1_)
  {
    field_152867_c = p_152841_1_;
  }
  
  public boolean func_152858_b()
  {
    return field_152876_l;
  }
  
  public void func_152842_a(String p_152842_1_)
  {
    field_152868_d = p_152842_1_;
  }
  
  public StreamInfo func_152816_j()
  {
    return field_152888_x;
  }
  
  public ChannelInfo func_152843_l()
  {
    return channelInfo;
  }
  
  public boolean isBroadcasting()
  {
    return (broadcastState == BroadcastState.Broadcasting) || (broadcastState == BroadcastState.Paused);
  }
  
  public boolean func_152857_n()
  {
    return broadcastState == BroadcastState.ReadyToBroadcast;
  }
  
  public boolean isIngestTesting()
  {
    return broadcastState == BroadcastState.IngestTesting;
  }
  
  public boolean isBroadcastPaused()
  {
    return broadcastState == BroadcastState.Paused;
  }
  
  public boolean func_152849_q()
  {
    return field_152877_m;
  }
  
  public IngestServer func_152833_s()
  {
    return field_152884_t;
  }
  
  public void func_152824_a(IngestServer p_152824_1_)
  {
    field_152884_t = p_152824_1_;
  }
  
  public IngestList func_152855_t()
  {
    return field_152883_s;
  }
  
  public void func_152829_a(float p_152829_1_)
  {
    field_152873_i.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, p_152829_1_);
  }
  
  public void func_152837_b(float p_152837_1_)
  {
    field_152873_i.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, p_152837_1_);
  }
  
  public IngestServerTester isReady()
  {
    return field_152860_A;
  }
  
  public long func_152844_x()
  {
    return field_152873_i.getStreamTime();
  }
  
  protected boolean func_152848_y()
  {
    return true;
  }
  
  public ErrorCode func_152852_P()
  {
    return field_152864_E;
  }
  
  public BroadcastController()
  {
    broadcastState = BroadcastState.Uninitialized;
    field_152880_p = null;
    field_152881_q = null;
    field_152882_r = null;
    field_152883_s = new IngestList(new IngestServer[0]);
    field_152884_t = null;
    field_152885_u = new AuthToken();
    channelInfo = new ChannelInfo();
    field_152887_w = new UserInfo();
    field_152888_x = new StreamInfo();
    field_152889_y = new ArchivingState();
    field_152890_z = 0L;
    field_152860_A = null;
    field_177948_B = new IStreamCallbacks()
    {
      private static final String __OBFID = "CL_00002375";
      
      public void requestAuthTokenCallback(ErrorCode p_requestAuthTokenCallback_1_, AuthToken p_requestAuthTokenCallback_2_) {
        if (ErrorCode.succeeded(p_requestAuthTokenCallback_1_))
        {
          field_152885_u = p_requestAuthTokenCallback_2_;
          func_152827_a(BroadcastController.BroadcastState.Authenticated);
        }
        else
        {
          field_152885_u.data = "";
          func_152827_a(BroadcastController.BroadcastState.Initialized);
          String var3 = ErrorCode.getString(p_requestAuthTokenCallback_1_);
          func_152820_d(String.format("RequestAuthTokenDoneCallback got failure: %s", new Object[] { var3 }));
        }
        
        try
        {
          if (field_152867_c != null)
          {
            field_152867_c.func_152900_a(p_requestAuthTokenCallback_1_, p_requestAuthTokenCallback_2_);
          }
        }
        catch (Exception var4)
        {
          func_152820_d(var4.toString());
        }
      }
      
      public void loginCallback(ErrorCode p_loginCallback_1_, ChannelInfo p_loginCallback_2_) {
        if (ErrorCode.succeeded(p_loginCallback_1_))
        {
          channelInfo = p_loginCallback_2_;
          func_152827_a(BroadcastController.BroadcastState.LoggedIn);
          field_152877_m = true;
        }
        else
        {
          func_152827_a(BroadcastController.BroadcastState.Initialized);
          field_152877_m = false;
          String var3 = ErrorCode.getString(p_loginCallback_1_);
          func_152820_d(String.format("LoginCallback got failure: %s", new Object[] { var3 }));
        }
        
        try
        {
          if (field_152867_c != null)
          {
            field_152867_c.func_152897_a(p_loginCallback_1_);
          }
        }
        catch (Exception var4)
        {
          func_152820_d(var4.toString());
        }
      }
      
      public void getIngestServersCallback(ErrorCode p_getIngestServersCallback_1_, IngestList p_getIngestServersCallback_2_) {
        if (ErrorCode.succeeded(p_getIngestServersCallback_1_))
        {
          field_152883_s = p_getIngestServersCallback_2_;
          field_152884_t = field_152883_s.getDefaultServer();
          func_152827_a(BroadcastController.BroadcastState.ReceivedIngestServers);
          
          try
          {
            if (field_152867_c == null)
              return;
            field_152867_c.func_152896_a(p_getIngestServersCallback_2_);

          }
          catch (Exception var4)
          {
            func_152820_d(var4.toString());
          }
        }
        else
        {
          String var3 = ErrorCode.getString(p_getIngestServersCallback_1_);
          func_152820_d(String.format("IngestListCallback got failure: %s", new Object[] { var3 }));
          func_152827_a(BroadcastController.BroadcastState.LoggingIn);
        }
      }
      
      public void getUserInfoCallback(ErrorCode p_getUserInfoCallback_1_, UserInfo p_getUserInfoCallback_2_) {
        field_152887_w = p_getUserInfoCallback_2_;
        
        if (ErrorCode.failed(p_getUserInfoCallback_1_))
        {
          String var3 = ErrorCode.getString(p_getUserInfoCallback_1_);
          func_152820_d(String.format("UserInfoDoneCallback got failure: %s", new Object[] { var3 }));
        }
      }
      
      public void getStreamInfoCallback(ErrorCode p_getStreamInfoCallback_1_, StreamInfo p_getStreamInfoCallback_2_) {
        if (ErrorCode.succeeded(p_getStreamInfoCallback_1_))
        {
          field_152888_x = p_getStreamInfoCallback_2_;
          
          try
          {
            if (field_152867_c == null)
              return;
            field_152867_c.func_152894_a(p_getStreamInfoCallback_2_);

          }
          catch (Exception var4)
          {
            func_152820_d(var4.toString());
          }
        }
        else
        {
          String var3 = ErrorCode.getString(p_getStreamInfoCallback_1_);
          func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", new Object[] { var3 }));
        }
      }
      
      public void getArchivingStateCallback(ErrorCode p_getArchivingStateCallback_1_, ArchivingState p_getArchivingStateCallback_2_) {
        field_152889_y = p_getArchivingStateCallback_2_;
        
        if (ErrorCode.failed(p_getArchivingStateCallback_1_)) {}
      }
      


      public void runCommercialCallback(ErrorCode p_runCommercialCallback_1_)
      {
        if (ErrorCode.failed(p_runCommercialCallback_1_))
        {
          String var2 = ErrorCode.getString(p_runCommercialCallback_1_);
          func_152832_e(String.format("RunCommercialCallback got failure: %s", new Object[] { var2 }));
        }
      }
      
      public void setStreamInfoCallback(ErrorCode p_setStreamInfoCallback_1_) {
        if (ErrorCode.failed(p_setStreamInfoCallback_1_))
        {
          String var2 = ErrorCode.getString(p_setStreamInfoCallback_1_);
          func_152832_e(String.format("SetStreamInfoCallback got failure: %s", new Object[] { var2 }));
        }
      }
      
      public void getGameNameListCallback(ErrorCode p_getGameNameListCallback_1_, GameInfoList p_getGameNameListCallback_2_) {
        if (ErrorCode.failed(p_getGameNameListCallback_1_))
        {
          String var3 = ErrorCode.getString(p_getGameNameListCallback_1_);
          func_152820_d(String.format("GameNameListCallback got failure: %s", new Object[] { var3 }));
        }
        
        try
        {
          if (field_152867_c != null)
          {
            field_152867_c.func_152898_a(p_getGameNameListCallback_1_, p_getGameNameListCallback_2_ == null ? new GameInfo[0] : list);
          }
        }
        catch (Exception var4)
        {
          func_152820_d(var4.toString());
        }
      }
      
      public void bufferUnlockCallback(long p_bufferUnlockCallback_1_) {
        FrameBuffer var3 = FrameBuffer.lookupBuffer(p_bufferUnlockCallback_1_);
        field_152875_k.add(var3);
      }
      
      public void startCallback(ErrorCode p_startCallback_1_) {
        if (ErrorCode.succeeded(p_startCallback_1_))
        {
          try
          {
            if (field_152867_c != null)
            {
              field_152867_c.func_152899_b();
            }
          }
          catch (Exception var4)
          {
            func_152820_d(var4.toString());
          }
          
          func_152827_a(BroadcastController.BroadcastState.Broadcasting);
        }
        else
        {
          field_152881_q = null;
          field_152882_r = null;
          func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
          
          try
          {
            if (field_152867_c != null)
            {
              field_152867_c.func_152892_c(p_startCallback_1_);
            }
          }
          catch (Exception var3)
          {
            func_152820_d(var3.toString());
          }
          
          String var2 = ErrorCode.getString(p_startCallback_1_);
          func_152820_d(String.format("startCallback got failure: %s", new Object[] { var2 }));
        }
      }
      
      public void stopCallback(ErrorCode p_stopCallback_1_) {
        if (ErrorCode.succeeded(p_stopCallback_1_))
        {
          field_152881_q = null;
          field_152882_r = null;
          func_152831_M();
          
          try
          {
            if (field_152867_c != null)
            {
              field_152867_c.func_152901_c();
            }
          }
          catch (Exception var3)
          {
            func_152820_d(var3.toString());
          }
          
          if (field_152877_m)
          {
            func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
          }
          else
          {
            func_152827_a(BroadcastController.BroadcastState.Initialized);
          }
        }
        else
        {
          func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
          String var2 = ErrorCode.getString(p_stopCallback_1_);
          func_152820_d(String.format("stopCallback got failure: %s", new Object[] { var2 }));
        }
      }
      
      public void sendActionMetaDataCallback(ErrorCode p_sendActionMetaDataCallback_1_) {
        if (ErrorCode.failed(p_sendActionMetaDataCallback_1_))
        {
          String var2 = ErrorCode.getString(p_sendActionMetaDataCallback_1_);
          func_152820_d(String.format("sendActionMetaDataCallback got failure: %s", new Object[] { var2 }));
        }
      }
      
      public void sendStartSpanMetaDataCallback(ErrorCode p_sendStartSpanMetaDataCallback_1_) {
        if (ErrorCode.failed(p_sendStartSpanMetaDataCallback_1_))
        {
          String var2 = ErrorCode.getString(p_sendStartSpanMetaDataCallback_1_);
          func_152820_d(String.format("sendStartSpanMetaDataCallback got failure: %s", new Object[] { var2 }));
        }
      }
      
      public void sendEndSpanMetaDataCallback(ErrorCode p_sendEndSpanMetaDataCallback_1_) {
        if (ErrorCode.failed(p_sendEndSpanMetaDataCallback_1_))
        {
          String var2 = ErrorCode.getString(p_sendEndSpanMetaDataCallback_1_);
          func_152820_d(String.format("sendEndSpanMetaDataCallback got failure: %s", new Object[] { var2 }));
        }
      }
    };
    field_177949_C = new IStatCallbacks() {
      private static final String __OBFID = "CL_00002374";
      
      public void statCallback(StatType p_statCallback_1_, long p_statCallback_2_) {}
    };
    field_152872_h = Core.getInstance();
    
    if (Core.getInstance() == null)
    {
      field_152872_h = new Core(new StandardCoreAPI());
    }
    
    field_152873_i = new Stream(new DesktopStreamAPI());
  }
  
  protected PixelFormat func_152826_z()
  {
    return PixelFormat.TTV_PF_RGBA;
  }
  
  public boolean func_152817_A()
  {
    if (field_152876_l)
    {
      return false;
    }
    

    field_152873_i.setStreamCallbacks(field_177948_B);
    ErrorCode var1 = field_152872_h.initialize(field_152868_d, System.getProperty("java.library.path"));
    
    if (!func_152853_a(var1))
    {
      field_152873_i.setStreamCallbacks(null);
      field_152864_E = var1;
      return false;
    }
    

    var1 = field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
    
    if (!func_152853_a(var1))
    {
      field_152873_i.setStreamCallbacks(null);
      field_152872_h.shutdown();
      field_152864_E = var1;
      return false;
    }
    if (ErrorCode.succeeded(var1))
    {
      field_152876_l = true;
      func_152827_a(BroadcastState.Initialized);
      return true;
    }
    

    field_152864_E = var1;
    field_152872_h.shutdown();
    return false;
  }
  



  public boolean func_152851_B()
  {
    if (!field_152876_l)
    {
      return true;
    }
    if (isIngestTesting())
    {
      return false;
    }
    

    field_152878_n = true;
    func_152845_C();
    field_152873_i.setStreamCallbacks(null);
    field_152873_i.setStatCallbacks(null);
    ErrorCode var1 = field_152872_h.shutdown();
    func_152853_a(var1);
    field_152876_l = false;
    field_152878_n = false;
    func_152827_a(BroadcastState.Uninitialized);
    return true;
  }
  

  public void statCallback()
  {
    if (broadcastState != BroadcastState.Uninitialized)
    {
      if (field_152860_A != null)
      {
        field_152860_A.func_153039_l();
      }
      for (; 
          field_152860_A != null; func_152821_H())
      {
        try
        {
          Thread.sleep(200L);
        }
        catch (Exception var2)
        {
          func_152820_d(var2.toString());
        }
      }
      
      func_152851_B();
    }
  }
  
  public boolean func_152818_a(String p_152818_1_, AuthToken p_152818_2_)
  {
    if (isIngestTesting())
    {
      return false;
    }
    

    func_152845_C();
    
    if ((p_152818_1_ != null) && (!p_152818_1_.isEmpty()))
    {
      if ((p_152818_2_ != null) && (data != null) && (!data.isEmpty()))
      {
        field_152880_p = p_152818_1_;
        field_152885_u = p_152818_2_;
        
        if (func_152858_b())
        {
          func_152827_a(BroadcastState.Authenticated);
        }
        
        return true;
      }
      

      func_152820_d("Auth token must be valid");
      return false;
    }
    


    func_152820_d("Username must be valid");
    return false;
  }
  


  public boolean func_152845_C()
  {
    if (isIngestTesting())
    {
      return false;
    }
    

    if (isBroadcasting())
    {
      field_152873_i.stop(false);
    }
    
    field_152880_p = "";
    field_152885_u = new AuthToken();
    
    if (!field_152877_m)
    {
      return false;
    }
    

    field_152877_m = false;
    
    if (!field_152878_n)
    {
      try
      {
        if (field_152867_c != null)
        {
          field_152867_c.func_152895_a();
        }
      }
      catch (Exception var2)
      {
        func_152820_d(var2.toString());
      }
    }
    
    func_152827_a(BroadcastState.Initialized);
    return true;
  }
  


  public boolean func_152828_a(String p_152828_1_, String p_152828_2_, String p_152828_3_)
  {
    if (!field_152877_m)
    {
      return false;
    }
    

    if ((p_152828_1_ == null) || (p_152828_1_.equals("")))
    {
      p_152828_1_ = field_152880_p;
    }
    
    if (p_152828_2_ == null)
    {
      p_152828_2_ = "";
    }
    
    if (p_152828_3_ == null)
    {
      p_152828_3_ = "";
    }
    
    StreamInfoForSetting var4 = new StreamInfoForSetting();
    streamTitle = p_152828_3_;
    gameName = p_152828_2_;
    ErrorCode var5 = field_152873_i.setStreamInfo(field_152885_u, p_152828_1_, var4);
    func_152853_a(var5);
    return ErrorCode.succeeded(var5);
  }
  

  public boolean func_152830_D()
  {
    if (!isBroadcasting())
    {
      return false;
    }
    

    ErrorCode var1 = field_152873_i.runCommercial(field_152885_u);
    func_152853_a(var1);
    return ErrorCode.succeeded(var1);
  }
  

  public VideoParams func_152834_a(int p_152834_1_, int p_152834_2_, float p_152834_3_, float p_152834_4_)
  {
    int[] var5 = field_152873_i.getMaxResolution(p_152834_1_, p_152834_2_, p_152834_3_, p_152834_4_);
    VideoParams var6 = new VideoParams();
    maxKbps = p_152834_1_;
    encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
    pixelFormat = func_152826_z();
    targetFps = p_152834_2_;
    outputWidth = var5[0];
    outputHeight = var5[1];
    disableAdaptiveBitrate = false;
    verticalFlip = false;
    return var6;
  }
  
  public boolean func_152836_a(VideoParams p_152836_1_)
  {
    if ((p_152836_1_ != null) && (func_152857_n()))
    {
      field_152881_q = p_152836_1_.clone();
      field_152882_r = new AudioParams();
      field_152882_r.audioEnabled = ((field_152871_g) && (func_152848_y()));
      field_152882_r.enableMicCapture = field_152882_r.audioEnabled;
      field_152882_r.enablePlaybackCapture = field_152882_r.audioEnabled;
      field_152882_r.enablePassthroughAudio = false;
      
      if (!func_152823_L())
      {
        field_152881_q = null;
        field_152882_r = null;
        return false;
      }
      

      ErrorCode var2 = field_152873_i.start(p_152836_1_, field_152882_r, field_152884_t, StartFlags.None, true);
      
      if (ErrorCode.failed(var2))
      {
        func_152831_M();
        String var3 = ErrorCode.getString(var2);
        func_152820_d(String.format("Error while starting to broadcast: %s", new Object[] { var3 }));
        field_152881_q = null;
        field_152882_r = null;
        return false;
      }
      

      func_152827_a(BroadcastState.Starting);
      return true;
    }
    



    return false;
  }
  

  public boolean func_152819_E()
  {
    if (!isBroadcasting())
    {
      return false;
    }
    

    ErrorCode var1 = field_152873_i.stop(true);
    
    if (ErrorCode.failed(var1))
    {
      String var2 = ErrorCode.getString(var1);
      func_152820_d(String.format("Error while stopping the broadcast: %s", new Object[] { var2 }));
      return false;
    }
    

    func_152827_a(BroadcastState.Stopping);
    return ErrorCode.succeeded(var1);
  }
  


  public boolean func_152847_F()
  {
    if (!isBroadcasting())
    {
      return false;
    }
    

    ErrorCode var1 = field_152873_i.pauseVideo();
    
    if (ErrorCode.failed(var1))
    {
      func_152819_E();
      String var2 = ErrorCode.getString(var1);
      func_152820_d(String.format("Error pausing stream: %s\n", new Object[] { var2 }));
    }
    else
    {
      func_152827_a(BroadcastState.Paused);
    }
    
    return ErrorCode.succeeded(var1);
  }
  

  public boolean func_152854_G()
  {
    if (!isBroadcastPaused())
    {
      return false;
    }
    

    func_152827_a(BroadcastState.Broadcasting);
    return true;
  }
  

  public boolean func_152840_a(String p_152840_1_, long p_152840_2_, String p_152840_4_, String p_152840_5_)
  {
    ErrorCode var6 = field_152873_i.sendActionMetaData(field_152885_u, p_152840_1_, p_152840_2_, p_152840_4_, p_152840_5_);
    
    if (ErrorCode.failed(var6))
    {
      String var7 = ErrorCode.getString(var6);
      func_152820_d(String.format("Error while sending meta data: %s\n", new Object[] { var7 }));
      return false;
    }
    

    return true;
  }
  

  public long func_177946_b(String p_177946_1_, long p_177946_2_, String p_177946_4_, String p_177946_5_)
  {
    long var6 = field_152873_i.sendStartSpanMetaData(field_152885_u, p_177946_1_, p_177946_2_, p_177946_4_, p_177946_5_);
    
    if (var6 == -1L)
    {
      func_152820_d(String.format("Error in SendStartSpanMetaData\n", new Object[0]));
    }
    
    return var6;
  }
  
  public boolean func_177947_a(String p_177947_1_, long p_177947_2_, long p_177947_4_, String p_177947_6_, String p_177947_7_)
  {
    if (p_177947_4_ == -1L)
    {
      func_152820_d(String.format("Invalid sequence id: %d\n", new Object[] { Long.valueOf(p_177947_4_) }));
      return false;
    }
    

    ErrorCode var8 = field_152873_i.sendEndSpanMetaData(field_152885_u, p_177947_1_, p_177947_2_, p_177947_4_, p_177947_6_, p_177947_7_);
    
    if (ErrorCode.failed(var8))
    {
      String var9 = ErrorCode.getString(var8);
      func_152820_d(String.format("Error in SendStopSpanMetaData: %s\n", new Object[] { var9 }));
      return false;
    }
    

    return true;
  }
  


  protected void func_152827_a(BroadcastState p_152827_1_)
  {
    if (p_152827_1_ != broadcastState)
    {
      broadcastState = p_152827_1_;
      
      try
      {
        if (field_152867_c != null)
        {
          field_152867_c.func_152891_a(p_152827_1_);
        }
      }
      catch (Exception var3)
      {
        func_152820_d(var3.toString());
      }
    }
  }
  
  public void func_152821_H()
  {
    if ((field_152873_i != null) && (field_152876_l))
    {
      ErrorCode var1 = field_152873_i.pollTasks();
      func_152853_a(var1);
      
      if (isIngestTesting())
      {
        field_152860_A.func_153041_j();
        
        if (field_152860_A.func_153032_e())
        {
          field_152860_A = null;
          func_152827_a(BroadcastState.ReadyToBroadcast);
        }
      }
      


      switch (SwitchBroadcastState.field_177773_a[broadcastState.ordinal()])
      {
      case 1: 
        func_152827_a(BroadcastState.LoggingIn);
        var1 = field_152873_i.login(field_152885_u);
        
        if (ErrorCode.failed(var1))
        {
          String var2 = ErrorCode.getString(var1);
          func_152820_d(String.format("Error in TTV_Login: %s\n", new Object[] { var2 }));
        }
        
        break;
      
      case 2: 
        func_152827_a(BroadcastState.FindingIngestServer);
        var1 = field_152873_i.getIngestServers(field_152885_u);
        
        if (ErrorCode.failed(var1))
        {
          func_152827_a(BroadcastState.LoggedIn);
          String var2 = ErrorCode.getString(var1);
          func_152820_d(String.format("Error in TTV_GetIngestServers: %s\n", new Object[] { var2 }));
        }
        
        break;
      
      case 3: 
        func_152827_a(BroadcastState.ReadyToBroadcast);
        var1 = field_152873_i.getUserInfo(field_152885_u);
        
        if (ErrorCode.failed(var1))
        {
          String var2 = ErrorCode.getString(var1);
          func_152820_d(String.format("Error in TTV_GetUserInfo: %s\n", new Object[] { var2 }));
        }
        
        func_152835_I();
        var1 = field_152873_i.getArchivingState(field_152885_u);
        
        if (ErrorCode.failed(var1))
        {
          String var2 = ErrorCode.getString(var1);
          func_152820_d(String.format("Error in TTV_GetArchivingState: %s\n", new Object[] { var2 }));
        }
        
        break;
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
        func_152835_I();
      }
    }
  }
  
  protected void func_152835_I()
  {
    long var1 = System.nanoTime();
    long var3 = (var1 - field_152890_z) / 1000000000L;
    
    if (var3 >= 30L)
    {
      field_152890_z = var1;
      ErrorCode var5 = field_152873_i.getStreamInfo(field_152885_u, field_152880_p);
      
      if (ErrorCode.failed(var5))
      {
        String var6 = ErrorCode.getString(var5);
        func_152820_d(String.format("Error in TTV_GetStreamInfo: %s", new Object[] { var6 }));
      }
    }
  }
  
  public IngestServerTester func_152838_J()
  {
    if ((func_152857_n()) && (field_152883_s != null))
    {
      if (isIngestTesting())
      {
        return null;
      }
      

      field_152860_A = new IngestServerTester(field_152873_i, field_152883_s);
      field_152860_A.func_176004_j();
      func_152827_a(BroadcastState.IngestTesting);
      return field_152860_A;
    }
    


    return null;
  }
  

  protected boolean func_152823_L()
  {
    for (int var1 = 0; var1 < 3; var1++)
    {
      FrameBuffer var2 = field_152873_i.allocateFrameBuffer(field_152881_q.outputWidth * field_152881_q.outputHeight * 4);
      
      if (!var2.getIsValid())
      {
        func_152820_d(String.format("Error while allocating frame buffer", new Object[0]));
        return false;
      }
      
      field_152874_j.add(var2);
      field_152875_k.add(var2);
    }
    
    return true;
  }
  
  protected void func_152831_M()
  {
    for (int var1 = 0; var1 < field_152874_j.size(); var1++)
    {
      FrameBuffer var2 = (FrameBuffer)field_152874_j.get(var1);
      var2.free();
    }
    
    field_152875_k.clear();
    field_152874_j.clear();
  }
  
  public FrameBuffer func_152822_N()
  {
    if (field_152875_k.size() == 0)
    {
      func_152820_d(String.format("Out of free buffers, this should never happen", new Object[0]));
      return null;
    }
    

    FrameBuffer var1 = (FrameBuffer)field_152875_k.get(field_152875_k.size() - 1);
    field_152875_k.remove(field_152875_k.size() - 1);
    return var1;
  }
  

  public void func_152846_a(FrameBuffer p_152846_1_)
  {
    try
    {
      field_152873_i.captureFrameBuffer_ReadPixels(p_152846_1_);
    }
    catch (Throwable var5)
    {
      CrashReport var3 = CrashReport.makeCrashReport(var5, "Trying to submit a frame to Twitch");
      CrashReportCategory var4 = var3.makeCategory("Broadcast State");
      var4.addCrashSection("Last reported errors", Arrays.toString(field_152862_C.func_152756_c()));
      var4.addCrashSection("Buffer", p_152846_1_);
      var4.addCrashSection("Free buffer count", Integer.valueOf(field_152875_k.size()));
      var4.addCrashSection("Capture buffer count", Integer.valueOf(field_152874_j.size()));
      throw new ReportedException(var3);
    }
  }
  
  public ErrorCode func_152859_b(FrameBuffer p_152859_1_)
  {
    if (isBroadcastPaused())
    {
      func_152854_G();
    }
    else if (!isBroadcasting())
    {
      return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
    }
    
    ErrorCode var2 = field_152873_i.submitVideoFrame(p_152859_1_);
    
    if (var2 != ErrorCode.TTV_EC_SUCCESS)
    {
      String var3 = ErrorCode.getString(var2);
      
      if (ErrorCode.succeeded(var2))
      {
        func_152832_e(String.format("Warning in SubmitTexturePointer: %s\n", new Object[] { var3 }));
      }
      else
      {
        func_152820_d(String.format("Error in SubmitTexturePointer: %s\n", new Object[] { var3 }));
        func_152819_E();
      }
      
      if (field_152867_c != null)
      {
        field_152867_c.func_152893_b(var2);
      }
    }
    
    return var2;
  }
  
  protected boolean func_152853_a(ErrorCode p_152853_1_)
  {
    if (ErrorCode.failed(p_152853_1_))
    {
      func_152820_d(ErrorCode.getString(p_152853_1_));
      return false;
    }
    

    return true;
  }
  

  protected void func_152820_d(String p_152820_1_)
  {
    field_152863_D = p_152820_1_;
    field_152862_C.func_152757_a("<Error> " + p_152820_1_);
    logger.error(TwitchStream.field_152949_a, "[Broadcast controller] {}", new Object[] { p_152820_1_ });
  }
  
  protected void func_152832_e(String p_152832_1_)
  {
    field_152862_C.func_152757_a("<Warning> " + p_152832_1_);
    logger.warn(TwitchStream.field_152949_a, "[Broadcast controller] {}", new Object[] { p_152832_1_ });
  }
  
  public static abstract interface BroadcastListener
  {
    public abstract void func_152900_a(ErrorCode paramErrorCode, AuthToken paramAuthToken);
    
    public abstract void func_152897_a(ErrorCode paramErrorCode);
    
    public abstract void func_152898_a(ErrorCode paramErrorCode, GameInfo[] paramArrayOfGameInfo);
    
    public abstract void func_152891_a(BroadcastController.BroadcastState paramBroadcastState);
    
    public abstract void func_152895_a();
    
    public abstract void func_152894_a(StreamInfo paramStreamInfo);
    
    public abstract void func_152896_a(IngestList paramIngestList);
    
    public abstract void func_152893_b(ErrorCode paramErrorCode);
    
    public abstract void func_152899_b();
    
    public abstract void func_152901_c();
    
    public abstract void func_152892_c(ErrorCode paramErrorCode);
  }
  
  public static enum BroadcastState
  {
    Uninitialized("Uninitialized", 0), 
    Initialized("Initialized", 1), 
    Authenticating("Authenticating", 2), 
    Authenticated("Authenticated", 3), 
    LoggingIn("LoggingIn", 4), 
    LoggedIn("LoggedIn", 5), 
    FindingIngestServer("FindingIngestServer", 6), 
    ReceivedIngestServers("ReceivedIngestServers", 7), 
    ReadyToBroadcast("ReadyToBroadcast", 8), 
    Starting("Starting", 9), 
    Broadcasting("Broadcasting", 10), 
    Stopping("Stopping", 11), 
    Paused("Paused", 12), 
    IngestTesting("IngestTesting", 13);
    
    private static final BroadcastState[] $VALUES = { Uninitialized, Initialized, Authenticating, Authenticated, LoggingIn, LoggedIn, FindingIngestServer, ReceivedIngestServers, ReadyToBroadcast, Starting, Broadcasting, Stopping, Paused, IngestTesting };
    private static final String __OBFID = "CL_00001820";
    
    private BroadcastState(String p_i1025_1_, int p_i1025_2_) {}
  }
  
  static final class SwitchBroadcastState
  {
    static final int[] field_177773_a = new int[BroadcastController.BroadcastState.values().length];
    private static final String __OBFID = "CL_00001821";
    
    static
    {
      try
      {
        field_177773_a[BroadcastController.BroadcastState.Authenticated.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.LoggedIn.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.ReceivedIngestServers.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Starting.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Stopping.ordinal()] = 5;
      }
      catch (NoSuchFieldError localNoSuchFieldError5) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.FindingIngestServer.ordinal()] = 6;
      }
      catch (NoSuchFieldError localNoSuchFieldError6) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Authenticating.ordinal()] = 7;
      }
      catch (NoSuchFieldError localNoSuchFieldError7) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Initialized.ordinal()] = 8;
      }
      catch (NoSuchFieldError localNoSuchFieldError8) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Uninitialized.ordinal()] = 9;
      }
      catch (NoSuchFieldError localNoSuchFieldError9) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.IngestTesting.ordinal()] = 10;
      }
      catch (NoSuchFieldError localNoSuchFieldError10) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Paused.ordinal()] = 11;
      }
      catch (NoSuchFieldError localNoSuchFieldError11) {}
      



      try
      {
        field_177773_a[BroadcastController.BroadcastState.Broadcasting.ordinal()] = 12;
      }
      catch (NoSuchFieldError localNoSuchFieldError12) {}
    }
    
    SwitchBroadcastState() {}
  }
}
