package rina.turok.bope.bopemod.hacks.combat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilBlock;
import rina.turok.bope.bopemod.util.BopeUtilMath;

public class BopeSurround extends BopeModule {
   BopeSetting animation = this.create("Animation", "SurroundAnimation", true);
   BopeSetting blocks_per_tick = this.create("Blocks Per Tick", "SurroundBlocksPerTick", 4, 1, 6);
   BopeSetting timeout_tick = this.create("Time Out Tick", "SurroundTimeOutTick", 10, 0, 30);
   BopeSetting tick = this.create("Tick", "SurroundTick", 2, 0, 10);
   int places_tick = 0;
   int place_tick = 0;
   int wait_tick = 0;
   int new_slot = 0;
   int old_slot = 0;
   boolean sneak = false;
   boolean verify;
   boolean missing;
   String sides_can_place;
   Vec3d[] mask = new Vec3d[]{new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D)};
   List not_true_blocks;
   List not_true_entitys_blocks;

   public BopeSurround() {
      super(BopeCategory.BOPE_COMBAT);
      this.not_true_blocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.CRAFTING_TABLE, Blocks.BREWING_STAND, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.DROPPER, Blocks.HOPPER, Blocks.CHEST, Blocks.ANVIL);
      this.not_true_entitys_blocks = Arrays.asList(Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.RED_SHULKER_BOX);
      this.name = "Surround";
      this.tag = "Surround";
      this.description = "A barrier into player with obsidian.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void enable() {
      if (this.mc.player != null) {
         this.verify = true;
         this.old_slot = this.mc.player.inventory.currentItem;
         this.new_slot = -1;
      }

   }

   public void disable() {
      if (this.mc.player != null) {
         if (this.new_slot != this.old_slot && this.old_slot != -1) {
            this.mc.player.inventory.currentItem = this.old_slot;
         }

         if (this.sneak) {
            this.mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, Action.STOP_SNEAKING));
            this.sneak = false;
         }

         this.old_slot = -1;
         this.new_slot = -1;
         this.missing = false;
      }

   }

   public void update() {
      if (this.mc.player != null) {
         if (this.place_tick >= this.timeout_tick.get_value(1)) {
            this.place_tick = 0;
            this.set_active(!this.is_active());
            return;
         }

         if (!this.verify) {
            if (this.wait_tick < this.tick.get_value(1)) {
               ++this.wait_tick;
               return;
            }

            this.wait_tick = 0;
         }

         if (this.verify) {
            this.verify = false;
            if (this.find_in_hotbar() == -1) {
               this.missing = true;
            }
         }

         Vec3d[] many_blocks = this.mask;
         int blocks_length = this.mask.length;

         int places;
         for(places = 0; places < this.blocks_per_tick.get_value(1); ++this.places_tick) {
            if (this.places_tick >= blocks_length) {
               this.places_tick = 0;
               break;
            }

            BlockPos off_place = new BlockPos(many_blocks[this.places_tick]);
            BlockPos target_place = (new BlockPos(this.mc.player.getPositionVector())).add(off_place.x, off_place.y, off_place.z);
            if (this.place_blocks(target_place)) {
               ++places;
            }
         }

         if (places > 0) {
            if (this.new_slot != this.old_slot && this.old_slot != -1) {
               this.mc.player.inventory.currentItem = this.old_slot;
               this.new_slot = this.old_slot;
            }

            if (this.sneak) {
               this.mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, Action.STOP_SNEAKING));
               this.sneak = false;
            }
         }

         ++this.place_tick;
         if (this.missing) {
            this.missing = false;
            this.set_active(!this.is_active());
         }
      }

   }

   public boolean place_blocks(BlockPos pos) {
      Block block = this.mc.world.getBlockState(pos).getBlock();
      if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
         return false;
      } else {
         Iterator var3 = this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).iterator();

         while(var3.hasNext()) {
            Entity entitys = (Entity)var3.next();
            if (!(entitys instanceof EntityItem) && !(entitys instanceof EntityXPOrb)) {
               return false;
            }
         }

         EnumFacing side = this.get_placeale_side(pos);
         if (side == null) {
            return false;
         } else {
            BlockPos left_side = pos.offset(side);
            EnumFacing opposite = side.getOpposite();
            if (!BopeUtilBlock.is_possible(left_side)) {
               return false;
            } else {
               Vec3d hit = (new Vec3d(left_side)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
               Block left_block = this.mc.world.getBlockState(left_side).getBlock();
               int obi_slot = this.find_in_hotbar();
               if (obi_slot == -1) {
                  this.missing = true;
                  return false;
               } else {
                  if (this.new_slot != obi_slot) {
                     this.mc.player.inventory.currentItem = obi_slot;
                     this.new_slot = obi_slot;
                  }

                  if ((!this.sneak || !this.not_true_blocks.contains(left_block)) && !this.not_true_entitys_blocks.contains(left_block)) {
                     this.mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, Action.START_SNEAKING));
                     this.sneak = true;
                  }

                  this.reset_rotate(hit);
                  this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, left_side, opposite, hit, EnumHand.MAIN_HAND);
                  if (this.animation.get_value(true)) {
                     this.mc.player.swingArm(EnumHand.MAIN_HAND);
                  }

                  this.mc.rightClickDelayTimer = 4;
                  return true;
               }
            }
         }
      }
   }

   public void reset_rotate(Vec3d pos) {
      float[] rotations = BopeUtilMath.legit_rotation(pos);
      this.mc.player.connection.sendPacket(new Rotation(rotations[0], rotations[1], this.mc.player.onGround));
   }

   public EnumFacing get_placeale_side(BlockPos pos) {
      EnumFacing[] var2 = EnumFacing.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumFacing sides = var2[var4];
         BlockPos left_side = pos.offset(sides);
         if (this.mc.world.getBlockState(left_side).getBlock().canCollideCheck(this.mc.world.getBlockState(left_side), false)) {
            IBlockState state_block = this.mc.world.getBlockState(left_side);
            if (!state_block.getMaterial().isReplaceable()) {
               return sides;
            }
         }
      }

      return null;
   }

   public int find_in_hotbar() {
      int slot = -1;

      for(int i = 0; i < 9; ++i) {
         ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
         if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
            Block type_block = ((ItemBlock)stack.getItem()).getBlock();
            if (type_block instanceof BlockObsidian) {
               slot = i;
               break;
            }
         }
      }

      return slot;
   }

   public String value_string_0() {
      return this.sides_can_place;
   }
}
