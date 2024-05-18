package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class EntityArmorStand extends EntityLivingBase
{
    private static final Rotations HorizonCode_Horizon_È;
    private static final Rotations Â;
    private static final Rotations Ý;
    private static final Rotations Ø­áŒŠá;
    private static final Rotations Âµá€;
    private static final Rotations Ó;
    private final ItemStack[] à;
    private boolean Ø;
    private long áŒŠÆ;
    private int áˆºÑ¢Õ;
    private Rotations ÂµÈ;
    private Rotations á;
    private Rotations ˆÏ­;
    private Rotations Ø­Ñ¢Ï­Ø­áˆº;
    private Rotations ŒÂ;
    private Rotations Ï­Ï;
    private static final String ŠØ = "CL_00002228";
    
    static {
        HorizonCode_Horizon_È = new Rotations(0.0f, 0.0f, 0.0f);
        Â = new Rotations(0.0f, 0.0f, 0.0f);
        Ý = new Rotations(-10.0f, 0.0f, -10.0f);
        Ø­áŒŠá = new Rotations(-15.0f, 0.0f, 10.0f);
        Âµá€ = new Rotations(-1.0f, 0.0f, -1.0f);
        Ó = new Rotations(1.0f, 0.0f, 1.0f);
    }
    
    public EntityArmorStand(final World worldIn) {
        super(worldIn);
        this.à = new ItemStack[5];
        this.ÂµÈ = EntityArmorStand.HorizonCode_Horizon_È;
        this.á = EntityArmorStand.Â;
        this.ˆÏ­ = EntityArmorStand.Ý;
        this.Ø­Ñ¢Ï­Ø­áˆº = EntityArmorStand.Ø­áŒŠá;
        this.ŒÂ = EntityArmorStand.Âµá€;
        this.Ï­Ï = EntityArmorStand.Ó;
        this.Ø­áŒŠá(true);
        this.ÇªÓ = this.µà();
        this.HorizonCode_Horizon_È(0.5f, 1.975f);
    }
    
    public EntityArmorStand(final World worldIn, final double p_i45855_2_, final double p_i45855_4_, final double p_i45855_6_) {
        this(worldIn);
        this.Ý(p_i45855_2_, p_i45855_4_, p_i45855_6_);
    }
    
    @Override
    public boolean ŠÄ() {
        return super.ŠÄ() && !this.µà();
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(10, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(11, EntityArmorStand.HorizonCode_Horizon_È);
        this.£Ó.HorizonCode_Horizon_È(12, EntityArmorStand.Â);
        this.£Ó.HorizonCode_Horizon_È(13, EntityArmorStand.Ý);
        this.£Ó.HorizonCode_Horizon_È(14, EntityArmorStand.Ø­áŒŠá);
        this.£Ó.HorizonCode_Horizon_È(15, EntityArmorStand.Âµá€);
        this.£Ó.HorizonCode_Horizon_È(16, EntityArmorStand.Ó);
    }
    
    @Override
    public ItemStack Çª() {
        return this.à[0];
    }
    
    @Override
    public ItemStack Ý(final int p_71124_1_) {
        return this.à[p_71124_1_];
    }
    
    @Override
    public ItemStack ÂµÈ(final int p_82169_1_) {
        return this.à[p_82169_1_ + 1];
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn) {
        this.à[slotIn] = itemStackIn;
    }
    
    @Override
    public ItemStack[] Ðƒá() {
        return this.à;
    }
    
    @Override
    public boolean Â(final int p_174820_1_, final ItemStack p_174820_2_) {
        int var3;
        if (p_174820_1_ == 99) {
            var3 = 0;
        }
        else {
            var3 = p_174820_1_ - 100 + 1;
            if (var3 < 0 || var3 >= this.à.length) {
                return false;
            }
        }
        if (p_174820_2_ != null && EntityLiving.Â(p_174820_2_) != var3 && (var3 != 4 || !(p_174820_2_.HorizonCode_Horizon_È() instanceof ItemBlock))) {
            return false;
        }
        this.HorizonCode_Horizon_È(var3, p_174820_2_);
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.à.length; ++var3) {
            final NBTTagCompound var4 = new NBTTagCompound();
            if (this.à[var3] != null) {
                this.à[var3].Â(var4);
            }
            var2.HorizonCode_Horizon_È(var4);
        }
        tagCompound.HorizonCode_Horizon_È("Equipment", var2);
        if (this.áŒŠá€() && (this.Šà() == null || this.Šà().length() == 0)) {
            tagCompound.HorizonCode_Horizon_È("CustomNameVisible", this.áŒŠá€());
        }
        tagCompound.HorizonCode_Horizon_È("Invisible", this.áŒŠÏ());
        tagCompound.HorizonCode_Horizon_È("Small", this.Ø());
        tagCompound.HorizonCode_Horizon_È("ShowArms", this.ˆà());
        tagCompound.HorizonCode_Horizon_È("DisabledSlots", this.áˆºÑ¢Õ);
        tagCompound.HorizonCode_Horizon_È("NoGravity", this.µà());
        tagCompound.HorizonCode_Horizon_È("NoBasePlate", this.¥Æ());
        tagCompound.HorizonCode_Horizon_È("Pose", this.ŒÏ());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("Equipment", 9)) {
            final NBTTagList var2 = tagCompund.Ý("Equipment", 10);
            for (int var3 = 0; var3 < this.à.length; ++var3) {
                this.à[var3] = ItemStack.HorizonCode_Horizon_È(var2.Â(var3));
            }
        }
        this.Ó(tagCompund.£á("Invisible"));
        this.HorizonCode_Horizon_È(tagCompund.£á("Small"));
        this.áˆºÑ¢Õ(tagCompund.£á("ShowArms"));
        this.áˆºÑ¢Õ = tagCompund.Ó("DisabledSlots");
        this.Ý(tagCompund.£á("NoGravity"));
        this.á(tagCompund.£á("NoBasePlate"));
        this.ÇªÓ = this.µà();
        final NBTTagCompound var4 = tagCompund.ˆÏ­("Pose");
        this.Ø(var4);
    }
    
    private void Ø(final NBTTagCompound p_175416_1_) {
        final NBTTagList var2 = p_175416_1_.Ý("Head", 5);
        if (var2.Âµá€() > 0) {
            this.HorizonCode_Horizon_È(new Rotations(var2));
        }
        else {
            this.HorizonCode_Horizon_È(EntityArmorStand.HorizonCode_Horizon_È);
        }
        final NBTTagList var3 = p_175416_1_.Ý("Body", 5);
        if (var3.Âµá€() > 0) {
            this.Â(new Rotations(var3));
        }
        else {
            this.Â(EntityArmorStand.Â);
        }
        final NBTTagList var4 = p_175416_1_.Ý("LeftArm", 5);
        if (var4.Âµá€() > 0) {
            this.Ý(new Rotations(var4));
        }
        else {
            this.Ý(EntityArmorStand.Ý);
        }
        final NBTTagList var5 = p_175416_1_.Ý("RightArm", 5);
        if (var5.Âµá€() > 0) {
            this.Ø­áŒŠá(new Rotations(var5));
        }
        else {
            this.Ø­áŒŠá(EntityArmorStand.Ø­áŒŠá);
        }
        final NBTTagList var6 = p_175416_1_.Ý("LeftLeg", 5);
        if (var6.Âµá€() > 0) {
            this.Âµá€(new Rotations(var6));
        }
        else {
            this.Âµá€(EntityArmorStand.Âµá€);
        }
        final NBTTagList var7 = p_175416_1_.Ý("RightLeg", 5);
        if (var7.Âµá€() > 0) {
            this.Ó(new Rotations(var7));
        }
        else {
            this.Ó(EntityArmorStand.Ó);
        }
    }
    
    private NBTTagCompound ŒÏ() {
        final NBTTagCompound var1 = new NBTTagCompound();
        if (!EntityArmorStand.HorizonCode_Horizon_È.equals(this.ÂµÈ)) {
            var1.HorizonCode_Horizon_È("Head", this.ÂµÈ.HorizonCode_Horizon_È());
        }
        if (!EntityArmorStand.Â.equals(this.á)) {
            var1.HorizonCode_Horizon_È("Body", this.á.HorizonCode_Horizon_È());
        }
        if (!EntityArmorStand.Ý.equals(this.ˆÏ­)) {
            var1.HorizonCode_Horizon_È("LeftArm", this.ˆÏ­.HorizonCode_Horizon_È());
        }
        if (!EntityArmorStand.Ø­áŒŠá.equals(this.Ø­Ñ¢Ï­Ø­áˆº)) {
            var1.HorizonCode_Horizon_È("RightArm", this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È());
        }
        if (!EntityArmorStand.Âµá€.equals(this.ŒÂ)) {
            var1.HorizonCode_Horizon_È("LeftLeg", this.ŒÂ.HorizonCode_Horizon_È());
        }
        if (!EntityArmorStand.Ó.equals(this.Ï­Ï)) {
            var1.HorizonCode_Horizon_È("RightLeg", this.Ï­Ï.HorizonCode_Horizon_È());
        }
        return var1;
    }
    
    @Override
    public boolean £à() {
        return false;
    }
    
    @Override
    protected void £à(final Entity p_82167_1_) {
    }
    
    @Override
    protected void ŠáŒŠà¢() {
        final List var1 = this.Ï­Ðƒà.Â(this, this.£É());
        if (var1 != null && !var1.isEmpty()) {
            for (int var2 = 0; var2 < var1.size(); ++var2) {
                final Entity var3 = var1.get(var2);
                if (var3 instanceof EntityMinecart && ((EntityMinecart)var3).à() == EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È && this.Âµá€(var3) <= 0.2) {
                    var3.Ó(this);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_174825_1_, final Vec3 p_174825_2_) {
        if (this.Ï­Ðƒà.ŠÄ || p_174825_1_.Ø­áŒŠá()) {
            return true;
        }
        byte var3 = 0;
        final ItemStack var4 = p_174825_1_.áŒŠá();
        final boolean var5 = var4 != null;
        if (var5 && var4.HorizonCode_Horizon_È() instanceof ItemArmor) {
            final ItemArmor var6 = (ItemArmor)var4.HorizonCode_Horizon_È();
            if (var6.Ø == 3) {
                var3 = 1;
            }
            else if (var6.Ø == 2) {
                var3 = 2;
            }
            else if (var6.Ø == 1) {
                var3 = 3;
            }
            else if (var6.Ø == 0) {
                var3 = 4;
            }
        }
        if (var5 && (var4.HorizonCode_Horizon_È() == Items.ˆ || var4.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ))) {
            var3 = 4;
        }
        final double var7 = 0.1;
        final double var8 = 0.9;
        final double var9 = 0.4;
        final double var10 = 1.6;
        byte var11 = 0;
        final boolean var12 = this.Ø();
        final double var13 = var12 ? (p_174825_2_.Â * 2.0) : p_174825_2_.Â;
        if (var13 >= 0.1 && var13 < 0.1 + (var12 ? 0.8 : 0.45) && this.à[1] != null) {
            var11 = 1;
        }
        else if (var13 >= 0.9 + (var12 ? 0.3 : 0.0) && var13 < 0.9 + (var12 ? 1.0 : 0.7) && this.à[3] != null) {
            var11 = 3;
        }
        else if (var13 >= 0.4 && var13 < 0.4 + (var12 ? 1.0 : 0.8) && this.à[2] != null) {
            var11 = 2;
        }
        else if (var13 >= 1.6 && this.à[4] != null) {
            var11 = 4;
        }
        final boolean var14 = this.à[var11] != null;
        if ((this.áˆºÑ¢Õ & 1 << var11) != 0x0 || (this.áˆºÑ¢Õ & 1 << var3) != 0x0) {
            var11 = var3;
            if ((this.áˆºÑ¢Õ & 1 << var3) != 0x0) {
                if ((this.áˆºÑ¢Õ & 0x1) != 0x0) {
                    return true;
                }
                var11 = 0;
            }
        }
        if (var5 && var3 == 0 && !this.ˆà()) {
            return true;
        }
        if (var5) {
            this.HorizonCode_Horizon_È(p_174825_1_, var3);
        }
        else if (var14) {
            this.HorizonCode_Horizon_È(p_174825_1_, var11);
        }
        return true;
    }
    
    private void HorizonCode_Horizon_È(final EntityPlayer p_175422_1_, final int p_175422_2_) {
        final ItemStack var3 = this.à[p_175422_2_];
        if ((var3 == null || (this.áˆºÑ¢Õ & 1 << p_175422_2_ + 8) == 0x0) && (var3 != null || (this.áˆºÑ¢Õ & 1 << p_175422_2_ + 16) == 0x0)) {
            final int var4 = p_175422_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý;
            final ItemStack var5 = p_175422_1_.Ø­Ñ¢Ï­Ø­áˆº.á(var4);
            if (p_175422_1_.áˆºáˆºáŠ.Ø­áŒŠá && (var3 == null || var3.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Â)) && var5 != null) {
                final ItemStack var6 = var5.áˆºÑ¢Õ();
                var6.Â = 1;
                this.HorizonCode_Horizon_È(p_175422_2_, var6);
            }
            else if (var5 != null && var5.Â > 1) {
                if (var3 == null) {
                    final ItemStack var6 = var5.áˆºÑ¢Õ();
                    var6.Â = 1;
                    this.HorizonCode_Horizon_È(p_175422_2_, var6);
                    final ItemStack itemStack = var5;
                    --itemStack.Â;
                }
            }
            else {
                this.HorizonCode_Horizon_È(p_175422_2_, var5);
                p_175422_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(var4, var3);
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.Ï­Ðƒà.ŠÄ || this.Ø) {
            return false;
        }
        if (DamageSource.áˆºÑ¢Õ.equals(source)) {
            this.á€();
            return false;
        }
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (source.Ý()) {
            this.Ø­Ñ¢á€();
            this.á€();
            return false;
        }
        if (DamageSource.HorizonCode_Horizon_È.equals(source)) {
            if (!this.ˆÏ()) {
                this.Âµá€(5);
            }
            else {
                this.Ý(0.15f);
            }
            return false;
        }
        if (DamageSource.Ý.equals(source) && this.Ï­Ä() > 0.5f) {
            this.Ý(4.0f);
            return false;
        }
        final boolean var3 = "arrow".equals(source.£à());
        final boolean var4 = "player".equals(source.£à());
        if (!var4 && !var3) {
            return false;
        }
        if (source.áŒŠÆ() instanceof EntityArrow) {
            source.áŒŠÆ().á€();
        }
        if (source.áˆºÑ¢Õ() instanceof EntityPlayer && !((EntityPlayer)source.áˆºÑ¢Õ()).áˆºáˆºáŠ.Âµá€) {
            return false;
        }
        if (source.µÕ()) {
            this.ÇŽÉ();
            this.á€();
            return false;
        }
        final long var5 = this.Ï­Ðƒà.Šáƒ();
        if (var5 - this.áŒŠÆ > 5L && !var3) {
            this.áŒŠÆ = var5;
        }
        else {
            this.Š();
            this.ÇŽÉ();
            this.á€();
        }
        return false;
    }
    
    private void ÇŽÉ() {
        if (this.Ï­Ðƒà instanceof WorldServer) {
            ((WorldServer)this.Ï­Ðƒà).HorizonCode_Horizon_È(EnumParticleTypes.ŠÂµà, this.ŒÏ, this.Çªà¢ + this.£ÂµÄ / 1.5, this.Ê, 10, this.áŒŠ / 4.0f, this.£ÂµÄ / 4.0f, this.áŒŠ / 4.0f, 0.05, Block.HorizonCode_Horizon_È(Blocks.à.¥à()));
        }
    }
    
    private void Ý(final float p_175406_1_) {
        float var2 = this.Ï­Ä();
        var2 -= p_175406_1_;
        if (var2 <= 0.5f) {
            this.Ø­Ñ¢á€();
            this.á€();
        }
        else {
            this.áˆºÑ¢Õ(var2);
        }
    }
    
    private void Š() {
        Block.HorizonCode_Horizon_È(this.Ï­Ðƒà, new BlockPos(this), new ItemStack(Items.¥Ðƒá));
        this.Ø­Ñ¢á€();
    }
    
    private void Ø­Ñ¢á€() {
        for (int var1 = 0; var1 < this.à.length; ++var1) {
            if (this.à[var1] != null && this.à[var1].Â > 0) {
                if (this.à[var1] != null) {
                    Block.HorizonCode_Horizon_È(this.Ï­Ðƒà, new BlockPos(this).Ø­áŒŠá(), this.à[var1]);
                }
                this.à[var1] = null;
            }
        }
    }
    
    @Override
    protected float à(final float p_110146_1_, final float p_110146_2_) {
        this.£ÇªÓ = this.á€;
        this.¥É = this.É;
        return 0.0f;
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.h_() ? (this.£ÂµÄ * 0.5f) : (this.£ÂµÄ * 0.9f);
    }
    
    @Override
    public void Ó(final float p_70612_1_, final float p_70612_2_) {
        if (!this.µà()) {
            super.Ó(p_70612_1_, p_70612_2_);
        }
    }
    
    @Override
    public void á() {
        super.á();
        final Rotations var1 = this.£Ó.à(11);
        if (!this.ÂµÈ.equals(var1)) {
            this.HorizonCode_Horizon_È(var1);
        }
        final Rotations var2 = this.£Ó.à(12);
        if (!this.á.equals(var2)) {
            this.Â(var2);
        }
        final Rotations var3 = this.£Ó.à(13);
        if (!this.ˆÏ­.equals(var3)) {
            this.Ý(var3);
        }
        final Rotations var4 = this.£Ó.à(14);
        if (!this.Ø­Ñ¢Ï­Ø­áˆº.equals(var4)) {
            this.Ø­áŒŠá(var4);
        }
        final Rotations var5 = this.£Ó.à(15);
        if (!this.ŒÂ.equals(var5)) {
            this.Âµá€(var5);
        }
        final Rotations var6 = this.£Ó.à(16);
        if (!this.Ï­Ï.equals(var6)) {
            this.Ó(var6);
        }
    }
    
    @Override
    protected void ÇªÂ() {
        this.Ó(this.Ø);
    }
    
    @Override
    public void Ó(final boolean invisible) {
        super.Ó(this.Ø = invisible);
    }
    
    @Override
    public boolean h_() {
        return this.Ø();
    }
    
    @Override
    public void ÇŽÕ() {
        this.á€();
    }
    
    @Override
    public boolean ÂµÕ() {
        return this.áŒŠÏ();
    }
    
    private void HorizonCode_Horizon_È(final boolean p_175420_1_) {
        byte var2 = this.£Ó.HorizonCode_Horizon_È(10);
        if (p_175420_1_) {
            var2 |= 0x1;
        }
        else {
            var2 &= 0xFFFFFFFE;
        }
        this.£Ó.Â(10, var2);
    }
    
    public boolean Ø() {
        return (this.£Ó.HorizonCode_Horizon_È(10) & 0x1) != 0x0;
    }
    
    private void Ý(final boolean p_175425_1_) {
        byte var2 = this.£Ó.HorizonCode_Horizon_È(10);
        if (p_175425_1_) {
            var2 |= 0x2;
        }
        else {
            var2 &= 0xFFFFFFFD;
        }
        this.£Ó.Â(10, var2);
    }
    
    public boolean µà() {
        return (this.£Ó.HorizonCode_Horizon_È(10) & 0x2) != 0x0;
    }
    
    private void áˆºÑ¢Õ(final boolean p_175413_1_) {
        byte var2 = this.£Ó.HorizonCode_Horizon_È(10);
        if (p_175413_1_) {
            var2 |= 0x4;
        }
        else {
            var2 &= 0xFFFFFFFB;
        }
        this.£Ó.Â(10, var2);
    }
    
    public boolean ˆà() {
        return (this.£Ó.HorizonCode_Horizon_È(10) & 0x4) != 0x0;
    }
    
    private void á(final boolean p_175426_1_) {
        byte var2 = this.£Ó.HorizonCode_Horizon_È(10);
        if (p_175426_1_) {
            var2 |= 0x8;
        }
        else {
            var2 &= 0xFFFFFFF7;
        }
        this.£Ó.Â(10, var2);
    }
    
    public boolean ¥Æ() {
        return (this.£Ó.HorizonCode_Horizon_È(10) & 0x8) != 0x0;
    }
    
    public void HorizonCode_Horizon_È(final Rotations p_175415_1_) {
        this.ÂµÈ = p_175415_1_;
        this.£Ó.Â(11, p_175415_1_);
    }
    
    public void Â(final Rotations p_175424_1_) {
        this.á = p_175424_1_;
        this.£Ó.Â(12, p_175424_1_);
    }
    
    public void Ý(final Rotations p_175405_1_) {
        this.ˆÏ­ = p_175405_1_;
        this.£Ó.Â(13, p_175405_1_);
    }
    
    public void Ø­áŒŠá(final Rotations p_175428_1_) {
        this.Ø­Ñ¢Ï­Ø­áˆº = p_175428_1_;
        this.£Ó.Â(14, p_175428_1_);
    }
    
    public void Âµá€(final Rotations p_175417_1_) {
        this.ŒÂ = p_175417_1_;
        this.£Ó.Â(15, p_175417_1_);
    }
    
    public void Ó(final Rotations p_175427_1_) {
        this.Ï­Ï = p_175427_1_;
        this.£Ó.Â(16, p_175427_1_);
    }
    
    public Rotations Ø­à() {
        return this.ÂµÈ;
    }
    
    public Rotations µÕ() {
        return this.á;
    }
    
    public Rotations Æ() {
        return this.ˆÏ­;
    }
    
    public Rotations Šáƒ() {
        return this.Ø­Ñ¢Ï­Ø­áˆº;
    }
    
    public Rotations Ï­Ðƒà() {
        return this.ŒÂ;
    }
    
    public Rotations Ñ¢á() {
        return this.Ï­Ï;
    }
}
