package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.google.common.base.Predicate;

public class EntityGuardian extends EntityMob
{
    private float Â;
    private float Ý;
    private float Ø­Ñ¢Ï­Ø­áˆº;
    private float ŒÂ;
    private float Ï­Ï;
    private EntityLivingBase ŠØ;
    private int ˆÐƒØ;
    private boolean Çªà;
    private EntityAIWander ¥Å;
    private static final String Œáƒ = "CL_00002213";
    
    public EntityGuardian(final World worldIn) {
        super(worldIn);
        this.à = 10;
        this.HorizonCode_Horizon_È(0.85f, 0.85f);
        this.ÂµÈ.HorizonCode_Horizon_È(4, new HorizonCode_Horizon_È());
        final EntityAIMoveTowardsRestriction var2;
        this.ÂµÈ.HorizonCode_Horizon_È(5, var2 = new EntityAIMoveTowardsRestriction(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, this.¥Å = new EntityAIWander(this, 1.0, 80));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0f, 0.01f));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAILookIdle(this));
        this.¥Å.HorizonCode_Horizon_È(3);
        var2.HorizonCode_Horizon_È(3);
        this.á.HorizonCode_Horizon_È(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, (Predicate)new Ý()));
        this.Ø = new Â();
        final float nextFloat = this.ˆáƒ.nextFloat();
        this.Â = nextFloat;
        this.Ý = nextFloat;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(6.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.5);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(16.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(30.0);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.HorizonCode_Horizon_È(tagCompund.£á("Elder"));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Elder", this.¥Ðƒá());
    }
    
    @Override
    protected PathNavigate Â(final World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)0);
        this.£Ó.HorizonCode_Horizon_È(17, (Object)0);
    }
    
    private boolean HorizonCode_Horizon_È(final int p_175468_1_) {
        return (this.£Ó.Ý(16) & p_175468_1_) != 0x0;
    }
    
    private void Â(final int p_175473_1_, final boolean p_175473_2_) {
        final int var3 = this.£Ó.Ý(16);
        if (p_175473_2_) {
            this.£Ó.Â(16, var3 | p_175473_1_);
        }
        else {
            this.£Ó.Â(16, var3 & ~p_175473_1_);
        }
    }
    
    public boolean Ø() {
        return this.HorizonCode_Horizon_È(2);
    }
    
    private void á(final boolean p_175476_1_) {
        this.Â(2, p_175476_1_);
    }
    
    public int ÇŽÅ() {
        return this.¥Ðƒá() ? 60 : 80;
    }
    
    public boolean ¥Ðƒá() {
        return this.HorizonCode_Horizon_È(4);
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175467_1_) {
        this.Â(4, p_175467_1_);
        if (p_175467_1_) {
            this.HorizonCode_Horizon_È(1.9975f, 1.9975f);
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.30000001192092896);
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(8.0);
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(80.0);
            this.ˆÈ();
            this.¥Å.Â(400);
        }
    }
    
    public void ÐƒÇŽà() {
        this.HorizonCode_Horizon_È(true);
        final float n = 1.0f;
        this.ŒÂ = n;
        this.Ï­Ï = n;
    }
    
    private void Â(final int p_175463_1_) {
        this.£Ó.Â(17, p_175463_1_);
    }
    
    public boolean ¥Ê() {
        return this.£Ó.Ý(17) != 0;
    }
    
    public EntityLivingBase ÐƒÓ() {
        if (!this.¥Ê()) {
            return null;
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            return this.Ñ¢Ó();
        }
        if (this.ŠØ != null) {
            return this.ŠØ;
        }
        final Entity var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£Ó.Ý(17));
        if (var1 instanceof EntityLivingBase) {
            return this.ŠØ = (EntityLivingBase)var1;
        }
        return null;
    }
    
    @Override
    public void áˆºÑ¢Õ(final int p_145781_1_) {
        super.áˆºÑ¢Õ(p_145781_1_);
        if (p_145781_1_ == 16) {
            if (this.¥Ðƒá() && this.áŒŠ < 1.0f) {
                this.HorizonCode_Horizon_È(1.9975f, 1.9975f);
            }
        }
        else if (p_145781_1_ == 17) {
            this.ˆÐƒØ = 0;
            this.ŠØ = null;
        }
    }
    
    @Override
    public int áŒŠÔ() {
        return 160;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return this.£ÂµÄ() ? (this.¥Ðƒá() ? "mob.guardian.elder.idle" : "mob.guardian.idle") : "mob.guardian.land.idle";
    }
    
    @Override
    protected String ¥áŠ() {
        return this.£ÂµÄ() ? (this.¥Ðƒá() ? "mob.guardian.elder.hit" : "mob.guardian.hit") : "mob.guardian.land.hit";
    }
    
    @Override
    protected String µÊ() {
        return this.£ÂµÄ() ? (this.¥Ðƒá() ? "mob.guardian.elder.death" : "mob.guardian.death") : "mob.guardian.land.death";
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ * 0.5f;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final BlockPos p_180484_1_) {
        return (this.Ï­Ðƒà.Â(p_180484_1_).Ý().Ó() == Material.Ø) ? (10.0f + this.Ï­Ðƒà.£à(p_180484_1_) - 0.5f) : super.HorizonCode_Horizon_È(p_180484_1_);
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ï­Ðƒà.ŠÄ) {
            this.Ý = this.Â;
            if (!this.£ÂµÄ()) {
                this.Ø­Ñ¢Ï­Ø­áˆº = 2.0f;
                if (this.ˆá > 0.0 && this.Çªà && !this.áŠ()) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, "mob.guardian.flop", 1.0f, 1.0f, false);
                }
                this.Çªà = (this.ˆá < 0.0 && this.Ï­Ðƒà.Ø­áŒŠá(new BlockPos(this).Âµá€(), false));
            }
            else if (this.Ø()) {
                if (this.Ø­Ñ¢Ï­Ø­áˆº < 0.5f) {
                    this.Ø­Ñ¢Ï­Ø­áˆº = 4.0f;
                }
                else {
                    this.Ø­Ñ¢Ï­Ø­áˆº += (0.5f - this.Ø­Ñ¢Ï­Ø­áˆº) * 0.1f;
                }
            }
            else {
                this.Ø­Ñ¢Ï­Ø­áˆº += (0.125f - this.Ø­Ñ¢Ï­Ø­áˆº) * 0.2f;
            }
            this.Â += this.Ø­Ñ¢Ï­Ø­áˆº;
            this.Ï­Ï = this.ŒÂ;
            if (!this.£ÂµÄ()) {
                this.ŒÂ = this.ˆáƒ.nextFloat();
            }
            else if (this.Ø()) {
                this.ŒÂ += (0.0f - this.ŒÂ) * 0.25f;
            }
            else {
                this.ŒÂ += (1.0f - this.ŒÂ) * 0.06f;
            }
            if (this.Ø() && this.£ÂµÄ()) {
                final Vec3 var1 = this.Ó(0.0f);
                for (int var2 = 0; var2 < 2; ++var2) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ - var1.HorizonCode_Horizon_È * 1.5, this.Çªà¢ + this.ˆáƒ.nextDouble() * this.£ÂµÄ - var1.Â * 1.5, this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ - var1.Ý * 1.5, 0.0, 0.0, 0.0, new int[0]);
                }
            }
            if (this.¥Ê()) {
                if (this.ˆÐƒØ < this.ÇŽÅ()) {
                    ++this.ˆÐƒØ;
                }
                final EntityLivingBase var3 = this.ÐƒÓ();
                if (var3 != null) {
                    this.Ñ¢á().HorizonCode_Horizon_È(var3, 90.0f, 90.0f);
                    this.Ñ¢á().HorizonCode_Horizon_È();
                    final double var4 = this.Å(0.0f);
                    double var5 = var3.ŒÏ - this.ŒÏ;
                    double var6 = var3.Çªà¢ + var3.£ÂµÄ * 0.5f - (this.Çªà¢ + this.Ðƒáƒ());
                    double var7 = var3.Ê - this.Ê;
                    final double var8 = Math.sqrt(var5 * var5 + var6 * var6 + var7 * var7);
                    var5 /= var8;
                    var6 /= var8;
                    var7 /= var8;
                    double var9 = this.ˆáƒ.nextDouble();
                    while (var9 < var8) {
                        var9 += 1.8 - var4 + this.ˆáƒ.nextDouble() * (1.7 - var4);
                        this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ + var5 * var9, this.Çªà¢ + var6 * var9 + this.Ðƒáƒ(), this.Ê + var7 * var9, 0.0, 0.0, 0.0, new int[0]);
                    }
                }
            }
        }
        if (this.Ø­á) {
            this.Ø(300);
        }
        else if (this.ŠÂµà) {
            this.ˆá += 0.5;
            this.ÇŽÉ += (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.ÇŽÕ += (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.É = this.ˆáƒ.nextFloat() * 360.0f;
            this.ŠÂµà = false;
            this.áŒŠÏ = true;
        }
        if (this.¥Ê()) {
            this.É = this.ÂµÕ;
        }
        super.ˆÏ­();
    }
    
    public float Ý(final float p_175471_1_) {
        return this.Ý + (this.Â - this.Ý) * p_175471_1_;
    }
    
    public float £á(final float p_175469_1_) {
        return this.Ï­Ï + (this.ŒÂ - this.Ï­Ï) * p_175469_1_;
    }
    
    public float Å(final float p_175477_1_) {
        return (this.ˆÐƒØ + p_175477_1_) / this.ÇŽÅ();
    }
    
    @Override
    protected void ˆØ() {
        super.ˆØ();
        if (this.¥Ðƒá()) {
            final boolean var1 = true;
            final boolean var2 = true;
            final boolean var3 = true;
            final boolean var4 = true;
            if ((this.Œ + this.ˆá()) % 1200 == 0) {
                final Potion var5 = Potion.Ó;
                final List var6 = this.Ï­Ðƒà.Â(EntityPlayerMP.class, (Predicate)new Predicate() {
                    private static final String Â = "CL_00002212";
                    
                    public boolean HorizonCode_Horizon_È(final EntityPlayerMP p_179913_1_) {
                        return EntityGuardian.this.Âµá€(p_179913_1_) < 2500.0 && p_179913_1_.Ý.Â();
                    }
                    
                    public boolean apply(final Object p_apply_1_) {
                        return this.HorizonCode_Horizon_È((EntityPlayerMP)p_apply_1_);
                    }
                });
                for (final EntityPlayerMP var8 : var6) {
                    if (!var8.HorizonCode_Horizon_È(var5) || var8.Â(var5).Ý() < 2 || var8.Â(var5).Â() < 1200) {
                        var8.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(10, 0.0f));
                        var8.HorizonCode_Horizon_È(new PotionEffect(var5.É, 6000, 2));
                    }
                }
            }
            if (!this.Šáƒ()) {
                this.HorizonCode_Horizon_È(new BlockPos(this), 16);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        final int var3 = this.ˆáƒ.nextInt(3) + this.ˆáƒ.nextInt(p_70628_2_ + 1);
        if (var3 > 0) {
            this.HorizonCode_Horizon_È(new ItemStack(Items.ŠÂµÏ, var3, 0), 1.0f);
        }
        if (this.ˆáƒ.nextInt(3 + p_70628_2_) > 1) {
            this.HorizonCode_Horizon_È(new ItemStack(Items.Ñ¢Ó, 1, ItemFishFood.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È()), 1.0f);
        }
        else if (this.ˆáƒ.nextInt(3 + p_70628_2_) > 1) {
            this.HorizonCode_Horizon_È(new ItemStack(Items.Ðƒ, 1, 0), 1.0f);
        }
        if (p_70628_1_ && this.¥Ðƒá()) {
            this.HorizonCode_Horizon_È(new ItemStack(Blocks.Šáƒ, 1, 1), 1.0f);
        }
    }
    
    @Override
    protected void áˆºáˆºáŠ() {
        final ItemStack var1 = ((WeightedRandomFishable)WeightedRandom.HorizonCode_Horizon_È(this.ˆáƒ, EntityFishHook.à())).HorizonCode_Horizon_È(this.ˆáƒ);
        this.HorizonCode_Horizon_È(var1, 1.0f);
    }
    
    @Override
    protected boolean w_() {
        return true;
    }
    
    @Override
    public boolean ÐƒÂ() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), this) && this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty();
    }
    
    @Override
    public boolean µà() {
        return (this.ˆáƒ.nextInt(20) == 0 || !this.Ï­Ðƒà.ÂµÈ(new BlockPos(this))) && super.µà();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (!this.Ø() && !source.¥Æ() && source.áŒŠÆ() instanceof EntityLivingBase) {
            final EntityLivingBase var3 = (EntityLivingBase)source.áŒŠÆ();
            if (!source.Ý()) {
                var3.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È((Entity)this), 2.0f);
                var3.HorizonCode_Horizon_È("damage.thorns", 0.5f, 1.0f);
            }
        }
        this.¥Å.Ø();
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public int áˆºà() {
        return 180;
    }
    
    @Override
    public void Ó(final float p_70612_1_, final float p_70612_2_) {
        if (this.ŠÄ()) {
            if (this.£ÂµÄ()) {
                this.Â(p_70612_1_, p_70612_2_, 0.1f);
                this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
                this.ÇŽÉ *= 0.8999999761581421;
                this.ˆá *= 0.8999999761581421;
                this.ÇŽÕ *= 0.8999999761581421;
                if (!this.Ø() && this.Ñ¢Ó() == null) {
                    this.ˆá -= 0.005;
                }
            }
            else {
                super.Ó(p_70612_1_, p_70612_2_);
            }
        }
        else {
            super.Ó(p_70612_1_, p_70612_2_);
        }
    }
    
    class HorizonCode_Horizon_È extends EntityAIBase
    {
        private EntityGuardian Â;
        private int Ý;
        private static final String Ø­áŒŠá = "CL_00002211";
        
        public HorizonCode_Horizon_È() {
            this.Â = EntityGuardian.this;
            this.HorizonCode_Horizon_È(3);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            final EntityLivingBase var1 = this.Â.Ñ¢Ó();
            return var1 != null && var1.Œ();
        }
        
        @Override
        public boolean Â() {
            return super.Â() && (this.Â.¥Ðƒá() || this.Â.Âµá€(this.Â.Ñ¢Ó()) > 9.0);
        }
        
        @Override
        public void Âµá€() {
            this.Ý = -10;
            this.Â.Š().à();
            this.Â.Ñ¢á().HorizonCode_Horizon_È(this.Â.Ñ¢Ó(), 90.0f, 90.0f);
            this.Â.áŒŠÏ = true;
        }
        
        @Override
        public void Ý() {
            this.Â.Â(0);
            this.Â.Â((EntityLivingBase)null);
            this.Â.¥Å.Ø();
        }
        
        @Override
        public void Ø­áŒŠá() {
            final EntityLivingBase var1 = this.Â.Ñ¢Ó();
            this.Â.Š().à();
            this.Â.Ñ¢á().HorizonCode_Horizon_È(var1, 90.0f, 90.0f);
            if (!this.Â.µà(var1)) {
                this.Â.Â((EntityLivingBase)null);
            }
            else {
                ++this.Ý;
                if (this.Ý == 0) {
                    this.Â.Â(this.Â.Ñ¢Ó().ˆá());
                    this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(this.Â, (byte)21);
                }
                else if (this.Ý >= this.Â.ÇŽÅ()) {
                    float var2 = 1.0f;
                    if (this.Â.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) {
                        var2 += 2.0f;
                    }
                    if (this.Â.¥Ðƒá()) {
                        var2 += 2.0f;
                    }
                    var1.HorizonCode_Horizon_È(DamageSource.Â(this.Â, this.Â), var2);
                    var1.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this.Â), (float)this.Â.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).Âµá€());
                    this.Â.Â((EntityLivingBase)null);
                }
                else if (this.Ý < 60 || this.Ý % 20 == 0) {}
                super.Ø­áŒŠá();
            }
        }
    }
    
    class Â extends EntityMoveHelper
    {
        private EntityGuardian Ø;
        private static final String áŒŠÆ = "CL_00002209";
        
        public Â() {
            super(EntityGuardian.this);
            this.Ø = EntityGuardian.this;
        }
        
        @Override
        public void Ý() {
            if (this.Ó && !this.Ø.Š().Ó()) {
                final double var1 = this.Â - this.Ø.ŒÏ;
                double var2 = this.Ý - this.Ø.Çªà¢;
                final double var3 = this.Ø­áŒŠá - this.Ø.Ê;
                double var4 = var1 * var1 + var2 * var2 + var3 * var3;
                var4 = MathHelper.HorizonCode_Horizon_È(var4);
                var2 /= var4;
                final float var5 = (float)(Math.atan2(var3, var1) * 180.0 / 3.141592653589793) - 90.0f;
                this.Ø.É = this.HorizonCode_Horizon_È(this.Ø.É, var5, 30.0f);
                this.Ø.¥É = this.Ø.É;
                final float var6 = (float)(this.Âµá€ * this.Ø.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Âµá€());
                this.Ø.áŒŠÆ(this.Ø.áˆºá() + (var6 - this.Ø.áˆºá()) * 0.125f);
                double var7 = Math.sin((this.Ø.Œ + this.Ø.ˆá()) * 0.5) * 0.05;
                final double var8 = Math.cos(this.Ø.É * 3.1415927f / 180.0f);
                final double var9 = Math.sin(this.Ø.É * 3.1415927f / 180.0f);
                final EntityGuardian ø = this.Ø;
                ø.ÇŽÉ += var7 * var8;
                final EntityGuardian ø2 = this.Ø;
                ø2.ÇŽÕ += var7 * var9;
                var7 = Math.sin((this.Ø.Œ + this.Ø.ˆá()) * 0.75) * 0.05;
                final EntityGuardian ø3 = this.Ø;
                ø3.ˆá += var7 * (var9 + var8) * 0.25;
                final EntityGuardian ø4 = this.Ø;
                ø4.ˆá += this.Ø.áˆºá() * var2 * 0.1;
                final EntityLookHelper var10 = this.Ø.Ñ¢á();
                final double var11 = this.Ø.ŒÏ + var1 / var4 * 2.0;
                final double var12 = this.Ø.Ðƒáƒ() + this.Ø.Çªà¢ + var2 / var4 * 1.0;
                final double var13 = this.Ø.Ê + var3 / var4 * 2.0;
                double var14 = var10.Ý();
                double var15 = var10.Ø­áŒŠá();
                double var16 = var10.Âµá€();
                if (!var10.Â()) {
                    var14 = var11;
                    var15 = var12;
                    var16 = var13;
                }
                this.Ø.Ñ¢á().HorizonCode_Horizon_È(var14 + (var11 - var14) * 0.125, var15 + (var12 - var15) * 0.125, var16 + (var13 - var16) * 0.125, 10.0f, 40.0f);
                this.Ø.á(true);
            }
            else {
                this.Ø.áŒŠÆ(0.0f);
                this.Ø.á(false);
            }
        }
    }
    
    class Ý implements Predicate
    {
        private EntityGuardian Â;
        private static final String Ý = "CL_00002210";
        
        Ý() {
            this.Â = EntityGuardian.this;
        }
        
        public boolean HorizonCode_Horizon_È(final EntityLivingBase p_179915_1_) {
            return (p_179915_1_ instanceof EntityPlayer || p_179915_1_ instanceof EntitySquid) && p_179915_1_.Âµá€(this.Â) > 9.0;
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((EntityLivingBase)p_apply_1_);
        }
    }
}
