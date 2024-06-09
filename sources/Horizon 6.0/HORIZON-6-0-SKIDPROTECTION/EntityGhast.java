package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EntityGhast extends EntityFlying implements IMob
{
    private int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001689";
    
    public EntityGhast(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = 1;
        this.HorizonCode_Horizon_È(4.0f, 4.0f);
        this.£Â = true;
        this.à = 5;
        this.Ø = new Ø­áŒŠá();
        this.ÂµÈ.HorizonCode_Horizon_È(5, new Ý());
        this.ÂµÈ.HorizonCode_Horizon_È(7, new Â());
        this.ÂµÈ.HorizonCode_Horizon_È(7, new HorizonCode_Horizon_È());
        this.á.HorizonCode_Horizon_È(1, new EntityAIFindEntityNearestPlayer(this));
    }
    
    public boolean Ø() {
        return this.£Ó.HorizonCode_Horizon_È(16) != 0;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175454_1_) {
        this.£Ó.Â(16, (byte)(byte)(p_175454_1_ ? 1 : 0));
    }
    
    public int ˆà() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void á() {
        super.á();
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.HorizonCode_Horizon_È) {
            this.á€();
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if ("fireball".equals(source.£à()) && source.áˆºÑ¢Õ() instanceof EntityPlayer) {
            super.HorizonCode_Horizon_È(source, 1000.0f);
            ((EntityPlayer)source.áˆºÑ¢Õ()).HorizonCode_Horizon_È(AchievementList.ŠÄ);
            return true;
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)0);
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(10.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(100.0);
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.ghast.moan";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.ghast.scream";
    }
    
    @Override
    protected String µÊ() {
        return "mob.ghast.death";
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.É;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(2) + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.¥Å, 1);
        }
        for (int var3 = this.ˆáƒ.nextInt(3) + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.É, 1);
        }
    }
    
    @Override
    protected float ˆÂ() {
        return 10.0f;
    }
    
    @Override
    public boolean µà() {
        return this.ˆáƒ.nextInt(20) == 0 && super.µà() && this.Ï­Ðƒà.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È;
    }
    
    @Override
    public int Ï­áˆºÓ() {
        return 1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("ExplosionPower", this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("ExplosionPower", 99)) {
            this.HorizonCode_Horizon_È = tagCompund.Ó("ExplosionPower");
        }
    }
    
    @Override
    public float Ðƒáƒ() {
        return 2.6f;
    }
    
    class HorizonCode_Horizon_È extends EntityAIBase
    {
        private EntityGhast Ý;
        public int HorizonCode_Horizon_È;
        private static final String Ø­áŒŠá = "CL_00002215";
        
        HorizonCode_Horizon_È() {
            this.Ý = EntityGhast.this;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Ý.Ñ¢Ó() != null;
        }
        
        @Override
        public void Âµá€() {
            this.HorizonCode_Horizon_È = 0;
        }
        
        @Override
        public void Ý() {
            this.Ý.HorizonCode_Horizon_È(false);
        }
        
        @Override
        public void Ø­áŒŠá() {
            final EntityLivingBase var1 = this.Ý.Ñ¢Ó();
            final double var2 = 64.0;
            if (var1.Âµá€(this.Ý) < var2 * var2 && this.Ý.µà(var1)) {
                final World var3 = this.Ý.Ï­Ðƒà;
                ++this.HorizonCode_Horizon_È;
                if (this.HorizonCode_Horizon_È == 10) {
                    var3.HorizonCode_Horizon_È(null, 1007, new BlockPos(this.Ý), 0);
                }
                if (this.HorizonCode_Horizon_È == 20) {
                    final double var4 = 4.0;
                    final Vec3 var5 = this.Ý.Ó(1.0f);
                    final double var6 = var1.ŒÏ - (this.Ý.ŒÏ + var5.HorizonCode_Horizon_È * var4);
                    final double var7 = var1.£É().Â + var1.£ÂµÄ / 2.0f - (0.5 + this.Ý.Çªà¢ + this.Ý.£ÂµÄ / 2.0f);
                    final double var8 = var1.Ê - (this.Ý.Ê + var5.Ý * var4);
                    var3.HorizonCode_Horizon_È(null, 1008, new BlockPos(this.Ý), 0);
                    final EntityLargeFireball var9 = new EntityLargeFireball(var3, this.Ý, var6, var7, var8);
                    var9.Âµá€ = this.Ý.ˆà();
                    var9.ŒÏ = this.Ý.ŒÏ + var5.HorizonCode_Horizon_È * var4;
                    var9.Çªà¢ = this.Ý.Çªà¢ + this.Ý.£ÂµÄ / 2.0f + 0.5;
                    var9.Ê = this.Ý.Ê + var5.Ý * var4;
                    var3.HorizonCode_Horizon_È(var9);
                    this.HorizonCode_Horizon_È = -40;
                }
            }
            else if (this.HorizonCode_Horizon_È > 0) {
                --this.HorizonCode_Horizon_È;
            }
            this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È > 10);
        }
    }
    
    class Â extends EntityAIBase
    {
        private EntityGhast Â;
        private static final String Ý = "CL_00002217";
        
        public Â() {
            this.Â = EntityGhast.this;
            this.HorizonCode_Horizon_È(2);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return true;
        }
        
        @Override
        public void Ø­áŒŠá() {
            if (this.Â.Ñ¢Ó() == null) {
                final EntityGhast â = this.Â;
                final EntityGhast â2 = this.Â;
                final float n = -(float)Math.atan2(this.Â.ÇŽÉ, this.Â.ÇŽÕ) * 180.0f / 3.1415927f;
                â2.É = n;
                â.¥É = n;
            }
            else {
                final EntityLivingBase var1 = this.Â.Ñ¢Ó();
                final double var2 = 64.0;
                if (var1.Âµá€(this.Â) < var2 * var2) {
                    final double var3 = var1.ŒÏ - this.Â.ŒÏ;
                    final double var4 = var1.Ê - this.Â.Ê;
                    final EntityGhast â3 = this.Â;
                    final EntityGhast â4 = this.Â;
                    final float n2 = -(float)Math.atan2(var3, var4) * 180.0f / 3.1415927f;
                    â4.É = n2;
                    â3.¥É = n2;
                }
            }
        }
    }
    
    class Ý extends EntityAIBase
    {
        private EntityGhast Â;
        private static final String Ý = "CL_00002214";
        
        public Ý() {
            this.Â = EntityGhast.this;
            this.HorizonCode_Horizon_È(1);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            final EntityMoveHelper var1 = this.Â.ŒÏ();
            if (!var1.HorizonCode_Horizon_È()) {
                return true;
            }
            final double var2 = var1.Ø­áŒŠá() - this.Â.ŒÏ;
            final double var3 = var1.Âµá€() - this.Â.Çªà¢;
            final double var4 = var1.Ó() - this.Â.Ê;
            final double var5 = var2 * var2 + var3 * var3 + var4 * var4;
            return var5 < 1.0 || var5 > 3600.0;
        }
        
        @Override
        public boolean Â() {
            return false;
        }
        
        @Override
        public void Âµá€() {
            final Random var1 = this.Â.ˆÐƒØ();
            final double var2 = this.Â.ŒÏ + (var1.nextFloat() * 2.0f - 1.0f) * 16.0f;
            final double var3 = this.Â.Çªà¢ + (var1.nextFloat() * 2.0f - 1.0f) * 16.0f;
            final double var4 = this.Â.Ê + (var1.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.Â.ŒÏ().HorizonCode_Horizon_È(var2, var3, var4, 1.0);
        }
    }
    
    class Ø­áŒŠá extends EntityMoveHelper
    {
        private EntityGhast Ø;
        private int áŒŠÆ;
        private static final String áˆºÑ¢Õ = "CL_00002216";
        
        public Ø­áŒŠá() {
            super(EntityGhast.this);
            this.Ø = EntityGhast.this;
        }
        
        @Override
        public void Ý() {
            if (this.Ó) {
                final double var1 = this.Â - this.Ø.ŒÏ;
                final double var2 = this.Ý - this.Ø.Çªà¢;
                final double var3 = this.Ø­áŒŠá - this.Ø.Ê;
                double var4 = var1 * var1 + var2 * var2 + var3 * var3;
                if (this.áŒŠÆ-- <= 0) {
                    this.áŒŠÆ += this.Ø.ˆÐƒØ().nextInt(5) + 2;
                    var4 = MathHelper.HorizonCode_Horizon_È(var4);
                    if (this.Â(this.Â, this.Ý, this.Ø­áŒŠá, var4)) {
                        final EntityGhast ø = this.Ø;
                        ø.ÇŽÉ += var1 / var4 * 0.1;
                        final EntityGhast ø2 = this.Ø;
                        ø2.ˆá += var2 / var4 * 0.1;
                        final EntityGhast ø3 = this.Ø;
                        ø3.ÇŽÕ += var3 / var4 * 0.1;
                    }
                    else {
                        this.Ó = false;
                    }
                }
            }
        }
        
        private boolean Â(final double p_179926_1_, final double p_179926_3_, final double p_179926_5_, final double p_179926_7_) {
            final double var9 = (p_179926_1_ - this.Ø.ŒÏ) / p_179926_7_;
            final double var10 = (p_179926_3_ - this.Ø.Çªà¢) / p_179926_7_;
            final double var11 = (p_179926_5_ - this.Ø.Ê) / p_179926_7_;
            AxisAlignedBB var12 = this.Ø.£É();
            for (int var13 = 1; var13 < p_179926_7_; ++var13) {
                var12 = var12.Ý(var9, var10, var11);
                if (!this.Ø.Ï­Ðƒà.HorizonCode_Horizon_È(this.Ø, var12).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }
}
