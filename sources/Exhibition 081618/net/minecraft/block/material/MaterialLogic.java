package net.minecraft.block.material;

public class MaterialLogic extends Material {
   public MaterialLogic(MapColor p_i2112_1_) {
      super(p_i2112_1_);
      this.setAdventureModeExempt();
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
