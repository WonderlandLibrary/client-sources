package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.entity.*;

public class RenderItemFrame extends Render<EntityItemFrame>
{
    private final Minecraft mc;
    private RenderItem itemRenderer;
    private final ModelResourceLocation itemFrameModel;
    private final ModelResourceLocation mapModel;
    private static final String[] I;
    private static final ResourceLocation mapBackgroundTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityItemFrame entityItemFrame) {
        return null;
    }
    
    private static void I() {
        (I = new String[0xB2 ^ 0xB7])["".length()] = I("\r/\u000f \u001c\u000b/\u0004{\u0004\u0018:X9\b\t\u0015\u00155\n\u0012-\u0005;\u001c\u0017.Y$\u0007\u001e", "yJwTi");
        RenderItemFrame.I[" ".length()] = I(";\u00076&\u001a4\u00012& ", "RsSKE");
        RenderItemFrame.I["  ".length()] = I("#$\u0011(\u0016!", "MKcEw");
        RenderItemFrame.I["   ".length()] = I("\u0007\u001a\u0003/\u000f\b\u001c\u0007/5", "nnfBP");
        RenderItemFrame.I[0x34 ^ 0x30] = I("8\u0003\u0012", "Ubben");
    }
    
    @Override
    protected void renderName(final Entity entity, final double n, final double n2, final double n3) {
        this.renderName((EntityItemFrame)entity, n, n2, n3);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityItemFrame)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityItemFrame entityItemFrame, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        final BlockPos hangingPosition = entityItemFrame.getHangingPosition();
        GlStateManager.translate(hangingPosition.getX() - entityItemFrame.posX + n + 0.5, hangingPosition.getY() - entityItemFrame.posY + n2 + 0.5, hangingPosition.getZ() - entityItemFrame.posZ + n3 + 0.5);
        GlStateManager.rotate(180.0f - entityItemFrame.rotationYaw, 0.0f, 1.0f, 0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        final BlockRendererDispatcher blockRendererDispatcher = this.mc.getBlockRendererDispatcher();
        final ModelManager modelManager = blockRendererDispatcher.getBlockModelShapes().getModelManager();
        IBakedModel bakedModel;
        if (entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().getItem() == Items.filled_map) {
            bakedModel = modelManager.getModel(this.mapModel);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            bakedModel = modelManager.getModel(this.itemFrameModel);
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5f, -0.5f, -0.5f);
        blockRendererDispatcher.getBlockModelRenderer().renderModelBrightnessColor(bakedModel, 1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
        GlStateManager.translate(0.0f, 0.0f, 0.4375f);
        this.renderItem(entityItemFrame);
        GlStateManager.popMatrix();
        this.renderName(entityItemFrame, n + entityItemFrame.facingDirection.getFrontOffsetX() * 0.3f, n2 - 0.25, n3 + entityItemFrame.facingDirection.getFrontOffsetZ() * 0.3f);
    }
    
    @Override
    protected void renderName(final EntityItemFrame entityItemFrame, final double n, final double n2, final double n3) {
        if (Minecraft.isGuiEnabled() && entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entityItemFrame) {
            final float n4 = 0.016666668f * 1.6f;
            final double distanceSqToEntity = entityItemFrame.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float n5;
            if (entityItemFrame.isSneaking()) {
                n5 = 32.0f;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n5 = 64.0f;
            }
            final float n6 = n5;
            if (distanceSqToEntity < n6 * n6) {
                final String displayName = entityItemFrame.getDisplayedItem().getDisplayName();
                if (entityItemFrame.isSneaking()) {
                    final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)n + 0.0f, (float)n2 + entityItemFrame.height + 0.5f, (float)n3);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-n4, -n4, n4);
                    GlStateManager.disableLighting();
                    GlStateManager.translate(0.0f, 0.25f / n4, 0.0f);
                    GlStateManager.depthMask("".length() != 0);
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(312 + 314 - 525 + 669, 138 + 122 - 99 + 610);
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    final int n7 = fontRendererFromRenderManager.getStringWidth(displayName) / "  ".length();
                    GlStateManager.disableTexture2D();
                    worldRenderer.begin(0x4 ^ 0x3, DefaultVertexFormats.POSITION_COLOR);
                    worldRenderer.pos(-n7 - " ".length(), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(-n7 - " ".length(), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n7 + " ".length(), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n7 + " ".length(), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    instance.draw();
                    GlStateManager.enableTexture2D();
                    GlStateManager.depthMask(" ".length() != 0);
                    fontRendererFromRenderManager.drawString(displayName, -fontRendererFromRenderManager.getStringWidth(displayName) / "  ".length(), "".length(), 72103352 + 223349052 - 149077087 + 407272810);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
                else {
                    this.renderLivingLabel(entityItemFrame, displayName, n, n2, n3, 0xCA ^ 0x8A);
                }
            }
        }
    }
    
    private void renderItem(final EntityItemFrame entityItemFrame) {
        final ItemStack displayedItem = entityItemFrame.getDisplayedItem();
        if (displayedItem != null) {
            final EntityItem entityItem = new EntityItem(entityItemFrame.worldObj, 0.0, 0.0, 0.0, displayedItem);
            final Item item = entityItem.getEntityItem().getItem();
            entityItem.getEntityItem().stackSize = " ".length();
            entityItem.hoverStart = 0.0f;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            int rotation = entityItemFrame.getRotation();
            if (item == Items.filled_map) {
                rotation = rotation % (0x8C ^ 0x88) * "  ".length();
            }
            GlStateManager.rotate(rotation * 360.0f / 8.0f, 0.0f, 0.0f, 1.0f);
            if (item == Items.filled_map) {
                this.renderManager.renderEngine.bindTexture(RenderItemFrame.mapBackgroundTextures);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                final float n = 0.0078125f;
                GlStateManager.scale(n, n, n);
                GlStateManager.translate(-64.0f, -64.0f, 0.0f);
                final MapData mapData = Items.filled_map.getMapData(entityItem.getEntityItem(), entityItemFrame.worldObj);
                GlStateManager.translate(0.0f, 0.0f, -1.0f);
                if (mapData != null) {
                    this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, " ".length() != 0);
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
            }
            else {
                TextureAtlasSprite atlasSprite = null;
                if (item == Items.compass) {
                    atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);
                    this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                    if (atlasSprite instanceof TextureCompass) {
                        final TextureCompass textureCompass = (TextureCompass)atlasSprite;
                        final double currentAngle = textureCompass.currentAngle;
                        final double angleDelta = textureCompass.angleDelta;
                        textureCompass.currentAngle = 0.0;
                        textureCompass.angleDelta = 0.0;
                        textureCompass.updateCompass(entityItemFrame.worldObj, entityItemFrame.posX, entityItemFrame.posZ, MathHelper.wrapAngleTo180_float(59 + 58 + 43 + 20 + entityItemFrame.facingDirection.getHorizontalIndex() * (0x19 ^ 0x43)), "".length() != 0, " ".length() != 0);
                        textureCompass.currentAngle = currentAngle;
                        textureCompass.angleDelta = angleDelta;
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        atlasSprite = null;
                    }
                }
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                if (!this.itemRenderer.shouldRenderItemIn3D(entityItem.getEntityItem()) || item instanceof ItemSkull) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.pushAttrib();
                RenderHelper.enableStandardItemLighting();
                this.itemRenderer.func_181564_a(entityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                if (atlasSprite != null && atlasSprite.getFrameCount() > 0) {
                    atlasSprite.updateAnimation();
                }
            }
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
    
    static {
        I();
        mapBackgroundTextures = new ResourceLocation(RenderItemFrame.I["".length()]);
    }
    
    public RenderItemFrame(final RenderManager renderManager, final RenderItem itemRenderer) {
        super(renderManager);
        this.mc = Minecraft.getMinecraft();
        this.itemFrameModel = new ModelResourceLocation(RenderItemFrame.I[" ".length()], RenderItemFrame.I["  ".length()]);
        this.mapModel = new ModelResourceLocation(RenderItemFrame.I["   ".length()], RenderItemFrame.I[0x56 ^ 0x52]);
        this.itemRenderer = itemRenderer;
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
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityItemFrame)entity);
    }
}
