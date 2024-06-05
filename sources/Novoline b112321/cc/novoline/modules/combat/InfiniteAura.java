package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.SlowdownEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.combat.AutoPot;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.exploits.Blink;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.player.Freecam;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.PlayerUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_22;
import cc.novoline.utils.pathfinding.AStarCustomPathfinder;
import cc.novoline.utils.pathfinding.Vec3;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.aHM;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;

public final class InfiniteAura extends AbstractModule {
   private Entity target;
   private int H;
   private List targetList = new CopyOnWriteArrayList();
   private final Timer timerAttack = new Timer();
   private final Timer timerSwitch = new Timer();
   private final Timer lossTimer;
   private final Dimension screenSize;
   private final List toDispatch;
   private float x;
   private float T;
   private float G;
   private float F;
   private boolean ad;
   private boolean I;
   private C08PacketPlayerBlockPlacement aa;
   @Property("packet-limit")
   private final IntProperty O;
   @Property("min-aps")
   private final DoubleProperty L;
   @Property("max-aps")
   private final DoubleProperty ag;
   @Property("range")
   private final DoubleProperty range;
   @Property("wall-range")
   private final DoubleProperty A;
   @Property("block-range")
   private final DoubleProperty ae;
   @Property("rotations-smoothness")
   private final IntProperty K;
   @Property("targets")
   private final ListProperty targets;
   @Property("aura-sort")
   private final StringProperty aj;
   @Property("filters")
   private final ListProperty filters;
   @Property("particles")
   private final ListProperty M;
   @Property("auto-block")
   private final BooleanProperty D;
   @Property("target-hud")
   private final BooleanProperty targetHud;
   @Property("th-mode")
   private final StringProperty U;
   @Property("autodisable")
   private final ListProperty ak;
   @Property("lag-check")
   private final BooleanProperty lagCheck;
   @Property("th-x")
   private final IntProperty V;
   @Property("th-y")
   private final IntProperty ab;
   @Property("particles-mode")
   private final StringProperty af;
   @Property("particles-amount")
   private final IntProperty E;
   @Property("auto-weapon")
   private final BooleanProperty R;
   @Property("keep-sprint")
   private final BooleanProperty z;
   @Property("auto-block-mode")
   private final StringProperty y;
   @Property("attack-event")
   private final StringProperty C;
   @Property("autoblock-event")
   private final StringProperty N;

