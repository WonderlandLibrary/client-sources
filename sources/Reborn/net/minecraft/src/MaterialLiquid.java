package net.minecraft.src;

public class MaterialLiquid extends Material
{
    public MaterialLiquid(final MapColor par1MapColor) {
        super(par1MapColor);
        this.setReplaceable();
        this.setNoPushMobility();
    }
    
    @Override
    public boolean isLiquid() {
        return true;
    }
    
    @Override
    public boolean blocksMovement() {
        return false;
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
}
