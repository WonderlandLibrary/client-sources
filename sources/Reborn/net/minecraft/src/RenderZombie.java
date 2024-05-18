package net.minecraft.src;

public class RenderZombie extends RenderBiped
{
    private ModelBiped field_82434_o;
    private ModelZombieVillager field_82432_p;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    private int field_82431_q;
    
    public RenderZombie() {
        super(new ModelZombie(), 0.5f, 1.0f);
        this.field_82431_q = 1;
        this.field_82434_o = this.modelBipedMain;
        this.field_82432_p = new ModelZombieVillager();
    }
    
    @Override
    protected void func_82421_b() {
        this.field_82423_g = new ModelZombie(1.0f, true);
        this.field_82425_h = new ModelZombie(0.5f, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
        this.field_82436_m = new ModelZombieVillager(1.0f, 0.0f, true);
        this.field_82433_n = new ModelZombieVillager(0.5f, 0.0f, true);
    }
    
    protected int func_82429_a(final EntityZombie par1EntityZombie, final int par2, final float par3) {
        this.func_82427_a(par1EntityZombie);
        return super.shouldRenderPass(par1EntityZombie, par2, par3);
    }
    
    public void func_82426_a(final EntityZombie par1EntityZombie, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82427_a(par1EntityZombie);
        super.doRenderLiving(par1EntityZombie, par2, par4, par6, par8, par9);
    }
    
    protected void func_82428_a(final EntityZombie par1EntityZombie, final float par2) {
        this.func_82427_a(par1EntityZombie);
        super.renderEquippedItems(par1EntityZombie, par2);
    }
    
    private void func_82427_a(final EntityZombie par1EntityZombie) {
        if (par1EntityZombie.isVillager()) {
            if (this.field_82431_q != this.field_82432_p.func_82897_a()) {
                this.field_82432_p = new ModelZombieVillager();
                this.field_82431_q = this.field_82432_p.func_82897_a();
                this.field_82436_m = new ModelZombieVillager(1.0f, 0.0f, true);
                this.field_82433_n = new ModelZombieVillager(0.5f, 0.0f, true);
            }
            this.mainModel = this.field_82432_p;
            this.field_82423_g = this.field_82436_m;
            this.field_82425_h = this.field_82433_n;
        }
        else {
            this.mainModel = this.field_82434_o;
            this.field_82423_g = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }
        this.modelBipedMain = (ModelBiped)this.mainModel;
    }
    
    protected void func_82430_a(final EntityZombie par1EntityZombie, final float par2, float par3, final float par4) {
        if (par1EntityZombie.isConverting()) {
            par3 += (float)(Math.cos(par1EntityZombie.ticksExisted * 3.25) * 3.141592653589793 * 0.25);
        }
        super.rotateCorpse(par1EntityZombie, par2, par3, par4);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.func_82428_a((EntityZombie)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82426_a((EntityZombie)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.func_82429_a((EntityZombie)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.func_82430_a((EntityZombie)par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82426_a((EntityZombie)par1Entity, par2, par4, par6, par8, par9);
    }
}
