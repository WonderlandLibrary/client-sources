package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderWither extends RenderLiving
{
    private int field_82419_a;
    
    public RenderWither() {
        super(new ModelWither(), 1.0f);
        this.field_82419_a = ((ModelWither)this.mainModel).func_82903_a();
    }
    
    public void func_82418_a(final EntityWither par1EntityWither, final double par2, final double par4, final double par6, final float par8, final float par9) {
        BossStatus.func_82824_a(par1EntityWither, true);
        final int var10 = ((ModelWither)this.mainModel).func_82903_a();
        if (var10 != this.field_82419_a) {
            this.field_82419_a = var10;
            this.mainModel = new ModelWither();
        }
        super.doRenderLiving(par1EntityWither, par2, par4, par6, par8, par9);
    }
    
    protected void func_82415_a(final EntityWither par1EntityWither, final float par2) {
        final int var3 = par1EntityWither.func_82212_n();
        if (var3 > 0) {
            final float var4 = 2.0f - (var3 - par2) / 220.0f * 0.5f;
            GL11.glScalef(var4, var4, var4);
        }
        else {
            GL11.glScalef(2.0f, 2.0f, 2.0f);
        }
    }
    
    protected int func_82417_a(final EntityWither par1EntityWither, final int par2, final float par3) {
        if (par1EntityWither.isArmored()) {
            if (par1EntityWither.isInvisible()) {
                GL11.glDepthMask(false);
            }
            else {
                GL11.glDepthMask(true);
            }
            if (par2 == 1) {
                final float var4 = par1EntityWither.ticksExisted + par3;
                this.loadTexture("/armor/witherarmor.png");
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                final float var5 = MathHelper.cos(var4 * 0.02f) * 3.0f;
                final float var6 = var4 * 0.01f;
                GL11.glTranslatef(var5, var6, 0.0f);
                this.setRenderPassModel(this.mainModel);
                GL11.glMatrixMode(5888);
                GL11.glEnable(3042);
                final float var7 = 0.5f;
                GL11.glColor4f(var7, var7, var7, 1.0f);
                GL11.glDisable(2896);
                GL11.glBlendFunc(1, 1);
                GL11.glTranslatef(0.0f, -0.01f, 0.0f);
                GL11.glScalef(1.1f, 1.1f, 1.1f);
                return 1;
            }
            if (par2 == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
            }
        }
        return -1;
    }
    
    protected int func_82416_b(final EntityWither par1EntityWither, final int par2, final float par3) {
        return -1;
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.func_82415_a((EntityWither)par1EntityLiving, par2);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.func_82417_a((EntityWither)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected int inheritRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.func_82416_b((EntityWither)par1EntityLiving, par2, par3);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82418_a((EntityWither)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82418_a((EntityWither)par1Entity, par2, par4, par6, par8, par9);
    }
}
