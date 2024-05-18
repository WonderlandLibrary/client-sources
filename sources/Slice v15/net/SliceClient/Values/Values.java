package net.SliceClient.Values;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;



public class Values
{
  private static Values values = new Values();
  
  public Values() {}
  
  public static Values getValues() { return values; }
  


  public double SlimeFly = 1.5D;
  public static int AutoSoupID = 282;
  public static int AutoSoupHealth = 14;
  public SwiftMode swiftmode = SwiftMode.COMBAT;
  public double StepHeight = 1.2D;
  public static float KillAuraSpeed = 15.3F;
  public static double KillAuraRange = 4.6D;
  public static double KillAuraAngle = 1020.0D;
  public static float GKillAuraSpeed = 13.0F;
  public static double GKillAuraRange = 4.5D;
  public static double GKillAuraAngle = 1020.0D;
  public static boolean AntiCheat = true;
  public String auramode = "Normal";
  public String phasemode = "None";
  public String speedmode = "None";
  public String jesusmode = "Normal";
  public String nametags = "One";
  public String clientcolor = "rainbow";
  public boolean noslowdown = true;
  public boolean flydamage = true;
  public int spd = 2;
  public String arraylist = "left";
  public double glidemotion = 0.0D;
  public static double distance = 6.0D;
  public static int cps = 180;
  public static boolean autoblock = false;
  public static boolean invisibles = false;
  public boolean criticals = false;
  public static int ticks = 10;
  public boolean tabgui = true;
  public boolean sortarraylist = true;
  public boolean premium = true;
  public double FlySpeed = 1.0D;
  public FlyMode flymode = FlyMode.NCP;
  public double glidespeed = 0.0D;
  public boolean shouldbypass = false;
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
  public boolean xray = false;
  public boolean convertpacket = false;
  public boolean invmove = true;
  public boolean ztbreath = true;
  public boolean ztpotion = false;
  public boolean ztfirionfreeze = false;
  public boolean safewalk = false;
  public AxisAlignedBB boundingBox = null;
  public boolean onlymotion = false;
  public int width = 0;
  public int height = 0;
  public String inboundmessage = "";
  public String outboundmessage = "";
  public static String nameprotect = "****";
  public float prepitch = 0.0F;
  public boolean fastcclimb = false;
  public static List<Integer> xrayblocks = new ArrayList();
  public String lastIp;
  public int lastPort;
  
  public static enum FlyMode
  {
    NORMAL,  TIGHT,  NCP,  GLIDE;
  }
}
