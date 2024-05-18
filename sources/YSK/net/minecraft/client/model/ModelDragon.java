package net.minecraft.client.model;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;

public class ModelDragon extends ModelBase
{
    private ModelRenderer wingTip;
    private ModelRenderer frontLeg;
    private ModelRenderer rearLegTip;
    private ModelRenderer frontFoot;
    private ModelRenderer body;
    private ModelRenderer rearFoot;
    private ModelRenderer rearLeg;
    private ModelRenderer head;
    private ModelRenderer frontLegTip;
    private static final String[] I;
    private float partialTicks;
    private ModelRenderer wing;
    private ModelRenderer jaw;
    private ModelRenderer spine;
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        GlStateManager.pushMatrix();
        final EntityDragon entityDragon = (EntityDragon)entity;
        final float n7 = entityDragon.prevAnimTime + (entityDragon.animTime - entityDragon.prevAnimTime) * this.partialTicks;
        this.jaw.rotateAngleX = (float)(Math.sin(n7 * 3.1415927f * 2.0f) + 1.0) * 0.2f;
        final float n8 = (float)(Math.sin(n7 * 3.1415927f * 2.0f - 1.0f) + 1.0);
        final float n9 = (n8 * n8 * 1.0f + n8 * 2.0f) * 0.05f;
        GlStateManager.translate(0.0f, n9 - 2.0f, -3.0f);
        GlStateManager.rotate(n9 * 2.0f, 1.0f, 0.0f, 0.0f);
        float n10 = 0.0f;
        final float n11 = 1.5f;
        final double[] movementOffsets = entityDragon.getMovementOffsets(0x48 ^ 0x4E, this.partialTicks);
        final float updateRotations = this.updateRotations(entityDragon.getMovementOffsets(0xA3 ^ 0xA6, this.partialTicks)["".length()] - entityDragon.getMovementOffsets(0x89 ^ 0x83, this.partialTicks)["".length()]);
        final float updateRotations2 = this.updateRotations(entityDragon.getMovementOffsets(0x8E ^ 0x8B, this.partialTicks)["".length()] + updateRotations / 2.0f);
        final float n12 = n7 * 3.1415927f * 2.0f;
        float n13 = 20.0f;
        float n14 = -12.0f;
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < (0x76 ^ 0x73)) {
            final double[] movementOffsets2 = entityDragon.getMovementOffsets((0xA8 ^ 0xAD) - i, this.partialTicks);
            final float n15 = (float)Math.cos(i * 0.45f + n12) * 0.15f;
            this.spine.rotateAngleY = this.updateRotations(movementOffsets2["".length()] - movementOffsets["".length()]) * 3.1415927f / 180.0f * n11;
            this.spine.rotateAngleX = n15 + (float)(movementOffsets2[" ".length()] - movementOffsets[" ".length()]) * 3.1415927f / 180.0f * n11 * 5.0f;
            this.spine.rotateAngleZ = -this.updateRotations(movementOffsets2["".length()] - updateRotations2) * 3.1415927f / 180.0f * n11;
            this.spine.rotationPointY = n13;
            this.spine.rotationPointZ = n14;
            this.spine.rotationPointX = n10;
            n13 += (float)(Math.sin(this.spine.rotateAngleX) * 10.0);
            n14 -= (float)(Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
            n10 -= (float)(Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
            this.spine.render(n6);
            ++i;
        }
        this.head.rotationPointY = n13;
        this.head.rotationPointZ = n14;
        this.head.rotationPointX = n10;
        final double[] movementOffsets3 = entityDragon.getMovementOffsets("".length(), this.partialTicks);
        this.head.rotateAngleY = this.updateRotations(movementOffsets3["".length()] - movementOffsets["".length()]) * 3.1415927f / 180.0f * 1.0f;
        this.head.rotateAngleZ = -this.updateRotations(movementOffsets3["".length()] - updateRotations2) * 3.1415927f / 180.0f * 1.0f;
        this.head.render(n6);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-updateRotations * n11 * 1.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.0f, -1.0f, 0.0f);
        this.body.rotateAngleZ = 0.0f;
        this.body.render(n6);
        int j = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (j < "  ".length()) {
            GlStateManager.enableCull();
            final float n16 = n7 * 3.1415927f * 2.0f;
            this.wing.rotateAngleX = 0.125f - (float)Math.cos(n16) * 0.2f;
            this.wing.rotateAngleY = 0.25f;
            this.wing.rotateAngleZ = (float)(Math.sin(n16) + 0.125) * 0.8f;
            this.wingTip.rotateAngleZ = -(float)(Math.sin(n16 + 2.0f) + 0.5) * 0.75f;
            this.rearLeg.rotateAngleX = 1.0f + n9 * 0.1f;
            this.rearLegTip.rotateAngleX = 0.5f + n9 * 0.1f;
            this.rearFoot.rotateAngleX = 0.75f + n9 * 0.1f;
            this.frontLeg.rotateAngleX = 1.3f + n9 * 0.1f;
            this.frontLegTip.rotateAngleX = -0.5f - n9 * 0.1f;
            this.frontFoot.rotateAngleX = 0.75f + n9 * 0.1f;
            this.wing.render(n6);
            this.frontLeg.render(n6);
            this.rearLeg.render(n6);
            GlStateManager.scale(-1.0f, 1.0f, 1.0f);
            if (j == 0) {
                GlStateManager.cullFace(967 + 34 - 846 + 873);
            }
            ++j;
        }
        GlStateManager.popMatrix();
        GlStateManager.cullFace(839 + 494 - 329 + 25);
        GlStateManager.disableCull();
        float n17 = -(float)Math.sin(n7 * 3.1415927f * 2.0f) * 0.0f;
        final float n18 = n7 * 3.1415927f * 2.0f;
        float rotationPointY = 10.0f;
        float rotationPointZ = 60.0f;
        float rotationPointX = 0.0f;
        final double[] movementOffsets4 = entityDragon.getMovementOffsets(0x6E ^ 0x65, this.partialTicks);
        int k = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (k < (0x43 ^ 0x4F)) {
            final double[] movementOffsets5 = entityDragon.getMovementOffsets((0x4A ^ 0x46) + k, this.partialTicks);
            n17 += (float)(Math.sin(k * 0.45f + n18) * 0.05000000074505806);
            this.spine.rotateAngleY = (this.updateRotations(movementOffsets5["".length()] - movementOffsets4["".length()]) * n11 + 180.0f) * 3.1415927f / 180.0f;
            this.spine.rotateAngleX = n17 + (float)(movementOffsets5[" ".length()] - movementOffsets4[" ".length()]) * 3.1415927f / 180.0f * n11 * 5.0f;
            this.spine.rotateAngleZ = this.updateRotations(movementOffsets5["".length()] - updateRotations2) * 3.1415927f / 180.0f * n11;
            this.spine.rotationPointY = rotationPointY;
            this.spine.rotationPointZ = rotationPointZ;
            this.spine.rotationPointX = rotationPointX;
            rotationPointY += (float)(Math.sin(this.spine.rotateAngleX) * 10.0);
            rotationPointZ -= (float)(Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
            rotationPointX -= (float)(Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0);
            this.spine.render(n6);
            ++k;
        }
        GlStateManager.popMatrix();
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public ModelDragon(final float n) {
        this.textureWidth = 105 + 8 - 99 + 242;
        this.textureHeight = 121 + 115 - 102 + 122;
        this.setTextureOffset(ModelDragon.I["".length()], "".length(), "".length());
        this.setTextureOffset(ModelDragon.I[" ".length()], -(0x46 ^ 0x7E), 0x6E ^ 0x36);
        this.setTextureOffset(ModelDragon.I["  ".length()], -(0xA1 ^ 0x99), 44 + 42 - 67 + 125);
        this.setTextureOffset(ModelDragon.I["   ".length()], "".length(), "".length());
        this.setTextureOffset(ModelDragon.I[0xA5 ^ 0xA1], 0x70 ^ 0x0, "".length());
        this.setTextureOffset(ModelDragon.I[0x51 ^ 0x54], 130 + 117 - 71 + 20, "".length());
        this.setTextureOffset(ModelDragon.I[0x3B ^ 0x3D], 0x73 ^ 0x3, 0x7A ^ 0x64);
        this.setTextureOffset(ModelDragon.I[0xAA ^ 0xAD], 0x1E ^ 0x6E, 0x4C ^ 0x14);
        this.setTextureOffset(ModelDragon.I[0x28 ^ 0x20], 4 + 173 - 133 + 132, 0x51 ^ 0x7D);
        this.setTextureOffset(ModelDragon.I[0x48 ^ 0x41], 164 + 79 - 203 + 136, 0xE3 ^ 0xA2);
        this.setTextureOffset(ModelDragon.I[0xCE ^ 0xC4], 0x55 ^ 0x25, 0xEA ^ 0x82);
        this.setTextureOffset(ModelDragon.I[0x13 ^ 0x18], 0xD7 ^ 0xA7, 54 + 132 - 88 + 38);
        this.setTextureOffset(ModelDragon.I[0x5E ^ 0x52], 88 + 68 - 18 + 6, 0x49 ^ 0x21);
        this.setTextureOffset(ModelDragon.I[0x7A ^ 0x77], 165 + 74 - 107 + 60, 0x2F ^ 0x47);
        this.setTextureOffset(ModelDragon.I[0x3B ^ 0x35], 76 + 131 + 8 + 11, 109 + 51 - 102 + 80);
        this.setTextureOffset(ModelDragon.I[0x2F ^ 0x20], 93 + 110 - 188 + 205, 0x62 ^ 0x57);
        this.setTextureOffset(ModelDragon.I[0x89 ^ 0x99], "".length(), "".length());
        this.setTextureOffset(ModelDragon.I[0x6 ^ 0x17], 0x76 ^ 0x46, "".length());
        this.setTextureOffset(ModelDragon.I[0x9A ^ 0x88], 0xE0 ^ 0x90, "".length());
        final float n2 = -16.0f;
        (this.head = new ModelRenderer(this, ModelDragon.I[0x26 ^ 0x35])).addBox(ModelDragon.I[0xA4 ^ 0xB0], -6.0f, -1.0f, -8.0f + n2, 0x5F ^ 0x53, 0x3D ^ 0x38, 0x16 ^ 0x6);
        this.head.addBox(ModelDragon.I[0x55 ^ 0x40], -8.0f, -8.0f, 6.0f + n2, 0x8A ^ 0x9A, 0x7A ^ 0x6A, 0x90 ^ 0x80);
        this.head.mirror = (" ".length() != 0);
        this.head.addBox(ModelDragon.I[0x29 ^ 0x3F], -5.0f, -12.0f, 12.0f + n2, "  ".length(), 0x52 ^ 0x56, 0x6E ^ 0x68);
        this.head.addBox(ModelDragon.I[0x6D ^ 0x7A], -5.0f, -3.0f, -6.0f + n2, "  ".length(), "  ".length(), 0xC6 ^ 0xC2);
        this.head.mirror = ("".length() != 0);
        this.head.addBox(ModelDragon.I[0xAF ^ 0xB7], 3.0f, -12.0f, 12.0f + n2, "  ".length(), 0x25 ^ 0x21, 0x12 ^ 0x14);
        this.head.addBox(ModelDragon.I[0x8A ^ 0x93], 3.0f, -3.0f, -6.0f + n2, "  ".length(), "  ".length(), 0x98 ^ 0x9C);
        (this.jaw = new ModelRenderer(this, ModelDragon.I[0x31 ^ 0x2B])).setRotationPoint(0.0f, 4.0f, 8.0f + n2);
        this.jaw.addBox(ModelDragon.I[0xBA ^ 0xA1], -6.0f, 0.0f, -16.0f, 0xE ^ 0x2, 0x29 ^ 0x2D, 0x3A ^ 0x2A);
        this.head.addChild(this.jaw);
        (this.spine = new ModelRenderer(this, ModelDragon.I[0xDE ^ 0xC2])).addBox(ModelDragon.I[0x76 ^ 0x6B], -5.0f, -5.0f, -5.0f, 0x28 ^ 0x22, 0x3C ^ 0x36, 0x3D ^ 0x37);
        this.spine.addBox(ModelDragon.I[0x96 ^ 0x88], -1.0f, -9.0f, -3.0f, "  ".length(), 0x5F ^ 0x5B, 0x15 ^ 0x13);
        (this.body = new ModelRenderer(this, ModelDragon.I[0x83 ^ 0x9C])).setRotationPoint(0.0f, 4.0f, 8.0f);
        this.body.addBox(ModelDragon.I[0xBF ^ 0x9F], -12.0f, 0.0f, -16.0f, 0x12 ^ 0xA, 0x4B ^ 0x53, 0xDF ^ 0x9F);
        this.body.addBox(ModelDragon.I[0x4E ^ 0x6F], -1.0f, -6.0f, -10.0f, "  ".length(), 0x4E ^ 0x48, 0x12 ^ 0x1E);
        this.body.addBox(ModelDragon.I[0x60 ^ 0x42], -1.0f, -6.0f, 10.0f, "  ".length(), 0x4A ^ 0x4C, 0x17 ^ 0x1B);
        this.body.addBox(ModelDragon.I[0x8A ^ 0xA9], -1.0f, -6.0f, 30.0f, "  ".length(), 0xBF ^ 0xB9, 0x6E ^ 0x62);
        (this.wing = new ModelRenderer(this, ModelDragon.I[0x5D ^ 0x79])).setRotationPoint(-12.0f, 5.0f, 2.0f);
        this.wing.addBox(ModelDragon.I[0x91 ^ 0xB4], -56.0f, -4.0f, -4.0f, 0x19 ^ 0x21, 0x75 ^ 0x7D, 0x43 ^ 0x4B);
        this.wing.addBox(ModelDragon.I[0x48 ^ 0x6E], -56.0f, 0.0f, 2.0f, 0xA ^ 0x32, "".length(), 0x77 ^ 0x4F);
        (this.wingTip = new ModelRenderer(this, ModelDragon.I[0x66 ^ 0x41])).setRotationPoint(-56.0f, 0.0f, 0.0f);
        this.wingTip.addBox(ModelDragon.I[0x33 ^ 0x1B], -56.0f, -2.0f, -2.0f, 0x83 ^ 0xBB, 0x47 ^ 0x43, 0x72 ^ 0x76);
        this.wingTip.addBox(ModelDragon.I[0x1C ^ 0x35], -56.0f, 0.0f, 2.0f, 0x79 ^ 0x41, "".length(), 0x4F ^ 0x77);
        this.wing.addChild(this.wingTip);
        (this.frontLeg = new ModelRenderer(this, ModelDragon.I[0xBA ^ 0x90])).setRotationPoint(-12.0f, 20.0f, 2.0f);
        this.frontLeg.addBox(ModelDragon.I[0x3 ^ 0x28], -4.0f, -4.0f, -4.0f, 0x53 ^ 0x5B, 0xE ^ 0x16, 0x1E ^ 0x16);
        (this.frontLegTip = new ModelRenderer(this, ModelDragon.I[0x53 ^ 0x7F])).setRotationPoint(0.0f, 20.0f, -1.0f);
        this.frontLegTip.addBox(ModelDragon.I[0x40 ^ 0x6D], -3.0f, -1.0f, -3.0f, 0x55 ^ 0x53, 0x8E ^ 0x96, 0x3 ^ 0x5);
        this.frontLeg.addChild(this.frontLegTip);
        (this.frontFoot = new ModelRenderer(this, ModelDragon.I[0x61 ^ 0x4F])).setRotationPoint(0.0f, 23.0f, 0.0f);
        this.frontFoot.addBox(ModelDragon.I[0x55 ^ 0x7A], -4.0f, 0.0f, -12.0f, 0x32 ^ 0x3A, 0x62 ^ 0x66, 0x9A ^ 0x8A);
        this.frontLegTip.addChild(this.frontFoot);
        (this.rearLeg = new ModelRenderer(this, ModelDragon.I[0x98 ^ 0xA8])).setRotationPoint(-16.0f, 16.0f, 42.0f);
        this.rearLeg.addBox(ModelDragon.I[0x8F ^ 0xBE], -8.0f, -4.0f, -8.0f, 0x7C ^ 0x6C, 0x6 ^ 0x26, 0x5B ^ 0x4B);
        (this.rearLegTip = new ModelRenderer(this, ModelDragon.I[0x8B ^ 0xB9])).setRotationPoint(0.0f, 32.0f, -4.0f);
        this.rearLegTip.addBox(ModelDragon.I[0x7E ^ 0x4D], -6.0f, -2.0f, 0.0f, 0xCF ^ 0xC3, 0x73 ^ 0x53, 0x1A ^ 0x16);
        this.rearLeg.addChild(this.rearLegTip);
        (this.rearFoot = new ModelRenderer(this, ModelDragon.I[0x35 ^ 0x1])).setRotationPoint(0.0f, 31.0f, 4.0f);
        this.rearFoot.addBox(ModelDragon.I[0x8F ^ 0xBA], -9.0f, 0.0f, -20.0f, 0xA ^ 0x18, 0x98 ^ 0x9E, 0xB9 ^ 0xA1);
        this.rearLegTip.addChild(this.rearFoot);
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x24 ^ 0x12])["".length()] = I("\u000f.\u000b\u0015C\u000f.\u000b\u0015", "mAolm");
        ModelDragon.I[" ".length()] = I("\u0012\u000f:$T\u0016\r=-", "efTCz");
        ModelDragon.I["  ".length()] = I("\u001c\u0006\u0001\b\u0013\u0002\u001fA\u001c\f\u0002\u0001", "kooog");
        ModelDragon.I["   ".length()] = I("\u00052\u001b\u0018\u0006\u00120T\u0007\u000b\u001e9", "wWzjj");
        ModelDragon.I[0x1D ^ 0x19] = I("\u0014\n)\u00135\t\u0000<O>\u0007\u0006&", "foHaS");
        ModelDragon.I[0x4D ^ 0x48] = I("\"\u000e.4\r5\f;/\u0011~\u0006./\u000f", "PkOFa");
        ModelDragon.I[0xC3 ^ 0xC5] = I("1\u0010)<X,\u00058=\u00041\u0010)<", "YuHXv");
        ModelDragon.I[0x4D ^ 0x4A] = I("=16+m(76)", "JXXLC");
        ModelDragon.I[0x51 ^ 0x59] = I("2/#\u000fg/:2\u000e;6#2", "ZJBkI");
        ModelDragon.I[0x8E ^ 0x87] = I("\u000f(\u0000C\u000b\u0004>", "eIwma");
        ModelDragon.I[0xB0 ^ 0xBA] = I("\"(\u001d=\u0002(?\u0015}\u001b%3\u001c", "DZrSv");
        ModelDragon.I[0xB7 ^ 0xBC] = I("6,\u0019#?(5Y&$/ ", "AEwDK");
        ModelDragon.I[0x4B ^ 0x47] = I(" \u001c#+0 \u0001#1j+\u000f%+", "FnLED");
        ModelDragon.I[0x96 ^ 0x9B] = I("++\u0012>_'!\t", "ENqUq");
        ModelDragon.I[0x16 ^ 0x18] = I("\u0003+**'\t<\"0:\u0015w(%:\u000b", "eYEDS");
        ModelDragon.I[0x38 ^ 0x37] = I("\u000e)\u001e=J\u001f%\u001b(\u0001", "lFzDd");
        ModelDragon.I[0x73 ^ 0x63] = I("8*\u0017\tl#,\u0017\u0001'", "POvmB");
        ModelDragon.I[0x55 ^ 0x44] = I("-1\r\u0003@07\u000f\u0004\u000b", "CTnhn");
        ModelDragon.I[0xAC ^ 0xBE] = I("\u000e*\u00071g\b \u0015!;\u000f#", "fOfUI");
        ModelDragon.I[0xBD ^ 0xAE] = I("-\u00145\u0017", "EqTso");
        ModelDragon.I[0x58 ^ 0x4C] = I("8\u0000!\u0006+!\u0019!", "MpQcY");
        ModelDragon.I[0x44 ^ 0x51] = I("8?\u00137(%*\u00026", "MOcRZ");
        ModelDragon.I[0x3F ^ 0x29] = I("<1#;(", "ORBWM");
        ModelDragon.I[0xD0 ^ 0xC7] = I("\u0002%\u0007,\u0010\u0005&", "lJtXb");
        ModelDragon.I[0x25 ^ 0x3D] = I("\u0010\r4*\n", "cnUFo");
        ModelDragon.I[0x51 ^ 0x48] = I("\u0006#+!$\u0001 ", "hLXUV");
        ModelDragon.I[0x2F ^ 0x35] = I(">\u0000>", "TaIVd");
        ModelDragon.I[0x44 ^ 0x5F] = I("(\u0017\u001c", "BvkhU");
        ModelDragon.I[0x26 ^ 0x3A] = I("\u001c2\u0013<", "rWpWk");
        ModelDragon.I[0x8E ^ 0x93] = I("2\u001f\u000b", "PpsKH");
        ModelDragon.I[0x5E ^ 0x40] = I("5\u0000\u0005\u0014\u000f", "Fcdxj");
        ModelDragon.I[0x95 ^ 0x8A] = I(";\u001d3\u001b", "YrWbP");
        ModelDragon.I[0x76 ^ 0x56] = I("\u0006\u00074+", "dhPRc");
        ModelDragon.I[0x8D ^ 0xAC] = I("$.0\t(", "WMQeM");
        ModelDragon.I[0x9D ^ 0xBF] = I("27\u00159 ", "ATtUE");
        ModelDragon.I[0xE2 ^ 0xC1] = I("7*\u0002\u0015,", "DIcyI");
        ModelDragon.I[0x3F ^ 0x1B] = I("1<*\u000e", "FUDiA");
        ModelDragon.I[0x3A ^ 0x1F] = I("7(>\u0001", "UGPda");
        ModelDragon.I[0x6E ^ 0x48] = I("5\u0002\u0011\u001d", "FixsK");
        ModelDragon.I[0x12 ^ 0x35] = I("2*>#',3", "ECPDS");
        ModelDragon.I[0x1C ^ 0x34] = I("\u000f?\u0007\u001f", "mPizr");
        ModelDragon.I[0xB7 ^ 0x9E] = I("*?'<", "YTNRV");
        ModelDragon.I[0x98 ^ 0xB2] = I("2;\u001f\u001c08,\u0017", "TIprD");
        ModelDragon.I[0x54 ^ 0x7F] = I("\u00043\u0005\b", "iRlfR");
        ModelDragon.I[0x6 ^ 0x2A] = I("(:%6\u0013\"--,\u000e>", "NHJXg");
        ModelDragon.I[0x5F ^ 0x72] = I("#\u000f\u0005<", "NnlRq");
        ModelDragon.I[0xAB ^ 0x85] = I("\r\u0019%\u001b8\r\u0004%\u0001", "kkJuL");
        ModelDragon.I[0x3D ^ 0x12] = I("\u0006\".\u001e", "kCGpW");
        ModelDragon.I[0xB9 ^ 0x89] = I("\u0000\u001f35\u0006\u0017\u001d", "rzRGj");
        ModelDragon.I[0x75 ^ 0x44] = I("\u001c1\u0018+", "qPqES");
        ModelDragon.I[0x58 ^ 0x6A] = I("=\u000f8\u0000 *\r-\u001b<", "OjYrL");
        ModelDragon.I[0x73 ^ 0x40] = I("\u001c\b,\u0017", "qiEyK");
        ModelDragon.I[0x13 ^ 0x27] = I("\u0004\u0000\n+ \u0019\n\u001f", "vekYF");
        ModelDragon.I[0x81 ^ 0xB4] = I("\u001a,\u001b\u001f", "wMrqe");
    }
    
    private float updateRotations(double n) {
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (n >= 180.0) {
            n -= 360.0;
        }
        "".length();
        if (false) {
            throw null;
        }
        while (n < -180.0) {
            n += 360.0;
        }
        return (float)n;
    }
}
