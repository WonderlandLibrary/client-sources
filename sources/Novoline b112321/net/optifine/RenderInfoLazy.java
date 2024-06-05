package net.optifine;

import net.acE;
import net.minecraft.client.renderer.RenderGlobal$ContainerLocalRenderInformation;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;
import net.optifine.MatchBlock;

public class RenderInfoLazy {
   private RenderChunk renderChunk;
   private RenderGlobal$ContainerLocalRenderInformation renderInfo;

   public RenderChunk getRenderChunk() {
      return this.renderChunk;
   }

   public void setRenderChunk(RenderChunk var1) {
      this.renderChunk = var1;
      this.renderInfo = null;
   }

   public RenderGlobal$ContainerLocalRenderInformation getRenderInfo() {
      acE[] var1 = MatchBlock.b();
      if(this.renderInfo == null) {
         this.renderInfo = new RenderGlobal$ContainerLocalRenderInformation(this.renderChunk, (EnumFacing)null, 0);
      }

      return this.renderInfo;
   }
}
