/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.network.play.client.CSelectTradePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MerchantScreen
extends ContainerScreen<MerchantContainer> {
    private static final ResourceLocation MERCHANT_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
    private static final ITextComponent field_243351_B = new TranslationTextComponent("merchant.trades");
    private static final ITextComponent field_243352_C = new StringTextComponent(" - ");
    private static final ITextComponent field_243353_D = new TranslationTextComponent("merchant.deprecated");
    private int selectedMerchantRecipe;
    private final TradeButton[] field_214138_m = new TradeButton[7];
    private int field_214139_n;
    private boolean field_214140_o;

    public MerchantScreen(MerchantContainer merchantContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(merchantContainer, playerInventory, iTextComponent);
        this.xSize = 276;
        this.playerInventoryTitleX = 107;
    }

    private void func_195391_j() {
        ((MerchantContainer)this.container).setCurrentRecipeIndex(this.selectedMerchantRecipe);
        ((MerchantContainer)this.container).func_217046_g(this.selectedMerchantRecipe);
        this.minecraft.getConnection().sendPacket(new CSelectTradePacket(this.selectedMerchantRecipe));
    }

    @Override
    protected void init() {
        super.init();
        int n = (this.width - this.xSize) / 2;
        int n2 = (this.height - this.ySize) / 2;
        int n3 = n2 + 16 + 2;
        for (int i = 0; i < 7; ++i) {
            this.field_214138_m[i] = this.addButton(new TradeButton(this, n + 5, n3, i, this::lambda$init$0));
            n3 += 20;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        int n3 = ((MerchantContainer)this.container).getMerchantLevel();
        if (n3 > 0 && n3 <= 5 && ((MerchantContainer)this.container).func_217042_i()) {
            IFormattableTextComponent iFormattableTextComponent = this.title.deepCopy().append(field_243352_C).append(new TranslationTextComponent("merchant.level." + n3));
            int n4 = this.font.getStringPropertyWidth(iFormattableTextComponent);
            int n5 = 49 + this.xSize / 2 - n4 / 2;
            this.font.func_243248_b(matrixStack, iFormattableTextComponent, n5, 6.0f, 0x404040);
        } else {
            this.font.func_243248_b(matrixStack, this.title, 49 + this.xSize / 2 - this.font.getStringPropertyWidth(this.title) / 2, 6.0f, 0x404040);
        }
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040);
        int n6 = this.font.getStringPropertyWidth(field_243351_B);
        this.font.func_243248_b(matrixStack, field_243351_B, 5 - n6 / 2 + 48, 6.0f, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
        int n3 = (this.width - this.xSize) / 2;
        int n4 = (this.height - this.ySize) / 2;
        MerchantScreen.blit(matrixStack, n3, n4, this.getBlitOffset(), 0.0f, 0.0f, this.xSize, this.ySize, 256, 512);
        MerchantOffers merchantOffers = ((MerchantContainer)this.container).getOffers();
        if (!merchantOffers.isEmpty()) {
            int n5 = this.selectedMerchantRecipe;
            if (n5 < 0 || n5 >= merchantOffers.size()) {
                return;
            }
            MerchantOffer merchantOffer = (MerchantOffer)merchantOffers.get(n5);
            if (merchantOffer.hasNoUsesLeft()) {
                this.minecraft.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                MerchantScreen.blit(matrixStack, this.guiLeft + 83 + 99, this.guiTop + 35, this.getBlitOffset(), 311.0f, 0.0f, 28, 21, 256, 512);
            }
        }
    }

    private void func_238839_a_(MatrixStack matrixStack, int n, int n2, MerchantOffer merchantOffer) {
        this.minecraft.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
        int n3 = ((MerchantContainer)this.container).getMerchantLevel();
        int n4 = ((MerchantContainer)this.container).getXp();
        if (n3 < 5) {
            MerchantScreen.blit(matrixStack, n + 136, n2 + 16, this.getBlitOffset(), 0.0f, 186.0f, 102, 5, 256, 512);
            int n5 = VillagerData.getExperiencePrevious(n3);
            if (n4 >= n5 && VillagerData.canLevelUp(n3)) {
                int n6 = 100;
                float f = 100.0f / (float)(VillagerData.getExperienceNext(n3) - n5);
                int n7 = Math.min(MathHelper.floor(f * (float)(n4 - n5)), 100);
                MerchantScreen.blit(matrixStack, n + 136, n2 + 16, this.getBlitOffset(), 0.0f, 191.0f, n7 + 1, 5, 256, 512);
                int n8 = ((MerchantContainer)this.container).getPendingExp();
                if (n8 > 0) {
                    int n9 = Math.min(MathHelper.floor((float)n8 * f), 100 - n7);
                    MerchantScreen.blit(matrixStack, n + 136 + n7 + 1, n2 + 16 + 1, this.getBlitOffset(), 2.0f, 182.0f, n9, 3, 256, 512);
                }
            }
        }
    }

    private void func_238840_a_(MatrixStack matrixStack, int n, int n2, MerchantOffers merchantOffers) {
        int n3 = merchantOffers.size() + 1 - 7;
        if (n3 > 1) {
            int n4 = 139 - (27 + (n3 - 1) * 139 / n3);
            int n5 = 1 + n4 / n3 + 139 / n3;
            int n6 = 113;
            int n7 = Math.min(113, this.field_214139_n * n5);
            if (this.field_214139_n == n3 - 1) {
                n7 = 113;
            }
            MerchantScreen.blit(matrixStack, n + 94, n2 + 18 + n7, this.getBlitOffset(), 0.0f, 199.0f, 6, 27, 256, 512);
        } else {
            MerchantScreen.blit(matrixStack, n + 94, n2 + 18, this.getBlitOffset(), 6.0f, 199.0f, 6, 27, 256, 512);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        MerchantOffers merchantOffers = ((MerchantContainer)this.container).getOffers();
        if (!merchantOffers.isEmpty()) {
            MerchantOffer merchantOffer22;
            int n3 = (this.width - this.xSize) / 2;
            int n4 = (this.height - this.ySize) / 2;
            int n5 = n4 + 16 + 1;
            int n6 = n3 + 5 + 5;
            RenderSystem.pushMatrix();
            RenderSystem.enableRescaleNormal();
            this.minecraft.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
            this.func_238840_a_(matrixStack, n3, n4, merchantOffers);
            int n7 = 0;
            for (MerchantOffer merchantOffer22 : merchantOffers) {
                if (this.func_214135_a(merchantOffers.size()) && (n7 < this.field_214139_n || n7 >= 7 + this.field_214139_n)) {
                    ++n7;
                    continue;
                }
                TradeButton[] tradeButtonArray = merchantOffer22.getBuyingStackFirst();
                ItemStack itemStack = merchantOffer22.getDiscountedBuyingStackFirst();
                ItemStack itemStack2 = merchantOffer22.getBuyingStackSecond();
                ItemStack object = merchantOffer22.getSellingStack();
                this.itemRenderer.zLevel = 100.0f;
                int n8 = n5 + 2;
                this.func_238841_a_(matrixStack, itemStack, (ItemStack)tradeButtonArray, n6, n8);
                if (!itemStack2.isEmpty()) {
                    this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemStack2, n3 + 5 + 35, n8);
                    this.itemRenderer.renderItemOverlays(this.font, itemStack2, n3 + 5 + 35, n8);
                }
                this.func_238842_a_(matrixStack, merchantOffer22, n3, n8);
                this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(object, n3 + 5 + 68, n8);
                this.itemRenderer.renderItemOverlays(this.font, object, n3 + 5 + 68, n8);
                this.itemRenderer.zLevel = 0.0f;
                n5 += 20;
                ++n7;
            }
            int n9 = this.selectedMerchantRecipe;
            merchantOffer22 = (MerchantOffer)merchantOffers.get(n9);
            if (((MerchantContainer)this.container).func_217042_i()) {
                this.func_238839_a_(matrixStack, n3, n4, merchantOffer22);
            }
            if (merchantOffer22.hasNoUsesLeft() && this.isPointInRegion(186, 35, 22, 21, n, n2) && ((MerchantContainer)this.container).func_223432_h()) {
                this.renderTooltip(matrixStack, field_243353_D, n, n2);
            }
            for (TradeButton tradeButton : this.field_214138_m) {
                if (tradeButton.isHovered()) {
                    tradeButton.renderToolTip(matrixStack, n, n2);
                }
                tradeButton.visible = tradeButton.field_212938_a < ((MerchantContainer)this.container).getOffers().size();
            }
            RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    private void func_238842_a_(MatrixStack matrixStack, MerchantOffer merchantOffer, int n, int n2) {
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
        if (merchantOffer.hasNoUsesLeft()) {
            MerchantScreen.blit(matrixStack, n + 5 + 35 + 20, n2 + 3, this.getBlitOffset(), 25.0f, 171.0f, 10, 9, 256, 512);
        } else {
            MerchantScreen.blit(matrixStack, n + 5 + 35 + 20, n2 + 3, this.getBlitOffset(), 15.0f, 171.0f, 10, 9, 256, 512);
        }
    }

    private void func_238841_a_(MatrixStack matrixStack, ItemStack itemStack, ItemStack itemStack2, int n, int n2) {
        this.itemRenderer.renderItemAndEffectIntoGuiWithoutEntity(itemStack, n, n2);
        if (itemStack2.getCount() == itemStack.getCount()) {
            this.itemRenderer.renderItemOverlays(this.font, itemStack, n, n2);
        } else {
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, itemStack2, n, n2, itemStack2.getCount() == 1 ? "1" : null);
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, itemStack, n + 14, n2, itemStack.getCount() == 1 ? "1" : null);
            this.minecraft.getTextureManager().bindTexture(MERCHANT_GUI_TEXTURE);
            this.setBlitOffset(this.getBlitOffset() + 300);
            MerchantScreen.blit(matrixStack, n + 7, n2 + 12, this.getBlitOffset(), 0.0f, 176.0f, 9, 2, 256, 512);
            this.setBlitOffset(this.getBlitOffset() - 300);
        }
    }

    private boolean func_214135_a(int n) {
        return n > 7;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        int n = ((MerchantContainer)this.container).getOffers().size();
        if (this.func_214135_a(n)) {
            int n2 = n - 7;
            this.field_214139_n = (int)((double)this.field_214139_n - d3);
            this.field_214139_n = MathHelper.clamp(this.field_214139_n, 0, n2);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        int n2 = ((MerchantContainer)this.container).getOffers().size();
        if (this.field_214140_o) {
            int n3 = this.guiTop + 18;
            int n4 = n3 + 139;
            int n5 = n2 - 7;
            float f = ((float)d2 - (float)n3 - 13.5f) / ((float)(n4 - n3) - 27.0f);
            f = f * (float)n5 + 0.5f;
            this.field_214139_n = MathHelper.clamp((int)f, 0, n5);
            return false;
        }
        return super.mouseDragged(d, d2, n, d3, d4);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        this.field_214140_o = false;
        int n2 = (this.width - this.xSize) / 2;
        int n3 = (this.height - this.ySize) / 2;
        if (this.func_214135_a(((MerchantContainer)this.container).getOffers().size()) && d > (double)(n2 + 94) && d < (double)(n2 + 94 + 6) && d2 > (double)(n3 + 18) && d2 <= (double)(n3 + 18 + 139 + 1)) {
            this.field_214140_o = true;
        }
        return super.mouseClicked(d, d2, n);
    }

    private void lambda$init$0(Button button) {
        if (button instanceof TradeButton) {
            this.selectedMerchantRecipe = ((TradeButton)button).func_212937_a() + this.field_214139_n;
            this.func_195391_j();
        }
    }

    static void access$000(MerchantScreen merchantScreen, MatrixStack matrixStack, ItemStack itemStack, int n, int n2) {
        merchantScreen.renderTooltip(matrixStack, itemStack, n, n2);
    }

    static void access$100(MerchantScreen merchantScreen, MatrixStack matrixStack, ItemStack itemStack, int n, int n2) {
        merchantScreen.renderTooltip(matrixStack, itemStack, n, n2);
    }

    static void access$200(MerchantScreen merchantScreen, MatrixStack matrixStack, ItemStack itemStack, int n, int n2) {
        merchantScreen.renderTooltip(matrixStack, itemStack, n, n2);
    }

    class TradeButton
    extends Button {
        final int field_212938_a;
        final MerchantScreen this$0;

        public TradeButton(MerchantScreen merchantScreen, int n, int n2, int n3, Button.IPressable iPressable) {
            this.this$0 = merchantScreen;
            super(n, n2, 89, 20, StringTextComponent.EMPTY, iPressable);
            this.field_212938_a = n3;
            this.visible = false;
        }

        public int func_212937_a() {
            return this.field_212938_a;
        }

        @Override
        public void renderToolTip(MatrixStack matrixStack, int n, int n2) {
            if (this.isHovered && ((MerchantContainer)this.this$0.container).getOffers().size() > this.field_212938_a + this.this$0.field_214139_n) {
                if (n < this.x + 20) {
                    ItemStack itemStack = ((MerchantOffer)((MerchantContainer)this.this$0.container).getOffers().get(this.field_212938_a + this.this$0.field_214139_n)).getDiscountedBuyingStackFirst();
                    MerchantScreen.access$000(this.this$0, matrixStack, itemStack, n, n2);
                } else if (n < this.x + 50 && n > this.x + 30) {
                    ItemStack itemStack = ((MerchantOffer)((MerchantContainer)this.this$0.container).getOffers().get(this.field_212938_a + this.this$0.field_214139_n)).getBuyingStackSecond();
                    if (!itemStack.isEmpty()) {
                        MerchantScreen.access$100(this.this$0, matrixStack, itemStack, n, n2);
                    }
                } else if (n > this.x + 65) {
                    ItemStack itemStack = ((MerchantOffer)((MerchantContainer)this.this$0.container).getOffers().get(this.field_212938_a + this.this$0.field_214139_n)).getSellingStack();
                    MerchantScreen.access$200(this.this$0, matrixStack, itemStack, n, n2);
                }
            }
        }
    }
}

