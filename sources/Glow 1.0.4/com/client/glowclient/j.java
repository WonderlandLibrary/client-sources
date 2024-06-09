package com.client.glowclient;

import javax.annotation.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;

public interface J
{
    int A();
    
    @Nonnull
    String M();
    
    void M(final BlockPos p0);
    
    TileEntity M(final BlockPos p0);
    
    List<Entity> D();
    
    List<TileEntity> M();
    
    boolean M(final BlockPos p0, final IBlockState p1);
    
    void M(final BlockPos p0, final TileEntity p1);
    
    ItemStack M();
    
    IBlockState M(final BlockPos p0);
    
    int D();
    
    void D(final Entity p0);
    
    int M();
    
    void M(final ItemStack p0);
    
    void M(@Nonnull final String p0);
    
    void M(final Entity p0);
}
