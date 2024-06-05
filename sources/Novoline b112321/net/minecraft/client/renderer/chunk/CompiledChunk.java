package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import net.iX;
import net.minecraft.client.renderer.chunk.CompiledChunk$1;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

public class CompiledChunk {
   public static final CompiledChunk DUMMY = new CompiledChunk$1();
   private final boolean[] layersUsed = new boolean[EnumWorldBlockLayer.values().length];
   private final boolean[] layersStarted = new boolean[EnumWorldBlockLayer.values().length];
   private boolean empty = true;
   private final List tileEntities = Lists.newArrayList();
   private SetVisibility setVisibility = new SetVisibility();
   private iX e;

   public boolean isEmpty() {
      return this.empty;
   }

   protected void setLayerUsed(EnumWorldBlockLayer var1) {
      this.empty = false;
      this.layersUsed[var1.ordinal()] = true;
   }

   public boolean isLayerEmpty(EnumWorldBlockLayer var1) {
      return !this.layersUsed[var1.ordinal()];
   }

   public void setLayerStarted(EnumWorldBlockLayer var1) {
      this.layersStarted[var1.ordinal()] = true;
   }

   public boolean isLayerStarted(EnumWorldBlockLayer var1) {
      return this.layersStarted[var1.ordinal()];
   }

   public List getTileEntities() {
      return this.tileEntities;
   }

   public void addTileEntity(TileEntity var1) {
      this.tileEntities.add(var1);
   }

   public boolean isVisible(EnumFacing var1, EnumFacing var2) {
      return this.setVisibility.isVisible(var1, var2);
   }

   public void setVisibility(SetVisibility var1) {
      this.setVisibility = var1;
   }

   public iX b() {
      return this.e;
   }

   public void a(iX var1) {
      this.e = var1;
   }
}
