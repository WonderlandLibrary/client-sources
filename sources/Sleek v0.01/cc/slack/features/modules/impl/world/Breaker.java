package cc.slack.features.modules.impl.world;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.ghost.AutoTool;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.BlockUtils;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.RotationUtil;
import cc.slack.utils.render.Render3DUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
   name = "Breaker",
   category = Category.WORLD
)
public class Breaker extends Module {
   public final ModeValue<String> mode = new ModeValue("Bypass", new String[]{"Hypixel", "None"});
   public final NumberValue<Double> radiusDist = new NumberValue("Radius", 4.5D, 1.0D, 7.0D, 0.5D);
   public final ModeValue<String> sortMode = new ModeValue("Sort", new String[]{"Distance", "Absolute"});
   public final NumberValue<Integer> switchDelay = new NumberValue("Switch Delay", 50, 0, 500, 10);
   public final NumberValue<Integer> targetSwitchDelay = new NumberValue("Target Switch Delay", 50, 0, 500, 10);
   private BlockPos targetBlock;
   private BlockPos currentBlock;
   private float breakingProgress;
   private TimeUtil switchTimer = new TimeUtil();

   public Breaker() {
      this.addSettings(new Value[]{this.mode, this.radiusDist, this.sortMode, this.switchDelay, this.targetSwitchDelay});
   }

   public void onEnable() {
      this.targetBlock = null;
      this.currentBlock = null;
      this.breakingProgress = 0.0F;
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (this.targetBlock == null) {
         if (this.switchTimer.hasReached((Integer)this.targetSwitchDelay.getValue())) {
            this.findTargetBlock();
         }
      } else {
         if (this.currentBlock == null && this.switchTimer.hasReached((Integer)this.switchDelay.getValue())) {
            this.findBreakBlock();
            this.breakingProgress = 0.0F;
            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
         }

         if (this.currentBlock != null) {
            ((AutoTool)Slack.getInstance().getModuleManager().getInstance(AutoTool.class)).getTool(true, BlockUtils.getBlock(this.currentBlock), 0, false);
            this.breakingProgress += BlockUtils.getHardness(this.currentBlock);
            mc.getWorld().sendBlockBreakProgress(mc.getPlayer().getEntityId(), this.currentBlock, (int)(this.breakingProgress * 10.0F) - 1);
            mc.getPlayer().swingItem();
            RotationUtil.setClientRotation(BlockUtils.getCenterRotation(this.currentBlock));
            RotationUtil.setStrafeFix(true, false);
            if (this.breakingProgress > 1.0F) {
               mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
               mc.getPlayerController().onPlayerDestroyBlock(this.currentBlock, EnumFacing.DOWN);
               mc.getWorld().setBlockState(this.currentBlock, Blocks.air.getDefaultState(), 11);
               ((AutoTool)Slack.getInstance().getModuleManager().getInstance(AutoTool.class)).getTool(false, BlockUtils.getBlock(this.currentBlock), 0, false);
               this.currentBlock = null;
               this.switchTimer.reset();
            }
         }
      }

   }

   @Listen
   public void onRender(RenderEvent event) {
      if (event.getState() == RenderEvent.State.RENDER_3D) {
         if (this.currentBlock != null) {
            BlockPos bp = this.currentBlock;
            Render3DUtil.drawAABB(AxisAlignedBB.fromBounds((double)bp.getX(), (double)bp.getY(), (double)bp.getZ(), (double)(bp.getX() + 1), (double)((float)bp.getY() + this.breakingProgress), (double)(bp.getZ() + 1)));
         }
      }
   }

   private void findTargetBlock() {
      int radius = (int)Math.ceil((Double)this.radiusDist.getValue());
      BlockPos bestBlock = null;
      double bestDist = -1.0D;
      int bestAbs = -1;

      for(int x = radius; x >= -radius + 1; --x) {
         for(int y = radius; y >= -radius + 1; --y) {
            for(int z = radius; z >= -radius + 1; --z) {
               BlockPos blockPos = new BlockPos(mc.getPlayer().posX + (double)x, mc.getPlayer().posY + (double)y, mc.getPlayer().posZ + (double)z);
               Block block = BlockUtils.getBlock(blockPos);
               if (block != null && block instanceof BlockBed) {
                  String var11 = ((String)this.sortMode.getValue()).toLowerCase();
                  byte var12 = -1;
                  switch(var11.hashCode()) {
                  case 288459765:
                     if (var11.equals("distance")) {
                        var12 = 0;
                     }
                     break;
                  case 1728122231:
                     if (var11.equals("absolute")) {
                        var12 = 1;
                     }
                  }

                  switch(var12) {
                  case 0:
                     if (bestDist == -1.0D || BlockUtils.getCenterDistance(blockPos) < bestDist) {
                        bestBlock = blockPos;
                        bestDist = BlockUtils.getCenterDistance(blockPos);
                     }
                     break;
                  case 1:
                     if (bestAbs == -1 || (double)BlockUtils.getAbsoluteValue(blockPos) < bestDist) {
                        bestBlock = blockPos;
                        bestAbs = BlockUtils.getAbsoluteValue(blockPos);
                     }
                  }
               }
            }
         }
      }

      if (bestBlock != null) {
         this.targetBlock = bestBlock;
         this.switchTimer.reset();
      }

   }

   private void findBreakBlock() {
      if (this.targetBlock != null) {
         String var1 = ((String)this.mode.getValue()).toLowerCase();
         byte var2 = -1;
         switch(var1.hashCode()) {
         case 3387192:
            if (var1.equals("none")) {
               var2 = 1;
            }
            break;
         case 1381910549:
            if (var1.equals("hypixel")) {
               var2 = 0;
            }
         }

         switch(var2) {
         case 0:
            if (!BlockUtils.isReplaceableNotBed(this.targetBlock.east()) && !BlockUtils.isReplaceableNotBed(this.targetBlock.north()) && !BlockUtils.isReplaceableNotBed(this.targetBlock.west()) && !BlockUtils.isReplaceableNotBed(this.targetBlock.south()) && !BlockUtils.isReplaceableNotBed(this.targetBlock.up())) {
               this.currentBlock = this.targetBlock.up();
            } else {
               this.currentBlock = this.targetBlock;
            }
            break;
         case 1:
            this.currentBlock = this.targetBlock;
         }

      }
   }
}
