package vestige.module.impl.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.PreMotionEvent;
import vestige.event.impl.RenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.PlayerUtil;
import vestige.util.player.RotationsUtil;
import vestige.util.render.RenderUtil;
import vestige.util.util.Utils;
import vestige.util.world.BlockUtils;

public class BreakerV2 extends Module {
   private final ModeSetting breakMode = new ModeSetting("Break mode", "Legit", new String[]{"Legit", "Instant", "Swap"});
   private final IntegerSetting breakSpeed = new IntegerSetting("Break speed", 1, 0, 2, 0);
   private final IntegerSetting fov = new IntegerSetting("FOV", 360, 30, 360, 4);
   private final IntegerSetting range = new IntegerSetting("Range", 4, 1, 8, 0);
   private final IntegerSetting rate = new IntegerSetting("Rate Second", 0, 0, 3, 0);
   private final BooleanSetting allowAura = new BooleanSetting("Allow aura", true);
   private final BooleanSetting breakNearBlock = new BooleanSetting("Break near block", false);
   private final BooleanSetting cancelKnockback = new BooleanSetting("Cancel knockback", false);
   private final BooleanSetting disableBHop = new BooleanSetting("Disable bhop", false);
   private final BooleanSetting disableBreakEffects = new BooleanSetting("Disable break effects", false);
   private final BooleanSetting groundSpoof = new BooleanSetting("Ground spoof", false);
   private final BooleanSetting ignoreSlow = new BooleanSetting("Ignore slow", false);
   private final BooleanSetting onlyWhileVisible = new BooleanSetting("Only while visible", false);
   private final BooleanSetting renderOutline = new BooleanSetting("Render block outline", true);
   private final BooleanSetting sendAnimations = new BooleanSetting("Send animations", false);
   private final BooleanSetting silentSwing = new BooleanSetting("Silent swing", false);
   private BlockPos[] bedPos;
   public float breakProgress;
   private int currentSlot = -1;
   private int lastSlot = -1;
   public boolean rotate;
   public BlockPos currentBlock;
   private long lastCheck = 0L;
   public boolean stopAutoblock;
   private int outlineColor = (new Color(226, 65, 65)).getRGB();
   private int breakTickDelay = 2;
   private int ticksAfterBreak = 0;
   private boolean delayStart;
   private BlockPos nearestBlock;
   private Map<BlockPos, Float> breakProgressMap = new HashMap();
   public double lastProgress;
   public float vanillaProgress;
   private int defaultOutlineColor = (new Color(226, 65, 65)).getRGB();
   private boolean aiming;

