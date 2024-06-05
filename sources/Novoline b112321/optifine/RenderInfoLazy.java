package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aA2;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumFacing;
import optifine.MatchBlock;

public class RenderInfoLazy {
   private RenderChunk renderChunk;
   private aA2 b;

   public RenderChunk getRenderChunk() {
      return this.renderChunk;
   }

   public void setRenderChunk(RenderChunk var1) {
      this.renderChunk = var1;
      this.b = null;
   }

   public aA2 a() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.b == null) {
         this.b = new aA2(this.renderChunk, (EnumFacing)null, 0);
      }

      return this.b;
   }
}
