// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module.modules;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.OnMotion;
import me.kaktuswasser.client.event.events.PostMotion;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.module.Module.Category;
import me.kaktuswasser.client.module.modules.HealingBot;
import me.kaktuswasser.client.module.modules.Teams;
import me.kaktuswasser.client.utilities.EntityHelper;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.utilities.MathUtils;
import me.kaktuswasser.client.utilities.TimeHelper;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.ModeValue;
import me.kaktuswasser.client.values.Value;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class KillAura extends Module {
   public final Value crack = new Value("killaura_Crack", "crack", Boolean.valueOf(true), this);
   public final Value burst = new Value("killaura_Burst", "burst", Boolean.valueOf(false), this);
   public final Value autosword = new Value("killaura_Auto Sword", "autosword", Boolean.valueOf(true), this);
   public final Value autoblock = new Value("killaura_Auto Block", "autoblock", Boolean.valueOf(true), this);
   public final Value randomized = new Value("killaura_Randomized Delay", "randomized", Boolean.valueOf(true), this);
   public final Value switchaura = new Value("killaura_Switch", "switch", Boolean.valueOf(true), this);
   public final Value invisibles = new Value("killaura_Invisibles", "invisibles", Boolean.valueOf(true), this);
   public final Value semivisibles = new Value("killaura_Semivisibles", "semivisibles", Boolean.valueOf(true), this);
   public final Value mobs = new Value("killaura_Mobs", "mobs", Boolean.valueOf(true), this);
   public final Value animals = new Value("killaura_Animals", "animals", Boolean.valueOf(true), this);
   public final Value players = new Value("killaura_Players", "players", Boolean.valueOf(true), this);
   public final Value ticksToWait = new ConstrainedValue("killaura_Ticks To Wait", "ticks", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(85), this);
   public final Value delay = new ConstrainedValue("killaura_Delay", "delay", Integer.valueOf(83), Integer.valueOf(1), Integer.valueOf(1000), this);
   public final Value crackSize = new ConstrainedValue("killaura_Crack Size", "cracksize", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(15), this);
   public final Value semivisibleTime = new ConstrainedValue("killaura_Semivisible Time", "semivisibletime", Integer.valueOf(10), Integer.valueOf(10), Integer.valueOf(50), this);
   public final Value reach = new ConstrainedValue("killaura_Reach", "reach", Double.valueOf(3.8D), Double.valueOf(1.0D), Double.valueOf(6.0D), this);
   public final ModeValue auraMode = new ModeValue("killaura_Aura Mode", "auramode", "on_motion", new String[]{"on_motion", "post_motion"}, this);
   private List targets = new CopyOnWriteArrayList();
   private final TimeHelper targetSwitchTime = new TimeHelper();
   private final TimeHelper burstTime = new TimeHelper();
   private final TimeHelper time = new TimeHelper();
   public EntityLivingBase target;
   private int itemSwitchTicks;
   private int tweenAmnt;
   private int random;
   private int shouldSwitch;
   private boolean canBlock;

   public KillAura() {
      super("KillAura", -65536, 19, Category.COMBAT);
      this.setTag("Kill Aura");
   }

   public boolean isValidTarget(Entity entity) {
      Teams teams = (Teams)Client.getModuleManager().getModuleByName("teams");
      if(entity == null) {
         return false;
      } else if(entity == mc.thePlayer) {
         return false;
      } else if(!entity.isEntityAlive()) {
         return false;
      } else if(entity.ticksExisted < ((Integer)this.ticksToWait.getValue()).intValue()) {
         return false;
      } else if((double)mc.thePlayer.getDistanceToEntity(entity) > ((Double)this.reach.getValue()).doubleValue()) {
         return false;
      } else if(teams.isEnabled() && entity instanceof EntityPlayer && teams.getTabName((EntityPlayer)entity).length() > 2 && teams.getTabName(mc.thePlayer).startsWith(teams.getTabName((EntityPlayer)entity).substring(0, 2))) {
         return false;
      } else if(!((Boolean)this.invisibles.getValue()).booleanValue() && entity.isInvisibleToPlayer(mc.thePlayer)) {
         return false;
      } else if(((Boolean)this.semivisibles.getValue()).booleanValue() && !((EntityLivingBase)entity).invisibleTime.hasReached((long)((Integer)this.semivisibleTime.getValue()).intValue())) {
         return false;
      } else if(entity instanceof EntityPlayer) {
         return !Client.getFriendManager().isFriend(entity.getName()) && ((Boolean)this.players.getValue()).booleanValue();
      } else if(entity instanceof IAnimals && !(entity instanceof IMob)) {
         if(!(entity instanceof EntityHorse)) {
            return ((Boolean)this.animals.getValue()).booleanValue();
         } else {
            EntityHorse horse1 = (EntityHorse)entity;
            return ((Boolean)this.animals.getValue()).booleanValue() && horse1.riddenByEntity != mc.thePlayer;
         }
      } else {
         return entity instanceof IMob?((Boolean)this.mobs.getValue()).booleanValue():false;
      }
   }

   private void burst(Entity entity) {
      ItemStack currentItem = mc.thePlayer.inventory.getCurrentItem();
      boolean wasSneaking = mc.thePlayer.isSneaking();
      boolean wasBlocking = currentItem != null && currentItem.getItem().getItemUseAction(currentItem) == EnumAction.BLOCK && mc.thePlayer.isBlocking();
      if(wasSneaking) {
         mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SNEAKING));
      }

      if(wasBlocking) {
         mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

      for(int i = 0; i < 6; ++i) {
         mc.thePlayer.attackTargetEntityWithCurrentItem(entity);
         mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
         mc.getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(entity, net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK));
         mc.thePlayer.attackTargetEntityWithCurrentItem(entity);
      }

      if(wasSneaking) {
         mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.START_SNEAKING));
      }

      if(wasBlocking) {
         mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(currentItem));
      }

      mc.thePlayer.setSneaking(wasSneaking);
      if(wasBlocking) {
         mc.thePlayer.setItemInUse(currentItem, 100);
      }

   }

   private void attack(Entity entity) {
      ItemStack currentItem;
      if(Client.getModuleManager().getModuleByName("durability").isEnabled()) {
         currentItem = mc.thePlayer.getCurrentEquippedItem();
         ItemStack autoSword = mc.thePlayer.inventoryContainer.getSlot(27).getStack();
         if(currentItem != null && autoSword != null && this.itemSwitchTicks >= 10) {
            this.swap(27, mc.thePlayer.inventory.currentItem);
         }
      }

      currentItem = mc.thePlayer.inventory.getCurrentItem();
      boolean var9 = ((Boolean)this.autosword.getValue()).booleanValue();
      boolean wasSneaking = mc.thePlayer.isSneaking();
      boolean wasBlocking = currentItem != null && currentItem.getItem().getItemUseAction(currentItem) == EnumAction.BLOCK && mc.thePlayer.isBlocking();
      if(var9) {
         mc.thePlayer.inventory.currentItem = EntityHelper.getBestWeapon(entity);
      }

      if(wasSneaking) {
         mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SNEAKING));
      }

      if(wasBlocking) {
         mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

      if(((Boolean)this.crack.getValue()).booleanValue()) {
         float current = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), this.target.getCreatureAttribute());

         for(int toSwitch = 0; toSwitch < ((Integer)this.crackSize.getValue()).intValue(); ++toSwitch) {
            if(Client.getModuleManager().getModuleByName("criticals").isEnabled() || !mc.thePlayer.onGround) {
               mc.thePlayer.onCriticalHit(this.target);
            }

            if(current > 0.0F) {
               mc.thePlayer.onEnchantmentCritical(this.target);
            }
         }

         mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(this.target, new Vec3(this.target.getPositionVector().xCoord, this.target.getPositionVector().yCoord, this.target.getPositionVector().zCoord)));
      }

      mc.thePlayer.swingItem();

      try {
         mc.playerController.attackEntity(mc.thePlayer, entity);
      } catch (Exception var8) {
         Logger.writeConsole("Aura exception thrown: " + var8.getMessage());
      }

      if(wasSneaking) {
         mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.START_SNEAKING));
      }

      if(wasBlocking) {
         mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(currentItem));
      }

      mc.thePlayer.setSneaking(wasSneaking);
      if(wasBlocking) {
         mc.thePlayer.setItemInUse(currentItem, currentItem.getMaxItemUseDuration());
      }

      if(Client.getModuleManager().getModuleByName("ninja").isEnabled()) {
         mc.thePlayer.setPosition(entity.posX, entity.posY, entity.posZ);
      }

      if(Client.getModuleManager().getModuleByName("durability").isEnabled()) {
         ItemStack var10 = mc.thePlayer.getCurrentEquippedItem();
         ItemStack var11 = mc.thePlayer.inventoryContainer.getSlot(27).getStack();
         if(var10 != null && var11 != null && this.itemSwitchTicks >= 10) {
            this.swap(27, mc.thePlayer.inventory.currentItem);
         }
      }

      if(this.itemSwitchTicks >= 10) {
         this.itemSwitchTicks = 0;
      }

   }

   public void onEvent(Event event) {
      if(event instanceof EatMyAssYouFuckingDecompiler) {
         OutputStreamWriter healingBot = new OutputStreamWriter(System.out);

         try {
            healingBot.flush();
         } catch (IOException var8) {
            ;
         } finally {
            healingBot = null;
         }
      }

      HealingBot var10;
      float[] var13;
      if(event instanceof PreMotion) {
         ++this.itemSwitchTicks;
         var10 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
         if(var10.isHealing() && var10.isEnabled()) {
            this.shouldSwitch = 5;
         }

         if(this.shouldSwitch > 0) {
            --this.shouldSwitch;
         }

         if(this.shouldSwitch != 0) {
            return;
         }

         PreMotion e = (PreMotion)event;
         if(this.targets.isEmpty()) {
            this.findTargets();
         }

         if(!this.targets.isEmpty()) {
            this.filterTargets();
         }

         ArrayList player = new ArrayList();
         mc.theWorld.loadedEntityList.stream().filter((entity) -> {
            return entity instanceof EntityLivingBase;
         }).filter((entity) -> {
            return this.isValidTarget((EntityLivingBase)entity);
         }).forEach((entity) -> {
            player.add((EntityLivingBase)entity);
         });
         this.setTag("Kill Aura §7" + player.size());
         if(this.isValidTarget(this.target)) {
            if(((Boolean)this.autoblock.getValue()).booleanValue() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
               ItemStack values = mc.thePlayer.inventory.getCurrentItem();
               mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 100);
            }

            var13 = EntityHelper.getEntityRotations(mc.thePlayer, this.target);
            e.setYaw(var13[0]);
            e.setPitch(var13[1]);
            this.random = ((Boolean)this.randomized.getValue()).booleanValue()?MathUtils.getRandom(1, 60):0;
         } else {
            this.targets.remove(this.target);
         }
      } else if(this.auraMode.getStringValue().equals("on_motion") && event instanceof OnMotion) {
         var10 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
         if(this.shouldSwitch != 0) {
            return;
         }

         if(this.isValidTarget(this.target) && this.time.hasReached((long)(((Integer)this.delay.getValue()).intValue() + this.random))) {
            this.attack(this.target);
            if(this.burstTime.hasReached(8000L) && ((Boolean)this.burst.getValue()).booleanValue()) {
               this.burst(this.target);
               this.burstTime.reset();
            }

            if(((Boolean)this.switchaura.getValue()).booleanValue() && this.targetSwitchTime.hasReached(250L)) {
               this.targetSwitchTime.reset();
               this.targets.remove(this.target);
               this.target = null;
            }

            this.time.reset();
         }
      } else if(this.auraMode.getStringValue().equals("post_motion") && event instanceof PostMotion) {
         var10 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
         if(this.shouldSwitch != 0) {
            return;
         }

         if(this.isValidTarget(this.target) && this.time.hasReached((long)(((Integer)this.delay.getValue()).intValue() + this.random))) {
            mc.thePlayer.swingItem();
            this.attack(this.target);
            if(this.burstTime.hasReached(8000L) && ((Boolean)this.burst.getValue()).booleanValue()) {
               this.burst(this.target);
               this.burstTime.reset();
            }

            if(((Boolean)this.switchaura.getValue()).booleanValue() && this.targetSwitchTime.hasReached(250L)) {
               this.targetSwitchTime.reset();
               this.targets.remove(this.target);
               this.target = null;
            }

            this.time.reset();
         }
      } else if(event instanceof SentPacket) {
         var10 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
         if(this.shouldSwitch != 0) {
            return;
         }

         SentPacket var11 = (SentPacket)event;
         if(var11.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer var12 = (C03PacketPlayer)var11.getPacket();
            if(this.isValidTarget(this.target)) {
               var13 = EntityHelper.getEntityRotations(mc.thePlayer, this.target);
               if(var12.rotating) {
                  var12.yaw = var13[0];
                  var12.pitch = var13[1];
               }
            }
         }
      }

   }

   protected void swap(int slot, int hotbarNum) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
   }

   private void findTargets() {
      mc.theWorld.loadedEntityList.stream().filter((entity) -> {
         return entity instanceof EntityLivingBase;
      }).filter((entity) -> {
         return this.isValidTarget((EntityLivingBase)entity);
      }).forEach((entity) -> {
         this.targets.add((EntityLivingBase)entity);
      });
   }

   private float[] getYawAndPitch(Entity paramEntityPlayer) {
      double d1 = paramEntityPlayer.posX - mc.thePlayer.posX;
      double d2 = paramEntityPlayer.posZ - mc.thePlayer.posZ;
      double d3 = mc.thePlayer.posY + 0.12D - (paramEntityPlayer.posY + 1.82D);
      double d4 = (double)MathHelper.sqrt_double(d1 + d2);
      float f1 = (float)(Math.atan2(d2, d1) * 180.0D / 3.141592653589793D) - 90.0F;
      float f2 = (float)(Math.atan2(d3, d4) * 180.0D / 3.141592653589793D);
      return new float[]{f1, f2};
   }

   private float getDistanceBetweenAngles(float paramFloat) {
      float f = Math.abs(paramFloat - mc.thePlayer.rotationYaw) % 360.0F;
      if(f > 180.0F) {
         f = 360.0F - f;
      }

      return f;
   }

   private void filterTargets() {
      EntityLivingBase first;
      if(((Boolean)this.switchaura.getValue()).booleanValue()) {
         first = (EntityLivingBase)this.targets.stream().sorted((entity1, entity2) -> {
            float yaw = EntityHelper.getYawChangeToEntity((Entity) entity1);
            float pitch = EntityHelper.getPitchChangeToEntity((Entity) entity1);
            float firstEntityDistance = (yaw + pitch) / 2.0F;
            yaw = EntityHelper.getYawChangeToEntity((Entity) entity2);
            pitch = EntityHelper.getPitchChangeToEntity((Entity) entity2);
            float secondEntityDistance = (yaw + pitch) / 2.0F;
            return firstEntityDistance > secondEntityDistance?1:(secondEntityDistance > firstEntityDistance?-1:0);
         }).findFirst().get();
         if(this.isValidTarget(first)) {
            this.target = first;
         } else {
            this.targets.remove(first);
         }
      } else if(!((Boolean)this.switchaura.getValue()).booleanValue()) {
         first = (EntityLivingBase)this.targets.stream().findFirst().get();
         if(this.isValidTarget(first)) {
            this.target = first;
         } else {
            this.targets.remove(first);
         }
      }

   }
}
