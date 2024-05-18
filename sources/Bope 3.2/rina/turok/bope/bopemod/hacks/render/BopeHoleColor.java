package rina.turok.bope.bopemod.hacks.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeHoleColor extends BopeModule {
   BopeSetting rgb = this.create("RGB Effect", "HoleColorRGBEffect", true);
   BopeSetting r = this.create("R", "HoleColorR", 255, 0, 255);
   BopeSetting g = this.create("G", "HoleColorG", 255, 0, 255);
   BopeSetting b = this.create("B", "HoleColorB", 255, 0, 255);
   BopeSetting a = this.create("A", "HoleColorA", 100, 0, 255);
   BopeSetting line_a = this.create("Outline A", "HoleColorLineOutlineA", 255, 0, 255);
   BopeSetting off_set = this.create("Off Set Y", "HoleColorOffSetY", 0.2D, 0.0D, 1.0D);
   BopeSetting range = this.create("Range", "HoleColorRange", 6, 1, 8);
   ArrayList hole;
   boolean safe = false;
   boolean outline = false;
   boolean solid = false;
   boolean docking = false;
   int color_r;
   int color_g;
   int color_b;

   public BopeHoleColor() {
      super(BopeCategory.BOPE_RENDER);
      this.name = "Hole Color";
      this.tag = "HoleColor";
      this.description = "It verify the holes and draw.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void update() {
      if (this.hole == null) {
         this.hole = new ArrayList();
      } else {
         this.hole.clear();
      }

      if (this.mc.player != null || this.mc.world != null) {
         int colapso_range = (int)Math.ceil((double)this.range.get_value(1));
         List spheres = this.sphere(this.player_as_blockpos(), (float)colapso_range, colapso_range);
         Iterator var3 = spheres.iterator();

         while(true) {
            BlockPos pos;
            do {
               do {
                  do {
                     if (!var3.hasNext()) {
                        return;
                     }

                     pos = (BlockPos)var3.next();
                  } while(!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR));
               } while(!this.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR));
            } while(!this.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR));

            boolean possible = true;
            BlockPos[] var6 = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               BlockPos seems_blocks = var6[var8];
               Block block = this.mc.world.getBlockState(pos.add(seems_blocks)).getBlock();
               if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                  possible = false;
                  break;
               }
            }

            if (possible) {
               this.hole.add(pos);
            }
         }
      }
   }

   public void render(BopeEventRender event) {
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_rgb = Color.HSBtoRGB(tick_color[0], 1.0F, 1.0F);
      if (this.rgb.get_value(true)) {
         this.color_r = color_rgb >> 16 & 255;
         this.color_g = color_rgb >> 8 & 255;
         this.color_b = color_rgb & 255;
         this.r.set_value(this.color_r);
         this.g.set_value(this.color_g);
         this.b.set_value(this.color_b);
      } else {
         this.color_r = this.r.get_value(1);
         this.color_g = this.g.get_value(2);
         this.color_b = this.b.get_value(3);
      }

      float off_set_h = 0.0F;
      if (this.hole != null && !this.hole.isEmpty() || this.safe) {
         off_set_h = (float)this.off_set.get_value(1.0D);
         Iterator var5 = this.hole.iterator();

         while(var5.hasNext()) {
            BlockPos holes = (BlockPos)var5.next();
            TurokRenderHelp.prepare("quads");
            TurokRenderHelp.draw_cube(TurokRenderHelp.get_buffer_build(), (float)holes.x, (float)holes.y, (float)holes.z, 1.0F, off_set_h, 1.0F, this.color_r, this.color_g, this.color_b, this.a.get_value(1), "all");
            TurokRenderHelp.release();
            TurokRenderHelp.prepare("lines");
            TurokRenderHelp.draw_cube_line(TurokRenderHelp.get_buffer_build(), (float)holes.x, (float)holes.y, (float)holes.z, 1.0F, off_set_h, 1.0F, this.color_r, this.color_g, this.color_b, this.line_a.get_value(1), "all");
            TurokRenderHelp.release();
         }
      }

   }

   public List sphere(BlockPos pos, float r, int h) {
      boolean hollow = false;
      boolean sphere = true;
      int plus_y = 0;
      List sphere_block = new ArrayList();
      int cx = pos.getX();
      int cy = pos.getY();
      int cz = pos.getZ();

      for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
         for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
            for(int y = sphere ? cy - (int)r : cy; (float)y < (sphere ? (float)cy + r : (float)(cy + h)); ++y) {
               double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
               if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0F) * (r - 1.0F)))) {
                  BlockPos spheres = new BlockPos(x, y + plus_y, z);
                  sphere_block.add(spheres);
               }
            }
         }
      }

      return sphere_block;
   }

   public BlockPos player_as_blockpos() {
      return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
   }
}
