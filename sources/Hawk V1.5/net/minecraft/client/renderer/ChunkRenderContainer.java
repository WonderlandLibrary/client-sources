package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;

public abstract class ChunkRenderContainer {
   private static final String __OBFID = "CL_00002563";
   protected List field_178009_a = Lists.newArrayListWithCapacity(17424);
   protected boolean field_178007_b;
   private double field_178005_d;
   private double field_178006_e;
   private double field_178008_c;

   public void func_178003_a(RenderChunk var1) {
      BlockPos var2 = var1.func_178568_j();
      GlStateManager.translate((float)((double)var2.getX() - this.field_178008_c), (float)((double)var2.getY() - this.field_178005_d), (float)((double)var2.getZ() - this.field_178006_e));
   }

   public abstract void func_178001_a(EnumWorldBlockLayer var1);

   public void func_178002_a(RenderChunk var1, EnumWorldBlockLayer var2) {
      this.field_178009_a.add(var1);
   }

   public void func_178004_a(double var1, double var3, double var5) {
      this.field_178007_b = true;
      this.field_178009_a.clear();
      this.field_178008_c = var1;
      this.field_178005_d = var3;
      this.field_178006_e = var5;
   }
}
