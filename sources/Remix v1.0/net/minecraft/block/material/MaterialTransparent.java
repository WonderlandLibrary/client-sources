package net.minecraft.block.material;

public class MaterialTransparent extends Material
{
    public MaterialTransparent(final MapColor p_i2113_1_) {
        super(p_i2113_1_);
        this.setReplaceable();
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
