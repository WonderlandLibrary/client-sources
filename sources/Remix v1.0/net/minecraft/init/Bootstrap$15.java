package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;

static final class Bootstrap$15 extends BehaviorDefaultDispenseItem {
    private boolean field_179240_b = true;
    
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final EnumFacing var4 = BlockDispenser.getFacing(source.getBlockMetadata());
        final BlockPos var5 = source.getBlockPos().offset(var4);
        final BlockSkull var6 = Blocks.skull;
        if (var3.isAirBlock(var5) && var6.func_176415_b(var3, var5, stack)) {
            if (!var3.isRemote) {
                var3.setBlockState(var5, var6.getDefaultState().withProperty(BlockSkull.field_176418_a, EnumFacing.UP), 3);
                final TileEntity var7 = var3.getTileEntity(var5);
                if (var7 instanceof TileEntitySkull) {
                    if (stack.getMetadata() == 3) {
                        GameProfile var8 = null;
                        if (stack.hasTagCompound()) {
                            final NBTTagCompound var9 = stack.getTagCompound();
                            if (var9.hasKey("SkullOwner", 10)) {
                                var8 = NBTUtil.readGameProfileFromNBT(var9.getCompoundTag("SkullOwner"));
                            }
                            else if (var9.hasKey("SkullOwner", 8)) {
                                var8 = new GameProfile((UUID)null, var9.getString("SkullOwner"));
                            }
                        }
                        ((TileEntitySkull)var7).setPlayerProfile(var8);
                    }
                    else {
                        ((TileEntitySkull)var7).setType(stack.getMetadata());
                    }
                    ((TileEntitySkull)var7).setSkullRotation(var4.getOpposite().getHorizontalIndex() * 4);
                    Blocks.skull.func_180679_a(var3, var5, (TileEntitySkull)var7);
                }
                --stack.stackSize;
            }
        }
        else {
            this.field_179240_b = false;
        }
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        if (this.field_179240_b) {
            source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
        }
        else {
            source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
        }
    }
}