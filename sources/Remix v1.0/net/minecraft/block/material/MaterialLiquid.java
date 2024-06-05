package net.minecraft.block.material;

public class MaterialLiquid extends Material
{
    public MaterialLiquid(final MapColor p_i2114_1_) {
        super(p_i2114_1_);
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
