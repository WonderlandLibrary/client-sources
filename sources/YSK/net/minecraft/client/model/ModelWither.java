package net.minecraft.client.model;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;

public class ModelWither extends ModelBase
{
    private ModelRenderer[] field_82905_a;
    private ModelRenderer[] field_82904_b;
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        final float cos = MathHelper.cos(n3 * 0.1f);
        this.field_82905_a[" ".length()].rotateAngleX = (0.065f + 0.05f * cos) * 3.1415927f;
        this.field_82905_a["  ".length()].setRotationPoint(-2.0f, 6.9f + MathHelper.cos(this.field_82905_a[" ".length()].rotateAngleX) * 10.0f, -0.5f + MathHelper.sin(this.field_82905_a[" ".length()].rotateAngleX) * 10.0f);
        this.field_82905_a["  ".length()].rotateAngleX = (0.265f + 0.1f * cos) * 3.1415927f;
        this.field_82904_b["".length()].rotateAngleY = n4 / 57.295776f;
        this.field_82904_b["".length()].rotateAngleX = n5 / 57.295776f;
    }
    
    public ModelWither(final float n) {
        this.textureWidth = (0x6E ^ 0x2E);
        this.textureHeight = (0x86 ^ 0xC6);
        this.field_82905_a = new ModelRenderer["   ".length()];
        (this.field_82905_a["".length()] = new ModelRenderer(this, "".length(), 0x88 ^ 0x98)).addBox(-10.0f, 3.9f, -0.5f, 0x1C ^ 0x8, "   ".length(), "   ".length(), n);
        (this.field_82905_a[" ".length()] = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight)).setRotationPoint(-2.0f, 6.9f, -0.5f);
        this.field_82905_a[" ".length()].setTextureOffset("".length(), 0x97 ^ 0x81).addBox(0.0f, 0.0f, 0.0f, "   ".length(), 0x90 ^ 0x9A, "   ".length(), n);
        this.field_82905_a[" ".length()].setTextureOffset(0xB4 ^ 0xAC, 0x7A ^ 0x6C).addBox(-4.0f, 1.5f, 0.5f, 0xB3 ^ 0xB8, "  ".length(), "  ".length(), n);
        this.field_82905_a[" ".length()].setTextureOffset(0x76 ^ 0x6E, 0x92 ^ 0x84).addBox(-4.0f, 4.0f, 0.5f, 0xB1 ^ 0xBA, "  ".length(), "  ".length(), n);
        this.field_82905_a[" ".length()].setTextureOffset(0x7B ^ 0x63, 0x1B ^ 0xD).addBox(-4.0f, 6.5f, 0.5f, 0x29 ^ 0x22, "  ".length(), "  ".length(), n);
        (this.field_82905_a["  ".length()] = new ModelRenderer(this, 0x3E ^ 0x32, 0x4A ^ 0x5C)).addBox(0.0f, 0.0f, 0.0f, "   ".length(), 0x27 ^ 0x21, "   ".length(), n);
        this.field_82904_b = new ModelRenderer["   ".length()];
        (this.field_82904_b["".length()] = new ModelRenderer(this, "".length(), "".length())).addBox(-4.0f, -4.0f, -4.0f, 0x73 ^ 0x7B, 0x5A ^ 0x52, 0x24 ^ 0x2C, n);
        (this.field_82904_b[" ".length()] = new ModelRenderer(this, 0x57 ^ 0x77, "".length())).addBox(-4.0f, -4.0f, -4.0f, 0xAE ^ 0xA8, 0x74 ^ 0x72, 0xC ^ 0xA, n);
        this.field_82904_b[" ".length()].rotationPointX = -8.0f;
        this.field_82904_b[" ".length()].rotationPointY = 4.0f;
        (this.field_82904_b["  ".length()] = new ModelRenderer(this, 0xB ^ 0x2B, "".length())).addBox(-4.0f, -4.0f, -4.0f, 0x80 ^ 0x86, 0x62 ^ 0x64, 0x5C ^ 0x5A, n);
        this.field_82904_b["  ".length()].rotationPointX = 10.0f;
        this.field_82904_b["  ".length()].rotationPointY = 4.0f;
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        final ModelRenderer[] field_82904_b;
        final int length = (field_82904_b = this.field_82904_b).length;
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < length) {
            field_82904_b[i].render(n6);
            ++i;
        }
        final ModelRenderer[] field_82905_a;
        final int length2 = (field_82905_a = this.field_82905_a).length;
        int j = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (j < length2) {
            field_82905_a[j].render(n6);
            ++j;
        }
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        final EntityWither entityWither = (EntityWither)entityLivingBase;
        int i = " ".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (i < "   ".length()) {
            this.field_82904_b[i].rotateAngleY = (entityWither.func_82207_a(i - " ".length()) - entityLivingBase.renderYawOffset) / 57.295776f;
            this.field_82904_b[i].rotateAngleX = entityWither.func_82210_r(i - " ".length()) / 57.295776f;
            ++i;
        }
    }
}
