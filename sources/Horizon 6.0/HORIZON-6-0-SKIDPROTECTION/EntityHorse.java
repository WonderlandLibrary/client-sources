package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;
import com.google.common.base.Predicate;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
    private static final Predicate ¥Å;
    private static final IAttribute Œáƒ;
    private static final String[] Œá;
    private static final String[] µÂ;
    private static final int[] Ñ¢ÇŽÏ;
    private static final String[] ÇªÂ;
    private static final String[] ÂµáˆºÂ;
    private static final String[] ¥Âµá€;
    private static final String[] ÇŽÈ;
    private int ÇªáˆºÕ;
    private int Ï­Ä;
    private int ¥áŠ;
    public int ŒÂ;
    public int Ï­Ï;
    protected boolean ŠØ;
    private AnimalChest µÊ;
    private boolean áˆºáˆºáŠ;
    protected int ˆÐƒØ;
    protected float Çªà;
    private boolean áŒŠÉ;
    private float ÇŽØ;
    private float ŒÓ;
    private float ÇŽÊ;
    private float µ;
    private float µÏ;
    private float µÐƒÓ;
    private int ¥áŒŠà;
    private String ˆÂ;
    private String[] áŒŠÈ;
    private boolean ˆØ­áˆº;
    private static final String £Ô = "CL_00001641";
    
    static {
        ¥Å = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00001642";
            
            public boolean HorizonCode_Horizon_È(final Entity p_179873_1_) {
                return p_179873_1_ instanceof EntityHorse && ((EntityHorse)p_179873_1_).¥ÇªÅ();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
        Œáƒ = new RangedAttribute(null, "horse.jumpStrength", 0.7, 0.0, 2.0).HorizonCode_Horizon_È("Jump Strength").HorizonCode_Horizon_È(true);
        Œá = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
        µÂ = new String[] { "", "meo", "goo", "dio" };
        Ñ¢ÇŽÏ = new int[] { 0, 5, 7, 11 };
        ÇªÂ = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
        ÂµáˆºÂ = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
        ¥Âµá€ = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
        ÇŽÈ = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
    }
    
    public EntityHorse(final World worldIn) {
        super(worldIn);
        this.áŒŠÈ = new String[3];
        this.ˆØ­áˆº = false;
        this.HorizonCode_Horizon_È(1.4f, 1.6f);
        this.Å(this.£Â = false);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIPanic(this, 1.2));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIMate(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIFollowParent(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWander(this, 0.7));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.Âµáƒ();
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)0);
        this.£Ó.HorizonCode_Horizon_È(19, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(20, (Object)0);
        this.£Ó.HorizonCode_Horizon_È(21, String.valueOf(""));
        this.£Ó.HorizonCode_Horizon_È(22, (Object)0);
    }
    
    public void ˆà(final int p_110214_1_) {
        this.£Ó.Â(19, (byte)p_110214_1_);
        this.ŒÇ();
    }
    
    public int ÐƒÇŽà() {
        return this.£Ó.HorizonCode_Horizon_È(19);
    }
    
    public void ¥Æ(final int p_110235_1_) {
        this.£Ó.Â(20, p_110235_1_);
        this.ŒÇ();
    }
    
    public int ¥Ê() {
        return this.£Ó.Ý(20);
    }
    
    @Override
    public String v_() {
        if (this.j_()) {
            return this.Šà();
        }
        final int var1 = this.ÐƒÇŽà();
        switch (var1) {
            default: {
                return StatCollector.HorizonCode_Horizon_È("entity.horse.name");
            }
            case 1: {
                return StatCollector.HorizonCode_Horizon_È("entity.donkey.name");
            }
            case 2: {
                return StatCollector.HorizonCode_Horizon_È("entity.mule.name");
            }
            case 3: {
                return StatCollector.HorizonCode_Horizon_È("entity.zombiehorse.name");
            }
            case 4: {
                return StatCollector.HorizonCode_Horizon_È("entity.skeletonhorse.name");
            }
        }
    }
    
    private boolean Šáƒ(final int p_110233_1_) {
        return (this.£Ó.Ý(16) & p_110233_1_) != 0x0;
    }
    
    private void Ý(final int p_110208_1_, final boolean p_110208_2_) {
        final int var3 = this.£Ó.Ý(16);
        if (p_110208_2_) {
            this.£Ó.Â(16, var3 | p_110208_1_);
        }
        else {
            this.£Ó.Â(16, var3 & ~p_110208_1_);
        }
    }
    
    public boolean ÐƒÓ() {
        return !this.h_();
    }
    
    public boolean áˆºÕ() {
        return this.Šáƒ(2);
    }
    
    public boolean ŒÐƒà() {
        return this.ÐƒÓ();
    }
    
    public String ÐƒáˆºÄ() {
        return this.£Ó.Âµá€(21);
    }
    
    public void HorizonCode_Horizon_È(final String p_152120_1_) {
        this.£Ó.Â(21, p_152120_1_);
    }
    
    public float áˆºÉ() {
        final int var1 = this.à();
        return (var1 >= 0) ? 1.0f : (0.5f + (-24000 - var1) / -24000.0f * 0.5f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean p_98054_1_) {
        if (p_98054_1_) {
            this.Ý(this.áˆºÉ());
        }
        else {
            this.Ý(1.0f);
        }
    }
    
    public boolean Ø­È() {
        return this.ŠØ;
    }
    
    public void á(final boolean p_110234_1_) {
        this.Ý(2, p_110234_1_);
    }
    
    public void ˆÏ­(final boolean p_110255_1_) {
        this.ŠØ = p_110255_1_;
    }
    
    @Override
    public boolean ŠÏ­áˆºá() {
        return !this.Ï­áŠ() && super.ŠÏ­áˆºá();
    }
    
    @Override
    protected void Ø­áŒŠá(final float p_142017_1_) {
        if (p_142017_1_ > 6.0f && this.áŒŠÓ()) {
            this.ˆà(false);
        }
    }
    
    public boolean Ñ¢Õ() {
        return this.Šáƒ(8);
    }
    
    public int Ø­à¢() {
        return this.£Ó.Ý(22);
    }
    
    private int Ó(final ItemStack p_110260_1_) {
        if (p_110260_1_ == null) {
            return 0;
        }
        final Item_1028566121 var2 = p_110260_1_.HorizonCode_Horizon_È();
        return (var2 == Items.ÐƒÇŽà) ? 1 : ((var2 == Items.¥Ê) ? 2 : ((var2 == Items.ÐƒÓ) ? 3 : 0));
    }
    
    public boolean áŒŠÓ() {
        return this.Šáƒ(32);
    }
    
    public boolean Ø­Â() {
        return this.Šáƒ(64);
    }
    
    public boolean ¥ÇªÅ() {
        return this.Šáƒ(16);
    }
    
    public boolean áˆºÓ() {
        return this.áˆºáˆºáŠ;
    }
    
    public void Âµá€(final ItemStack p_146086_1_) {
        this.£Ó.Â(22, this.Ó(p_146086_1_));
        this.ŒÇ();
    }
    
    public void £á(final boolean p_110242_1_) {
        this.Ý(16, p_110242_1_);
    }
    
    public void Å(final boolean p_110207_1_) {
        this.Ý(8, p_110207_1_);
    }
    
    public void £à(final boolean p_110221_1_) {
        this.áˆºáˆºáŠ = p_110221_1_;
    }
    
    public void µà(final boolean p_110251_1_) {
        this.Ý(4, p_110251_1_);
    }
    
    public int ÂµÊ() {
        return this.ˆÐƒØ;
    }
    
    public void Ø­à(final int p_110238_1_) {
        this.ˆÐƒØ = p_110238_1_;
    }
    
    public int µÕ(final int p_110198_1_) {
        final int var2 = MathHelper.HorizonCode_Horizon_È(this.ÂµÊ() + p_110198_1_, 0, this.£Ç());
        this.Ø­à(var2);
        return var2;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        final Entity var3 = source.áˆºÑ¢Õ();
        return (this.µÕ == null || !this.µÕ.equals(var3)) && super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public int áŒŠÉ() {
        return EntityHorse.Ñ¢ÇŽÏ[this.Ø­à¢()];
    }
    
    @Override
    public boolean £à() {
        return this.µÕ == null;
    }
    
    public boolean áˆºÂ() {
        final int var1 = MathHelper.Ý(this.ŒÏ);
        final int var2 = MathHelper.Ý(this.Ê);
        this.Ï­Ðƒà.Ý(new BlockPos(var1, 0, var2));
        return true;
    }
    
    public void Ø­() {
        if (!this.Ï­Ðƒà.ŠÄ && this.Ñ¢Õ()) {
            this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆáƒ), 1);
            this.Å(false);
        }
    }
    
    private void ÇªØ() {
        this.Ø­Ï­à();
        if (!this.áŠ()) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, "eating", 1.0f, 1.0f + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f);
        }
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        if (distance > 1.0f) {
            this.HorizonCode_Horizon_È("mob.horse.land", 0.4f, 1.0f);
        }
        final int var3 = MathHelper.Ó((distance * 0.5f - 3.0f) * damageMultiplier);
        if (var3 > 0) {
            this.HorizonCode_Horizon_È(DamageSource.áŒŠÆ, var3);
            if (this.µÕ != null) {
                this.µÕ.HorizonCode_Horizon_È(DamageSource.áŒŠÆ, var3);
            }
            final Block var4 = this.Ï­Ðƒà.Â(new BlockPos(this.ŒÏ, this.Çªà¢ - 0.2 - this.á€, this.Ê)).Ý();
            if (var4.Ó() != Material.HorizonCode_Horizon_È && !this.áŠ()) {
                final Block.Â var5 = var4.ˆá;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this, var5.Ý(), var5.Ø­áŒŠá() * 0.5f, var5.Âµá€() * 0.75f);
            }
        }
    }
    
    private int µØ() {
        final int var1 = this.ÐƒÇŽà();
        return (this.Ñ¢Õ() && (var1 == 1 || var1 == 2)) ? 17 : 2;
    }
    
    private void Âµáƒ() {
        final AnimalChest var1 = this.µÊ;
        (this.µÊ = new AnimalChest("HorseChest", this.µØ())).HorizonCode_Horizon_È(this.v_());
        if (var1 != null) {
            var1.Â(this);
            for (int var2 = Math.min(var1.áŒŠÆ(), this.µÊ.áŒŠÆ()), var3 = 0; var3 < var2; ++var3) {
                final ItemStack var4 = var1.á(var3);
                if (var4 != null) {
                    this.µÊ.Ý(var3, var4.áˆºÑ¢Õ());
                }
            }
        }
        this.µÊ.HorizonCode_Horizon_È(this);
        this.ÇªÊ();
    }
    
    private void ÇªÊ() {
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.µà(this.µÊ.á(0) != null);
            if (this.ÇŽáˆºÈ()) {
                this.Âµá€(this.µÊ.á(1));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final InventoryBasic p_76316_1_) {
        final int var2 = this.Ø­à¢();
        final boolean var3 = this.ŠÂµÏ();
        this.ÇªÊ();
        if (this.Œ > 20) {
            if (var2 == 0 && var2 != this.Ø­à¢()) {
                this.HorizonCode_Horizon_È("mob.horse.armor", 0.5f, 1.0f);
            }
            else if (var2 != this.Ø­à¢()) {
                this.HorizonCode_Horizon_È("mob.horse.armor", 0.5f, 1.0f);
            }
            if (!var3 && this.ŠÂµÏ()) {
                this.HorizonCode_Horizon_È("mob.horse.leather", 0.5f, 1.0f);
            }
        }
    }
    
    @Override
    public boolean µà() {
        this.áˆºÂ();
        return super.µà();
    }
    
    protected EntityHorse HorizonCode_Horizon_È(final Entity p_110250_1_, final double p_110250_2_) {
        double var4 = Double.MAX_VALUE;
        Entity var5 = null;
        final List var6 = this.Ï­Ðƒà.HorizonCode_Horizon_È(p_110250_1_, p_110250_1_.£É().HorizonCode_Horizon_È(p_110250_2_, p_110250_2_, p_110250_2_), EntityHorse.¥Å);
        for (final Entity var8 : var6) {
            final double var9 = var8.Âµá€(p_110250_1_.ŒÏ, p_110250_1_.Çªà¢, p_110250_1_.Ê);
            if (var9 < var4) {
                var5 = var8;
                var4 = var9;
            }
        }
        return (EntityHorse)var5;
    }
    
    public double ÐƒÉ() {
        return this.HorizonCode_Horizon_È(EntityHorse.Œáƒ).Âµá€();
    }
    
    @Override
    protected String µÊ() {
        this.Ø­Ï­à();
        final int var1 = this.ÐƒÇŽà();
        return (var1 == 3) ? "mob.horse.zombie.death" : ((var1 == 4) ? "mob.horse.skeleton.death" : ((var1 != 1 && var1 != 2) ? "mob.horse.death" : "mob.horse.donkey.death"));
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        final boolean var1 = this.ˆáƒ.nextInt(4) == 0;
        final int var2 = this.ÐƒÇŽà();
        return (var2 == 4) ? Items.ŠÕ : ((var2 == 3) ? (var1 ? null : Items.ŠØ) : Items.£áŒŠá);
    }
    
    @Override
    protected String ¥áŠ() {
        this.Ø­Ï­à();
        if (this.ˆáƒ.nextInt(3) == 0) {
            this.µá();
        }
        final int var1 = this.ÐƒÇŽà();
        return (var1 == 3) ? "mob.horse.zombie.hit" : ((var1 == 4) ? "mob.horse.skeleton.hit" : ((var1 != 1 && var1 != 2) ? "mob.horse.hit" : "mob.horse.donkey.hit"));
    }
    
    public boolean ŠÂµÏ() {
        return this.Šáƒ(4);
    }
    
    @Override
    protected String µÐƒáƒ() {
        this.Ø­Ï­à();
        if (this.ˆáƒ.nextInt(10) == 0 && !this.ˆØ­áˆº()) {
            this.µá();
        }
        final int var1 = this.ÐƒÇŽà();
        return (var1 == 3) ? "mob.horse.zombie.idle" : ((var1 == 4) ? "mob.horse.skeleton.idle" : ((var1 != 1 && var1 != 2) ? "mob.horse.idle" : "mob.horse.donkey.idle"));
    }
    
    protected String Ðƒ() {
        this.Ø­Ï­à();
        this.µá();
        final int var1 = this.ÐƒÇŽà();
        return (var1 != 3 && var1 != 4) ? ((var1 != 1 && var1 != 2) ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        Block.Â var3 = p_180429_2_.ˆá;
        if (this.Ï­Ðƒà.Â(p_180429_1_.Ø­áŒŠá()).Ý() == Blocks.áŒŠá€) {
            var3 = Blocks.áŒŠá€.ˆá;
        }
        if (!p_180429_2_.Ó().HorizonCode_Horizon_È()) {
            final int var4 = this.ÐƒÇŽà();
            if (this.µÕ != null && var4 != 1 && var4 != 2) {
                ++this.¥áŒŠà;
                if (this.¥áŒŠà > 5 && this.¥áŒŠà % 3 == 0) {
                    this.HorizonCode_Horizon_È("mob.horse.gallop", var3.Ø­áŒŠá() * 0.15f, var3.Âµá€());
                    if (var4 == 0 && this.ˆáƒ.nextInt(10) == 0) {
                        this.HorizonCode_Horizon_È("mob.horse.breathe", var3.Ø­áŒŠá() * 0.6f, var3.Âµá€());
                    }
                }
                else if (this.¥áŒŠà <= 5) {
                    this.HorizonCode_Horizon_È("mob.horse.wood", var3.Ø­áŒŠá() * 0.15f, var3.Âµá€());
                }
            }
            else if (var3 == Block.Ø­áŒŠá) {
                this.HorizonCode_Horizon_È("mob.horse.wood", var3.Ø­áŒŠá() * 0.15f, var3.Âµá€());
            }
            else {
                this.HorizonCode_Horizon_È("mob.horse.soft", var3.Ø­áŒŠá() * 0.15f, var3.Âµá€());
            }
        }
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.µÐƒÓ().Â(EntityHorse.Œáƒ);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(53.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.22499999403953552);
    }
    
    @Override
    public int Ï­áˆºÓ() {
        return 6;
    }
    
    public int £Ç() {
        return 100;
    }
    
    @Override
    protected float ˆÂ() {
        return 0.8f;
    }
    
    @Override
    public int áŒŠÔ() {
        return 400;
    }
    
    public boolean ÇŽØ­à() {
        return this.ÐƒÇŽà() == 0 || this.Ø­à¢() > 0;
    }
    
    private void ŒÇ() {
        this.ˆÂ = null;
    }
    
    public boolean ÇªÇªÉ() {
        return this.ˆØ­áˆº;
    }
    
    private void ˆÔ() {
        this.ˆÂ = "horse/";
        this.áŒŠÈ[0] = null;
        this.áŒŠÈ[1] = null;
        this.áŒŠÈ[2] = null;
        final int var1 = this.ÐƒÇŽà();
        final int var2 = this.¥Ê();
        if (var1 == 0) {
            final int var3 = var2 & 0xFF;
            final int var4 = (var2 & 0xFF00) >> 8;
            if (var3 >= EntityHorse.ÇªÂ.length) {
                this.ˆØ­áˆº = false;
                return;
            }
            this.áŒŠÈ[0] = EntityHorse.ÇªÂ[var3];
            this.ˆÂ = String.valueOf(this.ˆÂ) + EntityHorse.ÂµáˆºÂ[var3];
            if (var4 >= EntityHorse.¥Âµá€.length) {
                this.ˆØ­áˆº = false;
                return;
            }
            this.áŒŠÈ[1] = EntityHorse.¥Âµá€[var4];
            this.ˆÂ = String.valueOf(this.ˆÂ) + EntityHorse.ÇŽÈ[var4];
        }
        else {
            this.áŒŠÈ[0] = "";
            this.ˆÂ = String.valueOf(this.ˆÂ) + "_" + var1 + "_";
        }
        final int var3 = this.Ø­à¢();
        if (var3 >= EntityHorse.Œá.length) {
            this.ˆØ­áˆº = false;
        }
        else {
            this.áŒŠÈ[2] = EntityHorse.Œá[var3];
            this.ˆÂ = String.valueOf(this.ˆÂ) + EntityHorse.µÂ[var3];
            this.ˆØ­áˆº = true;
        }
    }
    
    public String ÐƒáŒŠÂµÐƒÕ() {
        if (this.ˆÂ == null) {
            this.ˆÔ();
        }
        return this.ˆÂ;
    }
    
    public String[] Ø­áƒ() {
        if (this.ˆÂ == null) {
            this.ˆÔ();
        }
        return this.áŒŠÈ;
    }
    
    public void à(final EntityPlayer p_110199_1_) {
        if (!this.Ï­Ðƒà.ŠÄ && (this.µÕ == null || this.µÕ == p_110199_1_) && this.áˆºÕ()) {
            this.µÊ.HorizonCode_Horizon_È(this.v_());
            p_110199_1_.HorizonCode_Horizon_È(this, this.µÊ);
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºáˆºáŠ) {
            return super.Ø­áŒŠá(p_70085_1_);
        }
        if (!this.áˆºÕ() && this.Ï­áŠ()) {
            return false;
        }
        if (this.áˆºÕ() && this.ÐƒÓ() && p_70085_1_.Çªà¢()) {
            this.à(p_70085_1_);
            return true;
        }
        if (this.ŒÐƒà() && this.µÕ != null) {
            return super.Ø­áŒŠá(p_70085_1_);
        }
        if (var2 != null) {
            boolean var3 = false;
            if (this.ÇŽáˆºÈ()) {
                byte var4 = -1;
                if (var2.HorizonCode_Horizon_È() == Items.ÐƒÇŽà) {
                    var4 = 1;
                }
                else if (var2.HorizonCode_Horizon_È() == Items.¥Ê) {
                    var4 = 2;
                }
                else if (var2.HorizonCode_Horizon_È() == Items.ÐƒÓ) {
                    var4 = 3;
                }
                if (var4 >= 0) {
                    if (!this.áˆºÕ()) {
                        this.áˆºÛ();
                        return true;
                    }
                    this.à(p_70085_1_);
                    return true;
                }
            }
            if (!var3 && !this.Ï­áŠ()) {
                float var5 = 0.0f;
                short var6 = 0;
                byte var7 = 0;
                if (var2.HorizonCode_Horizon_È() == Items.Âµà) {
                    var5 = 2.0f;
                    var6 = 20;
                    var7 = 3;
                }
                else if (var2.HorizonCode_Horizon_È() == Items.£Ø­à) {
                    var5 = 1.0f;
                    var6 = 30;
                    var7 = 3;
                }
                else if (Block.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È()) == Blocks.ÂµÊ) {
                    var5 = 20.0f;
                    var6 = 180;
                }
                else if (var2.HorizonCode_Horizon_È() == Items.Âµá€) {
                    var5 = 3.0f;
                    var6 = 60;
                    var7 = 3;
                }
                else if (var2.HorizonCode_Horizon_È() == Items.ŠÏ) {
                    var5 = 4.0f;
                    var6 = 60;
                    var7 = 5;
                    if (this.áˆºÕ() && this.à() == 0) {
                        var3 = true;
                        this.Ó(p_70085_1_);
                    }
                }
                else if (var2.HorizonCode_Horizon_È() == Items.£Õ) {
                    var5 = 10.0f;
                    var6 = 240;
                    var7 = 10;
                    if (this.áˆºÕ() && this.à() == 0) {
                        var3 = true;
                        this.Ó(p_70085_1_);
                    }
                }
                if (this.Ï­Ä() < this.ÇŽÊ() && var5 > 0.0f) {
                    this.a_(var5);
                    var3 = true;
                }
                if (!this.ÐƒÓ() && var6 > 0) {
                    this.HorizonCode_Horizon_È(var6);
                    var3 = true;
                }
                if (var7 > 0 && (var3 || !this.áˆºÕ()) && var7 < this.£Ç()) {
                    var3 = true;
                    this.µÕ(var7);
                }
                if (var3) {
                    this.ÇªØ();
                }
            }
            if (!this.áˆºÕ() && !var3) {
                if (var2 != null && var2.HorizonCode_Horizon_È(p_70085_1_, this)) {
                    return true;
                }
                this.áˆºÛ();
                return true;
            }
            else {
                if (!var3 && this.Ï­È() && !this.Ñ¢Õ() && var2.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆáƒ)) {
                    this.Å(true);
                    this.HorizonCode_Horizon_È("mob.chickenplop", 1.0f, (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
                    var3 = true;
                    this.Âµáƒ();
                }
                if (!var3 && this.ŒÐƒà() && !this.ŠÂµÏ() && var2.HorizonCode_Horizon_È() == Items.Û) {
                    this.à(p_70085_1_);
                    return true;
                }
                if (var3) {
                    if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                        final ItemStack itemStack = var2;
                        if (--itemStack.Â == 0) {
                            p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                        }
                    }
                    return true;
                }
            }
        }
        if (!this.ŒÐƒà() || this.µÕ != null) {
            return super.Ø­áŒŠá(p_70085_1_);
        }
        if (var2 != null && var2.HorizonCode_Horizon_È(p_70085_1_, this)) {
            return true;
        }
        this.áŒŠÆ(p_70085_1_);
        return true;
    }
    
    private void áŒŠÆ(final EntityPlayer p_110237_1_) {
        p_110237_1_.É = this.É;
        p_110237_1_.áƒ = this.áƒ;
        this.ˆà(false);
        this.¥Æ(false);
        if (!this.Ï­Ðƒà.ŠÄ) {
            p_110237_1_.HorizonCode_Horizon_È((Entity)this);
        }
    }
    
    public boolean ÇŽáˆºÈ() {
        return this.ÐƒÇŽà() == 0;
    }
    
    public boolean Ï­È() {
        final int var1 = this.ÐƒÇŽà();
        return var1 == 2 || var1 == 1;
    }
    
    @Override
    protected boolean ˆØ­áˆº() {
        return (this.µÕ != null && this.ŠÂµÏ()) || this.áŒŠÓ() || this.Ø­Â();
    }
    
    public boolean Ï­áŠ() {
        final int var1 = this.ÐƒÇŽà();
        return var1 == 3 || var1 == 4;
    }
    
    public boolean Ñ¢Ô() {
        return this.Ï­áŠ() || this.ÐƒÇŽà() == 2;
    }
    
    @Override
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return false;
    }
    
    private void ŠÐƒÇªáƒ() {
        this.ŒÂ = 1;
    }
    
    @Override
    public void Â(final DamageSource cause) {
        super.Â(cause);
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.ˆÇªÓ();
        }
    }
    
    @Override
    public void ˆÏ­() {
        if (this.ˆáƒ.nextInt(200) == 0) {
            this.ŠÐƒÇªáƒ();
        }
        super.ˆÏ­();
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (this.ˆáƒ.nextInt(900) == 0 && this.ÇªØ­ == 0) {
                this.a_(1.0f);
            }
            if (!this.áŒŠÓ() && this.µÕ == null && this.ˆáƒ.nextInt(300) == 0 && this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.Çªà¢) - 1, MathHelper.Ý(this.Ê))).Ý() == Blocks.Ø­áŒŠá) {
                this.ˆà(true);
            }
            if (this.áŒŠÓ() && ++this.ÇªáˆºÕ > 50) {
                this.ÇªáˆºÕ = 0;
                this.ˆà(false);
            }
            if (this.¥ÇªÅ() && !this.ÐƒÓ() && !this.áŒŠÓ()) {
                final EntityHorse var1 = this.HorizonCode_Horizon_È(this, 16.0);
                if (var1 != null && this.Âµá€(var1) > 4.0) {
                    this.áˆºÑ¢Õ.HorizonCode_Horizon_È(var1);
                }
            }
        }
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Ï­Ðƒà.ŠÄ && this.£Ó.HorizonCode_Horizon_È()) {
            this.£Ó.Âµá€();
            this.ŒÇ();
        }
        if (this.Ï­Ä > 0 && ++this.Ï­Ä > 30) {
            this.Ï­Ä = 0;
            this.Ý(128, false);
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.¥áŠ > 0 && ++this.¥áŠ > 20) {
            this.¥áŠ = 0;
            this.¥Æ(false);
        }
        if (this.ŒÂ > 0 && ++this.ŒÂ > 8) {
            this.ŒÂ = 0;
        }
        if (this.Ï­Ï > 0) {
            ++this.Ï­Ï;
            if (this.Ï­Ï > 300) {
                this.Ï­Ï = 0;
            }
        }
        this.ŒÓ = this.ÇŽØ;
        if (this.áŒŠÓ()) {
            this.ÇŽØ += (1.0f - this.ÇŽØ) * 0.4f + 0.05f;
            if (this.ÇŽØ > 1.0f) {
                this.ÇŽØ = 1.0f;
            }
        }
        else {
            this.ÇŽØ += (0.0f - this.ÇŽØ) * 0.4f - 0.05f;
            if (this.ÇŽØ < 0.0f) {
                this.ÇŽØ = 0.0f;
            }
        }
        this.µ = this.ÇŽÊ;
        if (this.Ø­Â()) {
            final float n = 0.0f;
            this.ÇŽØ = n;
            this.ŒÓ = n;
            this.ÇŽÊ += (1.0f - this.ÇŽÊ) * 0.4f + 0.05f;
            if (this.ÇŽÊ > 1.0f) {
                this.ÇŽÊ = 1.0f;
            }
        }
        else {
            this.áŒŠÉ = false;
            this.ÇŽÊ += (0.8f * this.ÇŽÊ * this.ÇŽÊ * this.ÇŽÊ - this.ÇŽÊ) * 0.6f - 0.05f;
            if (this.ÇŽÊ < 0.0f) {
                this.ÇŽÊ = 0.0f;
            }
        }
        this.µÐƒÓ = this.µÏ;
        if (this.Šáƒ(128)) {
            this.µÏ += (1.0f - this.µÏ) * 0.7f + 0.05f;
            if (this.µÏ > 1.0f) {
                this.µÏ = 1.0f;
            }
        }
        else {
            this.µÏ += (0.0f - this.µÏ) * 0.7f - 0.05f;
            if (this.µÏ < 0.0f) {
                this.µÏ = 0.0f;
            }
        }
    }
    
    private void Ø­Ï­à() {
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.Ï­Ä = 1;
            this.Ý(128, true);
        }
    }
    
    private boolean ŠÂ() {
        return this.µÕ == null && this.Æ == null && this.áˆºÕ() && this.ÐƒÓ() && !this.Ñ¢Ô() && this.Ï­Ä() >= this.ÇŽÊ() && this.ÇŽÅ();
    }
    
    @Override
    public void à(final boolean eating) {
        this.Ý(32, eating);
    }
    
    public void ˆà(final boolean p_110227_1_) {
        this.à(p_110227_1_);
    }
    
    public void ¥Æ(final boolean p_110219_1_) {
        if (p_110219_1_) {
            this.ˆà(false);
        }
        this.Ý(64, p_110219_1_);
    }
    
    private void µá() {
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.¥áŠ = 1;
            this.¥Æ(true);
        }
    }
    
    public void áˆºÛ() {
        this.µá();
        final String var1 = this.Ðƒ();
        if (var1 != null) {
            this.HorizonCode_Horizon_È(var1, this.ˆÂ(), this.áŒŠÈ());
        }
    }
    
    public void ˆÇªÓ() {
        this.HorizonCode_Horizon_È(this, this.µÊ);
        this.Ø­();
    }
    
    private void HorizonCode_Horizon_È(final Entity p_110240_1_, final AnimalChest p_110240_2_) {
        if (p_110240_2_ != null && !this.Ï­Ðƒà.ŠÄ) {
            for (int var3 = 0; var3 < p_110240_2_.áŒŠÆ(); ++var3) {
                final ItemStack var4 = p_110240_2_.á(var3);
                if (var4 != null) {
                    this.HorizonCode_Horizon_È(var4, 0.0f);
                }
            }
        }
    }
    
    public boolean Ø(final EntityPlayer p_110263_1_) {
        this.HorizonCode_Horizon_È(p_110263_1_.£áŒŠá().toString());
        this.á(true);
        return true;
    }
    
    @Override
    public void Ó(float p_70612_1_, float p_70612_2_) {
        if (this.µÕ != null && this.µÕ instanceof EntityLivingBase && this.ŠÂµÏ()) {
            final float é = this.µÕ.É;
            this.É = é;
            this.á€ = é;
            this.áƒ = this.µÕ.áƒ * 0.5f;
            this.Â(this.É, this.áƒ);
            final float é2 = this.É;
            this.¥É = é2;
            this.ÂµÕ = é2;
            p_70612_1_ = ((EntityLivingBase)this.µÕ).£áƒ * 0.5f;
            p_70612_2_ = ((EntityLivingBase)this.µÕ).Ï­áˆºÓ;
            if (p_70612_2_ <= 0.0f) {
                p_70612_2_ *= 0.25f;
                this.¥áŒŠà = 0;
            }
            if (this.ŠÂµà && this.Çªà == 0.0f && this.Ø­Â() && !this.áŒŠÉ) {
                p_70612_1_ = 0.0f;
                p_70612_2_ = 0.0f;
            }
            if (this.Çªà > 0.0f && !this.Ø­È() && this.ŠÂµà) {
                this.ˆá = this.ÐƒÉ() * this.Çªà;
                if (this.HorizonCode_Horizon_È(Potion.áˆºÑ¢Õ)) {
                    this.ˆá += (this.Â(Potion.áˆºÑ¢Õ).Ý() + 1) * 0.1f;
                }
                this.ˆÏ­(true);
                this.áŒŠÏ = true;
                if (p_70612_2_ > 0.0f) {
                    final float var3 = MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f);
                    final float var4 = MathHelper.Â(this.É * 3.1415927f / 180.0f);
                    this.ÇŽÉ += -0.4f * var3 * this.Çªà;
                    this.ÇŽÕ += 0.4f * var4 * this.Çªà;
                    this.HorizonCode_Horizon_È("mob.horse.jump", 0.4f, 1.0f);
                }
                this.Çªà = 0.0f;
            }
            this.Ô = 1.0f;
            this.Ø­Ñ¢á€ = this.áˆºá() * 0.1f;
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.áŒŠÆ((float)this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Âµá€());
                super.Ó(p_70612_1_, p_70612_2_);
            }
            if (this.ŠÂµà) {
                this.Çªà = 0.0f;
                this.ˆÏ­(false);
            }
            this.Šà = this.áŒŠá€;
            final double var5 = this.ŒÏ - this.áŒŠà;
            final double var6 = this.Ê - this.Ñ¢á;
            float var7 = MathHelper.HorizonCode_Horizon_È(var5 * var5 + var6 * var6) * 4.0f;
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            this.áŒŠá€ += (var7 - this.áŒŠá€) * 0.4f;
            this.¥Ï += this.áŒŠá€;
        }
        else {
            this.Ô = 0.5f;
            this.Ø­Ñ¢á€ = 0.02f;
            super.Ó(p_70612_1_, p_70612_2_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("EatingHaystack", this.áŒŠÓ());
        tagCompound.HorizonCode_Horizon_È("ChestedHorse", this.Ñ¢Õ());
        tagCompound.HorizonCode_Horizon_È("HasReproduced", this.áˆºÓ());
        tagCompound.HorizonCode_Horizon_È("Bred", this.¥ÇªÅ());
        tagCompound.HorizonCode_Horizon_È("Type", this.ÐƒÇŽà());
        tagCompound.HorizonCode_Horizon_È("Variant", this.¥Ê());
        tagCompound.HorizonCode_Horizon_È("Temper", this.ÂµÊ());
        tagCompound.HorizonCode_Horizon_È("Tame", this.áˆºÕ());
        tagCompound.HorizonCode_Horizon_È("OwnerUUID", this.ÐƒáˆºÄ());
        if (this.Ñ¢Õ()) {
            final NBTTagList var2 = new NBTTagList();
            for (int var3 = 2; var3 < this.µÊ.áŒŠÆ(); ++var3) {
                final ItemStack var4 = this.µÊ.á(var3);
                if (var4 != null) {
                    final NBTTagCompound var5 = new NBTTagCompound();
                    var5.HorizonCode_Horizon_È("Slot", (byte)var3);
                    var4.Â(var5);
                    var2.HorizonCode_Horizon_È(var5);
                }
            }
            tagCompound.HorizonCode_Horizon_È("Items", var2);
        }
        if (this.µÊ.á(1) != null) {
            tagCompound.HorizonCode_Horizon_È("ArmorItem", this.µÊ.á(1).Â(new NBTTagCompound()));
        }
        if (this.µÊ.á(0) != null) {
            tagCompound.HorizonCode_Horizon_È("SaddleItem", this.µÊ.á(0).Â(new NBTTagCompound()));
        }
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ˆà(tagCompund.£á("EatingHaystack"));
        this.£á(tagCompund.£á("Bred"));
        this.Å(tagCompund.£á("ChestedHorse"));
        this.£à(tagCompund.£á("HasReproduced"));
        this.ˆà(tagCompund.Ó("Type"));
        this.¥Æ(tagCompund.Ó("Variant"));
        this.Ø­à(tagCompund.Ó("Temper"));
        this.á(tagCompund.£á("Tame"));
        String var2 = "";
        if (tagCompund.Â("OwnerUUID", 8)) {
            var2 = tagCompund.áˆºÑ¢Õ("OwnerUUID");
        }
        else {
            final String var3 = tagCompund.áˆºÑ¢Õ("Owner");
            var2 = PreYggdrasilConverter.HorizonCode_Horizon_È(var3);
        }
        if (var2.length() > 0) {
            this.HorizonCode_Horizon_È(var2);
        }
        final IAttributeInstance var4 = this.µÐƒÓ().HorizonCode_Horizon_È("Speed");
        if (var4 != null) {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(var4.Â() * 0.25);
        }
        if (this.Ñ¢Õ()) {
            final NBTTagList var5 = tagCompund.Ý("Items", 10);
            this.Âµáƒ();
            for (int var6 = 0; var6 < var5.Âµá€(); ++var6) {
                final NBTTagCompound var7 = var5.Â(var6);
                final int var8 = var7.Ø­áŒŠá("Slot") & 0xFF;
                if (var8 >= 2 && var8 < this.µÊ.áŒŠÆ()) {
                    this.µÊ.Ý(var8, ItemStack.HorizonCode_Horizon_È(var7));
                }
            }
        }
        if (tagCompund.Â("ArmorItem", 10)) {
            final ItemStack var9 = ItemStack.HorizonCode_Horizon_È(tagCompund.ˆÏ­("ArmorItem"));
            if (var9 != null && HorizonCode_Horizon_È(var9.HorizonCode_Horizon_È())) {
                this.µÊ.Ý(1, var9);
            }
        }
        if (tagCompund.Â("SaddleItem", 10)) {
            final ItemStack var9 = ItemStack.HorizonCode_Horizon_È(tagCompund.ˆÏ­("SaddleItem"));
            if (var9 != null && var9.HorizonCode_Horizon_È() == Items.Û) {
                this.µÊ.Ý(0, var9);
            }
        }
        else if (tagCompund.£á("Saddle")) {
            this.µÊ.Ý(0, new ItemStack(Items.Û));
        }
        this.ÇªÊ();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityAnimal p_70878_1_) {
        if (p_70878_1_ == this) {
            return false;
        }
        if (p_70878_1_.getClass() != this.getClass()) {
            return false;
        }
        final EntityHorse var2 = (EntityHorse)p_70878_1_;
        if (this.ŠÂ() && var2.ŠÂ()) {
            final int var3 = this.ÐƒÇŽà();
            final int var4 = var2.ÐƒÇŽà();
            return var3 == var4 || (var3 == 0 && var4 == 1) || (var3 == 1 && var4 == 0);
        }
        return false;
    }
    
    @Override
    public EntityAgeable HorizonCode_Horizon_È(final EntityAgeable p_90011_1_) {
        final EntityHorse var2 = (EntityHorse)p_90011_1_;
        final EntityHorse var3 = new EntityHorse(this.Ï­Ðƒà);
        final int var4 = this.ÐƒÇŽà();
        final int var5 = var2.ÐƒÇŽà();
        int var6 = 0;
        if (var4 == var5) {
            var6 = var4;
        }
        else if ((var4 == 0 && var5 == 1) || (var4 == 1 && var5 == 0)) {
            var6 = 2;
        }
        if (var6 == 0) {
            final int var7 = this.ˆáƒ.nextInt(9);
            int var8;
            if (var7 < 4) {
                var8 = (this.¥Ê() & 0xFF);
            }
            else if (var7 < 8) {
                var8 = (var2.¥Ê() & 0xFF);
            }
            else {
                var8 = this.ˆáƒ.nextInt(7);
            }
            final int var9 = this.ˆáƒ.nextInt(5);
            if (var9 < 2) {
                var8 |= (this.¥Ê() & 0xFF00);
            }
            else if (var9 < 4) {
                var8 |= (var2.¥Ê() & 0xFF00);
            }
            else {
                var8 |= (this.ˆáƒ.nextInt(5) << 8 & 0xFF00);
            }
            var3.¥Æ(var8);
        }
        var3.ˆà(var6);
        final double var10 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).Â() + p_90011_1_.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).Â() + this.£È();
        var3.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(var10 / 3.0);
        final double var11 = this.HorizonCode_Horizon_È(EntityHorse.Œáƒ).Â() + p_90011_1_.HorizonCode_Horizon_È(EntityHorse.Œáƒ).Â() + this.áˆºÆ();
        var3.HorizonCode_Horizon_È(EntityHorse.Œáƒ).HorizonCode_Horizon_È(var11 / 3.0);
        final double var12 = this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Â() + p_90011_1_.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).Â() + this.¥Û();
        var3.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(var12 / 3.0);
        return var3;
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        Object p_180482_2_2 = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        final boolean var3 = false;
        int var4 = 0;
        int var5;
        if (p_180482_2_2 instanceof HorizonCode_Horizon_È) {
            var5 = ((HorizonCode_Horizon_È)p_180482_2_2).HorizonCode_Horizon_È;
            var4 = ((((HorizonCode_Horizon_È)p_180482_2_2).Â & 0xFF) | this.ˆáƒ.nextInt(5) << 8);
        }
        else {
            if (this.ˆáƒ.nextInt(10) == 0) {
                var5 = 1;
            }
            else {
                final int var6 = this.ˆáƒ.nextInt(7);
                final int var7 = this.ˆáƒ.nextInt(5);
                var5 = 0;
                var4 = (var6 | var7 << 8);
            }
            p_180482_2_2 = new HorizonCode_Horizon_È(var5, var4);
        }
        this.ˆà(var5);
        this.¥Æ(var4);
        if (this.ˆáƒ.nextInt(5) == 0) {
            this.Â(-24000);
        }
        if (var5 != 4 && var5 != 3) {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(this.£È());
            if (var5 == 0) {
                this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(this.¥Û());
            }
            else {
                this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.17499999701976776);
            }
        }
        else {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(15.0);
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.20000000298023224);
        }
        if (var5 != 2 && var5 != 1) {
            this.HorizonCode_Horizon_È(EntityHorse.Œáƒ).HorizonCode_Horizon_È(this.áˆºÆ());
        }
        else {
            this.HorizonCode_Horizon_È(EntityHorse.Œáƒ).HorizonCode_Horizon_È(0.5);
        }
        this.áˆºÑ¢Õ(this.ÇŽÊ());
        return (IEntityLivingData)p_180482_2_2;
    }
    
    public float £á(final float p_110258_1_) {
        return this.ŒÓ + (this.ÇŽØ - this.ŒÓ) * p_110258_1_;
    }
    
    public float Å(final float p_110223_1_) {
        return this.µ + (this.ÇŽÊ - this.µ) * p_110223_1_;
    }
    
    public float £à(final float p_110201_1_) {
        return this.µÐƒÓ + (this.µÏ - this.µÐƒÓ) * p_110201_1_;
    }
    
    public void Æ(int p_110206_1_) {
        if (this.ŠÂµÏ()) {
            if (p_110206_1_ < 0) {
                p_110206_1_ = 0;
            }
            else {
                this.áŒŠÉ = true;
                this.µá();
            }
            if (p_110206_1_ >= 90) {
                this.Çªà = 1.0f;
            }
            else {
                this.Çªà = 0.4f + 0.4f * p_110206_1_ / 90.0f;
            }
        }
    }
    
    protected void Ø­à(final boolean p_110216_1_) {
        final EnumParticleTypes var2 = p_110216_1_ ? EnumParticleTypes.áƒ : EnumParticleTypes.á;
        for (int var3 = 0; var3 < 7; ++var3) {
            final double var4 = this.ˆáƒ.nextGaussian() * 0.02;
            final double var5 = this.ˆáƒ.nextGaussian() * 0.02;
            final double var6 = this.ˆáƒ.nextGaussian() * 0.02;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var2, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 0.5 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, var4, var5, var6, new int[0]);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 7) {
            this.Ø­à(true);
        }
        else if (p_70103_1_ == 6) {
            this.Ø­à(false);
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    @Override
    public void ˆÉ() {
        super.ˆÉ();
        if (this.µ > 0.0f) {
            final float var1 = MathHelper.HorizonCode_Horizon_È(this.¥É * 3.1415927f / 180.0f);
            final float var2 = MathHelper.Â(this.¥É * 3.1415927f / 180.0f);
            final float var3 = 0.7f * this.µ;
            final float var4 = 0.15f * this.µ;
            this.µÕ.Ý(this.ŒÏ + var3 * var1, this.Çªà¢ + this.£Â() + this.µÕ.Ï­Ï­Ï() + var4, this.Ê - var3 * var2);
            if (this.µÕ instanceof EntityLivingBase) {
                ((EntityLivingBase)this.µÕ).¥É = this.¥É;
            }
        }
    }
    
    private float £È() {
        return 15.0f + this.ˆáƒ.nextInt(8) + this.ˆáƒ.nextInt(9);
    }
    
    private double áˆºÆ() {
        return 0.4000000059604645 + this.ˆáƒ.nextDouble() * 0.2 + this.ˆáƒ.nextDouble() * 0.2 + this.ˆáƒ.nextDouble() * 0.2;
    }
    
    private double ¥Û() {
        return (0.44999998807907104 + this.ˆáƒ.nextDouble() * 0.3 + this.ˆáƒ.nextDouble() * 0.3 + this.ˆáƒ.nextDouble() * 0.3) * 0.25;
    }
    
    public static boolean HorizonCode_Horizon_È(final Item_1028566121 p_146085_0_) {
        return p_146085_0_ == Items.ÐƒÇŽà || p_146085_0_ == Items.¥Ê || p_146085_0_ == Items.ÐƒÓ;
    }
    
    @Override
    public boolean i_() {
        return false;
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ;
    }
    
    @Override
    public boolean Â(final int p_174820_1_, final ItemStack p_174820_2_) {
        if (p_174820_1_ == 499 && this.Ï­È()) {
            if (p_174820_2_ == null && this.Ñ¢Õ()) {
                this.Å(false);
                this.Âµáƒ();
                return true;
            }
            if (p_174820_2_ != null && p_174820_2_.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆáƒ) && !this.Ñ¢Õ()) {
                this.Å(true);
                this.Âµáƒ();
                return true;
            }
        }
        final int var3 = p_174820_1_ - 400;
        if (var3 >= 0 && var3 < 2 && var3 < this.µÊ.áŒŠÆ()) {
            if (var3 == 0 && p_174820_2_ != null && p_174820_2_.HorizonCode_Horizon_È() != Items.Û) {
                return false;
            }
            if (var3 == 1 && ((p_174820_2_ != null && !HorizonCode_Horizon_È(p_174820_2_.HorizonCode_Horizon_È())) || !this.ÇŽáˆºÈ())) {
                return false;
            }
            this.µÊ.Ý(var3, p_174820_2_);
            this.ÇªÊ();
            return true;
        }
        else {
            final int var4 = p_174820_1_ - 500 + 2;
            if (var4 >= 2 && var4 < this.µÊ.áŒŠÆ()) {
                this.µÊ.Ý(var4, p_174820_2_);
                return true;
            }
            return false;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IEntityLivingData
    {
        public int HorizonCode_Horizon_È;
        public int Â;
        private static final String Ý = "CL_00001643";
        
        public HorizonCode_Horizon_È(final int p_i1684_1_, final int p_i1684_2_) {
            this.HorizonCode_Horizon_È = p_i1684_1_;
            this.Â = p_i1684_2_;
        }
    }
}
