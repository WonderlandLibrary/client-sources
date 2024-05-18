package me.hexxed.mercury.modulebase;

import com.memetix.mst.language.Language;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;

public class Values
{
  public Values() {}
  
  private static Values values = new Values();
  
  public static Values getValues() {
    return values; }
  

  public boolean premium = true;
  
  public double SlimeFly = 1.5D;
  
  public double FlySpeed = 1.0D;
  
  public FlyMode flymode = FlyMode.NORMAL;
  
  public SwiftMode swiftmode = SwiftMode.COMBAT;
  
  public double StepHeight = 1.2D;
  
  public double glidespeed = 0.07D;
  
  public boolean shouldbypass = false;
  
  public boolean fancyphase = false;
  
  public Integer bypasspackets = Integer.valueOf(0);
  
  public double horvelocity = 0.0D;
  
  public double vervelocity = 0.0D;
  
  public float jumpheight = 2.0F;
  
  public double motionX = 0.0D;
  
  public double motionY = 0.0D;
  
  public double motionZ = 0.0D;
  
  public float sprintspeed = 4.0F;
  
  public float walkspeed = 4.0F;
  
  public boolean bwsprint = true;
  
  public int ping = 0;
  
  public double distance = 3.8D;
  
  public int cps = 12;
  
  public boolean autoblock = false;
  
  public boolean invisibles = false;
  
  public boolean xray = false;
  
  public boolean convertpacket = false;
  
  public boolean invmove = true;
  
  public boolean invmore = false;
  
  public boolean ztbreath = true;
  
  public boolean ztpotion = false;
  
  public boolean ztfirionfreeze = false;
  
  public boolean safewalk = false;
  
  public boolean sortarraylist = true;
  
  public AxisAlignedBB boundingBox = null;
  
  public boolean onlymotion = false;
  
  public Language lang = Language.ENGLISH;
  
  public int width = 0;
  
  public int height = 0;
  
  public String inboundmessage = "";
  
  public String outboundmessage = "";
  
  public int ticks = 10;
  
  public float prepitch = 0.0F;
  
  public boolean fastcclimb = false;
  
  public static List<Integer> xrayblocks = new ArrayList();
  public String lastIp;
  
  public static enum FlyMode { NORMAL,  TIGHT,  NCP,  GLIDE;
  }
  
  public int lastPort;
  public static enum SwiftMode { TIMER,  COMBAT,  SANIC;
  }
}
