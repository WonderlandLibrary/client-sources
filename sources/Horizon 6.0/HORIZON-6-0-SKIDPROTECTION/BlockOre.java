package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockOre extends Block
{
    private static final String Õ = "CL_00000282";
    
    public BlockOre() {
        super(Material.Âµá€);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return (this == Blocks.ˆà) ? Items.Ø : ((this == Blocks.£Ï) ? Items.áŒŠÆ : ((this == Blocks.áŒŠà) ? Items.áŒŠÔ : ((this == Blocks.µÐƒÓ) ? Items.µ : ((this == Blocks.ÐƒáˆºÄ) ? Items.ÇªÅ : Item_1028566121.HorizonCode_Horizon_È(this)))));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return (this == Blocks.áŒŠà) ? (4 + random.nextInt(5)) : 1;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int fortune, final Random random) {
        if (fortune > 0 && Item_1028566121.HorizonCode_Horizon_È(this) != this.HorizonCode_Horizon_È((IBlockState)this.ŠÂµà().HorizonCode_Horizon_È().iterator().next(), random, fortune)) {
            int var3 = random.nextInt(fortune + 2) - 1;
            if (var3 < 0) {
                var3 = 0;
            }
            return this.HorizonCode_Horizon_È(random) * (var3 + 1);
        }
        return this.HorizonCode_Horizon_È(random);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, chance, fortune);
        if (this.HorizonCode_Horizon_È(state, worldIn.Å, fortune) != Item_1028566121.HorizonCode_Horizon_È(this)) {
            int var6 = 0;
            if (this == Blocks.ˆà) {
                var6 = MathHelper.HorizonCode_Horizon_È(worldIn.Å, 0, 2);
            }
            else if (this == Blocks.£Ï) {
                var6 = MathHelper.HorizonCode_Horizon_È(worldIn.Å, 3, 7);
            }
            else if (this == Blocks.µÐƒÓ) {
                var6 = MathHelper.HorizonCode_Horizon_È(worldIn.Å, 3, 7);
            }
            else if (this == Blocks.áŒŠà) {
                var6 = MathHelper.HorizonCode_Horizon_È(worldIn.Å, 2, 5);
            }
            else if (this == Blocks.ÐƒáˆºÄ) {
                var6 = MathHelper.HorizonCode_Horizon_È(worldIn.Å, 2, 5);
            }
            this.HorizonCode_Horizon_È(worldIn, pos, var6);
        }
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        return 0;
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return (this == Blocks.áŒŠà) ? EnumDyeColor.á.Ý() : 0;
    }
}
