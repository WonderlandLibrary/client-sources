package cc.novoline.modules.move;

import cc.novoline.Initializer;
import cc.novoline.Novoline;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.CollideWithBlockEvent;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.JumpEvent;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.SneakEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.move.Flight;
import cc.novoline.modules.move.LongJump;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.ColorUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import cc.novoline.utils.notifications.NotificationType;
import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import net.aqc;
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
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Scaffold extends AbstractModule {
   private List N = new CopyOnWriteArrayList();
   private Timer timer = new Timer();
   private Timer C = new Timer();
   private List blacklistedBlocks;
   private int oldSlot;
   private int aa;
   private int ab;
   private aqc E;
   private BlockPos blockBelow;
   private float Q;
   private float S;
   private float y;
   private double R;
   private double A;
   @Property("addons")
   private ListProperty K = PropertyFactory.createList("Swapper", "Silent", "Down Ward").acceptableValues((Object[])(new String[]{"Swing Item", "Safe Walk", "Swapper", "Silent", "Tower", "Down Ward", "Tower Move"}));
   @Property("render-rotations")
   private BooleanProperty render_rotations = PropertyFactory.createBoolean(Boolean.valueOf(true));
   @Property("slot")
   private IntProperty ad = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(6)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
   @Property("path")
   private BooleanProperty F = PropertyFactory.createBoolean(Boolean.valueOf(true));
   @Property("no-sprint")
   private BooleanProperty T = PropertyFactory.createBoolean(Boolean.valueOf(false));
   @Property("sprint-boost")
   private StringProperty ae;
   @Property("timer-support")
   private final ListProperty Y;
   @Property("swap-delay")
   private IntProperty ac;
   @Property("place-delay")
   private IntProperty H;
   @Property("place-event")
   private StringProperty x;
   @Property("timer-moving")
   private final FloatProperty X;
   @Property("timer-tower")
   private final FloatProperty G;
   @Property("timer-towermove")
   private final FloatProperty P;
   @Property("timer-downward")
   private final FloatProperty Z;
   @Property("ray-trace")
   private final BooleanProperty W;
   private double D;
   private double U;
   private double O;
   private boolean canPlace;
   private static String M;

   public Scaffold(ModuleManager var1) {
      super(var1, EnumModuleType.MOVEMENT, "Scaffold", "Scaffold");
      r();
      this.ae = PropertyFactory.createString("Boost").acceptableValues(new String[]{"Vanilla", "Boost", "Jump"});
      this.Y = PropertyFactory.createList("Moving", "Tower").acceptableValues((Object[])(new String[]{"Moving", "Tower", "Tower Move", "Down Ward"}));
      this.ac = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(250)).minimum(Integer.valueOf(200))).maximum(Integer.valueOf(500));
      this.H = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(0)).minimum(Integer.valueOf(0))).maximum(Integer.valueOf(200));
      this.x = PropertyFactory.createString("POST").acceptableValues(new String[]{"PRE", "POST"});
      this.X = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(2.0F));
      this.G = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(3.0F));
      this.P = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(2.0F));
      this.Z = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(2.0F));
      this.W = PropertyFactory.booleanFalse();
      Manager.put(new Setting("SF_PLACE_EVENT", "Place Event", SettingType.COMBOBOX, this, this.x));
      Manager.put(new Setting("SF_RAY_TRACE", "Ray Trace", SettingType.CHECKBOX, this, this.W));
      Manager.put(new Setting("SF_ADDONS", "Addons", SettingType.SELECTBOX, this, this.K));
      Manager.put(new Setting("SF_SWAP_DELAY", "Swap Delay", SettingType.SLIDER, this, this.ac, 50.0D, this::lambda$new$0));
      Manager.put(new Setting("SF_PLACE_DELAY", "Place Delay", SettingType.SLIDER, this, this.H, 50.0D));
      Manager.put(new Setting("SF_SPRINT_MODE", "Sprint Mode", SettingType.COMBOBOX, this, this.ae, this::lambda$new$1));
      Manager.put(new Setting("SF_NO_SPRINT", "No Sprint", SettingType.CHECKBOX, this, this.T));
      Manager.put(new Setting("SF_RENDER_ROTS", "Render Rotations", SettingType.CHECKBOX, this, this.render_rotations));
      Manager.put(new Setting("SF_PATH", "Render Path", SettingType.CHECKBOX, this, this.F));
      Manager.put(new Setting("SF_SWAP_SLOT", "Swap Slot", SettingType.SLIDER, this, this.ad, 1.0D));
      Manager.put(new Setting("SF_TIMER_SUPPORT", "Timer Support", SettingType.SELECTBOX, this, this.Y));
      Manager.put(new Setting("SF_TIMER_BOOST_MOVING", "Moving Boost", SettingType.SLIDER, this, this.X, 0.1D, this::lambda$new$2));
      Manager.put(new Setting("SF_TIMER_BOOST_TOWER", "Tower Boost", SettingType.SLIDER, this, this.G, 0.1D, this::lambda$new$3));
      Manager.put(new Setting("SF_TIMER_BOOST_TOWERMOVE", "Tower Move Boost", SettingType.SLIDER, this, this.P, 0.1D, this::lambda$new$4));
      Manager.put(new Setting("SF_TIMER_BOOST_DOWNWARD", "Down Ward Boost", SettingType.SLIDER, this, this.Z, 0.1D, this::lambda$new$5));
      this.blacklistedBlocks = Arrays.asList(new Block[]{Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.wooden_slab, Blocks.wooden_slab, Blocks.chest, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.skull, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.tnt, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.trapped_chest, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.activator_rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.redstone_torch, Blocks.acacia_stairs, Blocks.birch_stairs, Blocks.brick_stairs, Blocks.dark_oak_stairs, Blocks.jungle_stairs, Blocks.nether_brick_stairs, Blocks.oak_stairs, Blocks.quartz_stairs, Blocks.red_sandstone_stairs, Blocks.sandstone_stairs, Blocks.spruce_stairs, Blocks.stone_brick_stairs, Blocks.stone_stairs, Blocks.wooden_slab, Blocks.double_wooden_slab, Blocks.stone_slab, Blocks.double_stone_slab, Blocks.stone_slab2, Blocks.double_stone_slab2, Blocks.web, Blocks.gravel, Blocks.daylight_detector_inverted, Blocks.daylight_detector, Blocks.soul_sand, Blocks.piston, Blocks.piston_extension, Blocks.piston_head, Blocks.sticky_piston, Blocks.iron_trapdoor, Blocks.ender_chest, Blocks.end_portal, Blocks.end_portal_frame, Blocks.standing_banner, Blocks.wall_banner, Blocks.deadbush, Blocks.slime_block, Blocks.acacia_fence_gate, Blocks.birch_fence_gate, Blocks.dark_oak_fence_gate, Blocks.jungle_fence_gate, Blocks.spruce_fence_gate, Blocks.oak_fence_gate, Blocks.double_plant});
   }

   private boolean J() {
      r();
      byte var2 = 36;
      if(var2 >= 45) {
         return false;
      } else {
         ItemStack var3 = this.mc.player.inventoryContainer.getSlot(var2).getStack();
         return var3 != null && var3.getItem() != null && var3.getItem() instanceof ItemBlock && this.isValid(var3.getItem());
      }
   }

   private boolean isValid(Item var1) {
      String var2 = r();
      if(var1 instanceof ItemBlock) {
         ItemBlock var3 = (ItemBlock)var1;
         Block var4 = var3.getBlock();
         return !this.getBlacklistedBlocks().contains(var4);
      } else {
         return false;
      }
   }

   private int getBiggestBlockSlotInv() {
      r();
      int var2 = -1;
      int var3 = 0;
      if(this.getBlockCount() == 0) {
         return -1;
      } else {
         int var4 = 9;
         if(var4 < 36) {
            Slot var5 = this.mc.player.inventoryContainer.getSlot(var4);
            if(var5.getHasStack()) {
               Item var6 = var5.getStack().getItem();
               ItemStack var7 = var5.getStack();
               if(var6 instanceof ItemBlock && this.isValid(var6) && var7.stackSize > var3) {
                  var3 = var7.stackSize;
                  var2 = var4;
               }
            }

            ++var4;
         }

         return var2;
      }
   }

   private int getBiggestBlockSlotHotbar() {
      r();
      int var2 = -1;
      int var3 = 0;
      if(this.getBlockCount() == 0) {
         return -1;
      } else {
         int var4 = 36;
         if(var4 < 45) {
            Slot var5 = this.mc.player.inventoryContainer.getSlot(var4);
            if(var5.getHasStack()) {
               Item var6 = var5.getStack().getItem();
               ItemStack var7 = var5.getStack();
               if(var6 instanceof ItemBlock && this.isValid(var6) && var7.stackSize > var3) {
                  var3 = var7.stackSize;
                  var2 = var4;
               }
            }

            ++var4;
         }

         return var2;
      }
   }

   public boolean isAirBlock(Block var1) {
      String var2 = r();
      return !var1.getMaterial().isReplaceable()?false:!(var1 instanceof BlockSnow) || var1.getBlockBoundsMaxY() <= 0.125D;
   }

   public double[] a(double var1) {
      BlockPos var4 = new BlockPos(this.mc.player.posX, var1, this.mc.player.posZ);
      Block var5 = this.mc.world.getBlockState(var4).getBlock();
      r();
      MovementInput var6 = this.mc.player.movementInput();
      float var7 = var6.getMoveForward();
      float var8 = var6.getMoveStrafe();
      float var9 = this.mc.player.rotationYaw;
      double var10 = -999.0D;
      double var12 = -999.0D;
      double var14 = 0.0D;
      double var16 = 0.2D;
      if(!this.isAirBlock(var5)) {
         var10 = this.mc.player.posX;
         var12 = this.mc.player.posZ;
         ++var14;
         if(var14 > var16) {
            var14 = var16;
         }

         var10 = var10 + ((double)var7 * 0.45D * (double)MathHelper.cos(Math.toRadians((double)(var9 + 90.0F))) + (double)var8 * 0.45D * (double)MathHelper.sin(Math.toRadians((double)(var9 + 90.0F)))) * var14;
         var12 = var12 + ((double)var7 * 0.45D * (double)MathHelper.sin(Math.toRadians((double)(var9 + 90.0F))) - (double)var8 * 0.45D * (double)MathHelper.cos(Math.toRadians((double)(var9 + 90.0F)))) * var14;
         if(var14 == var16) {
            ;
         }

         var4 = new BlockPos(var10, var1, var12);
         var5 = this.mc.world.getBlockState(var4).getBlock();
      }

      return new double[]{var10, var12};
   }

   private float[] a(BlockPos var1, EnumFacing var2) {
      r();
      double var4 = (double)var1.getX() + 0.5D - this.mc.player.posX + (double)var2.getFrontOffsetX() / 2.0D;
      double var6 = (double)var1.getZ() + 0.5D - this.mc.player.posZ + (double)var2.getFrontOffsetZ() / 2.0D;
      double var8 = (double)var1.getY() + 0.5D;
      double var10 = this.mc.player.posY + (double)this.mc.player.getEyeHeight() - var8;
      double var12 = (double)MathHelper.sqrt_double(var4 * var4 + var6 * var6);
      float var14 = (float)(Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      float var15 = (float)(Math.atan2(var10, var12) * 180.0D / 3.141592653589793D);
      if(var14 < 0.0F) {
         var14 += 360.0F;
      }

      return new float[]{var14, var15};
   }

   private float[] a(Vec3 var1) {
      double var3 = var1.getX() - this.mc.player.posX;
      r();
      double var5 = var1.getZ() - this.mc.player.posZ;
      double var7 = var1.getY();
      double var9 = this.mc.player.posY + (double)this.mc.player.getEyeHeight() - var7;
      double var11 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5);
      float var13 = (float)(Math.atan2(var5, var3) * 180.0D / 3.141592653589793D) - 90.0F;
      float var14 = (float)(Math.atan2(var9, var11) * 180.0D / 3.141592653589793D);
      if(var13 < 0.0F) {
         var13 += 360.0F;
      }

      return new float[]{var13, var14};
   }

   private int b(int var1) {
      return this.mc.player.inventory.getStackInSlot(var1).stackSize;
   }

   @EventTarget
   public void onPre2(MotionUpdateEvent var1) {
      String var2 = r();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.getBlockCountHotBar() <= 0) {
            this.mc.player.swap(this.getBiggestBlockSlotInv(), ((Integer)this.ad.get()).intValue() - 1);
         }

         if(this.K.contains("Swapper") && this.timer.delay((double)((Integer)this.ac.get()).intValue())) {
            int var3 = this.mc.player.inventoryContainer.windowId;
            int var4 = this.getBiggestBlockSlotInv();
            int var5 = this.getBiggestBlockSlotHotbar();
            if(var4 != -1 && this.b(var4) > (var5 == -1?0:this.b(var5 - 36))) {
               this.mc.at.a(var3, var4, this.neededSlot(), 2, this.mc.player);
            }

            if(var5 != -1 && var5 - 36 != this.neededSlot()) {
               this.mc.at.a(var3, var5, this.neededSlot(), 2, this.mc.player);
            }

            this.timer.reset();
         }
      }

   }

   public boolean s() {
      String var1 = r();
      return this.K.contains("Tower Move") && this.B() && this.mc.player.isMoving();
   }

   public boolean j() {
      String var1 = r();
      return this.K.contains("Down Ward") && this.u();
   }

   public boolean a() {
      String var1 = r();
      return !this.j() && this.K.contains("Safe Walk") || ((Integer)this.H.get()).intValue() > 0;
   }

   @EventTarget
   public void a(SneakEvent var1) {
      if(this.K.contains("Down Ward")) {
         var1.setCancelled(true);
      }

   }

   private void f(MotionUpdateEvent var1) {
      String var2 = r();
      if(this.mc.player.isOnGround(0.76D) && !this.mc.player.isOnGround(0.75D) && this.mc.player.motionY > 0.23D && this.mc.player.motionY < 0.25D) {
         this.mc.player.motionY = (double)Math.round(this.mc.player.posY) - this.mc.player.posY;
      }

      if(this.mc.player.isOnGround(1.0E-4D)) {
         this.mc.player.motionY = 0.41999998688698D;
      }

      if(this.mc.player.posY >= (double)Math.round(this.mc.player.posY) - 1.0E-4D && this.mc.player.posY <= (double)Math.round(this.mc.player.posY) + 1.0E-4D && !this.u()) {
         this.mc.player.motionY = 0.0D;
      }

      var1.setOnGround(this.mc.player.c(var1.getY()));
   }

   public boolean f() {
      String var1 = r();
      return this.E != null && (!this.m() || ((Boolean)this.T.get()).booleanValue() || this.ae.equals("Vanilla") || this.mc.player.getLastTickDistance() <= this.mc.player.l(false));
   }

   private void d(MoveEvent var1) {
      String var2 = r();
      if(this.ab % 3 != 0) {
         var1.setMoveSpeed(this.mc.player.l(false) * ThreadLocalRandom.current().nextDouble(0.996D, 1.0D));
      }

      var1.setMoveSpeed(this.mc.player.a(true, 0.2D) * ThreadLocalRandom.current().nextDouble(1.186D, 1.2D));
   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      this.U = this.mc.player.getBaseMoveSpeed() * 0.98D;
      r();
      this.O = this.mc.player.getLastTickDistance();
      if(this.getBlockCount() == 0) {
         this.novoline.getNotificationManager().pop(this.getDisplayName(), "No blocks in inventory", NotificationType.WARNING);
         this.toggle();
      }

   }

   private void a(MoveEvent var1) {
      String var2 = r();
      if(this.mc.player.isMoving()) {
         if(this.mc.player.onGround && !this.mc.player.N()) {
            var1.setY(this.mc.player.motionY = 0.419999986886978D);
            this.D = this.U * 2.139999980926514D;
         }

         if(this.canPlace) {
            this.D = this.O - 0.81999D * (this.O - this.U);
         }

         this.D -= this.O / 159.0D;
         if(this.ab % 3 != 0) {
            var1.setMoveSpeed(this.mc.player.l(false) * 0.998D);
         }

         var1.setMoveSpeed(Math.max(this.D, this.U));
         this.canPlace = this.mc.player.onGround;
      }

      var1.setMoveSpeed(0.0D);
   }

   @EventTarget
   public void b(MoveEvent var1) {
      String var2 = r();
      if(this.mc.player.isMoving()) {
         if(this.mc.player.movementInput().sneak()) {
            this.mc.timer.timerSpeed = this.Y.contains("Moving")?((Float)this.X.get()).floatValue():1.0F;
            var1.setMoveSpeed(this.mc.player.b(0.09158123582468379D, 0.2D));
         }

         if(this.j()) {
            this.mc.timer.timerSpeed = this.Y.contains("Down Ward")?((Float)this.Z.get()).floatValue():1.0F;
            var1.setMoveSpeed(this.mc.player.l(false) * ThreadLocalRandom.current().nextDouble(0.994D, 1.0D));
         }

         if(this.s()) {
            this.mc.timer.timerSpeed = this.Y.contains("Tower Move")?((Float)this.P.get()).floatValue():1.0F;
            var1.setMoveSpeed(this.mc.player.l(false) * ThreadLocalRandom.current().nextDouble(0.994D, 1.0D));
         }

         if(((Boolean)this.T.get()).booleanValue()) {
            this.mc.timer.timerSpeed = this.Y.contains("Moving")?((Float)this.X.get()).floatValue():1.0F;
            var1.setMoveSpeed(this.mc.player.l(false) * 0.998D);
         }

         if(this.ae.equals("Jump")) {
            this.mc.timer.timerSpeed = this.Y.contains("Moving")?((Float)this.X.get()).floatValue():1.0F;
            this.a(var1);
         }

         if(!this.mc.player.onGround) {
            this.mc.timer.timerSpeed = this.Y.contains("Moving")?((Float)this.X.get()).floatValue():1.0F;
            var1.setMoveSpeed(this.mc.player.l(false) * ThreadLocalRandom.current().nextDouble(0.994D, 1.0D));
         }

         if(this.ae.equals("Boost")) {
            this.mc.timer.timerSpeed = this.Y.contains("Moving")?((Float)this.X.get()).floatValue():1.0F;
            this.d(var1);
         }

         if(!this.ae.equals("Vanilla")) {
            return;
         }

         this.mc.timer.timerSpeed = this.Y.contains("Moving")?((Float)this.X.get()).floatValue():1.0F;
      }

      this.mc.timer.timerSpeed = 1.0F;
   }

   @EventTarget
   public void e(MotionUpdateEvent var1) {
      String var2 = r();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.E != null) {
            if(((Boolean)this.W.get()).booleanValue()) {
               this.a(MathHelper.a(this.E.a(), this.E.b()));
            }

            float[] var3 = this.a(this.E.a(), this.E.b());
            this.Q = var3[0];
            this.S = var3[1];
            this.y = (this.b()?87.5F:84.0F) + ThreadLocalRandom.current().nextFloat();
         }

         if(this.aa != this.neededSlot()) {
            this.sendPacketNoEvent(new C09PacketHeldItemChange(this.aa = this.neededSlot()));
         }

         if(((Boolean)this.W.get()).booleanValue()) {
            var1.setYaw(this.Q);
            var1.setPitch(this.S);
         }

         var1.setPitch(this.y);
         var1.setYaw(this.mc.player.o(-180.0F));
         this.mc.player.setSprinting(!((Boolean)this.T.get()).booleanValue() && this.mc.player.movementInput().getMoveStrafe() > 0.0F);
      }

   }

   private void A() {
      String var1 = r();
      if(this.f() && this.C.delay((double)((Integer)this.H.get()).intValue())) {
         ItemStack var2 = this.mc.player.inventory.getStackInSlot(this.neededSlot());
         BlockPos var3 = this.E.a();
         EnumFacing var4 = this.E.b();
         Vec3 var5 = MathHelper.a(var3, var4);
         if(this.mc.at.b(this.mc.player, this.mc.world, var2, var3, var4, var5)) {
            if(((Boolean)this.F.get()).booleanValue() && !this.N.contains(var3)) {
               this.N.add(var3);
            }

            if(this.K.contains("Swing Item")) {
               this.mc.player.swingItem();
            }

            this.sendPacket(new C0APacketAnimation());
         }

         this.C.reset();
      }

   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      String var2 = r();
      if(var1.getState().equals(ServerUtils.isHypixel()?(this.mc.player.onGround?EventState.PRE:EventState.POST):EventState.valueOf((String)this.x.get()))) {
         this.A();
      }

   }

   public boolean m() {
      String var1 = r();
      return !this.B() && !this.u() && this.mc.player.onGround;
   }

   public boolean b() {
      String var1 = r();
      return this.K.contains("Tower") && this.B() && !this.mc.player.isMoving();
   }

   @EventTarget
   public void onJump(JumpEvent var1) {
      String var2 = r();
      if(this.b() || this.s()) {
         var1.setCancelled(true);
      }

   }

   @EventTarget
   public void c(MoveEvent var1) {
      String var2 = r();
      if(this.b() && (this.mc.player.onGround || this.E != null)) {
         this.mc.player.setPosition((double)MathHelper.floor_double(this.mc.player.posX) + 0.5D, this.mc.player.posY, (double)MathHelper.floor_double(this.mc.player.posZ) + 0.5D);
         var1.setY(this.mc.player.motionY = 0.41999998688698D);
      }

   }

   @EventTarget
   public void c(MotionUpdateEvent var1) {
      String var2 = r();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.s()) {
            this.f(var1);
            this.mc.player.cameraPitch = 0.0F;
            this.mc.player.cameraYaw = 0.0F;
         }

         if(this.b()) {
            this.mc.player.setSpeed(0.0D);
            this.mc.player.cameraPitch = 0.0F;
            this.mc.player.cameraYaw = 0.0F;
            this.mc.timer.timerSpeed = this.Y.contains("Tower")?((Float)this.G.get()).floatValue():1.0F;
            double var3 = var1.getY() % 1.0D;
            double var5 = (double)MathHelper.floor_double(var1.getY());
            if(var3 > 0.419D && var3 < 0.753D) {
               var1.setY(var5 + 0.41999998688698D);
            }

            if(var3 > 0.753D) {
               var1.setY(var5 + 0.7531999805212D);
            }

            var1.setY(var5);
            var1.setOnGround(true);
            if(ServerUtils.isHypixel()) {
               Initializer.getInstance().a(var1);
            }
         }

         if(this.mc.player.onGround && !((Boolean)this.T.get()).booleanValue() && this.ae.equals("Boost") && !this.j()) {
            ;
         }
      }

   }

   @EventTarget
   public void a(CollideWithBlockEvent var1) {
      String var2 = r();
      if(!this.a() && this.mc.player.onGround && !this.mc.world.a(this.mc.player.posX, this.mc.player.posY - 1.125D, this.mc.player.posZ).getBlock().isSolidFullCube() && !this.s() && !this.b() && !this.j() && this.mc.player.posY % 1.0D == 0.0D && (double)var1.getPos().getY() == this.mc.player.posY - 1.0D && var1.getBlock() instanceof BlockAir) {
         var1.setBoundingBox(new AxisAlignedBB(var1.getPos(), var1.getPos().a(1, 1, 1)));
      }

   }

   @EventTarget
   public void b(PacketEvent var1) {
      String var2 = r();
      if(var1.getDirection().equals(PacketDirection.OUTGOING)) {
         if(this.K.contains("Silent") && var1.getPacket() instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange var3 = (C09PacketHeldItemChange)var1.getPacket();
            if(var3.getSlotId() != this.neededSlot()) {
               var1.setCancelled(true);
            }
         }

         if(var1.getPacket() instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction var4 = (C0BPacketEntityAction)var1.getPacket();
            if(var4.getAction().name().contains("SPRINT")) {
               var1.setCancelled(true);
            }
         }

         if(this.mc.player.isMoving() || this.B()) {
            if(var1.getPacket() instanceof C08PacketPlayerBlockPlacement) {
               var1.setCancelled(true);
            }

            if(var1.getPacket() instanceof C07PacketPlayerDigging) {
               C07PacketPlayerDigging var5 = (C07PacketPlayerDigging)var1.getPacket();
               if(var5.getStatus().equals(C07PacketPlayerDigging$Action.RELEASE_USE_ITEM) || var5.getStatus().equals(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK)) {
                  var1.setCancelled(true);
               }
            }
         }
      }

   }

   @EventTarget
   public void d(MotionUpdateEvent var1) {
      String var2 = r();
      if(var1.getState().equals(EventState.PRE)) {
         label19: {
            if(!((Boolean)this.T.get()).booleanValue() && this.ae.equals("Jump") && !this.j() && !this.s() && !this.b()) {
               if(this.mc.player.onGround) {
                  this.R = (double)MathHelper.floor_double(this.mc.player.posY);
               }

               if(!this.mc.player.isMoving() || this.ab % 3 == 0 || this.mc.player.fallDistance >= 1.0F) {
                  break label19;
               }

               var1.setOnGround(true);
            }

            if(!this.j() || this.mc.player.onGround) {
               this.R = (double)MathHelper.floor_double(this.mc.player.posY);
            }
         }

         double var3 = this.R - (double)(this.j()?2:1);
         boolean var5 = this.isAirBlock(this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, var3, this.mc.player.posZ)).getBlock());
         double var6 = this.mc.player.posX;
         double var8 = this.mc.player.posZ;
         this.blockBelow = new BlockPos(var6, var3, var8);
         Block var10 = this.mc.world.getBlockState(this.blockBelow).getBlock();
         if(!var10.getMaterial().isLiquid() && var10.getMaterial().isReplaceable()) {
            this.E = this.j()?aqc.b(this.blockBelow):aqc.c(this.blockBelow);
         }

         this.E = null;
      }

   }

   public int getBlockCount() {
      int var2 = 0;
      r();
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.inventoryContainer.getSlot(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.inventoryContainer.getSlot(var3).getStack();
            if(this.isValid(var4.getItem())) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getBlockCountHotBar() {
      r();
      int var2 = 0;
      int var3 = 36;
      if(var3 < 45) {
         if(this.mc.player.inventoryContainer.getSlot(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.inventoryContainer.getSlot(var3).getStack();
            if(this.isValid(var4.getItem())) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private boolean u() {
      return Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode());
   }

   private boolean B() {
      return Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(ServerUtils.isHypixel()?"Hypixel":"");
      ++this.ab;
   }

   @EventTarget
   public void onRender2D(Render2DEvent var1) {
      r();
      ItemStack var3 = this.mc.player.inventory.getStackInSlot(this.neededSlot());
      if(var3 != null && var3.getItem() instanceof ItemBlock && this.isValid(var3.getItem())) {
         ScaledResolution var4 = var1.getResolution();
         float var5 = (float)Fonts$SF$SF_18.SF_18.stringWidth("/" + this.getBlockCount());
         float var6 = (float)(var4.getScaledWidth() / 2) - var5 / 2.0F - 2.0F;
         float var7 = (float)(var4.getScaledHeight() - 105);
         RenderUtils.drawRoundedRect(var6 - 10.0F, var7 + 8.5F, 22.0F + var5, 20.0F, 8.0F, ColorUtils.getColor(0, 0, 0, 120));
         RenderUtils.drawStack(Fonts$SF$SF_18.SF_18, false, var3, var6 - 7.0F, var7 + 10.5F);
         Fonts$SF$SF_18.SF_18.drawString(Integer.toString(this.getBlockCount()), (double)(var6 + 10.0F), (double)(var7 + 16.0F), 16777215, true);
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

   @EventTarget
   public void a(Render3DEvent var1) {
      String var2 = r();
      if(((Boolean)this.F.get()).booleanValue()) {
         RenderUtils.pre3D();
         GL11.glLineWidth(2.0F);
         GlStateManager.disableBlend();
         int var3 = 0;
         long var4 = Minecraft.getSystemTime();
         GL11.glBegin(3);
         Iterator var6 = this.N.iterator();
         if(var6.hasNext()) {
            BlockPos var7 = (BlockPos)var6.next();
            RenderUtils.setColor(new Color(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).a(var3, (float)var4, 3)));
            double var8 = (double)var7.getX() + 0.5D - this.mc.getRenderManager().renderPosX;
            double var10 = (double)(var7.getY() + 1) - this.mc.getRenderManager().renderPosY;
            double var12 = (double)var7.getZ() + 0.5D - this.mc.getRenderManager().renderPosZ;
            GL11.glVertex3d(var8, var10, var12);
            var4 = var4 - 300L;
            var3 = var3 + 20;
         }

         GL11.glEnd();
         RenderUtils.post3D();
      }

   }

   public int neededSlot() {
      r();
      int var2 = 0;
      if(var2 < 9) {
         if(this.mc.player.inventory.getStackInSlot(var2) != null && this.mc.player.inventory.getStackInSlot(var2).stackSize != 0) {
            Item var3 = this.mc.player.inventory.getStackInSlot(var2).getItem();
            if(this.isValid(var3)) {
               return var2;
            }
         }

         ++var2;
      }

      return this.mc.player.inventory.currentItem;
   }

   public void onEnable() {
      String var1 = r();
      if(this.getBlockCount() > 0) {
         this.setSuffix(ServerUtils.isHypixel()?"Hypixel":"");
         this.checkModule(new Class[]{Speed.class, Flight.class, LongJump.class});
         this.sendPacketNoEvent(new C0BPacketEntityAction(this.mc.player, C0BPacketEntityAction$Action.STOP_SPRINTING));
      }

      this.R = (double)MathHelper.floor_double(this.mc.player.posY);
      this.aa = this.mc.player.inventory.currentItem;
      this.oldSlot = this.mc.player.inventory.currentItem;
   }

   public void onDisable() {
      r();
      this.mc.timer.timerSpeed = 1.0F;
      this.N.clear();
      this.E = null;
      this.ab = 0;
      this.C.reset();
      this.timer.reset();
      if(this.K.contains("Silent")) {
         if(this.aa == this.mc.player.inventory.currentItem) {
            return;
         }

         this.sendPacketNoEvent(new C09PacketHeldItemChange(this.aa = this.mc.player.inventory.currentItem));
      }

      if(this.mc.player.inventory.currentItem != this.oldSlot) {
         this.mc.player.inventory.currentItem = this.oldSlot;
      }

   }

   public aqc k() {
      return this.E;
   }

   public List getBlacklistedBlocks() {
      return this.blacklistedBlocks;
   }

   public BlockPos getBlockBelow() {
      return this.blockBelow;
   }

   public ListProperty v() {
      return this.K;
   }

   public float getYaw() {
      return this.Q;
   }

   public float getPitch() {
      return this.S;
   }

   public boolean renderRotations() {
      return ((Boolean)this.render_rotations.get()).booleanValue();
   }

   public boolean G() {
      return ((Boolean)this.T.get()).booleanValue();
   }

   public String p() {
      return (String)this.ae.get();
   }

   public StringProperty w() {
      return this.ae;
   }

   public StringProperty C() {
      return this.x;
   }

   public BooleanProperty H() {
      return this.W;
   }

   public FloatProperty z() {
      return this.Z;
   }

   public FloatProperty c() {
      return this.G;
   }

   public FloatProperty x() {
      return this.X;
   }

   public FloatProperty n() {
      return this.P;
   }

   public ListProperty g() {
      return this.Y;
   }

   private Boolean lambda$new$5() {
      return Boolean.valueOf(this.Y.contains("Down Ward"));
   }

   private Boolean lambda$new$4() {
      return Boolean.valueOf(this.Y.contains("Tower Move"));
   }

   private Boolean lambda$new$3() {
      return Boolean.valueOf(this.Y.contains("Tower"));
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.Y.contains("Moving"));
   }

   private Boolean lambda$new$1() {
      String var1 = r();
      return Boolean.valueOf(!((Boolean)this.T.get()).booleanValue());
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.K.contains("Swapper"));
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
