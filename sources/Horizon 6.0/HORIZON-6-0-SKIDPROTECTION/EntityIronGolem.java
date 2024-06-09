package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class EntityIronGolem extends EntityGolem
{
    private int Â;
    Village HorizonCode_Horizon_È;
    private int Ý;
    private int Ø­Ñ¢Ï­Ø­áˆº;
    private static final String ŒÂ = "CL_00001652";
    
    public EntityIronGolem(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(1.4f, 2.9f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIAttackOnCollide(this, 1.0, true));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIMoveTowardsTarget(this, 0.9, 32.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIMoveThroughVillage(this, 0.6, true));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAILookAtVillager(this));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWander(this, 0.6));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIDefendVillage(this));
        this.á.HorizonCode_Horizon_È(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.á.HorizonCode_Horizon_È(3, new HorizonCode_Horizon_È(this, EntityLiving.class, 10, false, true, IMob.b_));
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)0);
    }
    
    @Override
    protected void ˆØ() {
        final int â = this.Â - 1;
        this.Â = â;
        if (â <= 0) {
            this.Â = 70 + this.ˆáƒ.nextInt(50);
            this.HorizonCode_Horizon_È = this.Ï­Ðƒà.È().HorizonCode_Horizon_È(new BlockPos(this), 32);
            if (this.HorizonCode_Horizon_È == null) {
                this.Æ();
            }
            else {
                final BlockPos var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
                this.HorizonCode_Horizon_È(var1, (int)(this.HorizonCode_Horizon_È.Â() * 0.6f));
            }
        }
        super.ˆØ();
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(100.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
    }
    
    @Override
    protected int á(final int p_70682_1_) {
        return p_70682_1_;
    }
    
    @Override
    protected void £à(final Entity p_82167_1_) {
        if (p_82167_1_ instanceof IMob && this.ˆÐƒØ().nextInt(20) == 0) {
            this.Â((EntityLivingBase)p_82167_1_);
        }
        super.£à(p_82167_1_);
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (this.Ý > 0) {
            --this.Ý;
        }
        if (this.Ø­Ñ¢Ï­Ø­áˆº > 0) {
            --this.Ø­Ñ¢Ï­Ø­áˆº;
        }
        if (this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ > 2.500000277905201E-7 && this.ˆáƒ.nextInt(5) == 0) {
            final int var1 = MathHelper.Ý(this.ŒÏ);
            final int var2 = MathHelper.Ý(this.Çªà¢ - 0.20000000298023224);
            final int var3 = MathHelper.Ý(this.Ê);
            final IBlockState var4 = this.Ï­Ðƒà.Â(new BlockPos(var1, var2, var3));
            final Block var5 = var4.Ý();
            if (var5.Ó() != Material.HorizonCode_Horizon_È) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.à¢, this.ŒÏ + (this.ˆáƒ.nextFloat() - 0.5) * this.áŒŠ, this.£É().Â + 0.1, this.Ê + (this.ˆáƒ.nextFloat() - 0.5) * this.áŒŠ, 4.0 * (this.ˆáƒ.nextFloat() - 0.5), 0.5, (this.ˆáƒ.nextFloat() - 0.5) * 4.0, Block.HorizonCode_Horizon_È(var4));
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Class p_70686_1_) {
        return (!this.¥Ðƒá() || !EntityPlayer.class.isAssignableFrom(p_70686_1_)) && super.HorizonCode_Horizon_È(p_70686_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("PlayerCreated", this.¥Ðƒá());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.á(tagCompund.£á("PlayerCreated"));
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        this.Ý = 10;
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)4);
        final boolean var2 = p_70652_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), 7 + this.ˆáƒ.nextInt(15));
        if (var2) {
            p_70652_1_.ˆá += 0.4000000059604645;
            this.HorizonCode_Horizon_È(this, p_70652_1_);
        }
        this.HorizonCode_Horizon_È("mob.irongolem.throw", 1.0f, 1.0f);
        return var2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 4) {
            this.Ý = 10;
            this.HorizonCode_Horizon_È("mob.irongolem.throw", 1.0f, 1.0f);
        }
        else if (p_70103_1_ == 11) {
            this.Ø­Ñ¢Ï­Ø­áˆº = 400;
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    public Village Ø() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int ÇŽ() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70851_1_) {
        this.Ø­Ñ¢Ï­Ø­áˆº = (p_70851_1_ ? 400 : 0);
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)11);
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.irongolem.hit";
    }
    
    @Override
    protected String µÊ() {
        return "mob.irongolem.death";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.irongolem.walk", 1.0f, 1.0f);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(3), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ç), 1, BlockFlower.Â.Â.Ý());
        }
        for (int var4 = 3 + this.ˆáƒ.nextInt(3), var5 = 0; var5 < var4; ++var5) {
            this.HorizonCode_Horizon_È(Items.áˆºÑ¢Õ, 1);
        }
    }
    
    public int ÇŽÅ() {
        return this.Ø­Ñ¢Ï­Ø­áˆº;
    }
    
    public boolean ¥Ðƒá() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    public void á(final boolean p_70849_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70849_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x1));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    @Override
    public void Â(final DamageSource cause) {
        if (!this.¥Ðƒá() && this.Ñ¢Ó != null && this.HorizonCode_Horizon_È != null) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ñ¢Ó.v_(), -5);
        }
        super.Â(cause);
    }
    
    static class HorizonCode_Horizon_È extends EntityAINearestAttackableTarget
    {
        private static final String à = "CL_00002231";
        
        public HorizonCode_Horizon_È(final EntityCreature p_i45858_1_, final Class p_i45858_2_, final int p_i45858_3_, final boolean p_i45858_4_, final boolean p_i45858_5_, final Predicate p_i45858_6_) {
            super(p_i45858_1_, p_i45858_2_, p_i45858_3_, p_i45858_4_, p_i45858_5_, p_i45858_6_);
            this.Ý = (Predicate)new Predicate() {
                private static final String Â = "CL_00002230";
                
                public boolean HorizonCode_Horizon_È(final EntityLivingBase p_180096_1_) {
                    if (p_i45858_6_ != null && !p_i45858_6_.apply((Object)p_180096_1_)) {
                        return false;
                    }
                    if (p_180096_1_ instanceof EntityCreeper) {
                        return false;
                    }
                    if (p_180096_1_ instanceof EntityPlayer) {
                        double var2 = EntityAITarget.this.Ø();
                        if (p_180096_1_.Çªà¢()) {
                            var2 *= 0.800000011920929;
                        }
                        if (p_180096_1_.áŒŠÏ()) {
                            float var3 = ((EntityPlayer)p_180096_1_).ÂµÂ();
                            if (var3 < 0.1f) {
                                var3 = 0.1f;
                            }
                            var2 *= 0.7f * var3;
                        }
                        if (p_180096_1_.Ø­áŒŠá(p_i45858_1_) > var2) {
                            return false;
                        }
                    }
                    return EntityAITarget.this.HorizonCode_Horizon_È(p_180096_1_, false);
                }
                
                public boolean apply(final Object p_apply_1_) {
                    return this.HorizonCode_Horizon_È((EntityLivingBase)p_apply_1_);
                }
            };
        }
    }
}
