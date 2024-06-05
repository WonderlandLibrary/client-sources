package net.minecraft.block.material;

public class MaterialPortal extends Material {
   private static final String __OBFID = "CL_00000545";

   public boolean blocksLight() {
      return false;
   }

   public MaterialPortal(MapColor var1) {
      super(var1);
   }

   public boolean blocksMovement() {
      return false;
   }

   public boolean isSolid() {
      return false;
   }
}
