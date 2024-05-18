package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;

public class ItemRenderer
{
    private final RenderItem itemRenderer;
    private static final String[] I;
    private int equippedItemSlot;
    private final Minecraft mc;
    private float prevEquippedProgress;
    private static final ResourceLocation RES_MAP_BACKGROUND;
    private ItemStack itemToRender;
    private static final ResourceLocation RES_UNDERWATER_OVERLAY;
    private final RenderManager renderManager;
    private static int[] $SWITCH_TABLE$net$minecraft$item$EnumAction;
    private float equippedProgress;
    
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void renderFireInFirstPerson(final float n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(286 + 270 - 258 + 221);
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(330 + 30 - 331 + 741, 674 + 166 - 80 + 11, " ".length(), "".length());
        final float n2 = 1.0f;
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < "  ".length()) {
            GlStateManager.pushMatrix();
            final TextureAtlasSprite atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite(ItemRenderer.I["  ".length()]);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            final float minU = atlasSprite.getMinU();
            final float maxU = atlasSprite.getMaxU();
            final float minV = atlasSprite.getMinV();
            final float maxV = atlasSprite.getMaxV();
            final float n3 = (0.0f - n2) / 2.0f;
            final float n4 = n3 + n2;
            final float n5 = 0.0f - n2 / 2.0f;
            final float n6 = n5 + n2;
            final float n7 = -0.5f;
            GlStateManager.translate(-(i * "  ".length() - " ".length()) * 0.24f, -0.3f, 0.0f);
            GlStateManager.rotate((i * "  ".length() - " ".length()) * 10.0f, 0.0f, 1.0f, 0.0f);
            worldRenderer.begin(0x16 ^ 0x11, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n3, n5, n7).tex(maxU, maxV).endVertex();
            worldRenderer.pos(n4, n5, n7).tex(minU, maxV).endVertex();
            worldRenderer.pos(n4, n6, n7).tex(minU, minV).endVertex();
            worldRenderer.pos(n3, n6, n7).tex(maxU, minV).endVertex();
            instance.draw();
            GlStateManager.popMatrix();
            ++i;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.depthFunc(211 + 362 - 246 + 188);
    }
    
    private void func_178105_d(final float n) {
        GlStateManager.translate(-0.4f * MathHelper.sin(MathHelper.sqrt_float(n) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt_float(n) * 3.1415927f * 2.0f), -0.2f * MathHelper.sin(n * 3.1415927f));
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("63\u001b\r&03\u0010V>#&L\u001422\t\u0001\u00180)1\u0011\u0016&,2M\t=%", "BVcyS");
        ItemRenderer.I[" ".length()] = I("\u000e141\u0004\b1?j\u001c\u0013'/j\u0004\u00140)7\u0006\u001b )7_\n:+", "zTLEq");
        ItemRenderer.I["  ".length()] = I("':\u0004 \u000482\f1](?\u0005&\f9|\f,\u0015/\f\u0006$\u001e/!5t", "JSjEg");
    }
    
