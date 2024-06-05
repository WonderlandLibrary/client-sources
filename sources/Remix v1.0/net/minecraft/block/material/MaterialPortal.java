package net.minecraft.block.material;

public class MaterialPortal extends Material
{
    public MaterialPortal(final MapColor p_i2118_1_) {
        super(p_i2118_1_);
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public boolean blocksLight() {
        return false;
    }
    
    @Override
    public boolean blocksMovement() {
        return false;
    }
}
