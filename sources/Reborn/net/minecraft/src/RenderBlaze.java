package net.minecraft.src;

public class RenderBlaze extends RenderLiving
{
    private int field_77068_a;
    
    public RenderBlaze() {
        super(new ModelBlaze(), 0.5f);
        this.field_77068_a = ((ModelBlaze)this.mainModel).func_78104_a();
    }
    
    public void renderBlaze(final EntityBlaze par1EntityBlaze, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final int var10 = ((ModelBlaze)this.mainModel).func_78104_a();
        if (var10 != this.field_77068_a) {
            this.field_77068_a = var10;
            this.mainModel = new ModelBlaze();
        }
        super.doRenderLiving(par1EntityBlaze, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderBlaze((EntityBlaze)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderBlaze((EntityBlaze)par1Entity, par2, par4, par6, par8, par9);
    }
}
