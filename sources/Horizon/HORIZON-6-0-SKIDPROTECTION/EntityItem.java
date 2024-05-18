package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityItem extends Entity
{
    private static final Logger Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private String Ó;
    private String à;
    public float HorizonCode_Horizon_È;
    private static final String Ø = "CL_00001669";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public EntityItem(final World worldIn, final double x, final double y, final double z) {
        super(worldIn);
        this.Âµá€ = 5;
        this.HorizonCode_Horizon_È = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.Ý(x, y, z);
        this.É = (float)(Math.random() * 360.0);
        this.ÇŽÉ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.ˆá = 0.20000000298023224;
        this.ÇŽÕ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public EntityItem(final World worldIn, final double x, final double y, final double z, final ItemStack stack) {
        this(worldIn, x, y, z);
        this.HorizonCode_Horizon_È(stack);
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    public EntityItem(final World worldIn) {
        super(worldIn);
        this.Âµá€ = 5;
        this.HorizonCode_Horizon_È = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Â, 0));
    }
    
    @Override
    protected void ÂµÈ() {
        this.É().HorizonCode_Horizon_È(10, 5);
    }
    
    @Override
    public void á() {
        if (this.Ø() == null) {
            this.á€();
        }
        else {
            super.á();
            if (this.Ø­áŒŠá > 0 && this.Ø­áŒŠá != 32767) {
                --this.Ø­áŒŠá;
            }
            this.áŒŠà = this.ŒÏ;
            this.ŠÄ = this.Çªà¢;
            this.Ñ¢á = this.Ê;
            this.ˆá -= 0.03999999910593033;
            this.ÇªÓ = this.Â(this.ŒÏ, (this.£É().Â + this.£É().Âµá€) / 2.0, this.Ê);
            this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
            final boolean var1 = (int)this.áŒŠà != (int)this.ŒÏ || (int)this.ŠÄ != (int)this.Çªà¢ || (int)this.Ñ¢á != (int)this.Ê;
            if (var1 || this.Œ % 25 == 0) {
                if (this.Ï­Ðƒà.Â(new BlockPos(this)).Ý().Ó() == Material.áŒŠÆ) {
                    this.ˆá = 0.20000000298023224;
                    this.ÇŽÉ = (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f;
                    this.ÇŽÕ = (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f;
                    this.HorizonCode_Horizon_È("random.fizz", 0.4f, 2.0f + this.ˆáƒ.nextFloat() * 0.4f);
                }
                if (!this.Ï­Ðƒà.ŠÄ) {
                    this.Ï­Ðƒà();
                }
            }
            float var2 = 0.98f;
            if (this.ŠÂµà) {
                var2 = this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.£É().Â) - 1, MathHelper.Ý(this.Ê))).Ý().áƒ * 0.98f;
            }
            this.ÇŽÉ *= var2;
            this.ˆá *= 0.9800000190734863;
            this.ÇŽÕ *= var2;
            if (this.ŠÂµà) {
                this.ˆá *= -0.5;
            }
            if (this.Ý != -32768) {
                ++this.Ý;
            }
            this.Ø­Âµ();
            if (!this.Ï­Ðƒà.ŠÄ && this.Ý >= 6000) {
                this.á€();
            }
        }
    }
    
    private void Ï­Ðƒà() {
        for (final EntityItem var2 : this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityItem.class, this.£É().Â(0.5, 0.0, 0.5))) {
            this.HorizonCode_Horizon_È(var2);
        }
    }
    
    private boolean HorizonCode_Horizon_È(final EntityItem other) {
        if (other == this) {
            return false;
        }
        if (!other.Œ() || !this.Œ()) {
            return false;
        }
        final ItemStack var2 = this.Ø();
        final ItemStack var3 = other.Ø();
        if (this.Ø­áŒŠá == 32767 || other.Ø­áŒŠá == 32767) {
            return false;
        }
        if (this.Ý == -32768 || other.Ý == -32768) {
            return false;
        }
        if (var3.HorizonCode_Horizon_È() != var2.HorizonCode_Horizon_È()) {
            return false;
        }
        if (var3.£á() ^ var2.£á()) {
            return false;
        }
        if (var3.£á() && !var3.Å().equals(var2.Å())) {
            return false;
        }
        if (var3.HorizonCode_Horizon_È() == null) {
            return false;
        }
        if (var3.HorizonCode_Horizon_È().Â() && var3.Ø() != var2.Ø()) {
            return false;
        }
        if (var3.Â < var2.Â) {
            return other.HorizonCode_Horizon_È(this);
        }
        if (var3.Â + var2.Â > var3.Â()) {
            return false;
        }
        final ItemStack itemStack = var3;
        itemStack.Â += var2.Â;
        other.Ø­áŒŠá = Math.max(other.Ø­áŒŠá, this.Ø­áŒŠá);
        other.Ý = Math.min(other.Ý, this.Ý);
        other.HorizonCode_Horizon_È(var3);
        this.á€();
        return true;
    }
    
    public void à() {
        this.Ý = 4800;
    }
    
    @Override
    public boolean Ø­Âµ() {
        if (this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), Material.Ø, this)) {
            if (!this.Ø­á && !this.Ï­Ï­Ï) {
                this.Ä();
            }
            this.Ø­á = true;
        }
        else {
            this.Ø­á = false;
        }
        return this.Ø­á;
    }
    
    @Override
    protected void Ó(final int amount) {
        this.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È, amount);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (this.Ø() != null && this.Ø().HorizonCode_Horizon_È() == Items.áˆºá && source.Ý()) {
            return false;
        }
        this.Ï();
        this.Âµá€ -= (int)amount;
        if (this.Âµá€ <= 0) {
            this.á€();
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("Health", (short)(byte)this.Âµá€);
        tagCompound.HorizonCode_Horizon_È("Age", (short)this.Ý);
        tagCompound.HorizonCode_Horizon_È("PickupDelay", (short)this.Ø­áŒŠá);
        if (this.ˆÏ­() != null) {
            tagCompound.HorizonCode_Horizon_È("Thrower", this.Ó);
        }
        if (this.áŒŠÆ() != null) {
            tagCompound.HorizonCode_Horizon_È("Owner", this.à);
        }
        if (this.Ø() != null) {
            tagCompound.HorizonCode_Horizon_È("Item", this.Ø().Â(new NBTTagCompound()));
        }
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.Âµá€ = (tagCompund.Âµá€("Health") & 0xFF);
        this.Ý = tagCompund.Âµá€("Age");
        if (tagCompund.Ý("PickupDelay")) {
            this.Ø­áŒŠá = tagCompund.Âµá€("PickupDelay");
        }
        if (tagCompund.Ý("Owner")) {
            this.à = tagCompund.áˆºÑ¢Õ("Owner");
        }
        if (tagCompund.Ý("Thrower")) {
            this.Ó = tagCompund.áˆºÑ¢Õ("Thrower");
        }
        final NBTTagCompound var2 = tagCompund.ˆÏ­("Item");
        this.HorizonCode_Horizon_È(ItemStack.HorizonCode_Horizon_È(var2));
        if (this.Ø() == null) {
            this.á€();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer entityIn) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            final ItemStack var2 = this.Ø();
            final int var3 = var2.Â;
            if (this.Ø­áŒŠá == 0 && (this.à == null || 6000 - this.Ý <= 200 || this.à.equals(entityIn.v_())) && entityIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var2)) {
                if (var2.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.¥Æ)) {
                    entityIn.HorizonCode_Horizon_È(AchievementList.à);
                }
                if (var2.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­à)) {
                    entityIn.HorizonCode_Horizon_È(AchievementList.à);
                }
                if (var2.HorizonCode_Horizon_È() == Items.£áŒŠá) {
                    entityIn.HorizonCode_Horizon_È(AchievementList.Ø­à);
                }
                if (var2.HorizonCode_Horizon_È() == Items.áŒŠÆ) {
                    entityIn.HorizonCode_Horizon_È(AchievementList.Šáƒ);
                }
                if (var2.HorizonCode_Horizon_È() == Items.Çªà) {
                    entityIn.HorizonCode_Horizon_È(AchievementList.Ñ¢á);
                }
                if (var2.HorizonCode_Horizon_È() == Items.áŒŠÆ && this.ˆÏ­() != null) {
                    final EntityPlayer var4 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ˆÏ­());
                    if (var4 != null && var4 != entityIn) {
                        var4.HorizonCode_Horizon_È(AchievementList.Ï­Ðƒà);
                    }
                }
                if (!this.áŠ()) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È((Entity)entityIn, "random.pop", 0.2f, ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                }
                entityIn.Â(this, var3);
                if (var2.Â <= 0) {
                    this.á€();
                }
            }
        }
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.Šà() : StatCollector.HorizonCode_Horizon_È("item." + this.Ø().ÂµÈ());
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public void áŒŠÆ(final int dimensionId) {
        super.áŒŠÆ(dimensionId);
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.Ï­Ðƒà();
        }
    }
    
    public ItemStack Ø() {
        final ItemStack var1 = this.É().Ó(10);
        if (var1 == null) {
            if (this.Ï­Ðƒà != null) {
                EntityItem.Â.error("Item entity " + this.ˆá() + " has no item?!");
            }
            return new ItemStack(Blocks.Ý);
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final ItemStack stack) {
        this.É().Â(10, stack);
        this.É().Ø(10);
    }
    
    public String áŒŠÆ() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final String owner) {
        this.à = owner;
    }
    
    public String ˆÏ­() {
        return this.Ó;
    }
    
    public void Â(final String thrower) {
        this.Ó = thrower;
    }
    
    public int µà() {
        return this.Ý;
    }
    
    public void ˆà() {
        this.Ø­áŒŠá = 10;
    }
    
    public void ¥Æ() {
        this.Ø­áŒŠá = 0;
    }
    
    public void Ø­à() {
        this.Ø­áŒŠá = 32767;
    }
    
    public void HorizonCode_Horizon_È(final int ticks) {
        this.Ø­áŒŠá = ticks;
    }
    
    public boolean µÕ() {
        return this.Ø­áŒŠá > 0;
    }
    
    public void Æ() {
        this.Ý = -6000;
    }
    
    public void Šáƒ() {
        this.Ø­à();
        this.Ý = 5999;
    }
}
