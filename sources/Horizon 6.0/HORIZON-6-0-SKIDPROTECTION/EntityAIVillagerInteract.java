package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2
{
    private int Âµá€;
    private EntityVillager Ó;
    private static final String à = "CL_00002251";
    
    public EntityAIVillagerInteract(final EntityVillager p_i45886_1_) {
        super(p_i45886_1_, EntityVillager.class, 3.0f, 0.02f);
        this.Ó = p_i45886_1_;
    }
    
    @Override
    public void Âµá€() {
        super.Âµá€();
        if (this.Ó.ŒÐƒà() && this.Â instanceof EntityVillager && ((EntityVillager)this.Â).ÐƒáˆºÄ()) {
            this.Âµá€ = 10;
        }
        else {
            this.Âµá€ = 0;
        }
    }
    
    @Override
    public void Ø­áŒŠá() {
        super.Ø­áŒŠá();
        if (this.Âµá€ > 0) {
            --this.Âµá€;
            if (this.Âµá€ == 0) {
                final InventoryBasic var1 = this.Ó.ÐƒÓ();
                for (int var2 = 0; var2 < var1.áŒŠÆ(); ++var2) {
                    final ItemStack var3 = var1.á(var2);
                    ItemStack var4 = null;
                    if (var3 != null) {
                        final Item_1028566121 var5 = var3.HorizonCode_Horizon_È();
                        if ((var5 == Items.Ç || var5 == Items.ˆÂ || var5 == Items.¥áŒŠà) && var3.Â > 3) {
                            final int var6 = var3.Â / 2;
                            final ItemStack itemStack = var3;
                            itemStack.Â -= var6;
                            var4 = new ItemStack(var5, var6, var3.Ø());
                        }
                        else if (var5 == Items.Âµà && var3.Â > 5) {
                            final int var6 = var3.Â / 2 / 3 * 3;
                            final int var7 = var6 / 3;
                            final ItemStack itemStack2 = var3;
                            itemStack2.Â -= var6;
                            var4 = new ItemStack(Items.Ç, var7, 0);
                        }
                        if (var3.Â <= 0) {
                            var1.Ý(var2, null);
                        }
                    }
                    if (var4 != null) {
                        final double var8 = this.Ó.Çªà¢ - 0.30000001192092896 + this.Ó.Ðƒáƒ();
                        final EntityItem var9 = new EntityItem(this.Ó.Ï­Ðƒà, this.Ó.ŒÏ, var8, this.Ó.Ê, var4);
                        final float var10 = 0.3f;
                        final float var11 = this.Ó.ÂµÕ;
                        final float var12 = this.Ó.áƒ;
                        var9.ÇŽÉ = -MathHelper.HorizonCode_Horizon_È(var11 / 180.0f * 3.1415927f) * MathHelper.Â(var12 / 180.0f * 3.1415927f) * var10;
                        var9.ÇŽÕ = MathHelper.Â(var11 / 180.0f * 3.1415927f) * MathHelper.Â(var12 / 180.0f * 3.1415927f) * var10;
                        var9.ˆá = -MathHelper.HorizonCode_Horizon_È(var12 / 180.0f * 3.1415927f) * var10 + 0.1f;
                        var9.ˆà();
                        this.Ó.Ï­Ðƒà.HorizonCode_Horizon_È(var9);
                        break;
                    }
                }
            }
        }
    }
}
