package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import java.util.Iterator;
import java.util.List;

public abstract class EntityLiving extends EntityLivingBase
{
    public int Ó;
    protected int à;
    private EntityLookHelper HorizonCode_Horizon_È;
    protected EntityMoveHelper Ø;
    protected EntityJumpHelper áŒŠÆ;
    private EntityBodyHelper Â;
    protected PathNavigate áˆºÑ¢Õ;
    protected final EntityAITasks ÂµÈ;
    protected final EntityAITasks á;
    private EntityLivingBase Ý;
    private EntitySenses Ø­áŒŠá;
    private ItemStack[] Âµá€;
    protected float[] ˆÏ­;
    private boolean Ø­Ñ¢Ï­Ø­áˆº;
    private boolean ŒÂ;
    private boolean Ï­Ï;
    private Entity ŠØ;
    private NBTTagCompound ˆÐƒØ;
    private static final String Çªà = "CL_00001550";
    
    public EntityLiving(final World worldIn) {
        super(worldIn);
        this.Âµá€ = new ItemStack[5];
        this.ˆÏ­ = new float[5];
        this.ÂµÈ = new EntityAITasks((worldIn != null && worldIn.Ï­Ðƒà != null) ? worldIn.Ï­Ðƒà : null);
        this.á = new EntityAITasks((worldIn != null && worldIn.Ï­Ðƒà != null) ? worldIn.Ï­Ðƒà : null);
        this.HorizonCode_Horizon_È = new EntityLookHelper(this);
        this.Ø = new EntityMoveHelper(this);
        this.áŒŠÆ = new EntityJumpHelper(this);
        this.Â = new EntityBodyHelper(this);
        this.áˆºÑ¢Õ = this.Â(worldIn);
        this.Ø­áŒŠá = new EntitySenses(this);
        for (int var2 = 0; var2 < this.ˆÏ­.length; ++var2) {
            this.ˆÏ­[var2] = 0.085f;
        }
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.µÐƒÓ().Â(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(16.0);
    }
    
    protected PathNavigate Â(final World worldIn) {
        return new PathNavigateGround(this, worldIn);
    }
    
    public EntityLookHelper Ñ¢á() {
        return this.HorizonCode_Horizon_È;
    }
    
    public EntityMoveHelper ŒÏ() {
        return this.Ø;
    }
    
    public EntityJumpHelper ÇŽÉ() {
        return this.áŒŠÆ;
    }
    
    public PathNavigate Š() {
        return this.áˆºÑ¢Õ;
    }
    
    public EntitySenses Ø­Ñ¢á€() {
        return this.Ø­áŒŠá;
    }
    
    public EntityLivingBase Ñ¢Ó() {
        return this.Ý;
    }
    
    public void Â(final EntityLivingBase p_70624_1_) {
        this.Ý = p_70624_1_;
        Reflector.HorizonCode_Horizon_È(Reflector.¥Æ, this, p_70624_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final Class p_70686_1_) {
        return p_70686_1_ != EntityGhast.class;
    }
    
    public void Ø­Æ() {
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(15, (Object)(byte)0);
    }
    
    public int áŒŠÔ() {
        return 80;
    }
    
    public void ŠÕ() {
        final String var1 = this.µÐƒáƒ();
        if (var1 != null) {
            this.HorizonCode_Horizon_È(var1, this.ˆÂ(), this.áŒŠÈ());
        }
    }
    
    @Override
    public void Õ() {
        super.Õ();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("mobBaseTick");
        if (this.Œ() && this.ˆáƒ.nextInt(1000) < this.Ó++) {
            this.Ó = -this.áŒŠÔ();
            this.ŠÕ();
        }
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
    }
    
    @Override
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        if (this.à > 0) {
            int var2 = this.à;
            final ItemStack[] var3 = this.Ðƒá();
            for (int var4 = 0; var4 < var3.length; ++var4) {
                if (var3[var4] != null && this.ˆÏ­[var4] <= 1.0f) {
                    var2 += 1 + this.ˆáƒ.nextInt(3);
                }
            }
            return var2;
        }
        return this.à;
    }
    
    public void £Ø­à() {
        if (this.Ï­Ðƒà.ŠÄ) {
            for (int var1 = 0; var1 < 20; ++var1) {
                final double var2 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var3 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var4 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var5 = 10.0;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.HorizonCode_Horizon_È, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ - var2 * var5, this.Çªà¢ + this.ˆáƒ.nextFloat() * this.£ÂµÄ - var3 * var5, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ - var4 * var5, var2, var3, var4, new int[0]);
            }
        }
        else {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)20);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 20) {
            this.£Ø­à();
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    public void á() {
        if (Config.ˆà¢() && this.¥Æ()) {
            this.Ø­à();
        }
        else {
            super.á();
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.Ï­Ðƒà();
            }
        }
    }
    
    @Override
    protected float à(final float p_110146_1_, final float p_110146_2_) {
        this.Â.HorizonCode_Horizon_È();
        return p_110146_2_;
    }
    
    protected String µÐƒáƒ() {
        return null;
    }
    
    protected Item_1028566121 áŒŠÕ() {
        return null;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        final Item_1028566121 var3 = this.áŒŠÕ();
        if (var3 != null) {
            int var4 = this.ˆáƒ.nextInt(3);
            if (p_70628_2_ > 0) {
                var4 += this.ˆáƒ.nextInt(p_70628_2_ + 1);
            }
            for (int var5 = 0; var5 < var4; ++var5) {
                this.HorizonCode_Horizon_È(var3, 1);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("CanPickUpLoot", this.ˆÅ());
        tagCompound.HorizonCode_Horizon_È("PersistenceRequired", this.ŒÂ);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.Âµá€.length; ++var3) {
            final NBTTagCompound var4 = new NBTTagCompound();
            if (this.Âµá€[var3] != null) {
                this.Âµá€[var3].Â(var4);
            }
            var2.HorizonCode_Horizon_È(var4);
        }
        tagCompound.HorizonCode_Horizon_È("Equipment", var2);
        final NBTTagList var5 = new NBTTagList();
        for (int var6 = 0; var6 < this.ˆÏ­.length; ++var6) {
            var5.HorizonCode_Horizon_È(new NBTTagFloat(this.ˆÏ­[var6]));
        }
        tagCompound.HorizonCode_Horizon_È("DropChances", var5);
        tagCompound.HorizonCode_Horizon_È("Leashed", this.Ï­Ï);
        if (this.ŠØ != null) {
            final NBTTagCompound var4 = new NBTTagCompound();
            if (this.ŠØ instanceof EntityLivingBase) {
                var4.HorizonCode_Horizon_È("UUIDMost", this.ŠØ.£áŒŠá().getMostSignificantBits());
                var4.HorizonCode_Horizon_È("UUIDLeast", this.ŠØ.£áŒŠá().getLeastSignificantBits());
            }
            else if (this.ŠØ instanceof EntityHanging) {
                final BlockPos var7 = ((EntityHanging)this.ŠØ).ˆÏ­();
                var4.HorizonCode_Horizon_È("X", var7.HorizonCode_Horizon_È());
                var4.HorizonCode_Horizon_È("Y", var7.Â());
                var4.HorizonCode_Horizon_È("Z", var7.Ý());
            }
            tagCompound.HorizonCode_Horizon_È("Leash", var4);
        }
        if (this.ˆà()) {
            tagCompound.HorizonCode_Horizon_È("NoAI", this.ˆà());
        }
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("CanPickUpLoot", 1)) {
            this.Ý(tagCompund.£á("CanPickUpLoot"));
        }
        this.ŒÂ = tagCompund.£á("PersistenceRequired");
        if (tagCompund.Â("Equipment", 9)) {
            final NBTTagList var2 = tagCompund.Ý("Equipment", 10);
            for (int var3 = 0; var3 < this.Âµá€.length; ++var3) {
                this.Âµá€[var3] = ItemStack.HorizonCode_Horizon_È(var2.Â(var3));
            }
        }
        if (tagCompund.Â("DropChances", 9)) {
            final NBTTagList var2 = tagCompund.Ý("DropChances", 5);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                this.ˆÏ­[var3] = var2.Âµá€(var3);
            }
        }
        this.Ï­Ï = tagCompund.£á("Leashed");
        if (this.Ï­Ï && tagCompund.Â("Leash", 10)) {
            this.ˆÐƒØ = tagCompund.ˆÏ­("Leash");
        }
        this.áˆºÑ¢Õ(tagCompund.£á("NoAI"));
    }
    
    public void Âµá€(final float p_70657_1_) {
        this.Ï­áˆºÓ = p_70657_1_;
    }
    
    @Override
    public void áŒŠÆ(final float p_70659_1_) {
        super.áŒŠÆ(p_70659_1_);
        this.Âµá€(p_70659_1_);
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("looting");
        if (!this.Ï­Ðƒà.ŠÄ && this.ˆÅ() && !this.áŒŠÔ && this.Ï­Ðƒà.Çªà¢().Â("mobGriefing")) {
            final List var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityItem.class, this.£É().Â(1.0, 0.0, 1.0));
            for (final EntityItem var3 : var1) {
                if (!var3.ˆáŠ && var3.Ø() != null && !var3.µÕ()) {
                    this.HorizonCode_Horizon_È(var3);
                }
            }
        }
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
    }
    
    protected void HorizonCode_Horizon_È(final EntityItem p_175445_1_) {
        final ItemStack var2 = p_175445_1_.Ø();
        final int var3 = Â(var2);
        if (var3 > -1) {
            boolean var4 = true;
            final ItemStack var5 = this.Ý(var3);
            if (var5 != null) {
                if (var3 == 0) {
                    if (var2.HorizonCode_Horizon_È() instanceof ItemSword && !(var5.HorizonCode_Horizon_È() instanceof ItemSword)) {
                        var4 = true;
                    }
                    else if (var2.HorizonCode_Horizon_È() instanceof ItemSword && var5.HorizonCode_Horizon_È() instanceof ItemSword) {
                        final ItemSword var6 = (ItemSword)var2.HorizonCode_Horizon_È();
                        final ItemSword var7 = (ItemSword)var5.HorizonCode_Horizon_È();
                        if (var6.ˆà() == var7.ˆà()) {
                            var4 = (var2.Ø() > var5.Ø() || (var2.£á() && !var5.£á()));
                        }
                        else {
                            var4 = (var6.ˆà() > var7.ˆà());
                        }
                    }
                    else {
                        var4 = (var2.HorizonCode_Horizon_È() instanceof ItemBow && var5.HorizonCode_Horizon_È() instanceof ItemBow && var2.£á() && !var5.£á());
                    }
                }
                else if (var2.HorizonCode_Horizon_È() instanceof ItemArmor && !(var5.HorizonCode_Horizon_È() instanceof ItemArmor)) {
                    var4 = true;
                }
                else if (var2.HorizonCode_Horizon_È() instanceof ItemArmor && var5.HorizonCode_Horizon_È() instanceof ItemArmor) {
                    final ItemArmor var8 = (ItemArmor)var2.HorizonCode_Horizon_È();
                    final ItemArmor var9 = (ItemArmor)var5.HorizonCode_Horizon_È();
                    if (var8.áŒŠÆ == var9.áŒŠÆ) {
                        var4 = (var2.Ø() > var5.Ø() || (var2.£á() && !var5.£á()));
                    }
                    else {
                        var4 = (var8.áŒŠÆ > var9.áŒŠÆ);
                    }
                }
                else {
                    var4 = false;
                }
            }
            if (var4 && this.HorizonCode_Horizon_È(var2)) {
                if (var5 != null && this.ˆáƒ.nextFloat() - 0.1f < this.ˆÏ­[var3]) {
                    this.HorizonCode_Horizon_È(var5, 0.0f);
                }
                if (var2.HorizonCode_Horizon_È() == Items.áŒŠÆ && p_175445_1_.ˆÏ­() != null) {
                    final EntityPlayer var10 = this.Ï­Ðƒà.HorizonCode_Horizon_È(p_175445_1_.ˆÏ­());
                    if (var10 != null) {
                        var10.HorizonCode_Horizon_È(AchievementList.Ï­Ðƒà);
                    }
                }
                this.HorizonCode_Horizon_È(var3, var2);
                this.ˆÏ­[var3] = 2.0f;
                this.ŒÂ = true;
                this.Â(p_175445_1_, 1);
                p_175445_1_.á€();
            }
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final ItemStack p_175448_1_) {
        return true;
    }
    
    protected boolean ÂµÂ() {
        return true;
    }
    
    protected void áŒŠá() {
        Object result = null;
        final Object Result_DEFAULT = Reflector.HorizonCode_Horizon_È(Reflector.£áŒŠá);
        final Object Result_DENY = Reflector.HorizonCode_Horizon_È(Reflector.Ñ¢à);
        if (this.ŒÂ) {
            this.ŠÕ = 0;
        }
        else if ((this.ŠÕ & 0x1F) == 0x1F && (result = Reflector.Ó(Reflector.áŒŠá€, this)) != Result_DEFAULT) {
            if (result == Result_DENY) {
                this.ŠÕ = 0;
            }
            else {
                this.á€();
            }
        }
        else {
            final EntityPlayer var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, -1.0);
            if (var1 != null) {
                final double var2 = var1.ŒÏ - this.ŒÏ;
                final double var3 = var1.Çªà¢ - this.Çªà¢;
                final double var4 = var1.Ê - this.Ê;
                final double var5 = var2 * var2 + var3 * var3 + var4 * var4;
                if (this.ÂµÂ() && var5 > 16384.0) {
                    this.á€();
                }
                if (this.ŠÕ > 600 && this.ˆáƒ.nextInt(800) == 0 && var5 > 1024.0 && this.ÂµÂ()) {
                    this.á€();
                }
                else if (var5 < 1024.0) {
                    this.ŠÕ = 0;
                }
            }
        }
    }
    
    @Override
    protected final void Ê() {
        ++this.ŠÕ;
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("checkDespawn");
        this.áŒŠá();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("sensing");
        this.Ø­áŒŠá.HorizonCode_Horizon_È();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("targetSelector");
        this.á.HorizonCode_Horizon_È();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("goalSelector");
        this.ÂµÈ.HorizonCode_Horizon_È();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("navigation");
        this.áˆºÑ¢Õ.Ø­áŒŠá();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("mob tick");
        this.ˆØ();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("controls");
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("move");
        this.Ø.Ý();
        this.Ï­Ðƒà.Ï­Ðƒà.Ý("look");
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        this.Ï­Ðƒà.Ï­Ðƒà.Ý("jump");
        this.áŒŠÆ.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
    }
    
    protected void ˆØ() {
    }
    
    public int áˆºà() {
        return 40;
    }
    
    public void HorizonCode_Horizon_È(final Entity p_70625_1_, final float p_70625_2_, final float p_70625_3_) {
        final double var4 = p_70625_1_.ŒÏ - this.ŒÏ;
        final double var5 = p_70625_1_.Ê - this.Ê;
        double var7;
        if (p_70625_1_ instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase)p_70625_1_;
            var7 = var6.Çªà¢ + var6.Ðƒáƒ() - (this.Çªà¢ + this.Ðƒáƒ());
        }
        else {
            var7 = (p_70625_1_.£É().Â + p_70625_1_.£É().Âµá€) / 2.0 - (this.Çªà¢ + this.Ðƒáƒ());
        }
        final double var8 = MathHelper.HorizonCode_Horizon_È(var4 * var4 + var5 * var5);
        final float var9 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float)(-(Math.atan2(var7, var8) * 180.0 / 3.141592653589793));
        this.áƒ = this.HorizonCode_Horizon_È(this.áƒ, var10, p_70625_3_);
        this.É = this.HorizonCode_Horizon_È(this.É, var9, p_70625_2_);
    }
    
    private float HorizonCode_Horizon_È(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
        float var4 = MathHelper.à(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
    
    public boolean µà() {
        return true;
    }
    
    public boolean ÐƒÂ() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), this) && this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty() && !this.Ï­Ðƒà.Ø­áŒŠá(this.£É());
    }
    
    public float £áƒ() {
        return 1.0f;
    }
    
    public int Ï­áˆºÓ() {
        return 4;
    }
    
    @Override
    public int ŠÓ() {
        if (this.Ñ¢Ó() == null) {
            return 3;
        }
        int var1 = (int)(this.Ï­Ä() - this.ÇŽÊ() * 0.33f);
        var1 -= (3 - this.Ï­Ðƒà.ŠÂµà().HorizonCode_Horizon_È()) * 4;
        if (var1 < 0) {
            var1 = 0;
        }
        return var1 + 3;
    }
    
    @Override
    public ItemStack Çª() {
        return this.Âµá€[0];
    }
    
    @Override
    public ItemStack Ý(final int slotIn) {
        return this.Âµá€[slotIn];
    }
    
    @Override
    public ItemStack ÂµÈ(final int slotIn) {
        return this.Âµá€[slotIn + 1];
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIn, final ItemStack stack) {
        this.Âµá€[slotIn] = stack;
    }
    
    @Override
    public ItemStack[] Ðƒá() {
        return this.Âµá€;
    }
    
    @Override
    protected void Â(final boolean p_82160_1_, final int p_82160_2_) {
        for (int var3 = 0; var3 < this.Ðƒá().length; ++var3) {
            final ItemStack var4 = this.Ý(var3);
            final boolean var5 = this.ˆÏ­[var3] > 1.0f;
            if (var4 != null && (p_82160_1_ || var5) && this.ˆáƒ.nextFloat() - p_82160_2_ * 0.01f < this.ˆÏ­[var3]) {
                if (!var5 && var4.Ø­áŒŠá()) {
                    final int var6 = Math.max(var4.áŒŠÆ() - 25, 1);
                    int var7 = var4.áŒŠÆ() - this.ˆáƒ.nextInt(this.ˆáƒ.nextInt(var6) + 1);
                    if (var7 > var6) {
                        var7 = var6;
                    }
                    if (var7 < 1) {
                        var7 = 1;
                    }
                    var4.Â(var7);
                }
                this.HorizonCode_Horizon_È(var4, 0.0f);
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final DifficultyInstance p_180481_1_) {
        if (this.ˆáƒ.nextFloat() < 0.15f * p_180481_1_.Â()) {
            int var2 = this.ˆáƒ.nextInt(2);
            final float var3 = (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) ? 0.1f : 0.25f;
            if (this.ˆáƒ.nextFloat() < 0.095f) {
                ++var2;
            }
            if (this.ˆáƒ.nextFloat() < 0.095f) {
                ++var2;
            }
            if (this.ˆáƒ.nextFloat() < 0.095f) {
                ++var2;
            }
            for (int var4 = 3; var4 >= 0; --var4) {
                final ItemStack var5 = this.ÂµÈ(var4);
                if (var4 < 3 && this.ˆáƒ.nextFloat() < var3) {
                    break;
                }
                if (var5 == null) {
                    final Item_1028566121 var6 = HorizonCode_Horizon_È(var4 + 1, var2);
                    if (var6 != null) {
                        this.HorizonCode_Horizon_È(var4 + 1, new ItemStack(var6));
                    }
                }
            }
        }
    }
    
    public static int Â(final ItemStack p_82159_0_) {
        if (p_82159_0_.HorizonCode_Horizon_È() != Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ) && p_82159_0_.HorizonCode_Horizon_È() != Items.ˆ) {
            if (p_82159_0_.HorizonCode_Horizon_È() instanceof ItemArmor) {
                switch (((ItemArmor)p_82159_0_.HorizonCode_Horizon_È()).Ø) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }
    
    public static Item_1028566121 HorizonCode_Horizon_È(final int armorSlot, final int itemTier) {
        switch (armorSlot) {
            case 4: {
                if (itemTier == 0) {
                    return Items.È;
                }
                if (itemTier == 1) {
                    return Items.Œ;
                }
                if (itemTier == 2) {
                    return Items.£ÂµÄ;
                }
                if (itemTier == 3) {
                    return Items.Ï­à;
                }
                if (itemTier == 4) {
                    return Items.Ô;
                }
            }
            case 3: {
                if (itemTier == 0) {
                    return Items.áŠ;
                }
                if (itemTier == 1) {
                    return Items.£Ï;
                }
                if (itemTier == 2) {
                    return Items.Ø­Âµ;
                }
                if (itemTier == 3) {
                    return Items.áˆºáˆºÈ;
                }
                if (itemTier == 4) {
                    return Items.ÇªÓ;
                }
            }
            case 2: {
                if (itemTier == 0) {
                    return Items.ˆáŠ;
                }
                if (itemTier == 1) {
                    return Items.Ø­á;
                }
                if (itemTier == 2) {
                    return Items.Ä;
                }
                if (itemTier == 3) {
                    return Items.ÇŽá€;
                }
                if (itemTier == 4) {
                    return Items.áˆºÏ;
                }
            }
            case 1: {
                if (itemTier == 0) {
                    return Items.áŒŠ;
                }
                if (itemTier == 1) {
                    return Items.ˆÉ;
                }
                if (itemTier == 2) {
                    return Items.Ñ¢Â;
                }
                if (itemTier == 3) {
                    return Items.Ï;
                }
                if (itemTier == 4) {
                    return Items.ˆáƒ;
                }
                break;
            }
        }
        return null;
    }
    
    protected void Â(final DifficultyInstance p_180483_1_) {
        final float var2 = p_180483_1_.Â();
        if (this.Çª() != null && this.ˆáƒ.nextFloat() < 0.25f * var2) {
            EnchantmentHelper.HorizonCode_Horizon_È(this.ˆáƒ, this.Çª(), (int)(5.0f + var2 * this.ˆáƒ.nextInt(18)));
        }
        for (int var3 = 0; var3 < 4; ++var3) {
            final ItemStack var4 = this.ÂµÈ(var3);
            if (var4 != null && this.ˆáƒ.nextFloat() < 0.5f * var2) {
                EnchantmentHelper.HorizonCode_Horizon_È(this.ˆáƒ, var4, (int)(5.0f + var2 * this.ˆáƒ.nextInt(18)));
            }
        }
    }
    
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).Â(new AttributeModifier("Random spawn bonus", this.ˆáƒ.nextGaussian() * 0.05, 1));
        return p_180482_2_;
    }
    
    public boolean ÇŽÄ() {
        return false;
    }
    
    public void ˆÈ() {
        this.ŒÂ = true;
    }
    
    public void HorizonCode_Horizon_È(final int p_96120_1_, final float p_96120_2_) {
        this.ˆÏ­[p_96120_1_] = p_96120_2_;
    }
    
    public boolean ˆÅ() {
        return this.Ø­Ñ¢Ï­Ø­áˆº;
    }
    
    public void Ý(final boolean p_98053_1_) {
        this.Ø­Ñ¢Ï­Ø­áˆº = p_98053_1_;
    }
    
    public boolean ÇªÉ() {
        return this.ŒÂ;
    }
    
    @Override
    public final boolean b_(final EntityPlayer playerIn) {
        if (this.ÇŽà() && this.ŠáˆºÂ() == playerIn) {
            this.HorizonCode_Horizon_È(true, !playerIn.áˆºáˆºáŠ.Ø­áŒŠá);
            return true;
        }
        final ItemStack var2 = playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºÕ && this.ŠÏ­áˆºá()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).ÐƒÓ()) {
                this.HorizonCode_Horizon_È(playerIn, true);
                final ItemStack itemStack = var2;
                --itemStack.Â;
                return true;
            }
            if (((EntityTameable)this).Âµá€((EntityLivingBase)playerIn)) {
                this.HorizonCode_Horizon_È(playerIn, true);
                final ItemStack itemStack2 = var2;
                --itemStack2.Â;
                return true;
            }
        }
        return this.Ø­áŒŠá(playerIn) || super.b_(playerIn);
    }
    
    protected boolean Ø­áŒŠá(final EntityPlayer player) {
        return false;
    }
    
    protected void Ï­Ðƒà() {
        if (this.ˆÐƒØ != null) {
            this.Ø();
        }
        if (this.Ï­Ï) {
            if (!this.Œ()) {
                this.HorizonCode_Horizon_È(true, true);
            }
            if (this.ŠØ == null || this.ŠØ.ˆáŠ) {
                this.HorizonCode_Horizon_È(true, true);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean p_110160_1_, final boolean p_110160_2_) {
        if (this.Ï­Ï) {
            this.Ï­Ï = false;
            this.ŠØ = null;
            if (!this.Ï­Ðƒà.ŠÄ && p_110160_2_) {
                this.HorizonCode_Horizon_È(Items.áˆºÕ, 1);
            }
            if (!this.Ï­Ðƒà.ŠÄ && p_110160_1_ && this.Ï­Ðƒà instanceof WorldServer) {
                ((WorldServer)this.Ï­Ðƒà).ÇŽá€().HorizonCode_Horizon_È(this, new S1BPacketEntityAttach(1, this, null));
            }
        }
    }
    
    public boolean ŠÏ­áˆºá() {
        return !this.ÇŽà() && !(this instanceof IMob);
    }
    
    public boolean ÇŽà() {
        return this.Ï­Ï;
    }
    
    public Entity ŠáˆºÂ() {
        return this.ŠØ;
    }
    
    public void HorizonCode_Horizon_È(final Entity entityIn, final boolean sendAttachNotification) {
        this.Ï­Ï = true;
        this.ŠØ = entityIn;
        if (!this.Ï­Ðƒà.ŠÄ && sendAttachNotification && this.Ï­Ðƒà instanceof WorldServer) {
            ((WorldServer)this.Ï­Ðƒà).ÇŽá€().HorizonCode_Horizon_È(this, new S1BPacketEntityAttach(1, this, this.ŠØ));
        }
    }
    
    private void Ø() {
        if (this.Ï­Ï && this.ˆÐƒØ != null) {
            if (this.ˆÐƒØ.Â("UUIDMost", 4) && this.ˆÐƒØ.Â("UUIDLeast", 4)) {
                final UUID var11 = new UUID(this.ˆÐƒØ.à("UUIDMost"), this.ˆÐƒØ.à("UUIDLeast"));
                final List var12 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityLivingBase.class, this.£É().Â(10.0, 10.0, 10.0));
                for (final EntityLivingBase var14 : var12) {
                    if (var14.£áŒŠá().equals(var11)) {
                        this.ŠØ = var14;
                        break;
                    }
                }
            }
            else if (this.ˆÐƒØ.Â("X", 99) && this.ˆÐƒØ.Â("Y", 99) && this.ˆÐƒØ.Â("Z", 99)) {
                final BlockPos var15 = new BlockPos(this.ˆÐƒØ.Ó("X"), this.ˆÐƒØ.Ó("Y"), this.ˆÐƒØ.Ó("Z"));
                EntityLeashKnot var16 = EntityLeashKnot.Â(this.Ï­Ðƒà, var15);
                if (var16 == null) {
                    var16 = EntityLeashKnot.HorizonCode_Horizon_È(this.Ï­Ðƒà, var15);
                }
                this.ŠØ = var16;
            }
            else {
                this.HorizonCode_Horizon_È(false, true);
            }
        }
        this.ˆÐƒØ = null;
    }
    
    @Override
    public boolean Â(final int p_174820_1_, final ItemStack p_174820_2_) {
        int var3;
        if (p_174820_1_ == 99) {
            var3 = 0;
        }
        else {
            var3 = p_174820_1_ - 100 + 1;
            if (var3 < 0 || var3 >= this.Âµá€.length) {
                return false;
            }
        }
        if (p_174820_2_ != null && Â(p_174820_2_) != var3 && (var3 != 4 || !(p_174820_2_.HorizonCode_Horizon_È() instanceof ItemBlock))) {
            return false;
        }
        this.HorizonCode_Horizon_È(var3, p_174820_2_);
        return true;
    }
    
    @Override
    public boolean ŠÄ() {
        return super.ŠÄ() && !this.ˆà();
    }
    
    protected void áˆºÑ¢Õ(final boolean p_94061_1_) {
        this.£Ó.Â(15, (byte)(byte)(p_94061_1_ ? 1 : 0));
    }
    
    private boolean ˆà() {
        return this.£Ó.HorizonCode_Horizon_È(15) != 0;
    }
    
    @Override
    public boolean £Ï() {
        if (this.ÇªÓ) {
            return false;
        }
        final BlockPosM posM = new BlockPosM(0, 0, 0);
        for (int var1 = 0; var1 < 8; ++var1) {
            final double var2 = this.ŒÏ + ((var1 >> 0) % 2 - 0.5f) * this.áŒŠ * 0.8f;
            final double var3 = this.Çªà¢ + ((var1 >> 1) % 2 - 0.5f) * 0.1f;
            final double var4 = this.Ê + ((var1 >> 2) % 2 - 0.5f) * this.áŒŠ * 0.8f;
            posM.HorizonCode_Horizon_È(var2, var3 + this.Ðƒáƒ(), var4);
            if (this.Ï­Ðƒà.Â(posM).Ý().áŒŠÆ()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean ¥Æ() {
        if (this.h_()) {
            return false;
        }
        if (this.µà > 0) {
            return false;
        }
        if (this.Œ < 20) {
            return false;
        }
        final Minecraft mc = Config.È();
        final WorldClient world = mc.áŒŠÆ;
        if (world == null) {
            return false;
        }
        if (world.Ó.size() != 1) {
            return false;
        }
        final Entity player = world.Ó.get(0);
        final double dx = Math.abs(this.ŒÏ - player.ŒÏ) - 16.0;
        final double dz = Math.abs(this.Ê - player.Ê) - 16.0;
        final double distSq = dx * dx + dz * dz;
        return !this.HorizonCode_Horizon_È(distSq);
    }
    
    private void Ø­à() {
        ++this.ŠÕ;
        if (this instanceof EntityMob) {
            final float brightness = this.Â(1.0f);
            if (brightness > 0.5f) {
                this.ŠÕ += 2;
            }
        }
        this.áŒŠá();
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("ON_GROUND", 0, "ON_GROUND", 0, "ON_GROUND", 0), 
        Â("IN_AIR", 1, "IN_AIR", 1, "IN_AIR", 1), 
        Ý("IN_WATER", 2, "IN_WATER", 2, "IN_WATER", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002255";
        private static final HorizonCode_Horizon_È[] Ó;
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46429_1_, final int p_i46429_2_, final String p_i45893_1_, final int p_i45893_2_) {
        }
    }
}
