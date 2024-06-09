package com.client.glowclient;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraftforge.fluids.*;

public class td
{
    private Bb M(final List<Bb> list, final ItemStack itemStack) {
        final Iterator<Bb> iterator2;
        Iterator<Bb> iterator = iterator2 = list.iterator();
        while (iterator.hasNext()) {
            final Bb bb;
            if ((bb = iterator2.next()).B.isItemEqual(itemStack)) {
                return bb;
            }
            iterator = iterator2;
        }
        final Bb bb2 = new Bb(itemStack.copy());
        list.add(bb2);
        return bb2;
    }
    
    public td() {
        super();
    }
    
    public List<Bb> M(final EntityPlayer entityPlayer, final XA xa, final World world) {
        final ArrayList<Bb> list = new ArrayList<Bb>();
        if (xa == null) {
            return list;
        }
        final RayTraceResult rayTraceResult = new RayTraceResult((Entity)entityPlayer);
        final wc wc = new wc();
        final Iterator<wc> iterator = LB.M(BlockPos.ORIGIN, new BlockPos(xa.getWidth() - 1, xa.getHeight() - 1, xa.getLength() - 1)).iterator();
    Label_0074:
        while (true) {
            Iterator<wc> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final wc wc2 = iterator.next();
                final IBlockState blockState;
                final Block block;
                if (!xa.A.M(xa, wc2.getY()) || (block = (blockState = xa.getBlockState(wc2)).getBlock()) == Blocks.AIR) {
                    continue Label_0074;
                }
                if (xa.isAirBlock(wc2)) {
                    iterator2 = iterator;
                }
                else {
                    wc.set((Vec3i)xa.L.add((Vec3i)wc2));
                    final boolean m = Ib.M(blockState, world.getBlockState((BlockPos)wc));
                    ItemStack itemStack = ItemStack.EMPTY;
                    try {
                        itemStack = block.getPickBlock(blockState, rayTraceResult, (World)xa, (BlockPos)wc2, entityPlayer);
                        final Block block2 = block;
                    }
                    catch (Exception ex) {
                        ld.H.warn("Could not get the pick block for: {}", (Object)blockState, (Object)ex);
                        final Block block2 = block;
                    }
                    Block block2;
                    final FluidActionResult tryFillContainer;
                    final ItemStack result;
                    if ((block2 instanceof IFluidBlock || block instanceof BlockLiquid) && (tryFillContainer = FluidUtil.tryFillContainer(new ItemStack(Items.BUCKET), FluidUtil.getFluidHandler((World)xa, (BlockPos)wc2, (EnumFacing)null), 1000, (EntityPlayer)null, (boolean)(0 != 0))).isSuccess() && !(result = tryFillContainer.getResult()).isEmpty()) {
                        itemStack = result;
                    }
                    if (itemStack == null) {
                        ld.H.error("Could not find the item for: {} (getPickBlock() returned null, this is a bug)", (Object)blockState);
                        iterator2 = iterator;
                    }
                    else if (itemStack.isEmpty()) {
                        ld.H.warn("Could not find the item for: {}", (Object)blockState);
                        iterator2 = iterator;
                    }
                    else {
                        int n = 1;
                        if (block instanceof BlockSlab && ((BlockSlab)block).isDouble()) {
                            n = 2;
                        }
                        final Bb i = this.M(list, itemStack);
                        if (m) {
                            final Bb bb = i;
                            bb.L += n;
                        }
                        final Bb bb2 = i;
                        bb2.A += n;
                        iterator2 = iterator;
                    }
                }
            }
            break;
        }
        final Iterator<Bb> iterator4;
        Iterator<Bb> iterator3 = iterator4 = list.iterator();
        while (iterator3.hasNext()) {
            final Bb bb3 = iterator4.next();
            if (entityPlayer.capabilities.isCreativeMode) {
                iterator3 = iterator4;
                bb3.b = -1;
            }
            else {
                bb3.b = Nc.M((IInventory)entityPlayer.inventory, bb3.B.getItem(), bb3.B.getItemDamage());
                iterator3 = iterator4;
            }
        }
        return list;
    }
}
