package net.minecraft.src;

public class MaterialTransparent extends Material
{
    public MaterialTransparent(final MapColor par1MapColor) {
        super(par1MapColor);
        this.setReplaceable();
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
