package net.minecraft.client.stream;

import com.google.common.collect.Lists;
import java.io.PrintStream;
import java.util.List;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.RTMPState;
import tv.twitch.broadcast.StatType;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.VideoParams;

public class IngestServerTester
{
  protected IngestTestListener field_153044_b = null;
  protected Stream field_153045_c = null;
  protected IngestList field_153046_d = null;
  protected IngestTestState field_153047_e;
  protected long field_153048_f;
  protected long field_153049_g;
  protected long field_153050_h;
  protected RTMPState field_153051_i;
  protected VideoParams field_153052_j;
  protected AudioParams field_153053_k;
  protected long field_153054_l;
  protected List field_153055_m;
  protected boolean field_153056_n;
  protected IStreamCallbacks field_153057_o;
  protected IStatCallbacks field_153058_p;
  protected IngestServer field_153059_q;
  protected boolean field_153060_r;
  protected boolean field_153061_s;
  protected int field_153062_t;
  protected int field_153063_u;
  protected long field_153064_v;
  protected float field_153065_w;
  protected float field_153066_x;
  protected boolean field_176009_x;
  protected boolean field_176008_y;
  protected boolean field_176007_z;
  protected IStreamCallbacks field_176005_A;
  protected IStatCallbacks field_176006_B;
  private static final String __OBFID = "CL_00001816";
  
  public void func_153042_a(IngestTestListener p_153042_1_)
  {
    field_153044_b = p_153042_1_;
  }
  
  public IngestServer func_153040_c()
  {
    return field_153059_q;
  }
  
  public int func_153028_p()
  {
    return field_153062_t;
  }
  
  public boolean func_153032_e()
  {
    return (field_153047_e == IngestTestState.Finished) || (field_153047_e == IngestTestState.Cancelled) || (field_153047_e == IngestTestState.Failed);
  }
  
  public float func_153030_h()
  {
    return field_153066_x;
  }
  
  public IngestServerTester(Stream p_i1019_1_, IngestList p_i1019_2_)
  {
    field_153047_e = IngestTestState.Uninitalized;
    field_153048_f = 8000L;
    field_153049_g = 2000L;
    field_153050_h = 0L;
    field_153051_i = RTMPState.Invalid;
    field_153052_j = null;
    field_153053_k = null;
    field_153054_l = 0L;
    field_153055_m = null;
    field_153056_n = false;
    field_153057_o = null;
    field_153058_p = null;
    field_153059_q = null;
    field_153060_r = false;
    field_153061_s = false;
    field_153062_t = -1;
    field_153063_u = 0;
    field_153064_v = 0L;
    field_153065_w = 0.0F;
    field_153066_x = 0.0F;
    field_176009_x = false;
    field_176008_y = false;
    field_176007_z = false;
    field_176005_A = new IStreamCallbacks() { public void requestAuthTokenCallback(ErrorCode p_requestAuthTokenCallback_1_, AuthToken p_requestAuthTokenCallback_2_) {}
      
      public void loginCallback(ErrorCode p_loginCallback_1_, ChannelInfo p_loginCallback_2_) {}
      
      public void getIngestServersCallback(ErrorCode p_getIngestServersCallback_1_, IngestList p_getIngestServersCallback_2_) {}
      
      public void getUserInfoCallback(ErrorCode p_getUserInfoCallback_1_, UserInfo p_getUserInfoCallback_2_) {}
      
      public void getStreamInfoCallback(ErrorCode p_getStreamInfoCallback_1_, StreamInfo p_getStreamInfoCallback_2_) {}
      
      public void getArchivingStateCallback(ErrorCode p_getArchivingStateCallback_1_, ArchivingState p_getArchivingStateCallback_2_) {}
      public void runCommercialCallback(ErrorCode p_runCommercialCallback_1_) {}
      public void setStreamInfoCallback(ErrorCode p_setStreamInfoCallback_1_) {}
      public void getGameNameListCallback(ErrorCode p_getGameNameListCallback_1_, GameInfoList p_getGameNameListCallback_2_) {}
      public void bufferUnlockCallback(long p_bufferUnlockCallback_1_) {}
      public void startCallback(ErrorCode p_startCallback_1_) { field_176008_y = false;
        
        if (ErrorCode.succeeded(p_startCallback_1_))
        {
          field_176009_x = true;
          field_153054_l = System.currentTimeMillis();
          func_153034_a(IngestServerTester.IngestTestState.ConnectingToServer);
        }
        else
        {
          field_153056_n = false;
          func_153034_a(IngestServerTester.IngestTestState.DoneTestingServer);
        }
      }
      
      private static final String __OBFID = "CL_00002368";
      public void stopCallback(ErrorCode p_stopCallback_1_) { if (ErrorCode.failed(p_stopCallback_1_))
        {
          System.out.println("IngestTester.stopCallback failed to stop - " + field_153059_q.serverName + ": " + p_stopCallback_1_.toString());
        }
        
        field_176007_z = false;
        field_176009_x = false;
        func_153034_a(IngestServerTester.IngestTestState.DoneTestingServer);
        field_153059_q = null;
        
        if (field_153060_r)
        {
          func_153034_a(IngestServerTester.IngestTestState.Cancelling); } }
      
      public void sendActionMetaDataCallback(ErrorCode p_sendActionMetaDataCallback_1_) {}
      
      public void sendStartSpanMetaDataCallback(ErrorCode p_sendStartSpanMetaDataCallback_1_) {}
      
      public void sendEndSpanMetaDataCallback(ErrorCode p_sendEndSpanMetaDataCallback_1_) {} };
    field_176006_B = new IStatCallbacks()
    {
      private static final String __OBFID = "CL_00002367";
      
      public void statCallback(StatType p_statCallback_1_, long p_statCallback_2_) {
        switch (IngestServerTester.SwitchStatType.field_176003_a[p_statCallback_1_.ordinal()])
        {
        case 1: 
          field_153051_i = RTMPState.lookupValue((int)p_statCallback_2_);
          break;
        
        case 2: 
          field_153050_h = p_statCallback_2_;
        }
      }
    };
    field_153045_c = p_i1019_1_;
    field_153046_d = p_i1019_2_;
  }
  
