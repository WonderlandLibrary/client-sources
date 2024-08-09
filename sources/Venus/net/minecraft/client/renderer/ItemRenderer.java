/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomItems;
import net.optifine.EmissiveTextures;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.VertexBuilderWrapper;
import net.optifine.shaders.Shaders;

public class ItemRenderer
implements IResourceManagerReloadListener {
    public static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final Set<Item> ITEM_MODEL_BLACKLIST = Sets.newHashSet(Items.AIR);
    public float zLevel;
    private final ItemModelMesher itemModelMesher;
    private final TextureManager textureManager;
    private final ItemColors itemColors;
    public ModelManager modelManager = null;
    private static boolean renderItemGui = false;

    public ItemRenderer(TextureManager textureManager, ModelManager modelManager, ItemColors itemColors) {
        this.textureManager = textureManager;
        this.modelManager = modelManager;
        this.itemModelMesher = Reflector.ItemModelMesherForge_Constructor.exists() ? (ItemModelMesher)Reflector.newInstance(Reflector.ItemModelMesherForge_Constructor, this.modelManager) : new ItemModelMesher(modelManager);
        for (Item item : Registry.ITEM) {
            if (ITEM_MODEL_BLACKLIST.contains(item)) continue;
            this.itemModelMesher.register(item, new ModelResourceLocation(Registry.ITEM.getKey(item), "inventory"));
        }
        this.itemColors = itemColors;
    }

    public ItemModelMesher getItemModelMesher() {
        return this.itemModelMesher;
    }

    public void renderModel(IBakedModel iBakedModel, ItemStack itemStack, int n, int n2, MatrixStack matrixStack, IVertexBuilder iVertexBuilder) {
        if (Config.isMultiTexture()) {
            iVertexBuilder.setRenderBlocks(true);
        }
        Random random2 = new Random();
        long l = 42L;
        for (Direction direction : Direction.VALUES) {
            random2.setSeed(42L);
            this.renderQuads(matrixStack, iVertexBuilder, iBakedModel.getQuads(null, direction, random2), itemStack, n, n2);
        }
        random2.setSeed(42L);
        this.renderQuads(matrixStack, iVertexBuilder, iBakedModel.getQuads(null, null, random2), itemStack, n, n2);
    }

    public void renderItem(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, boolean bl, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2, IBakedModel iBakedModel) {
        if (!itemStack.isEmpty()) {
            boolean bl2;
            matrixStack.push();
            boolean bl3 = bl2 = transformType == ItemCameraTransforms.TransformType.GUI || transformType == ItemCameraTransforms.TransformType.GROUND || transformType == ItemCameraTransforms.TransformType.FIXED;
            if (itemStack.getItem() == Items.TRIDENT && bl2) {
                iBakedModel = this.itemModelMesher.getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
            }
            if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
                iBakedModel = (IBakedModel)Reflector.ForgeHooksClient_handleCameraTransforms.call(new Object[]{matrixStack, iBakedModel, transformType, bl});
            } else {
                iBakedModel.getItemCameraTransforms().getTransform(transformType).apply(bl, matrixStack);
            }
            matrixStack.translate(-0.5, -0.5, -0.5);
            if (!iBakedModel.isBuiltInRenderer() && (itemStack.getItem() != Items.TRIDENT || bl2)) {
                Object object;
                boolean bl4 = transformType != ItemCameraTransforms.TransformType.GUI && !transformType.isFirstPerson() && itemStack.getItem() instanceof BlockItem ? !((object = ((BlockItem)itemStack.getItem()).getBlock()) instanceof BreakableBlock) && !(object instanceof StainedGlassPaneBlock) : true;
                if (iBakedModel.isLayered()) {
                    Reflector.ForgeHooksClient_drawItemLayered.call(this, iBakedModel, itemStack, matrixStack, iRenderTypeBuffer, n, n2, bl4);
                } else {
                    IVertexBuilder iVertexBuilder;
                    Object object2;
                    object = RenderTypeLookup.func_239219_a_(itemStack, bl4);
                    if (itemStack.getItem() == Items.COMPASS && itemStack.hasEffect()) {
                        matrixStack.push();
                        object2 = matrixStack.getLast();
                        if (transformType == ItemCameraTransforms.TransformType.GUI) {
                            ((MatrixStack.Entry)object2).getMatrix().mul(0.5f);
                        } else if (transformType.isFirstPerson()) {
                            ((MatrixStack.Entry)object2).getMatrix().mul(0.75f);
                        }
                        iVertexBuilder = bl4 ? ItemRenderer.getDirectGlintVertexBuilder(iRenderTypeBuffer, (RenderType)object, (MatrixStack.Entry)object2) : ItemRenderer.getGlintVertexBuilder(iRenderTypeBuffer, (RenderType)object, (MatrixStack.Entry)object2);
                        matrixStack.pop();
                    } else {
                        iVertexBuilder = bl4 ? ItemRenderer.getEntityGlintVertexBuilder(iRenderTypeBuffer, (RenderType)object, true, itemStack.hasEffect()) : ItemRenderer.getBuffer(iRenderTypeBuffer, (RenderType)object, true, itemStack.hasEffect());
                    }
                    if (Config.isCustomItems()) {
                        iBakedModel = CustomItems.getCustomItemModel(itemStack, iBakedModel, ItemOverrideList.lastModelLocation, false);
                        ItemOverrideList.lastModelLocation = null;
                    }
                    if (EmissiveTextures.isActive()) {
                        EmissiveTextures.beginRender();
                    }
                    this.renderModel(iBakedModel, itemStack, n, n2, matrixStack, iVertexBuilder);
                    if (EmissiveTextures.isActive()) {
                        if (EmissiveTextures.hasEmissive()) {
                            EmissiveTextures.beginRenderEmissive();
                            object2 = iVertexBuilder instanceof VertexBuilderWrapper ? ((VertexBuilderWrapper)iVertexBuilder).getVertexBuilder() : iVertexBuilder;
                            this.renderModel(iBakedModel, itemStack, LightTexture.MAX_BRIGHTNESS, n2, matrixStack, (IVertexBuilder)object2);
                            EmissiveTextures.endRenderEmissive();
                        }
                        EmissiveTextures.endRender();
                    }
                }
            } else if (Reflector.IForgeItem_getItemStackTileEntityRenderer.exists()) {
                ItemStackTileEntityRenderer itemStackTileEntityRenderer = (ItemStackTileEntityRenderer)Reflector.call(itemStack.getItem(), Reflector.IForgeItem_getItemStackTileEntityRenderer, new Object[0]);
                itemStackTileEntityRenderer.func_239207_a_(itemStack, transformType, matrixStack, iRenderTypeBuffer, n, n2);
            } else {
                ItemStackTileEntityRenderer.instance.func_239207_a_(itemStack, transformType, matrixStack, iRenderTypeBuffer, n, n2);
            }
            matrixStack.pop();
        }
    }

    public static IVertexBuilder getArmorVertexBuilder(IRenderTypeBuffer iRenderTypeBuffer, RenderType renderType, boolean bl, boolean bl2) {
        if (Shaders.isShadowPass) {
            bl2 = false;
        }
        if (EmissiveTextures.isRenderEmissive()) {
            bl2 = false;
        }
        return bl2 ? VertexBuilderUtils.newDelegate(iRenderTypeBuffer.getBuffer(bl ? RenderType.getArmorGlint() : RenderType.getArmorEntityGlint()), iRenderTypeBuffer.getBuffer(renderType)) : iRenderTypeBuffer.getBuffer(renderType);
    }

    public static IVertexBuilder getGlintVertexBuilder(IRenderTypeBuffer iRenderTypeBuffer, RenderType renderType, MatrixStack.Entry entry) {
        return VertexBuilderUtils.newDelegate(new MatrixApplyingVertexBuilder(iRenderTypeBuffer.getBuffer(RenderType.getGlint()), entry.getMatrix(), entry.getNormal()), iRenderTypeBuffer.getBuffer(renderType));
    }

    public static IVertexBuilder getDirectGlintVertexBuilder(IRenderTypeBuffer iRenderTypeBuffer, RenderType renderType, MatrixStack.Entry entry) {
        return VertexBuilderUtils.newDelegate(new MatrixApplyingVertexBuilder(iRenderTypeBuffer.getBuffer(RenderType.getGlintDirect()), entry.getMatrix(), entry.getNormal()), iRenderTypeBuffer.getBuffer(renderType));
    }

    public static IVertexBuilder getBuffer(IRenderTypeBuffer iRenderTypeBuffer, RenderType renderType, boolean bl, boolean bl2) {
        if (Shaders.isShadowPass) {
            bl2 = false;
        }
        if (EmissiveTextures.isRenderEmissive()) {
            bl2 = false;
        }
        if (!bl2) {
            return iRenderTypeBuffer.getBuffer(renderType);
        }
        return Minecraft.isFabulousGraphicsEnabled() && renderType == Atlases.getItemEntityTranslucentCullType() ? VertexBuilderUtils.newDelegate(iRenderTypeBuffer.getBuffer(RenderType.getGlintTranslucent()), iRenderTypeBuffer.getBuffer(renderType)) : VertexBuilderUtils.newDelegate(iRenderTypeBuffer.getBuffer(bl ? RenderType.getGlint() : RenderType.getEntityGlint()), iRenderTypeBuffer.getBuffer(renderType));
    }

    public static IVertexBuilder getEntityGlintVertexBuilder(IRenderTypeBuffer iRenderTypeBuffer, RenderType renderType, boolean bl, boolean bl2) {
        if (Shaders.isShadowPass) {
            bl2 = false;
        }
        if (EmissiveTextures.isRenderEmissive()) {
            bl2 = false;
        }
        return bl2 ? VertexBuilderUtils.newDelegate(iRenderTypeBuffer.getBuffer(bl ? RenderType.getGlintDirect() : RenderType.getEntityGlintDirect()), iRenderTypeBuffer.getBuffer(renderType)) : iRenderTypeBuffer.getBuffer(renderType);
    }

    private void renderQuads(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, List<BakedQuad> list, ItemStack itemStack, int n, int n2) {
        boolean bl = !itemStack.isEmpty();
        MatrixStack.Entry entry = matrixStack.getLast();
        boolean bl2 = EmissiveTextures.isActive();
        int n3 = list.size();
        for (int i = 0; i < n3; ++i) {
            BakedQuad bakedQuad = list.get(i);
            if (bl2 && (bakedQuad = EmissiveTextures.getEmissiveQuad(bakedQuad)) == null) continue;
            int n4 = -1;
            if (bl && bakedQuad.hasTintIndex()) {
                n4 = this.itemColors.getColor(itemStack, bakedQuad.getTintIndex());
                if (Config.isCustomColors()) {
                    n4 = CustomColors.getColorFromItemStack(itemStack, bakedQuad.getTintIndex(), n4);
                }
            }
            float f = (float)(n4 >> 16 & 0xFF) / 255.0f;
            float f2 = (float)(n4 >> 8 & 0xFF) / 255.0f;
            float f3 = (float)(n4 & 0xFF) / 255.0f;
            if (Reflector.ForgeHooksClient.exists()) {
                iVertexBuilder.addVertexData(entry, bakedQuad, f, f2, f3, n, n2, true);
                continue;
            }
            iVertexBuilder.addQuad(entry, bakedQuad, f, f2, f3, n, n2);
        }
    }

    public IBakedModel getItemModelWithOverrides(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity) {
        Item item = itemStack.getItem();
        IBakedModel iBakedModel = item == Items.TRIDENT ? this.itemModelMesher.getModelManager().getModel(new ModelResourceLocation("minecraft:trident_in_hand#inventory")) : this.itemModelMesher.getItemModel(itemStack);
        ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld)world : null;
        ItemOverrideList.lastModelLocation = null;
        IBakedModel iBakedModel2 = iBakedModel.getOverrides().getOverrideModel(iBakedModel, itemStack, clientWorld, livingEntity);
        if (Config.isCustomItems()) {
            iBakedModel2 = CustomItems.getCustomItemModel(itemStack, iBakedModel2, ItemOverrideList.lastModelLocation, true);
        }
        return iBakedModel2 == null ? this.itemModelMesher.getModelManager().getMissingModel() : iBakedModel2;
    }

    public void renderItem(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, int n, int n2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
        this.renderItem(null, itemStack, transformType, false, matrixStack, iRenderTypeBuffer, null, n, n2);
    }

    public void renderItem(@Nullable LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, boolean bl, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, @Nullable World world, int n, int n2) {
        if (!itemStack.isEmpty()) {
            IBakedModel iBakedModel = this.getItemModelWithOverrides(itemStack, world, livingEntity);
            this.renderItem(itemStack, transformType, bl, matrixStack, iRenderTypeBuffer, n, n2, iBakedModel);
        }
    }

    public void renderItemIntoGUI(ItemStack itemStack, int n, int n2) {
        this.renderItemModelIntoGUI(itemStack, n, n2, this.getItemModelWithOverrides(itemStack, null, null));
    }

    protected void renderItemModelIntoGUI(ItemStack itemStack, int n, int n2, IBakedModel iBakedModel) {
        boolean bl;
        renderItemGui = true;
        RenderSystem.pushMatrix();
        this.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        this.textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, true);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.translatef(n, n2, 100.0f + this.zLevel);
        RenderSystem.translatef(8.0f, 8.0f, 0.0f);
        RenderSystem.scalef(1.0f, -1.0f, 1.0f);
        RenderSystem.scalef(16.0f, 16.0f, 16.0f);
        MatrixStack matrixStack = new MatrixStack();
        IRenderTypeBuffer.Impl impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        boolean bl2 = bl = !iBakedModel.isSideLit();
        if (bl) {
            RenderHelper.setupGuiFlatDiffuseLighting();
        }
        this.renderItem(itemStack, ItemCameraTransforms.TransformType.GUI, false, matrixStack, impl, 0xF000F0, OverlayTexture.NO_OVERLAY, iBakedModel);
        impl.finish();
        RenderSystem.enableDepthTest();
        if (bl) {
            RenderHelper.setupGui3DDiffuseLighting();
        }
        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        renderItemGui = false;
    }

    public void renderItemAndEffectIntoGUI(ItemStack itemStack, int n, int n2) {
        this.renderItemIntoGUI(Minecraft.getInstance().player, itemStack, n, n2);
    }

    public void renderItemAndEffectIntoGuiWithoutEntity(ItemStack itemStack, int n, int n2) {
        this.renderItemIntoGUI(null, itemStack, n, n2);
    }

    public void renderItemAndEffectIntoGUI(LivingEntity livingEntity, ItemStack itemStack, int n, int n2) {
        this.renderItemIntoGUI(livingEntity, itemStack, n, n2);
    }

    private void renderItemIntoGUI(@Nullable LivingEntity livingEntity, ItemStack itemStack, int n, int n2) {
        if (!itemStack.isEmpty()) {
            try {
                this.renderItemModelIntoGUI(itemStack, n, n2, this.getItemModelWithOverrides(itemStack, null, livingEntity));
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering item");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Item being rendered");
                crashReportCategory.addDetail("Item Type", () -> ItemRenderer.lambda$renderItemIntoGUI$0(itemStack));
                crashReportCategory.addDetail("Registry Name", () -> ItemRenderer.lambda$renderItemIntoGUI$1(itemStack));
                crashReportCategory.addDetail("Item Damage", () -> ItemRenderer.lambda$renderItemIntoGUI$2(itemStack));
                crashReportCategory.addDetail("Item NBT", () -> ItemRenderer.lambda$renderItemIntoGUI$3(itemStack));
                crashReportCategory.addDetail("Item Foil", () -> ItemRenderer.lambda$renderItemIntoGUI$4(itemStack));
                throw new ReportedException(crashReport);
            }
        }
    }

    public void renderItemOverlays(FontRenderer fontRenderer, ItemStack itemStack, int n, int n2) {
        this.renderItemOverlayIntoGUI(fontRenderer, itemStack, n, n2, null);
    }

    public void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int n, int n2, @Nullable String string) {
        if (!itemStack.isEmpty()) {
            float f;
            Object object;
            Object object2;
            MatrixStack matrixStack = new MatrixStack();
            if (itemStack.getCount() != 1 || string != null) {
                object2 = string == null ? String.valueOf(itemStack.getCount()) : string;
                matrixStack.translate(0.0, 0.0, this.zLevel + 200.0f);
                object = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
                fontRenderer.renderString((String)object2, n + 19 - 2 - fontRenderer.getStringWidth((String)object2), n2 + 6 + 3, 0xFFFFFF, true, matrixStack.getLast().getMatrix(), (IRenderTypeBuffer)object, false, 0, 1);
                ((IRenderTypeBuffer.Impl)object).finish();
            }
            if (ReflectorForge.isItemDamaged(itemStack)) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableAlphaTest();
                RenderSystem.disableBlend();
                object2 = Tessellator.getInstance();
                object = ((Tessellator)object2).getBuffer();
                float f2 = itemStack.getDamage();
                float f3 = itemStack.getMaxDamage();
                float f4 = Math.max(0.0f, (f3 - f2) / f3);
                int n3 = Math.round(13.0f - f2 * 13.0f / f3);
                int n4 = MathHelper.hsvToRGB(f4 / 3.0f, 1.0f, 1.0f);
                if (Reflector.IForgeItem_getDurabilityForDisplay.exists() && Reflector.IForgeItem_getRGBDurabilityForDisplay.exists()) {
                    double d = Reflector.callDouble(itemStack.getItem(), Reflector.IForgeItem_getDurabilityForDisplay, itemStack);
                    int n5 = Reflector.callInt(itemStack.getItem(), Reflector.IForgeItem_getRGBDurabilityForDisplay, itemStack);
                    n3 = Math.round(13.0f - (float)d * 13.0f);
                    n4 = n5;
                }
                if (Config.isCustomColors()) {
                    n4 = CustomColors.getDurabilityColor(f4, n4);
                }
                this.draw((BufferBuilder)object, n + 2, n2 + 13, 13, 2, 0, 0, 0, 255);
                this.draw((BufferBuilder)object, n + 2, n2 + 13, n3, 1, n4 >> 16 & 0xFF, n4 >> 8 & 0xFF, n4 & 0xFF, 255);
                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }
            float f5 = f = (object2 = Minecraft.getInstance().player) == null ? 0.0f : ((PlayerEntity)object2).getCooldownTracker().getCooldown(itemStack.getItem(), Minecraft.getInstance().getRenderPartialTicks());
            if (f > 0.0f) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                this.draw(bufferBuilder, n, n2 + MathHelper.floor(16.0f * (1.0f - f)), 16, MathHelper.ceil(16.0f * f), 255, 255, 255, 127);
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }
        }
    }

    private void draw(BufferBuilder bufferBuilder, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(n + 0, n2 + 0, 0.0).color(n5, n6, n7, n8).endVertex();
        bufferBuilder.pos(n + 0, n2 + n4, 0.0).color(n5, n6, n7, n8).endVertex();
        bufferBuilder.pos(n + n3, n2 + n4, 0.0).color(n5, n6, n7, n8).endVertex();
        bufferBuilder.pos(n + n3, n2 + 0, 0.0).color(n5, n6, n7, n8).endVertex();
        Tessellator.getInstance().draw();
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.itemModelMesher.rebuildCache();
    }

    public IResourceType getResourceType() {
        return VanillaResourceType.MODELS;
    }

    private static String lambda$renderItemIntoGUI$4(ItemStack itemStack) throws Exception {
        return String.valueOf(itemStack.hasEffect());
    }

    private static String lambda$renderItemIntoGUI$3(ItemStack itemStack) throws Exception {
        return String.valueOf(itemStack.getTag());
    }

    private static String lambda$renderItemIntoGUI$2(ItemStack itemStack) throws Exception {
        return String.valueOf(itemStack.getDamage());
    }

    private static String lambda$renderItemIntoGUI$1(ItemStack itemStack) throws Exception {
        return String.valueOf(Reflector.call(itemStack.getItem(), Reflector.ForgeRegistryEntry_getRegistryName, new Object[0]));
    }

    private static String lambda$renderItemIntoGUI$0(ItemStack itemStack) throws Exception {
        return String.valueOf(itemStack.getItem());
    }
}

