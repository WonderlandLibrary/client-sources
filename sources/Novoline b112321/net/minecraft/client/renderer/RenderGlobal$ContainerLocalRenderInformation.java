package net.minecraft.client.renderer;

import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;

public class RenderGlobal$ContainerLocalRenderInformation {
   final RenderChunk renderChunk;
   final EnumFacing facing;
   final Set setFacing;
   final int counter;

   public RenderGlobal$ContainerLocalRenderInformation(RenderChunk var1, EnumFacing var2, int var3) {
      this.setFacing = EnumSet.noneOf(EnumFacing.class);
      this.renderChunk = var1;
      this.facing = var2;
      this.counter = var3;
   }

   RenderGlobal$ContainerLocalRenderInformation(RenderChunk var1, EnumFacing var2, int var3, Object var4) {
      this(var1, var2, var3);
   }
}
