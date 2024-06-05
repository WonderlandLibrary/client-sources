package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class RenderWitch extends RenderLiving
{
    private ModelWitch field_82414_a;
    private int field_82413_f;
    
    public RenderWitch() {
        super(new ModelWitch(0.0f), 0.5f);
        this.field_82414_a = (ModelWitch)this.mainModel;
        this.field_82413_f = this.field_82414_a.func_82899_a();
    }
    
    public void func_82412_a(final EntityWitch par1EntityWitch, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final ItemStack var10 = par1EntityWitch.getHeldItem();
        if (this.field_82414_a.func_82899_a() != this.field_82413_f) {
            Minecraft.getMinecraft().getLogAgent().logInfo("Loaded new witch model");
            final ModelWitch modelWitch = new ModelWitch(0.0f);
            this.field_82414_a = modelWitch;
            this.mainModel = modelWitch;
            this.field_82413_f = this.field_82414_a.func_82899_a();
        }
        this.field_82414_a.field_82900_g = (var10 != null);
        super.doRenderLiving(par1EntityWitch, par2, par4, par6, par8, par9);
    }
    
    protected void func_82411_a(final EntityWitch par1EntityWitch, final float par2) {
        final float var3 = 1.0f;
        GL11.glColor3f(var3, var3, var3);
        super.renderEquippedItems(par1EntityWitch, par2);
        final ItemStack var4 = par1EntityWitch.getHeldItem();
        if (var4 != null) {
            GL11.glPushMatrix();
            if (this.mainModel.isChild) {
                final float var5 = 0.5f;
                GL11.glTranslatef(0.0f, 0.625f, 0.0f);
                GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
                GL11.glScalef(var5, var5, var5);
            }
            this.field_82414_a.field_82898_f.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.53125f, 0.21875f);
            if (var4.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType())) {
                float var5 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var5 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var5, -var5, var5);
            }
            else if (var4.itemID == Item.bow.itemID) {
                final float var5 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var5, -var5, var5);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (Item.itemsList[var4.itemID].isFull3D()) {
                final float var5 = 0.625f;
                if (Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                this.func_82410_b();
                GL11.glScalef(var5, -var5, var5);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var5 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var5, var5, var5);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glRotatef(-15.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(40.0f, 0.0f, 0.0f, 1.0f);
            this.renderManager.itemRenderer.renderItem(par1EntityWitch, var4, 0);
            if (var4.getItem().requiresMultipleRenderPasses()) {
                this.renderManager.itemRenderer.renderItem(par1EntityWitch, var4, 1);
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void func_82410_b() {
        GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
    }
    
    protected void func_82409_b(final EntityWitch par1EntityWitch, final float par2) {
        final float var3 = 0.9375f;
        GL11.glScalef(var3, var3, var3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.func_82409_b((EntityWitch)par1EntityLiving, par2);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.func_82411_a((EntityWitch)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82412_a((EntityWitch)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82412_a((EntityWitch)par1Entity, par2, par4, par6, par8, par9);
    }
}
