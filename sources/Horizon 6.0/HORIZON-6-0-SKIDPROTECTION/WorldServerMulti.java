package HORIZON-6-0-SKIDPROTECTION;

public class WorldServerMulti extends WorldServer
{
    private WorldServer É;
    private static final String áƒ = "CL_00001430";
    
    public WorldServerMulti(final MinecraftServer server, final ISaveHandler saveHandlerIn, final int dimensionId, final WorldServer delegate, final Profiler profilerIn) {
        super(server, saveHandlerIn, new DerivedWorldInfo(delegate.ŒÏ()), dimensionId, profilerIn);
        this.É = delegate;
        delegate.áŠ().HorizonCode_Horizon_È(new IBorderListener() {
            private static final String Â = "CL_00002273";
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final double newSize) {
                WorldServerMulti.this.áŠ().HorizonCode_Horizon_È(newSize);
            }
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final double p_177692_2_, final double p_177692_4_, final long p_177692_6_) {
                WorldServerMulti.this.áŠ().HorizonCode_Horizon_È(p_177692_2_, p_177692_4_, p_177692_6_);
            }
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final double x, final double z) {
                WorldServerMulti.this.áŠ().Â(x, z);
            }
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final int p_177691_2_) {
                WorldServerMulti.this.áŠ().Â(p_177691_2_);
            }
            
            @Override
            public void Â(final WorldBorder border, final int p_177690_2_) {
                WorldServerMulti.this.áŠ().Ý(p_177690_2_);
            }
            
            @Override
            public void Â(final WorldBorder border, final double p_177696_2_) {
                WorldServerMulti.this.áŠ().Ý(p_177696_2_);
            }
            
            @Override
            public void Ý(final WorldBorder border, final double p_177695_2_) {
                WorldServerMulti.this.áŠ().Â(p_177695_2_);
            }
        });
    }
    
    @Override
    protected void Ñ¢Â() throws MinecraftException {
    }
    
    @Override
    public World Ø() {
        this.Æ = this.É.ÇŽÕ();
        this.áŒŠà = this.É.à¢();
        final String var1 = VillageCollection.HorizonCode_Horizon_È(this.£à);
        final VillageCollection var2 = (VillageCollection)this.Æ.HorizonCode_Horizon_È(VillageCollection.class, var1);
        if (var2 == null) {
            this.Šáƒ = new VillageCollection(this);
            this.Æ.HorizonCode_Horizon_È(var1, this.Šáƒ);
        }
        else {
            (this.Šáƒ = var2).HorizonCode_Horizon_È(this);
        }
        return this;
    }
}
