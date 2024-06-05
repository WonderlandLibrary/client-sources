package hawk.modules.combat;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.util.Timer;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

public class TPAura extends Module {
   public NumberSetting range = new NumberSetting("Range", 100.0D, 1.0D, 500.0D, 10.0D, this);
   public BooleanSetting Autoblock = new BooleanSetting("Autoblock", true, this);
   public BooleanSetting DisableOnDeath = new BooleanSetting("Disable on death", true, this);
   public BooleanSetting noSwing = new BooleanSetting("No Swing", false, this);
   public BooleanSetting OnlyPlayers = new BooleanSetting("Only Players", true, this);
   public BooleanSetting HitInvisible = new BooleanSetting("Hit invisibles", false, this);
   public static boolean HasTarget = false;
   public ModeSetting Rotation = new ModeSetting("Rotation", this, "None", new String[]{"None", "Basic", "Basic Legit", "OldVerus"});
   public Timer timer = new Timer();
   public NumberSetting aps = new NumberSetting("APS", 10.0D, 1.0D, 20.0D, 0.5D, this);
   public Random random = new Random();

   private boolean lambda$1(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && var1 != this.mc.thePlayer && !var1.isDead;
   }

   private double lambda$3(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer);
   }

   public void onEnable() {
   }

   public TPAura() {
      super("TPAura", 19, Module.Category.COMBAT);
      this.addSettings(new Setting[]{this.aps, this.range, this.noSwing, this.Rotation, this.DisableOnDeath, this.OnlyPlayers, this.HitInvisible, this.Autoblock});
   }

   public void onDisable() {
   }

   private boolean lambda$2(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !var1.isInvisible() && !var1.isInvisibleToPlayer(this.mc.thePlayer) && var1 != this.mc.thePlayer && !var1.isDead;
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion && var1.isPre()) {
         EventMotion var2 = (EventMotion)var1;
         Stream var3 = this.mc.theWorld.loadedEntityList.stream();
         EntityLivingBase.class.getClass();
         EntityLivingBase.class.getClass();
         List var4 = (List)var3.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
         if (this.HitInvisible.isEnabled()) {
            var4 = (List)var4.stream().filter(this::lambda$1).collect(Collectors.toList());
         } else {
            var4 = (List)var4.stream().filter(this::lambda$2).collect(Collectors.toList());
         }

         var4.sort(Comparator.comparingDouble(this::lambda$3));
         if (this.OnlyPlayers.isEnabled()) {
            var3 = var4.stream();
            EntityPlayer.class.getClass();
            EntityPlayer.class.getClass();
            var4 = (List)var3.filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
         }

         if (!var4.isEmpty()) {
            HasTarget = true;
            EntityLivingBase var7 = (EntityLivingBase)var4.get(0);
            if (this.Rotation.is("Basic Legit")) {
               this.mc.thePlayer.rotationYaw = this.getRotations(var7)[0] + (float)this.random.nextInt(10) - 10.0F;
               this.mc.thePlayer.rotationPitch = this.getRotations(var7)[1] + (float)this.random.nextInt(10) - 10.0F;
            }

            if (this.Rotation.is("Basic")) {
               var2.setYaw(this.getRotations(var7)[0] + (float)this.random.nextInt(10) - 10.0F);
               var2.setPitch(this.getRotations(var7)[1] + (float)this.random.nextInt(10) - 10.0F);
            }

            if (this.Rotation.is("OldVerus")) {
               var2.setYaw(this.getRotations(var7)[0] + (float)this.random.nextInt(10) - 10.0F);
               var2.setPitch(this.getRotations(var7)[1] + (float)this.random.nextInt(10) - 10.0F);
               if (!var4.isEmpty()) {
                  this.mc.thePlayer.setSprinting(false);
               }
            }

            if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.isDead || this.mc.thePlayer.getHealth() <= 0.0F)) {
               this.toggled = false;
            }

            if (this.timer.hasTimeElapsed((long)(1000.0D / this.aps.getValue()), true)) {
               if (this.noSwing.isEnabled()) {
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
               } else {
                  this.mc.thePlayer.swingItem();
               }

               this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(var7.posX, var7.posY, var7.posZ, false));
               this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(var7, C02PacketUseEntity.Action.ATTACK));
            }

            if (this.Autoblock.isEnabled() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
               this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem());
            }
         } else {
            HasTarget = true;
         }
      }

   }

   public float[] getRotations(Entity var1) {
      double var2 = var1.posX + (var1.posX - var1.lastTickPosX) - this.mc.thePlayer.posX;
      double var4 = var1.posY - 3.5D + (double)var1.getEyeHeight() - this.mc.thePlayer.posY + (double)this.mc.thePlayer.getEyeHeight();
      double var6 = var1.posZ + (var1.posZ - var1.lastTickPosZ) - this.mc.thePlayer.posZ;
      double var8 = Math.sqrt(Math.pow(var2, 2.0D) + Math.pow(var6, 2.0D));
      float var10 = (float)Math.toDegrees(-Math.atan(var2 / var6));
      float var11 = (float)(-Math.toDegrees(Math.atan(var4 / var8)));
      if (var2 < 0.0D && var6 < 0.0D) {
         var10 = (float)(90.0D + Math.toDegrees(Math.atan(var6 / var2)));
      } else if (var2 > 0.0D && var6 < 0.0D) {
         var10 = (float)(-90.0D + Math.toDegrees(Math.atan(var6 / var2)));
      }

      return new float[]{var10, var11};
   }
}
