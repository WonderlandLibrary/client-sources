package net.minecraft.client.renderer.tileentity;

import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;

public class RenderWitherSkull extends Render<EntityWitherSkull>
{
    private static final ResourceLocation invulnerableWitherTextures;
    private static final String[] I;
    private final ModelSkeletonHead skeletonHeadModel;
    private static final ResourceLocation witherTextures;
    
    @Override
    public void doRender(final EntityWitherSkull entityWitherSkull, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        final float func_82400_a = this.func_82400_a(entityWitherSkull.prevRotationYaw, entityWitherSkull.rotationYaw, n5);
        final float n6 = entityWitherSkull.prevRotationPitch + (entityWitherSkull.rotationPitch - entityWitherSkull.prevRotationPitch) * n5;
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n7 = 0.0625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entityWitherSkull);
        this.skeletonHeadModel.render(entityWitherSkull, 0.0f, 0.0f, 0.0f, func_82400_a, n6, n7);
        GlStateManager.popMatrix();
        super.doRender(entityWitherSkull, n, n2, n3, n4, n5);
    }
    
    public RenderWitherSkull(final RenderManager renderManager) {
        super(renderManager);
        this.skeletonHeadModel = new ModelSkeletonHead();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityWitherSkull entityWitherSkull) {
        ResourceLocation resourceLocation;
        if (entityWitherSkull.isInvulnerable()) {
            resourceLocation = RenderWitherSkull.invulnerableWitherTextures;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderWitherSkull.witherTextures;
        }
        return resourceLocation;
    }
    
    static {
        I();
        invulnerableWitherTextures = new ResourceLocation(RenderWitherSkull.I["".length()]);
        witherTextures = new ResourceLocation(RenderWitherSkull.I[" ".length()]);
    }
    
    private float func_82400_a(final float n, final float n2, final float n3) {
        float n4 = n2 - n;
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (n4 < -180.0f) {
            n4 += 360.0f;
        }
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityWitherSkull)entity, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("%,:\u0001\u0006#,1Z\u0016?=+\u0001\n~>+\u0001\u001b4;m\u0002\u001a%!'\u0007,8'4\u0000\u001f?,0\u0014\u0011=,l\u0005\u001d6", "QIBus");
        RenderWitherSkull.I[" ".length()] = I("\u0001!(:#\u0007!#a3\u001b09:/Z39:>\u00106\u007f9?\u0001,5<x\u0005*7", "uDPNV");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityWitherSkull)entity);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
