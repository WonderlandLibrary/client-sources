package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ModelChicken extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer rightWing;
    public ModelRenderer leftLeg;
    public ModelRenderer chin;
    public ModelRenderer rightLeg;
    public ModelRenderer body;
    public ModelRenderer bill;
    public ModelRenderer leftWing;
    
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float rotateAngleZ, final float n3, final float n4, final float n5, final Entity entity) {
        this.head.rotateAngleX = n4 / 57.295776f;
        this.head.rotateAngleY = n3 / 57.295776f;
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = 1.5707964f;
        this.rightLeg.rotateAngleX = MathHelper.cos(n * 0.6662f) * 1.4f * n2;
        this.leftLeg.rotateAngleX = MathHelper.cos(n * 0.6662f + 3.1415927f) * 1.4f * n2;
        this.rightWing.rotateAngleZ = rotateAngleZ;
        this.leftWing.rotateAngleZ = -rotateAngleZ;
    }
    
    public ModelChicken() {
        final int n = 0xA6 ^ 0xB6;
        (this.head = new ModelRenderer(this, "".length(), "".length())).addBox(-2.0f, -6.0f, -2.0f, 0x91 ^ 0x95, 0x1A ^ 0x1C, "   ".length(), 0.0f);
        this.head.setRotationPoint(0.0f, -" ".length() + n, -4.0f);
        (this.bill = new ModelRenderer(this, 0xA0 ^ 0xAE, "".length())).addBox(-2.0f, -4.0f, -4.0f, 0x9B ^ 0x9F, "  ".length(), "  ".length(), 0.0f);
        this.bill.setRotationPoint(0.0f, -" ".length() + n, -4.0f);
        (this.chin = new ModelRenderer(this, 0x59 ^ 0x57, 0xB5 ^ 0xB1)).addBox(-1.0f, -2.0f, -3.0f, "  ".length(), "  ".length(), "  ".length(), 0.0f);
        this.chin.setRotationPoint(0.0f, -" ".length() + n, -4.0f);
        (this.body = new ModelRenderer(this, "".length(), 0x56 ^ 0x5F)).addBox(-3.0f, -4.0f, -3.0f, 0x7F ^ 0x79, 0x2E ^ 0x26, 0xC2 ^ 0xC4, 0.0f);
        this.body.setRotationPoint(0.0f, n, 0.0f);
        (this.rightLeg = new ModelRenderer(this, 0x3F ^ 0x25, "".length())).addBox(-1.0f, 0.0f, -3.0f, "   ".length(), 0x5C ^ 0x59, "   ".length());
        this.rightLeg.setRotationPoint(-2.0f, "   ".length() + n, 1.0f);
        (this.leftLeg = new ModelRenderer(this, 0x46 ^ 0x5C, "".length())).addBox(-1.0f, 0.0f, -3.0f, "   ".length(), 0x56 ^ 0x53, "   ".length());
        this.leftLeg.setRotationPoint(1.0f, "   ".length() + n, 1.0f);
        (this.rightWing = new ModelRenderer(this, 0xB7 ^ 0xAF, 0x66 ^ 0x6B)).addBox(0.0f, 0.0f, -3.0f, " ".length(), 0xAA ^ 0xAE, 0x7E ^ 0x78);
        this.rightWing.setRotationPoint(-4.0f, -"   ".length() + n, 0.0f);
        (this.leftWing = new ModelRenderer(this, 0x1 ^ 0x19, 0xCE ^ 0xC3)).addBox(-1.0f, 0.0f, -3.0f, " ".length(), 0x1A ^ 0x1E, 0x22 ^ 0x24);
        this.leftWing.setRotationPoint(4.0f, -"   ".length() + n, 0.0f);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 5.0f * n6, 2.0f * n6);
            this.head.render(n6);
            this.bill.render(n6);
            this.chin.render(n6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.body.render(n6);
            this.rightLeg.render(n6);
            this.leftLeg.render(n6);
            this.rightWing.render(n6);
            this.leftWing.render(n6);
            GlStateManager.popMatrix();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            this.head.render(n6);
            this.bill.render(n6);
            this.chin.render(n6);
            this.body.render(n6);
            this.rightLeg.render(n6);
            this.leftLeg.render(n6);
            this.rightWing.render(n6);
            this.leftWing.render(n6);
        }
    }
}
