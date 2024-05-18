package net.minecraft.src;

import java.util.*;

public class BlockRedstoneLight extends Block
{
    private final boolean powered;
    
    public BlockRedstoneLight(final int par1, final boolean par2) {
        super(par1, Material.redstoneLight);
        this.powered = par2;
        if (par2) {
            this.setLightValue(1.0f);
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        if (this.powered) {
            this.blockIcon = par1IconRegister.registerIcon("redstoneLight_lit");
        }
        else {
            this.blockIcon = par1IconRegister.registerIcon("redstoneLight");
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.isRemote) {
            if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
            }
            else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                par1World.setBlock(par2, par3, par4, Block.redstoneLampActive.blockID, 0, 2);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
            }
            else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
                par1World.setBlock(par2, par3, par4, Block.redstoneLampActive.blockID, 0, 2);
            }
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote && this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
            par1World.setBlock(par2, par3, par4, Block.redstoneLampIdle.blockID, 0, 2);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.redstoneLampIdle.blockID;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Block.redstoneLampIdle.blockID;
    }
}