  public void func_176004_j()
  {
    if (field_153047_e == IngestTestState.Uninitalized)
    {
      field_153062_t = 0;
      field_153060_r = false;
      field_153061_s = false;
      field_176009_x = false;
      field_176008_y = false;
      field_176007_z = false;
      field_153058_p = field_153045_c.getStatCallbacks();
      field_153045_c.setStatCallbacks(field_176006_B);
      field_153057_o = field_153045_c.getStreamCallbacks();
      field_153045_c.setStreamCallbacks(field_176005_A);
      field_153052_j = new VideoParams();
      field_153052_j.targetFps = 60;
      field_153052_j.maxKbps = 3500;
      field_153052_j.outputWidth = 1280;
      field_153052_j.outputHeight = 720;
      field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
      field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
      field_153052_j.disableAdaptiveBitrate = true;
      field_153052_j.verticalFlip = false;
      field_153045_c.getDefaultParams(field_153052_j);
      field_153053_k = new AudioParams();
      field_153053_k.audioEnabled = false;
      field_153053_k.enableMicCapture = false;
      field_153053_k.enablePlaybackCapture = false;
      field_153053_k.enablePassthroughAudio = false;
      field_153055_m = Lists.newArrayList();
      byte var1 = 3;
      
      for (int var2 = 0; var2 < var1; var2++)
      {
        FrameBuffer var3 = field_153045_c.allocateFrameBuffer(field_153052_j.outputWidth * field_153052_j.outputHeight * 4);
        
        if (!var3.getIsValid())
        {
          func_153031_o();
          func_153034_a(IngestTestState.Failed);
          return;
        }
        
        field_153055_m.add(var3);
        field_153045_c.randomizeFrameBuffer(var3);
      }
      
      func_153034_a(IngestTestState.Starting);
      field_153054_l = System.currentTimeMillis();
    }
  }
  
