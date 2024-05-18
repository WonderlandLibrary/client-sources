package net.minecraft.src;

import org.lwjgl.opengl.*;

public class ModelDragon extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer neck;
    private ModelRenderer jaw;
    private ModelRenderer body;
    private ModelRenderer rearLeg;
    private ModelRenderer frontLeg;
    private ModelRenderer rearLegTip;
    private ModelRenderer frontLegTip;
    private ModelRenderer rearFoot;
    private ModelRenderer frontFoot;
    private ModelRenderer wing;
    private ModelRenderer wingTip;
    private float partialTicks;
    
    public ModelDragon(final float par1) {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.setTextureOffset("body.body", 0, 0);
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        this.setTextureOffset("rearleg.main", 0, 0);
        this.setTextureOffset("rearfoot.main", 112, 0);
        this.setTextureOffset("rearlegtip.main", 196, 0);
        this.setTextureOffset("head.upperhead", 112, 30);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("head.upperlip", 176, 44);
        this.setTextureOffset("jaw.jaw", 176, 65);
        this.setTextureOffset("frontleg.main", 112, 104);
        this.setTextureOffset("wingtip.bone", 112, 136);
        this.setTextureOffset("frontfoot.main", 144, 104);
        this.setTextureOffset("neck.box", 192, 104);
        this.setTextureOffset("frontlegtip.main", 226, 138);
        this.setTextureOffset("body.scale", 220, 53);
        this.setTextureOffset("head.scale", 0, 0);
        this.setTextureOffset("neck.scale", 48, 0);
        this.setTextureOffset("head.nostril", 112, 0);
        final float var2 = -16.0f;
        (this.head = new ModelRenderer(this, "head")).addBox("upperlip", -6.0f, -1.0f, -8.0f + var2, 12, 5, 16);
        this.head.addBox("upperhead", -8.0f, -8.0f, 6.0f + var2, 16, 16, 16);
        this.head.mirror = true;
        this.head.addBox("scale", -5.0f, -12.0f, 12.0f + var2, 2, 4, 6);
        this.head.addBox("nostril", -5.0f, -3.0f, -6.0f + var2, 2, 2, 4);
        this.head.mirror = false;
        this.head.addBox("scale", 3.0f, -12.0f, 12.0f + var2, 2, 4, 6);
        this.head.addBox("nostril", 3.0f, -3.0f, -6.0f + var2, 2, 2, 4);
        (this.jaw = new ModelRenderer(this, "jaw")).setRotationPoint(0.0f, 4.0f, 8.0f + var2);
        this.jaw.addBox("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16);
        this.head.addChild(this.jaw);
        (this.neck = new ModelRenderer(this, "neck")).addBox("box", -5.0f, -5.0f, -5.0f, 10, 10, 10);
        this.neck.addBox("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6);
        (this.body = new ModelRenderer(this, "body")).setRotationPoint(0.0f, 4.0f, 8.0f);
        this.body.addBox("body", -12.0f, 0.0f, -16.0f, 24, 24, 64);
        this.body.addBox("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12);
        this.body.addBox("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12);
        this.body.addBox("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12);
        (this.wing = new ModelRenderer(this, "wing")).setRotationPoint(-12.0f, 5.0f, 2.0f);
        this.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        this.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        (this.wingTip = new ModelRenderer(this, "wingtip")).setRotationPoint(-56.0f, 0.0f, 0.0f);
        this.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        this.wing.addChild(this.wingTip);
        (this.frontLeg = new ModelRenderer(this, "frontleg")).setRotationPoint(-12.0f, 20.0f, 2.0f);
        this.frontLeg.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8);
        (this.frontLegTip = new ModelRenderer(this, "frontlegtip")).setRotationPoint(0.0f, 20.0f, -1.0f);
        this.frontLegTip.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6);
        this.frontLeg.addChild(this.frontLegTip);
        (this.frontFoot = new ModelRenderer(this, "frontfoot")).setRotationPoint(0.0f, 23.0f, 0.0f);
        this.frontFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16);
        this.frontLegTip.addChild(this.frontFoot);
        (this.rearLeg = new ModelRenderer(this, "rearleg")).setRotationPoint(-16.0f, 16.0f, 42.0f);
        this.rearLeg.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16);
        (this.rearLegTip = new ModelRenderer(this, "rearlegtip")).setRotationPoint(0.0f, 32.0f, -4.0f);
        this.rearLegTip.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12);
        this.rearLeg.addChild(this.rearLegTip);
        (this.rearFoot = new ModelRenderer(this, "rearfoot")).setRotationPoint(0.0f, 31.0f, 4.0f);
        this.rearFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24);
        this.rearLegTip.addChild(this.rearFoot);
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.partialTicks = par4;
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        GL11.glPushMatrix();
        final EntityDragon var8 = (EntityDragon)par1Entity;
        final float var9 = var8.prevAnimTime + (var8.animTime - var8.prevAnimTime) * this.partialTicks;
        this.jaw.rotateAngleX = (float)(Math.sin(var9 * 3.1415927f * 2.0f) + 1.0) * 0.2f;
        float var10 = (float)(Math.sin(var9 * 3.1415927f * 2.0f - 1.0f) + 1.0);
        var10 = (var10 * var10 * 1.0f + var10 * 2.0f) * 0.05f;
        GL11.glTranslatef(0.0f, var10 - 2.0f, -3.0f);
        GL11.glRotatef(var10 * 2.0f, 1.0f, 0.0f, 0.0f);
        float var11 = -30.0f;
        float var12 = 0.0f;
        final float var13 = 1.5f;
        double[] var14 = var8.getMovementOffsets(6, this.partialTicks);
        final float var15 = this.updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] - var8.getMovementOffsets(10, this.partialTicks)[0]);
        final float var16 = this.updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] + var15 / 2.0f);
        var11 += 2.0f;
        float var17 = var9 * 3.1415927f * 2.0f;
        var11 = 20.0f;
        float var18 = -12.0f;
        for (int var19 = 0; var19 < 5; ++var19) {
            final double[] var20 = var8.getMovementOffsets(5 - var19, this.partialTicks);
            final float var21 = (float)Math.cos(var19 * 0.45f + var17) * 0.15f;
            this.neck.rotateAngleY = this.updateRotations(var20[0] - var14[0]) * 3.1415927f / 180.0f * var13;
            this.neck.rotateAngleX = var21 + (float)(var20[1] - var14[1]) * 3.1415927f / 180.0f * var13 * 5.0f;
            this.neck.rotateAngleZ = -this.updateRotations(var20[0] - var16) * 3.1415927f / 180.0f * var13;
            this.neck.rotationPointY = var11;
            this.neck.rotationPointZ = var18;
            this.neck.rotationPointX = var12;
            var11 += (float)(Math.sin(this.neck.rotateAngleX) * 10.0);
            var18 -= (float)(Math.cos(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0);
            var12 -= (float)(Math.sin(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0);
            this.neck.render(par7);
        }
        this.head.rotationPointY = var11;
        this.head.rotationPointZ = var18;
        this.head.rotationPointX = var12;
        double[] var22 = var8.getMovementOffsets(0, this.partialTicks);
        this.head.rotateAngleY = this.updateRotations(var22[0] - var14[0]) * 3.1415927f / 180.0f * 1.0f;
        this.head.rotateAngleZ = -this.updateRotations(var22[0] - var16) * 3.1415927f / 180.0f * 1.0f;
        this.head.render(par7);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-var15 * var13 * 1.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        this.body.rotateAngleZ = 0.0f;
        this.body.render(par7);
        for (int var23 = 0; var23 < 2; ++var23) {
            GL11.glEnable(2884);
            final float var21 = var9 * 3.1415927f * 2.0f;
            this.wing.rotateAngleX = 0.125f - (float)Math.cos(var21) * 0.2f;
            this.wing.rotateAngleY = 0.25f;
            this.wing.rotateAngleZ = (float)(Math.sin(var21) + 0.125) * 0.8f;
            this.wingTip.rotateAngleZ = -(float)(Math.sin(var21 + 2.0f) + 0.5) * 0.75f;
            this.rearLeg.rotateAngleX = 1.0f + var10 * 0.1f;
            this.rearLegTip.rotateAngleX = 0.5f + var10 * 0.1f;
            this.rearFoot.rotateAngleX = 0.75f + var10 * 0.1f;
            this.frontLeg.rotateAngleX = 1.3f + var10 * 0.1f;
            this.frontLegTip.rotateAngleX = -0.5f - var10 * 0.1f;
            this.frontFoot.rotateAngleX = 0.75f + var10 * 0.1f;
            this.wing.render(par7);
            this.frontLeg.render(par7);
            this.rearLeg.render(par7);
            GL11.glScalef(-1.0f, 1.0f, 1.0f);
            if (var23 == 0) {
                GL11.glCullFace(1028);
            }
        }
        GL11.glPopMatrix();
        GL11.glCullFace(1029);
        GL11.glDisable(2884);
        float var24 = -(float)Math.sin(var9 * 3.1415927f * 2.0f) * 0.0f;
        var17 = var9 * 3.1415927f * 2.0f;
        var11 = 10.0f;
        var18 = 60.0f;
        var12 = 0.0f;
        var14 = var8.getMovementOffsets(11, this.partialTicks);
        for (int var25 = 0; var25 < 12; ++var25) {
            var22 = var8.getMovementOffsets(12 + var25, this.partialTicks);
            var24 += (float)(Math.sin(var25 * 0.45f + var17) * 0.05000000074505806);
            this.neck.rotateAngleY = (this.updateRotations(var22[0] - var14[0]) * var13 + 180.0f) * 3.1415927f / 180.0f;
            this.neck.rotateAngleX = var24 + (float)(var22[1] - var14[1]) * 3.1415927f / 180.0f * var13 * 5.0f;
            this.neck.rotateAngleZ = this.updateRotations(var22[0] - var16) * 3.1415927f / 180.0f * var13;
            this.neck.rotationPointY = var11;
            this.neck.rotationPointZ = var18;
            this.neck.rotationPointX = var12;
            var11 += (float)(Math.sin(this.neck.rotateAngleX) * 10.0);
            var18 -= (float)(Math.cos(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0);
            var12 -= (float)(Math.sin(this.neck.rotateAngleY) * Math.cos(this.neck.rotateAngleX) * 10.0);
            this.neck.render(par7);
        }
        GL11.glPopMatrix();
    }
    
    private float updateRotations(double par1) {
        while (par1 >= 180.0) {
            par1 -= 360.0;
        }
        while (par1 < -180.0) {
            par1 += 360.0;
        }
        return (float)par1;
    }
}
