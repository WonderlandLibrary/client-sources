package net.augustus.utils;

import net.augustus.Augustus;
import net.augustus.events.EventAttackSlowdown;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

import java.util.ArrayList;

public class PlayerUtil implements MC, MM {
   public static ArrayList<ItemStack> arrayToArrayList(ItemStack[] itemStackArray) {
      ArrayList<ItemStack> itemStackList = new ArrayList<>();

      if (itemStackArray != null) {
         for (ItemStack itemStack : itemStackArray) {
            if (itemStack != null) {
               itemStackList.add(itemStack);
            }
         }
      }

      return itemStackList;
   }
   public static void sendChat(String msg) {
      String client = "§6[§9" + Augustus.getInstance().getName() + "§6] ";
      mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(client + "§7" + msg));
   }
   public static void verusdmg() {
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.001, mc.thePlayer.posZ, false));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
   }
   public static boolean isHoldingSword() {
      if (mc.thePlayer.getHeldItem() == null) return false;
      return (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword);
   }
   public static boolean isHoldingBow() {
      if (mc.thePlayer.getHeldItem() == null) return false;
      return (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow);
   }
   public static boolean isHoldingFood() {
      if (mc.thePlayer.getHeldItem() == null) return false;
      return (mc.thePlayer.getHeldItem().getItem() instanceof ItemFood || mc.thePlayer.getHeldItem().getItem() instanceof ItemBucketMilk || (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion && !(((ItemPotion) mc.thePlayer.getHeldItem().getItem()).isSplash(mc.thePlayer.getHeldItem().getMetadata()))));
   }
   public static boolean isUsingItemB() {
      if (mc.thePlayer.isUsingItem()) return true;
      if (mc.thePlayer.isEating()) return true;
      if (mc.currentScreen != null) return false;
      return mc.gameSettings.keyBindUseItem.pressed;
   }

   public static void cubedmg() {
      for(int i = 0; i < 51; ++i) {
         mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.06, mc.thePlayer.posZ, false));
         mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
      }

      mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
      mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
   }

   public static double[] predictPosition(EntityPlayer entity, int predictTicks) {
      double diffX = entity.prevPosX - entity.posX;
      double diffZ = entity.prevPosZ - entity.posZ;
      double posX = entity.posX;
      double posZ = entity.posZ;

      for(int i = 0; i <= predictTicks; ++i) {
         posX -= diffX * (double)i;
         posZ -= diffZ * (double)i;
      }

      return new double[]{posX, posZ};
   }

   public static double[] getPredictedPos(
      float forward, float strafe, double motionX, double motionY, double motionZ, double posX, double posY, double posZ, boolean isJumping
   ) {
      strafe *= 0.98F;
      forward *= 0.98F;
      float f4 = 0.91F;
      boolean isSprinting = mc.thePlayer.isSprinting();
      if (isJumping && mc.thePlayer.onGround && mc.thePlayer.getJumpTicks() == 0) {
         motionY = (double)mc.thePlayer.getJumpUpwardsMotion();
         if (mc.thePlayer.isPotionActive(Potion.jump)) {
            motionY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
         }

         if (isSprinting) {
            float f = mc.thePlayer.rotationYaw * (float) (Math.PI / 180.0);
            motionX -= (double)(MathHelper.sin(f) * 0.2F);
            motionZ += (double)(MathHelper.cos(f) * 0.2F);
         }
      }

      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(posY) - 1, MathHelper.floor_double(posZ)))
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      float f3 = 0.16277136F / (f4 * f4 * f4);
      float friction;
      if (mc.thePlayer.onGround) {
         friction = mc.thePlayer.getAIMoveSpeed() * f3;
         if (mc.thePlayer == Minecraft.getMinecraft().thePlayer
            && Augustus.getInstance().getModuleManager().sprint.isToggled()
            && Augustus.getInstance().getModuleManager().sprint.allDirection.getBoolean()
            && mm.sprint.allSprint) {
            friction = 0.12999998F;
         }
      } else {
         friction = mc.thePlayer.jumpMovementFactor;
      }

      float f = strafe * strafe + forward * forward;
      if (f >= 1.0E-4F) {
         f = MathHelper.sqrt_float(f);
         if (f < 1.0F) {
            f = 1.0F;
         }

         f = friction / f;
         strafe *= f;
         forward *= f;
         float f1 = MathHelper.sin(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         float f2 = MathHelper.cos(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         motionX += (double)(strafe * f2 - forward * f1);
         motionZ += (double)(forward * f2 + strafe * f1);
      }

      posX += motionX;
      posY += motionY;
      posZ += motionZ;
      f4 = 0.91F;
      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(posX), MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      if (mc.thePlayer.worldObj.isRemote
         && (
            !mc.thePlayer.worldObj.isBlockLoaded(new BlockPos((int)posX, 0, (int)posZ))
               || !mc.thePlayer.worldObj.getChunkFromBlockCoords(new BlockPos((int)posX, 0, (int)posZ)).isLoaded()
         )) {
         if (posY > 0.0) {
            motionY = -0.1;
         } else {
            motionY = 0.0;
         }
      } else {
         motionY -= 0.08;
      }

      motionY *= 0.98F;
      motionX *= (double)f4;
      motionZ *= (double)f4;
      return new double[]{posX, posY, posZ, motionX, motionY, motionZ};
   }

   public static Vec3 getPredictedPos(boolean isHitting, Entity targetEntity, float forward, float strafe) {
      strafe *= 0.98F;
      forward *= 0.98F;
      float f4 = 0.91F;
      double motionX = mc.thePlayer.motionX;
      double motionZ = mc.thePlayer.motionZ;
      double motionY = mc.thePlayer.motionY;
      boolean isSprinting = mc.thePlayer.isSprinting();
      if (isHitting) {
         float f = (float)mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         float f1 = 0.0F;
         if (targetEntity instanceof EntityLivingBase) {
            f1 = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
         } else {
            f1 = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
         }

         if (f > 0.0F || f1 > 0.0F) {
            int i = EnchantmentHelper.getKnockbackModifier(mc.thePlayer);
            if (mc.thePlayer.isSprinting()) {
               ++i;
            }

            boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(mc.thePlayer), f);
            if (flag2) {
               if (i > 0) {
                  EventAttackSlowdown eventAttackSlowdown = new EventAttackSlowdown(false, 0.6);
                  EventHandler.call(eventAttackSlowdown);
                  motionX *= eventAttackSlowdown.getSlowDown();
                  motionZ *= eventAttackSlowdown.getSlowDown();
                  isSprinting = eventAttackSlowdown.isSprint();
               } else if (Augustus.getInstance().getModuleManager().velocity.isToggled()
                  && Augustus.getInstance().getModuleManager().velocity.mode.getSelected().equals("Intave")
                  && Minecraft.getMinecraft().thePlayer.hurtTime != 0) {
                  motionX *= Augustus.getInstance().getModuleManager().velocity.XZValueIntave.getValue();
                  motionZ *= Augustus.getInstance().getModuleManager().velocity.XZValueIntave.getValue();
                  isSprinting = false;
               }
            }
         }
      }

      if (mc.thePlayer.isJumping && mc.thePlayer.onGround && mc.thePlayer.getJumpTicks() == 0) {
         motionY = (double)mc.thePlayer.getJumpUpwardsMotion();
         if (mc.thePlayer.isPotionActive(Potion.jump)) {
            motionY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
         }

         if (isSprinting) {
            float f = mc.thePlayer.rotationYaw * (float) (Math.PI / 180.0);
            motionX -= (double)(MathHelper.sin(f) * 0.2F);
            motionZ += (double)(MathHelper.cos(f) * 0.2F);
         }
      }

      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(mc.thePlayer.posX),
                     MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1,
                     MathHelper.floor_double(mc.thePlayer.posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      float f3 = 0.16277136F / (f4 * f4 * f4);
      float friction;
      if (mc.thePlayer.onGround) {
         friction = mc.thePlayer.getAIMoveSpeed() * f3;
         if (mc.thePlayer == Minecraft.getMinecraft().thePlayer
            && Augustus.getInstance().getModuleManager().sprint.isToggled()
            && Augustus.getInstance().getModuleManager().sprint.allDirection.getBoolean()
            && mm.sprint.allSprint) {
            friction = 0.12999998F;
         }
      } else {
         friction = mc.thePlayer.jumpMovementFactor;
      }

      float f = strafe * strafe + forward * forward;
      if (f >= 1.0E-4F) {
         f = MathHelper.sqrt_float(f);
         if (f < 1.0F) {
            f = 1.0F;
         }

         f = friction / f;
         strafe *= f;
         forward *= f;
         float f1 = MathHelper.sin(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         float f2 = MathHelper.cos(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         motionX += (double)(strafe * f2 - forward * f1);
         motionZ += (double)(forward * f2 + strafe * f1);
      }

      f4 = 0.91F;
      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(mc.thePlayer.posX),
                     MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1,
                     MathHelper.floor_double(mc.thePlayer.posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      motionY *= 0.98F;
      motionX *= (double)f4;
      motionZ *= (double)f4;
      return new Vec3(motionX, motionY, motionZ);
   }
}