  public void func_153041_j()
  {
    if ((!func_153032_e()) && (field_153047_e != IngestTestState.Uninitalized))
    {
      if ((!field_176008_y) && (!field_176007_z))
      {
        switch (SwitchStatType.field_176002_b[field_153047_e.ordinal()])
        {
        case 1: 
        case 2: 
          if (field_153059_q != null)
          {
            if ((field_153061_s) || (!field_153056_n))
            {
              field_153059_q.bitrateKbps = 0.0F;
            }
            
            func_153035_b(field_153059_q);
          }
          else
          {
            field_153054_l = 0L;
            field_153061_s = false;
            field_153056_n = true;
            
            if (field_153047_e != IngestTestState.Starting)
            {
              field_153062_t += 1;
            }
            
            if (field_153062_t < field_153046_d.getServers().length)
            {
              field_153059_q = field_153046_d.getServers()[field_153062_t];
              func_153036_a(field_153059_q);
            }
            else
            {
              func_153034_a(IngestTestState.Finished);
            }
          }
          
          break;
        
        case 3: 
        case 4: 
          func_153029_c(field_153059_q);
          break;
        
        case 5: 
          func_153034_a(IngestTestState.Cancelled);
        }
        
        func_153038_n();
        
        if ((field_153047_e == IngestTestState.Cancelled) || (field_153047_e == IngestTestState.Finished))
        {
          func_153031_o();
        }
      }
    }
  }
  
  public void func_153039_l()
  {
    if ((!func_153032_e()) && (!field_153060_r))
    {
      field_153060_r = true;
      
      if (field_153059_q != null)
      {
        field_153059_q.bitrateKbps = 0.0F;
      }
    }
  }
  
  protected boolean func_153036_a(IngestServer p_153036_1_)
  {
    field_153056_n = true;
    field_153050_h = 0L;
    field_153051_i = RTMPState.Idle;
    field_153059_q = p_153036_1_;
    field_176008_y = true;
    func_153034_a(IngestTestState.ConnectingToServer);
    ErrorCode var2 = field_153045_c.start(field_153052_j, field_153053_k, p_153036_1_, tv.twitch.broadcast.StartFlags.TTV_Start_BandwidthTest, true);
    
    if (ErrorCode.failed(var2))
    {
      field_176008_y = false;
      field_153056_n = false;
      func_153034_a(IngestTestState.DoneTestingServer);
      return false;
    }
    

    field_153064_v = field_153050_h;
    bitrateKbps = 0.0F;
    field_153063_u = 0;
    return true;
  }
  

  protected void func_153035_b(IngestServer p_153035_1_)
  {
    if (field_176008_y)
    {
      field_153061_s = true;
    }
    else if (field_176009_x)
    {
      field_176007_z = true;
      ErrorCode var2 = field_153045_c.stop(true);
      
      if (ErrorCode.failed(var2))
      {
        field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
        System.out.println("Stop failed: " + var2.toString());
      }
      
      field_153045_c.pollStats();
    }
    else
    {
      field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
    }
  }
  
  protected long func_153037_m()
  {
    return System.currentTimeMillis() - field_153054_l;
  }
  
  protected void func_153038_n()
  {
    float var1 = (float)func_153037_m();
    
    switch (SwitchStatType.field_176002_b[field_153047_e.ordinal()])
    {
    case 1: 
    case 3: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
      field_153066_x = 0.0F;
      break;
    
    case 2: 
      field_153066_x = 1.0F;
      break;
    
    case 4: 
    case 5: 
    default: 
      field_153066_x = (var1 / (float)field_153048_f);
    }
    
    switch (SwitchStatType.field_176002_b[field_153047_e.ordinal()])
    {
    case 7: 
    case 8: 
    case 9: 
      field_153065_w = 1.0F;
      break;
    
    default: 
      field_153065_w = (field_153062_t / field_153046_d.getServers().length);
      field_153065_w += field_153066_x / field_153046_d.getServers().length;
    }
  }
  
