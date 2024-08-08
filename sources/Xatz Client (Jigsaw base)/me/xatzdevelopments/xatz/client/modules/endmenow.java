package me.xatzdevelopments.xatz.client.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;

public class endmenow extends Module {

	public endmenow(String name, int defaultKeyCode, Category cat) {
		super(name, defaultKeyCode, cat);
		// TODO Bruh
	}
  /* public static EntityLivingBase target;
   public static EntityLivingBase vip;
   public static EntityLivingBase blockTarget;
   
   public IntegerSetting min_cps = new IntegerSetting(this, "Minimum Clicks Per Second", 10, 1, 30);
   public IntegerSetting max_cps = new IntegerSetting(this, "Maximum Clicks Per Second", 12, 1, 30);
   public IntegerSetting hitchance = new IntegerSetting(this, "Hit Chance", 87, 1, 100);
   public FloatSetting autoblock_range = new FloatSetting(this, "AutoBlock Range", 4.0F, 0.1F, 12.0F);
   public BooleanSetting interact = new BooleanSetting(this, "AutoBlock Interact", true);
   public BooleanSetting walls = new BooleanSetting(this, "Wall Hack", true);
   public BooleanSetting sprint = new BooleanSetting(this, "Keep Sprinting", true);
   public BooleanSetting autoblock = new BooleanSetting(this, "AutoBlock", true);
   private ModeSetting mode = new ModeSetting(this, "Mode", "Single", new ArrayList(Arrays.asList("Switch", "Single", "Multi", "Multi2")));
   private ModeSetting block_mode = new ModeSetting(this, "AutoBlock Mode", "Basic1", new ArrayList(Arrays.asList("NCP", "Hypixel", "Basic1", "Basic2")));
   private ModeSetting type = new ModeSetting(this, "Type", "Pre", new ArrayList(Arrays.asList("Pre", "Post")));
   private ModeSetting priority = new ModeSetting(this, "Priority", "Range", new ArrayList(Arrays.asList("Angle", "Range", "FOV", "Armor")));
   private ModeSetting rotations = new ModeSetting(this, "Rotations", "Basic", new ArrayList(Arrays.asList("Basic", "Smooth", "Legit", "Predict")));

	CheckBtnSetting players = new CheckBtnSetting("Players", "players");
	CheckBtnSetting animals = new CheckBtnSetting("Animals", "animals");
	CheckBtnSetting mobs = new CheckBtnSetting("Mobs", "mobs");
	CheckBtnSetting invisibles = new CheckBtnSetting("Invisibles", "invisibles");
    SliderSetting<Number> range = new SliderSetting<Number>("Range", ClientSettings.rangeBeta, 2, 6.0, 0.25, ValueFormat.DECIMAL);
   
   private SoulTimer switchTimer = new SoulTimer();
   public static float sYaw;
   public static float sPitch;
   public static float aacB;
   private double fall;
   int[] randoms = new int[]{0, 1, 0};
   private boolean isBlocking = false;
   public static boolean isSetup = false;
   private SoulTimer newTarget = new SoulTimer();
   private SoulTimer lastStep = new SoulTimer();
   private SoulTimer rtimer = new SoulTimer();
   private List loaded = new CopyOnWriteArrayList();
   private int index;
   private int timer;
   private int crits;
   private int groundTicks;
   ArrayList moveMods;

   public endmenow() {
      super("KillAura", Category.COMBAT, 20);
      this.addSetting(this.mode);
      this.addSetting(this.type);
      this.addSetting(this.rotations);
      this.addSetting(this.priority);
      this.addSetting(this.block_mode);
      this.addSetting(this.min_cps);
      this.addSetting(this.max_cps);
      this.addSetting(this.hitchance);
      this.addSetting(this.range);
      this.addSetting(this.autoblock_range);
      this.addSetting(this.interact);
      this.addSetting(this.walls);
      this.addSetting(this.sprint);
      this.addSetting(this.players);
      this.addSetting(this.mobs);
      this.addSetting(this.animals);
      this.addSetting(this.invisibles);
      this.addSetting(this.autoblock);
   }

   public void onEnable() {
      if (this.mc.thePlayer != null) {
         sYaw = this.mc.thePlayer.rotationYaw;
         sPitch = this.mc.thePlayer.rotationPitch;
         this.loaded.clear();
         this.isBlocking = this.mc.thePlayer.isBlocking();
      }

      this.newTarget.reset();
      this.timer = 20;
      this.groundTicks = EntityUtils.isOnGround(0.01D) ? 1 : 0;
      aacB = 0.0F;
      super.onEnable();
   }

   public void onDisable() {
      this.loaded.clear();
      if (this.mc.thePlayer != null) {
         if (this.isBlocking && this.hasSword() && this.mc.thePlayer.getItemInUseCount() == 999) {
            this.unBlock();
         }

         if (this.mc.thePlayer.itemInUseCount == 999) {
            this.mc.thePlayer.itemInUseCount = 0;
         }

         target = null;
         blockTarget = null;
         super.onDisable();
      }
   }

   @Override
   public void onUpdate(MotionUpdate event) {
      if (this.moveMods == null) {
         this.moveMods = new ArrayList(Arrays.asList(SoulClient.getInstance().getModuleManager().getModuleByName("Fly"), SoulClient.getInstance().getModuleManager().getModuleByName("Speed")));
      }

      boolean shouldMiss = randomNumber(0, 100) > this.hitchance.getCurrentValue();
      EntityLivingBase newT = this.getOptimalTarget((double)this.range.getCurrentValue());
      String mode = this.mode.getCurrentValue();
      String rotationsMode = this.rotations.getCurrentValue();
      String autoblockMode = this.block_mode.getCurrentValue();
      String type = this.type.getCurrentValue();
      if (this.min_cps.getCurrentValue() > this.max_cps.getCurrentValue()) {
         this.max_cps.setCurrentValue(this.min_cps.getCurrentValue());
      }

      int min = this.min_cps.getCurrentValue();
      int max = this.max_cps.getCurrentValue();
      AutoPot autoPot = (AutoPot)this.getModule("AutoPot");
      AutoGapple gapple = (AutoGapple)this.getModule("AutoGapple");
      if ((!autoPot.isEnabled() || !autoPot.potting) && (!gapple.isEnabled() || !gapple.eat)) {
         int cps = randomNumber(min, max);
         double range = (double)this.range.getCurrentValue();
         double blockRange = (double)this.autoblock_range.getCurrentValue();
         boolean autoblock = this.autoblock.getCurrentValue();
         if (event.isPre()) {
            ++this.timer;
            Module module = SoulClient.getInstance().getModuleManager().getModuleByName("Scaffold");
            Scaffold scaffold = (Scaffold)module;
            if (scaffold.mode.getCurrentValue().equalsIgnoreCase("Custom") && (scaffold.rot.getCurrentValue() || scaffold.mode.getCurrentValue().equalsIgnoreCase("Legit") || scaffold.mode.getCurrentValue().equalsIgnoreCase("Cubecraft") && Scaffold.yaw != 999.0F && Scaffold.pitch != 999.0F) && scaffold.isEnabled()) {
               if (this.mc.thePlayer.getItemInUseCount() == 999) {
                  this.mc.thePlayer.itemInUseCount = 0;
               }

               return;
            }

            blockTarget = this.getOptimalTarget((double)this.autoblock_range.getCurrentValue());
            float[] rot;
            if (mode.equalsIgnoreCase("Multi")) {
               this.loaded = this.getTargets(range);
               if (this.loaded.size() > 0) {
                  target = (EntityLivingBase)this.loaded.get(0);
                  rot = RotationUtils.getRotations(target);
                  event.setYaw(rot[0]);
                  event.setPitch(rot[1]);
                  sYaw = rot[0];
                  sPitch = rot[1];
                  if (autoblock) {
                     if (this.hasSword()) {
                        this.mc.thePlayer.itemInUseCount = 999;
                     } else if (this.mc.thePlayer.itemInUseCount == 999) {
                        this.mc.thePlayer.itemInUseCount = 0;
                     }
                  } else if (this.mc.thePlayer.itemInUseCount == 999) {
                     if (this.isBlocking && this.hasSword()) {
                        this.unBlock();
                     }

                     this.mc.thePlayer.itemInUseCount = 0;
                  }

                  if (this.timer < 20 / cps) {
                     if (autoblock && (autoblockMode.equalsIgnoreCase("NCP") || autoblockMode.equalsIgnoreCase("Hypixel")) && this.hasSword() && this.isBlocking) {
                        this.unBlock();
                     }
                  } else {
                     this.timer = 0;
                     if (this.isBlocking && this.hasSword()) {
                        this.unBlock();
                     }

                     if (this.loaded.size() >= 1 && !shouldMiss) {
                        this.mc.playerController.syncCurrentPlayItem();
                        this.mc.thePlayer.swingItem();
                     }

                     Iterator var31 = this.loaded.iterator();

                     while(var31.hasNext()) {
                        EntityLivingBase targ = (EntityLivingBase)var31.next();
                        if (!shouldMiss) {
                           AttackEvent pre = new AttackEvent(target, true);
                           SoulClient.getInstance().getEventManager().call(pre);
                           this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(targ, Action.ATTACK));
                           this.mc.thePlayer.attackTargetEntityWithCurrentItem(targ);
                           AttackEvent post = new AttackEvent(target, false);
                           SoulClient.getInstance().getEventManager().call(post);
                        }
                     }

                     if (autoblock && autoblockMode.equalsIgnoreCase("Basic1") && this.hasSword()) {
                        this.block(target);
                     }
                  }
               }
            } else {
               if (mode.equalsIgnoreCase("Switch")) {
                  float cooldown = rotationsMode.equalsIgnoreCase("Smooth") ? 500.0F : 400.0F;
                  if (this.switchTimer.delay(cooldown)) {
                     this.loaded = this.getTargets(range);
                  }

                  if (this.index >= this.loaded.size()) {
                     this.index = 0;
                  }

                  if (this.switchTimer.delay(cooldown) && this.loaded.size() > 0) {
                     ++this.index;
                     if (this.index >= this.loaded.size()) {
                        this.index = 0;
                     }

                     this.switchTimer.reset();
                  }

                  if (this.loaded.size() > 0) {
                     newT = (EntityLivingBase)this.loaded.get(this.index);
                  } else {
                     newT = null;
                  }
               } else if (mode.equalsIgnoreCase("Multi2")) {
                  this.loaded = this.getTargets(range);
                  if (this.index >= this.loaded.size()) {
                     this.index = 0;
                  }

                  if (this.timer >= 20 / cps && this.loaded.size() > 0) {
                     ++this.index;
                     if (this.index >= this.loaded.size()) {
                        this.index = 0;
                     }
                  }

                  if (this.loaded.size() > 0) {
                     newT = (EntityLivingBase)this.loaded.get(this.index);
                  } else {
                     newT = null;
                  }
               }

               if (target != newT) {
                  this.newTarget.reset();
                  if (!mode.equalsIgnoreCase("Multi2")) {
                     shouldMiss = true;
                  }

                  target = newT;
                  if (target == null) {
                     sYaw = this.mc.thePlayer.rotationYaw;
                     sPitch = this.mc.thePlayer.rotationPitch;
                  }
               }

               if (target != null) {
                  if ((!this.validEntity(target, range) || !this.mc.theWorld.loadedEntityList.contains(target)) && mode.equalsIgnoreCase("Switch")) {
                     this.loaded = this.getTargets(range);
                     ++this.index;
                     if (this.index >= this.loaded.size()) {
                        this.index = 0;
                     }

                     return;
                  }

                  if (!this.validEntity(target, range) && mode.equalsIgnoreCase("Multi2")) {
                     this.loaded = this.getTargets(range);
                     return;
                  }

                  rot = RotationUtils.getRotations(target);
                  byte var23 = -1;
                  switch(rotationsMode.hashCode()) {
                  case -1814666802:
                     if (rotationsMode.equals("Smooth")) {
                        var23 = 1;
                     }
                     break;
                  case 63955982:
                     if (rotationsMode.equals("Basic")) {
                        var23 = 0;
                     }
                     break;
                  case 73298841:
                     if (rotationsMode.equals("Legit")) {
                        var23 = 2;
                     }
                     break;
                  case 1345932473:
                     if (rotationsMode.equals("Predict")) {
                        var23 = 3;
                     }
                  }

                  switch(var23) {
                  case 0:
                     event.setYaw(rot[0]);
                     event.setPitch(rot[1]);
                     sYaw = rot[0];
                     sPitch = rot[1];
                     break;
                  case 1:
                     this.smoothAim(event);
                     break;
                  case 2:
                     aacB /= 2.0F;
                     this.customRots(event, target);
                     break;
                  case 3:
                     rot = RotationUtils.getPredictedRotations(target);
                     event.setYaw(rot[0]);
                     event.setPitch(rot[1]);
                     sYaw = rot[0];
                     sPitch = rot[1];
                  }

                  if (this.timer >= 20 / cps && type.equalsIgnoreCase("Pre")) {
                     this.timer = 0;
                     int XR = randomNumber(1, -1);
                     int YR = randomNumber(1, -1);
                     int ZR = randomNumber(1, -1);
                     this.randoms[0] = XR;
                     this.randoms[1] = YR;
                     this.randoms[2] = ZR;
                     float neededYaw = RotationUtils.getYawChange(sYaw, target.posX, target.posZ);
                     if (rotationsMode.equalsIgnoreCase("Legit")) {
                        neededYaw = this.getCustomRotsChange(sYaw, sPitch, target.posX, target.posY, target.posZ)[0];
                     }

                     float interval = 60.0F - this.mc.thePlayer.getDistanceToEntity(target) * 10.0F;
                     if (rotationsMode.equalsIgnoreCase("Legit")) {
                        interval = 50.0F - this.mc.thePlayer.getDistanceToEntity(target) * 10.0F;
                     }

                     if (neededYaw > interval || neededYaw < -interval || !this.newTarget.delay(70.0F)) {
                        shouldMiss = true;
                     }

                     if (shouldMiss && !mode.equalsIgnoreCase("Multi2")) {
                        this.mc.thePlayer.swingItem();
                     } else {
                        this.hitEntity(target, autoblock, autoblockMode);
                     }
                  }
               }
            }

            if (blockTarget != null) {
               if (autoblock) {
                  if (this.hasSword()) {
                     if (!autoblockMode.equalsIgnoreCase("NCP") && !autoblockMode.equalsIgnoreCase("Hypixel")) {
                        if (this.mc.thePlayer.itemInUseCount == 0) {
                           this.block(blockTarget);
                        }
                     } else if (this.hasSword() && this.isBlocking) {
                        this.unBlock();
                     }

                     this.mc.thePlayer.itemInUseCount = 999;
                  } else if (this.mc.thePlayer.itemInUseCount == 999) {
                     this.mc.thePlayer.itemInUseCount = 0;
                  }
               } else if (this.mc.thePlayer.itemInUseCount == 999) {
                  if (this.isBlocking && this.hasSword()) {
                     this.unBlock();
                  }

                  this.mc.thePlayer.itemInUseCount = 0;
               }
            } else if (this.mc.thePlayer.itemInUseCount == 999) {
               if (this.isBlocking && this.hasSword()) {
                  this.unBlock();
               }

               this.mc.thePlayer.itemInUseCount = 0;
            }
         } else {
            if (type.equalsIgnoreCase("Post") && target != null) {
               this.timer = 0;
               int XR = randomNumber(1, -1);
               int YR = randomNumber(1, -1);
               int ZR = randomNumber(1, -1);
               this.randoms[0] = XR;
               this.randoms[1] = YR;
               this.randoms[2] = ZR;
               float neededYaw = RotationUtils.getYawChange(sYaw, target.posX, target.posZ);
               if (rotationsMode.equalsIgnoreCase("Legit")) {
                  neededYaw = this.getCustomRotsChange(sYaw, sPitch, target.posX, target.posY, target.posZ)[0];
               }

               float interval = 60.0F - this.mc.thePlayer.getDistanceToEntity(target) * 10.0F;
               if (rotationsMode.equalsIgnoreCase("Legit")) {
                  interval = 50.0F - this.mc.thePlayer.getDistanceToEntity(target) * 10.0F;
               }

               if (neededYaw > interval || neededYaw < -interval || !this.newTarget.delay(70.0F)) {
                  shouldMiss = true;
               }

               if (shouldMiss && !mode.equalsIgnoreCase("Multi2")) {
                  this.mc.thePlayer.swingItem();
               } else {
                  this.hitEntity(target, autoblock, autoblockMode);
               }
            }

            if (blockTarget != null && !this.isBlocking && autoblock && this.hasSword()) {
               if (autoblockMode.equalsIgnoreCase("Hypixel")) {
                  this.blockHypixel(blockTarget);
               } else if (autoblockMode.equalsIgnoreCase("NCP")) {
                  this.block(blockTarget);
               } else if (autoblockMode.equalsIgnoreCase("Basic2") && this.timer == 0) {
                  this.block(blockTarget);
               }
            }
         }

      }
   }

   public void miniCrit(MotionUpdate em, String mode) {
      double offset = 0.0D;
      boolean ground = false;
      int min = this.min_cps.getCurrentValue();
      int max = this.max_cps.getCurrentValue();
      double hitchance = new Double((double)this.hitchance.getCurrentValue());
      int cps = randomNumber(min, max);
      int delay = 20 / cps;
      if (mode.equalsIgnoreCase("Minis")) {
         if (this.crits == 0) {
            double x = this.mc.thePlayer.posX;
            double y = this.mc.thePlayer.posY;
            double z = this.mc.thePlayer.posZ;
            new C04PacketPlayerPosition(x, y + 0.0626001D, z, false);
            offset = 0.0D;
            this.crits = 1;
         } else if ((this.timer == delay - 2 || delay - 2 <= 0 && this.timer <= delay - 2 || this.timer >= delay) && this.crits >= 1 && this.lastStep.delay(20.0F)) {
            this.crits = 0;
            offset = 0.0628D;
            this.fall += offset;
         }
      } else if (mode.equalsIgnoreCase("HMinis")) {
         if (!this.lastStep.delay(20.0F) || this.groundTicks <= 0) {
            this.crits = -1;
            ground = true;
         }

         ++this.crits;
         if (this.crits == 1) {
            offset = 0.0625D;
         } else if (this.crits == 2) {
            offset = 0.0626D;
         } else if (this.crits == 3) {
            offset = 0.0D;
         } else if (this.crits == 4) {
            offset = 0.0D;
            this.crits = 0;
         }
      }

      boolean aa = this.isOnGround(0.001D);
      if (!aa) {
         this.groundTicks = 0;
         this.crits = 0;
         this.fall = 0.0D;
      } else {
         ++this.groundTicks;
      }

      if (this.mc.thePlayer.isCollidedVertically && aa && !this.mc.thePlayer.isJumping && !this.mc.thePlayer.isInWater() && !this.mc.gameSettings.keyBindJump.isPressed()) {
         Iterator var13 = this.moveMods.iterator();

         while(var13.hasNext()) {
            Module m = (Module)var13.next();
            if (m.isEnabled()) {
               return;
            }
         }

         if (this.mc.thePlayer.motionY != -0.1552320045166016D) {
            if (offset != 0.0D) {
               isSetup = true;
            } else {
               isSetup = false;
            }

            this.mc.thePlayer.lastReportedPosY = 0.0D;
            em.setY(this.mc.thePlayer.posY + offset);
            em.setGround(ground);
         }
      }

   }

   private EntityLivingBase getOptimalTarget(double range) {
      List load = new ArrayList();
      Iterator var4 = this.mc.theWorld.loadedEntityList.iterator();

      while(var4.hasNext()) {
         Object o = var4.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)o;
            if (this.validEntity(ent, range)) {
               if (ent == vip) {
                  return ent;
               }

               load.add(ent);
            }
         }
      }

      if (load.isEmpty()) {
         return null;
      } else {
         return this.getTarget(load);
      }
   }

   boolean validEntity(EntityLivingBase entity, double range) {
      boolean players = this.players.getCurrentValue();
      boolean animals = this.animals.getCurrentValue();
      Module module = SoulClient.getInstance().getModuleManager().getModuleByName("AntiBot");
      AntiBot antiBot = (AntiBot)module;
      if (this.mc.thePlayer.isEntityAlive() && !(entity instanceof EntityPlayerSP) && (double)this.mc.thePlayer.getDistanceToEntity(entity) <= range) {
         if (!RotationUtils.canEntityBeSeen(entity) && !this.walls.getCurrentValue()) {
            return false;
         }

         if (entity.isPlayerSleeping() || module.isEnabled() && antiBot.getInvalid().contains(entity)) {
            return false;
         }

         if (entity instanceof EntityPlayer) {
            if (players) {
               EntityPlayer player = (EntityPlayer)entity;
               if (!player.isEntityAlive() && (double)player.getHealth() == 0.0D) {
                  return false;
               }

               if (player.isInvisible() && !this.invisibles.getCurrentValue()) {
                  return false;
               }

               return true;
            }
         } else if (!entity.isEntityAlive()) {
            return false;
         }

         if (animals && (entity instanceof EntityMob || entity instanceof EntityIronGolem || entity instanceof EntityAnimal || entity instanceof EntityVillager)) {
            if (entity.getName().equals("Villager") && entity instanceof EntityVillager) {
               return false;
            }

            return true;
         }
      }

      return false;
   }

   public EntityLivingBase getTarget(List list) {
      this.sortList(list);
      return list.isEmpty() ? null : (EntityLivingBase)list.get(0);
   }

   private void sortList(List weed) {
      String current = this.priority.getCurrentValue();
      byte var4 = -1;
      switch(current.hashCode()) {
      case -2137395588:
         if (current.equals("Health")) {
            var4 = 3;
         }
         break;
      case 69805:
         if (current.equals("FOV")) {
            var4 = 1;
         }
         break;
      case 63408307:
         if (current.equals("Angle")) {
            var4 = 2;
         }
         break;
      case 63533343:
         if (current.equals("Armor")) {
            var4 = 4;
         }
         break;
      case 78727453:
         if (current.equals("Range")) {
            var4 = 0;
         }
      }

      switch(var4) {
      case 0:
         weed.sort((o1, o2) -> {
            return (int)(o1.getDistanceToEntity(this.mc.thePlayer) * 1000.0F - o2.getDistanceToEntity(this.mc.thePlayer) * 1000.0F);
         });
         break;
      case 1:
         weed.sort(Comparator.comparingDouble((o) -> {
            return (double)RotationUtils.getDistanceBetweenAngles(this.mc.thePlayer.rotationPitch, RotationUtils.getRotations(o)[0]);
         }));
         break;
      case 2:
         weed.sort((o1, o2) -> {
            float[] rot1 = RotationUtils.getRotations(o1);
            float[] rot2 = RotationUtils.getRotations(o2);
            return (int)(this.mc.thePlayer.rotationYaw - rot1[0] - (this.mc.thePlayer.rotationYaw - rot2[0]));
         });
         break;
      case 3:
         weed.sort((o1, o2) -> {
            return (int)(o1.getHealth() - o2.getHealth());
         });
         break;
      case 4:
         weed.sort(Comparator.comparingInt((o) -> {
            return o instanceof EntityPlayer ? ((EntityPlayer)o).inventory.getTotalArmorValue() : (int)o.getHealth();
         }));
      }

   }

   public static int randomNumber(int max, int min) {
      return Math.round((float)min + (float)Math.random() * (float)(max - min));
   }

   private void smoothAim(MotionUpdate em) {
      double randomYaw = 0.05D;
      double randomPitch = 0.05D;
      float targetYaw = RotationUtils.getYawChange(sYaw, target.posX + (double)randomNumber(1, -1) * randomYaw, target.posZ + (double)randomNumber(1, -1) * randomYaw);
      float yawFactor = targetYaw / 1.7F;
      em.setYaw(sYaw + yawFactor);
      sYaw += yawFactor;
      float targetPitch = RotationUtils.getPitchChange(sPitch, target, target.posY + (double)randomNumber(1, -1) * randomPitch);
      float pitchFactor = targetPitch / 1.7F;
      em.setPitch(sPitch + pitchFactor);
      sPitch += pitchFactor;
   }

   private void block(EntityLivingBase ent) {
      this.isBlocking = true;
      if (this.interact.getCurrentValue()) {
         this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, new Vec3((double)randomNumber(-50, 50) / 100.0D, (double)randomNumber(0, 200) / 100.0D, (double)randomNumber(-50, 50) / 100.0D)));
         this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, Action.INTERACT));
      }

      this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
   }

   private void blockHypixel(EntityLivingBase ent) {
      this.isBlocking = true;
      if (this.interact.getCurrentValue()) {
         this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, new Vec3((double)randomNumber(-50, 50) / 100.0D, (double)randomNumber(0, 200) / 100.0D, (double)randomNumber(-50, 50) / 100.0D)));
         this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, Action.INTERACT));
      }

      this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.getHypixelBlockpos(this.mc.getSession().getUsername()), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
   }

   private void unBlock() {
      this.isBlocking = false;
      this.mc.playerController.syncCurrentPlayItem();
      this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
   }

   private boolean hasSword() {
      return this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
   }

   private void hitEntity(EntityLivingBase ent, boolean shouldBlock, String autoblockMode) {
      if (this.isBlocking && this.hasSword()) {
         this.unBlock();
      }

      new C0BPacketEntityAction(this.mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.START_SPRINTING);
      AttackEvent ej = new AttackEvent(target, true);
      SoulClient.getInstance().getEventManager().call(ej);
      this.mc.thePlayer.swingItem();
      this.mc.playerController.attackEntity(this.mc.thePlayer, target);
      AttackEvent post = new AttackEvent(target, false);
      SoulClient.getInstance().getEventManager().call(post);
      if (shouldBlock && autoblockMode.equalsIgnoreCase("Basic1") && this.hasSword()) {
         this.block(ent);
      }

   }

   public List getTargets(double range) {
      List targets = new ArrayList();
      Iterator var4 = this.mc.theWorld.loadedEntityList.iterator();

      while(var4.hasNext()) {
         Object o = var4.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)o;
            if (this.validEntity(entity, range)) {
               targets.add(entity);
            }
         }
      }

      this.sortList(targets);
      return targets;
   }

   public void customRots(MotionUpdate em, EntityLivingBase ent) {
      double randomYaw = 0.05D;
      double randomPitch = 0.05D;
      float[] rotsN = this.getCustomRotsChange(sYaw, sPitch, target.posX + (double)randomNumber(1, -1) * randomYaw, target.posY + (double)randomNumber(1, -1) * randomPitch, target.posZ + (double)randomNumber(1, -1) * randomYaw);
      float targetYaw = rotsN[0];
      float yawFactor = targetYaw * targetYaw / (4.7F * targetYaw);
      if (targetYaw < 5.0F) {
         yawFactor = targetYaw * targetYaw / (3.7F * targetYaw);
      }

      if (Math.abs(yawFactor) > 7.0F) {
         aacB = yawFactor * 7.0F;
         yawFactor = targetYaw * targetYaw / (3.7F * targetYaw);
      } else {
         yawFactor = targetYaw * targetYaw / (6.7F * targetYaw) + aacB;
      }

      em.setYaw(sYaw + yawFactor);
      sYaw += yawFactor;
      float targetPitch = rotsN[1];
      float pitchFactor = targetPitch / 3.7F;
      em.setPitch(sPitch + pitchFactor);
      sPitch += pitchFactor;
   }

   public float[] getCustomRotsChange(float yaw, float pitch, double x, double y, double z) {
      double xDiff = x - this.mc.thePlayer.posX;
      double zDiff = z - this.mc.thePlayer.posZ;
      double var10000 = y - this.mc.thePlayer.posY;
      double dist = (double)MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
      double mult = 1.0D / (dist + 1.0E-4D) * 2.0D;
      if (mult > 0.2D) {
         mult = 0.2D;
      }

      if (!this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox()).contains(target)) {
         x += 0.3D * (double)this.randoms[0];
         y -= 0.4D + mult * (double)this.randoms[1];
         z += 0.3D * (double)this.randoms[2];
      }

      xDiff = x - this.mc.thePlayer.posX;
      zDiff = z - this.mc.thePlayer.posZ;
      double yDiff = y - this.mc.thePlayer.posY;
      float yawToEntity = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitchToEntity = (float)(-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));
      return new float[]{MathHelper.wrapAngleTo180_float(-(yaw - yawToEntity)), -MathHelper.wrapAngleTo180_float(pitch - pitchToEntity) - 2.5F};
   }

   public boolean isOnGround(double height) {
      return !this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   public BlockPos getHypixelBlockpos(String str) {
      int val = 89;
      if (str != null && str.length() > 1) {
         char[] chs = str.toCharArray();
         int lenght = chs.length;

         for(int i = 0; i < lenght; ++i) {
            val += chs[i] * str.length() * str.length() + str.charAt(0) + str.charAt(1);
         }

         val /= str.length();
      }

      return new BlockPos(val, -val % 255, val);
   } */
}
