package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class ListedRenderChunk extends RenderChunk {
   private final int field_178601_d = GLAllocation.generateDisplayLists(EnumWorldBlockLayer.values().length);
   private static final String __OBFID = "CL_00002453";

   public ListedRenderChunk(World var1, RenderGlobal var2, BlockPos var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public void func_178566_a() {
      super.func_178566_a();
      GLAllocation.func_178874_a(this.field_178601_d, EnumWorldBlockLayer.values().length);
   }

   public int func_178600_a(EnumWorldBlockLayer var1, CompiledChunk var2) {
      return !var2.func_178491_b(var1) ? this.field_178601_d + var1.ordinal() : -1;
   }
}
