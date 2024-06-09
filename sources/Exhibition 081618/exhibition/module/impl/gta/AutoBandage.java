package exhibition.module.impl.gta;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.management.notifications.dev.DevNotifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RotationUtils;
import exhibition.util.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.MathHelper;

public class AutoBandage extends Module {
   public static boolean shouldBand;
   public boolean preBandage;
   private int fakeHoldTicks;
   public float possibleDamage;
   Timer timer = new Timer();
   private List eggs = new ArrayList();

   public AutoBandage(ModuleData data) {
      super(data);
      this.settings.put("HEALTH", new Setting("HEALTH", Integer.valueOf(17), "Thing health", 1.0D, 1.0D, 10.0D));
      this.settings.put("DELAY", new Setting("DELAY", Integer.valueOf(100), "Delay MS", 50.0D, 0.0D, 1000.0D));
      this.settings.put("HOLDTICKS", new Setting("HOLDTICKS", Integer.valueOf(5), "Fake holding ticks.", 1.0D, 1.0D, 20.0D));
   }

   public void onEnable() {
      shouldBand = false;
   }

   public void onDisable() {
      shouldBand = false;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event e) {
      EventMotionUpdate em = (EventMotionUpdate)e;
      int currentItem = mc.thePlayer.inventory.currentItem;
      int fakeTicks = ((Number)((Setting)this.settings.get("HOLDTICKS")).getValue()).intValue();
      int health = ((Number)((Setting)this.settings.get("HEALTH")).getValue()).intValue();
      if (!mc.thePlayer.isDead) {
         if (em.isPre()) {
            int bandageSlot = this.getBandageSlot();
            if (this.hotbarBandCount() < 8 && this.hotbarBandCount() != -1) {
               this.swapStacks(bandageSlot);
            }

            if (this.preBandage && shouldBand) {
               this.possibleDamage = 0.0F;
               this.preBandage = false;
            }

            if (this.fakeHoldTicks < fakeTicks && this.fakeHoldTicks != -1) {
               ++this.fakeHoldTicks;
            }

            if (shouldBand && this.fakeHoldTicks >= fakeTicks && this.fakeHoldTicks != -1) {
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentItem));
               shouldBand = false;
            }

            Iterator var7 = mc.theWorld.getLoadedEntityList().iterator();

            while(true) {
               while(true) {
                  EntityEgg egg;
                  float distance;
                  do {
                     do {
                        do {
                           do {
                              Object o;
                              do {
                                 if (!var7.hasNext()) {
                                    int delay = ((Number)((Setting)this.settings.get("DELAY")).getValue()).intValue();
                                    if (mc.thePlayer.getHealth() <= (float)(health * 2) && bandageSlot != -1 && this.timer.delay((float)delay) && mc.thePlayer.isEntityAlive() && !this.preBandage && !shouldBand) {
                                       this.eggs.clear();
                                       shouldBand = true;
                                       this.fakeHoldTicks = fakeTicks == 0 ? -1 : 0;
                                    }

                                    if (shouldBand && this.fakeHoldTicks == 0 && bandageSlot != -1) {
                                       mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bandageSlot));
                                       mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                                       this.timer.reset();
                                    } else if (shouldBand && this.fakeHoldTicks > 0 && bandageSlot != -1) {
                                       mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bandageSlot));
                                       return;
                                    }

                                    return;
                                 }

                                 o = var7.next();
                              } while(!(o instanceof EntityEgg));

                              egg = (EntityEgg)o;
                              float var2 = (float)(mc.thePlayer.lastTickPosX - egg.posX);
                              float var3 = (float)(mc.thePlayer.lastTickPosY - egg.posY);
                              float var4 = (float)(mc.thePlayer.lastTickPosZ - egg.posZ);
                              distance = MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
                           } while((double)mc.thePlayer.getDistanceToEntity(egg) > 2.7D);
                        } while((double)mc.thePlayer.getDistanceToEntity(egg) <= 0.5D);
                     } while(egg.ticksExisted < 0);
                  } while(this.eggs.contains(egg));

                  float yawDelta = RotationUtils.getDistanceBetweenAngles(Math.abs(MathHelper.wrapAngleTo180_float(Math.abs(egg.rotationYaw))), Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw)));
                  System.out.println("---EGG DATA---\n");
                  System.out.println("Rotation to Egg: " + Math.abs(RotationUtils.getYawChange(egg.posX, egg.posZ)) + "\nDistance from Player: " + distance + " " + mc.thePlayer.getDistanceToEntity(egg) + "\nEgg - Player Yaw Delta: " + yawDelta + "\nEgg Rotation Yaw: " + Math.abs(egg.rotationYaw) + "\nPlayer Rotations: " + Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw)) + " " + Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.prevRotationYaw)) + "\nMotions: " + egg.motionX + " " + egg.motionZ + "\nTicks Existed: " + egg.ticksExisted + "\nY Positions: " + (mc.thePlayer.posY - egg.posY));
                  System.out.println("\n---END DATA---\n");
                  if (egg.rotationYaw == 0.0F && egg.ticksExisted <= 1 && (mc.thePlayer.posY - egg.posY == -1.59375D || mc.thePlayer.posY - egg.posY == -1.53125D) && mc.thePlayer.getDistanceToEntity(egg) <= 2.0F) {
                     DevNotifications.getManager().post("§4[§cE§4]§8 YChange " + Math.abs(RotationUtils.getYawChange(egg.posX, egg.posZ)) + " " + mc.thePlayer.getDistanceToEntity(egg));
                  } else if (yawDelta < 30.0F && Math.abs(RotationUtils.getYawChange(egg.posX, egg.posZ)) < 50.0F && egg.ticksExisted <= 1) {
                     System.out.println("Ignored due to angle checks.");
                  } else if (mc.thePlayer.posY - egg.posY != -1.59375D && mc.thePlayer.posY - egg.posY != -1.53125D || egg.ticksExisted > 1 || mc.thePlayer.getDistanceToEntity(egg) > 2.0F) {
                     this.eggs.add(egg);
                     this.possibleDamage = (float)((double)this.possibleDamage + 0.25D);
                     if (mc.thePlayer.getHealth() - this.possibleDamage * 2.0F <= (float)(health * 2)) {
                        this.preBandage = true;
                        shouldBand = true;
                        this.fakeHoldTicks = fakeTicks == 0 ? -1 : 0;
                     }

                     DevNotifications.getManager().post("§6§l" + this.possibleDamage + " will be expected. ");
                  }
               }
            }
         }
      }
   }

   private void swapStacks(int slot) {
      for(int i = 9; i <= 36; ++i) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
         if (stack != null && Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.paper) && stack.stackSize > 40) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, slot, 2, mc.thePlayer);
         }
      }

   }

   private int hotbarBandCount() {
      for(int i = 0; i <= 8; ++i) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
         if (stack != null && Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.paper)) {
            return stack.stackSize;
         }
      }

      return -1;
   }

   private int getBandageSlot() {
      for(int i = 0; i <= 8; ++i) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
         if (stack != null && Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.paper)) {
            return i;
         }
      }

      return -1;
   }
}
