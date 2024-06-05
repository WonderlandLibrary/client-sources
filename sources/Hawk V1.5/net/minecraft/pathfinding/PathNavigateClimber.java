package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
   private BlockPos field_179696_f;
   private static final String __OBFID = "CL_00002245";

   public PathEntity func_179680_a(BlockPos var1) {
      this.field_179696_f = var1;
      return super.func_179680_a(var1);
   }

   public boolean tryMoveToEntityLiving(Entity var1, double var2) {
      PathEntity var4 = this.getPathToEntityLiving(var1);
      if (var4 != null) {
         return this.setPath(var4, var2);
      } else {
         this.field_179696_f = new BlockPos(var1);
         this.speed = var2;
         return true;
      }
   }

   public PathNavigateClimber(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   public void onUpdateNavigation() {
      if (!this.noPath()) {
         super.onUpdateNavigation();
      } else if (this.field_179696_f != null) {
         double var1 = (double)(this.theEntity.width * this.theEntity.width);
         if (!(this.theEntity.func_174831_c(this.field_179696_f) >= var1) || !(this.theEntity.posY <= (double)this.field_179696_f.getY()) && !(this.theEntity.func_174831_c(new BlockPos(this.field_179696_f.getX(), MathHelper.floor_double(this.theEntity.posY), this.field_179696_f.getZ())) >= var1)) {
            this.field_179696_f = null;
         } else {
            this.theEntity.getMoveHelper().setMoveTo((double)this.field_179696_f.getX(), (double)this.field_179696_f.getY(), (double)this.field_179696_f.getZ(), this.speed);
         }
      }

   }

   public PathEntity getPathToEntityLiving(Entity var1) {
      this.field_179696_f = new BlockPos(var1);
      return super.getPathToEntityLiving(var1);
   }
}
