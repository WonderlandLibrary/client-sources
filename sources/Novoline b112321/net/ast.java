package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
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
import net.UW;
import net.VN;
import net.WB;
import net.WL;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEE;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aSt;
import net.aY1;
import net.adZ;
import net.ae9;
import net.agu;
import net.alT;
import net.ap9;
import net.apV;
import net.as0;
import net.as2;
import net.as_;
import net.asx;
import net.au7;
import net.av2;
import net.avS;
import net.avq;
import net.avu;
import net.awR;
import net.aww;
import net.axu;
import net.aye;
import net.d3;
import net.lS;
import net.sT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
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
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;

public final class ast extends as0 {
   private Entity ac;
   private int H;
   private List Z = new CopyOnWriteArrayList();
   private final d3 P = new d3();
   private final d3 B = new d3();
   private final d3 Q;
   private final Dimension J;
   private final List Y;
   private float x;
   private float T;
   private float G;
   private float F;
   private boolean ad;
   private boolean I;
   private C08PacketPlayerBlockPlacement aa;
   @VN("packet-limit")
   private final aE8 O;
   @VN("min-aps")
   private final aEE L;
   @VN("max-aps")
   private final aEE ag;
   @VN("range")
   private final aEE W;
   @VN("wall-range")
   private final aEE A;
   @VN("block-range")
   private final aEE ae;
   @VN("rotations-smoothness")
   private final aE8 K;
   @VN("targets")
   private final aE3 S;
   @VN("aura-sort")
   private final aEs aj;
   @VN("filters")
   private final aE3 X;
   @VN("particles")
   private final aE3 M;
   @VN("auto-block")
   private final aEu D;
   @VN("target-hud")
   private final aEu ai;
   @VN("th-mode")
   private final aEs U;
   @VN("autodisable")
   private final aE3 ak;
   @VN("lag-check")
   private final aEu ah;
   @VN("th-x")
   private final aE8 V;
   @VN("th-y")
   private final aE8 ab;
   @VN("particles-mode")
   private final aEs af;
   @VN("particles-amount")
   private final aE8 E;
   @VN("auto-weapon")
   private final aEu R;
   @VN("keep-sprint")
   private final aEu z;
   @VN("auto-block-mode")
   private final aEs y;
   @VN("attack-event")
   private final aEs C;
   @VN("autoblock-event")
   private final aEs N;

