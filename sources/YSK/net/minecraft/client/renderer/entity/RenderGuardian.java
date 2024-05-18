package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;

public class RenderGuardian extends RenderLiving<EntityGuardian>
{
    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE;
    private static final String[] I;
    private static final ResourceLocation GUARDIAN_ELDER_TEXTURE;
    int field_177115_a;
    private static final ResourceLocation GUARDIAN_TEXTURE;
    
    @Override
    public boolean shouldRender(final EntityLiving entityLiving, final ICamera camera, final double n, final double n2, final double n3) {
        return this.shouldRender((EntityGuardian)entityLiving, camera, n, n2, n3);
    }
    
    @Override
    public void doRender(final EntityLiving entityLiving, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityGuardian)entityLiving, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("!\u00061&\u0003'\u0006:}\u0013;\u0017 &\u000fz\u0004<3\u00041\n(<X%\r.", "UcIRv");
        RenderGuardian.I[" ".length()] = I("#*9\u0001\u0019%*2Z\t9;(\u0001\u0015x(4\u0014\u001e3& \u001b32#%\u0010\u001ey?/\u0012", "WOAul");
        RenderGuardian.I["  ".length()] = I("\u0003\u0001/\u0006\u0001\u0005\u0001$]\u0011\u0019\u0010>\u0006\rX\u0003\"\u0013\u0006\u0013\r6\u001c+\u0015\u00016\u001fZ\u0007\n0", "wdWrt");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityGuardian)entity);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityGuardian entityGuardian) {
        ResourceLocation resourceLocation;
        if (entityGuardian.isElder()) {
            resourceLocation = RenderGuardian.GUARDIAN_ELDER_TEXTURE;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            resourceLocation = RenderGuardian.GUARDIAN_TEXTURE;
        }
        return resourceLocation;
    }
    
    static {
        I();
        GUARDIAN_TEXTURE = new ResourceLocation(RenderGuardian.I["".length()]);
        GUARDIAN_ELDER_TEXTURE = new ResourceLocation(RenderGuardian.I[" ".length()]);
        GUARDIAN_BEAM_TEXTURE = new ResourceLocation(RenderGuardian.I["  ".length()]);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityGuardian)entityLivingBase, n);
    }
    
    @Override
    public boolean shouldRender(final EntityGuardian entityGuardian, final ICamera camera, final double n, final double n2, final double n3) {
        if (super.shouldRender(entityGuardian, camera, n, n2, n3)) {
            return " ".length() != 0;
        }
        if (entityGuardian.hasTargetedEntity()) {
            final EntityLivingBase targetedEntity = entityGuardian.getTargetedEntity();
            if (targetedEntity != null) {
                final Vec3 func_177110_a = this.func_177110_a(targetedEntity, targetedEntity.height * 0.5, 1.0f);
                final Vec3 func_177110_a2 = this.func_177110_a(entityGuardian, entityGuardian.getEyeHeight(), 1.0f);
                if (camera.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(func_177110_a2.xCoord, func_177110_a2.yCoord, func_177110_a2.zCoord, func_177110_a.xCoord, func_177110_a.yCoord, func_177110_a.zCoord))) {
                    return " ".length() != 0;
                }
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public void doRender(final EntityGuardian entityGuardian, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (this.field_177115_a != ((ModelGuardian)this.mainModel).func_178706_a()) {
            this.mainModel = new ModelGuardian();
            this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
        }
        super.doRender(entityGuardian, n, n2, n3, n4, n5);
        final EntityLivingBase targetedEntity = entityGuardian.getTargetedEntity();
        if (targetedEntity != null) {
            final float func_175477_p = entityGuardian.func_175477_p(n5);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.bindTexture(RenderGuardian.GUARDIAN_BEAM_TEXTURE);
            GL11.glTexParameterf(71 + 2007 + 677 + 798, 6487 + 2317 - 1396 + 2834, 10497.0f);
            GL11.glTexParameterf(864 + 2949 - 3741 + 3481, 9526 + 5733 - 5155 + 139, 10497.0f);
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(" ".length() != 0);
            final float n6 = 240.0f;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n6, n6);
            GlStateManager.tryBlendFuncSeparate(380 + 540 - 805 + 655, " ".length(), " ".length(), "".length());
            final float n7 = entityGuardian.worldObj.getTotalWorldTime() + n5;
            final float n8 = n7 * 0.5f % 1.0f;
            final float eyeHeight = entityGuardian.getEyeHeight();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)n, (float)n2 + eyeHeight, (float)n3);
            final Vec3 subtract = this.func_177110_a(targetedEntity, targetedEntity.height * 0.5, n5).subtract(this.func_177110_a(entityGuardian, eyeHeight, n5));
            final double n9 = subtract.lengthVector() + 1.0;
            final Vec3 normalize = subtract.normalize();
            final float n10 = (float)Math.acos(normalize.yCoord);
            GlStateManager.rotate((1.5707964f + -(float)Math.atan2(normalize.zCoord, normalize.xCoord)) * 57.295776f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(n10 * 57.295776f, 1.0f, 0.0f, 0.0f);
            final int length = " ".length();
            final double n11 = n7 * 0.05 * (1.0 - (length & " ".length()) * 2.5);
            worldRenderer.begin(0x7E ^ 0x79, DefaultVertexFormats.POSITION_TEX_COLOR);
            final float n12 = func_175477_p * func_175477_p;
            final int n13 = (0xF5 ^ 0xB5) + (int)(n12 * 240.0f);
            final int n14 = (0x5F ^ 0x7F) + (int)(n12 * 192.0f);
            final int n15 = 84 + 12 - 83 + 115 - (int)(n12 * 64.0f);
            final double n16 = length * 0.2;
            final double n17 = n16 * 1.41;
            final double n18 = 0.0 + Math.cos(n11 + 2.356194490192345) * n17;
            final double n19 = 0.0 + Math.sin(n11 + 2.356194490192345) * n17;
            final double n20 = 0.0 + Math.cos(n11 + 0.7853981633974483) * n17;
            final double n21 = 0.0 + Math.sin(n11 + 0.7853981633974483) * n17;
            final double n22 = 0.0 + Math.cos(n11 + 3.9269908169872414) * n17;
            final double n23 = 0.0 + Math.sin(n11 + 3.9269908169872414) * n17;
            final double n24 = 0.0 + Math.cos(n11 + 5.497787143782138) * n17;
            final double n25 = 0.0 + Math.sin(n11 + 5.497787143782138) * n17;
            final double n26 = 0.0 + Math.cos(n11 + 3.141592653589793) * n16;
            final double n27 = 0.0 + Math.sin(n11 + 3.141592653589793) * n16;
            final double n28 = 0.0 + Math.cos(n11 + 0.0) * n16;
            final double n29 = 0.0 + Math.sin(n11 + 0.0) * n16;
            final double n30 = 0.0 + Math.cos(n11 + 1.5707963267948966) * n16;
            final double n31 = 0.0 + Math.sin(n11 + 1.5707963267948966) * n16;
            final double n32 = 0.0 + Math.cos(n11 + 4.71238898038469) * n16;
            final double n33 = 0.0 + Math.sin(n11 + 4.71238898038469) * n16;
            final double n34 = -1.0f + n8;
            final double n35 = n9 * (0.5 / n16) + n34;
            worldRenderer.pos(n26, n9, n27).tex(0.4999, n35).color(n13, n14, n15, 183 + 152 - 113 + 33).endVertex();
            worldRenderer.pos(n26, 0.0, n27).tex(0.4999, n34).color(n13, n14, n15, 174 + 57 - 8 + 32).endVertex();
            worldRenderer.pos(n28, 0.0, n29).tex(0.0, n34).color(n13, n14, n15, 47 + 52 + 150 + 6).endVertex();
            worldRenderer.pos(n28, n9, n29).tex(0.0, n35).color(n13, n14, n15, 22 + 203 - 121 + 151).endVertex();
            worldRenderer.pos(n30, n9, n31).tex(0.4999, n35).color(n13, n14, n15, 219 + 121 - 198 + 113).endVertex();
            worldRenderer.pos(n30, 0.0, n31).tex(0.4999, n34).color(n13, n14, n15, 110 + 167 - 262 + 240).endVertex();
            worldRenderer.pos(n32, 0.0, n33).tex(0.0, n34).color(n13, n14, n15, 230 + 2 - 80 + 103).endVertex();
            worldRenderer.pos(n32, n9, n33).tex(0.0, n35).color(n13, n14, n15, 28 + 127 - 33 + 133).endVertex();
            double n36 = 0.0;
            if (entityGuardian.ticksExisted % "  ".length() == 0) {
                n36 = 0.5;
            }
            worldRenderer.pos(n18, n9, n19).tex(0.5, n36 + 0.5).color(n13, n14, n15, 7 + 137 - 133 + 244).endVertex();
            worldRenderer.pos(n20, n9, n21).tex(1.0, n36 + 0.5).color(n13, n14, n15, 212 + 121 - 322 + 244).endVertex();
            worldRenderer.pos(n24, n9, n25).tex(1.0, n36).color(n13, n14, n15, 205 + 229 - 230 + 51).endVertex();
            worldRenderer.pos(n22, n9, n23).tex(0.5, n36).color(n13, n14, n15, 53 + 221 - 65 + 46).endVertex();
            instance.draw();
            GlStateManager.popMatrix();
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private Vec3 func_177110_a(final EntityLivingBase entityLivingBase, final double n, final float n2) {
        return new Vec3(entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * n2, n + entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * n2, entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * n2);
    }
    
    @Override
    protected void preRenderCallback(final EntityGuardian entityGuardian, final float n) {
        if (entityGuardian.isElder()) {
            GlStateManager.scale(2.35f, 2.35f, 2.35f);
        }
    }
    
    public RenderGuardian(final RenderManager renderManager) {
        super(renderManager, new ModelGuardian(), 0.5f);
        this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
    }
}
