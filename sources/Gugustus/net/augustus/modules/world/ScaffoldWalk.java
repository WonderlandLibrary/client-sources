//package net.augustus.modules.world;
//
//import com.sun.javafx.geom.Vec2d;
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Comparator;
//import net.augustus.Augustus;
//import net.augustus.events.EventClick;
//import net.augustus.events.EventEarlyTick;
//import net.augustus.events.EventJump;
//import net.augustus.events.EventMove;
//import net.augustus.events.EventSaveWalk;
//import net.augustus.events.EventSilentMove;
//import net.augustus.modules.Categorys;
//import net.augustus.modules.Module;
//import net.augustus.settings.BooleanValue;
//import net.augustus.settings.DoubleValue;
//import net.augustus.settings.StringValue;
//import net.augustus.utils.RandomUtil;
//import net.augustus.utils.RotationUtil;
//import net.augustus.utils.TimeHelper;
//import net.lenni0451.eventapi.reflection.EventTarget;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockAir;
//import net.minecraft.block.BlockChest;
//import net.minecraft.block.BlockFurnace;
//import net.minecraft.block.BlockLiquid;
//import net.minecraft.block.material.Material;
//import net.minecraft.client.settings.KeyBinding;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.BlockPos;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import org.lwjgl.input.Keyboard;
//
//public class ScaffoldWalk extends Module {
//   private final TimeHelper timeHelper = new TimeHelper();
//   private final TimeHelper timeHelper2 = new TimeHelper();
//   public StringValue mode = new StringValue(1, "Modes", this, "Intave", new String[]{"Normal", "Legit", "Intave"});
//   public BooleanValue adStrafe = new BooleanValue(5, "ADStrafe", this, false);
//   public BooleanValue moveFix = new BooleanValue(6, "MoveFix", this, true);
//   public DoubleValue yawSpeed = new DoubleValue(3, "YawSpeed", this, 40.0, 0.0, 180.0, 0);
//   public DoubleValue pitchSpeed = new DoubleValue(4, "PitchSpeed", this, 40.0, 0.0, 180.0, 0);
//   public StringValue silentMode = new StringValue(6, "SilentMode", this, "Spoof", new String[]{"Switch", "Spoof", "None"});
//   private float[] lastRots = new float[]{0.0F, 0.0F};
//   private float[] rots = new float[]{0.0F, 0.0F};
//   private BlockPos b;
//   private BlockPos lastB;
//   private RotationUtil rotationUtil;
//   private boolean rotated;
//   private int slotID;
//   private ItemStack block;
//   private int lastSlotID;
//   private long lastTime = 0L;
//   private EnumFacing enumFacing;
//
//   public ScaffoldWalk() {
//      super("OldScaffold", new Color(18, 35, 50, 255), Categorys.WORLD);
//      this.rotationUtil = new RotationUtil();
//   }
//
//   @Override
//   public void onEnable() {
//      if (mc.thePlayer != null) {
//         this.rotationUtil = new RotationUtil();
//         this.rots[0] = mc.thePlayer.rotationYaw;
//         this.rots[1] = mc.thePlayer.rotationPitch;
//         this.lastRots[0] = mc.thePlayer.prevRotationYaw;
//         this.lastRots[1] = mc.thePlayer.prevRotationPitch;
//         this.slotID = mc.thePlayer.inventory.currentItem;
//         this.lastSlotID = mc.thePlayer.inventory.currentItem;
//      }
//   }
//
//   @Override
//   public void onDisable() {
//      KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
//      KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
//      if (mc.thePlayer.inventory.currentItem != this.slotID) {
//      }
//
//      this.slotID = mc.thePlayer.inventory.currentItem;
//   }
//
//   @EventTarget
//   public void onEventSilentMove(EventSilentMove eventSilentMove) {
//      if (this.mode.getSelected().equals("Legit") && this.b != null) {
//         BlockPos bb = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//         if (mc.theWorld.getBlockState(bb).getBlock().getMaterial() == Material.air
//            && mc.currentScreen == null
//            && !Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCodeDefault())
//            && this.adStrafe.getBoolean()) {
//            if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.EAST) {
//               if ((double)this.b.getZ() + 0.5 > mc.thePlayer.posZ) {
//                  mc.thePlayer.movementInput.moveStrafe = 1.0F;
//               } else {
//                  mc.thePlayer.movementInput.moveStrafe = -1.0F;
//               }
//            } else if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.WEST) {
//               if ((double)this.b.getZ() + 0.5 < mc.thePlayer.posZ) {
//                  mc.thePlayer.movementInput.moveStrafe = 1.0F;
//               } else {
//                  mc.thePlayer.movementInput.moveStrafe = -1.0F;
//               }
//            } else if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.SOUTH) {
//               if ((double)this.b.getX() + 0.5 < mc.thePlayer.posX) {
//                  mc.thePlayer.movementInput.moveStrafe = 1.0F;
//               } else {
//                  mc.thePlayer.movementInput.moveStrafe = -1.0F;
//               }
//            } else if ((double)this.b.getX() + 0.5 > mc.thePlayer.posX) {
//               mc.thePlayer.movementInput.moveStrafe = 1.0F;
//            } else {
//               mc.thePlayer.movementInput.moveStrafe = -1.0F;
//            }
//
//            this.timeHelper.reset();
//         } else {
//            KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
//            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
//         }
//      }
//
//      if (this.moveFix.getBoolean()) {
//         eventSilentMove.setSilent(true);
//      }
//   }
//
//   @EventTarget
//   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
//      this.rotated = false;
//      this.b = this.getBlockPos();
//      if (this.b == null) {
//         this.rots[1] = this.rotationUtil.rotateToPitch((float)this.pitchSpeed.getValue(), this.rots[1], 85.34F + RandomUtil.nextFloat(-0.1F, 0.1F));
//         this.lastRots = this.rots;
//         this.setRotation();
//      } else {
//         String var2 = this.mode.getSelected();
//         switch(var2) {
//            case "Legit":
//               float realYawwww = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
//               double[] ex = this.getAdvancedDiagonalExpandXZ(this.b);
//               double g = 0.85;
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward != 0.0F) {
//                  g = 0.5;
//               }
//
//               float[] f = this.rotationUtil
//                  .scaffoldRots(
//                     (double)this.b.getX() + ex[0],
//                     (double)this.b.getY() + g,
//                     (double)this.b.getZ() + ex[1],
//                     this.lastRots[0],
//                     this.lastRots[1],
//                     (float)this.yawSpeed.getValue(),
//                     (float)this.pitchSpeed.getValue(),
//                     false
//                  );
//               if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.onGround && mc.thePlayer.moveForward != 0.0F) {
//                  this.rots[1] = f[1];
//               } else {
//                  this.rots[1] = this.rotationUtil.rotateToPitch((float)this.pitchSpeed.getValue(), this.rots[1], 80.34F + RandomUtil.nextFloat(-0.1F, 0.1F));
//               }
//
//               this.rots[0] = f[0];
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F
//                  )
//                {
//                  this.rots = this.rotationUtil
//                     .scaffoldRots(
//                        (double)this.b.getX() + 0.5,
//                        (double)this.b.getY(),
//                        (double)this.b.getZ() + 0.5,
//                        this.lastRots[0],
//                        this.lastRots[1],
//                        (float)this.yawSpeed.getValue(),
//                        (float)this.pitchSpeed.getValue(),
//                        true
//                     );
//               }
//
//               BlockPos bb = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//               if (mc.theWorld.getBlockState(bb).getBlock().getMaterial() == Material.air
//                  && mc.currentScreen == null
//                  && !Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCodeDefault())
//                  && !this.adStrafe.getBoolean()
//                  && mc.thePlayer.moveForward < 0.0F) {
//                  if (this.enumFacing == EnumFacing.EAST) {
//                     mc.thePlayer.motionX = 0.09505206927498006;
//                  } else if (this.enumFacing == EnumFacing.WEST) {
//                     mc.thePlayer.motionX = -0.09505206927498006;
//                  } else if (this.enumFacing == EnumFacing.SOUTH) {
//                     mc.thePlayer.motionZ = 0.09505206927498006;
//                  } else {
//                     mc.thePlayer.motionZ = -0.09505206927498006;
//                  }
//               }
//
//               this.rots = RotationUtil.mouseSens(this.rots[0], this.rots[1], this.lastRots[0], this.lastRots[1]);
//               this.setRotation();
//               this.lastRots = this.rots;
//               break;
//            case "Normal":
//               float realYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
//               double[] expandXZ = this.getAdvancedDiagonalExpandXZ(this.b);
//               double p = 0.85;
//               if ((
//                     (double)realYaw > 22.5 && (double)realYaw < 67.5
//                        || (double)realYaw > 112.5 && (double)realYaw < 157.5
//                        || (double)realYaw < -22.5 && (double)realYaw > -67.5
//                        || (double)realYaw < -112.5 && realYaw > -157.0F
//                  )
//                  && (mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround)
//                  && mc.thePlayer.moveForward != 0.0F) {
//                  if ((double)(this.b.getY() + 1) < mc.thePlayer.posY) {
//                     p = 1.3;
//                  } else {
//                     p = 0.8;
//                  }
//               } else if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.onGround && mc.thePlayer.moveForward != 0.0F) {
//                  p = 0.1;
//               }
//
//               float[] floats = this.rotationUtil
//                  .scaffoldRots(
//                     (double)this.b.getX() + expandXZ[0],
//                     (double)this.b.getY() + p,
//                     (double)this.b.getZ() + expandXZ[1],
//                     this.lastRots[0],
//                     this.lastRots[1],
//                     (float)this.yawSpeed.getValue(),
//                     (float)this.pitchSpeed.getValue(),
//                     false
//                  );
//               BlockPos bbb = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//               if (mc.theWorld.getBlockState(bbb).getBlock().getMaterial() == Material.air) {
//                  this.rots = floats;
//               }
//
//               this.rots[0] = floats[0];
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F
//                  )
//                {
//                  this.rots = this.rotationUtil
//                     .scaffoldRots(
//                        (double)this.b.getX() + 0.5,
//                        (double)this.b.getY(),
//                        (double)this.b.getZ() + 0.5,
//                        this.lastRots[0],
//                        this.lastRots[1],
//                        (float)this.yawSpeed.getValue(),
//                        (float)this.pitchSpeed.getValue(),
//                        true
//                     );
//               }
//
//               if ((double)realYaw > 22.5 && (double)realYaw < 67.5) {
//                  this.rots[0] = this.rotationUtil.rotateToYaw((float)this.yawSpeed.getValue(), this.rots[0], 225.53F + RandomUtil.nextFloat(-0.1F, 0.1F));
//                  if (mc.thePlayer.onGround) {
//                     this.getYawBasedPitch(this.rots[0]);
//                  }
//               } else if ((double)realYaw > 112.5 && (double)realYaw < 157.5) {
//                  this.rots[0] = this.rotationUtil.rotateToYaw((float)this.yawSpeed.getValue(), this.rots[0], 315.28F + RandomUtil.nextFloat(-0.1F, 0.1F));
//                  if (mc.thePlayer.onGround) {
//                     this.getYawBasedPitch(this.rots[0]);
//                  }
//               } else if ((double)realYaw < -22.5 && (double)realYaw > -67.5) {
//                  this.rots[0] = this.rotationUtil.rotateToYaw((float)this.yawSpeed.getValue(), this.rots[0], 135.36F + RandomUtil.nextFloat(-0.1F, 0.1F));
//                  if (mc.thePlayer.onGround) {
//                     this.getYawBasedPitch(this.rots[0]);
//                  }
//               } else if ((double)realYaw < -112.5 && realYaw > -157.0F) {
//                  this.rots[0] = this.rotationUtil.rotateToYaw((float)this.yawSpeed.getValue(), this.rots[0], 45.19F + RandomUtil.nextFloat(-0.1F, 0.1F));
//                  if (mc.thePlayer.onGround) {
//                     this.getYawBasedPitch(this.rots[0]);
//                  }
//               }
//
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F
//                  )
//                {
//                  this.rots[1] = this.rotationUtil
//                     .rotateToPitch((float)this.pitchSpeed.getValue(), this.lastRots[1], 88.0F + RandomUtil.nextFloat(-1.0F, 1.0F));
//                  this.rots[0] = this.lastRots[0];
//               }
//
//               this.rots = RotationUtil.mouseSens(this.rots[0], this.rots[1], this.lastRots[0], this.lastRots[1]);
//               this.setRotation();
//               this.lastRots = this.rots;
//               break;
//            case "Intave":
//               double[] expandXZ2 = this.getAdvancedDiagonalExpandXZ(this.b);
//               double pp = 0.85;
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward != 0.0F) {
//                  pp = -0.2;
//               }
//
//               float[] floatss = this.rotationUtil
//                  .scaffoldRots(
//                     (double)this.b.getX() + expandXZ2[0],
//                     (double)this.b.getY() + pp,
//                     (double)this.b.getZ() + expandXZ2[1],
//                     this.lastRots[0],
//                     this.lastRots[1],
//                     (float)this.yawSpeed.getValue(),
//                     (float)this.pitchSpeed.getValue(),
//                     false
//                  );
//               BlockPos bbbb = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//               if (mc.theWorld.getBlockState(bbbb).getBlock().getMaterial() == Material.air && mc.thePlayer.hurtTime == 0) {
//                  this.rots = floatss;
//               } else {
//                  this.rots[1] = this.lastRots[1];
//               }
//
//               this.rots[0] = floatss[0];
//               float yaw = Augustus.getInstance().getYawPitchHelper().realYaw - 181.78935F;
//               this.getYawBasedPitch(yaw);
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F
//                  )
//                {
//                  this.rots[1] = this.rotationUtil
//                     .rotateToPitch((float)this.pitchSpeed.getValue(), this.lastRots[1], 88.0F + RandomUtil.nextFloat(-1.0F, 1.0F));
//                  this.rots[0] = this.lastRots[0];
//               }
//
//               this.rots = RotationUtil.mouseSens(this.rots[0], this.rots[1], this.lastRots[0], this.lastRots[1]);
//               this.setRotation();
//               this.lastRots = this.rots;
//               this.lastB = this.b;
//         }
//      }
//   }
//
//   @EventTarget
//   public void onEventClick(EventClick eventClick) {
//      eventClick.setCanceled(true);
//      boolean flag = true;
//      if (this.block == null) {
//         this.block = mc.thePlayer.inventory.getCurrentItem();
//      }
//
//      if (this.b != null && mc.currentScreen == null) {
//         if (this.rotated) {
//            ItemStack lastItem = mc.thePlayer.inventory.getCurrentItem();
//            ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
//            if (!this.silentMode.getSelected().equals("None")) {
//               for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
//                  ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
//                  if (itemStack != null
//                     && itemStack.getItem() instanceof ItemBlock
//                     && itemStack.stackSize > 0
//                     && Augustus.getInstance().getBlockUtil().isValidStack(itemStack)) {
//                     this.block = itemStack;
//                     this.slotID = i - 36;
//                     break;
//                  }
//               }
//
//               if (this.silentMode.getSelected().equals("Spoof") && this.lastSlotID != this.slotID) {
//               }
//
//               itemstack = mc.thePlayer.inventoryContainer.getSlot(this.slotID + 36).getStack();
//            } else {
//               this.slotID = mc.thePlayer.inventory.currentItem;
//               this.lastSlotID = mc.thePlayer.inventory.currentItem;
//            }
//
//            MovingObjectPosition blockOver = mc.objectMouseOver;
//            BlockPos blockpos = blockOver.getBlockPos();
//            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
//               && mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air
//               && !mc.playerController.func_181040_m()) {
//               if (itemstack != null && !(itemstack.getItem() instanceof ItemBlock)) {
//                  return;
//               }
//
//               if (mc.thePlayer.posY < (double)(blockpos.getY() + 2)) {
//                  if (blockOver.sideHit != EnumFacing.UP && blockOver.sideHit != EnumFacing.DOWN) {
//                     if (this.silentMode.getSelected().equals("Switch")) {
//                        mc.thePlayer.inventory.setCurrentItem(this.block.getItem(), 0, false, false);
//                     }
//
//                     if (mc.playerController
//                        .onPlayerRightClick(mc.thePlayer, mc.theWorld, itemstack, blockpos, mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec)) {
//                        mc.thePlayer.swingItem();
//                        this.lastTime = System.currentTimeMillis();
//                        flag = false;
//                        this.timeHelper.reset();
//                     }
//
//                     if (itemstack != null && itemstack.stackSize == 0) {
//                        mc.thePlayer.inventory.mainInventory[this.slotID] = null;
//                     }
//
//                     mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
//                  }
//               } else if (blockOver.sideHit != EnumFacing.DOWN) {
//                  if (this.silentMode.getSelected().equals("Switch")) {
//                     mc.thePlayer.inventory.setCurrentItem(this.block.getItem(), 0, false, false);
//                  }
//
//                  if (mc.playerController
//                     .onPlayerRightClick(mc.thePlayer, mc.theWorld, itemstack, blockpos, mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec)) {
//                     mc.thePlayer.swingItem();
//                     this.lastTime = System.currentTimeMillis();
//                     flag = false;
//                  }
//
//                  if (itemstack != null && itemstack.stackSize == 0) {
//                     mc.thePlayer.inventory.mainInventory[this.slotID] = null;
//                  }
//
//                  mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
//               }
//            }
//
//            if (lastItem != null && this.silentMode.getSelected().equals("Switch")) {
//               mc.thePlayer.inventory.setCurrentItem(lastItem.getItem(), 0, false, false);
//            }
//
//            this.lastSlotID = this.slotID;
//            if (flag && itemstack != null && !this.silentMode.getSelected().equals("Switch")) {
//            }
//         }
//      }
//   }
//
//   @EventTarget
//   public void onEventSaveWalk(EventSaveWalk eventSaveWalk) {
//      if (mc.thePlayer.onGround) {
//      }
//   }
//
//   @EventTarget
//   public void onEventJump(EventJump eventJump) {
//      if (!this.moveFix.getBoolean()) {
//         eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
//      }
//   }
//
//   @EventTarget
//   public void onEventMove(EventMove eventMove) {
//      if (!this.moveFix.getBoolean()) {
//         eventMove.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
//      }
//   }
//
//   private boolean buildForward() {
//      float realYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
//      if ((!((double)realYaw > 77.5) || !((double)realYaw < 102.5))
//         && !((double)realYaw > 167.5)
//         && !(realYaw < -167.0F)
//         && (!((double)realYaw < -77.5) || !((double)realYaw > -102.5))
//         && (double)realYaw > -12.5
//         && (double)realYaw < 12.5) {
//      }
//
//      return true;
//   }
//
//   private void setRotation() {
//      if (mc.currentScreen == null) {
//         mc.thePlayer.rotationYaw = this.rots[0];
//         mc.thePlayer.rotationPitch = this.rots[1];
//         mc.thePlayer.prevRotationYaw = this.lastRots[0];
//         mc.thePlayer.prevRotationPitch = this.lastRots[1];
//         this.rotated = true;
//      }
//   }
//
//   private void getYawBasedPitch(float yaw) {
//      ArrayList<MovingObjectPosition> movingObjectPositions = new ArrayList<>();
//      ArrayList<MovingObjectPosition> movingObjectPositions2 = new ArrayList<>();
//      double d = 2.5;
//      if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
//         d = 3.5;
//      }
//
//      BlockPos bbbb = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//
//      for(float i = 40.0F; i < 89.0F; i += 0.01F) {
//         MovingObjectPosition m = mc.thePlayer.customRayTrace(d, 1.0F, yaw, i);
//         if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.isOkBlock(m.getBlockPos()) && !movingObjectPositions.contains(m)) {
//            if (m.sideHit != EnumFacing.DOWN && m.sideHit != EnumFacing.UP && m.getBlockPos().getY() <= bbbb.getY()) {
//               movingObjectPositions.add(m);
//            }
//
//            movingObjectPositions2.add(m);
//         }
//      }
//
//      movingObjectPositions.sort(Comparator.comparingDouble(mx -> mc.thePlayer.getDistanceSq(mx.getBlockPos().add(0.5, 0.5, 0.5))));
//      MovingObjectPosition m = null;
//      if (movingObjectPositions.size() > 0) {
//         m = movingObjectPositions.get(0);
//      }
//
//      if (mc.theWorld.getBlockState(bbbb).getBlock().getMaterial() == Material.air && m != null && mc.thePlayer.hurtTime == 0) {
//         MovingObjectPosition objectPosition = mc.thePlayer.customRayTrace(d, 1.0F, yaw, this.lastRots[1]);
//         if (objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
//            && this.isOkBlock(m.getBlockPos())
//            && objectPosition.sideHit != EnumFacing.DOWN
//            && objectPosition.sideHit != EnumFacing.UP
//            && objectPosition.getBlockPos().getY() <= bbbb.getY()) {
//            this.rots[1] = this.lastRots[1];
//         } else {
//            float[] f = this.rotationUtil
//               .scaffoldRots(
//                  m.hitVec.xCoord,
//                  (double)m.getBlockPos().getY() + RandomUtil.nextDouble(0.45, 0.55),
//                  m.hitVec.zCoord,
//                  this.lastRots[0],
//                  this.lastRots[1],
//                  (float)this.yawSpeed.getValue(),
//                  (float)this.pitchSpeed.getValue(),
//                  false
//               );
//            this.rots[1] = f[1];
//         }
//      }
//
//      if (movingObjectPositions2.size() != 0 || mc.thePlayer.hurtTime > 0 || mc.thePlayer.onGround) {
//         this.rots[0] = yaw;
//      }
//   }
//
//   private double[] getAdvancedDiagonalExpandXZ(BlockPos blockPos) {
//      double[] xz = new double[2];
//      Vec2d difference = new Vec2d((double)blockPos.getX() - mc.thePlayer.posX, (double)blockPos.getZ() - mc.thePlayer.posZ);
//      if (difference.x > -1.0 && difference.x < 0.0 && difference.y < -1.0) {
//         this.enumFacing = EnumFacing.SOUTH;
//         xz[0] = difference.x * -1.0;
//         xz[1] = 1.0;
//      }
//
//      if (difference.y < 0.0 && difference.y > -1.0 && difference.x < -1.0) {
//         this.enumFacing = EnumFacing.EAST;
//         xz[0] = 1.0;
//         xz[1] = difference.y * -1.0;
//      }
//
//      if (difference.x > -1.0 && difference.x < 0.0 && difference.y > 0.0) {
//         this.enumFacing = EnumFacing.NORTH;
//         xz[0] = difference.x * -1.0;
//         xz[1] = 0.0;
//      }
//
//      if (difference.y < 0.0 && difference.y > -1.0 && difference.x > 0.0) {
//         this.enumFacing = EnumFacing.WEST;
//         xz[0] = 0.0;
//         xz[1] = difference.y * -1.0;
//         this.enumFacing = EnumFacing.WEST;
//      }
//
//      if (difference.x >= 0.0 && difference.y < -1.0) {
//         xz[1] = 1.0;
//      }
//
//      if (difference.y >= 0.0 & difference.x < -1.0) {
//         xz[0] = 1.0;
//      }
//
//      if (difference.x >= 0.0 && difference.y > 0.0) {
//      }
//
//      if (difference.y <= -1.0 && difference.x < -1.0) {
//         xz[0] = 1.0;
//         xz[1] = 1.0;
//      }
//
//      return xz;
//   }
//
//   private BlockPos getBlockPos() {
//      BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//      if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround)
//         && mc.thePlayer.moveForward == 0.0F
//         && mc.thePlayer.moveStrafing == 0.0F
//         && this.isOkBlock(blockPos.add(0, -1, 0))) {
//         return blockPos.add(0, -1, 0);
//      } else {
//         switch(mc.getRenderViewEntity().getHorizontalFacing()) {
//            case EAST:
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround)
//                  && mc.thePlayer.moveForward == 0.0F
//                  && mc.thePlayer.moveStrafing == 0.0F
//                  && this.isOkBlock(blockPos.add(0, -1, 0))) {
//                  return blockPos.add(0, -1, 0);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, 0))) {
//                  return blockPos.add(-1, 0, 0);
//               } else if (this.isOkBlock(blockPos.add(1, 0, 0))) {
//                  return blockPos.add(1, 0, 0);
//               } else if (this.isOkBlock(blockPos.add(0, 0, 1))) {
//                  return blockPos.add(0, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(0, 0, -1))) {
//                  return blockPos.add(0, 0, -1);
//               } else if (this.isOkBlock(blockPos.add(1, 0, 1))) {
//                  return blockPos.add(1, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, -1))) {
//                  return blockPos.add(-1, 0, -1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, 1))) {
//                  return blockPos.add(-1, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(1, 0, -1))) {
//                  return blockPos.add(1, 0, -1);
//               } else {
//                  if (!mc.thePlayer.onGround) {
//                     if (this.isOkBlock(blockPos.add(-1, -1, 0))) {
//                        return blockPos.add(-1, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, 0))) {
//                        return blockPos.add(1, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, 1))) {
//                        return blockPos.add(0, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, -1))) {
//                        return blockPos.add(0, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, 1))) {
//                        return blockPos.add(1, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, -1))) {
//                        return blockPos.add(-1, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, 1))) {
//                        return blockPos.add(-1, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(2, -1, -2))) {
//                        return blockPos.add(2, -1, -2);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-2, -1, 0))) {
//                        return blockPos.add(-2, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(2, -1, 0))) {
//                        return blockPos.add(2, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, 2))) {
//                        return blockPos.add(0, -1, 2);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, -2))) {
//                        return blockPos.add(0, -1, -2);
//                     }
//                  }
//
//                  System.out.println("No Block found");
//                  return null;
//               }
//            case SOUTH:
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround)
//                  && mc.thePlayer.moveForward == 0.0F
//                  && mc.thePlayer.moveStrafing == 0.0F
//                  && this.isOkBlock(blockPos.add(0, -1, 0))) {
//                  return blockPos.add(0, -1, 0);
//               } else if (this.isOkBlock(blockPos.add(0, 0, -1))) {
//                  return blockPos.add(0, 0, -1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, 0))) {
//                  return blockPos.add(-1, 0, 0);
//               } else if (this.isOkBlock(blockPos.add(1, 0, 0))) {
//                  return blockPos.add(1, 0, 0);
//               } else if (this.isOkBlock(blockPos.add(0, 0, 1))) {
//                  return blockPos.add(0, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(1, 0, 1))) {
//                  return blockPos.add(1, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, -1))) {
//                  return blockPos.add(-1, 0, -1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, 1))) {
//                  return blockPos.add(-1, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(1, 0, -1))) {
//                  return blockPos.add(1, 0, -1);
//               } else {
//                  if (!mc.thePlayer.onGround) {
//                     if (this.isOkBlock(blockPos.add(0, -1, -1))) {
//                        return blockPos.add(0, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, 0))) {
//                        return blockPos.add(-1, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, 0))) {
//                        return blockPos.add(1, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, 1))) {
//                        return blockPos.add(0, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, 1))) {
//                        return blockPos.add(1, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, -1))) {
//                        return blockPos.add(-1, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, 1))) {
//                        return blockPos.add(-1, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, -1))) {
//                        return blockPos.add(1, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, -2))) {
//                        return blockPos.add(0, -1, -2);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-2, -1, 0))) {
//                        return blockPos.add(-2, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(2, -1, 0))) {
//                        return blockPos.add(2, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, 2))) {
//                        return blockPos.add(0, -1, 2);
//                     }
//                  }
//
//                  System.out.println("No Block found");
//                  return null;
//               }
//            case WEST:
//               if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround)
//                  && mc.thePlayer.moveForward == 0.0F
//                  && mc.thePlayer.moveStrafing == 0.0F
//                  && this.isOkBlock(blockPos.add(0, -1, 0))) {
//                  return blockPos.add(0, -1, 0);
//               } else if (this.isOkBlock(blockPos.add(1, 0, 0))) {
//                  return blockPos.add(1, 0, 0);
//               } else if (this.isOkBlock(blockPos.add(0, 0, 1))) {
//                  return blockPos.add(0, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, 0))) {
//                  return blockPos.add(-1, 0, 0);
//               } else if (this.isOkBlock(blockPos.add(0, 0, -1))) {
//                  return blockPos.add(0, 0, -1);
//               } else if (this.isOkBlock(blockPos.add(1, 0, 1))) {
//                  return blockPos.add(1, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, -1))) {
//                  return blockPos.add(-1, 0, -1);
//               } else if (this.isOkBlock(blockPos.add(-1, 0, 1))) {
//                  return blockPos.add(-1, 0, 1);
//               } else if (this.isOkBlock(blockPos.add(1, 0, -1))) {
//                  return blockPos.add(1, 0, -1);
//               } else {
//                  if (!mc.thePlayer.onGround) {
//                     if (this.isOkBlock(blockPos.add(1, -1, 0))) {
//                        return blockPos.add(1, 0, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, 1))) {
//                        return blockPos.add(0, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, 0))) {
//                        return blockPos.add(-1, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, -1))) {
//                        return blockPos.add(0, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, 1))) {
//                        return blockPos.add(1, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, -1))) {
//                        return blockPos.add(-1, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-1, -1, 1))) {
//                        return blockPos.add(-1, -1, 1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(1, -1, -1))) {
//                        return blockPos.add(1, -1, -1);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(2, -1, 0))) {
//                        return blockPos.add(2, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, 2))) {
//                        return blockPos.add(0, -1, 2);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(-2, -1, 0))) {
//                        return blockPos.add(-2, -1, 0);
//                     }
//
//                     if (this.isOkBlock(blockPos.add(0, -1, -2))) {
//                        return blockPos.add(0, -1, -2);
//                     }
//                  }
//               }
//         }
//      }
//      System.out.println("No Block found");
//      return null;
//   }
//
//   private boolean isOkBlock(BlockPos blockPos) {
//      Block block = mc.theWorld.getBlockState(blockPos).getBlock();
//      return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
//   }
//
//   public int getSlotID() {
//      return this.slotID;
//   }
//}
