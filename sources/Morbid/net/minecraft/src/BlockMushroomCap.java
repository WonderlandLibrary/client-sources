package net.minecraft.src;

import java.util.*;

public class BlockMushroomCap extends Block
{
    private static final String[] field_94429_a;
    private final int mushroomType;
    private Icon[] iconArray;
    private Icon field_94426_cO;
    private Icon field_94427_cP;
    
    static {
        field_94429_a = new String[] { "mushroom_skin_brown", "mushroom_skin_red" };
    }
    
    public BlockMushroomCap(final int par1, final Material par2Material, final int par3) {
        super(par1, par2Material);
        this.mushroomType = par3;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par2 == 10 && par1 > 1) ? this.field_94426_cO : ((par2 >= 1 && par2 <= 9 && par1 == 1) ? this.iconArray[this.mushroomType] : ((par2 >= 1 && par2 <= 3 && par1 == 2) ? this.iconArray[this.mushroomType] : ((par2 >= 7 && par2 <= 9 && par1 == 3) ? this.iconArray[this.mushroomType] : (((par2 == 1 || par2 == 4 || par2 == 7) && par1 == 4) ? this.iconArray[this.mushroomType] : (((par2 == 3 || par2 == 6 || par2 == 9) && par1 == 5) ? this.iconArray[this.mushroomType] : ((par2 == 14) ? this.iconArray[this.mushroomType] : ((par2 == 15) ? this.field_94426_cO : this.field_94427_cP)))))));
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        int var2 = par1Random.nextInt(10) - 7;
        if (var2 < 0) {
            var2 = 0;
        }
        return var2;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.mushroomBrown.blockID + this.mushroomType;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Block.mushroomBrown.blockID + this.mushroomType;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray = new Icon[BlockMushroomCap.field_94429_a.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockMushroomCap.field_94429_a[var2]);
        }
        this.field_94427_cP = par1IconRegister.registerIcon("mushroom_inside");
        this.field_94426_cO = par1IconRegister.registerIcon("mushroom_skin_stem");
    }
}
