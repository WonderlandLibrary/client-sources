// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import net.minecraft.init.Blocks;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockData
{
    public final /* synthetic */ BlockPos position;
    public final /* synthetic */ EnumFacing face;
    private static final /* synthetic */ int[] llllIlI;
    
    public BlockData(final BlockPos llIlIIlIIIIIIll, final EnumFacing llIlIIlIIIIIIlI) {
        this.position = llIlIIlIIIIIIll;
        this.face = llIlIIlIIIIIIlI;
    }
    
    private static boolean llIIIllII(final Object llIlIIIlllllIIl, final Object llIlIIIlllllIII) {
        return llIlIIIlllllIIl != llIlIIIlllllIII;
    }
    
    static {
        llIIIlIll();
    }
    
    private static void llIIIlIll() {
        (llllIlI = new int[3])[0] = ((0x71 ^ 0x51) & ~(0xA2 ^ 0x82));
        BlockData.llllIlI[1] = -" ".length();
        BlockData.llllIlI[2] = " ".length();
    }
    
    public static BlockData getBlockData(final BlockPos llIlIIIllllllII) {
        if (llIIIllII(Wrapper.INSTANCE.world().getBlockState(llIlIIIllllllII.add(BlockData.llllIlI[0], BlockData.llllIlI[1], BlockData.llllIlI[0])).getBlock(), Blocks.AIR)) {
            return new BlockData(llIlIIIllllllII.add(BlockData.llllIlI[0], BlockData.llllIlI[1], BlockData.llllIlI[0]), EnumFacing.UP);
        }
        if (llIIIllII(Wrapper.INSTANCE.world().getBlockState(llIlIIIllllllII.add(BlockData.llllIlI[1], BlockData.llllIlI[0], BlockData.llllIlI[0])).getBlock(), Blocks.AIR)) {
            return new BlockData(llIlIIIllllllII.add(BlockData.llllIlI[1], BlockData.llllIlI[0], BlockData.llllIlI[0]), EnumFacing.EAST);
        }
        if (llIIIllII(Wrapper.INSTANCE.world().getBlockState(llIlIIIllllllII.add(BlockData.llllIlI[2], BlockData.llllIlI[0], BlockData.llllIlI[0])).getBlock(), Blocks.AIR)) {
            return new BlockData(llIlIIIllllllII.add(BlockData.llllIlI[2], BlockData.llllIlI[0], BlockData.llllIlI[0]), EnumFacing.WEST);
        }
        if (llIIIllII(Wrapper.INSTANCE.world().getBlockState(llIlIIIllllllII.add(BlockData.llllIlI[0], BlockData.llllIlI[0], BlockData.llllIlI[1])).getBlock(), Blocks.AIR)) {
            return new BlockData(llIlIIIllllllII.add(BlockData.llllIlI[0], BlockData.llllIlI[0], BlockData.llllIlI[1]), EnumFacing.SOUTH);
        }
        if (llIIIllII(Wrapper.INSTANCE.world().getBlockState(llIlIIIllllllII.add(BlockData.llllIlI[0], BlockData.llllIlI[0], BlockData.llllIlI[2])).getBlock(), Blocks.AIR)) {
            return new BlockData(llIlIIIllllllII.add(BlockData.llllIlI[0], BlockData.llllIlI[0], BlockData.llllIlI[2]), EnumFacing.NORTH);
        }
        return null;
    }
}
