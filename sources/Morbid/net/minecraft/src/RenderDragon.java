package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;

public class RenderDragon extends RenderLiving
{
    private static int updateModelState;
    protected ModelDragon modelDragon;
    
    static {
        RenderDragon.updateModelState = 0;
    }
    
    public RenderDragon() {
        super(new ModelDragon(0.0f), 0.5f);
        this.modelDragon = (ModelDragon)this.mainModel;
        this.setRenderPassModel(this.mainModel);
    }
    
    protected void rotateDragonBody(final EntityDragon par1EntityDragon, final float par2, final float par3, final float par4) {
        final float var5 = (float)par1EntityDragon.getMovementOffsets(7, par4)[0];
        final float var6 = (float)(par1EntityDragon.getMovementOffsets(5, par4)[1] - par1EntityDragon.getMovementOffsets(10, par4)[1]);
        GL11.glRotatef(-var5, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(var6 * 10.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, 1.0f);
        if (par1EntityDragon.deathTime > 0) {
            float var7 = (par1EntityDragon.deathTime + par4 - 1.0f) / 20.0f * 1.6f;
            var7 = MathHelper.sqrt_float(var7);
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            GL11.glRotatef(var7 * this.getDeathMaxRotation(par1EntityDragon), 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected void renderDragonModel(final EntityDragon par1EntityDragon, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        if (par1EntityDragon.deathTicks > 0) {
            final float var8 = par1EntityDragon.deathTicks / 200.0f;
            GL11.glDepthFunc(515);
            GL11.glEnable(3008);
            GL11.glAlphaFunc(516, var8);
            this.loadTexture("/mob/enderdragon/shuffle.png");
            this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glAlphaFunc(516, 0.1f);
            GL11.glDepthFunc(514);
        }
        this.loadTexture(par1EntityDragon.getTexture());
        this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
        if (par1EntityDragon.hurtTime > 0) {
            GL11.glDepthFunc(514);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
            this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDepthFunc(515);
        }
    }
    
    public void renderDragon(final EntityDragon par1EntityDragon, final double par2, final double par4, final double par6, final float par8, final float par9) {
        BossStatus.func_82824_a(par1EntityDragon, false);
        if (RenderDragon.updateModelState != 4) {
            this.mainModel = new ModelDragon(0.0f);
            RenderDragon.updateModelState = 4;
        }
        super.doRenderLiving(par1EntityDragon, par2, par4, par6, par8, par9);
        if (par1EntityDragon.healingEnderCrystal != null) {
            final float var10 = par1EntityDragon.healingEnderCrystal.innerRotation + par9;
            float var11 = MathHelper.sin(var10 * 0.2f) / 2.0f + 0.5f;
            var11 = (var11 * var11 + var11) * 0.2f;
            final float var12 = (float)(par1EntityDragon.healingEnderCrystal.posX - par1EntityDragon.posX - (par1EntityDragon.prevPosX - par1EntityDragon.posX) * (1.0f - par9));
            final float var13 = (float)(var11 + par1EntityDragon.healingEnderCrystal.posY - 1.0 - par1EntityDragon.posY - (par1EntityDragon.prevPosY - par1EntityDragon.posY) * (1.0f - par9));
            final float var14 = (float)(par1EntityDragon.healingEnderCrystal.posZ - par1EntityDragon.posZ - (par1EntityDragon.prevPosZ - par1EntityDragon.posZ) * (1.0f - par9));
            final float var15 = MathHelper.sqrt_float(var12 * var12 + var14 * var14);
            final float var16 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4 + 2.0f, (float)par6);
            GL11.glRotatef((float)(-Math.atan2(var14, var12)) * 180.0f / 3.1415927f - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(-Math.atan2(var15, var13)) * 180.0f / 3.1415927f - 90.0f, 1.0f, 0.0f, 0.0f);
            final Tessellator var17 = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2884);
            this.loadTexture("/mob/enderdragon/beam.png");
            GL11.glShadeModel(7425);
            final float var18 = 0.0f - (par1EntityDragon.ticksExisted + par9) * 0.01f;
            final float var19 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14) / 32.0f - (par1EntityDragon.ticksExisted + par9) * 0.01f;
            var17.startDrawing(5);
            final byte var20 = 8;
            for (int var21 = 0; var21 <= var20; ++var21) {
                final float var22 = MathHelper.sin(var21 % var20 * 3.1415927f * 2.0f / var20) * 0.75f;
                final float var23 = MathHelper.cos(var21 % var20 * 3.1415927f * 2.0f / var20) * 0.75f;
                final float var24 = var21 % var20 * 1.0f / var20;
                var17.setColorOpaque_I(0);
                var17.addVertexWithUV(var22 * 0.2f, var23 * 0.2f, 0.0, var24, var19);
                var17.setColorOpaque_I(16777215);
                var17.addVertexWithUV(var22, var23, var16, var24, var18);
            }
            var17.draw();
            GL11.glEnable(2884);
            GL11.glShadeModel(7424);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }
    
    protected void renderDragonDying(final EntityDragon par1EntityDragon, final float par2) {
        super.renderEquippedItems(par1EntityDragon, par2);
        final Tessellator var3 = Tessellator.instance;
        if (par1EntityDragon.deathTicks > 0) {
            RenderHelper.disableStandardItemLighting();
            final float var4 = (par1EntityDragon.deathTicks + par2) / 200.0f;
            float var5 = 0.0f;
            if (var4 > 0.8f) {
                var5 = (var4 - 0.8f) / 0.2f;
            }
            final Random var6 = new Random(432L);
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDisable(3008);
            GL11.glEnable(2884);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, -1.0f, -2.0f);
            for (int var7 = 0; var7 < (var4 + var4 * var4) / 2.0f * 60.0f; ++var7) {
                GL11.glRotatef(var6.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f + var4 * 90.0f, 0.0f, 0.0f, 1.0f);
                var3.startDrawing(6);
                final float var8 = var6.nextFloat() * 20.0f + 5.0f + var5 * 10.0f;
                final float var9 = var6.nextFloat() * 2.0f + 1.0f + var5 * 2.0f;
                var3.setColorRGBA_I(16777215, (int)(255.0f * (1.0f - var5)));
                var3.addVertex(0.0, 0.0, 0.0);
                var3.setColorRGBA_I(16711935, 0);
                var3.addVertex(-0.866 * var9, var8, -0.5f * var9);
                var3.addVertex(0.866 * var9, var8, -0.5f * var9);
                var3.addVertex(0.0, var8, 1.0f * var9);
                var3.addVertex(-0.866 * var9, var8, -0.5f * var9);
                var3.draw();
            }
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glShadeModel(7424);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    protected int renderGlow(final EntityDragon par1EntityDragon, final int par2, final float par3) {
        if (par2 == 1) {
            GL11.glDepthFunc(515);
        }
        if (par2 != 0) {
            return -1;
        }
        this.loadTexture("/mob/enderdragon/ender_eyes.png");
        final float var4 = 1.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        GL11.glDisable(2896);
        GL11.glDepthFunc(514);
        final char var5 = '\uf0f0';
        final int var6 = var5 % 65536;
        final int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, var4);
        return 1;
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.renderGlow((EntityDragon)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving par1EntityLiving, final float par2) {
        this.renderDragonDying((EntityDragon)par1EntityLiving, par2);
    }
    
    @Override
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.rotateDragonBody((EntityDragon)par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    protected void renderModel(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.renderDragonModel((EntityDragon)par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderDragon((EntityDragon)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderDragon((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
    }
}
