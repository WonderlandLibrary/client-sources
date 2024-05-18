package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class ModelGuardian extends ModelBase
{
    private ModelRenderer guardianEye;
    private ModelRenderer guardianBody;
    private ModelRenderer[] guardianSpines;
    private ModelRenderer[] guardianTail;
    
    public ModelGuardian() {
        this.textureWidth = (0xFD ^ 0xBD);
        this.textureHeight = (0x84 ^ 0xC4);
        this.guardianSpines = new ModelRenderer[0x61 ^ 0x6D];
        this.guardianBody = new ModelRenderer(this);
        this.guardianBody.setTextureOffset("".length(), "".length()).addBox(-6.0f, 10.0f, -8.0f, 0xB9 ^ 0xB5, 0xB0 ^ 0xBC, 0x3C ^ 0x2C);
        this.guardianBody.setTextureOffset("".length(), 0x9C ^ 0x80).addBox(-8.0f, 10.0f, -6.0f, "  ".length(), 0xCA ^ 0xC6, 0x33 ^ 0x3F);
        this.guardianBody.setTextureOffset("".length(), 0x7C ^ 0x60).addBox(6.0f, 10.0f, -6.0f, "  ".length(), 0x57 ^ 0x5B, 0x1E ^ 0x12, " ".length() != 0);
        this.guardianBody.setTextureOffset(0x98 ^ 0x88, 0xA4 ^ 0x8C).addBox(-6.0f, 8.0f, -6.0f, 0x5 ^ 0x9, "  ".length(), 0x3 ^ 0xF);
        this.guardianBody.setTextureOffset(0x90 ^ 0x80, 0xA9 ^ 0x81).addBox(-6.0f, 22.0f, -6.0f, 0xCE ^ 0xC2, "  ".length(), 0x7D ^ 0x71);
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.guardianSpines.length) {
            (this.guardianSpines[i] = new ModelRenderer(this, "".length(), "".length())).addBox(-1.0f, -4.5f, -1.0f, "  ".length(), 0x11 ^ 0x18, "  ".length());
            this.guardianBody.addChild(this.guardianSpines[i]);
            ++i;
        }
        (this.guardianEye = new ModelRenderer(this, 0xB6 ^ 0xBE, "".length())).addBox(-1.0f, 15.0f, 0.0f, "  ".length(), "  ".length(), " ".length());
        this.guardianBody.addChild(this.guardianEye);
        this.guardianTail = new ModelRenderer["   ".length()];
        (this.guardianTail["".length()] = new ModelRenderer(this, 0x9B ^ 0xB3, "".length())).addBox(-2.0f, 14.0f, 7.0f, 0x0 ^ 0x4, 0x2E ^ 0x2A, 0x6E ^ 0x66);
        (this.guardianTail[" ".length()] = new ModelRenderer(this, "".length(), 0x1B ^ 0x2D)).addBox(0.0f, 14.0f, 0.0f, "   ".length(), "   ".length(), 0xB8 ^ 0xBF);
        this.guardianTail["  ".length()] = new ModelRenderer(this);
        this.guardianTail["  ".length()].setTextureOffset(0xAA ^ 0x83, 0x1A ^ 0x3A).addBox(0.0f, 14.0f, 0.0f, "  ".length(), "  ".length(), 0x93 ^ 0x95);
        this.guardianTail["  ".length()].setTextureOffset(0x32 ^ 0x2B, 0x9C ^ 0x8F).addBox(1.0f, 10.5f, 3.0f, " ".length(), 0x8E ^ 0x87, 0xB6 ^ 0xBF);
        this.guardianBody.addChild(this.guardianTail["".length()]);
        this.guardianTail["".length()].addChild(this.guardianTail[" ".length()]);
        this.guardianTail[" ".length()].addChild(this.guardianTail["  ".length()]);
    }
    
    public int func_178706_a() {
        return 0x8C ^ 0xBA;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final EntityGuardian entityGuardian = (EntityGuardian)entity;
        final float n7 = n3 - entityGuardian.ticksExisted;
        this.guardianBody.rotateAngleY = n4 / 57.295776f;
        this.guardianBody.rotateAngleX = n5 / 57.295776f;
        final float[] array = new float[0x6D ^ 0x61];
        array["".length()] = 1.75f;
        array[" ".length()] = 0.25f;
        array["  ".length()] = 0.0f;
        array["   ".length()] = 0.0f;
        array[0x2A ^ 0x2F] = (array[0x76 ^ 0x72] = 0.5f);
        array[0x99 ^ 0x9E] = (array[0x7B ^ 0x7D] = 0.5f);
        array[0x38 ^ 0x30] = 1.25f;
        array[0x97 ^ 0x9E] = 0.75f;
        array[0x3E ^ 0x35] = (array[0x7C ^ 0x76] = 0.0f);
        final float[] array2 = array;
        final float[] array3 = new float[0x3E ^ 0x32];
        array3["".length()] = 0.0f;
        array3[" ".length()] = 0.0f;
        array3["  ".length()] = 0.0f;
        array3["   ".length()] = 0.0f;
        array3[0x8A ^ 0x8E] = 0.25f;
        array3[0x29 ^ 0x2C] = 1.75f;
        array3[0x7B ^ 0x7D] = 1.25f;
        array3[0x99 ^ 0x9E] = 0.75f;
        array3[0xBC ^ 0xB5] = (array3[0x5B ^ 0x53] = 0.0f);
        array3[0xA ^ 0x1] = (array3[0x68 ^ 0x62] = 0.0f);
        final float[] array4 = array3;
        final float[] array5 = new float[0x41 ^ 0x4D];
        array5["".length()] = 0.0f;
        array5[" ".length()] = 0.0f;
        array5["  ".length()] = 0.25f;
        array5["   ".length()] = 1.75f;
        array5[0xAB ^ 0xAE] = (array5[0xAA ^ 0xAE] = 0.0f);
        array5[0x61 ^ 0x66] = (array5[0x36 ^ 0x30] = 0.0f);
        array5[0xB9 ^ 0xB0] = (array5[0x5 ^ 0xD] = 0.0f);
        array5[0xB7 ^ 0xBD] = 0.75f;
        array5[0x17 ^ 0x1C] = 1.25f;
        final float[] array6 = array5;
        final float[] array7 = new float[0x7C ^ 0x70];
        array7["".length()] = 0.0f;
        array7[" ".length()] = 0.0f;
        array7["  ".length()] = 8.0f;
        array7[0x58 ^ 0x5C] = (array7["   ".length()] = -8.0f);
        array7[0x2D ^ 0x2B] = (array7[0xA3 ^ 0xA6] = 8.0f);
        array7[0x89 ^ 0x8E] = -8.0f;
        array7[0x7B ^ 0x72] = (array7[0x4F ^ 0x47] = 0.0f);
        array7[0xC8 ^ 0xC2] = 8.0f;
        array7[0x1 ^ 0xA] = -8.0f;
        final float[] array8 = array7;
        final float[] array9 = new float[0x61 ^ 0x6D];
        array9["".length()] = -8.0f;
        array9[" ".length()] = -8.0f;
        array9["  ".length()] = -8.0f;
        array9["   ".length()] = -8.0f;
        array9[0x7C ^ 0x79] = (array9[0x59 ^ 0x5D] = 0.0f);
        array9[0x3F ^ 0x38] = (array9[0xB7 ^ 0xB1] = 0.0f);
        array9[0x75 ^ 0x7C] = (array9[0xCB ^ 0xC3] = 8.0f);
        array9[0x2C ^ 0x27] = (array9[0x2B ^ 0x21] = 8.0f);
        final float[] array10 = array9;
        final float[] array11 = new float[0xA5 ^ 0xA9];
        array11["".length()] = 8.0f;
        array11[" ".length()] = -8.0f;
        array11["  ".length()] = 0.0f;
        array11["   ".length()] = 0.0f;
        array11[0x94 ^ 0x91] = (array11[0x8A ^ 0x8E] = -8.0f);
        array11[0x3F ^ 0x39] = 8.0f;
        array11[0x66 ^ 0x6E] = (array11[0x20 ^ 0x27] = 8.0f);
        array11[0x29 ^ 0x20] = -8.0f;
        array11[0x4A ^ 0x41] = (array11[0xE ^ 0x4] = 0.0f);
        final float[] array12 = array11;
        final float n8 = (1.0f - entityGuardian.func_175469_o(n7)) * 0.55f;
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < (0x5A ^ 0x56)) {
            this.guardianSpines[i].rotateAngleX = 3.1415927f * array2[i];
            this.guardianSpines[i].rotateAngleY = 3.1415927f * array4[i];
            this.guardianSpines[i].rotateAngleZ = 3.1415927f * array6[i];
            this.guardianSpines[i].rotationPointX = array8[i] * (1.0f + MathHelper.cos(n3 * 1.5f + i) * 0.01f - n8);
            this.guardianSpines[i].rotationPointY = 16.0f + array10[i] * (1.0f + MathHelper.cos(n3 * 1.5f + i) * 0.01f - n8);
            this.guardianSpines[i].rotationPointZ = array12[i] * (1.0f + MathHelper.cos(n3 * 1.5f + i) * 0.01f - n8);
            ++i;
        }
        this.guardianEye.rotationPointZ = -8.25f;
        Entity entity2 = Minecraft.getMinecraft().getRenderViewEntity();
        if (entityGuardian.hasTargetedEntity()) {
            entity2 = entityGuardian.getTargetedEntity();
        }
        if (entity2 != null) {
            final Vec3 positionEyes = entity2.getPositionEyes(0.0f);
            final Vec3 positionEyes2 = entity.getPositionEyes(0.0f);
            if (positionEyes.yCoord - positionEyes2.yCoord > 0.0) {
                this.guardianEye.rotationPointY = 0.0f;
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                this.guardianEye.rotationPointY = 1.0f;
            }
            final Vec3 look = entity.getLook(0.0f);
            final double dotProduct = new Vec3(look.xCoord, 0.0, look.zCoord).dotProduct(new Vec3(positionEyes2.xCoord - positionEyes.xCoord, 0.0, positionEyes2.zCoord - positionEyes.zCoord).normalize().rotateYaw(1.5707964f));
            this.guardianEye.rotationPointX = MathHelper.sqrt_float((float)Math.abs(dotProduct)) * 2.0f * (float)Math.signum(dotProduct);
        }
        this.guardianEye.showModel = (" ".length() != 0);
        final float func_175471_a = entityGuardian.func_175471_a(n7);
        this.guardianTail["".length()].rotateAngleY = MathHelper.sin(func_175471_a) * 3.1415927f * 0.05f;
        this.guardianTail[" ".length()].rotateAngleY = MathHelper.sin(func_175471_a) * 3.1415927f * 0.1f;
        this.guardianTail[" ".length()].rotationPointX = -1.5f;
        this.guardianTail[" ".length()].rotationPointY = 0.5f;
        this.guardianTail[" ".length()].rotationPointZ = 14.0f;
        this.guardianTail["  ".length()].rotateAngleY = MathHelper.sin(func_175471_a) * 3.1415927f * 0.15f;
        this.guardianTail["  ".length()].rotationPointX = 0.5f;
        this.guardianTail["  ".length()].rotationPointY = 0.5f;
        this.guardianTail["  ".length()].rotationPointZ = 6.0f;
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        this.guardianBody.render(n6);
    }
}
