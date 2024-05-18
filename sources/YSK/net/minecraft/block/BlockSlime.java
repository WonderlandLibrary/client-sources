package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public class BlockSlime extends BlockBreakable
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        if (Math.abs(entity.motionY) < 0.1 && !entity.isSneaking()) {
            final double n = 0.4 + Math.abs(entity.motionY) * 0.2;
            entity.motionX *= n;
            entity.motionZ *= n;
        }
        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    public BlockSlime() {
        super(Material.clay, "".length() != 0, MapColor.grassColor);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.slipperiness = 0.8f;
    }
    
    @Override
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        if (entity.isSneaking()) {
            super.onFallenUpon(world, blockPos, entity, n);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            entity.fall(n, 0.0f);
        }
    }
    
    @Override
    public void onLanded(final World world, final Entity entity) {
        if (entity.isSneaking()) {
            super.onLanded(world, entity);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (entity.motionY < 0.0) {
            entity.motionY = -entity.motionY;
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
}
