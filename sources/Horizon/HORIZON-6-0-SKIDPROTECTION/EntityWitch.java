package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final UUID Â;
    private static final AttributeModifier Ý;
    private static final Item_1028566121[] Ø­Ñ¢Ï­Ø­áˆº;
    private int ŒÂ;
    private static final String Ï­Ï = "CL_00001701";
    
    static {
        Â = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
        Ý = new AttributeModifier(EntityWitch.Â, "Drinking speed penalty", -0.25, 0).HorizonCode_Horizon_È(false);
        Ø­Ñ¢Ï­Ø­áˆº = new Item_1028566121[] { Items.Ø­Ñ¢á€, Items.£Ø­à, Items.ÇŽá, Items.ÇªÂ, Items.Ñ¢ÇŽÏ, Items.É, Items.áŒŠà, Items.áŒŠà };
    }
    
    public EntityWitch(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.6f, 1.95f);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.HorizonCode_Horizon_È);
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.É().HorizonCode_Horizon_È(21, (Object)(byte)0);
    }
    
    @Override
    protected String µÐƒáƒ() {
        return null;
    }
    
    @Override
    protected String ¥áŠ() {
        return null;
    }
    
    @Override
    protected String µÊ() {
        return null;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_82197_1_) {
        this.É().Â(21, (byte)(byte)(p_82197_1_ ? 1 : 0));
    }
    
    public boolean Ø() {
        return this.É().HorizonCode_Horizon_È(21) == 1;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(26.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
    }
    
    @Override
    public void ˆÏ­() {
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (this.Ø()) {
                if (this.ŒÂ-- <= 0) {
                    this.HorizonCode_Horizon_È(false);
                    final ItemStack var1 = this.Çª();
                    this.HorizonCode_Horizon_È(0, (ItemStack)null);
                    if (var1 != null && var1.HorizonCode_Horizon_È() == Items.µÂ) {
                        final List var2 = Items.µÂ.ÂµÈ(var1);
                        if (var2 != null) {
                            for (final PotionEffect var4 : var2) {
                                this.HorizonCode_Horizon_È(new PotionEffect(var4));
                            }
                        }
                    }
                    this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Ý(EntityWitch.Ý);
                }
            }
            else {
                short var5 = -1;
                if (this.ˆáƒ.nextFloat() < 0.15f && this.HorizonCode_Horizon_È(Material.Ø) && !this.HorizonCode_Horizon_È(Potion.Å)) {
                    var5 = 8237;
                }
                else if (this.ˆáƒ.nextFloat() < 0.15f && this.ˆÏ() && !this.HorizonCode_Horizon_È(Potion.£á)) {
                    var5 = 16307;
                }
                else if (this.ˆáƒ.nextFloat() < 0.05f && this.Ï­Ä() < this.ÇŽÊ()) {
                    var5 = 16341;
                }
                else if (this.ˆáƒ.nextFloat() < 0.25f && this.Ñ¢Ó() != null && !this.HorizonCode_Horizon_È(Potion.Ý) && this.Ñ¢Ó().Âµá€(this) > 121.0) {
                    var5 = 16274;
                }
                else if (this.ˆáƒ.nextFloat() < 0.25f && this.Ñ¢Ó() != null && !this.HorizonCode_Horizon_È(Potion.Ý) && this.Ñ¢Ó().Âµá€(this) > 121.0) {
                    var5 = 16274;
                }
                if (var5 > -1) {
                    this.HorizonCode_Horizon_È(0, new ItemStack(Items.µÂ, 1, var5));
                    this.ŒÂ = this.Çª().á();
                    this.HorizonCode_Horizon_È(true);
                    final IAttributeInstance var6 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
                    var6.Ý(EntityWitch.Ý);
                    var6.Â(EntityWitch.Ý);
                }
            }
            if (this.ˆáƒ.nextFloat() < 7.5E-4f) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)15);
            }
        }
        super.ˆÏ­();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 15) {
            for (int var2 = 0; var2 < this.ˆáƒ.nextInt(35) + 10; ++var2) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.ˆà, this.ŒÏ + this.ˆáƒ.nextGaussian() * 0.12999999523162842, this.£É().Âµá€ + 0.5 + this.ˆáƒ.nextGaussian() * 0.12999999523162842, this.Ê + this.ˆáƒ.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0, new int[0]);
            }
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    protected float Ø­áŒŠá(final DamageSource p_70672_1_, float p_70672_2_) {
        p_70672_2_ = super.Ø­áŒŠá(p_70672_1_, p_70672_2_);
        if (p_70672_1_.áˆºÑ¢Õ() == this) {
            p_70672_2_ = 0.0f;
        }
        if (p_70672_1_.¥Æ()) {
            p_70672_2_ *= 0.15;
        }
        return p_70672_2_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(3) + 1, var4 = 0; var4 < var3; ++var4) {
            int var5 = this.ˆáƒ.nextInt(3);
            final Item_1028566121 var6 = EntityWitch.Ø­Ñ¢Ï­Ø­áˆº[this.ˆáƒ.nextInt(EntityWitch.Ø­Ñ¢Ï­Ø­áˆº.length)];
            if (p_70628_2_ > 0) {
                var5 += this.ˆáƒ.nextInt(p_70628_2_ + 1);
            }
            for (int var7 = 0; var7 < var5; ++var7) {
                this.HorizonCode_Horizon_È(var6, 1);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        if (!this.Ø()) {
            final EntityPotion var3 = new EntityPotion(this.Ï­Ðƒà, this, 32732);
            final double var4 = p_82196_1_.Çªà¢ + p_82196_1_.Ðƒáƒ() - 1.100000023841858;
            final EntityPotion entityPotion = var3;
            entityPotion.áƒ += 20.0f;
            final double var5 = p_82196_1_.ŒÏ + p_82196_1_.ÇŽÉ - this.ŒÏ;
            final double var6 = var4 - this.Çªà¢;
            final double var7 = p_82196_1_.Ê + p_82196_1_.ÇŽÕ - this.Ê;
            final float var8 = MathHelper.HorizonCode_Horizon_È(var5 * var5 + var7 * var7);
            if (var8 >= 8.0f && !p_82196_1_.HorizonCode_Horizon_È(Potion.Ø­áŒŠá)) {
                var3.HorizonCode_Horizon_È(32698);
            }
            else if (p_82196_1_.Ï­Ä() >= 8.0f && !p_82196_1_.HorizonCode_Horizon_È(Potion.µÕ)) {
                var3.HorizonCode_Horizon_È(32660);
            }
            else if (var8 <= 3.0f && !p_82196_1_.HorizonCode_Horizon_È(Potion.Ø­à) && this.ˆáƒ.nextFloat() < 0.25f) {
                var3.HorizonCode_Horizon_È(32696);
            }
            var3.a_(var5, var6 + var8 * 0.2f, var7, 0.75f, 8.0f);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var3);
        }
    }
    
    @Override
    public float Ðƒáƒ() {
        return 1.62f;
    }
}
