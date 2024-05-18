package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class ModelPlayer extends ModelBiped
{
    private ModelRenderer bipedDeadmau5Head;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedBodyWear;
    public ModelRenderer bipedLeftArmwear;
    private boolean smallArms;
    private static final String __OBFID;
    public ModelRenderer bipedRightLegwear;
    private static final String[] I;
    private ModelRenderer bipedCape;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("9#.\\`J_CZbL", "zoqlP");
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        super.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        ModelBase.copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        ModelBase.copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        ModelBase.copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        ModelBase.copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        ModelBase.copyModelAngles(this.bipedBody, this.bipedBodyWear);
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.render(entity, n, n2, n3, n4, n5, n6);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            final float n7 = 2.0f;
            GlStateManager.scale(1.0f / n7, 1.0f / n7, 1.0f / n7);
            GlStateManager.translate(0.0f, 24.0f * n6, 0.0f);
            this.bipedLeftLegwear.render(n6);
            this.bipedRightLegwear.render(n6);
            this.bipedLeftArmwear.render(n6);
            this.bipedRightArmwear.render(n6);
            this.bipedBodyWear.render(n6);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.bipedLeftLegwear.render(n6);
            this.bipedRightLegwear.render(n6);
            this.bipedLeftArmwear.render(n6);
            this.bipedRightArmwear.render(n6);
            this.bipedBodyWear.render(n6);
        }
        GlStateManager.popMatrix();
    }
    
    @Override
    public void postRenderArm(final float n) {
        if (this.smallArms) {
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            ++bipedRightArm.rotationPointX;
            this.bipedRightArm.postRender(n);
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            --bipedRightArm2.rotationPointX;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.bipedRightArm.postRender(n);
        }
    }
    
    static {
        I();
        __OBFID = ModelPlayer.I["".length()];
    }
    
    public void renderCape(final float n) {
        this.bipedCape.render(n);
    }
    
    public void renderRightArm() {
        this.bipedRightArm.render(0.0625f);
        this.bipedRightArmwear.render(0.0625f);
    }
    
    @Override
    public void setInvisible(final boolean b) {
        super.setInvisible(b);
        this.bipedLeftArmwear.showModel = b;
        this.bipedRightArmwear.showModel = b;
        this.bipedLeftLegwear.showModel = b;
        this.bipedRightLegwear.showModel = b;
        this.bipedBodyWear.showModel = b;
        this.bipedCape.showModel = b;
        this.bipedDeadmau5Head.showModel = b;
    }
    
    public ModelPlayer(final float n, final boolean smallArms) {
        super(n, 0.0f, 0xE0 ^ 0xA0, 0xEC ^ 0xAC);
        this.smallArms = smallArms;
        (this.bipedDeadmau5Head = new ModelRenderer(this, 0x1A ^ 0x2, "".length())).addBox(-3.0f, -6.0f, -1.0f, 0x6F ^ 0x69, 0x4E ^ 0x48, " ".length(), n);
        (this.bipedCape = new ModelRenderer(this, "".length(), "".length())).setTextureSize(0xF4 ^ 0xB4, 0x6B ^ 0x4B);
        this.bipedCape.addBox(-5.0f, 0.0f, -1.0f, 0x2E ^ 0x24, 0x4 ^ 0x14, " ".length(), n);
        if (smallArms) {
            (this.bipedLeftArm = new ModelRenderer(this, 0x29 ^ 0x9, 0x4A ^ 0x7A)).addBox(-1.0f, -2.0f, -2.0f, "   ".length(), 0x2B ^ 0x27, 0x47 ^ 0x43, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.5f, 0.0f);
            (this.bipedRightArm = new ModelRenderer(this, 0x5D ^ 0x75, 0x49 ^ 0x59)).addBox(-2.0f, -2.0f, -2.0f, "   ".length(), 0x35 ^ 0x39, 0x6A ^ 0x6E, n);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.5f, 0.0f);
            (this.bipedLeftArmwear = new ModelRenderer(this, 0x45 ^ 0x75, 0x42 ^ 0x72)).addBox(-1.0f, -2.0f, -2.0f, "   ".length(), 0x68 ^ 0x64, 0x46 ^ 0x42, n + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.5f, 0.0f);
            (this.bipedRightArmwear = new ModelRenderer(this, 0x16 ^ 0x3E, 0x6F ^ 0x4F)).addBox(-2.0f, -2.0f, -2.0f, "   ".length(), 0x69 ^ 0x65, 0x6 ^ 0x2, n + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.5f, 10.0f);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            (this.bipedLeftArm = new ModelRenderer(this, 0x48 ^ 0x68, 0x85 ^ 0xB5)).addBox(-1.0f, -2.0f, -2.0f, 0x4 ^ 0x0, 0x8B ^ 0x87, 0x57 ^ 0x53, n);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedLeftArmwear = new ModelRenderer(this, 0x25 ^ 0x15, 0xA9 ^ 0x99)).addBox(-1.0f, -2.0f, -2.0f, 0x59 ^ 0x5D, 0x22 ^ 0x2E, 0x54 ^ 0x50, n + 0.25f);
            this.bipedLeftArmwear.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedRightArmwear = new ModelRenderer(this, 0x6D ^ 0x45, 0xE2 ^ 0xC2)).addBox(-3.0f, -2.0f, -2.0f, 0x8A ^ 0x8E, 0xB3 ^ 0xBF, 0x63 ^ 0x67, n + 0.25f);
            this.bipedRightArmwear.setRotationPoint(-5.0f, 2.0f, 10.0f);
        }
        (this.bipedLeftLeg = new ModelRenderer(this, 0xB6 ^ 0xA6, 0x9B ^ 0xAB)).addBox(-2.0f, 0.0f, -2.0f, 0x41 ^ 0x45, 0x72 ^ 0x7E, 0x46 ^ 0x42, n);
        this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.bipedLeftLegwear = new ModelRenderer(this, "".length(), 0x37 ^ 0x7)).addBox(-2.0f, 0.0f, -2.0f, 0xA5 ^ 0xA1, 0x58 ^ 0x54, 0x3A ^ 0x3E, n + 0.25f);
        this.bipedLeftLegwear.setRotationPoint(1.9f, 12.0f, 0.0f);
        (this.bipedRightLegwear = new ModelRenderer(this, "".length(), 0x4F ^ 0x6F)).addBox(-2.0f, 0.0f, -2.0f, 0x87 ^ 0x83, 0x4D ^ 0x41, 0xA7 ^ 0xA3, n + 0.25f);
        this.bipedRightLegwear.setRotationPoint(-1.9f, 12.0f, 0.0f);
        (this.bipedBodyWear = new ModelRenderer(this, 0x2C ^ 0x3C, 0x12 ^ 0x32)).addBox(-4.0f, 0.0f, -2.0f, 0xAE ^ 0xA6, 0x7E ^ 0x72, 0x87 ^ 0x83, n + 0.25f);
        this.bipedBodyWear.setRotationPoint(0.0f, 0.0f, 0.0f);
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void renderDeadmau5Head(final float n) {
        ModelBase.copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
        this.bipedDeadmau5Head.rotationPointX = 0.0f;
        this.bipedDeadmau5Head.rotationPointY = 0.0f;
        this.bipedDeadmau5Head.render(n);
    }
    
    public void renderLeftArm() {
        this.bipedLeftArm.render(0.0625f);
        this.bipedLeftArmwear.render(0.0625f);
    }
}
