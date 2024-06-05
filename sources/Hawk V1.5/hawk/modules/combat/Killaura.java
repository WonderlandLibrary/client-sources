package hawk.modules.combat;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventRenderGUI;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.modules.player.AutoSetting;
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
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Killaura extends Module {
   public BooleanSetting OnlyPlayers = new BooleanSetting("Only Players", false, this);
   public NumberSetting HitChance = new NumberSetting("Hit chance", 99.0D, 1.0D, 100.0D, 2.0D, this);
   public Timer timer = new Timer();
   public BooleanSetting HitInvisible = new BooleanSetting("Hit invisibles", false, this);
   public ModeSetting criticals = new ModeSetting("Criticals", this, "None", new String[]{"None", "Redesky"});
   public static boolean HasTarget = false;
   double redeskypercent = 0.30000001192092896D;
   public BooleanSetting Autoblock = new BooleanSetting("Autoblock", true, this);
   public EntityLivingBase target;
   public Random random = new Random();
   public double bb2 = 0.0D;
   public BooleanSetting noSwing = new BooleanSetting("No Swing", false, this);
   public NumberSetting range = new NumberSetting("Range", 4.0D, 1.0D, 6.0D, 0.1D, this);
   public int aa1 = 0;
   public int hit;
   public ModeSetting Rotation = new ModeSetting("Rotation", this, "Basic", new String[]{"None", "Basic", "Basic Legit", "NCP", "Redesky", "No sprint"});
   int redeskyrotation;
   public double aa2 = 0.0D;
   public NumberSetting aps = new NumberSetting("APS", 10.0D, 1.0D, 20.0D, 0.5D, this);
   public BooleanSetting DisableOnDeath = new BooleanSetting("Disable on death", true, this);
   public int bb1 = 0;

   private boolean lambda$8(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && var1 != this.mc.thePlayer && var1.getHealth() > 0.0F && !var1.isDead;
   }

   public void onEnable() {
      this.redeskypercent = 0.30000001192092896D;
      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel) {
            this.aps.setValue(10.5D);
            this.range.setValue(4.0D);
            if (!this.Rotation.is("Basic")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }

         if (AutoSetting.isMineplex) {
            this.aps.setValue(11.5D);
            this.range.setValue(3.3D);
            if (!this.Rotation.is("Basic")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }

         if (AutoSetting.isRedesky) {
            this.aps.setValue(12.5D);
            this.range.setValue(4.8D);
            if (!this.Rotation.is("Redesky")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }

         if (AutoSetting.isOldVerus) {
            this.aps.setValue(10.5D);
            this.range.setValue(3.2D);
            if (!this.Rotation.is("OldVerus")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }
      }

   }

   private boolean lambda$11(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !var1.isInvisible() && !var1.isInvisibleToPlayer(this.mc.thePlayer) && var1 != this.mc.thePlayer && !var1.isDead;
   }

   public Killaura() {
      super("Killaura", 45, Module.Category.COMBAT);
      this.addSettings(new Setting[]{this.aps, this.range, this.noSwing, this.Rotation, this.DisableOnDeath, this.OnlyPlayers, this.HitInvisible, this.Autoblock, this.HitChance});
   }

   public void onEvent(Event var1) {
      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel) {
            this.aps.setValue(10.5D);
            this.range.setValue(4.0D);
            if (!this.Rotation.is("Basic")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }

         if (AutoSetting.isMineplex) {
            this.aps.setValue(11.5D);
            this.range.setValue(3.3D);
            if (!this.Rotation.is("Basic")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }

         if (AutoSetting.isRedesky) {
            this.aps.setValue(12.5D);
            this.range.setValue(4.8D);
            if (!this.Rotation.is("Redesky")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }

         if (AutoSetting.isOldVerus) {
            this.aps.setValue(10.5D);
            this.range.setValue(3.2D);
            if (!this.Rotation.is("OldVerus")) {
               this.Rotation.cycle();
            }

            this.OnlyPlayers.enabled = true;
            this.HitInvisible.enabled = false;
            this.Autoblock.enabled = true;
         }
      }

      if (var1 instanceof EventMotion) {
         Stream var10000;
         EventMotion var2;
         List var3;
         FontRenderer var7;
         if (var1.isPre()) {
            var2 = (EventMotion)var1;
            var10000 = this.mc.theWorld.loadedEntityList.stream();
            EntityLivingBase.class.getClass();
            var3 = (List)var10000.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            if (this.HitInvisible.isEnabled()) {
               if (AutoSetting.isMineplex) {
                  var3 = (List)var3.stream().filter(this::lambda$1).collect(Collectors.toList());
               } else {
                  var3 = (List)var3.stream().filter(this::lambda$2).collect(Collectors.toList());
               }
            } else {
               if (AutoSetting.isMineplex) {
                  var3 = (List)var3.stream().filter(this::lambda$3).collect(Collectors.toList());
               }

               var3 = (List)var3.stream().filter(this::lambda$4).collect(Collectors.toList());
            }

            var3.sort(Comparator.comparingDouble(this::lambda$5));
            if (this.OnlyPlayers.isEnabled()) {
               var10000 = var3.stream();
               EntityPlayer.class.getClass();
               var3 = (List)var10000.filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
            }

            if (!var3.isEmpty()) {
               new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
               var7 = this.mc.fontRendererObj;
               this.target = (EntityLivingBase)var3.get(0);
               if (var1 instanceof EventRenderGUI) {
                  System.out.println("aaaaaaaaaaaaaaaaa");
                  var7.drawString(String.valueOf((new StringBuilder(String.valueOf(this.target.getName()))).append(this.target.getHealth())), 300.0D, 300.0D, -1);
               }

               HasTarget = true;
               if (this.Rotation.is("Basic Legit")) {
                  this.mc.thePlayer.rotationYaw = this.getRotations(this.target)[0] + (float)this.random.nextInt(20) - 10.0F;
                  this.mc.thePlayer.rotationPitch = this.getRotations(this.target)[1] + (float)this.random.nextInt(20) - 10.0F;
               }

               if (this.Rotation.is("Basic")) {
                  var2.setYaw(this.getRotations(this.target)[0] + (float)this.random.nextInt(20) - 10.0F);
                  var2.setPitch(this.getRotations(this.target)[1] + (float)this.random.nextInt(20) - 10.0F);
               }

               if (this.Rotation.is("NCP")) {
                  var2.setYaw(this.getRotations(this.target)[0] + (float)this.random.nextInt(12) - 6.0F);
                  var2.setPitch(this.getRotations(this.target)[1] + (float)this.random.nextInt(12) - 6.0F);
               }

               if (this.Rotation.is("No sprint")) {
                  var2.setYaw(this.getRotations(this.target)[0] + (float)this.random.nextInt(20) - 10.0F);
                  var2.setPitch(this.getRotations(this.target)[1] + (float)this.random.nextInt(20) - 10.0F);
                  if (!var3.isEmpty()) {
                     this.mc.thePlayer.setSprinting(false);
                  }
               }

               if (this.timer.hasTimeElapsed(500L, true)) {
                  if (this.redeskyrotation == 0) {
                     this.redeskyrotation = 1;
                  } else if (this.redeskyrotation == 1) {
                     this.redeskyrotation = 2;
                  } else if (this.redeskyrotation == 2) {
                     this.redeskyrotation = 0;
                  }
               }

               if (this.Rotation.is("Redesky") && !this.mc.thePlayer.isSprinting() && this.timer.hasTimeElapsed(50L, true)) {
                  var2.setYaw(this.getRotations(this.target)[0] + (float)(this.random.nextInt(8) - 4));
                  var2.setPitch(this.getRotations(this.target)[1] + (float)(this.random.nextInt(8) - 4));
               }

               if (!this.Rotation.is("NCP")) {
                  if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.isDead || this.mc.thePlayer.getHealth() <= 0.0F)) {
                     this.toggled = false;
                  }

                  if (this.timer.hasTimeElapsed((long)((float)((long)(1000.0D / this.aps.getValue())) / this.mc.timer.timerSpeed), true)) {
                     if (this.noSwing.isEnabled()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                     } else {
                        this.mc.thePlayer.swingItem();
                     }

                     this.mc.thePlayer.clearItemInUse();
                     this.hit = this.random.nextInt(100) - 1;
                     if ((double)this.hit <= this.HitChance.getValue()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
                     }

                     if (this.redeskypercent < 1.0D) {
                        this.redeskypercent += 1.5D / this.aps.getValue();
                     }
                  }
               }
            } else {
               HasTarget = false;
               this.redeskypercent = 0.30000001192092896D;
            }
         } else {
            var2 = (EventMotion)var1;
            var10000 = this.mc.theWorld.loadedEntityList.stream();
            EntityLivingBase.class.getClass();
            var3 = (List)var10000.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            if (this.HitInvisible.isEnabled()) {
               if (AutoSetting.isMineplex) {
                  var3 = (List)var3.stream().filter(this::lambda$8).collect(Collectors.toList());
               } else {
                  var3 = (List)var3.stream().filter(this::lambda$9).collect(Collectors.toList());
               }
            } else {
               if (AutoSetting.isMineplex) {
                  var3 = (List)var3.stream().filter(this::lambda$10).collect(Collectors.toList());
               }

               var3 = (List)var3.stream().filter(this::lambda$11).collect(Collectors.toList());
            }

            var3.sort(Comparator.comparingDouble(this::lambda$12));
            if (this.OnlyPlayers.isEnabled()) {
               var10000 = var3.stream();
               EntityPlayer.class.getClass();
               var3 = (List)var10000.filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
            }

            if (!var3.isEmpty()) {
               if (this.Rotation.is("NCP")) {
                  new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                  var7 = this.mc.fontRendererObj;
                  this.target = (EntityLivingBase)var3.get(0);
                  if (var1 instanceof EventRenderGUI) {
                     System.out.println("aaaaaaaaaaaaaaaaa");
                     var7.drawString(String.valueOf((new StringBuilder(String.valueOf(this.target.getName()))).append(this.target.getHealth())), 300.0D, 300.0D, -1);
                  }

                  HasTarget = true;
                  if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.isDead || this.mc.thePlayer.getHealth() <= 0.0F)) {
                     this.toggled = false;
                  }

                  this.mc.thePlayer.clearItemInUse();
                  if (this.timer.hasTimeElapsed((long)((float)((long)(1000.0D / this.aps.getValue())) / this.mc.timer.timerSpeed), true)) {
                     this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                     if (this.noSwing.isEnabled()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                     } else {
                        this.mc.thePlayer.swingItem();
                     }

                     this.hit = this.random.nextInt(100) - 1;
                     if ((double)this.hit <= this.HitChance.getValue()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
                     }

                     if (this.redeskypercent < 1.0D) {
                        this.redeskypercent += 1.5D / this.aps.getValue();
                     }
                  }
               }

               if (this.Autoblock.isEnabled() && !var3.isEmpty() && this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword && this.target.getDistanceToEntity(this.mc.thePlayer) < 4.0F) {
                  this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
                  if (this.mc.thePlayer.isBlocking() && this.timer.hasTimeElapsed(10L, false)) {
                     this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                  }
               }
            }
         }
      }

      if (var1 instanceof EventUpdate) {
         this.setDisplayname(String.valueOf((new StringBuilder("KillAura §7R ")).append((int)this.range.getValue()).append(" | APS ").append((int)this.aps.getValue())));
      }

   }

   private double lambda$12(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer);
   }

   private boolean lambda$10(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !var1.isInvisible() && !var1.isInvisibleToPlayer(this.mc.thePlayer) && var1 != this.mc.thePlayer && var1.getHealth() > 0.0F && !var1.isDead;
   }

   public void onDisable() {
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

   private boolean lambda$3(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !var1.isInvisible() && !var1.isInvisibleToPlayer(this.mc.thePlayer) && var1 != this.mc.thePlayer && var1.getHealth() > 0.0F && !var1.isDead;
   }

   private boolean lambda$9(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && var1 != this.mc.thePlayer && !var1.isDead;
   }

   private boolean lambda$2(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && var1 != this.mc.thePlayer && !var1.isDead;
   }

   private double lambda$5(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer);
   }

   public void OnEvent(Event var1) {
      boolean var10000 = var1 instanceof EventRenderGUI;
   }

   private boolean lambda$4(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !var1.isInvisible() && !var1.isInvisibleToPlayer(this.mc.thePlayer) && var1 != this.mc.thePlayer && !var1.isDead;
   }

   private boolean lambda$1(EntityLivingBase var1) {
      return (double)var1.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && var1 != this.mc.thePlayer && var1.getHealth() > 0.0F && !var1.isDead;
   }
}