   public InfiniteAura(ModuleManager var1) {
      super(var1, "InfiniteAura", "Infinite Aura", EnumModuleType.COMBAT, "Automatically attacks entities around you");
      KillAura.Q();
      this.lossTimer = new Timer();
      this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.toDispatch = new ArrayList();
      this.O = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(10)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(50));
      this.L = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(5.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(20.0D));
      this.ag = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(5.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(20.0D));
      this.range = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(25.0D)).minimum(Double.valueOf(7.0D))).maximum(Double.valueOf(70.0D));
      this.A = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(25.0D)).minimum(Double.valueOf(7.0D))).maximum(Double.valueOf(70.0D));
      this.ae = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(25.0D)).minimum(Double.valueOf(7.0D))).maximum(Double.valueOf(70.0D));
      this.K = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(60)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(100));
      this.targets = PropertyFactory.createList((Object)"Players").acceptableValues((Object[])(new String[]{"Players", "Animals", "Mobs", "Invisibles"}));
      this.aj = PropertyFactory.createString("Distance").acceptableValues(new String[]{"Distance", "Health", "Armor", "FOV", "HurtTime"});
      this.filters = PropertyFactory.createList((Object)"Teams").acceptableValues((Object[])(new String[]{"Teams", "Armor"}));
      this.M = PropertyFactory.createList((Object)"Enchant").acceptableValues((Object[])(new String[]{"Enchant", "Critical"}));
      this.D = PropertyFactory.booleanFalse();
      this.targetHud = PropertyFactory.booleanFalse();
      this.U = PropertyFactory.createString("Pretty").acceptableValues(new String[]{"Trash", "Less Pretty", "Pretty", "Prettier", "Prettiest"});
      this.ak = PropertyFactory.createList((Object)"Death").acceptableValues((Object[])(new String[]{"World Change", "Game End", "Death"}));
      this.lagCheck = PropertyFactory.booleanFalse();
      this.V = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf((int)this.screenSize.getWidth() / 4)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf((int)this.screenSize.getWidth() / 2));
      this.ab = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf((int)this.screenSize.getHeight() / 4)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf((int)this.screenSize.getHeight() / 2));
      this.af = PropertyFactory.createString("Hit").acceptableValues(new String[]{"Hit", "Always"});
      this.E = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(2)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(5));
      this.R = PropertyFactory.booleanFalse();
      this.z = PropertyFactory.booleanFalse();
      this.y = PropertyFactory.createString("Packet").acceptableValues(new String[]{"Packet", "Vanilla"});
      this.C = PropertyFactory.createString("POST").acceptableValues(new String[]{"PRE", "POST"});
      this.N = PropertyFactory.createString("POST").acceptableValues(new String[]{"PRE", "POST"});
      Manager.put(new Setting("AURA_PACKETLIMIT", "One way packet limit", SettingType.SLIDER, this, this.O, 1.0D));
      Manager.put(new Setting("AURA_SORT", "Sort by", SettingType.COMBOBOX, this, this.aj));
      Manager.put(new Setting("MIN_APS", "Min APS", SettingType.SLIDER, this, this.L, 1.0D, InfiniteAura::lambda$new$0));
      Manager.put(new Setting("MAX_APS", "Max APS", SettingType.SLIDER, this, this.ag, 1.0D, InfiniteAura::lambda$new$1));
      Manager.put(new Setting("KA_ATTACK_EVENT", "Attack Event", SettingType.COMBOBOX, this, this.C));
      Manager.put(new Setting("RANGE", "Range", SettingType.SLIDER, this, this.range, 5.0D));
      Manager.put(new Setting("BLOCK_RANGE", "Block Range", SettingType.SLIDER, this, this.ae, 5.0D));
      Manager.put(new Setting("WALL_RANGE", "Wall Range", SettingType.SLIDER, this, this.A, 5.0D));
      Manager.put(new Setting("AURA_FOV", "Angle Smoothing", SettingType.SLIDER, this, this.K, 5.0D));
      Manager.put(new Setting("TARGETS", "Targets", SettingType.SELECTBOX, this, this.targets));
      Manager.put(new Setting("KA_FILTER", "Filters", SettingType.SELECTBOX, this, this.filters));
      Manager.put(new Setting("KA_PARTICLES", "Particles", SettingType.SELECTBOX, this, this.M));
      Manager.put(new Setting("KA_PARTICLES_MODE", "Particles Mode", SettingType.COMBOBOX, this, this.af, this::lambda$new$2));
      Manager.put(new Setting("KA_PARTICLES_AMOUNT", "Particles Amount", SettingType.SLIDER, this, this.E, 1.0D, this::lambda$new$3));
      Manager.put(new Setting("KA_KEEP_SPRINT", "Keep Sprint", SettingType.CHECKBOX, this, this.z));
      Manager.put(new Setting("KA_LAG_CHECK", "Lag Check", SettingType.CHECKBOX, this, this.lagCheck));
      Manager.put(new Setting("KA_AUTOBLOCK", "Auto Block", SettingType.CHECKBOX, this, this.D));
      Manager.put(new Setting("KA_AB_MODE", "Auto Block Mode", SettingType.COMBOBOX, this, this.y));
      Manager.put(new Setting("KA_AB_EVENT", "Auto Block Event", SettingType.COMBOBOX, this, this.N));
      Manager.put(new Setting("KA_AUTO_WEAPON", "Auto Weapon", SettingType.CHECKBOX, this, this.R));
      Manager.put(new Setting("TAR_HUD", "Target HUD", SettingType.CHECKBOX, this, this.targetHud));
      SettingType var10004 = SettingType.COMBOBOX;
      StringProperty var10006 = this.U;
      BooleanProperty var10007 = this.targetHud;
      this.targetHud.getClass();
      Manager.put(new Setting("TAR_HUD_MODE", "Style", var10004, this, var10006, var10007::get));
      Manager.put(new Setting("KADISABLE", "Disable On", SettingType.SELECTBOX, this, this.ak));
      if(acE.b() == null) {
         KillAura.a(new int[3]);
      }

   }

   public void onDisable() {
      this.x = this.mc.player.rotationYaw;
      this.T = this.mc.player.rotationPitch;
      this.target = null;
      this.aa = null;
      this.targetList.clear();
   }

   public void onEnable() {
      this.setSuffix("Single");
      this.x = this.mc.player.rotationYaw;
      this.T = this.mc.player.rotationPitch;
      this.lossTimer.reset();
   }

   private boolean hasArmor(EntityPlayer var1) {
      int[] var2 = KillAura.Q();
      return var1.inventory.armorInventory[0] != null || var1.inventory.armorInventory[1] != null || var1.inventory.armorInventory[2] != null || var1.inventory.armorInventory[3] != null;
   }

   private boolean p() {
      int[] var1 = KillAura.Q();
      return this.mc.player.getHeldItem() != null && this.mc.player.getHeldItem().getItem() instanceof ItemSword;
   }

   public boolean serverLag() {
      int[] var1 = KillAura.Q();
      return ((Boolean)this.lagCheck.get()).booleanValue() && this.lossTimer.getLastDelay() >= 100.0D;
   }

   public boolean shouldAttack() {
      int[] var1 = KillAura.Q();
      return this.isEnabled() && this.target != null && this.target.isEntityAlive() && (double)this.mc.player.getDistanceToEntity(this.target) <= (this.mc.player.canEntityBeSeen(this.target)?this.range():this.wallRange()) && !this.isEnabled(Scaffold.class);
   }

   public boolean shouldRotate() {
      int[] var1 = KillAura.Q();
      return this.shouldAttack() && !this.serverLag() && !this.isEnabled(Scaffold.class) && !this.mc.at.f();
   }

   public boolean Q() {
      int[] var1 = KillAura.Q();
      return ((Boolean)this.D.get()).booleanValue() && this.target != null && this.p() && this.target.isEntityAlive() && this.isEnabled() && !this.mc.at.f() && (double)this.mc.player.getDistanceToEntity(this.target) <= (this.mc.player.canEntityBeSeen(this.target)?this.u():this.wallRange()) && !this.isEnabled(Scaffold.class);
   }

   private boolean isAutismShopKeeperCheck(Entity var1) {
      KillAura.Q();
      IChatComponent var3 = var1.getDisplayName();
      String var4 = var3.getFormattedText();
      String var5 = var3.getUnformattedText();
      boolean var6 = !var4.substring(0, var4.length() - 2).contains("§");
      boolean var7 = var4.substring(var4.length() - 2).contains("§");
      return ServerUtils.isHypixel() && ServerUtils.serverIs(Servers.BW) && var6 && var7;
   }

   private double e(Entity var1) {
      float[] var3 = RotationUtil.b((EntityLivingBase)var1);
      KillAura.Q();
      float var4 = (float)((int)var3[0]);
      float var5 = this.mc.player.rotationYaw > var4?this.mc.player.rotationYaw - var4:var4 - this.mc.player.rotationYaw;
      return (double)var5;
   }

   @EventTarget
   public void b(MotionUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.mc.player.inventory.getStackInSlot(0) != null && this.mc.player.inventory.getStackInSlot(0).getItem() == Items.compass && this.mc.player.inventory.getStackInSlot(0).getDisplayName().contains("Teleporter")) {
            this.target = null;
         }

         if(this.targetsInRange() > 0) {
            this.targetList = this.z();
         }

         this.targetList = this.a((String)this.aj.get());
         if(this.target != null && !this.target.isEntityAlive()) {
            this.target = null;
         }

         if(this.targetList != null) {
            label166: {
               if(!this.a(false)) {
                  if(this.target != null) {
                     if((double)this.mc.player.getDistanceToEntity(this.target) > this.range() && this.targetsInRange() > 0) {
                        this.target = this.getTargetFromRange();
                     }

                     if((double)this.mc.player.getDistanceToEntity(this.target) > this.u()) {
                        this.target = null;
                        this.y();
                     }

                     if(this.target.isEntityAlive() || ((EntityLivingBase)this.target).getHealth() > 0.0F) {
                        break label166;
                     }

                     this.target = null;
                     this.y();
                  }

                  this.target = (Entity)this.targetList.get(0);
               }

               Iterator var3 = this.targetList.iterator();
               if(var3.hasNext()) {
                  Entity var4 = (Entity)var3.next();
                  if(this.novoline.getPlayerManager().hasType(var4.getName(), PlayerManager$EnumPlayerType.TARGET)) {
                     this.target = var4;
                  }
               }
            }
         }

         this.F = this.T;
         this.G = this.x;
      }

   }

   public float s() {
      return this.F;
   }

   public float h() {
      return this.G;
   }

   private void y() {
      this.H = (this.H + 1) % this.targetList.size();
      this.timerSwitch.reset();
   }

   private void a(Entity var1) {
      int[] var2 = KillAura.Q();
      if(this.Q()) {
         if(ServerUtils.isHypixel()) {
            this.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         }

         if(this.y.equals("Packet")) {
            this.d();
         }

         this.I = false;
      }

      ArrayList var3 = this.a(new Vec3(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ), new Vec3(var1.posX, var1.posY, var1.posZ));
      if(var3.size() <= ((Integer)this.O.get()).intValue()) {
         Iterator var4 = var3.iterator();
         if(var4.hasNext()) {
            Vec3 var5 = (Vec3)var4.next();
            if(var3.indexOf(var5) % 2 == 0) {
               this.sendPacketNoEvent(new C03PacketPlayer$C04PacketPlayerPosition(var5.getX(), var5.getY(), var5.getZ(), true));
            }

            this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(var5.getX(), var5.getY(), var5.getZ(), this.mc.player.rotationYaw, this.mc.player.rotationPitch, true));
         }

         this.mc.player.swingItem();
         this.sendPacket(new C02PacketUseEntity(var1, C02PacketUseEntity$Action.ATTACK));
         Collections.reverse(var3);
         var4 = var3.iterator();
         if(var4.hasNext()) {
            Vec3 var7 = (Vec3)var4.next();
            if(var3.indexOf(var7) % 2 == 0) {
               this.sendPacketNoEvent(new C03PacketPlayer$C04PacketPlayerPosition(var7.getX(), var7.getY(), var7.getZ(), true));
            }

            this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(var7.getX(), var7.getY(), var7.getZ(), this.mc.player.rotationYaw, this.mc.player.rotationPitch, true));
         }
      }

      if(this.af.equals("Always")) {
         if(this.M.contains("Enchant")) {
            this.mc.player.onEnchantmentCritical(var1);
         }

         if(this.M.contains("Critical")) {
            this.mc.player.onCriticalHit(var1);
         }
      }

   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getState().equals(EventState.PRE)) {
         if(!this.Q() || this.mc.player.isMoving() && this.y.equals("Packet")) {
            this.d();
         }

         if(this.shouldAttack()) {
            if(((Boolean)this.R.get()).booleanValue() && !this.mc.at.f()) {
               this.S();
            }

            if(this.af.equals("Hit") && !this.M.isEmpty() && this.target.hurtResistantTime == 20) {
               int var3 = 0;
               if(var3 < ((Integer)this.E.get()).intValue()) {
                  this.mc.player.onEnchantmentCritical(this.target);
                  this.mc.player.onCriticalHit(this.target);
                  ++var3;
               }
            }
         }
      }

      if(var1.getState().equals(EventState.valueOf((String)this.C.get())) && this.shouldAttack() && !this.serverLag() && this.timerAttack.delay((double)(1000L / this.A())) && (!((Speed)this.getModule(Speed.class)).c() || this.mc.player.ticksExisted % 2 != 0)) {
         this.a(this.target);
         this.timerAttack.reset();
      }

      if(var1.getState().equals(EventState.valueOf((String)this.N.get())) && this.Q()) {
         this.x();
         this.mc.player.getHeldItem().useItemRightClick(this.mc.world, this.mc.player);
         this.g();
      }

   }

   public void S() {
      float var2 = 1.0F;
      KillAura.Q();
      int var3 = -1;
      int var4 = 0;
      if(var4 < 9) {
         ItemStack var5 = this.mc.player.inventory.getStackInSlot(var4);
         if(var5 != null && this.a(var5) > var2) {
            var3 = var4;
            this.a(var5);
         }

         ++var4;
      }

      if(var3 != -1 && this.mc.player.inventory.getStackInSlot(this.mc.player.inventory.currentItem) != this.mc.player.inventory.getStackInSlot(var3)) {
         this.mc.player.inventory.currentItem = var3;
      }

   }

   private float a(ItemStack var1) {
      KillAura.Q();
      float var3 = 0.0F;
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).getDamage();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).getAttackDamage();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.3F;
      return var3;
   }

   private void g() {
      int[] var1 = KillAura.Q();
      if(!this.I) {
         this.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.player.getHeldItem()));
         this.I = true;
      }

   }

   private void d() {
      int[] var1 = KillAura.Q();
      if(this.I) {
         this.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         this.I = false;
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix("Single");
   }

   @EventTarget
   public void a(SlowdownEvent var1) {
      if(this.Q()) {
         var1.setCancelled(true);
      }

   }

   @EventTarget
   public void b(PacketEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getDirection().equals(PacketDirection.OUTGOING)) {
         if(this.Q() || this.I) {
            if(var1.getPacket() instanceof C07PacketPlayerDigging) {
               C07PacketPlayerDigging var3 = (C07PacketPlayerDigging)var1.getPacket();
               if(var3.getStatus().equals(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM)) {
                  var1.setCancelled(true);
               }
            }

            if(var1.getPacket() instanceof C08PacketPlayerBlockPlacement) {
               C08PacketPlayerBlockPlacement var4 = (C08PacketPlayerBlockPlacement)var1.getPacket();
               if(var4.getPlacedBlockDirection() == 255 && var4.getStack() != null && var4.getStack().getItem() instanceof ItemSword) {
                  var1.setCancelled(true);
               }
            }
         }

         if(!this.shouldRotate() || !ServerUtils.isHypixel() || !(var1.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
            return;
         }

         C08PacketPlayerBlockPlacement var5 = (C08PacketPlayerBlockPlacement)var1.getPacket();
         if(var5.getPlacedBlockDirection() != 255 || var5.getStack() == null || !(var5.getStack().getItem() instanceof ItemSword)) {
            this.aa = var5;
            var1.setCancelled(true);
         }
      }

      this.lossTimer.reset();
   }

   @EventTarget
   public void c(MotionUpdateEvent var1) {
      int[] var2 = KillAura.Q();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.shouldRotate()) {
            float[] var3 = RotationUtil.b((EntityLivingBase)this.target);
            float[] var4 = RotationUtil.a(var3, ((Integer)this.K.get()).intValue());
            this.x = var4[0];
            this.T = var4[1];
            if(this.aa == null && !((AutoPot)this.getModule(AutoPot.class)).d() && this.z().size() == 1) {
               var1.setYaw(this.x);
               var1.setPitch(this.T);
            }
         }

         this.x = this.mc.player.rotationYaw;
         this.T = this.mc.player.rotationPitch;
      }

      if(this.aa != null) {
         this.sendPacketNoEvent(this.aa);
         this.aa = null;
      }

   }

   public float i() {
      return this.x;
   }

   public float L() {
      return this.T;
   }

   private boolean a(Block var1) {
      int[] var2 = KillAura.Q();
      return var1 == Blocks.chest || var1 == Blocks.trapped_chest || var1 == Blocks.crafting_table || var1 == Blocks.furnace || var1 == Blocks.ender_chest || var1 == Blocks.enchanting_table;
   }

   private void x() {
      int[] var1 = KillAura.Q();
      if(this.mc.gameSettings.keyBindUseItem.isKeyDown()) {
         if(this.mc.objectMouseOver.entityHit != null) {
            this.sendPacket(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity$Action.INTERACT));
         }

         if(this.a(this.mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock())) {
            this.mc.at.onPlayerRightClick(this.mc.player, this.mc.world, this.mc.player.getHeldItem(), this.mc.objectMouseOver.getBlockPos(), Block.getFacingDirection(this.mc.objectMouseOver.getBlockPos()), this.mc.objectMouseOver.hitVec);
         }
      }

   }

   public boolean isValidEntity(Entity var1) {
      KillAura.Q();
      boolean var3 = this.targets.contains("Mobs");
      boolean var4 = this.targets.contains("Animals");
      boolean var5 = this.targets.contains("Players");
      boolean var6 = this.targets.contains("Invisibles");
      if(var1.isEntityAlive() && this.mc.player.getHealth() > 0.0F && !this.novoline.getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
         if(!(var1 instanceof EntityMob) && !(var1 instanceof EntitySlime) && !(var1 instanceof EntityGolem)) {
            if(!(var1 instanceof EntityAnimal) && !(var1 instanceof EntityVillager)) {
               if(var1 instanceof EntityPlayer) {
                  return var1 != this.mc.player && (!this.filters.contains("Armor") || this.hasArmor((EntityPlayer)var1)) && (!this.filters.contains("Teams") || !PlayerUtils.inTeamWithMinecraftPlayer(var1)) && (!var1.isInvisible() || var6) && !this.isAutismShopKeeperCheck(var1) && var1 != ((Freecam)this.getModule(Freecam.class)).getFreecamEntity() && var1 != ((Blink)this.getModule(Blink.class)).getBlinkEntity();
               } else {
                  return false;
               }
            } else {
               return var4 && (!ServerUtils.serverIs(Servers.SW) && !ServerUtils.serverIs(Servers.BW) || !((ClickGUI)this.getModule(ClickGUI.class)).k().contains(Integer.valueOf(var1.getEntityID()))) && (!var1.isInvisible() || var6);
            }
         } else {
            return var3 && (!ServerUtils.serverIs(Servers.SW) && !ServerUtils.serverIs(Servers.BW) || !((ClickGUI)this.getModule(ClickGUI.class)).k().contains(Integer.valueOf(var1.getEntityID()))) && (!var1.isInvisible() || var6);
         }
      } else {
         return false;
      }
   }

   private boolean a(boolean var1) {
      KillAura.Q();
      ObjectListIterator var3 = ((ObjectArrayList)this.a((String)this.aj.get()).stream().filter(this::lambda$isContainsTarget$4).collect(Collectors.toCollection(ObjectArrayList::<init>))).iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(this.novoline.getPlayerManager().hasType(var4.getName(), PlayerManager$EnumPlayerType.TARGET)) {
            return true;
         }
      }

      return false;
   }

   private List a(String var1) {
      KillAura.Q();
      List var3 = (List)this.mc.world.getLoadedEntityList().stream().filter(this::isValidEntity).collect(Collectors.toList());
      byte var5 = -1;
      switch(var1.hashCode()) {
      case 353103893:
         if(!var1.equals("Distance")) {
            break;
         }

         var5 = 0;
      case 63533343:
         if(!var1.equals("Armor")) {
            break;
         }

         var5 = 1;
      case -2137395588:
         if(!var1.equals("Health")) {
            break;
         }

         var5 = 2;
      case 69805:
         if(!var1.equals("FOV")) {
            break;
         }

         var5 = 3;
      case 765499292:
         if(var1.equals("HurtTime")) {
            var5 = 4;
         }
      }

      switch(var5) {
      case 0:
         var3 = (List)var3.stream().sorted(Comparator.comparing(this::lambda$getEntityList$1)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 1:
         var3 = (List)var3.stream().sorted(Comparator.comparing(this::lambda$getEntityList$6)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 2:
         var3 = (List)var3.stream().sorted(Comparator.comparing(InfiniteAura::lambda$getEntityList$7)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 3:
         var3 = (List)var3.stream().sorted(Comparator.comparing(this::e)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 4:
         var3 = (List)var3.stream().sorted(Comparator.comparing(InfiniteAura::lambda$getEntityList$8)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      default:
         return (List)var3.stream().filter(this::c).collect(Collectors.toList());
      }
   }

   public List z() {
      return (List)this.a((String)this.aj.get()).stream().filter(this::lambda$getTargetsFromRange$9).collect(Collectors.toList());
   }

   private int targetsInRange() {
      KillAura.Q();
      int var2 = 0;
      if(this.a((String)this.aj.get()).isEmpty()) {
         return 0;
      } else {
         Iterator var3 = this.a((String)this.aj.get()).iterator();
         if(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if((double)this.mc.player.getDistanceToEntity(var4) <= this.range()) {
               ++var2;
            }
         }

         return var2;
      }
   }

   private Entity getTargetFromRange() {
      KillAura.Q();
      Iterator var2 = this.a((String)this.aj.get()).iterator();
      if(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         if((double)this.mc.player.getDistanceToEntity(var3) <= this.range()) {
            return var3;
         }
      }

      return null;
   }

   private float a(float var1) {
      return 0.0F;
   }

   private float a(ItemStack[] var1) {
      float var3 = 0.0F;
      KillAura.Q();
      int var5 = var1.length;
      int var6 = 0;
      if(var6 < var5) {
         ItemStack var7 = var1[var6];
         if(var7.getItem() instanceof ItemArmor) {
            ItemArmor var8 = (ItemArmor)var7.getItem();
            var3 = (float)((double)var3 + (double)var8.damageReduceAmount + (double)((100 - var8.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var7)) * 0.0075D);
            var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, var7) / 100.0D);
            var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, var7) / 100.0D);
            var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, var7) / 100.0D);
            var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var7) / 50.0D);
            var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, var7) / 100.0D);
         }

         ++var6;
      }

      return var3;
   }

   private boolean c(Entity var1) {
      int[] var2 = KillAura.Q();
      return (double)this.mc.player.getDistanceToEntity(var1) <= (this.mc.player.canEntityBeSeen(var1)?Math.max(this.range(), this.u()):this.wallRange());
   }

   @EventTarget
   public void a(Render2DEvent var1) {
      KillAura.Q();
      Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.drawString("Ты пидор", 200.0D, 200.0D, -1, true);
      if(((Boolean)this.targetHud.get()).booleanValue() && this.shouldAttack() && this.target instanceof EntityPlayer && !(this.mc.currentScreen instanceof aHM)) {
         RenderUtils.a((InfiniteAura)this, (EntityPlayer)this.target);
      }

   }

   @EventTarget
   public void a(Render3DEvent var1) {
      int[] var2 = KillAura.Q();
      if(this.shouldAttack()) {
         RenderUtils.pre3D();
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);
         ArrayList var3 = this.a(new Vec3(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ), new Vec3(this.target.posX, this.target.posY, this.target.posZ));
         Iterator var4 = var3.iterator();
         if(var4.hasNext()) {
            Vec3 var5 = (Vec3)var4.next();
            double var6 = var5.getX() - this.mc.getRenderManager().renderPosX;
            double var8 = var5.getY() - this.mc.getRenderManager().renderPosY;
            double var10 = var5.getZ() - this.mc.getRenderManager().renderPosZ;
            GL11.glVertex3d(var6, var8, var10);
         }

         GL11.glEnd();
         RenderUtils.post3D();
      }

   }

   private ArrayList a(Vec3 var1, Vec3 var2) {
      int[] var3 = KillAura.Q();
      if(!this.a(new BlockPos(var1.mc()))) {
         var1 = var1.addVector(0.0D, 1.0D, 0.0D);
      }

      AStarCustomPathfinder var4 = new AStarCustomPathfinder(var1, var2);
      var4.compute();
      int var5 = 0;
      Object var6 = null;
      Vec3 var7 = null;
      ArrayList var8 = new ArrayList();
      ArrayList var9 = var4.getPath();
      Iterator var10 = var9.iterator();
      if(var10.hasNext()) {
         Vec3 var11 = (Vec3)var10.next();
         if(var5 == 0 || var5 == var9.size() - 1) {
            if(var6 != null) {
               var8.add(((Vec3)var6).addVector(0.5D, 0.0D, 0.5D));
            }

            var8.add(var11.addVector(0.5D, 0.0D, 0.5D));
            var7 = var11;
         }

         boolean var12 = true;
         if(var11.squareDistanceTo(var7) > 81.0D) {
            var12 = false;
         }

         double var13 = Math.min(var7.getX(), var11.getX());
         double var15 = Math.min(var7.getY(), var11.getY());
         double var17 = Math.min(var7.getZ(), var11.getZ());
         double var19 = Math.max(var7.getX(), var11.getX());
         double var21 = Math.max(var7.getY(), var11.getY());
         double var23 = Math.max(var7.getZ(), var11.getZ());
         int var25 = (int)var13;
         if((double)var25 <= var19) {
            int var26 = (int)var15;
            if((double)var26 <= var21) {
               int var27 = (int)var17;
               if((double)var27 <= var23) {
                  if(!AStarCustomPathfinder.checkPositionValidity(var25, var26, var27, false)) {
                     var12 = false;
                  }

                  ++var27;
               }

               ++var26;
            }

            ++var25;
         }

         if(!var12) {
            var8.add(((Vec3)var6).addVector(0.5D, 0.0D, 0.5D));
         }

         ++var5;
      }

      return var8;
   }

   private boolean a(BlockPos var1) {
      KillAura.Q();
      Block var3 = Minecraft.getInstance().world.getBlockState(new BlockPos(var1.getX(), var1.getY(), var1.getZ())).getBlock();
      return var3.getMaterial() == Material.air || var3.getMaterial() == Material.plants || var3.getMaterial() == Material.vine || var3 == Blocks.ladder || var3 == Blocks.water || var3 == Blocks.flowing_water || var3 == Blocks.wall_sign || var3 == Blocks.standing_sign;
   }

   public Entity getTarget() {
      return this.target;
   }

   public void setTarget(Entity var1) {
      this.target = var1;
   }

   public long A() {
      return ServerUtils.a()?2L:ThreadLocalRandom.current().nextLong(((Double)this.L.get()).longValue(), ((Double)this.ag.get()).longValue() + 1L);
   }

   public DoubleProperty getRange() {
      return this.range;
   }

   public DoubleProperty P() {
      return this.A;
   }

   public DoubleProperty k() {
      return this.ae;
   }

   public double range() {
      return ((Double)this.range.get()).doubleValue();
   }

   public double u() {
      return ((Double)this.ae.get()).doubleValue();
   }

   public double wallRange() {
      return ((Double)this.A.get()).doubleValue();
   }

   public ListProperty getTargets() {
      return this.targets;
   }

   public ListProperty getFilters() {
      return this.filters;
   }

   public ListProperty N() {
      return this.M;
   }

   public BooleanProperty f() {
      return this.D;
   }

   public BooleanProperty getTargetHud() {
      return this.targetHud;
   }

   public ListProperty n() {
      return this.ak;
   }

   public IntProperty C() {
      return this.V;
   }

   public IntProperty G() {
      return this.ab;
   }

   public List getTargetList() {
      return this.targetList;
   }

   public StringProperty t() {
      return this.aj;
   }

   public boolean lagCheck() {
      return ((Boolean)this.lagCheck.get()).booleanValue();
   }

   public StringProperty a() {
      return this.U;
   }

   public DoubleProperty H() {
      return this.L;
   }

   public DoubleProperty r() {
      return this.ag;
   }

   public boolean m() {
      return ((Boolean)this.z.get()).booleanValue();
   }

   public StringProperty w() {
      return this.C;
   }

   public StringProperty E() {
      return this.y;
   }

   public StringProperty K() {
      return this.N;
   }

   private boolean lambda$getTargetsFromRange$9(Entity var1) {
      int[] var2 = KillAura.Q();
      return (double)this.mc.player.getDistanceToEntity(var1) <= this.range();
   }

   private static Integer lambda$getEntityList$8(Entity var0) {
      return Integer.valueOf(var0.hurtResistantTime);
   }

   private static Float lambda$getEntityList$7(Entity var0) {
      return Float.valueOf(((EntityLivingBase)var0).getHealth());
   }

   private Float lambda$getEntityList$6(Entity var1) {
      return Float.valueOf(var1 instanceof EntityPlayer?this.a(((EntityPlayer)var1).inventory.armorInventory):99999.0F);
   }

   private Float lambda$getEntityList$1(Entity var1) {
      return Float.valueOf(var1.getDistanceToEntity(this.mc.player));
   }

   private boolean lambda$isContainsTarget$4(Entity var1) {
      int[] var2 = KillAura.Q();
      return (double)this.mc.player.getDistanceToEntity(var1) < this.range();
   }

   private Boolean lambda$new$3() {
      int[] var1 = KillAura.Q();
      return Boolean.valueOf(!this.M.isEmpty() && this.af.equals("Hit"));
   }

   private Boolean lambda$new$2() {
      int[] var1 = KillAura.Q();
      return Boolean.valueOf(!this.M.isEmpty());
   }

   private static Boolean lambda$new$1() {
      int[] var0 = KillAura.Q();
      return Boolean.valueOf(!ServerUtils.a());
   }

   private static Boolean lambda$new$0() {
      int[] var0 = KillAura.Q();
      return Boolean.valueOf(!ServerUtils.a());
   }
}
