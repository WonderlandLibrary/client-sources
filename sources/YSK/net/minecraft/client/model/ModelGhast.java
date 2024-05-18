package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;

public class ModelGhast extends ModelBase
{
    ModelRenderer body;
    ModelRenderer[] tentacles;
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.setRotationAngles(n, n2, n3, n4, n5, n6, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.6f, 0.0f);
        this.body.render(n6);
        final ModelRenderer[] tentacles;
        final int length = (tentacles = this.tentacles).length;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < length) {
            tentacles[i].render(n6);
            ++i;
        }
        GlStateManager.popMatrix();
    }
    
    @Override
    public void setRotationAngles(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final Entity entity) {
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < this.tentacles.length) {
            this.tentacles[i].rotateAngleX = 0.2f * MathHelper.sin(n3 * 0.3f + i) + 0.4f;
            ++i;
        }
    }
    
    public ModelGhast() {
        this.tentacles = new ModelRenderer[0xA6 ^ 0xAF];
        final int n = -(0x59 ^ 0x49);
        (this.body = new ModelRenderer(this, "".length(), "".length())).addBox(-8.0f, -8.0f, -8.0f, 0x1 ^ 0x11, 0x77 ^ 0x67, 0xAF ^ 0xBF);
        final ModelRenderer body = this.body;
        body.rotationPointY += (0xAC ^ 0xB4) + n;
        final Random random = new Random(1660L);
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < this.tentacles.length) {
            this.tentacles[i] = new ModelRenderer(this, "".length(), "".length());
            final float rotationPointX = ((i % "   ".length() - i / "   ".length() % "  ".length() * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            final float rotationPointZ = (i / "   ".length() / 2.0f * 2.0f - 1.0f) * 5.0f;
            this.tentacles[i].addBox(-1.0f, 0.0f, -1.0f, "  ".length(), random.nextInt(0x1D ^ 0x1A) + (0x87 ^ 0x8F), "  ".length());
            this.tentacles[i].rotationPointX = rotationPointX;
            this.tentacles[i].rotationPointZ = rotationPointZ;
            this.tentacles[i].rotationPointY = (0x5C ^ 0x43) + n;
            ++i;
        }
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
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
