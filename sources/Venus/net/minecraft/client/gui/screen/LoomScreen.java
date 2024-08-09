/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.LoomContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class LoomScreen
extends ContainerScreen<LoomContainer> {
    private static final ResourceLocation LOOM_GUI_TEXTURES = new ResourceLocation("textures/gui/container/loom.png");
    private static final int field_214114_l = (BannerPattern.BANNER_PATTERNS_COUNT - BannerPattern.BANNERS_WITH_ITEMS - 1 + 4 - 1) / 4;
    private final ModelRenderer modelRender;
    @Nullable
    private List<Pair<BannerPattern, DyeColor>> field_230155_n_;
    private ItemStack bannerStack = ItemStack.EMPTY;
    private ItemStack dyeStack = ItemStack.EMPTY;
    private ItemStack patternStack = ItemStack.EMPTY;
    private boolean displayPatternsIn;
    private boolean field_214124_v;
    private boolean field_214125_w;
    private float field_214126_x;
    private boolean isScrolling;
    private int indexStarting = 1;

    public LoomScreen(LoomContainer loomContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(loomContainer, playerInventory, iTextComponent);
        this.modelRender = BannerTileEntityRenderer.getModelRender();
        loomContainer.func_217020_a(this::containerChange);
        this.titleY -= 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bindTexture(LOOM_GUI_TEXTURES);
        int n3 = this.guiLeft;
        int n4 = this.guiTop;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        Slot slot = ((LoomContainer)this.container).getBannerSlot();
        Slot slot2 = ((LoomContainer)this.container).getDyeSlot();
        Slot slot3 = ((LoomContainer)this.container).getPatternSlot();
        Slot slot4 = ((LoomContainer)this.container).getOutputSlot();
        if (!slot.getHasStack()) {
            this.blit(matrixStack, n3 + slot.xPos, n4 + slot.yPos, this.xSize, 0, 16, 16);
        }
        if (!slot2.getHasStack()) {
            this.blit(matrixStack, n3 + slot2.xPos, n4 + slot2.yPos, this.xSize + 16, 0, 16, 16);
        }
        if (!slot3.getHasStack()) {
            this.blit(matrixStack, n3 + slot3.xPos, n4 + slot3.yPos, this.xSize + 32, 0, 16, 16);
        }
        int n5 = (int)(41.0f * this.field_214126_x);
        this.blit(matrixStack, n3 + 119, n4 + 13 + n5, 232 + (this.displayPatternsIn ? 0 : 12), 0, 12, 15);
        RenderHelper.setupGuiFlatDiffuseLighting();
        if (this.field_230155_n_ != null && !this.field_214125_w) {
            IRenderTypeBuffer.Impl impl = this.minecraft.getRenderTypeBuffers().getBufferSource();
            matrixStack.push();
            matrixStack.translate(n3 + 139, n4 + 52, 0.0);
            matrixStack.scale(24.0f, -24.0f, 1.0f);
            matrixStack.translate(0.5, 0.5, 0.5);
            float f2 = 0.6666667f;
            matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
            this.modelRender.rotateAngleX = 0.0f;
            this.modelRender.rotationPointY = -32.0f;
            BannerTileEntityRenderer.func_230180_a_(matrixStack, impl, 0xF000F0, OverlayTexture.NO_OVERLAY, this.modelRender, ModelBakery.LOCATION_BANNER_BASE, true, this.field_230155_n_);
            matrixStack.pop();
            impl.finish();
        } else if (this.field_214125_w) {
            this.blit(matrixStack, n3 + slot4.xPos - 2, n4 + slot4.yPos - 2, this.xSize, 17, 17, 16);
        }
        if (this.displayPatternsIn) {
            int n6 = n3 + 60;
            int n7 = n4 + 13;
            int n8 = this.indexStarting + 16;
            for (int i = this.indexStarting; i < n8 && i < BannerPattern.BANNER_PATTERNS_COUNT - BannerPattern.BANNERS_WITH_ITEMS; ++i) {
                int n9 = i - this.indexStarting;
                int n10 = n6 + n9 % 4 * 14;
                int n11 = n7 + n9 / 4 * 14;
                this.minecraft.getTextureManager().bindTexture(LOOM_GUI_TEXTURES);
                int n12 = this.ySize;
                if (i == ((LoomContainer)this.container).func_217023_e()) {
                    n12 += 14;
                } else if (n >= n10 && n2 >= n11 && n < n10 + 14 && n2 < n11 + 14) {
                    n12 += 28;
                }
                this.blit(matrixStack, n10, n11, 0, n12, 14, 14);
                this.func_228190_b_(i, n10, n11);
            }
        } else if (this.field_214124_v) {
            int n13 = n3 + 60;
            int n14 = n4 + 13;
            this.minecraft.getTextureManager().bindTexture(LOOM_GUI_TEXTURES);
            this.blit(matrixStack, n13, n14, 0, this.ySize, 14, 14);
            int n15 = ((LoomContainer)this.container).func_217023_e();
            this.func_228190_b_(n15, n13, n14);
        }
        RenderHelper.setupGui3DDiffuseLighting();
    }

    private void func_228190_b_(int n, int n2, int n3) {
        ItemStack itemStack = new ItemStack(Items.GRAY_BANNER);
        CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("BlockEntityTag");
        ListNBT listNBT = new BannerPattern.Builder().setPatternWithColor(BannerPattern.BASE, DyeColor.GRAY).setPatternWithColor(BannerPattern.values()[n], DyeColor.WHITE).buildNBT();
        compoundNBT.put("Patterns", listNBT);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.push();
        matrixStack.translate((float)n2 + 0.5f, n3 + 16, 0.0);
        matrixStack.scale(6.0f, -6.0f, 1.0f);
        matrixStack.translate(0.5, 0.5, 0.0);
        matrixStack.translate(0.5, 0.5, 0.5);
        float f = 0.6666667f;
        matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
        IRenderTypeBuffer.Impl impl = this.minecraft.getRenderTypeBuffers().getBufferSource();
        this.modelRender.rotateAngleX = 0.0f;
        this.modelRender.rotationPointY = -32.0f;
        List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.getPatternColorData(DyeColor.GRAY, BannerTileEntity.getPatternData(itemStack));
        BannerTileEntityRenderer.func_230180_a_(matrixStack, impl, 0xF000F0, OverlayTexture.NO_OVERLAY, this.modelRender, ModelBakery.LOCATION_BANNER_BASE, true, list);
        matrixStack.pop();
        impl.finish();
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        this.isScrolling = false;
        if (this.displayPatternsIn) {
            int n2 = this.guiLeft + 60;
            int n3 = this.guiTop + 13;
            int n4 = this.indexStarting + 16;
            for (int i = this.indexStarting; i < n4; ++i) {
                int n5 = i - this.indexStarting;
                double d3 = d - (double)(n2 + n5 % 4 * 14);
                double d4 = d2 - (double)(n3 + n5 / 4 * 14);
                if (!(d3 >= 0.0) || !(d4 >= 0.0) || !(d3 < 14.0) || !(d4 < 14.0) || !((LoomContainer)this.container).enchantItem(this.minecraft.player, i)) continue;
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0f));
                this.minecraft.playerController.sendEnchantPacket(((LoomContainer)this.container).windowId, i);
                return false;
            }
            n2 = this.guiLeft + 119;
            n3 = this.guiTop + 9;
            if (d >= (double)n2 && d < (double)(n2 + 12) && d2 >= (double)n3 && d2 < (double)(n3 + 56)) {
                this.isScrolling = true;
            }
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (this.isScrolling && this.displayPatternsIn) {
            int n2 = this.guiTop + 13;
            int n3 = n2 + 56;
            this.field_214126_x = ((float)d2 - (float)n2 - 7.5f) / ((float)(n3 - n2) - 15.0f);
            this.field_214126_x = MathHelper.clamp(this.field_214126_x, 0.0f, 1.0f);
            int n4 = field_214114_l - 4;
            int n5 = (int)((double)(this.field_214126_x * (float)n4) + 0.5);
            if (n5 < 0) {
                n5 = 0;
            }
            this.indexStarting = 1 + n5 * 4;
            return false;
        }
        return super.mouseDragged(d, d2, n, d3, d4);
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        if (this.displayPatternsIn) {
            int n = field_214114_l - 4;
            this.field_214126_x = (float)((double)this.field_214126_x - d3 / (double)n);
            this.field_214126_x = MathHelper.clamp(this.field_214126_x, 0.0f, 1.0f);
            this.indexStarting = 1 + (int)((double)(this.field_214126_x * (float)n) + 0.5) * 4;
        }
        return false;
    }

    @Override
    protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
        return d < (double)n || d2 < (double)n2 || d >= (double)(n + this.xSize) || d2 >= (double)(n2 + this.ySize);
    }

    private void containerChange() {
        ItemStack itemStack = ((LoomContainer)this.container).getOutputSlot().getStack();
        this.field_230155_n_ = itemStack.isEmpty() ? null : BannerTileEntity.getPatternColorData(((BannerItem)itemStack.getItem()).getColor(), BannerTileEntity.getPatternData(itemStack));
        ItemStack itemStack2 = ((LoomContainer)this.container).getBannerSlot().getStack();
        ItemStack itemStack3 = ((LoomContainer)this.container).getDyeSlot().getStack();
        ItemStack itemStack4 = ((LoomContainer)this.container).getPatternSlot().getStack();
        CompoundNBT compoundNBT = itemStack2.getOrCreateChildTag("BlockEntityTag");
        boolean bl = this.field_214125_w = compoundNBT.contains("Patterns", 0) && !itemStack2.isEmpty() && compoundNBT.getList("Patterns", 10).size() >= 6;
        if (this.field_214125_w) {
            this.field_230155_n_ = null;
        }
        if (!(ItemStack.areItemStacksEqual(itemStack2, this.bannerStack) && ItemStack.areItemStacksEqual(itemStack3, this.dyeStack) && ItemStack.areItemStacksEqual(itemStack4, this.patternStack))) {
            this.displayPatternsIn = !itemStack2.isEmpty() && !itemStack3.isEmpty() && itemStack4.isEmpty() && !this.field_214125_w;
            this.field_214124_v = !this.field_214125_w && !itemStack4.isEmpty() && !itemStack2.isEmpty() && !itemStack3.isEmpty();
        }
        this.bannerStack = itemStack2.copy();
        this.dyeStack = itemStack3.copy();
        this.patternStack = itemStack4.copy();
    }
}

