package exhibition.module.impl.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.vector.Vector2f;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.management.friend.FriendManager;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.MultiBool;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.movement.Fly;
import exhibition.module.impl.movement.LongJump;
import exhibition.module.impl.movement.Speed;
import exhibition.module.impl.player.Scaffold;
import exhibition.module.impl.render.TargetESP;
import exhibition.util.PlayerUtil;
import exhibition.util.RotationUtils;
import exhibition.util.TeamUtils;
import exhibition.util.Timer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class Killaura extends Module {
   private static final String AUTOBLOCK = "AUTOBLOCK";
   private static final String RANGE = "RANGE";
   private String ANGLESTEP = "ANGLESTEP";
   private String TICK = "EXISTED";
   private String MAX = "MXAXAPS";
   private String MIN = "MINAPS";
   private String SWITCH = "SWITCH";
   private String DEATH = "DEATH";
   private String TARGETMODE = "PRIORITY";
   private String AURAMODE = "MODE";
   private String FOVCHECK = "FOV";
   private String RAYTRACE = "RAYTRACE";
   private String TARGETING = "TARGETING";
   private String MISS = "MISS";
   private Timer delay = new Timer();
   private Timer deathTimer = new Timer();
   private Timer switchTimer = new Timer();
   private Timer blockTimer = new Timer();
   public static List loaded = new CopyOnWriteArrayList();
   private int index;
   private boolean isCritSetup;
   private int nextRandom;
   public static boolean canJump;
   private Vector2f lastAngles = new Vector2f(0.0F, 0.0F);
   private Setting blockRange = new Setting("BLOCK-RANGE", 4.5D, "Range for killaura.", 0.1D, 1.0D, 10.0D);
   private Setting noswing = new Setting("NOSWING", false, "Blocks swinging server sided.");
   private boolean isBlocking;
   public static EntityLivingBase target;
   public static EntityLivingBase vip;
   static boolean allowCrits;
   private boolean disabled;
   public static boolean pre;

   public Killaura(ModuleData data) {
      super(data);
      this.settings.put(this.FOVCHECK, new Setting(this.FOVCHECK, Integer.valueOf(180), "Targets must be in FOV.", 15.0D, 45.0D, 180.0D));
      this.settings.put(this.TICK, new Setting(this.TICK, Integer.valueOf(50), "Existed ticks before attacking.", 5.0D, 1.0D, 120.0D));
      this.settings.put("AUTOBLOCK", new Setting("AUTOBLOCK", true, "Automatically blocks for you."));
      this.settings.put("RANGE", new Setting("RANGE", 4.5D, "Range for killaura.", 0.1D, 1.0D, 7.0D));
      this.settings.put("BLOCK-RANGE", this.blockRange);
      this.settings.put(this.MIN, new Setting(this.MIN, Integer.valueOf(5), "Minimum APS.", 1.0D, 1.0D, 20.0D));
      this.settings.put(this.MAX, new Setting(this.MAX, Integer.valueOf(15), "Maximum APS.", 1.0D, 1.0D, 20.0D));
      this.settings.put(this.ANGLESTEP, new Setting(this.ANGLESTEP, Integer.valueOf(180), "Slows down aim speed.", 5.0D, 0.0D, 180.0D));
      this.settings.put(this.DEATH, new Setting(this.DEATH, true, "Disables killaura when you die."));
      this.settings.put(this.RAYTRACE, new Setting(this.RAYTRACE, true, "Ray-tracing check."));
      this.settings.put(this.TARGETMODE, new Setting(this.TARGETMODE, new Options("Priority", "Angle", new String[]{"Angle", "Range", "FOV", "Armor", "Health", "Bounty"}), "Target mode priority."));
      this.settings.put(this.AURAMODE, new Setting(this.AURAMODE, new Options("Mode", "Switch", new String[]{"Switch"}), "Attack method for the aura."));
      this.settings.put("PARTICLES", new Setting("PARTICLES", false, "Render enchant particles."));
      this.settings.put(this.MISS, new Setting(this.MISS, false, "Misses hits to prevent attack flags."));
      this.settings.put(this.SWITCH, new Setting(this.SWITCH, Integer.valueOf(300), "Switch speed delay.", 50.0D, 50.0D, 1000.0D));
      List ents = Arrays.asList(new Setting("PLAYERS", true, "Attack players."), new Setting("MOBS", false, "Attack mobs."), new Setting("PASSIVE", false, "Attack passive."), new Setting("VILLAGERS", false, "Attack villagers."), new Setting("INVISIBLES", false, "Attack invisible."), new Setting("TEAMS", false, "Check if player is not on your team."), new Setting("ARMOR", true, "Check if player has armor equipped."));
      this.settings.put("NOSWING", this.noswing);
      this.settings.put(this.TARGETING, new Setting(this.TARGETING, new MultiBool("Target Filter", ents), "Properties the aura will target."));
   }

   private int randomNumber(int max, int min) {
      return (int)(Math.random() * (double)(max - min)) + min;
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   public void onDisable() {
      if (mc.thePlayer != null) {
         this.lastAngles.x = mc.thePlayer.rotationYaw;
      }

      loaded.clear();
      this.disabled = false;
      allowCrits = true;
      this.isBlocking = false;
      this.isCritSetup = true;
      canJump = false;
      pre = false;
   }

   public void onEnable() {
      target = null;
      loaded.clear();
      this.disabled = false;
      allowCrits = true;
      if (mc.thePlayer != null) {
         this.lastAngles.x = mc.thePlayer.rotationYaw;
      }

      this.isBlocking = false;
      this.isCritSetup = true;
      canJump = false;
      pre = false;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventPacket) {
         EventPacket e = (EventPacket)event.cast();
         Packet packet = e.getPacket();
         if (mc.thePlayer.isBlocking() && (packet.getClass().getName().contains(S2FPacketSetSlot.class.getName()) || packet instanceof S2FPacketSetSlot)) {
            try {
               S2FPacketSetSlot packetSetSlot = (S2FPacketSetSlot)packet;
               if (packetSetSlot.getSlotID() == 36 + mc.thePlayer.inventory.currentItem) {
                  pre = true;
               }
            } catch (Exception var15) {
               ;
            }
         }

      } else {
         String suffix = ((Options)((Setting)this.settings.get(this.AURAMODE)).getValue()).getSelected();
         this.setSuffix(suffix);
         allowCrits = !this.getSuffix().contains("Tick") && !this.getSuffix().contains("Single");
         int min = ((Number)((Setting)this.settings.get(this.MIN)).getValue()).intValue();
         int max = ((Number)((Setting)this.settings.get(this.MAX)).getValue()).intValue();
         if (min > max) {
            ((Setting)this.settings.get(this.MIN)).setValue(max);
         }

         if (((Number)((Setting)this.settings.get("BLOCK-RANGE")).getValue()).floatValue() < ((Number)((Setting)this.settings.get("RANGE")).getValue()).floatValue()) {
            ((Setting)this.settings.get("BLOCK-RANGE")).setValue(((Setting)this.settings.get("RANGE")).getValue());
         }

         if (this.nextRandom == 0) {
            this.nextRandom = 20 / this.randomNumber(min, max);
         }

         boolean block = ((Boolean)((Setting)this.settings.get("AUTOBLOCK")).getValue()).booleanValue();
         EventMotionUpdate em = (EventMotionUpdate)event.cast();
         if (em.isPre()) {
            if (((Boolean)((Setting)this.settings.get(this.DEATH)).getValue()).booleanValue()) {
               if (!mc.thePlayer.isEntityAlive() && !this.disabled) {
                  this.toggle();
                  this.deathTimer.reset();
                  Notifications.getManager().post("Aura Death", "Aura disabled due to death.");
               }

               if (this.disabled && this.deathTimer.delay(10000.0F)) {
                  this.disabled = false;
               }

               if (this.disabled) {
                  return;
               }
            }

            if (canJump) {
               canJump = false;
            }
         }

         if ((!AutoPot.potting || AutoPot.haltTicks <= 4) && !((Module)Client.getModuleManager().get(Scaffold.class)).isEnabled()) {
            if (!Client.getModuleManager().isEnabled(Scaffold.class)) {
               boolean crits = Client.getModuleManager().isEnabled(Criticals.class) && !Client.getModuleManager().isEnabled(Speed.class) && !Client.getModuleManager().isEnabled(Fly.class) && !((Module)Client.getModuleManager().get(LongJump.class)).isEnabled();
               EntityLivingBase target;
               if (em.isPre()) {
                  if (this.switchTimer.delay((float)((Number)((Setting)this.settings.get(this.SWITCH)).getValue()).intValue())) {
                     loaded = this.getTargets();
                  }

                  if (this.index >= loaded.size()) {
                     this.index = 0;
                  }

                  if (loaded.size() > 0) {
                     if (this.switchTimer.delay((float)((Number)((Setting)this.settings.get(this.SWITCH)).getValue()).intValue())) {
                        this.incrementIndex();
                        this.switchTimer.reset();
                     }

                     this.sortList(loaded);
                     target = (EntityLivingBase)loaded.get(this.index);
                     if (target != null) {
                        if (!this.validEntity(target)) {
                           loaded = this.getTargets();
                           this.incrementIndex();
                           return;
                        }

                        float[] rotations = RotationUtils.getRotations(target);
                        float targetYaw = MathHelper.clamp_float(RotationUtils.getYawChangeGiven(target.posX, target.posZ, this.lastAngles.x) + (float)this.randomNumber(-5, 5), -180.0F, 180.0F);
                        int maxAngleStep = ((Number)((Setting)this.settings.get(this.ANGLESTEP)).getValue()).intValue();
                        if (targetYaw > (float)maxAngleStep) {
                           targetYaw = (float)maxAngleStep;
                        } else if (targetYaw < (float)(-maxAngleStep)) {
                           targetYaw = (float)(-maxAngleStep);
                        }

                        em.setYaw(this.lastAngles.x += targetYaw / 1.1F);
                        em.setPitch(rotations[1] / 1.5F + (float)this.randomNumber(-5, 5));
                        if (target.hurtResistantTime <= 15) {
                           if (mc.thePlayer.ticksExisted % 2 == 0) {
                              if (crits && mc.thePlayer.onGround) {
                                 this.isCritSetup = true;
                                 canJump = true;
                                 em.setY(em.getY() + 0.0712178904D);
                                 em.setGround(false);
                              }
                           } else if (crits && em.getY() == mc.thePlayer.posY && em.isOnground()) {
                              em.setGround(false);
                              em.setAlwaysSend(true);
                           }
                        }

                        if (block && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                           mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 71992);
                        }
                     }
                  } else {
                     if (!this.isBlocking) {
                        this.blockTimer.reset();
                     }

                     this.lastAngles.x = mc.thePlayer.rotationYaw;
                     pre = false;
                  }
               } else if (em.isPost() && loaded.size() > 0 && loaded.get(this.index) != null) {
                  target = (EntityLivingBase)loaded.get(this.index);
                  float range = ((Number)((Setting)this.settings.get("RANGE")).getValue()).floatValue();
                  boolean isAttacking = mc.thePlayer.getDistanceToEntity(target) <= (mc.thePlayer.canEntityBeSeen(target) ? range : 3.5F) && this.delay.delay((float)(50 * this.nextRandom)) && (!crits || mc.thePlayer.ticksExisted % 2 != 0 && (!mc.thePlayer.onGround || this.isCritSetup || target.hurtResistantTime > 15));
                  if (mc.thePlayer.isBlocking() && isAttacking && this.isBlocking) {
                     this.isBlocking = false;
                     mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                     this.blockTimer.reset();
                  }

                  if (isAttacking && !this.isBlocking && (((Number)((Setting)this.settings.get(this.ANGLESTEP)).getValue()).intValue() == 0 || Math.abs(RotationUtils.getYawChangeGiven(target.posX, target.posZ, this.lastAngles.x)) < 5.0F)) {
                     this.isCritSetup = false;
                     if (!((Boolean)this.noswing.getValue()).booleanValue()) {
                        mc.thePlayer.swingItem();
                     } else {
                        mc.thePlayer.swingItemFake();
                     }

                     if (!((Boolean)((Setting)this.settings.get(this.MISS)).getValue()).booleanValue() || ((Boolean)((Setting)this.settings.get(this.MISS)).getValue()).booleanValue() && target.hurtResistantTime <= 15) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                        if (pre) {
                           mc.thePlayer.stopUsingItem();
                        }
                     }
                     
                     if (em.isPost() && mc.thePlayer.isBlocking() && !block) {
                    	 KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                         this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
                     }
                     
                     float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.inventory.getCurrentItem(), target.getCreatureAttribute());
                     if (sharpLevel > 0.0F && ((Boolean)((Setting)this.settings.get("PARTICLES")).getValue()).booleanValue()) {
                        mc.thePlayer.onEnchantmentCritical(target);
                        if (mc.thePlayer.onGround && crits || Client.getModuleManager().isEnabled(Criticals.class) || !mc.thePlayer.onGround && (double)mc.thePlayer.fallDistance > 0.66D) {
                           mc.thePlayer.onCriticalHit(target);
                        }
                     }

                     this.delay.reset();
                     this.nextRandom = 20 / this.randomNumber(min, max);
                  }

                  double vel = Math.abs(mc.thePlayer.motionX) + Math.abs(mc.thePlayer.motionZ);
                  boolean tooFast = vel > 0.16D && vel < 0.22D ? mc.thePlayer.ticksExisted % 3 == 0 : vel > 0.22D && mc.thePlayer.ticksExisted % 5 != 0;
                  boolean shouldNotBlock = tooFast && PlayerUtil.isMoving() && mc.thePlayer.onGround && mc.thePlayer.isPotionActive(Potion.moveSpeed.id);
                  
                  if (mc.thePlayer.isBlocking() && !this.isBlocking && !shouldNotBlock) {
                     this.isBlocking = true;
                     mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                  }
               }

               if (em.isPost() && loaded.isEmpty() && this.isBlocking && this.blockTimer.delay(50.0F) && block) {
                  this.blockTimer.reset();
                  this.isBlocking = false;
                  this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                  mc.thePlayer.itemInUseCount = 0;
               }
            }
         }
      }
   }

   private void incrementIndex() {
      ++this.index;
      if (this.index >= loaded.size()) {
         this.index = 0;
      }

   }

   public EntityLivingBase getCurrentTarget() {
      String current = ((Options)((Setting)this.settings.get(this.AURAMODE)).getValue()).getSelected();
      if (target != null && !current.equalsIgnoreCase("Switch")) {
         return target;
      } else {
         return !loaded.isEmpty() && this.index < loaded.size() ? (EntityLivingBase)loaded.get(this.index) : null;
      }
   }

   protected void swap(int slot, int hotbarNum) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
   }

   public void attack(Entity ent, boolean crits) {
      if (!((Boolean)this.noswing.getValue()).booleanValue()) {
         mc.thePlayer.swingItem();
      } else {
         mc.thePlayer.swingItemFake();
      }

      if (crits) {
         Criticals.doCrits();
      } else {
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

      mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
      float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.inventory.getCurrentItem(), target.getCreatureAttribute());
      if (sharpLevel > 0.0F) {
         mc.thePlayer.onEnchantmentCritical(target);
      }

   }

   private EntityLivingBase getOptimalTarget() {
      List load = new ArrayList();
      Iterator var2 = mc.theWorld.getLoadedEntityList().iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)o;
            if (this.validEntity(ent)) {
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

   private boolean validEntity(EntityLivingBase entity) {
      MultiBool multi = (MultiBool)((Setting)this.settings.get(this.TARGETING)).getValue();
      boolean players = ((Boolean)multi.getSetting("PLAYERS").getValue()).booleanValue();
      boolean animals = ((Boolean)multi.getSetting("PASSIVE").getValue()).booleanValue();
      boolean mobs = ((Boolean)multi.getSetting("MOBS").getValue()).booleanValue();
      boolean villager = ((Boolean)multi.getSetting("VILLAGERS").getValue()).booleanValue();
      boolean invis = ((Boolean)multi.getSetting("INVISIBLES").getValue()).booleanValue();
      boolean teams = ((Boolean)multi.getSetting("TEAMS").getValue()).booleanValue();
      boolean armor = ((Boolean)multi.getSetting("ARMOR").getValue()).booleanValue();
      float range = ((Number)((Setting)this.settings.get("RANGE")).getValue()).floatValue();
      float focusRange = (mc.thePlayer.canEntityBeSeen(entity) ? range : 3.5F) >= ((Number)this.blockRange.getValue()).floatValue() ? (mc.thePlayer.canEntityBeSeen(entity) ? range : 3.5F) : ((Number)this.blockRange.getValue()).floatValue();
      if (mc.thePlayer.getHealth() > 0.0F && entity.getHealth() > 0.0F && !entity.isDead) {
         boolean raytrace = !((Boolean)((Setting)this.settings.get(this.RAYTRACE)).getValue()).booleanValue() || mc.thePlayer.canEntityBeSeen(entity);
         if (mc.thePlayer.getDistanceToEntity(entity) <= focusRange && raytrace && entity.ticksExisted > ((Number)((Setting)this.settings.get(this.TICK)).getValue()).intValue()) {
            if (!this.isInFOV(entity)) {
               return false;
            }

            if (entity instanceof EntityPlayer && players) {
               if (!AntiBot.getInvalid().contains(entity) && !entity.isPlayerSleeping()) {
                  EntityPlayer ent = (EntityPlayer)entity;
                  return (!TeamUtils.isTeam(mc.thePlayer, ent) || !teams) && (!ent.isInvisible() || invis) && (!armor || this.hasArmor(ent)) && !FriendManager.isFriend(ent.getName());
               }

               return false;
            }

            return (entity instanceof EntityMob || entity instanceof EntitySlime) && mobs || entity instanceof EntityAnimal && animals || entity instanceof EntityVillager && villager;
         }
      }

      return false;
   }

   private boolean hasArmor(EntityPlayer player) {
      return player.inventory.armorInventory[0] != null || player.inventory.armorInventory[1] != null || player.inventory.armorInventory[2] != null || player.inventory.armorInventory[3] != null;
   }

   private void sortList(List<EntityLivingBase> weed) {
      String current = ((Options)((Setting)this.settings.get(this.TARGETMODE)).getValue()).getSelected();
      byte var4 = -1;
      switch(current.hashCode()) {
      case -2137395588:
         if (current.equals("Health")) {
            var4 = 1;
         }
         break;
      case 69805:
         if (current.equals("FOV")) {
            var4 = 3;
         }
         break;
      case 63408307:
         if (current.equals("Angle")) {
            var4 = 4;
         }
         break;
      case 63533343:
         if (current.equals("Armor")) {
            var4 = 5;
         }
         break;
      case 78727453:
         if (current.equals("Range")) {
            var4 = 0;
         }
         break;
      case 1995629771:
         if (current.equals("Bounty")) {
            var4 = 2;
         }
      }

      switch(var4) {
      case 0:
         weed.sort(Comparator.comparingDouble((o) -> {
            return (double)o.getDistanceToEntity(mc.thePlayer);
         }));
         break;
      case 1:
         weed.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
         break;
      case 2:
         weed.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
         weed.sort(Comparator.comparing((o) -> {
            return o instanceof EntityPlayer && TargetESP.isPriority((EntityPlayer)o);
         }));
         break;
      case 3:
         weed.sort(Comparator.comparingDouble((o) -> {
            return (double)Math.abs(RotationUtils.getYawChange(o.posX, o.posZ));
         }));
         break;
      case 4:
         weed.sort(Comparator.comparingDouble((o) -> {
            return (double)RotationUtils.getRotations(o)[0];
         }));
         break;
      case 5:
         weed.sort(Comparator.comparingInt((o) -> {
            return o instanceof EntityPlayer ? ((EntityPlayer)o).inventory.getTotalArmorValue() : (int)o.getHealth();
         }));
      }

   }

   private EntityLivingBase getTarget(List list) {
      this.sortList(list);
      return list.isEmpty() ? null : (EntityLivingBase)list.get(0);
   }

   private List getTargets() {
      List targets = new ArrayList();
      Iterator var2 = mc.theWorld.getLoadedEntityList().iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         if (o instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)o;
            if (this.validEntity(entity)) {
               if (vip == entity) {
                  targets.clear();
                  targets.add(entity);
                  break;
               }

               targets.add(entity);
            }
         }
      }

      return targets;
   }

   private boolean isInFOV(EntityLivingBase entity) {
      int fov = ((Number)((Setting)this.settings.get(this.FOVCHECK)).getValue()).intValue();
      return RotationUtils.getYawChange(entity.posX, entity.posZ) <= (float)fov && RotationUtils.getPitchChange(entity, entity.posY) <= (float)fov;
   }
}
