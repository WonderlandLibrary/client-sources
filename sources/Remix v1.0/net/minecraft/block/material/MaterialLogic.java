package net.minecraft.block.material;

public class MaterialLogic extends Material
{
    public MaterialLogic(final MapColor p_i2112_1_) {
        super(p_i2112_1_);
        this.setAdventureModeExempt();
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
