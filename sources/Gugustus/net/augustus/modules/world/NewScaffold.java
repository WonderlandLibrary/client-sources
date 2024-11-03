//package net.augustus.modules.world;
//
//import com.sun.javafx.geom.Vec2d;
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import net.augustus.Augustus;
//import net.augustus.events.EventClick;
//import net.augustus.events.EventEarlyTick;
//import net.augustus.events.EventJump;
//import net.augustus.events.EventMove;
//import net.augustus.events.EventRayCast;
//import net.augustus.events.EventRender3D;
//import net.augustus.events.EventSilent;
//import net.augustus.events.EventSilentMove;
//import net.augustus.modules.Categorys;
//import net.augustus.modules.Module;
//import net.augustus.settings.BooleanValue;
//import net.augustus.settings.DoubleValue;
//import net.augustus.settings.StringValue;
//import net.augustus.utils.RenderUtil;
//import net.augustus.utils.RotationUtil;
//import net.augustus.utils.TimeHelper;
//import net.lenni0451.eventapi.reflection.EventTarget;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockAir;
//import net.minecraft.block.BlockChest;
//import net.minecraft.block.BlockFurnace;
//import net.minecraft.block.BlockLiquid;
//import net.minecraft.block.material.Material;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.BlockPos;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import net.minecraft.util.Vec3;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.GL11;
//
//public class NewScaffold extends Module {
//   private final TimeHelper startTimeHelper = new TimeHelper();
//   private final TimeHelper startTimeHelper2 = new TimeHelper();
//   private final TimeHelper adTimeHelper = new TimeHelper();
//   private final RotationUtil rotationUtil;
//   private final double[] lastXYZ = new double[3];
//   private final HashMap<float[], MovingObjectPosition> hashMap = new HashMap<>();
//   public StringValue mode = new StringValue(8, "Mode", this, "Legit", new String[]{"Legit", "Basic"});
//   public DoubleValue yawSpeed = new DoubleValue(3, "YawSpeed", this, 40.0, 0.0, 180.0, 0);
//   public DoubleValue pitchSpeed = new DoubleValue(4, "PitchSpeed", this, 40.0, 0.0, 180.0, 0);
//   public BooleanValue moveFix = new BooleanValue(7, "MoveFix", this, true);
//   public BooleanValue esp = new BooleanValue(5, "ESP", this, true);
//   public BooleanValue adStrafe = new BooleanValue(11, "AdStrafe", this, true);
//   public StringValue silentMode = new StringValue(6, "SilentMode", this, "Spoof", new String[]{"Switch", "Spoof", "None"});
//   ArrayList<double[]> hitpoints = new ArrayList<>();
//   private float[] lastRots = new float[]{0.0F, 0.0F};
//   private float[] rots = new float[]{0.0F, 0.0F};
//   private int slotID;
//   private ItemStack block;
//   private int lastSlotID;
//   private EnumFacing enumFacing;
//   private BlockPos blockPos;
//   private boolean start = true;
//   private double[] xyz = new double[3];
//   private MovingObjectPosition objectPosition = null;
//
//   public NewScaffold() {
//      super("ScaffoldWalk", new Color(18, 35, 50), Categorys.WORLD);
//      this.rotationUtil = new RotationUtil();
//   }
//
//   @Override
//   public void onEnable() {
//      super.onEnable();
//      if (mc.thePlayer != null && mc.theWorld != null) {
//         this.restRotation();
//         this.slotID = mc.thePlayer.inventory.currentItem;
//         this.lastSlotID = mc.thePlayer.inventory.currentItem;
//         this.start = true;
//         this.startTimeHelper.reset();
//      }
//   }
//
//   @Override
//   public void onDisable() {
//      super.onDisable();
//      if (mc.thePlayer.inventory.currentItem != this.slotID) {
//      }
//
//      this.slotID = mc.thePlayer.inventory.currentItem;
//   }
//
//   @EventTarget
//   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
//      if (mc.thePlayer != null && mc.theWorld != null) {
//         this.blockPos = this.getAimBlockPos();
//         this.start = mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0 && mc.thePlayer.onGround || !this.startTimeHelper.reached(200L);
//         if (this.start) {
//            this.startTimeHelper2.reset();
//         }
//
//         if (this.blockPos != null) {
//            float[] floats = new float[]{1.0F, 1.0F};
//            String var3 = this.mode.getSelected();
//            switch(var3) {
//               case "Legit":
//                  floats = this.getNearestRotation();
//                  break;
//               case "Basic":
//                  floats = this.basicRotation();
//            }
//
//            this.lastRots = this.rots;
//            if (floats != null) {
//               this.rots = floats;
//            }
//
//            this.setRotation();
//         }
//      }
//   }
//
//   @EventTarget
//   public void onEventRayCastPost(EventRayCast eventRayCast) {
//      if (this.objectPosition != null) {
//         mc.objectMouseOver = this.objectPosition;
//      }
//   }
//
//   private float[] basicRotation() {
//      double x = mc.thePlayer.posX;
//      double z = mc.thePlayer.posZ;
//      double add1 = 1.05;
//      double add2 = 0.05;
//      this.xyz = new double[]{mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
//      double maX = (double)this.blockPos.getX() + add1;
//      double miX = (double)this.blockPos.getX() - add2;
//      double maZ = (double)this.blockPos.getZ() + add1;
//      double miZ = (double)this.blockPos.getZ() - add2;
//      if (!(x > maX) && !(x < miX) && !(z > maZ) && !(z < miZ)) {
//         return new float[]{mc.thePlayer.rotationYaw - 180.0F, this.rots[1]};
//      } else {
//         double[] ex = this.getAdvancedDiagonalExpandXZ(this.blockPos);
//         float[] f = this.rotationUtil
//            .scaffoldRots(
//               (double)this.blockPos.getX() + ex[0],
//               (double)this.blockPos.getY() + 0.85,
//               (double)this.blockPos.getZ() + ex[1],
//               this.lastRots[0],
//               this.lastRots[1],
//               (float)this.yawSpeed.getValue(),
//               (float)this.pitchSpeed.getValue(),
//               false
//            );
//         return new float[]{mc.thePlayer.rotationYaw - 180.0F, f[1]};
//      }
//   }
//
//   private float[] getNearestRotation() {
//      this.objectPosition = null;
//      float[] floats = this.rots;
//      BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
//      this.hashMap.clear();
//      if (this.start) {
//         float yaw = this.rotationUtil.rotateToYaw((float)this.yawSpeed.getValue(), this.rots[0], Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F);
//         RotationUtil.mouseSens(yaw, 80.34F, this.rots[0], this.rots[1]);
//         floats[1] = 80.34F;
//         floats[0] = yaw;
//      } else {
//         float yaww = Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F;
//         floats[0] = yaww;
//         double x = mc.thePlayer.posX;
//         double z = mc.thePlayer.posZ;
//         double add1 = 1.288;
//         double add2 = 0.288;
//         if (!this.buildForward()) {
//            x += mc.thePlayer.posX - this.xyz[0];
//            z += mc.thePlayer.posZ - this.xyz[2];
//         }
//
//         this.xyz = new double[]{mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
//         double maX = (double)this.blockPos.getX() + add1;
//         double miX = (double)this.blockPos.getX() - add2;
//         double maZ = (double)this.blockPos.getZ() + add1;
//         double miZ = (double)this.blockPos.getZ() - add2;
//         if (!(x > maX) && !(x < miX) && !(z > maZ) && !(z < miZ)) {
//            floats[1] = this.rots[1];
//         } else {
//            ArrayList<MovingObjectPosition> movingObjectPositions = new ArrayList<>();
//            ArrayList<Float> pitchs = new ArrayList<>();
//
//            for(float i = Math.max(this.rots[1] - 20.0F, -90.0F); i < Math.min(this.rots[1] + 20.0F, 90.0F); i += 0.05F) {
//               float[] f = RotationUtil.mouseSens(yaww, i, this.rots[0], this.rots[1]);
//               MovingObjectPosition m4 = mc.thePlayer.customRayTrace(4.5, 1.0F, yaww, f[1]);
//               if (m4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
//                  && this.isOkBlock(m4.getBlockPos())
//                  && !movingObjectPositions.contains(m4)
//                  && m4.getBlockPos().equalsBlockPos(this.blockPos)
//                  && m4.sideHit != EnumFacing.DOWN
//                  && m4.sideHit != EnumFacing.UP
//                  && m4.getBlockPos().getY() <= b.getY()) {
//                  movingObjectPositions.add(m4);
//                  this.hashMap.put(f, m4);
//                  pitchs.add(f[1]);
//               }
//            }
//
//            movingObjectPositions.sort(Comparator.comparingDouble(m -> mc.thePlayer.getDistanceSq(m.getBlockPos().add(0.5, 0.5, 0.5))));
//            MovingObjectPosition mm = null;
//            if (movingObjectPositions.size() > 0) {
//               mm = movingObjectPositions.get(0);
//            }
//
//            if (mm != null) {
//               floats[0] = yaww;
//               pitchs.sort(Comparator.comparingDouble(this::distanceToLastPitch));
//               if (!pitchs.isEmpty()) {
//                  floats[1] = pitchs.get(0);
//                  this.objectPosition = this.hashMap.get(floats);
//               }
//
//               return floats;
//            }
//         }
//      }
//
//      return floats;
//   }
//
//   private boolean canPlace(float[] yawPitch) {
//      BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
//      MovingObjectPosition m4 = mc.thePlayer.customRayTrace(4.5, 1.0F, yawPitch[0], yawPitch[1]);
//      if (m4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
//         && this.isOkBlock(m4.getBlockPos())
//         && m4.getBlockPos().equalsBlockPos(this.blockPos)
//         && m4.sideHit != EnumFacing.DOWN
//         && m4.sideHit != EnumFacing.UP
//         && m4.getBlockPos().getY() <= b.getY()) {
//         this.hashMap.put(yawPitch, m4);
//         return true;
//      } else {
//         return false;
//      }
//   }
//
//   private double distanceToLastRots(float[] predictRots) {
//      float diff1 = Math.abs(predictRots[0] - this.rots[0]);
//      float diff2 = Math.abs(predictRots[1] - this.rots[1]);
//      return (double)(diff1 * diff1 + diff2 * diff2);
//   }
//
//   private double distanceToLastPitch(float pitch) {
//      return (double)Math.abs(pitch - this.rots[1]);
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
//   private EnumFacing getPlaceSide() {
//      BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
//      if (playerPos.equalsBlockPos(this.blockPos)) {
//         System.out.println("Error");
//      }
//
//      if (playerPos.add(0, 1, 0).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.UP;
//      } else if (playerPos.add(0, -1, 0).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.DOWN;
//      } else if (playerPos.add(1, 0, 0).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.WEST;
//      } else if (playerPos.add(-1, 0, 0).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.EAST;
//      } else if (playerPos.add(0, 0, 1).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.NORTH;
//      } else if (playerPos.add(0, 0, -1).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.SOUTH;
//      } else if (playerPos.add(1, 0, 1).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.WEST;
//      } else if (playerPos.add(-1, 0, 1).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.EAST;
//      } else if (playerPos.add(-1, 0, 1).equalsBlockPos(this.blockPos)) {
//         return EnumFacing.NORTH;
//      } else {
//         return playerPos.add(-1, 0, -1).equalsBlockPos(this.blockPos) ? EnumFacing.SOUTH : null;
//      }
//   }
//
//   @EventTarget
//   public void onEventClick(EventClick eventClick) {
//      eventClick.setCanceled(true);
//      if (this.block == null) {
//         this.block = mc.thePlayer.inventory.getCurrentItem();
//      }
//
//      if (this.blockPos != null && mc.currentScreen == null) {
//         ItemStack lastItem = mc.thePlayer.inventory.getCurrentItem();
//         ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
//         if (!this.silentMode.getSelected().equals("None")) {
//            for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
//               ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
//               if (itemStack != null
//                  && itemStack.getItem() instanceof ItemBlock
//                  && itemStack.stackSize > 0
//                  && Augustus.getInstance().getBlockUtil().isValidStack(itemStack)) {
//                  this.block = itemStack;
//                  this.slotID = i - 36;
//                  break;
//               }
//            }
//
//            if (this.silentMode.getSelected().equals("Spoof") && this.lastSlotID != this.slotID) {
//            }
//
//            itemstack = mc.thePlayer.inventoryContainer.getSlot(this.slotID + 36).getStack();
//         } else {
//            this.slotID = mc.thePlayer.inventory.currentItem;
//            this.lastSlotID = mc.thePlayer.inventory.currentItem;
//         }
//
//         String var24 = this.mode.getSelected();
//         switch(var24) {
//            case "Basic":
//               double x = mc.thePlayer.posX;
//               double z = mc.thePlayer.posZ;
//               double add1 = 1.05;
//               double add2 = 0.05;
//               this.xyz = new double[]{mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ};
//               double maX = (double)this.blockPos.getX() + add1;
//               double miX = (double)this.blockPos.getX() - add2;
//               double maZ = (double)this.blockPos.getZ() + add1;
//               double miZ = (double)this.blockPos.getZ() - add2;
//               if (x > maX || x < miX || z > maZ || z < miZ) {
//                  if (this.silentMode.getSelected().equals("Switch")) {
//                     mc.thePlayer.inventory.setCurrentItem(this.block.getItem(), 0, false, false);
//                  }
//
//                  EnumFacing e = this.getPlaceSide();
//                  if (e != null) {
//                     double[] ex = this.getAdvancedDiagonalExpandXZ(this.blockPos);
//                     if (mc.playerController
//                        .onPlayerRightClick(
//                           mc.thePlayer,
//                           mc.theWorld,
//                           itemstack,
//                           this.blockPos,
//                           e,
//                           new Vec3((double)this.blockPos.getX() + ex[0], (double)this.blockPos.getY() - 0.5234234, (double)this.blockPos.getZ() + ex[1])
//                        )) {
//                        mc.thePlayer.swingItem();
//                     }
//                  }
//
//                  if (itemstack != null && itemstack.stackSize == 0) {
//                     mc.thePlayer.inventory.mainInventory[this.slotID] = null;
//                  }
//               }
//               break;
//            default:
//               MovingObjectPosition blockOver = mc.objectMouseOver;
//               if (blockOver != null) {
//                  BlockPos blockpos = blockOver.getBlockPos();
//                  if (blockOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
//                     && mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
//                     if (itemstack != null && !(itemstack.getItem() instanceof ItemBlock)) {
//                        return;
//                     }
//
//                     this.hitpoints.add(new double[]{blockOver.hitVec.xCoord, blockOver.hitVec.yCoord, blockOver.hitVec.zCoord});
//                     if (mc.thePlayer.posY < (double)blockpos.getY() + 1.5) {
//                        if (blockOver.sideHit != EnumFacing.UP && blockOver.sideHit != EnumFacing.DOWN) {
//                           if (this.silentMode.getSelected().equals("Switch")) {
//                              mc.thePlayer.inventory.setCurrentItem(this.block.getItem(), 0, false, false);
//                           }
//
//                           if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemstack, blockpos, blockOver.sideHit, blockOver.hitVec)) {
//                              mc.thePlayer.swingItem();
//                           }
//
//                           if (itemstack != null && itemstack.stackSize == 0) {
//                              mc.thePlayer.inventory.mainInventory[this.slotID] = null;
//                           }
//
//                           mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
//                        }
//                     } else if (blockOver.sideHit != EnumFacing.DOWN
//                        && blockOver.getBlockPos().equalsBlockPos(this.blockPos)
//                        && mc.gameSettings.keyBindJump.isKeyDown()) {
//                        if (this.silentMode.getSelected().equals("Switch")) {
//                           mc.thePlayer.inventory.setCurrentItem(this.block.getItem(), 0, false, false);
//                        }
//
//                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemstack, blockpos, blockOver.sideHit, blockOver.hitVec)) {
//                           mc.thePlayer.swingItem();
//                        }
//
//                        if (itemstack != null && itemstack.stackSize == 0) {
//                           mc.thePlayer.inventory.mainInventory[this.slotID] = null;
//                        }
//
//                        mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
//                     }
//                  }
//               }
//         }
//
//         if (lastItem != null && this.silentMode.getSelected().equals("Switch")) {
//            mc.thePlayer.inventory.setCurrentItem(lastItem.getItem(), 0, false, false);
//         }
//
//         this.lastSlotID = this.slotID;
//      }
//   }
//
//   @EventTarget
//   public void onEventSilent(EventSilent eventSilent) {
//      eventSilent.setSlotID(this.slotID);
//   }
//
//   @EventTarget
//   public void onEventSilentMove(EventSilentMove eventSilentMove) {
//      if (this.moveFix.getBoolean()) {
//         eventSilentMove.setSilent(true);
//      }
//
//      if (this.mode.getSelected().equals("Legit") && this.adStrafe.getBoolean()) {
//         BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
//         if (mc.theWorld.getBlockState(b).getBlock().getMaterial() == Material.air
//            && mc.currentScreen == null
//            && !Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCodeDefault())
//            && this.buildForward()
//            && mc.thePlayer.movementInput.moveForward != 0.0F) {
//            if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.EAST) {
//               if ((double)b.getZ() + 0.5 > mc.thePlayer.posZ) {
//                  mc.thePlayer.movementInput.moveStrafe = 1.0F;
//               } else {
//                  mc.thePlayer.movementInput.moveStrafe = -1.0F;
//               }
//            } else if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.WEST) {
//               if ((double)b.getZ() + 0.5 < mc.thePlayer.posZ) {
//                  mc.thePlayer.movementInput.moveStrafe = 1.0F;
//               } else {
//                  mc.thePlayer.movementInput.moveStrafe = -1.0F;
//               }
//            } else if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.SOUTH) {
//               if ((double)b.getX() + 0.5 < mc.thePlayer.posX) {
//                  mc.thePlayer.movementInput.moveStrafe = 1.0F;
//               } else {
//                  mc.thePlayer.movementInput.moveStrafe = -1.0F;
//               }
//            } else if ((double)b.getX() + 0.5 > mc.thePlayer.posX) {
//               mc.thePlayer.movementInput.moveStrafe = 1.0F;
//            } else {
//               mc.thePlayer.movementInput.moveStrafe = -1.0F;
//            }
//
//            this.adTimeHelper.reset();
//         }
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
//   @EventTarget
//   public void onEventJump(EventJump eventJump) {
//      if (!this.moveFix.getBoolean()) {
//         eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
//      }
//   }
//
//   private void setRotation() {
//      if (mc.currentScreen == null) {
//         mc.thePlayer.rotationYaw = this.rots[0];
//         mc.thePlayer.rotationPitch = this.rots[1];
//         mc.thePlayer.prevRotationYaw = this.lastRots[0];
//         mc.thePlayer.prevRotationPitch = this.lastRots[1];
//      }
//   }
//
//   private boolean buildForward() {
//      float realYaw = MathHelper.wrapAngleTo180_float(Augustus.getInstance().getYawPitchHelper().realYaw);
//      if ((double)realYaw > 77.5 && (double)realYaw < 102.5) {
//         return true;
//      } else if (!((double)realYaw > 167.5) && !(realYaw < -167.0F)) {
//         if ((double)realYaw < -77.5 && (double)realYaw > -102.5) {
//            return true;
//         } else {
//            return (double)realYaw > -12.5 && (double)realYaw < 12.5;
//         }
//      } else {
//         return true;
//      }
//   }
//
//   private BlockPos getAimBlockPos() {
//      BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//      if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround)
//         && mc.thePlayer.moveForward == 0.0F
//         && mc.thePlayer.moveStrafing == 0.0F
//         && this.isOkBlock(playerPos.add(0, -1, 0))) {
//         return playerPos.add(0, -1, 0);
//      } else {
//         BlockPos blockPos = null;
//         ArrayList<BlockPos> bp = this.getBlockPos();
//         ArrayList<BlockPos> blockPositions = new ArrayList<>();
//         if (bp.size() > 0) {
//            for(int i = 0; i < Math.min(bp.size(), 18); ++i) {
//               blockPositions.add(bp.get(i));
//            }
//
//            blockPositions.sort(Comparator.comparingDouble(this::getDistanceToBlockPos));
//            if (blockPositions.size() > 0) {
//               blockPos = blockPositions.get(0);
//            }
//         }
//
//         return blockPos;
//      }
//   }
//
//   private ArrayList<BlockPos> getBlockPos() {
//      BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
//      ArrayList<BlockPos> blockPoses = new ArrayList<>();
//
//      for(int x = playerPos.getX() - 2; x <= playerPos.getX() + 2; ++x) {
//         for(int y = playerPos.getY() - 1; y <= playerPos.getY(); ++y) {
//            for(int z = playerPos.getZ() - 2; z <= playerPos.getZ() + 2; ++z) {
//               if (this.isOkBlock(new BlockPos(x, y, z))) {
//                  blockPoses.add(new BlockPos(x, y, z));
//               }
//            }
//         }
//      }
//
//      if (!blockPoses.isEmpty()) {
//         blockPoses.sort(
//            Comparator.comparingDouble(
//               blockPos -> mc.thePlayer.getDistance((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5)
//            )
//         );
//      }
//
//      return blockPoses;
//   }
//
//   private double getDistanceToBlockPos(BlockPos blockPos) {
//      double distance = 1337.0;
//
//      for(float x = (float)blockPos.getX(); x <= (float)(blockPos.getX() + 1); x = (float)((double)x + 0.2)) {
//         for(float y = (float)blockPos.getY(); y <= (float)(blockPos.getY() + 1); y = (float)((double)y + 0.2)) {
//            for(float z = (float)blockPos.getZ(); z <= (float)(blockPos.getZ() + 1); z = (float)((double)z + 0.2)) {
//               double d0 = mc.thePlayer.getDistance((double)x, (double)y, (double)z);
//               if (d0 < distance) {
//                  distance = d0;
//               }
//            }
//         }
//      }
//
//      return distance;
//   }
//
//   private boolean isOkBlock(BlockPos blockPos) {
//      Block block = mc.theWorld.getBlockState(blockPos).getBlock();
//      return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
//   }
//
//   private void restRotation() {
//      this.rots[0] = Augustus.getInstance().getYawPitchHelper().realYaw;
//      this.rots[1] = Augustus.getInstance().getYawPitchHelper().realPitch;
//      this.lastRots[0] = Augustus.getInstance().getYawPitchHelper().realLastYaw;
//      this.lastRots[1] = Augustus.getInstance().getYawPitchHelper().realLastPitch;
//   }
//
//   @EventTarget
//   public void onEventRender3D(EventRender3D eventRender3D) {
//      if (this.esp.getBoolean()) {
//         GL11.glEnable(3042);
//         GL11.glBlendFunc(770, 771);
//         GL11.glEnable(2848);
//         GL11.glDisable(2929);
//         GL11.glDisable(3553);
//         GlStateManager.disableCull();
//         GL11.glDepthMask(false);
//         float red = 0.16470589F;
//         float green = 0.5686275F;
//         float blue = 0.96862745F;
//         float lineWidth = 0.0F;
//         if (this.blockPos != null) {
//            if (mc.thePlayer.getDistance((double)this.blockPos.getX(), (double)this.blockPos.getY(), (double)this.blockPos.getZ()) > 1.0) {
//               double d0 = 1.0 - mc.thePlayer.getDistance((double)this.blockPos.getX(), (double)this.blockPos.getY(), (double)this.blockPos.getZ()) / 20.0;
//               if (d0 < 0.3) {
//                  d0 = 0.3;
//               }
//
//               lineWidth = (float)((double)lineWidth * d0);
//            }
//
//            RenderUtil.drawBlockESP(this.blockPos, red, green, blue, 0.39215687F, 1.0F, lineWidth);
//         }
//
//         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//         GL11.glDepthMask(true);
//         GlStateManager.enableCull();
//         GL11.glEnable(3553);
//         GL11.glEnable(2929);
//         GL11.glDisable(3042);
//         GL11.glBlendFunc(770, 771);
//         GL11.glDisable(2848);
//      }
//   }
//
//   public int getSlotID() {
//      return this.slotID;
//   }
//}
