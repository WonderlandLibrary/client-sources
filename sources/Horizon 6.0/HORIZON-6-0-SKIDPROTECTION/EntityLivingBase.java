package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;

public abstract class EntityLivingBase extends Entity
{
    private static final UUID HorizonCode_Horizon_È;
    private static final AttributeModifier Â;
    private BaseAttributeMap Ý;
    private final CombatTracker Ø­áŒŠá;
    private final Map Âµá€;
    private final ItemStack[] Ø;
    public boolean £á;
    public int Å;
    public int £à;
    public int µà;
    public int ÇŽá;
    public float Ñ¢à;
    public int ÇªØ­;
    public float £áŒŠá;
    public float áˆº;
    public float Šà;
    public float áŒŠá€;
    public float ¥Ï;
    public int ˆà¢;
    public float Ñ¢Ç;
    public float £É;
    public float Ðƒáƒ;
    public float Ðƒà;
    public float ¥É;
    public float £ÇªÓ;
    public float ÂµÕ;
    public float Š;
    public float Ø­Ñ¢á€;
    protected EntityPlayer Ñ¢Ó;
    protected int Ø­Æ;
    protected boolean áŒŠÔ;
    protected int ŠÕ;
    protected float £Ø­à;
    protected float µÐƒáƒ;
    protected float áŒŠÕ;
    protected float ÂµÂ;
    protected float áŒŠá;
    protected int ˆØ;
    protected float áˆºà;
    protected boolean ÐƒÂ;
    public float £áƒ;
    public float Ï­áˆºÓ;
    protected float Çª;
    protected int ÇŽÄ;
    protected double ˆÈ;
    protected double ˆÅ;
    protected double ÇªÉ;
    protected double ŠÏ­áˆºá;
    protected double ÇŽà;
    private boolean áŒŠÆ;
    private EntityLivingBase áˆºÑ¢Õ;
    private int ÂµÈ;
    private EntityLivingBase á;
    private int ˆÏ­;
    public float ŠáˆºÂ;
    private int Ø­Ñ¢Ï­Ø­áˆº;
    private float ŒÂ;
    private static final String Ï­Ï = "CL_00001549";
    
    static {
        HorizonCode_Horizon_È = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        Â = new AttributeModifier(EntityLivingBase.HorizonCode_Horizon_È, "Sprinting speed boost", 0.30000001192092896, 2).HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void ÇŽÕ() {
        this.HorizonCode_Horizon_È(DamageSource.áˆºÑ¢Õ, Float.MAX_VALUE);
    }
    
    public EntityLivingBase(final World worldIn) {
        super(worldIn);
        this.Ø­áŒŠá = new CombatTracker(this);
        this.Âµá€ = Maps.newHashMap();
        this.Ø = new ItemStack[5];
        this.ˆà¢ = 20;
        this.Ø­Ñ¢á€ = 0.02f;
        this.áŒŠÆ = true;
        this.áŒŠà();
        this.áˆºÑ¢Õ(this.ÇŽÊ());
        this.Ø­à = true;
        this.Ðƒà = (float)((Math.random() + 1.0) * 0.009999999776482582);
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        this.Ðƒáƒ = (float)Math.random() * 12398.0f;
        this.É = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.ÂµÕ = this.É;
        this.Ô = 0.6f;
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(7, (Object)0);
        this.£Ó.HorizonCode_Horizon_È(8, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(9, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(6, 1.0f);
    }
    
    protected void áŒŠà() {
        this.µÐƒÓ().Â(SharedMonsterAttributes.HorizonCode_Horizon_È);
        this.µÐƒÓ().Â(SharedMonsterAttributes.Ý);
        this.µÐƒÓ().Â(SharedMonsterAttributes.Ø­áŒŠá);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_, final BlockPos p_180433_5_) {
        if (!this.£ÂµÄ()) {
            this.Ø­Âµ();
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­à > 3.0f && p_180433_3_) {
            final IBlockState var6 = this.Ï­Ðƒà.Â(p_180433_5_);
            final Block var7 = var6.Ý();
            final float var8 = MathHelper.Ó(this.Ï­à - 3.0f);
            if (var7.Ó() != Material.HorizonCode_Horizon_È) {
                double var9 = Math.min(0.2f + var8 / 15.0f, 10.0f);
                if (var9 > 2.5) {
                    var9 = 2.5;
                }
                final int var10 = (int)(150.0 * var9);
                ((WorldServer)this.Ï­Ðƒà).HorizonCode_Horizon_È(EnumParticleTypes.ŠÂµà, this.ŒÏ, this.Çªà¢, this.Ê, var10, 0.0, 0.0, 0.0, 0.15000000596046448, Block.HorizonCode_Horizon_È(var6));
            }
        }
        super.HorizonCode_Horizon_È(p_180433_1_, p_180433_3_, p_180433_4_, p_180433_5_);
    }
    
    public boolean Ø­Ñ¢Ï­Ø­áˆº() {
        return false;
    }
    
    @Override
    public void Õ() {
        this.£áŒŠá = this.áˆº;
        super.Õ();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("livingEntityBaseTick");
        final boolean var1 = this instanceof EntityPlayer;
        if (this.Œ()) {
            if (this.£Ï()) {
                this.HorizonCode_Horizon_È(DamageSource.Âµá€, 1.0f);
            }
            else if (var1 && !this.Ï­Ðƒà.áŠ().HorizonCode_Horizon_È(this.£É())) {
                final double var2 = this.Ï­Ðƒà.áŠ().HorizonCode_Horizon_È(this) + this.Ï­Ðƒà.áŠ().ˆÏ­();
                if (var2 < 0.0) {
                    this.HorizonCode_Horizon_È(DamageSource.Âµá€, Math.max(1, MathHelper.Ý(-var2 * this.Ï­Ðƒà.áŠ().£á())));
                }
            }
        }
        if (this.ˆáŠ() || this.Ï­Ðƒà.ŠÄ) {
            this.¥à();
        }
        final boolean var3 = var1 && ((EntityPlayer)this).áˆºáˆºáŠ.HorizonCode_Horizon_È;
        if (this.Œ() && this.HorizonCode_Horizon_È(Material.Ø)) {
            if (!this.Ø­Ñ¢Ï­Ø­áˆº() && !this.ˆÏ­(Potion.Å.É) && !var3) {
                this.Ø(this.á(this.ˆÓ()));
                if (this.ˆÓ() == -20) {
                    this.Ø(0);
                    for (int var4 = 0; var4 < 8; ++var4) {
                        final float var5 = this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat();
                        final float var6 = this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat();
                        final float var7 = this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat();
                        this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ + var5, this.Çªà¢ + var6, this.Ê + var7, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
                    }
                    this.HorizonCode_Horizon_È(DamageSource.Ó, 2.0f);
                }
            }
            if (!this.Ï­Ðƒà.ŠÄ && this.áˆºÇŽØ() && this.Æ instanceof EntityLivingBase) {
                this.HorizonCode_Horizon_È((Entity)null);
            }
        }
        else {
            this.Ø(300);
        }
        if (this.Œ() && this.áŒŠ()) {
            this.¥à();
        }
        this.Ñ¢Ç = this.£É;
        if (this.µà > 0) {
            --this.µà;
        }
        if (this.ˆÉ > 0 && !(this instanceof EntityPlayerMP)) {
            --this.ˆÉ;
        }
        if (this.Ï­Ä() <= 0.0f) {
            this.ŒÂ();
        }
        if (this.Ø­Æ > 0) {
            --this.Ø­Æ;
        }
        else {
            this.Ñ¢Ó = null;
        }
        if (this.á != null && !this.á.Œ()) {
            this.á = null;
        }
        if (this.áˆºÑ¢Õ != null) {
            if (!this.áˆºÑ¢Õ.Œ()) {
                this.Ý((EntityLivingBase)null);
            }
            else if (this.Œ - this.ÂµÈ > 100) {
                this.Ý((EntityLivingBase)null);
            }
        }
        this.Ñ¢ÇŽÏ();
        this.ÂµÂ = this.áŒŠÕ;
        this.£ÇªÓ = this.¥É;
        this.Š = this.ÂµÕ;
        this.á€ = this.É;
        this.Õ = this.áƒ;
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
    }
    
    public boolean h_() {
        return false;
    }
    
    protected void ŒÂ() {
        ++this.ÇªØ­;
        if (this.ÇªØ­ == 20) {
            if (!this.Ï­Ðƒà.ŠÄ && (this.Ø­Æ > 0 || this.ŠØ()) && this.Ï­Ï() && this.Ï­Ðƒà.Çªà¢().Â("doMobLoot")) {
                int var1 = this.Âµá€(this.Ñ¢Ó);
                while (var1 > 0) {
                    final int var2 = EntityXPOrb.HorizonCode_Horizon_È(var1);
                    var1 -= var2;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, var2));
                }
            }
            this.á€();
            for (int var1 = 0; var1 < 20; ++var1) {
                final double var3 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var4 = this.ˆáƒ.nextGaussian() * 0.02;
                final double var5 = this.ˆáƒ.nextGaussian() * 0.02;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.HorizonCode_Horizon_È, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, var3, var4, var5, new int[0]);
            }
        }
    }
    
