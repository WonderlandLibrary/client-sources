package me.hexxed.mercury.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class AimAssist
  extends Module
{
  public AimAssist()
  {
    super("AimAssist", 0, true, ModuleCategory.COMBAT);
  }
  
  private double range = 3.8D;
  private EntityPlayer curtarget;
  private List<EntityPlayer> targetlist = new ArrayList();
  
  public void onEnable()
  {
    curtarget = null;
  }
  
  public void onPostMotionUpdate()
  {
    targetlist.clear();
    for (EntityPlayer e : mc.theWorld.playerEntities) {
      if (isAttackable(e)) {
        targetlist.add(e);
      }
    }
    if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
      Entity entity = mc.objectMouseOver.entityHit;
      if ((entity instanceof EntityPlayer)) {
        curtarget = ((EntityPlayer)entity);
        return;
      }
    }
    if ((!targetlist.contains(curtarget)) && (curtarget != null)) {
      curtarget = null;
      return;
    }
    Random r = new Random();
    if (curtarget == null) return;
    float diff = mc.thePlayer.rotationYaw - getAngles(curtarget)[0] % 180.0F; EntityPlayerSP 
    

      tmp186_183 = mc.thePlayer;186183rotationYaw = ((float)(186183rotationYaw - (mc.thePlayer.rotationYaw - getAngles(curtarget)[0]) * 0.5D)); EntityPlayerSP 
      tmp229_226 = mc.thePlayer;229226rotationPitch = ((float)(229226rotationPitch - (mc.thePlayer.rotationPitch - getAngles(curtarget)[1]) * 0.5D));
  }
  
  public float[] getAngles(Entity entity)
  {
    float xDiff = (float)(posX - mc.thePlayer.posX);
    float yDiff = (float)(boundingBox.minY + entity.getEyeHeight() - mc.thePlayer.boundingBox.maxY);
    float zDiff = (float)(posZ - mc.thePlayer.posZ);
    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D - 90.0D);
    float pitch = (float)-Math.toDegrees(Math.atan(yDiff / Math.sqrt(zDiff * zDiff + xDiff * xDiff)));
    return new float[] {
      yaw, pitch };
  }
  

  private float updateRotation(float par1, float par2, float par3)
  {
    float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
    if (var4 > par3) {
      var4 = par3;
    }
    if (var4 < -par3) {
      var4 = -par3;
    }
    return par1 + var4;
  }
  
  private boolean isAttackable(Entity e) {
    if (e == null)
      return false;
    if ((e instanceof EntityPlayer)) {
      EntityPlayer p = (EntityPlayer)e;
      if (Friends.isFriend(e.getName())) return false;
      if ((!isDead) && (!p.isInvisible()) && (mc.thePlayer.getDistanceToEntity(p) <= range) && (mc.thePlayer.canEntityBeSeen(p)) && (p != mc.thePlayer)) {
        return true;
      }
    }
    
    return false;
  }
}
