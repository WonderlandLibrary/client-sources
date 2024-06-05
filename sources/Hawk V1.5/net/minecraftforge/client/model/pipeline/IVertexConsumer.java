package net.minecraftforge.client.model.pipeline;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;

public interface IVertexConsumer {
   void setQuadTint(int var1);

   VertexFormat getVertexFormat();

   void setQuadColored();

   void setQuadOrientation(EnumFacing var1);

   void put(int var1, float... var2);
}
