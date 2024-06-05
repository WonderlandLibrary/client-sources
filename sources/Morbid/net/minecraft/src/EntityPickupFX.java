package net.minecraft.src;

import org.lwjgl.opengl.*;

public class EntityPickupFX extends EntityFX
{
    private Entity entityToPickUp;
    private Entity entityPickingUp;
    private int age;
    private int maxAge;
    private float yOffs;
    
    public EntityPickupFX(final World par1World, final Entity par2Entity, final Entity par3Entity, final float par4) {
        super(par1World, par2Entity.posX, par2Entity.posY, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        this.age = 0;
        this.maxAge = 0;
        this.entityToPickUp = par2Entity;
        this.entityPickingUp = par3Entity;
        this.maxAge = 3;
        this.yOffs = par4;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        float var8 = (this.age + par2) / this.maxAge;
        var8 *= var8;
        final double var9 = this.entityToPickUp.posX;
        final double var10 = this.entityToPickUp.posY;
        final double var11 = this.entityToPickUp.posZ;
        final double var12 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * par2;
        final double var13 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * par2 + this.yOffs;
        final double var14 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * par2;
        double var15 = var9 + (var12 - var9) * var8;
        double var16 = var10 + (var13 - var10) * var8;
        double var17 = var11 + (var14 - var11) * var8;
        final int var18 = MathHelper.floor_double(var15);
        final int var19 = MathHelper.floor_double(var16 + this.yOffset / 2.0f);
        final int var20 = MathHelper.floor_double(var17);
        final int var21 = this.getBrightnessForRender(par2);
        final int var22 = var21 % 65536;
        final int var23 = var21 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var22 / 1.0f, var23 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        var15 -= EntityPickupFX.interpPosX;
        var16 -= EntityPickupFX.interpPosY;
        var17 -= EntityPickupFX.interpPosZ;
        RenderManager.instance.renderEntityWithPosYaw(this.entityToPickUp, (float)var15, (float)var16, (float)var17, this.entityToPickUp.rotationYaw, par2);
    }
    
    @Override
    public void onUpdate() {
        ++this.age;
        if (this.age == this.maxAge) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
}
