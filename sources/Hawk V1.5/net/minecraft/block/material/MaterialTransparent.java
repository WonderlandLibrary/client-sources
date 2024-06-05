package net.minecraft.block.material;

public class MaterialTransparent extends Material {
   private static final String __OBFID = "CL_00000540";

   public MaterialTransparent(MapColor var1) {
      super(var1);
      this.setReplaceable();
   }

   public boolean blocksMovement() {
      return false;
   }

   public boolean blocksLight() {
      return false;
   }

   public boolean isSolid() {
      return false;
   }
}
