package optifine;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;

public class RenderInfoLazy {
   private RenderGlobal.ContainerLocalRenderInformation renderInfo;
   private RenderChunk renderChunk;

   public void setRenderChunk(RenderChunk var1) {
      this.renderChunk = var1;
      this.renderInfo = null;
   }

   public RenderChunk getRenderChunk() {
      return this.renderChunk;
   }

   public RenderGlobal.ContainerLocalRenderInformation getRenderInfo() {
      if (this.renderInfo == null) {
         this.renderInfo = new RenderGlobal.ContainerLocalRenderInformation(this.renderChunk, (EnumFacing)null, 0);
      }

      return this.renderInfo;
   }
}
