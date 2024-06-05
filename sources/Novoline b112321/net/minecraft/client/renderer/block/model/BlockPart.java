package net.minecraft.client.renderer.block.model;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.block.model.BlockPart$1;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;

public class BlockPart {
   public final Vector3f positionFrom;
   public final Vector3f positionTo;
   public final Map mapFaces;
   public final BlockPartRotation partRotation;
   public final boolean shade;

   public BlockPart(Vector3f var1, Vector3f var2, Map var3, BlockPartRotation var4, boolean var5) {
      this.positionFrom = var1;
      this.positionTo = var2;
      this.mapFaces = var3;
      this.partRotation = var4;
      this.shade = var5;
      this.setDefaultUvs();
   }

   private void setDefaultUvs() {
      for(Entry var2 : this.mapFaces.entrySet()) {
         float[] var3 = this.getFaceUvs((EnumFacing)var2.getKey());
         ((BlockPartFace)var2.getValue()).blockFaceUV.setUvs(var3);
      }

   }

   private float[] getFaceUvs(EnumFacing var1) {
      float[] var2;
      switch(BlockPart$1.$SwitchMap$net$minecraft$util$EnumFacing[var1.ordinal()]) {
      case 1:
      case 2:
         var2 = new float[]{this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z};
         break;
      case 3:
      case 4:
         var2 = new float[]{this.positionFrom.x, 16.0F - this.positionTo.y, this.positionTo.x, 16.0F - this.positionFrom.y};
         break;
      case 5:
      case 6:
         var2 = new float[]{this.positionFrom.z, 16.0F - this.positionTo.y, this.positionTo.z, 16.0F - this.positionFrom.y};
         break;
      default:
         throw new NullPointerException();
      }

      return var2;
   }
}
