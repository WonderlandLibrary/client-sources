package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;

public class ModelRabbit extends ModelBase
{
    ModelRenderer rabbitNose;
    private float field_178699_n;
    private static final String[] I;
    ModelRenderer rabbitLeftArm;
    ModelRenderer rabbitLeftThigh;
    ModelRenderer rabbitRightArm;
    ModelRenderer rabbitHead;
    private float field_178701_m;
    ModelRenderer rabbitBody;
    ModelRenderer rabbitRightThigh;
    ModelRenderer rabbitLeftFoot;
    ModelRenderer rabbitLeftEar;
    ModelRenderer rabbitRightEar;
    ModelRenderer rabbitTail;
    ModelRenderer rabbitRightFoot;
    
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x67 ^ 0x63])["".length()] = I("-\u000b\u001b0O(\u000f\u0013:", "EnzTa");
        ModelRabbit.I[" ".length()] = I("\u000f=03e\t7\"2", "gXQWK");
        ModelRabbit.I["  ".length()] = I("\u001f\u0012\u00000k\u0012\u0016\u0013e", "wwaTE");
        ModelRabbit.I["   ".length()] = I("+,3\u0014G&( B", "CIRpi");
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 5.0f * n6, 2.0f * n6);
            this.rabbitHead.render(n6);
            this.rabbitLeftEar.render(n6);
            this.rabbitRightEar.render(n6);
            this.rabbitNose.render(n6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.rabbitLeftFoot.render(n6);
            this.rabbitRightFoot.render(n6);
            this.rabbitLeftThigh.render(n6);
            this.rabbitRightThigh.render(n6);
            this.rabbitBody.render(n6);
            this.rabbitLeftArm.render(n6);
            this.rabbitRightArm.render(n6);
            this.rabbitTail.render(n6);
            GlStateManager.popMatrix();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            this.rabbitLeftFoot.render(n6);
            this.rabbitRightFoot.render(n6);
            this.rabbitLeftThigh.render(n6);
            this.rabbitRightThigh.render(n6);
            this.rabbitBody.render(n6);
            this.rabbitLeftArm.render(n6);
            this.rabbitRightArm.render(n6);
            this.rabbitHead.render(n6);
            this.rabbitRightEar.render(n6);
            this.rabbitLeftEar.render(n6);
            this.rabbitTail.render(n6);
            this.rabbitNose.render(n6);
        }
    }
    
    static {
        I();
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final float n7 = n3 - entity.ticksExisted;
        final EntityRabbit entityRabbit = (EntityRabbit)entity;
        final ModelRenderer rabbitNose = this.rabbitNose;
        final ModelRenderer rabbitHead = this.rabbitHead;
        final ModelRenderer rabbitRightEar = this.rabbitRightEar;
        final ModelRenderer rabbitLeftEar = this.rabbitLeftEar;
        final float n8 = n5 * 0.017453292f;
        rabbitLeftEar.rotateAngleX = n8;
        rabbitRightEar.rotateAngleX = n8;
        rabbitHead.rotateAngleX = n8;
        rabbitNose.rotateAngleX = n8;
        final ModelRenderer rabbitNose2 = this.rabbitNose;
        final ModelRenderer rabbitHead2 = this.rabbitHead;
        final float n9 = n4 * 0.017453292f;
        rabbitHead2.rotateAngleY = n9;
        rabbitNose2.rotateAngleY = n9;
        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - 0.2617994f;
        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + 0.2617994f;
        this.field_178701_m = MathHelper.sin(entityRabbit.func_175521_o(n7) * 3.1415927f);
        final ModelRenderer rabbitLeftThigh = this.rabbitLeftThigh;
        final ModelRenderer rabbitRightThigh = this.rabbitRightThigh;
        final float n10 = (this.field_178701_m * 50.0f - 21.0f) * 0.017453292f;
        rabbitRightThigh.rotateAngleX = n10;
        rabbitLeftThigh.rotateAngleX = n10;
        final ModelRenderer rabbitLeftFoot = this.rabbitLeftFoot;
        final ModelRenderer rabbitRightFoot = this.rabbitRightFoot;
        final float n11 = this.field_178701_m * 50.0f * 0.017453292f;
        rabbitRightFoot.rotateAngleX = n11;
        rabbitLeftFoot.rotateAngleX = n11;
        final ModelRenderer rabbitLeftArm = this.rabbitLeftArm;
        final ModelRenderer rabbitRightArm = this.rabbitRightArm;
        final float n12 = (this.field_178701_m * -40.0f - 11.0f) * 0.017453292f;
        rabbitRightArm.rotateAngleX = n12;
        rabbitLeftArm.rotateAngleX = n12;
    }
    
    private void setRotationOffset(final ModelRenderer modelRenderer, final float rotateAngleX, final float rotateAngleY, final float rotateAngleZ) {
        modelRenderer.rotateAngleX = rotateAngleX;
        modelRenderer.rotateAngleY = rotateAngleY;
        modelRenderer.rotateAngleZ = rotateAngleZ;
    }
    
    public ModelRabbit() {
        this.field_178701_m = 0.0f;
        this.field_178699_n = 0.0f;
        this.setTextureOffset(ModelRabbit.I["".length()], "".length(), "".length());
        this.setTextureOffset(ModelRabbit.I[" ".length()], "".length(), 0xBC ^ 0xA4);
        this.setTextureOffset(ModelRabbit.I["  ".length()], "".length(), 0x76 ^ 0x7C);
        this.setTextureOffset(ModelRabbit.I["   ".length()], 0x9E ^ 0x98, 0x45 ^ 0x4F);
        (this.rabbitLeftFoot = new ModelRenderer(this, 0xB6 ^ 0xAC, 0x86 ^ 0x9E)).addBox(-1.0f, 5.5f, -3.7f, "  ".length(), " ".length(), 0x60 ^ 0x67);
        this.rabbitLeftFoot.setRotationPoint(3.0f, 17.5f, 3.7f);
        this.rabbitLeftFoot.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitLeftFoot, 0.0f, 0.0f, 0.0f);
        (this.rabbitRightFoot = new ModelRenderer(this, 0x42 ^ 0x4A, 0x5B ^ 0x43)).addBox(-1.0f, 5.5f, -3.7f, "  ".length(), " ".length(), 0x3E ^ 0x39);
        this.rabbitRightFoot.setRotationPoint(-3.0f, 17.5f, 3.7f);
        this.rabbitRightFoot.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitRightFoot, 0.0f, 0.0f, 0.0f);
        (this.rabbitLeftThigh = new ModelRenderer(this, 0xB7 ^ 0xA9, 0x81 ^ 0x8E)).addBox(-1.0f, 0.0f, 0.0f, "  ".length(), 0xAE ^ 0xAA, 0x6C ^ 0x69);
        this.rabbitLeftThigh.setRotationPoint(3.0f, 17.5f, 3.7f);
        this.rabbitLeftThigh.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitLeftThigh, -0.34906584f, 0.0f, 0.0f);
        (this.rabbitRightThigh = new ModelRenderer(this, 0x66 ^ 0x76, 0x56 ^ 0x59)).addBox(-1.0f, 0.0f, 0.0f, "  ".length(), 0x41 ^ 0x45, 0x58 ^ 0x5D);
        this.rabbitRightThigh.setRotationPoint(-3.0f, 17.5f, 3.7f);
        this.rabbitRightThigh.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitRightThigh, -0.34906584f, 0.0f, 0.0f);
        (this.rabbitBody = new ModelRenderer(this, "".length(), "".length())).addBox(-3.0f, -2.0f, -10.0f, 0xA2 ^ 0xA4, 0x9D ^ 0x98, 0x92 ^ 0x98);
        this.rabbitBody.setRotationPoint(0.0f, 19.0f, 8.0f);
        this.rabbitBody.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitBody, -0.34906584f, 0.0f, 0.0f);
        (this.rabbitLeftArm = new ModelRenderer(this, 0x41 ^ 0x49, 0x3E ^ 0x31)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0xAB ^ 0xAC, "  ".length());
        this.rabbitLeftArm.setRotationPoint(3.0f, 17.0f, -1.0f);
        this.rabbitLeftArm.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitLeftArm, -0.17453292f, 0.0f, 0.0f);
        (this.rabbitRightArm = new ModelRenderer(this, "".length(), 0x6D ^ 0x62)).addBox(-1.0f, 0.0f, -1.0f, "  ".length(), 0x9A ^ 0x9D, "  ".length());
        this.rabbitRightArm.setRotationPoint(-3.0f, 17.0f, -1.0f);
        this.rabbitRightArm.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitRightArm, -0.17453292f, 0.0f, 0.0f);
        (this.rabbitHead = new ModelRenderer(this, 0x80 ^ 0xA0, "".length())).addBox(-2.5f, -4.0f, -5.0f, 0xBB ^ 0xBE, 0xC5 ^ 0xC1, 0x3B ^ 0x3E);
        this.rabbitHead.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitHead.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitHead, 0.0f, 0.0f, 0.0f);
        (this.rabbitRightEar = new ModelRenderer(this, 0x16 ^ 0x22, "".length())).addBox(-2.5f, -9.0f, -1.0f, "  ".length(), 0x8D ^ 0x88, " ".length());
        this.rabbitRightEar.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitRightEar.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitRightEar, 0.0f, -0.2617994f, 0.0f);
        (this.rabbitLeftEar = new ModelRenderer(this, 0x7F ^ 0x45, "".length())).addBox(0.5f, -9.0f, -1.0f, "  ".length(), 0x12 ^ 0x17, " ".length());
        this.rabbitLeftEar.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitLeftEar.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitLeftEar, 0.0f, 0.2617994f, 0.0f);
        (this.rabbitTail = new ModelRenderer(this, 0x49 ^ 0x7D, 0x64 ^ 0x62)).addBox(-1.5f, -1.5f, 0.0f, "   ".length(), "   ".length(), "  ".length());
        this.rabbitTail.setRotationPoint(0.0f, 20.0f, 7.0f);
        this.rabbitTail.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitTail, -0.3490659f, 0.0f, 0.0f);
        (this.rabbitNose = new ModelRenderer(this, 0xB1 ^ 0x91, 0x9D ^ 0x94)).addBox(-0.5f, -2.5f, -5.5f, " ".length(), " ".length(), " ".length());
        this.rabbitNose.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitNose.mirror = (" ".length() != 0);
        this.setRotationOffset(this.rabbitNose, 0.0f, 0.0f, 0.0f);
    }
}
