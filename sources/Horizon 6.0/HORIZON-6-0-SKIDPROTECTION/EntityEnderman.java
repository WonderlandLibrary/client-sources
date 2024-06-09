package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.UUID;

public class EntityEnderman extends EntityMob
{
    private static final UUID Â;
    private static final AttributeModifier Ý;
    private static final Set Ø­Ñ¢Ï­Ø­áˆº;
    private boolean ŒÂ;
    private static final String Ï­Ï = "CL_00001685";
    
    static {
        Â = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
        Ý = new AttributeModifier(EntityEnderman.Â, "Attacking speed boost", 0.15000000596046448, 0).HorizonCode_Horizon_È(false);
        (Ø­Ñ¢Ï­Ø­áˆº = Sets.newIdentityHashSet()).add(Blocks.Ø­áŒŠá);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Âµá€);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.£á);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Å);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Âµà);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Ç);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.È);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.áŠ);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Ñ¢Â);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Ñ¢Ç);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.£É);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Ø­Æ);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.ˆÅ);
        EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.add(Blocks.Œáƒ);
    }
    
    public EntityEnderman(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.6f, 2.9f);
        this.Ô = 1.0f;
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIAttackOnCollide(this, 1.0, false));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.ÂµÈ.HorizonCode_Horizon_È(10, new Â());
        this.ÂµÈ.HorizonCode_Horizon_È(11, new Ý());
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new HorizonCode_Horizon_È());
        this.á.HorizonCode_Horizon_È(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, (Predicate)new Predicate() {
            private static final String Â = "CL_00002223";
            
            public boolean HorizonCode_Horizon_È(final EntityEndermite p_179948_1_) {
                return p_179948_1_.Ø();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EntityEndermite)p_apply_1_);
            }
        }));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(40.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.30000001192092896);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(7.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(64.0);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, new Short((short)0));
        this.£Ó.HorizonCode_Horizon_È(17, new Byte((byte)0));
        this.£Ó.HorizonCode_Horizon_È(18, new Byte((byte)0));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        final IBlockState var2 = this.ÇŽÅ();
        tagCompound.HorizonCode_Horizon_È("carried", (short)Block.HorizonCode_Horizon_È(var2.Ý()));
        tagCompound.HorizonCode_Horizon_È("carriedData", (short)var2.Ý().Ý(var2));
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        IBlockState var2;
        if (tagCompund.Â("carried", 8)) {
            var2 = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("carried")).Ý(tagCompund.Âµá€("carriedData") & 0xFFFF);
        }
        else {
            var2 = Block.HorizonCode_Horizon_È(tagCompund.Âµá€("carried")).Ý(tagCompund.Âµá€("carriedData") & 0xFFFF);
        }
        this.HorizonCode_Horizon_È(var2);
    }
    
    private boolean Ó(final EntityPlayer p_70821_1_) {
        final ItemStack var2 = p_70821_1_.Ø­Ñ¢Ï­Ø­áˆº.Â[3];
        if (var2 != null && var2.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ)) {
            return false;
        }
        final Vec3 var3 = p_70821_1_.Ó(1.0f).HorizonCode_Horizon_È();
        Vec3 var4 = new Vec3(this.ŒÏ - p_70821_1_.ŒÏ, this.£É().Â + this.£ÂµÄ / 2.0f - (p_70821_1_.Çªà¢ + p_70821_1_.Ðƒáƒ()), this.Ê - p_70821_1_.Ê);
        final double var5 = var4.Â();
        var4 = var4.HorizonCode_Horizon_È();
        final double var6 = var3.Â(var4);
        return var6 > 1.0 - 0.025 / var5 && p_70821_1_.µà(this);
    }
    
    @Override
    public float Ðƒáƒ() {
        return 2.55f;
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ï­Ðƒà.ŠÄ) {
            for (int var1 = 0; var1 < 2; ++var1) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, this.Çªà¢ + this.ˆáƒ.nextDouble() * this.£ÂµÄ - 0.25, this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, (this.ˆáƒ.nextDouble() - 0.5) * 2.0, -this.ˆáƒ.nextDouble(), (this.ˆáƒ.nextDouble() - 0.5) * 2.0, new int[0]);
            }
        }
        this.ÐƒÂ = false;
        super.ˆÏ­();
    }
    
    @Override
    protected void ˆØ() {
        if (this.áŒŠ()) {
            this.HorizonCode_Horizon_È(DamageSource.Ó, 1.0f);
        }
        if (this.¥Ðƒá() && !this.ŒÂ && this.ˆáƒ.nextInt(100) == 0) {
            this.HorizonCode_Horizon_È(false);
        }
        if (this.Ï­Ðƒà.ÂµÈ()) {
            final float var1 = this.Â(1.0f);
            if (var1 > 0.5f && this.Ï­Ðƒà.áˆºÑ¢Õ(new BlockPos(this)) && this.ˆáƒ.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f) {
                this.Â((EntityLivingBase)null);
                this.HorizonCode_Horizon_È(false);
                this.ŒÂ = false;
                this.Ø();
            }
        }
        super.ˆØ();
    }
    
    protected boolean Ø() {
        final double var1 = this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * 64.0;
        final double var2 = this.Çªà¢ + (this.ˆáƒ.nextInt(64) - 32);
        final double var3 = this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * 64.0;
        return this.ÂµÈ(var1, var2, var3);
    }
    
    protected boolean Â(final Entity p_70816_1_) {
        Vec3 var2 = new Vec3(this.ŒÏ - p_70816_1_.ŒÏ, this.£É().Â + this.£ÂµÄ / 2.0f - p_70816_1_.Çªà¢ + p_70816_1_.Ðƒáƒ(), this.Ê - p_70816_1_.Ê);
        var2 = var2.HorizonCode_Horizon_È();
        final double var3 = 16.0;
        final double var4 = this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * 8.0 - var2.HorizonCode_Horizon_È * var3;
        final double var5 = this.Çªà¢ + (this.ˆáƒ.nextInt(16) - 8) - var2.Â * var3;
        final double var6 = this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * 8.0 - var2.Ý * var3;
        return this.ÂµÈ(var4, var5, var6);
    }
    
    protected boolean ÂµÈ(final double p_70825_1_, final double p_70825_3_, final double p_70825_5_) {
        final double var7 = this.ŒÏ;
        final double var8 = this.Çªà¢;
        final double var9 = this.Ê;
        this.ŒÏ = p_70825_1_;
        this.Çªà¢ = p_70825_3_;
        this.Ê = p_70825_5_;
        boolean var10 = false;
        BlockPos var11 = new BlockPos(this.ŒÏ, this.Çªà¢, this.Ê);
        if (this.Ï­Ðƒà.Ó(var11)) {
            boolean var12 = false;
            while (!var12 && var11.Â() > 0) {
                final BlockPos var13 = var11.Âµá€();
                final Block var14 = this.Ï­Ðƒà.Â(var13).Ý();
                if (var14.Ó().Ø­áŒŠá()) {
                    var12 = true;
                }
                else {
                    --this.Çªà¢;
                    var11 = var13;
                }
            }
            if (var12) {
                super.áˆºÑ¢Õ(this.ŒÏ, this.Çªà¢, this.Ê);
                if (this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty() && !this.Ï­Ðƒà.Ø­áŒŠá(this.£É())) {
                    var10 = true;
                }
            }
        }
        if (!var10) {
            this.Ý(var7, var8, var9);
            return false;
        }
        final short var15 = 128;
        for (int var16 = 0; var16 < var15; ++var16) {
            final double var17 = var16 / (var15 - 1.0);
            final float var18 = (this.ˆáƒ.nextFloat() - 0.5f) * 0.2f;
            final float var19 = (this.ˆáƒ.nextFloat() - 0.5f) * 0.2f;
            final float var20 = (this.ˆáƒ.nextFloat() - 0.5f) * 0.2f;
            final double var21 = var7 + (this.ŒÏ - var7) * var17 + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ * 2.0;
            final double var22 = var8 + (this.Çªà¢ - var8) * var17 + this.ˆáƒ.nextDouble() * this.£ÂµÄ;
            final double var23 = var9 + (this.Ê - var9) * var17 + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ * 2.0;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, var21, var22, var23, var18, var19, var20, new int[0]);
        }
        this.Ï­Ðƒà.HorizonCode_Horizon_È(var7, var8, var9, "mob.endermen.portal", 1.0f, 1.0f);
        this.HorizonCode_Horizon_È("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return this.¥Ðƒá() ? "mob.endermen.scream" : "mob.endermen.idle";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.endermen.hit";
    }
    
    @Override
    protected String µÊ() {
        return "mob.endermen.death";
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.ˆÐƒØ;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        final Item_1028566121 var3 = this.áŒŠÕ();
        if (var3 != null) {
            for (int var4 = this.ˆáƒ.nextInt(2 + p_70628_2_), var5 = 0; var5 < var4; ++var5) {
                this.HorizonCode_Horizon_È(var3, 1);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final IBlockState p_175490_1_) {
        this.£Ó.Â(16, (short)(Block.HorizonCode_Horizon_È(p_175490_1_) & 0xFFFF));
    }
    
    public IBlockState ÇŽÅ() {
        return Block.Â(this.£Ó.Â(16) & 0xFFFF);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (source.áˆºÑ¢Õ() == null || !(source.áˆºÑ¢Õ() instanceof EntityEndermite)) {
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.HorizonCode_Horizon_È(true);
            }
            if (source instanceof EntityDamageSource && source.áˆºÑ¢Õ() instanceof EntityPlayer) {
                if (source.áˆºÑ¢Õ() instanceof EntityPlayerMP && ((EntityPlayerMP)source.áˆºÑ¢Õ()).Ý.Ý()) {
                    this.HorizonCode_Horizon_È(false);
                }
                else {
                    this.ŒÂ = true;
                }
            }
            if (source instanceof EntityDamageSourceIndirect) {
                this.ŒÂ = false;
                for (int var4 = 0; var4 < 64; ++var4) {
                    if (this.Ø()) {
                        return true;
                    }
                }
                return false;
            }
        }
        final boolean var5 = super.HorizonCode_Horizon_È(source, amount);
        if (source.Âµá€() && this.ˆáƒ.nextInt(10) != 0) {
            this.Ø();
        }
        return var5;
    }
    
    public boolean ¥Ðƒá() {
        return this.£Ó.HorizonCode_Horizon_È(18) > 0;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70819_1_) {
        this.£Ó.Â(18, (byte)(byte)(p_70819_1_ ? 1 : 0));
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final EntityEnderman entityEnderman, final boolean œâ) {
        entityEnderman.ŒÂ = œâ;
    }
    
    class HorizonCode_Horizon_È extends EntityAINearestAttackableTarget
    {
        private EntityPlayer Ø;
        private int áŒŠÆ;
        private int áˆºÑ¢Õ;
        private EntityEnderman ÂµÈ;
        private static final String á = "CL_00002221";
        
        public HorizonCode_Horizon_È() {
            super(EntityEnderman.this, EntityPlayer.class, true);
            this.ÂµÈ = EntityEnderman.this;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            final double var1 = this.Ø();
            final List var2 = this.Âµá€.Ï­Ðƒà.HorizonCode_Horizon_È(EntityPlayer.class, this.Âµá€.£É().Â(var1, 4.0, var1), this.Ý);
            Collections.sort((List<Object>)var2, this.Â);
            if (var2.isEmpty()) {
                return false;
            }
            this.Ø = var2.get(0);
            return true;
        }
        
        @Override
        public void Âµá€() {
            this.áŒŠÆ = 5;
            this.áˆºÑ¢Õ = 0;
        }
        
        @Override
        public void Ý() {
            this.Ø = null;
            this.ÂµÈ.HorizonCode_Horizon_È(false);
            final IAttributeInstance var1 = this.ÂµÈ.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
            var1.Ý(EntityEnderman.Ý);
            super.Ý();
        }
        
        @Override
        public boolean Â() {
            if (this.Ø == null) {
                return super.Â();
            }
            if (!this.ÂµÈ.Ó(this.Ø)) {
                return false;
            }
            EntityEnderman.HorizonCode_Horizon_È(this.ÂµÈ, true);
            this.ÂµÈ.HorizonCode_Horizon_È(this.Ø, 10.0f, 10.0f);
            return true;
        }
        
        @Override
        public void Ø­áŒŠá() {
            if (this.Ø != null) {
                if (--this.áŒŠÆ <= 0) {
                    this.Ø­áŒŠá = this.Ø;
                    this.Ø = null;
                    super.Âµá€();
                    this.ÂµÈ.HorizonCode_Horizon_È("mob.endermen.stare", 1.0f, 1.0f);
                    this.ÂµÈ.HorizonCode_Horizon_È(true);
                    final IAttributeInstance var1 = this.ÂµÈ.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
                    var1.Â(EntityEnderman.Ý);
                }
            }
            else {
                if (this.Ø­áŒŠá != null) {
                    if (this.Ø­áŒŠá instanceof EntityPlayer && this.ÂµÈ.Ó((EntityPlayer)this.Ø­áŒŠá)) {
                        if (this.Ø­áŒŠá.Âµá€(this.ÂµÈ) < 16.0) {
                            this.ÂµÈ.Ø();
                        }
                        this.áˆºÑ¢Õ = 0;
                    }
                    else if (this.Ø­áŒŠá.Âµá€(this.ÂµÈ) > 256.0 && this.áˆºÑ¢Õ++ >= 30 && this.ÂµÈ.Â((Entity)this.Ø­áŒŠá)) {
                        this.áˆºÑ¢Õ = 0;
                    }
                }
                super.Ø­áŒŠá();
            }
        }
    }
    
    class Â extends EntityAIBase
    {
        private EntityEnderman Â;
        private static final String Ý = "CL_00002222";
        
        Â() {
            this.Â = EntityEnderman.this;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Â.Ï­Ðƒà.Çªà¢().Â("mobGriefing") && this.Â.ÇŽÅ().Ý().Ó() != Material.HorizonCode_Horizon_È && this.Â.ˆÐƒØ().nextInt(2000) == 0;
        }
        
        @Override
        public void Ø­áŒŠá() {
            final Random var1 = this.Â.ˆÐƒØ();
            final World var2 = this.Â.Ï­Ðƒà;
            final int var3 = MathHelper.Ý(this.Â.ŒÏ - 1.0 + var1.nextDouble() * 2.0);
            final int var4 = MathHelper.Ý(this.Â.Çªà¢ + var1.nextDouble() * 2.0);
            final int var5 = MathHelper.Ý(this.Â.Ê - 1.0 + var1.nextDouble() * 2.0);
            final BlockPos var6 = new BlockPos(var3, var4, var5);
            final Block var7 = var2.Â(var6).Ý();
            final Block var8 = var2.Â(var6.Âµá€()).Ý();
            if (this.HorizonCode_Horizon_È(var2, var6, this.Â.ÇŽÅ().Ý(), var7, var8)) {
                var2.HorizonCode_Horizon_È(var6, this.Â.ÇŽÅ(), 3);
                this.Â.HorizonCode_Horizon_È(Blocks.Â.¥à());
            }
        }
        
        private boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_179474_2_, final Block p_179474_3_, final Block p_179474_4_, final Block p_179474_5_) {
            return p_179474_3_.Ø­áŒŠá(worldIn, p_179474_2_) && p_179474_4_.Ó() == Material.HorizonCode_Horizon_È && p_179474_5_.Ó() != Material.HorizonCode_Horizon_È && p_179474_5_.áˆºÑ¢Õ();
        }
    }
    
    class Ý extends EntityAIBase
    {
        private EntityEnderman Â;
        private static final String Ý = "CL_00002220";
        
        Ý() {
            this.Â = EntityEnderman.this;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Â.Ï­Ðƒà.Çªà¢().Â("mobGriefing") && this.Â.ÇŽÅ().Ý().Ó() == Material.HorizonCode_Horizon_È && this.Â.ˆÐƒØ().nextInt(20) == 0;
        }
        
        @Override
        public void Ø­áŒŠá() {
            final Random var1 = this.Â.ˆÐƒØ();
            final World var2 = this.Â.Ï­Ðƒà;
            final int var3 = MathHelper.Ý(this.Â.ŒÏ - 2.0 + var1.nextDouble() * 4.0);
            final int var4 = MathHelper.Ý(this.Â.Çªà¢ + var1.nextDouble() * 3.0);
            final int var5 = MathHelper.Ý(this.Â.Ê - 2.0 + var1.nextDouble() * 4.0);
            final BlockPos var6 = new BlockPos(var3, var4, var5);
            final IBlockState var7 = var2.Â(var6);
            final Block var8 = var7.Ý();
            if (EntityEnderman.Ø­Ñ¢Ï­Ø­áˆº.contains(var8)) {
                this.Â.HorizonCode_Horizon_È(var7);
                var2.Â(var6, Blocks.Â.¥à());
            }
        }
    }
}
