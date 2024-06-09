package me.hexxed.mercury.modules;

import java.util.HashMap;
import java.util.Map;
import me.hexxed.mercury.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.util.IChatComponent;

public class CnC extends me.hexxed.mercury.modulebase.Module
{
  public CnC()
  {
    super("CnC", 0, true, me.hexxed.mercury.modulebase.ModuleCategory.COMBAT);
  }
  
  private long currentMS = 0L;
  private long lastShot = -1L;
  private long lastInvClick = -1L;
  private String team1Col = ChatColor.BLUE.toString();
  private String team2Col = ChatColor.DARK_RED.toString();
  private EntityPlayer target;
  private int myTeam;
  private Map<EntityPlayer, Integer> playerTeams;
  private ItemStack redDyeItemStack = new ItemStack(net.minecraft.init.Items.dye, 1, 1);
  
  public void onEnable()
  {
    playerTeams = new HashMap();
  }
  
  public boolean isValidTarget(EntityPlayer p) {
    return (p.isEntityAlive()) && 
      (!p.isInvisible()) && 
      (mc.thePlayer.canEntityBeSeen(p));
  }
  
  public double getTargetWeight(EntityPlayer p) { return -mc.thePlayer.getDistanceToEntity(p); }
  
  public void faceEntity(Entity entity) {
    float[] pitchYaw = getPitchYaw(entity);
    float newPitch = pitchYaw[0];
    float newYaw = pitchYaw[1];
    
    mc.thePlayer.rotationYaw = newYaw;
    mc.thePlayer.rotationPitch = newPitch;
  }
  
  public float[] getPitchYaw(Entity entity) { double xPred = 0.0D;
    double yPred = 0.0D;
    double zPred = 0.0D;
    double xPred1Tick = posX - lastTickPosX + motionX;
    double yPred1Tick = posY - lastTickPosY + motionY;
    double zPred1Tick = posZ - lastTickPosZ + motionZ;
    double ticksOfLag = new net.minecraft.client.network.NetworkPlayerInfo(mc.thePlayer.getGameProfile()).getResponseTime().intValue() * 1000 / 20;
    ticksOfLag += 2.0D;
    xPred = xPred1Tick * ticksOfLag;
    yPred = yPred1Tick * ticksOfLag;
    zPred = zPred1Tick * ticksOfLag;
    double x = posX + xPred - mc.thePlayer.posX;
    double y = posY + yPred - mc.thePlayer.posY;
    double z = posZ + zPred - mc.thePlayer.posZ;
    double helper = net.minecraft.util.MathHelper.sqrt_double(x * x + z * z);
    
    float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
    float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));
    if ((z < 0.0D) && (x < 0.0D)) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
    } else if ((z < 0.0D) && (x > 0.0D)) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
    }
    float[] floats = new float[2];
    floats[0] = newPitch;
    floats[1] = newYaw;
    return floats;
  }
  
  public void onPostUpdate()
  {
    playerTeams = new HashMap();
    myTeam = getTeamForPlayer(mc.thePlayer);
    for (EntityPlayer p : mc.theWorld.playerEntities) {
      playerTeams.put(p, Integer.valueOf(getTeamForPlayer(p)));
    }
    currentMS = (System.nanoTime() / 1000000L);
    if ((mc.thePlayer.inventory.currentItem < 2) && (hasDelayRun(500L)) && (!mc.thePlayer.getHeldItem().isItemDamaged()) && 
      (target != null) && (isValidTarget(target))) {
      mc.thePlayer.sendQueue.addToSendQueue(faceEntityPacketC05(target));
      mc.thePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
      lastShot = (System.nanoTime() / 1000000L);
    }
    
    if ((mc.thePlayer.openContainer != null) && ((mc.thePlayer.openContainer instanceof ContainerChest))) {
      ContainerChest chest = (ContainerChest)mc.thePlayer.openContainer;
      for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
        ItemStack stack = chest.getLowerChestInventory().getStackInSlot(i);
        if ((stack != null) && ((stack.getItem() instanceof net.minecraft.item.ItemDye)) && (hasDelayRun2(60L)) && 
          (stack.getMetadata() == redDyeItemStack.getMetadata())) {
          mc.playerController.windowClick(windowId, i, 0, 0, mc.thePlayer);
          mc.playerController.windowClick(windowId, i, 0, 0, mc.thePlayer);
          lastInvClick = (System.nanoTime() / 1000000L);
        }
      }
    }
  }
  

  public void onPreUpdate()
  {
    if (mc.thePlayer.inventory.currentItem < 2) {
      if (target == null) {
        double highestTargetWeight = Double.NEGATIVE_INFINITY;
        EntityPlayer highestTarget = null;
        for (EntityPlayer p : mc.theWorld.playerEntities) {
          if ((isValidTarget(p)) && 
            (((Integer)playerTeams.get(p)).intValue() != myTeam)) {
            double weight = getTargetWeight(p);
            if (weight > highestTargetWeight) {
              highestTargetWeight = weight;
              highestTarget = p;
            }
          }
        }
        
        if (highestTarget != null) {
          target = highestTarget;
        }
      }
      else if (!isValidTarget(target)) {
        target = null;
      }
    }
  }
  
  public int getTeamForPlayer(EntityPlayer p)
  {
    if (p.getDisplayName().getFormattedText().contains(team1Col))
      return 1;
    if (p.getDisplayName().getFormattedText().contains(team2Col)) {
      return 2;
    }
    return 0;
  }
  
  public C03PacketPlayer.C06PacketPlayerPosLook faceEntityPacketC06(Entity entity, double posX, double posY, double posZ) {
    float[] pitchYaw = getPitchYaw(entity);
    float newPitch = pitchYaw[0];
    float newYaw = pitchYaw[1];
    return new C03PacketPlayer.C06PacketPlayerPosLook(posX, posY, posZ, newYaw, newPitch, mc.thePlayer.onGround);
  }
  
  public C03PacketPlayer.C05PacketPlayerLook faceEntityPacketC05(Entity entity) { float[] pitchYaw = getPitchYaw(entity);
    float newPitch = pitchYaw[0];
    float newYaw = pitchYaw[1];
    return new C03PacketPlayer.C05PacketPlayerLook(newYaw, newPitch, mc.thePlayer.onGround);
  }
  
  private boolean hasDelayRun(long l) { return currentMS - lastShot >= l; }
  
  private boolean hasDelayRun2(long l) {
    return currentMS - lastInvClick >= l;
  }
}
