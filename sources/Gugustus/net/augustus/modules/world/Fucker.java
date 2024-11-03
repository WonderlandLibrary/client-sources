package net.augustus.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import net.augustus.Augustus;
import net.augustus.events.EventClick;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventSilentMove;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.RayTraceUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Fucker extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   public StringValue block = new StringValue(2, "Block", this, "Bed", new String[]{"Bed", "Cake", "Custom"});
   public StringValue action = new StringValue(3, "Action", this, "Break", new String[]{"Break", "Click", "Use"});
   public BooleanValue troughWall = new BooleanValue(1, "ThroughWall", this, true);
   public BooleanValue instant = new BooleanValue(10, "Instant", this, true);
   public BooleanValue moveFix = new BooleanValue(9, "MoveFix", this, true);
   public BooleanValue myBed = new BooleanValue(11, "FriendMyBlock", this, true);
   public DoubleValue customID = new DoubleValue(4, "CustomID", this, 26.0, 0.0, 400.0, 0);
   public DoubleValue yawSpeed = new DoubleValue(5, "YawSpeed", this, 180.0, 0.0, 180.0, 0);
   public DoubleValue pitchSpeed = new DoubleValue(6, "PitchSpeed", this, 180.0, 0.0, 180.0, 0);
   public DoubleValue delay = new DoubleValue(7, "Delay", this, 500.0, 0.0, 1000.0, 0);
   public DoubleValue range = new DoubleValue(8, "Range", this, 4.5, 0.0, 6.0, 1);
   public ArrayList<Packet> packets = new ArrayList<>();
   public BlockPos b;
   private RotationUtil rotationUtil = new RotationUtil();
   private float[] rots = new float[2];
   private float[] lastRots = new float[2];
   private EnumFacing lastEnumFacing;
   private int slotID;
   private boolean breaking = false;
   private INetHandler netHandler = null;

   public Fucker() {
      super("Fucker", new Color(161, 17, 51), Categorys.WORLD);
      this.customID.setVisible(false);
   }

   @Override
   public void onEnable() {
      this.rotationUtil = new RotationUtil();
      if (mc.thePlayer != null) {
         this.breaking = false;
         this.rots[0] = mc.thePlayer.rotationYaw;
         this.rots[1] = mc.thePlayer.rotationPitch;
         this.lastRots[0] = mc.thePlayer.prevRotationYaw;
         this.lastRots[1] = mc.thePlayer.prevRotationPitch;
         this.slotID = mc.thePlayer.inventory.currentItem + 36;
         this.b = null;
         this.lastEnumFacing = null;
      }
   }

   @Override
   public void onDisable() {
      if (this.slotID != mc.thePlayer.inventory.currentItem + 36) {
         this.slotID = mc.thePlayer.inventory.currentItem + 36;
      }

      if (this.breaking) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
         this.breaking = false;
      }

      this.resetPackets(this.netHandler);
   }

   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      this.setDisplayName(super.getName() + " §8" + this.block.getSelected());
      Vec3 bedVec = null;
      if ((!mm.killAura.isToggled() || mm.killAura.target == null) && !BlockUtil.isScaffoldToggled()) {
         bedVec = this.getBedPos();
         this.b = bedVec == null ? null : new BlockPos(bedVec);
      } else {
         this.b = null;
      }

      if (this.b == null) {
         this.lastRots = this.rots;
      } else {
         float yawSpeed = (float)(this.yawSpeed.getValue() + (double)RandomUtil.nextFloat(0.0F, 15.0F));
         float pitchSpeed = (float)(this.pitchSpeed.getValue() + (double)RandomUtil.nextFloat(0.0F, 15.0F));
         Block block = mc.theWorld.getBlockState(this.b).getBlock();
         float[] floats = this.rotationUtil
            .scaffoldRots(
               (double)this.b.getX() + block.getBlockBoundsMaxX() / 2.0,
               (double)this.b.getY() + block.getBlockBoundsMaxY() / 2.0,
               (double)this.b.getZ() + block.getBlockBoundsMaxZ() / 2.0,
               this.rots[0],
               this.rots[1],
               yawSpeed,
               pitchSpeed,
               false
            );
         MovingObjectPosition objectPosition = RayTraceUtil.rayCast(1.0F, floats);
         if (objectPosition != null
            && objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
            && !this.isValidBlock(objectPosition.getBlockPos(), this.getId())) {
            for(float yaw = this.rots[0] - Math.min(yawSpeed, 40.0F); yaw < this.rots[0] + Math.min(yawSpeed, 40.0F); yaw += 2.0F) {
               for(float pitch = MathHelper.clamp_float(this.rots[1] - Math.min(pitchSpeed, 30.0F), -90.0F, 90.0F);
                  pitch < MathHelper.clamp_float(this.rots[1] + Math.min(pitchSpeed, 30.0F), -90.0F, 90.0F);
                  pitch += 2.0F
               ) {
                  float[] sensedRots = RotationUtil.mouseSens(yaw, pitch, this.rots[0], this.rots[1]);
                  MovingObjectPosition objectPosition1 = RayTraceUtil.rayCast(1.0F, sensedRots);
                  if (objectPosition1 != null
                     && objectPosition1.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                     && this.isValidBlock(objectPosition1.getBlockPos(), this.getId())) {
                     this.lastRots = this.rots;
                     this.rots = sensedRots;
                     this.setRotation();
                     return;
                  }
               }
            }
         }

         this.lastRots = this.rots;
         this.rots = floats;
         this.setRotation();
      }
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      if ((!mm.killAura.isToggled() || mm.killAura.target == null) && !BlockUtil.isScaffoldToggled()) {
         boolean bb = false;
         if (this.b != null) {
            this.breaking = false;
            if (this.troughWall.getBoolean()) {
               eventClick.setCanceled(true);
               if (mc.thePlayer.isUsingItem()) {
                  mc.thePlayer.stopUsingItem();
                  mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
                  return;
               }

               this.slotID = mc.thePlayer.inventory.currentItem + 36;
               MovingObjectPosition movingObjectPosition = RayTraceUtil.getHitVec(this.b, this.rots[0], this.rots[1], this.range.getValue());
               if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                  String movingObjectPositionx = this.action.getSelected();
                  switch(movingObjectPositionx) {
                     case "Break":
                        if (this.instant.getBoolean()) {
                           this.breakInstant(this.b, movingObjectPosition.sideHit);
                        } else {
                           mc.sendClickBlockToControllerCustom(mc.currentScreen == null && mc.inGameHasFocus, movingObjectPosition, this.b, this.slotID - 36);
                           this.lastEnumFacing = movingObjectPosition.sideHit;
                        }
                        break;
                     case "Use":
                        if (this.timeHelper.reached((long)this.delay.getValue())
                           && mc.playerController
                              .onPlayerRightClick(
                                 mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.b, movingObjectPosition.sideHit, movingObjectPosition.hitVec
                              )) {
                           mc.thePlayer.swingItem();
                           this.timeHelper.reset();
                        }
                        break;
                     default:
                        if (this.timeHelper.reached((long)this.delay.getValue())) {
                           mc.thePlayer.swingItem();
                           mc.playerController.clickBlock(this.b, movingObjectPosition.sideHit);
                           this.timeHelper.reset();
                        }
                  }
               } else if (this.lastEnumFacing != null) {
                  mc.thePlayer
                     .sendQueue
                     .addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.b, this.lastEnumFacing));
               }
            } else {
               int bestItemID = this.getBestItem();
               if (bestItemID != -1) {
                  eventClick.setSlot(bestItemID - 36);
                  this.slotID = bestItemID;
               } else {
                  this.slotID = mc.thePlayer.inventory.currentItem + 36;
               }

               if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !mc.objectMouseOver.getBlockPos().equals(this.b)) {
                  KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                  this.breaking = true;
               } else {
                  MovingObjectPosition movingObjectPosition = mc.objectMouseOver;
                  if (movingObjectPosition != null) {
                     String var10 = this.action.getSelected();
                     switch(var10) {
                        case "Break":
                           if (this.instant.getBoolean()) {
                              this.breakInstant(this.b, movingObjectPosition.sideHit);
                           } else {
                              bb = true;
                              this.lastEnumFacing = movingObjectPosition.sideHit;
                              KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
                              this.breaking = true;
                           }
                           break;
                        case "Use":
                           if (this.timeHelper.reached((long)this.delay.getValue())
                              && mc.playerController
                                 .onPlayerRightClick(
                                    mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.b, movingObjectPosition.sideHit, movingObjectPosition.hitVec
                                 )) {
                              mc.thePlayer.swingItem();
                              this.timeHelper.reset();
                           }
                           break;
                        default:
                           if (this.timeHelper.reached((long)this.delay.getValue())) {
                              mc.thePlayer.swingItem();
                              mc.playerController.clickBlock(this.b, movingObjectPosition.sideHit);
                              this.timeHelper.reset();
                           }
                     }
                  }
               }
            }
         } else {
            if (this.breaking) {
               KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
               this.breaking = false;
            }

            this.slotID = mc.thePlayer.inventory.currentItem + 36;
         }
      } else {
         this.slotID = mc.thePlayer.inventory.currentItem + 36;
      }
   }

   @EventTarget
   public void onEventMove(EventMove eventMove) {
      if (!this.moveFix.getBoolean() && this.b != null) {
         eventMove.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
      }
   }

   @EventTarget
   public void onEventJump(EventJump eventJump) {
      if (!this.moveFix.getBoolean() && this.b != null) {
         eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
      }
   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      if (this.moveFix.getBoolean() && this.b != null) {
         eventSilentMove.setSilent(true);
      }
   }

   private int getBestItem() {
      float maxStrength = 0.0F;
      int bestItem = -1;

      for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
         ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (itemStack != null && itemStack.getItem() instanceof ItemTool && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            if (this.getToolSpeed(itemStack, block) > maxStrength) {
               maxStrength = this.getToolSpeed(itemStack, block);
               bestItem = i;
            }
         }
      }

      return bestItem;
   }

   private float getToolSpeed(ItemStack itemStack, Block block) {
      float damage = 0.0F;
      if (itemStack.getItem() instanceof ItemTool) {
         damage += itemStack.getItem().getStrVsBlock(itemStack, block)
            + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
         damage = (float)((double)damage + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0);
         damage = (float)((double)damage + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) / 11.0);
         damage = (float)((double)damage + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) / 33.0);
      }

      return damage;
   }

   private void setRotation() {
      if (mc.currentScreen == null) {
         mc.thePlayer.rotationYaw = this.rots[0];
         mc.thePlayer.rotationPitch = this.rots[1];
         mc.thePlayer.prevRotationYaw = this.lastRots[0];
         mc.thePlayer.prevRotationPitch = this.lastRots[1];
      }
   }

   private Vec3 getBedPos() {
      BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
      ArrayList<Vec3> positions = new ArrayList<>();
      HashMap<Vec3, BlockPos> map = new HashMap<>();
      int d = (int)(this.range.getValue() + 1.0);

      for(int x = b.getX() - d; x < b.getX() + d; ++x) {
         for(int y = b.getY() - d; y < b.getY() + d; ++y) {
            for(int z = b.getZ() - d; z < b.getZ() + d; ++z) {
               if (this.isValidBlock(new BlockPos(x, y, z), this.getId())) {
                  BlockPos blockPos = new BlockPos(x, y, z);
                  Vec3 vec3 = new Vec3((double)x, (double)y, (double)z);
                  if (this.myBed.getBoolean() && mc.thePlayer.getSpawnPos() != null) {
                     if (mc.thePlayer.getSpawnPos().distanceTo(vec3) > 20.0) {
                        positions.add(vec3);
                        map.put(vec3, blockPos);
                     }
                  } else {
                     positions.add(vec3);
                     map.put(vec3, blockPos);
                  }
               }
            }
         }
      }

      positions.sort(Comparator.comparingDouble(vec3x -> mc.thePlayer.getDistance(vec3x.xCoord, vec3x.yCoord, vec3x.zCoord)));
      return !positions.isEmpty() ? positions.get(0) : null;
   }

   private int getId() {
      String var2 = this.block.getSelected();
      int id;
      switch(var2) {
         case "Bed":
            id = 26;
            break;
         case "Cake":
            id = 92;
            break;
         default:
            id = (int)this.customID.getValue();
      }

      return id;
   }

   private boolean isValidBlock(BlockPos blockPos, int id) {
      if (blockPos != null) {
         Block block = mc.theWorld.getBlockState(blockPos).getBlock();
         return block.equals(Block.getBlockById(id));
      } else {
         return false;
      }
   }

   private void breakInstant(BlockPos blockPos, EnumFacing enumFacing) {
      if (this.timeHelper.reached((long)this.delay.getValue())) {
         mc.thePlayer.swingItem();
         mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, enumFacing));
         mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, enumFacing));
         this.timeHelper.reset();
      }
   }

   public int getSlotID() {
      return this.slotID - 36;
   }

   public void resetPackets(INetHandler iNetHandler) {
      if (this.packets.size() > 0) {
         for(; this.packets.size() != 0; this.packets.remove(this.packets.get(0))) {
            try {
               this.packets.get(0).processPacket(iNetHandler);
            } catch (ThreadQuickExitException var3) {
            }
         }
      }
   }
}
