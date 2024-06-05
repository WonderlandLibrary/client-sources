package net.minecraft.block.material;

public class MaterialLogic extends Material {
   private static final String __OBFID = "CL_00000539";

   public boolean blocksLight() {
      return false;
   }

   public MaterialLogic(MapColor var1) {
      super(var1);
      this.setAdventureModeExempt();
   }

   public boolean blocksMovement() {
      return false;
   }

   public boolean isSolid() {
      return false;
   }
}
