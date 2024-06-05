package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import net.BT;
import net.C6;
import net.Ju;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEl;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aL_;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.apM;
import net.apn;
import net.apo;
import net.aqc;
import net.as0;
import net.av8;
import net.avB;
import net.avS;
import net.ava;
import net.aw5;
import net.axu;
import net.aye;
import net.azi;
import net.bgM;
import net.d3;
import net.gZ;
import net.lS;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class avu extends as0 {
   private List N = new CopyOnWriteArrayList();
   private d3 B = new d3();
   private d3 C = new d3();
   private List L;
   private int V;
   private int aa;
   private int ab;
   private aqc E;
   private BlockPos I;
   private float Q;
   private float S;
   private float y;
   private double R;
   private double A;
   @VN("addons")
   private aE3 K = axu.a("Swapper", "Silent", "Down Ward").a((Object[])(new String[]{"Swing Item", "Safe Walk", "Swapper", "Silent", "Tower", "Down Ward", "Tower Move"}));
   @VN("render-rotations")
   private aEu J = axu.a(Boolean.valueOf(true));
   @VN("slot")
   private aE8 ad = (aE8)((aE8)axu.b(Integer.valueOf(6)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
   @VN("path")
   private aEu F = axu.a(Boolean.valueOf(true));
   @VN("no-sprint")
   private aEu T = axu.a(Boolean.valueOf(false));
   @VN("sprint-boost")
   private aEs ae;
   @VN("timer-support")
   private final aE3 Y;
   @VN("swap-delay")
   private aE8 ac;
   @VN("place-delay")
   private aE8 H;
   @VN("place-event")
   private aEs x;
   @VN("timer-moving")
   private final aEl X;
   @VN("timer-tower")
   private final aEl G;
   @VN("timer-towermove")
   private final aEl P;
   @VN("timer-downward")
   private final aEl Z;
   @VN("ray-trace")
   private final aEu W;
   private double D;
   private double U;
   private double O;
   private boolean z;
   private static String M;

   public avu(UW var1) {
      super(var1, a2V.MOVEMENT, "Scaffold", "Scaffold");
      r();
      this.ae = axu.a("Boost").a(new String[]{"Vanilla", "Boost", "Jump"});
      this.Y = axu.a("Moving", "Tower").a((Object[])(new String[]{"Moving", "Tower", "Tower Move", "Down Ward"}));
      this.ac = (aE8)((aE8)axu.b(Integer.valueOf(250)).d(Integer.valueOf(200))).c(Integer.valueOf(500));
      this.H = (aE8)((aE8)axu.b(Integer.valueOf(0)).d(Integer.valueOf(0))).c(Integer.valueOf(200));
      this.x = axu.a("POST").a(new String[]{"PRE", "POST"});
      this.X = (aEl)((aEl)axu.a(Float.valueOf(1.0F)).d(Float.valueOf(1.0F))).c(Float.valueOf(2.0F));
      this.G = (aEl)((aEl)axu.a(Float.valueOf(1.0F)).d(Float.valueOf(1.0F))).c(Float.valueOf(3.0F));
      this.P = (aEl)((aEl)axu.a(Float.valueOf(1.0F)).d(Float.valueOf(1.0F))).c(Float.valueOf(2.0F));
      this.Z = (aEl)((aEl)axu.a(Float.valueOf(1.0F)).d(Float.valueOf(1.0F))).c(Float.valueOf(2.0F));
      this.W = axu.g();
      ae9.a(new adZ("SF_PLACE_EVENT", "Place Event", a6d.COMBOBOX, this, this.x));
      ae9.a(new adZ("SF_RAY_TRACE", "Ray Trace", a6d.CHECKBOX, this, this.W));
      ae9.a(new adZ("SF_ADDONS", "Addons", a6d.SELECTBOX, this, this.K));
      ae9.a(new adZ("SF_SWAP_DELAY", "Swap Delay", a6d.SLIDER, this, this.ac, 50.0D, this::lambda$new$0));
      ae9.a(new adZ("SF_PLACE_DELAY", "Place Delay", a6d.SLIDER, this, this.H, 50.0D));
      ae9.a(new adZ("SF_SPRINT_MODE", "Sprint Mode", a6d.COMBOBOX, this, this.ae, this::lambda$new$1));
      ae9.a(new adZ("SF_NO_SPRINT", "No Sprint", a6d.CHECKBOX, this, this.T));
      ae9.a(new adZ("SF_RENDER_ROTS", "Render Rotations", a6d.CHECKBOX, this, this.J));
      ae9.a(new adZ("SF_PATH", "Render Path", a6d.CHECKBOX, this, this.F));
      ae9.a(new adZ("SF_SWAP_SLOT", "Swap Slot", a6d.SLIDER, this, this.ad, 1.0D));
      ae9.a(new adZ("SF_TIMER_SUPPORT", "Timer Support", a6d.SELECTBOX, this, this.Y));
      ae9.a(new adZ("SF_TIMER_BOOST_MOVING", "Moving Boost", a6d.SLIDER, this, this.X, 0.1D, this::lambda$new$2));
      ae9.a(new adZ("SF_TIMER_BOOST_TOWER", "Tower Boost", a6d.SLIDER, this, this.G, 0.1D, this::lambda$new$3));
      ae9.a(new adZ("SF_TIMER_BOOST_TOWERMOVE", "Tower Move Boost", a6d.SLIDER, this, this.P, 0.1D, this::lambda$new$4));
      ae9.a(new adZ("SF_TIMER_BOOST_DOWNWARD", "Down Ward Boost", a6d.SLIDER, this, this.Z, 0.1D, this::lambda$new$5));
      this.L = Arrays.asList(new Block[]{Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.wooden_slab, Blocks.wooden_slab, Blocks.chest, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.skull, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.tnt, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.trapped_chest, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.activator_rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.redstone_torch, Blocks.acacia_stairs, Blocks.birch_stairs, Blocks.brick_stairs, Blocks.dark_oak_stairs, Blocks.jungle_stairs, Blocks.nether_brick_stairs, Blocks.oak_stairs, Blocks.quartz_stairs, Blocks.red_sandstone_stairs, Blocks.sandstone_stairs, Blocks.spruce_stairs, Blocks.stone_brick_stairs, Blocks.stone_stairs, Blocks.wooden_slab, Blocks.double_wooden_slab, Blocks.stone_slab, Blocks.double_stone_slab, Blocks.stone_slab2, Blocks.double_stone_slab2, Blocks.web, Blocks.gravel, Blocks.daylight_detector_inverted, Blocks.daylight_detector, Blocks.soul_sand, Blocks.piston, Blocks.piston_extension, Blocks.piston_head, Blocks.sticky_piston, Blocks.iron_trapdoor, Blocks.ender_chest, Blocks.end_portal, Blocks.end_portal_frame, Blocks.standing_banner, Blocks.wall_banner, Blocks.deadbush, Blocks.slime_block, Blocks.acacia_fence_gate, Blocks.birch_fence_gate, Blocks.dark_oak_fence_gate, Blocks.jungle_fence_gate, Blocks.spruce_fence_gate, Blocks.oak_fence_gate, Blocks.double_plant});
   }

   private boolean J() {
      r();
      byte var2 = 36;
      if(var2 >= 45) {
         return false;
      } else {
         ItemStack var3 = this.w.thePlayer.bo.getSlot(var2).getStack();
         return var3 != null && var3.getItem() != null && var3.getItem() instanceof ItemBlock && this.a(var3.getItem());
      }
   }

   private boolean a(Item var1) {
      String var2 = r();
      if(var1 instanceof ItemBlock) {
         ItemBlock var3 = (ItemBlock)var1;
         Block var4 = var3.getBlock();
         return !this.l().contains(var4);
      } else {
         return false;
      }
   }

   private int d() {
      r();
      int var2 = -1;
      int var3 = 0;
      if(this.o() == 0) {
         return -1;
      } else {
         int var4 = 9;
         if(var4 < 36) {
            Slot var5 = this.w.thePlayer.bo.getSlot(var4);
            if(var5.getHasStack()) {
               Item var6 = var5.getStack().getItem();
               ItemStack var7 = var5.getStack();
               if(var6 instanceof ItemBlock && this.a(var6) && var7.stackSize > var3) {
                  var3 = var7.stackSize;
                  var2 = var4;
               }
            }

            ++var4;
         }

         return var2;
      }
   }

   private int D() {
      r();
      int var2 = -1;
      int var3 = 0;
      if(this.o() == 0) {
         return -1;
      } else {
         int var4 = 36;
         if(var4 < 45) {
            Slot var5 = this.w.thePlayer.bo.getSlot(var4);
            if(var5.getHasStack()) {
               Item var6 = var5.getStack().getItem();
               ItemStack var7 = var5.getStack();
               if(var6 instanceof ItemBlock && this.a(var6) && var7.stackSize > var3) {
                  var3 = var7.stackSize;
                  var2 = var4;
               }
            }

            ++var4;
         }

         return var2;
      }
   }

   public boolean a(Block var1) {
      String var2 = r();
      return !var1.getMaterial().isReplaceable()?false:!(var1 instanceof BlockSnow) || var1.getBlockBoundsMaxY() <= 0.125D;
   }

   public double[] a(double var1) {
      BlockPos var4 = new BlockPos(this.w.thePlayer.posX, var1, this.w.thePlayer.posZ);
      Block var5 = this.w.theWorld.getBlockState(var4).getBlock();
      r();
      C6 var6 = this.w.thePlayer.ap();
      float var7 = var6.c();
      float var8 = var6.e();
      float var9 = this.w.thePlayer.rotationYaw;
      double var10 = -999.0D;
      double var12 = -999.0D;
      double var14 = 0.0D;
      double var16 = 0.2D;
      if(!this.a(var5)) {
         var10 = this.w.thePlayer.posX;
         var12 = this.w.thePlayer.posZ;
         ++var14;
         if(var14 > var16) {
            var14 = var16;
         }

         var10 = var10 + ((double)var7 * 0.45D * (double)MathHelper.d(Math.toRadians((double)(var9 + 90.0F))) + (double)var8 * 0.45D * (double)MathHelper.h(Math.toRadians((double)(var9 + 90.0F)))) * var14;
         var12 = var12 + ((double)var7 * 0.45D * (double)MathHelper.h(Math.toRadians((double)(var9 + 90.0F))) - (double)var8 * 0.45D * (double)MathHelper.d(Math.toRadians((double)(var9 + 90.0F)))) * var14;
         if(var14 == var16) {
            ;
         }

         var4 = new BlockPos(var10, var1, var12);
         var5 = this.w.theWorld.getBlockState(var4).getBlock();
      }

      return new double[]{var10, var12};
   }

   private float[] a(BlockPos var1, EnumFacing var2) {
      r();
      double var4 = (double)var1.getX() + 0.5D - this.w.thePlayer.posX + (double)var2.getFrontOffsetX() / 2.0D;
      double var6 = (double)var1.getZ() + 0.5D - this.w.thePlayer.posZ + (double)var2.getFrontOffsetZ() / 2.0D;
      double var8 = (double)var1.getY() + 0.5D;
      double var10 = this.w.thePlayer.posY + (double)this.w.thePlayer.getEyeHeight() - var8;
      double var12 = (double)MathHelper.sqrt_double(var4 * var4 + var6 * var6);
      float var14 = (float)(Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      float var15 = (float)(Math.atan2(var10, var12) * 180.0D / 3.141592653589793D);
      if(var14 < 0.0F) {
         var14 += 360.0F;
      }

      return new float[]{var14, var15};
   }

   private float[] a(Vec3 var1) {
      double var3 = var1.c() - this.w.thePlayer.posX;
      r();
      double var5 = var1.a() - this.w.thePlayer.posZ;
      double var7 = var1.b();
      double var9 = this.w.thePlayer.posY + (double)this.w.thePlayer.getEyeHeight() - var7;
      double var11 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5);
      float var13 = (float)(Math.atan2(var5, var3) * 180.0D / 3.141592653589793D) - 90.0F;
      float var14 = (float)(Math.atan2(var9, var11) * 180.0D / 3.141592653589793D);
      if(var13 < 0.0F) {
         var13 += 360.0F;
      }

      return new float[]{var13, var14};
   }

   private int b(int var1) {
      return this.w.thePlayer.bJ.getStackInSlot(var1).stackSize;
   }

   @agu
   public void b(aG1 var1) {
      String var2 = r();
      if(var1.h().equals(EventState.PRE)) {
         if(this.F() <= 0) {
            this.w.thePlayer.a(this.d(), ((Integer)this.ad.a()).intValue() - 1);
         }

         if(this.K.a((Object)"Swapper") && this.B.a((double)((Integer)this.ac.a()).intValue())) {
            int var3 = this.w.thePlayer.bo.windowId;
            int var4 = this.d();
            int var5 = this.D();
            if(var4 != -1 && this.b(var4) > (var5 == -1?0:this.b(var5 - 36))) {
               this.w.playerController.a(var3, var4, this.t(), 2, this.w.thePlayer);
            }

            if(var5 != -1 && var5 - 36 != this.t()) {
               this.w.playerController.a(var3, var5, this.t(), 2, this.w.thePlayer);
            }

            this.B.b();
         }
      }

   }

   public boolean s() {
      String var1 = r();
      return this.K.a((Object)"Tower Move") && this.B() && this.w.thePlayer.p();
   }

   public boolean j() {
      String var1 = r();
      return this.K.a((Object)"Down Ward") && this.u();
   }

   public boolean a() {
      String var1 = r();
      return !this.j() && this.K.a((Object)"Safe Walk") || ((Integer)this.H.a()).intValue() > 0;
   }

   @agu
   public void a(apM var1) {
      if(this.K.a((Object)"Down Ward")) {
         var1.setCancelled(true);
      }

   }

   private void f(aG1 var1) {
      String var2 = r();
      if(this.w.thePlayer.d(0.76D) && !this.w.thePlayer.d(0.75D) && this.w.thePlayer.motionY > 0.23D && this.w.thePlayer.motionY < 0.25D) {
         this.w.thePlayer.motionY = (double)Math.round(this.w.thePlayer.posY) - this.w.thePlayer.posY;
      }

      if(this.w.thePlayer.d(1.0E-4D)) {
         this.w.thePlayer.motionY = 0.41999998688698D;
      }

      if(this.w.thePlayer.posY >= (double)Math.round(this.w.thePlayer.posY) - 1.0E-4D && this.w.thePlayer.posY <= (double)Math.round(this.w.thePlayer.posY) + 1.0E-4D && !this.u()) {
         this.w.thePlayer.motionY = 0.0D;
      }

      var1.c(this.w.thePlayer.c(var1.f()));
   }

   public boolean f() {
      String var1 = r();
      return this.E != null && (!this.m() || ((Boolean)this.T.a()).booleanValue() || this.ae.a("Vanilla") || this.w.thePlayer.ay() <= this.w.thePlayer.l(false));
   }

   private void d(aL_ var1) {
      String var2 = r();
      if(this.ab % 3 != 0) {
         var1.c(this.w.thePlayer.l(false) * ThreadLocalRandom.current().nextDouble(0.996D, 1.0D));
      }

      var1.c(this.w.thePlayer.a(true, 0.2D) * ThreadLocalRandom.current().nextDouble(1.186D, 1.2D));
   }

   @agu
   public void a(BT var1) {
      this.U = this.w.thePlayer.as() * 0.98D;
      r();
      this.O = this.w.thePlayer.ay();
      if(this.o() == 0) {
         this.j.t().a(this.f(), "No blocks in inventory", azi.WARNING);
         this.e();
      }

   }

   private void a(aL_ var1) {
      String var2 = r();
      if(this.w.thePlayer.p()) {
         if(this.w.thePlayer.onGround && !this.w.thePlayer.N()) {
            var1.a(this.w.thePlayer.motionY = 0.419999986886978D);
            this.D = this.U * 2.139999980926514D;
         }

         if(this.z) {
            this.D = this.O - 0.81999D * (this.O - this.U);
         }

         this.D -= this.O / 159.0D;
         if(this.ab % 3 != 0) {
            var1.c(this.w.thePlayer.l(false) * 0.998D);
         }

         var1.c(Math.max(this.D, this.U));
         this.z = this.w.thePlayer.onGround;
      }

      var1.c(0.0D);
   }

   @agu
   public void b(aL_ var1) {
      String var2 = r();
      if(this.w.thePlayer.p()) {
         if(this.w.thePlayer.ap().b()) {
            this.w.timer.i = this.Y.a((Object)"Moving")?((Float)this.X.a()).floatValue():1.0F;
            var1.c(this.w.thePlayer.b(0.09158123582468379D, 0.2D));
         }

         if(this.j()) {
            this.w.timer.i = this.Y.a((Object)"Down Ward")?((Float)this.Z.a()).floatValue():1.0F;
            var1.c(this.w.thePlayer.l(false) * ThreadLocalRandom.current().nextDouble(0.994D, 1.0D));
         }

         if(this.s()) {
            this.w.timer.i = this.Y.a((Object)"Tower Move")?((Float)this.P.a()).floatValue():1.0F;
            var1.c(this.w.thePlayer.l(false) * ThreadLocalRandom.current().nextDouble(0.994D, 1.0D));
         }

         if(((Boolean)this.T.a()).booleanValue()) {
            this.w.timer.i = this.Y.a((Object)"Moving")?((Float)this.X.a()).floatValue():1.0F;
            var1.c(this.w.thePlayer.l(false) * 0.998D);
         }

         if(this.ae.a("Jump")) {
            this.w.timer.i = this.Y.a((Object)"Moving")?((Float)this.X.a()).floatValue():1.0F;
            this.a(var1);
         }

         if(!this.w.thePlayer.onGround) {
            this.w.timer.i = this.Y.a((Object)"Moving")?((Float)this.X.a()).floatValue():1.0F;
            var1.c(this.w.thePlayer.l(false) * ThreadLocalRandom.current().nextDouble(0.994D, 1.0D));
         }

         if(this.ae.a("Boost")) {
            this.w.timer.i = this.Y.a((Object)"Moving")?((Float)this.X.a()).floatValue():1.0F;
            this.d(var1);
         }

         if(!this.ae.a("Vanilla")) {
            return;
         }

         this.w.timer.i = this.Y.a((Object)"Moving")?((Float)this.X.a()).floatValue():1.0F;
      }

      this.w.timer.i = 1.0F;
   }

   @agu
   public void e(aG1 var1) {
      String var2 = r();
      if(var1.h().equals(EventState.PRE)) {
         if(this.E != null) {
            if(((Boolean)this.W.a()).booleanValue()) {
               this.a(MathHelper.a(this.E.a(), this.E.b()));
            }

            float[] var3 = this.a(this.E.a(), this.E.b());
            this.Q = var3[0];
            this.S = var3[1];
            this.y = (this.b()?87.5F:84.0F) + ThreadLocalRandom.current().nextFloat();
         }

         if(this.aa != this.t()) {
            this.a((Packet)(new C09PacketHeldItemChange(this.aa = this.t())));
         }

         if(((Boolean)this.W.a()).booleanValue()) {
            var1.b(this.Q);
            var1.a(this.S);
         }

         var1.a(this.y);
         var1.b(this.w.thePlayer.o(-180.0F));
         this.w.thePlayer.setSprinting(!((Boolean)this.T.a()).booleanValue() && this.w.thePlayer.ap().e() > 0.0F);
      }

   }

   private void A() {
      String var1 = r();
      if(this.f() && this.C.a((double)((Integer)this.H.a()).intValue())) {
         ItemStack var2 = this.w.thePlayer.bJ.getStackInSlot(this.t());
         BlockPos var3 = this.E.a();
         EnumFacing var4 = this.E.b();
         Vec3 var5 = MathHelper.a(var3, var4);
         if(this.w.playerController.b(this.w.thePlayer, this.w.theWorld, var2, var3, var4, var5)) {
            if(((Boolean)this.F.a()).booleanValue() && !this.N.contains(var3)) {
               this.N.add(var3);
            }

            if(this.K.a((Object)"Swing Item")) {
               this.w.thePlayer.swingItem();
            }

            this.b((Packet)(new C0APacketAnimation()));
         }

         this.C.b();
      }

   }

   @agu
   public void a(aG1 var1) {
      String var2 = r();
      if(var1.h().equals(lS.c()?(this.w.thePlayer.onGround?EventState.PRE:EventState.POST):EventState.valueOf((String)this.x.a()))) {
         this.A();
      }

   }

   public boolean m() {
      String var1 = r();
      return !this.B() && !this.u() && this.w.thePlayer.onGround;
   }

   public boolean b() {
      String var1 = r();
      return this.K.a((Object)"Tower") && this.B() && !this.w.thePlayer.p();
   }

   @agu
   public void a(apn var1) {
      String var2 = r();
      if(this.b() || this.s()) {
         var1.setCancelled(true);
      }

   }

   @agu
   public void c(aL_ var1) {
      String var2 = r();
      if(this.b() && (this.w.thePlayer.onGround || this.E != null)) {
         this.w.thePlayer.setPosition((double)MathHelper.floor_double(this.w.thePlayer.posX) + 0.5D, this.w.thePlayer.posY, (double)MathHelper.floor_double(this.w.thePlayer.posZ) + 0.5D);
         var1.a(this.w.thePlayer.motionY = 0.41999998688698D);
      }

   }

   @agu
   public void c(aG1 var1) {
      String var2 = r();
      if(var1.h().equals(EventState.PRE)) {
         if(this.s()) {
            this.f(var1);
            this.w.thePlayer.cameraPitch = 0.0F;
            this.w.thePlayer.cameraYaw = 0.0F;
         }

         if(this.b()) {
            this.w.thePlayer.e(0.0D);
            this.w.thePlayer.cameraPitch = 0.0F;
            this.w.thePlayer.cameraYaw = 0.0F;
            this.w.timer.i = this.Y.a((Object)"Tower")?((Float)this.G.a()).floatValue():1.0F;
            double var3 = var1.f() % 1.0D;
            double var5 = (double)MathHelper.floor_double(var1.f());
            if(var3 > 0.419D && var3 < 0.753D) {
               var1.c(var5 + 0.41999998688698D);
            }

            if(var3 > 0.753D) {
               var1.c(var5 + 0.7531999805212D);
            }

            var1.c(var5);
            var1.c(true);
            if(lS.c()) {
               bgM.d().a(var1);
            }
         }

         if(this.w.thePlayer.onGround && !((Boolean)this.T.a()).booleanValue() && this.ae.a("Boost") && !this.j()) {
            ;
         }
      }

   }

   @agu
   public void a(apo var1) {
      String var2 = r();
      if(!this.a() && this.w.thePlayer.onGround && !this.w.theWorld.a(this.w.thePlayer.posX, this.w.thePlayer.posY - 1.125D, this.w.thePlayer.posZ).getBlock().r() && !this.s() && !this.b() && !this.j() && this.w.thePlayer.posY % 1.0D == 0.0D && (double)var1.a().getY() == this.w.thePlayer.posY - 1.0D && var1.b() instanceof BlockAir) {
         var1.a(new AxisAlignedBB(var1.a(), var1.a().a(1, 1, 1)));
      }

   }

   @agu
   public void b(ap9 var1) {
      String var2 = r();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(this.K.a((Object)"Silent") && var1.d() instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange var3 = (C09PacketHeldItemChange)var1.d();
            if(var3.getSlotId() != this.t()) {
               var1.setCancelled(true);
            }
         }

         if(var1.d() instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction var4 = (C0BPacketEntityAction)var1.d();
            if(var4.getAction().name().contains("SPRINT")) {
               var1.setCancelled(true);
            }
         }

         if(this.w.thePlayer.p() || this.B()) {
            if(var1.d() instanceof C08PacketPlayerBlockPlacement) {
               var1.setCancelled(true);
            }

            if(var1.d() instanceof C07PacketPlayerDigging) {
               C07PacketPlayerDigging var5 = (C07PacketPlayerDigging)var1.d();
               if(var5.getStatus().equals(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM) || var5.getStatus().equals(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK)) {
                  var1.setCancelled(true);
               }
            }
         }
      }

   }

   @agu
   public void d(aG1 var1) {
      String var2 = r();
      if(var1.h().equals(EventState.PRE)) {
         label19: {
            if(!((Boolean)this.T.a()).booleanValue() && this.ae.a("Jump") && !this.j() && !this.s() && !this.b()) {
               if(this.w.thePlayer.onGround) {
                  this.R = (double)MathHelper.floor_double(this.w.thePlayer.posY);
               }

               if(!this.w.thePlayer.p() || this.ab % 3 == 0 || this.w.thePlayer.fallDistance >= 1.0F) {
                  break label19;
               }

               var1.c(true);
            }

            if(!this.j() || this.w.thePlayer.onGround) {
               this.R = (double)MathHelper.floor_double(this.w.thePlayer.posY);
            }
         }

         double var3 = this.R - (double)(this.j()?2:1);
         boolean var5 = this.a(this.w.theWorld.getBlockState(new BlockPos(this.w.thePlayer.posX, var3, this.w.thePlayer.posZ)).getBlock());
         double var6 = this.w.thePlayer.posX;
         double var8 = this.w.thePlayer.posZ;
         this.I = new BlockPos(var6, var3, var8);
         Block var10 = this.w.theWorld.getBlockState(this.I).getBlock();
         if(!var10.getMaterial().isLiquid() && var10.getMaterial().isReplaceable()) {
            this.E = this.j()?aqc.b(this.I):aqc.c(this.I);
         }

         this.E = null;
      }

   }

   public int o() {
      int var2 = 0;
      r();
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.bo.getSlot(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.bo.getSlot(var3).getStack();
            if(this.a(var4.getItem())) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int F() {
      r();
      int var2 = 0;
      int var3 = 36;
      if(var3 < 45) {
         if(this.w.thePlayer.bo.getSlot(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.bo.getSlot(var3).getStack();
            if(this.a(var4.getItem())) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private boolean u() {
      return Keyboard.isKeyDown(this.w.gameSettings.keyBindSneak.getKeyCode());
   }

   private boolean B() {
      return Keyboard.isKeyDown(this.w.gameSettings.keyBindJump.getKeyCode());
   }

   @agu
   public void a(WB var1) {
      this.c((String)(lS.c()?"Hypixel":""));
      ++this.ab;
   }

   @agu
   public void a(aSt var1) {
      r();
      ItemStack var3 = this.w.thePlayer.bJ.getStackInSlot(this.t());
      if(var3 != null && var3.getItem() instanceof ItemBlock && this.a(var3.getItem())) {
         ScaledResolution var4 = var1.a();
         float var5 = (float)aw5.a.a("/" + this.o());
         float var6 = (float)(var4.getScaledWidth() / 2) - var5 / 2.0F - 2.0F;
         float var7 = (float)(var4.getScaledHeight() - 105);
         a6_.a(var6 - 10.0F, var7 + 8.5F, 22.0F + var5, 20.0F, 8.0F, Ju.a(0, 0, 0, 120));
         a6_.a(aw5.a, false, var3, var6 - 7.0F, var7 + 10.5F);
         aw5.a.a(Integer.toString(this.o()), (double)(var6 + 10.0F), (double)(var7 + 16.0F), 16777215, true);
      }

   }

   public float[] a(int var1) {
      r();
      float[] var3 = new float[3];
      int var7 = Math.max(var1 >>> 16 & 255, var1 >>> 8 & 255);
      if((var1 & 255) > var7) {
         var7 = var1 & 255;
      }

      int var8 = Math.min(var1 >>> 16 & 255, var1 >>> 8 & 255);
      if((var1 & 255) < var8) {
         var8 = var1 & 255;
      }

      float var5 = (float)var7 / 255.0F;
      float var4 = var7 != 0?(float)(var7 - var8) / (float)var7:0.0F;
      if(var4 == 0.0F) {
         float var6 = 0.0F;
      }

      float var9 = (float)(var7 - (var1 >>> 16 & 255)) / (float)(var7 - var8);
      float var10 = (float)(var7 - (var1 >>> 8 & 255)) / (float)(var7 - var8);
      float var11 = (float)(var7 - (var1 & 255)) / (float)(var7 - var8);
      float var12 = ((var1 >>> 16 & 255) == var7?var11 - var10:((var1 >>> 8 & 255) == var7?2.0F + var9 - var11:4.0F + var10 - var9)) / 6.0F;
      if(var12 < 0.0F) {
         ++var12;
      }

      var3[0] = var12;
      var3[1] = var4;
      var3[2] = var5;
      return var3;
   }

   @agu
   public void a(aye var1) {
      String var2 = r();
      if(((Boolean)this.F.a()).booleanValue()) {
         a6_.h();
         GL11.glLineWidth(2.0F);
         GlStateManager.disableBlend();
         int var3 = 0;
         long var4 = Minecraft.getSystemTime();
         GL11.glBegin(3);
         Iterator var6 = this.N.iterator();
         if(var6.hasNext()) {
            BlockPos var7 = (BlockPos)var6.next();
            a6_.a(new Color(((ava)gZ.g().d().b(ava.class)).a(var3, (float)var4, 3)));
            double var8 = (double)var7.getX() + 0.5D - this.w.getRenderManager().h;
            double var10 = (double)(var7.getY() + 1) - this.w.getRenderManager().g;
            double var12 = (double)var7.getZ() + 0.5D - this.w.getRenderManager().m;
            GL11.glVertex3d(var8, var10, var12);
            var4 = var4 - 300L;
            var3 = var3 + 20;
         }

         GL11.glEnd();
         a6_.i();
      }

   }

   public int t() {
      r();
      int var2 = 0;
      if(var2 < 9) {
         if(this.w.thePlayer.bJ.getStackInSlot(var2) != null && this.w.thePlayer.bJ.getStackInSlot(var2).stackSize != 0) {
            Item var3 = this.w.thePlayer.bJ.getStackInSlot(var2).getItem();
            if(this.a(var3)) {
               return var2;
            }
         }

         ++var2;
      }

      return this.w.thePlayer.bJ.currentItem;
   }

   public void n() {
      String var1 = r();
      if(this.o() > 0) {
         this.c((String)(lS.c()?"Hypixel":""));
         this.a((Class[])(new Class[]{avS.class, av8.class, avB.class}));
         this.a((Packet)(new C0BPacketEntityAction(this.w.thePlayer, C0BPacketEntityAction$Action.STOP_SPRINTING)));
      }

      this.R = (double)MathHelper.floor_double(this.w.thePlayer.posY);
      this.aa = this.w.thePlayer.bJ.currentItem;
      this.V = this.w.thePlayer.bJ.currentItem;
   }

   public void c() {
      r();
      this.w.timer.i = 1.0F;
      this.N.clear();
      this.E = null;
      this.ab = 0;
      this.C.b();
      this.B.b();
      if(this.K.a((Object)"Silent")) {
         if(this.aa == this.w.thePlayer.bJ.currentItem) {
            return;
         }

         this.a((Packet)(new C09PacketHeldItemChange(this.aa = this.w.thePlayer.bJ.currentItem)));
      }

      if(this.w.thePlayer.bJ.currentItem != this.V) {
         this.w.thePlayer.bJ.currentItem = this.V;
      }

   }

   public aqc k() {
      return this.E;
   }

   public List l() {
      return this.L;
   }

   public BlockPos y() {
      return this.I;
   }

   public aE3 v() {
      return this.K;
   }

   public float I() {
      return this.Q;
   }

   public float E() {
      return this.S;
   }

   public boolean q() {
      return ((Boolean)this.J.a()).booleanValue();
   }

   public boolean G() {
      return ((Boolean)this.T.a()).booleanValue();
   }

   public String p() {
      return (String)this.ae.a();
   }

   public aEs w() {
      return this.ae;
   }

   public aEs C() {
      return this.x;
   }

   public aEu H() {
      return this.W;
   }

   public aEl z() {
      return this.Z;
   }

   public aEl c() {
      return this.G;
   }

   public aEl x() {
      return this.X;
   }

   public aEl n() {
      return this.P;
   }

   public aE3 g() {
      return this.Y;
   }

   private Boolean lambda$new$5() {
      return Boolean.valueOf(this.Y.a((Object)"Down Ward"));
   }

   private Boolean lambda$new$4() {
      return Boolean.valueOf(this.Y.a((Object)"Tower Move"));
   }

   private Boolean lambda$new$3() {
      return Boolean.valueOf(this.Y.a((Object)"Tower"));
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.Y.a((Object)"Moving"));
   }

   private Boolean lambda$new$1() {
      String var1 = r();
      return Boolean.valueOf(!((Boolean)this.T.a()).booleanValue());
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.K.a((Object)"Swapper"));
   }

   public static void e(String var0) {
      M = var0;
   }

   public static String r() {
      return M;
   }

   static {
      e("sMFU8");
   }
}
