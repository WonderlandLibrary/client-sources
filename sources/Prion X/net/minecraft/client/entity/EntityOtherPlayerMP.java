package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOtherPlayerMP extends AbstractClientPlayer
{
  private boolean isItemInUse;
  private int otherPlayerMPPosRotationIncrements;
  private double otherPlayerMPX;
  private double otherPlayerMPY;
  private double otherPlayerMPZ;
  private double otherPlayerMPYaw;
  private double otherPlayerMPPitch;
  private static final String __OBFID = "CL_00000939";
  
  public EntityOtherPlayerMP(World worldIn, GameProfile p_i45075_2_)
  {
    super(worldIn, p_i45075_2_);
    stepHeight = 0.0F;
    noClip = true;
    field_71082_cx = 0.25F;
    renderDistanceWeight = 10.0D;
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    return true;
  }
  
  public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
  {
    otherPlayerMPX = p_180426_1_;
    otherPlayerMPY = p_180426_3_;
    otherPlayerMPZ = p_180426_5_;
    otherPlayerMPYaw = p_180426_7_;
    otherPlayerMPPitch = p_180426_8_;
    otherPlayerMPPosRotationIncrements = p_180426_9_;
  }
  



  public void onUpdate()
  {
    field_71082_cx = 0.0F;
    super.onUpdate();
    prevLimbSwingAmount = limbSwingAmount;
    double var1 = posX - prevPosX;
    double var3 = posZ - prevPosZ;
    float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0F;
    
    if (var5 > 1.0F)
    {
      var5 = 1.0F;
    }
    
    limbSwingAmount += (var5 - limbSwingAmount) * 0.4F;
    limbSwing += limbSwingAmount;
    
    if ((!isItemInUse) && (isEating()) && (inventory.mainInventory[inventory.currentItem] != null))
    {
      ItemStack var6 = inventory.mainInventory[inventory.currentItem];
      setItemInUse(inventory.mainInventory[inventory.currentItem], var6.getItem().getMaxItemUseDuration(var6));
      isItemInUse = true;
    }
    else if ((isItemInUse) && (!isEating()))
    {
      clearItemInUse();
      isItemInUse = false;
    }
  }
  




  public void onLivingUpdate()
  {
    if (otherPlayerMPPosRotationIncrements > 0)
    {
      double var1 = posX + (otherPlayerMPX - posX) / otherPlayerMPPosRotationIncrements;
      double var3 = posY + (otherPlayerMPY - posY) / otherPlayerMPPosRotationIncrements;
      double var5 = posZ + (otherPlayerMPZ - posZ) / otherPlayerMPPosRotationIncrements;
      

      for (double var7 = otherPlayerMPYaw - rotationYaw; var7 < -180.0D; var7 += 360.0D) {}
      



      while (var7 >= 180.0D)
      {
        var7 -= 360.0D;
      }
      
      rotationYaw = ((float)(rotationYaw + var7 / otherPlayerMPPosRotationIncrements));
      rotationPitch = ((float)(rotationPitch + (otherPlayerMPPitch - rotationPitch) / otherPlayerMPPosRotationIncrements));
      otherPlayerMPPosRotationIncrements -= 1;
      setPosition(var1, var3, var5);
      setRotation(rotationYaw, rotationPitch);
    }
    
    prevCameraYaw = cameraYaw;
    updateArmSwingProgress();
    float var9 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
    float var2 = (float)Math.atan(-motionY * 0.20000000298023224D) * 15.0F;
    
    if (var9 > 0.1F)
    {
      var9 = 0.1F;
    }
    
    if ((!onGround) || (getHealth() <= 0.0F))
    {
      var9 = 0.0F;
    }
    
    if ((onGround) || (getHealth() <= 0.0F))
    {
      var2 = 0.0F;
    }
    
    cameraYaw += (var9 - cameraYaw) * 0.4F;
    cameraPitch += (var2 - cameraPitch) * 0.8F;
  }
  



  public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn)
  {
    if (slotIn == 0)
    {
      inventory.mainInventory[inventory.currentItem] = itemStackIn;
    }
    else
    {
      inventory.armorInventory[(slotIn - 1)] = itemStackIn;
    }
  }
  






  public void addChatMessage(IChatComponent message)
  {
    getMinecraftingameGUI.getChatGUI().printChatMessage(message);
  }
  



  public boolean canCommandSenderUseCommand(int permissionLevel, String command)
  {
    return false;
  }
  
  public BlockPos getPosition()
  {
    return new BlockPos(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
  }
}
