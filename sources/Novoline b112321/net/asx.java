package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.UW;
import net.VN;
import net.WB;
import net.WL;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEB;
import net.aEE;
import net.aEl;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.apV;
import net.as0;
import net.as2;
import net.as_;
import net.au7;
import net.auS;
import net.av2;
import net.avS;
import net.avq;
import net.avu;
import net.aw5;
import net.awR;
import net.aww;
import net.axu;
import net.aye;
import net.azi;
import net.d3;
import net.lS;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.command.ICommandSender;
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
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class asx extends as0 {
   private Entity ar;
   private int al;
   private List D;
   private final d3 z;
   private final d3 aj;
   private final d3 ap;
   private final Dimension S;
   private final List ad;
   private float am;
   private float ac;
   private float ai;
   private float F;
   private boolean I;
   private boolean A;
   private C08PacketPlayerBlockPlacement y;
   @VN("mode")
   private final aEs an;
   @VN("switch-delay")
   private final aEl W;
   @VN("min-aps")
   private final aEE V;
   @VN("max-aps")
   private final aEE L;
   @VN("range")
   private final aEE U;
   @VN("wall-range")
   private final aEE H;
   @VN("block-range")
   private final aEE E;
   @VN("fov-check")
   private final aE8 af;
   @VN("rotations-smoothness")
   private final aE8 ak;
   @VN("targets")
   private final aE3 J;
   @VN("aura-sort")
   private final aEs Z;
   @VN("filters")
   private final aE3 aa;
   @VN("particles")
   private final aE3 T;
   @VN("auto-block")
   private final aEu O;
   @VN("target-hud")
   private final aEu ae;
   @VN("th-mode")
   private final aEs ao;
   @VN("autodisable")
   private final aE3 ag;
   @VN("blink")
   private final aEu M;
   @VN("raytrace")
   private final aEu R;
   @VN("lag-check")
   private final aEu x;
   @VN("visualize-range")
   private final aEu ah;
   @VN("color")
   private final aEB Q;
   @VN("th-x")
   private final aE8 N;
   @VN("th-y")
   private final aE8 K;
   @VN("particles-mode")
   private final aEs G;
   @VN("particles-amount")
   private final aE8 Y;
   @VN("auto-weapon")
   private final aEu B;
   @VN("keep-sprint")
   private final aEu C;
   @VN("auto-block-mode")
   private final aEs aq;
   @VN("attack-event")
   private final aEs P;
   @VN("autoblock-event")
   private final aEs ab;
   private static int[] X;

   public asx(UW var1) {
      Q();
      super(var1, "Killaura", "Kill Aura", a2V.COMBAT, "Automatically attacks entities around you");
      this.D = new CopyOnWriteArrayList();
      this.z = new d3();
      this.aj = new d3();
      this.ap = new d3();
      this.S = Toolkit.getDefaultToolkit().getScreenSize();
      this.ad = new ArrayList();
      this.an = axu.a("Switch").a(new String[]{"Multi", "Switch", "Single"});
      this.W = (aEl)((aEl)axu.a(Float.valueOf(400.0F)).d(Float.valueOf(50.0F))).c(Float.valueOf(1000.0F));
      this.V = (aEE)((aEE)axu.a(Double.valueOf(5.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(20.0D));
      this.L = (aEE)((aEE)axu.a(Double.valueOf(5.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(20.0D));
      this.U = (aEE)((aEE)axu.a(Double.valueOf(3.5D)).d(Double.valueOf(1.0D))).c(Double.valueOf(7.0D));
      this.H = (aEE)((aEE)axu.a(Double.valueOf(3.5D)).d(Double.valueOf(1.0D))).c(Double.valueOf(7.0D));
      this.E = (aEE)((aEE)axu.a(Double.valueOf(3.5D)).d(Double.valueOf(1.0D))).c(Double.valueOf(10.0D));
      this.af = (aE8)((aE8)axu.b(Integer.valueOf(180)).d(Integer.valueOf(1))).c(Integer.valueOf(180));
      this.ak = (aE8)((aE8)axu.b(Integer.valueOf(60)).d(Integer.valueOf(1))).c(Integer.valueOf(100));
      this.J = axu.a((Object)"Players").a((Object[])(new String[]{"Players", "Animals", "Mobs", "Invisibles"}));
      this.Z = axu.a("Distance").a(new String[]{"Distance", "Health", "Armor", "FOV", "HurtTime"});
      this.aa = axu.a((Object)"Teams").a((Object[])(new String[]{"Teams", "Armor"}));
      this.T = axu.a((Object)"Enchant").a((Object[])(new String[]{"Enchant", "Critical"}));
      this.O = axu.g();
      this.ae = axu.g();
      this.ao = axu.a("Pretty").a(new String[]{"Trash", "Less Pretty", "Pretty", "Prettier", "Prettiest"});
      this.ag = axu.a((Object)"Death").a((Object[])(new String[]{"World Change", "Game End", "Death"}));
      this.M = axu.g();
      this.R = axu.g();
      this.x = axu.g();
      this.ah = axu.g();
      this.Q = axu.a(Integer.valueOf(-1));
      this.N = (aE8)((aE8)axu.b(Integer.valueOf((int)this.S.getWidth() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.S.getWidth() / 2));
      this.K = (aE8)((aE8)axu.b(Integer.valueOf((int)this.S.getHeight() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.S.getHeight() / 2));
      this.G = axu.a("Hit").a(new String[]{"Hit", "Always"});
      this.Y = (aE8)((aE8)axu.b(Integer.valueOf(2)).d(Integer.valueOf(1))).c(Integer.valueOf(5));
      this.B = axu.g();
      this.C = axu.g();
      this.aq = axu.a("Packet").a(new String[]{"Packet", "Vanilla"});
      this.P = axu.a("POST").a(new String[]{"PRE", "POST"});
      this.ab = axu.a("POST").a(new String[]{"PRE", "POST"});
      ae9.a(new adZ("AURA_MODE", "Mode", a6d.COMBOBOX, this, this.an));
      ae9.a(new adZ("AURA_SORT", "Sort by", a6d.COMBOBOX, this, this.Z, this::lambda$new$0));
      ae9.a(new adZ("SWITCH_DELAY", "Switch Delay", a6d.SLIDER, this, this.W, 50.0D, this::lambda$new$1));
      ae9.a(new adZ("KA_MIN_APS", "Min APS", a6d.SLIDER, this, this.V, 1.0D, asx::lambda$new$2));
      ae9.a(new adZ("KA_MAX_APS", "Max APS", a6d.SLIDER, this, this.L, 1.0D, asx::lambda$new$3));
      ae9.a(new adZ("KA_ATTACK_EVENT", "Attack Event", a6d.COMBOBOX, this, this.P));
      ae9.a(new adZ("RANGE", "Range", a6d.SLIDER, this, this.U, 0.1D));
      ae9.a(new adZ("BLOCK_RANGE", "Block Range", a6d.SLIDER, this, this.E, 0.1D));
      ae9.a(new adZ("WALL_RANGE", "Wall Range", a6d.SLIDER, this, this.H, 0.1D));
      ae9.a(new adZ("AURA_FOV", "FOV Check", a6d.SLIDER, this, this.af, 1.0D));
      ae9.a(new adZ("AURA_FOV", "Angle Smoothing", a6d.SLIDER, this, this.ak, 5.0D));
      ae9.a(new adZ("TARGETS", "Targets", a6d.SELECTBOX, this, this.J));
      ae9.a(new adZ("KA_FILTER", "Filters", a6d.SELECTBOX, this, this.aa));
      ae9.a(new adZ("KA_PARTICLES", "Particles", a6d.SELECTBOX, this, this.T));
      ae9.a(new adZ("KA_PARTICLES_MODE", "Particles Mode", a6d.COMBOBOX, this, this.G, this::lambda$new$4));
      ae9.a(new adZ("KA_PARTICLES_AMOUNT", "Particles Amount", a6d.SLIDER, this, this.Y, 1.0D, this::lambda$new$5));
      ae9.a(new adZ("KA_KEEP_SPRINT", "Keep Sprint", a6d.CHECKBOX, this, this.C));
      ae9.a(new adZ("KA_LAG_CHECK", "Lag Check", a6d.CHECKBOX, this, this.x));
      ae9.a(new adZ("KA_RAY_TRACE", "Ray Trace", a6d.CHECKBOX, this, this.R));
      ae9.a(new adZ("KA_VIS_RANGE", "Visualize Range", a6d.CHECKBOX, this, this.ah));
      a6d var10004 = a6d.COLOR_PICKER;
      aEB var10006 = this.Q;
      aEu var10008 = this.ah;
      this.ah.getClass();
      ae9.a(new adZ("KA_RANGE_COLOR", "Range Color", var10004, this, var10006, (EnumSet)null, var10008::a));
      ae9.a(new adZ("KA_BLINK", "Blink", a6d.CHECKBOX, this, this.M));
      ae9.a(new adZ("KA_AUTOBLOCK", "Auto Block", a6d.CHECKBOX, this, this.O));
      ae9.a(new adZ("KA_AB_MODE", "Auto Block Mode", a6d.COMBOBOX, this, this.aq));
      ae9.a(new adZ("KA_AB_EVENT", "Auto Block Event", a6d.COMBOBOX, this, this.ab));
      ae9.a(new adZ("KA_AUTO_WEAPON", "Auto Weapon", a6d.CHECKBOX, this, this.B));
      ae9.a(new adZ("TAR_HUD", "Target HUD", a6d.CHECKBOX, this, this.ae));
      var10004 = a6d.COMBOBOX;
      aEs var4 = this.ao;
      aEu var10007 = this.ae;
      this.ae.getClass();
      ae9.a(new adZ("TAR_HUD_MODE", "Style", var10004, this, var4, var10007::a));
      ae9.a(new adZ("KADISABLE", "Disable On", a6d.SELECTBOX, this, this.ag));
   }

   public void c() {
      this.am = this.w.thePlayer.rotationYaw;
      this.ac = this.w.thePlayer.rotationPitch;
      this.ar = null;
      this.y = null;
      this.D.clear();
      this.G();
   }

   public void n() {
      Q();
      this.c((String)((String)this.an.a()));
      this.am = this.w.thePlayer.rotationYaw;
      this.ac = this.w.thePlayer.rotationPitch;
      this.ap.b();
      if(((Boolean)this.M.a()).booleanValue()) {
         this.ad.clear();
         this.I = true;
      }

      this.I = false;
   }

   private boolean a(EntityPlayer var1) {
      int[] var2 = Q();
      return var1.bJ.armorInventory[0] != null || var1.bJ.armorInventory[1] != null || var1.bJ.armorInventory[2] != null || var1.bJ.armorInventory[3] != null;
   }

   private boolean u() {
      int[] var1 = Q();
      return this.w.thePlayer.getHeldItem() != null && this.w.thePlayer.getHeldItem().getItem() instanceof ItemSword;
   }

   private boolean f() {
      int[] var1 = Q();
      if(this.ar == null) {
         return false;
      } else {
         float[] var2 = awR.b((EntityLivingBase)this.ar);
         int var3 = (int)var2[1];
         int var4 = (int)var2[0];
         int var5 = (int)this.w.thePlayer.rotationYaw;
         int var6 = (int)this.w.thePlayer.rotationPitch;
         int var7 = Math.abs(var5 - var4);
         int var8 = Math.abs(var6 - var3);
         return var7 <= ((Integer)this.af.a()).intValue() && var8 <= ((Integer)this.af.a()).intValue();
      }
   }

   public boolean W() {
      int[] var1 = Q();
      return ((Boolean)this.x.a()).booleanValue() && this.ap.f() >= 100.0D;
   }

   public boolean P() {
      int[] var1 = Q();
      return this.y() && this.ar != null && this.ar.isEntityAlive() && (double)this.w.thePlayer.getDistanceToEntity(this.ar) <= (this.w.thePlayer.canEntityBeSeen(this.ar)?this.x():this.N()) && this.f() && !this.a((Class)avu.class);
   }

   public boolean k() {
      int[] var1 = Q();
      return this.P() && !this.W() && !this.a((Class)avu.class) && !this.w.playerController.f();
   }

   public boolean E() {
      int[] var1 = Q();
      return ((Boolean)this.O.a()).booleanValue() && this.ar != null && this.u() && this.ar.isEntityAlive() && this.y() && !this.w.playerController.f() && (double)this.w.thePlayer.getDistanceToEntity(this.ar) <= (this.w.thePlayer.canEntityBeSeen(this.ar)?this.j():this.N()) && !this.a((Class)avu.class);
   }

   private boolean e(Entity var1) {
      IChatComponent var3 = var1.getDisplayName();
      Q();
      String var4 = var3.getFormattedText();
      String var5 = var3.getUnformattedText();
      boolean var6 = !var4.substring(0, var4.length() - 2).contains("§");
      boolean var7 = var4.substring(var4.length() - 2).contains("§");
      return lS.c() && lS.a(WL.BW) && var6 && var7;
   }

   private double f(Entity var1) {
      float[] var3 = awR.b((EntityLivingBase)var1);
      Q();
      float var4 = (float)((int)var3[0]);
      float var5 = this.w.thePlayer.rotationYaw > var4?this.w.thePlayer.rotationYaw - var4:var4 - this.w.thePlayer.rotationYaw;
      return (double)var5;
   }

   @agu
   public void c(aG1 var1) {
      int[] var2 = Q();
      if(var1.h().equals(EventState.PRE)) {
         if(this.w.thePlayer.bJ.getStackInSlot(0) != null && this.w.thePlayer.bJ.getStackInSlot(0).getItem() == Items.compass && this.w.thePlayer.bJ.getStackInSlot(0).getDisplayName().contains("Teleporter")) {
            this.ar = null;
         }

         if(this.T() > 0) {
            this.D = this.z();
         }

         this.D = this.a((String)this.Z.a());
         if(this.ar != null && !this.ar.isEntityAlive()) {
            this.ar = null;
         }

         if(this.D != null) {
            label166: {
               if(((String)this.an.a()).equals("Multi")) {
                  this.ar = (Entity)this.D.get(0);
               }

               if(this.aj.a((double)((Float)this.W.a()).floatValue())) {
                  this.Y();
               }

               if(!this.a(this.U().equalsIgnoreCase("Switch"))) {
                  if(this.ar != null) {
                     if((double)this.w.thePlayer.getDistanceToEntity(this.ar) > this.x() && this.T() > 0) {
                        this.ar = this.X();
                     }

                     if((double)this.w.thePlayer.getDistanceToEntity(this.ar) > this.j()) {
                        this.ar = null;
                        this.Y();
                     }

                     if(!this.ar.isEntityAlive() && ((EntityLivingBase)this.ar).getHealth() <= 0.0F) {
                        this.ar = null;
                        this.Y();
                     }

                     if(!this.U().equalsIgnoreCase("Switch")) {
                        break label166;
                     }

                     this.ar = (Entity)this.D.get(this.al);
                  }

                  this.ar = (Entity)this.D.get(this.U().equalsIgnoreCase("Switch")?this.al:0);
               }

               Iterator var3 = this.D.iterator();
               if(var3.hasNext()) {
                  Entity var4 = (Entity)var3.next();
                  if(this.j.k().b(var4.getName(), au7.TARGET)) {
                     this.ar = var4;
                  }
               }
            }
         }

         this.F = this.ac;
         this.ai = this.am;
      }

   }

   public float O() {
      return this.F;
   }

   public float d() {
      return this.ai;
   }

   private void Y() {
      this.al = (this.al + 1) % this.D.size();
      this.aj.b();
   }

   private boolean a(Entity var1) {
      int[] var2 = Q();
      return !((Boolean)this.R.a()).booleanValue() || this.j.k().b(var1.getName(), au7.TARGET) || this.m() != null || this.w.playerController.f();
   }

   private void g(Entity var1) {
      int[] var2 = Q();
      if(this.E()) {
         if(lS.c()) {
            this.a((Packet)(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
         }

         if(this.aq.a("Packet")) {
            this.I();
         }

         this.A = false;
      }

      this.w.thePlayer.swingItem();
      this.b((Packet)(new C02PacketUseEntity(var1, C02PacketUseEntity$Action.ATTACK)));
      if(!((Boolean)this.C.a()).booleanValue() && var1.hurtResistantTime >= 19) {
         this.w.thePlayer.motionX *= 0.6D;
         this.w.thePlayer.motionZ *= 0.6D;
      }

      if(this.G.a("Always")) {
         if(this.T.a((Object)"Enchant")) {
            this.w.thePlayer.onEnchantmentCritical(var1);
         }

         if(this.T.a((Object)"Critical")) {
            this.w.thePlayer.onCriticalHit(var1);
         }
      }

   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = Q();
      if(var1.h().equals(EventState.PRE)) {
         if(!this.E() || this.w.thePlayer.p() && this.aq.a("Packet")) {
            this.I();
         }

         if(this.P()) {
            if(((Boolean)this.B.a()).booleanValue() && !this.w.playerController.f()) {
               this.F();
            }

            if(this.G.a("Hit") && !this.T.e() && this.ar.hurtResistantTime == 20) {
               int var3 = 0;
               if(var3 < ((Integer)this.Y.a()).intValue()) {
                  this.w.thePlayer.onEnchantmentCritical(this.ar);
                  this.w.thePlayer.onCriticalHit(this.ar);
                  ++var3;
               }
            }
         }
      }

      if(var1.h().equals(EventState.valueOf(lS.c()?"PRE":(String)this.P.a())) && this.P() && this.a(this.ar) && !this.W() && this.z.a((double)(1000L / this.p())) && (!((avS)this.b((Class)avS.class)).c() || this.w.thePlayer.ticksExisted % 2 == 0)) {
         if(((String)this.an.a()).equals("Multi")) {
            Iterator var6 = this.z().iterator();
            if(var6.hasNext()) {
               Entity var4 = (Entity)var6.next();
               this.g(var4);
            }
         }

         this.g(this.ar);
         this.z.b();
      }

      if(var1.h().equals(EventState.valueOf((String)this.ab.a())) && this.E()) {
         this.y();
         this.w.thePlayer.getHeldItem().a(this.w.theWorld, this.w.thePlayer);
         this.S();
      }

   }

   public void F() {
      Q();
      float var2 = 1.0F;
      int var3 = -1;
      int var4 = 0;
      if(var4 < 9) {
         ItemStack var5 = this.w.thePlayer.bJ.getStackInSlot(var4);
         if(var5 != null && this.a(var5) > var2) {
            var3 = var4;
            this.a(var5);
         }

         ++var4;
      }

      if(var3 != -1 && this.w.thePlayer.bJ.getStackInSlot(this.w.thePlayer.bJ.currentItem) != this.w.thePlayer.bJ.getStackInSlot(var3)) {
         this.w.thePlayer.bJ.currentItem = var3;
      }

   }

   private float a(ItemStack var1) {
      Q();
      float var3 = 0.0F;
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).b();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).a();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.3F;
      return var3;
   }

   private void S() {
      int[] var1 = Q();
      if(!this.A) {
         this.a((Packet)(new C08PacketPlayerBlockPlacement(this.w.thePlayer.getHeldItem())));
         this.A = true;
      }

   }

   private void I() {
      int[] var1 = Q();
      if(this.A) {
         this.a((Packet)(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
         this.A = false;
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)this.U());
   }

   @agu
   public void a(apV var1) {
      if(this.E()) {
         var1.setCancelled(true);
      }

   }

   @agu
   public void b(ap9 var1) {
      int[] var2 = Q();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(this.I) {
            if(var1.d() instanceof C03PacketPlayer) {
               C03PacketPlayer var3 = (C03PacketPlayer)var1.d();
               if(var3.isMoving()) {
                  this.ad.add(var1.d());
                  var1.setCancelled(true);
               }
            }

            if(var1.d() instanceof C02PacketUseEntity) {
               C02PacketUseEntity var4 = (C02PacketUseEntity)var1.d();
               if(var4.getAction().equals(C02PacketUseEntity$Action.ATTACK) && var4.getEntityFromWorld(this.w.theWorld).equals(this.ar)) {
                  this.G();
                  this.I = false;
               }
            }

            if(this.ad.size() > 60) {
               this.G();
               this.j.t().a(this.f(), "Packet buffer overloaded!", 2000, azi.ERROR);
               this.I = false;
            }
         }

         if(this.E() || this.A) {
            if(var1.d() instanceof C07PacketPlayerDigging) {
               C07PacketPlayerDigging var5 = (C07PacketPlayerDigging)var1.d();
               if(var5.getStatus().equals(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM)) {
                  var1.setCancelled(true);
               }
            }

            if(var1.d() instanceof C08PacketPlayerBlockPlacement) {
               C08PacketPlayerBlockPlacement var6 = (C08PacketPlayerBlockPlacement)var1.d();
               if(var6.getPlacedBlockDirection() == 255 && var6.getStack() != null && var6.getStack().getItem() instanceof ItemSword) {
                  var1.setCancelled(true);
               }
            }
         }

         if(!this.k() || !lS.c() || !(var1.d() instanceof C08PacketPlayerBlockPlacement)) {
            return;
         }

         C08PacketPlayerBlockPlacement var7 = (C08PacketPlayerBlockPlacement)var1.d();
         if(var7.getPlacedBlockDirection() != 255 || var7.getStack() == null || !(var7.getStack().getItem() instanceof ItemSword)) {
            this.y = var7;
            var1.setCancelled(true);
         }
      }

      this.ap.b();
   }

   @agu
   public void b(aG1 var1) {
      int[] var2 = Q();
      if(var1.h().equals(EventState.PRE)) {
         if(this.k()) {
            float[] var3 = awR.b((EntityLivingBase)this.ar);
            float[] var4 = awR.a(var3, ((Integer)this.ak.a()).intValue());
            this.am = var4[0];
            this.ac = var4[1];
            if(this.y == null && !((as2)this.b((Class)as2.class)).d() && (!((String)this.an.a()).equals("Multi") || this.z().size() == 1)) {
               var1.b(this.am);
               var1.a(this.ac);
            }
         }

         this.am = this.w.thePlayer.rotationYaw;
         this.ac = this.w.thePlayer.rotationPitch;
      }

      if(this.y != null) {
         this.a((Packet)this.y);
         this.y = null;
      }

   }

   public float s() {
      return this.am;
   }

   public float H() {
      return this.ac;
   }

   private boolean a(Block var1) {
      int[] var2 = Q();
      return var1 == Blocks.chest || var1 == Blocks.trapped_chest || var1 == Blocks.crafting_table || var1 == Blocks.furnace || var1 == Blocks.ender_chest || var1 == Blocks.enchanting_table;
   }

   private void y() {
      int[] var1 = Q();
      if(this.w.gameSettings.keyBindUseItem.isKeyDown()) {
         if(this.w.objectMouseOver.entityHit != null) {
            this.b((Packet)(new C02PacketUseEntity(this.w.objectMouseOver.entityHit, C02PacketUseEntity$Action.INTERACT)));
         }

         if(this.a(this.w.theWorld.getBlockState(this.w.objectMouseOver.getBlockPos()).getBlock())) {
            this.w.playerController.onPlayerRightClick(this.w.thePlayer, this.w.theWorld, this.w.thePlayer.getHeldItem(), this.w.objectMouseOver.getBlockPos(), Block.a(this.w.objectMouseOver.getBlockPos()), this.w.objectMouseOver.hitVec);
         }
      }

   }

   public boolean c(Entity var1) {
      boolean var3 = this.J.a((Object)"Mobs");
      Q();
      boolean var4 = this.J.a((Object)"Animals");
      boolean var5 = this.J.a((Object)"Players");
      boolean var6 = this.J.a((Object)"Invisibles");
      if(var1.isEntityAlive() && this.w.thePlayer.getHealth() > 0.0F && !this.j.k().b(var1.getName(), au7.FRIEND)) {
         if(!(var1 instanceof EntityMob) && !(var1 instanceof EntitySlime) && !(var1 instanceof EntityGolem)) {
            if(!(var1 instanceof EntityAnimal) && !(var1 instanceof EntityVillager)) {
               if(var1 instanceof EntityPlayer) {
                  return var1 != this.w.thePlayer && (!this.aa.a((Object)"Armor") || this.a((EntityPlayer)var1)) && (!this.aa.a((Object)"Teams") || !aww.a((ICommandSender)var1)) && (!var1.isInvisible() || var6) && !this.e(var1) && var1 != ((avq)this.b((Class)avq.class)).b() && var1 != ((as_)this.b((Class)as_.class)).c();
               } else {
                  return false;
               }
            } else {
               return var4 && (!lS.a(WL.SW) && !lS.a(WL.BW) || !((av2)this.b((Class)av2.class)).k().contains(Integer.valueOf(var1.getEntityId()))) && (!var1.isInvisible() || var6);
            }
         } else {
            return var3 && (!lS.a(WL.SW) && !lS.a(WL.BW) || !((av2)this.b((Class)av2.class)).k().contains(Integer.valueOf(var1.getEntityId()))) && (!var1.isInvisible() || var6);
         }
      } else {
         return false;
      }
   }

   private boolean a(boolean var1) {
      Q();
      ObjectListIterator var3 = ((ObjectArrayList)this.a((String)this.Z.a()).stream().filter(this::lambda$isContainsTarget$6).collect(Collectors.toCollection(ObjectArrayList::<init>))).iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(this.j.k().b(var4.getName(), au7.TARGET)) {
            return true;
         }
      }

      return false;
   }

   private List a(String var1) {
      Q();
      List var3 = (List)this.w.theWorld.getLoadedEntityList().stream().filter(this::c).collect(Collectors.toList());
      if(this.U().equalsIgnoreCase("Single")) {
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
            var3 = (List)var3.stream().sorted(Comparator.comparing(this::lambda$getEntityList$7)).collect(Collectors.toCollection(ObjectArrayList::<init>));
         case 1:
            var3 = (List)var3.stream().sorted(Comparator.comparing(this::lambda$getEntityList$8)).collect(Collectors.toCollection(ObjectArrayList::<init>));
         case 2:
            var3 = (List)var3.stream().sorted(Comparator.comparing(asx::lambda$getEntityList$9)).collect(Collectors.toCollection(ObjectArrayList::<init>));
         case 3:
            var3 = (List)var3.stream().sorted(Comparator.comparing(this::f)).collect(Collectors.toCollection(ObjectArrayList::<init>));
         case 4:
            var3 = (List)var3.stream().sorted(Comparator.comparing(asx::lambda$getEntityList$10)).collect(Collectors.toCollection(ObjectArrayList::<init>));
         }
      }

      return (List)var3.stream().filter(this::d).collect(Collectors.toList());
   }

   public List z() {
      return (List)this.a((String)this.Z.a()).stream().filter(this::lambda$getTargetsFromRange$11).collect(Collectors.toList());
   }

   private int T() {
      Q();
      int var2 = 0;
      if(this.a((String)this.Z.a()).isEmpty()) {
         return 0;
      } else {
         Iterator var3 = this.a((String)this.Z.a()).iterator();
         if(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if((double)this.w.thePlayer.getDistanceToEntity(var4) <= this.x()) {
               ++var2;
            }
         }

         return var2;
      }
   }

   private Entity X() {
      Q();
      Iterator var2 = this.a((String)this.Z.a()).iterator();
      if(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         if((double)this.w.thePlayer.getDistanceToEntity(var3) <= this.x()) {
            return var3;
         }
      }

      return null;
   }

   private float a(float var1) {
      return 0.0F;
   }

   private float a(ItemStack[] var1) {
      Q();
      float var3 = 0.0F;
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

   private boolean d(Entity var1) {
      int[] var2 = Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) <= (this.w.thePlayer.canEntityBeSeen(var1)?Math.max(this.x(), this.j()):this.N());
   }

   @agu
   public void a(aSt var1) {
      int[] var2 = Q();
      if(((Boolean)this.ae.a()).booleanValue() && this.P() && this.ar instanceof EntityPlayer && !(this.w.currentScreen instanceof GuiChat)) {
         a6_.a((asx)this, (EntityLivingBase)((EntityPlayer)this.ar));
      }

      if(this.I) {
         ScaledResolution var3 = var1.a();
         int var4 = this.ad.size();
         String var5 = "";
         if(var4 > 40) {
            var5 = "§8";
         }

         if(var4 > 20) {
            var5 = "§7";
         }

         String var6 = var5 + this.ad.size();
         aw5.a.a("Buffer size: " + var6, (double)(var3.a(this.w) / 2 - 26), (double)(var3.b(this.w) / 2 + 10), 16777215, true);
      }

   }

   @agu
   public void a(aye var1) {
      int[] var2 = Q();
      if(((Boolean)this.ah.a()).booleanValue()) {
         a6_.h();
         GL11.glLineWidth(6.0F);
         GL11.glBegin(3);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
         double var3 = 0.0D;
         if(var3 < 6.283185307179586D) {
            double var5 = this.w.thePlayer.lastTickPosX + (this.w.thePlayer.posX - this.w.thePlayer.lastTickPosX) * (double)var1.a() + Math.sin(var3) * ((Double)this.U.a()).doubleValue() - this.w.getRenderManager().h;
            double var7 = this.w.thePlayer.lastTickPosY + (this.w.thePlayer.posY - this.w.thePlayer.lastTickPosY) * (double)var1.a() - this.w.getRenderManager().g;
            double var9 = this.w.thePlayer.lastTickPosZ + (this.w.thePlayer.posZ - this.w.thePlayer.lastTickPosZ) * (double)var1.a() + Math.cos(var3) * ((Double)this.U.a()).doubleValue() - this.w.getRenderManager().m;
            GL11.glVertex3d(var5, var7, var9);
            var3 = var3 + 0.12319971190548208D;
         }

         GL11.glEnd();
         GL11.glLineWidth(3.0F);
         GL11.glBegin(3);
         GL11.glColor4f((float)this.Q.a().getRed() / 255.0F, (float)this.Q.a().getGreen() / 255.0F, (float)this.Q.a().getBlue() / 255.0F, 1.0F);
         var3 = 0.0D;
         if(var3 < 6.283185307179586D) {
            double var14 = this.w.thePlayer.lastTickPosX + (this.w.thePlayer.posX - this.w.thePlayer.lastTickPosX) * (double)var1.a() + Math.sin(var3) * ((Double)this.U.a()).doubleValue() - this.w.getRenderManager().h;
            double var15 = this.w.thePlayer.lastTickPosY + (this.w.thePlayer.posY - this.w.thePlayer.lastTickPosY) * (double)var1.a() - this.w.getRenderManager().g;
            double var16 = this.w.thePlayer.lastTickPosZ + (this.w.thePlayer.posZ - this.w.thePlayer.lastTickPosZ) * (double)var1.a() + Math.cos(var3) * ((Double)this.U.a()).doubleValue() - this.w.getRenderManager().m;
            GL11.glVertex3d(var14, var15, var16);
            var3 = var3 + 0.12319971190548208D;
         }

         GL11.glEnd();
         a6_.i();
      }

   }

   @agu
   public void c(ap9 var1) {
      int[] var2 = Q();
      if(var1.a().equals(PacketDirection.INCOMING) && lS.a(WL.DUELS) && var1.d() instanceof S45PacketTitle) {
         S45PacketTitle var3 = (S45PacketTitle)var1.d();
         if(var3.getType().equals(S45PacketTitle$Type.TITLE)) {
            String var4 = var3.getMessage().getUnformattedText();
            if(var4.equals("VICTORY!")) {
               this.j.t().a("Check chat for stats", 1000, azi.INFO);
               auS.a(true, "[" + EnumChatFormatting.RED + "Stats" + EnumChatFormatting.GRAY + "] " + EnumChatFormatting.RESET + "Your hp: " + EnumChatFormatting.GREEN + this.w.thePlayer.getHealth());
            }

            if(var4.equals("GAME OVER!")) {
               this.j.t().a("Check chat for stats", 1000, azi.INFO);
               auS.a(true, "[" + EnumChatFormatting.RED + "Stats" + EnumChatFormatting.GRAY + "] " + EnumChatFormatting.RESET + "Opponent hp: " + EnumChatFormatting.GREEN + ((EntityLivingBase)this.ar).getHealth());
            }
         }
      }

   }

   private void G() {
      this.I = false;
      this.ad.forEach(this::a);
      this.ad.clear();
   }

   public MovingObjectPosition m() {
      float var1 = this.ar.getCollisionBorderSize();
      Vec3 var2 = awR.d(1.0F);
      Vec3 var3 = awR.a(this.H(), this.s());
      Vec3 var4 = var2.addVector(var3.xCoord * this.x(), var3.yCoord * this.x(), var3.zCoord * this.x());
      return this.ar.getEntityBoundingBox().expand((double)var1, (double)var1, (double)var1).calculateIntercept(var2, var4);
   }

   public Entity n() {
      return this.ar;
   }

   public void b(Entity var1) {
      this.ar = var1;
   }

   public aEs K() {
      return this.an;
   }

   public String U() {
      return (String)this.an.a();
   }

   public aEl e() {
      return this.W;
   }

   public long p() {
      return lS.a()?2L:ThreadLocalRandom.current().nextLong(((Double)this.V.a()).longValue(), ((Double)this.L.a()).longValue() + 1L);
   }

   public aEE v() {
      return this.U;
   }

   public aEE A() {
      return this.H;
   }

   public aEE q() {
      return this.E;
   }

   public double x() {
      return ((Double)this.U.a()).doubleValue();
   }

   public double j() {
      return ((Double)this.E.a()).doubleValue();
   }

   public double N() {
      return ((Double)this.H.a()).doubleValue();
   }

   public aE8 L() {
      return this.af;
   }

   public aE3 M() {
      return this.J;
   }

   public aE3 h() {
      return this.aa;
   }

   public aE3 J() {
      return this.T;
   }

   public aEu t() {
      return this.O;
   }

   public aEu C() {
      return this.ae;
   }

   public aE3 c() {
      return this.ag;
   }

   public aE8 o() {
      return this.N;
   }

   public aE8 l() {
      return this.K;
   }

   public List b() {
      return this.D;
   }

   public aEs D() {
      return this.Z;
   }

   public boolean a() {
      return ((Boolean)this.x.a()).booleanValue();
   }

   public aEs B() {
      return this.ao;
   }

   public aEE R() {
      return this.V;
   }

   public aEE V() {
      return this.L;
   }

   public boolean r() {
      return ((Boolean)this.C.a()).booleanValue();
   }

   public aEs w() {
      return this.P;
   }

   public aEs i() {
      return this.aq;
   }

   public aEs g() {
      return this.ab;
   }

   private boolean lambda$getTargetsFromRange$11(Entity var1) {
      int[] var2 = Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) <= this.x();
   }

   private static Integer lambda$getEntityList$10(Entity var0) {
      return Integer.valueOf(var0.hurtResistantTime);
   }

   private static Float lambda$getEntityList$9(Entity var0) {
      return Float.valueOf(((EntityLivingBase)var0).getHealth());
   }

   private Float lambda$getEntityList$8(Entity var1) {
      return Float.valueOf(var1 instanceof EntityPlayer?this.a(((EntityPlayer)var1).bJ.armorInventory):99999.0F);
   }

   private Float lambda$getEntityList$7(Entity var1) {
      return Float.valueOf(var1.getDistanceToEntity(this.w.thePlayer));
   }

   private boolean lambda$isContainsTarget$6(Entity var1) {
      int[] var2 = Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) < this.x();
   }

   private Boolean lambda$new$5() {
      int[] var1 = Q();
      return Boolean.valueOf(!this.T.e() && this.G.a("Hit"));
   }

   private Boolean lambda$new$4() {
      int[] var1 = Q();
      return Boolean.valueOf(!this.T.e());
   }

   private static Boolean lambda$new$3() {
      int[] var0 = Q();
      return Boolean.valueOf(!lS.a());
   }

   private static Boolean lambda$new$2() {
      int[] var0 = Q();
      return Boolean.valueOf(!lS.a());
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.U().equalsIgnoreCase("Switch"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.U().equalsIgnoreCase("Single"));
   }

   public static void a(int[] var0) {
      X = var0;
   }

   public static int[] Q() {
      return X;
   }

   static {
      a(new int[3]);
   }
}
