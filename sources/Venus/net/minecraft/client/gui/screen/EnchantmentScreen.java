/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class EnchantmentScreen
extends ContainerScreen<EnchantmentContainer> {
    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
    private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private static final BookModel MODEL_BOOK = new BookModel();
    private final Random random = new Random();
    public int ticks;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    private ItemStack last = ItemStack.EMPTY;

    public EnchantmentScreen(EnchantmentContainer enchantmentContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(enchantmentContainer, playerInventory, iTextComponent);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickBook();
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        int n2 = (this.width - this.xSize) / 2;
        int n3 = (this.height - this.ySize) / 2;
        for (int i = 0; i < 3; ++i) {
            double d3 = d - (double)(n2 + 60);
            double d4 = d2 - (double)(n3 + 14 + 19 * i);
            if (!(d3 >= 0.0) || !(d4 >= 0.0) || !(d3 < 108.0) || !(d4 < 19.0) || !((EnchantmentContainer)this.container).enchantItem(this.minecraft.player, i)) continue;
            this.minecraft.playerController.sendEnchantPacket(((EnchantmentContainer)this.container).windowId, i);
            return false;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderHelper.setupGuiFlatDiffuseLighting();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        this.blit(matrixStack, n3, n4, 0, 0, this.xSize, this.ySize);
        RenderSystem.matrixMode(5889);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        int n5 = (int)this.minecraft.getMainWindow().getGuiScaleFactor();
        RenderSystem.viewport((this.width - 320) / 2 * n5, (this.height - 240) / 2 * n5, 320 * n5, 240 * n5);
        RenderSystem.translatef(-0.34f, 0.23f, 0.0f);
        RenderSystem.multMatrix(Matrix4f.perspective(90.0, 1.3333334f, 9.0f, 80.0f));
        RenderSystem.matrixMode(5888);
        matrixStack.push();
        MatrixStack.Entry entry = matrixStack.getLast();
        entry.getMatrix().setIdentity();
        entry.getNormal().setIdentity();
        matrixStack.translate(0.0, 3.3f, 1984.0);
        float f2 = 5.0f;
        matrixStack.scale(5.0f, 5.0f, 5.0f);
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(20.0f));
        float f3 = MathHelper.lerp(f, this.oOpen, this.open);
        matrixStack.translate((1.0f - f3) * 0.2f, (1.0f - f3) * 0.1f, (1.0f - f3) * 0.25f);
        float f4 = -(1.0f - f3) * 90.0f - 90.0f;
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f4));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0f));
        float f5 = MathHelper.lerp(f, this.oFlip, this.flip) + 0.25f;
        float f6 = MathHelper.lerp(f, this.oFlip, this.flip) + 0.75f;
        f5 = (f5 - (float)MathHelper.fastFloor(f5)) * 1.6f - 0.3f;
        f6 = (f6 - (float)MathHelper.fastFloor(f6)) * 1.6f - 0.3f;
        if (f5 < 0.0f) {
            f5 = 0.0f;
        }
        if (f6 < 0.0f) {
            f6 = 0.0f;
        }
        if (f5 > 1.0f) {
            f5 = 1.0f;
        }
        if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        RenderSystem.enableRescaleNormal();
        MODEL_BOOK.setBookState(0.0f, f5, f6, f3);
        IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        IVertexBuilder iVertexBuilder = impl.getBuffer(MODEL_BOOK.getRenderType(ENCHANTMENT_TABLE_BOOK_TEXTURE));
        MODEL_BOOK.render(matrixStack, iVertexBuilder, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        impl.finish();
        matrixStack.pop();
        RenderSystem.matrixMode(5889);
        RenderSystem.viewport(0, 0, this.minecraft.getMainWindow().getFramebufferWidth(), this.minecraft.getMainWindow().getFramebufferHeight());
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
        RenderHelper.setupGui3DDiffuseLighting();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.getInstance().reseedRandomGenerator(((EnchantmentContainer)this.container).func_217005_f());
        int n6 = ((EnchantmentContainer)this.container).getLapisAmount();
        for (int i = 0; i < 3; ++i) {
            int n7 = n3 + 60;
            int n8 = n7 + 20;
            this.setBlitOffset(0);
            this.minecraft.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
            int n9 = ((EnchantmentContainer)this.container).enchantLevels[i];
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (n9 == 0) {
                this.blit(matrixStack, n7, n4 + 14 + 19 * i, 0, 185, 108, 19);
                continue;
            }
            String string = "" + n9;
            int n10 = 86 - this.font.getStringWidth(string);
            ITextProperties iTextProperties = EnchantmentNameParts.getInstance().getGalacticEnchantmentName(this.font, n10);
            int n11 = 6839882;
            if (!(n6 >= i + 1 && this.minecraft.player.experienceLevel >= n9 || this.minecraft.player.abilities.isCreativeMode)) {
                this.blit(matrixStack, n7, n4 + 14 + 19 * i, 0, 185, 108, 19);
                this.blit(matrixStack, n7 + 1, n4 + 15 + 19 * i, 16 * i, 239, 16, 16);
                this.font.func_238418_a_(iTextProperties, n8, n4 + 16 + 19 * i, n10, (n11 & 0xFEFEFE) >> 1);
                n11 = 4226832;
            } else {
                int n12 = n - (n3 + 60);
                int n13 = n2 - (n4 + 14 + 19 * i);
                if (n12 >= 0 && n13 >= 0 && n12 < 108 && n13 < 19) {
                    this.blit(matrixStack, n7, n4 + 14 + 19 * i, 0, 204, 108, 19);
                    n11 = 0xFFFF80;
                } else {
                    this.blit(matrixStack, n7, n4 + 14 + 19 * i, 0, 166, 108, 19);
                }
                this.blit(matrixStack, n7 + 1, n4 + 15 + 19 * i, 16 * i, 223, 16, 16);
                this.font.func_238418_a_(iTextProperties, n8, n4 + 16 + 19 * i, n10, n11);
                n11 = 8453920;
            }
            this.font.drawStringWithShadow(matrixStack, string, n8 + 86 - this.font.getStringWidth(string), n4 + 16 + 19 * i + 7, n11);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        f = this.minecraft.getRenderPartialTicks();
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        this.renderHoveredTooltip(matrixStack, n, n2);
        boolean bl = this.minecraft.player.abilities.isCreativeMode;
        int n3 = ((EnchantmentContainer)this.container).getLapisAmount();
        for (int i = 0; i < 3; ++i) {
            int n4 = ((EnchantmentContainer)this.container).enchantLevels[i];
            Enchantment enchantment = Enchantment.getEnchantmentByID(((EnchantmentContainer)this.container).enchantClue[i]);
            int n5 = ((EnchantmentContainer)this.container).worldClue[i];
            int n6 = i + 1;
            if (!this.isPointInRegion(60, 14 + 19 * i, 108, 17, n, n2) || n4 <= 0 || n5 < 0 || enchantment == null) continue;
            ArrayList<ITextComponent> arrayList = Lists.newArrayList();
            arrayList.add(new TranslationTextComponent("container.enchant.clue", enchantment.getDisplayName(n5)).mergeStyle(TextFormatting.WHITE));
            if (!bl) {
                arrayList.add(StringTextComponent.EMPTY);
                if (this.minecraft.player.experienceLevel < n4) {
                    arrayList.add(new TranslationTextComponent("container.enchant.level.requirement", ((EnchantmentContainer)this.container).enchantLevels[i]).mergeStyle(TextFormatting.RED));
                } else {
                    TranslationTextComponent translationTextComponent = n6 == 1 ? new TranslationTextComponent("container.enchant.lapis.one") : new TranslationTextComponent("container.enchant.lapis.many", n6);
                    arrayList.add(translationTextComponent.mergeStyle(n3 >= n6 ? TextFormatting.GRAY : TextFormatting.RED));
                    TranslationTextComponent translationTextComponent2 = n6 == 1 ? new TranslationTextComponent("container.enchant.level.one") : new TranslationTextComponent("container.enchant.level.many", n6);
                    arrayList.add(translationTextComponent2.mergeStyle(TextFormatting.GRAY));
                }
            }
            this.func_243308_b(matrixStack, arrayList, n, n2);
            break;
        }
    }

    public void tickBook() {
        ItemStack itemStack = ((EnchantmentContainer)this.container).getSlot(0).getStack();
        if (!ItemStack.areItemStacksEqual(itemStack, this.last)) {
            this.last = itemStack;
            do {
                this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));
            } while (this.flip <= this.flipT + 1.0f && this.flip >= this.flipT - 1.0f);
        }
        ++this.ticks;
        this.oFlip = this.flip;
        this.oOpen = this.open;
        boolean bl = false;
        for (int i = 0; i < 3; ++i) {
            if (((EnchantmentContainer)this.container).enchantLevels[i] == 0) continue;
            bl = true;
        }
        this.open = bl ? (this.open += 0.2f) : (this.open -= 0.2f);
        this.open = MathHelper.clamp(this.open, 0.0f, 1.0f);
        float f = (this.flipT - this.flip) * 0.4f;
        float f2 = 0.2f;
        f = MathHelper.clamp(f, -0.2f, 0.2f);
        this.flipA += (f - this.flipA) * 0.9f;
        this.flip += this.flipA;
    }
}

