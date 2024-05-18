package HORIZON-6-0-SKIDPROTECTION;

public class BlockSlime extends BlockBreakable
{
    private static final String Õ = "CL_00002063";
    
    public BlockSlime() {
        super(Material.ŒÏ, false);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.áƒ = 0.8f;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance) {
        if (entityIn.Çªà¢()) {
            super.HorizonCode_Horizon_È(worldIn, pos, entityIn, fallDistance);
        }
        else {
            entityIn.Ø­áŒŠá(fallDistance, 0.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Entity entityIn) {
        if (entityIn.Çªà¢()) {
            super.HorizonCode_Horizon_È(worldIn, entityIn);
        }
        else if (entityIn.ˆá < 0.0) {
            entityIn.ˆá = -entityIn.ˆá;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn) {
        if (Math.abs(entityIn.ˆá) < 0.1 && !entityIn.Çªà¢()) {
            final double var4 = 0.4 + Math.abs(entityIn.ˆá) * 0.2;
            entityIn.ÇŽÉ *= var4;
            entityIn.ÇŽÕ *= var4;
        }
        super.HorizonCode_Horizon_È(worldIn, pos, entityIn);
    }
}
