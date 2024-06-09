package HORIZON-6-0-SKIDPROTECTION;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    private final MobSpawnerBaseLogic HorizonCode_Horizon_È;
    private static final String Â = "CL_00001678";
    
    public EntityMinecartMobSpawner(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = new MobSpawnerBaseLogic() {
            private static final String Â = "CL_00001679";
            
            @Override
            public void HorizonCode_Horizon_È(final int p_98267_1_) {
                EntityMinecartMobSpawner.this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
            }
            
            @Override
            public World HorizonCode_Horizon_È() {
                return EntityMinecartMobSpawner.this.Ï­Ðƒà;
            }
            
            @Override
            public BlockPos Â() {
                return new BlockPos(EntityMinecartMobSpawner.this);
            }
        };
    }
    
    public EntityMinecartMobSpawner(final World worldIn, final double p_i1726_2_, final double p_i1726_4_, final double p_i1726_6_) {
        super(worldIn, p_i1726_2_, p_i1726_4_, p_i1726_6_);
        this.HorizonCode_Horizon_È = new MobSpawnerBaseLogic() {
            private static final String Â = "CL_00001679";
            
            @Override
            public void HorizonCode_Horizon_È(final int p_98267_1_) {
                EntityMinecartMobSpawner.this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
            }
            
            @Override
            public World HorizonCode_Horizon_È() {
                return EntityMinecartMobSpawner.this.Ï­Ðƒà;
            }
            
            @Override
            public BlockPos Â() {
                return new BlockPos(EntityMinecartMobSpawner.this);
            }
        };
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.Âµá€;
    }
    
    @Override
    public IBlockState Ø() {
        return Blocks.ÇªÓ.¥à();
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tagCompund);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        this.HorizonCode_Horizon_È.Â(tagCompound);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        this.HorizonCode_Horizon_È.Â(p_70103_1_);
    }
    
    @Override
    public void á() {
        super.á();
        this.HorizonCode_Horizon_È.Ý();
    }
    
    public MobSpawnerBaseLogic áŒŠÆ() {
        return this.HorizonCode_Horizon_È;
    }
}
