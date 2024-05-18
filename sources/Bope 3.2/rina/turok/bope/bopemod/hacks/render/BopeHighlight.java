package rina.turok.bope.bopemod.hacks.render;

import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeHighlight extends BopeModule {
   BopeSetting rgb = this.create("RGB Effect", "HighlightRGBEffect", true);
   BopeSetting r = this.create("R", "HighlightR", 255, 0, 255);
   BopeSetting g = this.create("G", "HighlightG", 255, 0, 255);
   BopeSetting b = this.create("B", "HighlightB", 255, 0, 255);
   BopeSetting a = this.create("A", "HighlightA", 50, 0, 255);
   BopeSetting l_a = this.create("Outline A", "HighlightLineA", 100, 0, 255);
   int color_r;
   int color_g;
   int color_b;
   boolean outline = false;
   boolean solid = false;

   public BopeHighlight() {
      super(BopeCategory.BOPE_RENDER);
      this.name = "Highlight";
      this.tag = "Highlight";
      this.description = "Highlight block, no?";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void disable() {
      this.outline = false;
      this.solid = false;
   }

   public void render(BopeEventRender event) {
      if (this.mc.player != null && this.mc.world != null) {
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

         RayTraceResult result = this.mc.objectMouseOver;
         if (result != null && result.typeOfHit == Type.BLOCK) {
            BlockPos block_pos = result.getBlockPos();
            TurokRenderHelp.prepare("quads");
            TurokRenderHelp.draw_cube(block_pos, this.color_r, this.color_g, this.color_b, this.a.get_value(1), "all");
            TurokRenderHelp.release();
            TurokRenderHelp.prepare("lines");
            TurokRenderHelp.draw_cube_line(block_pos, this.color_r, this.color_g, this.color_b, this.l_a.get_value(1), "all");
            TurokRenderHelp.release();
         }
      }

   }
}
