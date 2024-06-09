package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public abstract class MobSpawnerBaseLogic
{
    private int HorizonCode_Horizon_È;
    private String Â;
    private final List Ý;
    private HorizonCode_Horizon_È Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private Entity áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private int ˆÏ­;
    private static final String £á = "CL_00000129";
    
    public MobSpawnerBaseLogic() {
        this.HorizonCode_Horizon_È = 20;
        this.Â = "Pig";
        this.Ý = Lists.newArrayList();
        this.à = 200;
        this.Ø = 800;
        this.áŒŠÆ = 4;
        this.ÂµÈ = 6;
        this.á = 16;
        this.ˆÏ­ = 4;
    }
    
    private String Ó() {
        if (this.áŒŠÆ() == null) {
            if (this.Â.equals("Minecart")) {
                this.Â = "MinecartRideable";
            }
            return this.Â;
        }
        return this.áŒŠÆ().Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final String p_98272_1_) {
        this.Â = p_98272_1_;
    }
    
    private boolean à() {
        final BlockPos var1 = this.Â();
        return this.HorizonCode_Horizon_È().Â(var1.HorizonCode_Horizon_È() + 0.5, var1.Â() + 0.5, var1.Ý() + 0.5, this.á);
    }
    
    public void Ý() {
        if (this.à()) {
            final BlockPos var1 = this.Â();
            if (this.HorizonCode_Horizon_È().ŠÄ) {
                final double var2 = var1.HorizonCode_Horizon_È() + this.HorizonCode_Horizon_È().Å.nextFloat();
                final double var3 = var1.Â() + this.HorizonCode_Horizon_È().Å.nextFloat();
                final double var4 = var1.Ý() + this.HorizonCode_Horizon_È().Å.nextFloat();
                this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(EnumParticleTypes.á, var2, var3, var4, 0.0, 0.0, 0.0, new int[0]);
                this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var2, var3, var4, 0.0, 0.0, 0.0, new int[0]);
                if (this.HorizonCode_Horizon_È > 0) {
                    --this.HorizonCode_Horizon_È;
                }
                this.Ó = this.Âµá€;
                this.Âµá€ = (this.Âµá€ + 1000.0f / (this.HorizonCode_Horizon_È + 200.0f)) % 360.0;
            }
            else {
                if (this.HorizonCode_Horizon_È == -1) {
                    this.Ø();
                }
                if (this.HorizonCode_Horizon_È > 0) {
                    --this.HorizonCode_Horizon_È;
                    return;
                }
                boolean var5 = false;
                for (int var6 = 0; var6 < this.áŒŠÆ; ++var6) {
                    final Entity var7 = EntityList.HorizonCode_Horizon_È(this.Ó(), this.HorizonCode_Horizon_È());
                    if (var7 == null) {
                        return;
                    }
                    final int var8 = this.HorizonCode_Horizon_È().HorizonCode_Horizon_È(var7.getClass(), new AxisAlignedBB(var1.HorizonCode_Horizon_È(), var1.Â(), var1.Ý(), var1.HorizonCode_Horizon_È() + 1, var1.Â() + 1, var1.Ý() + 1).Â(this.ˆÏ­, this.ˆÏ­, this.ˆÏ­)).size();
                    if (var8 >= this.ÂµÈ) {
                        this.Ø();
                        return;
                    }
                    final double var4 = var1.HorizonCode_Horizon_È() + (this.HorizonCode_Horizon_È().Å.nextDouble() - this.HorizonCode_Horizon_È().Å.nextDouble()) * this.ˆÏ­ + 0.5;
                    final double var9 = var1.Â() + this.HorizonCode_Horizon_È().Å.nextInt(3) - 1;
                    final double var10 = var1.Ý() + (this.HorizonCode_Horizon_È().Å.nextDouble() - this.HorizonCode_Horizon_È().Å.nextDouble()) * this.ˆÏ­ + 0.5;
                    final EntityLiving var11 = (var7 instanceof EntityLiving) ? ((EntityLiving)var7) : null;
                    var7.Â(var4, var9, var10, this.HorizonCode_Horizon_È().Å.nextFloat() * 360.0f, 0.0f);
                    if (var11 == null || (var11.µà() && var11.ÐƒÂ())) {
                        this.HorizonCode_Horizon_È(var7, true);
                        this.HorizonCode_Horizon_È().Â(2004, var1, 0);
                        if (var11 != null) {
                            var11.£Ø­à();
                        }
                        var5 = true;
                    }
                }
                if (var5) {
                    this.Ø();
                }
            }
        }
    }
    
    private Entity HorizonCode_Horizon_È(final Entity p_180613_1_, final boolean p_180613_2_) {
        if (this.áŒŠÆ() != null) {
            NBTTagCompound var3 = new NBTTagCompound();
            p_180613_1_.Ø­áŒŠá(var3);
            for (final String var5 : this.áŒŠÆ().Â.Âµá€()) {
                final NBTBase var6 = this.áŒŠÆ().Â.HorizonCode_Horizon_È(var5);
                var3.HorizonCode_Horizon_È(var5, var6.Â());
            }
            p_180613_1_.Ó(var3);
            if (p_180613_1_.Ï­Ðƒà != null && p_180613_2_) {
                p_180613_1_.Ï­Ðƒà.HorizonCode_Horizon_È(p_180613_1_);
            }
            Entity var7 = p_180613_1_;
            while (var3.Â("Riding", 10)) {
                final NBTTagCompound var8 = var3.ˆÏ­("Riding");
                final Entity var9 = EntityList.HorizonCode_Horizon_È(var8.áˆºÑ¢Õ("id"), p_180613_1_.Ï­Ðƒà);
                if (var9 != null) {
                    final NBTTagCompound var10 = new NBTTagCompound();
                    var9.Ø­áŒŠá(var10);
                    for (final String var12 : var8.Âµá€()) {
                        final NBTBase var13 = var8.HorizonCode_Horizon_È(var12);
                        var10.HorizonCode_Horizon_È(var12, var13.Â());
                    }
                    var9.Ó(var10);
                    var9.Â(var7.ŒÏ, var7.Çªà¢, var7.Ê, var7.É, var7.áƒ);
                    if (p_180613_1_.Ï­Ðƒà != null && p_180613_2_) {
                        p_180613_1_.Ï­Ðƒà.HorizonCode_Horizon_È(var9);
                    }
                    var7.HorizonCode_Horizon_È(var9);
                }
                var7 = var9;
                var3 = var8;
            }
        }
        else if (p_180613_1_ instanceof EntityLivingBase && p_180613_1_.Ï­Ðƒà != null && p_180613_2_) {
            ((EntityLiving)p_180613_1_).HorizonCode_Horizon_È(p_180613_1_.Ï­Ðƒà.Ê(new BlockPos(p_180613_1_)), null);
            p_180613_1_.Ï­Ðƒà.HorizonCode_Horizon_È(p_180613_1_);
        }
        return p_180613_1_;
    }
    
    private void Ø() {
        if (this.Ø <= this.à) {
            this.HorizonCode_Horizon_È = this.à;
        }
        else {
            final int var10003 = this.Ø - this.à;
            this.HorizonCode_Horizon_È = this.à + this.HorizonCode_Horizon_È().Å.nextInt(var10003);
        }
        if (this.Ý.size() > 0) {
            this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)WeightedRandom.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È().Å, this.Ý));
        }
        this.HorizonCode_Horizon_È(1);
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_98270_1_) {
        this.Â = p_98270_1_.áˆºÑ¢Õ("EntityId");
        this.HorizonCode_Horizon_È = p_98270_1_.Âµá€("Delay");
        this.Ý.clear();
        if (p_98270_1_.Â("SpawnPotentials", 9)) {
            final NBTTagList var2 = p_98270_1_.Ý("SpawnPotentials", 10);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                this.Ý.add(new HorizonCode_Horizon_È(var2.Â(var3)));
            }
        }
        if (p_98270_1_.Â("SpawnData", 10)) {
            this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(p_98270_1_.ˆÏ­("SpawnData"), this.Â));
        }
        else {
            this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)null);
        }
        if (p_98270_1_.Â("MinSpawnDelay", 99)) {
            this.à = p_98270_1_.Âµá€("MinSpawnDelay");
            this.Ø = p_98270_1_.Âµá€("MaxSpawnDelay");
            this.áŒŠÆ = p_98270_1_.Âµá€("SpawnCount");
        }
        if (p_98270_1_.Â("MaxNearbyEntities", 99)) {
            this.ÂµÈ = p_98270_1_.Âµá€("MaxNearbyEntities");
            this.á = p_98270_1_.Âµá€("RequiredPlayerRange");
        }
        if (p_98270_1_.Â("SpawnRange", 99)) {
            this.ˆÏ­ = p_98270_1_.Âµá€("SpawnRange");
        }
        if (this.HorizonCode_Horizon_È() != null) {
            this.áˆºÑ¢Õ = null;
        }
    }
    
    public void Â(final NBTTagCompound p_98280_1_) {
        p_98280_1_.HorizonCode_Horizon_È("EntityId", this.Ó());
        p_98280_1_.HorizonCode_Horizon_È("Delay", (short)this.HorizonCode_Horizon_È);
        p_98280_1_.HorizonCode_Horizon_È("MinSpawnDelay", (short)this.à);
        p_98280_1_.HorizonCode_Horizon_È("MaxSpawnDelay", (short)this.Ø);
        p_98280_1_.HorizonCode_Horizon_È("SpawnCount", (short)this.áŒŠÆ);
        p_98280_1_.HorizonCode_Horizon_È("MaxNearbyEntities", (short)this.ÂµÈ);
        p_98280_1_.HorizonCode_Horizon_È("RequiredPlayerRange", (short)this.á);
        p_98280_1_.HorizonCode_Horizon_È("SpawnRange", (short)this.ˆÏ­);
        if (this.áŒŠÆ() != null) {
            p_98280_1_.HorizonCode_Horizon_È("SpawnData", this.áŒŠÆ().Â.Â());
        }
        if (this.áŒŠÆ() != null || this.Ý.size() > 0) {
            final NBTTagList var2 = new NBTTagList();
            if (this.Ý.size() > 0) {
                for (final HorizonCode_Horizon_È var4 : this.Ý) {
                    var2.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È());
                }
            }
            else {
                var2.HorizonCode_Horizon_È(this.áŒŠÆ().HorizonCode_Horizon_È());
            }
            p_98280_1_.HorizonCode_Horizon_È("SpawnPotentials", var2);
        }
    }
    
    public Entity HorizonCode_Horizon_È(final World worldIn) {
        if (this.áˆºÑ¢Õ == null) {
            Entity var2 = EntityList.HorizonCode_Horizon_È(this.Ó(), worldIn);
            if (var2 != null) {
                var2 = this.HorizonCode_Horizon_È(var2, false);
                this.áˆºÑ¢Õ = var2;
            }
        }
        return this.áˆºÑ¢Õ;
    }
    
    public boolean Â(final int p_98268_1_) {
        if (p_98268_1_ == 1 && this.HorizonCode_Horizon_È().ŠÄ) {
            this.HorizonCode_Horizon_È = this.à;
            return true;
        }
        return false;
    }
    
    private HorizonCode_Horizon_È áŒŠÆ() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_98277_1_) {
        this.Ø­áŒŠá = p_98277_1_;
    }
    
    public abstract void HorizonCode_Horizon_È(final int p0);
    
    public abstract World HorizonCode_Horizon_È();
    
    public abstract BlockPos Â();
    
    public double Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public double Âµá€() {
        return this.Ó;
    }
    
    public class HorizonCode_Horizon_È extends WeightedRandom.HorizonCode_Horizon_È
    {
        private final NBTTagCompound Â;
        private final String Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000130";
        
        public HorizonCode_Horizon_È(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final NBTTagCompound p_i1945_2_) {
            this(mobSpawnerBaseLogic, p_i1945_2_.ˆÏ­("Properties"), p_i1945_2_.áˆºÑ¢Õ("Type"), p_i1945_2_.Ó("Weight"));
        }
        
        public HorizonCode_Horizon_È(final MobSpawnerBaseLogic mobSpawnerBaseLogic, final NBTTagCompound p_i1946_2_, final String p_i1946_3_) {
            this(mobSpawnerBaseLogic, p_i1946_2_, p_i1946_3_, 1);
        }
        
        private HorizonCode_Horizon_È(final NBTTagCompound p_i45757_2_, String p_i45757_3_, final int p_i45757_4_) {
            super(p_i45757_4_);
            if (p_i45757_3_.equals("Minecart")) {
                if (p_i45757_2_ != null) {
                    p_i45757_3_ = EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i45757_2_.Ó("Type")).Â();
                }
                else {
                    p_i45757_3_ = "MinecartRideable";
                }
            }
            this.Â = p_i45757_2_;
            this.Ø­áŒŠá = p_i45757_3_;
        }
        
        public NBTTagCompound HorizonCode_Horizon_È() {
            final NBTTagCompound var1 = new NBTTagCompound();
            var1.HorizonCode_Horizon_È("Properties", this.Â);
            var1.HorizonCode_Horizon_È("Type", this.Ø­áŒŠá);
            var1.HorizonCode_Horizon_È("Weight", this.Ý);
            return var1;
        }
    }
}
