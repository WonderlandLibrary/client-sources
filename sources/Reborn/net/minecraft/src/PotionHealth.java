package net.minecraft.src;

public class PotionHealth extends Potion
{
    public PotionHealth(final int par1, final boolean par2, final int par3) {
        super(par1, par2, par3);
    }
    
    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public boolean isReady(final int par1, final int par2) {
        return par1 >= 1;
    }
}
