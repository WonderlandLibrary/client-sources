package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class EffectRenderer
{
    protected World worldObj;
    private List[] fxLayers;
    private RenderEngine renderer;
    private Random rand;
    
    public EffectRenderer(final World par1World, final RenderEngine par2RenderEngine) {
        this.fxLayers = new List[4];
        this.rand = new Random();
        if (par1World != null) {
            this.worldObj = par1World;
        }
        this.renderer = par2RenderEngine;
        for (int var3 = 0; var3 < 4; ++var3) {
            this.fxLayers[var3] = new ArrayList();
        }
    }
    
    public void addEffect(final EntityFX par1EntityFX) {
        final int var2 = par1EntityFX.getFXLayer();
        if (this.fxLayers[var2].size() >= 4000) {
            this.fxLayers[var2].remove(0);
        }
        this.fxLayers[var2].add(par1EntityFX);
    }
    
    public void updateEffects() {
        for (int var1 = 0; var1 < 4; ++var1) {
            for (int var2 = 0; var2 < this.fxLayers[var1].size(); ++var2) {
                final EntityFX var3 = this.fxLayers[var1].get(var2);
                var3.onUpdate();
                if (var3.isDead) {
                    this.fxLayers[var1].remove(var2--);
                }
            }
        }
    }
    
    public void renderParticles(final Entity par1Entity, final float par2) {
        final float var3 = ActiveRenderInfo.rotationX;
        final float var4 = ActiveRenderInfo.rotationZ;
        final float var5 = ActiveRenderInfo.rotationYZ;
        final float var6 = ActiveRenderInfo.rotationXY;
        final float var7 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par2;
        EntityFX.interpPosY = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par2;
        EntityFX.interpPosZ = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par2;
        for (int var8 = 0; var8 < 3; ++var8) {
            if (!this.fxLayers[var8].isEmpty()) {
                switch (var8) {
                    default: {
                        this.renderer.bindTexture("/particles.png");
                        break;
                    }
                    case 1: {
                        this.renderer.bindTexture("/terrain.png");
                        break;
                    }
                    case 2: {
                        this.renderer.bindTexture("/gui/items.png");
                        break;
                    }
                }
                final Tessellator var9 = Tessellator.instance;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDepthMask(false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glAlphaFunc(516, 0.003921569f);
                var9.startDrawingQuads();
                for (int var10 = 0; var10 < this.fxLayers[var8].size(); ++var10) {
                    final EntityFX var11 = this.fxLayers[var8].get(var10);
                    var9.setBrightness(var11.getBrightnessForRender(par2));
                    var11.renderParticle(var9, par2, var3, var7, var4, var5, var6);
                }
                var9.draw();
                GL11.glDisable(3042);
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(516, 0.1f);
            }
        }
    }
    
    public void renderLitParticles(final Entity par1Entity, final float par2) {
        final float var3 = MathHelper.cos(par1Entity.rotationYaw * 0.017453292f);
        final float var4 = MathHelper.sin(par1Entity.rotationYaw * 0.017453292f);
        final float var5 = -var4 * MathHelper.sin(par1Entity.rotationPitch * 0.017453292f);
        final float var6 = var3 * MathHelper.sin(par1Entity.rotationPitch * 0.017453292f);
        final float var7 = MathHelper.cos(par1Entity.rotationPitch * 0.017453292f);
        final byte var8 = 3;
        if (!this.fxLayers[var8].isEmpty()) {
            final Tessellator var9 = Tessellator.instance;
            for (int var10 = 0; var10 < this.fxLayers[var8].size(); ++var10) {
                final EntityFX var11 = this.fxLayers[var8].get(var10);
                var9.setBrightness(var11.getBrightnessForRender(par2));
                var11.renderParticle(var9, par2, var3, var7, var4, var5, var6);
            }
        }
    }
    
    public void clearEffects(final World par1World) {
        this.worldObj = par1World;
        for (int var2 = 0; var2 < 4; ++var2) {
            this.fxLayers[var2].clear();
        }
    }
    
    public void addBlockDestroyEffects(final int par1, final int par2, final int par3, final int par4, final int par5) {
        if (par4 != 0) {
            final Block var6 = Block.blocksList[par4];
            final byte var7 = 4;
            for (int var8 = 0; var8 < var7; ++var8) {
                for (int var9 = 0; var9 < var7; ++var9) {
                    for (int var10 = 0; var10 < var7; ++var10) {
                        final double var11 = par1 + (var8 + 0.5) / var7;
                        final double var12 = par2 + (var9 + 0.5) / var7;
                        final double var13 = par3 + (var10 + 0.5) / var7;
                        final int var14 = this.rand.nextInt(6);
                        this.addEffect(new EntityDiggingFX(this.worldObj, var11, var12, var13, var11 - par1 - 0.5, var12 - par2 - 0.5, var13 - par3 - 0.5, var6, var14, par5, this.renderer).func_70596_a(par1, par2, par3));
                    }
                }
            }
        }
    }
    
    public void addBlockHitEffects(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = this.worldObj.getBlockId(par1, par2, par3);
        if (var5 != 0) {
            final Block var6 = Block.blocksList[var5];
            final float var7 = 0.1f;
            double var8 = par1 + this.rand.nextDouble() * (var6.getBlockBoundsMaxX() - var6.getBlockBoundsMinX() - var7 * 2.0f) + var7 + var6.getBlockBoundsMinX();
            double var9 = par2 + this.rand.nextDouble() * (var6.getBlockBoundsMaxY() - var6.getBlockBoundsMinY() - var7 * 2.0f) + var7 + var6.getBlockBoundsMinY();
            double var10 = par3 + this.rand.nextDouble() * (var6.getBlockBoundsMaxZ() - var6.getBlockBoundsMinZ() - var7 * 2.0f) + var7 + var6.getBlockBoundsMinZ();
            if (par4 == 0) {
                var9 = par2 + var6.getBlockBoundsMinY() - var7;
            }
            if (par4 == 1) {
                var9 = par2 + var6.getBlockBoundsMaxY() + var7;
            }
            if (par4 == 2) {
                var10 = par3 + var6.getBlockBoundsMinZ() - var7;
            }
            if (par4 == 3) {
                var10 = par3 + var6.getBlockBoundsMaxZ() + var7;
            }
            if (par4 == 4) {
                var8 = par1 + var6.getBlockBoundsMinX() - var7;
            }
            if (par4 == 5) {
                var8 = par1 + var6.getBlockBoundsMaxX() + var7;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, var8, var9, var10, 0.0, 0.0, 0.0, var6, par4, this.worldObj.getBlockMetadata(par1, par2, par3), this.renderer).func_70596_a(par1, par2, par3).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
        }
    }
    
    public String getStatistics() {
        return new StringBuilder().append(this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size()).toString();
    }
}