    private void renderLeftArm(final RenderPlayer renderPlayer) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-0.3f, -1.1f, 0.45f);
        renderPlayer.renderLeftArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }
    
    private void renderRightArm(final RenderPlayer renderPlayer) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(64.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-62.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.25f, -0.85f, 0.75f);
        renderPlayer.renderRightArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }
    
    private void func_178098_a(final float n, final AbstractClientPlayer abstractClientPlayer) {
        GlStateManager.rotate(-18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-12.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.9f, 0.2f, 0.0f);
        final float n2 = this.itemToRender.getMaxItemUseDuration() - (abstractClientPlayer.getItemInUseCount() - n + 1.0f);
        final float n3 = n2 / 20.0f;
        float n4 = (n3 * n3 + n3 * 2.0f) / 3.0f;
        if (n4 > 1.0f) {
            n4 = 1.0f;
        }
        if (n4 > 0.1f) {
            final float n5 = MathHelper.sin((n2 - 0.1f) * 1.3f) * (n4 - 0.1f);
            GlStateManager.translate(n5 * 0.0f, n5 * 0.01f, n5 * 0.0f);
        }
        GlStateManager.translate(n4 * 0.0f, n4 * 0.0f, n4 * 0.1f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f + n4 * 0.2f);
    }
    
    private void func_178109_a(final AbstractClientPlayer abstractClientPlayer) {
        final int combinedLight = this.mc.theWorld.getCombinedLight(new BlockPos(abstractClientPlayer.posX, abstractClientPlayer.posY + abstractClientPlayer.getEyeHeight(), abstractClientPlayer.posZ), "".length());
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, combinedLight & 19586 + 58446 - 25542 + 13045, combinedLight >> (0xB5 ^ 0xA5));
    }
    
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }
    
    private void func_178104_a(final AbstractClientPlayer abstractClientPlayer, final float n) {
        final float n2 = abstractClientPlayer.getItemInUseCount() - n + 1.0f;
        final float n3 = n2 / this.itemToRender.getMaxItemUseDuration();
        float abs = MathHelper.abs(MathHelper.cos(n2 / 4.0f * 3.1415927f) * 0.1f);
        if (n3 >= 0.8f) {
            abs = 0.0f;
        }
        GlStateManager.translate(0.0f, abs, 0.0f);
        final float n4 = 1.0f - (float)Math.pow(n3, 27.0);
        GlStateManager.translate(n4 * 0.6f, n4 * -0.5f, n4 * 0.0f);
        GlStateManager.rotate(n4 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(n4 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n4 * 30.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$item$EnumAction() {
        final int[] $switch_TABLE$net$minecraft$item$EnumAction = ItemRenderer.$SWITCH_TABLE$net$minecraft$item$EnumAction;
        if ($switch_TABLE$net$minecraft$item$EnumAction != null) {
            return $switch_TABLE$net$minecraft$item$EnumAction;
        }
        final int[] $switch_TABLE$net$minecraft$item$EnumAction2 = new int[EnumAction.values().length];
        try {
            $switch_TABLE$net$minecraft$item$EnumAction2[EnumAction.BLOCK.ordinal()] = (0x2A ^ 0x2E);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$item$EnumAction2[EnumAction.BOW.ordinal()] = (0x8E ^ 0x8B);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$item$EnumAction2[EnumAction.DRINK.ordinal()] = "   ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$item$EnumAction2[EnumAction.EAT.ordinal()] = "  ".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$item$EnumAction2[EnumAction.NONE.ordinal()] = " ".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        return ItemRenderer.$SWITCH_TABLE$net$minecraft$item$EnumAction = $switch_TABLE$net$minecraft$item$EnumAction2;
    }
    
    private void renderPlayerArms(final AbstractClientPlayer abstractClientPlayer) {
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        final RenderPlayer renderPlayer = (RenderPlayer)this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        if (!abstractClientPlayer.isInvisible()) {
            GlStateManager.disableCull();
            this.renderRightArm(renderPlayer);
            this.renderLeftArm(renderPlayer);
            GlStateManager.enableCull();
        }
    }
    
    public void renderItem(final EntityLivingBase entityLivingBase, final ItemStack itemStack, final ItemCameraTransforms.TransformType transformType) {
        if (itemStack != null) {
            final Block blockFromItem = Block.getBlockFromItem(itemStack.getItem());
            GlStateManager.pushMatrix();
            if (this.itemRenderer.shouldRenderItemIn3D(itemStack)) {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                if (this.isBlockTranslucent(blockFromItem)) {
                    GlStateManager.depthMask("".length() != 0);
                }
            }
            this.itemRenderer.renderItemModelForEntity(itemStack, entityLivingBase, transformType);
            if (this.isBlockTranslucent(blockFromItem)) {
                GlStateManager.depthMask(" ".length() != 0);
            }
            GlStateManager.popMatrix();
        }
    }
    
    private void func_178108_a(final float n, final TextureAtlasSprite textureAtlasSprite) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.color(0.1f, 0.1f, 0.1f, 0.5f);
        GlStateManager.pushMatrix();
        final float minU = textureAtlasSprite.getMinU();
        final float maxU = textureAtlasSprite.getMaxU();
        final float minV = textureAtlasSprite.getMinV();
        final float maxV = textureAtlasSprite.getMaxV();
        worldRenderer.begin(0xC6 ^ 0xC1, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-1.0, -1.0, -0.5).tex(maxU, maxV).endVertex();
        worldRenderer.pos(1.0, -1.0, -0.5).tex(minU, maxV).endVertex();
        worldRenderer.pos(1.0, 1.0, -0.5).tex(minU, minV).endVertex();
        worldRenderer.pos(-1.0, 1.0, -0.5).tex(maxU, minV).endVertex();
        instance.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void transformFirstPersonItem(final float n, final float n2) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }
    
    public void renderItemInFirstPerson(final float n) {
        final float n2 = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * n);
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final float swingProgress = thePlayer.getSwingProgress(n);
        final float n3 = thePlayer.prevRotationPitch + (thePlayer.rotationPitch - thePlayer.prevRotationPitch) * n;
        this.func_178101_a(n3, thePlayer.prevRotationYaw + (thePlayer.rotationYaw - thePlayer.prevRotationYaw) * n);
        this.func_178109_a(thePlayer);
        this.func_178110_a(thePlayer, n);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        if (this.itemToRender != null) {
            Label_0305: {
                if (this.itemToRender.getItem() == Items.filled_map) {
                    this.renderItemMap(thePlayer, n3, n2, swingProgress);
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (thePlayer.getItemInUseCount() > 0) {
                    switch ($SWITCH_TABLE$net$minecraft$item$EnumAction()[this.itemToRender.getItemUseAction().ordinal()]) {
                        case 1: {
                            this.transformFirstPersonItem(n2, 0.0f);
                            "".length();
                            if (1 <= 0) {
                                throw null;
                            }
                            break Label_0305;
                        }
                        case 2:
                        case 3: {
                            this.func_178104_a(thePlayer, n);
                            this.transformFirstPersonItem(n2, 0.0f);
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                            break Label_0305;
                        }
                        case 4: {
                            this.transformFirstPersonItem(n2, 0.0f);
                            this.func_178103_d();
                            "".length();
                            if (0 >= 1) {
                                throw null;
                            }
                            break Label_0305;
                        }
                        case 5: {
                            this.transformFirstPersonItem(n2, 0.0f);
                            this.func_178098_a(n, thePlayer);
                            break;
                        }
                    }
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else {
                    this.func_178105_d(swingProgress);
                    this.transformFirstPersonItem(n2, swingProgress);
                }
            }
            this.renderItem(thePlayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (!thePlayer.isInvisible()) {
            this.func_178095_a(thePlayer, n2, swingProgress);
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
    
    private void renderItemMap(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3) {
        GlStateManager.translate(-0.4f * MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f), 0.2f * MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f * 2.0f), -0.2f * MathHelper.sin(n3 * 3.1415927f));
        final float func_178100_c = this.func_178100_c(n);
        GlStateManager.translate(0.0f, 0.04f, -0.72f);
        GlStateManager.translate(0.0f, n2 * -1.2f, 0.0f);
        GlStateManager.translate(0.0f, func_178100_c * -0.5f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(func_178100_c * -85.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        this.renderPlayerArms(abstractClientPlayer);
        final float sin = MathHelper.sin(n3 * n3 * 3.1415927f);
        final float sin2 = MathHelper.sin(MathHelper.sqrt_float(n3) * 3.1415927f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(sin2 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-1.0f, -1.0f, 0.0f);
        GlStateManager.scale(0.015625f, 0.015625f, 0.015625f);
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_MAP_BACKGROUND);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        worldRenderer.begin(0xA7 ^ 0xA0, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
        worldRenderer.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
        instance.draw();
        final MapData mapData = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);
        if (mapData != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, "".length() != 0);
        }
    }
    
    static {
        I();
        RES_MAP_BACKGROUND = new ResourceLocation(ItemRenderer.I["".length()]);
        RES_UNDERWATER_OVERLAY = new ResourceLocation(ItemRenderer.I[" ".length()]);
    }
    
    public void renderOverlays(final float n) {
        GlStateManager.disableAlpha();
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            IBlockState blockState = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            int i = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (i < (0x4E ^ 0x46)) {
                final IBlockState blockState2 = this.mc.theWorld.getBlockState(new BlockPos(thePlayer.posX + ((i >> "".length()) % "  ".length() - 0.5f) * thePlayer.width * 0.8f, thePlayer.posY + ((i >> " ".length()) % "  ".length() - 0.5f) * 0.1f + thePlayer.getEyeHeight(), thePlayer.posZ + ((i >> "  ".length()) % "  ".length() - 0.5f) * thePlayer.width * 0.8f));
                if (blockState2.getBlock().isVisuallyOpaque()) {
                    blockState = blockState2;
                }
                ++i;
            }
            if (blockState.getBlock().getRenderType() != -" ".length()) {
                this.func_178108_a(n, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(blockState));
            }
        }
        if (!this.mc.thePlayer.isSpectator()) {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
                this.renderWaterOverlayTexture(n);
            }
            if (this.mc.thePlayer.isBurning()) {
                this.renderFireInFirstPerson(n);
            }
        }
        GlStateManager.enableAlpha();
    }
    
    private float func_178100_c(final float n) {
        return -MathHelper.cos(MathHelper.clamp_float(1.0f - n / 45.0f + 0.1f, 0.0f, 1.0f) * 3.1415927f) * 0.5f + 0.5f;
    }
    
    public ItemRenderer(final Minecraft mc) {
        this.equippedItemSlot = -" ".length();
        this.mc = mc;
        this.renderManager = mc.getRenderManager();
        this.itemRenderer = mc.getRenderItem();
    }
    
    private void func_178095_a(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        GlStateManager.translate(-0.3f * MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f), 0.4f * MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f * 2.0f), -0.4f * MathHelper.sin(n2 * 3.1415927f));
        GlStateManager.translate(0.64000005f, -0.6f, -0.71999997f);
        GlStateManager.translate(0.0f, n * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float sin = MathHelper.sin(n2 * n2 * 3.1415927f);
        GlStateManager.rotate(MathHelper.sin(MathHelper.sqrt_float(n2) * 3.1415927f) * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(sin * -20.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0f, 3.6f, 3.5f);
        GlStateManager.rotate(120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(5.6f, 0.0f, 0.0f);
        final Render<Entity> entityRenderObject = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        GlStateManager.disableCull();
        ((RenderPlayer)entityRenderObject).renderRightArm(this.mc.thePlayer);
        GlStateManager.enableCull();
    }
    
    private void func_178110_a(final EntityPlayerSP entityPlayerSP, final float n) {
        final float n2 = entityPlayerSP.prevRenderArmPitch + (entityPlayerSP.renderArmPitch - entityPlayerSP.prevRenderArmPitch) * n;
        final float n3 = entityPlayerSP.prevRenderArmYaw + (entityPlayerSP.renderArmYaw - entityPlayerSP.prevRenderArmYaw) * n;
        GlStateManager.rotate((entityPlayerSP.rotationPitch - n2) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((entityPlayerSP.rotationYaw - n3) * 0.1f, 0.0f, 1.0f, 0.0f);
    }
    
    private void func_178101_a(final float n, final float n2) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(n, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    private boolean isBlockTranslucent(final Block block) {
        if (block != null && block.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void renderWaterOverlayTexture(final float n) {
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_UNDERWATER_OVERLAY);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final float brightness = this.mc.thePlayer.getBrightness(n);
        GlStateManager.color(brightness, brightness, brightness, 0.5f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(90 + 483 - 153 + 350, 382 + 216 + 33 + 140, " ".length(), "".length());
        GlStateManager.pushMatrix();
        final float n2 = -this.mc.thePlayer.rotationYaw / 64.0f;
        final float n3 = this.mc.thePlayer.rotationPitch / 64.0f;
        worldRenderer.begin(0x76 ^ 0x71, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-1.0, -1.0, -0.5).tex(4.0f + n2, 4.0f + n3).endVertex();
        worldRenderer.pos(1.0, -1.0, -0.5).tex(0.0f + n2, 4.0f + n3).endVertex();
        worldRenderer.pos(1.0, 1.0, -0.5).tex(0.0f + n2, 0.0f + n3).endVertex();
        worldRenderer.pos(-1.0, 1.0, -0.5).tex(4.0f + n2, 0.0f + n3).endVertex();
        instance.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
    }
    
    private void func_178103_d() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final ItemStack currentItem = thePlayer.inventory.getCurrentItem();
        int n = "".length();
        if (this.itemToRender != null && currentItem != null) {
            if (!this.itemToRender.getIsItemStackEqual(currentItem)) {
                n = " ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
        }
        else if (this.itemToRender == null && currentItem == null) {
            n = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final float n2 = 0.4f;
        float n3;
        if (n != 0) {
            n3 = 0.0f;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n3 = 1.0f;
        }
        this.equippedProgress += MathHelper.clamp_float(n3 - this.equippedProgress, -n2, n2);
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = currentItem;
            this.equippedItemSlot = thePlayer.inventory.currentItem;
        }
    }
}
