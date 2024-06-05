package net.minecraft.util;

import net.minecraft.entity.Entity;

public class MovingObjectPosition {
   public MovingObjectPosition.MovingObjectType typeOfHit;
   private BlockPos field_178783_e;
   public Entity entityHit;
   public Vec3 hitVec;
   public EnumFacing field_178784_b;
   private static final String __OBFID = "CL_00000610";

   public MovingObjectPosition(Vec3 var1, EnumFacing var2, BlockPos var3) {
      this(MovingObjectPosition.MovingObjectType.BLOCK, var1, var2, var3);
   }

   public MovingObjectPosition(Vec3 var1, EnumFacing var2) {
      this(MovingObjectPosition.MovingObjectType.BLOCK, var1, var2, BlockPos.ORIGIN);
   }

   public MovingObjectPosition(MovingObjectPosition.MovingObjectType var1, Vec3 var2, EnumFacing var3, BlockPos var4) {
      this.typeOfHit = var1;
      this.field_178783_e = var4;
      this.field_178784_b = var3;
      this.hitVec = new Vec3(var2.xCoord, var2.yCoord, var2.zCoord);
   }

   public MovingObjectPosition(Entity var1, Vec3 var2) {
      this.typeOfHit = MovingObjectPosition.MovingObjectType.ENTITY;
      this.entityHit = var1;
      this.hitVec = var2;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("HitResult{type=")).append(this.typeOfHit).append(", blockpos=").append(this.field_178783_e).append(", f=").append(this.field_178784_b).append(", pos=").append(this.hitVec).append(", entity=").append(this.entityHit).append('}'));
   }

   public MovingObjectPosition(Entity var1) {
      this(var1, new Vec3(var1.posX, var1.posY, var1.posZ));
   }

   public BlockPos func_178782_a() {
      return this.field_178783_e;
   }

   public static enum MovingObjectType {
      private static final MovingObjectPosition.MovingObjectType[] $VALUES = new MovingObjectPosition.MovingObjectType[]{MISS, BLOCK, ENTITY};
      MISS("MISS", 0);

      private static final String __OBFID = "CL_00000611";
      ENTITY("ENTITY", 2),
      BLOCK("BLOCK", 1);

      private static final MovingObjectPosition.MovingObjectType[] ENUM$VALUES = new MovingObjectPosition.MovingObjectType[]{MISS, BLOCK, ENTITY};

      private MovingObjectType(String var3, int var4) {
      }
   }
}
