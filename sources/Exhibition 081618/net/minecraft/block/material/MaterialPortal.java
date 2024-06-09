package net.minecraft.block.material;

public class MaterialPortal extends Material {
   public MaterialPortal(MapColor p_i2118_1_) {
      super(p_i2118_1_);
   }

   public boolean isSolid() {
      return false;
   }

   public boolean blocksLight() {
      return false;
   }

   public boolean blocksMovement() {
      return false;
   }
}
