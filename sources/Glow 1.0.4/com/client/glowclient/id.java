package com.client.glowclient;

import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;

public class Id implements J
{
    private ItemStack k;
    private final List<TileEntity> H;
    private final int f;
    private final List<Entity> M;
    private final int G;
    private final byte[][][] d;
    private final int L;
    private final short[][][] A;
    private String B;
    private static final ItemStack b;
    
    @Override
    public int A() {
        return this.f;
    }
    
    @Override
    public void M(@Nonnull final String b) {
        if (b == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.B = b;
    }
    
    @Override
    public void D(final Entity entity) {
        if (entity == null || entity.getUniqueID() == null || entity instanceof EntityPlayer) {
            return;
        }
        final Iterator<Entity> iterator2;
        Iterator<Entity> iterator = iterator2 = this.M.iterator();
        while (iterator.hasNext()) {
            if (entity.getUniqueID().equals(iterator2.next().getUniqueID())) {
                return;
            }
            iterator = iterator2;
        }
        this.M.add(entity);
    }
    
    @Override
    public List<TileEntity> M() {
        return this.H;
    }
    
    @Override
    public void M(final ItemStack itemStack) {
        this.k = ((itemStack != null) ? itemStack : Id.b.copy());
    }
    
    @Override
    public void M(final BlockPos blockPos, final TileEntity tileEntity) {
        if (!this.M(blockPos)) {
            return;
        }
        this.M(blockPos);
        if (tileEntity != null) {
            this.H.add(tileEntity);
        }
    }
    
    @Override
    public IBlockState M(final BlockPos blockPos) {
        if (!this.M(blockPos)) {
            return Blocks.AIR.getDefaultState();
        }
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        return ((Block)Block.REGISTRY.getObjectById((int)this.A[x][y][z])).getStateFromMeta((int)this.d[x][y][z]);
    }
    
    @Override
    public ItemStack M() {
        return this.k;
    }
    
    @Override
    public int M() {
        return this.G;
    }
    
    @Override
    public List<Entity> D() {
        return this.M;
    }
    
    public Id(final ItemStack k, final int l, final int g, final int f, @Nonnull final String b) {
        super();
        this.H = new ArrayList<TileEntity>();
        this.M = new ArrayList<Entity>();
        if (b == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.k = k;
        this.A = new short[l][g][f];
        this.d = new byte[l][g][f];
        this.L = l;
        this.G = g;
        this.f = f;
        this.B = b;
    }
    
    @Nonnull
    @Override
    public String M() {
        return this.B;
    }
    
    @Override
    public int D() {
        return this.L;
    }
    
    static {
        b = new ItemStack((Block)Blocks.GRASS);
    }
    
    @Override
    public void M(final BlockPos blockPos) {
        final Iterator<TileEntity> iterator2;
        Iterator<TileEntity> iterator = iterator2 = this.H.iterator();
        while (iterator.hasNext()) {
            if (!iterator2.next().getPos().equals((Object)blockPos)) {
                iterator = iterator2;
            }
            else {
                (iterator = iterator2).remove();
            }
        }
    }
    
    @Override
    public void M(final Entity entity) {
        if (entity == null || entity.getUniqueID() == null) {
            return;
        }
        final Iterator<Entity> iterator2;
        Iterator<Entity> iterator = iterator2 = this.M.iterator();
        while (iterator.hasNext()) {
            if (!entity.getUniqueID().equals(iterator2.next().getUniqueID())) {
                iterator = iterator2;
            }
            else {
                (iterator = iterator2).remove();
            }
        }
    }
    
    @Override
    public boolean M(final BlockPos blockPos, final IBlockState blockState) {
        if (!this.M(blockPos)) {
            return false;
        }
        final Block block = blockState.getBlock();
        final int idForObject;
        if ((idForObject = Block.REGISTRY.getIDForObject((Object)block)) == -1) {
            return false;
        }
        final int metaFromState = block.getMetaFromState(blockState);
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.A[x][y][z] = (short)idForObject;
        this.d[x][y][z] = (byte)metaFromState;
        return true;
    }
    
    @Override
    public TileEntity M(final BlockPos blockPos) {
        final Iterator<TileEntity> iterator2;
        Iterator<TileEntity> iterator = iterator2 = this.H.iterator();
        while (iterator.hasNext()) {
            final TileEntity tileEntity;
            if ((tileEntity = iterator2.next()).getPos().equals((Object)blockPos)) {
                return tileEntity;
            }
            iterator = iterator2;
        }
        return null;
    }
    
    public Id(final ItemStack itemStack, final int n, final int n2, final int n3) {
        this(itemStack, n, n2, n3, "");
    }
    
    private boolean M(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        return x >= 0 && y >= 0 && z >= 0 && x < this.L && y < this.G && z < this.f;
    }
}
