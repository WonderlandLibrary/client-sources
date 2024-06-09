package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox
{
    private final MobSpawnerBaseLogic Âµá€;
    private static final String Ó = "CL_00000360";
    
    public TileEntityMobSpawner() {
        this.Âµá€ = new MobSpawnerBaseLogic() {
            private static final String Â = "CL_00000361";
            
            @Override
            public void HorizonCode_Horizon_È(final int p_98267_1_) {
                TileEntityMobSpawner.this.HorizonCode_Horizon_È.Ý(TileEntityMobSpawner.this.Â, Blocks.ÇªÓ, p_98267_1_, 0);
            }
            
            @Override
            public World HorizonCode_Horizon_È() {
                return TileEntityMobSpawner.this.HorizonCode_Horizon_È;
            }
            
            @Override
            public BlockPos Â() {
                return TileEntityMobSpawner.this.Â;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_98277_1_) {
                super.HorizonCode_Horizon_È(p_98277_1_);
                if (this.HorizonCode_Horizon_È() != null) {
                    this.HorizonCode_Horizon_È().áŒŠÆ(TileEntityMobSpawner.this.Â);
                }
            }
        };
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€.HorizonCode_Horizon_È(compound);
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        this.Âµá€.Â(compound);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Âµá€.Ý();
    }
    
    @Override
    public Packet £á() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.Â(var1);
        var1.Å("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.Â, 1, var1);
    }
    
    @Override
    public boolean Ý(final int id, final int type) {
        return this.Âµá€.Â(id) || super.Ý(id, type);
    }
    
    public MobSpawnerBaseLogic Â() {
        return this.Âµá€;
    }
}
