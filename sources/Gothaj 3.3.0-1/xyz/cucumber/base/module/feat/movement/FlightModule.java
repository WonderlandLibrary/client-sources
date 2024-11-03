package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventGround;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMove;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.player.DisablerModule;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to fly",
   name = "Flight",
   key = 0
)
public class FlightModule extends Mod {
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Verus", "Vulcan Jump", "BlocksMC", "Intave Boat", "Grim", "Mush mc"});
   public NumberSettings speed = new NumberSettings("Speed", 0.5, 0.1, 10.0, 0.01);
   public Timer timer = new Timer();
   public int flyTicks;
   public int posY;
   public double startY;
   public boolean started;
   public boolean done;
   public BlockPos blockPos;
   public EnumFacing facing;
   private List<Packet> inPackets = new ArrayList<>();
   private List<Packet> outPackets = new ArrayList<>();

   public FlightModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.speed});
   }

   @Override
   public void onEnable() {
      this.startY = this.mc.thePlayer.posY;
      this.flyTicks = 0;
      this.started = false;
      this.done = false;
      this.timer.reset();
      String var1;
      switch ((var1 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -925584629:
            if (var1.equals("vulcan jump") && !this.mc.thePlayer.onGround) {
               this.toggle();
               return;
            }
         default:
            this.mc.gameSettings.keyBindSneak.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode());
      }
   }

   @Override
   public void onDisable() {
      this.mc.thePlayer.isDead = false;
      this.mc.thePlayer.speedInAir = 0.02F;
      this.mc.timer.timerSpeed = 1.0F;
      String var1;
      switch ((var1 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1525012097:
            if (var1.equals("intave boat")) {
               for (int i = 0; i < 3; i++) {
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(
                        new C03PacketPlayer.C06PacketPlayerPosLook(
                           this.mc.thePlayer.posX - Math.sin(Math.toRadians((double)this.mc.thePlayer.rotationYaw)) * 8.0,
                           this.mc.thePlayer.posY + 0.1,
                           this.mc.thePlayer.posZ + Math.cos(Math.toRadians((double)this.mc.thePlayer.rotationYaw)) * 8.0,
                           this.mc.thePlayer.rotationYaw,
                           this.mc.thePlayer.rotationPitch,
                           true
                        )
                     );
               }
            }
            break;
         case 112097665:
            if (var1.equals("verus")) {
               MovementUtils.strafe(0.0F);
            }
      }

      if (this.outPackets.size() > 0) {
         for (Packet p : this.outPackets) {
            this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
         }

         this.outPackets.clear();
      }

      this.mc.gameSettings.keyBindSneak.pressed = false;
      this.mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onGameLoop(EventGameLoop e) {
      this.mode.getMode().toLowerCase().hashCode();
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onGround(EventGround e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case 3181391:
            if (var2.equals("grim")) {
               if (InventoryUtils.getBlockSlot(false) == -1) {
                  return;
               }

               if (this.mc.thePlayer.posY <= this.startY) {
                  e.setOnGround(true);
               }
            }
            break;
         case 1412601497:
            if (var2.equals("mush mc") && this.mc.thePlayer.posY <= this.startY) {
               e.setOnGround(true);
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      if (e.getType() == EventType.PRE) {
         this.setInfo(this.mode.getMode());
         String var2;
         switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -925584629:
               if (var2.equals("vulcan jump")) {
                  if (this.flyTicks <= 5) {
                     this.mc.thePlayer.motionX = 0.0;
                     this.mc.thePlayer.motionY = 0.0;
                     this.mc.thePlayer.motionZ = 0.0;
                     if (this.done) {
                        if (this.flyTicks == 0) {
                           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 1) {
                           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 2) {
                           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 3) {
                           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 4.0, this.mc.thePlayer.posZ);
                        } else if (this.flyTicks == 4) {
                           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.0, this.mc.thePlayer.posZ);
                        }
                     }

                     this.flyTicks++;
                  } else {
                     if (this.mc.thePlayer.fallDistance > 2.5F) {
                        e.setOnGround(true);
                        this.mc.thePlayer.fallDistance = 0.0F;
                     }

                     if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                        this.mc.thePlayer.motionY = -0.1;
                     }

                     if (this.mc.thePlayer.onGround) {
                        this.toggle();
                     }
                  }

                  if (!this.done && this.mc.thePlayer.onGround) {
                     this.mc.thePlayer.motionX = 0.0;
                     this.mc.thePlayer.motionZ = 0.0;
                     e.setY(e.getY() - 0.1);
                     this.done = true;
                     this.flyTicks = 0;
                  }
               }
               break;
            case -664563300:
               if (var2.equals("blocksmc")) {
                  this.mc.thePlayer.motionY = 0.0;
                  if (MovementUtils.isMoving()) {
                     MovementUtils.strafe(0.55F);
                  }
               }
               break;
            case 3181391:
               if (var2.equals("grim")) {
                  int slot = InventoryUtils.getBlockSlot(false);
                  if (slot == -1) {
                     return;
                  }

                  if (this.mc.thePlayer.posY <= this.startY) {
                     this.mc.thePlayer.jump();
                     e.setOnGround(true);
                     e.setPitch(90.0F);
                     BlockPos hitPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
                     EnumFacing facing = EnumFacing.DOWN;
                     Vec3 hitVec = this.mc.objectMouseOver.hitVec;
                     float f = (float)(hitVec.xCoord - (double)hitPos.getX());
                     float f1 = (float)(hitVec.yCoord - (double)hitPos.getY());
                     float f2 = (float)(hitVec.zCoord - (double)hitPos.getZ());
                     int oldSlot = this.mc.thePlayer.inventory.currentItem;
                     this.mc.thePlayer.inventory.currentItem = slot;
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getHeldItem()));
                     this.mc.thePlayer.inventory.currentItem = oldSlot;
                  }
               }
               break;
            case 112097665:
               if (var2.equals("verus")) {
                  if (this.mc.thePlayer.hurtTime > 0) {
                     this.done = true;
                  }

                  if (this.done) {
                     this.mc.timer.timerSpeed = 1.0F;
                     this.mc.gameSettings.keyBindForward.pressed = true;
                     this.mc
                        .getNetHandler()
                        .getNetworkManager()
                        .sendPacketNoEvent(
                           new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0F, 0.5F, 0.0F)
                        );
                     this.mc
                        .getNetHandler()
                        .getNetworkManager()
                        .sendPacketNoEvent(
                           new C08PacketPlayerBlockPlacement(
                              new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.5, this.mc.thePlayer.posZ),
                              1,
                              new ItemStack(Blocks.stone.getItem(this.mc.theWorld, new BlockPos(-1, -1, -1))),
                              0.0F,
                              0.94F,
                              0.0F
                           )
                        );
                     MovementUtils.strafe(
                        (float)(
                           this.timer.getTime() <= (long)(Client.INSTANCE.getModuleManager().getModule(DisablerModule.class).isEnabled() ? 10000 : 1000)
                              ? this.speed.getValue()
                              : MovementUtils.getBaseMoveSpeed() * 1.3F
                        )
                     );
                     if (this.timer.getTime() <= 250L) {
                        MovementUtils.strafe(0.0F);
                     }

                     if (this.mc.thePlayer.fallDistance > 0.0F) {
                        this.mc.thePlayer.motionY = 0.0;
                     }

                     if (this.mc.gameSettings.keyBindJump.pressed) {
                        this.mc.thePlayer.motionY = 0.2;
                     } else if (this.mc.gameSettings.keyBindSneak.pressed) {
                        this.mc.thePlayer.motionY = -0.2;
                     }
                  }
               }
               break;
            case 233102203:
               if (var2.equals("vanilla")) {
                  this.mc.thePlayer.motionY = this.mc.gameSettings.keyBindJump.pressed
                     ? this.speed.getValue() / 2.0
                     : (this.mc.gameSettings.keyBindSneak.pressed ? -(this.speed.getValue() / 2.0) : 0.0);
                  this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = 0.0;
                  if (MovementUtils.isMoving()) {
                     MovementUtils.strafe((float)this.speed.getValue());
                  }
               }
               break;
            case 1412601497:
               if (var2.equals("mush mc")) {
                  if (this.mc.thePlayer.hurtTime == 0) {
                     this.mc
                        .getNetHandler()
                        .getNetworkManager()
                        .sendPacketNoEvent(
                           new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.0001, this.mc.thePlayer.posZ, false)
                        );
                     this.mc
                        .getNetHandler()
                        .getNetworkManager()
                        .sendPacketNoEvent(
                           new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false)
                        );
                     this.mc
                        .getNetHandler()
                        .getNetworkManager()
                        .sendPacketNoEvent(
                           new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true)
                        );
                  } else {
                     if (this.mc.gameSettings.keyBindSneak.pressed) {
                        return;
                     }

                     if (this.mc.thePlayer.posY <= this.startY) {
                        this.mc.thePlayer.jump();
                        e.setOnGround(true);
                        return;
                     }

                     if (this.mc.gameSettings.keyBindJump.pressed) {
                        this.mc.thePlayer.jump();
                        e.setOnGround(true);
                     }
                  }
               }
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMove(EventMove e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -925584629:
            if (!var2.equals("vulcan jump")) {
            }
            break;
         case 112097665:
            if (var2.equals("verus") && this.flyTicks <= 3) {
               e.setCancelled(true);
            }
            break;
         case 233102203:
            if (!var2.equals("vanilla")) {
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveButton(EventMoveButton e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -925584629:
            if (!var2.equals("vulcan jump")) {
            }
            break;
         case 112097665:
            if (var2.equals("verus")) {
               e.forward = true;
            }
            break;
         case 233102203:
            if (!var2.equals("vanilla")) {
            }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onSendPacket(EventSendPacket e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -925584629:
            if (!var2.equals("vulcan jump")) {
            }
            break;
         case -664563300:
            if (var2.equals("blocksmc")) {
               e.setCancelled(true);
               this.outPackets.add(e.getPacket());
            }
            break;
         case 112097665:
            if (var2.equals("verus") && e.getPacket() instanceof C03PacketPlayer && !this.started) {
               if (this.flyTicks <= 3) {
                  this.flyTicks++;
                  e.setCancelled(true);
               } else {
                  this.timer.reset();
                  this.started = true;
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0F, 0.5F, 0.0F));
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(
                        new C08PacketPlayerBlockPlacement(
                           new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.5, this.mc.thePlayer.posZ),
                           1,
                           new ItemStack(Blocks.stone.getItem(this.mc.theWorld, new BlockPos(-1, -1, -1))),
                           0.0F,
                           0.94F,
                           0.0F
                        )
                     );
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(
                        new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 3.0001, this.mc.thePlayer.posZ, false)
                     );
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(
                        new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false)
                     );
                  this.mc
                     .getNetHandler()
                     .getNetworkManager()
                     .sendPacketNoEvent(
                        new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true)
                     );
               }
            }
            break;
         case 233102203:
            if (!var2.equals("vanilla")) {
            }
      }
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -925584629:
            if (!var2.equals("vulcan jump")) {
            }
            break;
         case 112097665:
            if (!var2.equals("verus")) {
            }
            break;
         case 233102203:
            if (!var2.equals("vanilla")) {
            }
      }
   }

   public void processBlockData() {
      this.posY = (int)(this.mc.thePlayer.posY - 1.0);
      new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
      this.blockPos = this.getBlockPos(this.mc.thePlayer.posX, (double)this.posY, this.mc.thePlayer.posZ);
      if (this.blockPos != null) {
         this.facing = this.getPlaceSide(this.mc.thePlayer.posX, (double)this.posY, this.mc.thePlayer.posZ);
      }
   }

   private BlockPos getBlockPos(double posX, double posY, double posZ) {
      BlockPos playerPos = new BlockPos(posX, posY, posZ);
      ArrayList<Vec3> positions = new ArrayList<>();
      HashMap<Vec3, BlockPos> hashMap = new HashMap<>();

      for (int y = playerPos.getY() - 1; y <= playerPos.getY(); y++) {
         for (int x = playerPos.getX() - 5; x <= playerPos.getX() + 5; x++) {
            for (int z = playerPos.getZ() - 5; z <= playerPos.getZ() + 5; z++) {
               if (isValidBock(new BlockPos(x, y, z))) {
                  BlockPos blockPos = new BlockPos(x, y, z);
                  Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
                  double ex = MathHelper.clamp_double(posX, (double)blockPos.getX(), (double)blockPos.getX() + block.getBlockBoundsMaxX());
                  double ey = MathHelper.clamp_double(posY + 1.0, (double)blockPos.getY(), (double)blockPos.getY() + block.getBlockBoundsMaxY());
                  double ez = MathHelper.clamp_double(posZ, (double)blockPos.getZ(), (double)blockPos.getZ() + block.getBlockBoundsMaxZ());
                  Vec3 vec3 = new Vec3(ex, ey, ez);
                  positions.add(vec3);
                  hashMap.put(vec3, blockPos);
               }
            }
         }
      }

      if (positions.isEmpty()) {
         return null;
      } else {
         positions.sort(Comparator.comparingDouble(this::getBestBlock));
         return hashMap.get(positions.get(0));
      }
   }

   private EnumFacing getPlaceSide(double posX, double posY, double posZ) {
      ArrayList<Vec3> positions = new ArrayList<>();
      HashMap<Vec3, EnumFacing> hashMap = new HashMap<>();
      BlockPos playerPos = new BlockPos(posX, posY + 1.0, posZ);
      if (!isPosSolid(this.blockPos.add(0, 1, 0)) && !this.blockPos.add(0, 1, 0).equals(playerPos) && !this.mc.thePlayer.onGround) {
         new BlockPos(posX, posY, posZ);
         BlockPos bp = this.blockPos.add(0, 1, 0);
         Vec3 vec4 = this.getBestHitFeet(bp);
         positions.add(vec4);
         hashMap.put(vec4, EnumFacing.UP);
      }

      if (!isPosSolid(this.blockPos.add(1, 0, 0)) && !this.blockPos.add(1, 0, 0).equals(playerPos)) {
         BlockPos bp = this.blockPos.add(1, 0, 0);
         Vec3 vec4 = this.getBestHitFeet(bp);
         positions.add(vec4);
         hashMap.put(vec4, EnumFacing.EAST);
      }

      if (!isPosSolid(this.blockPos.add(-1, 0, 0)) && !this.blockPos.add(-1, 0, 0).equals(playerPos)) {
         BlockPos bp = this.blockPos.add(-1, 0, 0);
         Vec3 vec4 = this.getBestHitFeet(bp);
         positions.add(vec4);
         hashMap.put(vec4, EnumFacing.WEST);
      }

      if (!isPosSolid(this.blockPos.add(0, 0, 1)) && !this.blockPos.add(0, 0, 1).equals(playerPos)) {
         BlockPos bp = this.blockPos.add(0, 0, 1);
         Vec3 vec4 = this.getBestHitFeet(bp);
         positions.add(vec4);
         hashMap.put(vec4, EnumFacing.SOUTH);
      }

      if (!isPosSolid(this.blockPos.add(0, 0, -1)) && !this.blockPos.add(0, 0, -1).equals(playerPos)) {
         BlockPos bp = this.blockPos.add(0, 0, -1);
         Vec3 vec4 = this.getBestHitFeet(bp);
         positions.add(vec4);
         hashMap.put(vec4, EnumFacing.NORTH);
      }

      positions.sort(Comparator.comparingDouble(vec3 -> this.mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord)));
      if (!positions.isEmpty()) {
         Vec3 vec5 = this.getBestHitFeet(this.blockPos);
         if (this.mc.thePlayer.getDistance(vec5.xCoord, vec5.yCoord, vec5.zCoord)
            >= this.mc.thePlayer.getDistance(positions.get(0).xCoord, positions.get(0).yCoord, positions.get(0).zCoord)) {
            return hashMap.get(positions.get(0));
         }
      }

      return null;
   }

   private Vec3 getBestHitFeet(BlockPos blockPos) {
      Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
      double ex = MathHelper.clamp_double(this.mc.thePlayer.posX, (double)blockPos.getX(), (double)blockPos.getX() + block.getBlockBoundsMaxX());
      double ey = MathHelper.clamp_double(this.mc.thePlayer.posY, (double)blockPos.getY(), (double)blockPos.getY() + block.getBlockBoundsMaxY());
      double ez = MathHelper.clamp_double(this.mc.thePlayer.posZ, (double)blockPos.getZ(), (double)blockPos.getZ() + block.getBlockBoundsMaxZ());
      return new Vec3(ex, ey, ez);
   }

   public static boolean isPosSolid(BlockPos pos) {
      Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
      return (
            block.getMaterial().isSolid()
               || !block.isTranslucent()
               || block instanceof BlockLadder
               || block instanceof BlockCarpet
               || block instanceof BlockSnow
               || block instanceof BlockSkull
         )
         && !block.getMaterial().isLiquid()
         && !(block instanceof BlockContainer);
   }

   public static boolean isValidBock(BlockPos blockPos) {
      Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
      return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
   }

   private double getBestBlock(Vec3 vec3) {
      return this.mc.thePlayer.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord);
   }
}