    protected boolean Ï­Ï() {
        return !this.h_();
    }
    
    protected int á(final int p_70682_1_) {
        final int var2 = EnchantmentHelper.HorizonCode_Horizon_È((Entity)this);
        return (var2 > 0 && this.ˆáƒ.nextInt(var2 + 1) > 0) ? p_70682_1_ : (p_70682_1_ - 1);
    }
    
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        return 0;
    }
    
    protected boolean ŠØ() {
        return false;
    }
    
    public Random ˆÐƒØ() {
        return this.ˆáƒ;
    }
    
    public EntityLivingBase Çªà() {
        return this.áˆºÑ¢Õ;
    }
    
    public int ¥Å() {
        return this.ÂµÈ;
    }
    
    public void Ý(final EntityLivingBase p_70604_1_) {
        this.áˆºÑ¢Õ = p_70604_1_;
        this.ÂµÈ = this.Œ;
    }
    
    public EntityLivingBase Œáƒ() {
        return this.á;
    }
    
    public int Œá() {
        return this.ˆÏ­;
    }
    
    public void ˆÏ­(final Entity p_130011_1_) {
        if (p_130011_1_ instanceof EntityLivingBase) {
            this.á = (EntityLivingBase)p_130011_1_;
        }
        else {
            this.á = null;
        }
        this.ˆÏ­ = this.Œ;
    }
    
    public int µÂ() {
        return this.ŠÕ;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("HealF", this.Ï­Ä());
        tagCompound.HorizonCode_Horizon_È("Health", (short)Math.ceil(this.Ï­Ä()));
        tagCompound.HorizonCode_Horizon_È("HurtTime", (short)this.µà);
        tagCompound.HorizonCode_Horizon_È("HurtByTimestamp", this.ÂµÈ);
        tagCompound.HorizonCode_Horizon_È("DeathTime", (short)this.ÇªØ­);
        tagCompound.HorizonCode_Horizon_È("AbsorptionAmount", this.Ñ¢È());
        for (final ItemStack var5 : this.Ðƒá()) {
            if (var5 != null) {
                this.Ý.HorizonCode_Horizon_È(var5.ŒÏ());
            }
        }
        tagCompound.HorizonCode_Horizon_È("Attributes", SharedMonsterAttributes.HorizonCode_Horizon_È(this.µÐƒÓ()));
        for (final ItemStack var5 : this.Ðƒá()) {
            if (var5 != null) {
                this.Ý.Â(var5.ŒÏ());
            }
        }
        if (!this.Âµá€.isEmpty()) {
            final NBTTagList var6 = new NBTTagList();
            for (final PotionEffect var8 : this.Âµá€.values()) {
                var6.HorizonCode_Horizon_È(var8.HorizonCode_Horizon_È(new NBTTagCompound()));
            }
            tagCompound.HorizonCode_Horizon_È("ActiveEffects", var6);
        }
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.ˆÏ­(tagCompund.Ø("AbsorptionAmount"));
        if (tagCompund.Â("Attributes", 9) && this.Ï­Ðƒà != null && !this.Ï­Ðƒà.ŠÄ) {
            SharedMonsterAttributes.HorizonCode_Horizon_È(this.µÐƒÓ(), tagCompund.Ý("Attributes", 10));
        }
        if (tagCompund.Â("ActiveEffects", 9)) {
            final NBTTagList var2 = tagCompund.Ý("ActiveEffects", 10);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                final NBTTagCompound var4 = var2.Â(var3);
                final PotionEffect var5 = PotionEffect.Â(var4);
                if (var5 != null) {
                    this.Âµá€.put(var5.HorizonCode_Horizon_È(), var5);
                }
            }
        }
        if (tagCompund.Â("HealF", 99)) {
            this.áˆºÑ¢Õ(tagCompund.Ø("HealF"));
        }
        else {
            final NBTBase var6 = tagCompund.HorizonCode_Horizon_È("Health");
            if (var6 == null) {
                this.áˆºÑ¢Õ(this.ÇŽÊ());
            }
            else if (var6.HorizonCode_Horizon_È() == 5) {
                this.áˆºÑ¢Õ(((NBTTagFloat)var6).áˆºÑ¢Õ());
            }
            else if (var6.HorizonCode_Horizon_È() == 2) {
                this.áˆºÑ¢Õ((float)((NBTTagShort)var6).à());
            }
        }
        this.µà = tagCompund.Âµá€("HurtTime");
        this.ÇªØ­ = tagCompund.Âµá€("DeathTime");
        this.ÂµÈ = tagCompund.Ó("HurtByTimestamp");
    }
    
    protected void Ñ¢ÇŽÏ() {
        final Iterator var1 = this.Âµá€.keySet().iterator();
        while (var1.hasNext()) {
            final Integer var2 = var1.next();
            final PotionEffect var3 = this.Âµá€.get(var2);
            if (!var3.HorizonCode_Horizon_È(this)) {
                if (this.Ï­Ðƒà.ŠÄ) {
                    continue;
                }
                var1.remove();
                this.Ø­áŒŠá(var3);
            }
            else {
                if (var3.Â() % 600 != 0) {
                    continue;
                }
                this.HorizonCode_Horizon_È(var3, false);
            }
        }
        if (this.áŒŠÆ) {
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.ÇªÂ();
            }
            this.áŒŠÆ = false;
        }
        final int var4 = this.£Ó.Ý(7);
        final boolean var5 = this.£Ó.HorizonCode_Horizon_È(8) > 0;
        if (var4 > 0) {
            boolean var6 = false;
            if (!this.áŒŠÏ()) {
                var6 = this.ˆáƒ.nextBoolean();
            }
            else {
                var6 = (this.ˆáƒ.nextInt(15) == 0);
            }
            if (var5) {
                var6 &= (this.ˆáƒ.nextInt(5) == 0);
            }
            if (var6 && var4 > 0) {
                final double var7 = (var4 >> 16 & 0xFF) / 255.0;
                final double var8 = (var4 >> 8 & 0xFF) / 255.0;
                final double var9 = (var4 >> 0 & 0xFF) / 255.0;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(var5 ? EnumParticleTypes.µà : EnumParticleTypes.£à, this.ŒÏ + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, this.Çªà¢ + this.ˆáƒ.nextDouble() * this.£ÂµÄ, this.Ê + (this.ˆáƒ.nextDouble() - 0.5) * this.áŒŠ, var7, var8, var9, new int[0]);
            }
        }
    }
    
    protected void ÇªÂ() {
        if (this.Âµá€.isEmpty()) {
            this.ÂµáˆºÂ();
            this.Ó(false);
        }
        else {
            final int var1 = PotionHelper.HorizonCode_Horizon_È(this.Âµá€.values());
            this.£Ó.Â(8, (byte)(byte)(PotionHelper.Â(this.Âµá€.values()) ? 1 : 0));
            this.£Ó.Â(7, var1);
            this.Ó(this.ˆÏ­(Potion.£à.É));
        }
    }
    
    protected void ÂµáˆºÂ() {
        this.£Ó.Â(8, (byte)0);
        this.£Ó.Â(7, 0);
    }
    
    public void ¥Âµá€() {
        final Iterator var1 = this.Âµá€.keySet().iterator();
        while (var1.hasNext()) {
            final Integer var2 = var1.next();
            final PotionEffect var3 = this.Âµá€.get(var2);
            if (!this.Ï­Ðƒà.ŠÄ) {
                var1.remove();
                this.Ø­áŒŠá(var3);
            }
        }
    }
    
    public Collection ÇŽÈ() {
        return this.Âµá€.values();
    }
    
    public boolean ˆÏ­(final int p_82165_1_) {
        return this.Âµá€.containsKey(p_82165_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final Potion p_70644_1_) {
        return this.Âµá€.containsKey(p_70644_1_.É);
    }
    
    public PotionEffect Â(final Potion p_70660_1_) {
        return this.Âµá€.get(p_70660_1_.É);
    }
    
    public void HorizonCode_Horizon_È(final PotionEffect p_70690_1_) {
        if (this.Â(p_70690_1_)) {
            if (this.Âµá€.containsKey(p_70690_1_.HorizonCode_Horizon_È())) {
                this.Âµá€.get(p_70690_1_.HorizonCode_Horizon_È()).HorizonCode_Horizon_È(p_70690_1_);
                this.HorizonCode_Horizon_È(this.Âµá€.get(p_70690_1_.HorizonCode_Horizon_È()), true);
            }
            else {
                this.Âµá€.put(p_70690_1_.HorizonCode_Horizon_È(), p_70690_1_);
                this.Ý(p_70690_1_);
            }
        }
    }
    
    public boolean Â(final PotionEffect p_70687_1_) {
        if (this.¥áŒŠà() == EnumCreatureAttribute.Â) {
            final int var2 = p_70687_1_.HorizonCode_Horizon_È();
            if (var2 == Potion.á.É || var2 == Potion.µÕ.É) {
                return false;
            }
        }
        return true;
    }
    
    public boolean ÇªáˆºÕ() {
        return this.¥áŒŠà() == EnumCreatureAttribute.Â;
    }
    
    public void £á(final int p_70618_1_) {
        this.Âµá€.remove(p_70618_1_);
    }
    
    public void Å(final int p_82170_1_) {
        final PotionEffect var2 = this.Âµá€.remove(p_82170_1_);
        if (var2 != null) {
            this.Ø­áŒŠá(var2);
        }
    }
    
    protected void Ý(final PotionEffect p_70670_1_) {
        this.áŒŠÆ = true;
        if (!this.Ï­Ðƒà.ŠÄ) {
            Potion.HorizonCode_Horizon_È[p_70670_1_.HorizonCode_Horizon_È()].Â(this, this.µÐƒÓ(), p_70670_1_.Ý());
        }
    }
    
    protected void HorizonCode_Horizon_È(final PotionEffect p_70695_1_, final boolean p_70695_2_) {
        this.áŒŠÆ = true;
        if (p_70695_2_ && !this.Ï­Ðƒà.ŠÄ) {
            Potion.HorizonCode_Horizon_È[p_70695_1_.HorizonCode_Horizon_È()].HorizonCode_Horizon_È(this, this.µÐƒÓ(), p_70695_1_.Ý());
            Potion.HorizonCode_Horizon_È[p_70695_1_.HorizonCode_Horizon_È()].Â(this, this.µÐƒÓ(), p_70695_1_.Ý());
        }
    }
    
    protected void Ø­áŒŠá(final PotionEffect p_70688_1_) {
        this.áŒŠÆ = true;
        if (!this.Ï­Ðƒà.ŠÄ) {
            Potion.HorizonCode_Horizon_È[p_70688_1_.HorizonCode_Horizon_È()].HorizonCode_Horizon_È(this, this.µÐƒÓ(), p_70688_1_.Ý());
        }
    }
    
    public void a_(final float p_70691_1_) {
        final float var2 = this.Ï­Ä();
        if (var2 > 0.0f) {
            this.áˆºÑ¢Õ(var2 + p_70691_1_);
        }
    }
    
    public final float Ï­Ä() {
        return this.£Ó.Ø­áŒŠá(6);
    }
    
    public void áˆºÑ¢Õ(final float p_70606_1_) {
        this.£Ó.Â(6, MathHelper.HorizonCode_Horizon_È(p_70606_1_, 0.0f, this.ÇŽÊ()));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (this.Ï­Ðƒà.ŠÄ) {
            return false;
        }
        this.ŠÕ = 0;
        if (this.Ï­Ä() <= 0.0f) {
            return false;
        }
        if (source.Å() && this.HorizonCode_Horizon_È(Potion.£á)) {
            return false;
        }
        if ((source == DamageSource.£á || source == DamageSource.Å) && this.Ý(4) != null) {
            this.Ý(4).HorizonCode_Horizon_È((int)(amount * 4.0f + this.ˆáƒ.nextFloat() * amount * 2.0f), this);
            amount *= 0.75f;
        }
        this.áŒŠá€ = 1.5f;
        boolean var3 = true;
        if (this.ˆÉ > this.ˆà¢ / 2.0f) {
            if (amount <= this.áˆºà) {
                return false;
            }
            this.Â(source, amount - this.áˆºà);
            this.áˆºà = amount;
            var3 = false;
        }
        else {
            this.áˆºà = amount;
            this.ˆÉ = this.ˆà¢;
            this.Â(source, amount);
            final int n = 10;
            this.ÇŽá = n;
            this.µà = n;
        }
        this.Ñ¢à = 0.0f;
        final Entity var4 = source.áˆºÑ¢Õ();
        if (var4 != null) {
            if (var4 instanceof EntityLivingBase) {
                this.Ý((EntityLivingBase)var4);
            }
            if (var4 instanceof EntityPlayer) {
                this.Ø­Æ = 100;
                this.Ñ¢Ó = (EntityPlayer)var4;
            }
            else if (var4 instanceof EntityWolf) {
                final EntityWolf var5 = (EntityWolf)var4;
                if (var5.ÐƒÓ()) {
                    this.Ø­Æ = 100;
                    this.Ñ¢Ó = null;
                }
            }
        }
        if (var3) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)2);
            if (source != DamageSource.Ó) {
                this.Ï();
            }
            if (var4 != null) {
                double var6;
                double var7;
                for (var6 = var4.ŒÏ - this.ŒÏ, var7 = var4.Ê - this.Ê; var6 * var6 + var7 * var7 < 1.0E-4; var6 = (Math.random() - Math.random()) * 0.01, var7 = (Math.random() - Math.random()) * 0.01) {}
                this.Ñ¢à = (float)(Math.atan2(var7, var6) * 180.0 / 3.141592653589793 - this.É);
                this.HorizonCode_Horizon_È(var4, amount, var6, var7);
            }
            else {
                this.Ñ¢à = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.Ï­Ä() <= 0.0f) {
            final String var8 = this.µÊ();
            if (var3 && var8 != null) {
                this.HorizonCode_Horizon_È(var8, this.ˆÂ(), this.áŒŠÈ());
            }
            this.Â(source);
        }
        else {
            final String var8 = this.¥áŠ();
            if (var3 && var8 != null) {
                this.HorizonCode_Horizon_È(var8, this.ˆÂ(), this.áŒŠÈ());
            }
        }
        return true;
    }
    
    public void Ý(final ItemStack p_70669_1_) {
        this.HorizonCode_Horizon_È("random.break", 0.8f, 0.8f + this.Ï­Ðƒà.Å.nextFloat() * 0.4f);
        for (int var2 = 0; var2 < 5; ++var2) {
            Vec3 var3 = new Vec3((this.ˆáƒ.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            var3 = var3.HorizonCode_Horizon_È(-this.áƒ * 3.1415927f / 180.0f);
            var3 = var3.Â(-this.É * 3.1415927f / 180.0f);
            final double var4 = -this.ˆáƒ.nextFloat() * 0.6 - 0.3;
            Vec3 var5 = new Vec3((this.ˆáƒ.nextFloat() - 0.5) * 0.3, var4, 0.6);
            var5 = var5.HorizonCode_Horizon_È(-this.áƒ * 3.1415927f / 180.0f);
            var5 = var5.Â(-this.É * 3.1415927f / 180.0f);
            var5 = var5.Â(this.ŒÏ, this.Çªà¢ + this.Ðƒáƒ(), this.Ê);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Õ, var5.HorizonCode_Horizon_È, var5.Â, var5.Ý, var3.HorizonCode_Horizon_È, var3.Â + 0.05, var3.Ý, Item_1028566121.HorizonCode_Horizon_È(p_70669_1_.HorizonCode_Horizon_È()));
        }
    }
    
    public void Â(final DamageSource cause) {
        final Entity var2 = cause.áˆºÑ¢Õ();
        final EntityLivingBase var3 = this.ŒÓ();
        if (this.ˆØ >= 0 && var3 != null) {
            var3.HorizonCode_Horizon_È(this, this.ˆØ);
        }
        if (var2 != null) {
            var2.HorizonCode_Horizon_È(this);
        }
        this.áŒŠÔ = true;
        this.ÇŽØ().Âµá€();
        if (!this.Ï­Ðƒà.ŠÄ) {
            int var4 = 0;
            if (var2 instanceof EntityPlayer) {
                var4 = EnchantmentHelper.Ø((EntityLivingBase)var2);
            }
            if (this.Ï­Ï() && this.Ï­Ðƒà.Çªà¢().Â("doMobLoot")) {
                this.HorizonCode_Horizon_È(this.Ø­Æ > 0, var4);
                this.Â(this.Ø­Æ > 0, var4);
                if (this.Ø­Æ > 0 && this.ˆáƒ.nextFloat() < 0.025f + var4 * 0.01f) {
                    this.áˆºáˆºáŠ();
                }
            }
        }
        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)3);
    }
    
    protected void Â(final boolean p_82160_1_, final int p_82160_2_) {
    }
    
    public void HorizonCode_Horizon_È(final Entity p_70653_1_, final float p_70653_2_, final double p_70653_3_, final double p_70653_5_) {
        if (this.ˆáƒ.nextDouble() >= this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ý).Âµá€()) {
            this.áŒŠÏ = true;
            final float var7 = MathHelper.HorizonCode_Horizon_È(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
            final float var8 = 0.4f;
            this.ÇŽÉ /= 2.0;
            this.ˆá /= 2.0;
            this.ÇŽÕ /= 2.0;
            this.ÇŽÉ -= p_70653_3_ / var7 * var8;
            this.ˆá += var8;
            this.ÇŽÕ -= p_70653_5_ / var7 * var8;
            if (this.ˆá > 0.4000000059604645) {
                this.ˆá = 0.4000000059604645;
            }
        }
    }
    
    protected String ¥áŠ() {
        return "game.neutral.hurt";
    }
    
    protected String µÊ() {
        return "game.neutral.die";
    }
    
    protected void áˆºáˆºáŠ() {
    }
    
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
    }
    
    public boolean i_() {
        final int var1 = MathHelper.Ý(this.ŒÏ);
        final int var2 = MathHelper.Ý(this.£É().Â);
        final int var3 = MathHelper.Ý(this.Ê);
        final Block var4 = this.Ï­Ðƒà.Â(new BlockPos(var1, var2, var3)).Ý();
        return (var4 == Blocks.áŒŠÏ || var4 == Blocks.ÇŽà) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).Ø­áŒŠá());
    }
    
    @Override
    public boolean Œ() {
        return !this.ˆáŠ && this.Ï­Ä() > 0.0f;
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        super.Ø­áŒŠá(distance, damageMultiplier);
        final PotionEffect var3 = this.Â(Potion.áˆºÑ¢Õ);
        final float var4 = (var3 != null) ? (var3.Ý() + 1) : 0.0f;
        final int var5 = MathHelper.Ó((distance - 3.0f - var4) * damageMultiplier);
        if (var5 > 0) {
            this.HorizonCode_Horizon_È(this.£à(var5), 1.0f, 1.0f);
            this.HorizonCode_Horizon_È(DamageSource.áŒŠÆ, var5);
            final int var6 = MathHelper.Ý(this.ŒÏ);
            final int var7 = MathHelper.Ý(this.Çªà¢ - 0.20000000298023224);
            final int var8 = MathHelper.Ý(this.Ê);
            final Block var9 = this.Ï­Ðƒà.Â(new BlockPos(var6, var7, var8)).Ý();
            if (var9.Ó() != Material.HorizonCode_Horizon_È) {
                final Block.Â var10 = var9.ˆá;
                this.HorizonCode_Horizon_È(var10.Ý(), var10.Ø­áŒŠá() * 0.5f, var10.Âµá€() * 0.75f);
            }
        }
    }
    
    protected String £à(final int p_146067_1_) {
        return (p_146067_1_ > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
    }
    
    @Override
    public void Œà() {
        final int n = 10;
        this.ÇŽá = n;
        this.µà = n;
        this.Ñ¢à = 0.0f;
    }
    
    public int áŒŠÉ() {
        int var1 = 0;
        for (final ItemStack var5 : this.Ðƒá()) {
            if (var5 != null && var5.HorizonCode_Horizon_È() instanceof ItemArmor) {
                final int var6 = ((ItemArmor)var5.HorizonCode_Horizon_È()).áŒŠÆ;
                var1 += var6;
            }
        }
        return var1;
    }
    
    protected void ÂµÈ(final float p_70675_1_) {
    }
    
    protected float Ý(final DamageSource p_70655_1_, float p_70655_2_) {
        if (!p_70655_1_.Âµá€()) {
            final int var3 = 25 - this.áŒŠÉ();
            final float var4 = p_70655_2_ * var3;
            this.ÂµÈ(p_70655_2_);
            p_70655_2_ = var4 / 25.0f;
        }
        return p_70655_2_;
    }
    
    protected float Ø­áŒŠá(final DamageSource p_70672_1_, float p_70672_2_) {
        if (p_70672_1_.Ø()) {
            return p_70672_2_;
        }
        if (this.HorizonCode_Horizon_È(Potion.ˆÏ­) && p_70672_1_ != DamageSource.áˆºÑ¢Õ) {
            final int var3 = (this.Â(Potion.ˆÏ­).Ý() + 1) * 5;
            final int var4 = 25 - var3;
            final float var5 = p_70672_2_ * var4;
            p_70672_2_ = var5 / 25.0f;
        }
        if (p_70672_2_ <= 0.0f) {
            return 0.0f;
        }
        int var3 = EnchantmentHelper.HorizonCode_Horizon_È(this.Ðƒá(), p_70672_1_);
        if (var3 > 20) {
            var3 = 20;
        }
        if (var3 > 0 && var3 <= 20) {
            final int var4 = 25 - var3;
            final float var5 = p_70672_2_ * var4;
            p_70672_2_ = var5 / 25.0f;
        }
        return p_70672_2_;
    }
    
    protected void Â(final DamageSource p_70665_1_, float p_70665_2_) {
        if (!this.HorizonCode_Horizon_È(p_70665_1_)) {
            p_70665_2_ = this.Ý(p_70665_1_, p_70665_2_);
            final float var3;
            p_70665_2_ = (var3 = this.Ø­áŒŠá(p_70665_1_, p_70665_2_));
            p_70665_2_ = Math.max(p_70665_2_ - this.Ñ¢È(), 0.0f);
            this.ˆÏ­(this.Ñ¢È() - (var3 - p_70665_2_));
            if (p_70665_2_ != 0.0f) {
                final float var4 = this.Ï­Ä();
                this.áˆºÑ¢Õ(var4 - p_70665_2_);
                this.ÇŽØ().HorizonCode_Horizon_È(p_70665_1_, var4, p_70665_2_);
                this.ˆÏ­(this.Ñ¢È() - p_70665_2_);
            }
        }
    }
    
    public CombatTracker ÇŽØ() {
        return this.Ø­áŒŠá;
    }
    
    public EntityLivingBase ŒÓ() {
        return (this.Ø­áŒŠá.Ý() != null) ? this.Ø­áŒŠá.Ý() : ((this.Ñ¢Ó != null) ? this.Ñ¢Ó : ((this.áˆºÑ¢Õ != null) ? this.áˆºÑ¢Õ : null));
    }
    
    public final float ÇŽÊ() {
        return (float)this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).Âµá€();
    }
    
    public final int µ() {
        return this.£Ó.HorizonCode_Horizon_È(9);
    }
    
    public final void µà(final int p_85034_1_) {
        this.£Ó.Â(9, (byte)p_85034_1_);
    }
    
    private int Ø() {
        return this.HorizonCode_Horizon_È(Potion.Âµá€) ? (6 - (1 + this.Â(Potion.Âµá€).Ý()) * 1) : (this.HorizonCode_Horizon_È(Potion.Ó) ? (6 + (1 + this.Â(Potion.Ó).Ý()) * 2) : 6);
    }
    
    public void b_() {
        if (!this.£á || this.Å >= this.Ø() / 2 || this.Å < 0) {
            this.Å = -1;
            this.£á = true;
            if (this.Ï­Ðƒà instanceof WorldServer) {
                ((WorldServer)this.Ï­Ðƒà).ÇŽá€().HorizonCode_Horizon_È(this, new S0BPacketAnimation(this, 0));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 2) {
            this.áŒŠá€ = 1.5f;
            this.ˆÉ = this.ˆà¢;
            final int n = 10;
            this.ÇŽá = n;
            this.µà = n;
            this.Ñ¢à = 0.0f;
            final String var2 = this.¥áŠ();
            if (var2 != null) {
                this.HorizonCode_Horizon_È(this.¥áŠ(), this.ˆÂ(), (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
            }
            this.HorizonCode_Horizon_È(DamageSource.ÂµÈ, 0.0f);
        }
        else if (p_70103_1_ == 3) {
            final String var2 = this.µÊ();
            if (var2 != null) {
                this.HorizonCode_Horizon_È(this.µÊ(), this.ˆÂ(), (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
            }
            this.áˆºÑ¢Õ(0.0f);
            this.Â(DamageSource.ÂµÈ);
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    protected void Âµà() {
        this.HorizonCode_Horizon_È(DamageSource.áˆºÑ¢Õ, 4.0f);
    }
    
    protected void µÏ() {
        final int var1 = this.Ø();
        if (this.£á) {
            ++this.Å;
            if (this.Å >= var1) {
                this.Å = 0;
                this.£á = false;
            }
        }
        else {
            this.Å = 0;
        }
        this.áˆº = this.Å / var1;
    }
    
    public IAttributeInstance HorizonCode_Horizon_È(final IAttribute p_110148_1_) {
        return this.µÐƒÓ().HorizonCode_Horizon_È(p_110148_1_);
    }
    
    public BaseAttributeMap µÐƒÓ() {
        if (this.Ý == null) {
            this.Ý = new ServersideAttributeMap();
        }
        return this.Ý;
    }
    
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.HorizonCode_Horizon_È;
    }
    
    public abstract ItemStack Çª();
    
    public abstract ItemStack Ý(final int p0);
    
    public abstract ItemStack ÂµÈ(final int p0);
    
    @Override
    public abstract void HorizonCode_Horizon_È(final int p0, final ItemStack p1);
    
    @Override
    public void Â(final boolean sprinting) {
        super.Â(sprinting);
        final IAttributeInstance var2 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá);
        if (var2.HorizonCode_Horizon_È(EntityLivingBase.HorizonCode_Horizon_È) != null) {
            var2.Ý(EntityLivingBase.Â);
        }
        if (sprinting) {
            var2.Â(EntityLivingBase.Â);
        }
    }
    
    @Override
    public abstract ItemStack[] Ðƒá();
    
    protected float ˆÂ() {
        return 1.0f;
    }
    
    protected float áŒŠÈ() {
        return this.h_() ? ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.5f) : ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
    }
    
    protected boolean ˆØ­áˆº() {
        return this.Ï­Ä() <= 0.0f;
    }
    
    public void £á(final Entity p_110145_1_) {
        double var3 = p_110145_1_.ŒÏ;
        double var4 = p_110145_1_.£É().Â + p_110145_1_.£ÂµÄ;
        double var5 = p_110145_1_.Ê;
        final byte var6 = 1;
        for (int var7 = -var6; var7 <= var6; ++var7) {
            for (int var8 = -var6; var8 < var6; ++var8) {
                if (var7 != 0 || var8 != 0) {
                    final int var9 = (int)(this.ŒÏ + var7);
                    final int var10 = (int)(this.Ê + var8);
                    final AxisAlignedBB var11 = this.£É().Ý(var7, 1.0, var8);
                    if (this.Ï­Ðƒà.HorizonCode_Horizon_È(var11).isEmpty()) {
                        if (World.HorizonCode_Horizon_È(this.Ï­Ðƒà, new BlockPos(var9, (int)this.Çªà¢, var10))) {
                            this.áˆºÑ¢Õ(this.ŒÏ + var7, this.Çªà¢ + 1.0, this.Ê + var8);
                            return;
                        }
                        if (World.HorizonCode_Horizon_È(this.Ï­Ðƒà, new BlockPos(var9, (int)this.Çªà¢ - 1, var10)) || this.Ï­Ðƒà.Â(new BlockPos(var9, (int)this.Çªà¢ - 1, var10)).Ý().Ó() == Material.Ø) {
                            var3 = this.ŒÏ + var7;
                            var4 = this.Çªà¢ + 1.0;
                            var5 = this.Ê + var8;
                        }
                    }
                }
            }
        }
        this.áˆºÑ¢Õ(var3, var4, var5);
    }
    
    @Override
    public boolean ¥Ï() {
        return false;
    }
    
    protected float £Ô() {
        return 0.42f;
    }
    
    protected void ŠÏ() {
        this.ˆá = this.£Ô();
        if (this.HorizonCode_Horizon_È(Potion.áˆºÑ¢Õ)) {
            this.ˆá += (this.Â(Potion.áˆºÑ¢Õ).Ý() + 1) * 0.1f;
        }
        if (this.ÇªÂµÕ()) {
            final float var1 = this.É * 0.017453292f;
            this.ÇŽÉ -= MathHelper.HorizonCode_Horizon_È(var1) * 0.2f;
            this.ÇŽÕ += MathHelper.Â(var1) * 0.2f;
        }
        this.áŒŠÏ = true;
    }
    
    protected void ˆ() {
        this.ˆá += 0.03999999910593033;
    }
    
    protected void ŠÑ¢Ó() {
        this.ˆá += 0.03999999910593033;
    }
    
    public void Ó(final float p_70612_1_, final float p_70612_2_) {
        if (this.ŠÄ()) {
            if (this.£ÂµÄ() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).áˆºáˆºáŠ.Â)) {
                final double var8 = this.Çªà¢;
                float var9 = 0.8f;
                float var10 = 0.02f;
                float var11 = EnchantmentHelper.Â((Entity)this);
                if (var11 > 3.0f) {
                    var11 = 3.0f;
                }
                if (!this.ŠÂµà) {
                    var11 *= 0.5f;
                }
                if (var11 > 0.0f) {
                    var9 += (0.54600006f - var9) * var11 / 3.0f;
                    var10 += (this.áˆºá() * 1.0f - var10) * var11 / 3.0f;
                }
                this.Â(p_70612_1_, p_70612_2_, var10);
                this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
                this.ÇŽÉ *= var9;
                this.ˆá *= 0.800000011920929;
                this.ÇŽÕ *= var9;
                this.ˆá -= 0.02;
                if (this.¥à && this.Ø­áŒŠá(this.ÇŽÉ, this.ˆá + 0.6000000238418579 - this.Çªà¢ + var8, this.ÇŽÕ)) {
                    this.ˆá = 0.30000001192092896;
                }
            }
            else if (this.ÇŽá€() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).áˆºáˆºáŠ.Â)) {
                final double var8 = this.Çªà¢;
                this.Â(p_70612_1_, p_70612_2_, 0.02f);
                this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
                this.ÇŽÉ *= 0.5;
                this.ˆá *= 0.5;
                this.ÇŽÕ *= 0.5;
                this.ˆá -= 0.02;
                if (this.¥à && this.Ø­áŒŠá(this.ÇŽÉ, this.ˆá + 0.6000000238418579 - this.Çªà¢ + var8, this.ÇŽÕ)) {
                    this.ˆá = 0.30000001192092896;
                }
            }
            else {
                float var12 = 0.91f;
                if (this.ŠÂµà) {
                    var12 = this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.£É().Â) - 1, MathHelper.Ý(this.Ê))).Ý().áƒ * 0.91f;
                }
                final float var13 = 0.16277136f / (var12 * var12 * var12);
                float var9;
                if (this.ŠÂµà) {
                    var9 = this.áˆºá() * var13;
                }
                else {
                    var9 = this.Ø­Ñ¢á€;
                }
                this.Â(p_70612_1_, p_70612_2_, var9);
                var12 = 0.91f;
                if (this.ŠÂµà) {
                    var12 = this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.£É().Â) - 1, MathHelper.Ý(this.Ê))).Ý().áƒ * 0.91f;
                }
                if (this.i_()) {
                    final float var10 = 0.15f;
                    this.ÇŽÉ = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ, -var10, var10);
                    this.ÇŽÕ = MathHelper.HorizonCode_Horizon_È(this.ÇŽÕ, -var10, var10);
                    this.Ï­à = 0.0f;
                    if (this.ˆá < -0.15) {
                        this.ˆá = -0.15;
                    }
                    final boolean var14 = this.Çªà¢() && this instanceof EntityPlayer;
                    if (var14 && this.ˆá < 0.0) {
                        this.ˆá = 0.0;
                    }
                }
                this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
                if (this.¥à && this.i_()) {
                    this.ˆá = 0.2;
                }
                if (this.Ï­Ðƒà.ŠÄ && (!this.Ï­Ðƒà.Ó(new BlockPos((int)this.ŒÏ, 0, (int)this.Ê)) || !this.Ï­Ðƒà.à(new BlockPos((int)this.ŒÏ, 0, (int)this.Ê)).Å())) {
                    if (this.Çªà¢ > 0.0) {
                        this.ˆá = -0.1;
                    }
                    else {
                        this.ˆá = 0.0;
                    }
                }
                else {
                    this.ˆá -= 0.08;
                }
                this.ˆá *= 0.9800000190734863;
                this.ÇŽÉ *= var12;
                this.ÇŽÕ *= var12;
            }
        }
        this.Šà = this.áŒŠá€;
        final double var8 = this.ŒÏ - this.áŒŠà;
        final double var15 = this.Ê - this.Ñ¢á;
        float var11 = MathHelper.HorizonCode_Horizon_È(var8 * var8 + var15 * var15) * 4.0f;
        if (var11 > 1.0f) {
            var11 = 1.0f;
        }
        this.áŒŠá€ += (var11 - this.áŒŠá€) * 0.4f;
        this.¥Ï += this.áŒŠá€;
    }
    
    public float áˆºá() {
        return this.ŠáˆºÂ;
    }
    
    public void áŒŠÆ(final float p_70659_1_) {
        this.ŠáˆºÂ = p_70659_1_;
    }
    
    public boolean Å(final Entity p_70652_1_) {
        this.ˆÏ­(p_70652_1_);
        return false;
    }
    
    public boolean Ï­Ó() {
        return false;
    }
    
    @Override
    public void á() {
        super.á();
        if (!this.Ï­Ðƒà.ŠÄ) {
            final int var1 = this.µ();
            if (var1 > 0) {
                if (this.£à <= 0) {
                    this.£à = 20 * (30 - var1);
                }
                --this.£à;
                if (this.£à <= 0) {
                    this.µà(var1 - 1);
                }
            }
            for (int var2 = 0; var2 < 5; ++var2) {
                final ItemStack var3 = this.Ø[var2];
                final ItemStack var4 = this.Ý(var2);
                if (!ItemStack.Â(var4, var3)) {
                    ((WorldServer)this.Ï­Ðƒà).ÇŽá€().HorizonCode_Horizon_È(this, new S04PacketEntityEquipment(this.ˆá(), var2, var4));
                    if (var3 != null) {
                        this.Ý.HorizonCode_Horizon_È(var3.ŒÏ());
                    }
                    if (var4 != null) {
                        this.Ý.Â(var4.ŒÏ());
                    }
                    this.Ø[var2] = ((var4 == null) ? null : var4.áˆºÑ¢Õ());
                }
            }
            if (this.Œ % 20 == 0) {
                this.ÇŽØ().Âµá€();
            }
        }
        this.ˆÏ­();
        final double var5 = this.ŒÏ - this.áŒŠà;
        final double var6 = this.Ê - this.Ñ¢á;
        final float var7 = (float)(var5 * var5 + var6 * var6);
        float var8 = this.¥É;
        float var9 = 0.0f;
        this.£Ø­à = this.µÐƒáƒ;
        float var10 = 0.0f;
        if (var7 > 0.0025000002f) {
            var10 = 1.0f;
            var9 = (float)Math.sqrt(var7) * 3.0f;
            var8 = (float)Math.atan2(var6, var5) * 180.0f / 3.1415927f - 90.0f;
        }
        if (this.áˆº > 0.0f) {
            var8 = this.É;
        }
        if (!this.ŠÂµà) {
            var10 = 0.0f;
        }
        this.µÐƒáƒ += (var10 - this.µÐƒáƒ) * 0.3f;
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("headTurn");
        var9 = this.à(var8, var9);
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("rangeChecks");
        while (this.É - this.á€ < -180.0f) {
            this.á€ -= 360.0f;
        }
        while (this.É - this.á€ >= 180.0f) {
            this.á€ += 360.0f;
        }
        while (this.¥É - this.£ÇªÓ < -180.0f) {
            this.£ÇªÓ -= 360.0f;
        }
        while (this.¥É - this.£ÇªÓ >= 180.0f) {
            this.£ÇªÓ += 360.0f;
        }
        while (this.áƒ - this.Õ < -180.0f) {
            this.Õ -= 360.0f;
        }
        while (this.áƒ - this.Õ >= 180.0f) {
            this.Õ += 360.0f;
        }
        while (this.ÂµÕ - this.Š < -180.0f) {
            this.Š -= 360.0f;
        }
        while (this.ÂµÕ - this.Š >= 180.0f) {
            this.Š += 360.0f;
        }
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.áŒŠÕ += var9;
    }
    
    protected float à(final float p_110146_1_, float p_110146_2_) {
        final float var3 = MathHelper.à(p_110146_1_ - this.¥É);
        this.¥É += var3 * 0.3f;
        float var4 = MathHelper.à(this.É - this.¥É);
        final boolean var5 = var4 < -90.0f || var4 >= 90.0f;
        if (var4 < -75.0f) {
            var4 = -75.0f;
        }
        if (var4 >= 75.0f) {
            var4 = 75.0f;
        }
        this.¥É = this.É - var4;
        if (var4 * var4 > 2500.0f) {
            this.¥É += var4 * 0.2f;
        }
        if (var5) {
            p_110146_2_ *= -1.0f;
        }
        return p_110146_2_;
    }
    
    public void ˆÏ­() {
        if (this.Ø­Ñ¢Ï­Ø­áˆº > 0) {
            --this.Ø­Ñ¢Ï­Ø­áˆº;
        }
        if (this.ÇŽÄ > 0) {
            final double var1 = this.ŒÏ + (this.ˆÈ - this.ŒÏ) / this.ÇŽÄ;
            final double var2 = this.Çªà¢ + (this.ˆÅ - this.Çªà¢) / this.ÇŽÄ;
            final double var3 = this.Ê + (this.ÇªÉ - this.Ê) / this.ÇŽÄ;
            final double var4 = MathHelper.à(this.ŠÏ­áˆºá - this.É);
            this.É += (float)(var4 / this.ÇŽÄ);
            this.áƒ += (float)((this.ÇŽà - this.áƒ) / this.ÇŽÄ);
            --this.ÇŽÄ;
            this.Ý(var1, var2, var3);
            this.Â(this.É, this.áƒ);
        }
        else if (!this.ŠÄ()) {
            this.ÇŽÉ *= 0.98;
            this.ˆá *= 0.98;
            this.ÇŽÕ *= 0.98;
        }
        if (Math.abs(this.ÇŽÉ) < 0.005) {
            this.ÇŽÉ = 0.0;
        }
        if (Math.abs(this.ˆá) < 0.005) {
            this.ˆá = 0.0;
        }
        if (Math.abs(this.ÇŽÕ) < 0.005) {
            this.ÇŽÕ = 0.0;
        }
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("ai");
        if (this.ˆØ­áˆº()) {
            this.ÐƒÂ = false;
            this.£áƒ = 0.0f;
            this.Ï­áˆºÓ = 0.0f;
            this.Çª = 0.0f;
        }
        else if (this.ŠÄ()) {
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("newAi");
            this.Ê();
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
        }
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("jump");
        if (this.ÐƒÂ) {
            if (this.£ÂµÄ()) {
                this.ˆ();
            }
            else if (this.ÇŽá€()) {
                this.ŠÑ¢Ó();
            }
            else if (this.ŠÂµà && this.Ø­Ñ¢Ï­Ø­áˆº == 0) {
                this.ŠÏ();
                this.Ø­Ñ¢Ï­Ø­áˆº = 10;
            }
        }
        else {
            this.Ø­Ñ¢Ï­Ø­áˆº = 0;
        }
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("travel");
        this.£áƒ *= 0.98f;
        this.Ï­áˆºÓ *= 0.98f;
        this.Çª *= 0.9f;
        this.Ó(this.£áƒ, this.Ï­áˆºÓ);
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("push");
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.ŠáŒŠà¢();
        }
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
    }
    
    protected void Ê() {
    }
    
    protected void ŠáŒŠà¢() {
        final List var1 = this.Ï­Ðƒà.Â(this, this.£É().Â(0.20000000298023224, 0.0, 0.20000000298023224));
        if (var1 != null && !var1.isEmpty()) {
            for (int var2 = 0; var2 < var1.size(); ++var2) {
                final Entity var3 = var1.get(var2);
                if (var3.£à()) {
                    this.£à(var3);
                }
            }
        }
    }
    
    protected void £à(final Entity p_82167_1_) {
        p_82167_1_.Ó(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        if (this.Æ != null && entityIn == null) {
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.£á(this.Æ);
            }
            if (this.Æ != null) {
                this.Æ.µÕ = null;
            }
            this.Æ = null;
        }
        else {
            super.HorizonCode_Horizon_È(entityIn);
        }
    }
    
    @Override
    public void Ø­á() {
        super.Ø­á();
        this.£Ø­à = this.µÐƒáƒ;
        this.µÐƒáƒ = 0.0f;
        this.Ï­à = 0.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        this.ˆÈ = p_180426_1_;
        this.ˆÅ = p_180426_3_;
        this.ÇªÉ = p_180426_5_;
        this.ŠÏ­áˆºá = p_180426_7_;
        this.ÇŽà = p_180426_8_;
        this.ÇŽÄ = p_180426_9_;
    }
    
    public void ÂµÈ(final boolean p_70637_1_) {
        this.ÐƒÂ = p_70637_1_;
    }
    
    public void Â(final Entity p_71001_1_, final int p_71001_2_) {
        if (!p_71001_1_.ˆáŠ && !this.Ï­Ðƒà.ŠÄ) {
            final EntityTracker var3 = ((WorldServer)this.Ï­Ðƒà).ÇŽá€();
            if (p_71001_1_ instanceof EntityItem) {
                var3.HorizonCode_Horizon_È(p_71001_1_, new S0DPacketCollectItem(p_71001_1_.ˆá(), this.ˆá()));
            }
            if (p_71001_1_ instanceof EntityArrow) {
                var3.HorizonCode_Horizon_È(p_71001_1_, new S0DPacketCollectItem(p_71001_1_.ˆá(), this.ˆá()));
            }
            if (p_71001_1_ instanceof EntityXPOrb) {
                var3.HorizonCode_Horizon_È(p_71001_1_, new S0DPacketCollectItem(p_71001_1_.ˆá(), this.ˆá()));
            }
        }
    }
    
    public boolean µà(final Entity p_70685_1_) {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(new Vec3(this.ŒÏ, this.Çªà¢ + this.Ðƒáƒ(), this.Ê), new Vec3(p_70685_1_.ŒÏ, p_70685_1_.Çªà¢ + p_70685_1_.Ðƒáƒ(), p_70685_1_.Ê)) == null;
    }
    
    @Override
    public Vec3 ˆÐƒØ­à() {
        return this.Ó(1.0f);
    }
    
    @Override
    public Vec3 Ó(final float p_70676_1_) {
        if (p_70676_1_ == 1.0f) {
            return this.Âµá€(this.áƒ, this.ÂµÕ);
        }
        final float var2 = this.Õ + (this.áƒ - this.Õ) * p_70676_1_;
        final float var3 = this.Š + (this.ÂµÕ - this.Š) * p_70676_1_;
        return this.Âµá€(var2, var3);
    }
    
    public float á(final float p_70678_1_) {
        float var2 = this.áˆº - this.£áŒŠá;
        if (var2 < 0.0f) {
            ++var2;
        }
        return this.£áŒŠá + var2 * p_70678_1_;
    }
    
    public boolean ŠÄ() {
        return !this.Ï­Ðƒà.ŠÄ;
    }
    
    @Override
    public boolean Ô() {
        return !this.ˆáŠ;
    }
    
    @Override
    public boolean £à() {
        return !this.ˆáŠ;
    }
    
    @Override
    protected void Ï() {
        this.È = (this.ˆáƒ.nextDouble() >= this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ý).Âµá€());
    }
    
    @Override
    public float Û() {
        return this.ÂµÕ;
    }
    
    @Override
    public void Ø(final float rotation) {
        this.ÂµÕ = rotation;
    }
    
    public float Ñ¢È() {
        return this.ŒÂ;
    }
    
    public void ˆÏ­(float p_110149_1_) {
        if (p_110149_1_ < 0.0f) {
            p_110149_1_ = 0.0f;
        }
        this.ŒÂ = p_110149_1_;
    }
    
    public Team Çªáˆºá() {
        return this.Ï­Ðƒà.à¢().Ó(this.£áŒŠá().toString());
    }
    
    public boolean Ø­áŒŠá(final EntityLivingBase p_142014_1_) {
        return this.HorizonCode_Horizon_È(p_142014_1_.Çªáˆºá());
    }
    
    public boolean HorizonCode_Horizon_È(final Team p_142012_1_) {
        return this.Çªáˆºá() != null && this.Çªáˆºá().HorizonCode_Horizon_È(p_142012_1_);
    }
    
    public void ˆÕ() {
    }
    
    public void ÇªÈ() {
    }
    
    protected void ÇªÅ() {
        this.áŒŠÆ = true;
    }
}
