package net.minecraft.src;

public class MaterialLogic extends Material
{
    public MaterialLogic(final MapColor par1MapColor) {
        super(par1MapColor);
        this.setAlwaysHarvested();
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public boolean getCanBlockGrass() {
        return false;
    }
    
    @Override
    public boolean blocksMovement() {
        return false;
    }
}
