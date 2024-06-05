package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting$EnumArt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging {
   public EntityPainting$EnumArt art;

   public EntityPainting(World var1) {
      super(var1);
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3) {
      super(var1, var2);
      ArrayList var4 = Lists.newArrayList();

      for(EntityPainting$EnumArt var8 : EntityPainting$EnumArt.values()) {
         this.art = var8;
         this.updateFacingWithBoundingBox(var3);
         if(this.onValidSurface()) {
            var4.add(var8);
         }
      }

      if(!var4.isEmpty()) {
         this.art = (EntityPainting$EnumArt)var4.get(this.rand.nextInt(var4.size()));
      }

      this.updateFacingWithBoundingBox(var3);
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3, String var4) {
      this(var1, var2, var3);

      for(EntityPainting$EnumArt var8 : EntityPainting$EnumArt.values()) {
         if(var8.title.equals(var4)) {
            this.art = var8;
            break;
         }
      }

      this.updateFacingWithBoundingBox(var3);
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setString("Motive", this.art.title);
      super.writeEntityToNBT(var1);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      String var2 = var1.getString("Motive");

      for(EntityPainting$EnumArt var6 : EntityPainting$EnumArt.values()) {
         if(var6.title.equals(var2)) {
            this.art = var6;
         }
      }

      if(this.art == null) {
         this.art = EntityPainting$EnumArt.KEBAB;
      }

      super.readEntityFromNBT(var1);
   }

   public int getWidthPixels() {
      return this.art.sizeX;
   }

   public int getHeightPixels() {
      return this.art.sizeY;
   }

   public void onBroken(Entity var1) {
      if(this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         if(var1 instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var1;
            if(var2.abilities.isCreative()) {
               return;
            }
         }

         this.entityDropItem(new ItemStack(Items.painting), 0.0F);
      }

   }

   public void setLocationAndAngles(double var1, double var3, double var5, float var7, float var8) {
      BlockPos var9 = this.hangingPosition.add(var1 - this.posX, var3 - this.posY, var5 - this.posZ);
      this.setPosition((double)var9.getX(), (double)var9.getY(), (double)var9.getZ());
   }

   public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      BlockPos var11 = this.hangingPosition.add(var1 - this.posX, var3 - this.posY, var5 - this.posZ);
      this.setPosition((double)var11.getX(), (double)var11.getY(), (double)var11.getZ());
   }
}
