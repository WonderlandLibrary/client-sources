package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class EntityRabbit extends EntityAnimal
{
    private HorizonCode_Horizon_È ŒÂ;
    private int Ï­Ï;
    private int ŠØ;
    private boolean ˆÐƒØ;
    private boolean Çªà;
    private int ¥Å;
    private Âµá€ Œáƒ;
    private int Œá;
    private EntityPlayer µÂ;
    private static final String Ñ¢ÇŽÏ = "CL_00002242";
    
    public EntityRabbit(final World worldIn) {
        super(worldIn);
        this.Ï­Ï = 0;
        this.ŠØ = 0;
        this.ˆÐƒØ = false;
        this.Çªà = false;
        this.¥Å = 0;
        this.Œáƒ = Âµá€.Â;
        this.Œá = 0;
        this.µÂ = null;
        this.HorizonCode_Horizon_È(0.6f, 0.7f);
        this.áŒŠÆ = new Ó(this);
        this.Ø = new à();
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(2.5f);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new Ý(1.33));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAITempt(this, 1.0, Items.¥áŒŠà, false));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIMate(this, 0.8));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new Ø­áŒŠá());
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIWander(this, 0.6));
        this.ÂµÈ.HorizonCode_Horizon_È(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.ŒÂ = new HorizonCode_Horizon_È((Predicate)new Predicate() {
            private static final String Â = "CL_00002241";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180086_1_) {
                return p_180086_1_ instanceof EntityWolf;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        }, 16.0f, 1.33, 1.33);
        this.ÂµÈ.HorizonCode_Horizon_È(4, this.ŒÂ);
        this.Â(0.0);
    }
    
    @Override
    protected float £Ô() {
        return (this.Ø.HorizonCode_Horizon_È() && this.Ø.Âµá€() > this.Çªà¢ + 0.5) ? 0.5f : this.Œáƒ.Â();
    }
    
    public void HorizonCode_Horizon_È(final Âµá€ p_175522_1_) {
        this.Œáƒ = p_175522_1_;
    }
    
    public float £á(final float p_175521_1_) {
        return (this.ŠØ == 0) ? 0.0f : ((this.Ï­Ï + p_175521_1_) / this.ŠØ);
    }
    
    public void Â(final double p_175515_1_) {
        this.Š().HorizonCode_Horizon_È(p_175515_1_);
        this.Ø.HorizonCode_Horizon_È(this.Ø.Ø­áŒŠá(), this.Ø.Âµá€(), this.Ø.Ó(), p_175515_1_);
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175519_1_, final Âµá€ p_175519_2_) {
        super.ÂµÈ(p_175519_1_);
        if (!p_175519_1_) {
            if (this.Œáƒ == Âµá€.Âµá€) {
                this.Œáƒ = Âµá€.Â;
            }
        }
        else {
            this.Â(1.5 * p_175519_2_.HorizonCode_Horizon_È());
            this.HorizonCode_Horizon_È(this.¥Ê(), this.ˆÂ(), ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
        this.ˆÐƒØ = p_175519_1_;
    }
    
    public void Â(final Âµá€ p_175524_1_) {
        this.HorizonCode_Horizon_È(true, p_175524_1_);
        this.ŠØ = p_175524_1_.Ø­áŒŠá();
        this.Ï­Ï = 0;
    }
    
    public boolean ÐƒÇŽà() {
        return this.ˆÐƒØ;
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(18, (Object)(byte)0);
    }
    
    public void ˆØ() {
        if (this.Ø.Â() > 0.8) {
            this.HorizonCode_Horizon_È(Âµá€.Ø­áŒŠá);
        }
        else if (this.Œáƒ != Âµá€.Âµá€) {
            this.HorizonCode_Horizon_È(Âµá€.Â);
        }
        if (this.¥Å > 0) {
            --this.¥Å;
        }
        if (this.Œá > 0) {
            this.Œá -= this.ˆáƒ.nextInt(3);
            if (this.Œá < 0) {
                this.Œá = 0;
            }
        }
        if (this.ŠÂµà) {
            if (!this.Çªà) {
                this.HorizonCode_Horizon_È(false, Âµá€.HorizonCode_Horizon_È);
                this.Ñ¢Õ();
            }
            if (this.ÐƒÓ() == 99 && this.¥Å == 0) {
                final EntityLivingBase var1 = this.Ñ¢Ó();
                if (var1 != null && this.Âµá€(var1) < 16.0) {
                    this.HorizonCode_Horizon_È(var1.ŒÏ, var1.Ê);
                    this.Ø.HorizonCode_Horizon_È(var1.ŒÏ, var1.Çªà¢, var1.Ê, this.Ø.Â());
                    this.Â(Âµá€.Âµá€);
                    this.Çªà = true;
                }
            }
            final Ó var2 = (Ó)this.áŒŠÆ;
            if (!var2.Ý()) {
                if (this.Ø.HorizonCode_Horizon_È() && this.¥Å == 0) {
                    final PathEntity var3 = this.áˆºÑ¢Õ.Ý();
                    Vec3 var4 = new Vec3(this.Ø.Ø­áŒŠá(), this.Ø.Âµá€(), this.Ø.Ó());
                    if (var3 != null && var3.Âµá€() < var3.Ø­áŒŠá()) {
                        var4 = var3.HorizonCode_Horizon_È(this);
                    }
                    this.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È, var4.Ý);
                    this.Â(this.Œáƒ);
                }
            }
            else if (!var2.Ø­áŒŠá()) {
                this.ÐƒáˆºÄ();
            }
        }
        this.Çªà = this.ŠÂµà;
    }
    
    @Override
    public void Ñ¢Â() {
    }
    
    private void HorizonCode_Horizon_È(final double p_175533_1_, final double p_175533_3_) {
        this.É = (float)(Math.atan2(p_175533_3_ - this.Ê, p_175533_1_ - this.ŒÏ) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    private void ÐƒáˆºÄ() {
        ((Ó)this.áŒŠÆ).HorizonCode_Horizon_È(true);
    }
    
    private void áˆºÉ() {
        ((Ó)this.áŒŠÆ).HorizonCode_Horizon_È(false);
    }
    
    private void Ø­È() {
        this.¥Å = this.áˆºÕ();
    }
    
    private void Ñ¢Õ() {
        this.Ø­È();
        this.áˆºÉ();
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (this.Ï­Ï != this.ŠØ) {
            if (this.Ï­Ï == 0 && !this.Ï­Ðƒà.ŠÄ) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)1);
            }
            ++this.Ï­Ï;
        }
        else if (this.ŠØ != 0) {
            this.Ï­Ï = 0;
            this.ŠØ = 0;
        }
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(10.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.30000001192092896);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("RabbitType", this.ÐƒÓ());
        tagCompound.HorizonCode_Horizon_È("MoreCarrotTicks", this.Œá);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ˆà(tagCompund.Ó("RabbitType"));
        this.Œá = tagCompund.Ó("MoreCarrotTicks");
    }
    
    protected String ¥Ê() {
        return "mob.rabbit.hop";
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.rabbit.idle";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.rabbit.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.rabbit.death";
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        if (this.ÐƒÓ() == 99) {
            this.HorizonCode_Horizon_È("mob.attack", 1.0f, (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
            return p_70652_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), 8.0f);
        }
        return p_70652_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), 3.0f);
    }
    
    @Override
    public int áŒŠÉ() {
        return (this.ÐƒÓ() == 99) ? 8 : super.áŒŠÉ();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        return !this.HorizonCode_Horizon_È(source) && super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    protected void áˆºáˆºáŠ() {
        this.HorizonCode_Horizon_È(new ItemStack(Items.ŒÂ, 1), 0.0f);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(2) + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.Ï­Ï, 1);
        }
        for (int var3 = this.ˆáƒ.nextInt(2), var4 = 0; var4 < var3; ++var4) {
            if (this.ˆÏ()) {
                this.HorizonCode_Horizon_È(Items.ŠáˆºÂ, 1);
            }
            else {
                this.HorizonCode_Horizon_È(Items.ÇŽà, 1);
            }
        }
    }
    
    private boolean HorizonCode_Horizon_È(final Item_1028566121 p_175525_1_) {
        return p_175525_1_ == Items.¥áŒŠà || p_175525_1_ == Items.ŠÏ || p_175525_1_ == Item_1028566121.HorizonCode_Horizon_È(Blocks.Âµà);
    }
    
    public EntityRabbit Â(final EntityAgeable p_175526_1_) {
        final EntityRabbit var2 = new EntityRabbit(this.Ï­Ðƒà);
        if (p_175526_1_ instanceof EntityRabbit) {
            var2.ˆà(this.ˆáƒ.nextBoolean() ? this.ÐƒÓ() : ((EntityRabbit)p_175526_1_).ÐƒÓ());
        }
        return var2;
    }
    
    @Override
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && this.HorizonCode_Horizon_È(p_70877_1_.HorizonCode_Horizon_È());
    }
    
    public int ÐƒÓ() {
        return this.£Ó.HorizonCode_Horizon_È(18);
    }
    
    public void ˆà(final int p_175529_1_) {
        if (p_175529_1_ == 99) {
            this.ÂµÈ.HorizonCode_Horizon_È(this.ŒÂ);
            this.ÂµÈ.HorizonCode_Horizon_È(4, new Â());
            this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
            this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
            if (!this.j_()) {
                this.à(StatCollector.HorizonCode_Horizon_È("entity.KillerBunny.name"));
            }
        }
        this.£Ó.Â(18, (byte)p_175529_1_);
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        Object p_180482_2_2 = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        int var3 = this.ˆáƒ.nextInt(6);
        boolean var4 = false;
        if (p_180482_2_2 instanceof Ø) {
            var3 = ((Ø)p_180482_2_2).HorizonCode_Horizon_È;
            var4 = true;
        }
        else {
            p_180482_2_2 = new Ø(var3);
        }
        this.ˆà(var3);
        if (var4) {
            this.Â(-24000);
        }
        return (IEntityLivingData)p_180482_2_2;
    }
    
    private boolean Ø­à¢() {
        return this.Œá == 0;
    }
    
    protected int áˆºÕ() {
        return this.Œáƒ.Ý();
    }
    
    protected void ŒÐƒà() {
        this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.ŠÂµà, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 0.5 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, 0.0, 0.0, 0.0, Block.HorizonCode_Horizon_È(Blocks.Ñ¢È.Ý(7)));
        this.Œá = 100;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 1) {
            this.Ï­à();
            this.ŠØ = 10;
            this.Ï­Ï = 0;
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    public EntityAgeable HorizonCode_Horizon_È(final EntityAgeable p_90011_1_) {
        return this.Â(p_90011_1_);
    }
    
    enum Âµá€
    {
        HorizonCode_Horizon_È("NONE", 0, "NONE", 0, 0.0f, 0.0f, 30, 1), 
        Â("HOP", 1, "HOP", 1, 0.8f, 0.2f, 20, 10), 
        Ý("STEP", 2, "STEP", 2, 1.0f, 0.45f, 14, 14), 
        Ø­áŒŠá("SPRINT", 3, "SPRINT", 3, 1.75f, 0.4f, 1, 8), 
        Âµá€("ATTACK", 4, "ATTACK", 4, 2.0f, 0.7f, 7, 8);
        
        private final float Ó;
        private final float à;
        private final int Ø;
        private final int áŒŠÆ;
        private static final Âµá€[] áˆºÑ¢Õ;
        private static final String ÂµÈ = "CL_00002239";
        
        static {
            á = new Âµá€[] { Âµá€.HorizonCode_Horizon_È, Âµá€.Â, Âµá€.Ý, Âµá€.Ø­áŒŠá, Âµá€.Âµá€ };
            áˆºÑ¢Õ = new Âµá€[] { Âµá€.HorizonCode_Horizon_È, Âµá€.Â, Âµá€.Ý, Âµá€.Ø­áŒŠá, Âµá€.Âµá€ };
        }
        
        private Âµá€(final String s, final int n, final String p_i45866_1_, final int p_i45866_2_, final float p_i45866_3_, final float p_i45866_4_, final int p_i45866_5_, final int p_i45866_6_) {
            this.Ó = p_i45866_3_;
            this.à = p_i45866_4_;
            this.Ø = p_i45866_5_;
            this.áŒŠÆ = p_i45866_6_;
        }
        
        public float HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public float Â() {
            return this.à;
        }
        
        public int Ý() {
            return this.Ø;
        }
        
        public int Ø­áŒŠá() {
            return this.áŒŠÆ;
        }
    }
    
    class HorizonCode_Horizon_È extends EntityAIAvoidEntity
    {
        private EntityRabbit Âµá€;
        private static final String Ó = "CL_00002238";
        
        public HorizonCode_Horizon_È(final Predicate p_i45865_2_, final float p_i45865_3_, final double p_i45865_4_, final double p_i45865_6_) {
            super(EntityRabbit.this, p_i45865_2_, p_i45865_3_, p_i45865_4_, p_i45865_6_);
            this.Âµá€ = EntityRabbit.this;
        }
        
        @Override
        public void Ø­áŒŠá() {
            super.Ø­áŒŠá();
        }
    }
    
    class Â extends EntityAIAttackOnCollide
    {
        private static final String áŒŠÆ = "CL_00002240";
        
        public Â() {
            super(EntityRabbit.this, EntityLivingBase.class, 1.4, true);
        }
        
        @Override
        protected double HorizonCode_Horizon_È(final EntityLivingBase p_179512_1_) {
            return 4.0f + p_179512_1_.áŒŠ;
        }
    }
    
    class Ý extends EntityAIPanic
    {
        private EntityRabbit Ý;
        private static final String Ø­áŒŠá = "CL_00002234";
        
        public Ý(final double p_i45861_2_) {
            super(EntityRabbit.this, p_i45861_2_);
            this.Ý = EntityRabbit.this;
        }
        
        @Override
        public void Ø­áŒŠá() {
            super.Ø­áŒŠá();
            this.Ý.Â(this.HorizonCode_Horizon_È);
        }
    }
    
    class Ø­áŒŠá extends EntityAIMoveToBlock
    {
        private boolean Ø­áŒŠá;
        private boolean Âµá€;
        private static final String Ó = "CL_00002233";
        
        public Ø­áŒŠá() {
            super(EntityRabbit.this, 0.699999988079071, 16);
            this.Âµá€ = false;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            if (this.HorizonCode_Horizon_È <= 0) {
                if (!EntityRabbit.this.Ï­Ðƒà.Çªà¢().Â("mobGriefing")) {
                    return false;
                }
                this.Âµá€ = false;
                this.Ø­áŒŠá = EntityRabbit.this.Ø­à¢();
            }
            return super.HorizonCode_Horizon_È();
        }
        
        @Override
        public boolean Â() {
            return this.Âµá€ && super.Â();
        }
        
        @Override
        public void Âµá€() {
            super.Âµá€();
        }
        
        @Override
        public void Ý() {
            super.Ý();
        }
        
        @Override
        public void Ø­áŒŠá() {
            super.Ø­áŒŠá();
            EntityRabbit.this.Ñ¢á().HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È() + 0.5, this.Â.Â() + 1, this.Â.Ý() + 0.5, 10.0f, EntityRabbit.this.áˆºà());
            if (this.Ø()) {
                final World var1 = EntityRabbit.this.Ï­Ðƒà;
                final BlockPos var2 = this.Â.Ø­áŒŠá();
                final IBlockState var3 = var1.Â(var2);
                final Block var4 = var3.Ý();
                if (this.Âµá€ && var4 instanceof BlockCarrot && (int)var3.HorizonCode_Horizon_È(BlockCarrot.Õ) == 7) {
                    var1.HorizonCode_Horizon_È(var2, Blocks.Â.¥à(), 2);
                    var1.Â(var2, true);
                    EntityRabbit.this.ŒÐƒà();
                }
                this.Âµá€ = false;
                this.HorizonCode_Horizon_È = 10;
            }
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final World worldIn, BlockPos p_179488_2_) {
            Block var3 = worldIn.Â(p_179488_2_).Ý();
            if (var3 == Blocks.£Â) {
                p_179488_2_ = p_179488_2_.Ø­áŒŠá();
                final IBlockState var4 = worldIn.Â(p_179488_2_);
                var3 = var4.Ý();
                if (var3 instanceof BlockCarrot && (int)var4.HorizonCode_Horizon_È(BlockCarrot.Õ) == 7 && this.Ø­áŒŠá && !this.Âµá€) {
                    return this.Âµá€ = true;
                }
            }
            return false;
        }
    }
    
    public class Ó extends EntityJumpHelper
    {
        private EntityRabbit Ý;
        private boolean Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002236";
        
        public Ó(final EntityRabbit p_i45863_2_) {
            super(p_i45863_2_);
            this.Ø­áŒŠá = false;
            this.Ý = p_i45863_2_;
        }
        
        public boolean Ý() {
            return this.HorizonCode_Horizon_È;
        }
        
        public boolean Ø­áŒŠá() {
            return this.Ø­áŒŠá;
        }
        
        public void HorizonCode_Horizon_È(final boolean p_180066_1_) {
            this.Ø­áŒŠá = p_180066_1_;
        }
        
        @Override
        public void Â() {
            if (this.HorizonCode_Horizon_È) {
                this.Ý.Â(EntityRabbit.Âµá€.Ý);
                this.HorizonCode_Horizon_È = false;
            }
        }
    }
    
    class à extends EntityMoveHelper
    {
        private EntityRabbit Ø;
        private static final String áŒŠÆ = "CL_00002235";
        
        public à() {
            super(EntityRabbit.this);
            this.Ø = EntityRabbit.this;
        }
        
        @Override
        public void Ý() {
            if (this.Ø.ŠÂµà && !this.Ø.ÐƒÇŽà()) {
                this.Ø.Â(0.0);
            }
            super.Ý();
        }
    }
    
    public static class Ø implements IEntityLivingData
    {
        public int HorizonCode_Horizon_È;
        private static final String Â = "CL_00002237";
        
        public Ø(final int p_i45864_1_) {
            this.HorizonCode_Horizon_È = p_i45864_1_;
        }
    }
}