  protected boolean func_153029_c(IngestServer p_153029_1_)
  {
    if ((!field_153061_s) && (!field_153060_r) && (func_153037_m() < field_153048_f))
    {
      if ((!field_176008_y) && (!field_176007_z))
      {
        ErrorCode var2 = field_153045_c.submitVideoFrame((FrameBuffer)field_153055_m.get(field_153063_u));
        
        if (ErrorCode.failed(var2))
        {
          field_153056_n = false;
          func_153034_a(IngestTestState.DoneTestingServer);
          return false;
        }
        

        field_153063_u = ((field_153063_u + 1) % field_153055_m.size());
        field_153045_c.pollStats();
        
        if (field_153051_i == RTMPState.SendVideo)
        {
          func_153034_a(IngestTestState.TestingServer);
          long var3 = func_153037_m();
          
          if ((var3 > 0L) && (field_153050_h > field_153064_v))
          {
            bitrateKbps = ((float)(field_153050_h * 8L) / (float)func_153037_m());
            field_153064_v = field_153050_h;
          }
        }
        
        return true;
      }
      


      return true;
    }
    


    func_153034_a(IngestTestState.DoneTestingServer);
    return true;
  }
  

  protected void func_153031_o()
  {
    field_153059_q = null;
    
    if (field_153055_m != null)
    {
      for (int var1 = 0; var1 < field_153055_m.size(); var1++)
      {
        ((FrameBuffer)field_153055_m.get(var1)).free();
      }
      
      field_153055_m = null;
    }
    
    if (field_153045_c.getStatCallbacks() == field_176006_B)
    {
      field_153045_c.setStatCallbacks(field_153058_p);
      field_153058_p = null;
    }
    
    if (field_153045_c.getStreamCallbacks() == field_176005_A)
    {
      field_153045_c.setStreamCallbacks(field_153057_o);
      field_153057_o = null;
    }
  }
  
  protected void func_153034_a(IngestTestState p_153034_1_)
  {
    if (p_153034_1_ != field_153047_e)
    {
      field_153047_e = p_153034_1_;
      
      if (field_153044_b != null)
      {
        field_153044_b.func_152907_a(this, p_153034_1_);
      }
    }
  }
  
  public static abstract interface IngestTestListener
  {
    public abstract void func_152907_a(IngestServerTester paramIngestServerTester, IngestServerTester.IngestTestState paramIngestTestState);
  }
  
  public static enum IngestTestState
  {
    Uninitalized("Uninitalized", 0), 
    Starting("Starting", 1), 
    ConnectingToServer("ConnectingToServer", 2), 
    TestingServer("TestingServer", 3), 
    DoneTestingServer("DoneTestingServer", 4), 
    Finished("Finished", 5), 
    Cancelling("Cancelling", 6), 
    Cancelled("Cancelled", 7), 
    Failed("Failed", 8);
    
    private static final IngestTestState[] $VALUES = { Uninitalized, Starting, ConnectingToServer, TestingServer, DoneTestingServer, Finished, Cancelling, Cancelled, Failed };
    
    private static final String __OBFID = "CL_00001814";
    
    private IngestTestState(String p_i1016_1_, int p_i1016_2_) {}
  }
  
  static final class SwitchStatType
  {
    static final int[] field_176003_a;
    static final int[] field_176002_b = new int[IngestServerTester.IngestTestState.values().length];
    private static final String __OBFID = "CL_00001815";
    
    static
    {
      try
      {
        field_176002_b[IngestServerTester.IngestTestState.Starting.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.DoneTestingServer.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.ConnectingToServer.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.TestingServer.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.Cancelling.ordinal()] = 5;
      }
      catch (NoSuchFieldError localNoSuchFieldError5) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.Uninitalized.ordinal()] = 6;
      }
      catch (NoSuchFieldError localNoSuchFieldError6) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.Finished.ordinal()] = 7;
      }
      catch (NoSuchFieldError localNoSuchFieldError7) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.Cancelled.ordinal()] = 8;
      }
      catch (NoSuchFieldError localNoSuchFieldError8) {}
      



      try
      {
        field_176002_b[IngestServerTester.IngestTestState.Failed.ordinal()] = 9;
      }
      catch (NoSuchFieldError localNoSuchFieldError9) {}
      



      field_176003_a = new int[StatType.values().length];
      
      try
      {
        field_176003_a[StatType.TTV_ST_RTMPSTATE.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError10) {}
      



      try
      {
        field_176003_a[StatType.TTV_ST_RTMPDATASENT.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError11) {}
    }
    
    SwitchStatType() {}
  }
}
