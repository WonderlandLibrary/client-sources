package net.minecraft.client.renderer;

import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;
import net.optifine.Config;
import org.lwjgl.opengl.GL11;

public class RenderList extends ChunkRenderContainer {
   private static final String f = "CL_00000957";

   public void renderChunkLayer(EnumWorldBlockLayer var1) {
      if(this.initialized) {
         if(this.renderChunks.isEmpty()) {
            return;
         }

         for(RenderChunk var3 : this.renderChunks) {
            ListedRenderChunk var4 = (ListedRenderChunk)var3;
            GlStateManager.pushMatrix();
            this.preRenderChunk(var3);
            GL11.glCallList(var4.getDisplayList(var1, var4.getCompiledChunk()));
            GlStateManager.popMatrix();
         }

         if(Config.isMultiTexture()) {
            GlStateManager.bindCurrentTexture();
         }

         GlStateManager.resetColor();
         this.renderChunks.clear();
      }

   }
}