   public ast(UW var1) {
      super(var1, "InfiniteAura", "Infinite Aura", a2V.COMBAT, "Automatically attacks entities around you");
      asx.Q();
      this.Q = new d3();
      this.J = Toolkit.getDefaultToolkit().getScreenSize();
      this.Y = new ArrayList();
      this.O = (aE8)((aE8)axu.b(Integer.valueOf(10)).d(Integer.valueOf(1))).c(Integer.valueOf(50));
      this.L = (aEE)((aEE)axu.a(Double.valueOf(5.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(20.0D));
      this.ag = (aEE)((aEE)axu.a(Double.valueOf(5.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(20.0D));
      this.W = (aEE)((aEE)axu.a(Double.valueOf(25.0D)).d(Double.valueOf(7.0D))).c(Double.valueOf(70.0D));
      this.A = (aEE)((aEE)axu.a(Double.valueOf(25.0D)).d(Double.valueOf(7.0D))).c(Double.valueOf(70.0D));
      this.ae = (aEE)((aEE)axu.a(Double.valueOf(25.0D)).d(Double.valueOf(7.0D))).c(Double.valueOf(70.0D));
      this.K = (aE8)((aE8)axu.b(Integer.valueOf(60)).d(Integer.valueOf(1))).c(Integer.valueOf(100));
      this.S = axu.a((Object)"Players").a((Object[])(new String[]{"Players", "Animals", "Mobs", "Invisibles"}));
      this.aj = axu.a("Distance").a(new String[]{"Distance", "Health", "Armor", "FOV", "HurtTime"});
      this.X = axu.a((Object)"Teams").a((Object[])(new String[]{"Teams", "Armor"}));
      this.M = axu.a((Object)"Enchant").a((Object[])(new String[]{"Enchant", "Critical"}));
      this.D = axu.g();
      this.ai = axu.g();
      this.U = axu.a("Pretty").a(new String[]{"Trash", "Less Pretty", "Pretty", "Prettier", "Prettiest"});
      this.ak = axu.a((Object)"Death").a((Object[])(new String[]{"World Change", "Game End", "Death"}));
      this.ah = axu.g();
      this.V = (aE8)((aE8)axu.b(Integer.valueOf((int)this.J.getWidth() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.J.getWidth() / 2));
      this.ab = (aE8)((aE8)axu.b(Integer.valueOf((int)this.J.getHeight() / 4)).d(Integer.valueOf(1))).c(Integer.valueOf((int)this.J.getHeight() / 2));
      this.af = axu.a("Hit").a(new String[]{"Hit", "Always"});
      this.E = (aE8)((aE8)axu.b(Integer.valueOf(2)).d(Integer.valueOf(1))).c(Integer.valueOf(5));
      this.R = axu.g();
      this.z = axu.g();
      this.y = axu.a("Packet").a(new String[]{"Packet", "Vanilla"});
      this.C = axu.a("POST").a(new String[]{"PRE", "POST"});
      this.N = axu.a("POST").a(new String[]{"PRE", "POST"});
      ae9.a(new adZ("AURA_PACKETLIMIT", "One way packet limit", a6d.SLIDER, this, this.O, 1.0D));
      ae9.a(new adZ("AURA_SORT", "Sort by", a6d.COMBOBOX, this, this.aj));
      ae9.a(new adZ("MIN_APS", "Min APS", a6d.SLIDER, this, this.L, 1.0D, ast::lambda$new$0));
      ae9.a(new adZ("MAX_APS", "Max APS", a6d.SLIDER, this, this.ag, 1.0D, ast::lambda$new$1));
      ae9.a(new adZ("KA_ATTACK_EVENT", "Attack Event", a6d.COMBOBOX, this, this.C));
      ae9.a(new adZ("RANGE", "Range", a6d.SLIDER, this, this.W, 5.0D));
      ae9.a(new adZ("BLOCK_RANGE", "Block Range", a6d.SLIDER, this, this.ae, 5.0D));
      ae9.a(new adZ("WALL_RANGE", "Wall Range", a6d.SLIDER, this, this.A, 5.0D));
      ae9.a(new adZ("AURA_FOV", "Angle Smoothing", a6d.SLIDER, this, this.K, 5.0D));
      ae9.a(new adZ("TARGETS", "Targets", a6d.SELECTBOX, this, this.S));
      ae9.a(new adZ("KA_FILTER", "Filters", a6d.SELECTBOX, this, this.X));
      ae9.a(new adZ("KA_PARTICLES", "Particles", a6d.SELECTBOX, this, this.M));
      ae9.a(new adZ("KA_PARTICLES_MODE", "Particles Mode", a6d.COMBOBOX, this, this.af, this::lambda$new$2));
      ae9.a(new adZ("KA_PARTICLES_AMOUNT", "Particles Amount", a6d.SLIDER, this, this.E, 1.0D, this::lambda$new$3));
      ae9.a(new adZ("KA_KEEP_SPRINT", "Keep Sprint", a6d.CHECKBOX, this, this.z));
      ae9.a(new adZ("KA_LAG_CHECK", "Lag Check", a6d.CHECKBOX, this, this.ah));
      ae9.a(new adZ("KA_AUTOBLOCK", "Auto Block", a6d.CHECKBOX, this, this.D));
      ae9.a(new adZ("KA_AB_MODE", "Auto Block Mode", a6d.COMBOBOX, this, this.y));
      ae9.a(new adZ("KA_AB_EVENT", "Auto Block Event", a6d.COMBOBOX, this, this.N));
      ae9.a(new adZ("KA_AUTO_WEAPON", "Auto Weapon", a6d.CHECKBOX, this, this.R));
      ae9.a(new adZ("TAR_HUD", "Target HUD", a6d.CHECKBOX, this, this.ai));
      a6d var10004 = a6d.COMBOBOX;
      aEs var10006 = this.U;
      aEu var10007 = this.ai;
      this.ai.getClass();
      ae9.a(new adZ("TAR_HUD_MODE", "Style", var10004, this, var10006, var10007::a));
      ae9.a(new adZ("KADISABLE", "Disable On", a6d.SELECTBOX, this, this.ak));
      if(PacketRemapper.b() == null) {
         asx.a(new int[3]);
      }

   }

   public void c() {
      this.x = this.w.thePlayer.rotationYaw;
      this.T = this.w.thePlayer.rotationPitch;
      this.ac = null;
      this.aa = null;
      this.Z.clear();
   }

   public void n() {
      this.c((String)"Single");
      this.x = this.w.thePlayer.rotationYaw;
      this.T = this.w.thePlayer.rotationPitch;
      this.Q.b();
   }

   private boolean a(EntityPlayer var1) {
      int[] var2 = asx.Q();
      return var1.bJ.armorInventory[0] != null || var1.bJ.armorInventory[1] != null || var1.bJ.armorInventory[2] != null || var1.bJ.armorInventory[3] != null;
   }

   private boolean p() {
      int[] var1 = asx.Q();
      return this.w.thePlayer.getHeldItem() != null && this.w.thePlayer.getHeldItem().getItem() instanceof ItemSword;
   }

   public boolean F() {
      int[] var1 = asx.Q();
      return ((Boolean)this.ah.a()).booleanValue() && this.Q.f() >= 100.0D;
   }

   public boolean o() {
      int[] var1 = asx.Q();
      return this.y() && this.ac != null && this.ac.isEntityAlive() && (double)this.w.thePlayer.getDistanceToEntity(this.ac) <= (this.w.thePlayer.canEntityBeSeen(this.ac)?this.b():this.M()) && !this.a((Class)avu.class);
   }

   public boolean O() {
      int[] var1 = asx.Q();
      return this.o() && !this.F() && !this.a((Class)avu.class) && !this.w.playerController.f();
   }

   public boolean Q() {
      int[] var1 = asx.Q();
      return ((Boolean)this.D.a()).booleanValue() && this.ac != null && this.p() && this.ac.isEntityAlive() && this.y() && !this.w.playerController.f() && (double)this.w.thePlayer.getDistanceToEntity(this.ac) <= (this.w.thePlayer.canEntityBeSeen(this.ac)?this.u():this.M()) && !this.a((Class)avu.class);
   }

   private boolean f(Entity var1) {
      asx.Q();
      IChatComponent var3 = var1.getDisplayName();
      String var4 = var3.getFormattedText();
      String var5 = var3.getUnformattedText();
      boolean var6 = !var4.substring(0, var4.length() - 2).contains("§");
      boolean var7 = var4.substring(var4.length() - 2).contains("§");
      return lS.c() && lS.a(WL.BW) && var6 && var7;
   }

   private double e(Entity var1) {
      float[] var3 = awR.b((EntityLivingBase)var1);
      asx.Q();
      float var4 = (float)((int)var3[0]);
      float var5 = this.w.thePlayer.rotationYaw > var4?this.w.thePlayer.rotationYaw - var4:var4 - this.w.thePlayer.rotationYaw;
      return (double)var5;
   }

   @agu
   public void b(aG1 var1) {
      int[] var2 = asx.Q();
      if(var1.h().equals(EventState.PRE)) {
         if(this.w.thePlayer.bJ.getStackInSlot(0) != null && this.w.thePlayer.bJ.getStackInSlot(0).getItem() == Items.compass && this.w.thePlayer.bJ.getStackInSlot(0).getDisplayName().contains("Teleporter")) {
            this.ac = null;
         }

         if(this.e() > 0) {
            this.Z = this.z();
         }

         this.Z = this.a((String)this.aj.a());
         if(this.ac != null && !this.ac.isEntityAlive()) {
            this.ac = null;
         }

         if(this.Z != null) {
            label166: {
               if(!this.a(false)) {
                  if(this.ac != null) {
                     if((double)this.w.thePlayer.getDistanceToEntity(this.ac) > this.b() && this.e() > 0) {
                        this.ac = this.q();
                     }

                     if((double)this.w.thePlayer.getDistanceToEntity(this.ac) > this.u()) {
                        this.ac = null;
                        this.y();
                     }

                     if(this.ac.isEntityAlive() || ((EntityLivingBase)this.ac).getHealth() > 0.0F) {
                        break label166;
                     }

                     this.ac = null;
                     this.y();
                  }

                  this.ac = (Entity)this.Z.get(0);
               }

               Iterator var3 = this.Z.iterator();
               if(var3.hasNext()) {
                  Entity var4 = (Entity)var3.next();
                  if(this.j.k().b(var4.getName(), au7.TARGET)) {
                     this.ac = var4;
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
      this.H = (this.H + 1) % this.Z.size();
      this.B.b();
   }

   private void a(Entity var1) {
      int[] var2 = asx.Q();
      if(this.Q()) {
         if(lS.c()) {
            this.a((Packet)(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
         }

         if(this.y.a("Packet")) {
            this.d();
         }

         this.I = false;
      }

      ArrayList var3 = this.a(new alT(this.w.thePlayer.posX, this.w.thePlayer.posY, this.w.thePlayer.posZ), new alT(var1.posX, var1.posY, var1.posZ));
      if(var3.size() <= ((Integer)this.O.a()).intValue()) {
         Iterator var4 = var3.iterator();
         if(var4.hasNext()) {
            alT var5 = (alT)var4.next();
            if(var3.indexOf(var5) % 2 == 0) {
               this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(var5.f(), var5.c(), var5.g(), true)));
            }

            this.a((Packet)(new C03PacketPlayer$C06PacketPlayerPosLook(var5.f(), var5.c(), var5.g(), this.w.thePlayer.rotationYaw, this.w.thePlayer.rotationPitch, true)));
         }

         this.w.thePlayer.swingItem();
         this.b((Packet)(new C02PacketUseEntity(var1, C02PacketUseEntity$Action.ATTACK)));
         Collections.reverse(var3);
         var4 = var3.iterator();
         if(var4.hasNext()) {
            alT var7 = (alT)var4.next();
            if(var3.indexOf(var7) % 2 == 0) {
               this.a((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(var7.f(), var7.c(), var7.g(), true)));
            }

            this.a((Packet)(new C03PacketPlayer$C06PacketPlayerPosLook(var7.f(), var7.c(), var7.g(), this.w.thePlayer.rotationYaw, this.w.thePlayer.rotationPitch, true)));
         }
      }

      if(this.af.a("Always")) {
         if(this.M.a((Object)"Enchant")) {
            this.w.thePlayer.onEnchantmentCritical(var1);
         }

         if(this.M.a((Object)"Critical")) {
            this.w.thePlayer.onCriticalHit(var1);
         }
      }

   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = asx.Q();
      if(var1.h().equals(EventState.PRE)) {
         if(!this.Q() || this.w.thePlayer.p() && this.y.a("Packet")) {
            this.d();
         }

         if(this.o()) {
            if(((Boolean)this.R.a()).booleanValue() && !this.w.playerController.f()) {
               this.S();
            }

            if(this.af.a("Hit") && !this.M.e() && this.ac.hurtResistantTime == 20) {
               int var3 = 0;
               if(var3 < ((Integer)this.E.a()).intValue()) {
                  this.w.thePlayer.onEnchantmentCritical(this.ac);
                  this.w.thePlayer.onCriticalHit(this.ac);
                  ++var3;
               }
            }
         }
      }

      if(var1.h().equals(EventState.valueOf((String)this.C.a())) && this.o() && !this.F() && this.P.a((double)(1000L / this.A())) && (!((avS)this.b((Class)avS.class)).c() || this.w.thePlayer.ticksExisted % 2 != 0)) {
         this.a(this.ac);
         this.P.b();
      }

      if(var1.h().equals(EventState.valueOf((String)this.N.a())) && this.Q()) {
         this.x();
         this.w.thePlayer.getHeldItem().a(this.w.theWorld, this.w.thePlayer);
         this.g();
      }

   }

   public void S() {
      float var2 = 1.0F;
      asx.Q();
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
      asx.Q();
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

   private void g() {
      int[] var1 = asx.Q();
      if(!this.I) {
         this.a((Packet)(new C08PacketPlayerBlockPlacement(this.w.thePlayer.getHeldItem())));
         this.I = true;
      }

   }

   private void d() {
      int[] var1 = asx.Q();
      if(this.I) {
         this.a((Packet)(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
         this.I = false;
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)"Single");
   }

   @agu
   public void a(apV var1) {
      if(this.Q()) {
         var1.setCancelled(true);
      }

   }

   @agu
   public void b(ap9 var1) {
      int[] var2 = asx.Q();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(this.Q() || this.I) {
            if(var1.d() instanceof C07PacketPlayerDigging) {
               C07PacketPlayerDigging var3 = (C07PacketPlayerDigging)var1.d();
               if(var3.getStatus().equals(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM)) {
                  var1.setCancelled(true);
               }
            }

            if(var1.d() instanceof C08PacketPlayerBlockPlacement) {
               C08PacketPlayerBlockPlacement var4 = (C08PacketPlayerBlockPlacement)var1.d();
               if(var4.getPlacedBlockDirection() == 255 && var4.getStack() != null && var4.getStack().getItem() instanceof ItemSword) {
                  var1.setCancelled(true);
               }
            }
         }

         if(!this.O() || !lS.c() || !(var1.d() instanceof C08PacketPlayerBlockPlacement)) {
            return;
         }

         C08PacketPlayerBlockPlacement var5 = (C08PacketPlayerBlockPlacement)var1.d();
         if(var5.getPlacedBlockDirection() != 255 || var5.getStack() == null || !(var5.getStack().getItem() instanceof ItemSword)) {
            this.aa = var5;
            var1.setCancelled(true);
         }
      }

      this.Q.b();
   }

   @agu
   public void c(aG1 var1) {
      int[] var2 = asx.Q();
      if(var1.h().equals(EventState.PRE)) {
         if(this.O()) {
            float[] var3 = awR.b((EntityLivingBase)this.ac);
            float[] var4 = awR.a(var3, ((Integer)this.K.a()).intValue());
            this.x = var4[0];
            this.T = var4[1];
            if(this.aa == null && !((as2)this.b((Class)as2.class)).d() && this.z().size() == 1) {
               var1.b(this.x);
               var1.a(this.T);
            }
         }

         this.x = this.w.thePlayer.rotationYaw;
         this.T = this.w.thePlayer.rotationPitch;
      }

      if(this.aa != null) {
         this.a((Packet)this.aa);
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
      int[] var2 = asx.Q();
      return var1 == Blocks.chest || var1 == Blocks.trapped_chest || var1 == Blocks.crafting_table || var1 == Blocks.furnace || var1 == Blocks.ender_chest || var1 == Blocks.enchanting_table;
   }

   private void x() {
      int[] var1 = asx.Q();
      if(this.w.gameSettings.keyBindUseItem.isKeyDown()) {
         if(this.w.objectMouseOver.entityHit != null) {
            this.b((Packet)(new C02PacketUseEntity(this.w.objectMouseOver.entityHit, C02PacketUseEntity$Action.INTERACT)));
         }

         if(this.a(this.w.theWorld.getBlockState(this.w.objectMouseOver.getBlockPos()).getBlock())) {
            this.w.playerController.onPlayerRightClick(this.w.thePlayer, this.w.theWorld, this.w.thePlayer.getHeldItem(), this.w.objectMouseOver.getBlockPos(), Block.a(this.w.objectMouseOver.getBlockPos()), this.w.objectMouseOver.hitVec);
         }
      }

   }

   public boolean b(Entity var1) {
      asx.Q();
      boolean var3 = this.S.a((Object)"Mobs");
      boolean var4 = this.S.a((Object)"Animals");
      boolean var5 = this.S.a((Object)"Players");
      boolean var6 = this.S.a((Object)"Invisibles");
      if(var1.isEntityAlive() && this.w.thePlayer.getHealth() > 0.0F && !this.j.k().b(var1.getName(), au7.FRIEND)) {
         if(!(var1 instanceof EntityMob) && !(var1 instanceof EntitySlime) && !(var1 instanceof EntityGolem)) {
            if(!(var1 instanceof EntityAnimal) && !(var1 instanceof EntityVillager)) {
               if(var1 instanceof EntityPlayer) {
                  return var1 != this.w.thePlayer && (!this.X.a((Object)"Armor") || this.a((EntityPlayer)var1)) && (!this.X.a((Object)"Teams") || !aww.a((ICommandSender)var1)) && (!var1.isInvisible() || var6) && !this.f(var1) && var1 != ((avq)this.b((Class)avq.class)).b() && var1 != ((as_)this.b((Class)as_.class)).c();
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
      asx.Q();
      ObjectListIterator var3 = ((ObjectArrayList)this.a((String)this.aj.a()).stream().filter(this::lambda$isContainsTarget$4).collect(Collectors.toCollection(ObjectArrayList::<init>))).iterator();
      if(var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if(this.j.k().b(var4.getName(), au7.TARGET)) {
            return true;
         }
      }

      return false;
   }

   private List a(String var1) {
      asx.Q();
      List var3 = (List)this.w.theWorld.getLoadedEntityList().stream().filter(this::b).collect(Collectors.toList());
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
         var3 = (List)var3.stream().sorted(Comparator.comparing(this::lambda$getEntityList$5)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 1:
         var3 = (List)var3.stream().sorted(Comparator.comparing(this::lambda$getEntityList$6)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 2:
         var3 = (List)var3.stream().sorted(Comparator.comparing(ast::lambda$getEntityList$7)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 3:
         var3 = (List)var3.stream().sorted(Comparator.comparing(this::e)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      case 4:
         var3 = (List)var3.stream().sorted(Comparator.comparing(ast::lambda$getEntityList$8)).collect(Collectors.toCollection(ObjectArrayList::<init>));
      default:
         return (List)var3.stream().filter(this::c).collect(Collectors.toList());
      }
   }

   public List z() {
      return (List)this.a((String)this.aj.a()).stream().filter(this::lambda$getTargetsFromRange$9).collect(Collectors.toList());
   }

   private int e() {
      asx.Q();
      int var2 = 0;
      if(this.a((String)this.aj.a()).isEmpty()) {
         return 0;
      } else {
         Iterator var3 = this.a((String)this.aj.a()).iterator();
         if(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if((double)this.w.thePlayer.getDistanceToEntity(var4) <= this.b()) {
               ++var2;
            }
         }

         return var2;
      }
   }

   private Entity q() {
      asx.Q();
      Iterator var2 = this.a((String)this.aj.a()).iterator();
      if(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         if((double)this.w.thePlayer.getDistanceToEntity(var3) <= this.b()) {
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
      asx.Q();
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
      int[] var2 = asx.Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) <= (this.w.thePlayer.canEntityBeSeen(var1)?Math.max(this.b(), this.u()):this.M());
   }

   @agu
   public void a(aSt var1) {
      asx.Q();
      sT.a.a("Ты пидор", 200.0D, 200.0D, -1, true);
      if(((Boolean)this.ai.a()).booleanValue() && this.o() && this.ac instanceof EntityPlayer && !(this.w.currentScreen instanceof GuiChat)) {
         a6_.a((ast)this, (EntityLivingBase)((EntityPlayer)this.ac));
      }

   }

   @agu
   public void a(aye var1) {
      int[] var2 = asx.Q();
      if(this.o()) {
         a6_.h();
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);
         ArrayList var3 = this.a(new alT(this.w.thePlayer.posX, this.w.thePlayer.posY, this.w.thePlayer.posZ), new alT(this.ac.posX, this.ac.posY, this.ac.posZ));
         Iterator var4 = var3.iterator();
         if(var4.hasNext()) {
            alT var5 = (alT)var4.next();
            double var6 = var5.f() - this.w.getRenderManager().h;
            double var8 = var5.c() - this.w.getRenderManager().g;
            double var10 = var5.g() - this.w.getRenderManager().m;
            GL11.glVertex3d(var6, var8, var10);
         }

         GL11.glEnd();
         a6_.i();
      }

   }

   private ArrayList a(alT var1, alT var2) {
      int[] var3 = asx.Q();
      if(!this.a(new BlockPos(var1.e()))) {
         var1 = var1.a(0.0D, 1.0D, 0.0D);
      }

      aY1 var4 = new aY1(var1, var2);
      var4.a();
      int var5 = 0;
      Object var6 = null;
      alT var7 = null;
      ArrayList var8 = new ArrayList();
      ArrayList var9 = var4.b();
      Iterator var10 = var9.iterator();
      if(var10.hasNext()) {
         alT var11 = (alT)var10.next();
         if(var5 == 0 || var5 == var9.size() - 1) {
            if(var6 != null) {
               var8.add(((alT)var6).a(0.5D, 0.0D, 0.5D));
            }

            var8.add(var11.a(0.5D, 0.0D, 0.5D));
            var7 = var11;
         }

         boolean var12 = true;
         if(var11.a(var7) > 81.0D) {
            var12 = false;
         }

         double var13 = Math.min(var7.f(), var11.f());
         double var15 = Math.min(var7.c(), var11.c());
         double var17 = Math.min(var7.g(), var11.g());
         double var19 = Math.max(var7.f(), var11.f());
         double var21 = Math.max(var7.c(), var11.c());
         double var23 = Math.max(var7.g(), var11.g());
         int var25 = (int)var13;
         if((double)var25 <= var19) {
            int var26 = (int)var15;
            if((double)var26 <= var21) {
               int var27 = (int)var17;
               if((double)var27 <= var23) {
                  if(!aY1.a(var25, var26, var27, false)) {
                     var12 = false;
                  }

                  ++var27;
               }

               ++var26;
            }

            ++var25;
         }

         if(!var12) {
            var8.add(((alT)var6).a(0.5D, 0.0D, 0.5D));
         }

         ++var5;
      }

      return var8;
   }

   private boolean a(BlockPos var1) {
      asx.Q();
      Block var3 = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(var1.getX(), var1.getY(), var1.getZ())).getBlock();
      return var3.getMaterial() == Material.air || var3.getMaterial() == Material.plants || var3.getMaterial() == Material.vine || var3 == Blocks.ladder || var3 == Blocks.water || var3 == Blocks.flowing_water || var3 == Blocks.wall_sign || var3 == Blocks.standing_sign;
   }

   public Entity D() {
      return this.ac;
   }

   public void d(Entity var1) {
      this.ac = var1;
   }

   public long A() {
      return lS.a()?2L:ThreadLocalRandom.current().nextLong(((Double)this.L.a()).longValue(), ((Double)this.ag.a()).longValue() + 1L);
   }

   public aEE R() {
      return this.W;
   }

   public aEE P() {
      return this.A;
   }

   public aEE k() {
      return this.ae;
   }

   public double b() {
      return ((Double)this.W.a()).doubleValue();
   }

   public double u() {
      return ((Double)this.ae.a()).doubleValue();
   }

   public double M() {
      return ((Double)this.A.a()).doubleValue();
   }

   public aE3 j() {
      return this.S;
   }

   public aE3 J() {
      return this.X;
   }

   public aE3 N() {
      return this.M;
   }

   public aEu f() {
      return this.D;
   }

   public aEu B() {
      return this.ai;
   }

   public aE3 n() {
      return this.ak;
   }

   public aE8 C() {
      return this.V;
   }

   public aE8 G() {
      return this.ab;
   }

   public List I() {
      return this.Z;
   }

   public aEs t() {
      return this.aj;
   }

   public boolean v() {
      return ((Boolean)this.ah.a()).booleanValue();
   }

   public aEs a() {
      return this.U;
   }

   public aEE H() {
      return this.L;
   }

   public aEE r() {
      return this.ag;
   }

   public boolean m() {
      return ((Boolean)this.z.a()).booleanValue();
   }

   public aEs w() {
      return this.C;
   }

   public aEs E() {
      return this.y;
   }

   public aEs K() {
      return this.N;
   }

   private boolean lambda$getTargetsFromRange$9(Entity var1) {
      int[] var2 = asx.Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) <= this.b();
   }

   private static Integer lambda$getEntityList$8(Entity var0) {
      return Integer.valueOf(var0.hurtResistantTime);
   }

   private static Float lambda$getEntityList$7(Entity var0) {
      return Float.valueOf(((EntityLivingBase)var0).getHealth());
   }

   private Float lambda$getEntityList$6(Entity var1) {
      return Float.valueOf(var1 instanceof EntityPlayer?this.a(((EntityPlayer)var1).bJ.armorInventory):99999.0F);
   }

   private Float lambda$getEntityList$5(Entity var1) {
      return Float.valueOf(var1.getDistanceToEntity(this.w.thePlayer));
   }

   private boolean lambda$isContainsTarget$4(Entity var1) {
      int[] var2 = asx.Q();
      return (double)this.w.thePlayer.getDistanceToEntity(var1) < this.b();
   }

   private Boolean lambda$new$3() {
      int[] var1 = asx.Q();
      return Boolean.valueOf(!this.M.e() && this.af.a("Hit"));
   }

   private Boolean lambda$new$2() {
      int[] var1 = asx.Q();
      return Boolean.valueOf(!this.M.e());
   }

   private static Boolean lambda$new$1() {
      int[] var0 = asx.Q();
      return Boolean.valueOf(!lS.a());
   }

   private static Boolean lambda$new$0() {
      int[] var0 = asx.Q();
      return Boolean.valueOf(!lS.a());
   }
}
