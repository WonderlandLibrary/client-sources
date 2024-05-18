package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;

public abstract class Render<T extends Entity>
{
    private static final String[] I;
    protected float shadowSize;
    protected float shadowOpaque;
    private static final ResourceLocation shadowTextures;
    protected final RenderManager renderManager;
    
    private void renderShadow(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(186 + 742 - 444 + 286, 585 + 290 - 463 + 359);
        this.renderManager.renderEngine.bindTexture(Render.shadowTextures);
        final World worldFromRenderManager = this.getWorldFromRenderManager();
        GlStateManager.depthMask("".length() != 0);
        float shadowSize = this.shadowSize;
        if (entity instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)entity;
            shadowSize *= entityLiving.getRenderSizeModifier();
            if (entityLiving.isChild()) {
                shadowSize *= 0.5f;
            }
        }
        final double n6 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n5;
        final double n7 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n5;
        final double n8 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n5;
        final int floor_double = MathHelper.floor_double(n6 - shadowSize);
        final int floor_double2 = MathHelper.floor_double(n6 + shadowSize);
        final int floor_double3 = MathHelper.floor_double(n7 - shadowSize);
        final int floor_double4 = MathHelper.floor_double(n7);
        final int floor_double5 = MathHelper.floor_double(n8 - shadowSize);
        final int floor_double6 = MathHelper.floor_double(n8 + shadowSize);
        final double n9 = n - n6;
        final double n10 = n2 - n7;
        final double n11 = n3 - n8;
        final Tessellator instance = Tessellator.getInstance();
        instance.getWorldRenderer().begin(0x95 ^ 0x92, DefaultVertexFormats.POSITION_TEX_COLOR);
        final Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.getAllInBoxMutable(new BlockPos(floor_double, floor_double3, floor_double5), new BlockPos(floor_double2, floor_double4, floor_double6)).iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos.MutableBlockPos mutableBlockPos = iterator.next();
            final Block block = worldFromRenderManager.getBlockState(mutableBlockPos.down()).getBlock();
            if (block.getRenderType() != -" ".length() && worldFromRenderManager.getLightFromNeighbors(mutableBlockPos) > "   ".length()) {
                this.func_180549_a(block, n, n2, n3, mutableBlockPos, n4, shadowSize, n9, n10, n11);
            }
        }
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(" ".length() != 0);
    }
    
    public boolean shouldRender(final T t, final ICamera camera, final double n, final double n2, final double n3) {
        AxisAlignedBB entityBoundingBox = t.getEntityBoundingBox();
        if (entityBoundingBox.func_181656_b() || entityBoundingBox.getAverageEdgeLength() == 0.0) {
            entityBoundingBox = new AxisAlignedBB(t.posX - 2.0, t.posY - 2.0, t.posZ - 2.0, t.posX + 2.0, t.posY + 2.0, t.posZ + 2.0);
        }
        if (t.isInRangeToRender3d(n, n2, n3) && (t.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entityBoundingBox))) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected Render(final RenderManager renderManager) {
        this.shadowOpaque = 1.0f;
        this.renderManager = renderManager;
    }
    
    static {
        I();
        shadowTextures = new ResourceLocation(Render.I["".length()]);
    }
    
    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void doRender(final T t, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.renderName(t, n, n2, n3);
    }
    
    public void doRenderShadowAndFire(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (this.renderManager.options != null) {
            if (this.renderManager.options.field_181151_V && this.shadowSize > 0.0f && !entity.isInvisible() && this.renderManager.isRenderShadow()) {
                final float n6 = (float)((1.0 - this.renderManager.getDistanceToCamera(entity.posX, entity.posY, entity.posZ) / 256.0) * this.shadowOpaque);
                if (n6 > 0.0f) {
                    this.renderShadow(entity, n, n2, n3, n6, n5);
                }
            }
            if (entity.canRenderOnFire() && (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())) {
                this.renderEntityOnFire(entity, n, n2, n3, n5);
            }
        }
    }
    
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
    
    protected void renderName(final T t, final double n, final double n2, final double n3) {
        if (this.canRenderName(t)) {
            this.renderLivingLabel(t, t.getDisplayName().getFormattedText(), n, n2, n3, 0x2F ^ 0x6F);
        }
    }
    
    public void bindTexture(final ResourceLocation resourceLocation) {
        this.renderManager.renderEngine.bindTexture(resourceLocation);
    }
    
    protected boolean canRenderName(final T t) {
        if (t.getAlwaysRenderNameTagForRender() && t.hasCustomName()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void renderOffsetAABB(final AxisAlignedBB axisAlignedBB, final double n, final double n2, final double n3) {
        GlStateManager.disableTexture2D();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.setTranslation(n, n2, n3);
        worldRenderer.begin(0x86 ^ 0x81, DefaultVertexFormats.POSITION_NORMAL);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, 0.0f, -1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 0.0f, 1.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0f, -1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0f, 1.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(-1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(1.0f, 0.0f, 0.0f).endVertex();
        instance.draw();
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.enableTexture2D();
    }
    
    private void func_180549_a(final Block block, final double n, final double n2, final double n3, final BlockPos blockPos, final float n4, final float n5, final double n6, final double n7, final double n8) {
        if (block.isFullCube()) {
            final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
            double n9 = (n4 - (n2 - (blockPos.getY() + n7)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(blockPos);
            if (n9 >= 0.0) {
                if (n9 > 1.0) {
                    n9 = 1.0;
                }
                final double n10 = blockPos.getX() + block.getBlockBoundsMinX() + n6;
                final double n11 = blockPos.getX() + block.getBlockBoundsMaxX() + n6;
                final double n12 = blockPos.getY() + block.getBlockBoundsMinY() + n7 + 0.015625;
                final double n13 = blockPos.getZ() + block.getBlockBoundsMinZ() + n8;
                final double n14 = blockPos.getZ() + block.getBlockBoundsMaxZ() + n8;
                final float n15 = (float)((n - n10) / 2.0 / n5 + 0.5);
                final float n16 = (float)((n - n11) / 2.0 / n5 + 0.5);
                final float n17 = (float)((n3 - n13) / 2.0 / n5 + 0.5);
                final float n18 = (float)((n3 - n14) / 2.0 / n5 + 0.5);
                worldRenderer.pos(n10, n12, n13).tex(n15, n17).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
                worldRenderer.pos(n10, n12, n14).tex(n15, n18).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
                worldRenderer.pos(n11, n12, n14).tex(n16, n18).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
                worldRenderer.pos(n11, n12, n13).tex(n16, n17).color(1.0f, 1.0f, 1.0f, (float)n9).endVertex();
            }
        }
    }
    
    protected boolean bindEntityTexture(final T t) {
        final ResourceLocation entityTexture = this.getEntityTexture(t);
        if (entityTexture == null) {
            return "".length() != 0;
        }
        this.bindTexture(entityTexture);
        return " ".length() != 0;
    }
    
    protected void renderLivingLabel(final T t, final String s, final double n, final double n2, final double n3, final int n4) {
        if (t.getDistanceSqToEntity(this.renderManager.livingPlayer) <= n4 * n4) {
            final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
            final float n5 = 0.016666668f * 1.6f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)n + 0.0f, (float)n2 + t.height + 0.5f, (float)n3);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-n5, -n5, n5);
            GlStateManager.disableLighting();
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(717 + 570 - 861 + 344, 272 + 250 - 281 + 530, " ".length(), "".length());
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            int length = "".length();
            if (s.equals(Render.I["   ".length()])) {
                length = -(0x2C ^ 0x26);
            }
            final int n6 = fontRendererFromRenderManager.getStringWidth(s) / "  ".length();
            GlStateManager.disableTexture2D();
            worldRenderer.begin(0x30 ^ 0x37, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(-n6 - " ".length(), -" ".length() + length, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(-n6 - " ".length(), (0x84 ^ 0x8C) + length, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(n6 + " ".length(), (0x28 ^ 0x20) + length, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldRenderer.pos(n6 + " ".length(), -" ".length() + length, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            instance.draw();
            GlStateManager.enableTexture2D();
            fontRendererFromRenderManager.drawString(s, -fontRendererFromRenderManager.getStringWidth(s) / "  ".length(), length, 71813781 + 442262224 - 351394660 + 390966782);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(" ".length() != 0);
            fontRendererFromRenderManager.drawString(s, -fontRendererFromRenderManager.getStringWidth(s) / "  ".length(), length, -" ".length());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
    
    protected abstract ResourceLocation getEntityTexture(final T p0);
    
    private static void I() {
        (I = new String[0x36 ^ 0x32])["".length()] = I("#\u0000\u0012\u001b1%\u0000\u0019@)>\u0016\t@7?\u0004\u000e\u00003y\u0015\u0004\b", "WejoD");
        Render.I[" ".length()] = I("\f\u0013\u000b\u0007+\u0013\u001b\u0003\u0016r\u0003\u0016\n\u0001#\u0012U\u0003\u000b:\u0004%\t\u00031\u0004\b:R", "azebH");
        Render.I["  ".length()] = I("9\u000b#$\u000f&\u0003+5V6\u000e\"\"\u0007'M+(\u001e1=! \u00151\u0010\u0012p", "TbMAl");
        Render.I["   ".length()] = I("( \u0019\u000b\u0004-0M", "LExoi");
    }
    
    public RenderManager getRenderManager() {
        return this.renderManager;
    }
    
    private void renderEntityOnFire(final Entity entity, final double n, final double n2, final double n3, final float n4) {
        GlStateManager.disableLighting();
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        final TextureAtlasSprite atlasSprite = textureMapBlocks.getAtlasSprite(Render.I[" ".length()]);
        final TextureAtlasSprite atlasSprite2 = textureMapBlocks.getAtlasSprite(Render.I["  ".length()]);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n5 = entity.width * 1.4f;
        GlStateManager.scale(n5, n5, n5);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        float n6 = 0.5f;
        final float n7 = 0.0f;
        float n8 = entity.height / n5;
        float n9 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, -0.3f + (int)n8 * 0.02f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        float n10 = 0.0f;
        int length = "".length();
        worldRenderer.begin(0x56 ^ 0x51, DefaultVertexFormats.POSITION_TEX);
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (n8 > 0.0f) {
            TextureAtlasSprite textureAtlasSprite;
            if (length % "  ".length() == 0) {
                textureAtlasSprite = atlasSprite;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                textureAtlasSprite = atlasSprite2;
            }
            final TextureAtlasSprite textureAtlasSprite2 = textureAtlasSprite;
            this.bindTexture(TextureMap.locationBlocksTexture);
            float minU = textureAtlasSprite2.getMinU();
            final float minV = textureAtlasSprite2.getMinV();
            float maxU = textureAtlasSprite2.getMaxU();
            final float maxV = textureAtlasSprite2.getMaxV();
            if (length / "  ".length() % "  ".length() == 0) {
                final float n11 = maxU;
                maxU = minU;
                minU = n11;
            }
            worldRenderer.pos(n6 - n7, 0.0f - n9, n10).tex(maxU, maxV).endVertex();
            worldRenderer.pos(-n6 - n7, 0.0f - n9, n10).tex(minU, maxV).endVertex();
            worldRenderer.pos(-n6 - n7, 1.4f - n9, n10).tex(minU, minV).endVertex();
            worldRenderer.pos(n6 - n7, 1.4f - n9, n10).tex(maxU, minV).endVertex();
            n8 -= 0.45f;
            n9 -= 0.45f;
            n6 *= 0.9f;
            n10 += 0.03f;
            ++length;
        }
        instance.draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }
    
    protected void renderOffsetLivingLabel(final T t, final double n, final double n2, final double n3, final String s, final float n4, final double n5) {
        this.renderLivingLabel(t, s, n, n2, n3, 0xCC ^ 0x8C);
    }
}
