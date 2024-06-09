package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Charsets;
import java.util.UUID;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import com.mojang.authlib.GameProfile;

public abstract class EntityPlayer extends EntityLivingBase
{
    public InventoryPlayer Ø­Ñ¢Ï­Ø­áˆº;
    private InventoryEnderChest HorizonCode_Horizon_È;
    public Container ŒÂ;
    public Container Ï­Ï;
    protected FoodStats ŠØ;
    protected int ˆÐƒØ;
    public float Çªà;
    public float ¥Å;
    public int Œáƒ;
    public double Œá;
    public double µÂ;
    public double Ñ¢ÇŽÏ;
    public double ÇªÂ;
    public double ÂµáˆºÂ;
    public double ¥Âµá€;
    protected boolean ÇŽÈ;
    public BlockPos ÇªáˆºÕ;
    private int Â;
    public float Ï­Ä;
    public float ¥áŠ;
    public float µÊ;
    private BlockPos Ý;
    private boolean Ø­áŒŠá;
    private BlockPos Âµá€;
    public PlayerCapabilities áˆºáˆºáŠ;
    public int áŒŠÉ;
    public int ÇŽØ;
    public float ŒÓ;
    private int Ó;
    private ItemStack à;
    private int Ø;
    protected float ÇŽÊ;
    protected float µ;
    private int áŒŠÆ;
    private final GameProfile áˆºÑ¢Õ;
    private boolean ÂµÈ;
    public EntityFishHook µÏ;
    private static final String á = "CL_00001711";
    
    public EntityPlayer(final World worldIn, final GameProfile p_i45324_2_) {
        super(worldIn);
        this.Ø­Ñ¢Ï­Ø­áˆº = new InventoryPlayer(this);
        this.HorizonCode_Horizon_È = new InventoryEnderChest();
        this.ŠØ = new FoodStats();
        this.áˆºáˆºáŠ = new PlayerCapabilities();
        this.ÇŽÊ = 0.1f;
        this.µ = 0.02f;
        this.ÂµÈ = false;
        this.ŠÓ = HorizonCode_Horizon_È(p_i45324_2_);
        this.áˆºÑ¢Õ = p_i45324_2_;
        this.ŒÂ = new ContainerPlayer(this.Ø­Ñ¢Ï­Ø­áˆº, !worldIn.ŠÄ, this);
        this.Ï­Ï = this.ŒÂ;
        final BlockPos var3 = worldIn.áŒŠà();
        this.Â(var3.HorizonCode_Horizon_È() + 0.5, var3.Â() + 1, var3.Ý() + 0.5, 0.0f, 0.0f);
        this.áŒŠá = 180.0f;
        this.£Ï = 20;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.µÐƒÓ().Â(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(1.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.10000000149011612);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(17, 0.0f);
        this.£Ó.HorizonCode_Horizon_È(18, (Object)0);
        this.£Ó.HorizonCode_Horizon_È(10, (Object)(byte)0);
    }
    
    public ItemStack Š() {
        return this.à;
    }
    
    public int Ø­Ñ¢á€() {
        return this.Ø;
    }
    
    public boolean Ñ¢Ó() {
        return this.à != null;
    }
    
    public int Ø­Æ() {
        return this.Ñ¢Ó() ? (this.à.á() - this.Ø) : 0;
    }
    
    public void áŒŠÔ() {
        if (this.à != null) {
            this.à.Â(this.Ï­Ðƒà, this, this.Ø);
        }
        this.ŠÕ();
    }
    
    public void ŠÕ() {
        this.à = null;
        this.Ø = 0;
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.à(false);
        }
    }
    
    public boolean £Ø­à() {
        return this.Ñ¢Ó() && this.à.HorizonCode_Horizon_È().Ý(this.à) == EnumAction.Ø­áŒŠá;
    }
    
