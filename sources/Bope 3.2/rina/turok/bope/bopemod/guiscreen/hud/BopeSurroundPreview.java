package rina.turok.bope.bopemod.guiscreen.hud;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;
import rina.turok.bope.bopemod.util.BopeUtilEntity;

public class BopeSurroundPreview extends BopePinnable {
   int face = 0;

   public BopeSurroundPreview() {
      super("Surround Preview", "SurroundPreview", 1.0F, 0, 0);
   }

   public void render() {
      if (this.mc.world != null) {
         if (this.is_on_gui()) {
            this.background();
            this.create_line("S", 16, 32, "no dock");
            this.create_line("W", 0, 16, "no dock");
            this.create_line("N", 16, 0, "no dock");
            this.create_line("E", 32, 16, "no dock");
         }

         this.sync_face();
         float partial_tick = (float)Bope.get_module_manager().get_module_with_tag("SystemEventRender0").value_int_0();
         Vec3d vec = BopeUtilEntity.get_interpolated_entity(this.mc.player, partial_tick);
         BlockPos player_pos = new BlockPos(vec.x, vec.y, vec.z);
         BlockPos[] positions = new BlockPos[]{player_pos.south(), player_pos.west(), player_pos.north(), player_pos.east()};
         if (!this.is_touch(positions[0])) {
            this.render_surround(positions[0], 0);
         }

         if (!this.is_touch(positions[1])) {
            this.render_surround(positions[1], 1);
         }

         if (!this.is_touch(positions[2])) {
            this.render_surround(positions[2], 2);
         }

         if (!this.is_touch(positions[0])) {
            this.render_surround(positions[3], 3);
         }

         this.set_width(48);
         this.set_height(48);
      }

   }

   public void sync_face() {
      switch(MathHelper.floor((double)(this.mc.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7) {
      case 0:
         this.face = 0;
      case 1:
      case 3:
      case 5:
      default:
         break;
      case 2:
         this.face = 1;
         break;
      case 4:
         this.face = 2;
         break;
      case 6:
         this.face = 3;
      }

   }

   public void render_surround(BlockPos position, int side) {
      GlStateManager.pushMatrix();
      RenderHelper.enableGUIStandardItemLighting();
      Block block = this.mc.world.getBlockState(position).getBlock();
      byte off;
      if (side == this.face) {
         off = 0;
      } else {
         off = 0;
      }

      ItemStack item_to_render;
      if (side == 0 && (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN)) {
         item_to_render = new ItemStack(block);
         this.mc.getRenderItem().renderItemAndEffectIntoGUI(item_to_render, this.get_x() + 16, this.get_y() + 16 + 16 + off);
      }

      if (side == 1 && (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN)) {
         item_to_render = new ItemStack(block);
         this.mc.getRenderItem().renderItemAndEffectIntoGUI(item_to_render, this.get_x() - off, this.get_y() + 16);
      }

      if (side == 2 && (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN)) {
         item_to_render = new ItemStack(block);
         this.mc.getRenderItem().renderItemAndEffectIntoGUI(item_to_render, this.get_x() + 16, this.get_y() - off);
      }

      if (side == 3 && (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN)) {
         item_to_render = new ItemStack(block);
         this.mc.getRenderItem().renderItemAndEffectIntoGUI(item_to_render, this.get_x() + 16 + 16 + off, this.get_y() + 16);
      }

      this.mc.getRenderItem().zLevel = -5.0F;
      RenderHelper.disableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   public boolean is_touch(BlockPos pos) {
      Block block = this.mc.world.getBlockState(pos).getBlock();
      return (block instanceof BlockAir || block instanceof BlockLiquid) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).isEmpty();
   }
}
