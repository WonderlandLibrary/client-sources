package net.minecraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialLiquid extends Material {
   public MaterialLiquid(MapColor var1) {
      super(var1);
      this.setReplaceable();
      this.setNoPushMobility();
   }

   public boolean isLiquid() {
      return true;
   }

   public boolean blocksMovement() {
      return false;
   }

   public boolean isSolid() {
      return false;
   }
}