    @Override
    public void á() {
        this.ÇªÓ = this.Ø­áŒŠá();
        if (this.Ø­áŒŠá()) {
            this.ŠÂµà = false;
        }
        if (this.à != null) {
            final ItemStack var1 = this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
            if (var1 == this.à) {
                if (this.Ø <= 25 && this.Ø % 4 == 0) {
                    this.HorizonCode_Horizon_È(var1, 5);
                }
                if (--this.Ø == 0 && !this.Ï­Ðƒà.ŠÄ) {
                    this.µÐƒáƒ();
                }
            }
            else {
                this.ŠÕ();
            }
        }
        if (this.Œáƒ > 0) {
            --this.Œáƒ;
        }
        if (this.Ï­Ó()) {
            ++this.Â;
            if (this.Â > 100) {
                this.Â = 100;
            }
            if (!this.Ï­Ðƒà.ŠÄ) {
                if (!this.Ø()) {
                    this.HorizonCode_Horizon_È(true, true, false);
                }
                else if (this.Ï­Ðƒà.ÂµÈ()) {
                    this.HorizonCode_Horizon_È(false, true, true);
                }
            }
        }
        else if (this.Â > 0) {
            ++this.Â;
            if (this.Â >= 110) {
                this.Â = 0;
            }
        }
        super.á();
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ï != null && !this.Ï­Ï.HorizonCode_Horizon_È(this)) {
            this.ˆà();
            this.Ï­Ï = this.ŒÂ;
        }
        if (this.ˆÏ() && this.áˆºáˆºáŠ.HorizonCode_Horizon_È) {
            this.¥à();
        }
        this.Œá = this.ÇªÂ;
        this.µÂ = this.ÂµáˆºÂ;
        this.Ñ¢ÇŽÏ = this.¥Âµá€;
        final double var2 = this.ŒÏ - this.ÇªÂ;
        final double var3 = this.Çªà¢ - this.ÂµáˆºÂ;
        final double var4 = this.Ê - this.¥Âµá€;
        final double var5 = 10.0;
        if (var2 > var5) {
            final double œï = this.ŒÏ;
            this.ÇªÂ = œï;
            this.Œá = œï;
        }
        if (var4 > var5) {
            final double ê = this.Ê;
            this.¥Âµá€ = ê;
            this.Ñ¢ÇŽÏ = ê;
        }
        if (var3 > var5) {
            final double çªà¢ = this.Çªà¢;
            this.ÂµáˆºÂ = çªà¢;
            this.µÂ = çªà¢;
        }
        if (var2 < -var5) {
            final double œï2 = this.ŒÏ;
            this.ÇªÂ = œï2;
            this.Œá = œï2;
        }
        if (var4 < -var5) {
            final double ê2 = this.Ê;
            this.¥Âµá€ = ê2;
            this.Ñ¢ÇŽÏ = ê2;
        }
        if (var3 < -var5) {
            final double çªà¢2 = this.Çªà¢;
            this.ÂµáˆºÂ = çªà¢2;
            this.µÂ = çªà¢2;
        }
        this.ÇªÂ += var2 * 0.25;
        this.¥Âµá€ += var4 * 0.25;
        this.ÂµáˆºÂ += var3 * 0.25;
        if (this.Æ == null) {
            this.Âµá€ = null;
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.ŠØ.HorizonCode_Horizon_È(this);
            this.HorizonCode_Horizon_È(StatList.à);
            if (this.Œ()) {
                this.HorizonCode_Horizon_È(StatList.Ø);
            }
        }
        final int var6 = 29999999;
        final double var7 = MathHelper.HorizonCode_Horizon_È(this.ŒÏ, -2.9999999E7, 2.9999999E7);
        final double var8 = MathHelper.HorizonCode_Horizon_È(this.Ê, -2.9999999E7, 2.9999999E7);
        if (var7 != this.ŒÏ || var8 != this.Ê) {
            this.Ý(var7, this.Çªà¢, var8);
        }
    }
    
    @Override
    public int à¢() {
        return this.áˆºáˆºáŠ.HorizonCode_Horizon_È ? 0 : 80;
    }
    
    @Override
    protected String Ç() {
        return "game.player.swim";
    }
    
    @Override
    protected String áˆºáˆºÈ() {
        return "game.player.swim.splash";
    }
    
    @Override
    public int Ï­Ô() {
        return 10;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String name, final float volume, final float pitch) {
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, name, volume, pitch);
    }
    
    protected void HorizonCode_Horizon_È(final ItemStack itemStackIn, final int p_71010_2_) {
        if (itemStackIn.ˆÏ­() == EnumAction.Ý) {
            this.HorizonCode_Horizon_È("random.drink", 0.5f, this.Ï­Ðƒà.Å.nextFloat() * 0.1f + 0.9f);
        }
        if (itemStackIn.ˆÏ­() == EnumAction.Â) {
            for (int var3 = 0; var3 < p_71010_2_; ++var3) {
                Vec3 var4 = new Vec3((this.ˆáƒ.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                var4 = var4.HorizonCode_Horizon_È(-this.áƒ * 3.1415927f / 180.0f);
                var4 = var4.Â(-this.É * 3.1415927f / 180.0f);
                final double var5 = -this.ˆáƒ.nextFloat() * 0.6 - 0.3;
                Vec3 var6 = new Vec3((this.ˆáƒ.nextFloat() - 0.5) * 0.3, var5, 0.6);
                var6 = var6.HorizonCode_Horizon_È(-this.áƒ * 3.1415927f / 180.0f);
                var6 = var6.Â(-this.É * 3.1415927f / 180.0f);
                var6 = var6.Â(this.ŒÏ, this.Çªà¢ + this.Ðƒáƒ(), this.Ê);
                if (itemStackIn.Âµá€()) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Õ, var6.HorizonCode_Horizon_È, var6.Â, var6.Ý, var4.HorizonCode_Horizon_È, var4.Â + 0.05, var4.Ý, Item_1028566121.HorizonCode_Horizon_È(itemStackIn.HorizonCode_Horizon_È()), itemStackIn.Ø());
                }
                else {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Õ, var6.HorizonCode_Horizon_È, var6.Â, var6.Ý, var4.HorizonCode_Horizon_È, var4.Â + 0.05, var4.Ý, Item_1028566121.HorizonCode_Horizon_È(itemStackIn.HorizonCode_Horizon_È()));
                }
            }
            this.HorizonCode_Horizon_È("random.eat", 0.5f + 0.5f * this.ˆáƒ.nextInt(2), (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    protected void µÐƒáƒ() {
        if (this.à != null) {
            this.HorizonCode_Horizon_È(this.à, 16);
            final int var1 = this.à.Â;
            final ItemStack var2 = this.à.Â(this.Ï­Ðƒà, this);
            if (var2 != this.à || (var2 != null && var2.Â != var1)) {
                this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Ø­Ñ¢Ï­Ø­áˆº.Ý] = var2;
                if (var2.Â == 0) {
                    this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Ø­Ñ¢Ï­Ø­áˆº.Ý] = null;
                }
            }
            this.ŠÕ();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 9) {
            this.µÐƒáƒ();
        }
        else if (p_70103_1_ == 23) {
            this.ÂµÈ = false;
        }
        else if (p_70103_1_ == 22) {
            this.ÂµÈ = true;
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    protected boolean ˆØ­áˆº() {
        return this.Ï­Ä() <= 0.0f || this.Ï­Ó();
    }
    
    protected void ˆà() {
        this.Ï­Ï = this.ŒÂ;
    }
    
    @Override
    public void Ø­á() {
        if (!this.Ï­Ðƒà.ŠÄ && this.Çªà¢()) {
            this.HorizonCode_Horizon_È((Entity)null);
            this.Âµá€(false);
        }
        else {
            final double var1 = this.ŒÏ;
            final double var2 = this.Çªà¢;
            final double var3 = this.Ê;
            final float var4 = this.É;
            final float var5 = this.áƒ;
            super.Ø­á();
            this.Çªà = this.¥Å;
            this.¥Å = 0.0f;
            this.á(this.ŒÏ - var1, this.Çªà¢ - var2, this.Ê - var3);
            if (this.Æ instanceof EntityPig) {
                this.áƒ = var5;
                this.É = var4;
                this.¥É = ((EntityPig)this.Æ).¥É;
            }
        }
    }
    
    public void áƒ() {
        this.HorizonCode_Horizon_È(0.6f, 1.8f);
        super.áƒ();
        this.áˆºÑ¢Õ(this.ÇŽÊ());
        this.ÇªØ­ = 0;
    }
    
    @Override
    protected void Ê() {
        super.Ê();
        this.µÏ();
        this.ÂµÕ = this.É;
    }
    
    @Override
    public void ˆÏ­() {
        if (this.ˆÐƒØ > 0) {
            --this.ˆÐƒØ;
        }
        if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.HorizonCode_Horizon_È && this.Ï­Ðƒà.Çªà¢().Â("naturalRegeneration")) {
            if (this.Ï­Ä() < this.ÇŽÊ() && this.Œ % 20 == 0) {
                this.a_(1.0f);
            }
            if (this.ŠØ.Ý() && this.Œ % 10 == 0) {
                this.ŠØ.HorizonCode_Horizon_È(this.ŠØ.HorizonCode_Horizon_È() + 1);
            }
        }
        this.Ø­Ñ¢Ï­Ø­áˆº.Ø();
        this.Çªà = this.¥Å;
        super.ˆÏ­();
        final IAttributeInstance var1 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
        if (!this.Ï­Ðƒà.ŠÄ) {
            var1.HorizonCode_Horizon_È(this.áˆºáˆºáŠ.Â());
        }
        this.Ø­Ñ¢á€ = this.µ;
        if (this.ÇªÂµÕ()) {
            this.Ø­Ñ¢á€ += (float)(this.µ * 0.3);
        }
        this.áŒŠÆ((float)var1.Âµá€());
        float var2 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        float var3 = (float)(Math.atan(-this.ˆá * 0.20000000298023224) * 15.0);
        if (var2 > 0.1f) {
            var2 = 0.1f;
        }
        if (!this.ŠÂµà || this.Ï­Ä() <= 0.0f) {
            var2 = 0.0f;
        }
        if (this.ŠÂµà || this.Ï­Ä() <= 0.0f) {
            var3 = 0.0f;
        }
        this.¥Å += (var2 - this.¥Å) * 0.4f;
        this.£É += (var3 - this.£É) * 0.8f;
        if (this.Ï­Ä() > 0.0f && !this.Ø­áŒŠá()) {
            AxisAlignedBB var4 = null;
            if (this.Æ != null && !this.Æ.ˆáŠ) {
                var4 = this.£É().HorizonCode_Horizon_È(this.Æ.£É()).Â(1.0, 0.0, 1.0);
            }
            else {
                var4 = this.£É().Â(1.0, 0.5, 1.0);
            }
            final List var5 = this.Ï­Ðƒà.Â(this, var4);
            for (int var6 = 0; var6 < var5.size(); ++var6) {
                final Entity var7 = var5.get(var6);
                if (!var7.ˆáŠ) {
                    this.Ø­à(var7);
                }
            }
        }
    }
    
    private void Ø­à(final Entity p_71044_1_) {
        p_71044_1_.HorizonCode_Horizon_È(this);
    }
    
    public int áŒŠÕ() {
        return this.£Ó.Ý(18);
    }
    
    public void HorizonCode_Horizon_È(final int p_85040_1_) {
        this.£Ó.Â(18, p_85040_1_);
    }
    
    public void Â(final int p_85039_1_) {
        final int var2 = this.áŒŠÕ();
        this.£Ó.Â(18, var2 + p_85039_1_);
    }
    
    @Override
    public void Â(final DamageSource cause) {
        super.Â(cause);
        this.HorizonCode_Horizon_È(0.2f, 0.2f);
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        this.ˆá = 0.10000000149011612;
        if (this.v_().equals("Notch")) {
            this.HorizonCode_Horizon_È(new ItemStack(Items.Âµá€, 1), true, false);
        }
        if (!this.Ï­Ðƒà.Çªà¢().Â("keepInventory")) {
            this.Ø­Ñ¢Ï­Ø­áˆº.ÂµÈ();
        }
        if (cause != null) {
            this.ÇŽÉ = -MathHelper.Â((this.Ñ¢à + this.É) * 3.1415927f / 180.0f) * 0.1f;
            this.ÇŽÕ = -MathHelper.HorizonCode_Horizon_È((this.Ñ¢à + this.É) * 3.1415927f / 180.0f) * 0.1f;
        }
        else {
            final double n = 0.0;
            this.ÇŽÕ = n;
            this.ÇŽÉ = n;
        }
        this.HorizonCode_Horizon_È(StatList.áŒŠà);
        this.Â(StatList.Ø);
    }
    
    @Override
    protected String ¥áŠ() {
        return "game.player.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "game.player.die";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn, final int amount) {
        this.Â(amount);
        final Collection var3 = this.ÇŽÅ().HorizonCode_Horizon_È(IScoreObjectiveCriteria.Ó);
        if (entityIn instanceof EntityPlayer) {
            this.HorizonCode_Horizon_È(StatList.ŒÏ);
            var3.addAll(this.ÇŽÅ().HorizonCode_Horizon_È(IScoreObjectiveCriteria.Âµá€));
            var3.addAll(this.µÕ(entityIn));
        }
        else {
            this.HorizonCode_Horizon_È(StatList.ŠÄ);
        }
        for (final ScoreObjective var5 : var3) {
            final Score var6 = this.ÇŽÅ().Â(this.v_(), var5);
            var6.HorizonCode_Horizon_È();
        }
    }
    
    private Collection µÕ(final Entity p_175137_1_) {
        final ScorePlayerTeam var2 = this.ÇŽÅ().Ó(this.v_());
        if (var2 != null) {
            final int var3 = var2.ÂµÈ().HorizonCode_Horizon_È();
            if (var3 >= 0 && var3 < IScoreObjectiveCriteria.áŒŠÆ.length) {
                for (final ScoreObjective var5 : this.ÇŽÅ().HorizonCode_Horizon_È(IScoreObjectiveCriteria.áŒŠÆ[var3])) {
                    final Score var6 = this.ÇŽÅ().Â(p_175137_1_.v_(), var5);
                    var6.HorizonCode_Horizon_È();
                }
            }
        }
        final ScorePlayerTeam var7 = this.ÇŽÅ().Ó(p_175137_1_.v_());
        if (var7 != null) {
            final int var8 = var7.ÂµÈ().HorizonCode_Horizon_È();
            if (var8 >= 0 && var8 < IScoreObjectiveCriteria.Ø.length) {
                return this.ÇŽÅ().HorizonCode_Horizon_È(IScoreObjectiveCriteria.Ø[var8]);
            }
        }
        return Lists.newArrayList();
    }
    
    public EntityItem HorizonCode_Horizon_È(final boolean p_71040_1_) {
        return this.HorizonCode_Horizon_È(this.Ø­Ñ¢Ï­Ø­áˆº.Â(this.Ø­Ñ¢Ï­Ø­áˆº.Ý, (p_71040_1_ && this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá() != null) ? this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá().Â : 1), false, true);
    }
    
    public EntityItem HorizonCode_Horizon_È(final ItemStack itemStackIn, final boolean p_71019_2_) {
        return this.HorizonCode_Horizon_È(itemStackIn, false, false);
    }
    
    public EntityItem HorizonCode_Horizon_È(final ItemStack p_146097_1_, final boolean p_146097_2_, final boolean p_146097_3_) {
        if (p_146097_1_ == null) {
            return null;
        }
        if (p_146097_1_.Â == 0) {
            return null;
        }
        final double var4 = this.Çªà¢ - 0.30000001192092896 + this.Ðƒáƒ();
        final EntityItem var5 = new EntityItem(this.Ï­Ðƒà, this.ŒÏ, var4, this.Ê, p_146097_1_);
        var5.HorizonCode_Horizon_È(40);
        if (p_146097_3_) {
            var5.Â(this.v_());
        }
        if (p_146097_2_) {
            final float var6 = this.ˆáƒ.nextFloat() * 0.5f;
            final float var7 = this.ˆáƒ.nextFloat() * 3.1415927f * 2.0f;
            var5.ÇŽÉ = -MathHelper.HorizonCode_Horizon_È(var7) * var6;
            var5.ÇŽÕ = MathHelper.Â(var7) * var6;
            var5.ˆá = 0.20000000298023224;
        }
        else {
            float var6 = 0.3f;
            var5.ÇŽÉ = -MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f) * var6;
            var5.ÇŽÕ = MathHelper.Â(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f) * var6;
            var5.ˆá = -MathHelper.HorizonCode_Horizon_È(this.áƒ / 180.0f * 3.1415927f) * var6 + 0.1f;
            final float var7 = this.ˆáƒ.nextFloat() * 3.1415927f * 2.0f;
            var6 = 0.02f * this.ˆáƒ.nextFloat();
            final EntityItem entityItem = var5;
            entityItem.ÇŽÉ += Math.cos(var7) * var6;
            final EntityItem entityItem2 = var5;
            entityItem2.ˆá += (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.1f;
            final EntityItem entityItem3 = var5;
            entityItem3.ÇŽÕ += Math.sin(var7) * var6;
        }
        this.HorizonCode_Horizon_È(var5);
        if (p_146097_3_) {
            this.HorizonCode_Horizon_È(StatList.Æ);
        }
        return var5;
    }
    
    protected void HorizonCode_Horizon_È(final EntityItem p_71012_1_) {
        this.Ï­Ðƒà.HorizonCode_Horizon_È(p_71012_1_);
    }
    
    public float HorizonCode_Horizon_È(final Block p_180471_1_) {
        float var2 = this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(p_180471_1_);
        if (var2 > 1.0f) {
            final int var3 = EnchantmentHelper.Ý(this);
            final ItemStack var4 = this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
            if (var3 > 0 && var4 != null) {
                var2 += var3 * var3 + 1;
            }
        }
        if (this.HorizonCode_Horizon_È(Potion.Âµá€)) {
            var2 *= 1.0f + (this.Â(Potion.Âµá€).Ý() + 1) * 0.2f;
        }
        if (this.HorizonCode_Horizon_È(Potion.Ó)) {
            float var5 = 1.0f;
            switch (this.Â(Potion.Ó).Ý()) {
                case 0: {
                    var5 = 0.3f;
                    break;
                }
                case 1: {
                    var5 = 0.09f;
                    break;
                }
                case 2: {
                    var5 = 0.0027f;
                    break;
                }
                default: {
                    var5 = 8.1E-4f;
                    break;
                }
            }
            var2 *= var5;
        }
        if (this.HorizonCode_Horizon_È(Material.Ø) && !EnchantmentHelper.áŒŠÆ(this)) {
            var2 /= 5.0f;
        }
        if (!this.ŠÂµà) {
            var2 /= 5.0f;
        }
        return var2;
    }
    
    public boolean Â(final Block p_146099_1_) {
        return this.Ø­Ñ¢Ï­Ø­áˆº.Â(p_146099_1_);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ŠÓ = HorizonCode_Horizon_È(this.áˆºÑ¢Õ);
        final NBTTagList var2 = tagCompund.Ý("Inventory", 10);
        this.Ø­Ñ¢Ï­Ø­áˆº.Â(var2);
        this.Ø­Ñ¢Ï­Ø­áˆº.Ý = tagCompund.Ó("SelectedItemSlot");
        this.ÇŽÈ = tagCompund.£á("Sleeping");
        this.Â = tagCompund.Âµá€("SleepTimer");
        this.ŒÓ = tagCompund.Ø("XpP");
        this.áŒŠÉ = tagCompund.Ó("XpLevel");
        this.ÇŽØ = tagCompund.Ó("XpTotal");
        this.Ó = tagCompund.Ó("XpSeed");
        if (this.Ó == 0) {
            this.Ó = this.ˆáƒ.nextInt();
        }
        this.HorizonCode_Horizon_È(tagCompund.Ó("Score"));
        if (this.ÇŽÈ) {
            this.ÇªáˆºÕ = new BlockPos(this);
            this.HorizonCode_Horizon_È(true, true, false);
        }
        if (tagCompund.Â("SpawnX", 99) && tagCompund.Â("SpawnY", 99) && tagCompund.Â("SpawnZ", 99)) {
            this.Ý = new BlockPos(tagCompund.Ó("SpawnX"), tagCompund.Ó("SpawnY"), tagCompund.Ó("SpawnZ"));
            this.Ø­áŒŠá = tagCompund.£á("SpawnForced");
        }
        this.ŠØ.HorizonCode_Horizon_È(tagCompund);
        this.áˆºáˆºáŠ.Â(tagCompund);
        if (tagCompund.Â("EnderItems", 9)) {
            final NBTTagList var3 = tagCompund.Ý("EnderItems", 10);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Inventory", this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new NBTTagList()));
        tagCompound.HorizonCode_Horizon_È("SelectedItemSlot", this.Ø­Ñ¢Ï­Ø­áˆº.Ý);
        tagCompound.HorizonCode_Horizon_È("Sleeping", this.ÇŽÈ);
        tagCompound.HorizonCode_Horizon_È("SleepTimer", (short)this.Â);
        tagCompound.HorizonCode_Horizon_È("XpP", this.ŒÓ);
        tagCompound.HorizonCode_Horizon_È("XpLevel", this.áŒŠÉ);
        tagCompound.HorizonCode_Horizon_È("XpTotal", this.ÇŽØ);
        tagCompound.HorizonCode_Horizon_È("XpSeed", this.Ó);
        tagCompound.HorizonCode_Horizon_È("Score", this.áŒŠÕ());
        if (this.Ý != null) {
            tagCompound.HorizonCode_Horizon_È("SpawnX", this.Ý.HorizonCode_Horizon_È());
            tagCompound.HorizonCode_Horizon_È("SpawnY", this.Ý.Â());
            tagCompound.HorizonCode_Horizon_È("SpawnZ", this.Ý.Ý());
            tagCompound.HorizonCode_Horizon_È("SpawnForced", this.Ø­áŒŠá);
        }
        this.ŠØ.Â(tagCompound);
        this.áˆºáˆºáŠ.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("EnderItems", this.HorizonCode_Horizon_È.Ø­áŒŠá());
        final ItemStack var2 = this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() != null) {
            tagCompound.HorizonCode_Horizon_È("SelectedItem", var2.Â(new NBTTagCompound()));
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (this.áˆºáˆºáŠ.HorizonCode_Horizon_È && !source.à()) {
            return false;
        }
        this.ŠÕ = 0;
        if (this.Ï­Ä() <= 0.0f) {
            return false;
        }
        if (this.Ï­Ó() && !this.Ï­Ðƒà.ŠÄ) {
            this.HorizonCode_Horizon_È(true, true, false);
        }
        if (source.ˆà()) {
            if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.HorizonCode_Horizon_È) {
                amount = 0.0f;
            }
            if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Â) {
                amount = amount / 2.0f + 1.0f;
            }
            if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) {
                amount = amount * 3.0f / 2.0f;
            }
        }
        if (amount == 0.0f) {
            return false;
        }
        Entity var3 = source.áˆºÑ¢Õ();
        if (var3 instanceof EntityArrow && ((EntityArrow)var3).Ý != null) {
            var3 = ((EntityArrow)var3).Ý;
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    public boolean Ø­áŒŠá(final EntityPlayer other) {
        final Team var2 = this.Çªáˆºá();
        final Team var3 = other.Çªáˆºá();
        return var2 == null || !var2.HorizonCode_Horizon_È(var3) || var2.Ó();
    }
    
    @Override
    protected void ÂµÈ(final float p_70675_1_) {
        this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(p_70675_1_);
    }
    
    @Override
    public int áŒŠÉ() {
        return this.Ø­Ñ¢Ï­Ø­áˆº.áˆºÑ¢Õ();
    }
    
    public float ÂµÂ() {
        int var1 = 0;
        for (final ItemStack var5 : this.Ø­Ñ¢Ï­Ø­áˆº.Â) {
            if (var5 != null) {
                ++var1;
            }
        }
        return var1 / this.Ø­Ñ¢Ï­Ø­áˆº.Â.length;
    }
    
    @Override
    protected void Â(final DamageSource p_70665_1_, float p_70665_2_) {
        if (!this.HorizonCode_Horizon_È(p_70665_1_)) {
            if (!p_70665_1_.Âµá€() && this.£Ø­à() && p_70665_2_ > 0.0f) {
                p_70665_2_ = (1.0f + p_70665_2_) * 0.5f;
            }
            p_70665_2_ = this.Ý(p_70665_1_, p_70665_2_);
            final float var3;
            p_70665_2_ = (var3 = this.Ø­áŒŠá(p_70665_1_, p_70665_2_));
            p_70665_2_ = Math.max(p_70665_2_ - this.Ñ¢È(), 0.0f);
            this.ˆÏ­(this.Ñ¢È() - (var3 - p_70665_2_));
            if (p_70665_2_ != 0.0f) {
                this.Ý(p_70665_1_.Ó());
                final float var4 = this.Ï­Ä();
                this.áˆºÑ¢Õ(this.Ï­Ä() - p_70665_2_);
                this.ÇŽØ().HorizonCode_Horizon_È(p_70665_1_, var4, p_70665_2_);
                if (p_70665_2_ < 3.4028235E37f) {
                    this.HorizonCode_Horizon_È(StatList.Ï­Ðƒà, Math.round(p_70665_2_ * 10.0f));
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final TileEntitySign p_175141_1_) {
    }
    
    public void HorizonCode_Horizon_È(final CommandBlockLogic p_146095_1_) {
    }
    
    public void HorizonCode_Horizon_È(final IMerchant villager) {
    }
    
    public void HorizonCode_Horizon_È(final IInventory chestInventory) {
    }
    
    public void HorizonCode_Horizon_È(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
    }
    
    public void HorizonCode_Horizon_È(final IInteractionObject guiOwner) {
    }
    
    public void HorizonCode_Horizon_È(final ItemStack bookStack) {
    }
    
    public boolean ˆà(final Entity p_70998_1_) {
        if (this.Ø­áŒŠá()) {
            if (p_70998_1_ instanceof IInventory) {
                this.HorizonCode_Horizon_È((IInventory)p_70998_1_);
            }
            return false;
        }
        ItemStack var2 = this.áŒŠá();
        final ItemStack var3 = (var2 != null) ? var2.áˆºÑ¢Õ() : null;
        if (!p_70998_1_.b_(this)) {
            if (var2 != null && p_70998_1_ instanceof EntityLivingBase) {
                if (this.áˆºáˆºáŠ.Ø­áŒŠá) {
                    var2 = var3;
                }
                if (var2.HorizonCode_Horizon_È(this, (EntityLivingBase)p_70998_1_)) {
                    if (var2.Â <= 0 && !this.áˆºáˆºáŠ.Ø­áŒŠá) {
                        this.ˆØ();
                    }
                    return true;
                }
            }
            return false;
        }
        if (var2 != null && var2 == this.áŒŠá()) {
            if (var2.Â <= 0 && !this.áˆºáˆºáŠ.Ø­áŒŠá) {
                this.ˆØ();
            }
            else if (var2.Â < var3.Â && this.áˆºáˆºáŠ.Ø­áŒŠá) {
                var2.Â = var3.Â;
            }
        }
        return true;
    }
    
    public ItemStack áŒŠá() {
        return this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
    }
    
    public void ˆØ() {
        this.Ø­Ñ¢Ï­Ø­áˆº.Ý(this.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
    }
    
    @Override
    public double Ï­Ï­Ï() {
        return -0.35;
    }
    
    public void ¥Æ(final Entity targetEntity) {
        if (targetEntity.Å() && !targetEntity.áŒŠÆ(this)) {
            float var2 = (float)this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).Âµá€();
            final byte var3 = 0;
            float var4 = 0.0f;
            if (targetEntity instanceof EntityLivingBase) {
                var4 = EnchantmentHelper.HorizonCode_Horizon_È(this.Çª(), ((EntityLivingBase)targetEntity).¥áŒŠà());
            }
            else {
                var4 = EnchantmentHelper.HorizonCode_Horizon_È(this.Çª(), EnumCreatureAttribute.HorizonCode_Horizon_È);
            }
            int var5 = var3 + EnchantmentHelper.HorizonCode_Horizon_È(this);
            if (this.ÇªÂµÕ()) {
                ++var5;
            }
            if (var2 > 0.0f || var4 > 0.0f) {
                final boolean var6 = this.Ï­à > 0.0f && !this.ŠÂµà && !this.i_() && !this.£ÂµÄ() && !this.HorizonCode_Horizon_È(Potion.µà) && this.Æ == null && targetEntity instanceof EntityLivingBase;
                if (var6 && var2 > 0.0f) {
                    var2 *= 1.5f;
                }
                var2 += var4;
                boolean var7 = false;
                final int var8 = EnchantmentHelper.Â(this);
                if (targetEntity instanceof EntityLivingBase && var8 > 0 && !targetEntity.ˆÏ()) {
                    var7 = true;
                    targetEntity.Âµá€(1);
                }
                final double var9 = targetEntity.ÇŽÉ;
                final double var10 = targetEntity.ˆá;
                final double var11 = targetEntity.ÇŽÕ;
                final boolean var12 = targetEntity.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), var2);
                if (var12) {
                    if (var5 > 0) {
                        targetEntity.à(-MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f) * var5 * 0.5f, 0.1, MathHelper.Â(this.É * 3.1415927f / 180.0f) * var5 * 0.5f);
                        this.ÇŽÉ *= 0.6;
                        this.ÇŽÕ *= 0.6;
                        this.Â(false);
                    }
                    if (targetEntity instanceof EntityPlayerMP && targetEntity.È) {
                        ((EntityPlayerMP)targetEntity).HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S12PacketEntityVelocity(targetEntity));
                        targetEntity.È = false;
                        targetEntity.ÇŽÉ = var9;
                        targetEntity.ˆá = var10;
                        targetEntity.ÇŽÕ = var11;
                    }
                    if (var6) {
                        this.Â(targetEntity);
                    }
                    if (var4 > 0.0f) {
                        this.Ý(targetEntity);
                    }
                    if (var2 >= 18.0f) {
                        this.HorizonCode_Horizon_È(AchievementList.ˆá);
                    }
                    this.ˆÏ­(targetEntity);
                    if (targetEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.HorizonCode_Horizon_È((EntityLivingBase)targetEntity, this);
                    }
                    EnchantmentHelper.Â(this, targetEntity);
                    final ItemStack var13 = this.áŒŠá();
                    Object var14 = targetEntity;
                    if (targetEntity instanceof EntityDragonPart) {
                        final IEntityMultiPart var15 = ((EntityDragonPart)targetEntity).HorizonCode_Horizon_È;
                        if (var15 instanceof EntityLivingBase) {
                            var14 = var15;
                        }
                    }
                    if (var13 != null && var14 instanceof EntityLivingBase) {
                        var13.HorizonCode_Horizon_È((EntityLivingBase)var14, this);
                        if (var13.Â <= 0) {
                            this.ˆØ();
                        }
                    }
                    if (targetEntity instanceof EntityLivingBase) {
                        this.HorizonCode_Horizon_È(StatList.Šáƒ, Math.round(var2 * 10.0f));
                        if (var8 > 0) {
                            targetEntity.Âµá€(var8 * 4);
                        }
                    }
                    this.Ý(0.3f);
                }
                else if (var7) {
                    targetEntity.¥à();
                }
            }
        }
    }
    
    public void Â(final Entity p_71009_1_) {
    }
    
    public void Ý(final Entity p_71047_1_) {
    }
    
    public void µà() {
    }
    
    @Override
    public void á€() {
        super.á€();
        this.ŒÂ.Â(this);
        if (this.Ï­Ï != null) {
            this.Ï­Ï.Â(this);
        }
    }
    
    @Override
    public boolean £Ï() {
        return !this.ÇŽÈ && super.£Ï();
    }
    
    public boolean µÕ() {
        return false;
    }
    
    public GameProfile áˆºà() {
        return this.áˆºÑ¢Õ;
    }
    
    public Â HorizonCode_Horizon_È(final BlockPos p_180469_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (this.Ï­Ó() || !this.Œ()) {
                return EntityPlayer.Â.Âµá€;
            }
            if (!this.Ï­Ðƒà.£à.Ø­áŒŠá()) {
                return EntityPlayer.Â.Â;
            }
            if (this.Ï­Ðƒà.ÂµÈ()) {
                return EntityPlayer.Â.Ý;
            }
            if (Math.abs(this.ŒÏ - p_180469_1_.HorizonCode_Horizon_È()) > 3.0 || Math.abs(this.Çªà¢ - p_180469_1_.Â()) > 2.0 || Math.abs(this.Ê - p_180469_1_.Ý()) > 3.0) {
                return EntityPlayer.Â.Ø­áŒŠá;
            }
            final double var2 = 8.0;
            final double var3 = 5.0;
            final List var4 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityMob.class, new AxisAlignedBB(p_180469_1_.HorizonCode_Horizon_È() - var2, p_180469_1_.Â() - var3, p_180469_1_.Ý() - var2, p_180469_1_.HorizonCode_Horizon_È() + var2, p_180469_1_.Â() + var3, p_180469_1_.Ý() + var2));
            if (!var4.isEmpty()) {
                return EntityPlayer.Â.Ó;
            }
        }
        if (this.áˆºÇŽØ()) {
            this.HorizonCode_Horizon_È((Entity)null);
        }
        this.HorizonCode_Horizon_È(0.2f, 0.2f);
        if (this.Ï­Ðƒà.Ó(p_180469_1_)) {
            final EnumFacing var5 = (EnumFacing)this.Ï­Ðƒà.Â(p_180469_1_).HorizonCode_Horizon_È(BlockDirectional.ŠÂµà);
            float var6 = 0.5f;
            float var7 = 0.5f;
            switch (EntityPlayer.Ý.HorizonCode_Horizon_È[var5.ordinal()]) {
                case 1: {
                    var7 = 0.9f;
                    break;
                }
                case 2: {
                    var7 = 0.1f;
                    break;
                }
                case 3: {
                    var6 = 0.1f;
                    break;
                }
                case 4: {
                    var6 = 0.9f;
                    break;
                }
            }
            this.HorizonCode_Horizon_È(var5);
            this.Ý(p_180469_1_.HorizonCode_Horizon_È() + var6, p_180469_1_.Â() + 0.6875f, p_180469_1_.Ý() + var7);
        }
        else {
            this.Ý(p_180469_1_.HorizonCode_Horizon_È() + 0.5f, p_180469_1_.Â() + 0.6875f, p_180469_1_.Ý() + 0.5f);
        }
        this.ÇŽÈ = true;
        this.Â = 0;
        this.ÇªáˆºÕ = p_180469_1_;
        final double çžé = 0.0;
        this.ˆá = çžé;
        this.ÇŽÕ = çžé;
        this.ÇŽÉ = çžé;
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.Ï­Ðƒà.Ê();
        }
        return EntityPlayer.Â.HorizonCode_Horizon_È;
    }
    
    private void HorizonCode_Horizon_È(final EnumFacing p_175139_1_) {
        this.Ï­Ä = 0.0f;
        this.µÊ = 0.0f;
        switch (EntityPlayer.Ý.HorizonCode_Horizon_È[p_175139_1_.ordinal()]) {
            case 1: {
                this.µÊ = -1.8f;
                break;
            }
            case 2: {
                this.µÊ = 1.8f;
                break;
            }
            case 3: {
                this.Ï­Ä = 1.8f;
                break;
            }
            case 4: {
                this.Ï­Ä = -1.8f;
                break;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70999_1_, final boolean updateWorldFlag, final boolean setSpawn) {
        this.HorizonCode_Horizon_È(0.6f, 1.8f);
        final IBlockState var4 = this.Ï­Ðƒà.Â(this.ÇªáˆºÕ);
        if (this.ÇªáˆºÕ != null && var4.Ý() == Blocks.Ê) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ÇªáˆºÕ, var4.HorizonCode_Horizon_È(BlockBed.à¢, false), 4);
            BlockPos var5 = BlockBed.Â(this.Ï­Ðƒà, this.ÇªáˆºÕ, 0);
            if (var5 == null) {
                var5 = this.ÇªáˆºÕ.Ø­áŒŠá();
            }
            this.Ý(var5.HorizonCode_Horizon_È() + 0.5f, var5.Â() + 0.1f, var5.Ý() + 0.5f);
        }
        this.ÇŽÈ = false;
        if (!this.Ï­Ðƒà.ŠÄ && updateWorldFlag) {
            this.Ï­Ðƒà.Ê();
        }
        this.Â = (p_70999_1_ ? 0 : 100);
        if (setSpawn) {
            this.HorizonCode_Horizon_È(this.ÇªáˆºÕ, false);
        }
    }
    
    private boolean Ø() {
        return this.Ï­Ðƒà.Â(this.ÇªáˆºÕ).Ý() == Blocks.Ê;
    }
    
    public static BlockPos HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180467_1_, final boolean p_180467_2_) {
        if (worldIn.Â(p_180467_1_).Ý() == Blocks.Ê) {
            return BlockBed.Â(worldIn, p_180467_1_, 0);
        }
        if (!p_180467_2_) {
            return null;
        }
        final Material var3 = worldIn.Â(p_180467_1_).Ý().Ó();
        final Material var4 = worldIn.Â(p_180467_1_.Ø­áŒŠá()).Ý().Ó();
        final boolean var5 = !var3.Â() && !var3.HorizonCode_Horizon_È();
        final boolean var6 = !var4.Â() && !var4.HorizonCode_Horizon_È();
        return (var5 && var6) ? p_180467_1_ : null;
    }
    
    public float ÐƒÂ() {
        if (this.ÇªáˆºÕ != null) {
            final EnumFacing var1 = (EnumFacing)this.Ï­Ðƒà.Â(this.ÇªáˆºÕ).HorizonCode_Horizon_È(BlockDirectional.ŠÂµà);
            switch (EntityPlayer.Ý.HorizonCode_Horizon_È[var1.ordinal()]) {
                case 1: {
                    return 90.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 0.0f;
                }
                case 4: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }
    
    @Override
    public boolean Ï­Ó() {
        return this.ÇŽÈ;
    }
    
    public boolean £áƒ() {
        return this.ÇŽÈ && this.Â >= 100;
    }
    
    public int Ï­áˆºÓ() {
        return this.Â;
    }
    
    public void Â(final IChatComponent p_146105_1_) {
    }
    
    public BlockPos ÇŽÄ() {
        return this.Ý;
    }
    
    public boolean ˆÈ() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180473_1_, final boolean p_180473_2_) {
        if (p_180473_1_ != null) {
            this.Ý = p_180473_1_;
            this.Ø­áŒŠá = p_180473_2_;
        }
        else {
            this.Ý = null;
            this.Ø­áŒŠá = false;
        }
    }
    
    public void HorizonCode_Horizon_È(final StatBase p_71029_1_) {
        this.HorizonCode_Horizon_È(p_71029_1_, 1);
    }
    
    public void HorizonCode_Horizon_È(final StatBase p_71064_1_, final int p_71064_2_) {
    }
    
    public void Â(final StatBase p_175145_1_) {
    }
    
    public void ŠÏ() {
        super.ŠÏ();
        this.HorizonCode_Horizon_È(StatList.µÕ);
        if (this.ÇªÂµÕ()) {
            this.Ý(0.8f);
        }
        else {
            this.Ý(0.2f);
        }
    }
    
    @Override
    public void Ó(final float p_70612_1_, final float p_70612_2_) {
        final double var3 = this.ŒÏ;
        final double var4 = this.Çªà¢;
        final double var5 = this.Ê;
        if (this.áˆºáˆºáŠ.Â && this.Æ == null) {
            final double var6 = this.ˆá;
            final float var7 = this.Ø­Ñ¢á€;
            this.Ø­Ñ¢á€ = this.áˆºáˆºáŠ.HorizonCode_Horizon_È() * (this.ÇªÂµÕ() ? 2 : 1);
            super.Ó(p_70612_1_, p_70612_2_);
            this.ˆá = var6 * 0.6;
            this.Ø­Ñ¢á€ = var7;
        }
        else {
            super.Ó(p_70612_1_, p_70612_2_);
        }
        this.ÂµÈ(this.ŒÏ - var3, this.Çªà¢ - var4, this.Ê - var5);
    }
    
    @Override
    public float áˆºá() {
        return (float)this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Âµá€();
    }
    
    public void ÂµÈ(final double p_71000_1_, final double p_71000_3_, final double p_71000_5_) {
        if (this.Æ == null) {
            if (this.HorizonCode_Horizon_È(Material.Ø)) {
                final int var7 = Math.round(MathHelper.HorizonCode_Horizon_È(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 0) {
                    this.HorizonCode_Horizon_È(StatList.£à, var7);
                    this.Ý(0.015f * var7 * 0.01f);
                }
            }
            else if (this.£ÂµÄ()) {
                final int var7 = Math.round(MathHelper.HorizonCode_Horizon_È(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 0) {
                    this.HorizonCode_Horizon_È(StatList.á, var7);
                    this.Ý(0.015f * var7 * 0.01f);
                }
            }
            else if (this.i_()) {
                if (p_71000_3_ > 0.0) {
                    this.HorizonCode_Horizon_È(StatList.£á, (int)Math.round(p_71000_3_ * 100.0));
                }
            }
            else if (this.ŠÂµà) {
                final int var7 = Math.round(MathHelper.HorizonCode_Horizon_È(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 0) {
                    this.HorizonCode_Horizon_È(StatList.áŒŠÆ, var7);
                    if (this.ÇªÂµÕ()) {
                        this.HorizonCode_Horizon_È(StatList.ÂµÈ, var7);
                        this.Ý(0.099999994f * var7 * 0.01f);
                    }
                    else {
                        if (this.Çªà¢()) {
                            this.HorizonCode_Horizon_È(StatList.áˆºÑ¢Õ, var7);
                        }
                        this.Ý(0.01f * var7 * 0.01f);
                    }
                }
            }
            else {
                final int var7 = Math.round(MathHelper.HorizonCode_Horizon_È(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 25) {
                    this.HorizonCode_Horizon_È(StatList.Å, var7);
                }
            }
        }
    }
    
    private void á(final double p_71015_1_, final double p_71015_3_, final double p_71015_5_) {
        if (this.Æ != null) {
            final int var7 = Math.round(MathHelper.HorizonCode_Horizon_È(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0f);
            if (var7 > 0) {
                if (this.Æ instanceof EntityMinecart) {
                    this.HorizonCode_Horizon_È(StatList.µà, var7);
                    if (this.Âµá€ == null) {
                        this.Âµá€ = new BlockPos(this);
                    }
                    else if (this.Âµá€.Ý(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.Çªà¢), MathHelper.Ý(this.Ê)) >= 1000000.0) {
                        this.HorizonCode_Horizon_È(AchievementList.µà);
                    }
                }
                else if (this.Æ instanceof EntityBoat) {
                    this.HorizonCode_Horizon_È(StatList.ˆà, var7);
                }
                else if (this.Æ instanceof EntityPig) {
                    this.HorizonCode_Horizon_È(StatList.¥Æ, var7);
                }
                else if (this.Æ instanceof EntityHorse) {
                    this.HorizonCode_Horizon_È(StatList.Ø­à, var7);
                }
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        if (!this.áˆºáˆºáŠ.Ý) {
            if (distance >= 2.0f) {
                this.HorizonCode_Horizon_È(StatList.ˆÏ­, (int)Math.round(distance * 100.0));
            }
            super.Ø­áŒŠá(distance, damageMultiplier);
        }
    }
    
    @Override
    protected void Ä() {
        if (!this.Ø­áŒŠá()) {
            super.Ä();
        }
    }
    
    @Override
    protected String £à(final int p_146067_1_) {
        return (p_146067_1_ > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase entityLivingIn) {
        if (entityLivingIn instanceof IMob) {
            this.HorizonCode_Horizon_È(AchievementList.¥Æ);
        }
        final EntityList.HorizonCode_Horizon_È var2 = EntityList.HorizonCode_Horizon_È.get(EntityList.HorizonCode_Horizon_È(entityLivingIn));
        if (var2 != null) {
            this.HorizonCode_Horizon_È(var2.Ø­áŒŠá);
        }
    }
    
    @Override
    public void ¥Ä() {
        if (!this.áˆºáˆºáŠ.Â) {
            super.¥Ä();
        }
    }
    
    @Override
    public ItemStack ÂµÈ(final int p_82169_1_) {
        return this.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_82169_1_);
    }
    
    public void ˆà(int p_71023_1_) {
        this.Â(p_71023_1_);
        final int var2 = Integer.MAX_VALUE - this.ÇŽØ;
        if (p_71023_1_ > var2) {
            p_71023_1_ = var2;
        }
        this.ŒÓ += p_71023_1_ / this.ÇªÉ();
        this.ÇŽØ += p_71023_1_;
        while (this.ŒÓ >= 1.0f) {
            this.ŒÓ = (this.ŒÓ - 1.0f) * this.ÇªÉ();
            this.Ø­à(1);
            this.ŒÓ /= this.ÇªÉ();
        }
    }
    
    public int ˆÅ() {
        return this.Ó;
    }
    
    public void ¥Æ(final int p_71013_1_) {
        this.áŒŠÉ -= p_71013_1_;
        if (this.áŒŠÉ < 0) {
            this.áŒŠÉ = 0;
            this.ŒÓ = 0.0f;
            this.ÇŽØ = 0;
        }
        this.Ó = this.ˆáƒ.nextInt();
    }
    
    public void Ø­à(final int p_82242_1_) {
        this.áŒŠÉ += p_82242_1_;
        if (this.áŒŠÉ < 0) {
            this.áŒŠÉ = 0;
            this.ŒÓ = 0.0f;
            this.ÇŽØ = 0;
        }
        if (p_82242_1_ > 0 && this.áŒŠÉ % 5 == 0 && this.áŒŠÆ < this.Œ - 100.0f) {
            final float var2 = (this.áŒŠÉ > 30) ? 1.0f : (this.áŒŠÉ / 30.0f);
            this.Ï­Ðƒà.HorizonCode_Horizon_È((Entity)this, "random.levelup", var2 * 0.75f, 1.0f);
            this.áŒŠÆ = this.Œ;
        }
    }
    
    public int ÇªÉ() {
        return (this.áŒŠÉ >= 30) ? (112 + (this.áŒŠÉ - 30) * 9) : ((this.áŒŠÉ >= 15) ? (37 + (this.áŒŠÉ - 15) * 5) : (7 + this.áŒŠÉ * 2));
    }
    
    public void Ý(final float p_71020_1_) {
        if (!this.áˆºáˆºáŠ.HorizonCode_Horizon_È && !this.Ï­Ðƒà.ŠÄ) {
            this.ŠØ.HorizonCode_Horizon_È(p_71020_1_);
        }
    }
    
    public FoodStats ŠÏ­áˆºá() {
        return this.ŠØ;
    }
    
    public boolean Ý(final boolean p_71043_1_) {
        return (p_71043_1_ || this.ŠØ.Ý()) && !this.áˆºáˆºáŠ.HorizonCode_Horizon_È;
    }
    
    public boolean ÇŽà() {
        return this.Ï­Ä() > 0.0f && this.Ï­Ä() < this.ÇŽÊ();
    }
    
    public void Â(final ItemStack p_71008_1_, final int p_71008_2_) {
        if (p_71008_1_ != this.à) {
            this.à = p_71008_1_;
            this.Ø = p_71008_2_;
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.à(true);
            }
        }
    }
    
    public boolean ŠáˆºÂ() {
        return this.áˆºáˆºáŠ.Âµá€;
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_175151_1_, final EnumFacing p_175151_2_, final ItemStack p_175151_3_) {
        if (this.áˆºáˆºáŠ.Âµá€) {
            return true;
        }
        if (p_175151_3_ == null) {
            return false;
        }
        final BlockPos var4 = p_175151_1_.HorizonCode_Horizon_È(p_175151_2_.Âµá€());
        final Block var5 = this.Ï­Ðƒà.Â(var4).Ý();
        return p_175151_3_.Ø­áŒŠá(var5) || p_175151_3_.Ï­Ðƒà();
    }
    
    @Override
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        if (this.Ï­Ðƒà.Çªà¢().Â("keepInventory")) {
            return 0;
        }
        final int var2 = this.áŒŠÉ * 7;
        return (var2 > 100) ? 100 : var2;
    }
    
    @Override
    protected boolean ŠØ() {
        return true;
    }
    
    @Override
    public boolean ¥Ï() {
        return true;
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_71049_1_, final boolean p_71049_2_) {
        if (p_71049_2_) {
            this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(p_71049_1_.Ø­Ñ¢Ï­Ø­áˆº);
            this.áˆºÑ¢Õ(p_71049_1_.Ï­Ä());
            this.ŠØ = p_71049_1_.ŠØ;
            this.áŒŠÉ = p_71049_1_.áŒŠÉ;
            this.ÇŽØ = p_71049_1_.ÇŽØ;
            this.ŒÓ = p_71049_1_.ŒÓ;
            this.HorizonCode_Horizon_È(p_71049_1_.áŒŠÕ());
            this.Û = p_71049_1_.Û;
        }
        else if (this.Ï­Ðƒà.Çªà¢().Â("keepInventory")) {
            this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(p_71049_1_.Ø­Ñ¢Ï­Ø­áˆº);
            this.áŒŠÉ = p_71049_1_.áŒŠÉ;
            this.ÇŽØ = p_71049_1_.ÇŽØ;
            this.ŒÓ = p_71049_1_.ŒÓ;
            this.HorizonCode_Horizon_È(p_71049_1_.áŒŠÕ());
        }
        this.HorizonCode_Horizon_È = p_71049_1_.HorizonCode_Horizon_È;
        this.É().Â(10, p_71049_1_.É().HorizonCode_Horizon_È(10));
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return !this.áˆºáˆºáŠ.Â;
    }
    
    public void Ø­à() {
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È gameType) {
    }
    
    @Override
    public String v_() {
        return this.áˆºÑ¢Õ.getName();
    }
    
    public InventoryEnderChest ÇŽ() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public ItemStack Ý(final int p_71124_1_) {
        return (p_71124_1_ == 0) ? this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá() : this.Ø­Ñ¢Ï­Ø­áˆº.Â[p_71124_1_ - 1];
    }
    
    @Override
    public ItemStack Çª() {
        return this.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn) {
        this.Ø­Ñ¢Ï­Ø­áˆº.Â[slotIn] = itemStackIn;
    }
    
    @Override
    public boolean Ý(final EntityPlayer playerIn) {
        if (!this.áŒŠÏ()) {
            return false;
        }
        if (playerIn.Ø­áŒŠá()) {
            return false;
        }
        final Team var2 = this.Çªáˆºá();
        return var2 == null || playerIn == null || playerIn.Çªáˆºá() != var2 || !var2.à();
    }
    
    public abstract boolean Ø­áŒŠá();
    
    @Override
    public ItemStack[] Ðƒá() {
        return this.Ø­Ñ¢Ï­Ø­áˆº.Â;
    }
    
    @Override
    public boolean áˆº() {
        return !this.áˆºáˆºáŠ.Â;
    }
    
    public Scoreboard ÇŽÅ() {
        return this.Ï­Ðƒà.à¢();
    }
    
    @Override
    public Team Çªáˆºá() {
        return this.ÇŽÅ().Ó(this.v_());
    }
    
    @Override
    public IChatComponent Ý() {
        final ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.HorizonCode_Horizon_È(this.Çªáˆºá(), this.v_()));
        var1.à().HorizonCode_Horizon_È(new ClickEvent(ClickEvent.HorizonCode_Horizon_È.Âµá€, "/msg " + this.v_() + " "));
        var1.à().HorizonCode_Horizon_È(this.Ñ¢Ç());
        var1.à().HorizonCode_Horizon_È(this.v_());
        return var1;
    }
    
    @Override
    public float Ðƒáƒ() {
        float var1 = 1.62f;
        if (this.Ï­Ó()) {
            var1 = 0.2f;
        }
        if (this.Çªà¢()) {
            var1 -= 0.08f;
        }
        return var1;
    }
    
    @Override
    public void ˆÏ­(float p_110149_1_) {
        if (p_110149_1_ < 0.0f) {
            p_110149_1_ = 0.0f;
        }
        this.É().Â(17, p_110149_1_);
    }
    
    @Override
    public float Ñ¢È() {
        return this.É().Ø­áŒŠá(17);
    }
    
    public static UUID HorizonCode_Horizon_È(final GameProfile p_146094_0_) {
        UUID var1 = p_146094_0_.getId();
        if (var1 == null) {
            var1 = Ø(p_146094_0_.getName());
        }
        return var1;
    }
    
    public static UUID Ø(final String p_175147_0_) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_175147_0_).getBytes(Charsets.UTF_8));
    }
    
    public boolean HorizonCode_Horizon_È(final LockCode p_175146_1_) {
        if (p_175146_1_.HorizonCode_Horizon_È()) {
            return true;
        }
        final ItemStack var2 = this.áŒŠá();
        return var2 != null && var2.¥Æ() && var2.µà().equals(p_175146_1_.Â());
    }
    
    public boolean HorizonCode_Horizon_È(final EnumPlayerModelParts p_175148_1_) {
        return (this.É().HorizonCode_Horizon_È(10) & p_175148_1_.HorizonCode_Horizon_È()) == p_175148_1_.HorizonCode_Horizon_È();
    }
    
    @Override
    public boolean g_() {
        return MinecraftServer.áƒ().Ý[0].Çªà¢().Â("sendCommandFeedback");
    }
    
    @Override
    public boolean Â(final int p_174820_1_, final ItemStack p_174820_2_) {
        if (p_174820_1_ >= 0 && p_174820_1_ < this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È.length) {
            this.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_174820_1_, p_174820_2_);
            return true;
        }
        final int var3 = p_174820_1_ - 100;
        if (var3 >= 0 && var3 < this.Ø­Ñ¢Ï­Ø­áˆº.Â.length) {
            final int var4 = var3 + 1;
            if (p_174820_2_ != null && p_174820_2_.HorizonCode_Horizon_È() != null) {
                if (p_174820_2_.HorizonCode_Horizon_È() instanceof ItemArmor) {
                    if (EntityLiving.Â(p_174820_2_) != var4) {
                        return false;
                    }
                }
                else if (var4 != 4 || (p_174820_2_.HorizonCode_Horizon_È() != Items.ˆ && !(p_174820_2_.HorizonCode_Horizon_È() instanceof ItemBlock))) {
                    return false;
                }
            }
            this.Ø­Ñ¢Ï­Ø­áˆº.Ý(var3 + this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È.length, p_174820_2_);
            return true;
        }
        final int var4 = p_174820_1_ - 200;
        if (var4 >= 0 && var4 < this.HorizonCode_Horizon_È.áŒŠÆ()) {
            this.HorizonCode_Horizon_È.Ý(var4, p_174820_2_);
            return true;
        }
        return false;
    }
    
    public boolean ¥Ðƒá() {
        return this.ÂµÈ;
    }
    
    public void áˆºÑ¢Õ(final boolean p_175150_1_) {
        this.ÂµÈ = p_175150_1_;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("FULL", 0, "FULL", 0, 0, "options.chat.visibility.full"), 
        Â("SYSTEM", 1, "SYSTEM", 1, 1, "options.chat.visibility.system"), 
        Ý("HIDDEN", 2, "HIDDEN", 2, 2, "options.chat.visibility.hidden");
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private final int Âµá€;
        private final String Ó;
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00001714";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[values().length];
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø­áŒŠá[var4.Âµá€] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45323_1_, final int p_i45323_2_, final int p_i45323_3_, final String p_i45323_4_) {
            this.Âµá€ = p_i45323_3_;
            this.Ó = p_i45323_4_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final int p_151426_0_) {
            return HorizonCode_Horizon_È.Ø­áŒŠá[p_151426_0_ % HorizonCode_Horizon_È.Ø­áŒŠá.length];
        }
        
        public String Â() {
            return this.Ó;
        }
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("OK", 0, "OK", 0), 
        Â("NOT_POSSIBLE_HERE", 1, "NOT_POSSIBLE_HERE", 1), 
        Ý("NOT_POSSIBLE_NOW", 2, "NOT_POSSIBLE_NOW", 2), 
        Ø­áŒŠá("TOO_FAR_AWAY", 3, "TOO_FAR_AWAY", 3), 
        Âµá€("OTHER_PROBLEM", 4, "OTHER_PROBLEM", 4), 
        Ó("NOT_SAFE", 5, "NOT_SAFE", 5);
        
        private static final Â[] à;
        private static final String Ø = "CL_00001712";
        
        static {
            áŒŠÆ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
            à = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€, Â.Ó };
        }
        
        private Â(final String s, final int n, final String p_i1751_1_, final int p_i1751_2_) {
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002188";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ý.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
