package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;

public class RenderMinecart<T extends EntityMinecart> extends Render<T>
{
    protected ModelBase modelMinecart;
    private static final String[] I;
    private static final ResourceLocation minecartTextures;
    
    @Override
    public void doRender(final T t, double n, double n2, double n3, float n4, final float n5) {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(t);
        final long n6 = t.getEntityId() * 493286711L;
        final long n7 = n6 * n6 * 4392167121L + n6 * 98761L;
        GlStateManager.translate((((n7 >> (0xE ^ 0x1E) & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f, (((n7 >> (0x3D ^ 0x29) & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f, (((n7 >> (0xB3 ^ 0xAB) & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f);
        final double n8 = t.lastTickPosX + (t.posX - t.lastTickPosX) * n5;
        final double n9 = t.lastTickPosY + (t.posY - t.lastTickPosY) * n5;
        final double n10 = t.lastTickPosZ + (t.posZ - t.lastTickPosZ) * n5;
        final double n11 = 0.30000001192092896;
        final Vec3 func_70489_a = t.func_70489_a(n8, n9, n10);
        float n12 = t.prevRotationPitch + (t.rotationPitch - t.prevRotationPitch) * n5;
        if (func_70489_a != null) {
            Vec3 func_70495_a = t.func_70495_a(n8, n9, n10, n11);
            Vec3 func_70495_a2 = t.func_70495_a(n8, n9, n10, -n11);
            if (func_70495_a == null) {
                func_70495_a = func_70489_a;
            }
            if (func_70495_a2 == null) {
                func_70495_a2 = func_70489_a;
            }
            n += func_70489_a.xCoord - n8;
            n2 += (func_70495_a.yCoord + func_70495_a2.yCoord) / 2.0 - n9;
            n3 += func_70489_a.zCoord - n10;
            final Vec3 addVector = func_70495_a2.addVector(-func_70495_a.xCoord, -func_70495_a.yCoord, -func_70495_a.zCoord);
            if (addVector.lengthVector() != 0.0) {
                final Vec3 normalize = addVector.normalize();
                n4 = (float)(Math.atan2(normalize.zCoord, normalize.xCoord) * 180.0 / 3.141592653589793);
                n12 = (float)(Math.atan(normalize.yCoord) * 73.0);
            }
        }
        GlStateManager.translate((float)n, (float)n2 + 0.375f, (float)n3);
        GlStateManager.rotate(180.0f - n4, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-n12, 0.0f, 0.0f, 1.0f);
        final float n13 = t.getRollingAmplitude() - n5;
        float n14 = t.getDamage() - n5;
        if (n14 < 0.0f) {
            n14 = 0.0f;
        }
        if (n13 > 0.0f) {
            GlStateManager.rotate(MathHelper.sin(n13) * n13 * n14 / 10.0f * t.getRollingDirection(), 1.0f, 0.0f, 0.0f);
        }
        final int displayTileOffset = t.getDisplayTileOffset();
        final IBlockState displayTile = t.getDisplayTile();
        if (displayTile.getBlock().getRenderType() != -" ".length()) {
            GlStateManager.pushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            final float n15 = 0.75f;
            GlStateManager.scale(n15, n15, n15);
            GlStateManager.translate(-0.5f, (displayTileOffset - (0xA6 ^ 0xAE)) / 16.0f, 0.5f);
            this.func_180560_a(t, n5, displayTile);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindEntityTexture(t);
        }
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.modelMinecart.render(t, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.popMatrix();
        super.doRender(t, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0007#\u0001\u0006\u0011\u0001#\n]\u0001\u001d2\u0010\u0006\u001d\\+\u0010\u001c\u0001\u0010'\u000b\u0006J\u0003(\u001e", "sFyrd");
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void func_180560_a(final T t, final float n, final IBlockState blockState) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(blockState, t.getBrightness(n));
        GlStateManager.popMatrix();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final T t) {
        return RenderMinecart.minecartTextures;
    }
    
    static {
        I();
        minecartTextures = new ResourceLocation(RenderMinecart.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityMinecart)entity);
    }
    
    public RenderMinecart(final RenderManager renderManager) {
        super(renderManager);
        this.modelMinecart = new ModelMinecart();
        this.shadowSize = 0.5f;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityMinecart)entity, n, n2, n3, n4, n5);
    }
}
