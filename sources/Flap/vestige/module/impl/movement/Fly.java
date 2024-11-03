package vestige.module.impl.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Rotations;
import net.minecraft.util.Timer;
import net.minecraft.world.World;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.MoveEvent;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.PreMotionEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.exploit.Disabler;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.MovementUtil;
import vestige.util.player.PlayerUtil;

public class Fly extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", new String[]{"Vanilla", "Collision", "NCP", "Blocksmc", "Velocity", "CustomFly", "Cris A", "Cris B", "Cris C"});
   private final ModeSetting vanillaMode = new ModeSetting("Vanilla Mode", () -> {
      return this.mode.is("Vanilla");
   }, "Motion", new String[]{"Motion", "Creative"});
   private final DoubleSetting vanillaSpeed = new DoubleSetting("Vanilla Speed", () -> {
      return this.mode.is("Vanilla") && this.vanillaMode.is("Motion");
   }, 2.0D, 0.2D, 9.0D, 0.2D);
   private final DoubleSetting vanillaVerticalSpeed = new DoubleSetting("Vanilla Vertical Speed", () -> {
      return this.mode.is("Vanilla") && this.vanillaMode.is("Motion");
   }, 2.0D, 0.2D, 9.0D, 0.2D);
   private final ModeSetting collisionMode = new ModeSetting("Collision Mode", () -> {
      return this.mode.is("Collision");
   }, "Airwalk", new String[]{"Airwalk", "Airjump"});
   private final ModeSetting ncpMode = new ModeSetting("NCP Mode", () -> {
      return this.mode.is("NCP");
   }, "Old", new String[]{"Old"});
   private final DoubleSetting ncpSpeed = new DoubleSetting("NCP Speed", () -> {
      return this.mode.is("NCP") && this.ncpMode.is("Old");
   }, 1.0D, 0.3D, 1.7D, 0.05D);
   private final BooleanSetting damage = new BooleanSetting("Damage", () -> {
      return this.mode.is("NCP") && this.ncpMode.is("Old");
   }, false);
   private final ModeSetting velocityMode = new ModeSetting("Velocity Mode", () -> {
      return this.mode.is("Velocity");
   }, "Bow", new String[]{"Bow", "Bow2", "Wait for hit"});
   private final BooleanSetting legit = new BooleanSetting("Legit", () -> {
      return this.mode.is("Bow") || this.mode.is("Bow2");
   }, false);
   private final BooleanSetting automated = new BooleanSetting("Automated", () -> {
      return this.mode.is("Blocksmc");
   }, false);
   private final DoubleSetting crisACSpeed = new DoubleSetting("CrisAC Speed", () -> {
      return this.mode.is("CrisAC");
   }, 1.5D, 0.1D, 5.0D, 0.1D);
   private final DoubleSetting crisACVerticalSpeed = new DoubleSetting("CrisAC Vertical Speed", () -> {
      return this.mode.is("CrisAC");
   }, 1.0D, 0.1D, 5.0D, 0.1D);
   private double speed;
   private double y;
   private static boolean alrweadsactived = false;
   private boolean takingVelocity;
   private Rotations rotations;
   private double velocityX;
   private double velocityY;
   private double velocityZ;
   private double velocityDist;
   private List<BlockPos> placedBlocks = new ArrayList();
   private int ticksSinceVelocity;
   private int counter;
   private int ticks;
   private boolean started;
   private boolean done;
   private String finished;
   private double lastMotionX;
   private double lastMotionY;
   private double lastMotionZ;
   private boolean hasBow;
   private ArrayList<BlockPos> blocks = new ArrayList();
   private int oldSlot;
   private boolean notMoving;
   private float lastYaw;
   private float lastPitch;
   private BlockPos lastBarrier;
   private double lastY;
   int tickCounter = 0;
   double verticalSpeed = 1.0D;
   private static long lastActionTime = 0L;
   private static final long ACTION_DELAY_MS = 1000L;
   private static boolean delayActive = false;

   public Fly() {
      super("Fly", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.mode, this.vanillaMode, this.vanillaSpeed, this.vanillaVerticalSpeed, this.ncpMode, this.ncpSpeed, this.damage, this.velocityMode, this.legit, this.automated, this.crisACSpeed, this.crisACVerticalSpeed});
   }

   public void onEnable() {
      this.ticksSinceVelocity = Integer.MAX_VALUE;
      this.counter = this.ticks = 0;
      this.started = this.done = false;
      this.hasBow = false;
      this.notMoving = false;
      this.lastMotionX = mc.thePlayer.motionX;
      this.lastMotionY = mc.thePlayer.motionY;
      this.lastMotionZ = mc.thePlayer.motionZ;
      this.lastYaw = mc.thePlayer.rotationYaw;
      this.lastPitch = mc.thePlayer.rotationPitch;
      this.lastY = mc.thePlayer.posY;
      this.lastBarrier = null;
      String var1 = this.mode.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -2096062211:
         if (var1.equals("Velocity")) {
            var2 = 1;
         }
         break;
      case 77115:
         if (var1.equals("NCP")) {
            var2 = 0;
         }
         break;
      case 2026675163:
         if (var1.equals("CrisAC")) {
            var2 = 2;
         }
      }

      switch(var2) {
      case 0:
         if (this.ncpMode.is("Old")) {
            if (mc.thePlayer.onGround) {
               this.speed = this.ncpSpeed.getValue();
               if (this.damage.isEnabled()) {
                  PlayerUtil.ncpDamage();
               }
            } else {
               this.speed = 0.28D;
            }
         }
         break;
      case 1:
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
         }
         break;
      case 2:
         this.speed = this.crisACSpeed.getValue();
      }

   }

   public boolean onDisable() {
      alrweadsactived = false;
      Timer var10000 = mc.timer;
      var10000.timerSpeed = 1.0F;
      mc.thePlayer.capabilities.isFlying = false;
      Flap.instance.getPacketBlinkHandler().stopAll();
      String var1 = this.mode.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -2096062211:
         if (var1.equals("Velocity")) {
            var2 = 2;
         }
         break;
      case -599919172:
         if (var1.equals("Blocksmc")) {
            var2 = 3;
         }
         break;
      case 77115:
         if (var1.equals("NCP")) {
            var2 = 1;
         }
         break;
      case 1897755483:
         if (var1.equals("Vanilla")) {
            var2 = 0;
         }
         break;
      case 2026675163:
         if (var1.equals("CrisAC")) {
            var2 = 4;
         }
      }

      label49:
      switch(var2) {
      case 0:
         if (this.vanillaMode.is("Motion")) {
            MovementUtil.strafe(0.0D);
         }
         break;
      case 1:
         if (this.ncpMode.is("Old")) {
            MovementUtil.strafe(0.0D);
         }
         break;
      case 2:
         String var3 = this.velocityMode.getMode();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case 66986:
            if (var3.equals("Bow")) {
               var4 = 1;
            }
            break;
         case 2076616:
            if (var3.equals("Bow2")) {
               var4 = 2;
            }
            break;
         case 1019894833:
            if (var3.equals("Wait for hit")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            mc.thePlayer.motionX = this.lastMotionX * 0.91D;
            mc.thePlayer.motionY = this.lastMotionY;
            mc.thePlayer.motionZ = this.lastMotionZ * 0.91D;
            break label49;
         case 1:
         case 2:
            mc.thePlayer.motionX = this.lastMotionX * 0.91D;
            mc.thePlayer.motionY = this.lastMotionY;
            mc.thePlayer.motionZ = this.lastMotionZ * 0.91D;
            mc.thePlayer.rotationPitch = -90.0F;
            mc.gameSettings.keyBindUseItem.pressed = false;
         default:
            break label49;
         }
      case 3:
         MovementUtil.strafe(0.0D);
      case 4:
      }

      if (this.lastBarrier != null) {
         mc.theWorld.setBlockToAir(this.lastBarrier);
      }

      var10000 = mc.timer;
      var10000.timerSpeed = 1.0F;
      return false;
   }

   @Listener
   public void onUpdate(UpdateEvent event) throws InterruptedException {
      System.currentTimeMillis();
      ++this.tickCounter;
      String var4 = this.mode.getMode();
      byte var5 = -1;
      switch(var4.hashCode()) {
      case -2096062211:
         if (var4.equals("Velocity")) {
            var5 = 0;
         }
         break;
      case -679467230:
         if (var4.equals("CustomFly")) {
            var5 = 2;
         }
         break;
      case -403347694:
         if (var4.equals("Collision")) {
            var5 = 1;
         }
         break;
      case 2026674138:
         if (var4.equals("Cris A")) {
            var5 = 3;
         }
         break;
      case 2026674139:
         if (var4.equals("Cris B")) {
            var5 = 4;
         }
      }

      byte var7;
      String var12;
      switch(var5) {
      case 0:
         var12 = this.velocityMode.getMode();
         var7 = -1;
         switch(var12.hashCode()) {
         case 66986:
            if (var12.equals("Bow")) {
               var7 = 0;
            }
         }

         switch(var7) {
         case 0:
            if (this.takingVelocity) {
               Flap.instance.getPacketBlinkHandler().stopAll();
               mc.thePlayer.motionY = this.velocityY;
               boolean sameXDir = this.lastMotionX > 0.01D && this.velocityX > 0.0D || this.lastMotionX < -0.01D && this.velocityX < 0.0D;
               boolean sameZDir = this.lastMotionZ > 0.01D && this.velocityZ > 0.0D || this.lastMotionZ < -0.01D && this.velocityZ < 0.0D;
               if (sameXDir && sameZDir) {
                  mc.thePlayer.motionX = this.velocityX;
                  mc.thePlayer.motionZ = this.velocityZ;
                  return;
               }
            }

            return;
         default:
            return;
         }
      case 1:
         var12 = this.collisionMode.getMode();
         var7 = -1;
         switch(var12.hashCode()) {
         case 672813144:
            if (var12.equals("Airjump")) {
               var7 = 1;
            }
            break;
         case 673181171:
            if (var12.equals("Airwalk")) {
               var7 = 0;
            }
         }

         switch(var7) {
         case 0:
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
               if (this.lastBarrier != null) {
                  mc.theWorld.setBlockToAir(this.lastBarrier);
               }

               mc.theWorld.setBlockState(pos, Blocks.stone.getDefaultState());
               this.lastBarrier = pos;
            }
            break;
         case 1:
            if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
               mc.thePlayer.jump();
            }

            if ((double)mc.thePlayer.fallDistance > (mc.gameSettings.keyBindJump.isKeyDown() ? 0.0D : 0.7D)) {
               if (this.lastBarrier != null) {
                  mc.theWorld.setBlockToAir(this.lastBarrier);
               }

               this.lastBarrier = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
               mc.theWorld.setBlockState(this.lastBarrier, Blocks.barrier.getDefaultState());
            }
         }
      case 2:
      default:
         break;
      case 3:
         if (mc.thePlayer.onGround) {
            if (alrweadsactived) {
               this.removeAllPlacedBlocks();
               this.toggle();
               if (((Disabler)Flap.instance.getModuleManager().getModule(Disabler.class)).isEnabled()) {
                  ((Disabler)Flap.instance.getModuleManager().getModule(Disabler.class)).toggle();
               }

               return;
            }

            alrweadsactived = true;
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.25D, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            mc.thePlayer.motionY = 0.7D;
         }

         double x = mc.thePlayer.posX;
         double y = mc.thePlayer.posY;
         double var10000 = mc.thePlayer.posZ;
         MovementUtil.strafe(0.45D);
         break;
      case 4:
         if (mc.thePlayer.onGround) {
            if (alrweadsactived) {
               this.toggle();
               if (((Disabler)Flap.instance.getModuleManager().getModule(Disabler.class)).isEnabled()) {
                  ((Disabler)Flap.instance.getModuleManager().getModule(Disabler.class)).toggle();
               }

               return;
            }

            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.25D, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 10.0D, mc.thePlayer.posZ);
            alrweadsactived = true;
         }

         this.ticks = 0;
         if (this.ticks == 20) {
            MovementUtil.strafe(0.0D);
            mc.thePlayer.motionY = 0.0D;
         } else {
            MovementUtil.strafe(0.6D);
            mc.thePlayer.motionY = -0.9D;
            ++this.ticks;
         }
      }

   }

   private float calculateFallDamage(float fallDistance) {
      return Math.min(fallDistance, 10.0F);
   }

   public String getInfo() {
      return this.mode.getMode();
   }

   private void simulateBlockBelowPlayer(double x, double y, double z) {
      World world = mc.theWorld;
      BlockPos pos = new BlockPos(x, y - 1.0D, z);
      Block block = Blocks.barrier;
      if (world.getBlockState(pos).getBlock() != block) {
         world.setBlockState(pos, block.getDefaultState());
         if (!this.placedBlocks.contains(pos)) {
            this.placedBlocks.add(pos);
         }
      }

   }

   public void removeAllPlacedBlocks() {
      World world = mc.theWorld;
      Iterator var2 = this.placedBlocks.iterator();

      while(var2.hasNext()) {
         BlockPos pos = (BlockPos)var2.next();
         world.setBlockState(pos, Blocks.air.getDefaultState());
      }

      this.placedBlocks.clear();
   }

   @Listener
   public void onMove(MoveEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2096062211:
         if (var2.equals("Velocity")) {
            var3 = 3;
         }
         break;
      case -599919172:
         if (var2.equals("Blocksmc")) {
            var3 = 4;
         }
         break;
      case 77115:
         if (var2.equals("NCP")) {
            var3 = 2;
         }
         break;
      case 1897755483:
         if (var2.equals("Vanilla")) {
            var3 = 0;
         }
         break;
      case 2026674140:
         if (var2.equals("Cris C")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         if (this.vanillaMode.getMode() == "Motion") {
            MovementUtil.strafe(event, this.vanillaSpeed.getValue());
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
               event.setY(this.vanillaVerticalSpeed.getValue());
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
               event.setY(-this.vanillaVerticalSpeed.getValue());
            } else {
               event.setY(0.0D);
            }

            mc.thePlayer.motionY = 0.0D;
         }

         if (this.vanillaMode.getMode() == "Creative") {
            mc.thePlayer.capabilities.isFlying = true;
         }
         break;
      case 1:
         if (mc.thePlayer.onGround) {
            event.setY(10.0D);
         }

         MovementUtil.strafe(event, 0.2D);
         if (this.tickCounter == 3) {
            event.setY(0.4D);
         } else if (this.tickCounter == 6) {
            event.setY(-0.4D);
         } else if (this.tickCounter == 9) {
            event.setY(0.4D);
         } else if (this.tickCounter == 12) {
            event.setY(-0.4D);
         } else if (this.tickCounter == 15) {
            event.setY(0.4D);
         } else if (this.tickCounter == 18) {
            event.setY(-0.4D);
         } else if (this.tickCounter == 20) {
            event.setY(0.4D);
            this.tickCounter = 0;
         }

         mc.thePlayer.motionY = 0.0D;
         break;
      case 2:
         String var4 = this.ncpMode.getMode();
         byte var5 = -1;
         switch(var4.hashCode()) {
         case 79367:
            if (var4.equals("Old")) {
               var5 = 0;
            }
         }

         switch(var5) {
         case 0:
            if (mc.thePlayer.onGround) {
               MovementUtil.jump(event);
               MovementUtil.strafe(event, 0.58D);
            } else {
               event.setY(mc.thePlayer.motionY = 1.0E-10D);
               if (!MovementUtil.isMoving() || mc.thePlayer.isCollidedHorizontally || this.speed < 0.28D) {
                  this.speed = 0.28D;
               }

               MovementUtil.strafe(event, this.speed);
               this.speed -= this.speed / 159.0D;
            }

            return;
         default:
            return;
         }
      case 3:
         if (!this.takingVelocity) {
            MovementUtil.strafe(this.speed);
         }
         break;
      case 4:
         if (this.automated.isEnabled()) {
            MovementUtil.strafe(this.speed);
         }
      }

   }

   @Listener
   public void onPreMotion(PreMotionEvent e) {
   }

   @Listener
   public void onPacketSend(PacketSendEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2096062211:
         if (var2.equals("Velocity")) {
            var3 = 0;
         }
         break;
      case 77115:
         if (var2.equals("NCP")) {
            var3 = 1;
         }
         break;
      case 2026674140:
         if (var2.equals("Cris C")) {
            var3 = 2;
         }
      }

      C03PacketPlayer packet;
      switch(var3) {
      case 0:
         if ((this.velocityMode.is("Bow") || this.velocityMode.is("Bow2")) && event.getPacket() instanceof C03PacketPlayer) {
            packet = (C03PacketPlayer)event.getPacket();
            packet.isOnGround();
         }
         break;
      case 1:
         if (this.ncpMode.is("Old") && this.damage.isEnabled() && event.getPacket() instanceof C0APacketAnimation) {
            event.setCancelled(true);
         }
         break;
      case 2:
         if (event.getPacket() instanceof C0APacketAnimation) {
            event.setCancelled(true);
            if (event.getPacket() instanceof C03PacketPlayer) {
               packet = (C03PacketPlayer)event.getPacket();
               packet.isOnGround();
            }
         }
      }

   }
}
