package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import com.google.common.base.Predicate;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant
{
    private int ŒÂ;
    private boolean Ï­Ï;
    private boolean ŠØ;
    Village Ø­Ñ¢Ï­Ø­áˆº;
    private EntityPlayer ˆÐƒØ;
    private MerchantRecipeList Çªà;
    private int ¥Å;
    private boolean Œáƒ;
    private boolean Œá;
    private int µÂ;
    private String Ñ¢ÇŽÏ;
    private int ÇªÂ;
    private int ÂµáˆºÂ;
    private boolean ¥Âµá€;
    private boolean ÇŽÈ;
    private InventoryBasic ÇªáˆºÕ;
    private static final Â[][][][] Ï­Ä;
    private static final String ¥áŠ = "CL_00001707";
    
    static {
        Ï­Ä = new Â[][][][] { { { { new HorizonCode_Horizon_È(Items.Âµà, new à(18, 22)), new HorizonCode_Horizon_È(Items.ˆÂ, new à(15, 19)), new HorizonCode_Horizon_È(Items.¥áŒŠà, new à(15, 19)), new Ó(Items.Ç, new à(-4, -2)) }, { new HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ), new à(8, 13)), new Ó(Items.Ï­Ó, new à(-3, -2)) }, { new HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.ˆÅ), new à(7, 12)), new Ó(Items.Âµá€, new à(-5, -7)) }, { new Ó(Items.áŒŠá, new à(-6, -10)), new Ó(Items.µÐƒáƒ, new à(1, 1)) } }, { { new HorizonCode_Horizon_È(Items.ˆá, new à(15, 20)), new HorizonCode_Horizon_È(Items.Ø, new à(16, 24)), new Ý(Items.Ñ¢Ó, new à(6, 6), Items.Ø­Æ, new à(6, 6)) }, { new Âµá€(Items.ÂµÕ, new à(7, 8)) } }, { { new HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), new à(16, 22)), new Ó(Items.áˆºà, new à(3, 4)) }, { new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 0), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 1), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 2), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 3), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 4), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 5), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 6), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 7), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 8), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 9), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 10), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 11), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 12), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 13), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 14), new à(1, 2)), new Ó(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, 15), new à(1, 2)) } }, { { new HorizonCode_Horizon_È(Items.ˆá, new à(15, 20)), new Ó(Items.à, new à(-12, -8)) }, { new Ó(Items.Ó, new à(2, 3)), new Ý(Item_1028566121.HorizonCode_Horizon_È(Blocks.Å), new à(10, 10), Items.Ï­Ï­Ï, new à(6, 10)) } } }, { { { new HorizonCode_Horizon_È(Items.ˆà¢, new à(24, 36)), new Ø­áŒŠá() }, { new HorizonCode_Horizon_È(Items.Ñ¢Ç, new à(8, 10)), new Ó(Items.£ÇªÓ, new à(10, 12)), new Ó(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ï­à), new à(3, 4)) }, { new HorizonCode_Horizon_È(Items.ÇŽÊ, new à(2, 2)), new Ó(Items.Š, new à(10, 12)), new Ó(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ï­Ðƒà), new à(-5, -3)) }, { new Ø­áŒŠá() }, { new Ø­áŒŠá() }, { new Ó(Items.ŒÐƒà, new à(20, 22)) } } }, { { { new HorizonCode_Horizon_È(Items.ŠØ, new à(36, 40)), new HorizonCode_Horizon_È(Items.ÂµÈ, new à(8, 10)) }, { new Ó(Items.ÇŽá, new à(-4, -1)), new Ó(new ItemStack(Items.áŒŠÔ, 1, EnumDyeColor.á.Ý()), new à(-2, -1)) }, { new Ó(Items.¥áŠ, new à(7, 11)), new Ó(Item_1028566121.HorizonCode_Horizon_È(Blocks.£Ø­à), new à(-3, -1)) }, { new Ó(Items.áŒŠÉ, new à(3, 11)) } } }, { { { new HorizonCode_Horizon_È(Items.Ø, new à(16, 24)), new Ó(Items.Ï­à, new à(4, 6)) }, { new HorizonCode_Horizon_È(Items.áˆºÑ¢Õ, new à(7, 9)), new Ó(Items.áˆºáˆºÈ, new à(10, 14)) }, { new HorizonCode_Horizon_È(Items.áŒŠÆ, new à(3, 4)), new Âµá€(Items.ÇªÓ, new à(16, 19)) }, { new Ó(Items.Ñ¢Â, new à(5, 7)), new Ó(Items.Ä, new à(9, 11)), new Ó(Items.£ÂµÄ, new à(5, 7)), new Ó(Items.Ø­Âµ, new à(11, 15)) } }, { { new HorizonCode_Horizon_È(Items.Ø, new à(16, 24)), new Ó(Items.Ý, new à(6, 8)) }, { new HorizonCode_Horizon_È(Items.áˆºÑ¢Õ, new à(7, 9)), new Âµá€(Items.á, new à(9, 10)) }, { new HorizonCode_Horizon_È(Items.áŒŠÆ, new à(3, 4)), new Âµá€(Items.µÕ, new à(12, 15)), new Âµá€(Items.Ï­Ðƒà, new à(9, 12)) } }, { { new HorizonCode_Horizon_È(Items.Ø, new à(16, 24)), new Âµá€(Items.HorizonCode_Horizon_È, new à(5, 7)) }, { new HorizonCode_Horizon_È(Items.áˆºÑ¢Õ, new à(7, 9)), new Âµá€(Items.Â, new à(9, 11)) }, { new HorizonCode_Horizon_È(Items.áŒŠÆ, new à(3, 4)), new Âµá€(Items.Šáƒ, new à(12, 15)) } } }, { { { new HorizonCode_Horizon_È(Items.£Â, new à(14, 18)), new HorizonCode_Horizon_È(Items.ˆÈ, new à(14, 18)) }, { new HorizonCode_Horizon_È(Items.Ø, new à(16, 24)), new Ó(Items.£Ó, new à(-7, -5)), new Ó(Items.ˆÅ, new à(-8, -6)) } }, { { new HorizonCode_Horizon_È(Items.£áŒŠá, new à(9, 12)), new Ó(Items.ˆáŠ, new à(2, 4)) }, { new Âµá€(Items.áŠ, new à(7, 12)) }, { new Ó(Items.Û, new à(8, 10)) } } } };
    }
    
    public EntityVillager(final World worldIn) {
        this(worldIn, 0);
    }
    
    public EntityVillager(final World worldIn, final int p_i1748_2_) {
        super(worldIn);
        this.ÇªáˆºÕ = new InventoryBasic("Items", false, 8);
        this.ˆà(p_i1748_2_);
        this.HorizonCode_Horizon_È(0.6f, 1.8f);
        ((PathNavigateGround)this.Š()).Â(true);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIAvoidEntity(this, (Predicate)new Predicate() {
            private static final String Â = "CL_00002195";
            
            public boolean HorizonCode_Horizon_È(final Entity p_179530_1_) {
                return p_179530_1_ instanceof EntityZombie;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        }, 8.0f, 0.6, 0.6));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAITradePlayer(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAILookAtTradePlayer(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIMoveIndoors(this));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIRestrictOpenDoor(this));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIOpenDoor(this, true));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIVillagerMate(this));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIFollowGolem(this));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAIVillagerInteract(this));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAIWander(this, 0.6));
        this.ÂµÈ.HorizonCode_Horizon_È(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
        this.Ý(true);
    }
    
    private void Ø­È() {
        if (!this.ÇŽÈ) {
            this.ÇŽÈ = true;
            if (this.h_()) {
                this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIPlay(this, 0.32));
            }
            else if (this.ÇŽ() == 0) {
                this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIHarvestFarmland(this, 0.6));
            }
        }
    }
    
    @Override
    protected void Ø() {
        if (this.ÇŽ() == 0) {
            this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIHarvestFarmland(this, 0.6));
        }
        super.Ø();
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.5);
    }
    
    @Override
    protected void ˆØ() {
        final int œâ = this.ŒÂ - 1;
        this.ŒÂ = œâ;
        if (œâ <= 0) {
            final BlockPos var1 = new BlockPos(this);
            this.Ï­Ðƒà.È().HorizonCode_Horizon_È(var1);
            this.ŒÂ = 70 + this.ˆáƒ.nextInt(50);
            this.Ø­Ñ¢Ï­Ø­áˆº = this.Ï­Ðƒà.È().HorizonCode_Horizon_È(var1, 32);
            if (this.Ø­Ñ¢Ï­Ø­áˆº == null) {
                this.Æ();
            }
            else {
                final BlockPos var2 = this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È();
                this.HorizonCode_Horizon_È(var2, (int)(this.Ø­Ñ¢Ï­Ø­áˆº.Â() * 1.0f));
                if (this.¥Âµá€) {
                    this.¥Âµá€ = false;
                    this.Ø­Ñ¢Ï­Ø­áˆº.Â(5);
                }
            }
        }
        if (!this.ÐƒÇŽà() && this.¥Å > 0) {
            --this.¥Å;
            if (this.¥Å <= 0) {
                if (this.Œáƒ) {
                    for (final MerchantRecipe var4 : this.Çªà) {
                        if (var4.Ø()) {
                            var4.HorizonCode_Horizon_È(this.ˆáƒ.nextInt(6) + this.ˆáƒ.nextInt(6) + 2);
                        }
                    }
                    this.Ñ¢Õ();
                    this.Œáƒ = false;
                    if (this.Ø­Ñ¢Ï­Ø­áˆº != null && this.Ñ¢ÇŽÏ != null) {
                        this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)14);
                        this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(this.Ñ¢ÇŽÏ, 1);
                    }
                }
                this.HorizonCode_Horizon_È(new PotionEffect(Potion.á.É, 200, 0));
            }
        }
        super.ˆØ();
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        final boolean var3 = var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºáˆºáŠ;
        if (!var3 && this.Œ() && !this.ÐƒÇŽà() && !this.h_()) {
            if (!this.Ï­Ðƒà.ŠÄ && (this.Çªà == null || this.Çªà.size() > 0)) {
                this.a_(p_70085_1_);
                p_70085_1_.HorizonCode_Horizon_È((IMerchant)this);
            }
            p_70085_1_.HorizonCode_Horizon_È(StatList.ˆá);
            return true;
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Profession", this.ÇŽ());
        tagCompound.HorizonCode_Horizon_È("Riches", this.µÂ);
        tagCompound.HorizonCode_Horizon_È("Career", this.ÇªÂ);
        tagCompound.HorizonCode_Horizon_È("CareerLevel", this.ÂµáˆºÂ);
        tagCompound.HorizonCode_Horizon_È("Willing", this.Œá);
        if (this.Çªà != null) {
            tagCompound.HorizonCode_Horizon_È("Offers", this.Çªà.HorizonCode_Horizon_È());
        }
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.ÇªáˆºÕ.áŒŠÆ(); ++var3) {
            final ItemStack var4 = this.ÇªáˆºÕ.á(var3);
            if (var4 != null) {
                var2.HorizonCode_Horizon_È(var4.Â(new NBTTagCompound()));
            }
        }
        tagCompound.HorizonCode_Horizon_È("Inventory", var2);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ˆà(tagCompund.Ó("Profession"));
        this.µÂ = tagCompund.Ó("Riches");
        this.ÇªÂ = tagCompund.Ó("Career");
        this.ÂµáˆºÂ = tagCompund.Ó("CareerLevel");
        this.Œá = tagCompund.£á("Willing");
        if (tagCompund.Â("Offers", 10)) {
            final NBTTagCompound var2 = tagCompund.ˆÏ­("Offers");
            this.Çªà = new MerchantRecipeList(var2);
        }
        final NBTTagList var3 = tagCompund.Ý("Inventory", 10);
        for (int var4 = 0; var4 < var3.Âµá€(); ++var4) {
            final ItemStack var5 = ItemStack.HorizonCode_Horizon_È(var3.Â(var4));
            if (var5 != null) {
                this.ÇªáˆºÕ.HorizonCode_Horizon_È(var5);
            }
        }
        this.Ý(true);
        this.Ø­È();
    }
    
    @Override
    protected boolean ÂµÂ() {
        return false;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return this.ÐƒÇŽà() ? "mob.villager.haggle" : "mob.villager.idle";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.villager.hit";
    }
    
    @Override
    protected String µÊ() {
        return "mob.villager.death";
    }
    
    public void ˆà(final int p_70938_1_) {
        this.£Ó.Â(16, p_70938_1_);
    }
    
    public int ÇŽ() {
        return Math.max(this.£Ó.Ý(16) % 5, 0);
    }
    
    public boolean ÇŽÅ() {
        return this.Ï­Ï;
    }
    
    public void á(final boolean p_70947_1_) {
        this.Ï­Ï = p_70947_1_;
    }
    
    public void ˆÏ­(final boolean p_70939_1_) {
        this.ŠØ = p_70939_1_;
    }
    
    public boolean ¥Ðƒá() {
        return this.ŠØ;
    }
    
    @Override
    public void Ý(final EntityLivingBase p_70604_1_) {
        super.Ý(p_70604_1_);
        if (this.Ø­Ñ¢Ï­Ø­áˆº != null && p_70604_1_ != null) {
            this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(p_70604_1_);
            if (p_70604_1_ instanceof EntityPlayer) {
                byte var2 = -1;
                if (this.h_()) {
                    var2 = -3;
                }
                this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(p_70604_1_.v_(), var2);
                if (this.Œ()) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)13);
                }
            }
        }
    }
    
    @Override
    public void Â(final DamageSource cause) {
        if (this.Ø­Ñ¢Ï­Ø­áˆº != null) {
            final Entity var2 = cause.áˆºÑ¢Õ();
            if (var2 != null) {
                if (var2 instanceof EntityPlayer) {
                    this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var2.v_(), -2);
                }
                else if (var2 instanceof IMob) {
                    this.Ø­Ñ¢Ï­Ø­áˆº.Ø();
                }
            }
            else {
                final EntityPlayer var3 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, 16.0);
                if (var3 != null) {
                    this.Ø­Ñ¢Ï­Ø­áˆº.Ø();
                }
            }
        }
        super.Â(cause);
    }
    
    @Override
    public void a_(final EntityPlayer p_70932_1_) {
        this.ˆÐƒØ = p_70932_1_;
    }
    
    @Override
    public EntityPlayer HorizonCode_Horizon_È() {
        return this.ˆÐƒØ;
    }
    
    public boolean ÐƒÇŽà() {
        return this.ˆÐƒØ != null;
    }
    
    public boolean £á(final boolean p_175550_1_) {
        if (!this.Œá && p_175550_1_ && this.áˆºÕ()) {
            boolean var2 = false;
            for (int var3 = 0; var3 < this.ÇªáˆºÕ.áŒŠÆ(); ++var3) {
                final ItemStack var4 = this.ÇªáˆºÕ.á(var3);
                if (var4 != null) {
                    if (var4.HorizonCode_Horizon_È() == Items.Ç && var4.Â >= 3) {
                        var2 = true;
                        this.ÇªáˆºÕ.Â(var3, 3);
                    }
                    else if ((var4.HorizonCode_Horizon_È() == Items.ˆÂ || var4.HorizonCode_Horizon_È() == Items.¥áŒŠà) && var4.Â >= 12) {
                        var2 = true;
                        this.ÇªáˆºÕ.Â(var3, 12);
                    }
                }
                if (var2) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)18);
                    this.Œá = true;
                    break;
                }
            }
        }
        return this.Œá;
    }
    
    public void Å(final boolean p_175549_1_) {
        this.Œá = p_175549_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final MerchantRecipe p_70933_1_) {
        p_70933_1_.à();
        this.Ó = -this.áŒŠÔ();
        this.HorizonCode_Horizon_È("mob.villager.yes", this.ˆÂ(), this.áŒŠÈ());
        int var2 = 3 + this.ˆáƒ.nextInt(4);
        if (p_70933_1_.Âµá€() == 1 || this.ˆáƒ.nextInt(5) == 0) {
            this.¥Å = 40;
            this.Œáƒ = true;
            this.Œá = true;
            if (this.ˆÐƒØ != null) {
                this.Ñ¢ÇŽÏ = this.ˆÐƒØ.v_();
            }
            else {
                this.Ñ¢ÇŽÏ = null;
            }
            var2 += 5;
        }
        if (p_70933_1_.HorizonCode_Horizon_È().HorizonCode_Horizon_È() == Items.µ) {
            this.µÂ += p_70933_1_.HorizonCode_Horizon_È().Â;
        }
        if (p_70933_1_.áˆºÑ¢Õ()) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢ + 0.5, this.Ê, var2));
        }
    }
    
    @Override
    public void a_(final ItemStack p_110297_1_) {
        if (!this.Ï­Ðƒà.ŠÄ && this.Ó > -this.áŒŠÔ() + 20) {
            this.Ó = -this.áŒŠÔ();
            if (p_110297_1_ != null) {
                this.HorizonCode_Horizon_È("mob.villager.yes", this.ˆÂ(), this.áŒŠÈ());
            }
            else {
                this.HorizonCode_Horizon_È("mob.villager.no", this.ˆÂ(), this.áŒŠÈ());
            }
        }
    }
    
    @Override
    public MerchantRecipeList Â(final EntityPlayer p_70934_1_) {
        if (this.Çªà == null) {
            this.Ñ¢Õ();
        }
        return this.Çªà;
    }
    
    private void Ñ¢Õ() {
        final Â[][][] var1 = EntityVillager.Ï­Ä[this.ÇŽ()];
        if (this.ÇªÂ != 0 && this.ÂµáˆºÂ != 0) {
            ++this.ÂµáˆºÂ;
        }
        else {
            this.ÇªÂ = this.ˆáƒ.nextInt(var1.length) + 1;
            this.ÂµáˆºÂ = 1;
        }
        if (this.Çªà == null) {
            this.Çªà = new MerchantRecipeList();
        }
        final int var2 = this.ÇªÂ - 1;
        final int var3 = this.ÂµáˆºÂ - 1;
        final Â[][] var4 = var1[var2];
        if (var3 < var4.length) {
            final Â[] var6;
            final Â[] var5 = var6 = var4[var3];
            for (int var7 = var5.length, var8 = 0; var8 < var7; ++var8) {
                final Â var9 = var6[var8];
                var9.HorizonCode_Horizon_È(this.Çªà, this.ˆáƒ);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final MerchantRecipeList p_70930_1_) {
    }
    
    @Override
    public IChatComponent Ý() {
        final String var1 = this.Šà();
        if (var1 != null && var1.length() > 0) {
            return new ChatComponentText(var1);
        }
        if (this.Çªà == null) {
            this.Ñ¢Õ();
        }
        String var2 = null;
        switch (this.ÇŽ()) {
            case 0: {
                if (this.ÇªÂ == 1) {
                    var2 = "farmer";
                    break;
                }
                if (this.ÇªÂ == 2) {
                    var2 = "fisherman";
                    break;
                }
                if (this.ÇªÂ == 3) {
                    var2 = "shepherd";
                    break;
                }
                if (this.ÇªÂ == 4) {
                    var2 = "fletcher";
                    break;
                }
                break;
            }
            case 1: {
                var2 = "librarian";
                break;
            }
            case 2: {
                var2 = "cleric";
                break;
            }
            case 3: {
                if (this.ÇªÂ == 1) {
                    var2 = "armor";
                    break;
                }
                if (this.ÇªÂ == 2) {
                    var2 = "weapon";
                    break;
                }
                if (this.ÇªÂ == 3) {
                    var2 = "tool";
                    break;
                }
                break;
            }
            case 4: {
                if (this.ÇªÂ == 1) {
                    var2 = "butcher";
                    break;
                }
                if (this.ÇªÂ == 2) {
                    var2 = "leather";
                    break;
                }
                break;
            }
        }
        if (var2 != null) {
            final ChatComponentTranslation var3 = new ChatComponentTranslation("entity.Villager." + var2, new Object[0]);
            var3.à().HorizonCode_Horizon_È(this.Ñ¢Ç());
            var3.à().HorizonCode_Horizon_È(this.£áŒŠá().toString());
            return var3;
        }
        return super.Ý();
    }
    
    @Override
    public float Ðƒáƒ() {
        float var1 = 1.62f;
        if (this.h_()) {
            var1 -= 0.81;
        }
        return var1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 12) {
            this.HorizonCode_Horizon_È(EnumParticleTypes.áƒ);
        }
        else if (p_70103_1_ == 13) {
            this.HorizonCode_Horizon_È(EnumParticleTypes.µÕ);
        }
        else if (p_70103_1_ == 14) {
            this.HorizonCode_Horizon_È(EnumParticleTypes.Æ);
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    private void HorizonCode_Horizon_È(final EnumParticleTypes p_180489_1_) {
        for (int var2 = 0; var2 < 5; ++var2) {
            final double var3 = this.ˆáƒ.nextGaussian() * 0.02;
            final double var4 = this.ˆáƒ.nextGaussian() * 0.02;
            final double var5 = this.ˆáƒ.nextGaussian() * 0.02;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(p_180489_1_, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 1.0 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, var3, var4, var5, new int[0]);
        }
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
        p_180482_2_ = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        this.ˆà(this.Ï­Ðƒà.Å.nextInt(5));
        this.Ø­È();
        return p_180482_2_;
    }
    
    public void ¥Ê() {
        this.¥Âµá€ = true;
    }
    
    public EntityVillager Â(final EntityAgeable p_180488_1_) {
        final EntityVillager var2 = new EntityVillager(this.Ï­Ðƒà);
        var2.HorizonCode_Horizon_È(this.Ï­Ðƒà.Ê(new BlockPos(var2)), null);
        return var2;
    }
    
    @Override
    public boolean ŠÏ­áˆºá() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLightningBolt lightningBolt) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            final EntityWitch var2 = new EntityWitch(this.Ï­Ðƒà);
            var2.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
            var2.HorizonCode_Horizon_È(this.Ï­Ðƒà.Ê(new BlockPos(var2)), null);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var2);
            this.á€();
        }
    }
    
    public InventoryBasic ÐƒÓ() {
        return this.ÇªáˆºÕ;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityItem p_175445_1_) {
        final ItemStack var2 = p_175445_1_.Ø();
        final Item_1028566121 var3 = var2.HorizonCode_Horizon_È();
        if (this.HorizonCode_Horizon_È(var3)) {
            final ItemStack var4 = this.ÇªáˆºÕ.HorizonCode_Horizon_È(var2);
            if (var4 == null) {
                p_175445_1_.á€();
            }
            else {
                var2.Â = var4.Â;
            }
        }
    }
    
    private boolean HorizonCode_Horizon_È(final Item_1028566121 p_175558_1_) {
        return p_175558_1_ == Items.Ç || p_175558_1_ == Items.ˆÂ || p_175558_1_ == Items.¥áŒŠà || p_175558_1_ == Items.Âµà || p_175558_1_ == Items.¥à;
    }
    
    public boolean áˆºÕ() {
        return this.¥Æ(1);
    }
    
    public boolean ŒÐƒà() {
        return this.¥Æ(2);
    }
    
    public boolean ÐƒáˆºÄ() {
        final boolean var1 = this.ÇŽ() == 0;
        return var1 ? (!this.¥Æ(5)) : (!this.¥Æ(1));
    }
    
    private boolean ¥Æ(final int p_175559_1_) {
        final boolean var2 = this.ÇŽ() == 0;
        for (int var3 = 0; var3 < this.ÇªáˆºÕ.áŒŠÆ(); ++var3) {
            final ItemStack var4 = this.ÇªáˆºÕ.á(var3);
            if (var4 != null) {
                if ((var4.HorizonCode_Horizon_È() == Items.Ç && var4.Â >= 3 * p_175559_1_) || (var4.HorizonCode_Horizon_È() == Items.ˆÂ && var4.Â >= 12 * p_175559_1_) || (var4.HorizonCode_Horizon_È() == Items.¥áŒŠà && var4.Â >= 12 * p_175559_1_)) {
                    return true;
                }
                if (var2 && var4.HorizonCode_Horizon_È() == Items.Âµà && var4.Â >= 9 * p_175559_1_) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean áˆºÉ() {
        for (int var1 = 0; var1 < this.ÇªáˆºÕ.áŒŠÆ(); ++var1) {
            final ItemStack var2 = this.ÇªáˆºÕ.á(var1);
            if (var2 != null && (var2.HorizonCode_Horizon_È() == Items.¥à || var2.HorizonCode_Horizon_È() == Items.ˆÂ || var2.HorizonCode_Horizon_È() == Items.¥áŒŠà)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean Â(final int p_174820_1_, final ItemStack p_174820_2_) {
        if (super.Â(p_174820_1_, p_174820_2_)) {
            return true;
        }
        final int var3 = p_174820_1_ - 300;
        if (var3 >= 0 && var3 < this.ÇªáˆºÕ.áŒŠÆ()) {
            this.ÇªáˆºÕ.Ý(var3, p_174820_2_);
            return true;
        }
        return false;
    }
    
    @Override
    public EntityAgeable HorizonCode_Horizon_È(final EntityAgeable p_90011_1_) {
        return this.Â(p_90011_1_);
    }
    
    static class HorizonCode_Horizon_È implements Â
    {
        public Item_1028566121 HorizonCode_Horizon_È;
        public à Â;
        private static final String Ý = "CL_00002194";
        
        public HorizonCode_Horizon_È(final Item_1028566121 p_i45815_1_, final à p_i45815_2_) {
            this.HorizonCode_Horizon_È = p_i45815_1_;
            this.Â = p_i45815_2_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
            int var3 = 1;
            if (this.Â != null) {
                var3 = this.Â.HorizonCode_Horizon_È(p_179401_2_);
            }
            p_179401_1_.add(new MerchantRecipe(new ItemStack(this.HorizonCode_Horizon_È, var3, 0), Items.µ));
        }
    }
    
    static class Ý implements Â
    {
        public ItemStack HorizonCode_Horizon_È;
        public à Â;
        public ItemStack Ý;
        public à Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002191";
        
        public Ý(final Item_1028566121 p_i45813_1_, final à p_i45813_2_, final Item_1028566121 p_i45813_3_, final à p_i45813_4_) {
            this.HorizonCode_Horizon_È = new ItemStack(p_i45813_1_);
            this.Â = p_i45813_2_;
            this.Ý = new ItemStack(p_i45813_3_);
            this.Ø­áŒŠá = p_i45813_4_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
            int var3 = 1;
            if (this.Â != null) {
                var3 = this.Â.HorizonCode_Horizon_È(p_179401_2_);
            }
            int var4 = 1;
            if (this.Ø­áŒŠá != null) {
                var4 = this.Ø­áŒŠá.HorizonCode_Horizon_È(p_179401_2_);
            }
            p_179401_1_.add(new MerchantRecipe(new ItemStack(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), var3, this.HorizonCode_Horizon_È.Ø()), new ItemStack(Items.µ), new ItemStack(this.Ý.HorizonCode_Horizon_È(), var4, this.Ý.Ø())));
        }
    }
    
    static class Ø­áŒŠá implements Â
    {
        private static final String HorizonCode_Horizon_È = "CL_00002193";
        
        @Override
        public void HorizonCode_Horizon_È(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
            final Enchantment var3 = Enchantment.Â[p_179401_2_.nextInt(Enchantment.Â.length)];
            final int var4 = MathHelper.HorizonCode_Horizon_È(p_179401_2_, var3.Ý(), var3.Ø­áŒŠá());
            final ItemStack var5 = Items.Çªáˆºá.HorizonCode_Horizon_È(new EnchantmentData(var3, var4));
            int var6 = 2 + p_179401_2_.nextInt(5 + var4 * 10) + 3 * var4;
            if (var6 > 64) {
                var6 = 64;
            }
            p_179401_1_.add(new MerchantRecipe(new ItemStack(Items.Ñ¢Ç), new ItemStack(Items.µ, var6), var5));
        }
    }
    
    static class Âµá€ implements Â
    {
        public ItemStack HorizonCode_Horizon_È;
        public à Â;
        private static final String Ý = "CL_00002192";
        
        public Âµá€(final Item_1028566121 p_i45814_1_, final à p_i45814_2_) {
            this.HorizonCode_Horizon_È = new ItemStack(p_i45814_1_);
            this.Â = p_i45814_2_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
            int var3 = 1;
            if (this.Â != null) {
                var3 = this.Â.HorizonCode_Horizon_È(p_179401_2_);
            }
            final ItemStack var4 = new ItemStack(Items.µ, var3, 0);
            ItemStack var5 = new ItemStack(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), 1, this.HorizonCode_Horizon_È.Ø());
            var5 = EnchantmentHelper.HorizonCode_Horizon_È(p_179401_2_, var5, 5 + p_179401_2_.nextInt(15));
            p_179401_1_.add(new MerchantRecipe(var4, var5));
        }
    }
    
    static class Ó implements Â
    {
        public ItemStack HorizonCode_Horizon_È;
        public à Â;
        private static final String Ý = "CL_00002190";
        
        public Ó(final Item_1028566121 p_i45811_1_, final à p_i45811_2_) {
            this.HorizonCode_Horizon_È = new ItemStack(p_i45811_1_);
            this.Â = p_i45811_2_;
        }
        
        public Ó(final ItemStack p_i45812_1_, final à p_i45812_2_) {
            this.HorizonCode_Horizon_È = p_i45812_1_;
            this.Â = p_i45812_2_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
            int var3 = 1;
            if (this.Â != null) {
                var3 = this.Â.HorizonCode_Horizon_È(p_179401_2_);
            }
            ItemStack var4;
            ItemStack var5;
            if (var3 < 0) {
                var4 = new ItemStack(Items.µ, 1, 0);
                var5 = new ItemStack(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), -var3, this.HorizonCode_Horizon_È.Ø());
            }
            else {
                var4 = new ItemStack(Items.µ, var3, 0);
                var5 = new ItemStack(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), 1, this.HorizonCode_Horizon_È.Ø());
            }
            p_179401_1_.add(new MerchantRecipe(var4, var5));
        }
    }
    
    static class à extends Tuple
    {
        private static final String HorizonCode_Horizon_È = "CL_00002189";
        
        public à(final int p_i45810_1_, final int p_i45810_2_) {
            super(p_i45810_1_, p_i45810_2_);
        }
        
        public int HorizonCode_Horizon_È(final Random p_179412_1_) {
            return (int)(((int)this.HorizonCode_Horizon_È() >= (int)this.Â()) ? this.HorizonCode_Horizon_È() : ((int)this.HorizonCode_Horizon_È() + p_179412_1_.nextInt((int)this.Â() - (int)this.HorizonCode_Horizon_È() + 1)));
        }
    }
    
    interface Â
    {
        void HorizonCode_Horizon_È(final MerchantRecipeList p0, final Random p1);
    }
}
