package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;

public class ModelArmorStand extends ModelArmorStandArmor
{
    public ModelRenderer standWaist;
    public ModelRenderer standLeftSide;
    public ModelRenderer standBase;
    public ModelRenderer standRightSide;
    
    public ModelArmorStand(final float n) {
        super(n, 0x70 ^ 0x30, 0xD5 ^ 0x95);
        (this.bipedHead = new ModelRenderer(this, "".length(), "".length())).addBox(-1.0f, -7.0f, -1.0f, "  ".length(), 0x60 ^ 0x67, "  ".length(), n);
        this.bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedBody = new ModelRenderer(this, "".length(), 0x8A ^ 0x90)).addBox(-6.0f, 0.0f, -1.5f, 0x6F ^ 0x63, "   ".length(), "   ".length(), n);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.bipedRightArm = new ModelRenderer(this, 0xDD ^ 0xC5, "".length())).addBox(-2.0f, -2.0f, -1.0f, "  ".length(), 0x69 ^ 0x65, "  ".length(), n);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 0x32 ^ 0x12, 0x89 ^ 0x99);
        this.bipedLeftArm.mirror = (" ".length() != 0);
        this.bipedLeftArm.addBox(0.0f, -2.0f, -1.0f, "  ".length(), 0xB0 ^ 0xBC, "  ".length(), n);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, 0x7 ^ 0xF, "".length())).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0xC ^ 0x7, "  ".length(), n);
        this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0xAF ^ 0x87, 0xBD ^ 0xAD);
        this.bipedLeftLeg.mirror = (" ".length() != 0);
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x42 ^ 0x49, "  ".length(), n);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.standRightSide = new ModelRenderer(this, 0x88 ^ 0x98, "".length())).addBox(-3.0f, 3.0f, -1.0f, "  ".length(), 0x59 ^ 0x5E, "  ".length(), n);
        this.standRightSide.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.standRightSide.showModel = (" ".length() != 0);
        (this.standLeftSide = new ModelRenderer(this, 0xF2 ^ 0xC2, 0x91 ^ 0x81)).addBox(1.0f, 3.0f, -1.0f, "  ".length(), 0x17 ^ 0x10, "  ".length(), n);
        this.standLeftSide.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.standWaist = new ModelRenderer(this, "".length(), 0x6B ^ 0x5B)).addBox(-4.0f, 10.0f, -1.0f, 0xBB ^ 0xB3, "  ".length(), "  ".length(), n);
        this.standWaist.setRotationPoint(0.0f, 0.0f, 0.0f);
        (this.standBase = new ModelRenderer(this, "".length(), 0xA5 ^ 0x85)).addBox(-6.0f, 11.0f, -6.0f, 0x72 ^ 0x7E, " ".length(), 0xCD ^ 0xC1, n);
        this.standBase.setRotationPoint(0.0f, 12.0f, 0.0f);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelArmorStand() {
        this(0.0f);
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (entity instanceof EntityArmorStand) {
            final EntityArmorStand entityArmorStand = (EntityArmorStand)entity;
            this.bipedLeftArm.showModel = entityArmorStand.getShowArms();
            this.bipedRightArm.showModel = entityArmorStand.getShowArms();
            final ModelRenderer standBase = this.standBase;
            int showModel;
            if (entityArmorStand.hasNoBasePlate()) {
                showModel = "".length();
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                showModel = " ".length();
            }
            standBase.showModel = (showModel != 0);
            this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
            this.bipedRightLeg.setRotationPoint(-1.9f, 12.0f, 0.0f);
            this.standRightSide.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().getX();
            this.standRightSide.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().getY();
            this.standRightSide.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().getZ();
            this.standLeftSide.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().getX();
            this.standLeftSide.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().getY();
            this.standLeftSide.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().getZ();
            this.standWaist.rotateAngleX = 0.017453292f * entityArmorStand.getBodyRotation().getX();
            this.standWaist.rotateAngleY = 0.017453292f * entityArmorStand.getBodyRotation().getY();
            this.standWaist.rotateAngleZ = 0.017453292f * entityArmorStand.getBodyRotation().getZ();
            final float n7 = (entityArmorStand.getLeftLegRotation().getX() + entityArmorStand.getRightLegRotation().getX()) / 2.0f;
            final float n8 = (entityArmorStand.getLeftLegRotation().getY() + entityArmorStand.getRightLegRotation().getY()) / 2.0f;
            final float n9 = (entityArmorStand.getLeftLegRotation().getZ() + entityArmorStand.getRightLegRotation().getZ()) / 2.0f;
            this.standBase.rotateAngleX = 0.0f;
            this.standBase.rotateAngleY = 0.017453292f * -entity.rotationYaw;
            this.standBase.rotateAngleZ = 0.0f;
        }
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.standRightSide.render(n6);
            this.standLeftSide.render(n6);
            this.standWaist.render(n6);
            this.standBase.render(n6);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.standRightSide.render(n6);
            this.standLeftSide.render(n6);
            this.standWaist.render(n6);
            this.standBase.render(n6);
        }
        GlStateManager.popMatrix();
    }
    
    @Override
    public void postRenderArm(final float n) {
        final boolean showModel = this.bipedRightArm.showModel;
        this.bipedRightArm.showModel = (" ".length() != 0);
        super.postRenderArm(n);
        this.bipedRightArm.showModel = showModel;
    }
}