   public BreakerV2() {
      super("Breaker", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.breakMode, this.breakSpeed, this.fov, this.range, this.rate, this.allowAura, this.breakNearBlock, this.cancelKnockback, this.disableBHop, this.disableBreakEffects, this.groundSpoof, this.ignoreSlow, this.onlyWhileVisible, this.renderOutline, this.sendAnimations, this.silentSwing});
   }

   public void onEnable() {
   }

   @Listener
   public void onPreUpdate(PreMotionEvent event) {
      if (PlayerUtil.nullCheck()) {
         if (!Utils.isBedwarsPractice()) {
            if (mc.thePlayer.capabilities.allowEdit && !mc.thePlayer.isSpectator()) {
               if (this.bedPos == null) {
                  if (System.currentTimeMillis() - this.lastCheck >= (long)(this.rate.getValue() * 1000)) {
                     this.lastCheck = System.currentTimeMillis();
                     this.bedPos = this.getBedPos();
                  }

                  if (this.bedPos == null) {
                     this.reset(true);
                     return;
                  }
               } else if (!(BlockUtils.getBlock(this.bedPos[0]) instanceof BlockBed) || this.currentBlock != null && BlockUtils.replaceable(this.currentBlock)) {
                  this.reset(true);
                  return;
               }

               if (this.delayStart) {
                  if (this.ticksAfterBreak++ <= this.breakTickDelay) {
                     if (this.currentSlot != -1 && this.currentSlot != mc.thePlayer.inventory.currentItem) {
                        this.stopAutoblock = true;
                        this.delayStart = true;
                     }

                     return;
                  }

                  if (this.currentSlot != -1 && this.currentSlot != mc.thePlayer.inventory.currentItem) {
                     this.stopAutoblock = true;
                     this.delayStart = true;
                  }

                  this.resetSlot();
                  this.delayStart = false;
                  this.ticksAfterBreak = 0;
               } else {
                  this.ticksAfterBreak = 0;
               }

               if (this.breakNearBlock.isEnabled() && this.isCovered(this.bedPos[0]) && this.isCovered(this.bedPos[1])) {
                  if (this.nearestBlock == null) {
                     this.nearestBlock = this.getBestBlock(this.bedPos, true);
                  }

                  this.breakBlock(this.nearestBlock);
               } else {
                  this.nearestBlock = null;
                  this.resetSlot();
                  this.breakBlock(this.getBestBlock(this.bedPos, false) != null ? this.getBestBlock(this.bedPos, false) : this.bedPos[0]);
               }

            } else {
               this.reset(true);
            }
         }
      }
   }

   @Listener
   public void onReceivePacket(PacketReceiveEvent e) {
      if (PlayerUtil.nullCheck() && this.cancelKnockback.isEnabled() && this.currentBlock != null) {
         if (e.getPacket() instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity)e.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
               e.setCancelled(true);
            }
         } else if (e.getPacket() instanceof S27PacketExplosion) {
            e.setCancelled(true);
         }

      }
   }

   @Listener
   private void onPreMotion(PreMotionEvent e) {
      if ((this.rotate || this.breakProgress >= 1.0F || this.breakProgress == 0.0F) && this.currentBlock != null) {
         float[] rotations = RotationsUtil.getRotations(this.currentBlock, e.getYaw(), e.getPitch());
         if (!RotationsUtil.inRange(this.currentBlock, (double)this.range.getValue())) {
            return;
         }

         e.setYaw(rotations[0]);
         e.setPitch(rotations[1]);
         this.rotate = false;
         if (this.groundSpoof.isEnabled() && !mc.thePlayer.isInWater()) {
            e.setOnGround(true);
         }

         this.aiming = true;
      }

   }

   @Listener
   public void onRenderWorld(RenderEvent e) {
      if (this.renderOutline.isEnabled() && this.currentBlock != null && PlayerUtil.nullCheck()) {
         RenderUtil.renderBlock(this.currentBlock, this.outlineColor, Arrays.asList(this.bedPos).contains(this.currentBlock) ? 0.5625D : 1.0D, true, false);
      }
   }

   public boolean onDisable() {
      return false;
   }

   private void resetSlot() {
      if (this.currentSlot != -1 && this.currentSlot != mc.thePlayer.inventory.currentItem && this.breakMode.getMode() == "Swap") {
         this.stopAutoblock = true;
         this.delayStart = true;
         this.setPacketSlot(mc.thePlayer.inventory.currentItem);
      } else if (this.lastSlot != -1) {
         this.lastSlot = mc.thePlayer.inventory.currentItem = this.lastSlot;
      }

   }

   public boolean cancelKnockback() {
      return this.isEnabled() && this.currentBlock != null && this.cancelKnockback.isEnabled();
   }

   private BlockPos[] getBedPos() {
      int range;
      label39:
      for(int n = range = this.range.getValue(); range >= -n; --range) {
         for(int j = -n; j <= n; ++j) {
            for(int k = -n; k <= n; ++k) {
               BlockPos blockPos = new BlockPos(mc.thePlayer.posX + (double)j, mc.thePlayer.posY + (double)range, mc.thePlayer.posZ + (double)k);
               IBlockState getBlockState = mc.theWorld.getBlockState(blockPos);
               if (getBlockState.getBlock() == Blocks.bed && getBlockState.getValue(BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                  float fov = (float)this.fov.getValue();
                  if (fov == 360.0F || PlayerUtil.inFov(fov, blockPos)) {
                     return new BlockPos[]{blockPos, blockPos.offset((EnumFacing)getBlockState.getValue(BlockBed.FACING))};
                  }
                  continue label39;
               }
            }
         }
      }

      return null;
   }

   public BlockPos getBestBlock(BlockPos[] positions, boolean getSurrounding) {
      if (positions != null && positions.length != 0) {
         HashMap<BlockPos, double[]> blockMap = new HashMap();
         BlockPos[] var4 = positions;
         int var5 = positions.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockPos pos = var4[var6];
            if (pos != null) {
               if (getSurrounding) {
                  EnumFacing[] var19 = EnumFacing.values();
                  int var9 = var19.length;

                  for(int var20 = 0; var20 < var9; ++var20) {
                     EnumFacing enumFacing = var19[var20];
                     if (enumFacing != EnumFacing.DOWN) {
                        BlockPos offset = pos.offset(enumFacing);
                        if (!Arrays.asList(positions).contains(offset) && RotationsUtil.inRange(offset, (double)this.range.getValue())) {
                           double efficiency = this.getEfficiency(offset);
                           double distance = mc.thePlayer.getDistanceSqToCenter(offset);
                           blockMap.put(offset, new double[]{distance, efficiency});
                        }
                     }
                  }
               } else if (RotationsUtil.inRange(pos, (double)this.range.getValue())) {
                  double efficiency = this.getEfficiency(pos);
                  double distance = mc.thePlayer.getDistanceSqToCenter(pos);
                  blockMap.put(pos, new double[]{distance, efficiency});
               }
            }
         }

         List<Entry<BlockPos, double[]>> sortedByDistance = this.sortByDistance(blockMap);
         List<Entry<BlockPos, double[]>> sortedByEfficiency = this.sortByEfficiency(sortedByDistance);
         return sortedByEfficiency.isEmpty() ? null : (BlockPos)((Entry)sortedByEfficiency.get(0)).getKey();
      } else {
         return null;
      }
   }

   private List<Entry<BlockPos, double[]>> sortByDistance(HashMap<BlockPos, double[]> blockMap) {
      List<Entry<BlockPos, double[]>> list = new ArrayList(blockMap.entrySet());
      list.sort(Comparator.comparingDouble((entry) -> {
         return ((double[])entry.getValue())[0];
      }));
      return list;
   }

   private List<Entry<BlockPos, double[]>> sortByEfficiency(List<Entry<BlockPos, double[]>> blockList) {
      blockList.sort((entry1, entry2) -> {
         return Double.compare(((double[])entry2.getValue())[1], ((double[])entry1.getValue())[1]);
      });
      return blockList;
   }

   private double getEfficiency(BlockPos pos) {
      Block block = BlockUtils.getBlock(pos);
      ItemStack tool = this.breakMode.getMode() == "Swap" && Utils.getTool(block) != -1 ? mc.thePlayer.inventory.getStackInSlot(Utils.getTool(block)) : mc.thePlayer.getHeldItem();
      double efficiency = (double)BlockUtils.getBlockHardness(block, tool, false, this.ignoreSlow.isEnabled() || this.groundSpoof.isEnabled());
      if (this.breakProgressMap.get(pos) != null) {
         efficiency = (double)(Float)this.breakProgressMap.get(pos);
      }

      return efficiency;
   }

   private void reset(boolean resetSlot) {
      if (resetSlot) {
         this.resetSlot();
         this.currentSlot = -1;
      }

      this.bedPos = null;
      this.breakProgress = 0.0F;
      this.rotate = false;
      this.nearestBlock = null;
      this.aiming = false;
      this.ticksAfterBreak = 0;
      this.currentBlock = null;
      this.breakProgressMap.clear();
      this.lastSlot = -1;
      this.vanillaProgress = 0.0F;
      this.delayStart = false;
      this.stopAutoblock = false;
      this.lastProgress = 0.0D;
   }

   public void setPacketSlot(int slot) {
      if (slot != this.currentSlot && slot != -1 && mc.thePlayer.inventory.currentItem != slot) {
         mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(slot));
         this.currentSlot = slot;
      }
   }

   private void startBreak(BlockPos blockPos) {
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
   }

   private void stopBreak(BlockPos blockPos) {
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
   }

   private void swing() {
      mc.thePlayer.swingItem();
   }

   private void breakBlock(BlockPos blockPos) {
      if (blockPos != null) {
         float fov = (float)this.fov.getValue();
         if (fov == 360.0F || PlayerUtil.inFov(fov, blockPos)) {
            if (RotationsUtil.inRange(blockPos, (double)this.range.getValue())) {
               if (!this.onlyWhileVisible.isEnabled() || mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.objectMouseOver.getBlockPos().equals(blockPos)) {
                  if (BlockUtils.replaceable(this.currentBlock == null ? blockPos : this.currentBlock)) {
                     this.reset(true);
                  } else {
                     this.currentBlock = blockPos;
                     Block block = BlockUtils.getBlock(blockPos);
                     if (!this.silentSwing.isEnabled()) {
                        this.swing();
                     }

                     if (this.breakProgress != 0.0F || this.aiming) {
                        if (this.breakMode.getMode() != "Swap" && this.breakMode.getMode() != "Legit") {
                           if (this.breakMode.getMode() == "Instant" && this.aiming) {
                              this.stopAutoblock = true;
                              this.rotate = true;
                              if (!this.silentSwing.isEnabled()) {
                                 this.swing();
                              }

                              this.startBreak(blockPos);
                              this.setSlot(Utils.getTool(block));
                              this.stopBreak(blockPos);
                           }
                        } else {
                           if (this.breakProgress == 0.0F && this.aiming) {
                              this.resetSlot();
                              this.stopAutoblock = true;
                              this.rotate = true;
                              if (this.breakMode.getMode() == "Legit") {
                                 this.setSlot(Utils.getTool(block));
                              }

                              this.startBreak(blockPos);
                           } else {
                              if (this.breakProgress >= 1.0F && this.aiming) {
                                 if (this.breakMode.getMode() == "Swap") {
                                    this.setPacketSlot(Utils.getTool(block));
                                 }

                                 this.stopBreak(blockPos);
                                 this.reset(false);
                                 this.stopAutoblock = true;
                                 this.delayStart = true;
                                 Iterator iterator = this.breakProgressMap.entrySet().iterator();

                                 while(iterator.hasNext()) {
                                    Entry<BlockPos, Float> entry = (Entry)iterator.next();
                                    if (((BlockPos)entry.getKey()).equals(blockPos)) {
                                       iterator.remove();
                                    }
                                 }

                                 if (!this.disableBreakEffects.isEnabled()) {
                                    mc.playerController.onPlayerDestroyBlock(blockPos, EnumFacing.UP);
                                 }

                                 return;
                              }

                              if (this.breakMode.getMode() == "Legit") {
                                 this.stopAutoblock = true;
                                 this.rotate = true;
                              }
                           }

                           double progress = (double)(this.vanillaProgress = BlockUtils.getBlockHardness(block, this.breakMode.getMode() == "Swap" && Utils.getTool(block) != -1 ? mc.thePlayer.inventory.getStackInSlot(Utils.getTool(block)) : mc.thePlayer.getHeldItem(), false, this.ignoreSlow.isEnabled() || this.groundSpoof.isEnabled()) * (float)this.breakSpeed.getValue());
                           this.breakProgress = (float)((double)this.breakProgress + progress);
                           this.breakProgressMap.put(blockPos, this.breakProgress);
                           if (this.sendAnimations.isEnabled()) {
                              mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), blockPos, (int)(this.breakProgress * 10.0F - 1.0F));
                           }

                           for(this.lastProgress = 0.0D; this.lastProgress + progress < 1.0D; this.lastProgress += progress) {
                           }
                        }

                        this.aiming = false;
                     }
                  }
               }
            }
         }
      }
   }

   private void setSlot(int slot) {
      if (slot != -1 && slot != mc.thePlayer.inventory.currentItem) {
         if (this.lastSlot == -1) {
            this.lastSlot = mc.thePlayer.inventory.currentItem;
         }

         mc.thePlayer.inventory.currentItem = slot;
      }
   }

   private boolean isCovered(BlockPos blockPos) {
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumFacing enumFacing = var2[var4];
         BlockPos offset = blockPos.offset(enumFacing);
         if (BlockUtils.replaceable(offset) || BlockUtils.notFull(BlockUtils.getBlock(offset))) {
            return false;
         }
      }

      return true;
   }
}
