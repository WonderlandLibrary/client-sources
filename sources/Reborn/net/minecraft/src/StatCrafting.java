package net.minecraft.src;

public class StatCrafting extends StatBase
{
    private final int itemID;
    
    public StatCrafting(final int par1, final String par2Str, final int par3) {
        super(par1, par2Str);
        this.itemID = par3;
    }
    
    public int getItemID() {
        return this.itemID;
    }
}
