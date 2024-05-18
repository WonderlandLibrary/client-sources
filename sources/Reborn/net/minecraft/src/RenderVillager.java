package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderVillager extends RenderLiving
{
    protected ModelVillager villagerModel;
    
    public RenderVillager() {
        super(new ModelVillager(0.0f), 0.5f);
        this.villagerModel = (ModelVillager)this.mainModel;
    }
    
    protected int shouldVillagerRenderPass(final EntityVillager par1EntityVillager, final int par2, final float par3) {
        return -1;
    }
    
    public void renderVillager(final EntityVillager par1EntityVillager, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityVillager, par2, par4, par6, par8, par9);
    }
    
    protected void renderVillagerEquipedItems(final EntityVillager par1EntityVillager, final float par2) {
        super.renderEquippedItems(par1EntityVillager, par2);
    }
    
    protected void preRenderVillager(final EntityVillager par1EntityVillager, final float par2) {
        float var3 = 0.9375f;
        if (par1EntityVillager.getGrowingAge() < 0) {
            var3 *= 0.5;
            this.shadowSize = 0.25f;
        }
        else {
            this.shadowSize = 0.5f;
        }
        GL11.glScalef(var3, var3, var3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.preRenderVillager((EntityVillager)par1EntityLiving, par2);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.shouldVillagerRenderPass((EntityVillager)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderVillagerEquipedItems((EntityVillager)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderVillager((EntityVillager)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderVillager((EntityVillager)par1Entity, par2, par4, par6, par8, par9);
    }
}
