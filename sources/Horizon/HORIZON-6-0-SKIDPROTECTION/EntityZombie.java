package HORIZON-6-0-SKIDPROTECTION;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EntityZombie extends EntityMob
{
    protected static final IAttribute Â;
    private static final UUID Ý;
    private static final AttributeModifier Ø­Ñ¢Ï­Ø­áˆº;
    private final EntityAIBreakDoor ŒÂ;
    private int Ï­Ï;
    private boolean ŠØ;
    private float ˆÐƒØ;
    private float Çªà;
    private static final String ¥Å = "CL_00001702";
    
    static {
        Â = new RangedAttribute(null, "zombie.spawnReinforcements", 0.0, 0.0, 1.0).HorizonCode_Horizon_È("Spawn Reinforcements Chance");
        Ý = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        Ø­Ñ¢Ï­Ø­áˆº = new AttributeModifier(EntityZombie.Ý, "Baby speed boost", 0.5, 1);
    }
    
    public EntityZombie(final World worldIn) {
        super(worldIn);
        this.ŒÂ = new EntityAIBreakDoor(this);
        this.ŠØ = false;
        this.ˆÐƒØ = -1.0f;
        ((PathNavigateGround)this.Š()).Â(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.HorizonCode_Horizon_È);
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.Ø();
        this.HorizonCode_Horizon_È(0.6f, 1.95f);
    }
    
    protected void Ø() {
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, true));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(35.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.23000000417232513);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(3.0);
        this.µÐƒÓ().Â(EntityZombie.Â).HorizonCode_Horizon_È(this.ˆáƒ.nextDouble() * 0.10000000149011612);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.É().HorizonCode_Horizon_È(12, (Object)(byte)0);
        this.É().HorizonCode_Horizon_È(13, (Object)(byte)0);
        this.É().HorizonCode_Horizon_È(14, (Object)(byte)0);
    }
    
    @Override
    public int áŒŠÉ() {
        int var1 = super.áŒŠÉ() + 2;
        if (var1 > 20) {
            var1 = 20;
        }
        return var1;
    }
    
    public boolean ¥Ðƒá() {
        return this.ŠØ;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_146070_1_) {
        if (this.ŠØ != p_146070_1_) {
            this.ŠØ = p_146070_1_;
            if (p_146070_1_) {
                this.ÂµÈ.HorizonCode_Horizon_È(1, this.ŒÂ);
            }
            else {
                this.ÂµÈ.HorizonCode_Horizon_È(this.ŒÂ);
            }
        }
    }
    
    @Override
    public boolean h_() {
        return this.É().HorizonCode_Horizon_È(12) == 1;
    }
    
    @Override
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        if (this.h_()) {
            this.à *= (int)2.5f;
        }
        return super.Âµá€(p_70693_1_);
    }
    
    public void á(final boolean p_82227_1_) {
        this.É().Â(12, (byte)(byte)(p_82227_1_ ? 1 : 0));
        if (this.Ï­Ðƒà != null && !this.Ï­Ðƒà.ŠÄ) {
            final IAttributeInstance var2 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
            var2.Ý(EntityZombie.Ø­Ñ¢Ï­Ø­áˆº);
            if (p_82227_1_) {
                var2.Â(EntityZombie.Ø­Ñ¢Ï­Ø­áˆº);
            }
        }
        this.£á(p_82227_1_);
    }
    
    public boolean ÐƒÇŽà() {
        return this.É().HorizonCode_Horizon_È(13) == 1;
    }
    
    public void ˆÏ­(final boolean p_82229_1_) {
        this.É().Â(13, (byte)(byte)(p_82229_1_ ? 1 : 0));
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ï­Ðƒà.ÂµÈ() && !this.Ï­Ðƒà.ŠÄ && !this.h_()) {
            final float var1 = this.Â(1.0f);
            final BlockPos var2 = new BlockPos(this.ŒÏ, Math.round(this.Çªà¢), this.Ê);
            if (var1 > 0.5f && this.ˆáƒ.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f && this.Ï­Ðƒà.áˆºÑ¢Õ(var2)) {
                boolean var3 = true;
                final ItemStack var4 = this.Ý(4);
                if (var4 != null) {
                    if (var4.Ø­áŒŠá()) {
                        var4.Â(var4.à() + this.ˆáƒ.nextInt(2));
                        if (var4.à() >= var4.áŒŠÆ()) {
                            this.Ý(var4);
                            this.HorizonCode_Horizon_È(4, (ItemStack)null);
                        }
                    }
                    var3 = false;
                }
                if (var3) {
                    this.Âµá€(8);
                }
            }
        }
        if (this.áˆºÇŽØ() && this.Ñ¢Ó() != null && this.Æ instanceof EntityChicken) {
            ((EntityLiving)this.Æ).Š().HorizonCode_Horizon_È(this.Š().Ý(), 1.5);
        }
        super.ˆÏ­();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (super.HorizonCode_Horizon_È(source, amount)) {
            EntityLivingBase var3 = this.Ñ¢Ó();
            if (var3 == null && source.áˆºÑ¢Õ() instanceof EntityLivingBase) {
                var3 = (EntityLivingBase)source.áˆºÑ¢Õ();
            }
            if (var3 != null && this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá && this.ˆáƒ.nextFloat() < this.HorizonCode_Horizon_È(EntityZombie.Â).Âµá€()) {
                final int var4 = MathHelper.Ý(this.ŒÏ);
                final int var5 = MathHelper.Ý(this.Çªà¢);
                final int var6 = MathHelper.Ý(this.Ê);
                final EntityZombie var7 = new EntityZombie(this.Ï­Ðƒà);
                for (int var8 = 0; var8 < 50; ++var8) {
                    final int var9 = var4 + MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 7, 40) * MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, -1, 1);
                    final int var10 = var5 + MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 7, 40) * MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, -1, 1);
                    final int var11 = var6 + MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, 7, 40) * MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, -1, 1);
                    if (World.HorizonCode_Horizon_È(this.Ï­Ðƒà, new BlockPos(var9, var10 - 1, var11)) && this.Ï­Ðƒà.ˆÏ­(new BlockPos(var9, var10, var11)) < 10) {
                        var7.Ý(var9, var10, var11);
                        if (!this.Ï­Ðƒà.Â(var9, var10, var11, 7.0) && this.Ï­Ðƒà.HorizonCode_Horizon_È(var7.£É(), var7) && this.Ï­Ðƒà.HorizonCode_Horizon_È(var7, var7.£É()).isEmpty() && !this.Ï­Ðƒà.Ø­áŒŠá(var7.£É())) {
                            this.Ï­Ðƒà.HorizonCode_Horizon_È(var7);
                            var7.Â(var3);
                            var7.HorizonCode_Horizon_È(this.Ï­Ðƒà.Ê(new BlockPos(var7)), null);
                            this.HorizonCode_Horizon_È(EntityZombie.Â).Â(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806, 0));
                            var7.HorizonCode_Horizon_È(EntityZombie.Â).Â(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806, 0));
                            break;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void á() {
        if (!this.Ï­Ðƒà.ŠÄ && this.¥Ê()) {
            final int var1 = this.áˆºÕ();
            this.Ï­Ï -= var1;
            if (this.Ï­Ï <= 0) {
                this.ÐƒÓ();
            }
        }
        super.á();
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        final boolean var2 = super.Å(p_70652_1_);
        if (var2) {
            final int var3 = this.Ï­Ðƒà.ŠÂµà().HorizonCode_Horizon_È();
            if (this.Çª() == null && this.ˆÏ() && this.ˆáƒ.nextFloat() < var3 * 0.3f) {
                p_70652_1_.Âµá€(2 * var3);
            }
        }
        return var2;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.zombie.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.zombie.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.zombie.death";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.zombie.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.ŠØ;
    }
    
    @Override
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.Â;
    }
    
    @Override
    protected void áˆºáˆºáŠ() {
        switch (this.ˆáƒ.nextInt(3)) {
            case 0: {
                this.HorizonCode_Horizon_È(Items.áˆºÑ¢Õ, 1);
                break;
            }
            case 1: {
                this.HorizonCode_Horizon_È(Items.¥áŒŠà, 1);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(Items.ˆÂ, 1);
                break;
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final DifficultyInstance p_180481_1_) {
        super.HorizonCode_Horizon_È(p_180481_1_);
        if (this.ˆáƒ.nextFloat() < ((this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) ? 0.05f : 0.01f)) {
            final int var2 = this.ˆáƒ.nextInt(3);
            if (var2 == 0) {
                this.HorizonCode_Horizon_È(0, new ItemStack(Items.á));
            }
            else {
                this.HorizonCode_Horizon_È(0, new ItemStack(Items.HorizonCode_Horizon_È));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        if (this.h_()) {
            tagCompound.HorizonCode_Horizon_È("IsBaby", true);
        }
        if (this.ÐƒÇŽà()) {
            tagCompound.HorizonCode_Horizon_È("IsVillager", true);
        }
        tagCompound.HorizonCode_Horizon_È("ConversionTime", this.¥Ê() ? this.Ï­Ï : -1);
        tagCompound.HorizonCode_Horizon_È("CanBreakDoors", this.¥Ðƒá());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.£á("IsBaby")) {
            this.á(true);
        }
        if (tagCompund.£á("IsVillager")) {
            this.ˆÏ­(true);
        }
        if (tagCompund.Â("ConversionTime", 99) && tagCompund.Ó("ConversionTime") > -1) {
            this.HorizonCode_Horizon_È(tagCompund.Ó("ConversionTime"));
        }
        this.HorizonCode_Horizon_È(tagCompund.£á("CanBreakDoors"));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase entityLivingIn) {
        super.HorizonCode_Horizon_È(entityLivingIn);
        if ((this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ý || this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) && entityLivingIn instanceof EntityVillager) {
            if (this.Ï­Ðƒà.ŠÂµà() != EnumDifficulty.Ø­áŒŠá && this.ˆáƒ.nextBoolean()) {
                return;
            }
            final EntityZombie var2 = new EntityZombie(this.Ï­Ðƒà);
            var2.áˆºÑ¢Õ(entityLivingIn);
            this.Ï­Ðƒà.Â(entityLivingIn);
            var2.HorizonCode_Horizon_È(this.Ï­Ðƒà.Ê(new BlockPos(var2)), null);
            var2.ˆÏ­(true);
            if (entityLivingIn.h_()) {
                var2.á(true);
            }
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var2);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1016, new BlockPos((int)this.ŒÏ, (int)this.Çªà¢, (int)this.Ê), 0);
        }
    }
    
    @Override
    public float Ðƒáƒ() {
        float var1 = 1.74f;
        if (this.h_()) {
            var1 -= 0.81;
        }
        return var1;
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final ItemStack p_175448_1_) {
        return (p_175448_1_.HorizonCode_Horizon_È() != Items.¥É || !this.h_() || !this.áˆºÇŽØ()) && super.HorizonCode_Horizon_È(p_175448_1_);
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        Object p_180482_2_2 = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        final float var3 = p_180482_1_.Â();
        this.Ý(this.ˆáƒ.nextFloat() < 0.55f * var3);
        if (p_180482_2_2 == null) {
            p_180482_2_2 = new HorizonCode_Horizon_È(this.Ï­Ðƒà.Å.nextFloat() < 0.05f, this.Ï­Ðƒà.Å.nextFloat() < 0.05f, null);
        }
        if (p_180482_2_2 instanceof HorizonCode_Horizon_È) {
            final HorizonCode_Horizon_È var4 = (HorizonCode_Horizon_È)p_180482_2_2;
            if (var4.Â) {
                this.ˆÏ­(true);
            }
            if (var4.HorizonCode_Horizon_È) {
                this.á(true);
                if (this.Ï­Ðƒà.Å.nextFloat() < 0.05) {
                    final List var5 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityChicken.class, this.£É().Â(5.0, 3.0, 5.0), IEntitySelector.Â);
                    if (!var5.isEmpty()) {
                        final EntityChicken var6 = var5.get(0);
                        var6.á(true);
                        this.HorizonCode_Horizon_È((Entity)var6);
                    }
                }
                else if (this.Ï­Ðƒà.Å.nextFloat() < 0.05) {
                    final EntityChicken var7 = new EntityChicken(this.Ï­Ðƒà);
                    var7.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, 0.0f);
                    var7.HorizonCode_Horizon_È(p_180482_1_, null);
                    var7.á(true);
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(var7);
                    this.HorizonCode_Horizon_È((Entity)var7);
                }
            }
        }
        this.HorizonCode_Horizon_È(this.ˆáƒ.nextFloat() < var3 * 0.1f);
        this.HorizonCode_Horizon_È(p_180482_1_);
        this.Â(p_180482_1_);
        if (this.Ý(4) == null) {
            final Calendar var8 = this.Ï­Ðƒà.Õ();
            if (var8.get(2) + 1 == 10 && var8.get(5) == 31 && this.ˆáƒ.nextFloat() < 0.25f) {
                this.HorizonCode_Horizon_È(4, new ItemStack((this.ˆáƒ.nextFloat() < 0.1f) ? Blocks.áŒŠÕ : Blocks.Ø­Æ));
                this.ˆÏ­[4] = 0.0f;
            }
        }
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ý).Â(new AttributeModifier("Random spawn bonus", this.ˆáƒ.nextDouble() * 0.05000000074505806, 0));
        final double var9 = this.ˆáƒ.nextDouble() * 1.5 * var3;
        if (var9 > 1.0) {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).Â(new AttributeModifier("Random zombie-spawn bonus", var9, 2));
        }
        if (this.ˆáƒ.nextFloat() < var3 * 0.05f) {
            this.HorizonCode_Horizon_È(EntityZombie.Â).Â(new AttributeModifier("Leader zombie bonus", this.ˆáƒ.nextDouble() * 0.25 + 0.5, 0));
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).Â(new AttributeModifier("Leader zombie bonus", this.ˆáƒ.nextDouble() * 3.0 + 1.0, 2));
            this.HorizonCode_Horizon_È(true);
        }
        return (IEntityLivingData)p_180482_2_2;
    }
    
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.£Õ && var2.Ø() == 0 && this.ÐƒÇŽà() && this.HorizonCode_Horizon_È(Potion.Ø­à)) {
            if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                final ItemStack itemStack = var2;
                --itemStack.Â;
            }
            if (var2.Â <= 0) {
                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
            }
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.HorizonCode_Horizon_È(this.ˆáƒ.nextInt(2401) + 3600);
            }
            return true;
        }
        return false;
    }
    
    protected void HorizonCode_Horizon_È(final int p_82228_1_) {
        this.Ï­Ï = p_82228_1_;
        this.É().Â(14, (byte)1);
        this.Å(Potion.Ø­à.É);
        this.HorizonCode_Horizon_È(new PotionEffect(Potion.à.É, p_82228_1_, Math.min(this.Ï­Ðƒà.ŠÂµà().HorizonCode_Horizon_È() - 1, 0)));
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)16);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 16) {
            if (!this.áŠ()) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ + 0.5, this.Çªà¢ + 0.5, this.Ê + 0.5, "mob.zombie.remedy", 1.0f + this.ˆáƒ.nextFloat(), this.ˆáƒ.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    protected boolean ÂµÂ() {
        return !this.¥Ê();
    }
    
    public boolean ¥Ê() {
        return this.É().HorizonCode_Horizon_È(14) == 1;
    }
    
    protected void ÐƒÓ() {
        final EntityVillager var1 = new EntityVillager(this.Ï­Ðƒà);
        var1.áˆºÑ¢Õ(this);
        var1.HorizonCode_Horizon_È(this.Ï­Ðƒà.Ê(new BlockPos(var1)), null);
        var1.¥Ê();
        if (this.h_()) {
            var1.Â(-24000);
        }
        this.Ï­Ðƒà.Â(this);
        this.Ï­Ðƒà.HorizonCode_Horizon_È(var1);
        var1.HorizonCode_Horizon_È(new PotionEffect(Potion.ÂµÈ.É, 200, 0));
        this.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1017, new BlockPos((int)this.ŒÏ, (int)this.Çªà¢, (int)this.Ê), 0);
    }
    
    protected int áˆºÕ() {
        int var1 = 1;
        if (this.ˆáƒ.nextFloat() < 0.01f) {
            for (int var2 = 0, var3 = (int)this.ŒÏ - 4; var3 < (int)this.ŒÏ + 4 && var2 < 14; ++var3) {
                for (int var4 = (int)this.Çªà¢ - 4; var4 < (int)this.Çªà¢ + 4 && var2 < 14; ++var4) {
                    for (int var5 = (int)this.Ê - 4; var5 < (int)this.Ê + 4 && var2 < 14; ++var5) {
                        final Block var6 = this.Ï­Ðƒà.Â(new BlockPos(var3, var4, var5)).Ý();
                        if (var6 == Blocks.ÇŽÄ || var6 == Blocks.Ê) {
                            if (this.ˆáƒ.nextFloat() < 0.3f) {
                                ++var1;
                            }
                            ++var2;
                        }
                    }
                }
            }
        }
        return var1;
    }
    
    public void £á(final boolean p_146071_1_) {
        this.Ý(p_146071_1_ ? 0.5f : 1.0f);
    }
    
    @Override
    protected final void HorizonCode_Horizon_È(final float width, final float height) {
        final boolean var3 = this.ˆÐƒØ > 0.0f && this.Çªà > 0.0f;
        this.ˆÐƒØ = width;
        this.Çªà = height;
        if (!var3) {
            this.Ý(1.0f);
        }
    }
    
    protected final void Ý(final float p_146069_1_) {
        super.HorizonCode_Horizon_È(this.ˆÐƒØ * p_146069_1_, this.Çªà * p_146069_1_);
    }
    
    @Override
    public double Ï­Ï­Ï() {
        return super.Ï­Ï­Ï() - 0.5;
    }
    
    @Override
    public void Â(final DamageSource cause) {
        super.Â(cause);
        if (cause.áˆºÑ¢Õ() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)cause.áˆºÑ¢Õ()).Ø() && ((EntityCreeper)cause.áˆºÑ¢Õ()).¥Ê()) {
            ((EntityCreeper)cause.áˆºÑ¢Õ()).ÐƒÓ();
            this.HorizonCode_Horizon_È(new ItemStack(Items.ˆ, 1, 2), 0.0f);
        }
    }
    
    class HorizonCode_Horizon_È implements IEntityLivingData
    {
        public boolean HorizonCode_Horizon_È;
        public boolean Â;
        private static final String Ø­áŒŠá = "CL_00001704";
        
        private HorizonCode_Horizon_È(final boolean p_i2348_2_, final boolean p_i2348_3_) {
            this.HorizonCode_Horizon_È = false;
            this.Â = false;
            this.HorizonCode_Horizon_È = p_i2348_2_;
            this.Â = p_i2348_3_;
        }
        
        HorizonCode_Horizon_È(final EntityZombie entityZombie, final boolean p_i2349_2_, final boolean p_i2349_3_, final Object p_i2349_4_) {
            this(entityZombie, p_i2349_2_, p_i2349_3_);
        }
    }
}
