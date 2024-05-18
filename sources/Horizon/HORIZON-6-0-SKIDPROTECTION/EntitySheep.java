package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.collect.Maps;
import java.util.Map;

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting ŒÂ;
    private static final Map Ï­Ï;
    private int ŠØ;
    private EntityAIEatGrass ˆÐƒØ;
    private static final String Çªà = "CL_00001648";
    
    static {
        (Ï­Ï = Maps.newEnumMap((Class)EnumDyeColor.class)).put(EnumDyeColor.HorizonCode_Horizon_È, new float[] { 1.0f, 1.0f, 1.0f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Â, new float[] { 0.85f, 0.5f, 0.2f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Ý, new float[] { 0.7f, 0.3f, 0.85f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Ø­áŒŠá, new float[] { 0.4f, 0.6f, 0.85f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Âµá€, new float[] { 0.9f, 0.9f, 0.2f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Ó, new float[] { 0.5f, 0.8f, 0.1f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.à, new float[] { 0.95f, 0.5f, 0.65f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Ø, new float[] { 0.3f, 0.3f, 0.3f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.áŒŠÆ, new float[] { 0.6f, 0.6f, 0.6f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.áˆºÑ¢Õ, new float[] { 0.3f, 0.5f, 0.6f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.ÂµÈ, new float[] { 0.5f, 0.25f, 0.7f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.á, new float[] { 0.2f, 0.3f, 0.7f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.ˆÏ­, new float[] { 0.4f, 0.3f, 0.2f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.£á, new float[] { 0.4f, 0.5f, 0.2f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.Å, new float[] { 0.6f, 0.2f, 0.2f });
        EntitySheep.Ï­Ï.put(EnumDyeColor.£à, new float[] { 0.1f, 0.1f, 0.1f });
    }
    
    public static float[] HorizonCode_Horizon_È(final EnumDyeColor p_175513_0_) {
        return EntitySheep.Ï­Ï.get(p_175513_0_);
    }
    
    public EntitySheep(final World worldIn) {
        super(worldIn);
        this.ŒÂ = new InventoryCrafting(new Container() {
            private static final String Ó = "CL_00001649";
            
            @Override
            public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
                return false;
            }
        }, 2, 1);
        this.ˆÐƒØ = new EntityAIEatGrass(this);
        this.HorizonCode_Horizon_È(0.9f, 1.3f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIPanic(this, 1.25));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIMate(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAITempt(this, 1.1, Items.Âµà, false));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIFollowParent(this, 1.1));
        this.ÂµÈ.HorizonCode_Horizon_È(5, this.ˆÐƒØ);
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
        this.ŒÂ.Ý(0, new ItemStack(Items.áŒŠÔ, 1, 0));
        this.ŒÂ.Ý(1, new ItemStack(Items.áŒŠÔ, 1, 0));
    }
    
    @Override
    protected void ˆØ() {
        this.ŠØ = this.ˆÐƒØ.Ø();
        super.ˆØ();
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ï­Ðƒà.ŠÄ) {
            this.ŠØ = Math.max(0, this.ŠØ - 1);
        }
        super.ˆÏ­();
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(8.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.23000000417232513);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, new Byte((byte)0));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        if (!this.¥Ê()) {
            this.HorizonCode_Horizon_È(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, this.ÐƒÇŽà().Â()), 0.0f);
        }
        for (int var3 = this.ˆáƒ.nextInt(2) + 1 + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            if (this.ˆÏ()) {
                this.HorizonCode_Horizon_È(Items.ŠÏ­áˆºá, 1);
            }
            else {
                this.HorizonCode_Horizon_È(Items.ÇªÉ, 1);
            }
        }
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 10) {
            this.ŠØ = 40;
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    public float £á(final float p_70894_1_) {
        return (this.ŠØ <= 0) ? 0.0f : ((this.ŠØ >= 4 && this.ŠØ <= 36) ? 1.0f : ((this.ŠØ < 4) ? ((this.ŠØ - p_70894_1_) / 4.0f) : (-(this.ŠØ - 40 - p_70894_1_) / 4.0f)));
    }
    
    public float Å(final float p_70890_1_) {
        if (this.ŠØ > 4 && this.ŠØ <= 36) {
            final float var2 = (this.ŠØ - 4 - p_70890_1_) / 32.0f;
            return 0.62831855f + 0.2199115f * MathHelper.HorizonCode_Horizon_È(var2 * 28.7f);
        }
        return (this.ŠØ > 0) ? 0.62831855f : (this.áƒ / 57.295776f);
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºà && !this.¥Ê() && !this.h_()) {
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.á(true);
                for (int var3 = 1 + this.ˆáƒ.nextInt(3), var4 = 0; var4 < var3; ++var4) {
                    final EntityItem horizonCode_Horizon_È;
                    final EntityItem var5 = horizonCode_Horizon_È = this.HorizonCode_Horizon_È(new ItemStack(Item_1028566121.HorizonCode_Horizon_È(Blocks.ŠÂµà), 1, this.ÐƒÇŽà().Â()), 1.0f);
                    horizonCode_Horizon_È.ˆá += this.ˆáƒ.nextFloat() * 0.05f;
                    final EntityItem entityItem = var5;
                    entityItem.ÇŽÉ += (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.1f;
                    final EntityItem entityItem2 = var5;
                    entityItem2.ÇŽÕ += (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.1f;
                }
            }
            var2.HorizonCode_Horizon_È(1, p_70085_1_);
            this.HorizonCode_Horizon_È("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Sheared", this.¥Ê());
        tagCompound.HorizonCode_Horizon_È("Color", (byte)this.ÐƒÇŽà().Â());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.á(tagCompund.£á("Sheared"));
        this.Â(EnumDyeColor.Â(tagCompund.Ø­áŒŠá("Color")));
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String µÊ() {
        return "mob.sheep.say";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.sheep.step", 0.15f, 1.0f);
    }
    
    public EnumDyeColor ÐƒÇŽà() {
        return EnumDyeColor.Â(this.£Ó.HorizonCode_Horizon_È(16) & 0xF);
    }
    
    public void Â(final EnumDyeColor p_175512_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        this.£Ó.Â(16, (byte)((var2 & 0xF0) | (p_175512_1_.Â() & 0xF)));
    }
    
    public boolean ¥Ê() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x10) != 0x0;
    }
    
    public void á(final boolean p_70893_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70893_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x10));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFEF));
        }
    }
    
    public static EnumDyeColor HorizonCode_Horizon_È(final Random p_175510_0_) {
        final int var1 = p_175510_0_.nextInt(100);
        return (var1 < 5) ? EnumDyeColor.£à : ((var1 < 10) ? EnumDyeColor.Ø : ((var1 < 15) ? EnumDyeColor.áŒŠÆ : ((var1 < 18) ? EnumDyeColor.ˆÏ­ : ((p_175510_0_.nextInt(500) == 0) ? EnumDyeColor.à : EnumDyeColor.HorizonCode_Horizon_È))));
    }
    
    public EntitySheep Â(final EntityAgeable p_180491_1_) {
        final EntitySheep var2 = (EntitySheep)p_180491_1_;
        final EntitySheep var3 = new EntitySheep(this.Ï­Ðƒà);
        var3.Â(this.HorizonCode_Horizon_È(this, var2));
        return var3;
    }
    
    @Override
    public void Ø­Æ() {
        this.á(false);
        if (this.h_()) {
            this.HorizonCode_Horizon_È(60);
        }
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
        p_180482_2_ = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        this.Â(HorizonCode_Horizon_È(this.Ï­Ðƒà.Å));
        return p_180482_2_;
    }
    
    private EnumDyeColor HorizonCode_Horizon_È(final EntityAnimal p_175511_1_, final EntityAnimal p_175511_2_) {
        final int var3 = ((EntitySheep)p_175511_1_).ÐƒÇŽà().Ý();
        final int var4 = ((EntitySheep)p_175511_2_).ÐƒÇŽà().Ý();
        this.ŒÂ.á(0).Â(var3);
        this.ŒÂ.á(1).Â(var4);
        final ItemStack var5 = CraftingManager.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.ŒÂ, ((EntitySheep)p_175511_1_).Ï­Ðƒà);
        int var6;
        if (var5 != null && var5.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
            var6 = var5.Ø();
        }
        else {
            var6 = (this.Ï­Ðƒà.Å.nextBoolean() ? var3 : var4);
        }
        return EnumDyeColor.HorizonCode_Horizon_È(var6);
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.95f * this.£ÂµÄ;
    }
    
    @Override
    public EntityAgeable HorizonCode_Horizon_È(final EntityAgeable p_90011_1_) {
        return this.Â(p_90011_1_);
    }
}
