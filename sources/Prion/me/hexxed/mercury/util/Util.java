package me.hexxed.mercury.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserMigratedException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.web.WebUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util
{
  private static ArrayList<TimeScheduledPacket> packetQueue = new ArrayList();
  public static Minecraft mc = Minecraft.getMinecraft();
  private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
  private static final Logger logger = LogManager.getLogger();
  private NetworkManager networkManager;
  
  public Util() {}
  
  public static void updatePacketQueue() { if (packetQueue.isEmpty()) {
      return;
    }
    
    ArrayList<TimeScheduledPacket> remove = null;
    for (TimeScheduledPacket tsp : packetQueue) {
      if (tsp.shouldSend())
      {
        if (remove == null) {
          remove = new ArrayList();
        }
        mcthePlayer.sendQueue.addToSendQueue(tsp.getPacket());
        remove.add(tsp);
      }
    }
    if ((remove != null) && (!remove.isEmpty())) {
      for (TimeScheduledPacket tsp : remove) {
        packetQueue.remove(tsp);
      }
    }
  }
  






















































































  public static double eval(String str)
  {
    new Object()
    {
      int pos = -1;
      int c;
      
      void eatChar() { c = (++pos < length() ? charAt(pos) : -1); }
      
      void eatSpace()
      {
        while (Character.isWhitespace(c)) eatChar();
      }
      
      double parse() {
        eatChar();
        double v = parseExpression();
        if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
        return v;
      }
      





      double parseExpression()
      {
        double v = parseTerm();
        for (;;) {
          eatSpace();
          if (c == 43) {
            eatChar();
            v += parseTerm();
          } else { if (c != 45) break;
            eatChar();
            v -= parseTerm();
          } }
        return v;
      }
      

      double parseTerm()
      {
        double v = parseFactor();
        for (;;) {
          eatSpace();
          if (c == 47) {
            eatChar();
            v /= parseFactor();
          } else { if ((c != 42) && (c != 40)) break;
            if (c == 42) eatChar();
            v *= parseFactor();
          } }
        return v;
      }
      


      double parseFactor()
      {
        boolean negate = false;
        eatSpace();
        double v; if (c == 40) {
          eatChar();
          double v = parseExpression();
          if (c == 41) eatChar();
        } else {
          if ((c == 43) || (c == 45)) {
            negate = c == 45;
            eatChar();
            eatSpace();
          }
          StringBuilder sb = new StringBuilder();
          while (((c >= 48) && (c <= 57)) || (c == 46)) {
            sb.append((char)c);
            eatChar();
          }
          if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
          v = Double.parseDouble(sb.toString());
        }
        eatSpace();
        if (c == 94) {
          eatChar();
          v = Math.pow(v, parseFactor());
        }
        if (negate) v = -v;
        return v;
      }
    }.parse();
  }
  


  public static void sendPacketWithDelay(Packet p, long delayInMilliseconds)
  {
    packetQueue.add(new TimeScheduledPacket(p, delayInMilliseconds));
  }
  
  private static class TimeScheduledPacket
  {
    private TaskTimeTracker ttt;
    private Packet thePacket;
    private long theDelay;
    
    public TimeScheduledPacket(Packet packet, long delay)
    {
      ttt = new TaskTimeTracker();
      thePacket = packet;
      theDelay = delay;
    }
    
    public Packet getPacket()
    {
      return thePacket;
    }
    
    public boolean shouldSend()
    {
      return ttt.sleep(theDelay);
    }
  }
  
  public static void addChatMessage(String str) {
    Object chat = new ChatComponentText(str);
    if (str != null) {
      Mercury.getInstance();getMinecraftingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
    }
  }
  
  public static void sendInfo(String str) {
    addChatMessage("§3[" + Mercury.getInstance().getShortName() + "] §7" + str);
  }
  













  public static void damagePlayer(int amount)
  {
    for (int i = 0; i < 4; i++) {
      getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMinecraftthePlayer.posX, getMinecraftthePlayer.posY + 1.01D, getMinecraftthePlayer.posZ, false));
      

      getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMinecraftthePlayer.posX, getMinecraftthePlayer.posY, getMinecraftthePlayer.posZ, false));
    }
    



    getMinecraftthePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMinecraftthePlayer.posX, getMinecraftthePlayer.posY + 0.4D, getMinecraftthePlayer.posZ, false));
  }
  
  public static Integer getRandomColor() { Random rnd = new Random();
    int intVal = rnd.nextInt(16777216);
    return Integer.valueOf(intVal);
  }
  
  public static int getDistanceToGround()
  {
    int i2 = -1;
    Minecraft mc = Minecraft.getMinecraft();
    for (int dist = 0; dist < 256; dist++)
    {
      BlockPos bpos = new BlockPos(thePlayer.posX, thePlayer.posY - dist, thePlayer.posZ);
      Block block = theWorld.getBlockState(bpos).getBlock();
      if (!(block instanceof BlockAir))
      {
        i2 = dist;
      }
    }
    
    return i2;
  }
  
  public static int getNextStandableBlockHeight() {
    Minecraft mc = Minecraft.getMinecraft();
    int y = (int)thePlayer.posY;
    int dist = 0;
    int result = -1;
    while (result == -1) {
      BlockPos bpos = new BlockPos(thePlayer.posX, thePlayer.posY - dist, thePlayer.posZ);
      Block block = theWorld.getBlockState(bpos).getBlock();
      if (!(block instanceof BlockAir)) {
        result = y - dist;
        return result;
      }
      dist++;
    }
    return -1;
  }
  
  public static boolean login(String username, String password)
  {
    WebUtil.wipeCurrentUser();
    YggdrasilUserAuthentication auth = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()), com.mojang.authlib.Agent.MINECRAFT);
    auth.setUsername(username);
    auth.setPassword(password);
    try {
      auth.logIn();
      getMinecraftsession = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "legacy");
      return true;
    } catch (AuthenticationException e) {
      if (((e instanceof UserMigratedException)) || ((e instanceof InvalidCredentialsException)))
      {
        getMinecraftsession = new Session(username, UUID.randomUUID().toString(), "-", "legacy");
        return false;
      }
      
      getMinecraftsession = new Session(username, UUID.randomUUID().toString(), "-", "legacy");
      sendInfo("Couldn't login, is mojang down?"); }
    return true;
  }
  

  public static Double distance(double x1, double y1, double z1, double x2, double y2, double z2)
  {
    Double distance = null;
    distance = Double.valueOf(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)));
    return distance;
  }
  
  public static Block getBlock(double posX, double posY, double posZ)
  {
    BlockPos bpos = new BlockPos(posX, posY, posZ);
    Block block = getMinecrafttheWorld.getBlockState(bpos).getBlock();
    return block;
  }
  
  public static void connect(String ip, int port)
  {
    GuiConnecting gui = new GuiConnecting(null, mc, ip, port);
    mccurrentScreen = gui;
  }
  
  public static boolean isInsideBlock() {
    for (int x = MathHelper.floor_double(mcthePlayer.boundingBox.minX); x < 
          MathHelper.floor_double(mcthePlayer.boundingBox.maxX) + 1; x++) {
      for (int y = MathHelper.floor_double(mcthePlayer.boundingBox.minY); y < 
            MathHelper.floor_double(mcthePlayer.boundingBox.maxY) + 1; y++)
        for (int z = MathHelper.floor_double(mcthePlayer.boundingBox.minZ); z < 
              MathHelper.floor_double(mcthePlayer.boundingBox.maxZ) + 1; z++) {
          Block block = getMinecrafttheWorld
            .getBlockState(new BlockPos(x, y, z)).getBlock();
          if ((block != null) && (!(block instanceof BlockAir)))
          {


            AxisAlignedBB boundingBox = block
              .getCollisionBoundingBox(mctheWorld, new BlockPos(x, y, z), mctheWorld.getBlockState(new BlockPos(x, y, z)));
            if ((boundingBox != null) && 
              (mcthePlayer.boundingBox.intersectsWith(boundingBox)))
              return true;
          }
        }
    }
    return false;
  }
  
  public static boolean getColliding(int i) {
    if (!ModuleManager.getModByName("Jesus").isEnabled()) {
      return false;
    }
    int mx = i;
    int mz = i;
    int max = i;
    int maz = i;
    
    if (getPlayermotionX > 0.01D) {
      mx = 0;
    } else if (getPlayermotionX < -0.01D) {
      max = 0;
    }
    if (getPlayermotionZ > 0.01D) {
      mz = 0;
    } else if (getPlayermotionZ < -0.01D) {
      maz = 0;
    }
    
    int xmin = MathHelper.floor_double(getPlayergetEntityBoundingBoxminX - mx);
    int ymin = MathHelper.floor_double(getPlayergetEntityBoundingBoxminY - 1.0D);
    int zmin = MathHelper.floor_double(getPlayergetEntityBoundingBoxminZ - mz);
    int xmax = MathHelper.floor_double(getPlayergetEntityBoundingBoxminX + max);
    int ymax = MathHelper.floor_double(getPlayergetEntityBoundingBoxminY + 1.0D);
    int zmax = MathHelper.floor_double(getPlayergetEntityBoundingBoxminZ + maz);
    for (int x = xmin; x <= xmax; x++) {
      for (int y = ymin; y <= ymax; y++) {
        for (int z = zmin; z <= zmax; z++) {
          Block block = getBlock(x, y, z);
          if (((block instanceof BlockLiquid)) && (!getPlayer().isInsideOfMaterial(Material.lava)) && 
            (!getPlayer().isInsideOfMaterial(Material.water))) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public static EntityPlayerSP getPlayer() {
    return getMinecraftthePlayer;
  }
}
