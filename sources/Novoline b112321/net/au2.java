package net;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSkull;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

final class au2 extends BehaviorDefaultDispenseItem {
   private boolean b = true;

   protected ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      World var3 = var1.getWorld();
      EnumFacing var4 = BlockDispenser.getFacing(var1.getBlockMetadata());
      BlockPos var5 = var1.getBlockPos().offset(var4);
      BlockSkull var6 = Blocks.skull;
      if(var3.isAirBlock(var5) && var6.canDispenserPlace(var3, var5, var2)) {
         if(!var3.isRemote) {
            var3.setBlockState(var5, var6.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 3);
            TileEntity var7 = var3.getTileEntity(var5);
            if(var7 instanceof TileEntitySkull) {
               if(var2.getMetadata() == 3) {
                  GameProfile var8 = null;
                  if(var2.hasTagCompound()) {
                     NBTTagCompound var9 = var2.getTagCompound();
                     if(var9.hasKey("SkullOwner", 10)) {
                        var8 = NBTUtil.readGameProfileFromNBT(var9.getCompoundTag("SkullOwner"));
                     } else if(var9.hasKey("SkullOwner", 8)) {
                        String var10 = var9.getString("SkullOwner");
                        if(!StringUtils.isNullOrEmpty(var10)) {
                           var8 = new GameProfile((UUID)null, var10);
                        }
                     }
                  }

                  ((TileEntitySkull)var7).setPlayerProfile(var8);
               } else {
                  ((TileEntitySkull)var7).setType(var2.getMetadata());
               }

               ((TileEntitySkull)var7).setSkullRotation(var4.getOpposite().getHorizontalIndex() * 4);
               Blocks.skull.checkWitherSpawn(var3, var5, (TileEntitySkull)var7);
            }

            --var2.stackSize;
         }
      } else {
         this.b = false;
      }

      return var2;
   }

   protected void playDispenseSound(IBlockSource var1) {
      if(this.b) {
         var1.getWorld().playAuxSFX(1000, var1.getBlockPos(), 0);
      } else {
         var1.getWorld().playAuxSFX(1001, var1.getBlockPos(), 0);
      }

   }
}
