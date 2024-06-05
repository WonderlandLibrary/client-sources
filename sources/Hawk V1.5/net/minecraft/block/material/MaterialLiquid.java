package net.minecraft.block.material;

public class MaterialLiquid extends Material {
   private static final String __OBFID = "CL_00000541";

   public MaterialLiquid(MapColor var1) {
      super(var1);
      this.setReplaceable();
      this.setNoPushMobility();
   }

   public boolean isSolid() {
      return false;
   }

   public boolean isLiquid() {
      return true;
   }

   public boolean blocksMovement() {
      return false;
   }
}
