/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.CartographyContainer;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;

public class CartographyTableScreen
extends ContainerScreen<CartographyContainer> {
    private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/cartography_table.png");

    public CartographyTableScreen(CartographyContainer cartographyContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(cartographyContainer, playerInventory, iTextComponent);
        this.titleY -= 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        MapData mapData;
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(CONTAINER_TEXTURE);
        int n3 = this.guiLeft;
        int n4 = this.guiTop;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        Item item = ((CartographyContainer)this.container).getSlot(1).getStack().getItem();
        boolean bl = item == Items.MAP;
        boolean bl2 = item == Items.PAPER;
        boolean bl3 = item == Items.GLASS_PANE;
        ItemStack itemStack = ((CartographyContainer)this.container).getSlot(0).getStack();
        boolean bl4 = false;
        if (itemStack.getItem() == Items.FILLED_MAP) {
            mapData = FilledMapItem.getData(itemStack, this.minecraft.world);
            if (mapData != null) {
                if (mapData.locked) {
                    bl4 = true;
                    if (bl2 || bl3) {
                        this.blit(matrixStack, n3 + 35, n4 + 31, this.xSize + 50, 132, 28, 21);
                    }
                }
                if (bl2 && mapData.scale >= 4) {
                    bl4 = true;
                    this.blit(matrixStack, n3 + 35, n4 + 31, this.xSize + 50, 132, 28, 21);
                }
            }
        } else {
            mapData = null;
        }
        this.func_238807_a_(matrixStack, mapData, bl, bl2, bl3, bl4);
    }

    private void func_238807_a_(MatrixStack matrixStack, @Nullable MapData mapData, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = this.guiLeft;
        int n2 = this.guiTop;
        if (bl2 && !bl4) {
            this.blit(matrixStack, n + 67, n2 + 13, this.xSize, 66, 66, 66);
            this.drawMapItem(mapData, n + 85, n2 + 31, 0.226f);
        } else if (bl) {
            this.blit(matrixStack, n + 67 + 16, n2 + 13, this.xSize, 132, 50, 66);
            this.drawMapItem(mapData, n + 86, n2 + 16, 0.34f);
            this.minecraft.getTextureManager().bindTexture(CONTAINER_TEXTURE);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 1.0f);
            this.blit(matrixStack, n + 67, n2 + 13 + 16, this.xSize, 132, 50, 66);
            this.drawMapItem(mapData, n + 70, n2 + 32, 0.34f);
            RenderSystem.popMatrix();
        } else if (bl3) {
            this.blit(matrixStack, n + 67, n2 + 13, this.xSize, 0, 66, 66);
            this.drawMapItem(mapData, n + 71, n2 + 17, 0.45f);
            this.minecraft.getTextureManager().bindTexture(CONTAINER_TEXTURE);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 1.0f);
            this.blit(matrixStack, n + 66, n2 + 12, 0, this.ySize, 66, 66);
            RenderSystem.popMatrix();
        } else {
            this.blit(matrixStack, n + 67, n2 + 13, this.xSize, 0, 66, 66);
            this.drawMapItem(mapData, n + 71, n2 + 17, 0.45f);
        }
    }

    private void drawMapItem(@Nullable MapData mapData, int n, int n2, float f) {
        if (mapData != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(n, n2, 1.0f);
            RenderSystem.scalef(f, f, 1.0f);
            IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            this.minecraft.gameRenderer.getMapItemRenderer().renderMap(new MatrixStack(), impl, mapData, true, 1);
            impl.finish();
            RenderSystem.popMatrix();
        }
    }
}

